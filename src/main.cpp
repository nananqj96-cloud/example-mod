// ============================================================================
//  Macro Compare - a Geode mod for Geometry Dash
//
//  Load a .gdr2 macro, tap along with it on your own key, and at 100% get a
//  "similarity to the real level" percentage for your click pattern.
//
//  This mod does NOT play anything for you. It only reads a macro file and
//  compares its input timeline with the key presses it saw from you.
//
//  Everything is in this one file on purpose, so there is only one file to
//  paste into the repository:
//    Part 1 - the .gdr2 reader (matches GDReplayFormat / GDR version 2)
//    Part 2 - mod state + settings helpers
//    Part 3 - the score
//    Part 4 - the hooks
// ============================================================================

#include <Geode/Geode.hpp>
#include <Geode/modify/CCKeyboardDispatcher.hpp>
#include <Geode/modify/GJBaseGameLayer.hpp>
#include <Geode/modify/PlayLayer.hpp>
#include <Geode/utils/Keyboard.hpp>

#include <algorithm>
#include <bit>
#include <cctype>
#include <cmath>
#include <cstdint>
#include <filesystem>
#include <fstream>
#include <iomanip>
#include <optional>
#include <sstream>
#include <stdexcept>
#include <string>
#include <utility>
#include <vector>

using namespace geode::prelude;

// ============================================================================
//  Part 1 - reading .gdr2 macro files
//
//  Layout taken from the reference implementation (GDReplayFormat, "gdr2"
//  branch), which is the format Mega Hack, xdBot, Eclipse and GD Mega Overlay
//  export. Everything is little-endian-free: numbers are varints, floats are
//  big-endian, strings are a varint length followed by raw bytes.
// ============================================================================
namespace gdr2 {

namespace {

class Reader {
    std::vector<std::uint8_t> const& m_bytes;
    std::size_t m_pos = 0;

public:
    explicit Reader(std::vector<std::uint8_t> const& bytes) : m_bytes(bytes) {}

    bool empty() const { return m_pos >= m_bytes.size(); }
    std::size_t remaining() const { return m_bytes.size() - m_pos; }

    std::uint8_t byte() {
        if (empty()) throw std::runtime_error("the file ends too early");
        return m_bytes[m_pos++];
    }

    void skip(std::uint64_t amount) {
        if (amount > remaining()) throw std::runtime_error("the file ends too early");
        m_pos += static_cast<std::size_t>(amount);
    }

    std::uint64_t varint() {
        std::uint64_t value = 0;
        for (unsigned i = 0; i < 10; ++i) {
            auto b = byte();
            if (i == 9 && b > 1) throw std::runtime_error("a number in the file is too big");
            value |= static_cast<std::uint64_t>(b & 0x7F) << (7 * i);
            if (!(b & 0x80)) return value;
        }
        throw std::runtime_error("a number in the file never ends");
    }

    std::string string() {
        auto length = varint();
        if (length > remaining()) throw std::runtime_error("a string runs past the end of the file");
        std::string out(
            reinterpret_cast<char const*>(m_bytes.data() + m_pos),
            static_cast<std::size_t>(length)
        );
        m_pos += static_cast<std::size_t>(length);
        return out;
    }

    bool boolean() {
        auto b = byte();
        if (b > 1) throw std::runtime_error("a boolean in the file is not 0 or 1");
        return b != 0;
    }

    float float32() {
        std::uint32_t bits = 0;
        for (int i = 0; i < 4; ++i) bits = (bits << 8) | byte();
        return std::bit_cast<float>(bits);
    }

    double float64() {
        std::uint64_t bits = 0;
        for (int i = 0; i < 8; ++i) bits = (bits << 8) | byte();
        return std::bit_cast<double>(bits);
    }
};

} // namespace

struct Macro {
    std::string author;
    std::string levelName;
    std::string botName;
    std::uint64_t levelId = 0;
    double framerate = 240.0;
    bool platformer = false;
    std::size_t deathCount = 0;
    std::size_t totalInputs = 0;
    std::vector<std::uint64_t> clickFrames; // player 1 press frames, sorted
};

inline Macro parse(std::vector<std::uint8_t> const& bytes) {
    if (bytes.size() < 8 || bytes[0] != 'G' || bytes[1] != 'D' || bytes[2] != 'R') {
        throw std::runtime_error("not a GDR file");
    }

    Reader r(bytes);
    r.skip(3); // "GDR"

    auto version = r.varint();
    if (version != 2) {
        throw std::runtime_error(".gdr2 files are version 2, this one is " + std::to_string(version));
    }

    auto inputTag = r.string();

    Macro m;
    m.author = r.string();
    r.string();          // description
    r.float32();         // duration in seconds
    r.varint();          // game version
    m.framerate = r.float64();
    r.varint();          // seed
    r.varint();          // coins
    r.boolean();         // low detail mode
    m.platformer = r.boolean();
    m.botName = r.string();
    r.varint();          // bot version
    m.levelId = r.varint();
    m.levelName = r.string();

    // writer extension blob
    r.skip(r.varint());

    // recorded death frames (only useful for replaying, we just skip them)
    m.deathCount = static_cast<std::size_t>(r.varint());
    for (std::size_t i = 0; i < m.deathCount; ++i) r.varint();

    auto totalInputs = r.varint();
    auto player1Inputs = r.varint();

    if (totalInputs > 20'000'000) throw std::runtime_error("the input count looks corrupt");
    if (player1Inputs > totalInputs) throw std::runtime_error("the player 1 count is larger than the total");

    if (!std::isfinite(m.framerate) || m.framerate <= 0.0) m.framerate = 240.0;

    m.totalInputs = static_cast<std::size_t>(totalInputs);

    bool hasInputExtension = !inputTag.empty();

    // Inputs are packed as varints: the low bit is press/release, the rest is
    // the number of frames since the previous input. Player 1 inputs come
    // first and player 2 inputs start over at frame 0.
    std::uint64_t frame = 0;
    for (std::uint64_t i = 0; i < totalInputs; ++i) {
        bool player2 = i >= player1Inputs;
        if (i == player1Inputs) frame = 0;

        auto packed = r.varint();
        frame += m.platformer ? (packed >> 3) : (packed >> 1);

        bool down = (packed & 1) != 0;
        if (hasInputExtension) r.skip(r.varint());

        if (!player2 && down) m.clickFrames.push_back(frame);
    }

    std::sort(m.clickFrames.begin(), m.clickFrames.end());
    return m;
}

} // namespace gdr2

// ============================================================================
//  Part 2 - mod state
// ============================================================================

namespace {

struct Run {
    // macro that is currently loaded
    bool macroLoaded = false;
    std::string macroError;
    std::string macroFileName;
    gdr2::Macro macro;

    // the attempt that is being recorded right now
    cocos2d::enumKeyCodes myKey = cocos2d::KEY_Space;
    bool recording = false;
    std::uint64_t tick = 0;
    std::vector<std::uint64_t> presses;

    bool announced = false;
};

Run g_run;

std::string fixed(double value, int decimals) {
    std::ostringstream out;
    out << std::fixed << std::setprecision(decimals) << value;
    return out.str();
}

std::string lowercase(std::string value) {
    for (auto& c : value) c = static_cast<char>(std::tolower(static_cast<unsigned char>(c)));
    return value;
}

// Spelling variants the player is likely to type, mapped to the official Geode
// key names from loader/src/utils/Keyboard.cpp
std::optional<char const*> canonicalKeyName(std::string const& input) {
    static std::pair<char const*, char const*> const variants[] = {
        { "space", "Space" },       { "spacebar", "Space" },
        { "enter", "Enter" },       { "return", "Enter" },
        { "esc", "Escape" },        { "escape", "Escape" },
        { "tab", "Tab" },           { "backspace", "Backspace" },
        { "shift", "Shift" },       { "ctrl", "Control" },      { "control", "Control" },
        { "alt", "Alt" },
        { "up", "ArrowUp" },        { "uparrow", "ArrowUp" },    { "arrowup", "ArrowUp" },
        { "down", "ArrowDown" },    { "downarrow", "ArrowDown" },{ "arrowdown", "ArrowDown" },
        { "left", "ArrowLeft" },    { "leftarrow", "ArrowLeft" },{ "arrowleft", "ArrowLeft" },
        { "right", "ArrowRight" },  { "rightarrow", "ArrowRight" },{ "arrowright", "ArrowRight" },
        { "insert", "Insert" },     { "delete", "Delete" },      { "del", "Delete" },
        { "home", "Home" },         { "end", "End" },
        { "capslock", "CapsLock" }, { "pageup", "PageUp" },      { "pagedown", "PageDown" },
        { "0", "Zero" },            { "1", "One" },              { "2", "Two" },
        { "3", "Three" },           { "4", "Four" },             { "5", "Five" },
        { "6", "Six" },             { "7", "Seven" },            { "8", "Eight" },
        { "9", "Nine" },
    };

    auto lower = lowercase(input);
    for (auto const& [variant, official] : variants) {
        if (lower == variant) return official;
    }
    return std::nullopt;
}

std::optional<cocos2d::enumKeyCodes> parseKey(std::string const& name) {
    if (auto parsed = Keybind::fromString(name); parsed.isOk()) {
        return parsed.unwrap().key;
    }
    if (auto official = canonicalKeyName(name); official) {
        if (auto parsed = Keybind::fromString(*official); parsed.isOk()) {
            return parsed.unwrap().key;
        }
    }
    if (name.size() == 1) {
        std::string single(1, static_cast<char>(std::toupper(static_cast<unsigned char>(name[0]))));
        if (auto parsed = Keybind::fromString(single); parsed.isOk()) {
            return parsed.unwrap().key;
        }
    }
    return std::nullopt;
}

// The key the player taps with, e.g. "Space", "K", "F5", "ArrowUp".
cocos2d::enumKeyCodes readKeySetting() {
    auto name = Mod::get()->getSettingValue<std::string>("click-key");

    if (auto key = parseKey(name); key) return *key;

    log::warn("Macro Compare: unknown key '{}', falling back to Space", name);
    return cocos2d::KEY_Space;
}

bool ghostKeyEnabled() {
    return Mod::get()->getSettingValue<bool>("ghost-key");
}

double toleranceMs() {
    auto value = Mod::get()->getSettingValue<int64_t>("tolerance-ms");
    return value > 0 ? static_cast<double>(value) : 120.0;
}

double myTickRate() {
    auto value = Mod::get()->getSettingValue<int64_t>("physics-tps");
    return value > 0 ? static_cast<double>(value) : 240.0;
}

void loadMacro() {
    g_run.macroLoaded = false;
    g_run.macroError.clear();
    g_run.macroFileName.clear();
    g_run.macro = gdr2::Macro{};

    auto path = Mod::get()->getSettingValue<std::filesystem::path>("macro-file");
    if (path.empty()) {
        g_run.macroError = "no macro file picked yet (Mods -> Macro Compare -> Macro file)";
        return;
    }

    g_run.macroFileName = path.filename().string();

    std::error_code ec;
    if (!std::filesystem::exists(path, ec)) {
        g_run.macroError = g_run.macroFileName + " is not there anymore";
        return;
    }

    std::ifstream file(path, std::ios::binary);
    if (!file) {
        g_run.macroError = "can't open " + g_run.macroFileName;
        return;
    }

    std::istreambuf_iterator<char> fileBegin(file);
    std::istreambuf_iterator<char> fileEnd;
    std::vector<std::uint8_t> bytes(fileBegin, fileEnd);

    if (bytes.empty()) {
        g_run.macroError = g_run.macroFileName + " is empty";
        return;
    }

    try {
        auto macro = gdr2::parse(bytes);
        if (macro.platformer) {
            g_run.macroError = "platformer replays aren't supported";
            return;
        }
        if (macro.clickFrames.empty()) {
            g_run.macroError = "this macro has no click inputs in it";
            return;
        }
        g_run.macro = std::move(macro);
        g_run.macroLoaded = true;
    } catch (std::exception const& e) {
        g_run.macroError = std::string("can't read the macro - ") + e.what();
    }

    log::info(
        "Macro Compare: file='{}' loaded={} clicks={} macroFps={} error='{}'",
        g_run.macroFileName, g_run.macroLoaded, g_run.macro.clickFrames.size(),
        g_run.macro.framerate, g_run.macroError
    );
}

void announceOnce() {
    if (g_run.announced) return;
    g_run.announced = true;

    if (!g_run.macroLoaded) {
        if (!g_run.macroError.empty()) {
            Notification::create("Macro Compare: " + g_run.macroError, NotificationIcon::Warning)->show();
        }
        return;
    }

    Notification::create(
        "Macro Compare armed: " + std::to_string(g_run.macro.clickFrames.size()) +
            " clicks from " + g_run.macroFileName,
        NotificationIcon::Info
    )->show();
}

} // namespace

// ============================================================================
//  Part 3 - the score
//
//  For every click in the macro we look for your closest click. Dead on scores
//  100%, and being off by the whole tolerance scores 0%. The total is divided
//  by the larger of the two click counts, so spamming extra clicks lowers the
//  score instead of raising it.
// ============================================================================

namespace {

struct Score {
    double percent = 0.0;
    int matched = 0;
    int macroClicks = 0;
    int myClicks = 0;
    double averageOffsetMs = 0.0;
    int early = 0;
    int late = 0;
};

Score computeScore() {
    Score out;
    out.macroClicks = static_cast<int>(g_run.macro.clickFrames.size());
    out.myClicks = static_cast<int>(g_run.presses.size());
    if (out.macroClicks == 0) return out;

    double tolerance = toleranceMs();
    double macroMsPerFrame = 1000.0 / (g_run.macro.framerate > 0.0 ? g_run.macro.framerate : 240.0);
    double myMsPerTick = 1000.0 / myTickRate();

    std::vector<double> macroTimes;
    macroTimes.reserve(g_run.macro.clickFrames.size());
    for (auto frame : g_run.macro.clickFrames) {
        macroTimes.push_back(static_cast<double>(frame) * macroMsPerFrame);
    }

    std::vector<double> myTimes;
    myTimes.reserve(g_run.presses.size());
    for (auto tick : g_run.presses) {
        myTimes.push_back(static_cast<double>(tick) * myMsPerTick);
    }

    std::size_t next = 0;
    double credit = 0.0;
    double offsetSum = 0.0;

    for (double macroTime : macroTimes) {
        // presses that are hopelessly early get counted as extras and skipped
        while (next < myTimes.size() && myTimes[next] < macroTime - tolerance) ++next;
        if (next >= myTimes.size()) break;

        std::size_t bestIndex = next;
        double bestOffset = std::fabs(myTimes[next] - macroTime);

        if (next + 1 < myTimes.size()) {
            double other = std::fabs(myTimes[next + 1] - macroTime);
            if (other < bestOffset) {
                bestOffset = other;
                bestIndex = next + 1;
            }
        }

        if (bestOffset > tolerance) continue;

        if (myTimes[bestIndex] < macroTime) ++out.early;
        else ++out.late;

        offsetSum += bestOffset;
        credit += 1.0 - (bestOffset / tolerance);
        ++out.matched;
        next = bestIndex + 1;
    }

    int denominator = std::max(out.macroClicks, out.myClicks);
    out.percent = denominator > 0 ? (credit / static_cast<double>(denominator)) * 100.0 : 0.0;
    out.averageOffsetMs = out.matched > 0 ? offsetSum / static_cast<double>(out.matched) : 0.0;
    return out;
}

void reportRun() {
    auto score = computeScore();
    auto percent = fixed(score.percent, 1) + "%";

    std::string details;
    details += "Similarity to the real level: " + percent + "\n";
    details += "Clicks matched: " + std::to_string(score.matched) + " / " +
               std::to_string(score.macroClicks) + " macro clicks\n";
    details += "Your clicks: " + std::to_string(score.myClicks);
    if (score.myClicks > score.matched) {
        details += " (" + std::to_string(score.myClicks - score.matched) + " extra)";
    }
    details += "\n";

    if (score.matched > 0) {
        details += "Average offset: " + fixed(score.averageOffsetMs, 1) + " ms (" +
                   std::to_string(score.early) + " early, " + std::to_string(score.late) + " late)\n";
    } else {
        details += "None of your clicks landed inside the tolerance window.\n";
    }
    details += "Tolerance: " + fixed(toleranceMs(), 0) + " ms";

    auto icon = score.percent >= 90.0 ? NotificationIcon::Success
              : score.percent >= 70.0 ? NotificationIcon::Info
                                      : NotificationIcon::Warning;

    Notification::create("Similarity to the real level: " + percent, icon)->show();
    FLAlertLayer::create("Macro Compare", details, "OK")->show();

    log::info(
        "Macro Compare: {} - matched {}/{} macro clicks, {} clicks from the player",
        percent, score.matched, score.macroClicks, score.myClicks
    );
}

} // namespace

// ============================================================================
//  Part 4 - hooks
// ============================================================================

class $modify(MacroComparePlayLayer, PlayLayer) {
    bool init(GJGameLevel* level, bool useReplay, bool dontCreateObjects) {
        if (!PlayLayer::init(level, useReplay, dontCreateObjects)) return false;

        g_run.announced = false;
        g_run.recording = false;
        g_run.tick = 0;
        g_run.presses.clear();
        g_run.myKey = readKeySetting();

        loadMacro();
        return true;
    }

    void resetLevel() {
        PlayLayer::resetLevel();

        g_run.tick = 0;
        g_run.presses.clear();
        g_run.myKey = readKeySetting();
        g_run.recording = g_run.macroLoaded && !m_isPracticeMode;

        announceOnce();
    }

    void levelComplete() {
        PlayLayer::levelComplete();

        if (!g_run.recording) return;
        reportRun();
    }
};

class $modify(MacroCompareBaseLayer, GJBaseGameLayer) {
    void update(float dt) {
        GJBaseGameLayer::update(dt);

        auto playLayer = PlayLayer::get();
        if (g_run.recording && playLayer && static_cast<GJBaseGameLayer*>(playLayer) == this) {
            ++g_run.tick;
        }
    }
};

class $modify(MacroCompareKeyboard, CCKeyboardDispatcher) {
    bool dispatchKeyboardMSG(cocos2d::enumKeyCodes key, bool isKeyDown, bool isKeyRepeat, double timestamp) {
        if (g_run.recording && isKeyDown && !isKeyRepeat && key == g_run.myKey) {
            g_run.presses.push_back(g_run.tick);

            // Ghost mode: swallow the key so it never reaches the level.
            if (ghostKeyEnabled()) return true;
        }

        return CCKeyboardDispatcher::dispatchKeyboardMSG(key, isKeyDown, isKeyRepeat, timestamp);
    }
};

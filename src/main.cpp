// ============================================================================
//  Macro Compare - a Geode mod for Geometry Dash
//
//  Two features:
//
//   1. Macro compare - load a .gdr2 macro, tap along on your own key, and at
//      100% get a "similarity to the real level" percentage for your clicks.
//
//   2. Spam correcter - watches two keys you pick. When you are alternate
//      mashing them fast enough to count as spam, your presses are blocked and
//      replaced with a clean, fully custom hold/release pattern.
//
//  The mod never plays a level for you: feature 1 only reads a macro file and
//  compares it, feature 2 only cleans up inputs you are already making.
//
//  Everything is in this one file on purpose, so there is only one file to
//  paste into the repository:
//    Part 1 - the .gdr2 reader (matches GDReplayFormat / GDR version 2)
//    Part 2 - spam correcter logic (pure, testable)
//    Part 3 - mod state + settings helpers
//    Part 4 - the score
//    Part 5 - the spam correcter, wired to the game
//    Part 6 - the hooks
// ============================================================================

#include <Geode/Geode.hpp>
#include <Geode/modify/CCKeyboardDispatcher.hpp>
#include <Geode/modify/GJBaseGameLayer.hpp>
#include <Geode/modify/PlayLayer.hpp>
#include <Geode/utils/Keyboard.hpp>

#include <algorithm>
#include <bit>
#include <cctype>
#include <chrono>
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
//  export. Numbers are varints, floats are big-endian, strings are a varint
//  length followed by raw bytes.
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
//  Part 2 - spam correcter logic
//
//  No Geode calls in here on purpose, so it can be tested on its own:
//    Pattern  - the "hold N frames, release M frames" loop
//    Runner   - steps that loop one frame at a time
//    Detector - decides whether you are actually alternate spamming
// ============================================================================
namespace spamfix {

constexpr int kMaxPhases = 64;
constexpr int kMaxPhaseFrames = 1000;

struct Pattern {
    std::vector<int> phases; // hold, release, hold, release, ... in frames

    std::size_t size() const { return phases.size(); }
    bool holdsAt(std::size_t index) const { return index % 2 == 0; }
};

// "10,10" -> hold 10 frames, release 10 frames, then start over.
// "10"    -> the same thing. "5,5,3,3" -> two rhythms alternating.
// Anything unreadable falls back to 10,10.
inline Pattern parsePattern(std::string const& text) {
    std::vector<int> values;
    long long current = -1;

    for (char c : text) {
        if (c >= '0' && c <= '9') {
            current = (current < 0 ? 0 : current) * 10 + (c - '0');
            if (current > kMaxPhaseFrames) current = kMaxPhaseFrames;
        } else if (current >= 0) {
            values.push_back(static_cast<int>(current));
            current = -1;
        }
    }
    if (current >= 0) values.push_back(static_cast<int>(current));

    std::vector<int> kept;
    for (int value : values) {
        if (value > 0) kept.push_back(std::min(value, kMaxPhaseFrames));
    }

    if (kept.empty()) kept.push_back(10);                  // nothing usable -> 10/10
    if (kept.size() == 1) kept.push_back(kept.front());    // "10" means 10 on, 10 off
    if (kept.size() % 2 != 0) kept.push_back(kept.back()); // always finish on a release
    if (kept.size() > kMaxPhases) kept.resize(kMaxPhases);

    Pattern pattern;
    pattern.phases = std::move(kept);
    return pattern;
}

// Steps the pattern one frame at a time and says whether the key should be held.
struct Runner {
    Pattern pattern = parsePattern("10,10");
    std::size_t phase = 0;
    int phaseFrames = 0;

    void restart() {
        phase = 0;
        phaseFrames = 0;
    }

    bool outputWanted() const {
        return pattern.phases.empty() ? false : pattern.holdsAt(phase);
    }

    void step() {
        if (pattern.phases.empty()) return;
        if (++phaseFrames >= pattern.phases[phase]) {
            phaseFrames = 0;
            phase = (phase + 1) % pattern.size();
        }
    }
};

struct Detector {
    int needed = 4;          // presses that have to alternate in a row
    double windowMs = 250.0; // and the longest gap allowed between them

    struct Press {
        int key = 0;
        double timeMs = 0.0;
    };

    std::vector<Press> presses;

    void reset() { presses.clear(); }

    void note(int key, double timeMs) {
        // anything older than the window can never be part of a spam burst
        while (!presses.empty() && timeMs - presses.front().timeMs > windowMs) {
            presses.erase(presses.begin());
        }
        presses.push_back({key, timeMs});
        if (presses.size() > 64) presses.erase(presses.begin());
    }

    bool alternating() const {
        if (static_cast<int>(presses.size()) < needed) return false;

        auto start = presses.size() - static_cast<std::size_t>(needed);
        for (std::size_t i = start; i + 1 < presses.size(); ++i) {
            if (presses[i].key == presses[i + 1].key) return false;                 // same key twice
            if (presses[i + 1].timeMs - presses[i].timeMs > windowMs) return false; // too slow
        }
        return true;
    }
};

} // namespace spamfix

// ============================================================================
//  Part 3 - mod state + settings helpers
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

// The key you tap along with, e.g. "Space", "K", "F5", "ArrowUp".
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
//  Part 4 - the score
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
//  Part 5 - the spam correcter, wired to the game
//
//  Detect alternate spam -> block those two keys -> play the pattern instead.
//  The corrected presses are re-sent through the keyboard dispatcher, which is
//  the exact same path your real keys take, so the jump lands like a normal
//  press no matter which jump key you use.
// ============================================================================

namespace {

struct SpamSettings {
    bool enabled = false;
    cocos2d::enumKeyCodes key1 = cocos2d::KEY_RightShift;
    cocos2d::enumKeyCodes key2 = cocos2d::KEY_W;
    cocos2d::enumKeyCodes output = cocos2d::KEY_Space;
    std::string patternText = "10,10";
    int detectMs = 250;
    int alternations = 4;
};

SpamSettings g_spamSettings;
spamfix::Runner g_spamRunner;
spamfix::Detector g_spamDetector;
bool g_spamEngaged = false;
bool g_spamOutputDown = false;
double g_spamLastPressMs = 0.0;
bool g_spamSawKey1 = false;
bool g_spamSawKey2 = false;
int g_framesSinceSettingRefresh = 1000;
bool g_spamSettingsInitialized = false;

// The dispatcher hands us its own pointer the first time a key is pressed, so
// we never have to guess at a singleton accessor.
CCKeyboardDispatcher* g_dispatcher = nullptr;

// true while this mod is sending its own key events, so they are not treated
// as the player's input
bool g_synthesizing = false;

double nowMs() {
    using clock = std::chrono::steady_clock;
    return std::chrono::duration<double, std::milli>(clock::now().time_since_epoch()).count();
}

cocos2d::enumKeyCodes readKeybindSetting(char const* key, cocos2d::enumKeyCodes fallback) {
    auto binds = Mod::get()->getSettingValue<std::vector<Keybind>>(key);
    if (!binds.empty()) return binds.front().key;
    return fallback;
}

void refreshSpamSettings() {
    g_spamSettings.enabled = Mod::get()->getSettingValue<bool>("spam-enabled");
    g_spamSettings.detectMs = static_cast<int>(Mod::get()->getSettingValue<int64_t>("spam-detect-ms"));
    g_spamSettings.alternations = static_cast<int>(Mod::get()->getSettingValue<int64_t>("spam-alternations"));
    g_spamSettings.patternText = Mod::get()->getSettingValue<std::string>("spam-pattern");
    g_spamSettings.key1 = readKeybindSetting("spam-key-1", cocos2d::KEY_RightShift);
    g_spamSettings.key2 = readKeybindSetting("spam-key-2", cocos2d::KEY_W);
    g_spamSettings.output = parseKey(Mod::get()->getSettingValue<std::string>("spam-output-key"))
                                .value_or(cocos2d::KEY_Space);

    g_spamRunner.pattern = spamfix::parsePattern(g_spamSettings.patternText);
    g_spamDetector.needed = std::clamp(g_spamSettings.alternations, 2, 10);
    g_spamDetector.windowMs = static_cast<double>(std::clamp(g_spamSettings.detectMs, 60, 1000));

    bool wasInitialized = g_spamSettingsInitialized;
    g_spamSettingsInitialized = true;

    if (!wasInitialized && g_spamSettings.enabled) {
        Notification::create("Spam correcter ready", NotificationIcon::Info)->show();
        log::info("Macro Compare: spam correcter ready; key1={}, key2={}, output={}, pattern={}",
            static_cast<int>(g_spamSettings.key1), static_cast<int>(g_spamSettings.key2),
            static_cast<int>(g_spamSettings.output), g_spamSettings.patternText);
    }

    if (g_spamSettings.enabled && g_spamSettings.key1 == g_spamSettings.key2) {
        log::warn("Macro Compare: the two spam keys are the same key - the correcter needs two different keys");
    }
}

void setSpamOutput(bool down) {
    if (g_spamOutputDown == down) return;
    g_spamOutputDown = down;

    if (!g_dispatcher) {
        log::warn("Macro Compare: no keyboard dispatcher yet, corrected input not sent");
        return;
    }

    g_synthesizing = true;
    g_dispatcher->dispatchKeyboardMSG(g_spamSettings.output, down, false, nowMs() / 1000.0);
    g_synthesizing = false;
}

void disengageSpam() {
    bool wasEngaged = g_spamEngaged;
    g_spamEngaged = false;
    g_spamRunner.restart();
    g_spamDetector.reset();
    g_spamSawKey1 = false;
    g_spamSawKey2 = false;
    setSpamOutput(false);
    if (wasEngaged) {
        Notification::create("Spam correcter stopped", NotificationIcon::Info)->show();
        log::info("Macro Compare: spam correcter stopped after player inactivity");
    }
}

void engageSpam() {
    g_spamEngaged = true;
    g_spamRunner.restart();
    Notification::create("Spam correcter ACTIVE", NotificationIcon::Success)->show();
    log::info("Macro Compare: spam correcter engaged ({} phases for pattern '{}')",
        g_spamRunner.pattern.size(), g_spamSettings.patternText);
}

// Runs once per game frame while a level is playing.
void spamFrame() {
    if (--g_framesSinceSettingRefresh <= 0) {
        g_framesSinceSettingRefresh = 60; // re-read the settings a few times a second
        refreshSpamSettings();
    }

    if (!g_spamSettings.enabled) {
        if (g_spamEngaged) disengageSpam();
        return;
    }

    // stopped mashing? hand the keys back to the player
    if (g_spamEngaged && nowMs() - g_spamLastPressMs > g_spamDetector.windowMs) {
        disengageSpam();
        return;
    }

    if (!g_spamEngaged) return;

    setSpamOutput(g_spamRunner.outputWanted());
    g_spamRunner.step();
}

void noteSpamSource(int which) {
    if (!g_spamSettingsInitialized) refreshSpamSettings();
    if (!g_spamSettings.enabled) return;

    auto now = nowMs();
    if (which == 0 && !g_spamSawKey1) {
        g_spamSawKey1 = true;
        Notification::create("Spam key 1 detected", NotificationIcon::Info)->show();
    }
    if (which == 1 && !g_spamSawKey2) {
        g_spamSawKey2 = true;
        Notification::create("Spam key 2 detected", NotificationIcon::Info)->show();
    }

    g_spamLastPressMs = now;
    g_spamDetector.note(which, now);
    if (!g_spamEngaged && g_spamDetector.alternating()) engageSpam();
}

} // namespace

$on_game(Loaded) {
    listenForKeybindSettingPresses("spam-key-1", [](Keybind const&, bool down, bool repeat, double) {
        if (down && !repeat) noteSpamSource(0);
    });
    listenForKeybindSettingPresses("spam-key-2", [](Keybind const&, bool down, bool repeat, double) {
        if (down && !repeat) noteSpamSource(1);
    });
}

// ============================================================================
//  Part 6 - hooks
// ============================================================================

class $modify(MacroComparePlayLayer, PlayLayer) {
    bool init(GJGameLevel* level, bool useReplay, bool dontCreateObjects) {
        if (!PlayLayer::init(level, useReplay, dontCreateObjects)) return false;

        g_run.announced = false;
        g_run.recording = false;
        g_run.tick = 0;
        g_run.presses.clear();
        g_run.myKey = readKeySetting();

        g_framesSinceSettingRefresh = 0;
        refreshSpamSettings();
        disengageSpam();

        loadMacro();
        return true;
    }

    void resetLevel() {
        PlayLayer::resetLevel();

        g_run.tick = 0;
        g_run.presses.clear();
        g_run.myKey = readKeySetting();
        g_run.recording = g_run.macroLoaded && !m_isPracticeMode;

        // every new attempt gets a fresh correcter too
        g_framesSinceSettingRefresh = 0;
        refreshSpamSettings();
        disengageSpam();

        announceOnce();
    }

    // Drive spam correction from PlayLayer itself, independently of whether
    // macro comparison is armed or a macro file is loaded.
    void update(float dt) {
        PlayLayer::update(dt);
        spamFrame();
    }

    // leaving the level must never leave the corrected jump key stuck down
    void onQuit() {
        disengageSpam();
        PlayLayer::onQuit();
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
        if (playLayer && static_cast<GJBaseGameLayer*>(playLayer) == this && g_run.recording) {
            ++g_run.tick;
        }

    }
};

class $modify(MacroCompareKeyboard, CCKeyboardDispatcher) {
    bool dispatchKeyboardMSG(cocos2d::enumKeyCodes key, bool isKeyDown, bool isKeyRepeat, double timestamp) {
        // remember the dispatcher so the correcter can send its own events later
        g_dispatcher = this;

        // events this mod sends itself go straight through: no recording, no blocking
        if (g_synthesizing) {
            return CCKeyboardDispatcher::dispatchKeyboardMSG(key, isKeyDown, isKeyRepeat, timestamp);
        }

        // your taps for the macro comparison are recorded before anything is blocked
        if (g_run.recording && isKeyDown && !isKeyRepeat && key == g_run.myKey) {
            g_run.presses.push_back(g_run.tick);

            // Ghost mode: swallow the key so it never reaches the level.
            if (ghostKeyEnabled()) return true;
        }

        // Load settings before the first keyboard event. Previously the first
        // presses could arrive while the in-memory setting was still disabled.
        if (!g_spamSettingsInitialized) refreshSpamSettings();

        // spam correcter: source presses are observed internally, then swallowed
        // only after a valid alternating burst has been detected.
        if (g_spamSettings.enabled && (key == g_spamSettings.key1 || key == g_spamSettings.key2)) {
            // While active, block both source key-down and repeat events. Key-up
            // is allowed through so a key held before engagement cannot stick.
            if (g_spamEngaged && isKeyDown) return true;
        }

        return CCKeyboardDispatcher::dispatchKeyboardMSG(key, isKeyDown, isKeyRepeat, timestamp);
    }
};

#include <Geode/Geode.hpp>
#include <Geode/modify/PlayLayer.hpp>
#include <Geode/modify/GJBaseGameLayer.hpp>
#include <algorithm>
#include <random>
#include <string>

using namespace geode::prelude;
using namespace cocos2d;

namespace {

class HeartRateDisplay : public CCNode {
    CCScale9Sprite* m_panel = nullptr;
    CCDrawNode* m_border = nullptr;
    CCLabelTTF* m_heart = nullptr;
    CCLabelBMFont* m_bpm = nullptr;
    CCLabelBMFont* m_percent = nullptr;
    int m_bpmValue = 100;
    int m_target = 100;
    int m_zone = -1;
    float m_elapsedSeconds = 0.f;
    int m_targetAge = 0;
    std::mt19937 m_rng{std::random_device{}()};
    bool m_showPercent = true;

    bool init() {
        if (!CCNode::init()) return false;
        m_heart = CCLabelTTF::create("❤️", "Arial", 24.f);
        m_bpm = CCLabelBMFont::create("100 BPM", "bigFont.fnt");
        m_percent = CCLabelBMFont::create("0%", "bigFont.fnt");
        if (!m_heart || !m_bpm || !m_percent) return false;

        // Black rounded monitor body, white outline, red heart and red digits.
        m_panel = CCScale9Sprite::create("square02_001.png");
        if (m_panel) {
            m_panel->setContentSize({220.f, 64.f});
            m_panel->setColor({0, 0, 0});
            m_panel->setOpacity(235);
            addChild(m_panel, -1);
            m_panel->setPosition({110.f, 32.f});
            m_border = CCDrawNode::create();
            if (m_border) {
                CCPoint corners[] = {{7.f, 7.f}, {213.f, 7.f}, {213.f, 57.f}, {7.f, 57.f}};
                m_border->drawPolygon(corners, 4, {0.f, 0.f, 0.f, 0.f}, 3.f, {1.f, 1.f, 1.f, 1.f});
                addChild(m_border, 2);
            }
        }

        m_heart->setColor({255, 70, 80});
        m_heart->setScale(.9f);
        m_bpm->setScale(.42f);
        m_percent->setScale(.32f);
        m_heart->setAnchorPoint({1.f, .5f});
        m_bpm->setAnchorPoint({0.f, .5f});
        m_percent->setAnchorPoint({1.f, .5f});
        addChild(m_heart);
        addChild(m_bpm);
        addChild(m_percent);
        // The heart is directly next to the BPM value.
        m_heart->setPosition({39.f, 36.f});
        m_bpm->setPosition({62.f, 36.f});
        m_percent->setPosition({190.f, 10.f});
        setContentSize({220.f, 64.f});
        return true;
    }

    int setting(char const* id, int fallback) {
        auto value = Mod::get()->getSettingValue<int64_t>(id);
        return static_cast<int>(value == 0 ? fallback : value);
    }

    void chooseTarget(int percent) {
        int first = std::clamp(setting("first-threshold", 40), 1, 98);
        int second = std::clamp(setting("second-threshold", 60), first + 1, 99);
        int zone = percent <= first ? 0 : (percent <= second ? 1 : 2);
        int low = setting(zone == 0 ? "zone-1-min" : zone == 1 ? "zone-2-min" : "zone-3-min", 90);
        int high = setting(zone == 0 ? "zone-1-max" : zone == 1 ? "zone-2-max" : "zone-3-max", 110);
        if (low > high) std::swap(low, high);

        // Select a fresh target when entering a zone and periodically while
        // staying in it. Real heart-rate displays keep changing during a
        // steady section instead of freezing at one number.
        ++m_targetAge;
        if (zone != m_zone || m_targetAge >= 18) {
            std::uniform_int_distribution<int> pick(low, high);
            m_target = pick(m_rng);
            m_targetAge = 0;
            m_zone = zone;
        }

        // Keep the current value inside the active zone if the user changed
        // settings while playing.
        m_bpmValue = std::clamp(m_bpmValue, low, high);
        m_target = std::clamp(m_target, low, high);
    }

    void applyScale() {
        float scale = static_cast<float>(Mod::get()->getSettingValue<double>("monitor-scale"));
        if (scale <= 0.f) scale = .7f;
        scale = std::clamp(scale, .4f, 1.5f);
        setScale(scale);
    }

public:
    static HeartRateDisplay* create() {
        auto ret = new HeartRateDisplay();
        if (ret && ret->init()) { ret->applyScale(); ret->autorelease(); return ret; }
        delete ret;
        return nullptr;
    }

    void tick(int percent, float dt) {
        // Real-time timer: this is one second of elapsed game time, not a
        // fixed number of frames. It works consistently at 60, 144, 240 FPS.
        m_elapsedSeconds += std::max(dt, 0.f);
        if (m_elapsedSeconds < 1.f) return;
        m_elapsedSeconds -= 1.f;
        chooseTarget(percent);

        // Keep the display alive and moving even when the player remains in
        // one percentage zone: real heart-rate monitors fluctuate continuously.
        if (m_target == m_bpmValue) {
            int low = m_zone == 0 ? setting("zone-1-min", 90) : m_zone == 1 ? setting("zone-2-min", 120) : setting("zone-3-min", 140);
            int high = m_zone == 0 ? setting("zone-1-max", 110) : m_zone == 1 ? setting("zone-2-max", 140) : setting("zone-3-max", 220);
            std::uniform_int_distribution<int> pick(std::min(low, high), std::max(low, high));
            m_target = pick(m_rng);
        }

        int speed = std::clamp(setting("change-speed", 8), 8, 20);
        int lowBound = m_zone == 0 ? setting("zone-1-min", 90) : m_zone == 1 ? setting("zone-2-min", 120) : setting("zone-3-min", 140);
        int highBound = m_zone == 0 ? setting("zone-1-max", 110) : m_zone == 1 ? setting("zone-2-max", 140) : setting("zone-3-max", 220);
        if (lowBound > highBound) std::swap(lowBound, highBound);

        // Pick a target at least three BPM away. This loop prevents the
        // display from ever producing a 1- or 2-BPM visible change.
        int difference = std::abs(m_target - m_bpmValue);
        if (difference < 3 && highBound - lowBound >= 6) {
            std::uniform_int_distribution<int> pick(lowBound, highBound);
            do { m_target = pick(m_rng); }
            while (std::abs(m_target - m_bpmValue) < 3);
            difference = std::abs(m_target - m_bpmValue);
        }

        if (difference >= 3) {
            std::uniform_int_distribution<int> jump(3, speed);
            int step = jump(m_rng);
            if (m_bpmValue < m_target) m_bpmValue = std::min(m_bpmValue + step, m_target);
            else if (m_bpmValue > m_target) m_bpmValue = std::max(m_bpmValue - step, m_target);
        }

        // Hard bounds after movement.
        m_bpmValue = std::clamp(m_bpmValue, lowBound, highBound);
        m_bpm->setString((std::to_string(m_bpmValue) + " BPM").c_str());
        m_showPercent = Mod::get()->getSettingValue<bool>("show-percentage");
        m_percent->setVisible(m_showPercent);
        m_percent->setString((std::to_string(percent) + "%").c_str());

        float pulse = 1.f + .06f * std::sin(static_cast<float>(m_targetAge) * .35f);
        m_heart->setScale(.65f * pulse);
    }
};

HeartRateDisplay* g_display = nullptr;

}

void updateHeartRateForPlayLayer(PlayLayer* layer, float dt) {
    if (!g_display || !layer) return;
    bool enabled = Mod::get()->getSettingValue<bool>("enabled");
    g_display->setVisible(enabled);
    if (!enabled) return;

    float livePercent = layer->getCurrentPercent();
    // Geode's PlayLayer::getCurrentPercent() is already 0..100.
    // Do not multiply values below 1 by 100: 0.68 means 0.68%, not 68%.
    int percent = std::clamp(static_cast<int>(livePercent + 0.5f), 0, 100);
    g_display->tick(percent, dt);
}

class $modify(FakeHeartRatePlayLayer, PlayLayer) {
    bool init(GJGameLevel* level, bool useReplay, bool dontCreateObjects) {
        if (!PlayLayer::init(level, useReplay, dontCreateObjects)) return false;
        g_display = HeartRateDisplay::create();
        if (g_display) {
            auto size = CCDirector::sharedDirector()->getWinSize();
            g_display->setPosition({size.width - 230.f, size.height - 82.f});
            addChild(g_display, 9999);
        }
        return true;
    }

    void update(float dt) {
        PlayLayer::update(dt);
    }

    void onQuit() {
        g_display = nullptr;
        PlayLayer::onQuit();
    }
};


class $modify(FakeHeartRateGameLayer, GJBaseGameLayer) {
    void update(float dt) {
        GJBaseGameLayer::update(dt);
        auto layer = PlayLayer::get();
        if (layer && static_cast<GJBaseGameLayer*>(layer) == this) {
            updateHeartRateForPlayLayer(layer, dt);
        }
    }
};

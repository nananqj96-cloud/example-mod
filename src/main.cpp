#include <Geode/Geode.hpp>
#include <Geode/modify/PlayLayer.hpp>
#include <algorithm>
#include <random>
#include <string>

using namespace geode::prelude;
using namespace cocos2d;

namespace {

class HeartRateDisplay : public CCNode {
    CCLabelBMFont* m_heart = nullptr;
    CCLabelBMFont* m_bpm = nullptr;
    CCLabelBMFont* m_percent = nullptr;
    std::mt19937 m_rng{std::random_device{}()};
    int m_bpmValue = 100;
    int m_target = 100;
    int m_zone = -1;
    int m_frames = 0;
    bool m_showPercent = true;

    bool init() {
        if (!CCNode::init()) return false;
        m_heart = CCLabelBMFont::create("♥", "bigFont.fnt");
        m_bpm = CCLabelBMFont::create("100 BPM", "bigFont.fnt");
        m_percent = CCLabelBMFont::create("0%", "bigFont.fnt");
        if (!m_heart || !m_bpm || !m_percent) return false;

        m_heart->setColor({255, 70, 80});
        m_heart->setScale(.65f);
        m_bpm->setScale(.42f);
        m_percent->setScale(.32f);
        m_heart->setAnchorPoint({1.f, .5f});
        m_bpm->setAnchorPoint({1.f, .5f});
        m_percent->setAnchorPoint({1.f, .5f});
        addChild(m_heart);
        addChild(m_bpm);
        addChild(m_percent);
        m_heart->setPosition({180.f, 48.f});
        m_bpm->setPosition({180.f, 27.f});
        m_percent->setPosition({180.f, 9.f});
        setContentSize({190.f, 65.f});
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
        if (zone == m_zone) return;

        int low = setting(zone == 0 ? "zone-1-min" : zone == 1 ? "zone-2-min" : "zone-3-min", 90);
        int high = setting(zone == 0 ? "zone-1-max" : zone == 1 ? "zone-2-max" : "zone-3-max", 110);
        if (low > high) std::swap(low, high);
        std::uniform_int_distribution<int> pick(low, high);
        m_target = pick(m_rng);
        m_zone = zone;
    }

public:
    static HeartRateDisplay* create() {
        auto ret = new HeartRateDisplay();
        if (ret && ret->init()) { ret->autorelease(); return ret; }
        delete ret;
        return nullptr;
    }

    void tick(int percent) {
        if (++m_frames < setting("update-interval", 6)) return;
        m_frames = 0;
        chooseTarget(percent);

        int speed = std::clamp(setting("change-speed", 3), 1, 20);
        if (m_bpmValue < m_target) m_bpmValue = std::min(m_bpmValue + speed, m_target);
        if (m_bpmValue > m_target) m_bpmValue = std::max(m_bpmValue - speed, m_target);

        m_bpm->setString((std::to_string(m_bpmValue) + " BPM").c_str());
        m_showPercent = Mod::get()->getSettingValue<bool>("show-percentage");
        m_percent->setVisible(m_showPercent);
        m_percent->setString((std::to_string(percent) + "%").c_str());

        float pulse = 1.f + .06f * std::sin(static_cast<float>(m_frames) * .5f);
        m_heart->setScale(.65f * pulse);
    }
};

HeartRateDisplay* g_display = nullptr;

}

class $modify(FakeHeartRatePlayLayer, PlayLayer) {
    bool init(GJGameLevel* level, bool useReplay, bool dontCreateObjects) {
        if (!PlayLayer::init(level, useReplay, dontCreateObjects)) return false;
        g_display = HeartRateDisplay::create();
        if (g_display) {
            auto size = CCDirector::sharedDirector()->getWinSize();
            g_display->setPosition({size.width - 205.f, size.height - 78.f});
            addChild(g_display, 9999);
        }
        return true;
    }

    void update(float dt) {
        PlayLayer::update(dt);
        if (!g_display) return;
        bool enabled = Mod::get()->getSettingValue<bool>("enabled");
        g_display->setVisible(enabled);
        if (enabled) g_display->tick(std::clamp(getCurrentPercentInt(), 0, 100));
    }

    void onQuit() {
        g_display = nullptr;
        PlayLayer::onQuit();
    }
};

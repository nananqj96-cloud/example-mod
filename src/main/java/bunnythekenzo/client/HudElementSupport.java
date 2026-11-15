package bunnythekenzo.client;

import bunnythekenzo.client.setting.BooleanSetting;

public class HudElementSupport implements HudElementContract {
  public final BooleanSetting booleanSetting;

  public HudElementSupport(BooleanSetting booleanSetting2) {
    this.booleanSetting = booleanSetting2;
  }

  @Override
  public boolean test() {
    return (Boolean) this.booleanSetting.getValue();
  }

  @Override
  public void apply(boolean flag) {
    this.booleanSetting.setValue(flag);
  }

  @Override
  public void update() {
    this.booleanSetting.resetToDefault();
  }
}

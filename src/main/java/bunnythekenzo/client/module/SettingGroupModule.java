package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.Setting;

public class SettingGroupModule extends Module {
  public SettingGroupModule(
      String text, String text2, ModuleCategory moduleCategory, Setting[] settingArray) {
    super(text, text2, moduleCategory);

    for (Setting setting : settingArray) {
      this.registerSetting(setting);
    }
  }
}

package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.NumberSetting;

public class FullBrightModule extends Module {
  public final NumberSetting gammaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Gamma", "Brightness boost", 12.0, 1.0, 15.0, 1.0));

  public FullBrightModule() {
    super("FullBright", "Maximum brightness everywhere", ModuleCategory.RENDER);
  }
}

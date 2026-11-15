package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ColorSetting;

public class MobESPModule extends Module {
  public final ColorSetting hostileSetting =
      (ColorSetting) this.registerSetting(new ColorSetting("Hostile", "Hostile color", -45715));
  public final ColorSetting passiveSetting =
      (ColorSetting) this.registerSetting(new ColorSetting("Passive", "Passive color", -12654960));
  public final BooleanSetting passiveTooSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Passive Too", "Include passive mobs", false));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Tracers", "Thin lines from the crosshair to nearby mobs", false));

  public MobESPModule() {
    super("MobESP", "Highlights hostile mobs", ModuleCategory.RENDER);
  }
}

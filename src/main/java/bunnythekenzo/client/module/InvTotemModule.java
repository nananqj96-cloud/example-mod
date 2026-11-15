package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class InvTotemModule extends Module {
  public final NumberSetting delaySetting =
      (NumberSetting) this.registerSetting(new NumberSetting("Delay", "", 4.0, 1.0, 40.0, 1.0));
  public final BooleanSetting forceTotemSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("Force Totem", "", false));
  public final BooleanSetting hotbarTotemSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("Hotbar Totem", "", false));
  public final NumberSetting hotbarSlotSetting =
      (NumberSetting)
          this.registerSetting(new NumberSetting("Hotbar Slot", "", 1.0, 1.0, 9.0, 1.0));
  public final NumberSetting hotbarDelaySetting =
      (NumberSetting)
          this.registerSetting(new NumberSetting("Hotbar Delay", "", 4.0, 1.0, 40.0, 1.0));

  public InvTotemModule() {
    super(
        "Inv Totem",
        "Moves totems only while inventory (E) is open — click-based, not world-auto",
        ModuleCategory.COMBAT);
  }
}

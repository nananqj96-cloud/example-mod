package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.KeybindSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class DoubleAnchorModule extends Module {
  public final KeybindSetting activateKeySetting;
  public final NumberSetting timingSetting;
  public final NumberSetting detonateSlotSetting;
  public final BooleanSetting rePlaceSetting;
  public final BooleanSetting switchBackSetting;

  public static final long timestamp = -3129235978418388920L;

  public DoubleAnchorModule() {
    super("Double Anchor", "Two rapid anchor charge→detonate cycles", ModuleCategory.COMBAT);
    this.activateKeySetting =
        (KeybindSetting)
            this.registerSetting(new KeybindSetting("Activate Key", "Hold to run the combo", 72));
    this.timingSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Timing", "Delay between the two blows", 120.0, 40.0, 400.0, 10.0, "ms"));
    this.detonateSlotSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Detonate Slot",
                    "Hotbar slot to hold while detonating (anything but glowstone)",
                    1.0,
                    1.0,
                    9.0,
                    1.0));
    this.rePlaceSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Re-Place",
                    "Place a fresh anchor for the second blow if the first is consumed",
                    true));
    this.switchBackSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Switch Back", "Return to the previous hotbar slot when done", true));
  }
}

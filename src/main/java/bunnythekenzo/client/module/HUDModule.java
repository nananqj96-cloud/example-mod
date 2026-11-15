package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class HUDModule extends Module {
  public final BooleanSetting showHiddenInEditorSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Hidden In Editor",
                  "Preview disabled HUD elements while editing in chat",
                  true));
  public final BooleanSetting watermarkSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("Watermark", "The bunnythekenzo wordmark", true));
  public final BooleanSetting arrayListSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("ArrayList", "Enabled modules list", true));
  public final BooleanSetting fpsSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("FPS", "Framerate readout", true));
  public final BooleanSetting pingSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("Ping", "Latency readout", false));
  public final BooleanSetting coordinatesSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Coordinates", "Block position readout", true));
  public final BooleanSetting directionSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Direction", "Facing readout", true));
  public final BooleanSetting tpsSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("TPS", "Server tick rate estimate", false));
  public final BooleanSetting cpsSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("CPS", "Clicks per second", false));
  public final BooleanSetting armorSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Armor", "Equipped armor + durability", false));
  public final BooleanSetting potionsSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Potions", "Active effects with timers", false));
  public final BooleanSetting keystrokesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Keystrokes", "WASD + mouse + space display", false));
  public final BooleanSetting radarSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Radar", "Circular player radar", true));
  public final BooleanSetting listThemeSyncSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("List Theme Sync", "ArrayList follows the theme color", true));
  public final ColorSetting listColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("List Color", "ArrayList color when Theme Sync is off", -40008));
  public final NumberSetting radarRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Radar Range", "Scan radius in blocks", 48.0, 16.0, 128.0, 4.0, "m"));
  public final BooleanSetting radarHeadsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Radar Heads", "Skin faces instead of dots", true));
  public final BooleanSetting notificationsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Notifications",
                  "Themed toasts when modules toggle & the weather changes",
                  true));
  public final NumberSetting notifyDurationSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Notify Duration",
                  "How long a toast lingers before fading out",
                  2.5,
                  1.0,
                  6.0,
                  0.5,
                  "s"));

  public HUDModule() {
    super("HUD", "All HUD elements — move & resize via chat (T)", ModuleCategory.getInstance());
    this.setEnabled(true);
    this.notifyDurationSetting.setVisibilityCondition(this.notificationsSetting::getValue);
  }
}

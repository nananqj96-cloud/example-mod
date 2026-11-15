package bunnythekenzo.client.module;

import bunnythekenzo.client.HoleScanner;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class HoleESPModule extends Module {
  public final ChoiceSetting shapeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Shape",
                  "Which shaft footprints to detect",
                  "Both",
                  new String[] {"Both", "1x1", "1x3"}));
  public final NumberSetting minimumDepthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Minimum Depth",
                  "Minimum number of dug-down levels",
                  3.0,
                  1.0,
                  64.0,
                  1.0,
                  " levels"));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range", "Horizontal distance to scan for dig-downs", 32.0, 8.0, 64.0, 4.0));
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Hollow outline or translucent box",
                  "Full",
                  new String[] {"Outline", "Full"}));
  public final ColorSetting colorSetting;
  public final NumberSetting highlightAlphaSetting;
  public final BooleanSetting animationsSetting;
  public static final long timestamp = -2397388330174392321L;

  public HoleESPModule() {
    super("HoleESP", "Highlights 1x1 and 1x3 dig-down shafts", ModuleCategory.RENDER);
    this.colorSetting =
        (ColorSetting)
            this.registerSetting(new ColorSetting("Color", "Dig-down highlight color", -11937793));
    this.highlightAlphaSetting =
        (NumberSetting)
            this.registerSetting(
                new NumberSetting(
                    "Highlight Alpha", "Maximum opacity at the bottom", 150.0, 0.0, 255.0, 1.0));
    this.animationsSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Animations",
                    "Smooth entrance, breathing glow and descending scan line",
                    true));
  }

  @Override
  public void onEnable() {
    HoleScanner.update();
  }

  @Override
  public void onTick() {
    HoleScanner.apply(this);
  }

  @Override
  public void onDisable() {
    HoleScanner.update();
  }
}

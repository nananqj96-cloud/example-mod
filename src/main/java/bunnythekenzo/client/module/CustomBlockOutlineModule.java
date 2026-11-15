package bunnythekenzo.client.module;

import bunnythekenzo.client.BlockOutlineTransition;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public class CustomBlockOutlineModule extends Module {
  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "Sculpted corners, orbital gyroscope or compact targeting brackets",
                  "Prism",
                  new String[] {"Prism", "Orbit", "Focus"}));
  public final ColorSetting colorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                      "Color", "Primary material color; follows the theme until edited", -49508)
                  .getColorSetting());
  public final ColorSetting accentColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                  "Accent Color",
                  "Second color for crystal tips, rings and light inlays",
                  -9180929));
  public final BooleanSetting rainbowSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Rainbow", "Flow a rainbow through the 3D elements", false));
  public final NumberSetting glowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glow", "Soft light around rings and crystal tips", 55.0, 0.0, 100.0, 5.0, "%"));
  public final NumberSetting thicknessSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Thickness", "Thickness of the sculpted 3D material", 2.5, 1.0, 6.0, 0.5));
  public final NumberSetting detailScaleSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Detail Scale",
                  "Size of the details; anchors stay aligned to the selected block",
                  100.0,
                  60.0,
                  150.0,
                  5.0,
                  "%"));
  public final NumberSetting spacingSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Spacing",
                  "Distance between the block surface and floating details",
                  4.0,
                  1.0,
                  12.0,
                  1.0,
                  " cm"));
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Opacity", "Visibility of the selector", 95.0, 20.0, 100.0, 5.0, "%"));
  public final ChoiceSetting animationSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Animation",
                  "Orbit, breathe, flow between colors, or freeze the sculpture",
                  "Orbit",
                  new String[] {"Orbit", "Pulse", "Gradient Flow", "Static"}));
  public final NumberSetting animationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Animation Speed",
                  "Speed of movement and color flow; zero freezes everything",
                  1.0,
                  0.0,
                  3.0,
                  0.1,
                  "x"));
  public final BooleanSetting transitionsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Transitions",
                  "Corner pieces assemble and dissolve when changing targets",
                  true));
  public final BooleanSetting faceMarkerSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Face Marker",
                  "A layered reticle on the actual block face under your crosshair",
                  true));
  public final BooleanSetting orbitRingsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Orbit Rings", "Segmented 3D bands around the selected shape", true));
  public final BooleanSetting sparklesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Sparkles", "Small faceted satellites travelling along the bands", true));
  public final BlockOutlineTransition blockOutlineTransition = new BlockOutlineTransition();

  public CustomBlockOutlineModule() {
    super(
        "CustomBlockOutline",
        "3D block selector with sculpted corners, orbital rings and animated light",
        ModuleCategory.VISUALS);
    this.colorSetting.setVisibilityCondition(
        () -> {
          return !(Boolean) this.rainbowSetting.getValue();
        });
    this.accentColorSetting.setVisibilityCondition(
        () -> {
          return !(Boolean) this.rainbowSetting.getValue();
        });
    this.animationSpeedSetting.setVisibilityCondition(
        () -> {
          return !this.animationSetting.isSelected("Static");
        });
    this.transitionsSetting.setVisibilityCondition(
        () -> {
          return !this.animationSetting.isSelected("Static")
              && (Double) this.animationSpeedSetting.getValue() > 0.0;
        });
    this.orbitRingsSetting.setVisibilityCondition(
        () -> {
          return !this.styleSetting.isSelected("Focus");
        });
    this.sparklesSetting.setVisibilityCondition(
        () -> {
          return !this.styleSetting.isSelected("Focus")
              && (Boolean) this.orbitRingsSetting.getValue();
        });
    this.setEnabled(true);
  }

  public String getString(Setting setting) {
    if (setting == this.animationSetting
        || setting == this.animationSpeedSetting
        || setting == this.transitionsSetting) {
      return "Motion";
    } else {
      return setting != this.colorSetting
              && setting != this.accentColorSetting
              && setting != this.rainbowSetting
              && setting != this.glowSetting
              && setting != this.opacitySetting
          ? "Sculpture"
          : "Colors";
    }
  }

  @Override
  public void onDisable() {
    this.blockOutlineTransition.update();
  }
}

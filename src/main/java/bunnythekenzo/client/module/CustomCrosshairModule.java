package bunnythekenzo.client.module;

import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class CustomCrosshairModule extends Module {

  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "Crosshair shape",
                  "Cross",
                  new String[] {"Dot", "Cross", "Circle", "T-Shape", "Brackets", "Chevron", "67"}));
  public final NumberSetting sizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Size", "Overall crosshair size", 7.0, 2.0, 24.0, 1.0, "px"));
  public final NumberSetting thicknessSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Thickness", "Line / dot thickness", 2.0, 1.0, 6.0, 0.5, "px"));
  public final NumberSetting gapSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Gap", "Center gap for line styles", 3.0, 0.0, 12.0, 1.0, "px"));
  public final ColorSetting colorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Color", "Crosshair color; follows the theme until edited", -49508)
                  .getColorSetting());
  public final BooleanSetting rainbowSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Rainbow", "Cycle through the rainbow", false));
  public final BooleanSetting centerDotSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Center Dot", "Add a filled dot at the very center", false));
  public final BooleanSetting outlineSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Outline", "Dark border for contrast on any background", true));
  public final BooleanSetting glowSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Glow", "Soft glow behind the crosshair", true));
  public final BooleanSetting hideVanillaSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Hide Vanilla", "Hide Minecraft's default crosshair", true));

  public static final long timestamp = 3000000000L;

  public CustomCrosshairModule() {
    super("CustomCrosshair", "Draws a custom crosshair", ModuleCategory.MISC);
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return this.isEnabled() && (Boolean) this.hideVanillaSetting.getValue();
  }

  public int computeInt() {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.rainbowSetting.getValue()) {
      float floatValue = (float) (System.nanoTime() % timestamp) / 3.0E9F;

      return 0xFF000000 | ArgbColors.fromHsv(floatValue, 0.85F, 1.0F) & 16777215;
    } else {
      return this.colorSetting.getInteger();
    }
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2) {
    FakeLootDropModule.ensureInitialized();

    int value2 = this.computeInt();
    int intValue = (java.lang.Boolean) (this.outlineSetting.getValue()) ? -1342177280 : 0;
    float value3 = this.thicknessSetting.floatValue();
    float value4 = this.sizeSetting.floatValue();
    float value5 = this.gapSetting.floatValue();
    if ((Boolean) this.glowSetting.getValue()) {
      fontManager.render3(
          floatValue, floatValue2, value4 + 2.0F, 6.0F, ArgbColors.withOpacity(value2, 0.45F));
    }

    switch ((String) this.styleSetting.getValue()) {
      case "Dot":
        this.render(
            fontManager, floatValue, floatValue2, Math.max(1.5F, value4 * 0.35F), value2, intValue);
        break;
      case "Cross":
        this.render(
            fontManager,
            floatValue,
            floatValue2,
            value4,
            value3,
            value5,
            value2,
            intValue,
            true,
            true);
        break;
      case "T-Shape":
        this.render(
            fontManager,
            floatValue,
            floatValue2,
            value4,
            value3,
            value5,
            value2,
            intValue,
            false,
            true);
        break;
      case "Circle":
        this.render(fontManager, floatValue, floatValue2, value4, value3, value2, intValue);
        break;
      case "Brackets":
        this.render(fontManager, floatValue, floatValue2, value4, value3, value5, value2, intValue);
        break;
      case "Chevron":
        if (intValue != 0) {
          fontManager.render(
              floatValue,
              floatValue2 + value4 * 0.15F,
              value4 + 2.0F,
              value3 + 2.0F,
              intValue,
              true);
        }

        fontManager.render(floatValue, floatValue2 + value4 * 0.15F, value4, value3, value2, true);
        break;
      case "67":
        this.render(fontManager, floatValue, floatValue2, value4, value2);
        break;
      default:
        this.render(
            fontManager,
            floatValue,
            floatValue2,
            value4,
            value3,
            value5,
            value2,
            intValue,
            true,
            true);
    }

    if ((Boolean) this.centerDotSetting.getValue()
        && !this.styleSetting.isSelected("Dot")
        && !this.styleSetting.isSelected("67")) {
      this.render(
          fontManager, floatValue, floatValue2, Math.max(1.2F, value3 * 0.8F), value2, intValue);
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      int intValue2) {
    FakeLootDropModule.ensureInitialized();
    if (intValue2 != 0) {
      fontManager.render2(floatValue, floatValue2, floatValue3 + 1.0F, intValue2);
    }

    fontManager.render2(floatValue, floatValue2, floatValue3, intValue);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue,
      int intValue2) {
    FakeLootDropModule.ensureInitialized();
    if (intValue2 != 0) {
      fontManager.render2(floatValue, floatValue2, floatValue3, floatValue4 + 2.0F, intValue2);
    }

    fontManager.render2(floatValue, floatValue2, floatValue3, floatValue4, intValue);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      int intValue2,
      boolean flag,
      boolean flag2) {
    FakeLootDropModule.ensureInitialized();
    if (intValue2 != 0) {
      this.render(
          fontManager,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4 + 2.0F,
          floatValue5,
          intValue2,
          flag,
          flag2);
    }

    this.render(
        fontManager,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        intValue,
        flag,
        flag2);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      boolean flag,
      boolean flag2) {
    FakeLootDropModule.ensureInitialized();

    fontManager.render2(
        floatValue + floatValue5,
        floatValue2,
        floatValue + floatValue5 + floatValue3,
        floatValue2,
        floatValue4,
        intValue);
    fontManager.render2(
        floatValue - floatValue5,
        floatValue2,
        floatValue - floatValue5 - floatValue3,
        floatValue2,
        floatValue4,
        intValue);
    if (flag) {
      fontManager.render2(
          floatValue,
          floatValue2 - floatValue5,
          floatValue,
          floatValue2 - floatValue5 - floatValue3,
          floatValue4,
          intValue);
    }

    if (flag2) {
      fontManager.render2(
          floatValue,
          floatValue2 + floatValue5,
          floatValue,
          floatValue2 + floatValue5 + floatValue3,
          floatValue4,
          intValue);
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      int intValue2) {
    FakeLootDropModule.ensureInitialized();

    float floatValue6 = Math.max(2.0F, floatValue3 * 0.5F);
    float floatValue7 = floatValue3 + floatValue5 * 0.4F;
    if (intValue2 != 0) {
      this.render(
          fontManager,
          floatValue,
          floatValue2,
          floatValue7,
          floatValue6,
          floatValue4 + 2.0F,
          intValue2);
    }

    this.render(
        fontManager, floatValue, floatValue2, floatValue7, floatValue6, floatValue4, intValue);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue) {
    fontManager.render2(
        floatValue - floatValue3,
        floatValue2 - floatValue3,
        floatValue - floatValue3 + floatValue4,
        floatValue2 - floatValue3,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue - floatValue3,
        floatValue2 - floatValue3,
        floatValue - floatValue3,
        floatValue2 - floatValue3 + floatValue4,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue + floatValue3,
        floatValue2 - floatValue3,
        floatValue + floatValue3 - floatValue4,
        floatValue2 - floatValue3,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue + floatValue3,
        floatValue2 - floatValue3,
        floatValue + floatValue3,
        floatValue2 - floatValue3 + floatValue4,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue - floatValue3,
        floatValue2 + floatValue3,
        floatValue - floatValue3 + floatValue4,
        floatValue2 + floatValue3,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue - floatValue3,
        floatValue2 + floatValue3,
        floatValue - floatValue3,
        floatValue2 + floatValue3 - floatValue4,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue + floatValue3,
        floatValue2 + floatValue3,
        floatValue + floatValue3 - floatValue4,
        floatValue2 + floatValue3,
        floatValue5,
        intValue);
    fontManager.render2(
        floatValue + floatValue3,
        floatValue2 + floatValue3,
        floatValue + floatValue3,
        floatValue2 + floatValue3 - floatValue4,
        floatValue5,
        intValue);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    float floatValue4 = floatValue3 * 2.4F;
    float value = fontManager.computeFloat("67", floatValue4);
    float floatValue5 = floatValue - value / 2.0F;
    fontManager.render(
        "67", floatValue5, floatValue2, floatValue4, ArgbColors.withOpacity(-49508, 0.6F));
    fontManager.computeFloat("67", floatValue5, floatValue2, floatValue4, intValue);
  }
}

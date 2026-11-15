package bunnythekenzo.client;

import java.util.function.Supplier;
import bunnythekenzo.client.hud.HudElement;
import bunnythekenzo.client.render.FontManager;

public class TextValueHudElement extends HudElement {
  public static final float amount = 22.0F;
  public static final float amount2 = 13.0F;
  public static final float amount3 = 9.0F;
  public final ThemeManager themeManager;
  public final String string;
  public final Supplier supplier;

  public TextValueHudElement(
      String text,
      String text2,
      String text3,
      ThemeManager themeManager2,
      String string2,
      Supplier supplier2,
      float floatValue,
      float floatValue2,
      HudElementContract hudElementContract) {
    super(text, text2, text3, floatValue, floatValue2, hudElementContract);
    this.themeManager = themeManager2;
    this.string = string2;
    this.supplier = supplier2;
  }

  public String getString() {
    try {
      return (String) this.supplier.get();
    } catch (Exception failure) {
      return "?";
    }
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 9.0F
        + fontManager.computeFloat(this.string, 13.0F)
        + 5.0F
        + fontManager.computeFloat(this.getString(), 13.0F)
        + 9.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return 22.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    float floatValue5 = floatValue2 + floatValue4 / 2.0F;
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        colorPalette.computeInt7(),
        colorPalette.computeInt8(),
        true);
    float floatValue6 = floatValue + 9.0F;
    floatValue6 +=
        fontManager.computeFloat2(
            this.string,
            floatValue6,
            floatValue5,
            13.0F,
            colorPalette.computeInt(),
            colorPalette.getCount());
    fontManager.computeFloat(
        this.getString(), floatValue6 + 5.0F, floatValue5, 13.0F, colorPalette.computeInt16());
  }
}

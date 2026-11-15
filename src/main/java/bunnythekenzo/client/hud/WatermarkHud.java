package bunnythekenzo.client.hud;

import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.render.FontManager;

public class WatermarkHud extends HudElement {
  public static final float amount = 30.0F;
  public static final float amount2 = 13.0F;
  public static final float amount3 = 18.0F;
  public static final float amount4 = 14.0F;
  public static final String brand = "bunnythekenzo";
  public final ThemeManager themeManager;

  public WatermarkHud(ThemeManager themeManager2, HudElementContract hudElementContract) {
    super("watermark", "Watermark", "The bunnythekenzo wordmark", 0.006F, 0.01F, hudElementContract);
    this.themeManager = themeManager2;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 26.0F + fontManager.computeFloat(brand, 13.0F);
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return 30.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    float centerY = floatValue2 + floatValue4 / 2.0F;
    float pulse = (float) (0.5 + 0.5 * Math.sin(System.nanoTime() / 8.0E8));
    float colorPulse = (float) (0.5 + 0.5 * Math.sin(System.nanoTime() / 4.8E8));
    int startColor = ArgbColors.lerp(colorPalette.computeInt(), colorPalette.getCount(), colorPulse);
    int endColor = ArgbColors.lerp(colorPalette.getCount(), colorPalette.computeInt(), colorPulse);
    fontManager.render2(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        8.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), 0.1F + 0.1F * pulse));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        ArgbColors.withOpacity(-15264995, 0.88F),
        ArgbColors.withOpacity(-15856878, 0.88F),
        true);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        1.0F,
        ArgbColors.withOpacity(
            ArgbColors.lerp(colorPalette.getCount(), colorPalette.computeInt(), pulse),
            0.55F));
    fontManager.computeFloat(
        brand,
        floatValue + 13.0F,
        centerY,
        13.0F,
        ArgbColors.withOpacity(ArgbColors.lerp(startColor, endColor, pulse), 0.94F));
  }
}

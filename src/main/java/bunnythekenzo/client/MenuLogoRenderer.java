package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.IconCache;

public final class MenuLogoRenderer {
  public static final float amount = 1.0F;
  public static final float amount2 = 0.33F;
  public static final float amount3 = 0.7F;
  public static final float amount4 = 2.2F;
  public static final float amount5 = 0.3F;
  public static final float amount6 = 0.055F;
  public static final float amount7 = 0.011F;

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    int png = IconCache.getPng("bunnythekenzo");
    if (png > 0) {
      float floatValue8 = floatValue4 * 0.33F;
      float floatValue9 = floatValue8 * amount;
      float floatValue10 = floatValue + floatValue3 / 2.0F;
      float floatValue11 = floatValue2 + floatValue4 * 0.7F;
      float floatValue12 = floatValue10 - floatValue9 / 2.0F;
      float floatValue13 = floatValue11 - floatValue8 / 2.0F;
      float floatValue14 = floatValue10 - floatValue5;
      float floatValue15 = floatValue11 - floatValue6;
      float floatValue16 = (float) Math.hypot(floatValue14, floatValue15);
      float floatValue17 = floatValue16 < 0.001F ? 0.0F : floatValue14 / floatValue16;
      float floatValue18 = floatValue16 < 0.001F ? 0.0F : floatValue15 / floatValue16;
      float floatValue19 = 0.55F + 0.45F * floatValue7;
      int int2 = ArgbColors.lighten(ColorPalette.getInstance().computeInt(), 0.6F);
      fontManager.render(
          png,
          floatValue12,
          floatValue13,
          floatValue9,
          floatValue8,
          ArgbColors.withOpacity(-1, 0.011F));
      fontManager.render(
          png,
          floatValue12 - floatValue17 * 2.2F,
          floatValue13 - floatValue18 * 2.2F,
          floatValue9,
          floatValue8,
          ArgbColors.withOpacity(-16777216, 0.3F * floatValue19));
      fontManager.render(
          png,
          floatValue12 + floatValue17 * 2.2F,
          floatValue13 + floatValue18 * 2.2F,
          floatValue9,
          floatValue8,
          ArgbColors.withOpacity(int2, 0.055F * floatValue19));
    }
  }
}

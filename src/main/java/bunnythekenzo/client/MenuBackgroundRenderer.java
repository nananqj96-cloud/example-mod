package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class MenuBackgroundRenderer {
  public static final float amount = 44.0F;
  public static final float amount2 = 200.0F;
  public static final float amount3 = 150.0F;
  public static final float amount4 = 0.014F;
  public static final float amount5 = 0.115F;
  public static final float amount6 = 0.3F;
  public static final float amount7 = 0.012F;
  public static final float amount8 = 0.014F;

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue8 = (floatValue5 - (floatValue + floatValue3 / 2.0F)) * -0.014F;
    float floatValue9 = (floatValue6 - (floatValue2 + floatValue4 / 2.0F)) * -0.014F;
    fontManager.render2(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        0.0F,
        280.0F,
        instance.computeInt(0.042F * floatValue7),
        instance.computeInt(0.0F));
    int value = ArgbColors.withOpacity(-1, 0.014F);
    int int2 = ArgbColors.lighten(instance.computeInt(), 0.55F);
    int value2 = ArgbColors.withOpacity(int2, 0.0F);
    float floatValue10 = floatValue8 - 44.0F * (float) Math.floor(floatValue8 / 44.0F);
    float floatValue11 = floatValue9 - 44.0F * (float) Math.floor(floatValue9 / 44.0F);

    for (float floatValue12 = floatValue + floatValue10;
        floatValue12 <= floatValue + floatValue3;
        floatValue12 += 44.0F) {
      fontManager.render(floatValue12, floatValue2, 1.0F, floatValue4, 0.0F, value);
      float floatValue13 = Math.abs(floatValue5 - floatValue12) / 200.0F;
      if (!(floatValue13 >= 1.0F)) {
        int value3 =
            ArgbColors.withOpacity(
                int2, 0.115F * (1.0F - floatValue13) * (1.0F - floatValue13) * floatValue7);
        fontManager.render(
            floatValue12, floatValue6 - 200.0F, 1.0F, 200.0F, 0.0F, value2, value3, true);
        fontManager.render(floatValue12, floatValue6, 1.0F, 200.0F, 0.0F, value3, value2, true);
      }
    }

    for (float floatValue14 = floatValue2 + floatValue11;
        floatValue14 <= floatValue2 + floatValue4;
        floatValue14 += 44.0F) {
      fontManager.render(floatValue, floatValue14, floatValue3, 1.0F, 0.0F, value);
      float floatValue15 = Math.abs(floatValue6 - floatValue14) / 200.0F;
      if (!(floatValue15 >= 1.0F)) {
        int value4 =
            ArgbColors.withOpacity(
                int2, 0.115F * (1.0F - floatValue15) * (1.0F - floatValue15) * floatValue7);
        fontManager.render(
            floatValue5 - 200.0F, floatValue14, 200.0F, 1.0F, 0.0F, value2, value4, false);
        fontManager.render(floatValue5, floatValue14, 200.0F, 1.0F, 0.0F, value4, value2, false);
      }
    }

    this.render(
        fontManager,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue10,
        floatValue11,
        floatValue5,
        floatValue6,
        floatValue7,
        int2);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      int intValue) {
    for (float floatValue10 = floatValue + floatValue5;
        floatValue10 <= floatValue + floatValue3;
        floatValue10 += 44.0F) {
      if (!(Math.abs(floatValue7 - floatValue10) > 150.0F)) {
        for (float floatValue11 = floatValue2 + floatValue6;
            floatValue11 <= floatValue2 + floatValue4;
            floatValue11 += 44.0F) {
          if (!(Math.abs(floatValue8 - floatValue11) > 150.0F)) {
            float floatValue12 =
                (float) Math.hypot(floatValue7 - floatValue10, floatValue8 - floatValue11) / 150.0F;
            if (!(floatValue12 >= 1.0F)) {
              float floatValue13 =
                  0.3F * (1.0F - floatValue12) * (1.0F - floatValue12) * floatValue9;
              if (!(floatValue13 < 0.012F)) {
                fontManager.render(
                    floatValue10 - 0.8F,
                    floatValue11 - 0.8F,
                    1.6F,
                    1.6F,
                    0.8F,
                    ArgbColors.withOpacity(intValue, floatValue13));
              }
            }
          }
        }
      }
    }
  }
}

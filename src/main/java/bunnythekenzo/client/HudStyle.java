package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class HudStyle {
  public static final float amount = 8.0F;
  public static final float amount2 = 5.0F;
  public static final float amount3 = 10.0F;
  public static final float amount4 = 8.0F;
  public static final float amount5 = 10.0F;
  public static final float amount6 = 7.0F;
  public static final float amount7 = 7.5F;
  public static final float amount8 = 8.0F;
  public static final float amount9 = 9.5F;
  public static final float amount10 = 11.5F;
  public static final float amount11 = 12.0F;
  public static final float amount12 = 17.0F;
  public static final float amount13 = 1.5F;
  public static final int count = -11870592;
  public static final int count2 = -278748;
  public static final int count3 = -495247;

  public static int computeInt() {
    return GuiPreferences.getGuiPreferences().hudFollowsThemeControl.getValue()
        ? ColorPalette.getInstance().computeInt()
        : GuiPreferences.getGuiPreferences().hudColourControl.computeInt();
  }

  public static int computeInt(float floatValue) {
    return ArgbColors.withOpacity(computeInt(), floatValue);
  }

  public static int computeInt2() {
    return ColorPalette.getInstance().computeInt();
  }

  public static int computeInt2(float floatValue) {
    return ArgbColors.withOpacity(ColorPalette.getInstance().getCount(), floatValue);
  }

  public static boolean test() {
    return GuiPreferences.getGuiPreferences().hudShadowControl.getValue();
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    if (test()) {
      render(fontManager, floatValue, floatValue2, floatValue3, floatValue4, 8.0F, 1.0F);
    }
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    if (!(floatValue6 <= 0.01F)) {
      int count4 = ColorPalette.getInstance().getCount();
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          9.0F,
          3.0F,
          ArgbColors.withOpacity(-16777216, 0.5F * floatValue6));
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          ArgbColors.withOpacity(ArgbColors.lerp(-15002074, count4, 0.05F), 0.9F * floatValue6),
          ArgbColors.withOpacity(-15791082, 0.93F * floatValue6),
          true);
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          1.0F,
          ArgbColors.withOpacity(-1, 0.09F * floatValue6));
      fontManager.render(
          floatValue + floatValue5,
          floatValue2 + 0.5F,
          floatValue3 - floatValue5 * 2.0F,
          1.0F,
          0.0F,
          ArgbColors.withOpacity(-1, 0.13F * floatValue6));
    }
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        ArgbColors.withOpacity(-16316916, 0.82F));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        1.0F,
        ArgbColors.withOpacity(-16777216, 0.45F));
  }

  public static float computeFloat(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      String text,
      String text2) {
    float floatValue4 = floatValue2 + 5.0F;
    computeFloat(fontManager, text, floatValue, floatValue4, 8.0F, computeInt(0.42F));
    if (text2 != null && !text2.isEmpty()) {
      fontManager.computeFloat3(text2, floatValue + floatValue3, floatValue4, 9.5F, computeInt2());
    }

    return floatValue4;
  }

  public static float computeFloat(FontManager fontManager, String text, String text2) {
    float floatValue = computeFloat(fontManager, text, 8.0F);
    if (text2 != null && !text2.isEmpty()) {
      floatValue += 16.0F + fontManager.computeFloat(text2, 9.5F);
    }

    return floatValue;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue) {
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        ArgbColors.withOpacity(-16777216, 0.45F));
    float floatValue6 = floatValue3 * Math.clamp(floatValue5, 0.0F, 1.0F);
    if (floatValue6 > 0.4F) {
      fontManager.render(
          floatValue,
          floatValue2,
          Math.max(floatValue4, floatValue6),
          floatValue4,
          floatValue4 / 2.0F,
          intValue);
    }
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float[] floatValues,
      int intValue,
      float floatValue5,
      int intValue2) {
    if (intValue > 0 && !(floatValue5 <= 1.0E-4F)) {
      float floatValue6 = floatValue3 / intValue;
      float floatValue7 = Math.max(1.0F, floatValue6 - 0.8F);

      for (int index = 0; index < intValue; index++) {
        float floatValue8 = Math.clamp(floatValues[index] / floatValue5, 0.0F, 1.0F);
        float floatValue9 = Math.max(1.0F, floatValue8 * floatValue4);
        float floatValue10 = (float) index / Math.max(1, intValue - 1);
        int value = ArgbColors.withOpacity(intValue2, 0.22F + 0.78F * floatValue10 * floatValue10);
        fontManager.render(
            floatValue + index * floatValue6,
            floatValue2 + floatValue4 - floatValue9,
            floatValue7,
            floatValue9,
            Math.min(1.0F, floatValue7 / 2.0F),
            value);
      }
    }
  }

  public static float computeFloat(
      FontManager fontManager, float floatValue, float floatValue2, String text, int intValue) {
    float floatValue3 = computeFloat(fontManager, text);
    fontManager.render(
        floatValue,
        floatValue2 - 6.0F,
        floatValue3,
        12.0F,
        6.0F,
        ArgbColors.withOpacity(intValue, 0.16F));
    fontManager.render(
        floatValue,
        floatValue2 - 6.0F,
        floatValue3,
        12.0F,
        6.0F,
        1.0F,
        ArgbColors.withOpacity(intValue, 0.34F));
    fontManager.computeFloat(
        text, floatValue + 5.5F, floatValue2 + 0.5F, 7.5F, ArgbColors.lighten(intValue, 0.25F));
    return floatValue3;
  }

  public static float computeFloat(FontManager fontManager, String text) {
    return fontManager.computeFloat(text, 7.5F) + 11.0F;
  }

  public static float computeFloat(
      FontManager fontManager,
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    fontManager.setAmount(1.5F);
    float value = fontManager.computeFloat(text, floatValue, floatValue2, floatValue3, intValue);
    fontManager.setAmount(0.0F);
    return value;
  }

  public static float computeFloat(FontManager fontManager, String text, float floatValue) {
    fontManager.setAmount(1.5F);
    float value = fontManager.computeFloat(text, floatValue);
    fontManager.setAmount(0.0F);
    return value;
  }

  public static int computeInt3(float floatValue) {
    float floatValue2 = Math.clamp(floatValue, 0.0F, 1.0F);
    return floatValue2 > 0.5F
        ? ArgbColors.lerp(-278748, -11870592, (floatValue2 - 0.5F) * 2.0F)
        : ArgbColors.lerp(-495247, -278748, floatValue2 * 2.0F);
  }
}

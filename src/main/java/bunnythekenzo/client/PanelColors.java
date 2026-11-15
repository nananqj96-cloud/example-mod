package bunnythekenzo.client;

public final class PanelColors {
  public static final int count = -12569527;
  public static final int count2 = -14673881;
  public static final int count3 = -15002333;

  public static int computeInt(float floatValue) {
    return ArgbColors.lerp(
        ArgbColors.withOpacity(ColorPalette.getInstance().computeInt5(), 1.0F),
        -8291703,
        floatValue * 0.52F);
  }

  public static int computeInt2(float floatValue) {
    return ArgbColors.lerp(
        1090519039, ColorPalette.getInstance().computeInt(0.6F), floatValue * 0.65F);
  }

  public static int computeInt3(float floatValue) {
    return ArgbColors.lerp(-12569527, -14673881, floatValue);
  }

  public static int computeInt(int intValue, float floatValue, float floatValue2) {
    return ArgbColors.withOpacity(
        ArgbColors.lerp(
            ColorPalette.getInstance().computeInt5(),
            ArgbColors.withOpacity(intValue, 1.0F),
            floatValue * 0.105F + floatValue2 * 0.045F),
        1.0F);
  }
}

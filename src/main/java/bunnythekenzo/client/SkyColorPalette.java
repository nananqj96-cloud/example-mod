package bunnythekenzo.client;

import bunnythekenzo.client.module.SkyModule;

public final class SkyColorPalette {
  public static final int count = -16382966;
  public static final int count2 = -15265750;

  public static int computeInt() {
    return SkyModule.test19() ? ColorPalette.getInstance().getCount() : SkyModule.computeInt();
  }

  public static int computeInt2() {
    return ArgbColors.lerp(-16382966, computeInt(), 0.06F);
  }

  public static int computeInt3() {
    return ArgbColors.lerp(-15265750, computeInt(), 0.28F);
  }

  public static int computeInt4() {
    return ArgbColors.lerp(computeInt(), -1, 0.1F + 0.16F * SkyModule.computeFloat2());
  }

  public static int computeInt5() {
    return ArgbColors.lerp(-16382966, computeInt(), 0.08F);
  }

  public static int computeInt6() {
    return ArgbColors.lerp(ColorPalette.getInstance().computeInt(), -1, 0.45F);
  }

  public static int computeInt(float floatValue) {
    return computeInt(
        floatValue, computeInt(), SkyModule.computeFloat3(), SkyModule.computeFloat2());
  }

  public static int computeInt(
      float floatValue, int intValue, float floatValue2, float floatValue3) {
    int value = ArgbColors.lerp(intValue, -1, 0.1F + 0.16F * floatValue3);
    if (floatValue < 0.0F) {
      float floatValue4 = computeFloat(0.0F, 0.35F, -floatValue);
      return ArgbColors.lerp(value, ArgbColors.lerp(-16382966, intValue, 0.08F), floatValue4);
    } else {
      float floatValue5 = 1.0F - Math.min(floatValue, 1.0F);
      float floatValue6 = (float) Math.pow(floatValue5, 0.9 + 1.8 * floatValue2);
      float floatValue7 = (float) Math.pow(floatValue5, 3.0 + 6.0 * floatValue2);
      int value2 =
          ArgbColors.lerp(
              ArgbColors.lerp(-16382966, intValue, 0.06F),
              ArgbColors.lerp(-15265750, intValue, 0.28F),
              floatValue6);
      return ArgbColors.lerp(value2, value, floatValue7 * (0.45F + 0.55F * floatValue3));
    }
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 =
        Math.clamp((floatValue3 - floatValue) / (floatValue2 - floatValue), 0.0F, 1.0F);
    return floatValue4 * floatValue4 * (3.0F - 2.0F * floatValue4);
  }
}

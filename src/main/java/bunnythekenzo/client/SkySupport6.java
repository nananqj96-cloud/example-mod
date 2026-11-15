package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;

public final class SkySupport6 {
  public static final float amount = 0.351F;
  public static final float amount2 = 0.822F;
  public static final float amount3 = 0.448F;

  public static void render(
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue,
      float floatValue5) {
    render(
        vertexConsumer,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4,
        0.0F,
        intValue,
        floatValue5);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      float floatValue7) {
    if (SkySupport7.test(
        floatValue,
        floatValue2,
        floatValue3,
        (Math.abs(floatValue4) + Math.abs(floatValue5)) / 100.0F)) {
      float floatValue8 = floatValue * 100.0F;
      float floatValue9 = floatValue2 * 100.0F;
      float floatValue10 = floatValue3 * 100.0F;
      float floatValue11 = 0.0F;
      float floatValue12 = 1.0F;
      if (floatValue2 > 0.99F || floatValue2 < -0.99F) {
        floatValue11 = 1.0F;
        floatValue12 = 0.0F;
      }

      float floatValue13 = floatValue12 * floatValue3 - 0.0F * floatValue2;
      float floatValue14 = 0.0F * floatValue - floatValue11 * floatValue3;
      float floatValue15 = floatValue11 * floatValue2 - floatValue12 * floatValue;
      float floatValue16 =
          (float)
              Math.sqrt(
                  floatValue13 * floatValue13
                      + floatValue14 * floatValue14
                      + floatValue15 * floatValue15);
      if (!(floatValue16 < 1.0E-5F)) {
        floatValue13 /= floatValue16;
        floatValue14 /= floatValue16;
        floatValue15 /= floatValue16;
        float floatValue17 = floatValue2 * floatValue15 - floatValue3 * floatValue14;
        float floatValue18 = floatValue3 * floatValue13 - floatValue * floatValue15;
        float floatValue19 = floatValue * floatValue14 - floatValue2 * floatValue13;
        if (floatValue6 != 0.0F) {
          float floatValue20 = (float) Math.cos(floatValue6);
          float floatValue21 = (float) Math.sin(floatValue6);
          float floatValue22 = floatValue13 * floatValue20 + floatValue17 * floatValue21;
          float floatValue23 = floatValue14 * floatValue20 + floatValue18 * floatValue21;
          float floatValue24 = floatValue15 * floatValue20 + floatValue19 * floatValue21;
          floatValue17 = floatValue17 * floatValue20 - floatValue13 * floatValue21;
          floatValue18 = floatValue18 * floatValue20 - floatValue14 * floatValue21;
          floatValue19 = floatValue19 * floatValue20 - floatValue15 * floatValue21;
          floatValue13 = floatValue22;
          floatValue14 = floatValue23;
          floatValue15 = floatValue24;
        }

        floatValue13 *= floatValue4;
        floatValue14 *= floatValue4;
        floatValue15 *= floatValue4;
        floatValue17 *= floatValue5;
        floatValue18 *= floatValue5;
        floatValue19 *= floatValue5;
        float floatValue25 = Math.clamp(floatValue7, 0.0F, 1.0F);
        int intValue2 = (int) (ArgbColors.red(intValue) * floatValue25 + 0.5F);
        int intValue3 = (int) (ArgbColors.green(intValue) * floatValue25 + 0.5F);
        int intValue4 = (int) (ArgbColors.blue(intValue) * floatValue25 + 0.5F);
        int intValue5 = (int) (floatValue25 * 255.0F + 0.5F);
        vertexConsumer
            .vertex(
                floatValue8 - floatValue13 + floatValue17,
                floatValue9 - floatValue14 + floatValue18,
                floatValue10 - floatValue15 + floatValue19)
            .texture(0.0F, 0.0F)
            .color(intValue2, intValue3, intValue4, intValue5);
        vertexConsumer
            .vertex(
                floatValue8 - floatValue13 - floatValue17,
                floatValue9 - floatValue14 - floatValue18,
                floatValue10 - floatValue15 - floatValue19)
            .texture(0.0F, 1.0F)
            .color(intValue2, intValue3, intValue4, intValue5);
        vertexConsumer
            .vertex(
                floatValue8 + floatValue13 - floatValue17,
                floatValue9 + floatValue14 - floatValue18,
                floatValue10 + floatValue15 - floatValue19)
            .texture(1.0F, 1.0F)
            .color(intValue2, intValue3, intValue4, intValue5);
        vertexConsumer
            .vertex(
                floatValue8 + floatValue13 + floatValue17,
                floatValue9 + floatValue14 + floatValue18,
                floatValue10 + floatValue15 + floatValue19)
            .texture(1.0F, 0.0F)
            .color(intValue2, intValue3, intValue4, intValue5);
      }
    }
  }

  public static float computeFloat(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 = 0.0F;
    float floatValue8 = 1.0F;
    if (floatValue2 > 0.99F || floatValue2 < -0.99F) {
      floatValue7 = 1.0F;
      floatValue8 = 0.0F;
    }

    float floatValue9 = floatValue8 * floatValue3 - 0.0F * floatValue2;
    float floatValue10 = 0.0F * floatValue - floatValue7 * floatValue3;
    float floatValue11 = floatValue7 * floatValue2 - floatValue8 * floatValue;
    float floatValue12 =
        (float)
            Math.sqrt(
                floatValue9 * floatValue9
                    + floatValue10 * floatValue10
                    + floatValue11 * floatValue11);
    if (floatValue12 < 1.0E-5F) {
      return 0.0F;
    }

    floatValue9 /= floatValue12;
    floatValue10 /= floatValue12;
    floatValue11 /= floatValue12;
    float floatValue13 = floatValue2 * floatValue11 - floatValue3 * floatValue10;
    float floatValue14 = floatValue3 * floatValue9 - floatValue * floatValue11;
    float floatValue15 = floatValue * floatValue10 - floatValue2 * floatValue9;
    return (float)
        Math.atan2(
            -(floatValue4 * floatValue9 + floatValue5 * floatValue10 + floatValue6 * floatValue11),
            floatValue4 * floatValue13 + floatValue5 * floatValue14 + floatValue6 * floatValue15);
  }

  public static void render(float[] floatValues, float floatValue, float floatValue2) {
    float floatValue3 = floatValues[1] * floatValue - floatValues[2] * floatValue2;
    float floatValue4 = floatValues[1] * floatValue2 + floatValues[2] * floatValue;
    floatValues[1] = floatValue3;
    floatValues[2] = floatValue4;
  }

  public static boolean test(float[] floatValues) {
    float floatValue =
        (float)
            Math.sqrt(
                floatValues[0] * floatValues[0]
                    + floatValues[1] * floatValues[1]
                    + floatValues[2] * floatValues[2]);
    if (floatValue < 1.0E-6F) {
      return false;
    }

    floatValues[0] /= floatValue;
    floatValues[1] /= floatValue;
    floatValues[2] /= floatValue;
    return true;
  }
}

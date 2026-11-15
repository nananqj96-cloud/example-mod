package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport {
  public static final float amount = 100.0F;
  public static final int count = 48;
  public static final int count2 = 26;
  public static final double value = 1.9;
  public static final float[] amountArray = new float[49];
  public static final float[] amountArray2 = new float[49];
  public static final float[] amountArray3 = new float[27];
  public static final float[] amountArray4 = new float[27];
  public static final float[] amountArray5 = new float[27];
  public static final int[] countArray = new int[27];
  public static int count3;
  public static float amount2 = Float.NaN;
  public static float amount3 = Float.NaN;

  public static void apply(Immediate immediate) {
    VertexConsumer buffer = immediate.getBuffer(SkyPipelines.getRenderLayer());
    int value2 = SkyColorPalette.computeInt();
    float float3 = SkyModule.computeFloat3();
    float float2 = SkyModule.computeFloat2();
    if (value2 != count3 || float3 != amount2 || float2 != amount3) {
      for (int index = 0; index <= 26; index++) {
        countArray[index] = SkyColorPalette.computeInt(amountArray5[index], value2, float3, float2);
      }

      count3 = value2;
      amount2 = float3;
      amount3 = float2;
    }

    for (int index2 = 0; index2 < 26; index2++) {
      int intValue = countArray[index2];
      int intValue2 = countArray[index2 + 1];
      float floatValue = amountArray3[index2];
      float floatValue2 = amountArray3[index2 + 1];
      float floatValue3 = amountArray4[index2];
      float floatValue4 = amountArray4[index2 + 1];

      for (int index3 = 0; index3 < 48; index3++) {
        float floatValue5 = amountArray2[index3];
        float floatValue6 = amountArray[index3];
        float floatValue7 = amountArray2[index3 + 1];
        float floatValue8 = amountArray[index3 + 1];
        render(buffer, floatValue3 * floatValue5, floatValue, floatValue3 * floatValue6, intValue);
        render(buffer, floatValue3 * floatValue7, floatValue, floatValue3 * floatValue8, intValue);
        render(
            buffer, floatValue4 * floatValue7, floatValue2, floatValue4 * floatValue8, intValue2);
        render(
            buffer, floatValue4 * floatValue5, floatValue2, floatValue4 * floatValue6, intValue2);
      }
    }

    immediate.draw();
  }

  public static void render(
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    vertexConsumer
        .vertex(floatValue, floatValue2, floatValue3)
        .color(
            ArgbColors.red(intValue), ArgbColors.green(intValue), ArgbColors.blue(intValue), 255);
  }

  static {
    for (int index = 0; index <= 48; index++) {
      double doubleValue = index * 2 * Math.PI / 48.0;
      amountArray[index] = (float) Math.sin(doubleValue);
      amountArray2[index] = (float) Math.cos(doubleValue);
    }

    for (int index2 = 0; index2 <= 26; index2++) {
      double doubleValue2 = index2 * 2.0 / 26.0 - 1.0;
      double doubleValue3 =
          Math.signum(doubleValue2) * Math.pow(Math.abs(doubleValue2), 1.9) * (Math.PI / 2);
      amountArray5[index2] = (float) Math.sin(doubleValue3);
      amountArray3[index2] = 100.0F * amountArray5[index2];
      amountArray4[index2] = 100.0F * (float) Math.cos(doubleValue3);
    }
  }
}

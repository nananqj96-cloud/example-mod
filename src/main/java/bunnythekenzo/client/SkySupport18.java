package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport18 {
  public static final int count = 96;
  public static final int count2 = 3;
  public static final float amount = 0.28F;
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[1164];

  public static void render(float floatValue, float floatValue2, float[] floatValues) {
    float floatValue3 = (float) Math.cos(floatValue);
    float floatValue4 = (float) Math.sin(floatValue);
    floatValues[0] =
        amountArray[0] * floatValue3 + amountArray2[0] * floatValue4 + 0.351F * floatValue2;
    floatValues[1] =
        amountArray[1] * floatValue3 + amountArray2[1] * floatValue4 + 0.822F * floatValue2;
    floatValues[2] =
        amountArray[2] * floatValue3 + amountArray2[2] * floatValue4 + 0.448F * floatValue2;
    SkySupport6.test(floatValues);
  }

  public static void render(Immediate immediate, float floatValue, float floatValue2) {
    float floatValue3 = 1.0F - floatValue2;
    if (!(floatValue3 <= 0.01F)) {
      float floatValue4 = (float) Math.cos(floatValue);
      float floatValue5 = (float) Math.sin(floatValue);
      apply(floatValue4, floatValue5);
      int value =
          ArgbColors.lerp(SkyColorPalette.computeInt(), SkyColorPalette.computeInt6(), 0.42F);
      float floatValue6 = 0.46F * floatValue3;
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.sky("milkyway", true));

      for (int index = 0; index < 96; index++) {
        float floatValue7 = index / 96.0F;
        float floatValue8 = (index + 1) / 96.0F;

        for (int index2 = 0; index2 < 3; index2++) {
          float floatValue9 = index2 / 3.0F;
          float floatValue10 = (index2 + 1) / 3.0F;
          render(buffer, index, index2, floatValue7, floatValue9, value, floatValue6);
          render(buffer, index, index2 + 1, floatValue7, floatValue10, value, floatValue6);
          render(buffer, index + 1, index2 + 1, floatValue8, floatValue10, value, floatValue6);
          render(buffer, index + 1, index2, floatValue8, floatValue9, value, floatValue6);
        }
      }

      immediate.draw();
    }
  }

  public static void apply(float floatValue, float floatValue2) {
    for (int index = 0; index <= 96; index++) {
      double doubleValue = index * 2.0 * Math.PI / 96.0;
      float floatValue3 = (float) Math.cos(doubleValue);
      float floatValue4 = (float) Math.sin(doubleValue);

      for (int index2 = 0; index2 <= 3; index2++) {
        float floatValue5 = -0.28F + 0.56F * index2 / 3.0F;
        float floatValue6 =
            amountArray[0] * floatValue3 + amountArray2[0] * floatValue4 + 0.351F * floatValue5;
        float floatValue7 =
            amountArray[1] * floatValue3 + amountArray2[1] * floatValue4 + 0.822F * floatValue5;
        float floatValue8 =
            amountArray[2] * floatValue3 + amountArray2[2] * floatValue4 + 0.448F * floatValue5;
        float floatValue9 =
            (float)
                Math.sqrt(
                    floatValue6 * floatValue6
                        + floatValue7 * floatValue7
                        + floatValue8 * floatValue8);
        floatValue6 /= floatValue9;
        floatValue7 /= floatValue9;
        floatValue8 /= floatValue9;
        float floatValue10 = floatValue7 * floatValue - floatValue8 * floatValue2;
        float floatValue11 = floatValue7 * floatValue2 + floatValue8 * floatValue;
        int index3 = (index * 4 + index2) * 3;
        amountArray3[index3] = floatValue6;
        amountArray3[index3 + 1] = floatValue10;
        amountArray3[index3 + 2] = floatValue11;
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      int intValue,
      int intValue2,
      float floatValue,
      float floatValue2,
      int intValue3,
      float floatValue3) {
    int index = (intValue * 4 + intValue2) * 3;
    int intValue4 = (int) (ArgbColors.red(intValue3) * floatValue3 + 0.5F);
    int intValue5 = (int) (ArgbColors.green(intValue3) * floatValue3 + 0.5F);
    int intValue6 = (int) (ArgbColors.blue(intValue3) * floatValue3 + 0.5F);
    int intValue7 = (int) (floatValue3 * 255.0F + 0.5F);
    vertexConsumer
        .vertex(
            amountArray3[index] * 100.0F,
            amountArray3[index + 1] * 100.0F,
            amountArray3[index + 2] * 100.0F)
        .texture(floatValue, floatValue2)
        .color(intValue4, intValue5, intValue6, intValue7);
  }

  static {
    amountArray[0] = -0.448F;
    amountArray[1] = 0.0F;
    amountArray[2] = 0.351F;
    SkySupport6.test(amountArray);
    amountArray2[0] = 0.822F * amountArray[2] - 0.448F * amountArray[1];
    amountArray2[1] = 0.448F * amountArray[0] - 0.351F * amountArray[2];
    amountArray2[2] = 0.351F * amountArray[1] - 0.822F * amountArray[0];
    SkySupport6.test(amountArray2);
  }
}

package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport15 {
  public static final String string = "GAS";
  public static final String string2 = "ROCK";
  public static final String string3 = "ICE";
  public static final String string4 = "CLOUDS";
  public static final String string5 = "HAZE";
  public static final String string6 = "ICE_HAZE";
  public static final String string7 = "RINGS";
  public static final String string8 = "OCEAN";
  public static final String string9 = "OCEAN_CLOUDS";
  public static final String string10 = "OCEAN_HAZE";
  public static final String string11 = "VOLCANIC";
  public static final String string12 = "VOLCANIC_HAZE";
  public static final float amount = 1.3F;
  public static final float amount2 = 2.18F;
  public static final float amount3 = (float) (Math.PI * 2);
  public static final SkySupport20 skySupport20 = new SkySupport20(96, 48);
  public static final SkySupport20 skySupport202 = new SkySupport20(48, 24);
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];
  public static int count;
  public static int count2;
  public static int count3;
  public static int count4;
  public static int count5;

  public static float computeFloat(float[] floatValues, float floatValue) {
    return SkyColorPalette.computeFloat(-0.055F, 0.085F, floatValues[1])
        * (1.0F - floatValue * 0.65F);
  }

  public static void apply(float[] floatValues) {
    float floatValue = Math.abs(floatValues[1]) < 0.9F ? 0.0F : 1.0F;
    float floatValue2 = Math.abs(floatValues[1]) < 0.9F ? 1.0F : 0.0F;
    amountArray[0] = floatValue2 * floatValues[2];
    amountArray[1] = -floatValue * floatValues[2];
    amountArray[2] = floatValue * floatValues[1] - floatValue2 * floatValues[0];
    SkySupport6.test(amountArray);
    amountArray2[0] = floatValues[1] * amountArray[2] - floatValues[2] * amountArray[1];
    amountArray2[1] = floatValues[2] * amountArray[0] - floatValues[0] * amountArray[2];
    amountArray2[2] = floatValues[0] * amountArray[1] - floatValues[1] * amountArray[0];
    SkySupport6.test(amountArray2);
  }

  public static void apply(float[] floatValues, float[] floatValues2, float[] floatValues3) {
    float floatValue = computeFloat(floatValues, floatValues2);

    for (int index = 0; index < 3; index++) {
      floatValues3[index] =
          (floatValues[index] - floatValues2[index] * floatValue) * 0.85F
              - floatValues2[index] * 0.65F;
    }

    SkySupport6.test(floatValues3);
  }

  public static void render(
      float[] floatValues,
      float[] floatValues2,
      float floatValue,
      float[] floatValues3,
      float floatValue2,
      double doubleValue) {
    apply(floatValues2);
    float floatValue3 = (float) Math.cos(floatValue);
    float floatValue4 = (float) Math.sin(floatValue);
    float floatValue5 = computeFloat(floatValues3, amountArray);
    float floatValue6 = computeFloat(floatValues3, amountArray2);
    amountArray3[0] = floatValue5 * floatValue3 - floatValue6 * floatValue4;
    amountArray3[1] = computeFloat(floatValues3, floatValues2);
    amountArray3[2] = floatValue5 * floatValue4 + floatValue6 * floatValue3;
    float floatValue7 = -computeFloat(floatValues, amountArray);
    float floatValue8 = -computeFloat(floatValues, floatValues2);
    float floatValue9 = -computeFloat(floatValues, amountArray2);
    float floatValue10 =
        (float)
            Math.sqrt(
                floatValue7 * floatValue7 + floatValue8 * floatValue8 + floatValue9 * floatValue9);
    count =
        Math.round(
            (float)
                    Math.atan2(
                        floatValue7 * floatValue4 + floatValue9 * floatValue3,
                        floatValue7 * floatValue3 - floatValue9 * floatValue4)
                * 8192.0F);
    count2 =
        Math.round(
            (float) Math.asin(Math.clamp(floatValue8 / floatValue10, -1.0F, 1.0F)) * 8192.0F);
    count3 = Math.round(computeFloat(doubleValue, 0.025F) * 4096.0F);
    count4 = Math.round(computeFloat(doubleValue, 0.007F) * 4096.0F);
    count5 =
        ArgbColors.withAlpha(
            ArgbColors.lerp(-1, SkyColorPalette.computeInt(), 0.055F),
            Math.round(Math.clamp(floatValue2, 0.0F, 1.0F) * 255.0F));
  }

  public static void render(
      Immediate immediate,
      String text,
      float[] floatValues,
      float floatValue,
      float[] floatValues2,
      float floatValue2,
      float[] floatValues3,
      float floatValue3,
      double doubleValue,
      boolean flag) {
    if (SkySupport7.test(floatValues[0], floatValues[1], floatValues[2], floatValue)) {
      render(floatValues, floatValues2, floatValue2, floatValues3, floatValue3, doubleValue);
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.planet(text));
      SkySupport20 skySupport203 = flag ? skySupport202 : skySupport20;

      for (int index = 0; index < skySupport203.amountArray.length; index++) {
        float floatValue4 = skySupport203.amountArray[index];
        float floatValue5 = skySupport203.amountArray2[index];
        float floatValue6 = skySupport203.amountArray3[index];
        skySupport203.amountArray6[index] =
            floatValues[0]
                + floatValue
                    * (amountArray[0] * floatValue4
                        + floatValues2[0] * floatValue5
                        + amountArray2[0] * floatValue6);
        skySupport203.amountArray7[index] =
            floatValues[1]
                + floatValue
                    * (amountArray[1] * floatValue4
                        + floatValues2[1] * floatValue5
                        + amountArray2[1] * floatValue6);
        skySupport203.amountArray8[index] =
            floatValues[2]
                + floatValue
                    * (amountArray[2] * floatValue4
                        + floatValues2[2] * floatValue5
                        + amountArray2[2] * floatValue6);
      }

      float floatValue7 = computeFloat(floatValues, amountArray);
      float floatValue8 = computeFloat(floatValues, floatValues2);
      float floatValue9 = computeFloat(floatValues, amountArray2);

      for (int index2 = 0; index2 < skySupport203.countArray.length / 4; index2++) {
        if (!skySupport203.test(index2, floatValue7, floatValue8, floatValue9, floatValue)) {
          for (int index3 = 0; index3 < 4; index3++) {
            int index4 = skySupport203.countArray[index2 * 4 + index3];
            render(
                buffer,
                skySupport203.amountArray6[index4],
                skySupport203.amountArray7[index4],
                skySupport203.amountArray8[index4],
                skySupport203.amountArray4[index4] + floatValue2,
                skySupport203.amountArray5[index4]);
          }
        }
      }

      immediate.draw();
    }
  }

  public static void render(
      Immediate immediate,
      float[] floatValues,
      float floatValue,
      float[] floatValues2,
      float[] floatValues3,
      float floatValue2,
      double doubleValue,
      boolean flag) {
    if (SkySupport7.test(floatValues[0], floatValues[1], floatValues[2], floatValue * 2.18F)) {
      render(floatValues, floatValues2, 0.0F, floatValues3, floatValue2, doubleValue);
      float floatValue3 =
          (float)
                  Math.atan2(
                      computeFloat(floatValues, amountArray2),
                      computeFloat(floatValues, amountArray))
              - (float) (Math.PI / 2);
      if (!flag) {
        floatValue3 += (float) Math.PI;
      }

      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.planet("RINGS"));

      for (int index = 0; index < 128; index++) {
        float floatValue4 = floatValue3 + (float) Math.PI * index / 128.0F;
        float floatValue5 = floatValue3 + (float) Math.PI * (index + 1) / 128.0F;
        render(buffer, floatValues, floatValue, floatValue4, 1.3F);
        render(buffer, floatValues, floatValue, floatValue5, 1.3F);
        render(buffer, floatValues, floatValue, floatValue5, 2.18F);
        render(buffer, floatValues, floatValue, floatValue4, 2.18F);
      }

      immediate.draw();
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      float[] floatValues,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    float floatValue4 = (float) Math.cos(floatValue2) * floatValue3;
    float floatValue5 = (float) Math.sin(floatValue2) * floatValue3;
    render(
        vertexConsumer,
        floatValues[0]
            + floatValue * (amountArray[0] * floatValue4 + amountArray2[0] * floatValue5),
        floatValues[1]
            + floatValue * (amountArray[1] * floatValue4 + amountArray2[1] * floatValue5),
        floatValues[2]
            + floatValue * (amountArray[2] * floatValue4 + amountArray2[2] * floatValue5),
        floatValue2,
        floatValue3);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    vertexConsumer
        .vertex(floatValue * 100.0F, floatValue2 * 100.0F, floatValue3 * 100.0F)
        .color(count5)
        .texture(floatValue4, floatValue5)
        .overlay(count, count2)
        .light(count3, count4)
        .normal(amountArray3[0], amountArray3[1], amountArray3[2]);
  }

  public static void render(
      float[] floatValues,
      float[] floatValues2,
      float floatValue,
      float floatValue2,
      float[] floatValues3) {
    apply(floatValues2);
    float floatValue3 = (float) Math.cos(floatValue2);
    float floatValue4 = (float) Math.sin(floatValue2);

    for (int index = 0; index < 3; index++) {
      floatValues3[index] =
          floatValues[index]
              + (amountArray[index] * floatValue3 + amountArray2[index] * floatValue4) * floatValue;
    }
  }

  public static float computeFloat(double doubleValue, float floatValue) {
    return (float) (doubleValue * floatValue % (Math.PI * 2));
  }

  public static float computeFloat(float[] floatValues, float[] floatValues2) {
    return floatValues[0] * floatValues2[0]
        + floatValues[1] * floatValues2[1]
        + floatValues[2] * floatValues2[2];
  }
}

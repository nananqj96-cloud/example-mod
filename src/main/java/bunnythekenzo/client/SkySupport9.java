package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport9 {
  public static final int count = 24;
  public static final int count2 = 28;
  public static final float[] amountArray = new float[25];
  public static final float[] amountArray2 = new float[25];
  public static final float[] amountArray3 = new float[3];

  public static void render(
      Immediate immediate, float floatValue, float floatValue2, float floatValue3) {
    VertexConsumer buffer = immediate.getBuffer(SkyPipelines.rocketPlume());
    float floatValue4 =
        1.0F
            + 0.045F * (float) Math.sin(floatValue * 31.0F)
            + 0.023F * (float) Math.sin(floatValue * 47.0F);

    for (int index = 0; index < 3; index++) {
      float floatValue5 = (0.95F + floatValue2 * 2.4F) * (index == 0 ? 1.0F : 0.7F) * floatValue4;
      float floatValue6 = index == 0 ? 0.206F : 0.108F;

      for (int index2 = 0; index2 < 2; index2++) {
        for (int index3 = 0; index3 < 28; index3++) {
          float floatValue7 = index3 / 28.0F;
          float floatValue8 = (index3 + 1.0F) / 28.0F;

          for (int index4 = 0; index4 < 24; index4++) {
            render(
                buffer,
                index,
                index4,
                floatValue7,
                floatValue5,
                floatValue6,
                index2,
                floatValue,
                floatValue3,
                floatValue2);
            render(
                buffer,
                index,
                index4 + 1,
                floatValue7,
                floatValue5,
                floatValue6,
                index2,
                floatValue,
                floatValue3,
                floatValue2);
            render(
                buffer,
                index,
                index4 + 1,
                floatValue8,
                floatValue5,
                floatValue6,
                index2,
                floatValue,
                floatValue3,
                floatValue2);
            render(
                buffer,
                index,
                index4,
                floatValue8,
                floatValue5,
                floatValue6,
                index2,
                floatValue,
                floatValue3,
                floatValue2);
          }
        }
      }
    }

    immediate.draw();
    VertexConsumer buffer2 = immediate.getBuffer(SkyPipelines.sky("mote", true));

    for (int index5 = 0; index5 < 3; index5++) {
      SkySupport12.render(index5, 0.05F, amountArray3);
      SkySupport6.test(amountArray3);
      SkySupport6.render(
          buffer2,
          amountArray3[0],
          amountArray3[1],
          amountArray3[2],
          index5 == 0 ? 0.86F : 0.45F,
          -8594433,
          floatValue3 * (0.17F + 0.15F * floatValue2));
    }

    immediate.draw();
  }

  public static void render(
      VertexConsumer vertexConsumer,
      int intValue,
      int index,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue2,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 =
        1.0F + 0.2F * (float) Math.cos(floatValue * 39.0F - 0.7F) * (1.0F - floatValue);
    float floatValue8 =
        floatValue3
            * (float) Math.pow(1.0F - floatValue, 0.72F)
            * floatValue7
            * (intValue2 == 0 ? 1.0F : 0.34F);
    SkySupport12.render(
        intValue,
        floatValue * floatValue2,
        floatValue8 * amountArray[index],
        floatValue8 * amountArray2[index],
        amountArray3);
    float floatValue9 =
        floatValue5 * (intValue2 == 0 ? 0.18F : 0.4F) * (0.7F + 0.3F * Math.min(1.0F, floatValue6));
    vertexConsumer
        .vertex(amountArray3[0] * 100.0F, amountArray3[1] * 100.0F, amountArray3[2] * 100.0F)
        .texture((float) (index * Math.PI * 2.0 / 24.0) + floatValue4 * 5.0F, floatValue)
        .color(
            intValue2 == 0 ? 78 : 195,
            intValue2 == 0 ? 133 : 231,
            255,
            Math.round(floatValue9 * 255.0F));
  }

  static {
    for (int index = 0; index <= 24; index++) {
      amountArray[index] = (float) Math.cos(index * Math.PI * 2.0 / 24.0);
      amountArray2[index] = (float) Math.sin(index * Math.PI * 2.0 / 24.0);
    }
  }
}

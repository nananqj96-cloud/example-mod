package bunnythekenzo.client;

import java.util.Random;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport22 {
  public static final long timestamp = 1734827038L;
  public static int count = -1;
  public static float[] amountArray;
  public static float[] amountArray2;
  public static float[] amountArray3;
  public static float[] amountArray4;
  public static float[] amountArray5;
  public static float[] amountArray6;
  public static float[] amountArray7;
  public static float[] amountArray8;

  public static void apply(int count2) {
    if (count != count2) {
      count = count2;
      amountArray = new float[count2];
      amountArray2 = new float[count2];
      amountArray3 = new float[count2];
      amountArray4 = new float[count2];
      amountArray5 = new float[count2];
      amountArray6 = new float[count2];
      amountArray7 = new float[count2];
      amountArray8 = new float[count2];
      Random random = new Random(1734827038L);

      for (int index = 0; index < count2; index++) {
        float floatValue = random.nextFloat() * 2.0F - 1.0F;
        float floatValue2 = (float) Math.sqrt(Math.max(0.0F, 1.0F - floatValue * floatValue));
        float floatValue3 = random.nextFloat() * (float) (Math.PI * 2);
        amountArray[index] = floatValue2 * (float) Math.cos(floatValue3);
        amountArray2[index] = floatValue;
        amountArray3[index] = floatValue2 * (float) Math.sin(floatValue3);
        boolean flag = random.nextFloat() < 0.58F;
        if (flag) {
          float floatValue4 = 0.7F + 0.26F * random.nextFloat();
          float floatValue5 =
              amountArray[index] * 0.351F
                  + amountArray2[index] * 0.822F
                  + amountArray3[index] * 0.448F;
          amountArray[index] = amountArray[index] - 0.351F * floatValue5 * floatValue4;
          amountArray2[index] = amountArray2[index] - 0.822F * floatValue5 * floatValue4;
          amountArray3[index] = amountArray3[index] - 0.448F * floatValue5 * floatValue4;
          float floatValue6 =
              (float)
                  Math.sqrt(
                      amountArray[index] * amountArray[index]
                          + amountArray2[index] * amountArray2[index]
                          + amountArray3[index] * amountArray3[index]);
          amountArray[index] = amountArray[index] / floatValue6;
          amountArray2[index] = amountArray2[index] / floatValue6;
          amountArray3[index] = amountArray3[index] / floatValue6;
        }

        float floatValue7 = random.nextFloat();
        float floatValue8 = flag ? 0.75F : 1.0F;
        amountArray4[index] =
            100.0F * (7.0E-4F + 0.0021F * floatValue7 * floatValue7) * floatValue8;
        amountArray5[index] = (flag ? 0.4F : 0.46F) + 0.6F * random.nextFloat();
        amountArray6[index] = random.nextFloat() * (float) (Math.PI * 2);
        amountArray7[index] = 0.45F + 1.3F * random.nextFloat();
        amountArray8[index] = 1.0F - random.nextFloat() * random.nextFloat();
      }
    }
  }

  public static void render(
      Immediate immediate, float floatValue, float floatValue2, float floatValue3) {
    int intValue = Math.round(SkyModule.computeFloat());
    apply(intValue);
    float floatValue4 = (float) Math.cos(floatValue);
    float floatValue5 = (float) Math.sin(floatValue);
    boolean flag = SkyModule.test6();
    float floatValue6 = (1.0F - floatValue3) * (0.55F + 0.45F * SkyModule.computeFloat2());
    int value = SkyColorPalette.computeInt();
    int int6 = SkyColorPalette.computeInt6();
    VertexConsumer buffer = immediate.getBuffer(SkyPipelines.getRenderLayer2());

    for (int index = 0; index < intValue; index++) {
      float floatValue7 = amountArray[index];
      float floatValue8 = amountArray2[index] * floatValue4 - amountArray3[index] * floatValue5;
      float floatValue9 = amountArray2[index] * floatValue5 + amountArray3[index] * floatValue4;
      if (!(floatValue8 < -0.03F)
          && SkySupport7.test(
              floatValue7, floatValue8, floatValue9, amountArray4[index] * 2.0F / 100.0F)) {
        float floatValue10 = amountArray5[index] * floatValue6;
        if (flag) {
          floatValue10 *=
              0.55F
                  + 0.45F
                      * (float) Math.sin(floatValue2 * amountArray7[index] + amountArray6[index]);
        }

        floatValue10 *= SkyColorPalette.computeFloat(-0.03F, 0.06F, floatValue8);
        if (!(floatValue10 <= 0.004F)) {
          int value2 = ArgbColors.lerp(value, int6, amountArray8[index]);
          SkySupport6.render(
              buffer,
              floatValue7,
              floatValue8,
              floatValue9,
              amountArray4[index],
              value2,
              floatValue10);
        }
      }
    }

    immediate.draw();
  }
}

package bunnythekenzo.client;

import java.util.Random;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport2 {
  public static final int count = 3;
  public static final float amount = 0.3F;
  public static final float amount2 = 0.55F;
  public static final float amount3 = 0.1F;
  public static final float amount4 = 0.17F;
  public static final Random random = new Random();
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];
  public static final float[] amountArray4 = new float[3];
  public static final float[] amountArray5 = new float[3];
  public static final float[] amountArray6 = new float[3];
  public static final float[] amountArray7 = new float[3];
  public static final float[] amountArray8 = new float[3];
  public static final float[] amountArray9 = new float[3];
  public static final float[] amountArray10 = new float[3];
  public static final boolean[] enabledArray = new boolean[3];
  public static float amount5 = 4.0F;
  public static int count2 = -1;
  public static final float[] amountArray11 = new float[3];
  public static final float[] amountArray12 = new float[3];
  public static final float[] amountArray13 = new float[3];

  public static void update() {
    amount5 = 0.0F;
  }

  public static boolean test(float[] floatValues) {
    if (count2 >= 0 && enabledArray[count2]) {
      render(
          count2, amountArray7[count2] / amountArray8[count2] * amountArray9[count2], floatValues);
      return true;
    } else {
      return false;
    }
  }

  public static void render(
      Immediate immediate, float floatValue, float floatValue2, float floatValue3) {
    amount5 -= floatValue;
    if (amount5 <= 0.0F) {
      update2();
      float floatValue4 = 17.0F - 15.4F * Math.clamp(floatValue2, 0.0F, 1.0F);
      amount5 = floatValue4 * (0.55F + 0.9F * random.nextFloat());
    }

    float floatValue5 = 1.0F - floatValue3;
    int value = ArgbColors.lerp(SkyColorPalette.computeInt6(), -1, 0.35F);
    VertexConsumer buffer = immediate.getBuffer(SkyPipelines.sky("streak", true));
    boolean flag = false;

    for (int index = 0; index < 3; index++) {
      if (enabledArray[index]) {
        amountArray7[index] = amountArray7[index] + floatValue;
        if (amountArray7[index] >= amountArray8[index]) {
          enabledArray[index] = false;
        } else {
          float floatValue6 = amountArray7[index] / amountArray8[index];
          float floatValue7 =
              SkyColorPalette.computeFloat(0.0F, 0.1F, floatValue6)
                  * (1.0F - SkyColorPalette.computeFloat(0.55F, 1.0F, floatValue6));
          if (!(floatValue7 <= 0.01F)) {
            render(index, floatValue6 * amountArray9[index], amountArray11);
            render(
                index,
                Math.max(0.0F, floatValue6 * amountArray9[index] - amountArray10[index]),
                amountArray12);
            amountArray13[0] = amountArray11[0] + amountArray12[0];
            amountArray13[1] = amountArray11[1] + amountArray12[1];
            amountArray13[2] = amountArray11[2] + amountArray12[2];
            if (SkySupport6.test(amountArray13)) {
              float floatValue8 = (amountArray11[0] - amountArray12[0]) * 100.0F;
              float floatValue9 = (amountArray11[1] - amountArray12[1]) * 100.0F;
              float floatValue10 = (amountArray11[2] - amountArray12[2]) * 100.0F;
              float floatValue11 =
                  0.5F
                      * (float)
                          Math.sqrt(
                              floatValue8 * floatValue8
                                  + floatValue9 * floatValue9
                                  + floatValue10 * floatValue10);
              if (!(floatValue11 < 0.01F)) {
                float value2 =
                    SkySupport6.computeFloat(
                        amountArray13[0],
                        amountArray13[1],
                        amountArray13[2],
                        floatValue8,
                        floatValue9,
                        floatValue10);
                SkySupport6.render(
                    buffer,
                    amountArray13[0],
                    amountArray13[1],
                    amountArray13[2],
                    floatValue11 * 0.28F,
                    floatValue11,
                    value2,
                    value,
                    floatValue7 * floatValue5);
                flag = true;
              }
            }
          }
        }
      }
    }

    if (flag) {
      immediate.draw();
    }
  }

  public static void render(int index, float floatValue, float[] floatValues) {
    float floatValue2 = (float) Math.cos(floatValue);
    float floatValue3 = (float) Math.sin(floatValue);
    floatValues[0] = amountArray[index] * floatValue2 + amountArray4[index] * floatValue3;
    floatValues[1] = amountArray2[index] * floatValue2 + amountArray5[index] * floatValue3;
    floatValues[2] = amountArray3[index] * floatValue2 + amountArray6[index] * floatValue3;
  }

  public static void update2() {
    for (int index = 0; index < 3; index++) {
      if (!enabledArray[index]) {
        float floatValue = 0.12F + 0.8F * random.nextFloat();
        float floatValue2 = (float) Math.sqrt(Math.max(0.0F, 1.0F - floatValue * floatValue));
        float floatValue3 = random.nextFloat() * (float) (Math.PI * 2);
        amountArray[index] = floatValue2 * (float) Math.cos(floatValue3);
        amountArray2[index] = floatValue;
        amountArray3[index] = floatValue2 * (float) Math.sin(floatValue3);
        float floatValue4 = random.nextFloat() * 2.0F - 1.0F;
        float floatValue5 = random.nextFloat() * 2.0F - 1.0F;
        float floatValue6 = random.nextFloat() * 2.0F - 1.0F;
        float floatValue7 =
            floatValue4 * amountArray[index]
                + floatValue5 * amountArray2[index]
                + floatValue6 * amountArray3[index];
        floatValue4 -= amountArray[index] * floatValue7;
        floatValue5 -= amountArray2[index] * floatValue7;
        floatValue6 -= amountArray3[index] * floatValue7;
        float floatValue8 =
            (float)
                Math.sqrt(
                    floatValue4 * floatValue4
                        + floatValue5 * floatValue5
                        + floatValue6 * floatValue6);
        if (floatValue8 < 1.0E-4F) {
          return;
        }

        amountArray4[index] = floatValue4 / floatValue8;
        amountArray5[index] = floatValue5 / floatValue8;
        amountArray6[index] = floatValue6 / floatValue8;
        amountArray9[index] = 0.3F + 0.25F * random.nextFloat();
        amountArray10[index] = 0.1F + 0.07F * random.nextFloat();
        amountArray8[index] = 0.65F + 0.55F * random.nextFloat();
        amountArray7[index] = 0.0F;
        enabledArray[index] = true;
        count2 = index;
        return;
      }
    }
  }
}

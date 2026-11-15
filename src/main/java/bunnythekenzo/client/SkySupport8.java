package bunnythekenzo.client;

import java.util.Random;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport8 {
  public static final float amount = 8.0F;
  public static final float amount2 = 0.19F;
  public static final float amount3 = 0.72F;
  public static final float amount4 = 0.075F;
  public static final float amount5 = 0.34F;
  public static final Random random = new Random();
  public static float amount6;
  public static float amount7 = 1.0F;
  public static float amount8;
  public static float amount9 = -1.0F;
  public static float amount10 = 24.0F;
  public static final float[] amountArray = new float[3];

  public static void update() {
    amount10 = 0.0F;
    amount9 = -1.0F;
  }

  public static boolean test(float[] floatValues) {
    if (amount9 < 0.0F) {
      return false;
    }

    floatValues[0] = amount6;
    floatValues[1] = amount7;
    floatValues[2] = amount8;
    return true;
  }

  public static void render(
      Immediate immediate, float floatValue, float floatValue2, float floatValue3) {
    if (amount9 < 0.0F) {
      amount10 -= floatValue;
      if (amount10 > 0.0F) {
        return;
      }

      update2();
    }

    amount9 += floatValue;
    if (amount9 >= 8.0F) {
      amount9 = -1.0F;
      amount10 = 30.0F + 42.0F * random.nextFloat();
    } else {
      float floatValue4 = 1.0F - floatValue3;
      if (!(floatValue4 <= 0.01F)) {
        float floatValue5 = amount9 / 8.0F;
        amountArray[0] = amount6;
        amountArray[1] = amount7;
        amountArray[2] = amount8;
        SkySupport6.render(
            amountArray, (float) Math.cos(floatValue2), (float) Math.sin(floatValue2));
        int value = SkyColorPalette.computeInt();
        int value2 = ArgbColors.lerp(value, -1, 0.72F);
        if (floatValue5 > 0.19F) {
          float floatValue6 = (floatValue5 - 0.19F) / 0.81F;
          float floatValue7 = 0.34F * (1.0F - floatValue6) * (1.0F - floatValue6) * floatValue4;
          if (floatValue7 > 0.004F) {
            float floatValue8 = 0.075F * (1.6F + 2.6F * floatValue6) * 100.0F;
            VertexConsumer buffer = immediate.getBuffer(SkyPipelines.sky("nebula", true));
            SkySupport6.render(
                buffer,
                amountArray[0],
                amountArray[1],
                amountArray[2],
                floatValue8,
                floatValue8,
                floatValue6 * 0.7F,
                value,
                floatValue7);
            immediate.draw();
          }
        }

        if (floatValue5 > 0.19F && floatValue5 < 0.72F) {
          float floatValue9 = (floatValue5 - 0.19F) / 0.53000003F;
          float floatValue10 = 1.0F - (1.0F - floatValue9) * (1.0F - floatValue9);
          float floatValue11 =
              0.58F
                  * (1.0F - floatValue9)
                  * (1.0F - floatValue9)
                  * SkyColorPalette.computeFloat(0.0F, 0.08F, floatValue9)
                  * floatValue4;
          if (floatValue11 > 0.004F) {
            float floatValue12 = 0.34F * (0.1F + 0.9F * floatValue10) * 100.0F;
            VertexConsumer buffer2 = immediate.getBuffer(SkyPipelines.sky("shockwave", true));
            SkySupport6.render(
                buffer2,
                amountArray[0],
                amountArray[1],
                amountArray[2],
                floatValue12,
                floatValue12,
                0.0F,
                ArgbColors.lerp(value, SkyColorPalette.computeInt6(), 0.45F),
                floatValue11);
            immediate.draw();
          }
        }

        float floatValue13;
        float floatValue14;
        if (floatValue5 < 0.19F) {
          float floatValue15 = floatValue5 / 0.19F;
          floatValue13 = 0.22F + 1.9F * floatValue15 * floatValue15 * floatValue15;
          floatValue14 = 0.3F + 0.7F * floatValue15 * floatValue15;
        } else {
          float floatValue16 = (floatValue5 - 0.19F) / 0.81F;
          floatValue13 = 0.15F + 2.12F * (float) Math.exp(-floatValue16 * 5.5F);
          floatValue14 = (float) Math.exp(-floatValue16 * 4.0F);
        }

        floatValue14 *= floatValue4;
        if (floatValue14 > 0.004F) {
          float floatValue17 = 0.075F * floatValue13 * 100.0F;
          VertexConsumer buffer3 = immediate.getBuffer(SkyPipelines.sky("nova", true));
          SkySupport6.render(
              buffer3,
              amountArray[0],
              amountArray[1],
              amountArray[2],
              floatValue17,
              floatValue17,
              amount9 * 0.1F,
              value2,
              floatValue14);
          immediate.draw();
        }
      }
    }
  }

  public static void update2() {
    float amount72 = 0.45F + 0.5F * random.nextFloat();
    float floatValue = (float) Math.sqrt(Math.max(0.0F, 1.0F - amount72 * amount72));
    float floatValue2 = random.nextFloat() * (float) (Math.PI * 2);
    amount6 = floatValue * (float) Math.cos(floatValue2);
    amount7 = amount72;
    amount8 = floatValue * (float) Math.sin(floatValue2);
    amount9 = 0.0F;
  }
}

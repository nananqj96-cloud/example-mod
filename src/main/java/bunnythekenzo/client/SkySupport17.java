package bunnythekenzo.client;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport17 {
  public static final int count = 160;
  public static final float amount = 0.03F;
  public static final float amount2 = 2.4F;
  public static final float amount3 = 0.028F;
  public static final float amount4 = 2.8F;
  public static final float amount5 = 0.07F;
  public static final Random random = new Random();
  public static boolean enabled;
  public static float amount6 = 6.0F;
  public static float amount7;
  public static float amount8;
  public static float amount9;
  public static float amount10;
  public static float amount11;
  public static float amount12;
  public static float amount13;
  public static float amount14;
  public static float amount15;
  public static boolean enabled2;
  public static float amount16;
  public static float amount17;
  public static float amount18;
  public static float amount19;
  public static float amount20;
  public static float amount21 = 4.0F;
  public static float amount22;
  public static final float[] amountArray = new float[160];
  public static final float[] amountArray2 = new float[160];
  public static final float[] amountArray3 = new float[160];
  public static final float[] amountArray4 = new float[160];
  public static final float[] amountArray5 = new float[160];
  public static final float[] amountArray6 = new float[160];
  public static int count2;
  public static float amount23;
  public static final float[] amountArray7 = new float[3];
  public static final float[] amountArray8 = new float[3];
  public static final float[] amountArray9 = new float[3];
  public static final float[] amountArray10 = new float[3];
  public static final float[] amountArray11 = new float[3];

  public static boolean test(float[] floatValues) {
    if (!enabled) {
      return false;
    }

    apply(amount14 * amount13, floatValues);
    return true;
  }

  public static void update() {
    amount20 = 1.0F;
    amount22 = 8.0F;
    if (!enabled2) {
      amount18 = Math.max(amount18, 8.0F);
    }
  }

  public static void update2() {
    enabled2 = true;
    amount16 = 0.0F;
    amount17 = 2.4F;
  }

  public static void update3() {
    amount6 = 0.0F;
    enabled = false;
  }

  public static void update4() {
    amount7 = -0.8F;
    amount8 = 0.36F;
    amount9 = 0.48F;
    float floatValue = (float) Math.sqrt(amount7 * amount7 + amount8 * amount8 + amount9 * amount9);
    amount7 /= floatValue;
    amount8 /= floatValue;
    amount9 /= floatValue;
    float floatValue2 = amount8;
    amount10 = -amount7 * floatValue2;
    amount11 = 1.0F - amount8 * floatValue2;
    amount12 = -amount9 * floatValue2;
    floatValue = (float) Math.sqrt(amount10 * amount10 + amount11 * amount11 + amount12 * amount12);
    amount10 /= floatValue;
    amount11 /= floatValue;
    amount12 /= floatValue;
    float floatValue3 = (float) Math.sqrt(amount7 * amount7 + amount9 * amount9);
    amount10 = amount10 * 0.82F - amount9 / floatValue3 * 0.57F;
    amount11 *= 0.82F;
    amount12 = amount12 * 0.82F + amount7 / floatValue3 * 0.57F;
    floatValue = (float) Math.sqrt(amount10 * amount10 + amount11 * amount11 + amount12 * amount12);
    amount10 /= floatValue;
    amount11 /= floatValue;
    amount12 /= floatValue;
    amount13 = 2.2F;
    amount14 = 0.15F;
    amount15 = 0.0F;
    enabled = true;
    enabled2 = false;
    amount18 = 60.0F;
    amount19 = 0.0F;
    amount20 = 0.0F;
    amount21 = 60.0F;
    amount22 = 0.0F;
    amount23 = 0.0F;
    Arrays.fill(amountArray4, 0.0F);
    apply(amount14 * amount13, amountArray7);
  }

  public static float[] getAmountArray() {
    return new float[] {enabled ? 1.0F : 0.0F, amount14, amount15, computeFloat(), amount19};
  }

  public static void render(Immediate immediate, float floatValue, float floatValue2) {
    apply2(floatValue);
    if (enabled) {
      apply(amount14 * amount13, amountArray7);
    }

    float floatValue3 = 1.0F - floatValue2;
    if (!(floatValue3 <= 0.01F)) {
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.sky("mote", true));

      for (int index = 0; index < 160; index++) {
        if (!(amountArray4[index] <= 0.0F) && !(amountArray4[index] >= 2.4F)) {
          float floatValue4 = 1.0F - amountArray4[index] / 2.4F;
          float floatValue5 =
              floatValue4
                  * floatValue4
                  * 0.24F
                  * (0.55F + 0.75F * amountArray5[index])
                  * floatValue3;
          if (!(floatValue5 <= 0.004F)) {
            float floatValue6 =
                100.0F * amountArray6[index] * (0.55F + 0.75F * (1.0F - floatValue4));
            SkySupport6.render(
                buffer,
                amountArray[index],
                amountArray2[index],
                amountArray3[index],
                floatValue6,
                ArgbColors.lerp(-9204290, -6561793, amountArray5[index]),
                floatValue5);
          }
        }
      }

      immediate.draw();
      if (enabled) {
        float floatValue7 =
            SkyColorPalette.computeFloat(0.0F, 0.05F, amount14)
                * (1.0F - SkyColorPalette.computeFloat(0.95F, 1.0F, amount14))
                * SkyColorPalette.computeFloat(-0.12F, 0.015F, amountArray7[1]);
        if (!(floatValue7 <= 0.01F)) {
          float floatValue8 = computeFloat();
          float floatValue9 = Math.max(0.0F, floatValue8);
          apply(floatValue9);
          SkySupport12.render(
              amountArray7,
              amountArray10,
              amountArray9,
              amountArray8,
              0.07F,
              amount15,
              floatValue9,
              amount19);
          SkySupport9.render(
              immediate,
              amount15,
              floatValue9,
              floatValue7 * floatValue3 * (1.0F + Math.min(0.0F, floatValue8) * 2.5F));
          SkySupport12.render(
              immediate, getIdentifier(), floatValue7 * floatValue3, amount15, floatValue9);
        }
      }
    }
  }

  public static Identifier getIdentifier() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    return player == null ? null : player.getSkin().body().texturePath();
  }

  public static void apply(float floatValue) {
    float floatValue2 = amount14 * amount13;
    apply(floatValue2, amountArray7);
    apply2(floatValue2, amountArray8);
    float floatValue3 = 0.13F * (float) Math.sin(amount15 * 0.47F) + 0.1F * floatValue;

    for (int index = 0; index < 3; index++) {
      amountArray8[index] = amountArray8[index] + amountArray7[index] * floatValue3;
    }

    SkySupport6.test(amountArray8);
    if (floatValue > 0.05F) {
      float floatValue4 = 2.5E-4F * floatValue;
      amountArray7[0] = amountArray7[0] + floatValue4 * (float) Math.sin(amount15 * 41.0F);
      amountArray7[1] = amountArray7[1] + floatValue4 * (float) Math.sin(amount15 * 33.0F + 1.7F);
      amountArray7[2] = amountArray7[2] + floatValue4 * (float) Math.sin(amount15 * 37.0F + 0.4F);
      SkySupport6.test(amountArray7);
    }

    float floatValue5 =
        amountArray7[0] * amountArray8[0]
            + amountArray7[1] * amountArray8[1]
            + amountArray7[2] * amountArray8[2];

    for (int index2 = 0; index2 < 3; index2++) {
      amountArray9[index2] = -amountArray7[index2] + amountArray8[index2] * floatValue5;
    }

    SkySupport6.test(amountArray9);
    apply(amountArray9, amountArray8, amountArray10);
    float floatValue6 = 0.3F * (float) Math.sin(amount15 * 0.52F) + 0.18F * floatValue;
    float floatValue7 = (float) Math.cos(floatValue6);
    float floatValue8 = (float) Math.sin(floatValue6);

    for (int index3 = 0; index3 < 3; index3++) {
      amountArray9[index3] =
          amountArray9[index3] * floatValue7 + amountArray10[index3] * floatValue8;
    }

    apply(amountArray9, amountArray8, amountArray10);
  }

  public static void apply(float[] floatValues, float[] floatValues2, float[] floatValues3) {
    floatValues3[0] = floatValues[1] * floatValues2[2] - floatValues[2] * floatValues2[1];
    floatValues3[1] = floatValues[2] * floatValues2[0] - floatValues[0] * floatValues2[2];
    floatValues3[2] = floatValues[0] * floatValues2[1] - floatValues[1] * floatValues2[0];
    SkySupport6.test(floatValues3);
  }

  public static void apply2(float floatValue) {
    amount15 += floatValue;
    if (amount15 > 3600.0F) {
      amount15 -= 3600.0F;
    }

    for (int index = 0; index < 160; index++) {
      if (amountArray4[index] > 0.0F) {
        amountArray4[index] = amountArray4[index] + floatValue;
      }
    }

    if (!enabled) {
      amount6 -= floatValue;
      if (amount6 <= 0.0F) {
        update5();
      }
    } else {
      amount18 -= floatValue;
      if (!enabled2 && amount18 <= 0.0F) {
        enabled2 = true;
        amount16 = 0.0F;
        amount17 = 1.5F + 1.1F * random.nextFloat();
      }

      if (enabled2) {
        amount16 += floatValue;
        if (amount16 >= amount17) {
          enabled2 = false;
          amount18 = 4.0F + 5.0F * random.nextFloat();
        }
      }

      float floatValue2 = Math.max(0.0F, computeFloat());
      apply3(floatValue);
      amount14 += 0.028F * (1.0F + 2.8F * floatValue2) * floatValue;
      if (amount14 >= 1.0F) {
        enabled = false;
        amount6 = 11.0F + 17.0F * random.nextFloat();
      } else {
        amount23 += floatValue;
        float floatValue3 = 0.03F * (1.0F - 0.45F * floatValue2);

        while (amount23 >= floatValue3) {
          amount23 -= floatValue3;
          float floatValue4 = amount14 - 0.028F * (1.0F + 2.8F * floatValue2) * amount23;
          render(floatValue2, 0.0F, floatValue4);
          if (floatValue2 > 0.45F) {
            render(floatValue2, 0.002F, floatValue4);
          }
        }
      }
    }
  }

  public static float computeFloat() {
    if (!enabled2) {
      return 0.0F;
    }

    float floatValue = amount16 / amount17;
    if (floatValue < 0.2F) {
      return -0.2F * (float) Math.sin(Math.PI * (floatValue / 0.2F));
    }

    if (floatValue < 0.34F) {
      float floatValue2 = (floatValue - 0.2F) / 0.14F;
      return 1.16F * floatValue2 * floatValue2 * (3.0F - 2.0F * floatValue2);
    }

    if (floatValue < 0.7F) {
      return 1.05F + 0.05F * (float) Math.sin(amount15 * 26.0F);
    }

    float floatValue3 = (floatValue - 0.7F) / 0.3F;
    return (1.0F - floatValue3) * (1.0F - floatValue3);
  }

  public static void apply3(float floatValue) {
    if (enabled2) {
      amount20 = 0.0F;
      amount22 = 0.0F;
      amount21 = 2.5F + 3.0F * random.nextFloat();
    } else if (amount20 > 0.0F) {
      amount22 -= floatValue;
      if (amount22 <= 0.0F) {
        amount20 = 0.0F;
        amount21 = 5.0F + 8.0F * random.nextFloat();
      }
    } else {
      amount21 -= floatValue;
      if (amount21 <= 0.0F) {
        amount20 = 1.0F;
        amount22 = 2.5F + 2.5F * random.nextFloat();
      }
    }

    float floatValue2 = amount20 > amount19 ? 1.6F : 3.8F;
    amount19 =
        amount19
            + Math.clamp(amount20 - amount19, -floatValue2 * floatValue, floatValue2 * floatValue);
    amount19 = Math.clamp(amount19, 0.0F, 1.0F);
  }

  public static void render(float floatValue, float floatValue2, float floatValue3) {
    apply(floatValue3 * amount13, amountArray7);
    apply2(floatValue3 * amount13, amountArray11);
    float floatValue4 = amountArray7[0] - amountArray11[0] * 0.07F * 1.7F;
    float floatValue5 = amountArray7[1] - amountArray11[1] * 0.07F * 1.7F;
    float floatValue6 = amountArray7[2] - amountArray11[2] * 0.07F * 1.7F;
    if (floatValue2 > 0.0F) {
      floatValue4 += (random.nextFloat() * 2.0F - 1.0F) * floatValue2;
      floatValue5 += (random.nextFloat() * 2.0F - 1.0F) * floatValue2;
      floatValue6 += (random.nextFloat() * 2.0F - 1.0F) * floatValue2;
      float floatValue7 =
          (float)
              Math.sqrt(
                  floatValue4 * floatValue4
                      + floatValue5 * floatValue5
                      + floatValue6 * floatValue6);
      floatValue4 /= floatValue7;
      floatValue5 /= floatValue7;
      floatValue6 /= floatValue7;
    }

    float floatValue8 =
        (float)
            Math.sqrt(
                floatValue4 * floatValue4 + floatValue5 * floatValue5 + floatValue6 * floatValue6);
    amountArray[count2] = floatValue4 / floatValue8;
    amountArray2[count2] = floatValue5 / floatValue8;
    amountArray3[count2] = floatValue6 / floatValue8;
    amountArray4[count2] = 1.0E-4F;
    amountArray5[count2] = floatValue;
    amountArray6[count2] = floatValue2 > 0.0F ? 0.005F : 0.008F;
    count2 = (count2 + 1) % 160;
  }

  public static void apply(float floatValue, float[] floatValues) {
    float floatValue2 = (float) Math.cos(floatValue);
    float floatValue3 = (float) Math.sin(floatValue);
    floatValues[0] = amount7 * floatValue2 + amount10 * floatValue3;
    floatValues[1] = amount8 * floatValue2 + amount11 * floatValue3;
    floatValues[2] = amount9 * floatValue2 + amount12 * floatValue3;
  }

  public static void apply2(float floatValue, float[] floatValues) {
    float floatValue2 = (float) Math.cos(floatValue);
    float floatValue3 = (float) Math.sin(floatValue);
    floatValues[0] = -amount7 * floatValue3 + amount10 * floatValue2;
    floatValues[1] = -amount8 * floatValue3 + amount11 * floatValue2;
    floatValues[2] = -amount9 * floatValue3 + amount12 * floatValue2;
  }

  public static void update5() {
    float amount82 = -0.05F + 0.3F * random.nextFloat();
    float floatValue = (float) Math.sqrt(Math.max(0.0F, 1.0F - amount82 * amount82));
    float floatValue2 = random.nextFloat() * (float) (Math.PI * 2);
    amount7 = floatValue * (float) Math.cos(floatValue2);
    amount8 = amount82;
    amount9 = floatValue * (float) Math.sin(floatValue2);
    float floatValue3 = random.nextFloat() * 2.0F - 1.0F;
    float floatValue4 = 0.55F + 0.45F * random.nextFloat();
    float floatValue5 = random.nextFloat() * 2.0F - 1.0F;
    float floatValue6 = floatValue3 * amount7 + floatValue4 * amount8 + floatValue5 * amount9;
    floatValue3 -= amount7 * floatValue6;
    floatValue4 -= amount8 * floatValue6;
    floatValue5 -= amount9 * floatValue6;
    float floatValue7 =
        (float)
            Math.sqrt(
                floatValue3 * floatValue3 + floatValue4 * floatValue4 + floatValue5 * floatValue5);
    if (floatValue7 < 1.0E-4F) {
      amount6 = 1.0F;
    } else {
      amount10 = floatValue3 / floatValue7;
      amount11 = floatValue4 / floatValue7;
      amount12 = floatValue5 / floatValue7;
      amount13 = 1.9F + 0.8F * random.nextFloat();
      amount14 = 0.0F;
      amount15 = 0.0F;
      enabled = true;
      enabled2 = false;
      amount18 = 1.8F + 2.5F * random.nextFloat();
      amount23 = 0.0F;
      amount19 = 0.0F;
      amount20 = 0.0F;
      amount21 = 2.5F + 3.0F * random.nextFloat();
    }
  }
}

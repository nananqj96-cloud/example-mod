package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector4f;
import bunnythekenzo.client.render.FontManager;

public final class WorldLabelRenderer {
  public static final int count = 1024;
  public static final float amount = 0.9F;
  public static final float amount2 = 2.5F;
  public static final float amount3 = 0.22F;
  public static final float amount4 = 1.8F;
  public static final float amount5 = 2.0F;
  public static final float amount6 = 0.2F;
  public static final float amount7 = 24.0F;
  public static final float amount8 = 8.0F;
  public static final float amount9 = 0.15F;
  public static final float amount10 = 0.05F;
  public static final float amount11 = 0.05F;
  public static final double[] valueArray = new double[1024];
  public static final double[] valueArray2 = new double[1024];
  public static final double[] valueArray3 = new double[1024];
  public static final int[] countArray = new int[1024];
  public static final float[] amountArray = new float[1024];
  public static final float[] amountArray2 = new float[1024];
  public static final Vector4f vector4f = new Vector4f();
  public static int count2;

  public static void update() {
    count2 = 0;
  }

  public static void render(
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      int intValue,
      float floatValue,
      float floatValue2) {
    if (count2 < 1024
        && intValue >>> 24 != 0
        && Double.isFinite(doubleValue)
        && Double.isFinite(doubleValue2)
        && Double.isFinite(doubleValue3)) {
      valueArray[count2] = doubleValue;
      valueArray2[count2] = doubleValue2;
      valueArray3[count2] = doubleValue3;
      countArray[count2] = intValue;
      amountArray[count2] = floatValue;
      amountArray2[count2] = floatValue2;
      count2++;
    }
  }

  public static boolean test(MinecraftClient minecraftClient) {
    return count2 > 0
        && FramebufferSupport.isEnabled()
        && minecraftClient.player != null
        && minecraftClient.world != null
        && (minecraftClient.currentScreen == null
            || minecraftClient.currentScreen instanceof ChatScreen);
  }

  public static void render(
      FontManager fontManager,
      MinecraftClient minecraftClient,
      float floatValue,
      float floatValue2) {
    Vec3d vec3d = FramebufferSupport.getVec3d();
    float floatValue3 = floatValue * 0.5F;
    float floatValue4 = floatValue2 * 0.5F;
    float value = FramebufferSupport.computeFloat();
    float float2 = FramebufferSupport.computeFloat2();
    float floatValue5 = value > 1.0E-6F ? 1.0F / value : 0.0F;
    float floatValue6 = float2 > 1.0E-6F ? 1.0F / float2 : 0.0F;

    for (int index = 0; index < count2; index++) {
      double doubleValue = valueArray[index] - vec3d.x;
      double doubleValue2 = valueArray2[index] - vec3d.y;
      double doubleValue3 = valueArray3[index] - vec3d.z;
      float floatValue7 = 1.0F;
      float floatValue8 = amountArray2[index];
      if (floatValue8 > 0.0F) {
        double doubleValue4 =
            Math.sqrt(
                doubleValue * doubleValue
                    + doubleValue2 * doubleValue2
                    + doubleValue3 * doubleValue3);
        if (doubleValue4 >= floatValue8) {
          continue;
        }

        double doubleValue5 = floatValue8 * 0.85F;
        if (doubleValue4 > doubleValue5) {
          floatValue7 = (float) ((floatValue8 - doubleValue4) / (floatValue8 - doubleValue5));
        }
      }

      vector4f.set((float) doubleValue, (float) doubleValue2, (float) doubleValue3, 1.0F);
      vector4f.mul(FramebufferSupport.getMatrix4f2());
      float floatValue9 = vector4f.x();
      float floatValue10 = vector4f.y();
      float floatValue11 = vector4f.z();
      vector4f.mul(FramebufferSupport.getMatrix4f3());
      float floatValue12 = 0.0F;
      float floatValue13 = 0.0F;
      boolean flag = false;
      if (vector4f.w() > 0.05F) {
        float floatValue14 = 1.0F / vector4f.w();
        float floatValue15 = (vector4f.x() * floatValue14 * 0.5F + 0.5F) * floatValue;
        float floatValue16 = (1.0F - (vector4f.y() * floatValue14 * 0.5F + 0.5F)) * floatValue2;
        if (floatValue15 >= 8.0F
            && floatValue15 <= floatValue - 8.0F
            && floatValue16 >= 8.0F
            && floatValue16 <= floatValue2 - 8.0F) {
          floatValue12 = floatValue15;
          floatValue13 = floatValue16;
          flag = true;
        }
      }

      if (!flag) {
        float floatValue17 = vector4f.x() * floatValue;
        float floatValue18 = -vector4f.y() * floatValue2;
        float floatValue19 =
            (float) Math.sqrt(floatValue17 * floatValue17 + floatValue18 * floatValue18);
        if (floatValue19 < 0.001F) {
          floatValue17 = 0.0F;
          floatValue18 = 1.0F;
        } else {
          floatValue17 /= floatValue19;
          floatValue18 /= floatValue19;
        }

        float MAX_VALUE = Float.MAX_VALUE;
        if (floatValue17 > 0.0F) {
          MAX_VALUE = Math.min(Float.MAX_VALUE, (floatValue - 8.0F - floatValue3) / floatValue17);
        } else if (floatValue17 < 0.0F) {
          MAX_VALUE = Math.min(Float.MAX_VALUE, (8.0F - floatValue3) / floatValue17);
        }

        if (floatValue18 > 0.0F) {
          MAX_VALUE = Math.min(MAX_VALUE, (floatValue2 - 8.0F - floatValue4) / floatValue18);
        } else if (floatValue18 < 0.0F) {
          MAX_VALUE = Math.min(MAX_VALUE, (8.0F - floatValue4) / floatValue18);
        }

        if (MAX_VALUE == Float.MAX_VALUE) {
          continue;
        }

        floatValue12 = floatValue3 + floatValue17 * MAX_VALUE;
        floatValue13 = floatValue4 + floatValue18 * MAX_VALUE;
        float floatValue20 =
            (float)
                Math.sqrt(
                    floatValue9 * floatValue9
                        + floatValue10 * floatValue10
                        + floatValue11 * floatValue11);
        if (floatValue20 > 1.0E-4F && floatValue5 > 0.0F && floatValue6 > 0.0F) {
          float floatValue21 = Math.max(-1.0F, Math.min(1.0F, -floatValue11 / floatValue20));
          float floatValue22 = (float) Math.acos(floatValue21);
          float floatValue23 = (floatValue12 / floatValue * 2.0F - 1.0F) * floatValue5;
          float floatValue24 = (1.0F - floatValue13 / floatValue2 * 2.0F) * floatValue6;
          float floatValue25 =
              (float)
                  Math.atan(Math.sqrt(floatValue23 * floatValue23 + floatValue24 * floatValue24));
          float floatValue26 = (float) Math.PI - floatValue25;
          if (floatValue26 > 0.001F && floatValue22 > floatValue25) {
            float floatValue27 =
                (float) Math.sqrt(Math.min(1.0F, (floatValue22 - floatValue25) / floatValue26));
            float floatValue28 = 1.0F - floatValue27 * 0.95F;
            floatValue12 = floatValue3 + floatValue17 * MAX_VALUE * floatValue28;
            floatValue13 = floatValue4 + floatValue18 * MAX_VALUE * floatValue28;
          }
        }
      }

      float floatValue29 = floatValue12 - floatValue3;
      float floatValue30 = floatValue13 - floatValue4;
      float floatValue31 =
          (float) Math.sqrt(floatValue29 * floatValue29 + floatValue30 * floatValue30);
      floatValue7 *= Math.min(1.0F, Math.max(0.0F, floatValue31 / 24.0F));
      int intValue = (int) ((countArray[index] >>> 24) * floatValue7);
      if (intValue > 2) {
        int intValue2 = countArray[index] & 16777215;
        int intValue3 = intValue << 24 | intValue2;
        int intValue4 = (int) (intValue * 0.22F) << 24 | intValue2;
        float floatValue32 = 0.9F * amountArray[index];
        fontManager.render2(
            floatValue3, floatValue4, floatValue12, floatValue13, floatValue32 * 2.5F, intValue4);
        fontManager.render2(
            floatValue3, floatValue4, floatValue12, floatValue13, floatValue32, intValue3);
        if (flag) {
          float floatValue33 = 1.8F * Math.max(1.0F, amountArray[index]);
          fontManager.render2(
              floatValue12,
              floatValue13,
              floatValue33 * 2.0F,
              (int) (intValue * 0.2F) << 24 | intValue2);
          fontManager.render2(floatValue12, floatValue13, floatValue33, intValue3);
        }
      }
    }

    count2 = 0;
  }
}

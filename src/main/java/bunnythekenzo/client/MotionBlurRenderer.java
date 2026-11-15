package bunnythekenzo.client;

import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import bunnythekenzo.client.module.MotionBlurModule;
import bunnythekenzo.client.render.FontManager;

public final class MotionBlurRenderer {
  public static final double value = 12.0;
  public static int count = -1;
  public static int count2 = -1;
  public static int count3;
  public static int count4;
  public static boolean enabled;
  public static long timestamp;
  public static double value2;
  public static int count5;
  public static float amount;

  public static void apply(
      FontManager fontManager, int intValue, int intValue2, MotionBlurModule motionBlurModule) {
    if (intValue > 0 && intValue2 > 0) {
      long timestamp2 = System.nanoTime();
      double doubleValue = timestamp == 0L ? 0.0 : (timestamp2 - timestamp) / 1.0E9;
      timestamp = timestamp2;
      double doubleValue2 = 1.0 - Math.exp(-12.0 * Math.max(0.0, doubleValue));
      value2 = value2 + (1.0 - value2) * doubleValue2;
      if (value2 > 0.999) {
        value2 = 1.0;
      }

      if (count == -1 || count3 != intValue || count4 != intValue2) {
        apply(fontManager, intValue, intValue2);
        enabled = false;
      }

      double value3 = motionBlurModule.computeDouble();
      if ((Boolean) motionBlurModule.fpsCompensatedSetting.getValue() && doubleValue > 0.0) {
        value3 = Math.pow(value3, doubleValue * 60.0);
      }

      double doubleValue3 = Math.clamp(value3 * value2, 0.0, 0.97);
      amount = (float) doubleValue3;
      if (!enabled) {
        apply(intValue, intValue2);
        enabled = true;
        count5++;
      } else {
        int intValue3 = -1;
        float value4 = motionBlurModule.computeFloat();
        if (value4 > 0.0F) {
          intValue3 =
              ArgbColors.lerp(-1, ArgbColors.withAlpha(motionBlurModule.computeInt(), 255), value4);
        }

        fontManager.render(intValue, intValue2, 1.0F);
        fontManager.update2();
        fontManager.apply3((float) doubleValue3);
        fontManager.render(count2, 0.0F, 0.0F, (float) intValue, (float) intValue2, intValue3);
        fontManager.update3();
        fontManager.update();
        apply(intValue, intValue2);
        count5++;
      }
    }
  }

  public static void apply(FontManager fontManager, int count32, int count42) {
    long timestamp2 = fontManager.getTimestamp();
    if (count2 > 0) {
      NanoVG.nvgDeleteImage(timestamp2, count2);
      count2 = -1;
    }

    if (count != -1) {
      GL33C.glDeleteTextures(count);
    }

    count = GL33C.glGenTextures();
    GL33C.glActiveTexture(33984);
    GL33C.glBindTexture(3553, count);
    GL33C.glTexImage2D(3553, 0, 32856, count32, count42, 0, 6408, 5121, (ByteBuffer) null);
    GL33C.glTexParameteri(3553, 10241, 9729);
    GL33C.glTexParameteri(3553, 10240, 9729);
    GL33C.glTexParameteri(3553, 10242, 33071);
    GL33C.glTexParameteri(3553, 10243, 33071);
    GL33C.glTexParameteri(3553, 36421, 1);
    count3 = count32;
    count4 = count42;
    count2 = NanoVGGL3.nvglCreateImageFromHandle(timestamp2, count, count32, count42, 65544);
  }

  public static void apply(int intValue, int intValue2) {
    GL33C.glActiveTexture(33984);
    GL33C.glBindTexture(3553, count);
    GL33C.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, intValue, intValue2);
  }

  public static void update() {
    enabled = false;
    value2 = 0.0;
    timestamp = 0L;
  }

  public static int getCount5() {
    return count5;
  }

  public static float getAmount() {
    return amount;
  }
}

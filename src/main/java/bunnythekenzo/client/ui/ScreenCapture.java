package bunnythekenzo.client.ui;

import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.render.FontManager;

public final class ScreenCapture {
  public static final int count = 640;
  public static final int count2 = 360;
  public static final long timestamp = 200000000L;
  public static int count3 = -1;
  public static int count4 = -1;
  public static int count5 = -1;
  public static long timestamp2;
  public static boolean enabled;
  public static boolean enabled2;

  public static boolean isEnabled() {
    return enabled;
  }

  public static void apply(int intValue, int intValue2, int intValue3) {
    if (!enabled2) {
      long timestamp22 = System.nanoTime();
      if (!enabled || timestamp22 - timestamp2 >= 200000000L) {
        timestamp2 = timestamp22;

        try {
          if (count3 == -1 && !test()) {
            return;
          }

          int intValue4 = GL33C.glGetInteger(36006);
          int intValue5 = GL33C.glGetInteger(36010);
          GL33C.glBindFramebuffer(36008, intValue);
          GL33C.glBindFramebuffer(36009, count4);
          GL33C.glBlitFramebuffer(0, 0, intValue2, intValue3, 0, 360, 640, 0, 16384, 9729);
          int[] intValues = new int[4];
          GL33C.glGetIntegerv(3107, intValues);
          GL33C.glColorMask(false, false, false, true);
          GL33C.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
          GL33C.glClear(16384);
          GL33C.glColorMask(
              intValues[0] != 0, intValues[1] != 0, intValues[2] != 0, intValues[3] != 0);
          GL33C.glBindFramebuffer(36008, intValue5);
          GL33C.glBindFramebuffer(36009, intValue4);
          enabled = true;
        } catch (Throwable failure) {
          enabled2 = true;
          ClientInitializer.logger.warn(
              "Screen capture unavailable; the HUD preview will be plain", failure);
        }
      }
    }
  }

  public static boolean test() {
    count3 = GL33C.glGenTextures();
    GL33C.glBindTexture(3553, count3);
    GL33C.glTexImage2D(3553, 0, 32856, 640, 360, 0, 6408, 5121, (ByteBuffer) null);
    GL33C.glTexParameteri(3553, 10241, 9729);
    GL33C.glTexParameteri(3553, 10240, 9729);
    GL33C.glTexParameteri(3553, 10242, 33071);
    GL33C.glTexParameteri(3553, 10243, 33071);
    GL33C.glBindTexture(3553, 0);
    count4 = GL33C.glGenFramebuffers();
    int intValue = GL33C.glGetInteger(36006);
    GL33C.glBindFramebuffer(36009, count4);
    GL33C.glFramebufferTexture2D(36009, 36064, 3553, count3, 0);
    boolean flag = GL33C.glCheckFramebufferStatus(36009) == 36053;
    GL33C.glBindFramebuffer(36009, intValue);
    if (!flag) {
      enabled2 = true;
      ClientInitializer.logger.warn("Screen capture framebuffer incomplete; preview will be plain");
    }

    return flag;
  }

  public static int computeInt() {
    if (enabled && !enabled2) {
      if (count5 <= 0) {
        count5 =
            NanoVGGL3.nvglCreateImageFromHandle(
                FontManager.getFontManager().getTimestamp(), count3, 640, 360, 0);
      }

      return count5;
    } else {
      return -1;
    }
  }

  public static float computeFloat() {
    return 1.7777778F;
  }

  public static void ensureInitialized() {}
}

package bunnythekenzo.client;

import java.nio.ByteBuffer;
import java.util.Random;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.render.FontManager;

public final class MenuBackdropRenderer {
  public static final int count = 128;
  public static final float amount = 128.0F;
  public static final float amount2 = 0.03F;
  public static final long timestamp = 41000000L;
  public static int count2 = -1;
  public static boolean enabled;

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    GuiPreferences guiPreferences = GuiPreferences.getGuiPreferences();
    float floatValue8 = guiPreferences.computeFloat2() * floatValue7;
    if (floatValue8 > 0.002F) {
      float floatValue9 = floatValue3 + floatValue5 / 2.0F;
      float floatValue10 = floatValue4 + floatValue6 / 2.0F;
      float floatValue11 = Math.max(floatValue5, floatValue6) * 0.52F;
      float floatValue12 = (float) Math.hypot(floatValue, floatValue2) * 0.6F;
      fontManager.render2(
          0.0F,
          0.0F,
          floatValue,
          floatValue2,
          floatValue9,
          floatValue10,
          floatValue11,
          floatValue12,
          ArgbColors.withOpacity(-16317174, floatValue8),
          ArgbColors.withOpacity(-16317174, floatValue8 * 0.22F));
    }

    if (guiPreferences.filmGrainControl.getValue()) {
      render(fontManager, floatValue, floatValue2, floatValue7);
    }
  }

  public static void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    int intValue = computeInt(fontManager);
    if (intValue > 0) {
      long longValue = System.nanoTime() / 41000000L;
      float floatValue4 = (float) (longValue * 37L % 128L);
      float floatValue5 = (float) (longValue * 61L % 128L);
      fontManager.render(
          intValue,
          0.0F,
          0.0F,
          floatValue,
          floatValue2,
          128.0F,
          floatValue4,
          floatValue5,
          0.03F * floatValue3);
    }
  }

  public static int computeInt(FontManager fontManager) {
    if (count2 <= 0 && !enabled) {
      ByteBuffer byteBuffer = MemoryUtil.memAlloc(65536);

      try {
        Random random = new Random(6767L);

        for (int index = 0; index < 16384; index++) {
          int intValue = random.nextInt(256);
          byteBuffer.put((byte) -1).put((byte) -1).put((byte) -1).put((byte) intValue);
        }

        byteBuffer.flip();
        count2 = fontManager.computeInt(128, 128, byteBuffer);
        if (count2 <= 0) {
          enabled = true;
        }
      } catch (Throwable failure) {
        enabled = true;
      } finally {
        MemoryUtil.memFree(byteBuffer);
      }

      return count2;
    } else {
      return count2;
    }
  }
}

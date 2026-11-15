package bunnythekenzo.client;

import java.nio.ByteBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.render.FontManager;

public final class TextureImageCache {
  public static GlTexture glTexture;
  public static int count;
  public static long timestamp;
  public static final long timestamp2 = 1000000000L;

  public static int computeInt(Identifier identifier) {
    ProfileDefaults.ensureInitialized();

    AbstractTexture texture =
        MinecraftClient.getInstance().getTextureManager().getTexture(identifier);
    if (texture != null && texture.getGlTexture() instanceof GlTexture glTexture2) {
      long timestamp3 = System.nanoTime();
      if (glTexture == glTexture2 && count > 0 && timestamp3 - timestamp < timestamp2) {
        return count;
      }

      int width = glTexture2.getWidth(0);
      int height = glTexture2.getHeight(0);
      if (width >= 1 && height >= 1 && width <= 2048 && height <= 2048) {
        int intValue = GL33C.glGetInteger(32873);

        int intValue2 = GL33C.glGetInteger(35053);

        int intValue3 = GL33C.glGetInteger(35055);

        int[] intValues = new int[] {3333, 3330, 3332, 3331, 3317, 3314, 3316, 3315};
        int[] intValues2 = new int[intValues.length];
        ByteBuffer data = MemoryUtil.memAlloc(width * height * 4);
        boolean flag = false  ;

        int intValue4;
        try {
          flag = true;

          for (int index = 0; index < intValues.length; index++) {
            intValues2[index] = GL33C.glGetInteger(intValues[index]);
            GL33C.glPixelStorei(intValues[index], index % 4 == 0 ? 1 : 0);
          }

          GL33C.glBindBuffer(35051, 0);

          GL33C.glBindBuffer(35052, 0);

          GL33C.glBindTexture(3553, glTexture2.getGlId());

          GL33C.glGetTexImage(3553, 0, 6408, 5121, data);
          int count2 =
              NanoVG.nvgCreateImageRGBA(
                  FontManager.getFontManager().getTimestamp(), width, height, 32, data);
          if (count2 > 0) {
            update();

            count = count2;

            glTexture = glTexture2;
            timestamp = timestamp3;
            intValue4 =
                NanoVGGL3.nvglImageHandle(FontManager.getFontManager().getTimestamp(), count);
            GL33C.glBindTexture(3553, intValue4);

            GL33C.glTexParameteri(3553, 10241, 9728);

            GL33C.glTexParameteri(3553, 10240, 9728);

            GL33C.glTexParameteri(3553, 33085, 0);
          }

          intValue4 = count;

          flag = false;
        } finally {
          if (flag) {

            GL33C.glBindTexture(3553, intValue);

            GL33C.glBindBuffer(35051, intValue2);

            GL33C.glBindBuffer(35052, intValue3);

            for (int index2 = 0; index2 < intValues.length; index2++) {
              GL33C.glPixelStorei(intValues[index2], intValues2[index2]);
            }

            MemoryUtil.memFree(data);
          }
        }

        GL33C.glBindTexture(3553, intValue);

        GL33C.glBindBuffer(35051, intValue2);

        GL33C.glBindBuffer(35052, intValue3);

        for (int index3 = 0; index3 < intValues.length; index3++) {
          GL33C.glPixelStorei(intValues[index3], intValues2[index3]);
        }

        MemoryUtil.memFree(data);
        return intValue4;
      } else {
        return -1;
      }
    } else {
      return -1;
    }
  }

  public static void update() {
    ProfileDefaults.ensureInitialized();
    if (count > 0 && FontManager.getInstance() != null) {
      NanoVG.nvgDeleteImage(FontManager.getInstance().getTimestamp(), count);
    }

    count = 0;
    glTexture = null;
  }
}

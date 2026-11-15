package bunnythekenzo.client.render;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.nanovg.NSVGImage;
import org.lwjgl.nanovg.NanoSVG;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.ClientInitializer;

public final class IconCache {
  public static final int count = 96;
  public static final Map map = new HashMap();

  public static int get(String text) {
    return (java.lang.Integer)
        (map.computeIfAbsent(
            "svg:" + text,
            value -> {
              return computeInt2("assets/bunnythekenzo/icons/" + text + ".svg");
            }));
  }

  public static int getPng(String text) {
    return (java.lang.Integer)
        (map.computeIfAbsent(
            "png:" + text,
            value -> {
              return computeInt("assets/bunnythekenzo/icons/" + text + ".png");
            }));
  }

  public static byte[] getByteValueArray(String text) {
    try (InputStream resourceAsStream =
        IconCache.class.getClassLoader().getResourceAsStream(text)) {
      if (resourceAsStream == null) {
        ClientInitializer.logger.warn("Icon resource missing: {}", text);
        return null;
      } else {
        return resourceAsStream.readAllBytes();
      }
    } catch (Exception failure) {
      ClientInitializer.logger.error("Failed to read icon {}", text, failure);
      return null;
    }
  }

  public static int computeInt(String text) {
    byte[] bytes = getByteValueArray(text);
    if (bytes == null) {
      return -1;
    }

    ByteBuffer byteBuffer = MemoryUtil.memAlloc(bytes.length);

    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      byteBuffer.put(bytes).flip();
      IntBuffer intBuffer = memoryStack.mallocInt(1);
      IntBuffer intBuffer2 = memoryStack.mallocInt(1);
      IntBuffer intBuffer3 = memoryStack.mallocInt(1);
      STBImage.stbi_set_flip_vertically_on_load(false);
      ByteBuffer data =
          STBImage.stbi_load_from_memory(byteBuffer, intBuffer, intBuffer2, intBuffer3, 4);
      if (data != null) {
        try {
          return NanoVG.nvgCreateImageRGBA(
              FontManager.getFontManager().getTimestamp(),
              intBuffer.get(0),
              intBuffer2.get(0),
              0,
              data);
        } finally {
          STBImage.stbi_image_free(data);
        }
      }

      ClientInitializer.logger.warn("STB rejected {}: {}", text, STBImage.stbi_failure_reason());
    } finally {
      MemoryUtil.memFree(byteBuffer);
    }

    return -1;
  }

  public static int computeInt2(String text) {
    byte[] bytes = getByteValueArray(text);
    if (bytes == null) {
      return -1;
    }

    String text2 = new String(bytes, StandardCharsets.UTF_8);
    text2 =
        text2
            .replaceAll("#[0-9a-fA-F]{6}", "#ffffff")
            .replaceAll("#[0-9a-fA-F]{3}\\b", "#fff")
            .replace("currentColor", "#ffffff")
            .replace("\"black\"", "\"white\"");
    ByteBuffer input = MemoryUtil.memUTF8(text2, true);
    ByteBuffer units = MemoryUtil.memASCII("px");
    NSVGImage image = null;
    long r = 0L;
    ByteBuffer dst = null;
    boolean flag = false  ;

    label123:
    {
      label124:
      {
        int intValue;
        try {
          flag = true;
          image = NanoSVG.nsvgParse(input, units, 96.0F);
          if (image == null) {
            flag = false;
            break label124;
          }

          r = NanoSVG.nsvgCreateRasterizer();
          float scale = 96.0F / Math.max(image.width(), image.height());
          dst = MemoryUtil.memAlloc(36864);
          NanoSVG.nsvgRasterize(r, image, 0.0F, 0.0F, scale, dst, 96, 96, 384);
          intValue =
              NanoVG.nvgCreateImageRGBA(
                  FontManager.getFontManager().getTimestamp(), 96, 96, 0, dst);
          flag = false;
        } catch (Exception failure) {
          ClientInitializer.logger.error("Failed to rasterize icon {}", text, failure);
          flag = false;
          break label123;
        } finally {
          if (flag) {
            if (dst != null) {
              MemoryUtil.memFree(dst);
            }

            if (r != 0L) {
              NanoSVG.nsvgDeleteRasterizer(r);
            }

            if (image != null) {
              NanoSVG.nsvgDelete(image);
            }

            MemoryUtil.memFree(input);
            MemoryUtil.memFree(units);
          }
        }

        if (dst != null) {
          MemoryUtil.memFree(dst);
        }

        if (r != 0L) {
          NanoSVG.nsvgDeleteRasterizer(r);
        }

        if (image != null) {
          NanoSVG.nsvgDelete(image);
        }

        MemoryUtil.memFree(input);
        MemoryUtil.memFree(units);
        return intValue;
      }

      if (image != null) {
        NanoSVG.nsvgDelete(image);
      }

      MemoryUtil.memFree(input);
      MemoryUtil.memFree(units);
      return -1;
    }

    if (dst != null) {
      MemoryUtil.memFree(dst);
    }

    if (r != 0L) {
      NanoSVG.nsvgDeleteRasterizer(r);
    }

    if (image != null) {
      NanoSVG.nsvgDelete(image);
    }

    MemoryUtil.memFree(input);
    MemoryUtil.memFree(units);
    return -1;
  }
}

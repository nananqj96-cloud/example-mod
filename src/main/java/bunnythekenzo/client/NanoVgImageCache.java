package bunnythekenzo.client;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.render.FontManager;

public final class NanoVgImageCache {
  public static final Map map = new HashMap();
  public static final Map map2 = new HashMap();
  public static final Map map3 = new HashMap();
  public static int count = -1;
  public static int count2 = -1;
  public static Path path;
  public static final String[] stringArray =
      new String[] {
        "_pressure_plate",
        "_hanging_sign",
        "_fence_gate",
        "_wall_sign",
        "_trapdoor",
        "_stairs",
        "_button",
        "_fence",
        "_slab",
        "_wall",
        "_door",
        "_sign",
        "_bars",
        "_boat",
        "_chest_boat",
        "_raft"
      };

  public static int computeInt(Path path2, int count22) {
    if (count22 == count2 && path2.equals(path)) {
      return count;
    }

    long timestamp = FontManager.getFontManager().getTimestamp();
    if (count > 0) {
      NanoVG.nvgDeleteImage(timestamp, count);
      count = -1;
    }

    count2 = count22;
    path = path2;

    try {
      byte[] allBytes = Files.readAllBytes(path2);
      ByteBuffer data = MemoryUtil.memAlloc(allBytes.length);

      try {
        data.put(allBytes).flip();
        count = NanoVG.nvgCreateImageMem(timestamp, 0, data);
      } finally {
        MemoryUtil.memFree(data);
      }
    } catch (Exception failure) {
      count = -1;
    }

    return count;
  }

  public static int wrapGlTexture(Identifier identifier, int w, int h) {
    AbstractTexture texture =
        MinecraftClient.getInstance().getTextureManager().getTexture(identifier);
    if (texture != null && texture.getGlTexture() instanceof GlTexture glTexture) {
      int glId = glTexture.getGlId();
      return (java.lang.Integer)
          (map3.computeIfAbsent(
              glId,
              value -> {
                return NanoVGGL3.nvglCreateImageFromHandle(
                    FontManager.getFontManager().getTimestamp(),
                    (java.lang.Integer) (value),
                    w,
                    h,
                    65536);
              }));
    } else {
      return -1;
    }
  }

  public static void render(
      FontManager fontManager,
      int intValue,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11) {
    if (intValue > 0) {
      float floatValue12 = floatValue9 / (floatValue5 - floatValue3);
      float floatValue13 = floatValue10 / (floatValue6 - floatValue4);
      fontManager.render(
          intValue,
          floatValue7 - floatValue3 * floatValue12,
          floatValue8 - floatValue4 * floatValue13,
          floatValue * floatValue12,
          floatValue2 * floatValue13,
          floatValue7,
          floatValue8,
          floatValue9,
          floatValue10,
          floatValue11);
    }
  }

  public static int computeInt(Identifier identifier) {
    Integer intValue = (Integer) map.get(identifier);
    if (intValue != null) {
      return intValue;
    }

    int intValue2 = computeInt5(identifier);
    map.put(identifier, intValue2);
    return intValue2;
  }

  public static int computeInt2(Identifier identifier) {
    return computeInt(
        Identifier.of(identifier.getNamespace(), "textures/item/" + identifier.getPath() + ".png"));
  }

  public static int computeInt3(Identifier identifier) {
    Integer intValue = (Integer) map2.get(identifier);
    if (intValue != null) {
      return intValue;
    }

    String namespace = identifier.getNamespace();
    String path2 = identifier.getPath();
    int intValue2 = computeInt(namespace, path2);
    if (intValue2 <= 0) {
      for (String text : (java.lang.Iterable<String>) (java.lang.Iterable<?>) (getList(path2))) {
        intValue2 = computeInt(namespace, text);
        if (intValue2 > 0) {
          break;
        }
      }
    }

    map2.put(identifier, intValue2);
    return intValue2;
  }

  public static int computeInt(String text, String text2) {
    for (String text3 :
        new String[] {
          "textures/item/" + text2 + ".png",
          "textures/block/" + text2 + ".png",
          "textures/block/" + text2 + "_side.png",
          "textures/block/" + text2 + "_front.png",
          "textures/block/" + text2 + "_top.png",
          "textures/block/" + text2 + "_still.png",
          "textures/block/" + text2 + "_0.png"
        }) {
      int intValue = computeInt(Identifier.of(text, text3));
      if (intValue > 0) {
        return intValue;
      }
    }

    return -1;
  }

  public static List getList(String text) {
    if (text.endsWith("_wood")) {
      return List.of(text.substring(0, text.length() - "_wood".length()) + "_log");
    }

    if (text.endsWith("_hyphae")) {
      return List.of(text.substring(0, text.length() - "_hyphae".length()) + "_stem");
    }

    for (String text2 : stringArray) {
      if (text.endsWith(text2) && text.length() > text2.length()) {
        String text3 = text.substring(0, text.length() - text2.length());
        return List.of(text3, text3 + "_planks");
      }
    }

    return List.of();
  }

  public static int computeInt4(Identifier identifier) {
    return computeInt(
        Identifier.of(
            identifier.getNamespace(), "textures/mob_effect/" + identifier.getPath() + ".png"));
  }

  public static int computeInt5(Identifier identifier) {
    var resource = MinecraftClient.getInstance().getResourceManager().getResource(identifier);
    if (resource.isEmpty()) {
      return -1;
    }

    try (InputStream inputStream = ((Resource) resource.get()).getInputStream()) {
      byte[] allBytes = inputStream.readAllBytes();
      ByteBuffer data = MemoryUtil.memAlloc(allBytes.length);

      try {
        data.put(allBytes).flip();
        return NanoVG.nvgCreateImageMem(FontManager.getFontManager().getTimestamp(), 32, data);
      } finally {
        MemoryUtil.memFree(data);
      }
    } catch (Exception failure) {
      ClientInitializer.logger.warn("Could not load the texture {}", identifier, failure);
      return -1;
    }
  }
}

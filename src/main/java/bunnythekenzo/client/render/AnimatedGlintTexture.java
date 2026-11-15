package bunnythekenzo.client.render;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GlintColorSampler;
import bunnythekenzo.client.GlintStyle;
import bunnythekenzo.client.module.CustomGlintModule;
import bunnythekenzo.client.module.ModuleManager;

public final class AnimatedGlintTexture {
  public static final Identifier identifier =
      Identifier.of("bunnythekenzo", "dynamic/custom_glint");
  public static final int count = 192;
  public static NativeImageBackedTexture nativeImageBackedTexture;
  public static AnimatedGlintTextureData animatedGlintTextureData;
  public static long timestamp;
  public static ExecutorService executorService;
  public static Future future;
  public static int[] countArray = new int[36864];
  public static boolean enabled;

  public static void tick() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null && moduleManager.customGlintModule.test()) {
      CustomGlintModule customGlintModule = moduleManager.customGlintModule;
      GlintStyle glintStyle = customGlintModule.glintStyleSetting.getGlintStyle();
      AnimatedGlintTextureData animatedGlintTextureData2 =
          new AnimatedGlintTextureData(
              glintStyle,
              customGlintModule.getClientData2(glintStyle),
              (long) Math.floor(customGlintModule.computeDouble() * 30.0),
              glintStyle == GlintStyle.DEFAULT ? customGlintModule.computeInt2() : 0);
      if (nativeImageBackedTexture == null) {
        nativeImageBackedTexture =
            new NativeImageBackedTexture("bunnythekenzo animated glint", 192, 192, false);
        nativeImageBackedTexture.getImage().fillRect(0, 0, 192, 192, glintStyle.dark);
        nativeImageBackedTexture.upload();
        MinecraftClient.getInstance()
            .getTextureManager()
            .registerTexture(identifier, nativeImageBackedTexture);
      }

      if (!enabled) {
        if (future != null) {
          if (!future.isDone()) {
            return;
          }

          try {
            AnimatedGlintTextureData animatedGlintTextureData3 =
                (AnimatedGlintTextureData) future.get();
            if (animatedGlintTextureData3.test(animatedGlintTextureData2)) {
              NativeImage image = nativeImageBackedTexture.getImage();

              for (int index = 0; index < 192; index++) {
                for (int index2 = 0; index2 < 192; index2++) {
                  image.setColorArgb(index2, index, countArray[index * 192 + index2]);
                }
              }

              nativeImageBackedTexture.upload();
              animatedGlintTextureData = animatedGlintTextureData3;
            }
          } catch (Exception failure) {
            if (failure instanceof InterruptedException) {
              Thread.currentThread().interrupt();
            }

            enabled = true;
            ClientInitializer.logger.error("Custom Glint texture worker failed", failure);
          }

          future = null;
        }

        if (!enabled && !animatedGlintTextureData2.equals(animatedGlintTextureData)) {
          long longValue = System.nanoTime();
          if (!animatedGlintTextureData2.test(animatedGlintTextureData) || longValue >= timestamp) {
            if (executorService == null) {
              executorService =
                  Executors.newSingleThreadExecutor(
                      runnable -> {
                        Thread thread = new Thread(runnable, "bunnythekenzo-glint-texture");
                        thread.setDaemon(true);
                        thread.setPriority(1);
                        return thread;
                      });
            }

            int[] intValues = countArray;
            future =
                executorService.submit(
                    () -> {
                      return getAnimatedGlintTextureData(animatedGlintTextureData2, intValues);
                    });
            timestamp = longValue + 33333333L;
          }
        }
      }
    } else {
      update();
    }
  }

  public static AnimatedGlintTextureData getAnimatedGlintTextureData(
      AnimatedGlintTextureData animatedGlintTextureData2, int[] intValues) {
    for (int index = 0; index < 192; index++) {
      if (Thread.currentThread().isInterrupted()) {
        throw new CancellationException();
      }

      for (int index2 = 0; index2 < 192; index2++) {
        int value =
            GlintColorSampler.computeInt(
                animatedGlintTextureData2.glintStyle(),
                (index2 + 0.5F) / 192.0F,
                (index + 0.5F) / 192.0F,
                animatedGlintTextureData2.o_() / 30.0,
                1.0F,
                animatedGlintTextureData2.clientData2());
        if (animatedGlintTextureData2.glintStyle() == GlintStyle.DEFAULT) {
          int intValue = value & 0xFF;
          value =
              0xFF000000
                  | (animatedGlintTextureData2.count() >> 16 & 0xFF) * intValue / 255 << 16
                  | (animatedGlintTextureData2.count() >> 8 & 0xFF) * intValue / 255 << 8
                  | (animatedGlintTextureData2.count() & 0xFF) * intValue / 255;
        }

        intValues[index * 192 + index2] = value;
      }
    }

    return animatedGlintTextureData2;
  }

  public static boolean test() {
    CustomGlintModule customGlintModule = ClientInitializer.getModuleManager().customGlintModule;
    GlintStyle glintStyle = customGlintModule.glintStyleSetting.getGlintStyle();
    return animatedGlintTextureData != null
        && animatedGlintTextureData.glintStyle() == glintStyle
        && animatedGlintTextureData
            .clientData2()
            .equals(customGlintModule.getClientData2(glintStyle))
        && (glintStyle != GlintStyle.DEFAULT
            || animatedGlintTextureData.count() == customGlintModule.computeInt2());
  }

  public static void update() {
    if (future != null) {
      future.cancel(true);
      future = null;
      countArray = new int[36864];
    }
  }

  public static void update2() {
    update();
    if (executorService != null) {
      executorService.shutdownNow();
    }

    executorService = null;
    if (nativeImageBackedTexture != null) {
      MinecraftClient.getInstance().getTextureManager().destroyTexture(identifier);
    }

    nativeImageBackedTexture = null;
    animatedGlintTextureData = null;
    timestamp = 0L;
    enabled = false;
    GlintPipelines.update();
  }
}

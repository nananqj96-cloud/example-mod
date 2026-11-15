package bunnythekenzo.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

public final class SkySupport23 {
  public static GpuTexture gpuTexture;
  public static GpuTextureView gpuTextureView;
  public static boolean enabled;

  public static GpuTextureView begin() {
    if (!enabled) {
      ClientLifecycleEvents.CLIENT_STOPPING.register(
          minecraftClient -> {
            update();
          });
      enabled = true;
    }

    GpuTextureView outputColorTextureOverride = RenderSystem.outputColorTextureOverride;
    if (outputColorTextureOverride == null) {
      outputColorTextureOverride =
          MinecraftClient.getInstance().getFramebuffer().getColorAttachmentView();
    }

    int width = outputColorTextureOverride.getWidth(0);
    int height = outputColorTextureOverride.getHeight(0);
    if (gpuTexture == null
        || gpuTexture.getWidth(0) != width
        || gpuTexture.getHeight(0) != height) {
      update();
      gpuTexture =
          RenderSystem.getDevice()
              .createTexture(
                  "67 rocket object depth", 8, TextureFormat.DEPTH32, width, height, 1, 1);
      gpuTextureView = RenderSystem.getDevice().createTextureView(gpuTexture);
    }

    RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(gpuTexture, 1.0);
    GpuTextureView outputDepthTextureOverride = RenderSystem.outputDepthTextureOverride;
    RenderSystem.outputDepthTextureOverride = gpuTextureView;
    return outputDepthTextureOverride;
  }

  public static void update() {
    if (gpuTextureView != null) {
      gpuTextureView.close();
    }

    if (gpuTexture != null) {
      gpuTexture.close();
    }

    gpuTextureView = null;
    gpuTexture = null;
  }
}

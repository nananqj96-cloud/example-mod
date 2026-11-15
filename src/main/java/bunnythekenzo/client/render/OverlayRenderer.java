package bunnythekenzo.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.texture.GlTexture;
import org.lwjgl.opengl.GL33C;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.DeepscanNotifications;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.MotionBlurRenderer;
import bunnythekenzo.client.OpenGlStateSnapshot;
import bunnythekenzo.client.WorldLabelRenderer;
import bunnythekenzo.client.WorldSignalSupport;
import bunnythekenzo.client.hud.NotificationsHud;
import bunnythekenzo.client.module.CustomCrosshairModule;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.MotionBlurModule;
import bunnythekenzo.client.ui.ClientScreen;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.ScreenCapture;

public final class OverlayRenderer {
  public static int count = -1;
  public static boolean enabled;
  public static String string;

  public static float computeFloat() {
    MinecraftClient instance = MinecraftClient.getInstance();
    float floatValue = Math.max(1.0F, instance.getWindow().getFramebufferHeight() / 1080.0F);
    float floatValue2 = floatValue / GuiPreferences.getGuiPreferences().computeFloat3();
    if (instance.currentScreen instanceof PanelScreen) {
      floatValue2 =
          Math.min(
              floatValue2,
              Math.min(
                  instance.getWindow().getFramebufferWidth() / 824.0F,
                  instance.getWindow().getFramebufferHeight() / 514.0F));
    }

    return Math.max(0.1F, floatValue2);
  }

  public static boolean test() {
    MinecraftClient instance = MinecraftClient.getInstance();
    return instance.currentScreen instanceof ChatScreen && WorldSignalSupport.test3();
  }

  public static void update() {
    if (!enabled) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      boolean flag = instance.currentScreen instanceof ClientScreen;
      boolean flag2 = instance.world != null && !instance.options.hudHidden;
      boolean flag3 = flag2 && !flag && WorldSignalSupport.test3();
      boolean flag4 = instance.world != null && !instance.options.hudHidden && !flag;
      boolean flag5 = flag4 && WorldLabelRenderer.test(instance);
      MotionBlurModule motionBlurModule =
          moduleManager == null ? null : moduleManager.motionBlurModule;
      boolean flag6 =
          motionBlurModule != null
              && motionBlurModule.isEnabled()
              && instance.world != null
              && !flag;
      boolean flag7 = instance.world != null && !instance.options.hudHidden;
      if (flag || flag3 || flag5 || flag6 || flag7) {
        Framebuffer framebuffer = instance.getFramebuffer();
        if (framebuffer != null
            && framebuffer.getColorAttachment() instanceof GlTexture glTexture) {
          try {
            OpenGlStateSnapshot openGlStateSnapshot = OpenGlStateSnapshot.getOpenGlStateSnapshot();

            try {
              if (!test(glTexture, framebuffer.textureWidth, framebuffer.textureHeight)) {
                return;
              }

              FontManager fontManager = FontManager.getFontManager();
              if (flag6) {
                MotionBlurRenderer.apply(
                    fontManager,
                    framebuffer.textureWidth,
                    framebuffer.textureHeight,
                    motionBlurModule);
              }

              if (fontManager.test()) {
                apply(fontManager);
                if (flag3 && instance.currentScreen == null) {
                  ScreenCapture.apply(count, framebuffer.textureWidth, framebuffer.textureHeight);
                }

                float floatValue = computeFloat();
                float floatValue2 = framebuffer.textureWidth / floatValue;
                float floatValue3 = framebuffer.textureHeight / floatValue;
                if (flag) {
                  ((ClientScreen) (Object) (instance.currentScreen))
                      .render(
                          fontManager, computeFloat2(), computeFloat3(), floatValue2, floatValue3);
                }

                NotificationsHud notificationsHud = ClientInitializer.getNotificationsHud();
                DeepscanNotifications.apply(fontManager);
                fontManager.render(framebuffer.textureWidth, framebuffer.textureHeight, 1.0F);
                fontManager.update2();
                fontManager.apply(floatValue);
                if (flag5) {
                  WorldLabelRenderer.render(fontManager, instance, floatValue2, floatValue3);
                }

                if (flag4 && moduleManager != null) {
                  CustomCrosshairModule customCrosshairModule = moduleManager.customCrosshairModule;
                  if (customCrosshairModule != null
                      && customCrosshairModule.isEnabled()
                      && instance.currentScreen == null
                      && instance.options.getPerspective().isFirstPerson()) {
                    customCrosshairModule.render(
                        fontManager, floatValue2 / 2.0F, floatValue3 / 2.0F);
                  }

                  if (instance.currentScreen == null) {
                    SpawnerLabelRenderer.apply(fontManager);
                  }
                }

                if (flag3) {
                  boolean flag8 = test();
                  if (flag8 && WorldSignalSupport.test2()) {
                    WorldSignalSupport.render(
                        fontManager,
                        0.0F,
                        0.0F,
                        floatValue2,
                        floatValue3,
                        1.0F,
                        computeFloat2(),
                        computeFloat3());
                  }

                  WorldSignalSupport.render(
                      fontManager,
                      0.0F,
                      0.0F,
                      floatValue2,
                      floatValue3,
                      1.0F,
                      flag8,
                      computeFloat2(),
                      computeFloat3());
                  if (moduleManager != null
                      && moduleManager.clusterESPModule != null
                      && moduleManager.clusterESPModule.isEnabled()) {
                    GeodeClusterRenderer.apply(fontManager, moduleManager.clusterESPModule);
                  }
                }

                if (flag) {
                  ((ClientScreen) (Object) (instance.currentScreen))
                      .render2(
                          fontManager, computeFloat2(), computeFloat3(), floatValue2, floatValue3);
                }

                if (flag7 && notificationsHud != null) {
                  notificationsHud.render(fontManager, floatValue2, floatValue3);
                }

                if (flag7) {
                  DeepscanNotifications.apply(fontManager, floatValue2);
                }

                fontManager.update3();
                GL33C.glBindSampler(0, 0);
                fontManager.update();
                return;
              }
            } finally {
              openGlStateSnapshot.update();
            }
          } catch (Throwable failure) {
            enabled = true;
            ClientInitializer.logger.error(
                "bunnythekenzo overlay renderer crashed; disabling overlay", failure);
          }
        }
      }
    }
  }

  public static void apply(FontManager fontManager) {
    String string2 = GuiPreferences.getGuiPreferences().getString();
    if (!string2.equals(string)) {
      fontManager.apply(SystemFonts.getSystemFontsData(string2));
      string = string2;
    }
  }

  public static boolean test(GlTexture glTexture, int intValue, int intValue2) {
    if (count == -1) {
      count = GL33C.glGenFramebuffers();
    }

    GL33C.glBindFramebuffer(36160, count);
    GL33C.glFramebufferTexture2D(36160, 36064, 3553, glTexture.getGlId(), 0);
    if (GL33C.glCheckFramebufferStatus(36160) != 36053) {
      return false;
    }

    GL33C.glViewport(0, 0, intValue, intValue2);
    GL33C.glDisable(3089);
    return true;
  }

  public static float computeFloat2() {
    MinecraftClient instance = MinecraftClient.getInstance();
    return (float)
            (instance.mouse.getX()
                * instance.getWindow().getFramebufferWidth()
                / Math.max(1, instance.getWindow().getWidth()))
        / computeFloat();
  }

  public static float computeFloat3() {
    MinecraftClient instance = MinecraftClient.getInstance();
    return (float)
            (instance.mouse.getY()
                * instance.getWindow().getFramebufferHeight()
                / Math.max(1, instance.getWindow().getHeight()))
        / computeFloat();
  }

  public static float computeFloat(double doubleValue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    float floatValue = (float) (doubleValue * instance.getWindow().getScaleFactor());
    return floatValue / computeFloat();
  }

  public static float computeFloat4() {
    return MinecraftClient.getInstance().getWindow().getFramebufferWidth() / computeFloat();
  }

  public static float computeFloat5() {
    return MinecraftClient.getInstance().getWindow().getFramebufferHeight() / computeFloat();
  }
}

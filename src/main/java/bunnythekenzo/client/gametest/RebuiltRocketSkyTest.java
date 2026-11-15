package bunnythekenzo.client.gametest;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.Arrays;
import java.util.LinkedHashMap;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.SkyRenderer;
import bunnythekenzo.client.setting.Setting;

public final class RebuiltRocketSkyTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
              ProtectionPerformanceTest.ensureInitialized();

              LinkedHashMap linkedHashMap = new LinkedHashMap();

              for (Setting setting :
                  (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (skyModule.getSettings())) {
                linkedHashMap.put(setting, setting.toJson());
              }

              boolean enabled = skyModule.isEnabled();
              boolean hudHidden = minecraftClient.options.hudHidden;
              int value2 = minecraftClient.options.getFov().getValue();
              return () -> {
                linkedHashMap.forEach(
                    (referenceArgument52_0, referenceArgument52_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument52_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument52_1));
                skyModule.setEnabled(enabled);
                minecraftClient.options.hudHidden = hudHidden;
                minecraftClient.options.getFov().setValue(value2);
              };
            });

    try {
      preview(clientGameTestContext);
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            onClient.run();
          });
    }
  }

  public static void preview(ClientGameTestContext clientGameTestContext) {
    GpuTextureView onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return RenderSystem.outputDepthTextureOverride;
            });
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          SkyModule skyModule = ClientInitializer.getModuleManager().skyModule;
          skyModule.setEnabled(true);
          skyModule.skySetting.setValue(true);
          skyModule.rocketSetting.setValue(true);
          skyModule.planetSetting.setValue(false);
          skyModule.moonSetting.setValue(false);
          skyModule.iceWorldSetting.setValue(false);
          skyModule.particlesSetting.setValue(false);
          skyModule.supernovaSetting.setValue(false);
          skyModule.shootingStarsSetting.setValue(false);
          skyModule.motionSetting.setNumber(Double.valueOf(100.0));
          skyModule.followThemeSetting.setValue(false);
          skyModule.colourSetting.apply(Integer.valueOf(-40008));
          minecraftClient2.options.hudHidden = true;
          minecraftClient2.options.getFov().setValue(70);
          SkyRenderer.update5();
        });
    clientGameTestContext.waitTicks(16);
    look(clientGameTestContext);
    clientGameTestContext.takeScreenshot("rebuilt-rocket-sky");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.options.getFov().setValue(30);
        });
    clientGameTestContext.waitTicks(6);
    look(clientGameTestContext);
    clientGameTestContext.takeScreenshot("rebuilt-rocket-detail");
    clientGameTestContext.runOnClient(
        minecraftClient4 -> {
          SkyRenderer.update3();
        });
    clientGameTestContext.waitTicks(24);
    look(clientGameTestContext);
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(SkyRenderer.getAmountArray9()[4] > 0.9F, "cockpit opens for the pilot");
        });
    clientGameTestContext.takeScreenshot("rebuilt-rocket-cockpit");
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          SkyRenderer.update4();
        });
    clientGameTestContext.waitTicks(23);
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          ProtectionPerformanceTest.ensureInitialized();

          float[] amountArray9 = SkyRenderer.getAmountArray9();
          apply(amountArray9[3] > 0.8F, "engine reaches full boost");
          apply(amountArray9[4] < 0.05F, "cockpit closes before full thrust");
          ClientInitializer.getModuleManager()
              .skyModule
              .motionSetting
              .setNumber(Double.valueOf(0.0));
        });
    look(clientGameTestContext);
    clientGameTestContext.takeScreenshot("rebuilt-rocket-boost");
    float[] onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient8 -> {
              return SkyRenderer.getAmountArray9();
            });
    clientGameTestContext.waitTicks(24);
    clientGameTestContext.takeScreenshot("rebuilt-rocket-frozen");
    clientGameTestContext.runOnClient(
        minecraftClient9 -> {
          apply(
              Arrays.equals(onClient2, SkyRenderer.getAmountArray9()),
              "Motion 0 freezes the entire rocket state");
          apply(SkyRenderer.test(), "rocket shaders and geometry render without exceptions");
          ClientInitializer.getModuleManager()
              .skyModule
              .motionSetting
              .setNumber(Double.valueOf(100.0));
        });
    clientGameTestContext.waitTicks(20);
    clientGameTestContext.runOnClient(
        minecraftClient10 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              SkyRenderer.getAmountArray9()[2] > onClient2[2] + 0.25F,
              "resuming Motion advances the rocket smoothly");
        });
    look(clientGameTestContext);
    clientGameTestContext.takeScreenshot("rebuilt-rocket-recovery");
    clientGameTestContext.getInput().resizeWindow(1280, 720);
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.runOnClient(
        minecraftClient11 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(SkyRenderer.test(), "rocket depth attachment resizes with the window");
          apply(
              RenderSystem.outputDepthTextureOverride == onClient,
              "rocket restores the world's depth target after drawing");
        });
    clientGameTestContext.getInput().resizeWindow(1600, 900);
    clientGameTestContext.waitTicks(4);
    ClientInitializer.logger.info("Rocket render test passed: cockpit, boost, freeze and resume");
  }

  public static void look(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          float[] amountArray = SkyRenderer.getAmountArray();
          apply(amountArray != null, "rocket stays active during inspection");
          minecraftClient.player.setYaw(amountArray[0]);
          minecraftClient.player.setPitch(amountArray[1]);
        });
    clientGameTestContext.waitTicks(2);
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}

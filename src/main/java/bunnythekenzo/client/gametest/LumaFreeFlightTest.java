package bunnythekenzo.client.gametest;

import java.util.LinkedHashMap;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.Perspective;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.LumaSupport;
import bunnythekenzo.client.module.LumaModule;
import bunnythekenzo.client.render.LumaRenderer;
import bunnythekenzo.client.ui.PanelScreen;

public final class LumaFreeFlightTest {
  public static final long timestamp = 18000L;

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              LinkedHashMap linkedHashMap = new LinkedHashMap();
              ClientInitializer.getModuleManager()
                  .getList()
                  .forEach(
                      value2 -> {
                        linkedHashMap.put(
                            value2, ((bunnythekenzo.client.module.Module) (value2)).isEnabled());
                      });
              LinkedHashMap linkedHashMap2 = new LinkedHashMap();
              getLumaModule()
                  .getSettings()
                  .forEach(
                      value3 -> {
                        linkedHashMap2.put(
                            value3, ((bunnythekenzo.client.setting.Setting) (value3)).toJson());
                      });
              Perspective perspective = minecraftClient.options.getPerspective();
              int value4 = minecraftClient.options.getFov().getValue();
              int value5 = minecraftClient.options.getMaxFps().getValue();
              boolean enabled = AnimationPreferences.isEnabled();
              boolean hudHidden = minecraftClient.options.hudHidden;
              return () -> {
                minecraftClient.options.forwardKey.setPressed(false);
                minecraftClient.options.jumpKey.setPressed(false);
                minecraftClient.options.sneakKey.setPressed(false);
                minecraftClient.setScreen(null);
                linkedHashMap2.forEach(
                    (referenceArgument44_0, referenceArgument44_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument44_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument44_1));
                linkedHashMap.forEach(
                    (referenceArgument45_0, referenceArgument45_1) ->
                        ((bunnythekenzo.client.module.Module) referenceArgument45_0)
                            .setEnabled((java.lang.Boolean) referenceArgument45_1));
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.getFov().setValue(value4);
                minecraftClient.options.getMaxFps().setValue(value5);
                minecraftClient.options.hudHidden = hudHidden;
                AnimationPreferences.setEnabled(enabled);
              };
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            ProtectionPerformanceTest.ensureInitialized();

            getLumaModule()
                .getSettings()
                .forEach(
                    (referenceArgument46_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument46_0).resetToDefault());
            getLumaModule().setEnabled(true);
            AnimationPreferences.setEnabled(true);
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.getFov().setValue(60);
            minecraftClient2.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.player.setYaw(0.0F);
            minecraftClient2.player.setPitch(0.0F);
            GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(getLumaModule());
            apply(guiModuleEntry.getList().size() == 3, "three focused settings tabs");
            apply(
                guiModuleEntry.getList().stream()
                        .mapToInt(
                            collectionData -> {
                              return ((bunnythekenzo.client.CollectionData) (collectionData))
                                  .list()
                                  .size();
                            })
                        .sum()
                    == getLumaModule().getSettings().size(),
                "all controls reach the real menu");
            apply(
                ClientInitializer.getModuleManager().getList().stream()
                        .filter(
                            value7 -> {
                              return ((bunnythekenzo.client.module.Module) (value7))
                                  .getName()
                                  .equals("Luma");
                            })
                        .count()
                    == 1L,
                "exactly one registered Luma");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ServerWorld overworld = minecraftServer.getOverworld();
                ProtectionPerformanceTest.ensureInitialized();

                overworld.setTimeOfDay(timestamp);

                for (int index = -12; index <= 12; index++) {
                  for (int index2 = -12; index2 <= 12; index2++) {
                    overworld.setBlockState(
                        new BlockPos(index, 140, index2),
                        (index % 4 != 0 && index2 % 4 != 0
                                ? Blocks.DEEPSLATE_TILES
                                : Blocks.POLISHED_BLACKSTONE)
                            .getDefaultState());
                  }
                }

                for (int index3 = -12; index3 <= 12; index3++) {
                  for (int index4 = 141; index4 <= 148; index4++) {
                    overworld.setBlockState(
                        new BlockPos(index3, index4, 9), Blocks.BLACK_CONCRETE.getDefaultState());
                  }
                }

                minecraftServer
                    .getPlayerManager()
                    .getPlayerList()
                    .getFirst()
                    .setPosition(0.0, 141.0, 0.0);
              });
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            minecraftClient3.player.setPosition(0.0, 141.0, 0.0);
            minecraftClient3.player.setVelocity(Vec3d.ZERO);
            minecraftClient3.player.resetPosition();
          });
      clientGameTestContext.waitTicks(35);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(LumaRenderer.getCount() > 5, "native renderer submits real GPU frames");
            apply(getLumaModule().getCount4() == 16, "default flock appears");
          });
      clientGameTestContext.takeScreenshot("luma-free-flight");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            minecraftClient5.options.sneakKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(35);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(getLumaModule().computeFloat2(1.0F) > 0.9, "real sneak gathers the halo");
          });

      for (String text : new String[] {"Aurora", "Sakura", "Sunset", "Glacier"}) {
        clientGameTestContext.runOnClient(
            minecraftClient7 -> {
              getLumaModule().paletteSetting.setValue(text);
            });
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.takeScreenshot("luma-" + text.toLowerCase(Locale.ROOT));
      }

      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            getLumaModule().paletteSetting.resetToDefault();
            minecraftClient8.options.sneakKey.setPressed(false);
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            minecraftClient9.options.jumpKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient10.player.isOnGround() && getLumaModule().computeFloat(1.0F) > 0.4,
                "real jump blooms the flock");
            minecraftClient10.options.jumpKey.setPressed(false);
          });
      clientGameTestContext.takeScreenshot("luma-jump-bloom");
      clientGameTestContext.waitFor(
          minecraftClient11 -> {
            return minecraftClient11.player.isOnGround();
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            minecraftClient12.player.swingHand(Hand.MAIN_HAND);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(getLumaModule().computeFloat(1.0F) > 0.4, "hand swing scatters the flock");
          });
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            minecraftClient14.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient15.options.forwardKey.setPressed(false);

            for (int index5 = 0; index5 < getLumaModule().getCount4(); index5++) {
              LumaSupport lumaSupport = getLumaModule().getLumaSupport(index5);
              apply(
                  lumaSupport.getVec3d(1.0F).distanceTo(minecraftClient15.player.getEntityPos())
                      < 7.0,
                  "flock follows actual movement");
              apply(lumaSupport.getCount2() <= 34, "trail memory stays bounded");
            }

            minecraftClient15.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("luma-third-person");
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            getLumaModule().motionSetting.setValue("Still");
          });
      clientGameTestContext.waitTicks(10);
      double onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient17 -> {
                return getLumaModule().computeDouble(1.0F);
              });
      Vec3d onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient18 -> {
                return getLumaModule().getLumaSupport(0).getVec3d(1.0F);
              });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getLumaModule().computeDouble(0.5F) == onClient2,
                "Still freezes the animation clock");
            apply(
                getLumaModule().getLumaSupport(0).getVec3d(1.0F).squaredDistanceTo(onClient3)
                    < 1.0E-4,
                "Still holds the flock in place");
            getLumaModule().motionSetting.setValue("Reactive");
            getLumaModule().flightSpeedSetting.setNumber(Double.valueOf(0.0));
          });
      clientGameTestContext.waitTicks(6);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getLumaModule().computeDouble(0.5F) == onClient2,
                "zero speed freezes the animation");
            getLumaModule().flightSpeedSetting.resetToDefault();
            AnimationPreferences.setEnabled(false);
          });
      clientGameTestContext.waitTicks(6);
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getLumaModule().computeDouble(0.5F) == onClient2,
                "global reduced motion is respected");
            AnimationPreferences.setEnabled(true);
            getLumaModule().butterfliesSetting.setNumber(Double.valueOf(28.0));
            getLumaModule().trailLengthSetting.setNumber(Double.valueOf(1.6));
          });
      clientGameTestContext.waitTicks(35);
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(getLumaModule().getCount4() == 28, "maximum flock renders");
          });
      clientGameTestContext.takeScreenshot("luma-maximum");
      clientGameTestContext.runOnClient(
          minecraftClient23 -> {
            ProtectionPerformanceTest.ensureInitialized();

            getLumaModule().butterfliesSetting.setNumber(Double.valueOf(6.0));
            getLumaModule().silkTrailsSetting.setValue(false);
            getLumaModule().glowSetting.setNumber(Double.valueOf(0.0));
            apply(
                !getLumaModule().trailLengthSetting.isVisible()
                    && !getLumaModule().stardustSetting.isVisible(),
                "dependent trail controls hide");
          });
      clientGameTestContext.waitTicks(6);
      clientGameTestContext.runOnClient(
          minecraftClient24 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getLumaModule().getCount4() == 6, "shrinking the flock removes excess butterflies");
          });
      clientGameTestContext.takeScreenshot("luma-minimal");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                minecraftServer2
                    .getPlayerManager()
                    .getPlayerList()
                    .getFirst()
                    .setPosition(20.0, 141.0, 0.0);
              });
      clientGameTestContext.runOnClient(
          minecraftClient25 -> {
            minecraftClient25.player.setPosition(20.0, 141.0, 0.0);
            minecraftClient25.player.setVelocity(Vec3d.ZERO);
            minecraftClient25.player.resetPosition();
          });
      clientGameTestContext.waitTicks(1);
      clientGameTestContext.runOnClient(
          minecraftClient26 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getLumaModule()
                        .getLumaSupport(0)
                        .getVec3d(1.0F)
                        .distanceTo(minecraftClient26.player.getEntityPos())
                    < 7.0,
                "teleport snaps the swarm to the new location");
            apply(
                getLumaModule().getLumaSupport(0).getCount2() <= 3,
                "teleport discards long trails");
            getLumaModule()
                .getSettings()
                .forEach(
                    (referenceArgument47_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument47_0).resetToDefault());
            minecraftClient26.options.hudHidden = false;
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient26.setScreen(panelScreen);
            apply(panelScreen.test("Luma"), "Luma settings open in Visuals");
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("luma-settings");
      clientGameTestContext.runOnClient(
          minecraftClient27 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient27.setScreen(null);
            getLumaModule().setEnabled(false);
            apply(
                getLumaModule().getCount4() == 0
                    && getLumaModule().getLumaSupport(0).getCount2() == 0,
                "disable releases flock and trails");
          });
      int onClient4 =
          clientGameTestContext.computeOnClient(
              minecraftClient28 -> {
                return LumaRenderer.getCount();
              });
      clientGameTestContext.waitTicks(6);
      clientGameTestContext.runOnClient(
          minecraftClient29 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(LumaRenderer.getCount() == onClient4, "disabled module submits no geometry");
            getLumaModule().setEnabled(true);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient30 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(LumaRenderer.getCount() > onClient4, "rendering resumes after enabling");
            getLumaModule().update4();
            apply(
                getLumaModule().getCount4() == 0 && getLumaModule().computeDouble(1.0F) == 0.0,
                "world cleanup resets animation and geometry");
          });
      ClientInitializer.logger.info(
          "Luma verified: four palettes, real jump/sneak/swing/walk, first/third person, settings,"
              + " reduced motion, bounds, teleport and lifecycle");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient31 -> {
            onClient.run();
          });
    }
  }

  public static LumaModule getLumaModule() {
    return ClientInitializer.getModuleManager().lumaModule;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}

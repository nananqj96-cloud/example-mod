package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import java.util.LinkedHashMap;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.Perspective;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.module.AuroraModule;
import bunnythekenzo.client.render.AuroraRenderer;
import bunnythekenzo.client.ui.PanelScreen;

public final class AuroraBaselineTest {

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
              ClientInitializer.getModuleManager()
                  .auroraModule
                  .getSettings()
                  .forEach(
                      (java.util.function.Consumer<bunnythekenzo.client.setting.Setting>)
                          (setting -> {
                            linkedHashMap2.put(setting, setting.toJson());
                          }));
              Perspective perspective = minecraftClient.options.getPerspective();
              int value3 = minecraftClient.options.getFov().getValue();
              int value4 = minecraftClient.options.getMaxFps().getValue();
              boolean hudHidden = minecraftClient.options.hudHidden;
              boolean enabled = AnimationPreferences.isEnabled();
              return () -> {
                minecraftClient.options.jumpKey.setPressed(false);
                minecraftClient.options.forwardKey.setPressed(false);
                ((net.minecraft.client.MinecraftClient) (minecraftClient)).setScreen(null);
                linkedHashMap2.forEach(
                    (referenceArgument17_0, referenceArgument17_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument17_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument17_1));
                linkedHashMap.forEach(
                    (referenceArgument18_0, referenceArgument18_1) ->
                        ((bunnythekenzo.client.module.Module) referenceArgument18_0)
                            .setEnabled((java.lang.Boolean) referenceArgument18_1));
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.getFov().setValue(value3);
                minecraftClient.options.getMaxFps().setValue(value4);
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
            AuroraModule auroraModule = ClientInitializer.getModuleManager().auroraModule;
            auroraModule
                .getSettings()
                .forEach(
                    (referenceArgument19_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument19_0).resetToDefault());
            ProtectionPerformanceTest.ensureInitialized();

            auroraModule.setEnabled(false);
            AnimationPreferences.setEnabled(true);
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.options.getFov().setValue(70);
            minecraftClient2.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            minecraftClient2.player.setYaw(180.0F);
            minecraftClient2.player.setPitch(-8.0F);
            GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(auroraModule);
            apply(guiModuleEntry.getList().size() == 3, "three focused settings groups");
            apply(
                guiModuleEntry.getList().stream()
                        .mapToInt(
                            collectionData -> {
                              return ((bunnythekenzo.client.CollectionData) (collectionData))
                                  .list()
                                  .size();
                            })
                        .sum()
                    == auroraModule.getSettings().size(),
                "all controls reach the GUI");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerWorld overworld = minecraftServer.getOverworld();
                overworld.setTimeOfDay(13000L);

                for (int index = -13; index <= 13; index++) {
                  for (int index2 = -13; index2 <= 13; index2++) {
                    double doubleValue = Math.hypot(index, index2);
                    Block block =
                        doubleValue > 5.5 && doubleValue < 6.7
                            ? Blocks.POLISHED_DIORITE
                            : (doubleValue < 5.5 ? Blocks.DEEPSLATE_TILES : Blocks.SMOOTH_STONE);
                    overworld.setBlockState(
                        new BlockPos(index, 140, index2), block.getDefaultState());
                  }
                }

                for (int intValue : new int[] {-7, 7}) {
                  for (int intValue2 : new int[] {-7, 7}) {
                    overworld.setBlockState(
                        new BlockPos(intValue, 141, intValue2),
                        Blocks.SEA_LANTERN.getDefaultState());
                  }
                }

                overworld.setBlockState(new BlockPos(0, 144, 0), Blocks.LIGHT.getDefaultState());
                minecraftServer
                    .getPlayerManager()
                    .getPlayerList()
                    .getFirst()
                    .setPosition(0.0, 141.0, 0.0);
              });
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            minecraftClient3.player.setPosition(0.0, 141.0, 0.0);
            minecraftClient3.player.setVelocity(0.0, 0.0, 0.0);
            minecraftClient3.player.resetPosition();
            ClientInitializer.getModuleManager().auroraModule.update4();
          });
      clientGameTestContext.waitTicks(65);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("aurora-baseline");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ClientInitializer.getModuleManager().auroraModule.setEnabled(true);
          });
      clientGameTestContext.waitTicks(65);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(AuroraRenderer.getCount2() > 5, "native silk, glow and star passes render");
          });
      clientGameTestContext.takeScreenshot("aurora-in-game");
      if (Boolean.getBoolean("bunnythekenzo.test.aurora.capture")) {
        for (int index3 = 0; index3 < 100; index3++) {
          clientGameTestContext.waitTicks(1);
          clientGameTestContext.takeScreenshot(
              String.format(Locale.ROOT, "aurora-film-%04d", index3));
        }
      }

      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            AnimationPreferences.setEnabled(false);
          });
      double onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient7 -> {
                return ClientInitializer.getModuleManager().auroraModule.computeDouble(1.0F);
              });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager().auroraModule.computeDouble(0.5F) == onClient2,
                "reduced motion freezes animation");
          });

      for (String text : new String[] {"Borealis", "Candy", "Sunset", "Glacier", "Amethyst"}) {
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              AuroraModule auroraModule2 = ClientInitializer.getModuleManager().auroraModule;
              auroraModule2.toneSetting.setValue(text);
              JsonElement jsonElement = auroraModule2.toneSetting.toJson();
              auroraModule2.toneSetting.resetToDefault();
              auroraModule2.toneSetting.fromJson(jsonElement);
              apply(
                  auroraModule2.toneSetting.isSelected(text),
                  "palette survives configuration round-trip");
            });
        clientGameTestContext.waitTicks(3);
        clientGameTestContext.takeScreenshot("aurora-" + text.toLowerCase(Locale.ROOT));
      }

      for (String text2 : new String[] {"Helix", "Halo"}) {
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              ClientInitializer.getModuleManager().auroraModule.shapeSetting.setValue(text2);
            });
        clientGameTestContext.waitTicks(3);
        clientGameTestContext.takeScreenshot("aurora-" + text2.toLowerCase(Locale.ROOT));
      }

      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                minecraftServer2.getOverworld().setTimeOfDay(6000L);
              });
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ClientInitializer.getModuleManager().auroraModule.shapeSetting.resetToDefault();
            ClientInitializer.getModuleManager().auroraModule.toneSetting.resetToDefault();
          });
      clientGameTestContext.waitTicks(22);
      clientGameTestContext.takeScreenshot("aurora-daylight");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                minecraftServer3.getOverworld().setTimeOfDay(13000L);
              });
      clientGameTestContext.waitTicks(22);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            AuroraModule auroraModule3 = ClientInitializer.getModuleManager().auroraModule;
            auroraModule3.shapeSetting.resetToDefault();
            auroraModule3.toneSetting.resetToDefault();
            auroraModule3.flowSpeedSetting.setNumber(Double.valueOf(0.0));
            AnimationPreferences.setEnabled(true);
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager().auroraModule.computeDouble(0.5F) == onClient2,
                "zero flow speed freezes animation");
          });
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ClientInitializer.getModuleManager().auroraModule.flowSpeedSetting.resetToDefault();
            minecraftClient14.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(24);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();

            AuroraModule auroraModule4 = ClientInitializer.getModuleManager().auroraModule;
            apply(auroraModule4.computeFloat2(1.0F) > 0.05, "real walking changes ribbon flow");
            apply(
                auroraModule4.getVec3d(0.0, 1.0F).distanceTo(auroraModule4.getVec3d(1.0, 1.0F))
                    > 0.5,
                "trail retains past world positions");
            apply(auroraModule4.getCount3() <= 68, "history stays bounded");
            minecraftClient15.options.forwardKey.setPressed(false);
          });
      clientGameTestContext.takeScreenshot("aurora-moving");
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            minecraftClient16.options.jumpKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient17.options.jumpKey.setPressed(false);
            apply(!minecraftClient17.player.isOnGround(), "actual jump leaves the floor");
            apply(
                !ClientInitializer.getModuleManager().auroraModule.getList2().isEmpty(),
                "jump creates a world-space bloom");
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("aurora-jump");
      clientGameTestContext.waitTicks(32);
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            apply(
                ClientInitializer.getModuleManager().auroraModule.getList2().isEmpty(),
                "old blooms expire");
          });
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            minecraftClient19.options.setPerspective(Perspective.FIRST_PERSON);
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("aurora-first-person");
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ClientInitializer.getModuleManager().auroraModule.firstPersonSetting.setValue(false);
          });
      clientGameTestContext.waitTicks(2);
      int onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient21 -> {
                return AuroraRenderer.getCount2();
              });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                AuroraRenderer.getCount2() == onClient3,
                "first-person visibility control suppresses submission");
          });
      clientGameTestContext.runOnClient(
          minecraftClient23 -> {
            AuroraModule auroraModule5 = ClientInitializer.getModuleManager().auroraModule;
            auroraModule5.firstPersonSetting.resetToDefault();
            auroraModule5.ribbonsSetting.setNumber(Double.valueOf(4.0));
            auroraModule5.ribbonWidthSetting.setNumber(Double.valueOf(150.0));
            auroraModule5.trailLengthSetting.setNumber(Double.valueOf(3.0));
            auroraModule5.stardustSetting.setValue(false);
            auroraModule5.jumpBloomSetting.setValue(false);
            minecraftClient23.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("aurora-wide");
      clientGameTestContext.runOnClient(
          minecraftClient24 -> {
            ProtectionPerformanceTest.ensureInitialized();

            AuroraModule auroraModule6 = ClientInitializer.getModuleManager().auroraModule;
            Vec3d entityPos = minecraftClient24.player.getEntityPos();
            minecraftClient24.player.setPosition(entityPos.add(100.0, 0.0, 0.0));
            auroraModule6.onTick();
            apply(
                auroraModule6.getCount3() == 1
                    && auroraModule6
                            .getVec3d(0.0, 1.0F)
                            .distanceTo(auroraModule6.getVec3d(3.0, 1.0F))
                        < 0.001,
                "teleport discards the old trail instead of drawing across the world");
            minecraftClient24.player.setPosition(entityPos);
            auroraModule6.update4();
            auroraModule6
                .getSettings()
                .forEach(
                    (referenceArgument20_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument20_0).resetToDefault());
            minecraftClient24.options.hudHidden = false;
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient24.setScreen(panelScreen);
            apply(panelScreen.test("Aurora"), "Aurora settings open from Visuals");
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("aurora-settings");
      clientGameTestContext.runOnClient(
          minecraftClient25 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient25.setScreen(null);
            AuroraModule auroraModule7 = ClientInitializer.getModuleManager().auroraModule;
            auroraModule7.setEnabled(false);
            apply(
                auroraModule7.getVec3d(0.0, 1.0F) == null
                    && auroraModule7.getList2().isEmpty()
                    && auroraModule7.getCount3() == 0,
                "disable clears all world-space state");
          });
      clientGameTestContext.waitTicks(2);
      int onClient4 =
          clientGameTestContext.computeOnClient(
              minecraftClient26 -> {
                return AuroraRenderer.getCount2();
              });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient27 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(AuroraRenderer.getCount2() == onClient4, "disabled effect does not draw");
          });
      clientGameTestContext.runOnClient(
          minecraftClient28 -> {
            ClientInitializer.getModuleManager().auroraModule.setEnabled(true);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient29 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(AuroraRenderer.getCount2() > onClient4, "effect resumes after re-enabling");
          });
      ClientInitializer.logger.info(
          "Aurora verified: GPU passes, five palettes, three shapes, settings, motion, jump bloom,"
              + " bounded history, teleport cleanup, first/third person and enable/disable"
              + " lifecycle");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient30 -> {
            onClient.run();
          });
    }

    ClientInitializer.ensureInitialized();
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}

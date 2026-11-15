package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteBodyParts;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.LushLifeAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class LushLifeWheelTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    float[] floatValues = new float[] {Float.NaN};
    ArrayList arrayList = new ArrayList();
    SoundInstanceListener soundInstanceListener =
        (soundInstance, weightedSoundSet, floatValue) -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (soundInstance.getId().toString().equals("bunnythekenzo:emote.lush_life")) {
            arrayList.add(soundInstance);
          }
        };
    ProtectionPerformanceTest.ensureInitialized();

    clientGameTestContext.runOnClient(
        minecraftClient -> {
          ClientInitializer.getModuleManager()
              .getList()
              .forEach(
                  (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                      (module -> {
                        module.setEnabled(false);
                      }));
          minecraftClient.setScreen(null);
          minecraftClient.options.hudHidden = true;
          minecraftClient.options.getFov().setValue(55);
          minecraftClient.options.getMaxFps().setValue(60);
          minecraftClient.getSoundManager().registerListener(soundInstanceListener);
          ClientTickEvents.START_CLIENT_TICK.register(
              minecraftClient2 -> {
                ProtectionPerformanceTest.ensureInitialized();
                if (Float.isFinite(floatValues[0])) {
                  apply(floatValues[0]);
                }
              });
          WorldRenderEvents.START_MAIN.register(
              worldTerrainRenderContext -> {
                ProtectionPerformanceTest.ensureInitialized();
                if (Float.isFinite(floatValues[0])) {
                  apply(floatValues[0]);
                }
              });
          apply(minecraftClient);
          apply(
              minecraftClient
                  .getResourceManager()
                  .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/lush_life.ogg"))
                  .isPresent(),
              "original audio packaged");
        });
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              ProtectionPerformanceTest.ensureInitialized();

              ServerWorld overworld = minecraftServer.getOverworld();
              overworld.setTimeOfDay(6000L);

              for (int index = -9; index <= 9; index++) {
                for (int index2 = -9; index2 <= 9; index2++) {
                  overworld.setBlockState(
                      new BlockPos(index, 139, index2), Blocks.SEA_LANTERN.getDefaultState());
                  overworld.setBlockState(
                      new BlockPos(index, 140, index2),
                      ((index + index2) % 2 == 0
                              ? Blocks.SMOOTH_QUARTZ
                              : Blocks.LIGHT_GRAY_CONCRETE)
                          .getDefaultState());
                }
              }

              ServerPlayerEntity first =
                  minecraftServer.getPlayerManager().getPlayerList().getFirst();
              first.changeGameMode(GameMode.CREATIVE);
              first.setPosition(0.5, 141.0, 0.5);
            });
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          minecraftClient3.player.setPosition(0.5, 141.0, 0.5);
          minecraftClient3.player.setVelocity(Vec3d.ZERO);
          minecraftClient3.player.setYaw(0.0F);
          minecraftClient3.player.setPitch(0.0F);
          minecraftClient3.player.bodyYaw = minecraftClient3.player.lastBodyYaw = 0.0F;
          minecraftClient3.player.headYaw = minecraftClient3.player.lastHeadYaw = 0.0F;
          minecraftClient3.player.resetPosition();
        });
    clientGameTestContext.waitTicks(45);

    try {
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
            emoteCatalog.cancelOnActionControl.setValue(true);
            emoteCatalog.automaticF5Control.setValue(true);
            minecraftClient4.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient4.setScreen(new EmoteWheelScreen(emoteCatalog, 66));
          });
      clientGameTestContext.waitTicks(10);
      double[] onClient =
          clientGameTestContext.computeOnClient(
              minecraftClient5 -> {
                double doubleValue =
                    (-Math.PI / 2)
                        + computeInt()
                            * Math.PI
                            * 2.0
                            / ClientInitializer.getEmoteCatalog().getList().size();
                double doubleValue2 = 130.0F * OverlayRenderer.computeFloat();
                return new double[] {
                  minecraftClient5.getWindow().getWidth() / 2.0
                      + Math.cos(doubleValue) * doubleValue2,
                  minecraftClient5.getWindow().getHeight() / 2.0
                      + Math.sin(doubleValue) * doubleValue2
                };
              });
      clientGameTestContext.getInput().setCursorPos(onClient[0], onClient[1]);
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("lush-life-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient6.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "wheel release starts Lush life");
            apply(
                minecraftClient6.currentScreen == null
                    && minecraftClient6.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "selection closes wheel and switches camera");
          });
      clientGameTestContext.waitTicks(10);
      Vec3d onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient7 -> {
                return minecraftClient7.player.getEntityPos();
              });
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(arrayList.size() == 1, "one original soundtrack starts");
            apply(
                minecraftClient8.getSoundManager().isPlaying((SoundInstance) arrayList.getFirst()),
                "audio engine plays the OGG");
            minecraftClient8.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });
      if (Boolean.getBoolean("bunnythekenzo.test.lushLife.capture")) {
        for (int index3 = 0; index3 < 432; index3++) {
          float floatValue2 = index3 / 30.0F;
          clientGameTestContext.runOnClient(
              minecraftClient9 -> {
                floatValues[0] = floatValue2;
                apply(floatValue2);
              });
          clientGameTestContext.waitTicks(2);
          clientGameTestContext.takeScreenshot("lush-life-frame-%04d".formatted(index3));
        }
      }

      for (float floatValue3 :
          new float[] {
            0.5F, 2.7F, 4.6F, 5.2F, 6.53F, 7.43F, 8.73F, 9.93F, 10.73F, 11.13F, 11.57F, 12.03F,
            13.5F, 14.25F
          }) {
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              floatValues[0] = floatValue3;
              apply(floatValue3);
            });
        clientGameTestContext.waitTicks(4);
        clientGameTestContext.takeScreenshot("lush-life-" + Math.round(floatValue3 * 1000.0F));
      }

      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();

            floatValues[0] = Float.NaN;
            apply(
                minecraftClient11.player.getEntityPos().distanceTo(onClient2) < 0.001,
                "choreography never moves the entity");
            ClientInitializer.getEmoteCatalog().update();
            apply(
                minecraftClient11.options.getPerspective() == Perspective.THIRD_PERSON_FRONT,
                "manual camera choice survives stop");
            minecraftClient11.options.setPerspective(Perspective.FIRST_PERSON);
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            minecraftClient11.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient12.options.forwardKey.setPressed(false);
            apply(
                !ClientInitializer.getEmoteCatalog().test(),
                "walking cancels the stationary dance");
            apply(
                minecraftClient12.options.getPerspective() == Perspective.FIRST_PERSON,
                "cancel restores the camera");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            apply(14.5F);
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.waitFor(
          minecraftClient13 -> {
            final var referenceReceiver48 = minecraftClient13.getSoundManager();
            return arrayList.stream()
                .noneMatch(
                    (referenceArgument48_0) ->
                        referenceReceiver48.isPlaying(
                            (net.minecraft.client.sound.SoundInstance) referenceArgument48_0));
          },
          100);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(!ClientInitializer.getEmoteCatalog().test(), "clip completes at 14.419 seconds");
            final var referenceReceiver49 = minecraftClient14.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument49_0) ->
                            referenceReceiver49.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument49_0)),
                "stop clears every soundtrack instance");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            minecraftClient15.setScreen(new GameMenuScreen(true));
          });
      clientGameTestContext.waitFor(MinecraftClient::isPaused);
      double onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient16 -> {
                return ClientInitializer.getEmoteCatalog().computeDouble();
              });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                Math.abs(ClientInitializer.getEmoteCatalog().computeDouble() - onClient3) < 0.001,
                "singleplayer pause holds choreography with the soundtrack");
            minecraftClient17.setScreen(null);
          });
      clientGameTestContext.waitFor(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !minecraftClient18.isPaused();
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getEmoteCatalog().computeDouble() > onClient3,
                "choreography resumes with audio");
          });
      clientGameTestContext.waitFor(
          minecraftClient20 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !ClientInitializer.getEmoteCatalog().test();
          },
          1800);
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            final var referenceReceiver50 = minecraftClient21.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument50_0) ->
                            referenceReceiver50.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument50_0)),
                "full real-time playback ends without a lingering soundtrack");
          });
      ClientInitializer.logger.info(
          "Lush life verified: 432 frames x 2 skins, smooth complete turn, source OGG, wheel,"
              + " camera, cancellation and completion");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            floatValues[0] = Float.NaN;
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient22.options.forwardKey.setPressed(false);
            minecraftClient22.getSoundManager().unregisterListener(soundInstanceListener);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    EmoteDefinition emoteDefinition =
        (EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(computeInt());
    apply(
        !emoteDefinition.test()
            && emoteDefinition.emoteAnimation() == EmoteAnimation.LUSH_LIFE
            && emoteDefinition.timestamp() == 14419L
            && emoteDefinition.amount() == 98.0F,
        "Lush life catalogue timing");

    for (boolean flag : new boolean[] {false, true}) {
      PlayerEntityModel playerEntityModel =
          new PlayerEntityModel(
              minecraftClient
                  .getLoadedEntityModels()
                  .getModelPart(
                      flag
                          ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                          : EntityModelLayers.PLAYER),
              flag);
      float floatValue = 0.0F;
      float floatValue2 = 0.0F;

      for (int index = 0; index < 432; index++) {
        playerEntityModel.getRootPart().traverse().forEach(ModelPart::resetTransform);
        EmoteBodyParts.apply(playerEntityModel);
        LushLifeAnimation.render(playerEntityModel, index / 30.0F, 1.0F);

        for (ModelPart modelPart : playerEntityModel.getRootPart().traverse()) {
          apply(
              Float.isFinite(
                  modelPart.originX
                      + modelPart.originY
                      + modelPart.originZ
                      + modelPart.pitch
                      + modelPart.yaw
                      + modelPart.roll),
              "finite source-frame pose");
        }

        float floatValue3 =
            (float)
                Math.IEEEremainder(playerEntityModel.getRootPart().yaw - floatValue, Math.PI * 2);
        apply(Math.abs(floatValue3) < 0.32, "turn has no single-frame discontinuity");
        floatValue2 += floatValue3;
        floatValue = playerEntityModel.getRootPart().yaw;
      }

      apply(Math.abs(floatValue2 - (Math.PI * 2)) < 0.001, "one full observed turn");
      playerEntityModel.getRootPart().traverse().forEach(ModelPart::resetTransform);
      LushLifeAnimation.render(playerEntityModel, 14.4F, 0.15F);
      apply(
          Math.abs(playerEntityModel.getRootPart().yaw) < 0.001,
          "fade does not unwind the completed turn");
      playerEntityModel.getRootPart().traverse().forEach(ModelPart::resetTransform);
      EmoteBodyParts.apply(playerEntityModel);
      LushLifeAnimation.render(playerEntityModel, 8.7F, 0.0F);
      apply(
          playerEntityModel.getRootPart().yaw == 0.0F
              && playerEntityModel.rightArm.pitch == 0.0F
              && !playerEntityModel.rightArm.hidden,
          "zero blend leaves the vanilla rig intact");
      PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
      playerEntityRenderState.id = -100;
      ClientInitializer.getEmoteCatalog().apply(playerEntityModel, playerEntityRenderState);
      apply(playerEntityModel.getRootPart().yaw == 0.0F, "remote player is unaffected");
    }
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Lush life")) {
        return index;
      }
    }

    throw new AssertionError("Missing Lush life slot");
  }

  public static void apply(float floatValue) {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("timestamp");
      declaredField.setAccessible(true);
      declaredField.setLong(
          ClientInitializer.getEmoteCatalog(), System.nanoTime() - (long) (floatValue * 1.0E9F));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Lush life: " + text);
    }
  }
}

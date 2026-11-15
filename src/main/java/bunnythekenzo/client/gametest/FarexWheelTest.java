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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.FarexPullMyPearlAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class FarexWheelTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    float[] floatValues = new float[] {Float.NaN};
    ArrayList arrayList = new ArrayList();
    SoundInstanceListener soundInstanceListener =
        (soundInstance, weightedSoundSet, floatValue) -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (soundInstance.getId().toString().equals("bunnythekenzo:emote.pulse_pop")) {
            arrayList.add(soundInstance);
          }
        };
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
                  .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/pulse_pop.ogg"))
                  .isPresent(),
              "existing audio packaged");
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
      clientGameTestContext.takeScreenshot("farex-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient6.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "wheel release starts Farex pull my pearl");
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
            apply(arrayList.size() == 1, "one existing soundtrack starts");
            apply(
                minecraftClient8.getSoundManager().isPlaying((SoundInstance) arrayList.getFirst()),
                "audio engine plays the OGG");
            minecraftClient8.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });
      if (Boolean.getBoolean("bunnythekenzo.test.farex.capture")) {
        for (int index3 = 0; index3 < 396; index3++) {
          float floatValue2 = index3 / 30.0F;
          clientGameTestContext.runOnClient(
              minecraftClient9 -> {
                floatValues[0] = floatValue2;
                apply(floatValue2);
              });
          clientGameTestContext.waitTicks(2);
          clientGameTestContext.takeScreenshot("farex-frame-%04d".formatted(index3));
        }
      }

      for (float floatValue3 :
          new float[] {
            0.73F, 2.13F, 2.6F, 3.68F, 4.7F, 5.17F, 6.1F, 6.69F, 7.39F, 8.2F, 8.67F, 9.84F, 10.18F,
            10.42F, 11.24F, 11.71F, 11.94F, 12.41F, 12.9F
          }) {
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              floatValues[0] = floatValue3;
              apply(floatValue3);
            });
        clientGameTestContext.waitTicks(4);
        clientGameTestContext.takeScreenshot("farex-" + Math.round(floatValue3 * 1000.0F));
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
            apply(13.3F);
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.waitFor(
          minecraftClient13 -> {
            final var referenceReceiver37 = minecraftClient13.getSoundManager();
            return arrayList.stream()
                .noneMatch(
                    (referenceArgument37_0) ->
                        referenceReceiver37.isPlaying(
                            (net.minecraft.client.sound.SoundInstance) referenceArgument37_0));
          },
          100);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(!ClientInitializer.getEmoteCatalog().test(), "clip completes at 13.2 seconds");
            final var referenceReceiver38 = minecraftClient14.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument38_0) ->
                            referenceReceiver38.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument38_0)),
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
            final var referenceReceiver39 = minecraftClient21.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument39_0) ->
                            referenceReceiver39.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument39_0)),
                "full real-time playback ends without a lingering soundtrack");
          });
      ClientInitializer.logger.info(
          "Farex verified: 7920 rendered poses, distinct phrases, genuine freezes, smooth turn,"
              + " existing OGG, wheel, pause, camera, cancellation and full playback");
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
    boolean flag;
    EmoteDefinition emoteDefinition =
        (EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(computeInt());
    apply(
        emoteDefinition.emoteAnimation() == EmoteAnimation.FAREX_PULL_MY_PEARL
            && emoteDefinition.timestamp() == 13200L
            && emoteDefinition.amount() == 128.4F,
        "dedicated choreography and measured music tempo");

    for (boolean flag2 : new boolean[] {false, true}) {
      PlayerEntityModel playerEntityModel =
          new PlayerEntityModel(
              minecraftClient
                  .getLoadedEntityModels()
                  .getModelPart(
                      flag2
                          ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                          : EntityModelLayers.PLAYER),
              flag2);
      PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
      playerEntityRenderState.id = -123;
      playerEntityRenderState.hatVisible =
          playerEntityRenderState.jacketVisible =
              playerEntityRenderState.leftPantsLegVisible =
                  playerEntityRenderState.rightPantsLegVisible =
                      playerEntityRenderState.leftSleeveVisible =
                          playerEntityRenderState.rightSleeveVisible = true;
      HoppinStandingBeforeTestSupport3 hoppinStandingBeforeTestSupport3 =
          new HoppinStandingBeforeTestSupport3();
      float floatValue = 0.0F;
      float floatValue2 = 0.0F;

      for (int index = 0; index < 792; index++) {
        for (float floatValue3 : new float[] {0.0F, 0.25F, 0.5F, 0.75F, 1.0F}) {
          playerEntityModel.setAngles(playerEntityRenderState);
          EmotePoseAnimator.render(
              playerEntityModel,
              emoteDefinition.emoteAnimation(),
              index / 60.0F,
              emoteDefinition.amount(),
              floatValue3);
          hoppinStandingBeforeTestSupport3.amount = -Float.MAX_VALUE;
          hoppinStandingBeforeTestSupport3.count = 0;
          playerEntityModel
              .getRootPart()
              .render(new MatrixStack(), hoppinStandingBeforeTestSupport3, 15728880, 0);
          apply(hoppinStandingBeforeTestSupport3.count > 100, "visible skin and overlays");
          flag = hoppinStandingBeforeTestSupport3.amount <= 1.53F;
          float amount = hoppinStandingBeforeTestSupport3.amount;
          float floatValue4 = floatValue3;
          int intValue = index;
          apply(flag, "floor clearance: frame " + intValue + "/" + floatValue4 + ": " + amount);

          for (ModelPart modelPart : playerEntityModel.getRootPart().traverse()) {
            apply(
                Float.isFinite(
                    modelPart.originX
                        + modelPart.originY
                        + modelPart.originZ
                        + modelPart.pitch
                        + modelPart.yaw
                        + modelPart.roll),
                "finite whole-body pose");
          }
        }

        float floatValue5 =
            (float)
                Math.IEEEremainder(playerEntityModel.getRootPart().yaw - floatValue, Math.PI * 2);
        apply(Math.abs(floatValue5) < 0.26, "no visible single-frame snap during turn");
        floatValue2 += floatValue5;
        floatValue = playerEntityModel.getRootPart().yaw;
      }

      apply(Math.abs(floatValue2 - (Math.PI * 2)) < 0.001, "one complete turn returns to front");

      ArrayList arrayList = new ArrayList();

      for (float floatValue6 : new float[] {0.5F, 4.5F, 8.5F, 12.5F, 16.5F, 20.5F, 24.5F}) {
        playerEntityModel.setAngles(playerEntityRenderState);
        FarexPullMyPearlAnimation.render(playerEntityModel, computeFloat(floatValue6), 1.0F);
        arrayList.add(getAmountArray(playerEntityModel));
      }

      for (int index2 = 0; index2 < arrayList.size(); index2++) {
        for (int index3 = index2 + 1; index3 < arrayList.size(); index3++) {
          apply(
              computeFloat((float[]) arrayList.get(index2), (float[]) arrayList.get(index3)) > 1.0F,
              "phrases have distinct silhouettes");
        }
      }

      for (float floatValue7 : new float[] {7.1F, 15.1F, 23.1F}) {
        playerEntityModel.setAngles(playerEntityRenderState);
        FarexPullMyPearlAnimation.render(playerEntityModel, computeFloat(floatValue7), 1.0F);
        float[] floatValues = getAmountArray(playerEntityModel);
        playerEntityModel.setAngles(playerEntityRenderState);
        FarexPullMyPearlAnimation.render(playerEntityModel, computeFloat(floatValue7 + 0.2F), 1.0F);
        apply(
            computeFloat(floatValues, getAmountArray(playerEntityModel)) < 0.001,
            "freeze holds the entire pose");
      }

      playerEntityModel.setAngles(playerEntityRenderState);
      FarexPullMyPearlAnimation.render(playerEntityModel, 13.1F, 0.15F);
      apply(
          Math.abs(playerEntityModel.getRootPart().yaw) < 0.001, "finish fades without unwinding");
      playerEntityModel.setAngles(playerEntityRenderState);
      FarexPullMyPearlAnimation.render(playerEntityModel, 4.0F, 0.0F);
      apply(
          !playerEntityModel.rightArm.hidden && playerEntityModel.getRootPart().yaw == 0.0F,
          "zero mix retains vanilla geometry");
      float[] floatValues2 = getAmountArray(playerEntityModel);
      FarexPullMyPearlAnimation.render(playerEntityModel, Float.NaN, 1.0F);
      FarexPullMyPearlAnimation.render(playerEntityModel, 4.0F, Float.POSITIVE_INFINITY);
      apply(
          computeFloat(floatValues2, getAmountArray(playerEntityModel)) == 0.0F,
          "invalid clocks cannot corrupt the model");
      FarexPullMyPearlAnimation.render(playerEntityModel, 8.2F, 1.0F);
      playerEntityModel.setAngles(playerEntityRenderState);
      apply(
          !playerEntityModel.rightArm.hidden
              && computeFloat(floatValues2, getAmountArray(playerEntityModel)) < 0.001,
          "next remote or ordinary pose resets split limbs and all transforms");
    }
  }

  public static float computeFloat(float floatValue) {
    return 0.26F + floatValue * 60.0F / 128.4F;
  }

  public static float[] getAmountArray(PlayerEntityModel playerEntityModel) {
    ProtectionPerformanceTest.ensureInitialized();

    var list = playerEntityModel.getRootPart().traverse();
    float[] floatValues = new float[list.size() * 6];
    int index = 0;

    for (ModelPart modelPart : (java.lang.Iterable<ModelPart>) (java.lang.Iterable<?>) (list)) {
      floatValues[index++] = modelPart.originX / 8.0F;
      floatValues[index++] = modelPart.originY / 8.0F;
      floatValues[index++] = modelPart.originZ / 8.0F;
      floatValues[index++] = modelPart.pitch;
      floatValues[index++] = modelPart.yaw;
      floatValues[index++] = modelPart.roll;
    }

    return floatValues;
  }

  public static float computeFloat(float[] floatValues, float[] floatValues2) {
    ProtectionPerformanceTest.ensureInitialized();
    apply(floatValues.length == floatValues2.length, "stable rig");
    float floatValue = 0.0F;

    for (int index = 0; index < floatValues.length; index++) {
      floatValue += Math.abs(floatValues[index] - floatValues2[index]);
    }

    return floatValue;
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Farex pull my pearl")) {
        return index;
      }
    }

    throw new AssertionError("Missing Farex pull my pearl slot");
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
      throw new AssertionError("Farex pull my pearl: " + text);
    }
  }
}

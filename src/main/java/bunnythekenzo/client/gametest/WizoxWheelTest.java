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
import bunnythekenzo.client.WizoxBanAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class WizoxWheelTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    float[] floatValues = new float[] {Float.NaN};
    ArrayList arrayList = new ArrayList();
    SoundInstanceListener soundInstanceListener =
        (soundInstance, weightedSoundSet, floatValue) -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (soundInstance.getId().toString().equals("bunnythekenzo:emote.sidewave")) {
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
                  .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/sidewave.ogg"))
                  .isPresent(),
              "original audio packaged");
        });
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              ServerWorld overworld = minecraftServer.getOverworld();
              ProtectionPerformanceTest.ensureInitialized();

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
      clientGameTestContext.takeScreenshot("wizox-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient6.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "wheel release starts Wizox ban this guy");
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

      for (float floatValue2 :
          new float[] {
            1.2F, 2.5F, 2.72F, 6.02F, 7.79F, 9.54F, 11.31F, 13.2F, 15.7F, 17.25F, 18.5F, 21.5F,
            23.83F, 27.55F, 28.4F, 29.75F, 31.1F
          }) {
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              floatValues[0] = floatValue2;
              apply(floatValue2);
            });
        clientGameTestContext.waitTicks(4);
        clientGameTestContext.takeScreenshot("wizox-" + Math.round(floatValue2 * 1000.0F));
      }

      if (Boolean.getBoolean("bunnythekenzo.test.wizox.capture")) {
        for (int index3 = 0; index3 < 640; index3++) {
          float floatValue3 = index3 / 20.0F;
          clientGameTestContext.runOnClient(
              minecraftClient10 -> {
                floatValues[0] = floatValue3;
                apply(floatValue3);
              });
          clientGameTestContext.waitTicks(1);
          clientGameTestContext.takeScreenshot("wizox-frame-%04d".formatted(index3));
        }
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
            apply(32.1F);
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.waitFor(
          minecraftClient13 -> {
            final var referenceReceiver54 = minecraftClient13.getSoundManager();
            return arrayList.stream()
                .noneMatch(
                    (referenceArgument54_0) ->
                        referenceReceiver54.isPlaying(
                            (net.minecraft.client.sound.SoundInstance) referenceArgument54_0));
          },
          100);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(!ClientInitializer.getEmoteCatalog().test(), "clip completes at 32 seconds");
            final var referenceReceiver55 = minecraftClient14.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument55_0) ->
                            referenceReceiver55.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument55_0)),
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
            final var referenceReceiver56 = minecraftClient21.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument56_0) ->
                            referenceReceiver56.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument56_0)),
                "full real-time playback ends without a lingering soundtrack");
          });
      ClientInitializer.logger.info(
          "Wizox ban this guy verified: full 32-second geometry sweep on both skins, stable"
              + " freezes, varied phrases, one turn, OGG, wheel, camera, pause, cancellation and"
              + " completion");
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
        emoteDefinition.emoteAnimation() == EmoteAnimation.WIZOX_BAN
            && emoteDefinition.timestamp() == 32000L
            && emoteDefinition.amount() == 136.6F,
        "dedicated choreography in the existing catalogue slot");
    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    playerEntityRenderState.id = -123;
    playerEntityRenderState.hatVisible =
        playerEntityRenderState.jacketVisible =
            playerEntityRenderState.leftPantsLegVisible =
                playerEntityRenderState.rightPantsLegVisible =
                    playerEntityRenderState.leftSleeveVisible =
                        playerEntityRenderState.rightSleeveVisible = true;

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
      HoppinStandingBeforeTestSupport hoppinStandingBeforeTestSupport =
          new HoppinStandingBeforeTestSupport();
      float floatValue = 0.0F;
      float floatValue2 = 0.0F;

      for (int index = 0; index <= 1920; index++) {
        float floatValue3 = index / 60.0F;
        playerEntityModel.setAngles(playerEntityRenderState);
        WizoxBanAnimation.render(playerEntityModel, floatValue3, 1.0F);
        float floatValue4 =
            (float)
                Math.IEEEremainder(playerEntityModel.getRootPart().yaw - floatValue, Math.PI * 2);
        apply(Math.abs(floatValue4) < 0.13, "continuous turn at frame " + index);
        floatValue2 += floatValue4;
        floatValue = playerEntityModel.getRootPart().yaw;
        hoppinStandingBeforeTestSupport.amount = -Float.MAX_VALUE;
        hoppinStandingBeforeTestSupport.count = 0;
        playerEntityModel
            .getRootPart()
            .render(new MatrixStack(), hoppinStandingBeforeTestSupport, 15728880, 0);
        apply(hoppinStandingBeforeTestSupport.count > 100, "visible skin and overlays");
        apply(
            hoppinStandingBeforeTestSupport.amount <= 1.526F,
            "floor clearance at " + floatValue3 + ": " + hoppinStandingBeforeTestSupport.amount);
        if (index % 4 == 0) {
          for (float floatValue5 : new float[] {0.0F, 0.25F, 0.5F, 0.75F}) {
            playerEntityModel.setAngles(playerEntityRenderState);
            WizoxBanAnimation.render(playerEntityModel, floatValue3, floatValue5);
            hoppinStandingBeforeTestSupport.amount = -Float.MAX_VALUE;
            playerEntityModel
                .getRootPart()
                .render(new MatrixStack(), hoppinStandingBeforeTestSupport, 15728880, 0);
            apply(
                hoppinStandingBeforeTestSupport.amount <= 1.526F,
                "blended floor clearance at " + floatValue3 + "/" + floatValue5);
          }
        }
      }

      apply(Math.abs(floatValue2 - (Math.PI * 2)) < 0.002, "one complete finale turn");
      float[] floatValues = getAmountArray(playerEntityModel, playerEntityRenderState, 18.45F);
      apply(
          computeFloat(
                  floatValues, getAmountArray(playerEntityModel, playerEntityRenderState, 18.8F))
              < 1.0E-4,
          "intentional freeze holds every joint");
      apply(
          computeFloat(
                  getAmountArray(playerEntityModel, playerEntityRenderState, 6.05F),
                  getAmountArray(playerEntityModel, playerEntityRenderState, 14.9F))
              > 1.0F,
          "footwork and half-time body roll have distinct silhouettes");

      for (float floatValue6 : new float[] {2.5F, 6.02F, 9.54F, 15.1F, 21.0F, 24.0F}) {
        apply(
            computeFloat(
                    getAmountArray(playerEntityModel, playerEntityRenderState, floatValue6),
                    getAmountArray(
                        playerEntityModel, playerEntityRenderState, floatValue6 + 1.7569546F))
                > 0.5,
            "no short repeating wave at " + floatValue6);
      }

      playerEntityModel.setAngles(playerEntityRenderState);
      WizoxBanAnimation.render(playerEntityModel, 31.85F, 0.25F);
      apply(Math.abs(playerEntityModel.getRootPart().yaw) < 0.001, "exit does not unwind the turn");
      playerEntityModel.setAngles(playerEntityRenderState);
      WizoxBanAnimation.render(playerEntityModel, 5.0F, 0.0F);
      apply(
          !playerEntityModel.rightArm.hidden && !playerEntityModel.leftLeg.hidden,
          "zero blend keeps original geometry");
      apply(
          !playerEntityModel.rightArm.getChild("bunnythekenzo_emote_lower").visible,
          "joint geometry resets for other entities");
    }
  }

  public static float[] getAmountArray(
      PlayerEntityModel playerEntityModel,
      PlayerEntityRenderState playerEntityRenderState,
      float floatValue) {
    ProtectionPerformanceTest.ensureInitialized();

    playerEntityModel.setAngles(playerEntityRenderState);
    WizoxBanAnimation.render(playerEntityModel, floatValue, 1.0F);
    ModelPart[] modelPartArray =
        new ModelPart[] {
          playerEntityModel.getRootPart(),
          playerEntityModel.body,
          playerEntityModel.head,
          playerEntityModel.rightArm,
          playerEntityModel.leftArm,
          playerEntityModel.rightLeg,
          playerEntityModel.leftLeg,
          playerEntityModel.rightArm.getChild("bunnythekenzo_emote_lower"),
          playerEntityModel.leftArm.getChild("bunnythekenzo_emote_lower"),
          playerEntityModel.rightLeg.getChild("bunnythekenzo_emote_lower"),
          playerEntityModel.leftLeg.getChild("bunnythekenzo_emote_lower")
        };
    float[] floatValues = new float[modelPartArray.length * 6];

    for (int index = 0; index < modelPartArray.length; index++) {
      ModelPart modelPart = modelPartArray[index];
      int index2 = index * 6;
      floatValues[index2] = modelPart.pitch;
      floatValues[index2 + 1] = modelPart.yaw;
      floatValues[index2 + 2] = modelPart.roll;
      floatValues[index2 + 3] = modelPart.originX;
      floatValues[index2 + 4] = modelPart.originY;
      floatValues[index2 + 5] = modelPart.originZ;
    }

    return floatValues;
  }

  public static float computeFloat(float[] floatValues, float[] floatValues2) {
    float floatValue = 0.0F;
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < floatValues.length; index++) {
      floatValue += Math.abs(floatValues[index] - floatValues2[index]);
    }

    return floatValue;
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Wizox ban this guy")) {
        return index;
      }
    }

    throw new AssertionError("Missing Wizox ban this guy slot");
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
      throw new AssertionError("Wizox ban this guy: " + text);
    }
  }
}

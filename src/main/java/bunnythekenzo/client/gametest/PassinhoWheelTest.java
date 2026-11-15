package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
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
import bunnythekenzo.client.PassinhoDoJamalAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class PassinhoWheelTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    ArrayList arrayList = new ArrayList();
    SoundInstanceListener soundInstanceListener =
        (soundInstance, weightedSoundSet, floatValue) -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (soundInstance.getId().toString().equals("bunnythekenzo:emote.passinho_do_jamal")) {
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
          ClientInitializer.getEmoteCatalog().update();
          minecraftClient.options.hudHidden = true;
          minecraftClient.options.getFov().setValue(55);
          minecraftClient.options.getMaxFps().setValue(60);
          minecraftClient.getSoundManager().registerListener(soundInstanceListener);
          apply(minecraftClient);
          apply(
              minecraftClient
                  .getResourceManager()
                  .getResource(
                      Identifier.of("bunnythekenzo", "sounds/emotes/passinho_do_jamal.ogg"))
                  .isPresent(),
              "original soundtrack packaged");
        });
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              ProtectionPerformanceTest.ensureInitialized();

              ServerWorld overworld = minecraftServer.getOverworld();
              overworld.setTimeOfDay(6000L);

              for (int index = -10; index <= 10; index++) {
                for (int index2 = -10; index2 <= 10; index2++) {
                  overworld.setBlockState(
                      new BlockPos(index, 140, index2),
                      (index % 3 != 0 && index2 % 3 != 0
                              ? Blocks.SMOOTH_QUARTZ
                              : Blocks.POLISHED_ANDESITE)
                          .getDefaultState());
                }
              }

              ServerPlayerEntity first =
                  minecraftServer.getPlayerManager().getPlayerList().getFirst();
              first.changeGameMode(GameMode.CREATIVE);
              first.setPosition(0.5, 141.0, 0.5);
            });
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          minecraftClient2.player.setPosition(0.5, 141.0, 0.5);
          minecraftClient2.player.setVelocity(Vec3d.ZERO);
          minecraftClient2.player.setYaw(0.0F);
          minecraftClient2.player.setPitch(-12.0F);
          minecraftClient2.player.bodyYaw =
              minecraftClient2.player.lastBodyYaw =
                  minecraftClient2.player.headYaw = minecraftClient2.player.lastHeadYaw = 0.0F;
          minecraftClient2.player.resetPosition();
        });
    clientGameTestContext.waitTicks(45);

    try {
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
            emoteCatalog.cancelOnActionControl.setValue(true);
            emoteCatalog.automaticF5Control.setValue(true);
            minecraftClient3.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient3.setScreen(new EmoteWheelScreen(emoteCatalog, 66));
          });
      clientGameTestContext.waitTicks(8);
      double[] onClient =
          clientGameTestContext.computeOnClient(
              minecraftClient4 -> {
                double doubleValue =
                    (-Math.PI / 2)
                        + computeInt()
                            * Math.PI
                            * 2.0
                            / ClientInitializer.getEmoteCatalog().getList().size();
                double doubleValue2 = 130.0F * OverlayRenderer.computeFloat();
                return new double[] {
                  minecraftClient4.getWindow().getWidth() / 2.0
                      + Math.cos(doubleValue) * doubleValue2,
                  minecraftClient4.getWindow().getHeight() / 2.0
                      + Math.sin(doubleValue) * doubleValue2
                };
              });
      clientGameTestContext.getInput().setCursorPos(onClient[0], onClient[1]);
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("passinho-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient5.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "wheel release starts Passinho");
            apply(
                minecraftClient5.currentScreen == null
                    && minecraftClient5.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "wheel closes and F5 activates");
          });
      clientGameTestContext.waitTicks(8);
      Vec3d onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient6 -> {
                return minecraftClient6.player.getEntityPos();
              });
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                arrayList.size() == 1
                    && minecraftClient7
                        .getSoundManager()
                        .isPlaying((SoundInstance) arrayList.getFirst()),
                "one OGG actually plays");
            minecraftClient7.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });

      for (float floatValue2 :
          new float[] {
            0.35F, 1.03F, 2.03F, 2.77F, 3.23F, 3.6F, 4.0F, 4.73F, 5.43F, 6.43F, 6.9F, 7.57F, 8.3F,
            9.7F, 10.37F, 11.07F
          }) {
        clientGameTestContext.runOnClient(
            minecraftClient8 -> {
              apply(floatValue2);
            });
        clientGameTestContext.waitTick();
        clientGameTestContext.takeScreenshot("passinho-" + Math.round(floatValue2 * 1000.0F));
      }

      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient9.player.getEntityPos().distanceTo(onClient2) < 0.001,
                "dance never changes real player position");
            ClientInitializer.getEmoteCatalog().update();
            apply(
                minecraftClient9.options.getPerspective() == Perspective.THIRD_PERSON_FRONT,
                "manual camera choice survives stop");
            minecraftClient9.options.setPerspective(Perspective.FIRST_PERSON);
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            minecraftClient9.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient10.options.forwardKey.setPressed(false);
            apply(!ClientInitializer.getEmoteCatalog().test(), "walking cancels dance");
            apply(
                minecraftClient10.options.getPerspective() == Perspective.FIRST_PERSON,
                "cancel restores camera");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            apply(11.5F);
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(!ClientInitializer.getEmoteCatalog().test(), "clip ends at 11.433 seconds");
          });
      clientGameTestContext.waitTicks(24);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            final var referenceReceiver51 = minecraftClient12.getSoundManager();
            apply(
                arrayList.stream()
                    .noneMatch(
                        (referenceArgument51_0) ->
                            referenceReceiver51.isPlaying(
                                (net.minecraft.client.sound.SoundInstance) referenceArgument51_0)),
                "all soundtrack instances stop");
          });
      ClientInitializer.logger.info(
          "Passinho verified: 343 frames x 2 skin rigs, rendered geometry, knees/elbows, source"
              + " OGG, wheel, camera, cancellation, completion");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient13.options.forwardKey.setPressed(false);
            minecraftClient13.getSoundManager().unregisterListener(soundInstanceListener);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    EmoteDefinition emoteDefinition =
        (EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(computeInt());
    apply(
        emoteDefinition.emoteAnimation() == EmoteAnimation.PASSINHO_DO_JAMAL
            && emoteDefinition.timestamp() == 11433L
            && emoteDefinition.amount() == 170.0F
            && !emoteDefinition.enabled(),
        "catalogue timing and category");

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
      PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
      playerEntityRenderState.id = -123;
      playerEntityRenderState.leftSleeveVisible =
          playerEntityRenderState.rightSleeveVisible =
              playerEntityRenderState.leftPantsLegVisible =
                  playerEntityRenderState.rightPantsLegVisible = true;
      float floatValue = 0.0F;
      float floatValue2 = 0.0F;

      for (int index = 0; index < 343; index++) {
        playerEntityModel.setAngles(playerEntityRenderState);
        PassinhoDoJamalAnimation.render(playerEntityModel, index / 30.0F, 1.0F);

        for (ModelPart modelPart : playerEntityModel.getRootPart().traverse()) {
          apply(
              Float.isFinite(
                  modelPart.originX
                      + modelPart.originY
                      + modelPart.originZ
                      + modelPart.pitch
                      + modelPart.yaw
                      + modelPart.roll),
              "finite pose");
        }

        if (index > 0) {
          apply(
              Math.abs(playerEntityModel.rightArm.pitch - floatValue) < 0.65F
                  && Math.abs(playerEntityModel.leftArm.pitch - floatValue2) < 0.65F,
              "no arm discontinuity between source frames");
        }

        floatValue = playerEntityModel.rightArm.pitch;
        floatValue2 = playerEntityModel.leftArm.pitch;
        HoppinStandingBeforeTestSupport8 hoppinStandingBeforeTestSupport8 =
            new HoppinStandingBeforeTestSupport8();
        playerEntityModel
            .getRootPart()
            .render(new MatrixStack(), hoppinStandingBeforeTestSupport8, 15728880, 0);
        apply(hoppinStandingBeforeTestSupport8.count >= 144, "actual skin vertices rendered");
        apply(
            hoppinStandingBeforeTestSupport8.amount <= 1.57F,
            "feet stay above floor, frame "
                + index
                + ": "
                + hoppinStandingBeforeTestSupport8.amount);
        apply(
            playerEntityModel.leftArm.hidden
                && playerEntityModel.leftArm.getChild("bunnythekenzo_emote_lower").visible,
            "elbow geometry active");
      }

      playerEntityModel.setAngles(playerEntityRenderState);
      PassinhoDoJamalAnimation.render(playerEntityModel, 4.0F, 1.0F);
      apply(
          playerEntityModel.leftLeg.pitch < playerEntityModel.rightLeg.pitch - 0.5F,
          "observed left knee lift at 4 seconds");
      playerEntityModel.setAngles(playerEntityRenderState);
      PassinhoDoJamalAnimation.render(playerEntityModel, 4.77F, 1.0F);
      apply(
          playerEntityModel.rightLeg.pitch < playerEntityModel.leftLeg.pitch - 0.5F,
          "observed right knee lift at 4.77 seconds");
      playerEntityModel.setAngles(playerEntityRenderState);
      apply(
          !playerEntityModel.leftArm.hidden
              && !playerEntityModel.leftArm.getChild("bunnythekenzo_emote_lower").visible,
          "next remote-player pose restores vanilla geometry");
      apply(
          playerEntityModel.getRootPart().originX == 0.0F
              && playerEntityModel.getRootPart().originY == 0.0F
              && playerEntityModel.getRootPart().originZ == 0.0F,
          "root translation resets");
      PassinhoDoJamalAnimation.render(playerEntityModel, 4.0F, 0.0F);
      apply(!playerEntityModel.leftArm.hidden, "zero blend leaves original skin intact");
    }
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Passinho do jamal")) {
        return index;
      }
    }

    throw new AssertionError("Missing Passinho slot");
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
      throw new AssertionError("Passinho: " + text);
    }
  }
}

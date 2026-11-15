package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.HoppinDihAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class HoppinStandingBeforeTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
              Perspective perspective = minecraftClient.options.getPerspective();
              boolean flag = emoteCatalog.automaticF5Control.getValue();
              boolean flag2 = emoteCatalog.cancelOnActionControl.getValue();
              boolean hudHidden = minecraftClient.options.hudHidden;
              int value2 = minecraftClient.options.getFov().getValue();
              return () -> {
                emoteCatalog.update();
                emoteCatalog.automaticF5Control.setValue(flag);
                emoteCatalog.cancelOnActionControl.setValue(flag2);
                minecraftClient.options.forwardKey.setPressed(false);
                minecraftClient.options.jumpKey.setPressed(false);
                minecraftClient.options.attackKey.setPressed(false);
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.hudHidden = hudHidden;
                minecraftClient.options.getFov().setValue(value2);
                minecraftClient.setScreen(null);
              };
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();

            EmoteCatalog emoteCatalog2 = ClientInitializer.getEmoteCatalog();
            EmoteDefinition emoteDefinition =
                (EmoteDefinition) emoteCatalog2.getList().get(computeInt());
            apply(
                !emoteDefinition.test()
                    && emoteDefinition.emoteAnimation() == EmoteAnimation.HOPPIN_DIH,
                "Hoppin dih is playable in its existing wheel slot");
            apply(
                emoteDefinition.timestamp() == 15045L && emoteDefinition.enabled(),
                "exact original duration, mobile dance");
            apply(
                minecraftClient2
                    .getResourceManager()
                    .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/hoppin_dih.ogg"))
                    .isPresent(),
                "original soundtrack is bundled");

            for (boolean flag3 : new boolean[] {false, true}) {
              PlayerEntityModel playerEntityModel =
                  new PlayerEntityModel(
                      minecraftClient2
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag3
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag3);
              apply(playerEntityModel);
            }

            emoteCatalog2.automaticF5Control.setValue(false);
            emoteCatalog2.cancelOnActionControl.setValue(true);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.options.getFov().setValue(50);
            minecraftClient2.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            minecraftClient2.player.setYaw(180.0F);
            minecraftClient2.player.setPitch(-13.0F);
            minecraftClient2.player.resetPosition();
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
                        new BlockPos(index, 140, index2),
                        (index % 3 != 0 && index2 % 3 != 0
                                ? Blocks.SMOOTH_QUARTZ
                                : Blocks.POLISHED_ANDESITE)
                            .getDefaultState());
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
            minecraftClient3.player.setVelocity(0.0, 0.0, 0.0);
            minecraftClient3.player.resetPosition();
          });
      clientGameTestContext.waitTicks(35);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("hoppin-standing-before");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            minecraftClient4.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(3);
      double[] onClient2 =
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
      clientGameTestContext.getInput().setCursorPos(onClient2[0], onClient2[1]);
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("hoppin-wheel-selected");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            minecraftClient6.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt()
                    && minecraftClient6.currentScreen == null,
                "releasing B over Hoppin dih starts dance and closes wheel");
          });
      double[] onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient7 -> {
                return new double[] {
                  minecraftClient7.player.getX(),
                  minecraftClient7.player.getY(),
                  minecraftClient7.player.getZ()
                };
              });
      SoundInstance onClient4 =
          clientGameTestContext.computeOnClient(
              minecraftClient8 -> {
                return getSoundInstance();
              });
      long longValue = System.nanoTime() + 22000000000L;
      int index3 = 0;

      while (clientGameTestContext.computeOnClient(
          minecraftClient9 -> {
            return ClientInitializer.getEmoteCatalog().test();
          })) {
        apply(System.nanoTime() < longValue, "routine stops at its natural end");
        clientGameTestContext.waitTicks(2);
        double onClient5 =
            clientGameTestContext.computeOnClient(
                minecraftClient10 -> {
                  return ClientInitializer.getEmoteCatalog().computeDouble();
                });
        if (onClient5 > 0.5 && onClient5 < 14.5) {
          clientGameTestContext.runOnClient(
              minecraftClient11 -> {
                apply(
                    minecraftClient11.getSoundManager().isPlaying(onClient4),
                    "original audio stays active during routine");
              });
        }

        if (index3 < 9
            && onClient5
                >= new double[] {0.8, 1.05, 3.0, 6.0, 9.0, 12.0, 13.3, 13.65, 14.25}[index3]) {
          clientGameTestContext.takeScreenshot("hoppin-dance-" + index3++);
        }
      }

      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient12.getSoundManager().isPlaying(onClient4),
                "natural finish stops soundtrack");
            apply(
                Math.abs(minecraftClient12.player.getX() - onClient3[0]) < 0.001
                    && Math.abs(minecraftClient12.player.getY() - onClient3[1]) < 0.001
                    && Math.abs(minecraftClient12.player.getZ() - onClient3[2]) < 0.001,
                "rendered hopping never moves the actual player");
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("hoppin-standing-after");
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(true);
            minecraftClient13.options.setPerspective(Perspective.FIRST_PERSON);
          });
      SoundInstance onClient6 =
          clientGameTestContext.computeOnClient(
              minecraftClient14 -> {
                ClientInitializer.getEmoteCatalog().apply(computeInt());
                apply(
                    minecraftClient14.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                    "automatic F5 starts");
                minecraftClient14.options.forwardKey.setPressed(true);
                return getSoundInstance();
              });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "walking keeps the dance playing");
            apply(
                Math.hypot(
                        minecraftClient15.player.getX() - onClient3[0],
                        minecraftClient15.player.getZ() - onClient3[2])
                    > 0.2,
                "walking moves the actual player during the dance");
            minecraftClient15.options.jumpKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt(),
                "walking and jumping keep the dance playing");
            apply(
                minecraftClient16.player.getY() > onClient3[1] + 0.2
                    && !minecraftClient16.player.isOnGround(),
                "jump lifts the actual player");
            apply(
                getSoundInstance() == onClient6
                    && minecraftClient16.getSoundManager().isPlaying(onClient6),
                "movement preserves the playing soundtrack");
            apply(
                minecraftClient16.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "movement preserves the emote camera");
            minecraftClient16.options.forwardKey.setPressed(false);
            minecraftClient16.options.jumpKey.setPressed(false);
          });
      clientGameTestContext.takeScreenshot("hoppin-walk-and-jump");
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();

            EmoteCatalog emoteCatalog3 = ClientInitializer.getEmoteCatalog();
            apply(
                minecraftClient17.player.isOnGround() && emoteCatalog3.getCount() == computeInt(),
                "dance continues after landing");
            minecraftClient17.options.attackKey.setPressed(true);
            emoteCatalog3.update2();
            minecraftClient17.options.attackKey.setPressed(false);
            apply(!emoteCatalog3.test(), "attack still immediately cancels pose");
            apply(
                minecraftClient17.options.getPerspective() == Perspective.FIRST_PERSON,
                "cancel restores previous camera");
          });
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient18.getSoundManager().isPlaying(onClient6),
                "cancelled audio channel retires");
          });
      ClientInitializer.logger.info(
          "Hoppin dih verified: both skins, 451-frame geometry sweep, floor clearance, wheel"
              + " selection, full 15.045s audio, walking, jumping, landing and attack"
              + " cancellation");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            onClient.run();
          });
    }
  }

  public static void apply(PlayerEntityModel playerEntityModel) {
    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    ProtectionPerformanceTest.ensureInitialized();
    boolean flag;
    playerEntityRenderState.id = -123;
    playerEntityRenderState.hatVisible =
        playerEntityRenderState.jacketVisible =
            playerEntityRenderState.leftPantsLegVisible =
                playerEntityRenderState.rightPantsLegVisible =
                    playerEntityRenderState.leftSleeveVisible =
                        playerEntityRenderState.rightSleeveVisible = true;
    HoppinStandingBeforeTestSupport13 hoppinStandingBeforeTestSupport13 =
        new HoppinStandingBeforeTestSupport13();

    for (int index = 0; index < 451; index++) {
      for (float floatValue : new float[] {0.0F, 0.25F, 0.5F, 0.75F, 1.0F}) {
        playerEntityModel.setAngles(playerEntityRenderState);
        EmotePoseAnimator.render(
            playerEntityModel, EmoteAnimation.HOPPIN_DIH, index / 30.0F, 146.0F, floatValue);
        hoppinStandingBeforeTestSupport13.amount = -Float.MAX_VALUE;
        hoppinStandingBeforeTestSupport13.count = 0;
        playerEntityModel
            .getRootPart()
            .render(new MatrixStack(), hoppinStandingBeforeTestSupport13, 15728880, 0);
        apply(
            hoppinStandingBeforeTestSupport13.count > 100,
            "skin and overlays submit visible geometry");
        flag = hoppinStandingBeforeTestSupport13.amount <= 1.526F;
        float amount = hoppinStandingBeforeTestSupport13.amount;
        float floatValue2 = floatValue;
        int intValue = index;
        apply(
            flag,
            "feet stay above floor including skin overlays: "
                + intValue
                + "/"
                + floatValue2
                + ": "
                + amount);
      }
    }

    playerEntityModel.setAngles(playerEntityRenderState);
    HoppinDihAnimation.render(playerEntityModel, 0.1F, 1.0F);
    float originY = playerEntityModel.getRootPart().originY;
    playerEntityModel.setAngles(playerEntityRenderState);
    HoppinDihAnimation.render(playerEntityModel, 0.46666667F, 1.0F);
    float originY2 = playerEntityModel.getRootPart().originY;
    apply(
        Math.abs(originY - originY2) < 0.002,
        "phase repeats at 11 video frames, independently of music BPM");
    playerEntityModel.setAngles(playerEntityRenderState);
    HoppinDihAnimation.render(playerEntityModel, 0.23333333F, 1.0F);
    apply(
        playerEntityModel.getRootPart().originY > originY + 1.0F,
        "landing is visibly lower than airborne apex");
    playerEntityModel.setAngles(playerEntityRenderState);
    apply(
        playerEntityModel.getRootPart().originY == 0.0F
            && Math.abs(playerEntityModel.rightLeg.pitch) < 0.01,
        "next ordinary entity pose resets the lowered rig");
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Hoppin dih")) {
        return index;
      }
    }

    throw new AssertionError("Hoppin dih missing");
  }

  public static SoundInstance getSoundInstance() {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("emoteSound");
      declaredField.setAccessible(true);
      return (SoundInstance) declaredField.get(ClientInitializer.getEmoteCatalog());
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Hoppin dih: " + text);
    }
  }
}

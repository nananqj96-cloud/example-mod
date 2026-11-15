package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
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
import bunnythekenzo.client.OrangeJusticeAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class OrangeJusticeStandingBeforeTest {

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
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.hudHidden = hudHidden;
                minecraftClient.options.getFov().setValue(value2);
                minecraftClient.options.forwardKey.setPressed(false);
                minecraftClient.options.jumpKey.setPressed(false);
                minecraftClient.options.attackKey.setPressed(false);
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
                    && emoteDefinition.emoteAnimation() == EmoteAnimation.ORANGE_JUSTICE,
                "existing Orange justice slot is playable");
            apply(
                emoteDefinition.timestamp() == 29433L && !emoteDefinition.enabled(),
                "source duration and stationary routine");
            apply(
                minecraftClient2
                    .getResourceManager()
                    .getResource(
                        Identifier.of("bunnythekenzo", "sounds/emotes/orange_justice.ogg"))
                    .isPresent(),
                "original soundtrack bundled");
            OrangeJusticeAnimation.update();

            for (boolean flag3 : new boolean[] {false, true}) {
              apply(
                  new PlayerEntityModel(
                      minecraftClient2
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag3
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag3));
              EquipmentModelData equipmentModelData =
                  flag3 ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER_EQUIPMENT;

              for (EntityModelLayer entityModelLayer :
                  new EntityModelLayer[] {
                    (EntityModelLayer) equipmentModelData.chest(),
                    (EntityModelLayer) equipmentModelData.legs(),
                    (EntityModelLayer) equipmentModelData.feet()
                  }) {
                BipedEntityModel bipedEntityModel =
                    new BipedEntityModel(
                        minecraftClient2.getLoadedEntityModels().getModelPart(entityModelLayer));
                PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
                playerEntityRenderState.id = -456;

                for (float floatValue : new float[] {0.9F, 2.85F, 5.35F, 19.2F}) {
                  bipedEntityModel.setAngles(playerEntityRenderState);
                  OrangeJusticeAnimation.render(bipedEntityModel, floatValue, 1.0F);
                  HoppinStandingBeforeTestSupport14 hoppinStandingBeforeTestSupport14 =
                      new HoppinStandingBeforeTestSupport14();
                  bipedEntityModel
                      .getRootPart()
                      .render(new MatrixStack(), hoppinStandingBeforeTestSupport14, 15728880, 0);
                  apply(
                      hoppinStandingBeforeTestSupport14.count > 0,
                      "actual armor layer renders with joint rig");
                }

                bipedEntityModel.setAngles(playerEntityRenderState);
                apply(
                    !bipedEntityModel.rightArm.hidden && !bipedEntityModel.rightLeg.hidden,
                    "armor geometry restores");
              }
            }

            emoteCatalog2.automaticF5Control.setValue(false);
            emoteCatalog2.cancelOnActionControl.setValue(true);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.options.getFov().setValue(55);
            minecraftClient2.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            minecraftClient2.player.setYaw(180.0F);
            minecraftClient2.player.setPitch(-6.0F);
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
                                : Blocks.ORANGE_TERRACOTTA)
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
      clientGameTestContext.takeScreenshot("orange-justice-standing-before");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            minecraftClient4.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(5);
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
      clientGameTestContext.takeScreenshot("orange-justice-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            minecraftClient6.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt()
                    && minecraftClient6.currentScreen == null,
                "wheel release starts the selected dance");
          });
      SoundInstance onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient7 -> {
                return getSoundInstance();
              });
      double[] onClient4 =
          clientGameTestContext.computeOnClient(
              minecraftClient8 -> {
                return new double[] {
                  minecraftClient8.player.getX(),
                  minecraftClient8.player.getY(),
                  minecraftClient8.player.getZ()
                };
              });
      long longValue = System.nanoTime() + 40000000000L;
      double[] doubleValues =
          new double[] {
            0.9, 1.35, 1.8, 2.1, 2.85, 3.65, 4.55, 5.35, 5.95, 6.6, 7.3, 9.4, 12.4, 16.6, 19.2,
            23.2, 26.6, 28.8
          };
      int index3 = 0;

      while (clientGameTestContext.computeOnClient(
          minecraftClient9 -> {
            return ClientInitializer.getEmoteCatalog().test();
          })) {
        apply(System.nanoTime() < longValue, "natural end occurs");
        clientGameTestContext.waitTicks(1);
        double onClient5 =
            clientGameTestContext.computeOnClient(
                minecraftClient10 -> {
                  return ClientInitializer.getEmoteCatalog().computeDouble();
                });
        if (onClient5 > 0.5 && onClient5 < 29.0) {
          clientGameTestContext.runOnClient(
              minecraftClient11 -> {
                apply(
                    minecraftClient11.getSoundManager().isPlaying(onClient3),
                    "sound remains active with choreography");
              });
        }

        if (index3 < doubleValues.length && onClient5 >= doubleValues[index3]) {
          clientGameTestContext.takeScreenshot("orange-justice-pose-" + index3++);
        }
      }

      for (int index4 = 0;
          index4 < 40
              && clientGameTestContext.computeOnClient(
                  minecraftClient12 -> {
                    return minecraftClient12.getSoundManager().isPlaying(onClient3);
                  });
          index4++) {
        clientGameTestContext.waitTicks(1);
      }

      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient13.getSoundManager().isPlaying(onClient3),
                "natural finish stops audio");
            apply(
                Math.abs(minecraftClient13.player.getX() - onClient4[0]) < 0.001
                    && Math.abs(minecraftClient13.player.getY() - onClient4[1]) < 0.001
                    && Math.abs(minecraftClient13.player.getZ() - onClient4[2]) < 0.001,
                "dance never changes player position");
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("orange-justice-standing-after");
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();

            EmoteCatalog emoteCatalog3 = ClientInitializer.getEmoteCatalog();
            emoteCatalog3.automaticF5Control.setValue(true);
            minecraftClient14.options.setPerspective(Perspective.FIRST_PERSON);

            for (KeyBinding keyBinding :
                new KeyBinding[] {
                  minecraftClient14.options.forwardKey,
                  minecraftClient14.options.jumpKey,
                  minecraftClient14.options.attackKey
                }) {
              emoteCatalog3.apply(computeInt());
              apply(
                  minecraftClient14.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                  "automatic F5");
              keyBinding.setPressed(true);
              emoteCatalog3.update2();
              keyBinding.setPressed(false);
              apply(
                  !emoteCatalog3.test()
                      && minecraftClient14.options.getPerspective() == Perspective.FIRST_PERSON,
                  "action cancels and restores camera");
            }
          });
      ClientInitializer.logger.info(
          "Orange justice verified: 883 frames, both skin widths, UV-preserving joints, reset,"
              + " wheel, 29.433s playback, sound and cancellation");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            onClient.run();
          });
    }
  }

  public static void apply(PlayerEntityModel playerEntityModel) {
    ProtectionPerformanceTest.ensureInitialized();

    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    playerEntityRenderState.id = -123;
    playerEntityRenderState.hatVisible =
        playerEntityRenderState.jacketVisible =
            playerEntityRenderState.leftPantsLegVisible =
                playerEntityRenderState.rightPantsLegVisible =
                    playerEntityRenderState.leftSleeveVisible =
                        playerEntityRenderState.rightSleeveVisible = true;
    HoppinStandingBeforeTestSupport14 hoppinStandingBeforeTestSupport14 =
        new HoppinStandingBeforeTestSupport14();

    for (int index = 0; index < 883; index++) {
      playerEntityModel.setAngles(playerEntityRenderState);
      EmotePoseAnimator.render(
          playerEntityModel, EmoteAnimation.ORANGE_JUSTICE, index / 30.0F, 121.6F, 1.0F);
      hoppinStandingBeforeTestSupport14.count = 0;
      hoppinStandingBeforeTestSupport14.amount = -Float.MAX_VALUE;
      playerEntityModel
          .getRootPart()
          .render(new MatrixStack(), hoppinStandingBeforeTestSupport14, 15728880, 0);
      apply(hoppinStandingBeforeTestSupport14.count > 100, "skin geometry remains visible");
      apply(
          hoppinStandingBeforeTestSupport14.amount < 1.56F,
          "feet remain above floor: " + index + " y=" + hoppinStandingBeforeTestSupport14.amount);
    }

    playerEntityModel.setAngles(playerEntityRenderState);
    apply(
        !playerEntityModel.rightArm.hidden && !playerEntityModel.leftLeg.hidden,
        "ordinary pose restores original cubes");
    apply(
        !playerEntityModel.rightArm.getChild("bunnythekenzo_lower").visible,
        "joint children are hidden on next ordinary entity");
    apply(
        playerEntityModel.getRootPart().originY == 0.0F
            && playerEntityModel.getRootPart().originX == 0.0F,
        "root offsets do not leak between entities");
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Orange justice")) {
        return index;
      }
    }

    throw new AssertionError("Missing Orange justice");
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
      throw new AssertionError("Orange justice: " + text);
    }
  }
}

package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.util.List;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.joml.Vector3f;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.TwerkingAnimation;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;
import bunnythekenzo.client.ui.PanelScreen;

public final class TwerkingMenuTest {

  public static double value;

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              ProtectionPerformanceTest.ensureInitialized();

              ServerWorld overworld = minecraftServer.getOverworld();
              overworld.setTimeOfDay(6000L);

              for (int index = -5; index <= 5; index++) {
                for (int index2 = -5; index2 <= 5; index2++) {
                  overworld.setBlockState(
                      new BlockPos(index, 139, index2), Blocks.SMOOTH_QUARTZ.getDefaultState());
                }
              }

              ServerPlayerEntity first =
                  minecraftServer.getPlayerManager().getPlayerList().getFirst();
              first.changeGameMode(GameMode.CREATIVE);
              first.setPosition(0.5, 140.0, 0.5);
            });
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          minecraftClient2.player.setPosition(0.5, 140.0, 0.5);
          minecraftClient2.player.setVelocity(Vec3d.ZERO);
          minecraftClient2.player.setYaw(0.0F);
          minecraftClient2.player.setPitch(0.0F);
          minecraftClient2.player.bodyYaw = minecraftClient2.player.lastBodyYaw = 0.0F;
          minecraftClient2.player.headYaw = minecraftClient2.player.lastHeadYaw = 0.0F;
          minecraftClient2.player.resetPosition();
        });
    clientGameTestContext.waitTicks(40);
    ClientTickEvents.START_CLIENT_TICK.register(
        minecraftClient3 -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (Double.isFinite(value) && ClientInitializer.getEmoteCatalog().test()) {
            apply(value);
          }
        });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            minecraftClient4.options.getMaxFps().setValue(60);
            minecraftClient4.options.getGuiScale().setValue(2);
            minecraftClient4.options.setPerspective(Perspective.FIRST_PERSON);
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(true);
            ClientInitializer.getEmoteCatalog().cancelOnActionControl.setValue(true);
            ClientInitializer.getEmoteCatalog().update();
            EmoteDefinition emoteDefinition =
                (EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(computeInt());
            apply(
                !emoteDefinition.test()
                    && emoteDefinition.emoteAnimation() == EmoteAnimation.TWERKING
                    && emoteDefinition.timestamp() == 14371L,
                "existing Twerking slot has the complete clip");
            apply(
                minecraftClient4
                    .getResourceManager()
                    .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/twerking.ogg"))
                    .isPresent(),
                "original soundtrack bundled");

            for (boolean flag : new boolean[] {false, true}) {
              apply(
                  new PlayerEntityModel(
                      minecraftClient4
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag));
            }

            for (float floatValue : new float[] {0.5F, 1.0F}) {
              apply(
                  new BipedEntityModel(
                      BipedEntityModel.getModelData(new Dilation(floatValue), 0.0F)
                          .getRoot()
                          .createPart(64, 32)));
            }

            minecraftClient4.setScreen(new PanelScreen());
            ((PanelScreen) minecraftClient4.currentScreen).update();
          });
      clientGameTestContext.waitTicks(18);
      clientGameTestContext.takeScreenshot("twerking-menu");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            minecraftClient5.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(5);
      double[] onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient6 -> {
                double doubleValue =
                    (-Math.PI / 2)
                        + computeInt()
                            * Math.PI
                            * 2.0
                            / ClientInitializer.getEmoteCatalog().getList().size();
                double doubleValue2 = 130.0F * OverlayRenderer.computeFloat();
                return new double[] {
                  minecraftClient6.getWindow().getWidth() / 2.0
                      + Math.cos(doubleValue) * doubleValue2,
                  minecraftClient6.getWindow().getHeight() / 2.0
                      + Math.sin(doubleValue) * doubleValue2
                };
              });
      clientGameTestContext.getInput().setCursorPos(onClient2[0], onClient2[1]);
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("twerking-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();
            minecraftClient7.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt()
                    && minecraftClient7.currentScreen == null,
                "wheel release starts dance");
            apply(
                minecraftClient7.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "automatic F5");
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getSoundInstance() != null
                    && minecraftClient8.getSoundManager().isPlaying(getSoundInstance()),
                "original Ogg plays on the audio device");
            minecraftClient8.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient9.options.forwardKey.setPressed(false);
            apply(!ClientInitializer.getEmoteCatalog().test(), "movement cancels dance");
            apply(
                minecraftClient9.options.getPerspective() == Perspective.FIRST_PERSON,
                "cancel restores camera");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            value = 5.267;
            minecraftClient9.setScreen(new HoppinStandingBeforeTestSupport18());
          });
      clientGameTestContext.waitTicks(20);
      Vec3d onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient10 -> {
                return minecraftClient10.player.getEntityPos();
              });

      for (double value6 :
          new double[] {
            0.7, 1.3, 2.4, 3.7, 4.3, 5.267, 6.133, 7.767, 8.433, 9.733, 10.2, 11.667, 12.567, 14.267
          }) {
        clientGameTestContext.runOnClient(
            minecraftClient11 -> {
              value = value6;
            });
        clientGameTestContext.waitTicks(3);
        clientGameTestContext.takeScreenshot("twerking-pose-" + Math.round(value6 * 1000.0));
      }

      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            value = 9.733;
            ((HoppinStandingBeforeTestSupport18) minecraftClient12.currentScreen).enabled = true;
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("twerking-quarter-view");
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            minecraftClient13.player.equipStack(
                EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultStack());
            minecraftClient13.player.equipStack(
                EquipmentSlot.LEGS, Items.IRON_LEGGINGS.getDefaultStack());
            minecraftClient13.player.equipStack(
                EquipmentSlot.FEET, Items.IRON_BOOTS.getDefaultStack());
            minecraftClient13.player.equipStack(
                EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultStack());
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("twerking-armour");
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();

            for (EquipmentSlot equipmentSlot :
                new EquipmentSlot[] {
                  EquipmentSlot.CHEST,
                  EquipmentSlot.LEGS,
                  EquipmentSlot.FEET,
                  EquipmentSlot.MAINHAND
                }) {
              minecraftClient14.player.equipStack(equipmentSlot, ItemStack.EMPTY);
            }

            ((HoppinStandingBeforeTestSupport18) minecraftClient14.currentScreen).enabled = false;
          });
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            value = 9.733;
            minecraftClient15.setScreen(null);
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("twerking-in-game");
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            minecraftClient16.setScreen(new HoppinStandingBeforeTestSupport18());
          });
      if (Boolean.getBoolean("bunnythekenzo.test.twerking.capture")) {
        for (int index3 = 0; index3 < 431; index3++) {
          double value8 = index3 / 30.0;
          clientGameTestContext.runOnClient(
              minecraftClient17 -> {
                value = value8;
              });
          clientGameTestContext.waitTicks(2);
          clientGameTestContext.takeScreenshot("twerking-frame-%04d".formatted(index3));
        }
      }

      SoundInstance[] soundInstanceArray = new SoundInstance[1];
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient18.player.getEntityPos().distanceTo(onClient3) < 0.001,
                "dance never changes the entity position");
            value = Double.NaN;
            minecraftClient18.setScreen(null);
            apply(14.5);
            ClientInitializer.getEmoteCatalog().update2();
            apply(!ClientInitializer.getEmoteCatalog().test(), "ends with the full source audio");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            minecraftClient18.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            soundInstanceArray[0] = getSoundInstance();
            ClientInitializer.getEmoteCatalog().update();
            apply(
                minecraftClient18.options.getPerspective() == Perspective.THIRD_PERSON_FRONT,
                "manual camera choice respected");
          });
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient19.getSoundManager().isPlaying(soundInstanceArray[0]),
                "immediate stop cancels loaded or loading audio");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            minecraftClient20.setScreen(new GameMenuScreen(true));
          });
      clientGameTestContext.waitFor(MinecraftClient::isPaused);
      double onClient4 =
          clientGameTestContext.computeOnClient(
              minecraftClient21 -> {
                return ClientInitializer.getEmoteCatalog().computeDouble();
              });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                Math.abs(ClientInitializer.getEmoteCatalog().computeDouble() - onClient4) < 0.001,
                "pause holds source time with audio");
            minecraftClient22.setScreen(null);
          });
      clientGameTestContext.waitFor(
          minecraftClient23 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !minecraftClient23.isPaused();
          });
      clientGameTestContext.waitFor(
          minecraftClient24 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !ClientInitializer.getEmoteCatalog().test();
          },
          1800);
      ClientInitializer.logger.info(
          "Twerking verified: 431 source poses, classic/slim/armour, connected hips, turns,"
              + " alternating knees, source audio, wheel, cancellation, pause and full playback");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient25 -> {
            value = Double.NaN;
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient25.options.forwardKey.setPressed(false);
            minecraftClient25.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void apply(BipedEntityModel bipedEntityModel) {
    ProtectionPerformanceTest.ensureInitialized();

    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    playerEntityRenderState.id = -123456;
    playerEntityRenderState.leftSleeveVisible =
        playerEntityRenderState.rightSleeveVisible =
            playerEntityRenderState.leftPantsLegVisible =
                playerEntityRenderState.rightPantsLegVisible = true;

    for (float floatValue : new float[] {0.2F, 0.5F, 1.0F}) {
      float floatValue2 = 0.0F;

      for (int index = 0; index < 431; index++) {
        bipedEntityModel.setAngles(playerEntityRenderState);
        EmotePoseAnimator.render(
            bipedEntityModel, EmoteAnimation.TWERKING, index / 30.0F, 145.0F, floatValue);

        for (ModelPart modelPart : bipedEntityModel.getRootPart().traverse()) {
          apply(
              Float.isFinite(
                  modelPart.originX
                      + modelPart.originY
                      + modelPart.originZ
                      + modelPart.pitch
                      + modelPart.yaw
                      + modelPart.roll),
              "finite skin pose");
        }

        apply(Math.abs(bipedEntityModel.getRootPart().originY) < 8.0F, "bounded grounded movement");
        Vector3f vector3f =
            new Vector3f(0.0F, 12.0F, 0.0F)
                .rotateX(bipedEntityModel.body.pitch)
                .rotateY(bipedEntityModel.body.yaw)
                .rotateZ(bipedEntityModel.body.roll)
                .add(
                    bipedEntityModel.body.originX,
                    bipedEntityModel.body.originY,
                    bipedEntityModel.body.originZ);
        apply(
            vector3f.distance(0.0F, 12.0F, 0.0F) < 0.001,
            "torso stays connected to pelvis at every blend");
        if (index > 0 && floatValue == 1.0F) {
          apply(
              Math.abs(
                      Math.IEEEremainder(
                          bipedEntityModel.getRootPart().yaw - floatValue2, Math.PI * 2))
                  < 0.33,
              "turns have no single-frame jumps");
        }

        floatValue2 = bipedEntityModel.getRootPart().yaw;
      }
    }

    bipedEntityModel.setAngles(playerEntityRenderState);
    TwerkingAnimation.render(bipedEntityModel, 5.267F, 1.0F);
    apply(
        bipedEntityModel.rightLeg.getChild("bunnythekenzo_emote_lower").pitch
            > bipedEntityModel.leftLeg.getChild("bunnythekenzo_emote_lower").pitch + 0.8F,
        "first accented knee is right");
    bipedEntityModel.setAngles(playerEntityRenderState);
    TwerkingAnimation.render(bipedEntityModel, 6.133F, 1.0F);
    apply(
        bipedEntityModel.leftLeg.getChild("bunnythekenzo_emote_lower").pitch
            > bipedEntityModel.rightLeg.getChild("bunnythekenzo_emote_lower").pitch + 0.8F,
        "next accented knee is left");
    bipedEntityModel.setAngles(playerEntityRenderState);
    TwerkingAnimation.render(bipedEntityModel, 2.533F, 1.0F);
    apply(Math.cos(bipedEntityModel.getRootPart().yaw) < -0.99, "intro faces the reference camera");
    bipedEntityModel.setAngles(playerEntityRenderState);
    TwerkingAnimation.render(bipedEntityModel, 9.733F, 1.0F);
    apply(
        Math.cos(bipedEntityModel.getRootPart().yaw) > 0.99
            && bipedEntityModel.rightLeg.roll > 0.3F
            && bipedEntityModel.leftLeg.roll < -0.3F,
        "middle phrase faces away with both knees open");
    bipedEntityModel.setAngles(playerEntityRenderState);
    apply(
        !bipedEntityModel.rightArm.hidden && !bipedEntityModel.leftLeg.hidden,
        "idle/remote player uses original skin geometry");
    apply(
        !bipedEntityModel.rightArm.getChild("bunnythekenzo_emote_upper").visible,
        "split geometry is hidden after stop");
    TwerkingAnimation.render(bipedEntityModel, 9.733F, 0.0F);
    apply(
        !bipedEntityModel.rightArm.hidden && bipedEntityModel.getRootPart().yaw == 0.0F,
        "zero blend preserves idle model");
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Twerking")) {
        return index;
      }
    }

    throw new AssertionError("Twerking missing");
  }

  public static SoundInstance getSoundInstance() {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("emoteSound");
      declaredField.setAccessible(true);
      return (SoundInstance) declaredField.get(ClientInitializer.getEmoteCatalog());
    } catch (ReflectiveOperationException failure2) {
      throw new AssertionError(failure2);
    }
  }

  public static void apply(double doubleValue) {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("timestamp");
      declaredField.setAccessible(true);
      declaredField.setLong(
          ClientInitializer.getEmoteCatalog(), System.nanoTime() - (long) (doubleValue * 1.0E9));
    } catch (ReflectiveOperationException failure2) {
      throw new AssertionError(failure2);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Twerking: " + text);
    }
  }

  static {
    value = Double.NaN;
  }
}

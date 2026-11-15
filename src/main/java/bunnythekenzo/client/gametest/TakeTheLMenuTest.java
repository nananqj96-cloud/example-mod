package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;
import bunnythekenzo.client.ui.PanelScreen;

public final class TakeTheLMenuTest {
  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
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
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.getGuiScale().setValue(2);
            minecraftClient2.options.setPerspective(Perspective.FIRST_PERSON);
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(true);
            ClientInitializer.getEmoteCatalog().cancelOnActionControl.setValue(true);
            ClientInitializer.getEmoteCatalog().update();
            EmoteDefinition emoteDefinition =
                (EmoteDefinition) ClientInitializer.getEmoteCatalog().getList().get(computeInt());
            apply(
                !emoteDefinition.test()
                    && emoteDefinition.emoteAnimation() == EmoteAnimation.TAKE_THE_L,
                "Take the L is playable in the existing slot");
            apply(
                minecraftClient2
                    .getResourceManager()
                    .getResource(Identifier.of("bunnythekenzo", "sounds/emotes/take_the_l.ogg"))
                    .isPresent(),
                "original soundtrack bundled");

            for (boolean flag : new boolean[] {false, true}) {
              PlayerEntityModel playerEntityModel =
                  new PlayerEntityModel(
                      minecraftClient2
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag);
              apply(playerEntityModel);
            }

            for (float floatValue : new float[] {0.5F, 1.0F}) {
              apply(
                  new BipedEntityModel(
                      BipedEntityModel.getModelData(new Dilation(floatValue), 0.0F)
                          .getRoot()
                          .createPart(64, 32)));
            }

            apply(minecraftClient2);
            minecraftClient2.setScreen(new PanelScreen());
            ((PanelScreen) minecraftClient2.currentScreen).update();
          });
      clientGameTestContext.waitTicks(18);
      clientGameTestContext.takeScreenshot("take-the-l-menu");
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            minecraftClient3.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(5);
      double[] onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient4 -> {
                return new double[] {
                  minecraftClient4.getWindow().getWidth() / 2.0,
                  minecraftClient4.getWindow().getHeight() / 2.0
                      - 130.0F * OverlayRenderer.computeFloat()
                };
              });
      clientGameTestContext.getInput().setCursorPos(onClient2[0], onClient2[1]);
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("take-the-l-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();
            minecraftClient5.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt()
                    && minecraftClient5.currentScreen == null,
                "wheel release starts dance");
            apply(
                minecraftClient5.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "automatic F5");
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            SoundInstance soundInstance = getSoundInstance();
            apply(
                soundInstance != null
                    && minecraftClient6.getSoundManager().isPlaying(soundInstance),
                "original Ogg plays on sound device");
            minecraftClient6.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient7.options.forwardKey.setPressed(false);
            apply(!ClientInitializer.getEmoteCatalog().test(), "movement cancels dance");
            apply(
                minecraftClient7.options.getPerspective() == Perspective.FIRST_PERSON,
                "cancel restores camera");
          });
      clientGameTestContext.takeScreenshot("take-the-l-cancelled-first-person");
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            minecraftClient8.setScreen(new HoppinStandingBeforeTestSupport16());
          });

      for (int index = 0; index < 24; index++) {
        int count = index;
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              ((HoppinStandingBeforeTestSupport16) minecraftClient9.currentScreen).count = count;
            });
        clientGameTestContext.waitTicks(2);
        clientGameTestContext.takeScreenshot("take-the-l-frame-%02d".formatted(index));
      }

      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            ((HoppinStandingBeforeTestSupport16) minecraftClient10.currentScreen).count = 2;
            ((HoppinStandingBeforeTestSupport16) minecraftClient10.currentScreen).enabled = true;
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("take-the-l-quarter-view");
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            minecraftClient11.player.equipStack(
                EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultStack());
            minecraftClient11.player.equipStack(
                EquipmentSlot.LEGS, Items.IRON_LEGGINGS.getDefaultStack());
            minecraftClient11.player.equipStack(
                EquipmentSlot.FEET, Items.IRON_BOOTS.getDefaultStack());
            minecraftClient11.player.equipStack(
                EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultStack());
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("take-the-l-armour");
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            EquipmentSlot[] equipmentSlotArray =
                new EquipmentSlot[] {
                  EquipmentSlot.CHEST,
                  EquipmentSlot.LEGS,
                  EquipmentSlot.FEET,
                  EquipmentSlot.MAINHAND
                };
            ProtectionPerformanceTest.ensureInitialized();

            for (EquipmentSlot equipmentSlot : equipmentSlotArray) {
              minecraftClient12.player.equipStack(equipmentSlot, ItemStack.EMPTY);
            }
          });
      SoundInstance[] soundInstanceArray = new SoundInstance[1];
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient13.setScreen(null);
            apply(26.0);
            ClientInitializer.getEmoteCatalog().update2();
            apply(
                !ClientInitializer.getEmoteCatalog().test(),
                "ends with music, excludes source's silent tail");
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            minecraftClient13.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            soundInstanceArray[0] = getSoundInstance();
            ClientInitializer.getEmoteCatalog().update();
            apply(
                minecraftClient13.options.getPerspective() == Perspective.THIRD_PERSON_FRONT,
                "manual camera choice respected");
          });
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient14.getSoundManager().isPlaying(soundInstanceArray[0]),
                "stop stops soundtrack");
          });
      ClientInitializer.logger.info(
          "Take the L verified: classic/slim joints, continuous cycle, remote reset, first-person"
              + " hand cleanup, music, wheel, camera, cancellation, end and 24 rendered frames");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient15.options.forwardKey.setPressed(false);
            minecraftClient15.setScreen(null);
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
    float floatValue = 0.0F;

    for (int index = 0; index < 96; index++) {
      bipedEntityModel.setAngles(playerEntityRenderState);
      EmotePoseAnimator.render(
          bipedEntityModel, EmoteAnimation.TAKE_THE_L, index / 120.0F, 150.0F, 1.0F);
      apply(
          bipedEntityModel.rightArm.hidden && bipedEntityModel.leftLeg.hidden,
          "split skin geometry enabled");
      apply(
          Float.isFinite(bipedEntityModel.getRootPart().originY)
              && Math.abs(bipedEntityModel.getRootPart().originY) < 8.0F,
          "bounded grounded bounce");
      floatValue = Math.max(floatValue, Math.abs(bipedEntityModel.getRootPart().originY));
    }

    apply(floatValue > 0.2F, "dance includes a visible hop");
    bipedEntityModel.setAngles(playerEntityRenderState);
    EmotePoseAnimator.render(bipedEntityModel, EmoteAnimation.TAKE_THE_L, 0.07F, 150.0F, 1.0F);
    float roll = bipedEntityModel.leftLeg.roll;
    bipedEntityModel.setAngles(playerEntityRenderState);
    EmotePoseAnimator.render(bipedEntityModel, EmoteAnimation.TAKE_THE_L, 0.47F, 150.0F, 1.0F);
    apply(roll < -1.2F && bipedEntityModel.rightLeg.roll > 1.2F, "alternating wide side kicks");
    float roll2 = bipedEntityModel.rightLeg.roll;
    bipedEntityModel.setAngles(playerEntityRenderState);
    EmotePoseAnimator.render(bipedEntityModel, EmoteAnimation.TAKE_THE_L, 1.27F, 150.0F, 1.0F);
    apply(
        Math.abs(roll2 - bipedEntityModel.rightLeg.roll) < 1.0E-4F,
        "24-frame cycle repeats without drift");
    bipedEntityModel.setAngles(playerEntityRenderState);
    apply(
        !bipedEntityModel.rightArm.hidden && !bipedEntityModel.leftLeg.hidden,
        "remote/idle player uses original limbs");
    apply(
        !bipedEntityModel.rightArm.getChild("bunnythekenzo_emote_upper").visible,
        "no joint geometry leaks after stop");
  }

  public static void apply(MinecraftClient minecraftClient) {
    var playerRenderer =
        minecraftClient.getEntityRenderDispatcher().getPlayerRenderer(minecraftClient.player);
    PlayerEntityModel playerEntityModel =
        (net.minecraft.client.render.entity.model.PlayerEntityModel) (playerRenderer.getModel());
    OrderedRenderCommandQueueImpl orderedRenderCommandQueueImpl =
        new OrderedRenderCommandQueueImpl();
    ProtectionPerformanceTest.ensureInitialized();

    for (boolean flag : new boolean[] {true, false}) {
      for (String text : new String[] {"movement", "stop", "end", "camera"}) {
        minecraftClient.options.setPerspective(Perspective.FIRST_PERSON);
        ClientInitializer.getEmoteCatalog().apply(computeInt());
        apply(2.0);
        PlayerEntityRenderState renderState = playerRenderer.createRenderState();
        playerRenderer.updateRenderState(minecraftClient.player, renderState, 0.0F);
        playerEntityModel.setAngles(renderState);
        ModelPart child = playerEntityModel.rightArm.getChild("bunnythekenzo_emote_lower");
        ModelPart child2 = child.getChild("bunnythekenzo_l_gesture");
        apply(child.visible && child2.visible, "L visible before " + text);
        switch (text) {
          case "movement":
            minecraftClient.options.forwardKey.setPressed(true);
            ClientInitializer.getEmoteCatalog().update2();
            minecraftClient.options.forwardKey.setPressed(false);
            break;
          case "stop":
            ClientInitializer.getEmoteCatalog().update();
            break;
          case "end":
            apply(26.0);
            ClientInitializer.getEmoteCatalog().update2();
            break;
          case "camera":
            minecraftClient.options.setPerspective(Perspective.FIRST_PERSON);
        }

        apply(
            minecraftClient.options.getPerspective() == Perspective.FIRST_PERSON,
            "first-person after " + text);
        Identifier texture = playerRenderer.getTexture(renderState);
        if (flag) {
          playerRenderer.renderRightArm(
              new MatrixStack(), orderedRenderCommandQueueImpl, 15728880, texture, true);
        } else {
          playerRenderer.renderLeftArm(
              new MatrixStack(), orderedRenderCommandQueueImpl, 15728880, texture, false);
        }

        apply(!child2.visible && !child.visible, "no first-person L after " + text);

        for (ModelPart modelPart :
            new ModelPart[] {
              playerEntityModel.rightArm,
              playerEntityModel.leftArm,
              playerEntityModel.rightSleeve,
              playerEntityModel.leftSleeve
            }) {
          apply(!modelPart.hidden, "original hand/sleeve restored after " + text);
          apply(
              !modelPart.getChild("bunnythekenzo_emote_upper").visible
                  && !modelPart.getChild("bunnythekenzo_emote_lower").visible,
              "no split hand/sleeve after " + text);
        }

        apply(
            playerEntityModel.rightSleeve.visible == flag
                && playerEntityModel.leftSleeve.visible == flag,
            "first-person sleeve visibility preserved");
        if (text.equals("camera")) {
          apply(ClientInitializer.getEmoteCatalog().test(), "camera switch preserves dance");
          playerEntityModel.setAngles(renderState);
          apply(child.visible && child2.visible, "third-person dance can restore the L");
        } else {
          apply(!ClientInitializer.getEmoteCatalog().test(), text + " stops dance");
        }

        ClientInitializer.getEmoteCatalog().update();
        orderedRenderCommandQueueImpl.clear();
      }
    }
  }

  public static int computeInt() {
    ProtectionPerformanceTest.ensureInitialized();

    List list = ClientInitializer.getEmoteCatalog().getList();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).string().equals("Take the L")) {
        return index;
      }
    }

    throw new AssertionError("Take the L missing");
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

  public static void apply(double doubleValue) {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("timestamp");
      declaredField.setAccessible(true);
      declaredField.setLong(
          ClientInitializer.getEmoteCatalog(), System.nanoTime() - (long) (doubleValue * 1.0E9));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Take the L: " + text);
    }
  }
}

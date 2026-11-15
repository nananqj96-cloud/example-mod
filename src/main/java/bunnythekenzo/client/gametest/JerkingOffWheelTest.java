package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.network.OtherClientPlayerEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.joml.Vector3f;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ClientSounds;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteConfettiParticle;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.mixin.ModelPartAccessor;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class JerkingOffWheelTest {
  public static final long timestamp = 6000L;

  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    Perspective onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              return minecraftClient2.options.getPerspective();
            });
    ItemStack onClient3 =
        clientGameTestContext.computeOnClient(
            minecraftClient3 -> {
              return minecraftClient3.player.getMainHandStack().copy();
            });
    ItemStack onClient4 =
        clientGameTestContext.computeOnClient(
            minecraftClient4 -> {
              return minecraftClient4.player.getOffHandStack().copy();
            });
    boolean flag = ClientInitializer.getEmoteCatalog().cancelOnActionControl.getValue();
    boolean flag2 = ClientInitializer.getEmoteCatalog().automaticF5Control.getValue();

    try {
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            ClientInitializer.getEmoteCatalog().update();
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getEmoteCatalog().cancelOnActionControl.setValue(true);
            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(true);
            minecraftClient5.options.setPerspective(Perspective.FIRST_PERSON);

            for (boolean flag3 : new boolean[] {false, true}) {
              apply(
                  new PlayerEntityModel(
                      minecraftClient5
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag3
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag3));
            }

            for (float floatValue : new float[] {0.5F, 1.0F}) {
              apply(
                  new BipedEntityModel(
                      BipedEntityModel.getModelData(new Dilation(floatValue), 0.0F)
                          .getRoot()
                          .createPart(64, 32)));
            }

            minecraftClient5.player.equipStack(
                EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultStack());
            minecraftClient5.player.equipStack(
                EquipmentSlot.OFFHAND, Items.SHIELD.getDefaultStack());
            apply(minecraftClient5, false);
            minecraftClient5.setScreen(
                new EmoteWheelScreen(ClientInitializer.getEmoteCatalog(), 66));
          });
      clientGameTestContext.waitTicks(8);
      double[] onClient5 =
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
      clientGameTestContext.getInput().setCursorPos(onClient5[0], onClient5[1]);
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("jerking-off-wheel");
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();
            minecraftClient7.currentScreen.keyReleased(new KeyInput(66, 0, 0));
            apply(
                ClientInitializer.getEmoteCatalog().getCount() == computeInt()
                    && minecraftClient7.currentScreen == null,
                "wheel starts routine");
            apply(
                minecraftClient7.options.getPerspective() == Perspective.THIRD_PERSON_BACK,
                "automatic F5");
            apply(minecraftClient7, true);
            apply(minecraftClient7);
            apply(
                getSoundInstance() != null
                    && getSoundInstance().getId().equals(ClientSounds.soundEvent37.id()),
                "routine owns the original buildup soundtrack");
            minecraftClient7.setScreen(new HoppinStandingBeforeTestSupport7());
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            apply(
                minecraftClient8.getSoundManager().isPlaying(getSoundInstance()),
                "bundled music reaches a playing audio channel");
          });
      Vec3d onClient6 =
          clientGameTestContext.computeOnClient(
              minecraftClient9 -> {
                return minecraftClient9.player.getEntityPos();
              });

      for (int index = 0; index < 8; index++) {
        int count = index;
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              ((HoppinStandingBeforeTestSupport7) minecraftClient10.currentScreen).count = count;
            });
        clientGameTestContext.waitTicks(2);
        clientGameTestContext.takeScreenshot("jerking-off-frame-%02d".formatted(index));
      }

      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient11.player.getEntityPos().distanceTo(onClient6) < 0.001,
                "render animation never moves player");
            minecraftClient11.setScreen(null);
            SoundInstance soundInstance = getSoundInstance();
            minecraftClient11.options.forwardKey.setPressed(true);
            ClientInitializer.getEmoteCatalog().update2();
            minecraftClient11.options.forwardKey.setPressed(false);
            apply(!ClientInitializer.getEmoteCatalog().test(), "walking cancels");
            apply(
                getSoundInstance() == null && !soundInstance.canPlay(),
                "walking also cancels music");
            apply(
                minecraftClient11.options.getPerspective() == Perspective.FIRST_PERSON,
                "camera restored");
            apply(minecraftClient11, false);
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            apply(12.01);
            ClientInitializer.getEmoteCatalog().update2();
            apply(
                !ClientInitializer.getEmoteCatalog().test() && getSoundInstance() == null,
                "animation and music end after twelve seconds");
            apply(minecraftClient11, false);
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            ClientInitializer.getEmoteCatalog().apply(2);
            apply(getSoundInstance() != null, "switching to musical emote still starts its audio");
            SoundInstance soundInstance2 = getSoundInstance();
            apply(minecraftClient11, false);
            ClientInitializer.getEmoteCatalog().apply(computeInt());
            apply(
                getSoundInstance() != null
                    && getSoundInstance() != soundInstance2
                    && !soundInstance2.canPlay(),
                "switching replaces the previous soundtrack");
            apply(minecraftClient11, true);
            ClientInitializer.getEmoteCatalog().update();
          });
      ClientInitializer.logger.info(
          "Jerking off verified: accelerating arms, audible buildup, classic/slim/armour rigs, hand"
              + " position, remote isolation, held-item restoration, camera, music cancellation and"
              + " eight frames");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ClientInitializer.getEmoteCatalog().update();
            ClientInitializer.getEmoteCatalog().cancelOnActionControl.setValue(flag);
            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(flag2);
            minecraftClient12.options.forwardKey.setPressed(false);
            minecraftClient12.options.setPerspective(onClient2);
            minecraftClient12.player.equipStack(EquipmentSlot.MAINHAND, onClient3);
            minecraftClient12.player.equipStack(EquipmentSlot.OFFHAND, onClient4);
            minecraftClient12.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient, boolean flag) {
    ProtectionPerformanceTest.ensureInitialized();

    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    minecraftClient
        .getEntityRenderDispatcher()
        .getPlayerRenderer(minecraftClient.player)
        .updateRenderState(minecraftClient.player, playerEntityRenderState, 1.0F);
    apply(
        playerEntityRenderState.rightHandItemState.isEmpty() == flag
            && playerEntityRenderState.leftHandItemState.isEmpty() == flag,
        "both rendered items " + (flag ? "hidden" : "visible"));
    apply(
        minecraftClient.player.getMainHandStack().isOf(Items.DIAMOND_SWORD)
            && minecraftClient.player.getOffHandStack().isOf(Items.SHIELD),
        "inventory preserved");
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    OtherClientPlayerEntity otherClientPlayerEntity =
        new OtherClientPlayerEntity(
            minecraftClient.world, new GameProfile(UUID.randomUUID(), "EmoteObserver"));
    otherClientPlayerEntity.equipStack(
        EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultStack());
    otherClientPlayerEntity.equipStack(EquipmentSlot.OFFHAND, Items.SHIELD.getDefaultStack());
    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    minecraftClient
        .getEntityRenderDispatcher()
        .getPlayerRenderer(otherClientPlayerEntity)
        .updateRenderState(otherClientPlayerEntity, playerEntityRenderState, 1.0F);
    apply(
        !playerEntityRenderState.rightHandItemState.isEmpty()
            && !playerEntityRenderState.leftHandItemState.isEmpty(),
        "remote items stay visible");
  }

  public static void apply(BipedEntityModel bipedEntityModel) {
    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    ProtectionPerformanceTest.ensureInitialized();

    playerEntityRenderState.id = -123456;
    float MAX_VALUE = Float.MAX_VALUE;
    float floatValue = -Float.MAX_VALUE;
    float floatValue2 = 0.0F;
    float floatValue3 = 0.0F;
    int index = 0;
    int index2 = 0;
    Vector3f vector3f = null;

    for (int index3 = 0; index3 <= 1440; index3++) {
      bipedEntityModel.setAngles(playerEntityRenderState);
      EmotePoseAnimator.render(
          bipedEntityModel, EmoteAnimation.JERKING_OFF, index3 / 120.0F, 78.0F, 1.0F);
      ModelPart child = bipedEntityModel.rightArm.getChild("bunnythekenzo_emote_lower");
      float floatValue4 = child.pitch - floatValue2;
      if (index3 > 1 && floatValue4 * floatValue3 < 0.0F) {
        if (index3 < 240) {
          index++;
        }

        if (index3 > 1080 && index3 < 1320) {
          index2++;
        }
      }

      floatValue3 = floatValue4;
      floatValue2 = child.pitch;
      Cuboid cuboid =
          (Cuboid)
              ((ModelPartAccessor) (Object) (bipedEntityModel.rightArm)).getCuboids().getFirst();
      Vector3f vector3f2 =
          new Vector3f((cuboid.minX + cuboid.maxX) / 2.0F, cuboid.maxY - 1.0F - child.originY, 0.0F)
              .rotateX(child.pitch)
              .add(0.0F, child.originY, 0.0F)
              .rotateX(bipedEntityModel.rightArm.pitch)
              .rotateY(bipedEntityModel.rightArm.yaw)
              .rotateZ(bipedEntityModel.rightArm.roll)
              .add(
                  bipedEntityModel.rightArm.originX,
                  bipedEntityModel.rightArm.originY,
                  bipedEntityModel.rightArm.originZ);
      apply(
          vector3f2.isFinite() && Float.isFinite(bipedEntityModel.getRootPart().originY),
          "finite pose");
      Vector3f vector3f3 =
          new Vector3f(vector3f2)
              .sub(
                  bipedEntityModel.body.originX,
                  bipedEntityModel.body.originY,
                  bipedEntityModel.body.originZ)
              .rotateZ(-bipedEntityModel.body.roll)
              .rotateY(-bipedEntityModel.body.yaw)
              .rotateX(-bipedEntityModel.body.pitch);
      apply(
          vector3f3.y > 8.0F && vector3f3.y < 11.7F,
          "hand stays just below the navel: " + vector3f3);
      apply(
          Math.abs(vector3f3.x) < 1.8F && vector3f3.z < -2.8F,
          "hand stays centered in front of the lower belly: " + vector3f3);
      if (vector3f != null) {
        apply(vector3f2.distance(vector3f) < 0.8F, "continuous hand movement");
      }

      MAX_VALUE = Math.min(MAX_VALUE, vector3f2.y);
      floatValue = Math.max(floatValue, vector3f2.y);
      vector3f = vector3f2;
    }

    apply(
        floatValue - MAX_VALUE > 0.7F && floatValue - MAX_VALUE < 2.0F,
        "visible compact forearm travel");
    apply(
        index >= 1 && index <= 4 && index2 >= 16 && index2 > index * 4,
        "arm motion grows from slow to fast: " + index + " / " + index2);
    bipedEntityModel.setAngles(playerEntityRenderState);
    apply(
        !bipedEntityModel.rightArm.hidden
            && !bipedEntityModel.rightArm.getChild("bunnythekenzo_emote_lower").visible,
        "next idle/remote pose restores vanilla limbs");
  }

  public static void checkConfetti(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    boolean flag = ClientInitializer.getEmoteCatalog().automaticF5Control.getValue();
    Perspective onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              return minecraftClient2.options.getPerspective();
            });
    int onClient3 =
        clientGameTestContext.computeOnClient(
            minecraftClient3 -> {
              return minecraftClient3.options.getFov().getValue();
            });
    boolean onClient4 =
        clientGameTestContext.computeOnClient(
            minecraftClient4 -> {
              return minecraftClient4.options.hudHidden;
            });

    try {
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerWorld overworld = minecraftServer.getOverworld();
                overworld.setTimeOfDay(timestamp);

                for (int index = -12; index <= 12; index++) {
                  for (int index2 = -12; index2 <= 12; index2++) {
                    overworld.setBlockState(
                        new BlockPos(index, 140, index2), Blocks.DEEPSLATE_TILES.getDefaultState());
                  }
                }

                for (int index3 = -5; index3 <= 5; index3++) {
                  for (int index4 = 141; index4 <= 144; index4++) {
                    overworld.setBlockState(
                        new BlockPos(index3, index4, 7),
                        Blocks.POLISHED_ANDESITE.getDefaultState());
                  }
                }

                ServerPlayerEntity first =
                    minecraftServer.getPlayerManager().getPlayerList().getFirst();
                first.changeGameMode(GameMode.CREATIVE);
                first.setPosition(0.5, 141.0, 0.5);
              });
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            minecraftClient5.setScreen(null);
            minecraftClient5.player.setPosition(0.5, 141.0, 0.5);
            minecraftClient5.player.setVelocity(Vec3d.ZERO);
            minecraftClient5.player.setYaw(0.0F);
            minecraftClient5.player.bodyYaw = 0.0F;
            minecraftClient5.player.setPitch(12.0F);
            minecraftClient5.player.resetPosition();
            minecraftClient5.options.hudHidden = true;
            minecraftClient5.options.getFov().setValue(80);
            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(false);
            minecraftClient5.options.setPerspective(Perspective.THIRD_PERSON_BACK);
          });
      clientGameTestContext.waitTicks(35);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            apply(
                minecraftClient6
                    .world
                    .getBlockState(new BlockPos(0, 140, 0))
                    .isOf(Blocks.DEEPSLATE_TILES),
                "confetti floor loaded");
            ProtectionPerformanceTest.ensureInitialized();

            EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
            emoteCatalog.apply(computeInt());
            apply(10.0);
            emoteCatalog.update2();
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().isEmpty(),
                "no confetti before finale");
            apply(11.25);
            emoteCatalog.update2();
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().size() == 64,
                "one complete paper burst");
            EmoteConfettiParticle emoteConfettiParticle =
                (EmoteConfettiParticle) emoteCatalog.getEmoteConfettiSystem().getList2().getFirst();
            emoteCatalog.update2();
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().getFirst()
                    == emoteConfettiParticle,
                "finale fires once per playback");
            apply(12.01);
            emoteCatalog.update2();
            apply(
                !emoteCatalog.test() && !emoteCatalog.getEmoteConfettiSystem().getList2().isEmpty(),
                "natural end lets paper settle");
            List list = List.copyOf(emoteCatalog.getEmoteConfettiSystem().getList2());

            for (int index5 = 0; index5 < 105; index5++) {
              emoteCatalog.update2();

              for (EmoteConfettiParticle emoteConfettiParticle2 :
                  (java.lang.Iterable<EmoteConfettiParticle>)
                      (java.lang.Iterable<?>) (emoteCatalog.getEmoteConfettiSystem().getList2())) {
                apply(
                    emoteConfettiParticle2.vec3d2.y >= 141.0
                        && emoteConfettiParticle2.vec3d2.z < 7.0,
                    "paper collides with floor and wall: " + emoteConfettiParticle2.vec3d2);
                apply(
                    emoteConfettiParticle2.vec3d2.distanceTo(emoteConfettiParticle2.vec3d) < 0.8,
                    "continuous confetti travel");
              }
            }

            apply(
                list.stream()
                    .allMatch(
                        value3 -> {
                          ProtectionPerformanceTest.ensureInitialized();
                          return ((bunnythekenzo.client.EmoteConfettiParticle) (value3)).count2 > 0;
                        }),
                "all paper reaches and bounces off the floor");
            apply(
                list.stream()
                    .anyMatch(
                        value4 -> {
                          ProtectionPerformanceTest.ensureInitialized();
                          return ((bunnythekenzo.client.EmoteConfettiParticle) (value4)).vec3d2.z > 3.0;
                        }),
                "paper flies visibly forward");
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().isEmpty(),
                "paper expires after natural completion");
            emoteCatalog.apply(computeInt());
            apply(11.25);
            emoteCatalog.update2();
            emoteCatalog.update();
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().isEmpty(),
                "manual stop clears paper");
            emoteCatalog.apply(computeInt());
            apply(11.25);
            emoteCatalog.update2();
            emoteCatalog.apply(2);
            apply(
                emoteCatalog.getEmoteConfettiSystem().getList2().isEmpty(),
                "switching dances clears paper");
            emoteCatalog.apply(computeInt());
            apply(11.25);
            emoteCatalog.update2();
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.takeScreenshot("jerking-confetti-launch");
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            minecraftClient7.setScreen(new GameMenuScreen(true));
          });
      clientGameTestContext.waitFor(MinecraftClient::isPaused);
      Vec3d onClient5 =
          clientGameTestContext.computeOnClient(
              minecraftClient8 -> {
                return ((EmoteConfettiParticle)
                        ClientInitializer.getEmoteCatalog()
                            .getEmoteConfettiSystem()
                            .getList2()
                            .getFirst())
                    .vec3d2;
              });
      int onClient6 =
          clientGameTestContext.computeOnClient(
              minecraftClient9 -> {
                return ((EmoteConfettiParticle)
                        ClientInitializer.getEmoteCatalog()
                            .getEmoteConfettiSystem()
                            .getList2()
                            .getFirst())
                    .count;
              });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            ProtectionPerformanceTest.ensureInitialized();

            EmoteConfettiParticle emoteConfettiParticle3 =
                (EmoteConfettiParticle)
                    ClientInitializer.getEmoteCatalog()
                        .getEmoteConfettiSystem()
                        .getList2()
                        .getFirst();
            apply(
                emoteConfettiParticle3.vec3d2.equals(onClient5)
                    && emoteConfettiParticle3.count == onClient6,
                "singleplayer pause holds confetti position and lifetime");
            minecraftClient10.setScreen(null);
          });
      clientGameTestContext.waitFor(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !minecraftClient11.isPaused();
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.takeScreenshot("jerking-confetti-flight");
      clientGameTestContext.waitTicks(14);
      clientGameTestContext.takeScreenshot("jerking-confetti-bounce");
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("jerking-confetti-settle");
      clientGameTestContext.waitTicks(60);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            apply(
                ClientInitializer.getEmoteCatalog().getEmoteConfettiSystem().getList2().isEmpty(),
                "rendered burst expires");
          });
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ClientInitializer.getEmoteCatalog().apply(computeInt());
          });
      clientGameTestContext.waitTicks(8);
      SoundInstance onClient7 =
          clientGameTestContext.computeOnClient(
              minecraftClient14 -> {
                return getSoundInstance();
              });
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                onClient7 != null && minecraftClient15.getSoundManager().isPlaying(onClient7),
                "buildup track starts audibly");
          });
      clientGameTestContext.waitFor(
          minecraftClient16 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !ClientInitializer.getEmoteCatalog()
                .getEmoteConfettiSystem()
                .getList2()
                .isEmpty();
          },
          600);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            ProtectionPerformanceTest.ensureInitialized();

            double value7 = ClientInitializer.getEmoteCatalog().computeDouble();
            apply(
                value7 >= 11.2F && value7 < 11.65,
                "live confetti follows the musical finale: " + value7);
            apply(
                getSoundInstance() == onClient7
                    && minecraftClient17.getSoundManager().isPlaying(onClient7),
                "same soundtrack plays through the launch accent");
          });
      clientGameTestContext.takeScreenshot("jerking-buildup-live-finale");
      clientGameTestContext.waitFor(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return !ClientInitializer.getEmoteCatalog().test();
          },
          100);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                getSoundInstance() == null && !onClient7.canPlay(),
                "complete performance stops its music");
          });
      ClientInitializer.logger.info(
          "Confetti verified: finale timing, one burst, forward flight, floor/wall collisions,"
              + " bounces, pause, natural completion, manual cancel, dance switch and full musical"
              + " playback");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ClientInitializer.getEmoteCatalog().update();
            ClientInitializer.getEmoteCatalog().automaticF5Control.setValue(flag);
            minecraftClient20.setScreen(null);
            minecraftClient20.options.setPerspective(onClient2);
            minecraftClient20.options.getFov().setValue(onClient3);
            minecraftClient20.options.hudHidden = onClient4;
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static int computeInt() {
    List list = ClientInitializer.getEmoteCatalog().getList();
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < list.size(); index++) {
      if (((EmoteDefinition) list.get(index)).emoteAnimation() == EmoteAnimation.JERKING_OFF) {
        return index;
      }
    }

    throw new AssertionError("Jerking off missing");
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
      throw new AssertionError("Jerking off: " + text);
    }
  }
}

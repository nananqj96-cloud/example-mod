package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.SkinTextures;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteAnimation;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.EmotePoseAnimator;
import bunnythekenzo.client.mixin.ModelPartAccessor;
import bunnythekenzo.client.module.SkinProtectModule;

public final class EmoteSkinTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    Runnable[] runnableArray = new Runnable[] {() -> {}};

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
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient2.options.getGuiScale().setValue(2);
            minecraftClient2.options.getMaxFps().setValue(60);
            ProtectionPerformanceTest.ensureInitialized();

            for (boolean flag : new boolean[] {false, true}) {
              checkModel(
                  new PlayerEntityModel(
                      ((net.minecraft.client.MinecraftClient) (minecraftClient2))
                          .getLoadedEntityModels()
                          .getModelPart(
                              flag
                                  ? bunnythekenzo.client.runtime.ModelLayerAccess.PLAYER_SLIM
                                  : EntityModelLayers.PLAYER),
                      flag));
            }

            for (float floatValue : new float[] {0.5F, 1.0F}) {
              checkModel(
                  new BipedEntityModel(
                      BipedEntityModel.getModelData(new Dilation(floatValue), 0.0F)
                          .getRoot()
                          .createPart(64, 32)));
            }
          });

      for (EmoteAnimation emoteAnimation :
          new EmoteAnimation[] {EmoteAnimation.TAKE_THE_L, EmoteAnimation.ORANGE_JUSTICE}) {
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              ProtectionPerformanceTest.ensureInitialized();

              List list = ClientInitializer.getEmoteCatalog().getList();

              for (int index = 0; index < list.size(); index++) {
                if (((EmoteDefinition) list.get(index)).emoteAnimation() == emoteAnimation) {
                  ClientInitializer.getEmoteCatalog().apply(index);
                  break;
                }
              }

              minecraftClient3.setScreen(
                  new HoppinStandingBeforeTestSupport22(emoteAnimation.name()));
              apply(minecraftClient3);
            });
        clientGameTestContext.waitTicks(5);
        clientGameTestContext.takeScreenshot(
            "emote-skin-" + emoteAnimation.name().toLowerCase() + "-account");
        clientGameTestContext.runOnClient(
            minecraftClient4 -> {
              runnableArray[0] =
                  HoppinStandingBeforeTestSupport9.enableReplacement(minecraftClient4);
            });

        for (boolean flag2 : new boolean[] {false, true}) {
          clientGameTestContext.runOnClient(
              minecraftClient5 -> {
                SkinProtectModule skinProtectModule =
                    ClientInitializer.getModuleManager().skinProtectModule;
                SkinTextures skinTextures = null;
                ProtectionPerformanceTest.ensureInitialized();

                for (int index2 = 1; index2 < 100; index2++) {
                  SkinTextures skinTextures2 =
                      DefaultSkinHelper.getSkinTextures(new UUID(0L, index2));
                  if (skinTextures2.model().name().equals("SLIM") == flag2) {
                    skinTextures = skinTextures2;
                    break;
                  }
                }

                apply(skinTextures != null, "test skin exists for both arm widths");

                try {
                  Field declaredField = SkinProtectModule.class.getDeclaredField("skinTextures");
                  declaredField.setAccessible(true);
                  declaredField.set(skinProtectModule, skinTextures);
                } catch (ReflectiveOperationException failure) {
                  throw new AssertionError(failure);
                }

                apply(minecraftClient5);
                apply(
                    minecraftClient5.player.getSkin().equals(skinTextures),
                    "skin changes during the same dance");
                apply(
                    ClientInitializer.getEmoteCatalog().test(),
                    "skin change preserves active dance");
              });
          clientGameTestContext.waitTicks(5);
          String text = emoteAnimation.name().toLowerCase();
          String text2 = flag2 ? "-slim" : "-classic";
          String text3 = text;
          clientGameTestContext.takeScreenshot("emote-skin-" + text3 + text2);
        }

        clientGameTestContext.runOnClient(
            minecraftClient6 -> {
              runnableArray[0].run();
              runnableArray[0] = () -> {};
              apply(minecraftClient6);
            });
      }

      ClientInitializer.logger.info(
          "Emote skins verified: real vertex buffers match authored skin UVs, all dances,"
              + " classic/slim, clothing, armour, live SkinProtect changes and reset");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            runnableArray[0].run();
            ClientInitializer.getEmoteCatalog().update();
            minecraftClient7.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    var playerRenderer =
        minecraftClient.getEntityRenderDispatcher().getPlayerRenderer(minecraftClient.player);
    PlayerEntityRenderState renderState = playerRenderer.createRenderState();
    playerRenderer.updateRenderState(minecraftClient.player, renderState, 0.0F);
    apply(
        playerRenderer
            .getTexture(renderState)
            .equals(minecraftClient.player.getSkin().body().texturePath()),
        "dancing renderer binds the current player skin");
    apply(
        renderState.skinTextures.model() == minecraftClient.player.getSkin().model(),
        "renderer follows current arm width");
  }

  public static void checkModel(BipedEntityModel bipedEntityModel) {
    PlayerEntityRenderState playerEntityRenderState = new PlayerEntityRenderState();
    ProtectionPerformanceTest.ensureInitialized();

    playerEntityRenderState.id = -98765;
    playerEntityRenderState.leftSleeveVisible =
        playerEntityRenderState.rightSleeveVisible =
            playerEntityRenderState.leftPantsLegVisible =
                playerEntityRenderState.rightPantsLegVisible = true;

    for (EmoteDefinition emoteDefinition :
        (java.lang.Iterable<EmoteDefinition>)
            (java.lang.Iterable<?>) (ClientInitializer.getEmoteCatalog().getList())) {
      if (!emoteDefinition.test()) {
        bipedEntityModel.setAngles(playerEntityRenderState);
        EmotePoseAnimator.render(
            bipedEntityModel,
            emoteDefinition.emoteAnimation(),
            2.4F,
            emoteDefinition.amount(),
            1.0F);
        bipedEntityModel
            .getRootPart()
            .traverse()
            .forEach(
                (java.util.function.Consumer<net.minecraft.client.model.ModelPart>)
                    (modelPart -> {
                      ProtectionPerformanceTest.ensureInitialized();

                      for (Cuboid cuboid :
                          (java.lang.Iterable<Cuboid>)
                              (Object) (((ModelPartAccessor) (Object) (modelPart)).getCuboids())) {
                        apply(cuboid, emoteDefinition.string());
                      }
                    }));
        bipedEntityModel.setAngles(playerEntityRenderState);
        apply(
            !bipedEntityModel.rightArm.hidden && !bipedEntityModel.leftLeg.hidden,
            "stopping restores ordinary limbs");
      }
    }
  }

  public static void apply(Cuboid cuboid, String text) {
    ProtectionPerformanceTest.ensureInitialized();

    HoppinStandingBeforeTestSupport4 hoppinStandingBeforeTestSupport4 =
        new HoppinStandingBeforeTestSupport4();
    cuboid.renderCuboid(
        new MatrixStack().peek(), hoppinStandingBeforeTestSupport4, 15728880, 0, -1);

    try (BufferAllocator bufferAllocator = new BufferAllocator(4096)) {
      VertexFormat POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL =
          VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL;
      BufferBuilder bufferBuilder =
          new BufferBuilder(
              bufferAllocator, DrawMode.QUADS, POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
      cuboid.renderCuboid(new MatrixStack().peek(), bufferBuilder, 15728880, 0, -1);

      try (BuiltBuffer builtBuffer = bufferBuilder.end()) {
        ByteBuffer byteBuffer = builtBuffer.getBuffer().order(ByteOrder.nativeOrder());
        int vertexSize = POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL.getVertexSize();
        int offset = POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL.getOffset(VertexFormatElement.UV0);
        int offset2 =
            POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL.getOffset(VertexFormatElement.POSITION);
        apply(
            builtBuffer.getDrawParameters().vertexCount()
                == hoppinStandingBeforeTestSupport4.list.size(),
            text + " vertex count");
        ArrayList arrayList = new ArrayList(hoppinStandingBeforeTestSupport4.list);

        for (int index = 0; index < builtBuffer.getDrawParameters().vertexCount(); index++) {
          int intValue = index * vertexSize;
          HoppinStandingBeforeTestData hoppinStandingBeforeTestData =
              new HoppinStandingBeforeTestData(
                  byteBuffer.getFloat(intValue + offset2),
                  byteBuffer.getFloat(intValue + offset2 + 4),
                  byteBuffer.getFloat(intValue + offset2 + 8),
                  byteBuffer.getFloat(intValue + offset),
                  byteBuffer.getFloat(intValue + offset + 4));
          int intValue2 = -1;

          for (int index2 = 0; index2 < arrayList.size(); index2++) {
            if (hoppinStandingBeforeTestData.test(
                (HoppinStandingBeforeTestData) arrayList.get(index2))) {
              intValue2 = index2;
              break;
            }
          }

          apply(
              intValue2 >= 0,
              text
                  + " GPU vertex uses the original skin UV and position: "
                  + hoppinStandingBeforeTestData);
          arrayList.remove(intValue2);
        }
      }
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Emote skin: " + text);
    }
  }
}

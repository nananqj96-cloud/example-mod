package bunnythekenzo.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CosmeticAttachmentRenderer;
import bunnythekenzo.client.VertexConsumerSupport6;
import bunnythekenzo.client.module.CosmeticsModule;

public final class CosmeticsFeatureRenderer extends FeatureRenderer {
  public CosmeticsFeatureRenderer(FeatureRendererContext featureRendererContext) {
    super(featureRendererContext);
  }

  public static void register() {
    LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
        (entityType, livingEntityRenderer, registrationHelper, context) -> {
          if (livingEntityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
            registrationHelper.register(new CosmeticsFeatureRenderer(playerEntityRenderer));
          }
        });
  }

  public void render(
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      PlayerEntityRenderState playerEntityRenderState,
      float floatValue,
      float floatValue2) {
    if (test(playerEntityRenderState)) {
      MinecraftClient instance = MinecraftClient.getInstance();
      CosmeticsModule cosmeticsModule = ClientInitializer.getModuleManager().cosmeticsModule;
      float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
      float floatValue3 =
          cosmeticsModule.motionSetting.isSelected("Still")
              ? 0.0F
              : cosmeticsModule.computeFloat(tickProgress);
      float float2 = cosmeticsModule.computeFloat2(tickProgress);
      VertexConsumerSupport6 vertexConsumerSupport6 = new VertexConsumerSupport6();
      MatrixStack matrixStack2 = new MatrixStack();
      if ((Boolean) cosmeticsModule.wingsSetting.getValue()) {
        matrixStack2.push();
        ((PlayerEntityModel) this.getContextModel()).body.applyTransform(matrixStack2);
        CosmeticAttachmentRenderer.render(
            vertexConsumerSupport6,
            matrixStack2,
            cosmeticsModule,
            playerEntityRenderState.skinTextures,
            floatValue3,
            float2,
            playerEntityRenderState.isGliding);
        matrixStack2.pop();
      }

      if ((Boolean) cosmeticsModule.crownSetting.getValue()) {
        matrixStack2.push();
        ((PlayerEntityModel) this.getContextModel()).head.applyTransform(matrixStack2);
        CosmeticAttachmentRenderer.apply(vertexConsumerSupport6, matrixStack2, cosmeticsModule);
        matrixStack2.pop();
      }

      if ((Boolean) cosmeticsModule.miniYouSetting.getValue()) {
        CosmeticAttachmentRenderer.render(
            vertexConsumerSupport6,
            matrixStack2,
            cosmeticsModule,
            playerEntityRenderState.skinTextures,
            floatValue3,
            float2);
      }

      vertexConsumerSupport6.submit(matrixStack, orderedRenderCommandQueue, intValue);
    }
  }

  public static boolean test(PlayerEntityRenderState playerEntityRenderState) {
    MinecraftClient instance = MinecraftClient.getInstance();
    return instance.player != null
        && playerEntityRenderState.id == instance.player.getId()
        && !playerEntityRenderState.spectator
        && !playerEntityRenderState.invisible
        && playerEntityRenderState.deathTime <= 0.0F
        && playerEntityRenderState.sleepingDirection == null
        && ClientInitializer.getModuleManager() != null
        && ClientInitializer.getModuleManager().cosmeticsModule.isEnabled();
  }

  public static boolean test2(PlayerEntityRenderState playerEntityRenderState) {
    if (!test(playerEntityRenderState)) {
      return false;
    }

    CosmeticsModule cosmeticsModule = ClientInitializer.getModuleManager().cosmeticsModule;
    return (Boolean) cosmeticsModule.wingsSetting.getValue()
        && (Boolean) cosmeticsModule.hideElytraSetting.getValue();
  }

  @Override
  public void render(
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      EntityRenderState entityRenderState,
      float floatValue,
      float floatValue2) {
    this.render(
        matrixStack,
        orderedRenderCommandQueue,
        intValue,
        (PlayerEntityRenderState) entityRenderState,
        floatValue,
        floatValue2);
  }
}

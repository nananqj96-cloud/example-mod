package bunnythekenzo.client.mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteBodyParts;
import bunnythekenzo.client.EmoteCatalog;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
  @Inject(method = "renderArm", at = @At("HEAD"))
  public void apply(CallbackInfo callbackInfo) {
    EmoteBodyParts.apply(
        (net.minecraft.client.render.entity.model.BipedEntityModel)
            (((PlayerEntityRenderer) (Object) (this)).getModel()));
  }

  @Inject(
      method =
          "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
      at = @At("TAIL"))
  public void render(
      PlayerLikeEntity playerLikeEntity,
      PlayerEntityRenderState playerEntityRenderState,
      float floatValue,
      CallbackInfo callbackInfo) {
    EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
    if (emoteCatalog != null && emoteCatalog.test(playerLikeEntity.getId())) {
      playerEntityRenderState.rightHandItemState.clear();
      playerEntityRenderState.leftHandItemState.clear();
      playerEntityRenderState.rightHandItem = ItemStack.EMPTY;
      playerEntityRenderState.leftHandItem = ItemStack.EMPTY;
      playerEntityRenderState.spyglassState.clear();
      playerEntityRenderState.rightArmPose = playerEntityRenderState.leftArmPose = ArmPose.EMPTY;
      playerEntityRenderState.handSwingProgress = 0.0F;
      playerEntityRenderState.isUsingItem = false;
      playerEntityRenderState.itemUseTime = 0.0F;
    }
  }
}

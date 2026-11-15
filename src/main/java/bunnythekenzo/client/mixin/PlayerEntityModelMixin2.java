package bunnythekenzo.client.mixin;

import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.EmoteBodyParts;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin2 {
  @Inject(
      method =
          "setArmAngle(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;)V",
      at = @At("TAIL"))
  public void apply(
      PlayerEntityRenderState playerEntityRenderState,
      Arm arm,
      MatrixStack matrixStack,
      CallbackInfo callbackInfo) {
    EmoteBodyParts.apply((PlayerEntityModel) (Object) (this), arm, matrixStack);
  }
}

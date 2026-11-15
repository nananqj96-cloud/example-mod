package bunnythekenzo.client.mixin;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.render.CosmeticsFeatureRenderer;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin {
  @Inject(
      method =
          "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
      at = @At("HEAD"),
      cancellable = true)
  public void render(
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      BipedEntityRenderState bipedEntityRenderState,
      float floatValue,
      float floatValue2,
      CallbackInfo callbackInfo) {
    if (bipedEntityRenderState instanceof PlayerEntityRenderState playerEntityRenderState
        && CosmeticsFeatureRenderer.test2(playerEntityRenderState)) {
      callbackInfo.cancel();
    }
  }
}

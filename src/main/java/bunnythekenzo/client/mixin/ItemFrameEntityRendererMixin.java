package bunnythekenzo.client.mixin;

import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.entity.decoration.ItemFrameEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin {
  @Inject(
      method =
          "updateRenderState(Lnet/minecraft/entity/decoration/ItemFrameEntity;Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;F)V",
      at = @At("TAIL"))
  public void render(
      ItemFrameEntity itemFrameEntity,
      ItemFrameEntityRenderState itemFrameEntityRenderState,
      float floatValue,
      CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null
        && fakeLootDropModule.isEnabled()
        && fakeLootDropModule.test(itemFrameEntity.getHeldItemStack())) {
      itemFrameEntityRenderState.mapId = null;
      itemFrameEntityRenderState.displayName = null;
    }
  }
}

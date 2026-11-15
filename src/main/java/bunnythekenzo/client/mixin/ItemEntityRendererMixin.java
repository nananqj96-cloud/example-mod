package bunnythekenzo.client.mixin;

import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(ItemEntityRenderer.class)
public final class ItemEntityRendererMixin {
  @Inject(
      method =
          "updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V",
      at = @At("TAIL"))
  public void render(
      ItemEntity itemEntity,
      ItemEntityRenderState itemEntityRenderState,
      float floatValue,
      CallbackInfo callbackInfo) {
    if (ClientInitializer.getModuleManager() != null) {
      FakeLootDropModule fakeLootDropModule =
          ClientInitializer.getModuleManager().fakeLootDropModule;
      if (fakeLootDropModule != null
          && fakeLootDropModule.isEnabled()
          && fakeLootDropModule.test(itemEntity.getStack())) {
        itemEntityRenderState.displayName = null;
      }
    }
  }
}

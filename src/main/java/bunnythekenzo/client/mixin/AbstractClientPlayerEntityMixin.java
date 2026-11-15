package bunnythekenzo.client.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.SkinTextures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.SkinProtectModule;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
  @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
  public void apply(CallbackInfoReturnable callbackInfoReturnable) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null) {
      SkinProtectModule skinProtectModule = moduleManager.skinProtectModule;
      if (skinProtectModule != null) {
        AbstractClientPlayerEntity abstractClientPlayerEntity =
            (AbstractClientPlayerEntity) (Object) (this);
        callbackInfoReturnable.setReturnValue(
            skinProtectModule.getSkinTextures(
                abstractClientPlayerEntity.getUuid(),
                (SkinTextures) callbackInfoReturnable.getReturnValue()));
      }
    }
  }
}

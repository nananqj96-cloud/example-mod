package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(LivingEntity.class)
public class LivingEntityMixin2 {
  @ModifyReturnValue(method = "getHandSwingDuration", at = @At("RETURN"))
  public int computeInt(int intValue) {
    LivingEntity livingEntity = (LivingEntity) (Object) (this);
    if (MinecraftClient.getInstance().player != livingEntity) {
      return intValue;
    } else {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null
          && moduleManager.animationsModule != null
          && moduleManager.animationsModule.isEnabled()) {
        float value2 = moduleManager.animationsModule.computeFloat();
        return value2 <= 0.01F ? intValue : Math.max(1, Math.round(intValue / value2));
      } else {
        return intValue;
      }
    }
  }

  @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
  public void apply(Hand hand, boolean flag, CallbackInfo callbackInfo) {
    LivingEntity livingEntity = (LivingEntity) (Object) (this);
    if (MinecraftClient.getInstance().player == livingEntity && livingEntity.handSwinging) {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null
          && moduleManager.animationsModule != null
          && moduleManager.animationsModule.isEnabled()
          && moduleManager.animationsModule.test()) {
        callbackInfo.cancel();
      }
    }
  }
}

package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.MovementOverrides;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  @Inject(method = "jump", at = @At("HEAD"))
  public void apply(CallbackInfo callbackInfo) {
    if ((net.minecraft.client.network.ClientPlayerEntity) (Object) (this)
            instanceof ClientPlayerEntity clientPlayerEntity
        && clientPlayerEntity == MinecraftClient.getInstance().player) {
      MovementOverrides.setClientPlayerEntity2(clientPlayerEntity);
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null
          && moduleManager.client67JumpCirclesModule != null
          && moduleManager.client67JumpCirclesModule.isEnabled()) {
        moduleManager.client67JumpCirclesModule.apply(clientPlayerEntity);
      }
    }
  }
}

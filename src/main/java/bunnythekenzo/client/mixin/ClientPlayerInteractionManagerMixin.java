package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
  @Inject(method = "attackEntity", at = @At("HEAD"))
  public void apply(PlayerEntity playerEntity, Entity entity, CallbackInfo callbackInfo) {
    if (playerEntity == MinecraftClient.getInstance().player && entity != playerEntity) {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null
          && moduleManager.hitParticlesModule != null
          && moduleManager.hitParticlesModule.isEnabled()) {
        moduleManager.hitParticlesModule.apply(entity);
      }
    }
  }
}

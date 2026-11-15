package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(Entity.class)
public class EntityMixin3 {
  @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
  public void apply(PlayerEntity playerEntity, CallbackInfoReturnable callbackInfoReturnable) {
    Entity entity = (Entity) (Object) (this);
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && entity == instance.player && playerEntity == instance.player) {
      FreecamModule instance2 = FreecamModule.getInstance();
      if (instance2 != null && instance2.test() && instance2.test4()) {
        callbackInfoReturnable.setReturnValue(false);
      }
    }
  }
}

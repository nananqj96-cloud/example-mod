package bunnythekenzo.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.MovementOverrides;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin3 {
  @Inject(method = "tickMovement", at = @At("HEAD"))
  public void apply(CallbackInfo callbackInfo) {
    MovementOverrides.apply3((ClientPlayerEntity) (Object) (this));
  }
}

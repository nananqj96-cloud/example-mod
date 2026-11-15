package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
  @Inject(method = "tickMovement", at = @At("HEAD"))
  public void apply(CallbackInfo callbackInfo) {
    this.update();
  }

  @Inject(method = "sendMovementPackets", at = @At("HEAD"))
  public void apply2(CallbackInfo callbackInfo) {
    this.update();
  }

  public void update() {
    ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) (this);
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player == clientPlayerEntity) {
      FreecamModule instance2 = FreecamModule.getInstance();
      if (instance2 != null && instance2.test()) {
        FreecamModule.apply2(instance);
      }
    }
  }
}

package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(Entity.class)
public class EntityMixin {
  @Inject(method = "changeLookDirection(DD)V", at = @At("HEAD"), cancellable = true)
  public void render(double doubleValue, double doubleValue2, CallbackInfo callbackInfo) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if ((Object) this == instance.player) {
      FreecamModule instance2 = FreecamModule.getInstance();
      if (instance2 != null && instance2.test()) {
        instance2.apply(
            instance2.getAmount() + (float) (doubleValue * 0.15 * instance2.computeFloat()),
            instance2.getAmount2() + (float) (doubleValue2 * 0.15 * instance2.computeFloat()));
        callbackInfo.cancel();
      } else {
        FreeLookModule instance3 = FreeLookModule.getInstance();
        if (instance3 != null && instance3.test5()) {
          instance3.apply(doubleValue, doubleValue2);
          callbackInfo.cancel();
        }
      }
    }
  }
}

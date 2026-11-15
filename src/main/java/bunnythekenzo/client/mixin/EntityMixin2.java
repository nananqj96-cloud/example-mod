package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(Entity.class)
public abstract class EntityMixin2 {
  @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
  public void render(double doubleValue, double doubleValue2, CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null
        && fakeLootDropModule.equipmentPreview.test3()
        && (Object) this == MinecraftClient.getInstance().player) {
      fakeLootDropModule.equipmentPreview.apply(doubleValue, doubleValue2);
      callbackInfo.cancel();
    }
  }
}

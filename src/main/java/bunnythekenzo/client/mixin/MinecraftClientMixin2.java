package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin2 {
  @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
  public void apply(CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null
        && fakeLootDropModule.isEnabled()
        && fakeLootDropModule.equipmentPreview.test()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
  public void apply(CallbackInfoReturnable callbackInfoReturnable) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.equipmentPreview.test3()) {
      callbackInfoReturnable.setReturnValue(false);
    }
  }

  @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
  public void apply(boolean flag, CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.equipmentPreview.test3()) {
      callbackInfo.cancel();
    }
  }
}

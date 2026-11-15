package bunnythekenzo.client.mixin;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(Camera.class)
public abstract class CameraMixin2 {
  @ModifyArgs(
      method = "update",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V",
              ordinal = 1))
  public void apply(Args args) {
    FreeLookModule instance = FreeLookModule.getInstance();
    if (instance != null && instance.test()) {
      FreecamModule instance2 = FreecamModule.getInstance();
      if (instance2 == null || !instance2.test()) {
        args.set(0, instance.getAmount());
        args.set(1, instance.getAmount2());
      }
    }
  }

  @ModifyArgs(
      method = "update",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V",
              ordinal = 0))
  public void apply2(Args args) {
    this.apply(args);
  }

  @ModifyArg(
      method = "update",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(F)F"),
      index = 0)
  public float computeFloat(float floatValue) {
    FreeLookModule instance = FreeLookModule.getInstance();
    if (instance != null && instance.test()) {
      FreecamModule instance2 = FreecamModule.getInstance();
      return instance2 != null && instance2.test() ? floatValue : instance.computeFloat(floatValue);
    } else {
      return floatValue;
    }
  }

  @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
  public void apply(float floatValue, CallbackInfoReturnable callbackInfoReturnable) {
    FreeLookModule instance = FreeLookModule.getInstance();
    if (instance != null && instance.test4()) {
      FreecamModule instance2 = FreecamModule.getInstance();
      if (instance2 == null || !instance2.test()) {
        callbackInfoReturnable.setReturnValue(floatValue);
      }
    }
  }
}

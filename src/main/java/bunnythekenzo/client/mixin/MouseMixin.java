package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(Mouse.class)
public abstract class MouseMixin {
  @Shadow public double cursorDeltaX;
  @Shadow public double cursorDeltaY;

  @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
  public void apply(double doubleValue, CallbackInfo callbackInfo) {
    FreecamModule instance = FreecamModule.getInstance();
    if (instance != null && instance.test()) {
      MinecraftClient instance2 = MinecraftClient.getInstance();
      if (instance2.currentScreen == null) {
        double doubleValue2 = instance2.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
        double doubleValue3 = doubleValue2 * doubleValue2 * doubleValue2 * 8.0;
        double doubleValue4 = this.cursorDeltaX * doubleValue3 * instance.computeFloat();
        double doubleValue5 = this.cursorDeltaY * doubleValue3 * instance.computeFloat();
        float floatValue = instance.getAmount() + (float) doubleValue4 * 0.15F;
        float floatValue2 = instance.getAmount2() + (float) doubleValue5 * 0.15F;
        instance.apply(floatValue, floatValue2);
        callbackInfo.cancel();
      }
    }
  }
}

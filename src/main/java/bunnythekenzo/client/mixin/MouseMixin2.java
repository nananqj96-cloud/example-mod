package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.input.MouseInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClickRateTracker;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(Mouse.class)
public class MouseMixin2 {
  @Shadow public double cursorDeltaX;
  @Shadow public double cursorDeltaY;

  @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
  public void apply(double doubleValue, CallbackInfo callbackInfo) {
    if (PearlEscapeAutomation.test()) {
      this.cursorDeltaX = 0.0;
      this.cursorDeltaY = 0.0;
      callbackInfo.cancel();
    } else {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null) {
        double[] doubleValues =
            moduleManager.autoCrystalModule == null
                ? null
                : moduleManager.autoCrystalModule.getValueArray(
                    doubleValue, this.cursorDeltaX, this.cursorDeltaY);
        if ((moduleManager.autoCrystalModule == null || !moduleManager.autoCrystalModule.test())
            && moduleManager.aimAssistModule != null
            && moduleManager.aimAssistModule.isEnabled()) {
          doubleValues =
              moduleManager.aimAssistModule.getValueArray(
                  doubleValue, this.cursorDeltaX, this.cursorDeltaY);
        }

        if (doubleValues != null) {
          this.cursorDeltaX = this.cursorDeltaX + doubleValues[0];
          this.cursorDeltaY = this.cursorDeltaY + doubleValues[1];
        }
      }
    }
  }

  @Inject(
      method = "onMouseButton(JLnet/minecraft/client/input/MouseInput;I)V",
      at = @At("HEAD"),
      cancellable = true)
  public void apply(
      long longValue, MouseInput mouseInput, int intValue, CallbackInfo callbackInfo) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (longValue == instance.getWindow().getHandle()) {
      if (ClientInitializer.getModuleManager() != null
          && ClientInitializer.getModuleManager().creamyKeysModule != null) {
        ClientInitializer.getModuleManager().creamyKeysModule.apply(mouseInput.button(), intValue);
      }

      if (intValue == 1 && PearlEscapeAutomation.test(mouseInput.button())) {
        callbackInfo.cancel();
      } else {
        if (intValue == 1 && instance.currentScreen == null) {
          ClickRateTracker.recordClick(mouseInput.button());
        }
      }
    }
  }

  @ModifyExpressionValue(
      method = "updateMouse",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"),
      slice =
          @Slice(
              from =
                  @At(
                      value = "INVOKE",
                      target =
                          "Lnet/minecraft/client/option/GameOptions;getMouseSensitivity()Lnet/minecraft/client/option/SimpleOption;")))
  public Object getObject(Object value2) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.zoomModule != null
        && value2 instanceof Double doubleValue) {
      double value3 = moduleManager.zoomModule.computeDouble();
      if (value3 <= 1.0001) {
        return value2;
      }

      double doubleValue2 = doubleValue * 0.6 + 0.2;
      double doubleValue3 = doubleValue2 / Math.cbrt(value3);
      double doubleValue4 = (doubleValue3 - 0.2) / 0.6;
      return Math.max(0.0, doubleValue4);
    } else {
      return value2;
    }
  }

  @Inject(method = "onMouseScroll(JDD)V", at = @At("HEAD"), cancellable = true)
  public void render(
      long longValue, double doubleValue, double doubleValue2, CallbackInfo callbackInfo) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (longValue == instance.getWindow().getHandle()) {
      if (PearlEscapeAutomation.test() && instance.currentScreen == null) {
        callbackInfo.cancel();
      } else {
        if (ClientInitializer.getModuleManager() != null
            && ClientInitializer.getModuleManager().creamyKeysModule != null) {
          ClientInitializer.getModuleManager().creamyKeysModule.apply(doubleValue2);
        }

        if (!(instance.currentScreen instanceof ChatScreen) && instance.currentScreen == null) {
          ModuleManager moduleManager = ClientInitializer.getModuleManager();
          if (moduleManager != null
              && moduleManager.freecamModule != null
              && moduleManager.freecamModule.test(doubleValue2)) {
            callbackInfo.cancel();
          } else {
            if (moduleManager != null
                && moduleManager.freeLookModule != null
                && moduleManager.freeLookModule.test(doubleValue2)) {
              callbackInfo.cancel();
            }
          }
        }
      }
    }
  }
}

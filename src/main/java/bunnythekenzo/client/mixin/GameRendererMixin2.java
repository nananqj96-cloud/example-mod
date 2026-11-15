package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiBlurOverride;
import bunnythekenzo.client.module.FreecamModule;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.OverlayRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin2 {
  @Inject(
      method = "render(Lnet/minecraft/client/render/RenderTickCounter;Z)V",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/gui/render/GuiRenderer;incrementFrame()V",
              shift = Shift.AFTER))
  public void apply(RenderTickCounter renderTickCounter, boolean flag, CallbackInfo callbackInfo) {
    OverlayRenderer.update();
  }

  @ModifyArg(
      method = "render(Lnet/minecraft/client/render/RenderTickCounter;Z)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/gl/GlobalSettings;set(IIDJLnet/minecraft/client/render/RenderTickCounter;ILnet/minecraft/client/render/Camera;Z)V"),
      index = 5)
  public int computeInt(int intValue) {
    return GuiBlurOverride.applyOverride(intValue);
  }

  @ModifyReturnValue(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)F", at = @At("RETURN"))
  public float computeFloat(float floatValue) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null && moduleManager.zoomModule != null) {
      double value2 = moduleManager.zoomModule.computeDouble();
      return value2 > 1.0001 ? (float) (floatValue / value2) : floatValue;
    } else {
      return floatValue;
    }
  }

  @ModifyExpressionValue(
      method = "getFov(Lnet/minecraft/client/render/Camera;FZ)F",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F",
              ordinal = 0))
  public float computeFloat2(float floatValue) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null && moduleManager.customFOVModule != null
        ? moduleManager.customFOVModule.computeFloat(floatValue)
        : floatValue;
  }

  @ModifyExpressionValue(
      method = "renderHand",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
  public boolean test(boolean flag) {
    FreecamModule instance = FreecamModule.getInstance();
    return instance != null && instance.test() && instance.test6() ? true : flag;
  }
}

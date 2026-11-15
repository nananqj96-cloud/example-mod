package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.FreecamModule;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(InGameHud.class)
public class InGameHudMixin3 {
  @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
  public void apply(
      DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.customCrosshairModule != null
        && moduleManager.customCrosshairModule.test()) {
      callbackInfo.cancel();
    }
  }

  @ModifyExpressionValue(
      method = "renderCrosshair",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
  public boolean test(boolean flag) {
    FreecamModule instance = FreecamModule.getInstance();
    return instance != null && instance.test() && instance.test4() && !flag ? true : flag;
  }
}

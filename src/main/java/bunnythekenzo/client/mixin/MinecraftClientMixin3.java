package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin3 {
  @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
  public void apply(CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.anchorMacroModule != null
        && moduleManager.anchorMacroModule.test()) {
      callbackInfo.cancel();
    }
  }
}

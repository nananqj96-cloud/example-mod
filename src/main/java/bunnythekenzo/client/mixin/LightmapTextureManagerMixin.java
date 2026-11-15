package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
  @ModifyExpressionValue(
      method = "update",
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
                          "Lnet/minecraft/client/option/GameOptions;getGamma()Lnet/minecraft/client/option/SimpleOption;")))
  public Object getObject(Object value2) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null
            && moduleManager.fullBrightModule != null
            && moduleManager.fullBrightModule.isEnabled()
        ? (double) moduleManager.fullBrightModule.gammaSetting.floatValue()
        : value2;
  }
}

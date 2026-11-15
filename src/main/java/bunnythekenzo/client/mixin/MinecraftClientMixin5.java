package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.CameraChunkCulling;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin5 {
  @Inject(method = "render", at = @At("HEAD"))
  public void apply(boolean flag, CallbackInfo callbackInfo) {
    FreecamModule instance = FreecamModule.getInstance();
    if (instance != null && instance.isEnabled()) {
      instance.update5();
    }

    FreeLookModule instance2 = FreeLookModule.getInstance();
    if (instance2 != null) {
      instance2.update4();
    }

    CameraChunkCulling.apply(MinecraftClient.getInstance());
  }
}

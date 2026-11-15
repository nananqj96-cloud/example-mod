package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {
  @Inject(method = "tick", at = @At("TAIL"))
  public void apply(CallbackInfo callbackInfo) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && instance.player.input == (Object) this) {
      if (PearlEscapeAutomation.test()) {
        PearlEscapeAutomation.apply6(instance);
      } else {
        FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
        if (fakeLootDropModule != null) {
          fakeLootDropModule.equipmentPreview.update();
        }

        FreecamModule instance2 = FreecamModule.getInstance();
        if (instance2 != null && instance2.test()) {
          FreecamModule.apply2(instance);
        }
      }
    }
  }
}

package bunnythekenzo.client.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
  @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"))
  public void apply(Packet packet, CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.antiVanishModule != null
        && moduleManager.antiVanishModule.isEnabled()) {
      moduleManager.antiVanishModule.apply(packet);
    }
  }
}

package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.LightSectionCache;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin2 {
  @Inject(method = "onLightUpdate", at = @At("TAIL"))
  public void apply(LightUpdateS2CPacket lightUpdateS2CPacket, CallbackInfo callbackInfo) {
    LightSectionCache.getInstance()
        .apply(
            lightUpdateS2CPacket.getChunkX(),
            lightUpdateS2CPacket.getChunkZ(),
            lightUpdateS2CPacket.getData(),
            MinecraftClient.getInstance().world);
  }

  @Inject(method = "onChunkData", at = @At("TAIL"))
  public void apply(ChunkDataS2CPacket chunkDataS2CPacket, CallbackInfo callbackInfo) {
    LightSectionCache.getInstance()
        .apply(
            chunkDataS2CPacket.getChunkX(),
            chunkDataS2CPacket.getChunkZ(),
            chunkDataS2CPacket.getLightData(),
            MinecraftClient.getInstance().world);
  }
}

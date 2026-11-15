package bunnythekenzo.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

public final class PlayerModelHooks {
  public static void register(
      PlayerModelHooksContract2 playerModelHooksContract2,
      PlayerModelHooksContract playerModelHooksContract,
      PlayerModelHooksContract playerModelHooksContract3,
      PlayerModelHooksContract2 playerModelHooksContract22,
      PlayerModelHooksContract2 playerModelHooksContract23,
      Runnable runnable) {
    ClientTickEvents.START_CLIENT_TICK.register(playerModelHooksContract2::apply);
    ClientEntityEvents.ENTITY_LOAD.register(
        (entity, clientWorld) -> {
          playerModelHooksContract.apply(entity);
        });
    ClientEntityEvents.ENTITY_UNLOAD.register(
        (entity2, clientWorld2) -> {
          playerModelHooksContract3.apply(entity2);
        });
    ClientChunkEvents.CHUNK_LOAD.register(
        (clientWorld3, worldChunk) -> {
          playerModelHooksContract22.apply(MinecraftClient.getInstance());
        });
    ClientPlayConnectionEvents.JOIN.register(
        (clientPlayNetworkHandler, packetSender, minecraftClient) -> {
          playerModelHooksContract23.apply(minecraftClient);
        });
    ClientPlayConnectionEvents.DISCONNECT.register(
        (clientPlayNetworkHandler2, minecraftClient2) -> {
          runnable.run();
        });
  }
}

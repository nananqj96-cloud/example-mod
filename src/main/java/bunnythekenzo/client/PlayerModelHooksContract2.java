package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;

@FunctionalInterface
public interface PlayerModelHooksContract2 {
  void apply(MinecraftClient minecraftClient);
}

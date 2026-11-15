package bunnythekenzo.client.gametest;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public final class HoppinStandingBeforeTestSupport5 extends ClientPlayerInteractionManager {
  public int count;

  public HoppinStandingBeforeTestSupport5(MinecraftClient minecraftClient) {
    super(minecraftClient, minecraftClient.getNetworkHandler());
  }

  @Override
  public void attackEntity(PlayerEntity playerEntity, Entity entity) {
    this.count++;
  }
}

package bunnythekenzo.client;

import net.minecraft.entity.player.PlayerEntity;

public final class TargetESPSupport {
  public final PlayerEntity playerEntity;
  public float amount;
  public float amount2;

  public TargetESPSupport(PlayerEntity playerEntity2) {
    this.playerEntity = playerEntity2;
  }

  public float computeFloat() {
    return this.amount * this.amount * (3.0F - 2.0F * this.amount);
  }
}

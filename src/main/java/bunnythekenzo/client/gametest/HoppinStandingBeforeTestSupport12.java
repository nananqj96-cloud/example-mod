package bunnythekenzo.client.gametest;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.util.Hand;

public final class HoppinStandingBeforeTestSupport12 extends OtherClientPlayerEntity {
  public static final String string = "TriggerTest";

  public HoppinStandingBeforeTestSupport12(MinecraftClient minecraftClient) {
    super(minecraftClient.world, new GameProfile(UUID.randomUUID(), string));
  }

  public void update() {
    this.setCurrentHand(Hand.OFF_HAND);
    this.setLivingFlag(2, true);
    this.setLivingFlag(1, true);
  }

  public void update2() {
    this.setLivingFlag(1, false);
    this.clearActiveItem();
  }
}

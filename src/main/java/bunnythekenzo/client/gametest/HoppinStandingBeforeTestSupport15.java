package bunnythekenzo.client.gametest;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.GameMode;

public final class HoppinStandingBeforeTestSupport15 extends ClientPlayerInteractionManager {
  public final List list = new ArrayList();

  public HoppinStandingBeforeTestSupport15(MinecraftClient minecraftClient) {
    super(minecraftClient, minecraftClient.getNetworkHandler());
    this.setGameMode(GameMode.SURVIVAL);
  }

  @Override
  public ActionResult interactBlock(
      ClientPlayerEntity clientPlayerEntity, Hand hand, BlockHitResult blockHitResult) {
    this.list.add(clientPlayerEntity.getInventory().getSelectedSlot());
    return super.interactBlock(clientPlayerEntity, hand, blockHitResult);
  }
}

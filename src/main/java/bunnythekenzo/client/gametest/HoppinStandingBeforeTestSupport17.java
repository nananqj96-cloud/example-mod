package bunnythekenzo.client.gametest;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

public final class HoppinStandingBeforeTestSupport17 extends ClientPlayerInteractionManager {
  public final List list = new ArrayList();

  public HoppinStandingBeforeTestSupport17(MinecraftClient minecraftClient) {
    super(minecraftClient, minecraftClient.getNetworkHandler());
  }

  @Override
  public void clickSlot(
      int intValue,
      int intValue2,
      int intValue3,
      SlotActionType slotActionType,
      PlayerEntity playerEntity) {
    ProtectionPerformanceTest.ensureInitialized();
    AutoTotemSettingsTest.apply(
        slotActionType == SlotActionType.SWAP
            && intValue == playerEntity.playerScreenHandler.syncId,
        "one vanilla player-menu swap");
    this.list.add(intValue2 + ":" + intValue3);
    playerEntity.currentScreenHandler.onSlotClick(
        intValue2, intValue3, slotActionType, playerEntity);
    AutoTotemSettingsTest.apply(
        playerEntity.currentScreenHandler.getCursorStack().isEmpty(),
        "atomic swap never touches cursor");
  }
}

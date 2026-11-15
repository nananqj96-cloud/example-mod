package bunnythekenzo.client;

import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public final class InventoryActions {
  public static MinecraftClient getMinecraftClient() {
    return MinecraftClient.getInstance();
  }

  public static int computeInt(int intValue) {
    return intValue < 9 ? intValue + 36 : intValue;
  }

  public static void apply(int intValue) {
    MinecraftClient minecraftClient = getMinecraftClient();
    if (intValue >= 0 && intValue <= 8 && minecraftClient.player != null) {
      if (minecraftClient.player.getInventory().getSelectedSlot() != intValue) {
        minecraftClient.player.getInventory().setSelectedSlot(intValue);
        if (minecraftClient.getNetworkHandler() != null) {
          minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue));
        }
      }
    }
  }

  public static void update() {
    MinecraftClient minecraftClient = getMinecraftClient();
    if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
      minecraftClient
          .getNetworkHandler()
          .sendPacket(
              new PlayerActionC2SPacket(
                  Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
    }
  }

  public static void apply(int intValue, int intValue2) {
    MinecraftClient minecraftClient = getMinecraftClient();
    if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
      if (intValue >= 9 && intValue <= 35 && intValue2 >= 0 && intValue2 <= 8) {
        minecraftClient.interactionManager.clickSlot(
            minecraftClient.player.currentScreenHandler.syncId,
            computeInt(intValue),
            intValue2,
            SlotActionType.SWAP,
            minecraftClient.player);
      }
    }
  }

  public static void apply2(int intValue) {
    apply(intValue);
  }

  public static Hand getHand(Item item) {
    ClientPlayerEntity player = getMinecraftClient().player;
    if (player == null) {
      return null;
    } else if (player.getMainHandStack().isOf(item)) {
      return Hand.MAIN_HAND;
    } else {
      return player.getOffHandStack().isOf(item) ? Hand.OFF_HAND : null;
    }
  }

  public static int computeInt(Item item) {
    ClientPlayerEntity player = getMinecraftClient().player;
    if (player == null) {
      return -1;
    }

    for (int index = 0; index < 9; index++) {
      if (player.getInventory().getStack(index).isOf(item)) {
        return index;
      }
    }

    return -1;
  }

  public static int computeInt2(Item item) {
    ClientPlayerEntity player = getMinecraftClient().player;
    if (player == null) {
      return -1;
    }

    for (int index = 0; index < 36; index++) {
      if (player.getInventory().getStack(index).isOf(item)) {
        return index;
      }
    }

    return -1;
  }

  public static boolean test(Item item) {
    int intValue = computeInt(item);
    if (intValue < 0) {
      return false;
    }

    apply(intValue);
    return true;
  }

  public static boolean test(Predicate predicate) {
    ClientPlayerEntity player = getMinecraftClient().player;
    if (player == null) {
      return false;
    }

    for (int index = 0; index < 9; index++) {
      if (predicate.test(player.getInventory().getStack(index))) {
        apply(index);
        return true;
      }
    }

    return false;
  }

  public static int computeInt() {
    ClientPlayerEntity player = getMinecraftClient().player;
    if (player == null) {
      return -1;
    }

    for (int index = 0; index < 9; index++) {
      if (player.getInventory().getStack(index).isEmpty()) {
        return index;
      }
    }

    return -1;
  }
}

package bunnythekenzo.client;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public final class AnchorInteractions {
  public static boolean test(BlockPos blockPos, Block block) {
    MinecraftClient instance = MinecraftClient.getInstance();
    return instance.world != null && instance.world.getBlockState(blockPos).isOf(block);
  }

  public static boolean test(BlockPos blockPos) {
    MinecraftClient instance = MinecraftClient.getInstance();
    return test(blockPos, Blocks.RESPAWN_ANCHOR)
        && instance.world.getBlockState(blockPos).get(RespawnAnchorBlock.CHARGES) != 0;
  }

  public static boolean test2(BlockPos blockPos) {
    MinecraftClient instance = MinecraftClient.getInstance();
    return test(blockPos, Blocks.RESPAWN_ANCHOR)
        && instance.world.getBlockState(blockPos).get(RespawnAnchorBlock.CHARGES) == 0;
  }

  public static void apply(BlockHitResult blockHitResult, boolean flag) {
    apply(blockHitResult, Hand.MAIN_HAND, flag);
  }

  public static void apply(BlockHitResult blockHitResult, Hand hand, boolean flag) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && instance.interactionManager != null) {
      ActionResult actionResult =
          instance.interactionManager.interactBlock(instance.player, hand, blockHitResult);
      if (actionResult.isAccepted() && flag) {
        instance.player.swingHand(hand);
      }
    }
  }
}

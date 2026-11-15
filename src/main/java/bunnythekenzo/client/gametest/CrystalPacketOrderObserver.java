package bunnythekenzo.client.gametest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class CrystalPacketOrderObserver extends ChannelOutboundHandlerAdapter {
  public boolean interactedSinceBoundary;
  public boolean movementSent;
  public int placementsThisTick;
  public double playerX;
  public double playerY;
  public double playerZ;
  public float yaw;
  public float pitch;
  public final List pendingPlacements;
  public Vec3d lastEyePosition;
  public final AutoCrystalTestTest testContext;

  public CrystalPacketOrderObserver(AutoCrystalTestTest autoCrystalTestTest2) {
    this.testContext = autoCrystalTestTest2;
    this.pendingPlacements = new ArrayList();
  }

  public void checkPlacementLook(BlockPos blockPos, Vec3d vec3d2, String text) {
    ProtectionPerformanceTest.ensureInitialized();
    List list2 = null;
    Vec3d vec3d3 = vec3d2.add(Vec3d.fromPolar(this.pitch, this.yaw).multiply(5.0));
    Box box = new Box(blockPos).expand(1.0E-6);
    if (!box.contains(vec3d2) && box.raycast(vec3d2, vec3d3).isEmpty()) {
      list2 = this.testContext.list2;
      String text2 = String.valueOf(blockPos);
      float amount22 = this.pitch;
      float amount3 = this.yaw;
      String text3 = text2;
      list2.add(text + " look misses " + text3 + " yaw=" + amount3 + " pitch=" + amount22);
    }
  }

  @Override
  public void write(
      ChannelHandlerContext channelHandlerContext, Object value4, ChannelPromise channelPromise)
      throws Exception {
    ProtectionPerformanceTest.ensureInitialized();
    if (value4 instanceof PlayerMoveC2SPacket playerMoveC2SPacket) {
      this.yaw = playerMoveC2SPacket.getYaw(this.yaw);
      this.pitch = playerMoveC2SPacket.getPitch(this.pitch);
      if (this.testContext.enabled) {
        for (BlockPos blockPos :
            (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (this.pendingPlacements)) {
          this.checkPlacementLook(blockPos, this.lastEyePosition, "post-movement");
        }
      }

      this.pendingPlacements.clear();
      this.playerX = playerMoveC2SPacket.getX(this.playerX);
      this.playerY = playerMoveC2SPacket.getY(this.playerY);
      this.playerZ = playerMoveC2SPacket.getZ(this.playerZ);
      this.interactedSinceBoundary = false;
      this.movementSent = true;
    } else if (value4 instanceof ClientTickEndC2SPacket) {
      if (this.testContext.enabled) {
        for (BlockPos blockPos2 :
            (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (this.pendingPlacements)) {
          this.checkPlacementLook(blockPos2, this.lastEyePosition, "idle tick");
        }
      }

      this.pendingPlacements.clear();
      this.interactedSinceBoundary = false;
      this.movementSent = false;
      this.placementsThisTick = 0;
    }

    if (this.testContext.enabled && value4 instanceof UpdateSelectedSlotC2SPacket) {
      if (this.interactedSinceBoundary) {
        this.testContext.list2.add("slot change after use/attack");
      }

      if (this.movementSent) {
        this.testContext.list2.add("slot change after movement");
      }
    }

    if (value4 instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket) {
      this.testContext.atomicInteger2.set(updateSelectedSlotC2SPacket.getSelectedSlot());
    }

    if (value4 instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket) {
      if (this.testContext.enabled) {
        if (this.movementSent) {
          this.testContext.list2.add("placement after movement");
        }

        if (++this.placementsThisTick > 1) {
          this.testContext.list2.add("multiple placements in a tick");
        }

        this.lastEyePosition = new Vec3d(this.playerX, this.playerY + 1.62, this.playerZ);
        this.checkPlacementLook(
            playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos(),
            this.lastEyePosition,
            "pre-movement");
        this.pendingPlacements.add(playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos());
      }

      this.interactedSinceBoundary = true;
      Item item =
          this.testContext.atomicInteger2.get() == 0
              ? (Item) this.testContext.atomicReference.get()
              : (this.testContext.atomicInteger2.get() == 1
                  ? Items.END_CRYSTAL
                  : (this.testContext.atomicInteger2.get() == 2 ? Items.OBSIDIAN : Items.APPLE));
      String string =
          playerInteractBlockC2SPacket.getHand() == Hand.OFF_HAND
              ? "offhand"
              : (item == Items.END_CRYSTAL
                  ? "crystal"
                  : (item == Items.OBSIDIAN ? "obsidian" : "other"));
      this.testContext.list.add(
          new HoppinStandingBeforeTestData3(
              string,
              this.testContext.atomicInteger.get(),
              playerInteractBlockC2SPacket.getSequence()));
    }

    if (value4 instanceof PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket) {
      playerInteractEntityC2SPacket.handle(new AttackPacketOrderObserver(this));
    }

    super.write(channelHandlerContext, value4, channelPromise);
  }
}

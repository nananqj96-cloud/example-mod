package bunnythekenzo.client.gametest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.List;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;

public class AnchorPacketOrderObserver extends ChannelOutboundHandlerAdapter {
  public int selectedSlot;
  public boolean usedSinceBoundary;
  public boolean movementSent;
  public int usesThisTick;
  public final List violations;
  public final List events;
  public final List sequences;

  public AnchorPacketOrderObserver(List list4, List list22, List list32) {
    this.violations = list4;
    this.events = list22;
    this.sequences = list32;
    this.selectedSlot = 2;
  }

  @Override
  public void write(
      ChannelHandlerContext channelHandlerContext, Object value, ChannelPromise channelPromise)
      throws Exception {
    ProtectionPerformanceTest.ensureInitialized();
    if (value instanceof ClientTickEndC2SPacket) {
      this.usedSinceBoundary = false;
      this.movementSent = false;
      this.usesThisTick = 0;
    } else if (value instanceof PlayerMoveC2SPacket) {
      this.usedSinceBoundary = false;
      this.movementSent = true;
    }

    if (value instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket) {
      if (this.usedSinceBoundary) {
        this.violations.add(
            "slot after use before tick boundary: "
                + updateSelectedSlotC2SPacket.getSelectedSlot());
      }

      if (this.movementSent) {
        this.violations.add(
            "slot after movement before tick end: "
                + updateSelectedSlotC2SPacket.getSelectedSlot());
      }

      this.selectedSlot = updateSelectedSlotC2SPacket.getSelectedSlot();
      int count3 = this.selectedSlot;
      this.events.add("slot:" + count3);
    }

    if (value instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket) {
      if (this.movementSent) {
        this.violations.add("use after movement before tick end");
      }

      if (++this.usesThisTick > 1) {
        this.violations.add("multiple block uses in one client tick");
      }

      this.usedSinceBoundary = true;
      int count4 = this.selectedSlot;
      this.events.add("use:" + count4);
      this.sequences.add(playerInteractBlockC2SPacket.getSequence());
      if (!playerInteractBlockC2SPacket
              .getBlockHitResult()
              .getBlockPos()
              .equals(AnchorTestObserverTest.blockPos)
          && !playerInteractBlockC2SPacket
              .getBlockHitResult()
              .getBlockPos()
              .equals(AnchorTestObserverTest.blockPos.down())) {
        this.events.add(
            "unexpected-hit:" + playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos());
      }
    }

    super.write(channelHandlerContext, value, channelPromise);
  }
}

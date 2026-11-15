package bunnythekenzo.client.gametest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.List;
import net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PearlPacketObserver extends ChannelOutboundHandlerAdapter {
  public final List useSequences;
  public final List eventOrder;
  public final List lookPitches;
  public final List viewDistances;

  public PearlPacketObserver(List list5, List list22, List list32, List list42) {
    this.useSequences = list5;
    this.eventOrder = list22;
    this.lookPitches = list32;
    this.viewDistances = list42;
  }

  @Override
  public void write(
      ChannelHandlerContext channelHandlerContext, Object value, ChannelPromise channelPromise)
      throws Exception {
    ProtectionPerformanceTest.ensureInitialized();
    if (value instanceof PlayerInteractItemC2SPacket playerInteractItemC2SPacket) {
      RelogTestObserverTest.apply(
          playerInteractItemC2SPacket.getPitch() == -90.0F, "pearl use packet points straight up");
      this.useSequences.add(playerInteractItemC2SPacket.getSequence());
      this.eventOrder.add("use");
    }

    if (value instanceof CommandExecutionC2SPacket commandExecutionC2SPacket
        && commandExecutionC2SPacket.command().equals("rtp")) {
      this.eventOrder.add("rtp");
    }

    if (value instanceof PlayerMoveC2SPacket playerMoveC2SPacket
        && playerMoveC2SPacket.changesLook()) {
      this.lookPitches.add(playerMoveC2SPacket.getPitch(0.0F));
    }

    if (value instanceof ClientOptionsC2SPacket clientOptionsC2SPacket) {
      this.viewDistances.add(clientOptionsC2SPacket.options().viewDistance());
    }

    super.write(channelHandlerContext, value, channelPromise);
  }
}

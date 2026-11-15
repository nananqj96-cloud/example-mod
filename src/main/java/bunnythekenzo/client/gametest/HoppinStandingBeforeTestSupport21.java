package bunnythekenzo.client.gametest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.PlayerInput;

public final class HoppinStandingBeforeTestSupport21 extends ChannelOutboundHandlerAdapter {
  public final List list;
  public final List list2 = new CopyOnWriteArrayList();
  public volatile PlayerInput playerInput = PlayerInput.DEFAULT;
  public volatile boolean enabled;

  public HoppinStandingBeforeTestSupport21(List list3) {
    this.list = list3;
  }

  @Override
  public void write(
      ChannelHandlerContext channelHandlerContext, Object value, ChannelPromise channelPromise)
      throws Exception {
    ProtectionPerformanceTest.ensureInitialized();
    if (value instanceof PlayerInputC2SPacket playerInputC2SPacket) {
      this.playerInput = playerInputC2SPacket.input();
    }

    if (value instanceof ClientCommandC2SPacket clientCommandC2SPacket) {
      if (clientCommandC2SPacket.getMode() == Mode.START_SPRINTING) {
        this.enabled = true;
      }

      if (clientCommandC2SPacket.getMode() == Mode.STOP_SPRINTING) {
        this.enabled = false;
      }
    }

    if (value instanceof ClickSlotC2SPacket clickSlotC2SPacket) {
      this.list.add(clickSlotC2SPacket);
      if (this.enabled || !this.playerInput.equals(PlayerInput.DEFAULT)) {
        List list22 = this.list2;
        short shortValue = clickSlotC2SPacket.slot();
        boolean enabled2 = this.enabled;
        String text = String.valueOf(this.playerInput);
        boolean flag = enabled2;
        short shortValue2 = shortValue;
        list22.add("click=" + shortValue2 + ", sprinting=" + flag + ", input=" + text);
      }
    }

    super.write(channelHandlerContext, value, channelPromise);
  }
}

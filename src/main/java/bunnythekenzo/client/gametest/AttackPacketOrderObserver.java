package bunnythekenzo.client.gametest;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket.Handler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class AttackPacketOrderObserver implements Handler {
  public final CrystalPacketOrderObserver crystalPacketOrderObserver;

  public AttackPacketOrderObserver(CrystalPacketOrderObserver crystalPacketOrderObserver2) {
    this.crystalPacketOrderObserver = crystalPacketOrderObserver2;
  }

  @Override
  public void interact(Hand hand) {}

  @Override
  public void interactAt(Hand hand, Vec3d vec3d) {}

  @Override
  public void attack() {
    ProtectionPerformanceTest.ensureInitialized();
    if (this.crystalPacketOrderObserver.testContext.enabled
        && this.crystalPacketOrderObserver.movementSent) {
      this.crystalPacketOrderObserver.testContext.list2.add("attack after movement");
    }

    this.crystalPacketOrderObserver.interactedSinceBoundary = true;
    this.crystalPacketOrderObserver.testContext.list.add(
        new HoppinStandingBeforeTestData3(
            "attack", this.crystalPacketOrderObserver.testContext.atomicInteger.get(), -1));
  }
}

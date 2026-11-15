package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public final class EmoteConfettiSystem {
  public static final double value = 11.2F;
  public static final int count = 64;
  public final List list = new ArrayList();
  public final List list2 = Collections.unmodifiableList(this.list);
  public ClientWorld clientWorld;

  public List getList2() {
    return this.list2;
  }

  public void update() {
    this.list.clear();
    this.clientWorld = null;
  }

  public void apply(MinecraftClient minecraftClient) {
    this.update();
    if (minecraftClient.world != null && minecraftClient.player != null) {
      this.clientWorld = minecraftClient.world;
      double doubleValue = Math.toRadians(minecraftClient.player.bodyYaw);
      Vec3d vec3d = new Vec3d(-Math.sin(doubleValue), 0.0, Math.cos(doubleValue));
      Vec3d vec3d2 = new Vec3d(vec3d.z, 0.0, -vec3d.x);
      Random random = this.clientWorld.random;

      for (int index = 0; index < 64; index++) {
        double doubleValue2 = (random.nextDouble() - 0.5) * 0.27;
        Vec3d vec3d3 =
            minecraftClient
                .player
                .getEntityPos()
                .add(0.0, 1.45 + random.nextDouble() * 0.22, 0.0)
                .add(vec3d.multiply(0.4))
                .add(vec3d2.multiply(index % 2 == 0 ? -0.32 : 0.32));
        Vec3d vec3d4 =
            vec3d
                .multiply(0.28 + random.nextDouble() * 0.22)
                .add(vec3d2.multiply(doubleValue2))
                .add(0.0, 0.12 + random.nextDouble() * 0.12, 0.0);
        this.list.add(
            new EmoteConfettiParticle(
                vec3d3,
                vec3d4,
                0.055F + random.nextFloat() * 0.04F,
                0.023F + random.nextFloat() * 0.02F,
                random.nextFloat() * 6.283185F,
                (random.nextFloat() - 0.5F) * 0.8F,
                80 + random.nextInt(21)));
      }
    }
  }

  public void tick(MinecraftClient minecraftClient) {
    if (minecraftClient.world != this.clientWorld
        || minecraftClient.player == null
        || !minecraftClient.player.isAlive()) {
      this.update();
    } else if (!minecraftClient.isPaused()) {
      for (EmoteConfettiParticle emoteConfettiParticle :
          (java.lang.Iterable<EmoteConfettiParticle>) (java.lang.Iterable<?>) (this.list)) {
        emoteConfettiParticle.apply(minecraftClient);
      }

      this.list.removeIf(
          value2 -> {
            return ((bunnythekenzo.client.EmoteConfettiParticle) (value2)).count
                >= ((bunnythekenzo.client.EmoteConfettiParticle) (value2)).count3;
          });
    }
  }
}

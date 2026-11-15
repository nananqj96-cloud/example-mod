package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.DeepscanChunkFinderModule;

public final class DeepscanChunkRenderer {
  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      DeepscanChunkFinderModule deepscanChunkFinderModule) {
    for (ChunkPos chunkPos :
        (java.lang.Iterable<ChunkPos>)
            (java.lang.Iterable<?>) (deepscanChunkFinderModule.deepscanWorker.getList())) {
      double doubleValue = chunkPos.x * 16.0;
      double doubleValue2 = chunkPos.z * 16.0;
      double value =
          VertexConsumerSupport.computeDouble(63.0, doubleValue + 8.0, doubleValue2 + 8.0);
      VertexConsumerSupport.render(
          immediate,
          matrixStack,
          vec3d,
          doubleValue,
          doubleValue2,
          doubleValue + 16.0,
          doubleValue2 + 16.0,
          value,
          1711341368);
    }

    VertexConsumerSupport.apply(immediate);
  }
}

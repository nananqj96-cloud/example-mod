package bunnythekenzo.client;

import java.util.List;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.SpawnerNametagsModule;

public final class SpawnerEspRenderer {
  public static final int count = -24576;
  public static final double value = 16.0;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      SpawnerNametagsModule spawnerNametagsModule) {
    List list = spawnerNametagsModule.blockPredicateScanner.getList();
    if (!list.isEmpty()) {
      boolean flag = (Boolean) spawnerNametagsModule.rangeRingSetting.getValue();
      boolean flag2 = (Boolean) spawnerNametagsModule.boxSetting.getValue();
      boolean flag3 = (Boolean) spawnerNametagsModule.tracersSetting.getValue();
      if (flag || flag2 || flag3) {
        for (BlockPos blockPos : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (list)) {
          if (flag2) {
            VertexConsumerSupport4.render(
                immediate,
                matrixStack,
                vec3d,
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                blockPos.getX() + 1,
                blockPos.getY() + 1,
                blockPos.getZ() + 1,
                -24576,
                1.0F);
          }

          if (flag) {
            VertexConsumerSupport4.render(
                immediate,
                matrixStack,
                vec3d,
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                16.0,
                48,
                ArgbColors.withOpacity(-24576, 0.6F),
                2.0F);
          }

          if (flag3) {
            WorldLabelRenderer.render(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                -1258315776,
                1.0F,
                0.0F);
          }
        }

        VertexConsumerSupport4.apply(immediate);
      }
    }
  }
}

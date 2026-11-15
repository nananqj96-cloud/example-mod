package bunnythekenzo.client;

import java.util.Collection;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.BlockEntityESPModule;

public final class BlockEntityEspRenderer {
  public static final double value = 0.002;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      BlockEntityESPModule blockEntityESPModule) {
    Collection collection = blockEntityESPModule.getCollection();
    if (!collection.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      ClientPlayerEntity player = instance.player;
      if (world != null && player != null) {
        boolean flag = blockEntityESPModule.modeSetting.isSelected("Full");
        int intValue = Math.clamp(blockEntityESPModule.highlightAlphaSetting.intValue(), 0, 255);
        boolean flag2 = (Boolean) blockEntityESPModule.showGhostsSetting.getValue();
        int integer = blockEntityESPModule.ghostTintSetting.getInteger();
        double doubleValue = (Double) blockEntityESPModule.rangeSetting.getValue();
        double doubleValue2 = doubleValue * doubleValue;
        int value2 = blockEntityESPModule.maxYLevelSetting.intValue();
        boolean flag3 = (Boolean) blockEntityESPModule.tracersSetting.getValue();

        for (BlockEntityESPData blockEntityESPData :
            (java.lang.Iterable<BlockEntityESPData>) (java.lang.Iterable<?>) (collection)) {
          String text = blockEntityESPData.string();
          if (blockEntityESPModule.blockEntitiesSetting.test(text)) {
            BlockPos blockPos = blockEntityESPData.blockPos();
            if (blockPos.getY() <= value2
                && !(player.squaredDistanceTo(
                        blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5)
                    > doubleValue2)) {
              boolean chunkLoaded =
                  world.getChunkManager().isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4);
              boolean flag4 = !chunkLoaded || world.getBlockEntity(blockPos) == null;
              if (!flag4 || flag2) {
                int intValue2 =
                    computeInt(
                        blockEntityESPModule.blockEntitiesSetting.computeInt(text), flag4, integer);
                int intValue3 = intValue2 & 16777215;
                int intValue4 = intValue3 | Math.min(intValue, intValue2 >>> 24) << 24;
                if (flag) {
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      blockPos.getX() - 0.002,
                      blockPos.getY() - 0.002,
                      blockPos.getZ() - 0.002,
                      blockPos.getX() + 1 + 0.002,
                      blockPos.getY() + 1 + 0.002,
                      blockPos.getZ() + 1 + 0.002,
                      intValue4);
                } else {
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
                      intValue4,
                      1.0F);
                }

                if (flag3) {
                  WorldLabelRenderer.render(
                      blockPos.getX() + 0.5,
                      blockPos.getY() + 0.5,
                      blockPos.getZ() + 0.5,
                      -1275068416 | intValue3,
                      1.0F,
                      (float) doubleValue);
                }
              }
            }
          }
        }

        VertexConsumerSupport4.apply(immediate);
      }
    }
  }

  public static int computeInt(int intValue, boolean flag, int intValue2) {
    int intValue3 = intValue & 16777215;
    int intValue4 = intValue >>> 24;
    if (intValue4 == 0) {
      intValue4 = 200;
    }

    if (!flag) {
      return intValue4 << 24 | intValue3;
    }

    int intValue5 = intValue3 >> 16 & 0xFF;
    int intValue6 = intValue3 >> 8 & 0xFF;
    int intValue7 = intValue3 & 0xFF;
    int intValue8 = intValue2 >> 16 & 0xFF;
    int intValue9 = intValue2 >> 8 & 0xFF;
    int intValue10 = intValue2 & 0xFF;
    int intValue11 = intValue2 >>> 24;
    if (intValue11 == 0) {
      intValue11 = 160;
    }

    return Math.min(intValue4, intValue11) << 24
        | (intValue5 + intValue8) / 2 << 16
        | (intValue6 + intValue9) / 2 << 8
        | (intValue7 + intValue10) / 2;
  }
}

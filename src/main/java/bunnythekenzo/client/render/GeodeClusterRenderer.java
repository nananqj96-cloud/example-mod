package bunnythekenzo.client.render;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3fc;
import bunnythekenzo.client.VertexConsumerSupport4;
import bunnythekenzo.client.WorldLabelRenderer;
import bunnythekenzo.client.module.ClusterESPModule;

public final class GeodeClusterRenderer {
  public static final double value = 55.0;
  public static final double value2 = 0.05;
  public static final int count = 11822335;
  public static final int count2 = 400;
  public static final int count3 = 80;

  public static void update() {}

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      ClusterESPModule clusterESPModule) {
    Map map = clusterESPModule.getMap();
    if (!map.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      Camera camera = instance.gameRenderer.getCamera();
      Vector3fc horizontalPlane = camera.getHorizontalPlane();
      Vec3d vec3d2 = new Vec3d(horizontalPlane.x(), horizontalPlane.y(), horizontalPlane.z());
      boolean flag = (Boolean) clusterESPModule.onlyLookingDirSetting.getValue();
      boolean flag2 = (Boolean) clusterESPModule.renderChunkOverlaySetting.getValue();
      boolean flag3 = (Boolean) clusterESPModule.showTracersSetting.getValue();
      int intValue = computeInt(clusterESPModule.espAlphaSetting.intValue());
      int intValue2 = computeInt(clusterESPModule.chunkAlphaSetting.intValue());
      int intValue3 = computeInt(clusterESPModule.tracerAlphaSetting.intValue());
      int index = 0;

      for (Entry entry : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (map.entrySet())) {
        long longValue = (Long) entry.getKey();
        Set set = (Set) entry.getValue();
        if (set != null && !set.isEmpty()) {
          ChunkPos chunkPos = new ChunkPos(longValue);
          if (flag2 && clusterESPModule.test(longValue)) {
            double centerX = chunkPos.getCenterX();
            double centerZ = chunkPos.getCenterZ();
            if (!flag || test(vec3d2, centerX - vec3d.x, 55.0 - vec3d.y, centerZ - vec3d.z)) {
              double startX = chunkPos.getStartX();
              double startZ = chunkPos.getStartZ();
              double doubleValue = chunkPos.getEndX() + 1.0;
              double doubleValue2 = chunkPos.getEndZ() + 1.0;
              VertexConsumerSupport4.render(
                  immediate,
                  matrixStack,
                  vec3d,
                  startX,
                  55.0,
                  startZ,
                  doubleValue,
                  55.05,
                  doubleValue2,
                  intValue2);
              VertexConsumerSupport4.render(
                  immediate,
                  matrixStack,
                  vec3d,
                  startX,
                  55.0,
                  startZ,
                  doubleValue,
                  55.05,
                  doubleValue2,
                  intValue2,
                  1.5F);
            }
          }

          int index2 = 0;

          for (BlockPos blockPos : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (set)) {
            if (index2 >= 400) {
              break;
            }

            double doubleValue3 = blockPos.getX() + 0.5;
            double doubleValue4 = blockPos.getY() + 0.5;
            double doubleValue5 = blockPos.getZ() + 0.5;
            if (!flag
                || test(
                    vec3d2,
                    doubleValue3 - vec3d.x,
                    doubleValue4 - vec3d.y,
                    doubleValue5 - vec3d.z)) {
              index2++;
              VertexConsumerSupport4.render(
                  immediate,
                  matrixStack,
                  vec3d,
                  blockPos.getX() + 0.0625,
                  blockPos.getY(),
                  blockPos.getZ() + 0.0625,
                  blockPos.getX() + 0.9375,
                  blockPos.getY() + 0.875,
                  blockPos.getZ() + 0.9375,
                  intValue,
                  1.0F);
              VertexConsumerSupport4.render(
                  immediate,
                  matrixStack,
                  vec3d,
                  blockPos.getX() + 0.0625,
                  blockPos.getY(),
                  blockPos.getZ() + 0.0625,
                  blockPos.getX() + 0.9375,
                  blockPos.getY() + 0.875,
                  blockPos.getZ() + 0.9375,
                  intValue);
              if (flag3 && index++ < 80) {
                WorldLabelRenderer.render(
                    doubleValue3, doubleValue4, doubleValue5, intValue3, 1.0F, 0.0F);
              }
            }
          }
        }
      }

      VertexConsumerSupport4.apply(immediate);
    }
  }

  public static void apply(FontManager fontManager, ClusterESPModule clusterESPModule) {
    String text = "[Cluster ESP] ";
    fontManager.computeFloat(text, 10.0F, 16.0F, 13.0F, -4954881);
    fontManager.computeFloat(
        clusterESPModule.computeInt() + " geodes",
        10.0F + fontManager.computeFloat(text, 13.0F),
        16.0F,
        13.0F,
        -1);
  }

  public static boolean test(
      Vec3d vec3d, double doubleValue, double doubleValue2, double doubleValue3) {
    return vec3d.x * doubleValue + vec3d.y * doubleValue2 + vec3d.z * doubleValue3 > 0.0;
  }

  public static int computeInt(int intValue) {
    return Math.clamp(intValue, 0, 255) << 24 | 11822335;
  }
}

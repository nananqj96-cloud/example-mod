package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.SpawnerBeamModule;

public final class VertexConsumerSupport2 {
  public static final double value = Math.PI * 2;
  public static final int count = 12;
  public static final int count2 = 32;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      SpawnerBeamModule spawnerBeamModule) {
    List list2 = spawnerBeamModule.incrementalChunkScanner.getList2();
    if (!list2.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      if (world != null) {
        ArrayList arrayList = new ArrayList(list2.size());

        for (BlockPos blockPos : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (list2)) {
          if (world.getChunkManager().isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4)
              && world.getBlockState(blockPos).isOf(Blocks.SPAWNER)) {
            arrayList.add(blockPos);
          }
        }

        if (!arrayList.isEmpty()) {
          double doubleValue = System.nanoTime() % 1000000000000L / 1.0E9;
          double doubleValue2 = (Double) spawnerBeamModule.animationSpeedSetting.getValue();
          float floatValue = spawnerBeamModule.opacitySetting.floatValue() / 100.0F;
          float floatValue2 = spawnerBeamModule.glowSetting.floatValue() / 100.0F;
          VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer);
          Entry entry = matrixStack.peek();

          for (BlockPos blockPos2 :
              (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (arrayList)) {
            VertexConsumerData vertexConsumerData =
                getVertexConsumerData(spawnerBeamModule, doubleValue, blockPos2);
            render(
                buffer,
                entry,
                vec3d,
                blockPos2,
                spawnerBeamModule,
                vertexConsumerData,
                doubleValue,
                doubleValue2,
                floatValue,
                floatValue2);
            if ((Boolean) spawnerBeamModule.groundGlowSetting.getValue()) {
              render(
                  buffer,
                  entry,
                  vec3d,
                  blockPos2,
                  spawnerBeamModule,
                  vertexConsumerData,
                  doubleValue,
                  doubleValue2,
                  floatValue);
            }
          }

          immediate.draw(VertexConsumerSupport.renderLayer);

          for (BlockPos blockPos3 :
              (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (arrayList)) {
            VertexConsumerData vertexConsumerData2 =
                getVertexConsumerData(spawnerBeamModule, doubleValue, blockPos3);
            render(
                immediate,
                matrixStack,
                vec3d,
                blockPos3,
                spawnerBeamModule,
                vertexConsumerData2,
                doubleValue,
                doubleValue2,
                floatValue);
            if ((Boolean) spawnerBeamModule.tracersSetting.getValue()) {
              WorldLabelRenderer.render(
                  blockPos3.getX() + 0.5,
                  blockPos3.getY() + 0.5,
                  blockPos3.getZ() + 0.5,
                  computeInt(vertexConsumerData2.count(), Math.round(floatValue * 210.0F)),
                  1.25F,
                  0.0F);
            }
          }

          immediate.draw(VertexConsumerSupport.renderLayer2);
        }
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      BlockPos blockPos,
      SpawnerBeamModule spawnerBeamModule,
      VertexConsumerData vertexConsumerData,
      double doubleValue,
      double doubleValue2,
      float floatValue,
      float floatValue2) {
    double doubleValue3 = computeDouble(blockPos);
    double doubleValue4 = doubleValue * doubleValue2;
    double doubleValue5 =
        (java.lang.Boolean) (spawnerBeamModule.pulseSetting.getValue())
            ? 1.0 + Math.sin(doubleValue4 * (Math.PI * 2) * 0.55 + doubleValue3) * 0.085
            : 1.0;
    double doubleValue6 = blockPos.getX() + 0.5 - vec3d.x;
    double doubleValue7 = blockPos.getZ() + 0.5 - vec3d.z;
    double doubleValue8 = blockPos.getY() + 0.08 - vec3d.y;
    double doubleValue9 = doubleValue8 + (Double) spawnerBeamModule.heightSetting.getValue();
    double doubleValue10 =
        (Double) spawnerBeamModule.beamWidthSetting.getValue() * 0.5 * doubleValue5;
    byte byteValue;
    double doubleValue11;
    double doubleValue12;
    if (spawnerBeamModule.styleSetting.isSelected("Beacon")) {
      byteValue = 4;
      doubleValue11 = Math.PI / 4;
      doubleValue12 = 0.0;
    } else if (spawnerBeamModule.styleSetting.isSelected("Prism")) {
      byteValue = 6;
      doubleValue11 = doubleValue4 * 0.55 + doubleValue3;
      doubleValue12 = 0.3;
    } else {
      byteValue = 12;
      doubleValue11 = doubleValue4 * 0.28 + doubleValue3;
      doubleValue12 = 0.7;
    }

    if (floatValue2 > 0.001F) {
      double doubleValue13 = doubleValue10 * (1.15 + floatValue2 * 1.85);
      render(
          vertexConsumer,
          entry,
          doubleValue6,
          doubleValue8,
          doubleValue7,
          doubleValue9,
          doubleValue13,
          Math.max(8, byteValue),
          doubleValue11 * 0.45,
          doubleValue12 * 0.35,
          vertexConsumerData.count(),
          vertexConsumerData.count2(),
          floatValue * floatValue2 * 0.18F,
          doubleValue4 + doubleValue3,
          true);
    }

    render(
        vertexConsumer,
        entry,
        doubleValue6,
        doubleValue8,
        doubleValue7,
        doubleValue9,
        doubleValue10,
        byteValue,
        doubleValue11,
        doubleValue12,
        vertexConsumerData.count(),
        vertexConsumerData.count2(),
        floatValue * 0.46F,
        doubleValue4 + doubleValue3,
        false);
    render(
        vertexConsumer,
        entry,
        doubleValue6,
        doubleValue8,
        doubleValue7,
        doubleValue9,
        Math.max(0.09, doubleValue10 * 0.22),
        spawnerBeamModule.styleSetting.isSelected("Beacon") ? 4 : 8,
        -doubleValue11 * 0.65,
        -doubleValue12 * 0.45,
        computeInt(vertexConsumerData.count(), 0.42F),
        computeInt(vertexConsumerData.count2(), 0.42F),
        floatValue * 0.92F,
        doubleValue4 + doubleValue3 + 0.5,
        false);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      int intValue,
      double doubleValue6,
      double doubleValue7,
      int intValue2,
      int intValue3,
      float floatValue,
      double doubleValue8,
      boolean flag) {
    if (!(floatValue <= 0.001F) && !(doubleValue5 <= 0.001)) {
      for (int index = 0; index < 12; index++) {
        float floatValue2 = index / 12.0F;
        float floatValue3 = (index + 1) / 12.0F;
        double doubleValue9 = MathHelper.lerp(floatValue2, doubleValue2, doubleValue4);
        double doubleValue10 = MathHelper.lerp(floatValue3, doubleValue2, doubleValue4);
        double doubleValue11 = doubleValue6 + doubleValue7 * floatValue2;
        double doubleValue12 = doubleValue6 + doubleValue7 * floatValue3;
        int intValue4 = computeInt(intValue2, intValue3, floatValue2);
        int intValue5 = computeInt(intValue2, intValue3, floatValue3);
        float floatValue4 =
            0.88F + 0.12F * (float) Math.sin(doubleValue8 * 3.1 + floatValue2 * 15.0);
        float floatValue5 =
            0.88F + 0.12F * (float) Math.sin(doubleValue8 * 3.1 + floatValue3 * 15.0);
        float floatValue6 = flag ? computeFloat(floatValue2) : 1.0F;
        float floatValue7 = flag ? computeFloat(floatValue3) : 1.0F;
        int intValue6 =
            computeInt(intValue4, Math.round(255.0F * floatValue * floatValue4 * floatValue6));
        int intValue7 =
            computeInt(intValue5, Math.round(255.0F * floatValue * floatValue5 * floatValue7));

        for (int index2 = 0; index2 < intValue; index2++) {
          double doubleValue13 = (Math.PI * 2) * index2 / intValue;
          double doubleValue14 = (Math.PI * 2) * (index2 + 1) / intValue;
          float floatValue8 =
              (float) (doubleValue + Math.cos(doubleValue13 + doubleValue11) * doubleValue5);
          float floatValue9 =
              (float) (doubleValue3 + Math.sin(doubleValue13 + doubleValue11) * doubleValue5);
          float floatValue10 =
              (float) (doubleValue + Math.cos(doubleValue14 + doubleValue11) * doubleValue5);
          float floatValue11 =
              (float) (doubleValue3 + Math.sin(doubleValue14 + doubleValue11) * doubleValue5);
          float floatValue12 =
              (float) (doubleValue + Math.cos(doubleValue14 + doubleValue12) * doubleValue5);
          float floatValue13 =
              (float) (doubleValue3 + Math.sin(doubleValue14 + doubleValue12) * doubleValue5);
          float floatValue14 =
              (float) (doubleValue + Math.cos(doubleValue13 + doubleValue12) * doubleValue5);
          float floatValue15 =
              (float) (doubleValue3 + Math.sin(doubleValue13 + doubleValue12) * doubleValue5);
          render(
              vertexConsumer,
              entry,
              floatValue8,
              (float) doubleValue9,
              floatValue9,
              intValue6,
              floatValue10,
              (float) doubleValue9,
              floatValue11,
              intValue6,
              floatValue12,
              (float) doubleValue10,
              floatValue13,
              intValue7,
              floatValue14,
              (float) doubleValue10,
              floatValue15,
              intValue7);
        }
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      BlockPos blockPos,
      SpawnerBeamModule spawnerBeamModule,
      VertexConsumerData vertexConsumerData,
      double doubleValue,
      double doubleValue2,
      float floatValue) {
    double doubleValue3 = computeDouble(blockPos);
    double doubleValue4 =
        (java.lang.Boolean) (spawnerBeamModule.pulseSetting.getValue())
            ? 1.0
                + Math.sin(doubleValue * doubleValue2 * (Math.PI * 2) * 0.55 + doubleValue3) * 0.06
            : 1.0;
    double doubleValue5 = (Double) spawnerBeamModule.glowRadiusSetting.getValue() * doubleValue4;
    double doubleValue6 = blockPos.getX() + 0.5 - vec3d.x;
    double doubleValue7 = blockPos.getY() + 0.035 - vec3d.y;
    double doubleValue8 = blockPos.getZ() + 0.5 - vec3d.z;

    for (int index = 0; index < 5; index++) {
      double doubleValue9 = doubleValue5 * index / 5.0;
      double doubleValue10 = doubleValue5 * (index + 1) / 5.0;
      float floatValue2 = 1.0F - index / 5.0F;
      float floatValue3 = 1.0F - (index + 1) / 5.0F;
      int intValue =
          computeInt(
              computeInt(vertexConsumerData.count(), vertexConsumerData.count2(), index / 5.0F),
              Math.round(floatValue * 72.0F * floatValue2 * floatValue2));
      int intValue2 =
          computeInt(
              computeInt(
                  vertexConsumerData.count(), vertexConsumerData.count2(), (index + 1) / 5.0F),
              Math.round(floatValue * 72.0F * floatValue3 * floatValue3));

      for (int index2 = 0; index2 < 32; index2++) {
        double doubleValue11 = (Math.PI * 2) * index2 / 32.0;
        double doubleValue12 = (Math.PI * 2) * (index2 + 1) / 32.0;
        render(
            vertexConsumer,
            entry,
            (float) (doubleValue6 + Math.cos(doubleValue11) * doubleValue9),
            (float) doubleValue7,
            (float) (doubleValue8 + Math.sin(doubleValue11) * doubleValue9),
            intValue,
            (float) (doubleValue6 + Math.cos(doubleValue11) * doubleValue10),
            (float) doubleValue7,
            (float) (doubleValue8 + Math.sin(doubleValue11) * doubleValue10),
            intValue2,
            (float) (doubleValue6 + Math.cos(doubleValue12) * doubleValue10),
            (float) doubleValue7,
            (float) (doubleValue8 + Math.sin(doubleValue12) * doubleValue10),
            intValue2,
            (float) (doubleValue6 + Math.cos(doubleValue12) * doubleValue9),
            (float) doubleValue7,
            (float) (doubleValue8 + Math.sin(doubleValue12) * doubleValue9),
            intValue);
      }
    }
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      BlockPos blockPos,
      SpawnerBeamModule spawnerBeamModule,
      VertexConsumerData vertexConsumerData,
      double doubleValue,
      double doubleValue2,
      float floatValue) {
    if ((Boolean) spawnerBeamModule.spawnerBoxSetting.getValue()) {
      VertexConsumerSupport4.render(
          immediate,
          matrixStack,
          vec3d,
          blockPos.getX() - 0.025,
          blockPos.getY() - 0.025,
          blockPos.getZ() - 0.025,
          blockPos.getX() + 1.025,
          blockPos.getY() + 1.025,
          blockPos.getZ() + 1.025,
          computeInt(vertexConsumerData.count(), Math.round(floatValue * 255.0F)),
          2.5F);
    }

    double doubleValue3 = computeDouble(blockPos);
    double doubleValue4 =
        (java.lang.Boolean) (spawnerBeamModule.pulseSetting.getValue())
            ? 1.0
                + Math.sin(doubleValue * doubleValue2 * (Math.PI * 2) * 0.55 + doubleValue3) * 0.06
            : 1.0;
    if ((Boolean) spawnerBeamModule.groundGlowSetting.getValue()) {
      double doubleValue5 = (Double) spawnerBeamModule.glowRadiusSetting.getValue() * doubleValue4;

      for (int index = 0; index < 3; index++) {
        double doubleValue6 =
            doubleValue2 <= 0.001
                ? index / 3.0
                : (doubleValue * doubleValue2 * 0.22 + index / 3.0 + doubleValue3 / (Math.PI * 2))
                    % 1.0;
        double doubleValue7 = doubleValue5 * (0.28 + doubleValue6 * 0.72);
        float floatValue2 = (float) (1.0 - doubleValue6);
        int intValue =
            computeInt(
                vertexConsumerData.count(), vertexConsumerData.count2(), (float) doubleValue6);
        VertexConsumerSupport4.render(
            immediate,
            matrixStack,
            vec3d,
            blockPos.getX() + 0.5,
            blockPos.getY() + 0.055,
            blockPos.getZ() + 0.5,
            doubleValue7,
            32,
            computeInt(intValue, Math.round(floatValue * 210.0F * floatValue2)),
            1.4F + floatValue2);
      }
    }

    if ((Boolean) spawnerBeamModule.energySpiralSetting.getValue()) {
      render(
          immediate,
          matrixStack.peek(),
          vec3d,
          blockPos,
          spawnerBeamModule,
          vertexConsumerData,
          doubleValue,
          doubleValue2,
          floatValue);
    }
  }

  public static void render(
      Immediate immediate,
      Entry entry,
      Vec3d vec3d,
      BlockPos blockPos,
      SpawnerBeamModule spawnerBeamModule,
      VertexConsumerData vertexConsumerData,
      double doubleValue,
      double doubleValue2,
      float floatValue) {
    VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer2);
    double doubleValue3 = (Double) spawnerBeamModule.heightSetting.getValue();
    double doubleValue4 = (Double) spawnerBeamModule.beamWidthSetting.getValue() * 0.69;
    double doubleValue5 = blockPos.getX() + 0.5 - vec3d.x;
    double doubleValue6 = blockPos.getY() + 0.12 - vec3d.y;
    double doubleValue7 = blockPos.getZ() + 0.5 - vec3d.z;
    double doubleValue8 = computeDouble(blockPos) + doubleValue * doubleValue2 * 1.15;
    int intValue = Math.clamp((int) (doubleValue3 / 5.0), 56, 144);
    double doubleValue9 = Math.clamp(doubleValue3 / 42.0, 3.0, 11.0);

    for (int index = 0; index < 2; index++) {
      double doubleValue10 = doubleValue8 + index * Math.PI;

      for (int index2 = 0; index2 < intValue; index2++) {
        float floatValue2 = (float) index2 / intValue;
        float floatValue3 = (float) (index2 + 1) / intValue;
        double doubleValue11 = doubleValue10 + doubleValue9 * (Math.PI * 2) * floatValue2;
        double doubleValue12 = doubleValue10 + doubleValue9 * (Math.PI * 2) * floatValue3;
        double doubleValue13 =
            doubleValue4
                * (0.92 + 0.08 * Math.sin(floatValue2 * (Math.PI * 2) * 3.0 + doubleValue8));
        double doubleValue14 =
            doubleValue4
                * (0.92 + 0.08 * Math.sin(floatValue3 * (Math.PI * 2) * 3.0 + doubleValue8));
        float floatValue4 = (float) (doubleValue5 + Math.cos(doubleValue11) * doubleValue13);
        float floatValue5 = (float) (doubleValue7 + Math.sin(doubleValue11) * doubleValue13);
        float floatValue6 = (float) (doubleValue5 + Math.cos(doubleValue12) * doubleValue14);
        float floatValue7 = (float) (doubleValue7 + Math.sin(doubleValue12) * doubleValue14);
        float floatValue8 = (float) (doubleValue6 + doubleValue3 * floatValue2);
        float floatValue9 = (float) (doubleValue6 + doubleValue3 * floatValue3);
        int intValue2 =
            computeInt(
                index == 0 ? vertexConsumerData.count() : vertexConsumerData.count2(),
                index == 0 ? vertexConsumerData.count2() : vertexConsumerData.count(),
                floatValue2);
        float floatValue10 = Math.min(1.0F, (1.0F - floatValue2) * 8.0F);
        int intValue3 = computeInt(intValue2, Math.round(floatValue * 225.0F * floatValue10));
        render(
            buffer,
            entry,
            floatValue4,
            floatValue8,
            floatValue5,
            floatValue6,
            floatValue9,
            floatValue7,
            intValue3,
            1.35F);
      }
    }
  }

  public static VertexConsumerData getVertexConsumerData(
      SpawnerBeamModule spawnerBeamModule, double doubleValue, BlockPos blockPos) {
    if (spawnerBeamModule.colorModeSetting.isSelected("Rainbow")) {
      float floatValue =
          (float)
              ((doubleValue * (Double) spawnerBeamModule.animationSpeedSetting.getValue() * 0.08
                      + computeDouble(blockPos) / (Math.PI * 2) * 0.12)
                  % 1.0);
      int count3 = MathHelper.hsvToRgb(floatValue, 0.72F, 1.0F) & 16777215;
      int count22 = MathHelper.hsvToRgb((floatValue + 0.18F) % 1.0F, 0.62F, 1.0F) & 16777215;
      return new VertexConsumerData(count3, count22);
    } else {
      int count4 = spawnerBeamModule.primaryColorSetting.getInteger() & 16777215;
      int count23 =
          spawnerBeamModule.colorModeSetting.isSelected("Single")
              ? count4
              : spawnerBeamModule.accentColorSetting.getInteger() & 16777215;
      return new VertexConsumerData(count4, count23);
    }
  }

  public static double computeDouble(BlockPos blockPos) {
    long longValue = blockPos.asLong() * -7046029254386353131L;
    return (longValue >>> 40 & 65535L) / 65535.0 * (Math.PI * 2);
  }

  public static float computeFloat(float floatValue) {
    return floatValue <= 0.82F ? 1.0F : MathHelper.lerp((floatValue - 0.82F) / 0.18F, 1.0F, 0.08F);
  }

  public static int computeInt(int intValue, float floatValue) {
    return computeInt(intValue, 16777215, floatValue);
  }

  public static int computeInt(int intValue, int intValue2, float floatValue) {
    floatValue = Math.clamp(floatValue, 0.0F, 1.0F);
    int intValue3 =
        Math.round(
            MathHelper.lerp(
                floatValue, (float) (intValue >> 16 & 0xFF), (float) (intValue2 >> 16 & 0xFF)));
    int intValue4 =
        Math.round(
            MathHelper.lerp(
                floatValue, (float) (intValue >> 8 & 0xFF), (float) (intValue2 >> 8 & 0xFF)));
    int intValue5 =
        Math.round(
            MathHelper.lerp(floatValue, (float) (intValue & 0xFF), (float) (intValue2 & 0xFF)));
    return intValue3 << 16 | intValue4 << 8 | intValue5;
  }

  public static int computeInt(int intValue, int intValue2) {
    return Math.clamp(intValue2, 0, 255) << 24 | intValue & 16777215;
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue2,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      int intValue3,
      float floatValue10,
      float floatValue11,
      float floatValue12,
      int intValue4) {
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
    vertexConsumer.vertex(entry, floatValue4, floatValue5, floatValue6).color(intValue2);
    vertexConsumer.vertex(entry, floatValue7, floatValue8, floatValue9).color(intValue3);
    vertexConsumer.vertex(entry, floatValue10, floatValue11, floatValue12).color(intValue4);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      float floatValue7) {
    float floatValue8 = floatValue4 - floatValue;
    float floatValue9 = floatValue5 - floatValue2;
    float floatValue10 = floatValue6 - floatValue3;
    float floatValue11 =
        (float)
            Math.sqrt(
                floatValue8 * floatValue8
                    + floatValue9 * floatValue9
                    + floatValue10 * floatValue10);
    if (floatValue11 > 1.0E-6F) {
      floatValue8 /= floatValue11;
      floatValue9 /= floatValue11;
      floatValue10 /= floatValue11;
    } else {
      floatValue9 = 1.0F;
    }

    vertexConsumer
        .vertex(entry, floatValue, floatValue2, floatValue3)
        .color(intValue)
        .normal(entry, floatValue8, floatValue9, floatValue10)
        .lineWidth(floatValue7);
    vertexConsumer
        .vertex(entry, floatValue4, floatValue5, floatValue6)
        .color(intValue)
        .normal(entry, floatValue8, floatValue9, floatValue10)
        .lineWidth(floatValue7);
  }
}

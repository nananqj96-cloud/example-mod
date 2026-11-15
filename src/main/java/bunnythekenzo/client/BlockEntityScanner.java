package bunnythekenzo.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.module.BlockESPModule;
import bunnythekenzo.client.setting.BlockFilterEntry;

public final class BlockEntityScanner {
  public static final int count = 12;
  public static final int count2 = 8000;
  public static final double value = 0.002;
  public static final IncrementalChunkScanner incrementalChunkScanner =
      new IncrementalChunkScanner(48, 80000, 20, 8000);
  public static Set set = Set.of();
  public static int count3 = Integer.MIN_VALUE;

  public static void update() {
    incrementalChunkScanner.update2();
    set = Set.of();
    count3 = Integer.MIN_VALUE;
  }

  public static int computeInt() {
    return incrementalChunkScanner.getList2().size();
  }

  public static void scan(BlockESPModule blockESPModule) {
    HashSet hashSet = new HashSet();

    for (BlockFilterEntry choiceSettingSupport :
        (java.lang.Iterable<BlockFilterEntry>)
            (java.lang.Iterable<?>) (blockESPModule.targetBlocksSetting.getList())) {
      if ((Boolean) choiceSettingSupport.enabledSetting.getValue()
          && choiceSettingSupport.getBlock() != null) {
        hashSet.add(choiceSettingSupport.getBlock());
      }
    }

    if (hashSet.isEmpty()) {
      incrementalChunkScanner.update2();
      set = Set.of();
    } else {
      int value2 = blockESPModule.maxYLevelSetting.intValue();
      if (!hashSet.equals(set) || count3 != value2) {
        set = Set.copyOf(hashSet);
        count3 = value2;
        incrementalChunkScanner.update();
      }

      int value3 = blockESPModule.rangeExtraChunksSetting.intValue();
      int intValue =
          Math.min(12, MinecraftClient.getInstance().options.getViewDistance().getValue() + value3);
      incrementalChunkScanner.apply(
          intValue,
          (worldChunk, intValue2, list, intValue3) -> {
            return scanSection(worldChunk, intValue2, hashSet, value2, list, intValue3);
          });
    }
  }

  public static int scanSection(
      WorldChunk worldChunk, int index, Set set2, int intValue, List list, int intValue2) {
    if (list.size() >= intValue2) {
      return 0;
    }

    ChunkSection[] sectionArray = worldChunk.getSectionArray();
    int bottomSectionCoord = worldChunk.getBottomSectionCoord();
    int startX = worldChunk.getPos().getStartX();
    int startZ = worldChunk.getPos().getStartZ();
    int intValue3 = bottomSectionCoord + index << 4;
    if (intValue3 > intValue) {
      return 0;
    }

    ChunkSection chunkSection = sectionArray[index];
    if (!chunkSection.isEmpty()
        && chunkSection.hasAny(
            blockState -> {
              return set2.contains(blockState.getBlock());
            })) {
      int intValue4 = Math.min(15, intValue - intValue3);
      int intValue5 = 0 + (intValue4 + 1) * 256;

      for (int index2 = 0; index2 <= intValue4; index2++) {
        for (int index3 = 0; index3 < 16; index3++) {
          for (int index4 = 0; index4 < 16; index4++) {
            Block block = chunkSection.getBlockState(index4, index2, index3).getBlock();
            if (set2.contains(block)) {
              list.add(
                  new BlockEntityScannerData(
                      startX + index4, intValue3 + index2, startZ + index3, block));
              if (list.size() >= intValue2) {
                return intValue5;
              }
            }
          }
        }
      }

      return intValue5;
    } else {
      return 0;
    }
  }

  public static void apply(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, BlockESPModule blockESPModule) {
    List list2 = incrementalChunkScanner.getList2();
    if (!list2.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      if (world != null) {
        HashMap hashMap = new HashMap();

        for (BlockFilterEntry choiceSettingSupport :
            (java.lang.Iterable<BlockFilterEntry>)
                (java.lang.Iterable<?>) (blockESPModule.targetBlocksSetting.getList())) {
          if ((Boolean) choiceSettingSupport.enabledSetting.getValue()
              && choiceSettingSupport.getBlock() != null) {
            hashMap.put(
                choiceSettingSupport.getBlock(), choiceSettingSupport.colorSetting.getInteger());
          }
        }

        if (!hashMap.isEmpty()) {
          int intValue = Math.clamp(blockESPModule.highlightAlphaSetting.intValue(), 0, 255);
          boolean flag = blockESPModule.modeSetting.isSelected("Full");
          boolean flag2 = (Boolean) blockESPModule.tracersSetting.getValue();
          int value2 = blockESPModule.maxYLevelSetting.intValue();
          float floatValue =
              (instance.options.getViewDistance().getValue()
                      + blockESPModule.rangeExtraChunksSetting.intValue())
                  * 16.0F;
          Mutable mutable = new Mutable();
          long MIN_VALUE = Long.MIN_VALUE;
          boolean flag3 = false;

          for (BlockEntityScannerData blockEntityScannerData :
              (java.lang.Iterable<BlockEntityScannerData>) (java.lang.Iterable<?>) (list2)) {
            if (blockEntityScannerData.count2() <= value2) {
              Integer intValue2 = (Integer) hashMap.get(blockEntityScannerData.block());
              if (intValue2 != null) {
                long longValue =
                    ChunkPos.toLong(
                        blockEntityScannerData.count() >> 4, blockEntityScannerData.count3() >> 4);
                if (longValue != MIN_VALUE) {
                  MIN_VALUE = longValue;
                  flag3 =
                      world
                          .getChunkManager()
                          .isChunkLoaded(
                              blockEntityScannerData.count() >> 4,
                              blockEntityScannerData.count3() >> 4);
                }

                if (flag3) {
                  BlockState blockState =
                      world.getBlockState(
                          mutable.set(
                              blockEntityScannerData.count(),
                              blockEntityScannerData.count2(),
                              blockEntityScannerData.count3()));
                  if (blockState.getBlock() != blockEntityScannerData.block()) {
                    continue;
                  }
                }

                int intValue3 = intValue2 & 16777215;
                int intValue4 = intValue3 | intValue << 24;
                if (flag) {
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      blockEntityScannerData.count() - 0.002,
                      blockEntityScannerData.count2() - 0.002,
                      blockEntityScannerData.count3() - 0.002,
                      blockEntityScannerData.count() + 1 + 0.002,
                      blockEntityScannerData.count2() + 1 + 0.002,
                      blockEntityScannerData.count3() + 1 + 0.002,
                      intValue4);
                } else {
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      blockEntityScannerData.count(),
                      blockEntityScannerData.count2(),
                      blockEntityScannerData.count3(),
                      blockEntityScannerData.count() + 1,
                      blockEntityScannerData.count2() + 1,
                      blockEntityScannerData.count3() + 1,
                      intValue4,
                      1.0F);
                }

                if (flag2) {
                  WorldLabelRenderer.render(
                      blockEntityScannerData.count() + 0.5,
                      blockEntityScannerData.count2() + 0.5,
                      blockEntityScannerData.count3() + 0.5,
                      -1275068416 | intValue3,
                      1.0F,
                      floatValue);
                }
              }
            }
          }

          VertexConsumerSupport4.apply(immediate);
        }
      }
    }
  }
}

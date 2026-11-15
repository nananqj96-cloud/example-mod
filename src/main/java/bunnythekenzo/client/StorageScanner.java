package bunnythekenzo.client;

import java.util.List;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.module.StorageESPModule;

public final class StorageScanner {
  public static final int count = 16;
  public static final int count2 = 6000;
  public static final float amount = 0.002F;
  public static final int count3 = 6579300;
  public static final IncrementalChunkScanner incrementalChunkScanner =
      new IncrementalChunkScanner(48, 80000, 20, 6000);
  public static int count4 = -1;
  public static double value = Double.NaN;
  public static int count5 = Integer.MIN_VALUE;

  public static void update() {
    incrementalChunkScanner.update2();
    count4 = -1;
    value = Double.NaN;
    count5 = Integer.MIN_VALUE;
  }

  public static int computeInt() {
    return incrementalChunkScanner.getList2().size();
  }

  public static long cachedShulkerCount() {
    return incrementalChunkScanner.getList2().stream()
        .filter(
            value2 -> {
              return ((bunnythekenzo.client.StorageScannerData) (value2)).storageType()
                  == StorageType.SHULKER;
            })
        .count();
  }

  public static void scan(StorageESPModule storageESPModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    double value2 = (Double) storageESPModule.rangeSetting.getValue();
    double doubleValue = value2 * value2;
    int value3 = storageESPModule.maxYLevelSetting.intValue();
    int intValue = (int) Math.ceil(value2 / 16.0) + 1;
    int count42 = Math.min(Math.min(16, intValue), instance.options.getViewDistance().getValue());
    if (world != null && instance.player != null) {
      if (count4 != count42 || Double.compare(value, value2) != 0 || count5 != value3) {
        count4 = count42;
        value = value2;
        count5 = value3;
        incrementalChunkScanner.update();
      }

      incrementalChunkScanner.apply(
          count42,
          (worldChunk, intValue2, list, intValue3) -> {
            return computeInt(worldChunk, intValue2, doubleValue, value3, list, intValue3);
          });
    }
  }

  public static int computeInt(
      WorldChunk worldChunk,
      int index,
      double doubleValue,
      int intValue,
      List list,
      int intValue2) {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null && list.size() < intValue2) {
      ChunkSection[] sectionArray = worldChunk.getSectionArray();
      int bottomSectionCoord = worldChunk.getBottomSectionCoord();
      int startX = worldChunk.getPos().getStartX();
      int startZ = worldChunk.getPos().getStartZ();
      int intValue3 = bottomSectionCoord + index << 4;
      if (intValue3 > intValue) {
        return 0;
      }

      ChunkSection chunkSection = sectionArray[index];
      if (!chunkSection.isEmpty() && chunkSection.hasAny(StorageScanner::test)) {
        int intValue4 = Math.min(15, intValue - intValue3);
        int intValue5 = 0 + (intValue4 + 1) * 256;

        for (int index2 = 0; index2 <= intValue4; index2++) {
          for (int index3 = 0; index3 < 16; index3++) {
            for (int index4 = 0; index4 < 16; index4++) {
              StorageType storageType =
                  getStorageType(chunkSection.getBlockState(index4, index2, index3).getBlock());
              if (storageType != null) {
                int count6 = startX + index4;
                int count22 = intValue3 + index2;
                int count32 = startZ + index3;
                if (!(player.squaredDistanceTo(count6 + 0.5, count22 + 0.5, count32 + 0.5)
                    > doubleValue)) {
                  list.add(new StorageScannerData(count6, count22, count32, storageType));
                  if (list.size() >= intValue2) {
                    return intValue5;
                  }
                }
              }
            }
          }
        }

        return intValue5;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      StorageESPModule storageESPModule) {
    List list2 = incrementalChunkScanner.getList2();
    if (!list2.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      ClientPlayerEntity player = instance.player;
      if (world != null && player != null) {
        boolean flag = storageESPModule.modeSetting.isSelected("Full");
        int intValue =
            Math.max(0, Math.min(255, storageESPModule.highlightAlphaSetting.intValue()));
        boolean flag2 = storageESPModule.test();
        double doubleValue = (Double) storageESPModule.rangeSetting.getValue();
        double doubleValue2 = doubleValue * doubleValue;
        int value2 = storageESPModule.maxYLevelSetting.intValue();
        boolean flag3 = (Boolean) storageESPModule.tracersSetting.getValue();
        Mutable mutable = new Mutable();
        long MIN_VALUE = Long.MIN_VALUE;
        boolean flag4 = false;

        for (StorageScannerData storageScannerData :
            (java.lang.Iterable<StorageScannerData>) (java.lang.Iterable<?>) (list2)) {
          if (storageScannerData.count2() <= value2
              && !(player.squaredDistanceTo(
                      storageScannerData.count() + 0.5,
                      storageScannerData.count2() + 0.5,
                      storageScannerData.count3() + 0.5)
                  > doubleValue2)) {
            long longValue =
                ChunkPos.toLong(storageScannerData.count() >> 4, storageScannerData.count3() >> 4);
            if (longValue != MIN_VALUE) {
              MIN_VALUE = longValue;
              flag4 =
                  world
                      .getChunkManager()
                      .isChunkLoaded(
                          storageScannerData.count() >> 4, storageScannerData.count3() >> 4);
            }

            BlockState blockState = null;
            StorageType storageType = storageScannerData.storageType();
            if (flag4) {
              blockState =
                  world.getBlockState(
                      mutable.set(
                          storageScannerData.count(),
                          storageScannerData.count2(),
                          storageScannerData.count3()));
              storageType = getStorageType(blockState.getBlock());
              if (storageType == null) {
                continue;
              }
            }

            if (storageESPModule.test(storageType)) {
              boolean flag5 =
                  storageESPModule.test(
                      storageScannerData.count(),
                      storageScannerData.count2(),
                      storageScannerData.count3());
              if (!flag5 || !flag2) {
                int intValue2 =
                    flag5 ? 6579300 : storageESPModule.computeInt(storageType) & 16777215;
                int intValue3 = intValue << 24 | intValue2;
                double doubleValue3 = storageScannerData.count();
                double doubleValue4 = storageScannerData.count2();
                double doubleValue5 = storageScannerData.count3();
                double doubleValue6 = doubleValue3 + 1.0;
                double doubleValue7 = doubleValue4 + 1.0;
                double doubleValue8 = doubleValue5 + 1.0;
                if (storageType == StorageType.CHEST
                    || storageType == StorageType.TRAPPED
                    || storageType == StorageType.ENDER) {
                  doubleValue3 += 0.0625;
                  doubleValue5 += 0.0625;
                  doubleValue6 -= 0.0625;
                  doubleValue7 -= 0.125;
                  doubleValue8 -= 0.0625;
                  if (blockState != null
                      && (storageType == StorageType.CHEST || storageType == StorageType.TRAPPED)
                      && blockState.getBlock() instanceof ChestBlock
                      && blockState.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
                    Direction direction = blockState.get(ChestBlock.FACING);
                    ChestType chestType = blockState.get(ChestBlock.CHEST_TYPE);
                    Direction direction2 =
                        chestType == ChestType.LEFT
                            ? direction.rotateYClockwise()
                            : direction.rotateYCounterclockwise();
                    switch (StorageScannerSupport.countArray[direction2.ordinal()]) {
                      case 1:
                        doubleValue3 = storageScannerData.count();
                        break;
                      case 2:
                        doubleValue6 = storageScannerData.count() + 1;
                        break;
                      case 3:
                        doubleValue5 = storageScannerData.count3();
                        break;
                      case 4:
                        doubleValue8 = storageScannerData.count3() + 1;
                    }
                  }
                }

                if (flag) {
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      doubleValue3 - 0.002F,
                      doubleValue4 - 0.002F,
                      doubleValue5 - 0.002F,
                      doubleValue6 + 0.002F,
                      doubleValue7 + 0.002F,
                      doubleValue8 + 0.002F,
                      intValue3);
                } else {
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      doubleValue3,
                      doubleValue4,
                      doubleValue5,
                      doubleValue6,
                      doubleValue7,
                      doubleValue8,
                      intValue3,
                      1.0F);
                }

                if (flag3) {
                  WorldLabelRenderer.render(
                      (doubleValue3 + doubleValue6) * 0.5,
                      (doubleValue4 + doubleValue7) * 0.5,
                      (doubleValue5 + doubleValue8) * 0.5,
                      -1275068416 | intValue2,
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

  public static boolean test(BlockState blockState) {
    return getStorageType(blockState.getBlock()) != null;
  }

  public static StorageType getStorageType(Block block) {
    if (block instanceof TrappedChestBlock) {
      return StorageType.TRAPPED;
    } else if (block instanceof ChestBlock) {
      return StorageType.CHEST;
    } else if (block instanceof EnderChestBlock) {
      return StorageType.ENDER;
    } else if (block instanceof ShulkerBoxBlock) {
      return StorageType.SHULKER;
    } else if (block instanceof BarrelBlock) {
      return StorageType.BARREL;
    } else if (block instanceof SpawnerBlock) {
      return StorageType.SPAWNER;
    } else if (block instanceof HopperBlock) {
      return StorageType.HOPPER;
    } else if (block instanceof AbstractFurnaceBlock) {
      return StorageType.FURNACE;
    } else if (block instanceof BrewingStandBlock) {
      return StorageType.FURNACE;
    } else {
      return block instanceof DispenserBlock ? StorageType.HOPPER : null;
    }
  }
}

package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.HoleESPModule;

public final class HoleScanner {
  public static final int count = 8;
  public static final int count2 = 32;
  public static final int count3 = 512;
  public static final double value = 0.002;
  public static final double value2 = 0.45;
  public static volatile List list = List.of();
  public static int count4;

  public static void update() {
    list = List.of();
    count4 = 0;
  }

  public static int computeInt() {
    return list.size();
  }

  public static void apply(HoleESPModule holeESPModule) {
    if (count4++ % 8 == 0) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      ClientPlayerEntity player = instance.player;
      if (world != null && player != null) {
        int value3 = holeESPModule.rangeSetting.intValue();
        double doubleValue = (double) value3 * value3;
        int value4 = holeESPModule.minimumDepthSetting.intValue();
        boolean flag = !holeESPModule.shapeSetting.isSelected("1x3");
        boolean flag2 = !holeESPModule.shapeSetting.isSelected("1x1");
        BlockPos blockPos = player.getBlockPos();
        Mutable mutable = new Mutable();
        HashMap hashMap = new HashMap();

        for (BlockPosData2 blockPosData2 :
            (java.lang.Iterable<BlockPosData2>) (java.lang.Iterable<?>) (list)) {
          hashMap.put(blockPosData2.blockPosData(), blockPosData2.timestamp());
        }

        long longValue = System.nanoTime();
        ArrayList arrayList = new ArrayList();
        int intValue = Math.max(world.getBottomY() + 1, blockPos.getY() - 32);
        int intValue2 = Math.min(world.getTopYInclusive() - 2, blockPos.getY() + 32);

        label167:
        for (int index = -value3 - 2; index <= value3 + 1; index++) {
          for (int index2 = -value3 - 2; index2 <= value3 + 1; index2++) {
            int intValue3 = blockPos.getX() + index;
            int intValue4 = blockPos.getZ() + index2;
            boolean flag3 = flag && test(player, intValue3 + 0.5, intValue4 + 0.5, doubleValue);
            boolean flag4 = flag2 && test(player, intValue3 + 1.5, intValue4 + 0.5, doubleValue);
            boolean flag5 = flag2 && test(player, intValue3 + 0.5, intValue4 + 1.5, doubleValue);
            if (flag3 || flag4 || flag5) {
              for (int index3 = intValue2; index3 >= intValue; index3--) {
                if (test(world, mutable, intValue3, index3, intValue4)
                    && test2(world, mutable, intValue3 - 1, index3, intValue4)
                    && test2(world, mutable, intValue3, index3, intValue4 - 1)) {
                  if (flag3) {
                    int intValue5 =
                        computeInt(world, mutable, intValue3, index3, intValue4, 1, 1, value4);
                    if (intValue5 >= value4) {
                      apply(
                          arrayList, hashMap, longValue, intValue3, index3, intValue4, 1, 1,
                          intValue5);
                      if (arrayList.size() >= 512) {
                        break label167;
                      }
                    }
                  }

                  if (flag2) {
                    if (flag4) {
                      int intValue6 =
                          computeInt(world, mutable, intValue3, index3, intValue4, 3, 1, value4);
                      if (intValue6 >= value4) {
                        apply(
                            arrayList, hashMap, longValue, intValue3, index3, intValue4, 3, 1,
                            intValue6);
                        if (arrayList.size() >= 512) {
                          break label167;
                        }
                      }
                    }

                    if (flag5) {
                      int intValue7 =
                          computeInt(world, mutable, intValue3, index3, intValue4, 1, 3, value4);
                      if (intValue7 >= value4) {
                        apply(
                            arrayList, hashMap, longValue, intValue3, index3, intValue4, 1, 3,
                            intValue7);
                        if (arrayList.size() >= 512) {
                          break label167;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

        if (arrayList.size() < 512 && !list.isEmpty()) {
          HashSet hashSet = new HashSet();

          for (BlockPosData2 blockPosData22 :
              (java.lang.Iterable<BlockPosData2>) (java.lang.Iterable<?>) (arrayList)) {
            hashSet.add(blockPosData22.blockPosData());
          }

          for (BlockPosData2 blockPosData23 :
              (java.lang.Iterable<BlockPosData2>) (java.lang.Iterable<?>) (list)) {
            if (arrayList.size() < 512
                && !hashSet.contains(blockPosData23.blockPosData())
                && (blockPosData23.computeInt2() < intValue
                    || blockPosData23.computeInt2() > intValue2)) {
              boolean flag6 =
                  blockPosData23.computeInt4() == 1 && blockPosData23.computeInt5() == 1
                      ? flag
                      : flag2;
              if (flag6
                  && test(
                      player,
                      blockPosData23.computeInt() + blockPosData23.computeInt4() * 0.5,
                      blockPosData23.computeInt3() + blockPosData23.computeInt5() * 0.5,
                      doubleValue)) {
                int count5 =
                    computeInt(
                        world,
                        mutable,
                        blockPosData23.computeInt(),
                        blockPosData23.computeInt2(),
                        blockPosData23.computeInt3(),
                        blockPosData23.computeInt4(),
                        blockPosData23.computeInt5(),
                        value4);
                if (count5 >= value4) {
                  arrayList.add(
                      new BlockPosData2(
                          blockPosData23.blockPosData(), count5, blockPosData23.timestamp()));
                  hashSet.add(blockPosData23.blockPosData());
                }
              }
            }
          }
        }

        list = List.copyOf(arrayList);
      }
    }
  }

  public static void apply(
      List list2,
      Map map,
      long longValue,
      int count5,
      int count22,
      int count32,
      int count42,
      int count52,
      int count6) {
    BlockPosData blockPosData = new BlockPosData(count5, count22, count32, count42, count52);
    list2.add(
        new BlockPosData2(
            blockPosData, count6, (java.lang.Long) (map.getOrDefault(blockPosData, longValue))));
  }

  public static boolean test(
      ClientPlayerEntity clientPlayerEntity,
      double doubleValue,
      double doubleValue2,
      double doubleValue3) {
    double doubleValue4 = doubleValue - clientPlayerEntity.getX();
    double doubleValue5 = doubleValue2 - clientPlayerEntity.getZ();
    return doubleValue4 * doubleValue4 + doubleValue5 * doubleValue5 <= doubleValue3;
  }

  public static void apply(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, HoleESPModule holeESPModule) {
    List list2 = list;
    if (!list2.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      ClientPlayerEntity player = instance.player;
      if (world != null && player != null) {
        int intValue = holeESPModule.colorSetting.getInteger() & 16777215;
        int intValue2 = Math.clamp(holeESPModule.highlightAlphaSetting.intValue(), 0, 255);
        boolean flag = holeESPModule.modeSetting.isSelected("Full");
        boolean flag2 = (Boolean) holeESPModule.animationsSetting.getValue();
        double doubleValue = (Double) holeESPModule.rangeSetting.getValue();
        double doubleValue2 = doubleValue * doubleValue;
        float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
        Box boundingBox = player.getBoundingBox();
        boundingBox =
            boundingBox.union(
                boundingBox.offset(
                    player.getLerpedPos(tickProgress).subtract(player.getEntityPos())));
        BlockPos blockPos =
            instance.interactionManager != null
                    && instance.currentScreen == null
                    && !player.isSpectator()
                    && instance.options.attackKey.isPressed()
                    && instance.crosshairTarget instanceof BlockHitResult blockHitResult
                    && blockHitResult.getType() == Type.BLOCK
                ? blockHitResult.getBlockPos()
                : null;
        long longValue = System.nanoTime();
        double doubleValue3 = longValue / 1.0E9;

        for (BlockPosData2 blockPosData2 :
            (java.lang.Iterable<BlockPosData2>) (java.lang.Iterable<?>) (list2)) {
          if (!test(blockPosData2, boundingBox, blockPos)) {
            double doubleValue4 = blockPosData2.computeInt() + blockPosData2.computeInt4() * 0.5;
            double doubleValue5 = blockPosData2.computeInt3() + blockPosData2.computeInt5() * 0.5;
            double doubleValue6 = doubleValue4 - player.getX();
            double doubleValue7 = doubleValue5 - player.getZ();
            if (!(doubleValue6 * doubleValue6 + doubleValue7 * doubleValue7 > doubleValue2)
                && test(world, blockPosData2)) {
              double doubleValue8 =
                  flag2
                      ? computeDouble(
                          Math.clamp(
                              (longValue - blockPosData2.timestamp()) / 1.0E9 / 0.45, 0.0, 1.0))
                      : 1.0;
              double doubleValue9 = computeDouble(blockPosData2.blockPosData());
              double doubleValue10 =
                  flag2
                      ? 0.9 + 0.1 * Math.sin(doubleValue3 * 2.15 + doubleValue9 * Math.PI * 2.0)
                      : 1.0;
              int intValue3 = (int) Math.round(intValue2 * doubleValue8 * doubleValue10);
              int intValue4 = (int) Math.round(intValue3 * 0.08);
              if (intValue3 > 0) {
                double doubleValue11 = blockPosData2.computeInt() - 0.002;
                double doubleValue12 = blockPosData2.computeInt3() - 0.002;
                double doubleValue13 =
                    blockPosData2.computeInt() + blockPosData2.computeInt4() + 0.002;
                double doubleValue14 =
                    blockPosData2.computeInt3() + blockPosData2.computeInt5() + 0.002;
                double doubleValue15 = blockPosData2.F_() - 0.002;
                double doubleValue16 = blockPosData2.computeInt2() + 1.0 + 0.002;
                VertexConsumerSupport4.render(
                    immediate,
                    matrixStack,
                    vec3d,
                    doubleValue11,
                    doubleValue15,
                    doubleValue12,
                    doubleValue13,
                    doubleValue16,
                    doubleValue14,
                    intValue,
                    intValue3,
                    intValue4,
                    flag,
                    1.25F);
                if (flag2) {
                  double doubleValue17 = computeDouble(doubleValue3 * 0.34 + doubleValue9, 1.0);
                  double doubleValue18 =
                      doubleValue16 - (doubleValue16 - doubleValue15) * doubleValue17;
                  int intValue5 =
                      (int)
                          Math.round(
                              Math.min(
                                  255.0, intValue2 * doubleValue8 * (0.22 + 0.48 * doubleValue17)));
                  VertexConsumerSupport4.render(
                      immediate,
                      matrixStack,
                      vec3d,
                      doubleValue11,
                      doubleValue18,
                      doubleValue12,
                      doubleValue13,
                      doubleValue14,
                      intValue5 << 24 | intValue,
                      1.7F);
                }
              }
            }
          }
        }

        VertexConsumerSupport4.apply(immediate);
      }
    }
  }

  public static boolean test(BlockPosData2 blockPosData2, Box box, BlockPos blockPos) {
    return box.maxX > blockPosData2.computeInt() - 0.002
            && box.minX < blockPosData2.computeInt() + blockPosData2.computeInt4() + 0.002
            && box.maxZ > blockPosData2.computeInt3() - 0.002
            && box.minZ < blockPosData2.computeInt3() + blockPosData2.computeInt5() + 0.002
            && box.minY <= blockPosData2.computeInt2() + 1.0 + 0.002
        ? true
        : blockPos != null
            && blockPos.getX() >= blockPosData2.computeInt()
            && blockPos.getX() < blockPosData2.computeInt() + blockPosData2.computeInt4()
            && blockPos.getZ() >= blockPosData2.computeInt3()
            && blockPos.getZ() < blockPosData2.computeInt3() + blockPosData2.computeInt5()
            && blockPos.getY() >= blockPosData2.F_() - 1
            && blockPos.getY() <= blockPosData2.computeInt2();
  }

  public static int computeInt(
      ClientWorld clientWorld,
      Mutable mutable,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5,
      int intValue6) {
    if (!test(clientWorld, intValue, intValue3, intValue4, intValue5)) {
      return -1;
    }

    if (!test(clientWorld, mutable, intValue, intValue2 + 1, intValue3, intValue4, intValue5)) {
      return -1;
    }

    int intValue7 = computeInt(intValue4, intValue5);
    if (computeInt(clientWorld, mutable, intValue, intValue2, intValue3, intValue4, intValue5)
        != intValue7) {
      return -1;
    }

    if (computeInt(clientWorld, mutable, intValue, intValue2 + 1, intValue3, intValue4, intValue5)
        == intValue7) {
      return -1;
    }

    int intValue8 = intValue7 / 2;
    int index = 0;
    int intValue9 = 0;
    int index2 = 0;

    while (intValue2 - index >= clientWorld.getBottomY()) {
      int intValue10 = intValue2 - index;
      if (!test(clientWorld, mutable, intValue, intValue10, intValue3, intValue4, intValue5)) {
        break;
      }

      index++;
      if (computeInt(clientWorld, mutable, intValue, intValue10, intValue3, intValue4, intValue5)
          >= intValue8) {
        intValue9 = index;
        index2 = 0;
      } else if (++index2 >= 2) {
        break;
      }
    }

    return intValue9 >= intValue6 ? intValue9 : -1;
  }

  public static boolean test(
      ClientWorld clientWorld,
      Mutable mutable,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5) {
    for (int index = 0; index < intValue4; index++) {
      for (int index2 = 0; index2 < intValue5; index2++) {
        if (!test(clientWorld, mutable, intValue + index, intValue2, intValue3 + index2)) {
          return false;
        }
      }
    }

    return true;
  }

  public static int computeInt(int intValue, int intValue2) {
    return intValue * 2 + intValue2 * 2;
  }

  public static int computeInt(
      ClientWorld clientWorld,
      Mutable mutable,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5) {
    int index = 0;

    for (int index2 = 0; index2 < intValue4; index2++) {
      if (test2(clientWorld, mutable, intValue + index2, intValue2, intValue3 - 1)) {
        index++;
      }

      if (test2(clientWorld, mutable, intValue + index2, intValue2, intValue3 + intValue5)) {
        index++;
      }
    }

    for (int index3 = 0; index3 < intValue5; index3++) {
      if (test2(clientWorld, mutable, intValue - 1, intValue2, intValue3 + index3)) {
        index++;
      }

      if (test2(clientWorld, mutable, intValue + intValue4, intValue2, intValue3 + index3)) {
        index++;
      }
    }

    return index;
  }

  public static boolean test(
      ClientWorld clientWorld, Mutable mutable, int intValue, int intValue2, int intValue3) {
    mutable.set(intValue, intValue2, intValue3);
    BlockState blockState = clientWorld.getBlockState(mutable);
    return blockState.getCollisionShape(clientWorld, mutable).isEmpty();
  }

  public static boolean test2(
      ClientWorld clientWorld, Mutable mutable, int intValue, int intValue2, int intValue3) {
    mutable.set(intValue, intValue2, intValue3);
    return !clientWorld.getBlockState(mutable).getCollisionShape(clientWorld, mutable).isEmpty();
  }

  public static boolean test(
      ClientWorld clientWorld, int intValue, int intValue2, int intValue3, int intValue4) {
    int intValue5 = intValue - 1 >> 4;
    int intValue6 = intValue + intValue3 >> 4;
    int intValue7 = intValue2 - 1 >> 4;
    int intValue8 = intValue2 + intValue4 >> 4;

    for (int index = intValue5; index <= intValue6; index++) {
      for (int index2 = intValue7; index2 <= intValue8; index2++) {
        if (!clientWorld.getChunkManager().isChunkLoaded(index, index2)) {
          return false;
        }
      }
    }

    return true;
  }

  public static boolean test(ClientWorld clientWorld, BlockPosData2 blockPosData2) {
    return test(
        clientWorld,
        blockPosData2.computeInt(),
        blockPosData2.computeInt3(),
        blockPosData2.computeInt4(),
        blockPosData2.computeInt5());
  }

  public static double computeDouble(BlockPosData blockPosData) {
    long longValue =
        BlockPos.asLong(blockPosData.count(), blockPosData.count2(), blockPosData.count3());
    longValue ^= (long) blockPosData.count4() << 19;
    longValue ^= (long) blockPosData.count5() << 27;
    return (longValue & 65535L) / 65536.0;
  }

  public static double computeDouble(double doubleValue) {
    return doubleValue * doubleValue * (3.0 - 2.0 * doubleValue);
  }

  public static double computeDouble(double doubleValue, double doubleValue2) {
    double doubleValue3 = doubleValue % doubleValue2;
    return doubleValue3 < 0.0 ? doubleValue3 + doubleValue2 : doubleValue3;
  }
}

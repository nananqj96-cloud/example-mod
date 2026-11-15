package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public final class ChunkClusterCalculator {
  public static BlockPosData7 calculate(
      List list, BlockPosData6 blockPosData6, double doubleValue, int intValue, int count3) {
    LinkedHashMap linkedHashMap = new LinkedHashMap();

    for (BlockPosData5 blockPosData5 :
        (java.lang.Iterable<BlockPosData5>) (java.lang.Iterable<?>) (list)) {
      ((java.util.Map) (linkedHashMap))
          .merge(
              blockPosData5.timestamp(),
              blockPosData5,
              (value, value2) -> {
                return new BlockPosData5(
                    ((bunnythekenzo.client.BlockPosData5) (value)).timestamp(),
                    Math.max(
                        ((bunnythekenzo.client.BlockPosData5) (value)).count(),
                        ((bunnythekenzo.client.BlockPosData5) (value2)).count()),
                    Math.max(
                        ((bunnythekenzo.client.BlockPosData5) (value)).count2(),
                        ((bunnythekenzo.client.BlockPosData5) (value2)).count2()),
                    ((bunnythekenzo.client.BlockPosData5) (value)).enabled()
                        && ((bunnythekenzo.client.BlockPosData5) (value2)).enabled());
              });
    }

    ArrayList arrayList = new ArrayList(linkedHashMap.values());
    int[] intValues = new int[arrayList.size()];
    HashMap<Long, List<Integer>> hashMap = new HashMap();

    for (int index = 0; index < arrayList.size(); index++) {
      update();
      intValues[index] = index;
      BlockPos blockPos = BlockPos.fromLong(((BlockPosData5) arrayList.get(index)).timestamp());
      int intValue2 = Math.floorDiv(blockPos.getX(), 24);
      int intValue3 = Math.floorDiv(blockPos.getY(), 24);
      int intValue4 = Math.floorDiv(blockPos.getZ(), 24);

      for (int index2 = -1; index2 <= 1; index2++) {
        for (int index3 = -1; index3 <= 1; index3++) {
          for (int index4 = -1; index4 <= 1; index4++) {
            List list2 =
                (List)
                    hashMap.get(
                        BlockPos.asLong(
                            intValue2 + index2, intValue3 + index3, intValue4 + index4));
            if (list2 != null) {
              for (int intValue5 : (java.lang.Iterable<Integer>) (java.lang.Iterable<?>) (list2)) {
                BlockPos blockPos2 =
                    BlockPos.fromLong(((BlockPosData5) arrayList.get(intValue5)).timestamp());
                if (blockPos.getSquaredDistance(blockPos2) <= 576.0) {
                  intValues[computeInt(intValues, index)] = computeInt(intValues, intValue5);
                }
              }
            }
          }
        }
      }

      hashMap
          .computeIfAbsent(
              BlockPos.asLong(intValue2, intValue3, intValue4),
              longValue -> {
                return new ArrayList<>();
              })
          .add(index);
    }

    LinkedHashMap<Integer, List<BlockPosData5>> linkedHashMap2 = new LinkedHashMap();

    for (int index5 = 0; index5 < arrayList.size(); index5++) {
      linkedHashMap2
          .computeIfAbsent(
              computeInt(intValues, index5),
              intValue6 -> {
                return new ArrayList<>();
              })
          .add((BlockPosData5) arrayList.get(index5));
    }

    ArrayList arrayList2 = new ArrayList();
    int count = 0;
    int count2 = 0;

    for (List list3 :
        (java.lang.Iterable<List>) (java.lang.Iterable<?>) (linkedHashMap2.values())) {
      update();
      double doubleValue2 = 0.0;
      double doubleValue3 = 0.0;
      double doubleValue4 = 0.0;
      double doubleValue5 = 0.0;
      double doubleValue6 = 0.0;
      int index6 = 0;
      int index7 = 0;
      int count32 = 0;
      HashSet hashSet = new HashSet();

      for (BlockPosData5 blockPosData52 :
          (java.lang.Iterable<BlockPosData5>) (java.lang.Iterable<?>) (list3)) {
        BlockPos blockPos3 = BlockPos.fromLong(blockPosData52.timestamp());
        double doubleValue7 =
            blockPosData52.enabled() ? 0.25 : Math.max(0.25, blockPosData52.count() / 4.0);
        doubleValue2 += (blockPos3.getX() + 0.5) * doubleValue7;
        doubleValue3 += (blockPos3.getY() + 0.5) * doubleValue7;
        doubleValue4 += (blockPos3.getZ() + 0.5) * doubleValue7;
        doubleValue5 += doubleValue7;
        hashSet.add(ChunkPos.toLong(blockPos3.getX() >> 4, blockPos3.getZ() >> 4));
        if (blockPosData52.enabled()) {
          index7++;
        } else {
          index6++;
          doubleValue6 += blockPosData52.count() * 0.5;
        }

        count32 += blockPosData52.count2();
      }

      double value4 =
          Math.min(24.0, doubleValue6) + Math.min(32, count32 * 8) + Math.min(18.0, index7 * 0.75);
      count += count32;
      count2 += index7;
      arrayList2.add(
          new BlockPosData4(
              doubleValue2 / doubleValue5,
              doubleValue3 / doubleValue5,
              doubleValue4 / doubleValue5,
              value4,
              index6,
              index7,
              count32,
              Set.copyOf(hashSet)));
    }

    arrayList2.sort(
        Comparator.comparingDouble(BlockPosData4::value4)
            .reversed()
            .thenComparingDouble(BlockPosData4::value)
            .thenComparingDouble(BlockPosData4::value3));
    ArrayList arrayList3 = new ArrayList();

    for (int index8 = blockPosData6._T();
        index8 < blockPosData6._T() + blockPosData6._u();
        index8++) {
      for (int index9 = blockPosData6._i();
          index9 < blockPosData6._i() + blockPosData6._X();
          index9++) {
        update();
        double value3 = 0.0;
        int index10 = 0;

        for (BlockPosData4 blockPosData4 :
            (java.lang.Iterable<BlockPosData4>) (java.lang.Iterable<?>) (arrayList2)) {
          if (!(blockPosData4.value4() < doubleValue)) {
            boolean flag = false;

            for (long longValue2 :
                (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (blockPosData4.set())) {
              double doubleValue8 =
                  index9 * 16.0 + 8.0 - (ChunkPos.getPackedX(longValue2) * 16.0 + 8.0);
              double doubleValue9 =
                  index8 * 16.0 + 8.0 - (ChunkPos.getPackedZ(longValue2) * 16.0 + 8.0);
              if (doubleValue8 * doubleValue8 + doubleValue9 * doubleValue9
                  <= (double) intValue * intValue) {
                flag = true;
                break;
              }
            }

            if (flag) {
              value3 += blockPosData4.value4();
              index10++;
            }
          }
        }

        if (index10 > 0) {
          arrayList3.add(new BlockPosData3(ChunkPos.toLong(index9, index8), value3, index10));
        }
      }
    }

    arrayList3.sort(
        Comparator.comparingDouble(BlockPosData3::value)
            .reversed()
            .thenComparingLong(BlockPosData3::timestamp));
    return new BlockPosData7(
        List.copyOf(arrayList2), List.copyOf(arrayList3), count, count2, count3);
  }

  public static BlockPosData7 rankBaseAreas(
      BlockPosData7 blockPosData7, List list, BlockPosData6 blockPosData6, int intValue) {
    ArrayList arrayList = new ArrayList();

    for (WorldSignalTypeData2 worldSignalTypeData2 :
        (java.lang.Iterable<WorldSignalTypeData2>)
            (java.lang.Iterable<?>)
                (list.stream()
                    .sorted(Comparator.comparingDouble(WorldSignalTypeData2::value3).reversed())
                    .toList())) {
      if (!(worldSignalTypeData2.value3() < 12.0)
          && Double.isFinite(worldSignalTypeData2.value())
          && Double.isFinite(worldSignalTypeData2.value2())
          && !arrayList.stream()
              .anyMatch(
                  value -> {
                    return !Collections.disjoint(
                            ((bunnythekenzo.client.WorldSignalTypeData2) (value))._x(),
                            worldSignalTypeData2._x())
                        || Math.pow(
                                    ((bunnythekenzo.client.WorldSignalTypeData2) (value)).value()
                                        - worldSignalTypeData2.value(),
                                    2.0)
                                + Math.pow(
                                    ((bunnythekenzo.client.WorldSignalTypeData2) (value)).value2()
                                        - worldSignalTypeData2.value2(),
                                    2.0)
                            < 2304.0;
                  })) {
        arrayList.add(worldSignalTypeData2);
      }
    }

    Set set = getSet(list);
    list = arrayList;
    if (list.size() < 2) {
      return new BlockPosData7(
          blockPosData7.list(),
          List.of(),
          blockPosData7.count(),
          blockPosData7.count2(),
          blockPosData7.count3());
    }

    ArrayList arrayList2 = new ArrayList();
    double[] doubleValues = new double[list.size()];

    for (int index = 0; index < list.size(); index++) {
      update();
      WorldSignalTypeData2 worldSignalTypeData22 = (WorldSignalTypeData2) list.get(index);
      int intValue2 = 0;

      for (BlockPosData4 blockPosData4 :
          (java.lang.Iterable<BlockPosData4>) (java.lang.Iterable<?>) (blockPosData7.list())) {
        if (!Collections.disjoint(blockPosData4.set(), worldSignalTypeData22._x())) {
          intValue2 = Math.max(intValue2, blockPosData4.count3());
        }
      }

      doubleValues[index] =
          Math.min(96.0, worldSignalTypeData22.value3()) * (1.0 + Math.min(4, intValue2) * 0.25);
    }

    for (int index2 = blockPosData6._T();
        index2 < blockPosData6._T() + blockPosData6._u();
        index2++) {
      for (int index3 = blockPosData6._i();
          index3 < blockPosData6._i() + blockPosData6._X();
          index3++) {
        update();
        if (!set.contains(ChunkPos.toLong(index3, index2))) {
          double value2 = 0.0;
          int index4 = 0;

          for (int index5 = 0; index5 < list.size(); index5++) {
            WorldSignalTypeData2 worldSignalTypeData23 = (WorldSignalTypeData2) list.get(index5);
            double doubleValue = index3 * 16.0 + 8.0 - worldSignalTypeData23.value();
            double doubleValue2 = index2 * 16.0 + 8.0 - worldSignalTypeData23.value2();
            double doubleValue3 = doubleValue * doubleValue + doubleValue2 * doubleValue2;
            if (!(doubleValue3 > (double) intValue * intValue)) {
              value2 += doubleValues[index5] / (1.0 + doubleValue3 / 16384.0);
              index4++;
            }
          }

          if (index4 >= 2) {
            arrayList2.add(new BlockPosData3(ChunkPos.toLong(index3, index2), value2, index4));
          }
        }
      }
    }

    arrayList2.sort(
        Comparator.comparingInt(BlockPosData3::count)
            .reversed()
            .thenComparing(Comparator.comparingDouble(BlockPosData3::value).reversed())
            .thenComparingLong(BlockPosData3::timestamp));
    return new BlockPosData7(
        blockPosData7.list(),
        List.copyOf(arrayList2),
        blockPosData7.count(),
        blockPosData7.count2(),
        blockPosData7.count3());
  }

  public static Set getSet(List list) {
    HashSet hashSet = new HashSet();

    for (WorldSignalTypeData2 worldSignalTypeData2 :
        (java.lang.Iterable<WorldSignalTypeData2>) (java.lang.Iterable<?>) (list)) {
      for (long longValue :
          (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (worldSignalTypeData2._x())) {
        for (int index = -1; index <= 1; index++) {
          for (int index2 = -1; index2 <= 1; index2++) {
            hashSet.add(
                ChunkPos.toLong(
                    ChunkPos.getPackedX(longValue) + index,
                    ChunkPos.getPackedZ(longValue) + index2));
          }
        }
      }
    }

    return hashSet;
  }

  public static int computeInt(int[] intValues, int index) {
    while (intValues[index] != index) {
      intValues[index] = intValues[intValues[index]];
      index = intValues[index];
    }

    return index;
  }

  public static void update() {
    if (Thread.currentThread().isInterrupted()) {
      throw new CancellationException();
    }
  }
}

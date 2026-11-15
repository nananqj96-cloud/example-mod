package bunnythekenzo.client;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.module.SusChunkFinderModule;

public class SuspiciousChunkScanner {
  public static final int count = 5;
  public static final int count2 = 24;
  public static final boolean enabled = Boolean.getBoolean("bunnythekenzo.sus.debug");
  public final SusChunkFinderModule susChunkFinderModule;
  public final Map map = new ConcurrentHashMap();
  public final Deque deque = new ArrayDeque();
  public final Set set = ConcurrentHashMap.newKeySet();
  public final Set set2 = new HashSet();
  public volatile List list = List.of();
  public List list2 = List.of();
  public long timestamp;
  public volatile List list3 = List.of();
  public ChunkPos chunkPos;
  public int count3;

  public List getList2() {
    return this.list2;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public SuspiciousChunkScanner(SusChunkFinderModule susChunkFinderModule2) {
    this.susChunkFinderModule = susChunkFinderModule2;
    LightSectionCache.getInstance().apply(this.set::add);
  }

  public List getList() {
    return this.list;
  }

  public List getList3() {
    return this.list3;
  }

  public int computeInt() {
    return this.susChunkFinderModule.sensitivitySetting.intValue() * 5;
  }

  public void update() {
    this.map.clear();
    this.deque.clear();
    this.set.clear();
    this.set2.clear();
    this.list = List.of();
    this.list2 = List.of();
    this.timestamp++;
    this.list3 = List.of();
    this.chunkPos = null;
  }

  public void update2() {
    this.apply(true);
  }

  public void apply(boolean flag) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      try {
        this.refillQueueIfNeeded(
            instance.player.getChunkPos(),
            Math.min(instance.options.getViewDistance().getValue() + 1, 16));
        int value = this.susChunkFinderModule.scanSpeedSetting.intValue();
        long longValue = System.nanoTime() + 2000000L;
        if ((this.count3 & 1) == 0) {
          int int2 = this.computeInt2(instance, value, longValue);
          this.computeInt(instance, value - int2, longValue);
        } else {
          int value2 = this.computeInt(instance, value, longValue);
          this.computeInt2(instance, value - value2, longValue);
        }

        if (++this.count3 % 10 == 0) {
          this.rebuild(instance, flag);
        }
      } catch (Exception failure) {
        ClientInitializer.logger.warn("SusChunkFinder scan error: {}", failure.toString());
      }
    }
  }

  public int computeInt(MinecraftClient minecraftClient, int intValue, long longValue) {
    if (this.set.isEmpty()) {
      return 0;
    }

    int index = 0;
    Iterator iterator = this.set.iterator();

    while (iterator.hasNext() && index < intValue && System.nanoTime() < longValue) {
      long longValue2 = (Long) iterator.next();
      iterator.remove();
      WorldChunk worldChunk =
          minecraftClient
              .world
              .getChunkManager()
              .getWorldChunk(
                  ChunkPos.getPackedX(longValue2), ChunkPos.getPackedZ(longValue2), false);
      if (worldChunk != null && this.map.containsKey(longValue2)) {
        this.map.put(longValue2, this.getWorldSignalTypeSupport(minecraftClient, worldChunk));
        index++;
      }
    }

    return index;
  }

  public int computeInt2(MinecraftClient minecraftClient, int intValue, long longValue) {
    int index = 0;
    int index2 = 0;

    while (index < intValue
        && index2 < 128
        && !this.deque.isEmpty()
        && System.nanoTime() < longValue) {
      index2++;
      long longValue2 = (Long) this.deque.pollFirst();
      WorldChunk worldChunk =
          minecraftClient
              .world
              .getChunkManager()
              .getWorldChunk(
                  ChunkPos.getPackedX(longValue2), ChunkPos.getPackedZ(longValue2), false);
      if (worldChunk != null) {
        this.map.put(longValue2, this.getWorldSignalTypeSupport(minecraftClient, worldChunk));
        index++;
      }
    }

    return index;
  }

  public void refillQueueIfNeeded(ChunkPos chunkPos2, int intValue) {
    if (this.deque.isEmpty()
        || this.chunkPos == null
        || Math.max(
                Math.abs(chunkPos2.x - this.chunkPos.x), Math.abs(chunkPos2.z - this.chunkPos.z))
            >= 3) {
      this.chunkPos = chunkPos2;
      this.deque.removeIf(
          value -> {
            return Math.abs(ChunkPos.getPackedX((java.lang.Long) (value)) - chunkPos2.x) > intValue
                || Math.abs(ChunkPos.getPackedZ((java.lang.Long) (value)) - chunkPos2.z) > intValue;
          });
      HashSet hashSet = new HashSet(this.deque);
      ArrayList arrayList = new ArrayList();

      for (int index = -intValue; index <= intValue; index++) {
        for (int index2 = -intValue; index2 <= intValue; index2++) {
          long longValue = ChunkPos.toLong(chunkPos2.x + index, chunkPos2.z + index2);
          if (!hashSet.contains(longValue)) {
            arrayList.add(longValue);
          }
        }
      }

      arrayList.sort(
          Comparator.comparingDouble(
              value2 -> {
                return Math.hypot(
                    ChunkPos.getPackedX((java.lang.Long) (value2)) - chunkPos2.x,
                    ChunkPos.getPackedZ((java.lang.Long) (value2)) - chunkPos2.z);
              }));
      this.deque.addAll(arrayList);
    }
  }

  public static boolean test(BlockState blockState) {
    Block block = blockState.getBlock();
    return block == Blocks.KELP
        || block == Blocks.KELP_PLANT
        || block == Blocks.BAMBOO
        || block == Blocks.SWEET_BERRY_BUSH
        || block == Blocks.VINE
        || block == Blocks.POINTED_DRIPSTONE;
  }

  public WorldSignalTypeSupport getWorldSignalTypeSupport(
      MinecraftClient minecraftClient, WorldChunk worldChunk) {
    WorldSignalTypeSupport worldSignalTypeSupport =
        new WorldSignalTypeSupport(worldChunk.getPos().toLong());
    if ((Boolean) this.susChunkFinderModule.amethystSetting.getValue()) {
      this.apply(minecraftClient, worldChunk, worldSignalTypeSupport);
    }

    this.apply2(minecraftClient, worldChunk, worldSignalTypeSupport);
    worldSignalTypeSupport.update();
    if (enabled && worldSignalTypeSupport.value > 0.0) {
      ClientInitializer.logger.info(
          "[sus] chunk {} score={} hits={}",
          worldChunk.getPos(),
          worldSignalTypeSupport.value,
          worldSignalTypeSupport.enumMap);
    }

    return worldSignalTypeSupport;
  }

  public void apply(
      MinecraftClient minecraftClient,
      WorldChunk worldChunk,
      WorldSignalTypeSupport worldSignalTypeSupport) {
    for (BlockPos blockPos :
        (java.lang.Iterable<BlockPos>)
            (java.lang.Iterable<?>)
                (ChunkLightCandidateScanner.getList(minecraftClient, worldChunk))) {
      worldSignalTypeSupport.list.add(blockPos);
      if (enabled) {
        ClientInitializer.logger.info("[sus] AMETHYST grown crystal @{}", blockPos);
      }
    }
  }

  public void apply2(
      MinecraftClient minecraftClient,
      WorldChunk worldChunk,
      WorldSignalTypeSupport worldSignalTypeSupport) {
    ChunkSection[] sectionArray = worldChunk.getSectionArray();
    int bottomSectionCoord = worldChunk.getBottomSectionCoord();
    int sectionCoord = ChunkSectionPos.getSectionCoord(-12);
    int sectionCoord2 = ChunkSectionPos.getSectionCoord(80);
    int startX = worldChunk.getPos().getStartX();
    int startZ = worldChunk.getPos().getStartZ();
    Mutable mutable = new Mutable();

    for (int index = 0; index < sectionArray.length; index++) {
      int intValue = bottomSectionCoord + index;
      if (intValue >= sectionCoord && intValue <= sectionCoord2) {
        ChunkSection chunkSection = sectionArray[index];
        if (!chunkSection.isEmpty()
            && chunkSection.getBlockStateContainer().hasAny(SuspiciousChunkScanner::test)) {
          int intValue2 = intValue << 4;

          for (int index2 = 0; index2 < 16; index2++) {
            int intValue3 = intValue2 + index2;
            if (intValue3 >= -12 && intValue3 <= 80) {
              for (int index3 = 0; index3 < 16; index3++) {
                for (int index4 = 0; index4 < 16; index4++) {
                  BlockState blockState = chunkSection.getBlockState(index4, index2, index3);
                  if (test(blockState)) {
                    mutable.set(startX + index4, intValue3, startZ + index3);
                    this.apply(minecraftClient, blockState, mutable, worldSignalTypeSupport);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void apply(
      MinecraftClient minecraftClient,
      BlockState blockState,
      BlockPos blockPos,
      WorldSignalTypeSupport worldSignalTypeSupport) {
    Block block = blockState.getBlock();
    if ((block == Blocks.KELP || block == Blocks.KELP_PLANT)
        && (Boolean) this.susChunkFinderModule.kelpSetting.getValue()) {
      BlockState blockState2 = minecraftClient.world.getBlockState(blockPos.up());
      if (blockState2.isOf(Blocks.KELP) || blockState2.isOf(Blocks.KELP_PLANT)) {
        return;
      }

      int intValue =
          computeInt(
              minecraftClient,
              blockPos,
              Direction.DOWN,
              new Block[] {Blocks.KELP, Blocks.KELP_PLANT});
      boolean flag =
          block == Blocks.KELP
              && blockState.contains(Properties.AGE_25)
              && blockState.get(Properties.AGE_25) == 25;
      if (flag && intValue >= 8 || intValue >= 14) {
        worldSignalTypeSupport.apply(WorldSignalType.KELP, blockPos);
        if (enabled) {
          ClientInitializer.logger.info(
              "[sus] KELP @{} height={} maxAge={}", blockPos, intValue, flag);
        }
      }
    } else if (block == Blocks.BAMBOO
        && (Boolean) this.susChunkFinderModule.bambooSetting.getValue()) {
      if (minecraftClient.world.getBlockState(blockPos.down()).isOf(Blocks.BAMBOO)) {
        return;
      }

      int intValue2 =
          computeInt(minecraftClient, blockPos, Direction.UP, new Block[] {Blocks.BAMBOO});
      if (intValue2 >= 12) {
        worldSignalTypeSupport.apply(WorldSignalType.BAMBOO, blockPos);
        if (enabled) {
          ClientInitializer.logger.info("[sus] BAMBOO @{} height={}", blockPos, intValue2);
        }
      }
    } else if (block == Blocks.SWEET_BERRY_BUSH
        && (Boolean) this.susChunkFinderModule.berriesSetting.getValue()) {
      if (blockState.contains(Properties.AGE_3) && blockState.get(Properties.AGE_3) == 3) {
        worldSignalTypeSupport.apply(WorldSignalType.BERRIES, blockPos);
        if (enabled) {
          ClientInitializer.logger.info("[sus] BERRIES max age @{}", blockPos);
        }
      }
    } else if (block == Blocks.VINE
        && (Boolean) this.susChunkFinderModule.vinesSetting.getValue()) {
      if (minecraftClient.world.getBlockState(blockPos.up()).isOf(Blocks.VINE)) {
        return;
      }

      int intValue3 =
          computeInt(minecraftClient, blockPos, Direction.DOWN, new Block[] {Blocks.VINE});
      if (intValue3 >= 7) {
        worldSignalTypeSupport.apply(WorldSignalType.VINES, blockPos);
        if (enabled) {
          ClientInitializer.logger.info("[sus] VINES @{} hang={}", blockPos, intValue3);
        }
      }
    } else if (block == Blocks.POINTED_DRIPSTONE
        && (Boolean) this.susChunkFinderModule.dripstoneSetting.getValue()) {
      if (minecraftClient.world.getBlockState(blockPos.up()).isOf(Blocks.POINTED_DRIPSTONE)
          || !minecraftClient.world.getBlockState(blockPos.down()).isOf(Blocks.POINTED_DRIPSTONE)) {
        return;
      }

      int intValue4 =
          computeInt(
              minecraftClient, blockPos, Direction.DOWN, new Block[] {Blocks.POINTED_DRIPSTONE});
      if (intValue4 >= 5) {
        worldSignalTypeSupport.apply(WorldSignalType.DRIPSTONE, blockPos);
        if (enabled) {
          ClientInitializer.logger.info("[sus] DRIPSTONE @{} length={}", blockPos, intValue4);
        }
      }
    }
  }

  public static int computeInt(
      MinecraftClient minecraftClient, BlockPos blockPos, Direction direction, Block[] blockArray) {
    int index = 1;
    Mutable mutable = blockPos.mutableCopy();

    label22:
    while (index < 40) {
      mutable.move(direction);
      BlockState blockState = minecraftClient.world.getBlockState(mutable);

      for (Block block : blockArray) {
        if (blockState.isOf(block)) {
          index++;
          continue label22;
        }
      }
      break;
    }

    return index;
  }

  public void rebuild(MinecraftClient minecraftClient, boolean flag) {
    this.map
        .keySet()
        .removeIf(
            value -> {
              return minecraftClient
                      .world
                      .getChunkManager()
                      .getWorldChunk(
                          ChunkPos.getPackedX((java.lang.Long) (value)),
                          ChunkPos.getPackedZ((java.lang.Long) (value)),
                          false)
                  == null;
            });
    int value2 = this.computeInt();
    HashMap hashMap = new HashMap();
    ArrayList arrayList = new ArrayList();

    for (WorldSignalTypeSupport worldSignalTypeSupport :
        (java.lang.Iterable<WorldSignalTypeSupport>) (java.lang.Iterable<?>) (this.map.values())) {
      if (worldSignalTypeSupport.value >= value2) {
        arrayList.add(
            new WorldSignalTypeData2(
                Set.of(worldSignalTypeSupport.timestamp),
                worldSignalTypeSupport.value3 / worldSignalTypeSupport.value2,
                worldSignalTypeSupport.value4 / worldSignalTypeSupport.value2,
                worldSignalTypeSupport.value));
        WorldSignalTypeSupport2 worldSignalTypeSupport2 =
            (bunnythekenzo.client.WorldSignalTypeSupport2)
                (hashMap.computeIfAbsent(
                    worldSignalTypeSupport.timestamp,
                    (referenceArgument9_0) ->
                        new bunnythekenzo.client.WorldSignalTypeSupport2(
                            (java.lang.Long) referenceArgument9_0)));
        worldSignalTypeSupport2.value =
            Math.max(worldSignalTypeSupport2.value, worldSignalTypeSupport.value);
        worldSignalTypeSupport2.value2 =
            worldSignalTypeSupport2.value2 + worldSignalTypeSupport.value2;
        worldSignalTypeSupport2.value3 =
            worldSignalTypeSupport2.value3 + worldSignalTypeSupport.value3;
        worldSignalTypeSupport2.value4 =
            worldSignalTypeSupport2.value4 + worldSignalTypeSupport.value4;
      }
    }

    for (WorldSignalTypeData4 worldSignalTypeData4 :
        (java.lang.Iterable<WorldSignalTypeData4>) (java.lang.Iterable<?>) (this.clusterGeodes())) {
      if (!(worldSignalTypeData4.value() < value2)) {
        arrayList.add(
            new WorldSignalTypeData2(
                worldSignalTypeData4.set(),
                worldSignalTypeData4.value2(),
                worldSignalTypeData4.value3(),
                worldSignalTypeData4.value()));

        for (long longValue :
            (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (worldSignalTypeData4.set())) {
          WorldSignalTypeSupport2 worldSignalTypeSupport22 =
              (bunnythekenzo.client.WorldSignalTypeSupport2)
                  (hashMap.computeIfAbsent(
                      longValue,
                      (referenceArgument10_0) ->
                          new bunnythekenzo.client.WorldSignalTypeSupport2(
                              (java.lang.Long) referenceArgument10_0)));
          worldSignalTypeSupport22.value =
              Math.max(worldSignalTypeSupport22.value, worldSignalTypeData4.value());
        }

        double weight = WorldSignalType.AMETHYST.weight;

        for (BlockPos blockPos :
            (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (worldSignalTypeData4.list())) {
          WorldSignalTypeSupport2 worldSignalTypeSupport23 =
              (WorldSignalTypeSupport2)
                  hashMap.get(ChunkPos.toLong(blockPos.getX() >> 4, blockPos.getZ() >> 4));
          if (worldSignalTypeSupport23 != null) {
            worldSignalTypeSupport23.value2 += weight;
            worldSignalTypeSupport23.value3 =
                worldSignalTypeSupport23.value3 + (blockPos.getX() + 0.5) * weight;
            worldSignalTypeSupport23.value4 =
                worldSignalTypeSupport23.value4 + (blockPos.getZ() + 0.5) * weight;
          }
        }

        if (enabled) {
          ClientInitializer.logger.info(
              "[sus] GEODE {} cells / {} chunks, score {} -> FLAG",
              worldSignalTypeData4.list().size(),
              worldSignalTypeData4.set().size(),
              worldSignalTypeData4.value());
        }
      }
    }

    ArrayList arrayList2 = new ArrayList();

    for (WorldSignalTypeSupport2 worldSignalTypeSupport24 :
        (java.lang.Iterable<WorldSignalTypeSupport2>) (java.lang.Iterable<?>) (hashMap.values())) {
      arrayList2.add(
          new WorldSignalTypeData3(
              worldSignalTypeSupport24.timestamp, worldSignalTypeSupport24.value));
    }

    this.list = List.copyOf(arrayList2);
    this.list3 = this.getList(arrayList2, hashMap);
    this.list2 = List.copyOf(arrayList);
    this.timestamp++;
    if (flag) {
      this.apply(minecraftClient);
    }
  }

  public List clusterGeodes() {
    LinkedHashSet linkedHashSet = new LinkedHashSet();

    for (WorldSignalTypeSupport worldSignalTypeSupport :
        (java.lang.Iterable<WorldSignalTypeSupport>) (java.lang.Iterable<?>) (this.map.values())) {
      linkedHashSet.addAll(worldSignalTypeSupport.list);
    }

    ArrayList arrayList = new ArrayList(linkedHashSet);
    int size = arrayList.size();
    if (size == 0) {
      return List.of();
    }

    int[] intValues = new int[size];
    int index = 0;

    while (index < size) {
      intValues[index] = index++;
    }

    for (int index2 = 0; index2 < size; index2++) {
      BlockPos blockPos = (BlockPos) arrayList.get(index2);

      for (int index3 = index2 + 1; index3 < size; index3++) {
        BlockPos blockPos2 = (BlockPos) arrayList.get(index3);
        long longValue = blockPos.getX() - blockPos2.getX();
        long longValue2 = blockPos.getZ() - blockPos2.getZ();
        if (longValue * longValue + longValue2 * longValue2 <= 576L) {
          intValues[computeInt(intValues, index2)] = computeInt(intValues, index3);
        }
      }
    }

    HashMap<Integer, List<BlockPos>> hashMap = new HashMap();

    for (int index4 = 0; index4 < size; index4++) {
      hashMap
          .computeIfAbsent(
              computeInt(intValues, index4),
              intValue -> {
                return new ArrayList<>();
              })
          .add((BlockPos) arrayList.get(index4));
    }

    ArrayList arrayList2 = new ArrayList();

    for (List list4 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (hashMap.values())) {
      HashSet hashSet = new HashSet();
      double doubleValue = 0.0;
      double doubleValue2 = 0.0;

      for (BlockPos blockPos3 : (java.lang.Iterable<BlockPos>) (java.lang.Iterable<?>) (list4)) {
        hashSet.add(ChunkPos.toLong(blockPos3.getX() >> 4, blockPos3.getZ() >> 4));
        doubleValue += blockPos3.getX() + 0.5;
        doubleValue2 += blockPos3.getZ() + 0.5;
      }

      double value =
          WorldSignalType.AMETHYST.weight * Math.min(list4.size(), WorldSignalType.AMETHYST.count);
      arrayList2.add(
          new WorldSignalTypeData4(
              List.copyOf(list4),
              Set.copyOf(hashSet),
              value,
              doubleValue / list4.size(),
              doubleValue2 / list4.size()));
    }

    return arrayList2;
  }

  public static int computeInt(int[] intValues, int index) {
    while (intValues[index] != index) {
      intValues[index] = intValues[intValues[index]];
      index = intValues[index];
    }

    return index;
  }

  public List getList(List list4, Map map2) {
    int intValue = Math.max(1, this.susChunkFinderModule.mergeRadiusSetting.intValue());
    HashMap hashMap = new HashMap();

    for (WorldSignalTypeData3 worldSignalTypeData3 :
        (java.lang.Iterable<WorldSignalTypeData3>) (java.lang.Iterable<?>) (list4)) {
      hashMap.put(worldSignalTypeData3.timestamp(), worldSignalTypeData3);
    }

    ArrayList arrayList = new ArrayList();
    HashSet hashSet = new HashSet();

    for (WorldSignalTypeData3 worldSignalTypeData32 :
        (java.lang.Iterable<WorldSignalTypeData3>) (java.lang.Iterable<?>) (list4)) {
      if (hashSet.add(worldSignalTypeData32.timestamp())) {
        ArrayList arrayList2 = new ArrayList();
        var arrayDeque = new ArrayDeque<>(List.of(worldSignalTypeData32));

        while (!arrayDeque.isEmpty()) {
          WorldSignalTypeData3 worldSignalTypeData33 = (WorldSignalTypeData3) arrayDeque.poll();
          arrayList2.add(worldSignalTypeData33);
          int packedX = ChunkPos.getPackedX(worldSignalTypeData33.timestamp());
          int packedZ = ChunkPos.getPackedZ(worldSignalTypeData33.timestamp());

          for (int index = -intValue; index <= intValue; index++) {
            for (int index2 = -intValue; index2 <= intValue; index2++) {
              if (index != 0 || index2 != 0) {
                WorldSignalTypeData3 worldSignalTypeData34 =
                    (WorldSignalTypeData3)
                        hashMap.get(ChunkPos.toLong(packedX + index, packedZ + index2));
                if (worldSignalTypeData34 != null
                    && hashSet.add(worldSignalTypeData34.timestamp())) {
                  arrayDeque.add(worldSignalTypeData34);
                }
              }
            }
          }
        }

        arrayList.add(this.getWorldSignalTypeData(arrayList2, map2));
      }
    }

    arrayList.sort(Comparator.comparingDouble(WorldSignalTypeData::value3).reversed());
    return arrayList;
  }

  public WorldSignalTypeData getWorldSignalTypeData(List list4, Map map2) {
    HashSet hashSet = new HashSet();
    double value3 = 0.0;
    double value4 = 0.0;
    double doubleValue = 0.0;
    double doubleValue2 = 0.0;
    double doubleValue3 = 0.0;
    double doubleValue4 = 0.0;
    double doubleValue5 = 0.0;

    for (WorldSignalTypeData3 worldSignalTypeData3 :
        (java.lang.Iterable<WorldSignalTypeData3>) (java.lang.Iterable<?>) (list4)) {
      hashSet.add(worldSignalTypeData3.timestamp());
      value3 += worldSignalTypeData3.value();
      value4 = Math.max(value4, worldSignalTypeData3.value());
      WorldSignalTypeSupport2 worldSignalTypeSupport2 =
          (WorldSignalTypeSupport2) map2.get(worldSignalTypeData3.timestamp());
      if (worldSignalTypeSupport2 != null && worldSignalTypeSupport2.value2 > 0.0) {
        doubleValue += worldSignalTypeSupport2.value2;
        doubleValue2 += worldSignalTypeSupport2.value3;
        doubleValue3 += worldSignalTypeSupport2.value4;
      }

      doubleValue4 +=
          (ChunkPos.getPackedX(worldSignalTypeData3.timestamp()) * 16 + 8)
              * worldSignalTypeData3.value();
      doubleValue5 +=
          (ChunkPos.getPackedZ(worldSignalTypeData3.timestamp()) * 16 + 8)
              * worldSignalTypeData3.value();
    }

    double value = doubleValue > 0.0 ? doubleValue2 / doubleValue : doubleValue4 / value3;
    double value2 = doubleValue > 0.0 ? doubleValue3 / doubleValue : doubleValue5 / value3;
    return new WorldSignalTypeData(Set.copyOf(hashSet), value, value2, value3, value4);
  }

  public void apply(MinecraftClient minecraftClient) {
    for (WorldSignalTypeData worldSignalTypeData :
        (java.lang.Iterable<WorldSignalTypeData>) (java.lang.Iterable<?>) (this.list3)) {
      boolean flag = true;

      for (long longValue :
          (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (worldSignalTypeData.set())) {
        if (this.set2.contains(longValue)) {
          flag = false;
          break;
        }
      }

      if (!flag) {
        this.set2.addAll(worldSignalTypeData.set());
      } else {
        this.set2.addAll(worldSignalTypeData.set());
        if (!this.susChunkFinderModule.notificationsSetting.isSelected("Off")) {
          int intValue = (int) Math.round(worldSignalTypeData.value());
          int intValue2 = (int) Math.round(worldSignalTypeData.value2());
          int intValue3 =
              (int)
                  Math.hypot(
                      intValue - minecraftClient.player.getX(),
                      intValue2 - minecraftClient.player.getZ());
          String text = "Sus zone · " + intValue3 + "m · " + intValue + ", " + intValue2;
          if (this.susChunkFinderModule.notificationsSetting.isSelected("Toast")
              && ClientInitializer.getNotificationsHud() != null) {
            ClientInitializer.getNotificationsHud().apply(text);
            if ((Boolean) this.susChunkFinderModule.notificationSoundSetting.getValue()) {
              ClientAudio.apply2(true);
            }
          } else if (this.susChunkFinderModule.notificationsSetting.isSelected("Chat")) {
            minecraftClient.player.sendMessage(Text.literal("§d[bunnythekenzo] §f" + text), false);
          }
        }
      }
    }
  }
}

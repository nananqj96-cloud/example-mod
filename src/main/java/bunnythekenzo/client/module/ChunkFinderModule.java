package bunnythekenzo.client.module;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.setting.NumberSetting;

public final class ChunkFinderModule extends Module {
  public static final int count = 200;
  public static final int count2 = 8;
  public final NumberSetting mergeRadiusSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Merge Radius",
                  "Hives within this many chunks of each other merge to one middle flag",
                  4.0,
                  1.0,
                  8.0,
                  1.0,
                  " ch"));
  public final Set set = ConcurrentHashMap.newKeySet();
  public final Set set2 = ConcurrentHashMap.newKeySet();
  public final Map map = new ConcurrentHashMap();
  public volatile Set set3 = Set.of();
  public int count3;
  public int count4;
  public boolean enabled;
  public int count5;

  public ChunkFinderModule() {
    super(
        "Chunk Finder",
        "Highlights Donut SMP chunks that match a rare world signal worth checking.",
        ModuleCategory.RENDER);
  }

  @Override
  public void onEnable() {
    this.update5();
  }

  @Override
  public void onDisable() {
    this.update5();
  }

  @Override
  public void onTick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      this.count4++;
      int value = instance.options.getViewDistance().getValue();
      ChunkPos chunkPos = instance.player.getChunkPos();
      int intValue = value * 2 + 1;
      int intValue2 = intValue * intValue;
      if (intValue2 > 0) {
        for (int index = 0; index < 8; index++) {
          int intValue3 = this.count3 % intValue2;
          this.count3 = (this.count3 + 1) % intValue2;
          int intValue4 = intValue3 % intValue - value;
          int intValue5 = intValue3 / intValue - value;
          int intValue6 = chunkPos.x + intValue4;
          int intValue7 = chunkPos.z + intValue5;
          WorldChunk worldChunk =
              instance.world.getChunkManager().getWorldChunk(intValue6, intValue7, false);
          if (worldChunk != null && !worldChunk.isEmpty()) {
            long longValue = ChunkPos.toLong(intValue6, intValue7);
            this.map.putIfAbsent(longValue, this.count4);
            boolean flag = this.count4 - (Integer) this.map.get(longValue) >= 200;
            if (flag && test(worldChunk)) {
              if (this.set.add(longValue)) {
                this.enabled = true;
              }

              if (this.set2.add(longValue)) {
                this.apply(new ChunkPos(intValue6, intValue7));
              }
            } else if (this.set.remove(longValue)) {
              this.enabled = true;
            }
          }
        }

        if (this.set.removeIf(
            value2 -> {
              return test((java.lang.Long) (value2), chunkPos, value);
            })) {
          this.enabled = true;
        }

        this.map
            .keySet()
            .removeIf(
                value3 -> {
                  return test((java.lang.Long) (value3), chunkPos, value);
                });
        if (this.enabled || this.mergeRadiusSetting.intValue() != this.count5) {
          this.update4();
        }
      }
    }
  }

  public void update4() {
    this.enabled = false;
    this.count5 = this.mergeRadiusSetting.intValue();
    HashSet hashSet = new HashSet(this.set);
    if (hashSet.size() < 2) {
      this.set3 = Set.copyOf(hashSet);
    } else {
      int intValue = Math.max(1, this.count5);
      HashSet hashSet2 = new HashSet();
      HashSet hashSet3 = new HashSet();

      for (long longValue : (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (hashSet)) {
        if (hashSet3.add(longValue)) {
          ArrayList arrayList = new ArrayList();
          ArrayDeque arrayDeque = new ArrayDeque();
          arrayDeque.add(longValue);

          while (!arrayDeque.isEmpty()) {
            long longValue2 = (Long) arrayDeque.poll();
            arrayList.add(longValue2);
            int packedX = ChunkPos.getPackedX(longValue2);
            int packedZ = ChunkPos.getPackedZ(longValue2);

            for (int index = -intValue; index <= intValue; index++) {
              for (int index2 = -intValue; index2 <= intValue; index2++) {
                if (index != 0 || index2 != 0) {
                  long longValue3 = ChunkPos.toLong(packedX + index, packedZ + index2);
                  if (hashSet.contains(longValue3) && hashSet3.add(longValue3)) {
                    arrayDeque.add(longValue3);
                  }
                }
              }
            }
          }

          hashSet2.add(arrayList.size() == 1 ? (Long) arrayList.get(0) : computeLong(arrayList));
        }
      }

      this.set3 = Set.copyOf(hashSet2);
    }
  }

  public static long computeLong(List list) {
    long longValue = 0L;
    long longValue2 = 0L;

    for (long longValue3 : (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (list)) {
      longValue += ChunkPos.getPackedX(longValue3);
      longValue2 += ChunkPos.getPackedZ(longValue3);
    }

    int intValue = (int) Math.round((double) longValue / list.size());
    int intValue2 = (int) Math.round((double) longValue2 / list.size());
    return ChunkPos.toLong(intValue, intValue2);
  }

  public static boolean test(WorldChunk worldChunk) {
    for (ChunkSection chunkSection : worldChunk.getSectionArray()) {
      if (chunkSection != null
          && !chunkSection.isEmpty()
          && chunkSection.getBlockStateContainer().hasAny(ChunkFinderModule::test)) {
        for (int index = 0; index < 16; index++) {
          for (int index2 = 0; index2 < 16; index2++) {
            for (int index3 = 0; index3 < 16; index3++) {
              if (test(chunkSection.getBlockState(index, index3, index2))) {
                return true;
              }
            }
          }
        }
      }
    }

    return false;
  }

  public static boolean test(BlockState blockState) {
    return (blockState.isOf(Blocks.BEEHIVE) || blockState.isOf(Blocks.BEE_NEST))
        && blockState.contains(Properties.HONEY_LEVEL)
        && blockState.get(Properties.HONEY_LEVEL) == 5;
  }

  public static boolean test(long longValue, ChunkPos chunkPos, int intValue) {
    return Math.abs(ChunkPos.getPackedX(longValue) - chunkPos.x) > intValue
        || Math.abs(ChunkPos.getPackedZ(longValue) - chunkPos.z) > intValue;
  }

  public void apply(ChunkPos chunkPos) {
    if (ClientInitializer.getNotificationsHud() != null) {
      ClientInitializer.getNotificationsHud()
          .apply("Chunk Finder · X " + chunkPos.getCenterX() + " Z " + chunkPos.getCenterZ());
    }
  }

  public Set getSet3() {
    return this.set3;
  }

  public void update5() {
    this.set.clear();
    this.set2.clear();
    this.map.clear();
    this.set3 = Set.of();
    this.count3 = 0;
    this.count4 = 0;
    this.enabled = false;
    this.count5 = this.mergeRadiusSetting.intValue();
  }
}

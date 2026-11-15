package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.LongConsumer;
import net.minecraft.network.packet.s2c.play.LightData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;

public final class LightSectionCache {
  public static final int count = -12;
  public static final int count2 = 80;
  public static final int count3 = -60;
  public static final int count4 = 31;
  public static final boolean enabled = Boolean.getBoolean("bunnythekenzo.sus.debug");
  public static final int count5 = Math.min(-12, -60);
  public static final int count6 = Math.max(80, 31);
  public static final int count7 = ChunkSectionPos.getSectionCoord(count5);
  public static final int count8 = ChunkSectionPos.getSectionCoord(count6);
  public static final LightSectionCache lightSectionCache = new LightSectionCache();
  public final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
  public final Set set = ConcurrentHashMap.newKeySet();
  public final ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
  public final Set set2 = ConcurrentHashMap.newKeySet();
  public final List list = new CopyOnWriteArrayList();

  public static LightSectionCache getInstance() {
    return lightSectionCache;
  }

  public void update() {
    this.concurrentHashMap.clear();
    this.set.clear();
    this.concurrentHashMap2.clear();
    this.set2.clear();
  }

  public boolean test(int intValue, int intValue2) {
    return this.set2.remove(ChunkPos.toLong(intValue, intValue2));
  }

  public void apply(LongConsumer longConsumer) {
    this.list.add(longConsumer);
  }

  public void apply(int intValue, int intValue2) {
    long longValue = ChunkPos.toLong(intValue, intValue2);
    this.set2.add(longValue);

    for (LongConsumer longConsumer :
        (java.lang.Iterable<LongConsumer>) (java.lang.Iterable<?>) (this.list)) {
      longConsumer.accept(longValue);
    }
  }

  public void apply(int intValue, int intValue2, LightData lightData, World world) {
    if (world != null) {
      int intValue3 = world.getBottomSectionCoord() - 1;
      BitSet initedBlock = lightData.getInitedBlock();
      BitSet uninitedBlock = lightData.getUninitedBlock();
      var blockNibbles = lightData.getBlockNibbles();
      int index = 0;
      boolean flag = false;

      for (int intValue4 = initedBlock.nextSetBit(0);
          intValue4 >= 0;
          intValue4 = initedBlock.nextSetBit(intValue4 + 1)) {
        byte[] bytes = index < blockNibbles.size() ? (byte[]) blockNibbles.get(index) : null;
        index++;
        int intValue5 = intValue3 + intValue4;
        if (bytes != null && bytes.length == 2048 && intValue5 >= count7 && intValue5 <= count8) {
          long longValue = ChunkSectionPos.asLong(intValue, intValue5, intValue2);
          this.set.remove(longValue);
          this.concurrentHashMap.put(longValue, (byte[]) bytes.clone());
          int value = this.computeInt(longValue, bytes);
          if (enabled && value > 0) {
            ClientInitializer.logger.info(
                "[sus-light] section ({},{},{}): {} light-4/5 positions",
                intValue,
                intValue5,
                intValue2,
                value);
          }

          flag = true;
        }
      }

      for (int intValue6 = uninitedBlock.nextSetBit(0);
          intValue6 >= 0;
          intValue6 = uninitedBlock.nextSetBit(intValue6 + 1)) {
        int intValue7 = intValue3 + intValue6;
        if (intValue7 >= count7 && intValue7 <= count8) {
          long longValue2 = ChunkSectionPos.asLong(intValue, intValue7, intValue2);
          this.concurrentHashMap2.remove(longValue2);
          this.concurrentHashMap.remove(longValue2);
          this.set.add(longValue2);
          flag = true;
        }
      }

      if (flag) {
        this.apply(intValue, intValue2);
      }
    }
  }

  public int computeInt(int intValue, int intValue2, int intValue3) {
    if (intValue2 >= count5 && intValue2 <= count6) {
      long longValue =
          ChunkSectionPos.asLong(
              ChunkSectionPos.getSectionCoord(intValue),
              ChunkSectionPos.getSectionCoord(intValue2),
              ChunkSectionPos.getSectionCoord(intValue3));
      byte[] bytes = (byte[]) this.concurrentHashMap.get(longValue);
      if (bytes == null) {
        return this.set.contains(longValue) ? 0 : -1;
      }

      int intValue4 = (intValue2 & 15) << 8 | (intValue3 & 15) << 4 | intValue & 15;
      int intValue5 = bytes[intValue4 >> 1] & 255;
      return (intValue4 & 1) == 0 ? intValue5 & 15 : intValue5 >> 4 & 15;
    } else {
      return -1;
    }
  }

  public boolean test(int intValue, int intValue2, int intValue3) {
    return this.computeInt(intValue, intValue2, intValue3) == 5;
  }

  public int computeInt(long longValue, byte[] bytes) {
    short[] shortValues = null;
    int index = 0;

    for (int index2 = 0; index2 < 4096; index2++) {
      int intValue = bytes[index2 >> 1] & 255;
      int intValue2 = (index2 & 1) == 0 ? intValue & 15 : intValue >> 4 & 15;
      if (intValue2 == 4 || intValue2 == 5) {
        if (shortValues == null) {
          shortValues = new short[16];
        } else if (index == shortValues.length) {
          shortValues = Arrays.copyOf(shortValues, shortValues.length * 2);
        }

        shortValues[index++] = (short) index2;
      }
    }

    if (index == 0) {
      this.concurrentHashMap2.remove(longValue);
    } else {
      this.concurrentHashMap2.put(longValue, Arrays.copyOf(shortValues, index));
    }

    return index;
  }

  public List getList(int intValue, int intValue2) {
    ArrayList arrayList = null;
    int intValue3 = intValue << 4;
    int intValue4 = intValue2 << 4;

    for (int index = count7; index <= count8; index++) {
      short[] shortValues =
          (short[]) this.concurrentHashMap2.get(ChunkSectionPos.asLong(intValue, index, intValue2));
      if (shortValues != null) {
        int intValue5 = index << 4;

        for (short shortValue : shortValues) {
          int intValue6 = shortValue & '\uffff';
          int intValue7 = intValue5 + (intValue6 >> 8);
          if (intValue7 >= -12 && intValue7 <= 80) {
            int intValue8 = intValue3 + (intValue6 & 15);
            int intValue9 = intValue4 + (intValue6 >> 4 & 15);
            if (this.computeInt(intValue8, intValue7, intValue9) == 5) {
              if (arrayList == null) {
                arrayList = new ArrayList();
              }

              arrayList.add(new BlockPos(intValue8, intValue7, intValue9));
            }
          }
        }
      }
    }

    return arrayList == null ? List.of() : arrayList;
  }

  public List getList(int intValue, int intValue2, int intValue3, int intValue4) {
    ArrayList arrayList = null;
    int intValue5 = intValue << 4;
    int intValue6 = intValue2 << 4;
    int intValue7 = Math.max(count7, ChunkSectionPos.getSectionCoord(intValue3));
    int intValue8 = Math.min(count8, ChunkSectionPos.getSectionCoord(intValue4));

    for (int index = intValue7; index <= intValue8; index++) {
      short[] shortValues =
          (short[]) this.concurrentHashMap2.get(ChunkSectionPos.asLong(intValue, index, intValue2));
      if (shortValues != null) {
        int intValue9 = index << 4;

        for (short shortValue : shortValues) {
          int intValue10 = shortValue & '\uffff';
          int intValue11 = intValue9 + (intValue10 >> 8);
          if (intValue11 >= intValue3 && intValue11 <= intValue4) {
            int intValue12 = intValue5 + (intValue10 & 15);
            int intValue13 = intValue6 + (intValue10 >> 4 & 15);
            int value = this.computeInt(intValue12, intValue11, intValue13);
            if (value == 4 || value == 5) {
              if (arrayList == null) {
                arrayList = new ArrayList();
              }

              arrayList.add(
                  new LightSectionCacheData(
                      new BlockPos(intValue12, intValue11, intValue13), value));
            }
          }
        }
      }
    }

    return arrayList == null ? List.of() : arrayList;
  }

  public void injectForTest(int intValue, int intValue2, int intValue3, int intValue4) {
    int sectionCoord = ChunkSectionPos.getSectionCoord(intValue);
    int sectionCoord2 = ChunkSectionPos.getSectionCoord(intValue3);
    long longValue =
        ChunkSectionPos.asLong(
            sectionCoord, ChunkSectionPos.getSectionCoord(intValue2), sectionCoord2);
    this.set.remove(longValue);
    byte[] bytes =
        (byte[])
            (this.concurrentHashMap.computeIfAbsent(
                longValue,
                value -> {
                  return new byte[2048];
                }));
    int intValue5 = (intValue2 & 15) << 8 | (intValue3 & 15) << 4 | intValue & 15;
    int index = intValue5 >> 1;
    int intValue6 = (intValue5 & 1) * 4;
    bytes[index] = (byte) (bytes[index] & ~(15 << intValue6) | (intValue4 & 15) << intValue6);
    this.computeInt(longValue, bytes);
    this.apply(sectionCoord, sectionCoord2);
  }

  public boolean test2(int intValue, int intValue2) {
    for (int index = count7; index <= count8; index++) {
      long longValue = ChunkSectionPos.asLong(intValue, index, intValue2);
      if (this.concurrentHashMap.containsKey(longValue) || this.set.contains(longValue)) {
        return true;
      }
    }

    return false;
  }
}

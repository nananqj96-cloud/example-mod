package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public final class IncrementalChunkScanner {
  public int count;
  public List list = new ArrayList();
  public final int count2;
  public final int count3;
  public final int count4;
  public final int count5;
  public volatile List list2 = List.of();
  public Map map = new LinkedHashMap();
  public Map map2 = new LinkedHashMap();
  public Set set = new HashSet();
  public int count6;
  public int count7;
  public int[] countArray = new int[0];
  public int count8 = -1;
  public int count9 = Integer.MIN_VALUE;
  public int count10 = Integer.MIN_VALUE;
  public int count11;
  public boolean enabled;
  public boolean enabled2;
  public ClientWorld clientWorld;

  public IncrementalChunkScanner(int count22, int count32, int count42, int count52) {
    this.count2 = count22;
    this.count3 = count32;
    this.count4 = count42;
    this.count5 = count52;
  }

  public List getList2() {
    return this.list2;
  }

  public void update() {
    this.enabled = true;
  }

  public void update2() {
    this.count = 0;
    this.list = new ArrayList();
    this.list2 = List.of();
    this.map = new LinkedHashMap();
    this.map2 = new LinkedHashMap();
    this.set = new HashSet();
    this.count6 = 0;
    this.count7 = 0;
    this.count11 = 0;
    this.enabled = false;
    this.enabled2 = false;
    this.count9 = this.count10 = Integer.MIN_VALUE;
    this.clientWorld = null;
  }

  public void apply(int intValue, ChunkScanOperation chunkScanOperation) {
    this.apply(intValue, chunkScanOperation, null);
  }

  public void apply(int intValue, ChunkSliceScanOperation chunkSliceScanOperation) {
    this.apply(intValue, null, chunkSliceScanOperation);
  }

  public void apply(
      int intValue,
      ChunkScanOperation chunkScanOperation,
      ChunkSliceScanOperation chunkSliceScanOperation) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    ClientPlayerEntity player = instance.player;
    if (world != null && player != null) {
      if (this.clientWorld != world) {
        this.update2();
        this.clientWorld = world;
        this.enabled = true;
      }

      if (this.count8 != intValue) {
        this.ensureOrder(intValue);
        this.enabled = true;
      }

      int x = player.getChunkPos().x;
      int z = player.getChunkPos().z;
      boolean flag = x != this.count9 || z != this.count10;
      if (!this.enabled2) {
        if (!this.enabled && !flag && this.count11 > 0) {
          this.count11--;
          return;
        }

        this.apply(x, z);
      } else if (this.enabled || flag) {
        this.apply(x, z);
      }

      int index = 0;
      int intValue2 = 0;

      for (long longValue = System.nanoTime() + 1000000L;
          this.count7 < this.countArray.length
              && index < this.count2
              && intValue2 < this.count3
              && (index <= 0 || System.nanoTime() < longValue);
          index++) {
        int intValue3 = this.countArray[this.count7];
        short shortValue = (short) (intValue3 >> 16);
        short shortValue2 = (short) (intValue3 & 65535);
        int intValue4 = this.count9 + shortValue;
        int intValue5 = this.count10 + shortValue2;
        long longValue2 = ChunkPos.toLong(intValue4, intValue5);
        int intValue6 = this.count5 - this.count6;
        if (intValue6 <= 0) {
          this.set.add(longValue2);
        } else if (world.getChunkManager().isChunkLoaded(intValue4, intValue5)) {
          WorldChunk chunk = world.getChunk(intValue4, intValue5);
          List list3;
          if (chunkSliceScanOperation == null) {
            list3 = new ArrayList();
            intValue2 += chunkScanOperation.computeInt(chunk, list3, intValue6);
          } else {
            list3 = this.list;

            while (this.count < chunk.getSectionArray().length && list3.size() < intValue6) {
              intValue2 +=
                  chunkSliceScanOperation.computeInt(chunk, this.count++, list3, intValue6);
              if (System.nanoTime() >= longValue || intValue2 >= this.count3) {
                break;
              }
            }

            if (this.count < chunk.getSectionArray().length && list3.size() < intValue6) {
              break;
            }
          }

          this.set.add(longValue2);
          if (!list3.isEmpty()) {
            List list4 = List.copyOf(list3);
            this.map2.put(longValue2, list4);
            this.count6 = this.count6 + list4.size();
          }
        }

        this.count = 0;
        this.list = new ArrayList();
        this.count7++;
      }

      this.update5();
      if (this.count7 >= this.countArray.length) {
        this.update3();
        this.update4();
        this.update6();
        this.map2 = new LinkedHashMap();
        this.set = new HashSet();
        this.count6 = 0;
        this.enabled2 = false;
        this.count11 = this.count4;
      }
    }
  }

  public void apply(int count92, int count102) {
    this.count = 0;
    this.list = new ArrayList();
    if (this.enabled2) {
      this.update3();
    }

    this.count9 = count92;
    this.count10 = count102;
    this.update4();
    this.count7 = 0;
    this.map2 = new LinkedHashMap();
    this.set = new HashSet();
    this.count6 = 0;
    this.count11 = 0;
    this.enabled = false;
    this.enabled2 = true;
  }

  public void update3() {
    for (long longValue : (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (this.set)) {
      this.map.remove(longValue);
    }

    this.map.putAll(this.map2);
  }

  public void update4() {
    if (!this.map.isEmpty()) {
      LinkedHashMap map3 = new LinkedHashMap();
      int intValue = 0;

      for (int intValue2 : this.countArray) {
        short shortValue = (short) (intValue2 >> 16);
        short shortValue2 = (short) (intValue2 & 65535);
        long longValue = ChunkPos.toLong(this.count9 + shortValue, this.count10 + shortValue2);
        List list3 = (List) this.map.get(longValue);
        if (list3 != null && !list3.isEmpty()) {
          int intValue3 = this.count5 - intValue;
          if (intValue3 <= 0) {
            break;
          }

          List list4 = list3.size() <= intValue3 ? list3 : List.copyOf(list3.subList(0, intValue3));
          map3.put(longValue, list4);
          intValue += list4.size();
        }
      }

      this.map = map3;
    }
  }

  public void update5() {
    ArrayList arrayList = new ArrayList(Math.min(this.count5, this.count6 + this.list2.size()));
    this.apply(arrayList, this.map2.values());
    if (arrayList.size() < this.count5) {
      for (Entry entry :
          (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.map.entrySet())) {
        long longValue = (Long) entry.getKey();
        if (!this.set.contains(longValue) && this.test(longValue)) {
          this.apply(arrayList, (List) entry.getValue());
          if (arrayList.size() >= this.count5) {
            break;
          }
        }
      }
    }

    this.list2 = arrayList.isEmpty() ? List.of() : List.copyOf(arrayList);
  }

  public void update6() {
    ArrayList arrayList = new ArrayList(Math.min(this.count5, this.list2.size()));
    this.apply(arrayList, this.map.values());
    this.list2 = arrayList.isEmpty() ? List.of() : List.copyOf(arrayList);
  }

  public void apply(List list3, Iterable iterable) {
    for (List list4 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (iterable)) {
      this.apply(list3, list4);
      if (list3.size() >= this.count5) {
        return;
      }
    }
  }

  public void apply(List list3, List list4) {
    int intValue = this.count5 - list3.size();
    if (intValue > 0) {
      if (list4.size() <= intValue) {
        list3.addAll(list4);
      } else {
        list3.addAll(list4.subList(0, intValue));
      }
    }
  }

  public boolean test(long longValue) {
    int intValue = (int) longValue;
    int intValue2 = (int) (longValue >>> 32);
    return Math.abs(intValue - this.count9) <= this.count8
        && Math.abs(intValue2 - this.count10) <= this.count8;
  }

  public void ensureOrder(int count82) {
    if (this.count8 != count82) {
      int intValue = 2 * count82 + 1;
      Integer[] intValueArray = new Integer[intValue * intValue];
      int index = 0;

      for (int index2 = -count82; index2 <= count82; index2++) {
        for (int index3 = -count82; index3 <= count82; index3++) {
          intValueArray[index++] = (index2 & 65535) << 16 | index3 & 65535;
        }
      }

      Arrays.sort(
          intValueArray,
          (intValue2, intValue3) -> {
            short shortValue = (short) (intValue2 >> 16);
            short shortValue2 = (short) (intValue2 & 65535);
            short shortValue3 = (short) (intValue3 >> 16);
            short shortValue4 = (short) (intValue3 & 65535);
            return Integer.compare(
                shortValue * shortValue + shortValue2 * shortValue2,
                shortValue3 * shortValue3 + shortValue4 * shortValue4);
          });
      int[] countArray2 = new int[intValueArray.length];

      for (int index4 = 0; index4 < intValueArray.length; index4++) {
        countArray2[index4] = intValueArray[index4];
      }

      this.countArray = countArray2;
      this.count8 = count82;
    }
  }
}

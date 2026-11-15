package bunnythekenzo.client;

import java.util.List;
import java.util.Set;
import net.minecraft.util.math.ChunkPos;

public final class ChunkRouteTracker {
  public static final int count = 24;
  public List list = List.of();
  public Long longValue;
  public int count2;

  public List getList() {
    return this.list;
  }

  public void update() {
    this.list = List.of();
    this.longValue = null;
    this.count2 = 0;
  }

  public boolean test(Set set) {
    if (this.longValue != null && set.contains(this.longValue)) {
      this.longValue = null;
      this.count2 = 0;
    }

    if (!this.list.isEmpty() && set.contains(((ChunkPos) this.list.getFirst()).toLong())) {
      this.update();
      return true;
    } else {
      return false;
    }
  }

  public boolean test(List list2, ChunkPos chunkPos) {
    boolean flag = !this.list.isEmpty();
    if (flag && computeLong((ChunkPos) this.list.getFirst(), chunkPos) < 576L) {
      this.longValue = null;
      this.count2 = 0;
      return false;
    }

    for (BlockPosData3 blockPosData3 :
        (java.lang.Iterable<BlockPosData3>) (java.lang.Iterable<?>) (list2)) {
      ChunkPos chunkPos2 = new ChunkPos(blockPosData3.timestamp());
      if (!flag
          || !chunkPos2.equals(this.list.getFirst()) && computeLong(chunkPos2, chunkPos) <= 64L) {
        if (this.longValue == null || this.longValue != blockPosData3.timestamp()) {
          this.longValue = blockPosData3.timestamp();
          this.count2 = 1;
          return false;
        }

        if (++this.count2 < 3) {
          return false;
        }

        this.longValue = null;
        this.count2 = 0;
        this.list = List.of(chunkPos2);
        return true;
      }
    }

    this.longValue = null;
    this.count2 = 0;
    return false;
  }

  public static long computeLong(ChunkPos chunkPos, ChunkPos chunkPos2) {
    long longValue2 = (long) chunkPos.x - chunkPos2.x;
    long longValue3 = (long) chunkPos.z - chunkPos2.z;
    return longValue2 * longValue2 + longValue3 * longValue3;
  }
}

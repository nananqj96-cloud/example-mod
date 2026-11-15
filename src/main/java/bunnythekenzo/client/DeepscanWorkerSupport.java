package bunnythekenzo.client;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public final class DeepscanWorkerSupport {
  public final WorldChunk worldChunk;
  public final long timestamp;
  public final DeepscanWorkerData deepscanWorkerData;
  public final Map map;
  public final Map map2;
  public final Map map3;
  public int count;
  public int count2;
  public int count3;
  public List list;
  public int count4;
  public int count5;
  public boolean enabled;
  public final DeepscanWorker deepscanWorker;

  public DeepscanWorkerSupport(DeepscanWorker deepscanWorker2, WorldChunk worldChunk2) {
    this.deepscanWorker = deepscanWorker2;
    this.map = new HashMap();
    this.map2 = new HashMap();
    this.map3 = new LinkedHashMap();
    this.count = -4;
    this.worldChunk = worldChunk2;
    this.timestamp = worldChunk2.getPos().toLong();
    DeepscanWorkerData deepscanWorkerData2 =
        (DeepscanWorkerData) deepscanWorker2.map.get(this.timestamp);
    this.deepscanWorkerData =
        deepscanWorkerData2 != null && deepscanWorkerData2.worldChunk() == worldChunk2
            ? deepscanWorkerData2
            : null;
  }

  public boolean step(long longValue) {
    if (this.count > 1) {
      return true;
    }

    int index = this.count - this.deepscanWorker.clientWorld.getBottomSectionCoord();
    if (index >= 0 && index < this.worldChunk.getSectionArray().length) {
      ChunkSection chunkSection = this.worldChunk.getSectionArray()[index];
      if (!this.enabled) {
        this.enabled = true;
        if (!chunkSection
            .getBlockStateContainer()
            .hasAny(
                blockState -> {
                  return blockState.isOf(Blocks.BUDDING_AMETHYST);
                })) {
          this.count2 = 4096;
        }
      }

      if (this.count2 >= 4096) {
        if (this.list == null) {
          this.list =
              chunkSection
                      .getBlockStateContainer()
                      .hasAny(
                          blockState2 -> {
                            return blockState2.isOf(Blocks.AMETHYST_BLOCK)
                                || blockState2.isOf(Blocks.BUDDING_AMETHYST);
                          })
                  ? LightSectionCache.getInstance()
                      .getList(
                          this.worldChunk.getPos().x,
                          this.worldChunk.getPos().z,
                          Math.max(-60, this.count << 4),
                          (this.count << 4) + 15)
                  : List.of();
          return false;
        }

        if (this.count4 < this.list.size()) {
          LightSectionCacheData lightSectionCacheData =
              (LightSectionCacheData) this.list.get(this.count4++);
          if (lightSectionCacheData.count() == 5) {
            this.count4--;
            if (!this.test(lightSectionCacheData.blockPos(), false, longValue)) {
              return false;
            }

            this.count4++;
          } else {
            this.count4--;
            if (this.count5 < DeepscanWorker.directionArray.length) {
              if (!this.test(
                  lightSectionCacheData
                      .blockPos()
                      .offset(DeepscanWorker.directionArray[this.count5]),
                  true,
                  longValue)) {
                return false;
              }

              this.count5++;
              return false;
            }

            this.count5 = 0;
            this.count4++;
          }

          return false;
        } else {
          this.update();
          return false;
        }
      } else {
        for (int intValue = Math.min(4096, this.count2 + 32);
            this.count2 < intValue;
            this.count2++) {
          if (System.nanoTime() >= longValue) {
            return false;
          }

          int intValue2 = (this.count << 4) + (this.count2 >> 8);
          if (intValue2 >= -60) {
            int intValue3 = this.count2 & 15;
            int intValue4 = this.count2 >> 4 & 15;
            if (chunkSection
                .getBlockState(intValue3, this.count2 >> 8, intValue4)
                .isOf(Blocks.BUDDING_AMETHYST)) {
              BlockPos blockPos =
                  new BlockPos(
                      (this.worldChunk.getPos().x << 4) + intValue3,
                      intValue2,
                      (this.worldChunk.getPos().z << 4) + intValue4);

              for (Direction direction : DeepscanWorker.directionArray) {
                BlockPos blockPos2 = blockPos.offset(direction);
                int value =
                    DeepscanWorker.computeInt(
                        this.deepscanWorker.getBlockState(blockPos2), direction);
                if (value >= 0) {
                  long timestamp2 = blockPos2.asLong();
                  if (this.map.size() >= 512 && !this.map.containsKey(timestamp2)) {
                    this.count3++;
                  } else {
                    this.map.put(timestamp2, value);
                    int value2 =
                        DeepscanWorker.computeInt(
                            this.deepscanWorkerData == null
                                ? null
                                : (Integer) this.deepscanWorkerData.map().get(timestamp2),
                            value,
                            this.deepscanWorkerData == null
                                ? 0
                                : (Integer)
                                    this.deepscanWorkerData.map2().getOrDefault(timestamp2, 0));
                    this.map2.put(timestamp2, value2);
                    if (value > 0 || value2 > 0) {
                      this.map3.put(
                          timestamp2, new BlockPosData5(timestamp2, value, value2, false));
                    }
                  }
                }
              }
            }
          }
        }

        return false;
      }
    } else {
      this.update();
      return false;
    }
  }

  public void update() {
    this.count++;
    this.count2 = 0;
    this.enabled = false;
    this.list = null;
    this.count4 = this.count5 = 0;
  }

  public boolean test(BlockPos blockPos, boolean flag, long longValue) {
    if (blockPos.getY() >= -60
        && blockPos.getY() <= 31
        && blockPos.getX() >> 4 == this.worldChunk.getPos().x
        && blockPos.getZ() >> 4 == this.worldChunk.getPos().z
        && !this.map3.containsKey(blockPos.asLong())) {
      BlockState blockState = this.deepscanWorker.getBlockState(blockPos);
      if (blockState != null && blockState.getLuminance() <= 0) {
        boolean flag2 = false;
        int index = 0;
        int value =
            LightSectionCache.getInstance()
                .computeInt(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (value >= 0 && (flag || value == 5) && (!flag || value <= 5)) {
          for (Direction direction : DeepscanWorker.directionArray) {
            BlockPos blockPos2 = blockPos.offset(direction);
            BlockState blockState2 = this.deepscanWorker.getBlockState(blockPos2);
            if (blockState2 == null) {
              return true;
            }

            flag2 |=
                blockState2.isOf(Blocks.AMETHYST_BLOCK)
                    || blockState2.isOf(Blocks.BUDDING_AMETHYST);
            int value2 =
                LightSectionCache.getInstance()
                    .computeInt(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            if (value2 < 0 || value2 > 5 || blockState2.getLuminance() > 0) {
              return true;
            }

            if (value2 == 4) {
              index++;
            }
          }

          if (flag2 && (!flag || index >= 2)) {
            for (int index2 = -4; index2 <= 4; index2++) {
              for (int index3 = -4; index3 <= 4; index3++) {
                for (int index4 = -4; index4 <= 4; index4++) {
                  if (System.nanoTime() >= longValue) {
                    return false;
                  }

                  if (Math.abs(index2) + Math.abs(index3) + Math.abs(index4) <= 4) {
                    BlockState blockState3 =
                        this.deepscanWorker.getBlockState(blockPos.add(index2, index3, index4));
                    if (blockState3 == null || blockState3.getLuminance() > 0) {
                      return true;
                    }
                  }
                }
              }
            }

            if (this.map3.size() >= 512) {
              this.count3++;
              return true;
            } else {
              this.map3.put(blockPos.asLong(), new BlockPosData5(blockPos.asLong(), 0, 0, true));
              return true;
            }
          } else {
            return true;
          }
        } else {
          return true;
        }
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  public DeepscanWorkerData getDeepscanWorkerData() {
    return new DeepscanWorkerData(
        this.worldChunk,
        Map.copyOf(this.map),
        Map.copyOf(this.map2),
        List.copyOf(this.map3.values()),
        this.count3);
  }
}

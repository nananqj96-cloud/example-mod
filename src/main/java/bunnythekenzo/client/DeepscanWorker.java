package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.module.SusChunkFinderModule;

public final class DeepscanWorker {
  public static final int count = 512;
  public static final Direction[] directionArray = Direction.values();
  public static final int count2 = 30;
  public static final int count3 = 50;
  public static final int count4 = 12;
  public static final int count5 = 128;
  public static final int count6 = 63;
  public static final long timestamp = 250000L;
  public final Map map = new HashMap();
  public final Set set = new HashSet();
  public final Set set2 = new HashSet();
  public final ChunkRouteTracker chunkRouteTracker = new ChunkRouteTracker();
  public final ExecutorService executorService =
      Executors.newSingleThreadExecutor(
          runnable -> {
            Thread thread = new Thread(runnable, "bunnythekenzo-deepscan");
            thread.setDaemon(true);
            thread.setPriority(1);
            return thread;
          });
  public Future future;
  public ClientWorld clientWorld;
  public BlockPosData6 blockPosData6;
  public BlockPosData7 blockPosData7;
  public DeepscanWorkerSupport deepscanWorkerSupport;
  public int count7;
  public int count8;
  public long timestamp2;
  public long timestamp3;
  public long timestamp4;
  public long timestamp5;

  public DeepscanWorker() {
    this.blockPosData7 = BlockPosData7.blockPosData7;
    this.timestamp3 = -1L;
    this.timestamp4 = -1L;
  }

  public BlockPosData7 getBlockPosData7() {
    return this.blockPosData7;
  }

  public BlockPosData6 getBlockPosData6() {
    return this.blockPosData6;
  }

  public int computeInt() {
    return this.map.size();
  }

  public List getList() {
    return this.chunkRouteTracker.getList();
  }

  public void update() {
    if (this.future != null) {
      this.future.cancel(true);
    }

    this.future = null;
    this.map.clear();
    this.set.clear();
    this.deepscanWorkerSupport = null;
    this.clientWorld = null;
    this.blockPosData6 = null;
    this.blockPosData7 = BlockPosData7.blockPosData7;
    this.count7 = this.count8 = 0;
    this.timestamp5 = 0L;
    this.chunkRouteTracker.update();
    this.set2.clear();
    this.timestamp2++;
    this.timestamp3 = -1L;
    this.timestamp4 = -1L;
  }

  public void apply(long longValue) {
    if (this.map.remove(longValue) != null) {
      this.timestamp2++;
      this.update2();
    }

    if (this.deepscanWorkerSupport != null && this.deepscanWorkerSupport.timestamp == longValue) {
      this.deepscanWorkerSupport = null;
    }

    this.set.remove(longValue);
  }

  public void update2() {
    if (this.future != null) {
      this.future.cancel(true);
    }

    this.future = null;
    this.timestamp3 = -1L;
  }

  public void tick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      if (this.clientWorld != instance.world) {
        this.update();
        this.clientWorld = instance.world;
      }

      SusChunkFinderModule susChunkFinderModule =
          ClientInitializer.getModuleManager().susChunkFinderModule;
      if (!susChunkFinderModule.isEnabled()) {
        susChunkFinderModule.suspiciousChunkScanner.apply(false);
      }

      SuspiciousChunkScanner suspiciousChunkScanner = susChunkFinderModule.suspiciousChunkScanner;
      ChunkPos chunkPos = instance.player.getChunkPos();
      BlockPosData6 blockPosData62 = new BlockPosData6(chunkPos.x - 15, chunkPos.z - 25, 30, 50);
      if (!blockPosData62.equals(this.blockPosData6)) {
        this.blockPosData6 = blockPosData62;
        if (this.deepscanWorkerSupport != null
            && !this.blockPosData6.test(
                ChunkPos.getPackedX(this.deepscanWorkerSupport.timestamp),
                ChunkPos.getPackedZ(this.deepscanWorkerSupport.timestamp))) {
          this.deepscanWorkerSupport = null;
        }

        this.map
            .keySet()
            .removeIf(
                value -> {
                  return !this.blockPosData6.test(
                      ChunkPos.getPackedX((java.lang.Long) (value)),
                      ChunkPos.getPackedZ((java.lang.Long) (value)));
                });
        this.set.removeIf(
            value2 -> {
              return !this.blockPosData6.test(
                  ChunkPos.getPackedX((java.lang.Long) (value2)),
                  ChunkPos.getPackedZ((java.lang.Long) (value2)));
            });
        this.timestamp2++;
        this.update2();
      }

      if (this.timestamp4 != suspiciousChunkScanner.getTimestamp()) {
        this.timestamp4 = suspiciousChunkScanner.getTimestamp();
        this.timestamp2++;
        this.set2.addAll(ChunkClusterCalculator.getSet(suspiciousChunkScanner.getList2()));
        if (this.chunkRouteTracker.test(this.set2)) {
          DeepscanNotifications.update2();
        }
      }

      if (this.future != null && this.future.isDone()) {
        try {
          this.blockPosData7 = (BlockPosData7) this.future.get();
          this.publishHighlights(chunkPos);
        } catch (CancellationException failure) {
        } catch (InterruptedException failure2) {
          Thread.currentThread().interrupt();
        } catch (ExecutionException failure3) {
          ClientInitializer.logger.warn("Deepscan analysis failed", failure3.getCause());
        }

        this.future = null;
      }

      long longValue = System.nanoTime() + 250000L;
      int index = 0;

      do {
        if (this.deepscanWorkerSupport == null) {
          int count72 = this.count7;
          this.count7 = (this.count7 + 1) % 1500;
          int intValue = this.blockPosData6._i() + count72 % 30;
          int intValue2 = this.blockPosData6._T() + count72 / 30;
          WorldChunk worldChunk =
              this.clientWorld.getChunkManager().getWorldChunk(intValue, intValue2, false);
          if (worldChunk == null || worldChunk.isEmpty()) {
            this.apply(ChunkPos.toLong(intValue, intValue2));
            continue;
          }

          this.deepscanWorkerSupport = new DeepscanWorkerSupport(this, worldChunk);
        }

        if (this.clientWorld
                .getChunkManager()
                .getWorldChunk(
                    this.deepscanWorkerSupport.worldChunk.getPos().x,
                    this.deepscanWorkerSupport.worldChunk.getPos().z,
                    false)
            != this.deepscanWorkerSupport.worldChunk) {
          this.apply(this.deepscanWorkerSupport.timestamp);
          this.deepscanWorkerSupport = null;
        } else if (this.deepscanWorkerSupport.step(longValue)) {
          this.map.put(
              this.deepscanWorkerSupport.timestamp,
              this.deepscanWorkerSupport.getDeepscanWorkerData());
          this.deepscanWorkerSupport = null;
          this.timestamp2++;
          this.timestamp5++;
        }
      } while (++index < 256 && System.nanoTime() < longValue);

      if (++this.count8 % 20 == 0 && this.future == null && this.timestamp2 != this.timestamp3) {
        List list = List.copyOf(this.map.values());
        List list2 = suspiciousChunkScanner.getList2();
        BlockPosData6 blockPosData63 = this.blockPosData6;
        this.timestamp3 = this.timestamp2;
        this.future =
            this.executorService.submit(
                () -> {
                  ArrayList arrayList = new ArrayList();
                  int intValue3 = 0;

                  for (DeepscanWorkerData deepscanWorkerData :
                      (java.lang.Iterable<DeepscanWorkerData>) (java.lang.Iterable<?>) (list)) {
                    if (Thread.currentThread().isInterrupted()) {
                      throw new CancellationException();
                    }

                    intValue3 += deepscanWorkerData.count();
                    int intValue4 =
                        Math.min(deepscanWorkerData.list().size(), 32768 - arrayList.size());
                    arrayList.addAll(deepscanWorkerData.list().subList(0, intValue4));
                    intValue3 += deepscanWorkerData.list().size() - intValue4;
                  }

                  return ChunkClusterCalculator.rankBaseAreas(
                      ChunkClusterCalculator.calculate(
                          arrayList, blockPosData63, 12.0, 128, intValue3),
                      list2,
                      blockPosData63,
                      128);
                });
      }
    } else {
      if (this.clientWorld != null) {
        this.update();
      }
    }
  }

  public void publishHighlights(ChunkPos chunkPos) {
    if (this.chunkRouteTracker.test(
        this.blockPosData7.list2().stream()
            .filter(
                value -> {
                  return !this.set2.contains(((bunnythekenzo.client.BlockPosData3) (value)).timestamp());
                })
            .toList(),
        chunkPos)) {
      ChunkPos chunkPos2 = (ChunkPos) this.chunkRouteTracker.getList().getFirst();
      BlockPosData3 blockPosData3 =
          (bunnythekenzo.client.BlockPosData3)
              (this.blockPosData7.list2().stream()
                  .filter(
                      value2 -> {
                        return ((bunnythekenzo.client.BlockPosData3) (value2)).timestamp()
                            == chunkPos2.toLong();
                      })
                  .findFirst()
                  .orElseThrow());
      DeepscanNotifications.apply(
          chunkPos2.x * 16 + 8, chunkPos2.z * 16 + 8, blockPosData3.count());
    }
  }

  public BlockState getBlockState(BlockPos blockPos) {
    WorldChunk worldChunk =
        this.clientWorld
            .getChunkManager()
            .getWorldChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, false);
    return worldChunk != null && !worldChunk.isEmpty() ? worldChunk.getBlockState(blockPos) : null;
  }

  public static int computeInt(BlockState blockState, Direction direction) {
    if (blockState == null) {
      return -1;
    } else if (!blockState.isAir() && !blockState.isOf(Blocks.WATER)) {
      int intValue =
          blockState.isOf(Blocks.SMALL_AMETHYST_BUD)
              ? 1
              : (blockState.isOf(Blocks.MEDIUM_AMETHYST_BUD)
                  ? 2
                  : (blockState.isOf(Blocks.LARGE_AMETHYST_BUD)
                      ? 3
                      : (blockState.isOf(Blocks.AMETHYST_CLUSTER) ? 4 : -1)));
      return intValue > 0 && blockState.get(Properties.FACING) != direction ? -1 : intValue;
    } else {
      return 0;
    }
  }

  public static int computeInt(Integer intValue, int intValue2, int intValue3) {
    return Math.min(
        4, intValue3 + (intValue != null && intValue2 > intValue ? intValue2 - intValue : 0));
  }
}

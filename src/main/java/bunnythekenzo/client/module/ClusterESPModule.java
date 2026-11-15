package bunnythekenzo.client.module;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.LightSectionCache;
import bunnythekenzo.client.render.GeodeClusterRenderer;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public final class ClusterESPModule extends Module {
  public static final int count = -58;
  public static final int count2 = 30;
  public static final int count3 = 8;
  public static final int count4 = 0;
  public static final int count5 = 4;
  public static final int count6 = 9000;
  public static final int count7 = 6;
  public static final int count8 = 4;
  public static final Direction[] directionArray = Direction.values();
  public final NumberSetting simDistanceSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Sim Distance",
                  "Chunk radius searched around the player",
                  8.0,
                  1.0,
                  32.0,
                  1.0,
                  " ch"));
  public final NumberSetting thresholdSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Threshold",
                  "Open slots needed before the geode's chunk is marked",
                  12.0,
                  1.0,
                  100.0,
                  1.0));
  public final BooleanSetting showTracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Tracers", "Draw screen-space tracers to detected slots", true));
  public final BooleanSetting onlyLookingDirSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Only Looking Dir", "Only render detections in front of the camera", true));
  public final BooleanSetting renderChunkOverlaySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Render Chunk Overlay", "Mark chunks that pass the threshold at Y 55", true));
  public final NumberSetting espAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "ESP Alpha", "Opacity of detected-slot boxes", 180.0, 0.0, 255.0, 1.0));
  public final NumberSetting tracerAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Tracer Alpha", "Opacity of tracer lines", 220.0, 0.0, 255.0, 1.0));
  public final NumberSetting chunkAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Chunk Alpha", "Opacity of marked chunk overlays", 200.0, 0.0, 255.0, 1.0));
  public final Map map = new ConcurrentHashMap();
  public final Set set = ConcurrentHashMap.newKeySet();
  public final Set set2 = ConcurrentHashMap.newKeySet();
  public final ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
  public final Set set3 = ConcurrentHashMap.newKeySet();
  public ChunkPos chunkPos;
  public int count9;

  public ClusterESPModule() {
    super(
        "Cluster ESP",
        "Finds dark open crystal slots around amethyst geodes",
        ModuleCategory.RENDER);
    LightSectionCache.getInstance()
        .apply(
            longValue -> {
              if (this.isEnabled()) {
                this.apply(longValue);
              }
            });
  }

  @Override
  public void onEnable() {
    this.update4();
    this.update5();
  }

  @Override
  public void onDisable() {
    this.update4();
    GeodeClusterRenderer.update();
  }

  public void update4() {
    this.map.clear();
    this.set.clear();
    this.set2.clear();
    this.concurrentLinkedQueue.clear();
    this.set3.clear();
    this.chunkPos = null;
    this.count9 = 0;
  }

  public void apply(int intValue, int intValue2) {
    if (this.isEnabled()) {
      this.apply(ChunkPos.toLong(intValue, intValue2));
    }
  }

  public void apply2(int intValue, int intValue2) {
    long longValue = ChunkPos.toLong(intValue, intValue2);
    this.map.remove(longValue);
    this.set.remove(longValue);
    this.set2.remove(longValue);
    this.set3.remove(longValue);
    this.concurrentLinkedQueue.remove(longValue);
  }

  public void apply(BlockPos blockPos) {
    if (this.isEnabled()) {
      this.apply(ChunkPos.toLong(blockPos.getX() >> 4, blockPos.getZ() >> 4));
    }
  }

  @Override
  public void onTick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      ChunkPos chunkPos2 = instance.player.getChunkPos();
      if (this.chunkPos == null || !this.chunkPos.equals(chunkPos2)) {
        this.chunkPos = chunkPos2;
        this.update5();
      }

      this.apply(chunkPos2);
      int index = 0;

      while (index < 4) {
        Long longValue = (Long) this.concurrentLinkedQueue.poll();
        if (longValue == null) {
          break;
        }

        this.set3.remove(longValue);
        int packedX = ChunkPos.getPackedX(longValue);
        int packedZ = ChunkPos.getPackedZ(longValue);
        if (test(instance, chunkPos2, packedX, packedZ)) {
          WorldChunk worldChunk =
              instance.world.getChunkManager().getWorldChunk(packedX, packedZ, false);
          if (worldChunk != null) {
            this.apply(instance.world, worldChunk);
            index++;
          }
        }
      }

      if (++this.count9 % 60 == 0) {
        this.update5();
      }
    }
  }

  public void update5() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      ChunkPos chunkPos2 = instance.player.getChunkPos();
      int value = this.simDistanceSetting.intValue();

      for (int index = chunkPos2.x - value; index <= chunkPos2.x + value; index++) {
        for (int index2 = chunkPos2.z - value; index2 <= chunkPos2.z + value; index2++) {
          this.apply(ChunkPos.toLong(index, index2));
        }
      }
    }
  }

  public void apply(long longValue) {
    if (this.set3.add(longValue)) {
      this.concurrentLinkedQueue.offer(longValue);
    }
  }

  public void apply(ClientWorld clientWorld, WorldChunk worldChunk) {
    long longValue = worldChunk.getPos().toLong();
    BlockPos blockPos = findGeodeCenter(worldChunk);
    if (blockPos == null) {
      this.apply2(longValue);
    } else {
      HashSet hashSet = new HashSet();
      Mutable mutable = new Mutable();
      Mutable mutable2 = new Mutable();
      Mutable mutable3 = new Mutable();

      label101:
      for (int index = -8; index <= 8; index++) {
        for (int index2 = -8; index2 <= 8; index2++) {
          for (int index3 = -8; index3 <= 8; index3++) {
            mutable.set(
                blockPos.getX() + index, blockPos.getY() + index2, blockPos.getZ() + index3);
            BlockState blockState = clientWorld.getBlockState(mutable);
            if ((blockState.isOf(Blocks.AIR) || blockState.isOf(Blocks.AMETHYST_CLUSTER))
                && computeInt(clientWorld, mutable) == 0) {
              BlockPos blockPos2 = null;
              boolean flag = false;

              for (Direction direction : directionArray) {
                mutable2.set(mutable, direction);
                int intValue = computeInt(clientWorld, mutable2);
                if (intValue > 4) {
                  flag = true;
                  break;
                }

                if (blockPos2 == null
                    && intValue == 4
                    && clientWorld.getBlockState(mutable2).isOf(Blocks.AIR)) {
                  blockPos2 = mutable2.toImmutable();
                }
              }

              if (!flag && blockPos2 != null) {
                boolean flag2 = false;

                for (Direction direction2 : directionArray) {
                  mutable3.set(blockPos2, direction2);
                  if (computeInt(clientWorld, mutable3) > 4) {
                    flag2 = true;
                    break;
                  }
                }

                if (!flag2) {
                  hashSet.add(mutable.toImmutable());
                  if (hashSet.size() >= 9000) {
                    break label101;
                  }
                }
              }
            }
          }
        }
      }

      if (hashSet.isEmpty()) {
        this.apply2(longValue);
      } else {
        this.map.put(longValue, Set.copyOf(hashSet));
        int value = this.thresholdSetting.intValue();
        if (hashSet.size() >= value) {
          this.set.add(longValue);
          if (this.set2.add(longValue)) {
            this.apply(hashSet.size());
          }
        } else {
          this.set.remove(longValue);
          this.set2.remove(longValue);
        }
      }
    }
  }

  public static BlockPos findGeodeCenter(WorldChunk worldChunk) {
    long longValue = 0L;
    long longValue2 = 0L;
    long longValue3 = 0L;
    int index = 0;
    int startX = worldChunk.getPos().getStartX();
    int startZ = worldChunk.getPos().getStartZ();
    int bottomSectionCoord = worldChunk.getBottomSectionCoord();
    ChunkSection[] sectionArray = worldChunk.getSectionArray();

    for (int index2 = 0; index2 < sectionArray.length; index2++) {
      int intValue = bottomSectionCoord + index2 << 4;
      if (intValue <= 30 && intValue + 15 >= -58) {
        ChunkSection chunkSection = sectionArray[index2];
        if (!chunkSection.isEmpty()
            && chunkSection.hasAny(
                blockState -> {
                  return blockState.isOf(Blocks.AMETHYST_BLOCK)
                      || blockState.isOf(Blocks.BUDDING_AMETHYST);
                })) {
          int intValue2 = Math.max(0, -58 - intValue);
          int intValue3 = Math.min(15, 30 - intValue);

          for (int index3 = intValue2; index3 <= intValue3; index3++) {
            for (int index4 = 0; index4 < 16; index4++) {
              for (int index5 = 0; index5 < 16; index5++) {
                BlockState blockState2 = chunkSection.getBlockState(index4, index3, index5);
                if (blockState2.isOf(Blocks.AMETHYST_BLOCK)
                    || blockState2.isOf(Blocks.BUDDING_AMETHYST)) {
                  longValue += startX + index4;
                  longValue2 += intValue + index3;
                  longValue3 += startZ + index5;
                  index++;
                }
              }
            }
          }
        }
      }
    }

    return index == 0
        ? null
        : new BlockPos(
            (int) (longValue / index), (int) (longValue2 / index), (int) (longValue3 / index));
  }

  public static int computeInt(ClientWorld clientWorld, BlockPos blockPos) {
    int value =
        LightSectionCache.getInstance()
            .computeInt(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    return value >= 0 ? value : clientWorld.getLightLevel(LightType.BLOCK, blockPos);
  }

  public void apply2(long longValue) {
    this.map.remove(longValue);
    this.set.remove(longValue);
    this.set2.remove(longValue);
  }

  public void apply(ChunkPos chunkPos2) {
    int intValue = this.simDistanceSetting.intValue() + 6;

    for (long longValue :
        (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (List.copyOf(this.map.keySet()))) {
      if (Math.max(
              Math.abs(ChunkPos.getPackedX(longValue) - chunkPos2.x),
              Math.abs(ChunkPos.getPackedZ(longValue) - chunkPos2.z))
          > intValue) {
        this.apply2(longValue);
      }
    }
  }

  public static boolean test(
      MinecraftClient minecraftClient, ChunkPos chunkPos2, int intValue, int intValue2) {
    int value = minecraftClient.options.getViewDistance().getValue();
    return Math.abs(intValue - chunkPos2.x) <= value && Math.abs(intValue2 - chunkPos2.z) <= value;
  }

  public void apply(int intValue) {
    if (ClientInitializer.getNotificationsHud() != null) {
      ClientInitializer.getNotificationsHud()
          .apply("Cluster ESP · Found " + intValue + " open slots");
    }

    ClientAudio.apply2(true);
  }

  public Map getMap() {
    return this.map;
  }

  public boolean test(long longValue) {
    return this.set.contains(longValue);
  }

  public int computeInt() {
    return this.map.size();
  }

  public int computeInt2() {
    return this.set.size();
  }
}

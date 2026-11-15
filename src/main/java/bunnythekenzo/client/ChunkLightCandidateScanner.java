package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public final class ChunkLightCandidateScanner {
  public static final int count = 5;
  public static final int count2 = 4;
  public static final int count3 = 6;
  public static final int count4 = 768;
  public static final Direction[] directionArray = Direction.values();

  public static List getList(MinecraftClient minecraftClient, WorldChunk worldChunk) {
    if (minecraftClient.world == null
        || worldChunk == null
        || !ChunkLightCandidateScanner.test(worldChunk)) {
      return List.of();
    }
    ChunkPos chunkPos = worldChunk.getPos();
    List list = LightSectionCache.getInstance().getList(chunkPos.x, chunkPos.z, -60, 31);
    if (list.isEmpty()) {
      return List.of();
    }
    boolean bl = ChunkLightCandidateScanner.test(minecraftClient, chunkPos.x - 1, chunkPos.z);
    boolean bl2 = ChunkLightCandidateScanner.test(minecraftClient, chunkPos.x + 1, chunkPos.z);
    boolean bl3 = ChunkLightCandidateScanner.test(minecraftClient, chunkPos.x, chunkPos.z - 1);
    boolean bl4 = ChunkLightCandidateScanner.test(minecraftClient, chunkPos.x, chunkPos.z + 1);
    BlockPos.Mutable mutable = new BlockPos.Mutable();
    HashSet<BlockPos> hashSet = new HashSet<BlockPos>();
    ArrayList<BlockPosData8> arrayList = new ArrayList<BlockPosData8>();
    HashSet<Long> hashSet2 = new HashSet<Long>();
    block4:
    for (LightSectionCacheData lightSectionCacheData :
        (java.lang.Iterable<LightSectionCacheData>) (java.lang.Iterable<?>) (list)) {
      BlockPos blockPos = lightSectionCacheData.blockPos();
      int n = blockPos.getX() & 0xF;
      int n2 = blockPos.getZ() & 0xF;
      if (n == 0 && !bl || n == 15 && !bl2 || n2 == 0 && !bl3 || n2 == 15 && !bl4) continue;
      switch (ChunkLightCandidateScanner.getBlockPosState(
              minecraftClient,
              mutable,
              blockPos.getX(),
              blockPos.getY(),
              blockPos.getZ(),
              lightSectionCacheData.count())
          .ordinal()) {
        case 1:
        case 2:
          {
            hashSet.add(blockPos.toImmutable());
            break;
          }
        case 3:
          {
            long l = blockPos.asLong();
            if (arrayList.size() >= 768 || !hashSet2.add(l)) continue block4;
            arrayList.add(new BlockPosData8(l, lightSectionCacheData.count()));
            break;
          }
      }
    }
    Iterator iterator =
        ChunkLightCandidateScanner.getSet(minecraftClient, arrayList, hashSet2).iterator();
    while (iterator.hasNext()) {
      long l = (Long) iterator.next();
      hashSet.add(BlockPos.fromLong(l));
    }
    return hashSet.isEmpty() ? List.of() : List.copyOf(hashSet);
  }

  public static boolean test(WorldChunk worldChunk2) {
    ChunkSection[] sectionArray = worldChunk2.getSectionArray();
    int bottomSectionCoord = worldChunk2.getBottomSectionCoord();

    for (int index = 0; index < sectionArray.length; index++) {
      int intValue = bottomSectionCoord + index << 4;
      if (intValue <= 31 && intValue + 15 >= -60) {
        ChunkSection chunkSection = sectionArray[index];
        if (!chunkSection.isEmpty()
            && chunkSection.getBlockStateContainer().hasAny(ChunkLightCandidateScanner::test)) {
          return true;
        }
      }
    }

    return false;
  }

  public static BlockPosState getBlockPosState(
      MinecraftClient minecraftClient2,
      Mutable mutable2,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4) {
    if (intValue4 < 4 || intValue4 > 5) {
      return BlockPosState.NONE;
    } else if (!test(minecraftClient2, mutable2, intValue, intValue2, intValue3, intValue4)) {
      return BlockPosState.NONE;
    } else {
      mutable2.set(intValue, intValue2, intValue3);
      BlockState blockState = minecraftClient2.world.getBlockState(mutable2);
      if (blockState.isOf(Blocks.AMETHYST_CLUSTER)) {
        return BlockPosState.VISIBLE_CLUSTER;
      } else if (blockState.getLuminance() >= intValue4) {
        return BlockPosState.NONE;
      } else if (intValue4 == 5) {
        return test(minecraftClient2, mutable2, intValue, intValue2, intValue3)
            ? BlockPosState.NONE
            : BlockPosState.MASKED_CLUSTER;
      } else {
        return BlockPosState.HALO;
      }
    }
  }

  public static Set getSet(MinecraftClient minecraftClient2, List list2, Set set) {
    HashSet hashSet3 = new HashSet();
    if (list2.isEmpty()) {
      return hashSet3;
    }

    Mutable mutable2 = new Mutable();
    Mutable mutable3 = new Mutable();
    HashMap hashMap = new HashMap();

    for (BlockPosData8 blockPosData8 :
        (java.lang.Iterable<BlockPosData8>) (java.lang.Iterable<?>) (list2)) {
      BlockPos blockPos2 = BlockPos.fromLong(blockPosData8.timestamp());

      for (Direction direction : directionArray) {
        int intValue = blockPos2.getX() + direction.getOffsetX();
        int intValue2 = blockPos2.getY() + direction.getOffsetY();
        int intValue3 = blockPos2.getZ() + direction.getOffsetZ();
        long longValue = BlockPos.asLong(intValue, intValue2, intValue3);
        if (!set.contains(longValue)) {
          mutable2.set(intValue, intValue2, intValue3);
          if (computeInt(minecraftClient2, mutable2) <= blockPosData8.count() - 2) {
            BlockState blockState = minecraftClient2.world.getBlockState(mutable2);
            if (blockState.getLuminance() <= 0 && !test2(blockState)) {
              hashMap.merge(
                  longValue,
                  1,
                  (referenceArgument1_0, referenceArgument1_1) ->
                      Integer.sum(
                          (java.lang.Integer) referenceArgument1_0,
                          (java.lang.Integer) referenceArgument1_1));
            }
          }
        }
      }
    }

    for (long longValue2 : (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (hashMap.keySet())) {
      BlockPos blockPos3 = BlockPos.fromLong(longValue2);
      if (test2(minecraftClient2, mutable3, blockPos3.getX(), blockPos3.getY(), blockPos3.getZ())
          && !test(
              minecraftClient2, mutable3, blockPos3.getX(), blockPos3.getY(), blockPos3.getZ())) {
        hashSet3.add(longValue2);
      }
    }

    return hashSet3;
  }

  public static boolean test(
      MinecraftClient minecraftClient2,
      Mutable mutable2,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4) {
    for (Direction direction : directionArray) {
      mutable2.set(
          intValue + direction.getOffsetX(),
          intValue2 + direction.getOffsetY(),
          intValue3 + direction.getOffsetZ());
      if (computeInt(minecraftClient2, mutable2) > intValue4) {
        return false;
      }
    }

    return true;
  }

  public static int computeInt(MinecraftClient minecraftClient2, BlockPos blockPos2) {
    int value =
        LightSectionCache.getInstance()
            .computeInt(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    return value >= 0 ? value : minecraftClient2.world.getLightLevel(LightType.BLOCK, blockPos2);
  }

  public static boolean test(
      MinecraftClient minecraftClient2,
      Mutable mutable2,
      int intValue,
      int intValue2,
      int intValue3) {
    for (int index = -6; index <= 6; index++) {
      int intValue4 = 6 - Math.abs(index);

      for (int index2 = -intValue4; index2 <= intValue4; index2++) {
        int intValue5 = intValue4 - Math.abs(index2);

        for (int index3 = -intValue5; index3 <= intValue5; index3++) {
          int intValue6 = Math.abs(index) + Math.abs(index2) + Math.abs(index3);
          if (intValue6 != 0) {
            mutable2.set(intValue + index, intValue2 + index2, intValue3 + index3);
            BlockState blockState = minecraftClient2.world.getBlockState(mutable2);
            int luminance = blockState.getLuminance();
            if (luminance > 0 && !test2(blockState) && luminance - intValue6 >= 4) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  public static boolean test2(
      MinecraftClient minecraftClient2,
      Mutable mutable2,
      int intValue,
      int intValue2,
      int intValue3) {
    for (Direction direction : directionArray) {
      mutable2.set(
          intValue + direction.getOffsetX(),
          intValue2 + direction.getOffsetY(),
          intValue3 + direction.getOffsetZ());
      if (test(minecraftClient2.world.getBlockState(mutable2))) {
        return true;
      }
    }

    return false;
  }

  public static boolean test(BlockState blockState) {
    return blockState.isOf(Blocks.AMETHYST_BLOCK) || blockState.isOf(Blocks.BUDDING_AMETHYST);
  }

  public static boolean test2(BlockState blockState) {
    return blockState.isOf(Blocks.AMETHYST_BLOCK)
        || blockState.isOf(Blocks.BUDDING_AMETHYST)
        || blockState.isOf(Blocks.CALCITE)
        || blockState.isOf(Blocks.SMOOTH_BASALT)
        || blockState.isOf(Blocks.AMETHYST_CLUSTER)
        || blockState.isOf(Blocks.LARGE_AMETHYST_BUD)
        || blockState.isOf(Blocks.MEDIUM_AMETHYST_BUD)
        || blockState.isOf(Blocks.SMALL_AMETHYST_BUD);
  }

  public static boolean test(MinecraftClient minecraftClient2, int intValue, int intValue2) {
    return minecraftClient2.world.getChunkManager().getWorldChunk(intValue, intValue2, false)
        != null;
  }
}

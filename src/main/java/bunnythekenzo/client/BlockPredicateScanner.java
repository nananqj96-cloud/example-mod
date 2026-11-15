package bunnythekenzo.client;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

public final class BlockPredicateScanner {
  public static final int count = 16;
  public static final int count2 = 32768;
  public final Predicate<net.minecraft.block.BlockState> predicate;
  public final int count3;
  public final double value;
  public final IncrementalChunkScanner incrementalChunkScanner;

  public BlockPredicateScanner(
      Predicate<net.minecraft.block.BlockState> predicate2,
      int intValue,
      int count32,
      int intValue2,
      double doubleValue) {
    this.predicate = predicate2;
    this.count3 = count32;
    this.value = doubleValue * doubleValue;
    this.incrementalChunkScanner = new IncrementalChunkScanner(16, 32768, intValue, intValue2);
  }

  public List getList() {
    return this.incrementalChunkScanner.getList2();
  }

  public void update() {
    this.incrementalChunkScanner.update2();
  }

  public void update2() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      int intValue = Math.min(this.count3, instance.options.getViewDistance().getValue());
      this.incrementalChunkScanner.apply(intValue, this::computeInt);
    }
  }

  public int computeInt(WorldChunk worldChunk, List list, int intValue) {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null && list.size() < intValue) {
      ChunkSection[] sectionArray = worldChunk.getSectionArray();
      int bottomSectionCoord = worldChunk.getBottomSectionCoord();
      int startX = worldChunk.getPos().getStartX();
      int startZ = worldChunk.getPos().getStartZ();
      short shortValue = 0;

      for (int index = 0; index < sectionArray.length; index++) {
        ChunkSection chunkSection = sectionArray[index];
        if (!chunkSection.isEmpty() && chunkSection.hasAny(this.predicate)) {
          int intValue2 = bottomSectionCoord + index << 4;
          shortValue += 4096;

          for (int index2 = 0; index2 < 16; index2++) {
            for (int index3 = 0; index3 < 16; index3++) {
              for (int index4 = 0; index4 < 16; index4++) {
                if (this.predicate.test(chunkSection.getBlockState(index4, index2, index3))) {
                  int intValue3 = startX + index4;
                  int intValue4 = intValue2 + index2;
                  int intValue5 = startZ + index3;
                  if (!(player.squaredDistanceTo(intValue3 + 0.5, intValue4 + 0.5, intValue5 + 0.5)
                      > this.value)) {
                    list.add(new BlockPos(intValue3, intValue4, intValue5));
                    if (list.size() >= intValue) {
                      return shortValue;
                    }
                  }
                }
              }
            }
          }
        }
      }

      return shortValue;
    } else {
      return 0;
    }
  }
}

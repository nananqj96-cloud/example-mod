package bunnythekenzo.client;

import java.util.List;
import net.minecraft.world.chunk.WorldChunk;

public interface ChunkSliceScanOperation {
  int computeInt(WorldChunk worldChunk, int intValue, List list, int intValue2);
}

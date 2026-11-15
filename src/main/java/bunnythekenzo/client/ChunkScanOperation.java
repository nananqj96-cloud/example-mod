package bunnythekenzo.client;

import java.util.List;
import net.minecraft.world.chunk.WorldChunk;

public interface ChunkScanOperation {
  int computeInt(WorldChunk worldChunk, List list, int intValue);
}

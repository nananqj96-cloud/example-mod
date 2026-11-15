package bunnythekenzo.client;

import java.util.List;
import java.util.Map;
import net.minecraft.world.chunk.WorldChunk;

public record DeepscanWorkerData(WorldChunk worldChunk, Map map, Map map2, List list, int count) {}

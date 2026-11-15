package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.util.math.BlockPos;

public final class WorldSignalTypeSupport {
  public final long timestamp;
  public final EnumMap enumMap = new EnumMap<>(WorldSignalType.class);
  public final List list = new ArrayList();
  public double value;
  public double value2;
  public double value3;
  public double value4;

  public WorldSignalTypeSupport(long timestamp2) {
    this.timestamp = timestamp2;
  }

  public void apply(WorldSignalType worldSignalType, BlockPos blockPos) {
    this.enumMap.merge(
        worldSignalType,
        1,
        (referenceArgument12_0, referenceArgument12_1) ->
            Integer.sum(
                (java.lang.Integer) referenceArgument12_0,
                (java.lang.Integer) referenceArgument12_1));
    this.value2 = this.value2 + worldSignalType.weight;
    this.value3 = this.value3 + (blockPos.getX() + 0.5) * worldSignalType.weight;
    this.value4 = this.value4 + (blockPos.getZ() + 0.5) * worldSignalType.weight;
  }

  public void update() {
    double value5 = 0.0;

    for (Entry entry :
        (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.enumMap.entrySet())) {
      value5 +=
          ((WorldSignalType) entry.getKey()).weight
              * Math.min((Integer) entry.getValue(), ((WorldSignalType) entry.getKey()).count);
    }

    this.value = value5;
  }
}

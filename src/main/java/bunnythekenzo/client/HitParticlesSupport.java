package bunnythekenzo.client;

public final class HitParticlesSupport {
  public final int count2;
  public final double value;
  public final double value2;
  public final double value3;
  public final int count;

  public final long timestamp;

  public HitParticlesSupport(
      double value4, double value22, double value32, int count3, int count22, long timestamp2) {
    this.value = value4;
    this.value2 = value22;
    this.value3 = value32;
    this.count = count3;
    this.count2 = count22;
    this.timestamp = timestamp2;
  }

  public float computeFloat(long longValue) {
    return (float) (longValue - this.timestamp) / 1.0E9F;
  }
}

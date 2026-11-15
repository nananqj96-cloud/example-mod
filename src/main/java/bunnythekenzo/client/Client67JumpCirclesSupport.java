package bunnythekenzo.client;

public final class Client67JumpCirclesSupport {
  public final double value;
  public final double value2;
  public final double value3;
  public final float amount;
  public final float amount2;
  public final long timestamp;

  public Client67JumpCirclesSupport(
      double value4,
      double value22,
      double value32,
      float amount3,
      float amount22,
      long timestamp2) {
    this.value = value4;
    this.value2 = value22;
    this.value3 = value32;
    this.amount = amount3;
    this.amount2 = amount22;
    this.timestamp = timestamp2;
  }

  public float computeFloat(long longValue) {
    return (float) (longValue - this.timestamp) / 1.0E9F;
  }
}

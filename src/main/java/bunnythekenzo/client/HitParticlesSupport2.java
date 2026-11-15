package bunnythekenzo.client;

public final class HitParticlesSupport2 {
  public final double value;
  public final double value2;
  public final double value3;
  public final float amount;
  public final float amount2;
  public final float amount3;
  public final int count;
  public final int count2;
  public final float amount4;
  public final float amount5;
  public final float amount6;
  public final float amount7;
  public final float amount8;
  public final long timestamp;

  public HitParticlesSupport2(
      double value4,
      double value22,
      double value32,
      float amount9,
      float amount22,
      float amount32,
      int count3,
      int count22,
      float amount42,
      float amount52,
      float amount62,
      float amount72,
      float amount82,
      long timestamp2) {
    this.value = value4;
    this.value2 = value22;
    this.value3 = value32;
    this.amount = amount9;
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.count = count3;
    this.count2 = count22;
    this.amount4 = amount42;
    this.amount5 = amount52;
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.timestamp = timestamp2;
  }

  public float computeFloat(long longValue) {
    return (float) (longValue - this.timestamp) / 1.0E9F;
  }

  public double computeDouble(float floatValue) {
    return this.value + this.amount * floatValue;
  }

  public double computeDouble2(float floatValue) {
    return this.value2 + this.amount2 * floatValue - 0.5 * this.amount7 * floatValue * floatValue;
  }

  public double computeDouble3(float floatValue) {
    return this.value3 + this.amount3 * floatValue;
  }
}

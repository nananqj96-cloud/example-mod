package bunnythekenzo.client;

public final class StaggeredTransition {
  public final float amount;
  public final float amount2;
  public final Easing easing;
  public final Easing easing2;
  public boolean enabled;
  public long timestamp;
  public boolean enabled2;
  public int count;
  public static final float amount3 = 240.0F;

  public StaggeredTransition(float floatValue, float floatValue2) {
    this(floatValue, floatValue2, Easing.OUT_EXPO, Easing.IN_QUINT);
  }

  public StaggeredTransition(float amount4, float amount22, Easing easing3, Easing easing22) {
    this.amount = amount4;
    this.amount2 = amount22;
    this.easing = easing3;
    this.easing2 = easing22;
  }

  public void apply(int intValue) {
    this.count = Math.max(intValue, 1);
    this.enabled2 = false;
    this.enabled = true;
    this.timestamp = System.nanoTime();
  }

  public void apply2(int intValue) {
    this.count = Math.max(intValue, 1);
    this.enabled2 = true;
    this.enabled = true;
    this.timestamp = System.nanoTime();
  }

  public float computeFloat(int intValue) {
    if (!this.enabled) {
      return 0.0F;
    }

    float floatValue = this.enabled2 ? this.count - 1 - intValue : intValue;
    float floatValue2 = Math.min(this.amount, 240.0F / Math.max(this.count, 1));
    float floatValue3 =
        (float) (System.nanoTime() - this.timestamp) / 1000000.0F - floatValue * floatValue2;
    float floatValue4 =
        Math.clamp(floatValue3 / (this.enabled2 ? this.amount2 * 0.6F : this.amount2), 0.0F, 1.0F);
    float value = (this.enabled2 ? this.easing2 : this.easing).computeFloat(floatValue4);
    return this.enabled2 ? 1.0F - value : value;
  }
}

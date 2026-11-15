package bunnythekenzo.client;

public final class TweenValue {
  public final float durationMillis;
  public final Easing easing;
  public float startValue;
  public float targetValue;
  public long startedNanos;

  public TweenValue(float floatValue, float floatValue2) {
    this(floatValue, floatValue2, Easing.OUT_EXPO);
  }

  public TweenValue(float amount4, float amount22, Easing easing2) {
    this.durationMillis = amount4;
    this.easing = easing2;
    this.startValue = amount22;
    this.targetValue = amount22;
    this.startedNanos = System.nanoTime();
  }

  public void animateTo(float amount32) {
    if (amount32 != this.targetValue) {
      this.startValue = this.getValue();
      this.targetValue = amount32;
      this.startedNanos = System.nanoTime();
    }
  }

  public void snapTo(float amount22) {
    this.startValue = amount22;
    this.targetValue = amount22;
    this.startedNanos = System.nanoTime();
  }

  public float getTarget() {
    return this.targetValue;
  }

  public float getValue() {
    if (!AnimationPreferences.isEnabled()) {
      return this.targetValue;
    }

    float floatValue =
        (float) (System.nanoTime() - this.startedNanos) / 1000000.0F / this.durationMillis;
    return floatValue >= 1.0F
        ? this.targetValue
        : this.startValue
            + (this.targetValue - this.startValue)
                * this.easing.computeFloat(Math.max(floatValue, 0.0F));
  }
}

package bunnythekenzo.client;

public class CurveTween {
  public final float durationMillis;
  public final AnimationCurve animationCurve;
  public float startValue;
  public float targetValue;
  public long startedNanos;

  public CurveTween(float floatValue, float floatValue2) {
    this(floatValue, floatValue2, AnimationCurve.EASE_OUT_CUBIC);
  }

  public CurveTween(float amount4, float amount22, AnimationCurve animationCurve2) {
    this.durationMillis = amount4;
    this.animationCurve = animationCurve2;
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
  }

  public float getTarget() {
    return this.targetValue;
  }

  public float getValue() {
    float floatValue =
        (float) (System.nanoTime() - this.startedNanos) / 1000000.0F / this.durationMillis;
    return floatValue >= 1.0F
        ? this.targetValue
        : this.startValue
            + (this.targetValue - this.startValue)
                * this.animationCurve.computeFloat(Math.max(floatValue, 0.0F));
  }

  public boolean isFinished() {
    return (float) (System.nanoTime() - this.startedNanos) / 1000000.0F >= this.durationMillis;
  }
}

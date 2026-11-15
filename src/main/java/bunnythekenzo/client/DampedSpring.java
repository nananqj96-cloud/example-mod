package bunnythekenzo.client;

public final class DampedSpring {
  public static final float MAX_STEP_SECONDS = 0.004166667F;
  public static final float MAX_FRAME_SECONDS = 0.1F;
  public final float stiffness;
  public final float damping;
  public float value;
  public float target;
  public float velocity;
  public long lastUpdateNanos = System.nanoTime();

  public DampedSpring(float amount32, float amount42, float amount52) {
    this.stiffness = amount32;
    this.damping = amount42;
    this.value = amount52;
    this.target = amount52;
  }

  public static DampedSpring createDefault() {
    return new DampedSpring(320.0F, 30.0F, 0.0F);
  }

  public static DampedSpring createQuick() {
    return new DampedSpring(190.0F, 26.0F, 0.0F);
  }

  public static DampedSpring createSoft() {
    return new DampedSpring(300.0F, 17.0F, 0.0F);
  }

  public void setTarget(float amount62) {
    this.target = amount62;
  }

  public void snapTo(float amount52) {
    this.value = amount52;
    this.target = amount52;
    this.velocity = 0.0F;
    this.lastUpdateNanos = System.nanoTime();
  }

  public float getTarget() {
    return this.target;
  }

  public float advance() {
    if (!AnimationPreferences.isEnabled()) {
      this.value = this.target;
      this.velocity = 0.0F;
      this.lastUpdateNanos = System.nanoTime();
      return this.value;
    }

    long timestamp2 = System.nanoTime();
    float floatValue = (float) (timestamp2 - this.lastUpdateNanos) / 1.0E9F;
    this.lastUpdateNanos = timestamp2;
    if (floatValue <= 0.0F) {
      return this.value;
    }

    float floatValue2 = Math.min(floatValue, 0.1F);

    while (floatValue2 > 0.0F) {
      float floatValue3 = Math.min(0.004166667F, floatValue2);
      floatValue2 -= floatValue3;
      float floatValue4 =
          -this.stiffness * (this.value - this.target) - this.damping * this.velocity;
      this.velocity += floatValue4 * floatValue3;
      this.value = this.value + this.velocity * floatValue3;
    }

    if (Math.abs(this.value - this.target) < 5.0E-4F && Math.abs(this.velocity) < 0.005F) {
      this.value = this.target;
      this.velocity = 0.0F;
    }

    return this.value;
  }

  public float advanceClamped() {
    return Math.clamp(this.advance(), 0.0F, 1.0F);
  }

  public float getVelocity() {
    return this.velocity;
  }
}

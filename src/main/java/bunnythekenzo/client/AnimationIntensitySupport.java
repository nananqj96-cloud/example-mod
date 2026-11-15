package bunnythekenzo.client;

public final class AnimationIntensitySupport {
  public final DampedSpring dampedSpring = DampedSpring.createDefault();
  public final DampedSpring dampedSpring2 = new DampedSpring(560.0F, 38.0F, 0.0F);
  public boolean enabled;
  public boolean enabled2;
  public final AnimationIntensity animationIntensity;

  public AnimationIntensitySupport() {
    this(AnimationIntensity.NORMAL);
  }

  public AnimationIntensitySupport(AnimationIntensity animationIntensity2) {
    this.animationIntensity = animationIntensity2;
  }

  public float computeFloat(boolean bl) {
    if (bl && !this.enabled) {
      switch (this.animationIntensity.ordinal()) {
        case 0:
          {
            ClientAudio.update3();
            break;
          }
        case 1:
          {
            ClientAudio.update4();
            break;
          }
      }
    }
    this.enabled = bl;
    this.dampedSpring.setTarget(bl ? 1.0f : 0.0f);
    return this.dampedSpring.advanceClamped();
  }

  public float computeFloat() {
    return this.dampedSpring.advanceClamped();
  }

  public void apply(boolean enabled22) {
    this.enabled2 = enabled22;
    this.dampedSpring2.setTarget(enabled22 ? 1.0F : 0.0F);
  }

  public float computeFloat2() {
    return this.dampedSpring2.advanceClamped();
  }

  public float computeFloat(float floatValue) {
    return 1.0F - this.computeFloat2() * floatValue;
  }

  public void update() {
    this.enabled = false;
    this.enabled2 = false;
    this.dampedSpring.setTarget(0.0F);
    this.dampedSpring2.setTarget(0.0F);
  }
}

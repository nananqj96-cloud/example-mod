package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class SmoothScrollState {
  public static final float amount = 22.0F;
  public static final float amount2 = 3.5F;
  public static final float amount3 = 46.0F;
  public final DampedSpring dampedSpring = DampedSpring.createQuick();
  public final TweenValue tweenValue = new TweenValue(320.0F, 0.0F);
  public float amount4;
  public long timestamp;

  public void apply(float floatValue, float floatValue2) {
    this.amount4 = Math.max(0.0F, floatValue - floatValue2);
    if (this.dampedSpring.getTarget() > this.amount4) {
      this.dampedSpring.setTarget(this.amount4);
    }

    boolean flag = System.nanoTime() - this.timestamp < 900000000L;
    this.tweenValue.animateTo(this.amount4 > 0.0F && flag ? 1.0F : 0.0F);
  }

  public boolean test(double doubleValue) {
    if (this.amount4 <= 0.0F) {
      return false;
    } else {
      float amount6 = this.dampedSpring.getTarget();
      float floatValue = Math.clamp(amount6 - (float) doubleValue * 46.0F, 0.0F, this.amount4);
      this.timestamp = System.nanoTime();
      if (floatValue == amount6) {
        ClientAudio.update9();
        return false;
      } else {
        this.dampedSpring.setTarget(floatValue);
        return true;
      }
    }
  }

  public void update() {
    if (this.amount4 > 0.0F) {
      this.timestamp = System.nanoTime();
    }
  }

  public float computeFloat() {
    return this.dampedSpring.advance();
  }

  public void update2() {
    this.dampedSpring.snapTo(0.0F);
    this.tweenValue.snapTo(0.0F);
  }

  public static float computeFloat(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    float floatValue5 = Math.clamp((floatValue2 - floatValue3) / 22.0F, 0.0F, 1.0F);
    float floatValue6 = Math.clamp((floatValue4 - floatValue) / 22.0F, 0.0F, 1.0F);
    return Math.min(floatValue5, floatValue6);
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    float value = this.tweenValue.getValue();
    if (!(value <= 0.01F) && !(this.amount4 <= 0.0F)) {
      ColorPalette instance = ColorPalette.getInstance();
      float floatValue4 = floatValue - 3.5F - 3.0F;
      float floatValue5 =
          Math.max(28.0F, floatValue3 * (floatValue3 / (floatValue3 + this.amount4)));
      float floatValue6 = floatValue3 - floatValue5;
      float floatValue7 =
          floatValue2
              + floatValue6 * Math.clamp(this.dampedSpring.advance() / this.amount4, 0.0F, 1.0F);
      fontManager.render(
          floatValue4,
          floatValue2 + 2.0F,
          3.5F,
          floatValue3 - 4.0F,
          1.75F,
          ArgbColors.withOpacity(-1, 0.05F * value));
      fontManager.render(
          floatValue4,
          floatValue7,
          3.5F,
          floatValue5,
          1.75F,
          ArgbColors.withOpacity(instance.computeInt(), 0.72F * value));
    }
  }
}

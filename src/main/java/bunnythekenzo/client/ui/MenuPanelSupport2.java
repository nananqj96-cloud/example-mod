package bunnythekenzo.client.ui;

import java.util.function.LongSupplier;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.Easing;

public final class MenuPanelSupport2 {
  public static final float amount = 360.0F;
  public static final float amount2 = 26.0F;
  public static final float amount3 = 130.0F;
  public static final float amount4 = 210.0F;
  public final LongSupplier longSupplier;
  public long timestamp;
  public long timestamp2;
  public boolean enabled;
  public boolean enabled2;
  public boolean enabled3;
  public float amount5;
  public float amount6 = 1.0F;

  public MenuPanelSupport2() {
    this(System::nanoTime);
  }

  public MenuPanelSupport2(LongSupplier longSupplier2) {
    this.longSupplier = longSupplier2;
  }

  public void update() {
    long asLong = this.longSupplier.getAsLong();
    if (!this.enabled) {
      this.enabled = true;
      this.timestamp = asLong;
    }

    if (!this.enabled2) {
      this.amount5 =
          AnimationPreferences.isEnabled()
              ? Math.max(this.amount5, (float) (asLong - this.timestamp) / 1000000.0F)
              : 490.0F;
    } else {
      float floatValue =
          AnimationPreferences.isEnabled()
              ? Math.clamp((float) (asLong - this.timestamp2) / 1000000.0F / 210.0F, 0.0F, 1.0F)
              : 1.0F;
      this.enabled3 |= floatValue >= 1.0F;
      this.amount6 = this.enabled3 ? 0.0F : 1.0F - Easing.IN_QUAD.computeFloat(floatValue);
    }
  }

  public float computeFloat(float floatValue) {
    if (!AnimationPreferences.isEnabled()) {
      return this.enabled2 ? 0.0F : 1.0F;
    } else {
      return Easing.OUT_QUART.computeFloat(
              Math.clamp((this.amount5 - floatValue) / 360.0F, 0.0F, 1.0F))
          * this.amount6;
    }
  }

  public void update2() {
    if (!this.enabled2) {
      this.enabled2 = true;
      this.timestamp2 = this.longSupplier.getAsLong();
      this.enabled3 = !this.enabled || !AnimationPreferences.isEnabled();
      if (this.enabled3) {
        this.amount6 = 0.0F;
      }
    }
  }

  public boolean test() {
    return this.enabled2
        && (this.enabled3
            || !AnimationPreferences.isEnabled()
            || (float) (this.longSupplier.getAsLong() - this.timestamp2) / 1000000.0F >= 210.0F);
  }
}

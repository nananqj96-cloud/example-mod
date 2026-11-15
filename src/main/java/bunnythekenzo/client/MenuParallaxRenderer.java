package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class MenuParallaxRenderer {
  public static final float amount = 7.0F;
  public static final float amount2 = 4.0F;
  public static final float amount3 = 0.55F;
  public static final float amount4 = 0.1F;
  public final MenuBackgroundRenderer menuBackgroundRenderer = new MenuBackgroundRenderer();
  public final MenuLogoRenderer menuLogoRenderer = new MenuLogoRenderer();
  public final DampedSpring dampedSpring = new DampedSpring(190.0F, 26.0F, 0.0F);
  public float amount5;
  public float amount6;
  public float amount7 = 0.55F;
  public long timestamp = System.nanoTime();
  public boolean enabled;

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    if (GuiPreferences.getGuiPreferences().test3()) {
      float value = this.computeFloat();
      boolean flag =
          floatValue5 >= floatValue
              && floatValue5 <= floatValue + floatValue3
              && floatValue6 >= floatValue2
              && floatValue6 <= floatValue2 + floatValue4;
      this.render(
          value,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          flag ? floatValue5 : Float.NaN,
          flag ? floatValue6 : Float.NaN);
      this.dampedSpring.setTarget(floatValue7);
      float value2 = this.dampedSpring.advance();
      this.menuLogoRenderer.render(
          fontManager,
          floatValue + value2,
          floatValue2,
          floatValue3 - value2,
          floatValue4,
          this.amount5,
          this.amount6,
          this.amount7);
      this.menuBackgroundRenderer.render(
          fontManager,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          this.amount5,
          this.amount6,
          this.amount7);
    }
  }

  public float computeFloat() {
    long timestamp2 = System.nanoTime();
    float floatValue = (float) (timestamp2 - this.timestamp) / 1.0E9F;
    this.timestamp = timestamp2;
    return Math.clamp(floatValue, 0.0F, 0.1F);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    boolean flag = !Float.isNaN(floatValue6);
    float amount52;
    float amount62;
    if (flag) {
      amount52 = floatValue6;
      amount62 = floatValue7;
    } else if (AnimationPreferences.isEnabled()) {
      amount52 = floatValue2 + floatValue4 * computeFloat(0.13, 0.0, 0.5F, 0.26F);
      amount62 = floatValue3 + floatValue5 * computeFloat(0.089, 1.7, 0.45F, 0.24F);
    } else {
      amount52 = floatValue2 + floatValue4 * 0.5F;
      amount62 = floatValue3 + floatValue5 * 0.45F;
    }

    if (this.enabled && AnimationPreferences.isEnabled()) {
      float floatValue8 = 1.0F - (float) Math.exp(-floatValue * 7.0F);
      this.amount5 = this.amount5 + (amount52 - this.amount5) * floatValue8;
      this.amount6 = this.amount6 + (amount62 - this.amount6) * floatValue8;
      float floatValue9 = flag ? 1.0F : 0.55F;
      this.amount7 =
          this.amount7
              + (floatValue9 - this.amount7) * (1.0F - (float) Math.exp(-floatValue * 4.0F));
    } else {
      this.enabled = true;
      this.amount5 = amount52;
      this.amount6 = amount62;
      this.amount7 = flag ? 1.0F : 0.55F;
    }
  }

  public static float computeFloat(
      double doubleValue, double doubleValue2, float floatValue, float floatValue2) {
    double doubleValue3 = System.nanoTime() % 600000000000L / 1.0E9;
    return floatValue + floatValue2 * (float) Math.sin(doubleValue3 * doubleValue + doubleValue2);
  }
}

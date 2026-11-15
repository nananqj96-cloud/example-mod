package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;

public final class ToggleSwitchAnimation {
  public final DampedSpring dampedSpring = DampedSpring.createSoft();
  public final DampedSpring dampedSpring2 = DampedSpring.createQuick();
  public final ClickRippleEffects clickRippleEffects = new ClickRippleEffects();
  public boolean enabled;
  public boolean enabled2;

  public ToggleSwitchAnimation(boolean enabled3) {
    this.enabled = enabled3;
    this.dampedSpring.snapTo(enabled3 ? 1.0F : 0.0F);
    this.dampedSpring2.snapTo(enabled3 ? 1.0F : 0.0F);
  }

  public void apply(boolean enabled3) {
    if (enabled3 != this.enabled) {
      this.enabled = enabled3;
      this.dampedSpring.setTarget(enabled3 ? 1.0F : 0.0F);
      this.dampedSpring2.setTarget(enabled3 ? 1.0F : 0.0F);
      this.enabled2 = true;
    }
  }

  public void apply2(boolean flag) {
    if (flag != this.enabled) {
      this.apply(flag);
    }
  }

  public void apply3(boolean enabled3) {
    this.enabled = enabled3;
    this.dampedSpring.snapTo(enabled3 ? 1.0F : 0.0F);
    this.dampedSpring2.snapTo(enabled3 ? 1.0F : 0.0F);
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    this.render(
        fontManager,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        ColorPalette.getInstance().computeInt());
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue) {
    ColorPalette.getInstance();
    float floatValue6 = floatValue4 / 2.0F;
    float floatValue7 = floatValue3 - floatValue4;
    float value = this.dampedSpring.advance();
    float floatValue8 = Math.clamp(this.dampedSpring2.advance(), 0.0F, 1.0F);
    float floatValue9 = floatValue + floatValue6 + floatValue7 * Math.clamp(value, 0.0F, 1.0F);
    float floatValue10 = floatValue6 - 3.0F;
    if (this.enabled2) {
      this.clickRippleEffects.render(floatValue9, floatValue2 + floatValue6, floatValue6 * 2.6F);
      this.enabled2 = false;
    }

    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue6,
        ArgbColors.withOpacity(-14015692, 0.95F));
    if (floatValue8 > 0.01F) {
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue6,
          ArgbColors.withOpacity(intValue, floatValue8));
    }

    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue6,
        1.0F,
        ArgbColors.lerp(
            ArgbColors.withOpacity(-1, 0.1F + 0.06F * floatValue5),
            ArgbColors.withOpacity(intValue, 0.7F),
            floatValue8));
    this.clickRippleEffects.apply(
        value2 -> {
          fontManager.render2(
              ((bunnythekenzo.client.CollectionData5) (value2)).amount(),
              ((bunnythekenzo.client.CollectionData5) (value2)).amount2(),
              ((bunnythekenzo.client.CollectionData5) (value2)).amount3(),
              1.2F,
              ArgbColors.withOpacity(
                  intValue, 0.42F * ((bunnythekenzo.client.CollectionData5) (value2)).amount4()));
        });
    float floatValue11 =
        Math.clamp(Math.abs(this.dampedSpring.getVelocity()) * 0.055F, 0.0F, 0.32F);
    float floatValue12 = floatValue10 * (1.0F + floatValue11);
    float floatValue13 = floatValue10 * (1.0F - floatValue11 * 0.55F);
    fontManager.render(
        floatValue9 - floatValue10,
        floatValue2 + floatValue6 - floatValue10,
        floatValue10 * 2.0F,
        floatValue10 * 2.0F,
        floatValue10,
        3.0F,
        1.2F,
        ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.update2();
    fontManager.apply(floatValue9, floatValue2 + floatValue6);
    fontManager.apply2(floatValue12 / floatValue10, floatValue13 / floatValue10);
    fontManager.render2(
        0.0F,
        0.0F,
        floatValue10,
        ArgbColors.lerp(-7633766, -1, floatValue8 * 0.85F + floatValue5 * 0.15F));
    fontManager.update3();
  }
}

package bunnythekenzo.client.setting;

import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport7 extends SettingWidget {
  public static final float amount = 4.0F;
  public static final float amount2 = 6.0F;
  public final NumberSettingView numberSettingView;
  public final DampedSpring dampedSpring;
  public final DampedSpring dampedSpring2 = DampedSpring.createSoft();
  public boolean enabled;

  public SettingWidgetSupport7(NumberSettingView numberSettingView2) {
    super(numberSettingView2);
    this.numberSettingView = numberSettingView2;
    this.dampedSpring = new DampedSpring(760.0F, 48.0F, numberSettingView2.getFraction());
  }

  @Override
  public float computeFloat() {
    return 52.0F;
  }

  @Override
  public float computeFloat2() {
    return this.amount3 + 15.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    float floatValue4 = Math.max(floatValue, this.enabled ? 1.0F : 0.0F);
    this.dampedSpring2.setTarget(floatValue4);
    fontManager.computeFloat3(
        this.numberSettingView.getFormattedValue(),
        this.amount2 + this.amount4 - 12.0F,
        this.amount3 + 15.0F,
        11.0F,
        ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt(), floatValue4));
    float floatValue5 = this.amount2 + 12.0F;
    float floatValue6 = this.amount4 - 24.0F;
    float floatValue7 = this.amount3 + 36.0F - 2.0F;
    float floatValue8 = Math.clamp(this.dampedSpring.advance(), 0.0F, 1.0F);
    float floatValue9 = floatValue5 + floatValue6 * floatValue8;
    fontManager.render(
        floatValue5, floatValue7, floatValue6, 4.0F, 2.0F, ArgbColors.withOpacity(-1, 0.07F));
    if (floatValue8 > 0.001F) {
      fontManager.render(
          floatValue5,
          floatValue7,
          floatValue6 * floatValue8,
          4.0F,
          2.0F,
          ArgbColors.lerp(
              colorPalette.computeInt(),
              ArgbColors.lighten(colorPalette.computeInt(), 0.16F),
              floatValue4));
    }

    float floatValue10 = 6.0F + this.dampedSpring2.advanceClamped() * 1.8F;
    fontManager.render(
        floatValue9 - floatValue10,
        this.amount3 + 36.0F - floatValue10,
        floatValue10 * 2.0F,
        floatValue10 * 2.0F,
        floatValue10,
        4.0F,
        1.5F,
        ArgbColors.withOpacity(-16777216, 0.45F));
    fontManager.render2(floatValue9, this.amount3 + 36.0F, floatValue10, -1);
    fontManager.render2(
        floatValue9, this.amount3 + 36.0F, floatValue10 * 0.4F, colorPalette.computeInt());
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    }

    this.enabled = true;
    this.animationIntensitySupport.apply(true);
    this.apply(floatValue);
    return true;
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.enabled) {
      this.apply(floatValue);
    }
  }

  @Override
  public void update5() {
    this.enabled = false;
    super.update5();
  }

  public void apply(float floatValue) {
    float floatValue2 = this.amount2 + 12.0F;
    float floatValue3 = this.amount4 - 24.0F;
    float value = this.numberSettingView.getValue();
    this.numberSettingView.setFraction((floatValue - floatValue2) / floatValue3);
    this.dampedSpring.setTarget(this.numberSettingView.getFraction());
    if (this.numberSettingView.getValue() != value) {
      ClientAudio.apply2(this.numberSettingView.getFraction());
    }
  }
}

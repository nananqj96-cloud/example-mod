package bunnythekenzo.client.setting;

import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.KeyNames;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport6 extends SettingWidget {
  public static final float amount = 22.0F;
  public static final float amount2 = 46.0F;
  public final KeybindSettingView toggleSettingViewSupport;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public boolean enabled;
  public float amount3;
  public float amount4 = 46.0F;

  public SettingWidgetSupport6(KeybindSettingView toggleSettingViewSupport2) {
    super(toggleSettingViewSupport2);
    this.toggleSettingViewSupport = toggleSettingViewSupport2;
  }

  @Override
  public float computeFloat() {
    return 42.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    String text =
        this.enabled
            ? "Press…"
            : (this.toggleSettingViewSupport.test3()
                ? KeyNames.getString(this.toggleSettingViewSupport.computeInt())
                : "None");
    this.amount4 = Math.max(46.0F, fontManager.computeFloat(text, 9.5F) + 16.0F);
    this.amount3 = this.amount2 + this.amount4 - 12.0F - this.amount4;
    float floatValue4 = this.amount3 + (this.amount5 - 22.0F) / 2.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue2, floatValue3));
    float floatValue5 =
        this.enabled ? 0.55F + 0.45F * (float) Math.sin(System.nanoTime() / 1.0E9 * 4.5) : 1.0F;
    int intValue =
        this.enabled
            ? ArgbColors.withOpacity(colorPalette.computeInt(), 0.2F * floatValue5)
            : ArgbColors.withOpacity(-1, 0.05F + 0.06F * value);
    int intValue2 =
        this.enabled
            ? ArgbColors.withOpacity(colorPalette.computeInt(), 0.5F + 0.4F * floatValue5)
            : ArgbColors.withOpacity(-1, 0.1F + 0.18F * value);
    fontManager.render(this.amount3, floatValue4, this.amount4, 22.0F, 6.0F, intValue);
    fontManager.render(this.amount3, floatValue4, this.amount4, 22.0F, 6.0F, 1.0F, intValue2);
    fontManager.computeFloat2(
        text,
        this.amount3 + this.amount4 / 2.0F,
        this.amount3 + this.amount5 / 2.0F,
        9.5F,
        this.enabled
            ? colorPalette.computeInt()
            : (this.toggleSettingViewSupport.test3()
                ? ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), value)
                : colorPalette.computeInt19()));
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount3 + (this.amount5 - 22.0F) / 2.0F;
    return floatValue >= this.amount3 - 4.0F
        && floatValue <= this.amount3 + this.amount4 + 4.0F
        && floatValue2 >= floatValue3 - 4.0F
        && floatValue2 <= floatValue3 + 22.0F + 4.0F;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    } else if (this.enabled && this.test(floatValue, floatValue2) && intValue != 0) {
      this.toggleSettingViewSupport.apply(intValue);
      this.enabled = false;
      ClientAudio.update5();
      return true;
    } else {
      this.enabled = !this.enabled;
      this.animationIntensitySupport.apply(true);
      ClientAudio.update5();
      return true;
    }
  }

  @Override
  public boolean test2(int intValue) {
    if (!this.enabled) {
      return false;
    }

    this.toggleSettingViewSupport.apply(intValue == 256 ? -1 : intValue);
    this.enabled = false;
    ClientAudio.update5();
    return true;
  }

  @Override
  public boolean test2() {
    return this.enabled;
  }

  @Override
  public void update6() {
    this.enabled = false;
  }
}

package bunnythekenzo.client.ui;

import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;

public abstract class MenuPanel {
  public final ThemeManager themeManager;
  public static final float amount = 210.0F;
  public static final float amount2 = 38.0F;
  public static final float amount3 = 12.0F;
  public static final float amount4 = 6.0F;
  public static final float amount5 = 16.0F;

  public final PanelLayoutSupport panelLayoutSupport;
  public final TweenValue tweenValue;
  public final TweenValue tweenValue2 = new TweenValue(200.0F, 0.0F);
  public float amount6;
  public boolean enabled;
  public final TweenValue tweenValue3 = new TweenValue(190.0F, 0.0F, Easing.OUT_QUART);
  public float amount7 = Float.MAX_VALUE;

  public void setAmount7(float amount72) {
    this.amount7 = amount72;
  }

  public boolean test() {
    return this.panelLayoutSupport.enabled;
  }

  public void apply(boolean flag) {
    if (this.panelLayoutSupport.enabled != flag) {
      this.update3();
    }
  }

  public float computeFloat() {
    return this.tweenValue2.getValue();
  }

  public void apply2(float floatValue) {
    this.tweenValue2.animateTo(Math.clamp(floatValue, 0.0F, this.amount6));
  }

  public MenuPanel(ThemeManager themeManager2, PanelLayoutSupport panelLayoutSupport2) {
    this.themeManager = themeManager2;
    this.panelLayoutSupport = panelLayoutSupport2;
    this.tweenValue =
        new TweenValue(280.0F, panelLayoutSupport2.enabled ? 0.0F : 1.0F, Easing.OUT_QUART);
  }

  public ColorPalette getColorPalette() {
    return ColorPalette.getInstance();
  }

  public float computeFloat2() {
    return this.panelLayoutSupport.amount;
  }

  public float computeFloat3() {
    return this.panelLayoutSupport.amount2;
  }

  public void apply2(float amount8, float amount22) {
    this.panelLayoutSupport.amount = amount8;
    this.panelLayoutSupport.amount2 = amount22;
  }

  public void update3() {
    this.panelLayoutSupport.enabled = !this.panelLayoutSupport.enabled;
    this.tweenValue.animateTo(this.panelLayoutSupport.enabled ? 0.0F : 1.0F);
  }

  public abstract String getString();

  public int computeInt() {
    return -1;
  }

  public abstract float computeFloat(FontManager fontManager);

  public abstract void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5);

  public float computeFloat3(float floatValue) {
    return Math.max(
        60.0F,
        Math.min(this.amount7, floatValue - this.panelLayoutSupport.amount2 - 38.0F - 64.0F));
  }

  public float computeFloat(FontManager fontManager, float floatValue) {
    return Math.min(this.computeFloat(fontManager), this.computeFloat3(floatValue))
        * this.tweenValue.getValue();
  }

  public float computeFloat2(FontManager fontManager, float floatValue) {
    return 38.0F + this.computeFloat(fontManager, floatValue);
  }

  public float computeFloat(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    float floatValue5 = Math.clamp((floatValue2 - floatValue3) / 16.0F, 0.0F, 1.0F);
    float floatValue6 = Math.clamp((floatValue4 - floatValue) / 16.0F, 0.0F, 1.0F);
    return Math.min(floatValue5, floatValue6);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.tweenValue.animateTo(this.panelLayoutSupport.enabled ? 0.0F : 1.0F);
    if (!AnimationPreferences.isEnabled()) {
      this.tweenValue.snapTo(this.tweenValue.getTarget());
      this.tweenValue2.snapTo(this.tweenValue2.getTarget());
    }

    this.panelLayoutSupport.amount =
        Math.clamp(this.panelLayoutSupport.amount, -170.0F, floatValue3 - 40.0F);
    this.panelLayoutSupport.amount2 =
        Math.clamp(this.panelLayoutSupport.amount2, 0.0F, floatValue4 - 38.0F);
    ColorPalette colorPalette = this.getColorPalette();
    float value = this.computeFloat(fontManager, floatValue4);
    boolean flag = value > 0.5F;
    boolean enabled2 = this.test(floatValue, floatValue2);
    if (enabled2 && !this.enabled) {
      ClientAudio.update3();
    }

    this.enabled = enabled2;
    this.tweenValue3.animateTo(enabled2 ? 1.0F : 0.0F);
    fontManager.render2(
        this.panelLayoutSupport.amount,
        this.panelLayoutSupport.amount2,
        210.0F,
        38.0F + value,
        12.0F,
        14.0F,
        ArgbColors.withOpacity(-16777216, 0.35F));
    if (flag) {
      fontManager.render(
          this.panelLayoutSupport.amount,
          this.panelLayoutSupport.amount2 + 38.0F,
          210.0F,
          value,
          0.0F,
          0.0F,
          12.0F,
          12.0F,
          colorPalette.computeInt7(),
          colorPalette.computeInt8());
    }

    float floatValue5 = 12.0F * (1.0F - Math.clamp(value / 12.0F, 0.0F, 1.0F));
    fontManager.render(
        this.panelLayoutSupport.amount,
        this.panelLayoutSupport.amount2,
        210.0F,
        38.0F,
        12.0F,
        12.0F,
        floatValue5,
        floatValue5,
        colorPalette.computeInt5(),
        colorPalette.computeInt6());
    fontManager.render(
        this.panelLayoutSupport.amount,
        this.panelLayoutSupport.amount2,
        210.0F,
        38.0F,
        12.0F,
        12.0F,
        floatValue5,
        floatValue5,
        colorPalette.computeInt(0.08F * this.tweenValue3.getValue()),
        colorPalette.computeInt(0.025F * this.tweenValue3.getValue()));
    if (flag) {
      fontManager.render(
          this.panelLayoutSupport.amount + 1.0F,
          this.panelLayoutSupport.amount2 + 38.0F - 1.5F,
          208.0F,
          1.5F,
          0.75F,
          ArgbColors.withOpacity(colorPalette.getCount(), 0.85F),
          ArgbColors.withOpacity(colorPalette.computeInt(), 0.5F),
          false);
    }

    float floatValue6 = this.panelLayoutSupport.amount + 12.0F;
    int value2 = this.computeInt();
    if (value2 > 0) {
      fontManager.render(
          value2,
          this.panelLayoutSupport.amount + 11.0F,
          this.panelLayoutSupport.amount2 + 19.0F - 9.0F,
          18.0F,
          18.0F,
          colorPalette.computeInt());
      floatValue6 = this.panelLayoutSupport.amount + 36.0F;
    }

    fontManager.computeFloat(
        this.getString(),
        floatValue6,
        this.panelLayoutSupport.amount2 + 19.0F,
        16.5F,
        colorPalette.computeInt16());
    float floatValue7 = this.panelLayoutSupport.amount + 210.0F - 16.0F;
    float floatValue8 = this.panelLayoutSupport.amount2 + 19.0F;
    fontManager.update2();
    fontManager.apply(floatValue7, floatValue8);
    fontManager.apply2((float) (this.tweenValue.getValue() * Math.PI / 2.0));
    fontManager.render(0.0F, 0.0F, 4.5F, 1.8F, colorPalette.computeInt18(), false);
    fontManager.update3();
    if (flag) {
      float floatValue9 = this.panelLayoutSupport.amount2 + 38.0F;
      float floatValue10 = floatValue9 + value;
      this.amount6 =
          Math.max(0.0F, this.computeFloat(fontManager) - this.computeFloat3(floatValue4));
      this.tweenValue2.animateTo(Math.clamp(this.tweenValue2.getTarget(), 0.0F, this.amount6));
      fontManager.update2();
      fontManager.render(this.panelLayoutSupport.amount, floatValue9, 210.0F, value);
      this.render(
          fontManager,
          floatValue9 + 6.0F - this.tweenValue2.getValue(),
          floatValue,
          floatValue2,
          floatValue9,
          floatValue10);
      fontManager.update3();
    }
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.panelLayoutSupport.amount
        && floatValue <= this.panelLayoutSupport.amount + 210.0F
        && floatValue2 >= this.panelLayoutSupport.amount2
        && floatValue2 <= this.panelLayoutSupport.amount2 + 38.0F;
  }

  public boolean test(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    float value = this.computeFloat(fontManager, floatValue3);
    return floatValue >= this.panelLayoutSupport.amount
        && floatValue <= this.panelLayoutSupport.amount + 210.0F
        && floatValue2 >= this.panelLayoutSupport.amount2 + 38.0F
        && floatValue2 <= this.panelLayoutSupport.amount2 + 38.0F + value;
  }

  public void apply(double doubleValue) {
    if (!(this.amount6 <= 0.0F)) {
      this.tweenValue2.animateTo(
          Math.clamp(
              this.tweenValue2.getTarget() - (float) doubleValue * 38.0F, 0.0F, this.amount6));
    }
  }

  public boolean test(float floatValue, float floatValue2, int intValue) {
    return false;
  }

  public void apply(float floatValue, float floatValue2) {}

  public void update2() {}

  public boolean test2(int intValue) {
    return false;
  }

  public boolean test(int intValue) {
    return false;
  }

  public boolean test2() {
    return false;
  }
}

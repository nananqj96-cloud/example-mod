package bunnythekenzo.client.setting;

import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport2 extends SettingWidget {
  public static final float amount = 34.0F;
  public static final float amount2 = 20.0F;
  public static final float amount3 = 232.0F;
  public static final float amount4 = 12.0F;
  public static final float amount5 = 118.0F;
  public static final float amount6 = 12.0F;
  public static final float amount7 = 26.0F;
  public static final float amount8 = 18.0F;
  public static final float amount9 = 234.0F;
  public static final float amount10 = 46.0F;
  public static final int[] countArray =
      new int[] {-40008, -41862, -26020, -11930, -10690144, -11744784, -9663233, -3634177};
  public final ColorSettingView toggleSettingViewSupport2;
  public final Runnable runnable;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final TweenValue tweenValue = new TweenValue(220.0F, 0.0F, Easing.OUT_EXPO);
  public boolean enabled;
  public float amount11;
  public float amount12;
  public float amount13;
  public boolean enabled2;
  public boolean enabled3;
  public float amount14;
  public float amount15;

  public SettingWidgetSupport2(ColorSettingView toggleSettingViewSupport22) {
    this(
        toggleSettingViewSupport22,
        () -> {
          GuiPreferences.getGuiPreferences().update();
        });
  }

  public SettingWidgetSupport2(ColorSettingView toggleSettingViewSupport22, Runnable runnable2) {
    super(toggleSettingViewSupport22);
    this.toggleSettingViewSupport2 = toggleSettingViewSupport22;
    this.runnable = runnable2;
    this.update();
  }

  public void update() {
    float[] amountArray = ArgbColors.toHsv(this.toggleSettingViewSupport2.computeInt());
    this.amount11 = amountArray[0];
    this.amount12 = amountArray[1];
    this.amount13 = amountArray[2];
  }

  public void update2() {
    this.toggleSettingViewSupport2.apply(
        ArgbColors.fromHsv(this.amount11, this.amount12, this.amount13));
    this.runnable.run();
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
    float floatValue4 = this.amount2 + this.amount4 - 12.0F - 34.0F;
    float floatValue5 = this.amount3 + (this.amount5 - 20.0F) / 2.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue2, floatValue3));
    fontManager.computeFloat3(
        "#" + ArgbColors.toHex(this.toggleSettingViewSupport2.computeInt()),
        floatValue4 - 12.0F,
        this.amount3 + this.amount5 / 2.0F,
        11.0F,
        colorPalette.computeInt18());
    fontManager.render(
        floatValue4,
        floatValue5,
        34.0F,
        20.0F,
        6.0F,
        5.0F,
        1.5F,
        ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.render(
        floatValue4, floatValue5, 34.0F, 20.0F, 6.0F, this.toggleSettingViewSupport2.computeInt());
    fontManager.render(
        floatValue4,
        floatValue5,
        34.0F,
        20.0F,
        6.0F,
        1.0F,
        ArgbColors.withOpacity(-1, 0.18F + 0.22F * Math.max(value, this.enabled ? 1.0F : 0.0F)));
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount2 + this.amount4 - 12.0F - 34.0F;
    float floatValue4 = this.amount3 + (this.amount5 - 20.0F) / 2.0F;
    return floatValue >= floatValue3 - 4.0F
        && floatValue <= floatValue3 + 34.0F + 4.0F
        && floatValue2 >= floatValue4 - 6.0F
        && floatValue2 <= floatValue4 + 20.0F + 6.0F;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    }

    this.enabled = !this.enabled;
    this.tweenValue.animateTo(this.enabled ? 1.0F : 0.0F);
    if (this.enabled) {
      this.update();
    }

    ClientAudio.update5();
    return true;
  }

  @Override
  public boolean test() {
    return this.enabled || this.tweenValue.getValue() > 0.01F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    boolean flag = this.amount3 + this.amount5 + 6.0F + 234.0F <= floatValue4;
    this.amount14 = this.amount2 + this.amount4 - 12.0F - 232.0F;
    this.amount15 = flag ? this.amount3 + this.amount5 + 6.0F : this.amount3 - 6.0F - 234.0F;
    this.render(fontManager, floatValue, floatValue2, flag, true);
  }

  public static float computeFloat3() {
    return 232.0F;
  }

  public static float computeFloat4() {
    return 234.0F;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void update3() {
    this.enabled = true;
    this.update();
    this.tweenValue.animateTo(1.0F);
    ClientAudio.update5();
  }

  public void update4() {
    this.enabled = false;
    this.tweenValue.animateTo(0.0F);
    this.update5();
  }

  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float amount142,
      float amount152) {
    this.amount14 = amount142;
    this.amount15 = amount152;
    this.render(fontManager, floatValue, floatValue2, true, false);
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, boolean flag, boolean flag2) {
    float value = this.tweenValue.getValue();
    if (!(value <= 0.01F)) {
      ColorPalette instance = ColorPalette.getInstance();
      fontManager.update2();
      fontManager.apply3(value);
      if (flag2) {
        fontManager.render2(
            this.amount14 + 232.0F - 17.0F,
            flag ? this.amount15 : this.amount15 + 234.0F,
            0.94F + 0.06F * value);
      }

      fontManager.render(
          this.amount14,
          this.amount15,
          232.0F,
          234.0F,
          12.0F,
          18.0F,
          6.0F,
          ArgbColors.withOpacity(-16777216, 0.55F));
      fontManager.render(
          this.amount14,
          this.amount15,
          232.0F,
          234.0F,
          12.0F,
          instance.computeInt5(),
          instance.computeInt6(),
          true);
      fontManager.render(
          this.amount14, this.amount15, 232.0F, 234.0F, 12.0F, 1.0F, instance.computeInt14());
      float floatValue3 = this.amount14 + 12.0F;
      this.render(fontManager, floatValue3, this.computeFloat5(), 208.0F);
      this.render2(fontManager, floatValue3, this.computeFloat6(), 208.0F);
      this.render(
          fontManager,
          instance,
          floatValue3,
          this.computeFloat7(),
          208.0F,
          floatValue,
          floatValue2);
      this.render(fontManager, floatValue3, this.computeFloat8(), 208.0F, floatValue, floatValue2);
      fontManager.update3();
    }
  }

  public float computeFloat5() {
    return this.amount15 + 12.0F;
  }

  public float computeFloat6() {
    return this.computeFloat5() + 118.0F + 12.0F;
  }

  public float computeFloat7() {
    return this.computeFloat6() + 12.0F + 12.0F;
  }

  public float computeFloat8() {
    return this.computeFloat7() + 26.0F + 12.0F;
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        118.0F,
        8.0F,
        ArgbColors.fromHsv(this.amount11, 1.0F, 1.0F));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        118.0F,
        8.0F,
        -1,
        ArgbColors.withOpacity(-1, 0.0F),
        false);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        118.0F,
        8.0F,
        ArgbColors.withOpacity(-16777216, 0.0F),
        -16777216,
        true);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        118.0F,
        8.0F,
        1.0F,
        ArgbColors.withOpacity(-1, 0.12F));
    float floatValue4 = floatValue + this.amount12 * floatValue3;
    float floatValue5 = floatValue2 + (1.0F - this.amount13) * 118.0F;
    fontManager.render2(
        floatValue4, floatValue5, 7.0F, 3.0F, ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.render2(floatValue4, floatValue5, 6.5F, 2.0F, -1);
  }

  public void render2(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    int[] intValues = new int[] {-65536, -256, -16711936, -16711681, -16776961, -65281, -65536};
    float floatValue4 = floatValue3 / 6.0F;

    for (int index = 0; index < 6; index++) {
      float floatValue5 = floatValue + floatValue4 * index;
      float floatValue6 = floatValue4 + (index < 5 ? 0.75F : 0.0F);
      float floatValue7 = index == 0 ? 6.0F : 0.0F;
      float floatValue8 = index == 0 ? 6.0F : 0.0F;
      float floatValue9 = index == 5 ? 6.0F : 0.0F;
      float floatValue10 = index == 5 ? 6.0F : 0.0F;
      fontManager.render(
          floatValue5,
          floatValue2,
          floatValue6,
          12.0F,
          floatValue7,
          floatValue9,
          floatValue10,
          floatValue8,
          intValues[index],
          intValues[index + 1],
          false);
    }

    fontManager.render(
        floatValue, floatValue2, floatValue3, 12.0F, 6.0F, 1.0F, ArgbColors.withOpacity(-1, 0.14F));
    float floatValue11 = floatValue + this.amount11 / 360.0F * floatValue3;
    fontManager.render2(
        floatValue11, floatValue2 + 6.0F, 8.5F, ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.render2(floatValue11, floatValue2 + 6.0F, 7.5F, -1);
    fontManager.render2(
        floatValue11, floatValue2 + 6.0F, 4.5F, ArgbColors.fromHsv(this.amount11, 1.0F, 1.0F));
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    fontManager.render(
        floatValue, floatValue2, 39.0F, 26.0F, 7.0F, this.toggleSettingViewSupport2.computeInt());
    fontManager.render(
        floatValue, floatValue2, 39.0F, 26.0F, 7.0F, 1.0F, ArgbColors.withOpacity(-1, 0.16F));
    fontManager.computeFloat(
        "#" + ArgbColors.toHex(this.toggleSettingViewSupport2.computeInt()),
        floatValue + 39.0F + 10.0F,
        floatValue2 + 13.0F,
        13.5F,
        colorPalette.computeInt16());
    float floatValue6 = floatValue + floatValue3 - 46.0F;
    boolean flag =
        floatValue4 >= floatValue6
            && floatValue4 <= floatValue6 + 46.0F
            && floatValue5 >= floatValue2
            && floatValue5 <= floatValue2 + 26.0F;
    fontManager.render(
        floatValue6,
        floatValue2,
        46.0F,
        26.0F,
        13.0F,
        1.0F,
        ArgbColors.withOpacity(-1, flag ? 0.32F : 0.12F));
    fontManager.computeFloat2(
        "Reset",
        floatValue6 + 23.0F,
        floatValue2 + 13.0F,
        11.0F,
        flag ? colorPalette.computeInt16() : colorPalette.computeInt18());
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    float floatValue6 = (floatValue3 - 18.0F * countArray.length) / (countArray.length - 1);

    for (int index = 0; index < countArray.length; index++) {
      float floatValue7 = floatValue + index * (18.0F + floatValue6);
      boolean flag =
          floatValue4 >= floatValue7
              && floatValue4 <= floatValue7 + 18.0F
              && floatValue5 >= floatValue2
              && floatValue5 <= floatValue2 + 18.0F;
      fontManager.render(floatValue7, floatValue2, 18.0F, 18.0F, 5.0F, countArray[index]);
      fontManager.render(
          floatValue7,
          floatValue2,
          18.0F,
          18.0F,
          5.0F,
          1.0F,
          ArgbColors.withOpacity(-1, flag ? 0.55F : 0.16F));
    }
  }

  @Override
  public boolean test2(float floatValue, float floatValue2) {
    return this.test()
        && floatValue >= this.amount14
        && floatValue <= this.amount14 + 232.0F
        && floatValue2 >= this.amount15
        && floatValue2 <= this.amount15 + 234.0F;
  }

  @Override
  public boolean test3(float floatValue, float floatValue2) {
    if (!this.test2(floatValue, floatValue2)) {
      boolean flag = this.test4(floatValue, floatValue2);
      if (this.enabled) {
        this.enabled = false;
        this.tweenValue.animateTo(0.0F);
      }

      return flag;
    } else {
      float floatValue3 = this.amount14 + 12.0F;
      if (floatValue2 >= this.computeFloat5() && floatValue2 <= this.computeFloat5() + 118.0F) {
        this.enabled2 = true;
        this.apply2(floatValue, floatValue2);
        return true;
      }

      if (floatValue2 >= this.computeFloat6() - 4.0F
          && floatValue2 <= this.computeFloat6() + 12.0F + 4.0F) {
        this.enabled3 = true;
        this.apply(floatValue);
        return true;
      }

      if (floatValue2 >= this.computeFloat7()
          && floatValue2 <= this.computeFloat7() + 26.0F
          && floatValue >= floatValue3 + 208.0F - 46.0F) {
        this.toggleSettingViewSupport2.update();
        this.update();
        this.runnable.run();
        ClientAudio.apply(false);
        return true;
      }

      if (floatValue2 >= this.computeFloat8() && floatValue2 <= this.computeFloat8() + 18.0F) {
        float floatValue4 = (208.0F - 18.0F * countArray.length) / (countArray.length - 1);

        for (int index = 0; index < countArray.length; index++) {
          float floatValue5 = floatValue3 + index * (18.0F + floatValue4);
          if (floatValue >= floatValue5 && floatValue <= floatValue5 + 18.0F) {
            this.toggleSettingViewSupport2.apply(countArray[index]);
            this.update();
            this.runnable.run();
            ClientAudio.update5();
            return true;
          }
        }
      }

      return true;
    }
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.enabled2) {
      this.apply2(floatValue, floatValue2);
    }

    if (this.enabled3) {
      this.apply(floatValue);
    }
  }

  @Override
  public void update5() {
    this.enabled2 = false;
    this.enabled3 = false;
    super.update5();
  }

  public void apply2(float floatValue, float floatValue2) {
    this.amount12 = Math.clamp((floatValue - (this.amount14 + 12.0F)) / 208.0F, 0.0F, 1.0F);
    this.amount13 = 1.0F - Math.clamp((floatValue2 - this.computeFloat5()) / 118.0F, 0.0F, 1.0F);
    this.update2();
  }

  public void apply(float floatValue) {
    this.amount11 =
        Math.clamp((floatValue - (this.amount14 + 12.0F)) / 208.0F, 0.0F, 1.0F) * 360.0F;
    this.update2();
  }

  public static void ensureInitialized() {}
}

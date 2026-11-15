package bunnythekenzo.client.setting;

import java.util.List;
import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport5 extends SettingWidget {
  public static final int count = 4;
  public static final float amount = 24.0F;
  public static final float amount2 = 168.0F;
  public static final float amount3 = 26.0F;
  public static final float amount4 = 6.0F;
  public static final float amount5 = 208.0F;
  public final ChoiceSettingView choiceSettingView;
  public final boolean enabled;
  public boolean enabled2;
  public final AnimationIntensitySupport[] animationIntensitySupportArray;
  public final DampedSpring dampedSpring = new DampedSpring(300.0F, 24.0F, 0.0F);
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final TweenValue tweenValue = new TweenValue(200.0F, 0.0F, Easing.OUT_EXPO);
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public boolean enabled3;
  public float amount6;
  public float amount7;
  public float amount8;
  public float amount9;
  public int count2 = -1;

  public SettingWidgetSupport5(ChoiceSettingView choiceSettingView2) {
    super(choiceSettingView2);
    this.choiceSettingView = choiceSettingView2;
    this.enabled = choiceSettingView2.getChoices().size() <= 4;
    this.enabled2 = this.enabled;
    this.animationIntensitySupportArray =
        new AnimationIntensitySupport[this.enabled ? choiceSettingView2.getChoices().size() : 0];

    for (int index = 0; index < this.animationIntensitySupportArray.length; index++) {
      this.animationIntensitySupportArray[index] =
          new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
    }

    this.dampedSpring.snapTo(choiceSettingView2.getSelectedIndex());
  }

  @Override
  public float computeFloat() {
    return this.enabled ? 56.0F : 42.0F;
  }

  @Override
  public float computeFloat2() {
    return this.enabled ? this.amount3 + 15.0F : this.amount3 + this.amount5 / 2.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    if (this.enabled) {
      boolean enabled22 = this.test(fontManager);
      if (this.enabled2 && !enabled22) {
        this.enabled3 = false;
        this.tweenValue.snapTo(0.0F);
      }

      this.enabled2 = enabled22;
    }

    if (this.enabled2) {
      this.render2(fontManager, colorPalette, floatValue, floatValue2, floatValue3);
    } else {
      this.render(fontManager, colorPalette, floatValue2, floatValue3);
    }
  }

  public boolean test(FontManager fontManager) {
    if (this.enabled && this.animationIntensitySupportArray.length != 0) {
      float floatValue = (this.amount4 - 24.0F) / this.animationIntensitySupportArray.length;

      for (String text :
          (java.lang.Iterable<String>)
              (java.lang.Iterable<?>) (this.choiceSettingView.getChoices())) {
        if (fontManager.computeFloat(text, 11.0F) > floatValue - 10.0F) {
          return false;
        }
      }

      return true;
    } else {
      return false;
    }
  }

  public void render2(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    float floatValue4 = this.amount2 + 12.0F;
    float floatValue5 = this.amount4 - 24.0F;
    float floatValue6 = this.amount3 + 26.0F;
    int length = this.animationIntensitySupportArray.length;
    float floatValue7 = floatValue5 / length;
    fontManager.render(
        floatValue4,
        floatValue6,
        floatValue5,
        24.0F,
        12.0F,
        ArgbColors.withOpacity(-15331042, 0.9F));
    fontManager.render(
        floatValue4,
        floatValue6,
        floatValue5,
        24.0F,
        12.0F,
        1.0F,
        ArgbColors.withOpacity(-1, 0.06F + 0.04F * floatValue));
    float value = this.dampedSpring.advance();
    float floatValue8 =
        Math.clamp(
            Math.abs(this.dampedSpring.getVelocity()) * floatValue7 * 0.07F,
            0.0F,
            floatValue7 * 0.28F);
    float floatValue9 = floatValue4 + value * floatValue7 + 2.0F - floatValue8 / 2.0F;
    float floatValue10 = floatValue7 - 4.0F + floatValue8;
    fontManager.render(
        floatValue9,
        floatValue6 + 2.0F,
        floatValue10,
        20.0F,
        10.0F,
        ArgbColors.lighten(colorPalette.computeInt(), 0.1F),
        colorPalette.computeInt(),
        1.4F);

    for (int index = 0; index < length; index++) {
      float floatValue11 = floatValue4 + index * floatValue7;
      boolean bl =
          floatValue2 >= floatValue11
              && floatValue2 <= floatValue11 + floatValue7
              && floatValue3 >= floatValue6
              && floatValue3 <= floatValue6 + 24.0F;
      float value2 = this.animationIntensitySupportArray[index].computeFloat(bl);
      boolean flag = index == this.choiceSettingView.getSelectedIndex();
      int intValue =
          flag
              ? -15135211
              : ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), value2);
      fontManager.update2();
      fontManager.render2(
          floatValue11 + floatValue7 / 2.0F, floatValue6 + 12.0F, 1.0F + value2 * 0.04F);
      fontManager.computeFloat2(
          (String) this.choiceSettingView.getChoices().get(index),
          floatValue11 + floatValue7 / 2.0F,
          floatValue6 + 12.0F,
          11.0F,
          intValue);
      fontManager.update3();
    }
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float float3 = this.computeFloat3();
    float float4 = this.computeFloat4();
    float float5 = this.computeFloat5();
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue, floatValue2));
    boolean enabled32 = this.enabled3;
    fontManager.render(
        float3, float4, float5, 24.0F, 12.0F, ArgbColors.withOpacity(-15331042, 0.9F));
    fontManager.render(
        float3,
        float4,
        float5,
        24.0F,
        12.0F,
        1.0F,
        enabled32
            ? colorPalette.computeInt(0.5F)
            : ArgbColors.withOpacity(-1, 0.08F + 0.14F * value));
    fontManager.computeFloat(
        this.choiceSettingView.getSelectedValue(),
        float3 + 12.0F,
        float4 + 12.0F,
        11.0F,
        ArgbColors.lerp(
            colorPalette.computeInt17(),
            colorPalette.computeInt16(),
            Math.max(value, enabled32 ? 1.0F : 0.0F)),
        float5 - 34.0F);
    fontManager.update2();
    fontManager.apply(float3 + float5 - 14.0F, float4 + 12.0F);
    fontManager.apply2((float) Math.PI * this.tweenValue.getValue());
    fontManager.render(0.0F, -1.5F, 3.5F, 1.5F, colorPalette.computeInt18(), true);
    fontManager.update3();
  }

  public float computeFloat3() {
    return this.enabled
        ? this.amount2 + 12.0F
        : this.amount2 + this.amount4 - 12.0F - this.computeFloat5();
  }

  public float computeFloat4() {
    return this.enabled ? this.amount3 + 26.0F : this.amount3 + (this.amount5 - 24.0F) / 2.0F;
  }

  public float computeFloat5() {
    return this.enabled ? this.amount4 - 24.0F : Math.min(168.0F, this.amount4 - 24.0F);
  }

  public boolean test(float floatValue, float floatValue2) {
    float float4 = this.computeFloat4();
    return floatValue >= this.computeFloat3()
        && floatValue <= this.computeFloat3() + this.computeFloat5()
        && floatValue2 >= float4
        && floatValue2 <= float4 + 24.0F;
  }

  @Override
  public boolean test() {
    return !this.enabled2 && (this.enabled3 || this.tweenValue.getValue() > 0.01F);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float value = this.tweenValue.getValue();
    if (!(value <= 0.01F)) {
      ColorPalette instance = ColorPalette.getInstance();
      List list = this.choiceSettingView.getChoices();
      float floatValue5 = list.size() * 26.0F + 12.0F;
      this.amount8 = this.computeFloat5();
      this.amount9 = Math.min(208.0F, floatValue5);
      this.amount6 = this.computeFloat3();
      boolean flag = this.amount3 + this.amount5 + 4.0F + this.amount9 <= floatValue4;
      this.amount7 = flag ? this.amount3 + this.amount5 + 4.0F : this.amount3 - 4.0F - this.amount9;
      fontManager.update2();
      fontManager.apply3(value);
      fontManager.render2(
          this.amount6 + this.amount8 / 2.0F,
          flag ? this.amount7 : this.amount7 + this.amount9,
          0.96F + 0.04F * value);
      fontManager.render(
          this.amount6,
          this.amount7,
          this.amount8,
          this.amount9,
          10.0F,
          16.0F,
          5.0F,
          ArgbColors.withOpacity(-16777216, 0.55F));
      fontManager.render(
          this.amount6,
          this.amount7,
          this.amount8,
          this.amount9,
          10.0F,
          instance.computeInt5(),
          instance.computeInt6(),
          true);
      fontManager.render(
          this.amount6,
          this.amount7,
          this.amount8,
          this.amount9,
          10.0F,
          1.0F,
          instance.computeInt14());
      this.smoothScrollState.apply(floatValue5, this.amount9);
      this.count2 = -1;
      fontManager.update2();
      fontManager.render(
          this.amount6 + 1.0F, this.amount7 + 1.0F, this.amount8 - 2.0F, this.amount9 - 2.0F);
      float floatValue6 = this.amount7 + 6.0F - this.smoothScrollState.computeFloat();

      for (int index = 0; index < list.size(); index++) {
        float floatValue7 = floatValue6 + index * 26.0F;
        if (!(floatValue7 > this.amount7 + this.amount9) && !(floatValue7 + 26.0F < this.amount7)) {
          boolean flag2 =
              floatValue >= this.amount6
                  && floatValue <= this.amount6 + this.amount8
                  && floatValue2 >= floatValue7
                  && floatValue2 <= floatValue7 + 26.0F;
          if (flag2) {
            this.count2 = index;
          }

          boolean flag3 = index == this.choiceSettingView.getSelectedIndex();
          if (flag2) {
            fontManager.render(
                this.amount6 + 4.0F,
                floatValue7,
                this.amount8 - 8.0F,
                26.0F,
                6.0F,
                ArgbColors.withOpacity(-1, 0.06F));
          }

          if (flag3) {
            fontManager.render(
                this.amount6 + 5.0F, floatValue7 + 7.0F, 2.5F, 12.0F, 1.25F, instance.computeInt());
          }

          fontManager.computeFloat(
              (String) list.get(index),
              this.amount6 + 14.0F,
              floatValue7 + 13.0F,
              11.0F,
              flag3
                  ? instance.computeInt()
                  : ArgbColors.lerp(
                      instance.computeInt18(), instance.computeInt16(), flag2 ? 1.0F : 0.0F),
              this.amount8 - 26.0F);
        }
      }

      fontManager.update3();
      this.smoothScrollState.render(
          fontManager,
          this.amount6 + this.amount8 - 2.0F,
          this.amount7 + 2.0F,
          this.amount9 - 4.0F);
      fontManager.update3();
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    }

    if (!this.enabled2) {
      this.enabled3 = !this.enabled3;
      this.tweenValue.animateTo(this.enabled3 ? 1.0F : 0.0F);
      if (this.enabled3) {
        this.smoothScrollState.update2();
      }

      ClientAudio.update5();
      return true;
    } else {
      float floatValue3 = this.amount2 + 12.0F;
      float floatValue4 = this.amount4 - 24.0F;
      float floatValue5 = this.amount3 + 26.0F;
      if (!(floatValue2 < floatValue5) && !(floatValue2 > floatValue5 + 24.0F)) {
        int length = this.animationIntensitySupportArray.length;
        int intValue2 = (int) Math.floor((floatValue - floatValue3) / (floatValue4 / length));
        intValue2 = Math.clamp(intValue2, 0, length - 1);
        this.apply(intValue2, length);
        this.animationIntensitySupport.apply(true);
        return true;
      } else {
        return true;
      }
    }
  }

  @Override
  public boolean test2(float floatValue, float floatValue2) {
    return this.test()
        && floatValue >= this.amount6
        && floatValue <= this.amount6 + this.amount8
        && floatValue2 >= this.amount7
        && floatValue2 <= this.amount7 + this.amount9;
  }

  @Override
  public boolean test3(float floatValue, float floatValue2) {
    if (!this.test2(floatValue, floatValue2)) {
      boolean flag = this.test4(floatValue, floatValue2);
      if (this.enabled3) {
        this.enabled3 = false;
        this.tweenValue.animateTo(0.0F);
      }

      return flag;
    } else {
      if (this.count2 >= 0) {
        this.apply(this.count2, this.choiceSettingView.getChoices().size());
        this.enabled3 = false;
        this.tweenValue.animateTo(0.0F);
      }

      return true;
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (!this.test2(floatValue, floatValue2)) {
      return false;
    }

    this.smoothScrollState.test(doubleValue);
    return true;
  }

  public void apply(int intValue, int intValue2) {
    if (intValue != this.choiceSettingView.getSelectedIndex()) {
      this.choiceSettingView.selectIndex(intValue);
      this.dampedSpring.setTarget(intValue);
      GuiPreferences.getGuiPreferences().update();
      ClientAudio.apply(intValue2 <= 1 ? 0.5F : (float) intValue / (intValue2 - 1));
    }
  }
}

package bunnythekenzo.client.setting;

import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport4 extends SettingWidget {
  public static final float amount = 142.0F;
  public static final float amount2 = 24.0F;
  public final StringSettingView toggleSettingViewSupport4;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public boolean enabled;

  public SettingWidgetSupport4(StringSettingView toggleSettingViewSupport42) {
    super(toggleSettingViewSupport42);
    this.toggleSettingViewSupport4 = toggleSettingViewSupport42;
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
    float float3 = this.computeFloat3();
    float float4 = this.computeFloat4();
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue2, floatValue3));
    int intValue =
        this.enabled
            ? colorPalette.computeInt(0.75F)
            : ArgbColors.withOpacity(-1, 0.08F + 0.14F * value);
    fontManager.render(
        float3, float4, 142.0F, 24.0F, 7.0F, ArgbColors.withOpacity(-15331042, 0.92F));
    fontManager.render(float3, float4, 142.0F, 24.0F, 7.0F, 1.0F, intValue);
    String string3 = this.toggleSettingViewSupport4.getValue();
    boolean empty = string3.isEmpty();
    String string =
        this.getString(
            fontManager, empty ? this.toggleSettingViewSupport4.getPlaceholder() : string3, 124.0F);
    int intValue2 = empty ? colorPalette.computeInt19() : colorPalette.computeInt16();
    fontManager.computeFloat(string, float3 + 9.0F, float4 + 12.0F, 11.0F, intValue2);
    if (this.enabled && !empty && (System.nanoTime() / 450000000L & 1L) == 0L) {
      float floatValue4 = float3 + 9.0F + Math.min(fontManager.computeFloat(string, 11.0F), 124.0F);
      fontManager.render(
          floatValue4 + 1.0F, float4 + 6.0F, 1.0F, 12.0F, 0.5F, colorPalette.computeInt());
    } else if (this.enabled && empty && (System.nanoTime() / 450000000L & 1L) == 0L) {
      fontManager.render(
          float3 + 9.0F, float4 + 6.0F, 1.0F, 12.0F, 0.5F, colorPalette.computeInt());
    }
  }

  public String getString(FontManager fontManager, String text, float floatValue) {
    String text2 = text;

    while (!text2.isEmpty() && fontManager.computeFloat(text2, 11.0F) > floatValue) {
      int intValue = text2.offsetByCodePoints(0, 1);
      text2 = text2.substring(intValue);
    }

    return text2;
  }

  public float computeFloat3() {
    return this.amount2 + this.amount4 - 12.0F - 142.0F;
  }

  public float computeFloat4() {
    return this.amount3 + (this.amount5 - 24.0F) / 2.0F;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.computeFloat3()
        && floatValue <= this.computeFloat3() + 142.0F
        && floatValue2 >= this.computeFloat4()
        && floatValue2 <= this.computeFloat4() + 24.0F;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    }

    boolean enabled2 = this.enabled;
    this.enabled = intValue == 0 && this.test(floatValue, floatValue2);
    if (this.enabled && !enabled2) {
      ClientAudio.update5();
    }

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

  @Override
  public boolean test(int intValue) {
    if (this.enabled && !Character.isISOControl(intValue) && Character.isValidCodePoint(intValue)) {
      String string3 = this.toggleSettingViewSupport4.getValue();
      if (string3.codePointCount(0, string3.length())
          < this.toggleSettingViewSupport4.getMaxCodePoints()) {
        this.toggleSettingViewSupport4.setValue(string3 + new String(Character.toChars(intValue)));
      }

      return true;
    } else {
      return this.enabled;
    }
  }

  @Override
  public boolean test2(int intValue) {
    if (!this.enabled) {
      return false;
    }

    switch (intValue) {
      case 256:
      case 257:
      case 335:
        this.enabled = false;
        break;
      case 259:
        this.update();
        break;
      case 261:
        this.toggleSettingViewSupport4.setValue("");
    }

    return true;
  }

  public void update() {
    String string3 = this.toggleSettingViewSupport4.getValue();
    if (!string3.isEmpty()) {
      int intValue = string3.offsetByCodePoints(string3.length(), -1);
      this.toggleSettingViewSupport4.setValue(string3.substring(0, intValue));
    }
  }
}

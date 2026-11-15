package bunnythekenzo.client.setting;

import java.util.Objects;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.render.FontManager;

public abstract class SettingWidget {
  public static final float amount = 12.0F;
  public final SettingEntry settingEntry;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public float amount2;
  public float amount3;
  public float amount4;
  public float amount5;

  public SettingWidget(SettingEntry settingEntry4) {
    this.settingEntry = settingEntry4;
  }

  public SettingEntry getSettingEntry() {
    return this.settingEntry;
  }

  public abstract float computeFloat();

  public abstract void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3);

  public float computeFloat2() {
    return this.amount3 + this.amount5 / 2.0F;
  }

  public void render(FontManager fontManager, ColorPalette colorPalette, float floatValue) {
    fontManager.computeFloat(
        this.settingEntry.getName(),
        this.amount2 + 12.0F,
        this.computeFloat2(),
        12.5F,
        ArgbColors.lerp(colorPalette.computeInt17(), colorPalette.computeInt16(), floatValue));
  }

  public final void render(
      FontManager fontManager,
      float amount22,
      float amount32,
      float amount42,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.amount4 = amount42;
    this.amount5 = this.computeFloat();
    if (floatValue3 * floatValue4 <= 0.004F) {
      this.update7();
    } else {
      ColorPalette instance = ColorPalette.getInstance();
      float value =
          this.animationIntensitySupport.computeFloat(this.test4(floatValue, floatValue2));
      fontManager.update2();
      fontManager.apply3(floatValue3 * floatValue4);
      fontManager.apply((1.0F - floatValue3) * 22.0F, 0.0F);
      fontManager.render2(
          amount22 + amount42 / 2.0F,
          amount32 + this.amount5 / 2.0F,
          this.animationIntensitySupport.computeFloat(0.008F));
      if (value > 0.01F) {
        fontManager.render(
            amount22,
            amount32 + 1.0F,
            amount42,
            this.amount5,
            9.0F,
            5.0F,
            2.0F,
            ArgbColors.withOpacity(-16777216, value * 0.16F));
        fontManager.render(
            amount22,
            amount32,
            amount42,
            this.amount5,
            9.0F,
            ArgbColors.withOpacity(instance.computeInt11(), value * 0.55F));
        fontManager.render(
            amount22 + 0.5F,
            amount32 + 0.5F,
            amount42 - 1.0F,
            this.amount5 - 1.0F,
            8.5F,
            0.8F,
            ArgbColors.withOpacity(-1, value * 0.17F),
            ArgbColors.withOpacity(-1, value * 0.025F),
            true);
        float floatValue5 = (this.amount5 - 22.0F) * value;
        fontManager.render(
            amount22 + 1.0F,
            amount32 + this.amount5 / 2.0F - floatValue5 / 2.0F,
            2.0F,
            floatValue5,
            1.0F,
            instance.computeInt(0.7F * value));
      }

      this.render(fontManager, instance, value);
      this.render(fontManager, instance, value, floatValue, floatValue2);
      fontManager.update3();
    }
  }

  public boolean test4(float floatValue, float floatValue2) {
    return floatValue >= this.amount2
        && floatValue <= this.amount2 + this.amount4
        && floatValue2 >= this.amount3
        && floatValue2 <= this.amount3 + this.amount5;
  }

  public boolean test() {
    return false;
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {}

  public boolean test2(float floatValue, float floatValue2) {
    return false;
  }

  public boolean test3(float floatValue, float floatValue2) {
    return false;
  }

  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    return false;
  }

  public abstract boolean test(float floatValue, float floatValue2, int intValue);

  public void apply(float floatValue, float floatValue2) {}

  public void update5() {
    this.animationIntensitySupport.apply(false);
  }

  public boolean test2(int intValue) {
    return false;
  }

  public boolean test(int intValue) {
    return false;
  }

  public boolean test2() {
    return false;
  }

  public void update6() {}

  public void update7() {
    this.animationIntensitySupport.update();
  }

  public static SettingWidget getSettingWidget(SettingEntry settingEntry) {
    SettingEntry settingEntry2 = settingEntry;
    Objects.requireNonNull(settingEntry2);
    SettingEntry settingEntry3 = settingEntry2;
    return switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          StringSettingView.class,
          ToggleSettingView.class,
          NumberSettingView.class,
          ChoiceSettingView.class,
          ColorSettingView.class,
          KeybindSettingView.class,
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class
        },
        (Object) settingEntry3,
        0)) {
      default -> throw new MatchException(null, null);
      case 0 -> {
        StringSettingView toggleSettingViewSupport4 = (StringSettingView) settingEntry3;
        yield new SettingWidgetSupport4(toggleSettingViewSupport4);
      }
      case 1 -> {
        ToggleSettingView toggleSettingView = (ToggleSettingView) settingEntry3;
        yield new SettingWidgetSupport3(toggleSettingView);
      }
      case 2 -> {
        NumberSettingView numberSettingView = (NumberSettingView) settingEntry3;
        yield new SettingWidgetSupport7(numberSettingView);
      }
      case 3 -> {
        ChoiceSettingView choiceSettingView = (ChoiceSettingView) settingEntry3;
        yield new SettingWidgetSupport5(choiceSettingView);
      }
      case 4 -> {
        ColorSettingView toggleSettingViewSupport2 = (ColorSettingView) settingEntry3;
        yield new SettingWidgetSupport2(toggleSettingViewSupport2);
      }
      case 5 -> {
        KeybindSettingView toggleSettingViewSupport = (KeybindSettingView) settingEntry3;
        yield new SettingWidgetSupport6(toggleSettingViewSupport);
      }
      case 6 -> {
        BlockListSettingView toggleSettingViewSupport8 = (BlockListSettingView) settingEntry3;
        yield new SettingWidgetSupport(toggleSettingViewSupport8);
      }
      case 7 -> {
        EntityTypeSettingView toggleSettingViewSupport3 = (EntityTypeSettingView) settingEntry3;
        yield new SettingWidgetSupport(toggleSettingViewSupport3);
      }
      case 8 -> {
        ItemSettingView toggleSettingViewSupport6 = (ItemSettingView) settingEntry3;
        yield new SettingWidgetSupport(toggleSettingViewSupport6);
      }
      case 9 -> {
        GlintStyleSettingView toggleSettingViewSupport7 = (GlintStyleSettingView) settingEntry3;
        yield new SettingWidgetSupport(toggleSettingViewSupport7);
      }
      case 10 -> {
        ItemReplacementSettingView toggleSettingViewSupport5 =
            (ItemReplacementSettingView) settingEntry3;
        yield new SettingWidgetSupport(toggleSettingViewSupport5);
      }
    };
  }
}

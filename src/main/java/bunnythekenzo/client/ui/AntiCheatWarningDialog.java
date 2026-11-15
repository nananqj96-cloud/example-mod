package bunnythekenzo.client.ui;

import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.StaggeredTransition;
import bunnythekenzo.client.render.FontManager;

public final class AntiCheatWarningDialog {
  public static final float amount = 30.0F;
  public static final float amount2 = 32.0F;
  public static final float amount3 = 10.0F;
  public static final float amount4 = 96.0F;
  public static final float amount5 = 152.0F;
  public static final int count = 4;
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(46.0F, 420.0F);
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport();
  public boolean enabled;
  public float amount6;
  public float amount7;
  public float amount8;

  public boolean isEnabled() {
    return this.enabled;
  }

  public void update() {
    this.staggeredTransition.apply(4);
  }

  public void update2() {
    this.staggeredTransition.apply2(4);
  }

  public void update3() {
    if (!this.enabled) {
      this.enabled = true;
      ClientAudio.update6();
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue7 = floatValue + floatValue3 / 2.0F;
    float floatValue8 = floatValue2 + floatValue4 / 2.0F - 100.0F;
    float floatValue9 = Math.min(floatValue3 - 48.0F, 420.0F);
    float value = this.staggeredTransition.computeFloat(0);
    if (value > 0.01F) {
      fontManager.update2();
      fontManager.apply3(value);
      fontManager.apply(0.0F, (1.0F - value) * 12.0F);
      fontManager.render2(
          floatValue7, floatValue8 + 15.0F, 30.0F, 2.0F, instance.computeInt23(), 0.0F);
      fontManager.update3();
    }

    float value2 = this.staggeredTransition.computeFloat(1);
    if (value2 > 0.01F) {
      fontManager.update2();
      fontManager.apply3(value2);
      fontManager.apply(0.0F, (1.0F - value2) * 10.0F);
      fontManager.computeFloat2(
          "Anti-Cheat Off", floatValue7, floatValue8 + 56.0F, 18.0F, instance.computeInt16());
      fontManager.update3();
    }

    float value3 = this.staggeredTransition.computeFloat(2);
    if (value3 > 0.01F) {
      fontManager.update2();
      fontManager.apply3(value3);
      fontManager.apply(0.0F, (1.0F - value3) * 8.0F);
      fontManager.computeFloat(
          "Are you sure you want to enter this section?",
          floatValue7,
          floatValue8 + 82.0F,
          13.5F,
          instance.computeInt17(),
          floatValue9,
          2);
      fontManager.computeFloat(
          "Every module in here is highly detectable.",
          floatValue7,
          floatValue8 + 102.0F,
          11.0F,
          instance.computeInt18(),
          floatValue9,
          2);
      fontManager.computeFloat(
          "Only use them on a server with protection switched off.",
          floatValue7,
          floatValue8 + 118.0F,
          11.0F,
          instance.computeInt18(),
          floatValue9,
          2);
      fontManager.update3();
    }

    float value4 = this.staggeredTransition.computeFloat(3);
    this.amount8 = floatValue8 + 150.0F;
    this.amount6 = floatValue7 - 129.0F;
    this.amount7 = this.amount6 + 96.0F + 10.0F;
    if (value4 > 0.01F) {
      fontManager.update2();
      fontManager.apply3(value4);
      fontManager.apply(0.0F, (1.0F - value4) * 8.0F);
      this.render(
          fontManager,
          instance,
          this.animationIntensitySupport,
          this.amount6,
          96.0F,
          "Cancel",
          false,
          this.animationIntensitySupport.computeFloat(
              this.test(this.amount6, 96.0F, floatValue5, floatValue6)));
      this.render(
          fontManager,
          instance,
          this.animationIntensitySupport2,
          this.amount7,
          152.0F,
          "Enter section",
          true,
          this.animationIntensitySupport2.computeFloat(
              this.test(this.amount7, 152.0F, floatValue5, floatValue6)));
      fontManager.update3();
    } else {
      this.animationIntensitySupport.update();
      this.animationIntensitySupport2.update();
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      AnimationIntensitySupport animationIntensitySupport3,
      float floatValue,
      float floatValue2,
      String text,
      boolean flag,
      float floatValue3) {
    int intValue =
        flag
            ? ArgbColors.lerp(
                colorPalette.computeInt2(0.16F), colorPalette.computeInt2(0.3F), floatValue3)
            : ArgbColors.withOpacity(-1, 0.05F + floatValue3 * 0.05F);
    int intValue2 =
        flag
            ? ArgbColors.withOpacity(colorPalette.computeInt23(), 0.5F + floatValue3 * 0.35F)
            : ArgbColors.withOpacity(-1, 0.11F + floatValue3 * 0.14F);
    int intValue3 =
        flag
            ? ArgbColors.lerp(colorPalette.computeInt23(), -1, floatValue3 * 0.4F)
            : ArgbColors.lerp(
                colorPalette.computeInt17(), colorPalette.computeInt16(), floatValue3);
    fontManager.update2();
    fontManager.render2(
        floatValue + floatValue2 / 2.0F,
        this.amount8 + 16.0F,
        animationIntensitySupport3.computeFloat(0.025F));
    fontManager.render(floatValue, this.amount8, floatValue2, 32.0F, 8.0F, intValue);
    fontManager.render(floatValue, this.amount8, floatValue2, 32.0F, 8.0F, 1.0F, intValue2);
    fontManager.computeFloat2(
        text, floatValue + floatValue2 / 2.0F, this.amount8 + 16.0F, 11.0F, intValue3);
    fontManager.update3();
  }

  public boolean test(float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    return floatValue3 >= floatValue
        && floatValue3 <= floatValue + floatValue2
        && floatValue4 >= this.amount8
        && floatValue4 <= this.amount8 + 32.0F;
  }

  public boolean test(float floatValue, float floatValue2, Runnable runnable) {
    if (this.test(this.amount7, 152.0F, floatValue, floatValue2)) {
      this.animationIntensitySupport2.apply(true);
      this.update3();
      return true;
    } else if (this.test(this.amount6, 96.0F, floatValue, floatValue2)) {
      this.animationIntensitySupport.apply(true);
      runnable.run();
      return true;
    } else {
      return false;
    }
  }

  public void update4() {
    this.animationIntensitySupport.apply(false);
    this.animationIntensitySupport2.apply(false);
  }

  public boolean test(int intValue) {
    if (intValue != 257 && intValue != 335) {
      return false;
    }

    this.update3();
    return true;
  }
}

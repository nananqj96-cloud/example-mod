package bunnythekenzo.client.setting;

import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.ToggleSwitchAnimation;
import bunnythekenzo.client.render.FontManager;

public final class SettingWidgetSupport3 extends SettingWidget {
  public static final float amount = 34.0F;
  public static final float amount2 = 18.0F;
  public final ToggleSettingView toggleSettingView;
  public final ToggleSwitchAnimation toggleSwitchAnimation;

  public SettingWidgetSupport3(ToggleSettingView toggleSettingView2) {
    super(toggleSettingView2);
    this.toggleSettingView = toggleSettingView2;
    this.toggleSwitchAnimation = new ToggleSwitchAnimation(toggleSettingView2.getValue());
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
    this.toggleSwitchAnimation.render(
        fontManager,
        this.amount2 + this.amount4 - 34.0F - 12.0F,
        this.amount3 + (this.amount5 - 18.0F) / 2.0F,
        34.0F,
        18.0F,
        floatValue);
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    }

    boolean flag = this.toggleSettingView.toggle();
    this.toggleSwitchAnimation.apply(flag);
    this.animationIntensitySupport.apply(true);
    ClientAudio.apply(flag);
    return true;
  }
}

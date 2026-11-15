package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.ToggleSwitchAnimation;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.SettingWidget;

public final class HudEditorPageSupport {
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final ToggleSwitchAnimation toggleSwitchAnimation;
  public final DampedSpring dampedSpring = new DampedSpring(240.0F, 26.0F, 0.0F);
  public final DampedSpring dampedSpring2 = new DampedSpring(230.0F, 27.0F, 0.0F);
  public final List list = new ArrayList();
  public float amount;
  public float amount2;

  public HudEditorPageSupport(HudWidget hudWidget) {
    this.toggleSwitchAnimation = new ToggleSwitchAnimation(hudWidget.isEnabled2());

    for (SettingEntry settingEntry :
        (java.lang.Iterable<SettingEntry>) (java.lang.Iterable<?>) (hudWidget.getList2())) {
      this.list.add(SettingWidget.getSettingWidget(settingEntry));
    }

    for (SettingWidget settingWidget :
        (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (this.list)) {
      this.amount2 = this.amount2 + (settingWidget.computeFloat() + 4.0F);
    }

    if (!this.list.isEmpty()) {
      this.amount2 += 8.0F;
    }
  }

  public boolean test() {
    return !this.list.isEmpty();
  }
}

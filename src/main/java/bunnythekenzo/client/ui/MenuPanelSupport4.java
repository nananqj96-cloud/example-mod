package bunnythekenzo.client.ui;

import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.setting.SettingWidgetSupport2;

public final class MenuPanelSupport4 {
  public SettingWidgetSupport2 settingWidgetSupport2;
  public float amount;
  public float amount2;

  public void open(ColorSettingView toggleSettingViewSupport2, ClientData3 clientData3) {
    this.settingWidgetSupport2 = new SettingWidgetSupport2(toggleSettingViewSupport2, () -> {});
    this.amount = clientData3.amount();
    this.amount2 = clientData3.amount2();
    this.settingWidgetSupport2.update3();
  }

  public boolean test() {
    return this.settingWidgetSupport2 != null && this.settingWidgetSupport2.test();
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    if (this.test()) {
      float amount3 = this.amount;
      SettingWidgetSupport2.ensureInitialized();
      this.amount = Math.clamp(amount3, 8.0F, Math.max(8.0F, floatValue3 - 232.0F - 8.0F));
      amount3 = this.amount2;
      SettingWidgetSupport2.ensureInitialized();
      this.amount2 = Math.clamp(amount3, 8.0F, Math.max(8.0F, floatValue4 - 234.0F - 8.0F));
      this.settingWidgetSupport2.render2(
          fontManager, floatValue, floatValue2, this.amount, this.amount2);
    }
  }

  public void apply(float floatValue, float floatValue2) {
    if (this.settingWidgetSupport2 != null) {
      this.settingWidgetSupport2.test3(floatValue, floatValue2);
    }
  }

  public void apply2(float floatValue, float floatValue2) {
    if (this.settingWidgetSupport2 != null) {
      this.settingWidgetSupport2.apply(floatValue, floatValue2);
    }
  }

  public void update() {
    if (this.settingWidgetSupport2 != null) {
      this.settingWidgetSupport2.update5();
    }
  }

  public void update2() {
    if (this.settingWidgetSupport2 != null) {
      this.settingWidgetSupport2.update4();
    }
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return new ClientData3(this.amount + floatValue, this.amount2 + floatValue2);
  }
}

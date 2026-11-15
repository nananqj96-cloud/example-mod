package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.CollectionData;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.KeyNames;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingEntry;

public final class MenuPanelSupport {
  public static final float amount = 30.0F;
  public final GuiModuleEntry guiModuleEntry;
  public final List list = new ArrayList();
  public final String string;
  public final PanelLayout panelLayout;
  public final MenuPanelSupport4 menuPanelSupport4;
  public final TweenValue tweenValue = new TweenValue(180.0F, 0.0F, Easing.OUT_QUART);
  public final TweenValue tweenValue2;
  public final TweenValue tweenValue3;
  public final TweenValue tweenValue4 = new TweenValue(260.0F, 0.0F, Easing.OUT_QUART);
  public float amount2;
  public float amount3;
  public float amount4;
  public float amount5;
  public boolean enabled;
  public MenuPanelSupport5 menuPanelSupport5;
  public MenuPanelSupport5 menuPanelSupport52;

  public MenuPanelSupport(
      GuiModuleEntry guiModuleEntry2,
      String text,
      PanelLayout panelLayout2,
      MenuPanelSupport4 menuPanelSupport42) {
    this.guiModuleEntry = guiModuleEntry2;
    this.panelLayout = panelLayout2;
    this.menuPanelSupport4 = menuPanelSupport42;
    this.string = guiModuleEntry2.getString() + "@" + text;
    this.tweenValue2 =
        new TweenValue(220.0F, guiModuleEntry2.test() ? 1.0F : 0.0F, Easing.OUT_QUART);
    this.tweenValue3 = new TweenValue(270.0F, this.test() ? 1.0F : 0.0F, Easing.OUT_QUART);

    for (CollectionData collectionData :
        (java.lang.Iterable<CollectionData>) (java.lang.Iterable<?>) (guiModuleEntry2.getList())) {
      for (SettingEntry settingEntry :
          (java.lang.Iterable<SettingEntry>) (java.lang.Iterable<?>) (collectionData.list())) {
        this.list.add(new MenuPanelSupport5(settingEntry, collectionData.string()));
      }
    }
  }

  public boolean test() {
    return this.panelLayout.test(this.string);
  }

  public boolean test2() {
    return this.tweenValue3.getValue() > 0.999F;
  }

  public void apply(boolean flag) {
    this.panelLayout.apply(this.string, flag);
    this.tweenValue3.animateTo(flag ? 1.0F : 0.0F);
    if (!flag) {
      this.update3();
    }
  }

  public void update() {
    float amount32 = 44.0F;
    String text = null;

    for (MenuPanelSupport5 menuPanelSupport53 :
        (java.lang.Iterable<MenuPanelSupport5>) (java.lang.Iterable<?>) (this.list)) {
      if (!menuPanelSupport53.settingEntry.isVisible()) {
        menuPanelSupport53.amount3 = -1.0F;
        menuPanelSupport53.update();
      } else {
        if (!menuPanelSupport53.string.equals(text)) {
          amount32 += 28.0F;
          text = menuPanelSupport53.string;
        }

        menuPanelSupport53.amount3 = amount32;
        amount32 += menuPanelSupport53.computeFloat() + 5.0F;
      }
    }

    this.amount5 = amount32 + 6.0F;
    if (!AnimationPreferences.isEnabled()) {
      this.tweenValue3.snapTo(this.tweenValue3.getTarget());
    }
  }

  public float computeFloat() {
    this.update();
    return 30.0F + this.amount5 * this.tweenValue3.getValue();
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    ColorPalette instance = ColorPalette.getInstance();
    this.tweenValue.animateTo(
        test(floatValue, floatValue2, this.amount2, this.amount3, this.amount4, 30.0F)
            ? 1.0F
            : 0.0F);
    this.tweenValue2.animateTo(this.guiModuleEntry.test() ? 1.0F : 0.0F);
    float value = this.tweenValue2.getValue();
    float value2 = this.tweenValue.getValue();
    float value3 = this.tweenValue3.getValue();
    fontManager.update2();
    fontManager.apply3(floatValue3);
    fontManager.render(
        this.amount2, this.amount3, this.amount4, 30.0F, 8.0F, instance.computeInt(value * 0.14F));
    fontManager.render(
        this.amount2, this.amount3, this.amount4, 30.0F, 8.0F, instance.computeInt(value2 * 0.1F));
    fontManager.render(
        this.amount2 + 1.0F,
        this.amount3 + 1.0F,
        this.amount4 - 2.0F,
        28.0F,
        7.0F,
        1.0F,
        instance.computeInt(value3 * 0.45F));
    fontManager.render(
        this.amount2 + 1.0F,
        this.amount3 + 1.0F,
        this.amount4 - 2.0F,
        28.0F,
        7.0F,
        instance.computeInt(this.tweenValue4.getValue() * 0.16F));
    fontManager.render(
        this.amount2 + 3.0F,
        this.amount3 + 15.0F - 5.0F * value,
        2.0F,
        Math.max(0.01F, 10.0F * value),
        1.0F,
        instance.computeInt(0.8F * value));
    fontManager.computeFloat(
        this.guiModuleEntry.getString(),
        this.amount2 + 10.0F + 2.0F * value2,
        this.amount3 + 15.0F,
        14.0F,
        ArgbColors.lerp(instance.computeInt16(), instance.computeInt(), value),
        this.amount4 - 42.0F);
    fontManager.update2();
    fontManager.apply(this.amount2 + this.amount4 - 14.0F, this.amount3 + 15.0F);
    fontManager.apply2(value3 * (float) Math.PI / 2.0F);
    fontManager.render(
        0.0F,
        0.0F,
        5.0F,
        1.4F,
        ArgbColors.lerp(instance.computeInt18(), instance.computeInt(), value2),
        false);
    fontManager.update3();
    if (value3 > 0.005F) {
      fontManager.update2();
      fontManager.render(this.amount2, this.amount3 + 30.0F, this.amount4, this.amount5 * value3);
      fontManager.apply3(value3);
      fontManager.apply(this.amount2, this.amount3 + 30.0F);
      fontManager.render(0.0F, 0.0F, this.amount4, this.amount5, 8.0F, instance.computeInt4());
      fontManager.render(
          0.5F,
          0.5F,
          this.amount4 - 1.0F,
          this.amount5 - 1.0F,
          8.0F,
          1.0F,
          instance.computeInt14());
      fontManager.computeFloat("Keybind", 12.0F, 20.0F, 12.0F, instance.computeInt17());
      String text =
          this.enabled ? "Press key…" : KeyNames.getString(this.guiModuleEntry.computeInt());
      float floatValue4 =
          Math.min(
              this.amount4 - 82.0F, Math.max(54.0F, fontManager.computeFloat(text, 12.0F) + 20.0F));
      fontManager.render(
          this.amount4 - 12.0F - floatValue4,
          7.0F,
          floatValue4,
          26.0F,
          5.0F,
          instance.computeInt10());
      fontManager.render(
          this.amount4 - 12.0F - floatValue4,
          7.0F,
          floatValue4,
          26.0F,
          5.0F,
          1.0F,
          this.enabled ? instance.computeInt() : instance.computeInt14());
      fontManager.computeFloat(
          text, this.amount4 - 20.0F, 20.0F, 12.0F, instance.computeInt(), floatValue4 - 16.0F, 4);
      fontManager.render2(12.0F, 40.0F, this.amount4 - 12.0F, 40.0F, 1.0F, instance.computeInt14());
      String text2 = null;

      for (MenuPanelSupport5 menuPanelSupport53 :
          (java.lang.Iterable<MenuPanelSupport5>) (java.lang.Iterable<?>) (this.list)) {
        if (!(menuPanelSupport53.amount3 < 0.0F)) {
          if (!menuPanelSupport53.string.equals(text2)) {
            fontManager.computeFloat(
                menuPanelSupport53.string.toUpperCase(Locale.ROOT),
                12.0F,
                menuPanelSupport53.amount3 - 13.0F,
                11.0F,
                instance.computeInt17(),
                this.amount4 - 24.0F);
            text2 = menuPanelSupport53.string;
          }

          menuPanelSupport53.render(
              fontManager,
              this.amount4,
              test(
                  floatValue - this.amount2,
                  floatValue2 - this.amount3 - 30.0F,
                  0.0F,
                  menuPanelSupport53.amount3,
                  this.amount4,
                  menuPanelSupport53.computeFloat()),
              menuPanelSupport53 == this.menuPanelSupport5,
              menuPanelSupport53 == this.menuPanelSupport52);
        }
      }

      fontManager.update3();
    }

    fontManager.update3();
  }

  public boolean test(float amount6, float amount22, int intValue) {
    if (this.test()
        && this.menuPanelSupport5 != null
        && this.menuPanelSupport5.settingEntry.isVisible()
        && test(
            amount6,
            amount22,
            this.amount2,
            this.amount3 + 30.0F + this.menuPanelSupport5.amount3,
            this.amount4,
            this.menuPanelSupport5.computeFloat())
        && this.menuPanelSupport5.test2(intValue)) {
      return true;
    }

    if (test(amount6, amount22, this.amount2, this.amount3, this.amount4, 30.0F)) {
      if (AnimationPreferences.isEnabled()) {
        this.tweenValue4.snapTo(1.0F);
        this.tweenValue4.animateTo(0.0F);
      }

      this.update3();
      if (intValue == 1 || intValue == 0 && amount6 > this.amount2 + this.amount4 - 30.0F) {
        this.apply(!this.test());
        ClientAudio.update5();
      } else if (intValue == 0) {
        ClientAudio.apply(this.guiModuleEntry.test2());
      } else if (intValue == 2) {
        this.apply(true);
        this.enabled = true;
      }

      return true;
    } else if (this.test() && !(this.tweenValue3.getValue() < 0.98F)) {
      float floatValue = amount6 - this.amount2;
      float floatValue2 = amount22 - this.amount3 - 30.0F;
      if (test(floatValue, floatValue2, 0.0F, 0.0F, this.amount4, 40.0F)) {
        this.update3();
        this.enabled = true;
        return true;
      }

      for (MenuPanelSupport5 menuPanelSupport53 :
          (java.lang.Iterable<MenuPanelSupport5>) (java.lang.Iterable<?>) (this.list)) {
        if (menuPanelSupport53.amount3 >= 0.0F
            && test(
                floatValue,
                floatValue2,
                0.0F,
                menuPanelSupport53.amount3,
                this.amount4,
                menuPanelSupport53.computeFloat())) {
          this.update3();
          this.menuPanelSupport5 = menuPanelSupport53;
          if (menuPanelSupport53.settingEntry instanceof NumberSettingView && intValue == 0) {
            this.menuPanelSupport52 = menuPanelSupport53;
            menuPanelSupport53.apply(floatValue, this.amount4);
          } else if (intValue == 0 || intValue == 1) {
            menuPanelSupport53.apply(
                intValue == 1 ? -1 : 1, this.menuPanelSupport4, new ClientData3(amount6, amount22));
          }

          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public void apply(float floatValue, float floatValue2) {
    if (this.menuPanelSupport52 != null && this.menuPanelSupport52.settingEntry.isVisible()) {
      this.menuPanelSupport52.apply(floatValue - this.amount2, this.amount4);
    }
  }

  public void update2() {
    this.menuPanelSupport52 = null;
  }

  public void update3() {
    this.enabled = false;
    this.menuPanelSupport52 = this.menuPanelSupport5 = null;
    this.list.forEach(
        (referenceArgument69_0) ->
            ((bunnythekenzo.client.ui.MenuPanelSupport5) referenceArgument69_0).update());
  }

  public boolean test(int intValue, boolean flag) {
    if (this.enabled) {
      if (intValue != 256) {
        this.guiModuleEntry.apply(intValue != 261 && intValue != 259 ? intValue : -1);
      }

      this.enabled = false;
      ClientAudio.update5();
      return true;
    } else if (this.menuPanelSupport5 == null || !this.menuPanelSupport5.settingEntry.isVisible()) {
      return false;
    } else {
      return this.menuPanelSupport5.test(intValue)
          ? true
          : (intValue == 263 || intValue == 262)
              && this.menuPanelSupport5.test(intValue == 263 ? -1 : 1, flag);
    }
  }

  public boolean test(int intValue) {
    return this.menuPanelSupport5 != null
        && this.menuPanelSupport5.settingEntry.isVisible()
        && this.menuPanelSupport5.test3(intValue);
  }

  public void focus(SettingEntry settingEntry) {
    this.update3();
    this.menuPanelSupport5 =
        (bunnythekenzo.client.ui.MenuPanelSupport5)
            (this.list.stream()
                .filter(
                    value -> {
                      return ((bunnythekenzo.client.ui.MenuPanelSupport5) (value)).settingEntry
                          == settingEntry;
                    })
                .findFirst()
                .orElseThrow());
  }

  public static boolean test(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + floatValue5
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + floatValue6;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    fontManager.render(
        floatValue,
        floatValue2 - 9.0F,
        36.0F,
        18.0F,
        9.0F,
        ArgbColors.lerp(-10201234, intValue, floatValue3));
    fontManager.render2(floatValue + 9.0F + 18.0F * floatValue3, floatValue2, 6.0F, -527880);
  }
}

package bunnythekenzo.client.ui;

import java.util.EnumMap;
import java.util.Map;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.SettingsPage;

public final class MenuPanelSupport3 implements AutoCloseable {
  public static final MenuTab[] menuTabArray =
      new MenuTab[] {
        MenuTab.CONFIGS, MenuTab.PROFILE, MenuTab.HUD_EDITOR, MenuTab.EMOTES, MenuTab.SETTINGS
      };
  public static final float amount = 800.0F;
  public static final float amount2 = 524.0F;
  public static final float amount3 = 38.0F;
  public final Map map = new EnumMap<>(MenuTab.class);
  public MenuTab menuTab;
  public MenuTab menuTab2;
  public MenuTab menuTab3;
  public final TweenValue tweenValue = new TweenValue(280.0F, 0.0F, Easing.OUT_QUART);
  public final TweenValue tweenValue2 = new TweenValue(180.0F, 1.0F, Easing.IN_QUAD);
  public final TweenValue tweenValue3 = new TweenValue(220.0F, 1.0F, Easing.OUT_QUART);
  public final TweenValue tweenValue4 = new TweenValue(150.0F, 0.0F);
  public boolean enabled;
  public float amount4;
  public float amount5;
  public float amount6 = 1.0F;
  public float amount7;

  public MenuPanelSupport3() {
    this.map.put(MenuTab.CONFIGS, new ConfigsPage());
    this.map.put(MenuTab.PROFILE, new ProfilePage());
    this.map.put(MenuTab.HUD_EDITOR, new HudEditorPage());
    this.map.put(MenuTab.EMOTES, new EmotesPage());
    this.map.put(MenuTab.SETTINGS, new SettingsPage());
  }

  public boolean test() {
    return this.menuTab2 != null;
  }

  public MenuTab getMenuTab() {
    return this.menuTab;
  }

  public void apply(MenuTab menuTab22) {
    if (this.menuTab == menuTab22) {
      this.update();
    } else {
      if (this.menuTab != null) {
        ((MenuPage) this.map.get(this.menuTab)).update5();
        ((MenuPage) this.map.get(this.menuTab)).update2();
      }

      this.menuTab3 = this.menuTab2 != menuTab22 ? this.menuTab2 : null;
      float floatValue = this.tweenValue.getValue() * this.tweenValue2.getValue();
      this.enabled = this.menuTab2 == null;
      this.tweenValue.snapTo(floatValue);
      this.tweenValue.animateTo(1.0F);
      this.tweenValue2.snapTo(1.0F);
      if (this.menuTab3 != null) {
        this.tweenValue3.snapTo(0.0F);
        this.tweenValue3.animateTo(1.0F);
      } else {
        this.tweenValue3.snapTo(1.0F);
      }

      this.menuTab = this.menuTab2 = menuTab22;
      ((MenuPage) this.map.get(menuTab22)).update();
      ClientAudio.update5();
    }
  }

  public void update() {
    if (this.menuTab != null) {
      ((MenuPage) this.map.get(this.menuTab)).update5();
      ((MenuPage) this.map.get(this.menuTab)).test();
      ((MenuPage) this.map.get(this.menuTab)).update2();
    }

    if (this.menuTab != null) {
      this.tweenValue.snapTo(this.tweenValue.getValue());
      this.tweenValue2.animateTo(0.0F);
      this.enabled = false;
      this.menuTab = null;
    }
  }

  public void apply(FontManager fontManager) {
    if (this.menuTab2 != null) {
      ((MenuPage) this.map.get(this.menuTab2)).apply(fontManager);
    }

    if (this.menuTab3 != null) {
      ((MenuPage) this.map.get(this.menuTab3)).apply(fontManager);
    }

    if (this.enabled) {
      this.tweenValue.snapTo(0.0F);
      this.tweenValue.animateTo(1.0F);
      this.enabled = false;
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    if (this.menuTab2 != null) {
      float floatValue5 = this.tweenValue.getValue() * this.tweenValue2.getValue();
      if (this.menuTab == null && floatValue5 <= 0.001F) {
        this.menuTab2 = this.menuTab3 = null;
      } else {
        this.amount6 =
            Math.max(
                0.1F,
                Math.min(
                    1.0F,
                    Math.min((floatValue3 - 24.0F) / 800.0F, (floatValue4 - 116.0F) / 524.0F)));
        this.amount6 *= 0.985F + 0.015F * floatValue5;
        this.amount4 = (floatValue3 - 800.0F * this.amount6) / 2.0F;
        this.amount5 = 58.0F + (floatValue4 - 116.0F - 524.0F * this.amount6) / 2.0F;
        this.amount5 += 14.0F * (1.0F - floatValue5);
        fontManager.update2();
        fontManager.apply3(floatValue5);
        fontManager.update2();
        fontManager.apply(this.amount4, this.amount5);
        fontManager.apply(this.amount6);
        ColorPalette instance = ColorPalette.getInstance();
        fontManager.render(
            0.0F,
            0.0F,
            800.0F,
            524.0F,
            12.0F,
            ArgbColors.withOpacity(instance.computeInt3(), 1.0F),
            ArgbColors.withOpacity(instance.computeInt4(), 1.0F),
            true);
        fontManager.render(0.0F, 0.0F, 800.0F, 524.0F, 12.0F, 1.0F, instance.computeInt14());
        this.tweenValue4.animateTo(
            this.menuTab != null
                    && MenuPanelSupport.test(
                        this.computeFloat(floatValue),
                        this.computeFloat2(floatValue2),
                        758.0F,
                        0.0F,
                        42.0F,
                        38.0F)
                ? 1.0F
                : 0.0F);
        fontManager.render(
            762.0F,
            5.0F,
            30.0F,
            28.0F,
            7.0F,
            instance.computeInt(0.12F * this.tweenValue4.getValue()));
        fontManager.render5(
            772.0F,
            13.0F,
            12.0F,
            1.5F,
            ArgbColors.lerp(
                instance.computeInt18(), instance.computeInt(), this.tweenValue4.getValue()));
        fontManager.render(12.0F, 37.0F, 776.0F, 1.0F, 0.0F, instance.computeInt13());
        float value = this.tweenValue3.getValue();
        this.amount7 = 8.0F * (1.0F - value);
        if (this.menuTab3 != null && value < 0.999F) {
          this.render(
              fontManager, this.menuTab3, 1.0F - value, -8.0F * value, Float.NaN, Float.NaN);
        } else {
          this.menuTab3 = null;
        }

        this.render(
            fontManager,
            this.menuTab2,
            value,
            this.amount7,
            this.menuTab == null ? Float.NaN : this.computeFloat(floatValue) - this.amount7,
            this.menuTab == null ? Float.NaN : this.computeFloat2(floatValue2));
        fontManager.update3();
        fontManager.update3();
      }
    }
  }

  public void render(
      FontManager fontManager,
      MenuTab menuTab4,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    fontManager.update2();
    fontManager.apply3(floatValue);
    fontManager.apply(floatValue2, 0.0F);
    fontManager.computeFloat(
        menuTab4.getLabel(), 20.0F, 19.0F, 15.0F, ColorPalette.getInstance().computeInt16());
    fontManager.render(1.0F, 38.0F, 798.0F, 485.0F);
    ((MenuPage) this.map.get(menuTab4))
        .render(
            fontManager,
            0.0F,
            38.0F,
            800.0F,
            486.0F,
            floatValue > 0.98F ? floatValue3 : Float.NaN,
            floatValue > 0.98F ? floatValue4 : Float.NaN);
    fontManager.update3();
  }

  public float computeFloat(float floatValue) {
    return (floatValue - this.amount4) / this.amount6;
  }

  public float computeFloat2(float floatValue) {
    return (floatValue - this.amount5) / this.amount6;
  }

  public void render(float floatValue, float floatValue2, int intValue) {
    if (this.menuTab != null) {
      float value = this.computeFloat(floatValue);
      float float2 = this.computeFloat2(floatValue2);
      if (intValue == 0 && MenuPanelSupport.test(value, float2, 758.0F, 0.0F, 42.0F, 38.0F)) {
        this.update();
      } else if (this.tweenValue3.getValue() >= 0.98F) {
        ((MenuPage) this.map.get(this.menuTab)).test(value - this.amount7, float2, intValue);
      }
    }
  }

  public void apply(float floatValue, float floatValue2) {
    if (this.menuTab != null && this.tweenValue3.getValue() >= 0.98F) {
      ((MenuPage) this.map.get(this.menuTab))
          .apply(this.computeFloat(floatValue) - this.amount7, this.computeFloat2(floatValue2));
    }
  }

  public void update2() {
    if (this.menuTab != null) {
      ((MenuPage) this.map.get(this.menuTab)).update5();
    }
  }

  public void render(float floatValue, float floatValue2, double doubleValue) {
    if (this.menuTab != null && this.tweenValue3.getValue() >= 0.98F) {
      ((MenuPage) this.map.get(this.menuTab))
          .test(
              this.computeFloat(floatValue) - this.amount7,
              this.computeFloat2(floatValue2),
              doubleValue);
    }
  }

  public boolean test(int intValue) {
    if (this.menuTab == null) {
      return false;
    }

    if (((MenuPage) this.map.get(this.menuTab)).test2(intValue)) {
      return true;
    }

    if (intValue == 256) {
      if (!((MenuPage) this.map.get(this.menuTab)).test()) {
        this.update();
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean test2(int intValue) {
    return this.menuTab != null && ((MenuPage) this.map.get(this.menuTab)).test(intValue);
  }

  public String getString() {
    return this.menuTab == null ? null : ((MenuPage) this.map.get(this.menuTab)).getString();
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return new ClientData3(
        this.amount4 + floatValue * this.amount6, this.amount5 + floatValue2 * this.amount6);
  }

  @Override
  public void close() {
    this.update();
    this.menuTab = this.menuTab2 = this.menuTab3 = null;
    this.map
        .values()
        .forEach(
            (referenceArgument70_0) ->
                ((bunnythekenzo.client.ui.MenuPage) referenceArgument70_0).update6());
  }
}

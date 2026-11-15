package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.StaggeredTransition;
import bunnythekenzo.client.ToggleSwitchAnimation;
import bunnythekenzo.client.WorldSignalSupport;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.OverlayRenderer;
import bunnythekenzo.client.setting.SettingWidget;

public final class HudEditorPage implements MenuPage {
  public static final float amount = 300.0F;
  public static final float amount2 = 30.0F;
  public static final float amount3 = 2.0F;
  public static final float amount4 = 30.0F;
  public static final float amount5 = 16.0F;
  public static final float amount6 = 46.0F;
  public static final float amount7 = 12.0F;
  public static final float amount8 = 52.0F;
  public static final float amount9 = 16.0F;
  public static final float amount10 = 4.0F;
  public final List list = JsonSupport2.getList3();
  public final List list2 = new ArrayList();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(20.0F, 380.0F);
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final ToggleSwitchAnimation toggleSwitchAnimation;
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public HudWidget hudWidget;
  public SettingWidget settingWidget;
  public SettingWidget settingWidget2;
  public float amount11;
  public float amount12;
  public float amount13;
  public float amount14;
  public float amount15;
  public float amount16;
  public float amount17;
  public float amount18;
  public String string;

  public HudEditorPage() {
    this.toggleSwitchAnimation =
        new ToggleSwitchAnimation(GuiPreferences.getGuiPreferences().showHudControl.getValue());

    for (HudWidget hudWidget2 :
        (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (this.list)) {
      this.list2.add(new HudEditorPageSupport(hudWidget2));
    }
  }

  public float computeFloat2() {
    return this.amount17 / Math.max(1.0F, OverlayRenderer.computeFloat4());
  }

  @Override
  public void update() {
    this.staggeredTransition.apply(this.list.size());
  }

  @Override
  public void update2() {
    this.staggeredTransition.apply2(this.list.size());
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount112,
      float amount122,
      float amount132,
      float amount142,
      float floatValue,
      float floatValue2) {
    this.amount11 = amount112;
    this.amount12 = amount122;
    this.amount13 = amount132;
    this.amount14 = amount142;
    this.string = null;
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(
        amount112 + 300.0F - 1.0F, amount122, 1.0F, amount142, 0.0F, instance.computeInt13());
    this.render(fontManager, instance, amount112, amount122, floatValue, floatValue2);
    this.render(
        fontManager,
        instance,
        amount112,
        amount122 + 46.0F,
        amount142 - 46.0F,
        floatValue,
        floatValue2);
    this.render(
        fontManager,
        instance,
        amount112 + 300.0F,
        amount122,
        amount132 - 300.0F,
        amount142,
        floatValue,
        floatValue2);
  }

  @Override
  public float computeFloat() {
    return 300.0F;
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    GuiPreferences guiPreferences = GuiPreferences.getGuiPreferences();
    float floatValue5 = floatValue + 12.0F;
    float floatValue6 = floatValue2 + 10.0F;
    float value =
        this.animationIntensitySupport.computeFloat(
            floatValue3 >= floatValue5
                && floatValue3 <= floatValue5 + 275.0F
                && floatValue4 >= floatValue6
                && floatValue4 <= floatValue6 + 28.0F);
    boolean flag = guiPreferences.showHudControl.getValue();
    this.toggleSwitchAnimation.apply2(flag);
    fontManager.render(
        floatValue5,
        floatValue6,
        275.0F,
        28.0F,
        9.0F,
        ArgbColors.lerp(colorPalette.computeInt10(), colorPalette.computeInt11(), value));
    fontManager.render(
        floatValue5,
        floatValue6,
        275.0F,
        28.0F,
        9.0F,
        1.0F,
        flag ? colorPalette.computeInt(0.45F) : ArgbColors.withOpacity(-1, 0.08F));
    fontManager.computeFloat(
        "Show HUD",
        floatValue5 + 12.0F,
        floatValue6 + 14.0F,
        13.5F,
        flag ? colorPalette.computeInt16() : colorPalette.computeInt18());
    this.toggleSwitchAnimation.render(
        fontManager, floatValue5 + 275.0F - 30.0F - 10.0F, floatValue6 + 6.0F, 30.0F, 16.0F, value);
    if (floatValue3 >= floatValue5
        && floatValue3 <= floatValue5 + 275.0F
        && floatValue4 >= floatValue6
        && floatValue4 <= floatValue6 + 28.0F) {
      this.string = guiPreferences.showHudControl.getDescription();
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    boolean flag = GuiPreferences.getGuiPreferences().showHudControl.getValue();
    this.smoothScrollState.apply(this.computeFloat3() + 12.0F, floatValue3);
    float amount19 = floatValue2 + 4.0F - this.smoothScrollState.computeFloat();
    fontManager.update2();
    fontManager.render(floatValue, floatValue2, 299.0F, floatValue3);
    fontManager.apply3(flag ? 1.0F : 0.4F);

    for (int index = 0; index < this.list.size(); index++) {
      HudEditorPageSupport hudEditorPageSupport = (HudEditorPageSupport) this.list2.get(index);
      hudEditorPageSupport.amount = amount19;
      float floatValue6 = Math.clamp(hudEditorPageSupport.dampedSpring2.advance(), 0.0F, 1.0F);
      float floatValue7 = hudEditorPageSupport.amount2 * floatValue6;
      float floatValue8 = 30.0F + floatValue7;
      if (!(amount19 > floatValue2 + floatValue3 + 24.0F)
          && !(amount19 + floatValue8 < floatValue2 - 24.0F)) {
        this.render(
            fontManager,
            colorPalette,
            index,
            floatValue + 12.0F,
            amount19,
            275.0F,
            floatValue4,
            floatValue5,
            this.staggeredTransition.computeFloat(index),
            SmoothScrollState.computeFloat(
                amount19, amount19 + 30.0F, floatValue2, floatValue2 + floatValue3),
            flag);
        if (floatValue6 > 0.004F) {
          this.render(
              fontManager,
              hudEditorPageSupport,
              floatValue + 12.0F + 16.0F,
              amount19 + 30.0F,
              259.0F,
              floatValue6,
              floatValue2,
              floatValue2 + floatValue3,
              floatValue4,
              floatValue5,
              flag);
        }

        amount19 += floatValue8 + 2.0F;
      } else {
        hudEditorPageSupport.animationIntensitySupport.update();

        for (SettingWidget settingWidget3 :
            (java.lang.Iterable<SettingWidget>)
                (java.lang.Iterable<?>) (hudEditorPageSupport.list)) {
          settingWidget3.update7();
        }

        amount19 += floatValue8 + 2.0F;
      }
    }

    fontManager.update3();
    if (floatValue4 >= floatValue
        && floatValue4 <= floatValue + 300.0F
        && floatValue5 >= floatValue2
        && floatValue5 <= floatValue2 + floatValue3) {
      this.smoothScrollState.update();
    }

    this.smoothScrollState.render(
        fontManager, floatValue + 300.0F - 4.0F, floatValue2, floatValue3);
  }

  public float computeFloat3() {
    float floatValue = 0.0F;

    for (HudEditorPageSupport hudEditorPageSupport :
        (java.lang.Iterable<HudEditorPageSupport>) (java.lang.Iterable<?>) (this.list2)) {
      floatValue +=
          30.0F
              + hudEditorPageSupport.amount2
                  * Math.clamp(hudEditorPageSupport.dampedSpring2.advance(), 0.0F, 1.0F)
              + 2.0F;
    }

    return floatValue;
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      int intValue,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      boolean flag) {
    HudEditorPageSupport hudEditorPageSupport = (HudEditorPageSupport) this.list2.get(intValue);
    if (floatValue6 * floatValue7 <= 0.004F) {
      hudEditorPageSupport.animationIntensitySupport.update();
    } else {
      HudWidget hudWidget2 = (HudWidget) this.list.get(intValue);
      float value =
          hudEditorPageSupport.animationIntensitySupport.computeFloat(
              flag && this.test(intValue, floatValue4, floatValue5));
      if (value > 0.5F) {
        this.string = hudWidget2.getString3();
      }

      hudEditorPageSupport.dampedSpring.setTarget(hudWidget2.isEnabled2() ? 1.0F : 0.0F);
      float floatValue8 = Math.clamp(hudEditorPageSupport.dampedSpring.advance(), 0.0F, 1.0F);
      float floatValue9 = Math.clamp(hudEditorPageSupport.dampedSpring2.advance(), 0.0F, 1.0F);
      fontManager.update2();
      fontManager.apply3(floatValue6 * floatValue7);
      fontManager.apply((1.0F - floatValue6) * -18.0F, 0.0F);
      if (value > 0.01F || floatValue8 > 0.01F || floatValue9 > 0.01F) {
        fontManager.render(
            floatValue,
            floatValue2,
            floatValue3,
            30.0F,
            9.0F,
            ArgbColors.withOpacity(
                colorPalette.computeInt11(),
                Math.max(Math.max(value, floatValue9 * 0.6F), floatValue8 * 0.5F) * 0.7F));
      }

      float floatValue10 = floatValue + 12.0F;
      if (hudEditorPageSupport.test()) {
        fontManager.update2();
        fontManager.apply(floatValue + 14.0F, floatValue2 + 15.0F);
        fontManager.apply2(floatValue9 * 1.5708F);
        fontManager.render(
            0.0F,
            0.0F,
            3.2F,
            1.4F,
            ArgbColors.lerp(
                colorPalette.computeInt19(),
                colorPalette.computeInt17(),
                Math.max(value, floatValue9)),
            false);
        fontManager.update3();
        floatValue10 = floatValue + 26.0F;
      }

      int value2 = ArgbColors.lerp(colorPalette.computeInt17(), colorPalette.computeInt16(), value);
      if (hudWidget2.isEnabled2()) {
        value2 = ArgbColors.lerp(value2, colorPalette.computeInt(), 0.7F);
      }

      float floatValue11 = floatValue3 - (floatValue10 - floatValue) - 52.0F;
      float value3 =
          fontManager.computeFloat(
              hudWidget2.getString22(),
              floatValue10,
              floatValue2 + 15.0F,
              13.5F,
              value2,
              floatValue11);
      if (hudWidget2.getAmount7() != 1.0F) {
        fontManager.computeFloat(
            Math.round(hudWidget2.getAmount7() * 100.0F) + "%",
            floatValue10 + value3 + 8.0F,
            floatValue2 + 15.0F + 0.5F,
            9.5F,
            colorPalette.computeInt18());
      }

      hudEditorPageSupport.toggleSwitchAnimation.render(
          fontManager,
          floatValue + floatValue3 - 30.0F - 10.0F,
          floatValue2 + 7.0F,
          30.0F,
          16.0F,
          value);
      fontManager.update3();
    }
  }

  public void render(
      FontManager fontManager,
      HudEditorPageSupport hudEditorPageSupport,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      boolean flag) {
    float floatValue9 = hudEditorPageSupport.amount2 * floatValue4;
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.update2();
    fontManager.render(
        floatValue - 16.0F,
        Math.max(floatValue2, floatValue5),
        floatValue3 + 16.0F,
        Math.max(
            0.0F,
            Math.min(floatValue2 + floatValue9, floatValue6) - Math.max(floatValue2, floatValue5)));
    fontManager.render(
        floatValue - 8.0F,
        floatValue2 + 2.0F,
        1.0F,
        Math.max(0.0F, floatValue9 - 6.0F),
        0.0F,
        instance.computeInt(0.28F));
    float floatValue10 = floatValue2 + 4.0F;

    for (SettingWidget settingWidget3 :
        (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (hudEditorPageSupport.list)) {
      float value =
          SmoothScrollState.computeFloat(
              floatValue10, floatValue10 + settingWidget3.computeFloat(), floatValue5, floatValue6);
      settingWidget3.render(
          fontManager,
          floatValue,
          floatValue10,
          floatValue3,
          flag ? floatValue7 : Float.NaN,
          flag ? floatValue8 : Float.NaN,
          floatValue4,
          value);
      if (settingWidget3.test4(floatValue7, floatValue8) && flag && floatValue4 > 0.6F) {
        this.string = settingWidget3.getSettingEntry().getDescription();
      }

      floatValue10 += settingWidget3.computeFloat() + 4.0F;
    }

    fontManager.update3();
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 = floatValue3 - 48.0F;
    float floatValue8 = floatValue4 - 96.0F;
    ScreenCapture.ensureInitialized();
    float amount172 = Math.min(floatValue7, floatValue8 * 1.7777778F);
    ScreenCapture.ensureInitialized();
    float amount182 = amount172 / 1.7777778F;
    this.amount15 = floatValue + (floatValue3 - amount172) / 2.0F;
    this.amount16 = floatValue2 + 42.0F;
    this.amount17 = amount172;
    this.amount18 = amount182;
    fontManager.setAmount(1.6F);
    fontManager.computeFloat(
        "YOUR SCREEN", this.amount15, floatValue2 + 22.0F, 9.5F, colorPalette.computeInt18());
    fontManager.setAmount(0.0F);
    this.render2(
        fontManager,
        colorPalette,
        this.amount15 + amount172,
        floatValue2 + 22.0F,
        floatValue5,
        floatValue6);
    int value = ScreenCapture.computeInt();
    fontManager.render(
        this.amount15,
        this.amount16,
        amount172,
        amount182,
        8.0F,
        12.0F,
        4.0F,
        ArgbColors.withOpacity(-16777216, 0.45F));
    if (value > 0) {
      fontManager.render(value, this.amount15, this.amount16, amount172, amount182, 1.0F, 8.0F);
    } else {
      fontManager.render(
          this.amount15,
          this.amount16,
          amount172,
          amount182,
          8.0F,
          ArgbColors.withOpacity(-16119792, 0.85F));
      fontManager.computeFloat2(
          "Join a world to see your screen here",
          this.amount15 + amount172 / 2.0F,
          this.amount16 + amount182 / 2.0F,
          11.0F,
          colorPalette.computeInt19());
    }

    fontManager.render(
        this.amount15,
        this.amount16,
        amount172,
        amount182,
        8.0F,
        1.0F,
        colorPalette.computeInt14());
    fontManager.update2();
    fontManager.render(this.amount15, this.amount16, amount172, amount182);
    WorldSignalSupport.render(
        fontManager,
        this.amount15,
        this.amount16,
        amount172,
        amount182,
        this.computeFloat2(),
        true,
        floatValue5,
        floatValue6);
    fontManager.update3();
  }

  public void render2(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = floatValue - 92.0F;
    float floatValue6 = floatValue2 - 11.0F;
    float value =
        this.animationIntensitySupport2.computeFloat(
            floatValue3 >= floatValue5
                && floatValue3 <= floatValue5 + 92.0F
                && floatValue4 >= floatValue6
                && floatValue4 <= floatValue6 + 22.0F);
    fontManager.render(
        floatValue5,
        floatValue6,
        92.0F,
        22.0F,
        11.0F,
        1.0F,
        ArgbColors.withOpacity(-1, 0.1F + 0.2F * value));
    fontManager.computeFloat2(
        "Reset layout",
        floatValue5 + 46.0F,
        floatValue2,
        11.0F,
        ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), value));
  }

  public boolean test(int intValue, float floatValue, float floatValue2) {
    float floatValue3 = this.amount11 + 12.0F;
    float amount19 = ((HudEditorPageSupport) this.list2.get(intValue)).amount;
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + 275.0F
        && floatValue2 >= amount19
        && floatValue2 <= amount19 + 30.0F;
  }

  public boolean test2(int intValue, float floatValue, float floatValue2) {
    float floatValue3 = this.amount11 + 300.0F - 13.0F;
    return this.test(intValue, floatValue, floatValue2) && floatValue >= floatValue3 - 52.0F;
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount11 + 12.0F;
    float floatValue4 = this.amount12 + 10.0F;
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + 275.0F
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + 46.0F - 18.0F;
  }

  public boolean test2(float floatValue, float floatValue2) {
    float floatValue3 = this.amount15 + this.amount17 - 92.0F;
    float floatValue4 = this.amount12 + 22.0F - 11.0F;
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + 92.0F
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + 22.0F;
  }

  public boolean test3(float floatValue, float floatValue2) {
    return floatValue >= this.amount15
        && floatValue <= this.amount15 + this.amount17
        && floatValue2 >= this.amount16
        && floatValue2 <= this.amount16 + this.amount18;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    GuiPreferences guiPreferences = GuiPreferences.getGuiPreferences();
    if (this.test(floatValue, floatValue2)) {
      boolean flag = guiPreferences.showHudControl.toggle();
      this.toggleSwitchAnimation.apply(flag);
      guiPreferences.update();
      ClientAudio.apply(flag);
      return true;
    }

    if (this.test2(floatValue, floatValue2)) {
      JsonSupport2.update();
      JsonSupport2.update4();
      ClientAudio.update7();
      return true;
    }

    if (!guiPreferences.showHudControl.getValue()) {
      return this.test4(floatValue, floatValue2);
    }

    FontManager instance = FontManager.getInstance();
    if (instance != null
        && this.test3(floatValue, floatValue2)
        && WorldSignalSupport.test(
            instance,
            this.amount15,
            this.amount16,
            this.amount17,
            this.amount18,
            this.computeFloat2(),
            floatValue,
            floatValue2)) {
      this.update3();
      return true;
    }

    for (int index = 0; index < this.list2.size(); index++) {
      HudEditorPageSupport hudEditorPageSupport = (HudEditorPageSupport) this.list2.get(index);
      if (!(hudEditorPageSupport.dampedSpring2.advance() < 0.5F)) {
        for (SettingWidget settingWidget3 :
            (java.lang.Iterable<SettingWidget>)
                (java.lang.Iterable<?>) (hudEditorPageSupport.list)) {
          if (settingWidget3.test(floatValue, floatValue2, intValue)) {
            if (this.settingWidget2 != null && this.settingWidget2 != settingWidget3) {
              this.settingWidget2.update6();
            }

            this.settingWidget2 = settingWidget3.test2() ? settingWidget3 : null;
            this.settingWidget = settingWidget3;
            JsonSupport2.update4();
            return true;
          }
        }
      }
    }

    this.update3();

    for (int index2 = 0; index2 < this.list.size(); index2++) {
      if (this.test(index2, floatValue, floatValue2)) {
        if (this.test2(index2, floatValue, floatValue2)) {
          boolean flag2 = ((HudWidget) this.list.get(index2)).test6();
          ((HudEditorPageSupport) this.list2.get(index2)).toggleSwitchAnimation.apply(flag2);
          ((HudEditorPageSupport) this.list2.get(index2)).animationIntensitySupport.apply(true);
          JsonSupport2.update4();
          ClientAudio.apply(flag2);
        } else {
          this.apply((HudWidget) this.list.get(index2));
        }

        return true;
      }
    }

    return this.test4(floatValue, floatValue2);
  }

  public void apply(HudWidget hudWidget2) {
    int intValue = this.list.indexOf(hudWidget2);
    if (intValue >= 0 && ((HudEditorPageSupport) this.list2.get(intValue)).test()) {
      boolean flag = this.hudWidget == hudWidget2;
      this.hudWidget = flag ? null : hudWidget2;

      for (int index = 0; index < this.list2.size(); index++) {
        ((HudEditorPageSupport) this.list2.get(index))
            .dampedSpring2.setTarget(this.list.get(index) == this.hudWidget ? 1.0F : 0.0F);
      }

      if (flag) {
        ClientAudio.update7();
      } else {
        ClientAudio.update6();
      }
    }
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.settingWidget != null) {
      this.settingWidget.apply(floatValue, floatValue2);
    } else {
      FontManager instance = FontManager.getInstance();
      if (instance != null) {
        WorldSignalSupport.render(
            instance,
            this.amount15,
            this.amount16,
            this.amount17,
            this.amount18,
            this.computeFloat2(),
            floatValue,
            floatValue2);
      }
    }
  }

  @Override
  public void update5() {
    if (this.settingWidget != null) {
      this.settingWidget.update5();
      this.settingWidget = null;
      JsonSupport2.update4();
    }

    WorldSignalSupport.update();

    for (HudEditorPageSupport hudEditorPageSupport :
        (java.lang.Iterable<HudEditorPageSupport>) (java.lang.Iterable<?>) (this.list2)) {
      hudEditorPageSupport.animationIntensitySupport.apply(false);
    }

    this.animationIntensitySupport.apply(false);
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (!this.test4(floatValue, floatValue2)) {
      return false;
    } else if (floatValue <= this.amount11 + 300.0F) {
      this.smoothScrollState.test(doubleValue);
      return true;
    } else {
      FontManager instance = FontManager.getInstance();
      return instance != null
              && GuiPreferences.getGuiPreferences().showHudControl.getValue()
              && this.test3(floatValue, floatValue2)
          ? WorldSignalSupport.test(
              instance,
              this.amount15,
              this.amount16,
              this.amount17,
              this.amount18,
              this.computeFloat2(),
              floatValue,
              floatValue2,
              doubleValue)
          : false;
    }
  }

  @Override
  public boolean test2(int intValue) {
    if (this.settingWidget2 == null) {
      return false;
    }

    boolean flag = this.settingWidget2.test2(intValue);
    if (!this.settingWidget2.test2()) {
      this.settingWidget2 = null;
      JsonSupport2.update4();
    }

    return flag;
  }

  @Override
  public boolean test(int intValue) {
    if (this.settingWidget2 == null) {
      return false;
    }

    boolean flag = this.settingWidget2.test(intValue);
    if (flag) {
      JsonSupport2.update4();
    }

    return flag;
  }

  @Override
  public boolean test() {
    if (this.settingWidget2 != null && this.settingWidget2.test2()) {
      this.update3();
      return true;
    } else if (this.hudWidget != null) {
      this.apply(this.hudWidget);
      return true;
    } else {
      return false;
    }
  }

  public void update3() {
    if (this.settingWidget2 != null) {
      this.settingWidget2.update6();
    }

    this.settingWidget2 = null;
  }

  @Override
  public String getString() {
    return this.string;
  }

  public boolean test4(float floatValue, float floatValue2) {
    return floatValue >= this.amount11
        && floatValue <= this.amount11 + this.amount13
        && floatValue2 >= this.amount12
        && floatValue2 <= this.amount12 + this.amount14;
  }
}

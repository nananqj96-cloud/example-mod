package bunnythekenzo.client.ui;

import java.util.EnumMap;
import java.util.Map;
import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModal;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.HoverTooltip;
import bunnythekenzo.client.MenuBackdropRenderer;
import bunnythekenzo.client.MenuLayout;
import bunnythekenzo.client.MenuParallaxRenderer;
import bunnythekenzo.client.ModuleSearchSupport2;
import bunnythekenzo.client.ModuleSearchSupport7;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.IconCache;
import bunnythekenzo.client.setting.SettingsPage;

public final class MenuRenderContext implements AutoCloseable {
  public static final float amount = 430.0F;
  public static final float amount2 = 260.0F;
  public static final float amount3 = 0.35F;
  public static final float amount4 = 300.0F;
  public static final float amount5 = 40.0F;
  public final Map map = new EnumMap<>(MenuTab.class);
  public final ModuleSearchSupport2 moduleSearchSupport2 = new ModuleSearchSupport2();
  public final HoverTooltip hoverTooltip = new HoverTooltip();
  public final MenuParallaxRenderer menuParallaxRenderer = new MenuParallaxRenderer();
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public boolean enabled;
  public final TweenValue tweenValue = new TweenValue(300.0F, 1.0F, Easing.OUT_EXPO);
  public MenuTab menuTab;
  public int count = 1;
  public TweenValue tweenValue2 = new TweenValue(430.0F, 0.0F, Easing.LINEAR);
  public boolean enabled2;
  public boolean enabled3;
  public float amount6;
  public float amount7;
  public final ModuleSearchSupport7 moduleSearchSupport7 = new ModuleSearchSupport7();
  public final DampedSpring dampedSpring = DampedSpring.createQuick();
  public final DampedSpring dampedSpring2 = DampedSpring.createQuick();
  public MenuLayout menuLayout;
  public float amount8;
  public float amount9 = 1.0F;
  public float amount10;
  public float amount11;
  public boolean enabled4;
  public static final float amount12 = 1.946768F;
  public MenuTab menuTab2 = MenuTab.MODULES;

  public boolean isEnabled() {
    return this.enabled;
  }

  public MenuRenderContext() {
    this.map.put(MenuTab.MODULES, new ModulesPage());
    this.map.put(MenuTab.HUD_EDITOR, new HudEditorPage());
    this.map.put(MenuTab.EMOTES, new EmotesPage());
    this.map.put(MenuTab.CONFIGS, new ConfigsPage());
    this.map.put(MenuTab.SETTINGS, new SettingsPage());
    this.map.put(MenuTab.PROFILE, new ProfilePage());
    this.tweenValue2.animateTo(1.0F);
    ClientAudio.update();
  }

  public MenuPage getMenuPage() {
    return (MenuPage) this.map.get(this.moduleSearchSupport2.getMenuTab());
  }

  public boolean test(String text) {
    return ((ModulesPage) this.map.get(MenuTab.MODULES)).test(text);
  }

  public void update() {
    this.moduleSearchSupport2.apply(
        MenuTab.EMOTES,
        (referenceArgument71_0) -> this.apply((bunnythekenzo.client.ui.MenuTab) referenceArgument71_0));
  }

  public void update2() {
    ((MenuPage) this.map.get(MenuTab.PROFILE)).update();
    this.moduleSearchSupport2.apply(
        MenuTab.PROFILE,
        (referenceArgument72_0) -> this.apply((bunnythekenzo.client.ui.MenuTab) referenceArgument72_0));
  }

  public void apply(GuiModuleEntry guiModuleEntry) {
    ((ModulesPage) this.map.get(MenuTab.MODULES)).apply(guiModuleEntry);
  }

  public void update3() {
    if (!this.enabled2) {
      this.enabled2 = true;
      GuiDialogManager.closeAll();
      this.tweenValue2 = new TweenValue(260.0F, this.tweenValue2.getValue(), Easing.LINEAR);
      this.tweenValue2.animateTo(0.0F);
      this.getMenuPage().update2();
      ClientAudio.update2();
    }
  }

  public boolean test() {
    return this.enabled2 && this.tweenValue2.getValue() <= 0.001F;
  }

  public float computeFloat() {
    return this.enabled2
        ? Easing.IN_QUAD.computeFloat(this.tweenValue2.getValue())
        : Easing.OUT_QUART.computeFloat(this.tweenValue2.getValue());
  }

  public void prepare(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.getMenuPage().apply(fontManager);
    if (this.menuTab != null && this.tweenValue.getValue() < 0.999F) {
      ((MenuPage) this.map.get(this.menuTab)).apply(fontManager);
    }

    float value = this.tweenValue2.getValue();
    if (!this.enabled2 && !this.enabled3 && value >= 0.35F) {
      this.enabled3 = true;
      this.getMenuPage().update();
    }

    this.amount8 =
        this.enabled2 ? Easing.IN_QUINT.computeFloat(value) : Easing.OUT_QUART.computeFloat(value);
    float floatValue5 =
        this.enabled2 ? Easing.IN_CUBIC.computeFloat(value) : Easing.OUT_QUART.computeFloat(value);
    this.amount10 = floatValue3 / 2.0F;
    this.amount11 = floatValue4 * 0.485F;
    this.amount9 =
        Math.max(
            0.1F,
            Math.min(
                1.0F, Math.min((floatValue3 - 36.0F) / 880.0F, (floatValue4 - 36.0F) / 556.0F)));
    boolean flag =
        AnimationPreferences.isEnabled() && !this.enabled2 && !GuiDialogManager.hasDialog();
    if (!this.enabled4) {
      this.dampedSpring.setTarget(
          flag
              ? Math.clamp((floatValue - this.amount10) / (800.0F * this.amount9), -0.5F, 0.5F)
                  * 0.012F
              : 0.0F);
      this.dampedSpring2.setTarget(
          flag
              ? Math.clamp((floatValue2 - this.amount11) / (486.0F * this.amount9), -0.5F, 0.5F)
                  * -0.012F
              : 0.0F);
    }

    this.menuLayout =
        new MenuLayout(
            0.0F,
            24.0F * (1.0F - floatValue5),
            -115.0F * (1.0F - floatValue5),
            -0.01F + this.dampedSpring.advance() + (1.0F - floatValue5) * 0.055F,
            -0.018F + this.dampedSpring2.advance() - (1.0F - floatValue5) * 0.09F,
            400.0F,
            243.0F,
            -480.0F,
            -580.0F);
    this.amount6 = this.amount7 = 0.0F;
    ClientData3 clientData32 = this.getClientData32(floatValue, floatValue2);
    boolean flag2 = !this.enabled2 && !GuiDialogManager.hasDialog() && value > 0.7F;
    float floatValue6 = flag2 ? clientData32.amount() : Float.NaN;
    float floatValue7 = flag2 ? clientData32.amount2() : Float.NaN;
    this.moduleSearchSupport7.paint(
        fontManager,
        this.menuLayout,
        () -> {
          this.render2(fontManager, floatValue6, floatValue7);
          this.render3(fontManager, floatValue6, floatValue7);
          this.render(fontManager, floatValue6, floatValue7);
        });
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    MenuBackdropRenderer.render(
        fontManager,
        floatValue3,
        floatValue4,
        this.amount10 - 800.0F * this.amount9 / 2.0F,
        this.amount11 - 486.0F * this.amount9 / 2.0F,
        800.0F * this.amount9,
        486.0F * this.amount9,
        this.amount8);
    fontManager.update2();
    fontManager.apply(this.amount10, this.amount11);
    fontManager.apply(this.amount9);
    this.moduleSearchSupport7.apply(fontManager, this.amount8);
    fontManager.update3();
    this.hoverTooltip.apply(
        !this.enabled2 && !GuiDialogManager.hasDialog() ? this.getMenuPage().getString() : null);
    this.hoverTooltip.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    GuiDialogManager.finishClosedDialog();
    GuiModal guiModal = GuiDialogManager.getCurrentDialog();
    if (guiModal != null) {
      guiModal.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    }
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    if (this.menuLayout == null) {
      return new ClientData3(Float.NaN, Float.NaN);
    }

    ClientData3 clientData3 = this.menuLayout.getClientData3(floatValue, floatValue2, 0.0F);
    return new ClientData3(
        this.amount10 + clientData3.amount() * this.amount9,
        this.amount11 + clientData3.amount2() * this.amount9);
  }

  public ClientData3 getClientData32(float floatValue, float floatValue2) {
    return this.menuLayout == null
        ? new ClientData3(Float.NaN, Float.NaN)
        : this.menuLayout.getClientData3(
            (floatValue - this.amount10) / this.amount9,
            (floatValue2 - this.amount11) / this.amount9);
  }

  public ClientData3 getClientData3(ClientData3 clientData3) {
    return new ClientData3(
        this.amount10 + clientData3.amount() * this.amount9,
        this.amount11 + clientData3.amount2() * this.amount9);
  }

  public ClientData3 getClientData3(String text, boolean flag) {
    return this.getClientData3(
        ((ModulesPage) this.map.get(MenuTab.MODULES)).getClientData3(text, flag));
  }

  public ClientData3 getClientData33(float floatValue, float floatValue2) {
    return this.getClientData3(
        ((ModulesPage) this.map.get(MenuTab.MODULES)).getClientData3(floatValue, floatValue2));
  }

  public ClientData3 getClientData3(MenuTab menuTab3) {
    return this.getClientData3(this.moduleSearchSupport2.getClientData3(menuTab3));
  }

  public MenuTab getMenuTab() {
    return this.moduleSearchSupport2.getMenuTab();
  }

  public boolean test2() {
    return ((ModulesPage) this.map.get(MenuTab.MODULES)).isEnabled();
  }

  @Override
  public void close() {
    this.moduleSearchSupport7.close();
    this.map
        .values()
        .forEach(
            (referenceArgument73_0) ->
                ((bunnythekenzo.client.ui.MenuPage) referenceArgument73_0).update6());
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2) {
    float floatValue3 = this.amount7 + 54.0F;
    float value = this.tweenValue.getValue();
    fontManager.update2();
    fontManager.render(this.amount6, floatValue3, 800.0F, 420.0F);
    if (this.menuTab != null && value < 0.999F) {
      fontManager.update2();
      fontManager.apply3(1.0F - Easing.IN_QUAD.computeFloat(value));
      fontManager.apply(-this.count * 40.0F * value, 0.0F);
      ((MenuPage) this.map.get(this.menuTab))
          .render(fontManager, this.amount6, floatValue3, 800.0F, 420.0F, Float.NaN, Float.NaN);
      fontManager.update3();
    } else if (this.menuTab != null) {
      this.menuTab = null;
    }

    fontManager.update2();
    fontManager.apply3(value);
    fontManager.apply(this.count * 40.0F * (1.0F - value), 0.0F);
    boolean flag = value > 0.98F;
    this.getMenuPage()
        .render(
            fontManager,
            this.amount6,
            floatValue3,
            800.0F,
            420.0F,
            flag ? floatValue : Float.NaN,
            flag ? floatValue2 : Float.NaN);
    fontManager.update3();
    fontManager.update3();
  }

  public void render2(FontManager fontManager, float floatValue, float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(
        this.amount6,
        this.amount7,
        800.0F,
        486.0F,
        16.0F,
        instance.computeInt3(),
        instance.computeInt4(),
        true);
    float floatValue3 = this.amount7 + 54.0F;
    fontManager.update2();
    fontManager.render(this.amount6, floatValue3, 800.0F, 420.0F);
    this.menuParallaxRenderer.render(
        fontManager,
        this.amount6,
        floatValue3,
        800.0F,
        420.0F,
        floatValue,
        floatValue2,
        this.getMenuPage().computeFloat());
    fontManager.update3();
    fontManager.render(0.5F, 0.5F, 799.0F, 485.0F, 15.5F, 1.0F, 1090519039, 301989887, true);
  }

  public void render3(FontManager fontManager, float floatValue, float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.update2();
    fontManager.render(this.amount6, this.amount7, 800.0F, 54.0F);
    fontManager.render(
        this.amount6,
        this.amount7,
        800.0F,
        54.0F,
        16.0F,
        16.0F,
        0.0F,
        0.0F,
        instance.computeInt5(),
        instance.computeInt6());
    this.render(fontManager, instance, 54.0F);
    this.apply(fontManager, 54.0F);
    fontManager.update3();
    fontManager.render(
        this.amount6, this.amount7 + 54.0F - 1.0F, 800.0F, 1.0F, 0.0F, instance.computeInt13());
    float value = this.computeFloat(fontManager, instance);
    this.moduleSearchSupport2.render(
        fontManager, value + 26.0F, this.amount7, 54.0F, floatValue, floatValue2);
    this.render(fontManager, instance, floatValue, floatValue2);
    float floatValue3 = this.amount6 + 800.0F - 154.0F;
    float floatValue4 = this.amount7 + 13.0F;
    float value2 =
        this.animationIntensitySupport2.computeFloat(
            !this.enabled2 && this.test(floatValue, floatValue2));
    fontManager.render(
        floatValue3,
        floatValue4,
        106.0F,
        28.0F,
        7.0F,
        instance.computeInt(0.045F + value2 * 0.09F));
    fontManager.render(
        floatValue3,
        floatValue4,
        106.0F,
        28.0F,
        7.0F,
        1.0F,
        instance.computeInt(0.16F + value2 * 0.22F));
    fontManager.computeFloat(
        "Dropdown",
        floatValue3 + 12.0F,
        floatValue4 + 14.0F,
        10.5F,
        ArgbColors.lerp(instance.computeInt17(), instance.computeInt16(), value2));
    fontManager.render(
        floatValue3 + 91.0F, floatValue4 + 14.0F, 6.0F, 1.2F, instance.computeInt(), true);
  }

  public void render(FontManager fontManager, ColorPalette colorPalette, float floatValue) {
    double doubleValue =
        AnimationPreferences.isEnabled() ? System.nanoTime() % 600000000000L / 1.0E9 : 0.0;
    float floatValue2 =
        this.amount6 + 800.0F * (0.2F + 0.1F * (float) Math.sin(doubleValue * 0.17));
    fontManager.render3(
        floatValue2,
        this.amount7 + floatValue * 0.62F,
        8.0F,
        54.0F,
        colorPalette.computeInt(0.055F));
  }

  public void apply(FontManager fontManager, float floatValue) {
    if (AnimationPreferences.isEnabled()) {
      double doubleValue = System.nanoTime() % 600000000000L / 1.0E9;
      float floatValue2 = (float) (doubleValue % 11.0 / 11.0);
      if (!(floatValue2 > 0.26F)) {
        float floatValue3 = floatValue2 / 0.26F;
        float floatValue4 = this.amount6 - 80.0F + floatValue3 * 960.0F;
        int value = ArgbColors.withOpacity(-1, 0.045F * (float) Math.sin(floatValue3 * Math.PI));
        fontManager.update2();
        fontManager.apply(floatValue4, this.amount7 + floatValue / 2.0F);
        fontManager.apply2(-0.32F);
        fontManager.render(
            -40.0F,
            -floatValue,
            40.0F,
            floatValue * 2.0F,
            0.0F,
            ArgbColors.withAlpha(-1, 0),
            value,
            false);
        fontManager.render(
            0.0F,
            -floatValue,
            40.0F,
            floatValue * 2.0F,
            0.0F,
            value,
            ArgbColors.withAlpha(-1, 0),
            false);
        fontManager.update3();
      }
    }
  }

  public float computeFloat(FontManager fontManager, ColorPalette colorPalette) {
    float floatValue = this.amount6 + 18.0F;
    float floatValue2 = this.amount7 + 15.0F;
    int png = IconCache.getPng("bunnythekenzo");
    if (png > 0) {
      fontManager.update2();
      fontManager.apply3(0.3F);
      fontManager.render(
          png,
          floatValue - 1.0F,
          floatValue2 - 1.0F,
          28.0F,
          26.0F,
          colorPalette.computeInt(0.4F));
      fontManager.update3();
      fontManager.render(
          png, floatValue, floatValue2, 26.0F, 24.0F, colorPalette.computeInt());
    } else {
      fontManager.computeFloat2(
          "BTK", floatValue + 13.0F, floatValue2 + 12.0F, 9.0F, colorPalette.computeInt());
    }

    return floatValue + 26.0F;
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float floatValue3 = this.amount6 + 800.0F - 24.0F;
    float floatValue4 = this.amount7 + 27.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test2(floatValue, floatValue2));
    if (value > 0.01F) {
      fontManager.render2(
          floatValue3, floatValue4, 12.0F, ArgbColors.withOpacity(-1, 0.07F * value));
    }

    fontManager.update2();
    fontManager.apply(floatValue3, floatValue4);
    fontManager.apply2(value * 0.2F);
    fontManager.render5(
        -5.0F,
        -5.0F,
        10.0F,
        1.5F,
        ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), value));
    fontManager.update3();
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount6 + 800.0F - 154.0F;
    float floatValue4 = this.amount7 + 13.0F;
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + 106.0F
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + 28.0F;
  }

  public boolean test2(float floatValue, float floatValue2) {
    float floatValue3 = this.amount6 + 800.0F - 24.0F;
    float floatValue4 = this.amount7 + 27.0F;
    float floatValue5 = floatValue - floatValue3;
    float floatValue6 = floatValue2 - floatValue4;
    return floatValue5 * floatValue5 + floatValue6 * floatValue6 <= 225.0F;
  }

  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (this.enabled2 || this.tweenValue2.getValue() < 0.7F) {
      return true;
    }

    if (GuiDialogManager.hasDialog()) {
      return GuiDialogManager.getCurrentDialog().mouseClicked(floatValue, floatValue2, intValue);
    }

    ClientData3 clientData32 = this.getClientData32(floatValue, floatValue2);
    floatValue = clientData32.amount();
    floatValue2 = clientData32.amount2();
    this.enabled4 = intValue == 0;
    if (this.enabled4) {
      this.dampedSpring.snapTo(this.dampedSpring.advance());
      this.dampedSpring2.snapTo(this.dampedSpring2.advance());
    }

    if (intValue == 0 && this.test(floatValue, floatValue2)) {
      this.enabled = true;
      this.update3();
      return true;
    } else if (this.test2(floatValue, floatValue2)) {
      ClientAudio.update5();
      this.update3();
      return true;
    } else if (this.moduleSearchSupport2.test(
        floatValue,
        floatValue2,
        (referenceArgument74_0) ->
            this.apply((bunnythekenzo.client.ui.MenuTab) referenceArgument74_0))) {
      return true;
    } else {
      return this.tweenValue.getValue() < 0.98F
          ? this.test3(floatValue, floatValue2)
          : this.getMenuPage().test(floatValue, floatValue2, intValue)
              || this.test3(floatValue, floatValue2);
    }
  }

  public void apply(MenuTab menuTab22) {
    ((MenuPage) this.map.get(this.menuTab2)).update2();
    this.menuTab = this.menuTab2;
    this.count = Integer.signum(menuTab22.ordinal() - this.menuTab2.ordinal());
    this.menuTab2 = menuTab22;
    this.tweenValue.snapTo(0.0F);
    this.tweenValue.animateTo(1.0F);
    ((MenuPage) this.map.get(menuTab22)).update();
  }

  public void apply(float floatValue, float floatValue2) {
    if (GuiDialogManager.hasDialog()) {
      GuiDialogManager.getCurrentDialog().mouseDragged(floatValue, floatValue2);
    } else {
      ClientData3 clientData32 = this.getClientData32(floatValue, floatValue2);
      this.getMenuPage().apply(clientData32.amount(), clientData32.amount2());
    }
  }

  public void update4() {
    this.enabled4 = false;
    if (GuiDialogManager.hasDialog()) {
      GuiDialogManager.getCurrentDialog().mouseReleased();
    } else {
      this.getMenuPage().update5();
    }
  }

  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (GuiDialogManager.hasDialog()) {
      return GuiDialogManager.getCurrentDialog()
          .mouseScrolled(floatValue, floatValue2, doubleValue);
    }

    ClientData3 clientData32 = this.getClientData32(floatValue, floatValue2);
    return !this.enabled2
        && this.getMenuPage().test(clientData32.amount(), clientData32.amount2(), doubleValue);
  }

  public boolean test(int intValue) {
    return GuiDialogManager.hasDialog()
        ? GuiDialogManager.getCurrentDialog().keyPressed(intValue)
        : this.getMenuPage().test2(intValue);
  }

  public boolean test2(int intValue) {
    return GuiDialogManager.hasDialog()
        ? GuiDialogManager.getCurrentDialog().charTyped(intValue)
        : this.getMenuPage().test(intValue);
  }

  public boolean test3() {
    if (GuiDialogManager.hasDialog()) {
      GuiDialogManager.getCurrentDialog().keyPressed(256);
      return true;
    } else {
      return this.getMenuPage().test();
    }
  }

  public boolean test3(float floatValue, float floatValue2) {
    return floatValue >= this.amount6
        && floatValue <= this.amount6 + 800.0F
        && floatValue2 >= this.amount7
        && floatValue2 <= this.amount7 + 486.0F;
  }
}

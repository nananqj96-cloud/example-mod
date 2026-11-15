package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DiagnosticTokenGenerator;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.HoverTooltip;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.SettingEntry;

public final class PanelMenu implements AutoCloseable {
  public static final PanelLayout panelLayout = new PanelLayout();
  public static final float amount = 280.0F;
  public static final float amount2 = 32.0F;
  public static final float amount3 = 106.0F;
  public static final float amount4 = 30.0F;
  public static final float amount5 = 8.0F;
  public final List list = new ArrayList();
  public final List list2 = new ArrayList();
  public final MenuPanelSupport4 menuPanelSupport4 = new MenuPanelSupport4();
  public final MenuPanelSupport3 menuPanelSupport3 = new MenuPanelSupport3();
  public final HoverTooltip hoverTooltip = new HoverTooltip();
  public final AntiCheatWarningDialog antiCheatWarningDialog = new AntiCheatWarningDialog();
  public final MenuPanelSupport2 menuPanelSupport2 = new MenuPanelSupport2();
  public final TweenValue tweenValue = new TweenValue(180.0F, 0.0F, Easing.OUT_QUART);
  public final TweenValue tweenValue2 = new TweenValue(190.0F, 0.0F, Easing.OUT_QUART);
  public final TweenValue[] clientSupport20Array =
      getClientSupport20Array(2 + MenuPanelSupport3.menuTabArray.length);
  public final TweenValue[] clientSupport20Array2 = getClientSupport20Array(3);
  public final StringBuilder stringBuilder = new StringBuilder();
  public boolean enabled;
  public boolean enabled2;
  public boolean enabled3;
  public boolean enabled4;
  public ModulePanel modulePanel;
  public ModulePanel modulePanel2;
  public ModulePanel modulePanel3;
  public float amount6;
  public float amount7;
  public float amount8;
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12 = 1.0F;
  public float amount13;
  public boolean enabled5;
  public int count = -1;

  public PanelMenu() {
    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (JsonSupport2.getList())) {
      this.list.add(new ModulePanel(collectionData3, panelLayout, this.menuPanelSupport4));
    }

    this.list2.addAll(this.list);
    ClientAudio.update();
  }

  public static PanelLayout getPanelLayout() {
    return panelLayout;
  }

  public boolean isEnabled3() {
    return this.enabled3;
  }

  public boolean isEnabled2() {
    return this.enabled2;
  }

  public boolean test() {
    return this.menuPanelSupport2.test();
  }

  public float computeFloat() {
    return this.menuPanelSupport2.computeFloat(0.0F);
  }

  public void update() {
    if (!this.enabled2) {
      this.enabled2 = true;
      this.menuPanelSupport2.update2();
      this.enabled5 = true;
      this.update3();
      this.update2();
      this.hoverTooltip.apply(null);
      ClientAudio.update2();
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float amount102,
      float amount112) {
    this.amount10 = amount102;
    this.amount11 = amount112;
    panelLayout.ensureDefaultLayout(amount102, amount112);
    int intValue = Math.max(1, (int) ((amount102 - 24.0F) / 222.0F));
    int intValue2 = Math.max(1, (this.list.size() + intValue - 1) / intValue);

    for (ModulePanel modulePanel4 :
        (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list)) {
      modulePanel4.setAmount7((amount112 - 122.0F) / intValue2 - 38.0F - 12.0F);
      if (modulePanel4.enabled) {
        modulePanel4.apply(true);
      }

      modulePanel4.apply(this.stringBuilder.toString());
    }

    this.menuPanelSupport3.apply(fontManager);
    this.menuPanelSupport2.update();
    this.amount12 = this.computeFloat();

    for (int index = 0; index < this.list.size(); index++) {
      ((ModulePanel) this.list.get(index))
          .apply(this.menuPanelSupport2.computeFloat(Math.min(130.0F, index * 26.0F)));
    }

    this.tweenValue2.animateTo(this.enabled4 ? 1.0F : 0.0F);
    this.amount13 = this.tweenValue2.getValue();
  }

  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    boolean flag =
        this.menuPanelSupport3.test()
            || this.menuPanelSupport4.test()
            || this.modulePanel3 != null
            || this.enabled4
            || GuiDialogManager.hasDialog()
            || this.enabled2;
    ModulePanel modulePanel4 = flag ? null : this.getModulePanel(floatValue, floatValue2);

    for (int index = this.list2.size() - 1; index >= 0; index--) {
      ModulePanel modulePanel5 = (ModulePanel) this.list2.get(index);
      modulePanel5.render(
          fontManager,
          modulePanel5 == modulePanel4 ? floatValue : Float.NaN,
          modulePanel5 == modulePanel4 ? floatValue2 : Float.NaN,
          floatValue3,
          floatValue4);
    }

    fontManager.update2();
    fontManager.apply3(this.amount12);
    if (this.enabled2) {
      floatValue = Float.NaN;
      floatValue2 = Float.NaN;
    }

    this.render(fontManager, flag ? Float.NaN : floatValue, flag ? Float.NaN : floatValue2);
    this.menuPanelSupport3.render(
        fontManager,
        GuiDialogManager.hasDialog() ? Float.NaN : floatValue,
        GuiDialogManager.hasDialog() ? Float.NaN : floatValue2,
        floatValue3,
        floatValue4);
    this.render2(fontManager, floatValue, floatValue2);
    this.menuPanelSupport4.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    if (this.modulePanel3 != null) {
      fontManager.render(0.0F, 0.0F, floatValue3, floatValue4, 0.0F, -1526266614);
      float floatValue5 = Math.min(520.0F, floatValue3 - 24.0F);
      float floatValue6 = Math.min(300.0F, floatValue4 - 24.0F);
      fontManager.render(
          (floatValue3 - floatValue5) / 2.0F,
          (floatValue4 - floatValue6) / 2.0F,
          floatValue5,
          floatValue6,
          12.0F,
          ColorPalette.getInstance().computeInt3(),
          ColorPalette.getInstance().computeInt4(),
          true);
      this.antiCheatWarningDialog.render(
          fontManager,
          (floatValue3 - floatValue5) / 2.0F,
          (floatValue4 - floatValue6) / 2.0F,
          floatValue5,
          floatValue6,
          floatValue,
          floatValue2);
    }

    this.hoverTooltip.apply(
        this.enabled2
            ? null
            : (flag
                ? this.menuPanelSupport3.getString()
                : this.getString(modulePanel4, floatValue, floatValue2)));
    this.hoverTooltip.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    GuiDialogManager.finishClosedDialog();
    if (GuiDialogManager.getCurrentDialog() != null) {
      GuiDialogManager.getCurrentDialog()
          .render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    }

    fontManager.update3();
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue3 = (this.amount10 - 280.0F) / 2.0F;
    float float2 = this.computeFloat2();
    this.tweenValue.animateTo(
        !this.enabled
                && !MenuPanelSupport.test(
                    floatValue, floatValue2, floatValue3, float2, 280.0F, 32.0F)
            ? 0.0F
            : 1.0F);
    render(fontManager, floatValue3, float2, 280.0F, 32.0F, this.tweenValue.getValue());
    fontManager.render2(floatValue3 + 16.0F, float2 + 15.0F, 4.5F, 1.5F, instance.computeInt18());
    fontManager.render2(
        floatValue3 + 19.0F,
        float2 + 18.0F,
        floatValue3 + 23.0F,
        float2 + 22.0F,
        1.5F,
        instance.computeInt18());
    fontManager.computeFloat(
        this.stringBuilder.isEmpty() && !this.enabled
            ? "CrackedByDexter"
            : this.stringBuilder + (this.enabled ? "|" : ""),
        floatValue3 + 32.0F,
        float2 + 16.0F,
        13.0F,
        instance.computeInt16(),
        214.0F);
    if (!this.stringBuilder.isEmpty()) {
      fontManager.render5(
          floatValue3 + 280.0F - 24.0F, float2 + 11.0F, 10.0F, 1.4F, instance.computeInt18());
    }
  }

  public void render2(FontManager fontManager, float floatValue, float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();

    for (int index = 0; index < MenuPanelSupport3.menuTabArray.length; index++) {
      MenuTab menuTab = MenuPanelSupport3.menuTabArray[index];
      float value = this.computeFloat(index);
      float float3 = this.computeFloat3();
      boolean flag =
          this.menuPanelSupport3.getMenuTab() == menuTab
              || this.count == index + 2
              || MenuPanelSupport.test(floatValue, floatValue2, value, float3, 106.0F, 30.0F);
      this.clientSupport20Array[index + 2].animateTo(flag ? 1.0F : 0.0F);
      float value2 = this.clientSupport20Array[index + 2].getValue();
      render(fontManager, value, float3, 106.0F, 30.0F, value2);
      fontManager.computeFloat2(
          menuTab.getLabel(),
          value + 53.0F,
          float3 + 15.0F,
          12.5F,
          ArgbColors.lerp(instance.computeInt16(), instance.computeInt(), value2));
    }

    float floatValue3 = this.computeFloat2() + 1.0F;
    this.clientSupport20Array[0].animateTo(
        this.count != 0
                && !MenuPanelSupport.test(
                    floatValue, floatValue2, 16.0F, floatValue3, 110.0F, 30.0F)
            ? 0.0F
            : 1.0F);
    this.clientSupport20Array[1].animateTo(
        !this.enabled4
                && this.count != 1
                && !MenuPanelSupport.test(
                    floatValue, floatValue2, this.amount10 - 126.0F, floatValue3, 110.0F, 30.0F)
            ? 0.0F
            : 1.0F);
    render(fontManager, 16.0F, floatValue3, 110.0F, 30.0F, this.clientSupport20Array[0].getValue());
    fontManager.computeFloat2(
        "Panel GUI",
        71.0F,
        floatValue3 + 15.0F,
        12.0F,
        ArgbColors.lerp(
            instance.computeInt16(),
            instance.computeInt(),
            this.clientSupport20Array[0].getValue()));
    render(
        fontManager,
        this.amount10 - 126.0F,
        floatValue3,
        110.0F,
        30.0F,
        this.clientSupport20Array[1].getValue());
    fontManager.computeFloat2(
        "Layout",
        this.amount10 - 71.0F,
        floatValue3 + 15.0F,
        12.0F,
        ArgbColors.lerp(
            instance.computeInt16(),
            instance.computeInt(),
            this.clientSupport20Array[1].getValue()));
    if (this.amount13 > 0.001F) {
      float float4 = this.computeFloat4();
      fontManager.update2();
      fontManager.apply3(this.amount13);
      fontManager.render(
          this.amount10 - 198.0F, float4, 182.0F, 112.0F, 10.0F, 14.0F, 3.0F, 1610612736);
      fontManager.render(
          this.amount10 - 198.0F,
          float4,
          182.0F,
          112.0F,
          10.0F,
          instance.computeInt5(),
          instance.computeInt6(),
          true);
      fontManager.render(
          this.amount10 - 198.0F, float4, 182.0F, 112.0F, 10.0F, 1.0F, instance.computeInt14());
      String[] textArray = new String[] {"Reset arrangement", "Fold all", "Unfold all"};

      for (int index2 = 0; index2 < textArray.length; index2++) {
        float floatValue4 = float4 + 5.0F + index2 * 32;
        this.clientSupport20Array2[index2].animateTo(
            this.enabled4
                    && MenuPanelSupport.test(
                        floatValue, floatValue2, this.amount10 - 192.0F, floatValue4, 170.0F, 32.0F)
                ? 1.0F
                : 0.0F);
        float value3 = this.clientSupport20Array2[index2].getValue();
        fontManager.render(
            this.amount10 - 192.0F,
            floatValue4,
            170.0F,
            32.0F,
            6.0F,
            instance.computeInt(0.12F * value3));
        fontManager.computeFloat(
            textArray[index2],
            this.amount10 - 184.0F + 2.0F * value3,
            floatValue4 + 16.0F,
            12.0F,
            ArgbColors.lerp(instance.computeInt16(), instance.computeInt(), value3));
      }

      fontManager.update3();
    }
  }

  public static TweenValue[] getClientSupport20Array(int intValue) {
    TweenValue[] clientSupport20Array3 = new TweenValue[intValue];

    for (int index = 0; index < intValue; index++) {
      clientSupport20Array3[index] = new TweenValue(180.0F, 0.0F, Easing.OUT_QUART);
    }

    return clientSupport20Array3;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render2(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        4.0F + 3.0F * floatValue5,
        instance.computeInt(0.1F + 0.12F * floatValue5));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        instance.computeInt5(),
        instance.computeInt6(),
        true);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        instance.computeInt(0.045F * floatValue5));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue4 / 2.0F,
        1.2F,
        instance.computeInt(0.4F + 0.45F * floatValue5));
  }

  public float computeFloat2() {
    return 16.0F - 8.0F * (1.0F - this.amount12);
  }

  public float computeFloat3() {
    return this.amount11 - 44.0F + 12.0F * (1.0F - this.amount12);
  }

  public float computeFloat4() {
    return 54.0F - 6.0F * (1.0F - this.amount13);
  }

  public float computeFloat(int intValue) {
    return (this.amount10 - 114.0F * MenuPanelSupport3.menuTabArray.length + 8.0F) / 2.0F
        + intValue * 114.0F;
  }

  public ModulePanel getModulePanel(float floatValue, float floatValue2) {
    for (ModulePanel modulePanel4 :
        (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list2)) {
      float value = modulePanel4.computeFloat(floatValue);
      float float2 = modulePanel4.computeFloat2(floatValue2);
      if (modulePanel4.test(value, float2)
          || modulePanel4.test(FontManager.getFontManager(), value, float2, this.amount11)) {
        return modulePanel4;
      }
    }

    return null;
  }

  public String getString(ModulePanel modulePanel4, float floatValue, float floatValue2) {
    if (modulePanel4 != null && !modulePanel4.test()) {
      floatValue = modulePanel4.computeFloat(floatValue);
      floatValue2 = modulePanel4.computeFloat2(floatValue2);

      for (MenuPanelSupport menuPanelSupport :
          (java.lang.Iterable<MenuPanelSupport>) (java.lang.Iterable<?>) (modulePanel4.visible())) {
        if (MenuPanelSupport.test(
            floatValue,
            floatValue2,
            menuPanelSupport.amount2,
            menuPanelSupport.amount3,
            menuPanelSupport.amount4,
            30.0F)) {
          return menuPanelSupport.guiModuleEntry.getString2();
        }

        if (menuPanelSupport.test()) {
          for (MenuPanelSupport5 menuPanelSupport5 :
              (java.lang.Iterable<MenuPanelSupport5>)
                  (java.lang.Iterable<?>) (menuPanelSupport.list)) {
            if (menuPanelSupport5.amount3 >= 0.0F
                && MenuPanelSupport.test(
                    floatValue,
                    floatValue2,
                    menuPanelSupport.amount2,
                    menuPanelSupport.amount3 + 30.0F + menuPanelSupport5.amount3,
                    menuPanelSupport.amount4,
                    menuPanelSupport5.computeFloat())) {
              return menuPanelSupport5.settingEntry.getDescription();
            }
          }
        }
      }

      return null;
    } else {
      return null;
    }
  }

  public void apply(ModulePanel modulePanel4) {
    this.list2.remove(modulePanel4);
    this.list2.addFirst(modulePanel4);
  }

  public void update2() {
    this.enabled = false;
    this.list.forEach(
        (referenceArgument77_0) ->
            ((bunnythekenzo.client.ui.ModulePanel) referenceArgument77_0).update());
  }

  public void click(float floatValue, float floatValue2, int intValue) {
    if (!this.enabled2) {
      if (GuiDialogManager.hasDialog()) {
        GuiDialogManager.getCurrentDialog().mouseClicked(floatValue, floatValue2, intValue);
      } else if (this.menuPanelSupport4.test()) {
        this.menuPanelSupport4.apply(floatValue, floatValue2);
      } else if (this.modulePanel3 != null) {
        this.antiCheatWarningDialog.test(
            floatValue,
            floatValue2,
            () -> {
              this.modulePanel3 = null;
            });
        this.update4();
      } else if (this.enabled4) {
        if (MenuPanelSupport.test(
                floatValue,
                floatValue2,
                this.amount10 - 198.0F,
                this.computeFloat4() + 5.0F,
                182.0F,
                96.0F)
            && intValue == 0) {
          int intValue2 = (int) ((floatValue2 - this.computeFloat4() - 5.0F) / 32.0F);
          if (intValue2 == 0) {
            this.resetLayout();
          } else {
            for (ModulePanel modulePanel4 :
                (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list)) {
              modulePanel4.apply(intValue2 == 1 || modulePanel4.enabled);
            }
          }
        }

        this.enabled4 = false;
      } else if (intValue == 0
          && MenuPanelSupport.test(
              floatValue, floatValue2, 16.0F, this.computeFloat2() + 1.0F, 110.0F, 30.0F)) {
        this.enabled3 = true;
        this.update();
      } else if (intValue == 0
          && MenuPanelSupport.test(
              floatValue,
              floatValue2,
              this.amount10 - 126.0F,
              this.computeFloat2() + 1.0F,
              110.0F,
              30.0F)) {
        this.update2();
        this.enabled4 = true;
      } else {
        for (int index = 0; index < MenuPanelSupport3.menuTabArray.length; index++) {
          if (intValue == 0
              && MenuPanelSupport.test(
                  floatValue,
                  floatValue2,
                  this.computeFloat(index),
                  this.computeFloat3(),
                  106.0F,
                  30.0F)) {
            this.update2();
            this.update3();
            this.menuPanelSupport3.apply(MenuPanelSupport3.menuTabArray[index]);
            return;
          }
        }

        if (this.menuPanelSupport3.test()) {
          this.menuPanelSupport3.render(floatValue, floatValue2, intValue);
        } else if (intValue == 0
            && MenuPanelSupport.test(
                floatValue,
                floatValue2,
                (this.amount10 - 280.0F) / 2.0F,
                this.computeFloat2(),
                280.0F,
                32.0F)) {
          this.update2();
          this.enabled = true;
          if (floatValue >= (this.amount10 + 280.0F) / 2.0F - 30.0F) {
            this.stringBuilder.setLength(0);
          }
        } else {
          ModulePanel modulePanel5 = this.getModulePanel(floatValue, floatValue2);
          if (modulePanel5 == null) {
            this.update2();
          } else {
            float amount62 = floatValue;
            float amount72 = floatValue2;
            floatValue = modulePanel5.computeFloat(floatValue);
            floatValue2 = modulePanel5.computeFloat2(floatValue2);
            this.apply(modulePanel5);
            if (modulePanel5.test(floatValue, floatValue2)) {
              this.update2();
              if (modulePanel5.enabled) {
                this.modulePanel3 = modulePanel5;
                this.antiCheatWarningDialog.update();
                return;
              }

              if (intValue == 0) {
                this.modulePanel = modulePanel5;
                this.amount6 = amount62;
                this.amount7 = amount72;
                this.amount8 = floatValue - modulePanel5.computeFloat2();
                this.amount9 = floatValue2 - modulePanel5.computeFloat3();
                this.enabled5 = false;
              } else {
                modulePanel5.update3();
                ClientAudio.update5();
              }
            } else {
              for (ModulePanel modulePanel6 :
                  (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list)) {
                for (MenuPanelSupport menuPanelSupport :
                    (java.lang.Iterable<MenuPanelSupport>)
                        (java.lang.Iterable<?>) (modulePanel6.list)) {
                  if (modulePanel6 != modulePanel5
                      || !MenuPanelSupport.test(
                          floatValue,
                          floatValue2,
                          menuPanelSupport.amount2,
                          menuPanelSupport.amount3,
                          menuPanelSupport.amount4,
                          menuPanelSupport.computeFloat())) {
                    menuPanelSupport.update3();
                  }
                }
              }

              this.enabled = false;
              this.modulePanel2 = modulePanel5;
              modulePanel5.test(floatValue, floatValue2, intValue);
            }
          }
        }
      }
    }
  }

  public void apply(float floatValue, float floatValue2) {
    if (!this.enabled2) {
      if (GuiDialogManager.hasDialog()) {
        GuiDialogManager.getCurrentDialog().mouseDragged(floatValue, floatValue2);
      } else if (this.menuPanelSupport4.test()) {
        this.menuPanelSupport4.apply2(floatValue, floatValue2);
      } else if (this.menuPanelSupport3.test()) {
        this.menuPanelSupport3.apply(floatValue, floatValue2);
      } else {
        if (this.modulePanel != null) {
          this.enabled5 =
              this.enabled5
                  | Math.abs(floatValue - this.amount6) + Math.abs(floatValue2 - this.amount7)
                      > 4.0F;
          if (this.enabled5) {
            this.modulePanel.render(
                floatValue, floatValue2, this.amount8, this.amount9, this.amount10, this.amount11);
            panelLayout.update();
          }
        } else if (this.modulePanel2 != null) {
          this.modulePanel2.apply(
              this.modulePanel2.computeFloat(floatValue),
              this.modulePanel2.computeFloat2(floatValue2));
        }
      }
    }
  }

  public void update3() {
    if (this.modulePanel != null && !this.enabled5) {
      this.modulePanel.update3();
      ClientAudio.update5();
    }

    this.modulePanel = this.modulePanel2 = null;
    this.list.forEach(
        (referenceArgument78_0) ->
            ((bunnythekenzo.client.ui.ModulePanel) referenceArgument78_0).update2());
    this.menuPanelSupport4.update();
    this.menuPanelSupport3.update2();
    this.antiCheatWarningDialog.update4();
    if (GuiDialogManager.getCurrentDialog() != null) {
      GuiDialogManager.getCurrentDialog().mouseReleased();
    }
  }

  public void render(float floatValue, float floatValue2, double doubleValue) {
    if (!this.enabled2) {
      if (GuiDialogManager.hasDialog()) {
        GuiDialogManager.getCurrentDialog().mouseScrolled(floatValue, floatValue2, doubleValue);
      } else if (this.menuPanelSupport3.test()) {
        this.menuPanelSupport3.render(floatValue, floatValue2, doubleValue);
      } else if (!this.menuPanelSupport4.test() && this.modulePanel3 == null && !this.enabled4) {
        ModulePanel modulePanel4 = this.getModulePanel(floatValue, floatValue2);
        if (modulePanel4 != null) {
          modulePanel4.apply(doubleValue);
        }
      }
    }
  }

  public boolean test(int intValue, boolean flag) {
    if (this.enabled2) {
      return true;
    }

    if (GuiDialogManager.hasDialog()) {
      GuiDialogManager.getCurrentDialog().keyPressed(intValue);
      return true;
    }

    if (this.menuPanelSupport4.test()) {
      if (intValue == 256) {
        this.menuPanelSupport4.update2();
      }

      return true;
    } else if (this.modulePanel3 != null) {
      if (intValue == 256) {
        this.modulePanel3 = null;
      } else {
        this.antiCheatWarningDialog.test(intValue);
        this.update4();
      }

      return true;
    } else if (this.enabled4) {
      if (intValue == 256) {
        this.enabled4 = false;
      }

      return true;
    } else if (this.menuPanelSupport3.test()) {
      if (this.menuPanelSupport3.test(intValue)) {
        return true;
      }

      if (intValue == 344) {
        this.update();
      }

      return true;
    } else if (this.enabled) {
      if (intValue == 256 || intValue == 257) {
        this.enabled = false;
      } else if (intValue == 259 && !this.stringBuilder.isEmpty()) {
        this.stringBuilder.delete(
            this.stringBuilder.offsetByCodePoints(this.stringBuilder.length(), -1),
            this.stringBuilder.length());
      } else if (intValue == 261) {
        this.stringBuilder.setLength(0);
      }

      return true;
    } else {
      for (ModulePanel modulePanel4 :
          (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list)) {
        if (modulePanel4.key(intValue, flag)) {
          return true;
        }
      }

      if (intValue == 256 || intValue == 344) {
        this.update();
        return true;
      } else if (intValue == 258) {
        this.count =
            Math.floorMod(this.count + (flag ? -1 : 1), 2 + MenuPanelSupport3.menuTabArray.length);
        return true;
      } else if ((intValue == 257 || intValue == 32) && this.count >= 0) {
        ClientData3 clientData3 =
            this.count < 2
                ? this.getClientData3(this.count)
                : this.getClientData3(MenuPanelSupport3.menuTabArray[this.count - 2]);
        this.click(clientData3.amount(), clientData3.amount2(), 0);
        this.update3();
        return true;
      } else {
        return false;
      }
    }
  }

  public boolean charTyped(int intValue) {
    if (this.enabled2) {
      return true;
    }

    if (GuiDialogManager.hasDialog()) {
      return GuiDialogManager.getCurrentDialog().charTyped(intValue);
    }

    if (this.menuPanelSupport3.test()) {
      return this.menuPanelSupport3.test2(intValue);
    }

    if (this.modulePanel3 != null || this.menuPanelSupport4.test() || this.enabled4) {
      return true;
    }

    if (this.enabled) {
      if (Character.isValidCodePoint(intValue) && !Character.isISOControl(intValue)) {
        this.stringBuilder.appendCodePoint(intValue);
      }

      return true;
    } else {
      return this.list.stream()
          .anyMatch(
              value -> {
                return ((bunnythekenzo.client.ui.ModulePanel) (value)).test(intValue);
              });
    }
  }

  public void update4() {
    if (this.modulePanel3 != null && this.antiCheatWarningDialog.isEnabled()) {
      this.modulePanel3.enabled = false;
      this.modulePanel3.apply(false);
      this.modulePanel3 = null;
    }
  }

  public void resetLayout() {
    panelLayout.update2();
    panelLayout.ensureDefaultLayout(this.amount10, this.amount11);

    for (ModulePanel modulePanel4 :
        (java.lang.Iterable<ModulePanel>) (java.lang.Iterable<?>) (this.list)) {
      modulePanel4.apply(modulePanel4.enabled);
      modulePanel4.list.forEach(
          (java.util.function.Consumer<bunnythekenzo.client.ui.MenuPanelSupport>)
              (menuPanelSupport -> {
                menuPanelSupport.apply(false);
              }));
      modulePanel4.apply2(0.0F);
    }
  }

  public void apply(String text) {
    this.update2();
    this.stringBuilder.setLength(0);
    this.stringBuilder.append(text);
    this.enabled = true;
  }

  public int computeInt() {
    return this.list.size();
  }

  public CollectionData3 getCollectionData3(int intValue) {
    return ((ModulePanel) this.list.get(intValue)).collectionData3;
  }

  public int computeInt(int intValue) {
    return ((ModulePanel) this.list.get(intValue)).list.size();
  }

  public GuiModuleEntry getGuiModuleEntry(int intValue, int intValue2) {
    return ((MenuPanelSupport) ((ModulePanel) this.list.get(intValue)).list.get(intValue2))
        .guiModuleEntry;
  }

  public List moduleSettings(int intValue, int intValue2) {
    return ((MenuPanelSupport) ((ModulePanel) this.list.get(intValue)).list.get(intValue2))
        .list.stream()
            .map(
                value -> {
                  return ((bunnythekenzo.client.ui.MenuPanelSupport5) (value)).settingEntry;
                })
            .toList();
  }

  public boolean test(int intValue) {
    return !((ModulePanel) this.list.get(intValue)).test();
  }

  public boolean test(int intValue, int intValue2) {
    return ((MenuPanelSupport) ((ModulePanel) this.list.get(intValue)).list.get(intValue2)).test();
  }

  public float computeFloat2(int intValue) {
    return ((ModulePanel) this.list.get(intValue)).computeFloat();
  }

  public boolean test2() {
    return this.modulePanel3 != null;
  }

  public void apply(int intValue, int intValue2) {
    ModulePanel modulePanel4 = (ModulePanel) this.list.get(intValue);
    if (!modulePanel4.enabled) {
      this.stringBuilder.setLength(0);
      modulePanel4.apply("");
      modulePanel4.apply(false);
      ((MenuPanelSupport) modulePanel4.list.get(intValue2)).apply(true);
      this.apply(modulePanel4);
    }
  }

  public void focusSetting(int intValue, int intValue2, SettingEntry settingEntry) {
    ModulePanel modulePanel4 = (ModulePanel) this.list.get(intValue);
    if (!modulePanel4.enabled) {
      MenuPanelSupport menuPanelSupport = (MenuPanelSupport) modulePanel4.list.get(intValue2);
      menuPanelSupport.focus(settingEntry);
      MenuPanelSupport5 menuPanelSupport5 =
          (bunnythekenzo.client.ui.MenuPanelSupport5)
              (menuPanelSupport.list.stream()
                  .filter(
                      value -> {
                        return ((bunnythekenzo.client.ui.MenuPanelSupport5) (value)).settingEntry
                            == settingEntry;
                      })
                  .findFirst()
                  .orElseThrow());
      modulePanel4.apply(menuPanelSupport, 30.0F + menuPanelSupport5.amount3);
      this.apply(modulePanel4);
    }
  }

  public ClientData3 getClientData3(int intValue, float floatValue, float floatValue2) {
    ModulePanel modulePanel4 = (ModulePanel) this.list.get(intValue);
    return modulePanel4.getClientData3(
        modulePanel4.computeFloat2() + floatValue, modulePanel4.computeFloat3() + floatValue2);
  }

  public ClientData3 getClientData3(
      int intValue, int intValue2, float floatValue, float floatValue2) {
    ModulePanel modulePanel4 = (ModulePanel) this.list.get(intValue);
    MenuPanelSupport menuPanelSupport = (MenuPanelSupport) modulePanel4.list.get(intValue2);
    return modulePanel4.getClientData3(
        menuPanelSupport.amount2 + floatValue, menuPanelSupport.amount3 + floatValue2);
  }

  public ClientData3 settingPoint(
      int intValue, int intValue2, SettingEntry settingEntry, float floatValue, float floatValue2) {
    ModulePanel modulePanel4 = (ModulePanel) this.list.get(intValue);
    MenuPanelSupport menuPanelSupport = (MenuPanelSupport) modulePanel4.list.get(intValue2);
    MenuPanelSupport5 menuPanelSupport5 =
        (bunnythekenzo.client.ui.MenuPanelSupport5)
            (menuPanelSupport.list.stream()
                .filter(
                    value -> {
                      return ((bunnythekenzo.client.ui.MenuPanelSupport5) (value)).settingEntry
                          == settingEntry;
                    })
                .findFirst()
                .orElseThrow());
    return modulePanel4.getClientData3(
        menuPanelSupport.amount2 + floatValue,
        menuPanelSupport.amount3 + 30.0F + menuPanelSupport5.amount3 + floatValue2);
  }

  public ClientData3 getClientData3(int intValue) {
    return new ClientData3(
        intValue == 0 ? 71.0F : this.amount10 - 71.0F, this.computeFloat2() + 16.0F);
  }

  public ClientData3 getClientData3(MenuTab menuTab) {
    for (int index = 0; index < MenuPanelSupport3.menuTabArray.length; index++) {
      if (MenuPanelSupport3.menuTabArray[index] == menuTab) {
        return new ClientData3(this.computeFloat(index) + 53.0F, this.computeFloat3() + 15.0F);
      }
    }

    throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1601));
  }

  public MenuTab getMenuTab() {
    return this.menuPanelSupport3.getMenuTab();
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return this.menuPanelSupport3.getClientData3(floatValue, floatValue2);
  }

  public boolean test3() {
    return this.menuPanelSupport4.test();
  }

  public ClientData3 getClientData32(float floatValue, float floatValue2) {
    return this.menuPanelSupport4.getClientData3(floatValue, floatValue2);
  }

  @Override
  public void close() {
    GuiDialogManager.closeAll();
    this.enabled5 = true;
    this.update3();
    this.update2();
    this.menuPanelSupport3.close();
    this.menuPanelSupport4.update2();
  }
}

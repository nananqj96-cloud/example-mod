package bunnythekenzo.client.ui;

import java.util.List;
import java.util.Locale;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.IconCache;

public final class ModulePanel extends MenuPanel {
  public final CollectionData3 collectionData3;
  public final List list;
  public String string = "";
  public boolean enabled;
  public MenuPanelSupport menuPanelSupport;
  public float amount = 1.0F;
  public float amount2 = 1.0F;
  public float amount3;

  public ModulePanel(
      CollectionData3 collectionData32,
      PanelLayout panelLayout,
      MenuPanelSupport4 menuPanelSupport4) {
    super(ClientInitializer.getThemeManager(), panelLayout.panel(collectionData32.string()));
    this.collectionData3 = collectionData32;
    this.list =
        collectionData32.list().stream()
            .map(
                guiModuleEntry -> {
                  return new MenuPanelSupport(
                      (bunnythekenzo.client.GuiModuleEntry) (guiModuleEntry),
                      collectionData32.string(),
                      panelLayout,
                      menuPanelSupport4);
                })
            .toList();
    this.enabled = collectionData32.enabled();
    if (this.enabled) {
      this.apply(true);
    }
  }

  public void apply(String text) {
    this.string = text.toLowerCase(Locale.ROOT).trim();
  }

  public void apply(float amount4) {
    this.amount = amount4;
    this.amount2 = 0.975F + 0.025F * amount4;
    this.amount3 = 24.0F * (1.0F - amount4);
  }

  public float computeFloat(float floatValue) {
    return (floatValue - this.computeFloat2() - 105.0F) / this.amount2
        + this.computeFloat2()
        + 105.0F;
  }

  public float computeFloat2(float floatValue) {
    return (floatValue - this.amount3 - this.computeFloat3() - 19.0F) / this.amount2
        + this.computeFloat3()
        + 19.0F;
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return new ClientData3(
        this.computeFloat2() + 105.0F + (floatValue - this.computeFloat2() - 105.0F) * this.amount2,
        this.computeFloat3()
            + 19.0F
            + (floatValue2 - this.computeFloat3() - 19.0F) * this.amount2
            + this.amount3);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 = floatValue - 105.0F - (floatValue3 - 105.0F) * this.amount2;
    float floatValue8 = floatValue2 - this.amount3 - 19.0F - (floatValue4 - 19.0F) * this.amount2;
    this.apply2(
        Math.clamp(floatValue7, 0.0F, Math.max(0.0F, floatValue5 - 210.0F)),
        Math.clamp(floatValue8, 58.0F, Math.max(58.0F, floatValue6 - 112.0F)));
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    fontManager.update2();
    fontManager.apply3(this.amount);
    fontManager.apply(0.0F, this.amount3);
    fontManager.render2(this.computeFloat2() + 105.0F, this.computeFloat3() + 19.0F, this.amount2);
    super.render(
        fontManager,
        this.computeFloat(floatValue),
        this.computeFloat2(floatValue2),
        floatValue3,
        floatValue4);
    fontManager.update3();
  }

  public List visible() {
    return this.list.stream()
        .filter(
            value -> {
              return this.string.isEmpty()
                  || ((bunnythekenzo.client.ui.MenuPanelSupport) (value))
                      .guiModuleEntry
                      .getString()
                      .toLowerCase(Locale.ROOT)
                      .contains(this.string);
            })
        .toList();
  }

  @Override
  public String getString() {
    return this.collectionData3.string();
  }

  @Override
  public int computeInt() {
    return IconCache.get(this.collectionData3.string2());
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    if (this.enabled) {
      return 0.0F;
    }

    float floatValue = 12.0F;

    for (MenuPanelSupport menuPanelSupport2 :
        (java.lang.Iterable<MenuPanelSupport>) (java.lang.Iterable<?>) (this.visible())) {
      floatValue += menuPanelSupport2.computeFloat() + 3.0F;
    }

    return Math.max(32.0F, floatValue - 3.0F);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    if (!this.enabled) {
      float amount32 = floatValue;

      for (MenuPanelSupport menuPanelSupport2 :
          (java.lang.Iterable<MenuPanelSupport>) (java.lang.Iterable<?>) (this.visible())) {
        float value = menuPanelSupport2.computeFloat();
        menuPanelSupport2.amount2 = this.computeFloat2() + 6.0F;
        menuPanelSupport2.amount3 = amount32;
        menuPanelSupport2.amount4 = 198.0F;
        if (amount32 + value >= floatValue4 && amount32 <= floatValue5) {
          menuPanelSupport2.render(
              fontManager,
              floatValue2,
              floatValue3,
              this.computeFloat(amount32, amount32 + value, floatValue4, floatValue5));
        }

        amount32 += value + 3.0F;
      }

      if (this.visible().isEmpty()) {
        fontManager.computeFloat(
            "No matching modules",
            this.computeFloat2() + 16.0F,
            floatValue + 16.0F,
            11.0F,
            this.getColorPalette().computeInt18());
      }

      if (this.menuPanelSupport != null) {
        this.apply(this.menuPanelSupport, 0.0F);
        if (this.menuPanelSupport.test2()) {
          this.menuPanelSupport = null;
        }
      }
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (!this.enabled && !this.test()) {
      for (MenuPanelSupport menuPanelSupport2 :
          (java.lang.Iterable<MenuPanelSupport>) (java.lang.Iterable<?>) (this.visible())) {
        boolean flag = menuPanelSupport2.test();
        if (menuPanelSupport2.test(floatValue, floatValue2, intValue)) {
          if (!flag && menuPanelSupport2.test()) {
            this.menuPanelSupport = menuPanelSupport2;
          }

          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public void update() {
    this.list.forEach(
        (referenceArgument75_0) ->
            ((bunnythekenzo.client.ui.MenuPanelSupport) referenceArgument75_0).update3());
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    this.list.forEach(
        value -> {
          ((bunnythekenzo.client.ui.MenuPanelSupport) (value)).apply(floatValue, floatValue2);
        });
  }

  @Override
  public void update2() {
    this.list.forEach(
        (referenceArgument76_0) ->
            ((bunnythekenzo.client.ui.MenuPanelSupport) referenceArgument76_0).update2());
  }

  public boolean key(int intValue, boolean flag) {
    return !this.enabled
        && !this.test()
        && this.visible().stream()
            .anyMatch(
                value -> {
                  return ((bunnythekenzo.client.ui.MenuPanelSupport) (value)).test(intValue, flag);
                });
  }

  @Override
  public boolean test(int intValue) {
    return !this.enabled
        && !this.test()
        && this.visible().stream()
            .anyMatch(
                value -> {
                  return ((bunnythekenzo.client.ui.MenuPanelSupport) (value)).test(intValue);
                });
  }

  public void apply(MenuPanelSupport menuPanelSupport2, float floatValue) {
    float floatValue2 = 0.0F;

    for (MenuPanelSupport menuPanelSupport3 :
        (java.lang.Iterable<MenuPanelSupport>) (java.lang.Iterable<?>) (this.visible())) {
      if (menuPanelSupport3 == menuPanelSupport2) {
        this.apply2(floatValue2 + floatValue);
        return;
      }

      floatValue2 += menuPanelSupport3.computeFloat() + 3.0F;
    }
  }
}

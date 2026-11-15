package bunnythekenzo.client.ui;

import java.util.List;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.CollectionData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.ModuleDetailTransition;
import bunnythekenzo.client.ModuleSearch;
import bunnythekenzo.client.ModuleSearchContract;
import bunnythekenzo.client.ModuleSearchData4;
import bunnythekenzo.client.ModuleSearchSupport;
import bunnythekenzo.client.ModuleSearchSupport4;
import bunnythekenzo.client.ModuleSearchSupport6;
import bunnythekenzo.client.ModuleSearchSupport7;
import bunnythekenzo.client.render.FontManager;

public final class ModulesPage implements MenuPage {
  public final List list = JsonSupport2.getList();
  public final ModuleSearchSupport moduleSearchSupport = new ModuleSearchSupport(this.list);
  public final ModuleSearch moduleSearch = new ModuleSearch(this.list);
  public final ModuleDetailTransition moduleDetailTransition = new ModuleDetailTransition();
  public final ModuleSearchContract moduleSearchContract = new ModuleSearchSupport4(this);
  public int count;
  public final DampedSpring dampedSpring = new DampedSpring(210.0F, 31.0F, 0.0F);
  public boolean enabled;
  public final ModuleSearchSupport6 moduleSearchSupport6 = new ModuleSearchSupport6();
  public float amount;
  public float amount2;
  public float amount3;
  public float amount4;

  public ClientData3 getClientData3(String text, boolean flag) {
    return this.moduleSearch.getClientData3(text, flag);
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return this.moduleSearchSupport6.getClientData33(floatValue, floatValue2);
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void apply(GuiModuleEntry guiModuleEntry) {
    for (int index = 0; index < this.list.size(); index++) {
      CollectionData3 collectionData3 = (CollectionData3) this.list.get(index);
      if (collectionData3.enabled() && collectionData3.list().contains(guiModuleEntry)) {
        this.count = this.moduleSearch.getCount();
        this.moduleSearchSupport.apply(index);
        this.moduleSearch.apply2(index);
        this.update4();
        this.moduleSearch.apply(guiModuleEntry);
        return;
      }
    }
  }

  public void apply2(GuiModuleEntry guiModuleEntry) {
    if (!guiModuleEntry.test() && !this.moduleSearch.test2() && this.test(guiModuleEntry)) {
      this.moduleSearch.test5();
      this.apply(guiModuleEntry);
    } else {
      this.moduleSearch.apply2(guiModuleEntry);
    }
  }

  public boolean test(GuiModuleEntry guiModuleEntry) {
    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (this.list)) {
      if (collectionData3.enabled() && collectionData3.list().contains(guiModuleEntry)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void update() {
    this.moduleSearchSupport.update();
    this.moduleSearch.update4();
    this.moduleDetailTransition.update();
  }

  @Override
  public void update2() {
    this.moduleSearchSupport.update2();
    this.moduleSearch.update5();
    this.moduleDetailTransition.update2();
  }

  @Override
  public float computeFloat() {
    return 178.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount5,
      float amount22,
      float amount32,
      float amount42,
      float floatValue,
      float floatValue2) {
    this.amount = amount5;
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.amount4 = amount42;
    this.moduleSearchSupport.apply(this.moduleSearch.test2());
    float floatValue3 = 252.0F * Math.clamp(this.dampedSpring.advance(), 0.0F, 1.0F);
    this.moduleSearchSupport.render(
        fontManager, amount5, amount22, 178.0F, amount42, floatValue, floatValue2);
    this.moduleSearch.render(
        fontManager,
        amount5 + 178.0F,
        amount22,
        amount32 - 178.0F - floatValue3,
        amount42,
        floatValue,
        floatValue2);
    if (floatValue3 > 0.5F) {
      this.renderDrawer(
          fontManager, amount5, amount22, amount32, amount42, floatValue3, floatValue, floatValue2);
    } else {
      this.moduleSearchSupport6.update();
    }
  }

  public void renderDrawer(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue8 = floatValue + floatValue3 - floatValue5;
    ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue6, floatValue7);
    float floatValue9 = floatValue8 + 6.0F;
    float floatValue10 = floatValue2 + 6.0F;
    float floatValue11 = floatValue4 - 12.0F;
    ModuleSearchData4 moduleSearchData4 =
        ModuleSearchSupport7.getModuleSearchData4(
            floatValue, floatValue2, floatValue3, floatValue4);
    ModuleSearchSupport7.render(
        fontManager,
        this.moduleSearchSupport6,
        floatValue9,
        floatValue10,
        240.0F,
        floatValue11,
        10.0F,
        (1.0F - floatValue5 / 252.0F) * 0.16F,
        -0.015F,
        10.0F,
        8.0F,
        0.35F,
        () -> {
          fontManager.render(
              floatValue9,
              floatValue10,
              240.0F,
              floatValue11,
              10.0F,
              instance.computeInt3(),
              instance.computeInt4(),
              true);
          fontManager.render(
              floatValue9 + 0.5F,
              floatValue10 + 0.5F,
              240.0F - 1.0F,
              floatValue11 - 1.0F,
              9.5F,
              1.0F,
              instance.computeInt(0.35F),
              369098751,
              true);
          this.moduleDetailTransition.render(
              fontManager,
              floatValue9,
              floatValue10,
              240.0F,
              floatValue11,
              clientData3.amount(),
              clientData3.amount2());
        });
    ModuleSearchSupport7.apply(moduleSearchData4);
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
    if (this.enabled
        && this.moduleDetailTransition.test3(clientData3.amount(), clientData3.amount2())) {
      this.update3();
      return true;
    } else if (this.moduleSearchSupport.test(floatValue, floatValue2, this::apply)) {
      return true;
    } else if (this.enabled
        && this.moduleDetailTransition.test2(clientData3.amount(), clientData3.amount2())) {
      return this.moduleDetailTransition.test(
          clientData3.amount(), clientData3.amount2(), intValue);
    } else {
      return this.moduleSearch.test2(floatValue, floatValue2)
          ? this.moduleSearch.mouseClicked(floatValue, floatValue2, this.moduleSearchContract)
          : this.test(floatValue, floatValue2);
    }
  }

  public void apply(int intValue) {
    if (((CollectionData3) this.list.get(intValue)).enabled() && !this.moduleSearch.test2()) {
      this.count = this.moduleSearch.getCount();
    }

    this.moduleSearch.apply2(intValue);
    this.update4();
  }

  public void leaveVault() {
    this.moduleSearch.update();
    this.moduleSearchSupport.apply(
        this.count,
        intValue -> {
          this.moduleSearch.apply2(intValue);
          this.update4();
        });
  }

  public boolean test(String text) {
    List list2 = JsonSupport2.getList();

    for (int index = 0; index < list2.size(); index++) {
      for (GuiModuleEntry guiModuleEntry :
          (java.lang.Iterable<GuiModuleEntry>)
              (java.lang.Iterable<?>) (((CollectionData3) list2.get(index)).list())) {
        if (guiModuleEntry.getString().equals(text)) {
          this.moduleSearchSupport.apply(index);
          this.moduleSearch.apply2(index);
          this.apply(guiModuleEntry, false);
          return true;
        }
      }
    }

    return false;
  }

  public void apply(GuiModuleEntry guiModuleEntry, boolean flag) {
    if (flag && this.enabled) {
      this.update3();
    } else {
      boolean enabled2 = this.enabled;
      this.enabled = true;
      this.dampedSpring.setTarget(1.0F);
      this.moduleSearchSupport.apply(this.moduleSearch.getCount());
      this.moduleDetailTransition.apply(guiModuleEntry, !enabled2);
      if (enabled2) {
        ClientAudio.update5();
      } else {
        ClientAudio.update6();
      }
    }
  }

  public void update3() {
    if (this.enabled) {
      this.update4();
      ClientAudio.update7();
    }
  }

  public void update4() {
    if (this.enabled) {
      this.moduleDetailTransition.test2();
      this.enabled = false;
      this.dampedSpring.setTarget(0.0F);
      this.moduleDetailTransition.update2();
      this.moduleSearch.update3();
    }
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    ClientData3 clientData32 = this.moduleSearchSupport6.getClientData32(floatValue, floatValue2);
    if (Float.isFinite(clientData32.amount())) {
      this.moduleDetailTransition.apply(clientData32.amount(), clientData32.amount2());
    }
  }

  @Override
  public void update5() {
    this.moduleSearchSupport.update3();
    this.moduleSearch.update8();
    this.moduleDetailTransition.update3();
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
    return this.enabled
            && this.moduleDetailTransition.test(
                clientData3.amount(), clientData3.amount2(), doubleValue)
        ? true
        : this.moduleSearch.test(floatValue, floatValue2, doubleValue);
  }

  @Override
  public boolean test(int intValue) {
    return this.enabled && this.moduleDetailTransition.test2(intValue)
        ? true
        : this.moduleSearch.test2(intValue);
  }

  @Override
  public boolean test2(int intValue) {
    if (this.enabled && this.moduleDetailTransition.test(intValue)) {
      return true;
    }

    if (this.moduleSearch.test3()) {
      this.moduleSearch.apply(intValue);
      return true;
    }

    if (this.moduleSearch.test3(intValue)) {
      return true;
    }

    if (this.moduleSearch.test4(intValue)) {
      return true;
    }

    return switch (intValue) {
      case 264 -> {
        this.moduleSearchSupport.apply2(1, this::apply);
        yield true;
      }
      case 265 -> {
        this.moduleSearchSupport.apply2(-1, this::apply);
        yield true;
      }
      default -> false;
    };
  }

  @Override
  public boolean test() {
    if (this.enabled && this.moduleDetailTransition.test2()) {
      return true;
    }

    if (this.moduleSearch.test3()) {
      this.moduleSearch.apply(256);
      return true;
    }

    if (this.moduleSearch.test5()) {
      return true;
    }

    if (this.moduleSearch.test()) {
      this.leaveVault();
      return true;
    }

    if (!this.enabled) {
      return false;
    }

    this.update3();
    return true;
  }

  @Override
  public String getString() {
    String string = this.moduleSearch.getString();
    return string == null && this.enabled ? this.moduleDetailTransition.getString() : string;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount
        && floatValue <= this.amount + this.amount3
        && floatValue2 >= this.amount2
        && floatValue2 <= this.amount2 + this.amount4;
  }
}

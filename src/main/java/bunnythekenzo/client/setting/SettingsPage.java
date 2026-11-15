package bunnythekenzo.client.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import bunnythekenzo.client.CollectionData;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.StaggeredTransition;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.ui.MenuPage;

public final class SettingsPage implements MenuPage {
  public static final float amount = 520.0F;
  public static final float amount2 = 26.0F;
  public static final float amount3 = 26.0F;
  public final List list = GuiPreferences.getGuiPreferences().getList();
  public final List list2 = new ArrayList();
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(24.0F, 380.0F);
  public float amount4;
  public float amount5;
  public float amount6;
  public float amount7;
  public SettingWidget settingWidget;
  public String string;

  public SettingsPage() {
    for (CollectionData collectionData :
        (java.lang.Iterable<CollectionData>) (java.lang.Iterable<?>) (this.list)) {
      ArrayList arrayList = new ArrayList();
      collectionData
          .list()
          .forEach(
              settingEntry -> {
                arrayList.add(
                    SettingWidget.getSettingWidget(
                        (bunnythekenzo.client.setting.SettingEntry) (settingEntry)));
              });
      this.list2.add(arrayList);
    }
  }

  @Override
  public void update() {
    this.staggeredTransition.apply(this.computeInt());
  }

  @Override
  public void update2() {
    this.staggeredTransition.apply2(this.computeInt());
  }

  public int computeInt() {
    int intValue = 0;

    for (List list3 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      intValue += list3.size();
    }

    return intValue;
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount42,
      float amount52,
      float amount62,
      float amount72,
      float floatValue,
      float floatValue2) {
    this.amount4 = amount42;
    this.amount5 = amount52;
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.string = null;
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue3 = amount42 + (amount62 - 520.0F) / 2.0F;
    float floatValue4 = amount52;
    float floatValue5 = amount52 + amount72;
    this.smoothScrollState.apply(this.computeFloat2(), amount72);
    float floatValue6 = amount52 + 22.0F - this.smoothScrollState.computeFloat();
    int index = 0;
    fontManager.update2();
    fontManager.render(amount42, amount52, amount62, amount72);

    for (int index2 = 0; index2 < this.list.size(); index2++) {
      if (index2 > 0) {
        floatValue6 += 26.0F;
      }

      float value =
          SmoothScrollState.computeFloat(
              floatValue6, floatValue6 + 26.0F, floatValue4, floatValue5);
      if (value > 0.01F) {
        fontManager.update2();
        fontManager.apply3(value);
        fontManager.setAmount(1.6F);
        fontManager.computeFloat(
            ((CollectionData) this.list.get(index2)).string().toUpperCase(Locale.ROOT),
            floatValue3 + 12.0F,
            floatValue6 + 13.0F,
            9.5F,
            instance.computeInt18());
        fontManager.setAmount(0.0F);
        fontManager.render(
            floatValue3 + 12.0F,
            floatValue6 + 26.0F - 1.0F,
            496.0F,
            1.0F,
            0.0F,
            instance.computeInt13());
        fontManager.update3();
      }

      floatValue6 += 32.0F;

      for (SettingWidget settingWidget2 :
          (java.lang.Iterable<SettingWidget>)
              (java.lang.Iterable<?>) ((List) this.list2.get(index2))) {
        float floatValue7 = floatValue6;
        floatValue6 += settingWidget2.computeFloat() + 4.0F;
        if (!(floatValue7 > floatValue5 + 40.0F)
            && !(floatValue7 + settingWidget2.computeFloat() < floatValue4 - 40.0F)) {
          float value2 =
              SmoothScrollState.computeFloat(
                  floatValue7,
                  floatValue7 + settingWidget2.computeFloat(),
                  floatValue4,
                  floatValue5);
          settingWidget2.render(
              fontManager,
              floatValue3,
              floatValue7,
              520.0F,
              floatValue,
              floatValue2,
              this.staggeredTransition.computeFloat(index),
              value2);
          if (settingWidget2.test4(floatValue, floatValue2)) {
            this.string = settingWidget2.getSettingEntry().getDescription();
          }

          index++;
        } else {
          settingWidget2.update7();
          index++;
        }
      }
    }

    for (List list3 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (SettingWidget settingWidget3 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list3)) {
        if (settingWidget3.test()) {
          settingWidget3.render(fontManager, floatValue, floatValue2, floatValue4, floatValue5);
        }
      }
    }

    fontManager.update3();
    if (floatValue >= amount42
        && floatValue <= amount42 + amount62
        && floatValue2 >= amount52
        && floatValue2 <= amount52 + amount72) {
      this.smoothScrollState.update();
    }

    this.smoothScrollState.render(fontManager, floatValue3 + 520.0F + 16.0F, floatValue4, amount72);
  }

  public float computeFloat2() {
    float floatValue = 44.0F;

    for (int index = 0; index < this.list.size(); index++) {
      if (index > 0) {
        floatValue += 26.0F;
      }

      floatValue += 32.0F;

      for (SettingWidget settingWidget2 :
          (java.lang.Iterable<SettingWidget>)
              (java.lang.Iterable<?>) ((List) this.list2.get(index))) {
        floatValue += settingWidget2.computeFloat() + 4.0F;
      }
    }

    return floatValue;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    for (List list3 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (SettingWidget settingWidget2 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list3)) {
        if (settingWidget2.test() && settingWidget2.test3(floatValue, floatValue2)) {
          this.settingWidget = settingWidget2;
          return true;
        }
      }
    }

    for (List list4 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (SettingWidget settingWidget3 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list4)) {
        if (settingWidget3.test(floatValue, floatValue2, intValue)) {
          this.settingWidget = settingWidget3;
          GuiPreferences.getGuiPreferences().update();
          return true;
        }
      }
    }

    return this.test(floatValue, floatValue2);
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.settingWidget != null) {
      this.settingWidget.apply(floatValue, floatValue2);
      GuiPreferences.getGuiPreferences().update();
    }
  }

  @Override
  public void update5() {
    if (this.settingWidget != null) {
      this.settingWidget.update5();
      this.settingWidget = null;
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    for (List list3 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (SettingWidget settingWidget2 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list3)) {
        if (settingWidget2.test() && settingWidget2.test(floatValue, floatValue2, doubleValue)) {
          return true;
        }
      }
    }

    if (!this.test(floatValue, floatValue2)) {
      return false;
    }

    this.smoothScrollState.test(doubleValue);
    return true;
  }

  @Override
  public String getString() {
    return this.string;
  }

  @Override
  public boolean test() {
    for (List list3 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (SettingWidget settingWidget2 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list3)) {
        if (settingWidget2.test()) {
          settingWidget2.test3(Float.NaN, Float.NaN);
          return true;
        }
      }
    }

    return false;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount4
        && floatValue <= this.amount4 + this.amount6
        && floatValue2 >= this.amount5
        && floatValue2 <= this.amount5 + this.amount7;
  }
}

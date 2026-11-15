package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.SettingWidget;

public final class ModuleDetailTransition {
  public static final float amount = 60.0F;
  public static final float amount2 = 14.0F;
  public static final float amount3 = 4.0F;
  public static final float amount4 = 16.0F;
  public static final float amount5 = 11.0F;
  public final Map map = new IdentityHashMap();
  public final Map map2 = new IdentityHashMap();
  public final TweenValue tweenValue = new TweenValue(300.0F, 1.0F, Easing.OUT_EXPO);
  public final DampedSpring dampedSpring = new DampedSpring(300.0F, 26.0F, 0.0F);
  public final DampedSpring dampedSpring2 = new DampedSpring(340.0F, 34.0F, 0.0F);
  public GuiModuleEntry guiModuleEntry;
  public GuiModuleEntry guiModuleEntry2;
  public GuiModuleEntry guiModuleEntry3;
  public float amount6;
  public float amount7;
  public float amount8;
  public float amount9;
  public float[] amountArray = new float[0];
  public float[] amountArray2 = new float[0];
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public SettingWidget settingWidget;
  public SettingWidget settingWidget2;
  public String string;
  public final Map map3 = new IdentityHashMap();

  public void apply(GuiModuleEntry guiModuleEntry22, boolean flag) {
    if (guiModuleEntry22 != this.guiModuleEntry2 || flag) {
      this.guiModuleEntry3 = flag ? null : this.guiModuleEntry2;
      this.guiModuleEntry2 = guiModuleEntry22;
      this.tweenValue.snapTo(0.0F);
      this.tweenValue.animateTo(1.0F);
      if (guiModuleEntry22 != null) {
        LookupData lookupData = this.viewFor(guiModuleEntry22);
        lookupData.smoothScrollState().update2();
        lookupData.staggeredTransition().apply(this.rowsOf(guiModuleEntry22).size());
        this.guiModuleEntry = null;
      }
    }
  }

  public String getString() {
    return this.string;
  }

  public void update() {
    if (this.guiModuleEntry2 != null) {
      this.viewFor(this.guiModuleEntry2)
          .staggeredTransition()
          .apply(this.rowsOf(this.guiModuleEntry2).size());
    }
  }

  public void update2() {
    if (this.guiModuleEntry2 != null) {
      this.viewFor(this.guiModuleEntry2)
          .staggeredTransition()
          .apply2(this.rowsOf(this.guiModuleEntry2).size());
    }
  }

  public LookupData viewFor(GuiModuleEntry guiModuleEntry4) {
    return (bunnythekenzo.client.LookupData)
        (this.map.computeIfAbsent(
            guiModuleEntry4,
            value -> {
              ArrayList list = new ArrayList();

              for (CollectionData collectionData :
                  (java.lang.Iterable<CollectionData>)
                      (java.lang.Iterable<?>)
                          (((bunnythekenzo.client.GuiModuleEntry) (value)).getList())) {
                ArrayList arrayList = new ArrayList();
                collectionData
                    .list()
                    .forEach(
                        settingEntry -> {
                          arrayList.add(
                              SettingWidget.getSettingWidget(
                                  (bunnythekenzo.client.setting.SettingEntry) (settingEntry)));
                        });
                list.add(arrayList);
              }

              return new LookupData(
                  list, new SmoothScrollState(), new StaggeredTransition(28.0F, 380.0F));
            }));
  }

  public int computeInt(GuiModuleEntry guiModuleEntry4) {
    return Math.clamp(
        ((java.lang.Integer) (this.map2.getOrDefault(guiModuleEntry4, 0))).intValue(),
        0,
        Math.max(0, guiModuleEntry4.getList().size() - 1));
  }

  public List rowsOf(GuiModuleEntry guiModuleEntry4) {
    List list = this.viewFor(guiModuleEntry4).list();
    return list.isEmpty()
        ? List.of()
        : ((List) list.get(this.computeInt(guiModuleEntry4)))
            .stream()
                .filter(
                    value -> {
                      return ((bunnythekenzo.client.setting.SettingWidget) (value))
                          .getSettingEntry()
                          .isVisible();
                    })
                .toList();
  }

  public void render(
      FontManager fontManager,
      float amount62,
      float amount72,
      float amount82,
      float amount92,
      float floatValue,
      float floatValue2) {
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.amount9 = amount92;
    this.string = null;
    float value = this.tweenValue.getValue();
    if (this.guiModuleEntry3 != null && value < 0.999F) {
      fontManager.update2();
      fontManager.apply3(1.0F - Easing.IN_QUAD.computeFloat(value));
      fontManager.apply(-14.0F * value, 0.0F);
      this.render(fontManager, this.guiModuleEntry3, Float.NaN, Float.NaN, true);
      fontManager.update3();
    } else if (this.guiModuleEntry3 != null) {
      this.guiModuleEntry3 = null;
    }

    if (this.guiModuleEntry2 != null) {
      fontManager.update2();
      fontManager.apply3(value);
      fontManager.apply(20.0F * (1.0F - value), 0.0F);
      boolean flag = value > 0.6F;
      this.render(
          fontManager,
          this.guiModuleEntry2,
          flag ? floatValue : Float.NaN,
          flag ? floatValue2 : Float.NaN,
          false);
      fontManager.update3();
      this.render(fontManager, floatValue, floatValue2);

      for (SettingWidget settingWidget3 :
          (java.lang.Iterable<SettingWidget>)
              (java.lang.Iterable<?>) (this.rowsOf(this.guiModuleEntry2))) {
        if (settingWidget3.test4(floatValue, floatValue2)) {
          this.string = settingWidget3.getSettingEntry().getDescription();
          break;
        }
      }
    }
  }

  public void render(
      FontManager fontManager,
      GuiModuleEntry guiModuleEntry4,
      float floatValue,
      float floatValue2,
      boolean flag) {
    ColorPalette instance = ColorPalette.getInstance();
    this.apply(fontManager, instance, guiModuleEntry4);
    float floatValue3 = this.amount7 + 60.0F;
    this.render(fontManager, instance, guiModuleEntry4, floatValue3, floatValue, floatValue2, flag);
    float floatValue4 = floatValue3 + 30.0F + 8.0F;
    float floatValue5 = this.amount9 - (floatValue4 - this.amount7) - 6.0F;
    fontManager.render(
        this.amount6 + 14.0F,
        floatValue4 - 8.0F,
        this.amount8 - 28.0F,
        1.0F,
        0.0F,
        instance.computeInt13());
    List list = this.rowsOf(guiModuleEntry4);
    if (list.isEmpty()) {
      this.render(fontManager, instance, floatValue4, floatValue5);
    } else {
      LookupData lookupData = this.viewFor(guiModuleEntry4);
      SmoothScrollState smoothScrollState = lookupData.smoothScrollState();
      float floatValue6 = 12.0F;

      for (SettingWidget settingWidget3 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list)) {
        floatValue6 += settingWidget3.computeFloat() + 4.0F;
      }

      smoothScrollState.apply(floatValue6, floatValue5);
      fontManager.update2();
      fontManager.render(this.amount6, floatValue4, this.amount8, floatValue5);
      float floatValue7 = floatValue4 + 8.0F - smoothScrollState.computeFloat();
      float floatValue8 = floatValue4 + floatValue5;

      for (int index = 0; index < list.size(); index++) {
        SettingWidget settingWidget4 = (SettingWidget) list.get(index);
        float floatValue9 = floatValue7;
        floatValue7 += settingWidget4.computeFloat() + 4.0F;
        if (!(floatValue9 > floatValue8 + 40.0F)
            && !(floatValue9 + settingWidget4.computeFloat() < floatValue4 - 40.0F)) {
          float floatValue10 = flag ? 1.0F : lookupData.staggeredTransition().computeFloat(index);
          float value =
              SmoothScrollState.computeFloat(
                  floatValue9,
                  floatValue9 + settingWidget4.computeFloat(),
                  floatValue4,
                  floatValue8);
          settingWidget4.render(
              fontManager,
              this.amount6 + 14.0F - 4.0F,
              floatValue9,
              this.amount8 - 20.0F,
              floatValue,
              floatValue2,
              floatValue10,
              value);
        } else {
          settingWidget4.update7();
        }
      }

      for (SettingWidget settingWidget5 :
          (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (list)) {
        if (settingWidget5.test()) {
          settingWidget5.render(fontManager, floatValue, floatValue2, floatValue4, floatValue8);
        }
      }

      fontManager.update3();
      if (!flag) {
        if (floatValue >= this.amount6
            && floatValue <= this.amount6 + this.amount8
            && floatValue2 >= floatValue4
            && floatValue2 <= floatValue8) {
          smoothScrollState.update();
        }

        smoothScrollState.render(
            fontManager, this.amount6 + this.amount8, floatValue4, floatValue5);
      }
    }
  }

  public void apply(
      FontManager fontManager, ColorPalette colorPalette, GuiModuleEntry guiModuleEntry4) {
    float floatValue = this.amount6 + 14.0F;
    float floatValue2 = this.amount7 + 23.0F;
    fontManager.update2();
    fontManager.render(floatValue, this.amount7, this.amount8 - 28.0F - 22.0F, 60.0F);
    fontManager.computeFloat(
        guiModuleEntry4.getString(), floatValue, floatValue2, 18.0F, colorPalette.computeInt16());
    fontManager.update3();
    fontManager.computeFloat(
        guiModuleEntry4.getString2(),
        floatValue,
        this.amount7 + 42.0F,
        11.0F,
        colorPalette.computeInt18(),
        this.amount8 - 28.0F);
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue3 = this.amount6 + this.amount8 - 14.0F - 11.0F + 3.0F;
    float floatValue4 = this.amount7 + 22.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue, floatValue2));
    if (value > 0.01F) {
      fontManager.render2(
          floatValue3, floatValue4, 11.0F, ArgbColors.withOpacity(-1, 0.07F * value));
    }

    fontManager.update2();
    fontManager.apply(floatValue3, floatValue4);
    fontManager.apply2(value * -0.25F);
    fontManager.render(
        1.0F,
        0.0F,
        4.0F,
        1.5F,
        ArgbColors.lerp(instance.computeInt18(), instance.computeInt(), value),
        false);
    fontManager.update3();
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount6 + this.amount8 - 14.0F - 11.0F + 3.0F;
    float floatValue4 = this.amount7 + 22.0F;
    float floatValue5 = floatValue - floatValue3;
    float floatValue6 = floatValue2 - floatValue4;
    return floatValue5 * floatValue5 + floatValue6 * floatValue6 <= 196.0F;
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      GuiModuleEntry guiModuleEntry4,
      float floatValue,
      float floatValue2,
      float floatValue3,
      boolean flag) {
    List list = guiModuleEntry4.getList();
    if (!list.isEmpty()) {
      float[] amountArray3 = new float[list.size()];
      float[] amountArray22 = new float[list.size()];
      float floatValue4 = this.amount6 + 14.0F - 2.0F;

      for (int index = 0; index < list.size(); index++) {
        float value = fontManager.computeFloat(((CollectionData) list.get(index)).string(), 12.5F);
        amountArray22[index] = value + 16.0F;
        amountArray3[index] = floatValue4;
        floatValue4 += amountArray22[index] + 4.0F;
      }

      if (!flag) {
        this.amountArray = amountArray3;
        this.amountArray2 = amountArray22;
      }

      int value2 = this.computeInt(guiModuleEntry4);
      float floatValue5 = floatValue + 30.0F - 3.0F;
      if (flag) {
        fontManager.render(
            amountArray3[value2] + 4.0F,
            floatValue5,
            amountArray22[value2] - 8.0F,
            2.0F,
            1.0F,
            colorPalette.computeInt());
      } else {
        float floatValue6 = amountArray3[value2] - this.amount6 + amountArray22[value2] / 2.0F;
        if (this.guiModuleEntry != guiModuleEntry4) {
          this.dampedSpring.snapTo(floatValue6);
          this.dampedSpring2.snapTo(amountArray22[value2]);
          this.guiModuleEntry = guiModuleEntry4;
        } else {
          this.dampedSpring.setTarget(floatValue6);
          this.dampedSpring2.setTarget(amountArray22[value2]);
        }

        float value3 = this.dampedSpring.advance();
        float value4 = this.dampedSpring2.advance();
        float floatValue7 =
            Math.clamp(Math.abs(this.dampedSpring.getVelocity()) * 0.05F, 0.0F, 12.0F);
        float floatValue8 = this.amount6 + value3 - (value4 + floatValue7) / 2.0F;
        float floatValue9 = value4 + floatValue7;
        fontManager.render(
            floatValue8 + 4.0F,
            floatValue5,
            floatValue9 - 8.0F,
            2.0F,
            1.0F,
            colorPalette.computeInt());

        for (int index2 = 0; index2 < list.size(); index2++) {
          boolean flag2 = index2 == value2;
          boolean bl =
              !flag
                  && floatValue2 >= amountArray3[index2]
                  && floatValue2 <= amountArray3[index2] + amountArray22[index2]
                  && floatValue3 >= floatValue
                  && floatValue3 <= floatValue + 30.0F;
          float value5 = this.tabInteraction(guiModuleEntry4, index2).computeFloat(bl);
          int intValue =
              flag2
                  ? colorPalette.computeInt16()
                  : ArgbColors.lerp(
                      colorPalette.computeInt18(), colorPalette.computeInt17(), value5);
          if (value5 > 0.01F || flag2) {
            fontManager.render(
                amountArray3[index2],
                floatValue + 2.0F,
                amountArray22[index2],
                22.0F,
                6.0F,
                flag2 ? colorPalette.computeInt12() : colorPalette.computeInt11(),
                colorPalette.computeInt6(),
                true);
            fontManager.render(
                amountArray3[index2] + 0.5F,
                floatValue + 2.5F,
                amountArray22[index2] - 1.0F,
                21.0F,
                5.5F,
                0.8F,
                flag2 ? colorPalette.computeInt(0.38F) : ArgbColors.withOpacity(-1, value5 * 0.22F),
                184549375,
                true);
          }

          fontManager.update2();
          fontManager.render2(
              amountArray3[index2] + amountArray22[index2] / 2.0F,
              floatValue + 15.0F - 2.0F,
              1.0F + value5 * 0.03F);
          fontManager.computeFloat2(
              ((CollectionData) list.get(index2)).string(),
              amountArray3[index2] + amountArray22[index2] / 2.0F,
              floatValue + 15.0F - 2.0F,
              12.5F,
              intValue);
          fontManager.update3();
        }
      }
    }
  }

  public AnimationIntensitySupport tabInteraction(GuiModuleEntry guiModuleEntry4, int intValue) {
    AnimationIntensitySupport[] animationIntensitySupportArray =
        (bunnythekenzo.client.AnimationIntensitySupport[])
            (this.map3.computeIfAbsent(
                guiModuleEntry4,
                value -> {
                  AnimationIntensitySupport[] animationIntensitySupportArray2 =
                      new AnimationIntensitySupport
                          [((bunnythekenzo.client.GuiModuleEntry) (value)).getList().size()];

                  for (int index = 0; index < animationIntensitySupportArray2.length; index++) {
                    animationIntensitySupportArray2[index] =
                        new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
                  }

                  return animationIntensitySupportArray2;
                }));
    return animationIntensitySupportArray[
        Math.clamp(intValue, 0, animationIntensitySupportArray.length - 1)];
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float floatValue3 = this.amount6 + this.amount8 / 2.0F;
    float floatValue4 = floatValue + floatValue2 / 2.0F - 14.0F;
    fontManager.render(
        floatValue3 - 14.0F, floatValue4, 28.0F, 1.0F, 0.5F, ArgbColors.withOpacity(-1, 0.12F));
    fontManager.computeFloat2(
        "No settings", floatValue3, floatValue4 + 20.0F, 13.5F, colorPalette.computeInt18());
    fontManager.computeFloat2(
        "Nothing to configure in this group",
        floatValue3,
        floatValue4 + 36.0F,
        11.0F,
        colorPalette.computeInt19());
  }

  public boolean test2(float floatValue, float floatValue2) {
    return floatValue >= this.amount6
        && floatValue <= this.amount6 + this.amount8
        && floatValue2 >= this.amount7
        && floatValue2 <= this.amount7 + this.amount9;
  }

  public boolean test3(float floatValue, float floatValue2) {
    return this.guiModuleEntry2 != null
        && this.tweenValue.getValue() >= 0.6F
        && this.test(floatValue, floatValue2);
  }

  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (this.guiModuleEntry2 != null && !(this.tweenValue.getValue() < 0.6F)) {
      float floatValue3 = this.amount7 + 60.0F;

      for (int index = 0; index < this.amountArray.length; index++) {
        if (floatValue >= this.amountArray[index]
            && floatValue <= this.amountArray[index] + this.amountArray2[index]
            && floatValue2 >= floatValue3
            && floatValue2 <= floatValue3 + 30.0F) {
          this.update4();
          this.apply(index);
          return true;
        }
      }

      for (SettingWidget settingWidget3 :
          (java.lang.Iterable<SettingWidget>)
              (java.lang.Iterable<?>) (this.rowsOf(this.guiModuleEntry2))) {
        if (settingWidget3.test() && settingWidget3.test3(floatValue, floatValue2)) {
          this.settingWidget = settingWidget3;
          return true;
        }
      }

      for (SettingWidget settingWidget4 :
          (java.lang.Iterable<SettingWidget>)
              (java.lang.Iterable<?>) (this.rowsOf(this.guiModuleEntry2))) {
        if (settingWidget4.test(floatValue, floatValue2, intValue)) {
          if (this.settingWidget2 != null && this.settingWidget2 != settingWidget4) {
            this.settingWidget2.update6();
          }

          this.settingWidget2 = settingWidget4.test2() ? settingWidget4 : null;
          this.settingWidget = settingWidget4;
          return true;
        }
      }

      this.update4();
      return this.test2(floatValue, floatValue2);
    } else {
      return this.test2(floatValue, floatValue2);
    }
  }

  public void apply(int intValue) {
    int value = this.computeInt(this.guiModuleEntry2);
    if (intValue != value) {
      this.map2.put(this.guiModuleEntry2, intValue);
      LookupData lookupData = this.viewFor(this.guiModuleEntry2);
      lookupData.smoothScrollState().update2();
      lookupData.staggeredTransition().apply(this.rowsOf(this.guiModuleEntry2).size());
      int intValue2 = Math.max(1, this.guiModuleEntry2.getList().size() - 1);
      ClientAudio.apply((float) intValue / intValue2);
    }
  }

  public void apply(float floatValue, float floatValue2) {
    if (this.settingWidget != null) {
      this.settingWidget.apply(floatValue, floatValue2);
    }
  }

  public void update3() {
    if (this.settingWidget != null) {
      this.settingWidget.update5();
      this.settingWidget = null;
    }
  }

  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (this.guiModuleEntry2 == null) {
      return false;
    }

    for (SettingWidget settingWidget3 :
        (java.lang.Iterable<SettingWidget>)
            (java.lang.Iterable<?>) (this.rowsOf(this.guiModuleEntry2))) {
      if (settingWidget3.test() && settingWidget3.test(floatValue, floatValue2, doubleValue)) {
        return true;
      }
    }

    if (!this.test2(floatValue, floatValue2)) {
      return false;
    }

    this.viewFor(this.guiModuleEntry2).smoothScrollState().test(doubleValue);
    return true;
  }

  public boolean test(int intValue) {
    if (this.settingWidget2 == null) {
      return false;
    }

    boolean flag = this.settingWidget2.test2(intValue);
    if (!this.settingWidget2.test2()) {
      this.settingWidget2 = null;
    }

    return flag;
  }

  public boolean test2(int intValue) {
    return this.settingWidget2 != null && this.settingWidget2.test(intValue);
  }

  public boolean test() {
    return this.settingWidget2 != null && this.settingWidget2.test2();
  }

  public boolean test2() {
    if (!this.test()) {
      return false;
    }

    this.update4();
    return true;
  }

  public void update4() {
    if (this.settingWidget2 != null) {
      this.settingWidget2.update6();
    }

    this.settingWidget2 = null;
  }
}

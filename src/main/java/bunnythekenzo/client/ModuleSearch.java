package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.ui.AntiCheatWarningDialog;

public final class ModuleSearch {
  public static final float amount = 32.0F;
  public static final float amount2 = 16.0F;
  public static final float amount3 = 340.0F;
  public static final float amount4 = 46.0F;
  public final List list;
  public final List list2 = new ArrayList();
  public final List list3 = new ArrayList();
  public final List list4 = new ArrayList();
  public final int[] countArray;
  public final TweenValue tweenValue = new TweenValue(340.0F, 1.0F, Easing.OUT_EXPO);
  public final AntiCheatWarningDialog antiCheatWarningDialog = new AntiCheatWarningDialog();
  public int count;
  public int count2 = -1;
  public int count3 = 1;
  public static final float amount5 = 22.0F;
  public static final float amount6 = 210.0F;
  public final StringBuilder stringBuilder = new StringBuilder();
  public boolean enabled;
  public final List list5 = new ArrayList();
  public final List list6 = new ArrayList();
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(26.0F, 400.0F);
  public int count4;
  public float amount7;
  public float amount8;
  public float amount9;
  public float amount10;
  public ModuleSearchSupport5 moduleSearchSupport5;
  public String string;
  public GuiModuleEntry guiModuleEntry;
  public GuiModuleEntry guiModuleEntry2;

  public ModuleSearch(List list7) {
    this.list = list7;
    this.countArray = new int[list7.size()];
    Arrays.fill(this.countArray, -1);

    for (CollectionData3 collectionData3 :
        (java.lang.Iterable<CollectionData3>) (java.lang.Iterable<?>) (list7)) {
      ArrayList arrayList = new ArrayList();

      for (GuiModuleEntry guiModuleEntry3 :
          (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (collectionData3.list())) {
        arrayList.add(new ModuleSearchSupport5(guiModuleEntry3, collectionData3.enabled()));
      }

      this.list2.add(arrayList);
      this.list3.add(new SmoothScrollState());
      this.list4.add(new StaggeredTransition(26.0F, 400.0F));
    }

    this.update6();
  }

  public int getCount() {
    return this.count;
  }

  public ClientData3 getClientData3(String text, boolean flag) {
    for (ModuleSearchSupport5 moduleSearchSupport52 :
        (java.lang.Iterable<ModuleSearchSupport5>)
            (java.lang.Iterable<?>) (this.test4() ? this.list5 : this.getList(this.count))) {
      if (moduleSearchSupport52.getGuiModuleEntry().getString().equals(text)) {
        return moduleSearchSupport52.getClientData3(flag);
      }
    }

    return new ClientData3(Float.NaN, Float.NaN);
  }

  public boolean test(int intValue) {
    return ((CollectionData3) this.list.get(intValue)).enabled()
        && !this.antiCheatWarningDialog.isEnabled();
  }

  public boolean test() {
    return !this.test4() && this.test(this.count);
  }

  public boolean test2() {
    return this.antiCheatWarningDialog.isEnabled();
  }

  public boolean test3() {
    return this.guiModuleEntry != null;
  }

  public void apply(GuiModuleEntry guiModuleEntry22) {
    if (this.antiCheatWarningDialog.isEnabled()) {
      this.apply2(guiModuleEntry22);
    } else {
      this.guiModuleEntry2 = guiModuleEntry22;
      this.antiCheatWarningDialog.update();
    }
  }

  public void update() {
    this.guiModuleEntry2 = null;
  }

  public void apply2(GuiModuleEntry guiModuleEntry3) {
    for (List list7 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (ModuleSearchSupport5 moduleSearchSupport52 :
          (java.lang.Iterable<ModuleSearchSupport5>) (java.lang.Iterable<?>) (list7)) {
        if (moduleSearchSupport52.getGuiModuleEntry() == guiModuleEntry3) {
          moduleSearchSupport52.update2();
          return;
        }
      }
    }
  }

  public void apply(int intValue) {
    if (this.guiModuleEntry != null) {
      this.guiModuleEntry.apply(intValue == 256 ? -1 : intValue);
      ClientAudio.apply(this.guiModuleEntry.test3());
      this.guiModuleEntry = null;
      this.update2();
    }
  }

  public void apply3(GuiModuleEntry guiModuleEntry3) {
    this.guiModuleEntry = guiModuleEntry3;
    this.update2();
    ClientAudio.update6();
  }

  public void update2() {
    for (List list7 : (java.lang.Iterable<List>) (java.lang.Iterable<?>) (this.list2)) {
      for (ModuleSearchSupport5 moduleSearchSupport52 :
          (java.lang.Iterable<ModuleSearchSupport5>) (java.lang.Iterable<?>) (list7)) {
        moduleSearchSupport52.setEnabled2(
            moduleSearchSupport52.getGuiModuleEntry() == this.guiModuleEntry);
      }
    }
  }

  public void update3() {
    this.countArray[this.count] = -1;
    this.update6();
  }

  public String getString() {
    return this.string;
  }

  public void update4() {
    if (this.test()) {
      this.antiCheatWarningDialog.update();
    }

    if (this.test4()) {
      this.staggeredTransition.apply(this.list5.size());
    } else {
      ((StaggeredTransition) this.list4.get(this.count)).apply(this.getList(this.count).size());
    }
  }

  public void update5() {
    if (this.test()) {
      this.antiCheatWarningDialog.update2();
    }

    if (this.test4()) {
      this.staggeredTransition.apply2(this.list5.size());
    } else {
      ((StaggeredTransition) this.list4.get(this.count)).apply2(this.getList(this.count).size());
    }
  }

  public void apply2(int count5) {
    if (count5 != this.count) {
      this.count3 = Integer.signum(count5 - this.count);
      this.count2 = this.count;
      this.count = count5;
      if (this.test4()) {
        this.count2 = -1;
        this.tweenValue.snapTo(1.0F);
        this.update6();
      } else {
        this.tweenValue.snapTo(0.0F);
        this.tweenValue.animateTo(1.0F);
        if (this.test()) {
          this.antiCheatWarningDialog.update();
        }

        ((StaggeredTransition) this.list4.get(this.count)).apply(this.getList(this.count).size());
        this.update6();
      }
    }
  }

  public List getList(int intValue) {
    return (List) this.list2.get(intValue);
  }

  public void update6() {
    for (int index = 0; index < this.list2.size(); index++) {
      List list7 = this.getList(index);

      for (int index2 = 0; index2 < list7.size(); index2++) {
        ((ModuleSearchSupport5) list7.get(index2))
            .apply(index == this.count && index2 == this.countArray[index]);
      }
    }
  }

  public void render(
      FontManager fontManager,
      float amount72,
      float amount82,
      float amount92,
      float amount102,
      float floatValue,
      float floatValue2) {
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.amount9 = amount92;
    this.amount10 = amount102;
    this.string = null;
    ColorPalette instance = ColorPalette.getInstance();
    this.render(fontManager, instance, amount72, amount82, amount92, floatValue, floatValue2);
    float floatValue3 = amount82 + 32.0F;
    float floatValue4 = amount102 - 32.0F;
    float value = this.tweenValue.getValue();
    fontManager.update2();
    fontManager.render(amount72, floatValue3, amount92, floatValue4);
    ModuleSearchData4 moduleSearchData4 =
        ModuleSearchSupport7.getModuleSearchData4(amount72, floatValue3, amount92, floatValue4);
    if (this.count2 >= 0 && value < 0.999F) {
      fontManager.update2();
      fontManager.apply3(1.0F - Easing.IN_QUAD.computeFloat(value));
      fontManager.apply(-this.count3 * 46.0F * value, -this.count3 * 10 * value);
      this.render(fontManager, this.count2, floatValue3, floatValue4, Float.NaN, Float.NaN, true);
      fontManager.update3();
    } else if (this.count2 >= 0) {
      this.count2 = -1;
    }

    fontManager.update2();
    fontManager.apply3(value);
    fontManager.apply(this.count3 * 46.0F * (1.0F - value), this.count3 * 10 * (1.0F - value));
    boolean flag = value > 0.98F;
    this.render(
        fontManager,
        this.count,
        floatValue3,
        floatValue4,
        flag ? floatValue : Float.NaN,
        flag ? floatValue2 : Float.NaN,
        false);
    fontManager.update3();
    fontManager.update3();
    ModuleSearchSupport7.apply(moduleSearchData4);
    SmoothScrollState smoothScrollState2 =
        this.test4() ? this.smoothScrollState : (SmoothScrollState) this.list3.get(this.count);
    if (floatValue >= amount72
        && floatValue <= amount72 + amount92
        && floatValue2 >= floatValue3
        && floatValue2 <= floatValue3 + floatValue4) {
      smoothScrollState2.update();
    }

    smoothScrollState2.render(fontManager, amount72 + amount92, floatValue3, floatValue4);
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    float floatValue6 = Math.min(210.0F, floatValue3 - 32.0F - 90.0F);
    float floatValue7 = floatValue + 16.0F;
    float floatValue8 = floatValue2 + 5.0F + 1.0F;
    boolean flag = this.test(floatValue4, floatValue5);
    boolean flag2 = this.enabled || flag;
    fontManager.render(
        floatValue7,
        floatValue8,
        floatValue6,
        22.0F,
        11.0F,
        ArgbColors.withOpacity(-15462372, 0.85F));
    fontManager.render(
        floatValue7,
        floatValue8,
        floatValue6,
        22.0F,
        11.0F,
        1.0F,
        this.enabled
            ? colorPalette.computeInt(0.55F)
            : ArgbColors.withOpacity(-1, flag2 ? 0.14F : 0.08F));
    float floatValue9 = floatValue8 + 11.0F;
    int intValue = this.enabled ? colorPalette.computeInt() : colorPalette.computeInt18();
    fontManager.render2(floatValue7 + 13.0F, floatValue9 - 1.0F, 4.0F, 1.3F, intValue);
    fontManager.render2(
        floatValue7 + 16.0F,
        floatValue9 + 2.0F,
        floatValue7 + 18.5F,
        floatValue9 + 4.5F,
        1.3F,
        intValue);
    float floatValue10 = floatValue7 + 25.0F;
    float floatValue11 = floatValue7 + floatValue6 - (this.stringBuilder.isEmpty() ? 10 : 24);
    if (this.stringBuilder.isEmpty()) {
      fontManager.computeFloat(
          "CrackedByDexter", floatValue10, floatValue9, 11.0F, colorPalette.computeInt19());
    } else {
      float value =
          fontManager.computeFloat(
              this.stringBuilder.toString(),
              floatValue10,
              floatValue9,
              11.0F,
              colorPalette.computeInt16(),
              floatValue11 - floatValue10);
      if (this.enabled && System.nanoTime() / 500000000L % 2L == 0L) {
        fontManager.render(
            floatValue10 + value + 2.0F,
            floatValue9 - 6.0F,
            1.4F,
            12.0F,
            0.7F,
            colorPalette.computeInt());
      }

      fontManager.render5(
          floatValue7 + floatValue6 - 22.0F,
          floatValue9 - 6.0F,
          12.0F,
          1.4F,
          colorPalette.computeInt18());
    }

    String text;
    if (this.stringBuilder.isEmpty()) {
      text = ((CollectionData3) this.list.get(this.count)).list().size() + " modules";
    } else {
      text = this.list5.size() + (this.list5.size() == 1 ? " result" : " results");
      if (this.count4 > 0) {
        text = text + "  ·  " + this.count4 + " locked";
      }
    }

    fontManager.computeFloat3(
        text,
        floatValue + floatValue3 - 16.0F - 2.0F,
        floatValue9,
        9.5F,
        colorPalette.computeInt19());
  }

  public void render(
      FontManager fontManager,
      int intValue,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      boolean flag) {
    boolean flag2 = this.test4();
    if (!flag2 && this.test(intValue)) {
      ((SmoothScrollState) this.list3.get(intValue)).apply(0.0F, floatValue2);
      this.antiCheatWarningDialog.render(
          fontManager,
          this.amount7,
          floatValue,
          this.amount9,
          floatValue2,
          floatValue3,
          floatValue4);
    } else {
      List list7 = flag2 ? this.list5 : this.getList(intValue);
      SmoothScrollState smoothScrollState2 =
          flag2 ? this.smoothScrollState : (SmoothScrollState) this.list3.get(intValue);
      StaggeredTransition staggeredTransition2 =
          flag2 ? this.staggeredTransition : (StaggeredTransition) this.list4.get(intValue);
      float floatValue5 = this.amount9 - 32.0F;
      float floatValue6 = (floatValue5 - 10.0F) / 2.0F;
      int intValue2 = (list7.size() + 2 - 1) / 2;
      float floatValue7 = intValue2 * 52.0F - 10.0F + 16.0F;
      smoothScrollState2.apply(floatValue7, floatValue2);
      float floatValue8 = floatValue + 8.0F - smoothScrollState2.computeFloat();
      float floatValue9 = floatValue + floatValue2;
      if (list7.isEmpty()) {
        ColorPalette instance = ColorPalette.getInstance();
        float floatValue10 = this.amount7 + this.amount9 / 2.0F;
        float floatValue11 = floatValue + floatValue2 / 2.0F - 10.0F;
        fontManager.render(
            floatValue10 - 14.0F,
            floatValue11 - 12.0F,
            28.0F,
            1.0F,
            0.5F,
            ArgbColors.withOpacity(-1, 0.12F));
        fontManager.computeFloat2(
            "No modules match", floatValue10, floatValue11 + 8.0F, 13.5F, instance.computeInt18());
        fontManager.computeFloat2(
            "Try a shorter search",
            floatValue10,
            floatValue11 + 24.0F,
            11.0F,
            instance.computeInt19());
      } else {
        for (int index = 0; index < list7.size(); index++) {
          ModuleSearchSupport5 moduleSearchSupport52 = (ModuleSearchSupport5) list7.get(index);
          float floatValue12 = this.amount7 + 16.0F + index % 2 * (floatValue6 + 10.0F);
          float floatValue13 = floatValue8 + index / 2 * 52.0F;
          if (!(floatValue13 > floatValue9 + 30.0F)
              && !(floatValue13 + 42.0F < floatValue - 30.0F)) {
            float floatValue14 = flag ? 1.0F : staggeredTransition2.computeFloat(index);
            float value =
                SmoothScrollState.computeFloat(
                    floatValue13, floatValue13 + 42.0F, floatValue, floatValue9);
            moduleSearchSupport52.setString(
                flag2
                    ? ((CollectionData3) this.list.get(((int[]) this.list6.get(index))[0])).string()
                    : null);
            moduleSearchSupport52.render(
                fontManager,
                floatValue12,
                floatValue13,
                floatValue6,
                floatValue3,
                floatValue4,
                floatValue14,
                value);
            if (!flag) {
              String string2 = moduleSearchSupport52.getString();
              if (string2 != null) {
                this.string = string2;
              }
            }
          } else {
            moduleSearchSupport52.update3();
          }
        }
      }
    }
  }

  public boolean test4() {
    return !this.stringBuilder.isEmpty();
  }

  public void update7() {
    this.list5.clear();
    this.list6.clear();
    this.count4 = 0;
    String text = this.stringBuilder.toString().toLowerCase(Locale.ROOT).trim();
    if (text.isEmpty()) {
      ((StaggeredTransition) this.list4.get(this.count)).apply(this.getList(this.count).size());
    } else {
      for (int index = 0; index < this.list.size(); index++) {
        List list7 = ((CollectionData3) this.list.get(index)).list();
        boolean flag = this.test(index);

        for (int index2 = 0; index2 < list7.size(); index2++) {
          if (((GuiModuleEntry) list7.get(index2))
              .getString()
              .toLowerCase(Locale.ROOT)
              .contains(text)) {
            if (flag) {
              this.count4++;
            } else {
              this.list5.add((ModuleSearchSupport5) this.getList(index).get(index2));
              this.list6.add(new int[] {index, index2});
            }
          }
        }
      }

      this.count2 = -1;
      this.tweenValue.snapTo(1.0F);
      this.smoothScrollState.update2();
      this.staggeredTransition.apply(this.list5.size());
    }
  }

  public boolean test2(int intValue) {
    if (this.enabled && intValue >= 32 && this.stringBuilder.length() < 24) {
      this.stringBuilder.appendCodePoint(intValue);
      this.update7();
      return true;
    } else {
      return false;
    }
  }

  public boolean test3(int intValue) {
    if (!this.enabled) {
      return false;
    }

    switch (intValue) {
      case 257:
      case 335:
        this.enabled = false;
        break;
      case 259:
        if (!this.stringBuilder.isEmpty()) {
          this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
          this.update7();
        }
        break;
      default:
        return false;
    }

    return true;
  }

  public boolean test5() {
    if (this.stringBuilder.isEmpty() && !this.enabled) {
      return false;
    }

    boolean flag = !this.stringBuilder.isEmpty();
    this.stringBuilder.setLength(0);
    this.enabled = false;
    if (flag) {
      this.update7();
      ClientAudio.update7();
    }

    return true;
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = Math.min(210.0F, this.amount9 - 32.0F - 90.0F);
    float floatValue4 = this.amount7 + 16.0F;
    float floatValue5 = this.amount8 + 5.0F + 1.0F;
    return floatValue >= floatValue4
        && floatValue <= floatValue4 + floatValue3
        && floatValue2 >= floatValue5
        && floatValue2 <= floatValue5 + 22.0F;
  }

  public boolean test2(float floatValue, float floatValue2) {
    return floatValue >= this.amount7
        && floatValue <= this.amount7 + this.amount9
        && floatValue2 >= this.amount8
        && floatValue2 <= this.amount8 + this.amount10;
  }

  public boolean mouseClicked(
      float floatValue, float floatValue2, ModuleSearchContract moduleSearchContract) {
    if (this.test(floatValue, floatValue2)) {
      float floatValue3 = Math.min(210.0F, this.amount9 - 32.0F - 90.0F);
      boolean flag =
          !this.stringBuilder.isEmpty() && floatValue >= this.amount7 + 16.0F + floatValue3 - 26.0F;
      if (flag) {
        this.test5();
      } else {
        this.enabled = true;
        ClientAudio.update5();
      }

      return true;
    } else {
      this.enabled = false;
      if (this.tweenValue.getValue() < 0.98F) {
        return this.test2(floatValue, floatValue2);
      }

      if (!(floatValue2 < this.amount8 + 32.0F) && !(floatValue2 > this.amount8 + this.amount10)) {
        if (this.test()) {
          if (this.antiCheatWarningDialog.test(
                  floatValue, floatValue2, moduleSearchContract::update)
              && this.antiCheatWarningDialog.isEnabled()) {
            this.update9();
          }

          return true;
        } else {
          List list7 = this.test4() ? this.list5 : this.getList(this.count);

          for (int intValue :
              IntStream.range(0, list7.size())
                  .boxed()
                  .sorted(
                      (intValue2, intValue3) -> {
                        return Float.compare(
                            ((ModuleSearchSupport5) list7.get(intValue3)).computeFloat(),
                            ((ModuleSearchSupport5) list7.get(intValue2)).computeFloat());
                      })
                  .toList()) {
            ModuleSearchSupport5 moduleSearchSupport52 = (ModuleSearchSupport5) list7.get(intValue);
            int index = this.test4() ? ((int[]) this.list6.get(intValue))[0] : this.count;
            switch (ModuleSearchSupport3.countArray[
                moduleSearchSupport52.getMenuInteraction(floatValue, floatValue2).ordinal()]) {
              case 1:
                this.apply3(moduleSearchSupport52.getGuiModuleEntry());
                return true;
              case 2:
                moduleSearchSupport52.apply(floatValue, floatValue2);
                this.moduleSearchSupport5 = moduleSearchSupport52;
                moduleSearchContract.apply(moduleSearchSupport52.getGuiModuleEntry());
                return true;
              case 3:
                moduleSearchSupport52.apply(floatValue, floatValue2);
                this.moduleSearchSupport5 = moduleSearchSupport52;
                int intValue4 = this.test4() ? ((int[]) this.list6.get(intValue))[1] : intValue;
                boolean flag2 = this.count == index && this.countArray[index] == intValue4;
                if (!flag2) {
                  this.count = index;
                  this.countArray[index] = intValue4;
                  this.update6();
                }

                moduleSearchContract.apply(moduleSearchSupport52.getGuiModuleEntry(), flag2);
                return true;
              case 4:
            }
          }

          return this.test2(floatValue, floatValue2);
        }
      } else {
        return this.test2(floatValue, floatValue2);
      }
    }
  }

  public void update8() {
    this.antiCheatWarningDialog.update4();
    if (this.moduleSearchSupport5 != null) {
      this.moduleSearchSupport5.update();
      this.moduleSearchSupport5 = null;
    }
  }

  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (!this.test2(floatValue, floatValue2)) {
      return false;
    }

    if (this.test()) {
      return true;
    }

    (this.test4() ? this.smoothScrollState : (SmoothScrollState) this.list3.get(this.count))
        .test(doubleValue);
    return true;
  }

  public boolean test4(int intValue) {
    if (!this.test()) {
      return false;
    }

    if (!this.antiCheatWarningDialog.test(intValue)) {
      return false;
    }

    this.update9();
    return true;
  }

  public void update9() {
    this.count2 = -1;
    this.tweenValue.snapTo(0.0F);
    this.tweenValue.animateTo(1.0F);
    ((SmoothScrollState) this.list3.get(this.count)).update2();
    ((StaggeredTransition) this.list4.get(this.count)).apply(this.getList(this.count).size());
    if (this.guiModuleEntry2 != null) {
      this.apply2(this.guiModuleEntry2);
      this.guiModuleEntry2 = null;
    }
  }
}

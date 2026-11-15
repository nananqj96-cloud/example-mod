package bunnythekenzo.client.hud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientData;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.StaffListHudSupport;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.module.StaffListModule;
import bunnythekenzo.client.render.FontManager;

public class StaffListHud extends HudElement {
  public static final float amount = 8.0F;
  public static final float amount2 = 15.0F;
  public static final float amount3 = 5.0F;
  public static final float amount4 = 18.0F;
  public static final float amount5 = 16.0F;
  public static final float amount6 = 13.0F;
  public static final float amount7 = 15.0F;
  public static final float amount8 = 11.0F;
  public static final float amount9 = 9.0F;
  public static final float amount10 = 12.5F;
  public static final float amount11 = 9.0F;
  public static final float amount12 = 11.0F;
  public static final float amount13 = 92.0F;
  public static final float amount14 = 128.0F;
  public static final int count = -11870592;
  public static final int count2 = -340971;
  public static final int count3 = -495247;
  public final StaffListModule staffListModule;
  public final ThemeManager themeManager;
  public final Map map = new LinkedHashMap();

  public StaffListHud(StaffListModule staffListModule2, ThemeManager themeManager2) {
    super(
        "staffList",
        "Staff List",
        "Server staff currently online",
        0.008F,
        0.27F,
        HudElement.getHudElementContract(staffListModule2));
    this.staffListModule = staffListModule2;
    this.themeManager = themeManager2;
  }

  public int computeInt(List list) {
    return Math.min(list.size(), Math.max(1, this.staffListModule.maxRowsSetting.intValue()));
  }

  public List getList(List list) {
    int value = this.computeInt(list);
    HashSet hashSet = new HashSet();

    for (int index = 0; index < value && index < list.size(); index++) {
      ClientData clientData = (ClientData) list.get(index);
      hashSet.add(clientData.string());
      StaffListHudSupport staffListHudSupport =
          (StaffListHudSupport) this.map.get(clientData.string());
      if (staffListHudSupport == null) {
        staffListHudSupport = new StaffListHudSupport(clientData);
        this.map.put(clientData.string(), staffListHudSupport);
      } else {
        staffListHudSupport.clientData = clientData;
      }

      staffListHudSupport.curveTween.animateTo(1.0F);
    }

    ArrayList arrayList = new ArrayList();

    for (int index2 = 0; index2 < value && index2 < list.size(); index2++) {
      arrayList.add((StaffListHudSupport) this.map.get(((ClientData) list.get(index2)).string()));
    }

    Iterator iterator = this.map.values().iterator();

    while (iterator.hasNext()) {
      StaffListHudSupport staffListHudSupport2 = (StaffListHudSupport) iterator.next();
      if (!hashSet.contains(staffListHudSupport2.clientData.string())) {
        staffListHudSupport2.curveTween.animateTo(0.0F);
        if (staffListHudSupport2.curveTween.getValue() <= 0.01F) {
          iterator.remove();
        } else {
          arrayList.add(staffListHudSupport2);
        }
      }
    }

    return arrayList;
  }

  public float computeFloat(FontManager fontManager, ClientData clientData) {
    float floatValue =
        15.0F + Math.min(fontManager.computeFloat(clientData.string(), 12.5F), 128.0F);
    if ((Boolean) this.staffListModule.showRankSetting.getValue()
        && !clientData.string2().isEmpty()) {
      floatValue += 6.0F + fontManager.computeFloat(clientData.string2(), 9.0F);
    }

    if ((Boolean) this.staffListModule.showPingSetting.getValue()) {
      floatValue += 19.0F;
    }

    return floatValue;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    List list2 = this.staffListModule.getList2();
    String text = Integer.toString(list2.size());
    float floatValue =
        Math.max(
            92.0F,
            fontManager.computeFloat("STAFF", 9.0F)
                + 10.0F
                + fontManager.computeFloat(text, 8.5F)
                + 9.0F);

    for (StaffListHudSupport staffListHudSupport :
        (java.lang.Iterable<StaffListHudSupport>) (java.lang.Iterable<?>) (this.getList(list2))) {
      floatValue =
          Math.max(floatValue, this.computeFloat(fontManager, staffListHudSupport.clientData));
    }

    if (list2.isEmpty()) {
      floatValue = Math.max(floatValue, 14.0F + fontManager.computeFloat("No staff online", 11.0F));
    }

    return 16.0F + floatValue;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    List list2 = this.staffListModule.getList2();
    float floatValue = 0.0F;

    for (StaffListHudSupport staffListHudSupport :
        (java.lang.Iterable<StaffListHudSupport>) (java.lang.Iterable<?>) (this.getList(list2))) {
      floatValue += 18.0F * staffListHudSupport.curveTween.getValue();
    }

    if (list2.isEmpty() && floatValue < 0.5F) {
      floatValue = 16.0F;
    }

    float floatValue2 = 28.0F + floatValue + 8.0F;
    if (list2.size() > this.computeInt(list2)) {
      floatValue2 += 13.0F;
    }

    return floatValue2;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    List list2 = this.staffListModule.getList2();
    fontManager.render2(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        13.0F,
        8.0F,
        ArgbColors.withOpacity(-16777216, 0.3F));
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        10.0F,
        colorPalette.computeInt7(),
        colorPalette.computeInt8(),
        true);
    this.render(fontManager, colorPalette, floatValue, floatValue2, floatValue3, list2.size());
    float floatValue5 = floatValue2 + 8.0F + 15.0F + 5.0F;
    if (list2.isEmpty()) {
      float floatValue6 = floatValue5 + 8.0F;
      fontManager.render4(floatValue + 8.0F, floatValue6 - 4.0F, 8.0F, 1.7F, -11870592);
      fontManager.computeFloat(
          "No staff online",
          floatValue + 8.0F + 14.0F,
          floatValue6,
          11.0F,
          colorPalette.computeInt18());
    } else {
      for (StaffListHudSupport staffListHudSupport :
          (java.lang.Iterable<StaffListHudSupport>) (java.lang.Iterable<?>) (this.getList(list2))) {
        float floatValue7 = Math.clamp(staffListHudSupport.curveTween.getValue(), 0.0F, 1.0F);
        if (!(floatValue7 <= 0.01F)) {
          this.render(
              fontManager,
              colorPalette,
              staffListHudSupport.clientData,
              floatValue,
              floatValue5,
              floatValue3,
              floatValue7);
          floatValue5 += 18.0F * floatValue7;
        }
      }

      int intValue = list2.size() - this.computeInt(list2);
      if (intValue > 0) {
        fontManager.computeFloat(
            "+" + intValue + " more",
            floatValue + 8.0F + 15.0F,
            floatValue5 + 6.5F,
            9.0F,
            colorPalette.computeInt19());
      }
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    float floatValue4 = floatValue2 + 8.0F + 7.5F;
    fontManager.computeFloat(
        "STAFF", floatValue + 8.0F, floatValue4, 9.0F, colorPalette.computeInt16());
    String text = Integer.toString(intValue);
    float value = fontManager.computeFloat(text, 8.5F);
    float floatValue5 = value + 9.0F;
    float floatValue6 = floatValue + floatValue3 - 8.0F - floatValue5;
    float floatValue7 = floatValue4 - 6.25F;
    boolean flag = intValue > 0;
    fontManager.render(
        floatValue6,
        floatValue7,
        floatValue5,
        12.5F,
        6.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), flag ? 0.2F : 0.1F));
    fontManager.render(
        floatValue6,
        floatValue7,
        floatValue5,
        12.5F,
        6.0F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), flag ? 0.5F : 0.22F));
    fontManager.computeFloat(
        text,
        floatValue6 + 4.5F,
        floatValue4,
        8.5F,
        flag ? colorPalette.computeInt() : colorPalette.computeInt18());
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      ClientData clientData,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = floatValue2 + 9.0F;
    int intValue = clientData.test() ? clientData.count() : colorPalette.getCount();
    fontManager.update2();
    fontManager.apply3(floatValue4);
    fontManager.apply((1.0F - floatValue4) * -10.0F, 0.0F);
    this.render(
        fontManager,
        floatValue + 8.0F + 7.5F - 2.0F,
        floatValue5,
        4.2F,
        intValue,
        clientData.enabled());
    float floatValue6 = floatValue + 8.0F + 15.0F;
    int intValue2 =
        clientData.enabled() ? colorPalette.computeInt18() : colorPalette.computeInt16();
    float value =
        fontManager.computeFloat(
            clientData.string(), floatValue6, floatValue5, 12.5F, intValue2, 128.0F);
    if ((Boolean) this.staffListModule.showRankSetting.getValue()
        && !clientData.string2().isEmpty()) {
      int intValue3 =
          clientData.test() ? ArgbColors.lighten(intValue, 0.25F) : colorPalette.getCount();
      fontManager.computeFloat(
          clientData.string2(),
          floatValue6 + value + 6.0F,
          floatValue5 + 0.5F,
          9.0F,
          ArgbColors.withOpacity(intValue3, clientData.enabled() ? 0.6F : 0.95F));
    }

    if ((Boolean) this.staffListModule.showPingSetting.getValue()) {
      this.render(
          fontManager,
          floatValue + floatValue3 - 8.0F - 11.0F,
          floatValue5,
          clientData.count2(),
          colorPalette);
    }

    fontManager.update3();
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      boolean flag) {
    if (flag) {
      fontManager.update2();
      fontManager.apply(floatValue, floatValue2);
      fontManager.apply2((float) (Math.PI / 4));
      fontManager.render(
          -floatValue3 * 0.55F,
          -floatValue3 * 0.55F,
          floatValue3 * 1.1F,
          floatValue3 * 1.1F,
          floatValue3 * 0.25F,
          1.2F,
          ArgbColors.withOpacity(intValue, 0.55F));
      fontManager.update3();
    } else {
      int int2 = ArgbColors.lighten(intValue, 0.4F);
      fontManager.render3(
          floatValue,
          floatValue2,
          floatValue3 * 0.5F,
          floatValue3 * 1.3F,
          ArgbColors.withOpacity(intValue, 0.5F));
      int value = ArgbColors.withOpacity(int2, 0.9F);
      fontManager.render2(
          floatValue,
          floatValue2 - floatValue3,
          floatValue,
          floatValue2 + floatValue3,
          1.2F,
          value);
      fontManager.render2(
          floatValue - floatValue3,
          floatValue2,
          floatValue + floatValue3,
          floatValue2,
          1.2F,
          value);
      fontManager.update2();
      fontManager.apply(floatValue, floatValue2);
      fontManager.apply2((float) (Math.PI / 4));
      fontManager.render(
          -floatValue3 * 0.5F,
          -floatValue3 * 0.5F,
          floatValue3,
          floatValue3,
          floatValue3 * 0.22F,
          intValue);
      fontManager.update3();
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      int intValue,
      ColorPalette colorPalette) {
    int intValue2 =
        intValue < 0
            ? 0
            : (intValue <= 80
                ? 4
                : (intValue <= 150 ? 3 : (intValue <= 300 ? 2 : (intValue <= 600 ? 1 : 0))));
    int intValue3 = intValue2 >= 3 ? -11870592 : (intValue2 == 2 ? -340971 : -495247);

    for (int index = 0; index < 4; index++) {
      float floatValue3 = 2.0F + index * 2.0F;
      float floatValue4 = floatValue + index * 3.0F;
      float floatValue5 = floatValue2 + 4.0F - floatValue3;
      int intValue4 =
          index < intValue2
              ? intValue3
              : ArgbColors.withOpacity(colorPalette.computeInt19(), 0.45F);
      fontManager.render(floatValue4, floatValue5, 2.0F, floatValue3, 0.5F, intValue4);
    }
  }
}

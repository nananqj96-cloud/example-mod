package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.lwjgl.nanovg.NanoVG;
import bunnythekenzo.client.render.FontManager;

public final class ModuleSearchSupport7 implements AutoCloseable {
  public static final int count = 4;
  public static final int count2 = 816;
  public static final float amount = 0.5F;
  public static final float amount2 = 0.57F;
  public static ModuleSearchSupport7 moduleSearchSupport7;
  public final ModuleSearchSupport8 moduleSearchSupport8 = new ModuleSearchSupport8(2048, 1024);
  public final List list = new ArrayList();
  public MenuLayout menuLayout;
  public int count3;
  public int count4;
  public int count5;
  public ModuleSearchData4 moduleSearchData4 = new ModuleSearchData4(0.0F, 0.0F, 800.0F, 486.0F);
  public static final List list2 = getList();
  public static final float[] amountArray =
      new float[] {-1.5F, 0.4F, 1.9F, 2.8F, 2.8F, 2.3F, 1.1F, -0.85F};
  public static final float[] amountArray2 =
      new float[] {-24.0F, -23.6F, -22.5F, -20.0F, -4.0F, -2.3F, -0.8F, 0.0F};

  public static void render(
      FontManager fontManager,
      ModuleSearchSupport6 moduleSearchSupport6,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      Runnable runnable) {
    if (moduleSearchSupport7 == null) {
      runnable.run();
    } else {
      moduleSearchSupport7.render2(
          fontManager,
          moduleSearchSupport6,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue6,
          floatValue7,
          floatValue8,
          floatValue9,
          floatValue10,
          runnable);
    }
  }

  public static ModuleSearchData4 getModuleSearchData4(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    if (moduleSearchSupport7 == null) {
      return null;
    }

    ModuleSearchData4 moduleSearchData42 = moduleSearchSupport7.moduleSearchData4;
    moduleSearchSupport7.moduleSearchData4 =
        new ModuleSearchData4(
            Math.max(moduleSearchData42.amount(), floatValue),
            Math.max(moduleSearchData42.amount2(), floatValue2),
            Math.max(
                0.0F,
                Math.min(
                        moduleSearchData42.amount() + moduleSearchData42.amount3(),
                        floatValue + floatValue3)
                    - Math.max(moduleSearchData42.amount(), floatValue)),
            Math.max(
                0.0F,
                Math.min(
                        moduleSearchData42.amount2() + moduleSearchData42.k_(),
                        floatValue2 + floatValue4)
                    - Math.max(moduleSearchData42.amount2(), floatValue2)));
    return moduleSearchData42;
  }

  public static void apply(ModuleSearchData4 moduleSearchData42) {
    if (moduleSearchSupport7 != null && moduleSearchData42 != null) {
      moduleSearchSupport7.moduleSearchData4 = moduleSearchData42;
    }
  }

  public void paint(FontManager fontManager, MenuLayout menuLayout2, Runnable runnable) {
    this.menuLayout = menuLayout2;
    this.list.clear();
    this.count3 = 816;
    this.count4 = 4;
    this.count5 = 0;
    this.moduleSearchData4 = new ModuleSearchData4(0.0F, 0.0F, 800.0F, 486.0F);
    this.moduleSearchSupport8.apply(
        fontManager,
        () -> {
          moduleSearchSupport7 = this;

          try {
            fontManager.update2();
            fontManager.apply(4.0F, 4.0F);
            fontManager.render(-1.0F, -1.0F, 802.0F, 488.0F);
            runnable.run();
            fontManager.update3();
          } finally {
            moduleSearchSupport7 = null;
          }
        });
  }

  public void render2(
      FontManager fontManager,
      ModuleSearchSupport6 moduleSearchSupport6,
      float amount3,
      float amount22,
      float amount32,
      float l_,
      float amount6,
      float amount7,
      float amount8,
      float amount9,
      float amount10,
      float amount11,
      Runnable runnable) {
    int intValue = (int) Math.ceil(amount32) + 8;
    int intValue2 = (int) Math.ceil(l_) + 8;
    if (this.count3 + intValue > this.moduleSearchSupport8.count) {
      this.count3 = 816;
      this.count4 = this.count4 + this.count5;
      this.count5 = 0;
    }

    if (this.count4 + intValue2 > this.moduleSearchSupport8.count2) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1611));
    }

    int amount13 = this.count3 + 4;
    int amount14 = this.count4 + 4;
    this.count3 += intValue;
    this.count5 = Math.max(this.count5, intValue2);
    float[] xform = new float[6];
    NanoVG.nvgCurrentTransform(fontManager.getTimestamp(), xform);
    float amount4 = xform[4] - 4.0F + amount6 * 0.5F;
    float amount5 = xform[5] - 4.0F + amount6 * 0.57F;
    ModuleSearchData2 moduleSearchData2 =
        new ModuleSearchData2(
            amount3,
            amount22,
            amount32,
            l_,
            amount4,
            amount5,
            amount6,
            amount7,
            amount8,
            amount9,
            amount10,
            amount11,
            fontManager.getAmount2(),
            amount13,
            amount14,
            this.moduleSearchData4);
    this.list.add(moduleSearchData2);
    moduleSearchSupport6.moduleSearchData2 = moduleSearchData2;
    moduleSearchSupport6.menuLayout = this.menuLayout;
    fontManager.update2();
    NanoVG.nvgResetTransform(fontManager.getTimestamp());
    NanoVG.nvgResetScissor(fontManager.getTimestamp());
    fontManager.render(amount13 - 1, amount14 - 1, amount32 + 2.0F, l_ + 2.0F);
    fontManager.apply(amount13 - amount3, amount14 - amount22);
    runnable.run();
    fontManager.update3();
  }

  public void apply(FontManager fontManager, float floatValue) {
    if (this.menuLayout != null && !(floatValue <= 0.001F)) {
      fontManager.update2();
      fontManager.apply3(floatValue);
      ModuleSearchData2 moduleSearchData2 =
          new ModuleSearchData2(
              0.0F,
              0.0F,
              800.0F,
              486.0F,
              0.0F,
              0.0F,
              0.0F,
              0.0F,
              0.0F,
              16.0F,
              -amountArray2[0],
              0.25F,
              1.0F,
              4.0F,
              4.0F,
              new ModuleSearchData4(-64.0F, -64.0F, 928.0F, 614.0F));
      this.apply(fontManager, moduleSearchData2);
      this.apply2(fontManager, moduleSearchData2);
      this.apply(fontManager);
      this.list.sort(Comparator.comparingDouble(ModuleSearchData2::amount6));

      for (ModuleSearchData2 moduleSearchData22 :
          (java.lang.Iterable<ModuleSearchData2>) (java.lang.Iterable<?>) (this.list)) {
        fontManager.update2();
        fontManager.apply3(moduleSearchData22.amount12());
        this.apply3(fontManager, moduleSearchData22);
        fontManager.update3();
        this.apply2(fontManager, moduleSearchData22);
      }

      fontManager.update3();
    }
  }

  public static List getList() {
    ArrayList arrayList = new ArrayList();

    for (int index = 0; index < 4; index++) {
      float floatValue = index != 0 && index != 3 ? 16.0F : 784.0F;
      float floatValue2 = index < 2 ? 470.0F : 16.0F;

      for (int index2 = 0; index2 <= 16; index2++) {
        float floatValue3 = (index * 0.5F + index2 / 32.0F) * (float) Math.PI;
        float amount3 = (float) Math.cos(floatValue3);
        float amount4 = (float) Math.sin(floatValue3);
        arrayList.add(
            new ModuleSearchData(
                floatValue + amount3 * 16.0F, floatValue2 + amount4 * 16.0F, amount3, amount4));
      }

      ModuleSearchData moduleSearchData = (ModuleSearchData) arrayList.getLast();
      float floatValue4 = (index % 2 == 0 ? 800.0F : 486.0F) - 32.0F;
      int intValue = Math.max(1, (int) Math.ceil(floatValue4 / 32.0F));

      for (int index3 = 1; index3 < intValue; index3++) {
        float floatValue5 = floatValue4 * index3 / intValue;
        arrayList.add(
            new ModuleSearchData(
                moduleSearchData.amount() - moduleSearchData.amount4() * floatValue5,
                moduleSearchData.amount2() + moduleSearchData.amount3() * floatValue5,
                moduleSearchData.amount3(),
                moduleSearchData.amount4()));
      }
    }

    return List.copyOf(arrayList);
  }

  public void apply(FontManager fontManager, ModuleSearchData2 moduleSearchData2) {
    if (GuiPreferences.getGuiPreferences().test2()) {
      for (int index = 8; index >= 1; index--) {
        this.render(
            fontManager, moduleSearchData2, amountArray2[0] - 5.0F, 3.0F + index * 2.7F, 150994944);
      }
    }

    ArrayList arrayList = new ArrayList(list2.size() * amountArray.length);

    for (int index2 = 0; index2 < amountArray.length; index2++) {
      for (ModuleSearchData moduleSearchData :
          (java.lang.Iterable<ModuleSearchData>) (java.lang.Iterable<?>) (list2)) {
        ModuleSearchData3 moduleSearchData3 =
            moduleSearchData.getModuleSearchData3(amountArray[index2], amountArray2[index2]);
        arrayList.add(
            this.menuLayout.getClientData3(
                moduleSearchData3.amount(),
                moduleSearchData3.amount2(),
                moduleSearchData3.amount3()));
      }
    }

    List list3 = getList(arrayList);
    int value = PanelColors.computeInt(0.25F);
    ClientData3 clientData3 = (ClientData3) list3.getFirst();
    fontManager.update2();
    NanoVG.nvgShapeAntiAlias(fontManager.getTimestamp(), false);

    for (int index3 = 1; index3 < list3.size() - 1; index3++) {
      ClientData3 clientData32 = (ClientData3) list3.get(index3);
      ClientData3 clientData33 = (ClientData3) list3.get(index3 + 1);
      fontManager.render3(
          clientData3.amount(),
          clientData3.amount2(),
          clientData32.amount(),
          clientData32.amount2(),
          clientData33.amount(),
          clientData33.amount2(),
          value);
    }

    fontManager.update3();
    float[] floatValues = new float[list3.size() + 1];
    float[] floatValues2 = new float[floatValues.length];

    for (int index4 = 0; index4 < list3.size(); index4++) {
      floatValues[index4] = ((ClientData3) list3.get(index4)).amount();
      floatValues2[index4] = ((ClientData3) list3.get(index4)).amount2();
    }

    floatValues[floatValues.length - 1] = floatValues[0];
    floatValues2[floatValues2.length - 1] = floatValues2[0];
    fontManager.update2();
    NanoVG.nvgShapeAntiAlias(fontManager.getTimestamp(), false);

    for (int index5 = 0; index5 < amountArray.length - 1; index5++) {
      for (int index6 = 0; index6 < list2.size(); index6++) {
        ModuleSearchData moduleSearchData5 = (ModuleSearchData) list2.get(index6);
        ModuleSearchData moduleSearchData6 =
            (ModuleSearchData) list2.get((index6 + 1) % list2.size());
        ModuleSearchData3 moduleSearchData32 =
            moduleSearchData5.getModuleSearchData3(amountArray[index5], amountArray2[index5]);
        ModuleSearchData3 moduleSearchData33 =
            moduleSearchData6.getModuleSearchData3(amountArray[index5], amountArray2[index5]);
        ModuleSearchData3 moduleSearchData34 =
            moduleSearchData6.getModuleSearchData3(
                amountArray[index5 + 1], amountArray2[index5 + 1]);
        ModuleSearchData3 moduleSearchData35 =
            moduleSearchData5.getModuleSearchData3(
                amountArray[index5 + 1], amountArray2[index5 + 1]);
        if (!(computeFloat(
                this.menuLayout.getClientData3(
                    moduleSearchData32.amount(),
                    moduleSearchData32.amount2(),
                    moduleSearchData32.amount3()),
                this.menuLayout.getClientData3(
                    moduleSearchData33.amount(),
                    moduleSearchData33.amount2(),
                    moduleSearchData33.amount3()),
                this.menuLayout.getClientData3(
                    moduleSearchData34.amount(),
                    moduleSearchData34.amount2(),
                    moduleSearchData34.amount3()))
            <= 0.0F)) {
          float floatValue = moduleSearchData33.amount() - moduleSearchData32.amount();
          float floatValue2 = moduleSearchData33.amount2() - moduleSearchData32.amount2();
          float floatValue3 = moduleSearchData35.amount() - moduleSearchData32.amount();
          float floatValue4 = moduleSearchData35.amount2() - moduleSearchData32.amount2();
          float floatValue5 = moduleSearchData35.amount3() - moduleSearchData32.amount3();
          float floatValue6 = floatValue2 * floatValue5;
          float floatValue7 = -floatValue * floatValue5;
          float floatValue8 = floatValue * floatValue4 - floatValue2 * floatValue3;
          float floatValue9 =
              (float)
                  Math.sqrt(
                      floatValue6 * floatValue6
                          + floatValue7 * floatValue7
                          + floatValue8 * floatValue8);
          if (!(floatValue9 < 1.0E-4F)) {
            floatValue7 /= floatValue9;
            floatValue8 /= floatValue9;
            int value2 =
                PanelColors.computeInt(
                    0.45F - 0.2F * floatValue7 + 0.1F * Math.max(0.0F, floatValue8));
            this.apply(
                fontManager,
                moduleSearchData2.moduleSearchData4(),
                List.of(
                    moduleSearchData32, moduleSearchData33, moduleSearchData34, moduleSearchData35),
                false,
                value2);
          }
        }
      }
    }

    fontManager.update3();
    fontManager.render(floatValues, floatValues2, floatValues.length, 0.7F, value);
  }

  public void apply(FontManager fontManager) {
    float[] floatValues = new float[list2.size() + 1];
    float[] floatValues2 = new float[floatValues.length];

    for (int index = 0; index < list2.size(); index++) {
      ModuleSearchData moduleSearchData = (ModuleSearchData) list2.get(index);
      ClientData3 clientData3 =
          this.menuLayout.getClientData3(
              moduleSearchData.amount(), moduleSearchData.amount2(), 0.0F);
      floatValues[index] = clientData3.amount();
      floatValues2[index] = clientData3.amount2();
    }

    floatValues[floatValues.length - 1] = floatValues[0];
    floatValues2[floatValues2.length - 1] = floatValues2[0];
    fontManager.render(
        floatValues, floatValues2, floatValues.length, 0.8F, PanelColors.computeInt2(0.0F));
  }

  public static float computeFloat(
      ClientData3 clientData3, ClientData3 clientData32, ClientData3 clientData33) {
    return (clientData32.amount() - clientData3.amount())
            * (clientData33.amount2() - clientData3.amount2())
        - (clientData32.amount2() - clientData3.amount2())
            * (clientData33.amount() - clientData3.amount());
  }

  public static List getList(List list3) {
    list3.sort(
        Comparator.comparingDouble(ClientData3::amount).thenComparingDouble(ClientData3::amount2));
    ArrayList arrayList = new ArrayList();

    for (ClientData3 clientData3 :
        (java.lang.Iterable<ClientData3>) (java.lang.Iterable<?>) (list3)) {
      while (arrayList.size() >= 2
          && computeFloat(
                  (ClientData3) arrayList.get(arrayList.size() - 2),
                  (ClientData3) arrayList.getLast(),
                  clientData3)
              <= 0.0F) {
        arrayList.removeLast();
      }

      arrayList.add(clientData3);
    }

    int size = arrayList.size();

    for (int index = list3.size() - 2; index >= 0; index--) {
      ClientData3 clientData32 = (ClientData3) list3.get(index);

      while (arrayList.size() > size
          && computeFloat(
                  (ClientData3) arrayList.get(arrayList.size() - 2),
                  (ClientData3) arrayList.getLast(),
                  clientData32)
              <= 0.0F) {
        arrayList.removeLast();
      }

      arrayList.add(clientData32);
    }

    arrayList.removeLast();
    return arrayList;
  }

  public void apply2(FontManager fontManager, ModuleSearchData2 moduleSearchData2) {
    int intValue = Math.max(1, (int) Math.ceil(moduleSearchData2.amount3() / 42.0F));
    int intValue2 = Math.max(1, (int) Math.ceil(moduleSearchData2.l_() / 30.0F));

    for (int index = 0; index < intValue2; index++) {
      for (int index2 = 0; index2 < intValue; index2++) {
        float floatValue = -1.0F + index2 * (moduleSearchData2.amount3() + 2.0F) / intValue;
        float floatValue2 = -1.0F + (index2 + 1) * (moduleSearchData2.amount3() + 2.0F) / intValue;
        float floatValue3 = -1.0F + index * (moduleSearchData2.l_() + 2.0F) / intValue2;
        float floatValue4 = -1.0F + (index + 1) * (moduleSearchData2.l_() + 2.0F) / intValue2;
        ModuleSearchData3 moduleSearchData3 =
            moduleSearchData2.getModuleSearchData3(
                floatValue,
                floatValue3,
                0.0F,
                moduleSearchData2.amount13() + floatValue,
                moduleSearchData2.amount14() + floatValue3);
        ModuleSearchData3 moduleSearchData32 =
            moduleSearchData2.getModuleSearchData3(
                floatValue2,
                floatValue3,
                0.0F,
                moduleSearchData2.amount13() + floatValue2,
                moduleSearchData2.amount14() + floatValue3);
        ModuleSearchData3 moduleSearchData33 =
            moduleSearchData2.getModuleSearchData3(
                floatValue2,
                floatValue4,
                0.0F,
                moduleSearchData2.amount13() + floatValue2,
                moduleSearchData2.amount14() + floatValue4);
        ModuleSearchData3 moduleSearchData34 =
            moduleSearchData2.getModuleSearchData3(
                floatValue,
                floatValue4,
                0.0F,
                moduleSearchData2.amount13() + floatValue,
                moduleSearchData2.amount14() + floatValue4);
        this.apply(
            fontManager,
            moduleSearchData2.moduleSearchData4(),
            List.of(moduleSearchData3, moduleSearchData32, moduleSearchData33),
            true,
            0);
        this.apply(
            fontManager,
            moduleSearchData2.moduleSearchData4(),
            List.of(moduleSearchData3, moduleSearchData33, moduleSearchData34),
            true,
            0);
      }
    }
  }

  public void apply3(FontManager fontManager, ModuleSearchData2 moduleSearchData2) {
    if (GuiPreferences.getGuiPreferences().test2()) {
      for (int index = 5; index >= 1; index--) {
        float floatValue = index * (1.0F + moduleSearchData2.amount11() * 0.6F);
        this.render(
            fontManager,
            moduleSearchData2,
            -Math.max(moduleSearchData2.amount10() + 1.0F, moduleSearchData2.amount6()),
            floatValue,
            268435456);
      }
    }

    float[] floatValues = new float[36];
    float[] floatValues2 = new float[floatValues.length];
    int index2 = 0;

    for (int index3 = 0; index3 < 4; index3++) {
      float floatValue2 =
          index3 != 0 && index3 != 3
              ? moduleSearchData2.amount9()
              : moduleSearchData2.amount3() - moduleSearchData2.amount9();
      float floatValue3 =
          index3 < 2
              ? moduleSearchData2.l_() - moduleSearchData2.amount9()
              : moduleSearchData2.amount9();

      for (int index4 = 0; index4 <= 8; index4++) {
        float floatValue4 = (index3 * 0.5F + index4 * 0.5F / 8.0F) * (float) Math.PI;
        floatValues[index2] =
            floatValue2 + moduleSearchData2.amount9() * (float) Math.cos(floatValue4);
        floatValues2[index2++] =
            floatValue3 + moduleSearchData2.amount9() * (float) Math.sin(floatValue4);
      }
    }

    fontManager.update2();
    NanoVG.nvgShapeAntiAlias(fontManager.getTimestamp(), false);

    for (int index5 = 0; index5 < index2; index5++) {
      int index6 = (index5 + 1) % index2;
      int int3 =
          PanelColors.computeInt3(
              (floatValues2[index5] + floatValues2[index6]) / (2.0F * moduleSearchData2.l_()));
      ModuleSearchData3 moduleSearchData3 =
          moduleSearchData2.getModuleSearchData3(
              floatValues[index5], floatValues2[index5], -moduleSearchData2.amount10(), 0.0F, 0.0F);
      ModuleSearchData3 moduleSearchData32 =
          moduleSearchData2.getModuleSearchData3(
              floatValues[index6], floatValues2[index6], -moduleSearchData2.amount10(), 0.0F, 0.0F);
      ModuleSearchData3 moduleSearchData33 =
          moduleSearchData2.getModuleSearchData3(
              floatValues[index6], floatValues2[index6], 0.0F, 0.0F, 0.0F);
      ModuleSearchData3 moduleSearchData34 =
          moduleSearchData2.getModuleSearchData3(
              floatValues[index5], floatValues2[index5], 0.0F, 0.0F, 0.0F);
      if (!(computeFloat(
              this.menuLayout.getClientData3(
                  moduleSearchData3.amount(),
                  moduleSearchData3.amount2(),
                  moduleSearchData3.amount3()),
              this.menuLayout.getClientData3(
                  moduleSearchData32.amount(),
                  moduleSearchData32.amount2(),
                  moduleSearchData32.amount3()),
              this.menuLayout.getClientData3(
                  moduleSearchData33.amount(),
                  moduleSearchData33.amount2(),
                  moduleSearchData33.amount3()))
          <= 0.0F)) {
        this.apply(
            fontManager,
            moduleSearchData2.moduleSearchData4(),
            List.of(moduleSearchData3, moduleSearchData32, moduleSearchData33, moduleSearchData34),
            false,
            int3);
      }
    }

    fontManager.update3();
  }

  public void render(
      FontManager fontManager,
      ModuleSearchData2 moduleSearchData2,
      float floatValue,
      float floatValue2,
      int intValue) {
    ArrayList arrayList = new ArrayList();
    float floatValue3 = moduleSearchData2.amount9() + floatValue2;

    for (int index = 0; index < 4; index++) {
      float floatValue4 =
          index != 0 && index != 3
              ? moduleSearchData2.amount9()
              : moduleSearchData2.amount3() - moduleSearchData2.amount9();
      float floatValue5 =
          index < 2
              ? moduleSearchData2.l_() - moduleSearchData2.amount9()
              : moduleSearchData2.amount9();

      for (int index2 = 0; index2 <= 6; index2++) {
        float floatValue6 = (index * 0.5F + index2 / 12.0F) * (float) Math.PI;
        arrayList.add(
            moduleSearchData2.getModuleSearchData3(
                floatValue4 + floatValue3 * (float) Math.cos(floatValue6),
                floatValue5 + floatValue3 * (float) Math.sin(floatValue6) + 3.0F,
                floatValue,
                0.0F,
                0.0F));
      }
    }

    fontManager.update2();
    NanoVG.nvgShapeAntiAlias(fontManager.getTimestamp(), false);
    this.apply(fontManager, moduleSearchData2.moduleSearchData4(), arrayList, false, intValue);
    fontManager.update3();
  }

  public void apply(
      FontManager fontManager,
      ModuleSearchData4 moduleSearchData42,
      List list3,
      boolean flag,
      int intValue) {
    List list4 = list3;

    for (int index = 0; index < 4 && !list4.isEmpty(); index++) {
      ArrayList arrayList = null;
      ModuleSearchData3 moduleSearchData3 = (ModuleSearchData3) list4.get(list4.size() - 1);
      float floatValue = computeFloat(moduleSearchData3, moduleSearchData42, index);

      for (int index2 = 0; index2 < list4.size(); index2++) {
        ModuleSearchData3 moduleSearchData32 = (ModuleSearchData3) list4.get(index2);
        float floatValue2 = computeFloat(moduleSearchData32, moduleSearchData42, index);
        if (floatValue2 < 0.0F || floatValue < 0.0F) {
          if (arrayList == null) {
            arrayList = new ArrayList();
            arrayList.addAll(list4.subList(0, index2));
          }

          if (floatValue2 >= 0.0F != floatValue >= 0.0F) {
            arrayList.add(
                moduleSearchData3.getModuleSearchData3(
                    moduleSearchData32, floatValue / (floatValue - floatValue2)));
          }
        }

        if (arrayList != null && floatValue2 >= 0.0F) {
          arrayList.add(moduleSearchData32);
        }

        moduleSearchData3 = moduleSearchData32;
        floatValue = floatValue2;
      }

      if (arrayList != null) {
        list4 = arrayList;
      }
    }

    if (list4.size() >= 3) {
      ModuleSearchData3 moduleSearchData33 = (ModuleSearchData3) list4.get(0);
      ClientData3 clientData3 =
          this.menuLayout.getClientData3(
              moduleSearchData33.amount(),
              moduleSearchData33.amount2(),
              moduleSearchData33.amount3());

      for (int index3 = 1; index3 < list4.size() - 1; index3++) {
        ModuleSearchData3 moduleSearchData34 = (ModuleSearchData3) list4.get(index3);
        ModuleSearchData3 moduleSearchData35 = (ModuleSearchData3) list4.get(index3 + 1);
        ClientData3 clientData32 =
            this.menuLayout.getClientData3(
                moduleSearchData34.amount(),
                moduleSearchData34.amount2(),
                moduleSearchData34.amount3());
        ClientData3 clientData33 =
            this.menuLayout.getClientData3(
                moduleSearchData35.amount(),
                moduleSearchData35.amount2(),
                moduleSearchData35.amount3());
        if (flag) {
          fontManager.render(
              this.moduleSearchSupport8.getCount6(),
              this.moduleSearchSupport8.count,
              this.moduleSearchSupport8.count2,
              moduleSearchData33.amount4(),
              moduleSearchData33.amount5(),
              moduleSearchData34.amount4(),
              moduleSearchData34.amount5(),
              moduleSearchData35.amount4(),
              moduleSearchData35.amount5(),
              clientData3.amount(),
              clientData3.amount2(),
              clientData32.amount(),
              clientData32.amount2(),
              clientData33.amount(),
              clientData33.amount2());
        } else {
          fontManager.render3(
              clientData3.amount(),
              clientData3.amount2(),
              clientData32.amount(),
              clientData32.amount2(),
              clientData33.amount(),
              clientData33.amount2(),
              intValue);
        }
      }
    }
  }

  public static float computeFloat(
      ModuleSearchData3 moduleSearchData3, ModuleSearchData4 moduleSearchData42, int intValue) {
    return switch (intValue) {
      case 0 -> moduleSearchData3.amount() - moduleSearchData42.amount();
      case 1 ->
          moduleSearchData42.amount() + moduleSearchData42.amount3() - moduleSearchData3.amount();
      case 2 -> moduleSearchData3.amount2() - moduleSearchData42.amount2();
      default ->
          moduleSearchData42.amount2() + moduleSearchData42.k_() - moduleSearchData3.amount2();
    };
  }

  @Override
  public void close() {
    this.list.clear();
    this.moduleSearchSupport8.close();
  }
}

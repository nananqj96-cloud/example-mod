package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.hud.HudElement;
import bunnythekenzo.client.hud.HudWidget;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.FontManager;

public final class WorldSignalSupport {
  public static final float amount = 0.01F;
  public static final float amount2 = 5.0F;
  public static final float amount3 = 0.05F;
  public static HudWidget hudWidget;
  public static WorldSignal worldSignal;
  public static float amount4;
  public static float amount5;
  public static final List list = new ArrayList();
  public static HudWidget hudWidget2;

  public static boolean test() {
    return hudWidget != null;
  }

  public static boolean test2() {
    return hudWidget != null || worldSignal != null;
  }

  public static HudWidget getHudWidget2() {
    return hudWidget2;
  }

  public static boolean test3() {
    if (!GuiPreferences.getGuiPreferences().showHudControl.getValue()) {
      return false;
    }

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager == null
        || moduleManager.hUDModule == null
        || moduleManager.hUDModule.isEnabled();
  }

  public static List getList(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      boolean flag) {
    float floatValue6 = GuiPreferences.getGuiPreferences().computeFloat4() * floatValue5;
    ArrayList arrayList = new ArrayList();

    for (HudWidget hudWidget3 :
        (java.lang.Iterable<HudWidget>) (java.lang.Iterable<?>) (JsonSupport2.getList3())) {
      if (hudWidget3.test7()) {
        float floatValue7 = hudWidget3.getAmount7() * floatValue6;
        float value = hudWidget3.computeFloat(fontManager);
        float float2 = hudWidget3.computeFloat2(fontManager);
        boolean enabled = !hudWidget3.test3() || value < 4.0F || float2 < 4.0F;
        if (!enabled || flag) {
          if (enabled) {
            value = Math.max(value, computeFloat(fontManager, hudWidget3));
            float2 = Math.max(float2, 18.0F);
          }

          hudWidget3.apply3(value, float2);
          float amount32 = value * floatValue7;
          float amount42 = float2 * floatValue7;
          arrayList.add(
              new WorldSignal(
                  hudWidget3,
                  floatValue + hudWidget3.getAmount5() * Math.max(0.0F, floatValue3 - amount32),
                  floatValue2 + hudWidget3.getAmount6() * Math.max(0.0F, floatValue4 - amount42),
                  amount32,
                  amount42,
                  enabled));
        }
      }
    }

    return arrayList;
  }

  public static float computeFloat(FontManager fontManager, HudWidget hudWidget3) {
    return fontManager.computeFloat(hudWidget3.getString22(), 11.5F) + 20.0F + 4.0F;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      boolean flag,
      float floatValue6,
      float floatValue7) {
    if (test3()) {
      if (!(11.5F * GuiPreferences.getGuiPreferences().computeFloat4() * floatValue5 < 3.0F)) {
        List list2 =
            getList(
                fontManager, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, flag);
        hudWidget2 = null;

        for (WorldSignal worldSignal2 :
            (java.lang.Iterable<WorldSignal>) (java.lang.Iterable<?>) (list2)) {
          HudWidget hudWidget22 = worldSignal2.hudWidget();
          float floatValue8 =
              hudWidget22.getAmount7()
                  * GuiPreferences.getGuiPreferences().computeFloat4()
                  * floatValue5;
          boolean flag2 =
              flag && hudWidget22.isEnabled2() && worldSignal2.test(floatValue6, floatValue7);
          boolean flag3 = hudWidget22 == hudWidget;
          if (flag2) {
            hudWidget2 = hudWidget22;
          }

          float float2 = hudWidget22.computeFloat2();
          fontManager.update2();
          fontManager.apply3(float2);
          fontManager.apply(Math.round(worldSignal2.amount()), Math.round(worldSignal2.amount2()));
          fontManager.apply(floatValue8);
          if (float2 < 0.999F) {
            fontManager.render2(
                worldSignal2.amount3() / floatValue8 / 2.0F,
                worldSignal2.amount4() / floatValue8 / 2.0F,
                0.94F + 0.06F * float2);
          }

          if (worldSignal2.enabled()) {
            render(
                fontManager,
                hudWidget22,
                worldSignal2.amount3() / floatValue8,
                worldSignal2.amount4() / floatValue8);
          } else {
            hudWidget22.render(
                fontManager,
                0.0F,
                0.0F,
                worldSignal2.amount3() / floatValue8,
                worldSignal2.amount4() / floatValue8);
          }

          fontManager.update3();
          if (flag && hudWidget22.isEnabled2()) {
            apply(fontManager, worldSignal2, flag2, flag3);
          }
        }

        if (flag && hudWidget != null) {
          render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
        }
      }
    }
  }

  public static void render(
      FontManager fontManager, HudWidget hudWidget3, float floatValue, float floatValue2) {
    fontManager.render(
        0.0F, 0.0F, floatValue, floatValue2, 8.0F, ArgbColors.withOpacity(-16119792, 0.55F));
    fontManager.render(
        0.0F, 0.0F, floatValue, floatValue2, 8.0F, 1.0F, HudStyle.computeInt2(0.28F));
    fontManager.computeFloat(
        hudWidget3.getString22(), 10.0F, floatValue2 / 2.0F, 11.5F, HudStyle.computeInt(0.4F));
  }

  public static void apply(
      FontManager fontManager, WorldSignal worldSignal2, boolean flag, boolean flag2) {
    float floatValue = worldSignal2.amount();
    float floatValue2 = worldSignal2.amount2();
    float floatValue3 = worldSignal2.amount3();
    float floatValue4 = worldSignal2.amount4();
    if (!flag2 && !flag) {
      fontManager.render(
          floatValue - 2.0F,
          floatValue2 - 2.0F,
          floatValue3 + 4.0F,
          floatValue4 + 4.0F,
          10.0F,
          1.0F,
          ArgbColors.withOpacity(-1, 0.12F));
    } else {
      fontManager.render(
          floatValue - 2.0F,
          floatValue2 - 2.0F,
          floatValue3 + 4.0F,
          floatValue4 + 4.0F,
          10.0F,
          HudStyle.computeInt2(flag2 ? 0.14F : 0.07F));
      fontManager.render(
          floatValue - 2.0F,
          floatValue2 - 2.0F,
          floatValue3 + 4.0F,
          floatValue4 + 4.0F,
          10.0F,
          1.2F,
          HudStyle.computeInt2(flag2 ? 0.95F : 0.7F));
      render(fontManager, worldSignal2, floatValue + floatValue3, floatValue2 - 3.0F);
    }
  }

  public static void render(
      FontManager fontManager, WorldSignal worldSignal2, float floatValue, float floatValue2) {
    String text = Math.round(worldSignal2.hudWidget().getAmount7() * 100.0F) + "%";
    float value = fontManager.computeFloat(text, 7.5F);
    float floatValue3 = value + 12.0F;
    float floatValue4 = floatValue - floatValue3;
    float floatValue5 = floatValue2 - 14.0F;
    fontManager.render(
        floatValue4,
        floatValue5,
        floatValue3,
        14.0F,
        7.0F,
        5.0F,
        2.0F,
        ArgbColors.withOpacity(-16777216, 0.45F));
    fontManager.render(
        floatValue4,
        floatValue5,
        floatValue3,
        14.0F,
        7.0F,
        ArgbColors.withOpacity(-15593956, 0.95F));
    fontManager.render(
        floatValue4, floatValue5, floatValue3, 14.0F, 7.0F, 1.0F, HudStyle.computeInt2(0.55F));
    fontManager.computeFloat2(
        text, floatValue4 + floatValue3 / 2.0F, floatValue5 + 7.0F, 7.5F, HudStyle.computeInt2());
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    for (WorldSignalData worldSignalData :
        (java.lang.Iterable<WorldSignalData>) (java.lang.Iterable<?>) (list)) {
      if (worldSignalData.enabled()) {
        fontManager.render(
            worldSignalData.amount() - 0.5F,
            floatValue2,
            1.0F,
            floatValue4,
            0.0F,
            HudStyle.computeInt2(0.55F));
      } else {
        fontManager.render(
            floatValue,
            worldSignalData.amount() - 0.5F,
            floatValue3,
            1.0F,
            0.0F,
            HudStyle.computeInt2(0.55F));
      }
    }
  }

  public static HudWidget getHudWidget(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    List list2 =
        getList(fontManager, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, true);

    for (int index = list2.size() - 1; index >= 0; index--) {
      WorldSignal worldSignal2 = (WorldSignal) list2.get(index);
      if (worldSignal2.hudWidget().isEnabled2() && worldSignal2.test(floatValue6, floatValue7)) {
        return worldSignal2.hudWidget();
      }
    }

    return null;
  }

  public static boolean test(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    if (!test3()) {
      return false;
    }

    List list2 =
        getList(fontManager, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, true);

    for (int index = list2.size() - 1; index >= 0; index--) {
      WorldSignal worldSignal2 = (WorldSignal) list2.get(index);
      if (worldSignal2.hudWidget().isEnabled2() && worldSignal2.test(floatValue6, floatValue7)) {
        if (!worldSignal2.enabled()
            && worldSignal2.hudWidget() instanceof HudElement hudElement
            && hudElement.test(
                worldSignal2.computeFloat(floatValue6), worldSignal2.computeFloat2(floatValue7))) {
          worldSignal = worldSignal2;
          return true;
        }

        hudWidget = worldSignal2.hudWidget();
        amount4 = floatValue6 - worldSignal2.amount();
        amount5 = floatValue7 - worldSignal2.amount2();
        list.clear();
        ClientAudio.update5();
        return true;
      }
    }

    return false;
  }

  public static void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    if (worldSignal == null) {
      if (hudWidget != null) {
        float floatValue8 =
            hudWidget.getAmount7()
                * GuiPreferences.getGuiPreferences().computeFloat4()
                * floatValue5;
        float floatValue9 = hudWidget.getAmount8() * floatValue8;
        float floatValue10 = hudWidget.getAmount9() * floatValue8;
        float floatValue11 = floatValue6 - amount4;
        float floatValue12 = floatValue7 - amount5;
        list.clear();
        if (GuiPreferences.getGuiPreferences().snapWhileDraggingControl.getValue() && !test4()) {
          List list2 =
              getList(
                  fontManager,
                  floatValue,
                  floatValue2,
                  floatValue3,
                  floatValue4,
                  floatValue5,
                  true);
          floatValue11 =
              computeFloat(floatValue11, floatValue9, floatValue, floatValue3, list2, true);
          floatValue12 =
              computeFloat(floatValue12, floatValue10, floatValue2, floatValue4, list2, false);
        }

        hudWidget.apply2(
            computeFloat(floatValue11 - floatValue, floatValue3 - floatValue9),
            computeFloat(floatValue12 - floatValue2, floatValue4 - floatValue10));
      }
    } else {
      HudElement hudElement = (HudElement) worldSignal.hudWidget();
      if (test3() && hudElement.test8()) {
        hudElement.apply(
            worldSignal.computeFloat(floatValue6), worldSignal.computeFloat2(floatValue7));
      } else {
        update();
      }
    }
  }

  public static void update() {
    if (worldSignal != null) {
      ((HudElement) worldSignal.hudWidget()).update();
      worldSignal = null;
    }

    if (hudWidget != null) {
      hudWidget = null;
      list.clear();
      JsonSupport2.update4();
    }
  }

  public static boolean test(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      double doubleValue) {
    if (test3() && doubleValue != 0.0) {
      HudWidget hudWidget3 =
          getHudWidget(
              fontManager,
              floatValue,
              floatValue2,
              floatValue3,
              floatValue4,
              floatValue5,
              floatValue6,
              floatValue7);
      if (hudWidget3 == null) {
        return false;
      }

      if (hudWidget3 instanceof HudElement hudElement && hudElement.test3()) {
        for (WorldSignal worldSignal2 :
            (java.lang.Iterable<WorldSignal>)
                (java.lang.Iterable<?>)
                    (getList(
                        fontManager,
                        floatValue,
                        floatValue2,
                        floatValue3,
                        floatValue4,
                        floatValue5,
                        true))) {
          if (worldSignal2.hudWidget() == hudWidget3
              && hudElement.test(
                  worldSignal2.computeFloat(floatValue6),
                  worldSignal2.computeFloat2(floatValue7),
                  doubleValue)) {
            return true;
          }
        }
      }

      if (hudWidget3.test((float) Math.signum(doubleValue) * 0.05F)) {
        ClientAudio.apply2((hudWidget3.getAmount7() - 0.5F) / 2.0F);
        JsonSupport2.update4();
      } else {
        ClientAudio.update9();
      }

      return true;
    } else {
      return false;
    }
  }

  public static boolean test4() {
    long handle = MinecraftClient.getInstance().getWindow().getHandle();
    return GLFW.glfwGetKey(handle, 342) == 1 || GLFW.glfwGetKey(handle, 346) == 1;
  }

  public static float computeFloat(float floatValue, float floatValue2) {
    return floatValue2 <= 0.5F ? 0.0F : Math.clamp(floatValue / floatValue2, 0.0F, 1.0F);
  }

  public static float computeFloat(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      List list2,
      boolean enabled) {
    float floatValue5 = floatValue;
    float floatValue6 = 5.0F;
    float NaN = Float.NaN;

    for (float[] floatValues :
        (java.lang.Iterable<float[]>)
            (java.lang.Iterable<?>)
                (getList(floatValue2, floatValue3, floatValue4, list2, enabled))) {
      float floatValue7 = Math.abs(floatValue - floatValues[0]);
      if (floatValue7 < floatValue6) {
        floatValue6 = floatValue7;
        floatValue5 = floatValues[0];
        NaN = floatValues[1];
      }
    }

    if (!Float.isNaN(NaN)) {
      list.add(new WorldSignalData(enabled, NaN));
    }

    return floatValue5;
  }

  public static List getList(
      float floatValue, float floatValue2, float floatValue3, List list2, boolean flag) {
    float floatValue4 = floatValue3 * 0.01F;
    ArrayList arrayList = new ArrayList();
    arrayList.add(new float[] {floatValue2 + floatValue4, floatValue2 + floatValue4});
    arrayList.add(new float[] {floatValue2, floatValue2});
    arrayList.add(
        new float[] {
          floatValue2 + floatValue3 - floatValue4 - floatValue,
          floatValue2 + floatValue3 - floatValue4
        });
    arrayList.add(new float[] {floatValue2 + floatValue3 - floatValue, floatValue2 + floatValue3});
    arrayList.add(
        new float[] {
          floatValue2 + floatValue3 / 2.0F - floatValue / 2.0F, floatValue2 + floatValue3 / 2.0F
        });

    for (WorldSignal worldSignal2 :
        (java.lang.Iterable<WorldSignal>) (java.lang.Iterable<?>) (list2)) {
      if (worldSignal2.hudWidget() != hudWidget) {
        float floatValue5 = flag ? worldSignal2.amount() : worldSignal2.amount2();
        float floatValue6 = flag ? worldSignal2.amount3() : worldSignal2.amount4();
        arrayList.add(new float[] {floatValue5, floatValue5});
        arrayList.add(
            new float[] {floatValue5 + floatValue6 - floatValue, floatValue5 + floatValue6});
        arrayList.add(
            new float[] {
              floatValue5 + floatValue6 / 2.0F - floatValue / 2.0F, floatValue5 + floatValue6 / 2.0F
            });
      }
    }

    return arrayList;
  }
}

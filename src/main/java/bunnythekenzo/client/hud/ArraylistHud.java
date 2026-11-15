package bunnythekenzo.client.hud;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bunnythekenzo.client.AnimationCurve;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.CurveTween;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.module.HUDModule;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.render.FontManager;

public class ArraylistHud extends HudElement {
  public static final float amount = 20.0F;
  public static final float amount2 = 13.5F;
  public static final float amount3 = 8.0F;
  public static final float amount4 = 2.5F;
  public final ModuleManager moduleManager;
  public final HUDModule hUDModule;
  public final ThemeManager themeManager;
  public final Map map = new HashMap();

  public ArraylistHud(
      ModuleManager moduleManager2,
      HUDModule hUDModule2,
      ThemeManager themeManager2,
      HudElementContract hudElementContract) {
    super(
        "arraylist",
        "Module List",
        "Every enabled module, longest first",
        1.0F,
        0.008F,
        hudElementContract);
    this.moduleManager = moduleManager2;
    this.hUDModule = hUDModule2;
    this.themeManager = themeManager2;
  }

  public List animatedEntries(FontManager fontManager) {
    ArrayList arrayList = new ArrayList();

    for (Module module :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.moduleManager.getList())) {
      if (module.getModuleCategory() != ModuleCategory.getInstance()) {
        CurveTween curveTween =
            (bunnythekenzo.client.CurveTween)
                (this.map.computeIfAbsent(
                    module,
                    value -> {
                      return new CurveTween(
                          240.0F,
                          ((bunnythekenzo.client.module.Module) (value)).isEnabled() ? 1.0F : 0.0F,
                          AnimationCurve.EASE_OUT_CUBIC);
                    }));
        curveTween.animateTo(module.isEnabled() ? 1.0F : 0.0F);
        if (module.isEnabled() || curveTween.getValue() > 0.01F) {
          arrayList.add(module);
        }
      }
    }

    arrayList.sort(
        Comparator.<Module>comparingDouble(
                module2 -> {
                  return fontManager.computeFloat(module2.getName(), 13.5F);
                })
            .reversed());
    return arrayList;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    float floatValue = 40.0F;

    for (Module module :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.animatedEntries(fontManager))) {
      floatValue =
          Math.max(
              floatValue, fontManager.computeFloat(module.getName(), 13.5F) + 16.0F + 2.5F + 2.0F);
    }

    return floatValue;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    float floatValue = 0.0F;

    for (Module module :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.animatedEntries(fontManager))) {
      floatValue += 20.0F * ((CurveTween) this.map.get(module)).getValue();
    }

    return Math.max(20.0F, floatValue);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    boolean flag = (Boolean) this.hUDModule.listThemeSyncSetting.getValue();
    boolean flag2 = this.test4();
    float floatValue5 = floatValue2;

    for (Module module :
        (java.lang.Iterable<Module>) (java.lang.Iterable<?>) (this.animatedEntries(fontManager))) {
      float value = ((CurveTween) this.map.get(module)).getValue();
      if (!(value <= 0.01F)) {
        int intValue =
            flag ? colorPalette.getCount() : this.hUDModule.listColorSetting.getInteger();
        int intValue2 = flag ? colorPalette.computeInt() : ArgbColors.lighten(intValue, 0.35F);
        float value2 = fontManager.computeFloat(module.getName(), 13.5F);
        float floatValue6 = value2 + 16.0F + 2.5F + 2.0F;
        float floatValue7 = (1.0F - value) * (floatValue6 + 12.0F) * (flag2 ? 1 : -1);
        float floatValue8 =
            Math.round((flag2 ? floatValue + floatValue3 - floatValue6 : floatValue) + floatValue7);
        float floatValue9 = Math.round(floatValue5);
        fontManager.update2();
        fontManager.apply3(value);
        fontManager.render(
            floatValue8,
            floatValue9,
            floatValue6,
            20.0F,
            5.0F,
            ArgbColors.withOpacity(-15330788, 0.86F),
            ArgbColors.withOpacity(-15791084, 0.86F),
            true);
        float floatValue10 = flag2 ? floatValue8 + floatValue6 - 2.5F : floatValue8;
        fontManager.render2(
            floatValue10 - 1.0F,
            floatValue9 + 2.0F,
            4.5F,
            16.0F,
            2.0F,
            3.0F,
            ArgbColors.withOpacity(intValue, 0.35F));
        fontManager.render(floatValue10, floatValue9 + 2.0F, 2.5F, 16.0F, 1.25F, intValue);
        float floatValue11 =
            Math.round(flag2 ? floatValue8 + 8.0F : floatValue8 + 2.5F + 2.0F + 8.0F - 2.0F);
        fontManager.computeFloat2(
            module.getName(), floatValue11, floatValue9 + 10.0F, 13.5F, intValue2, intValue);
        fontManager.update3();
        floatValue5 += 20.0F * value;
      }
    }
  }
}

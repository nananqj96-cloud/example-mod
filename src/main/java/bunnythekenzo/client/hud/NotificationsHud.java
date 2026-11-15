package bunnythekenzo.client.hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.WeatherState;
import bunnythekenzo.client.WeatherStateSupport;
import bunnythekenzo.client.module.HUDModule;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.OverlayRenderer;

public class NotificationsHud extends HudElement {
  public static final float amount = 12.0F;
  public static final float amount2 = 30.0F;
  public static final float amount3 = 42.0F;
  public static final float amount4 = 6.0F;
  public static final float amount5 = 13.5F;
  public static final float amount6 = 11.0F;
  public static final float amount7 = 26.0F;
  public static final float amount8 = 168.0F;
  public static final float amount9 = 30.0F;
  public final ThemeManager themeManager;
  public final HUDModule hUDModule;
  public final List list = new ArrayList();
  public static ClientInitializer[] clientInitializerArray;

  public NotificationsHud(ThemeManager themeManager2, HUDModule hUDModule2) {
    super(
        "notifications",
        "Notifications",
        "Where toasts stack when a module toggles",
        0.99F,
        0.71F,
        HudElement.getHudElementContract(hUDModule2.notificationsSetting));
    this.themeManager = themeManager2;
    this.hUDModule = hUDModule2;
  }

  public boolean test() {
    getClientInitializerArray();
    return this.hUDModule.isEnabled() && (Boolean) this.hUDModule.notificationsSetting.getValue();
  }

  public float computeFloat() {
    return this.hUDModule.notifyDurationSetting.floatValue();
  }

  public void apply(String text, boolean flag) {
    this.apply(new WeatherStateSupport(text + (flag ? " enabled" : " disabled"), null, null, flag));
  }

  public void apply(String text) {
    this.apply(new WeatherStateSupport(text, null, null, true));
  }

  public void apply(String text, String text2, WeatherState weatherState2, boolean flag) {
    this.apply(new WeatherStateSupport(text, text2, weatherState2, flag));
  }

  public void apply(WeatherStateSupport weatherStateSupport) {
    getClientInitializerArray();
    if (this.test()) {
      synchronized (this.list) {
        this.list.add(weatherStateSupport);
        if (this.list.size() > 6) {
          this.list.removeFirst();
        }
      }
    }
  }

  @Override
  public float computeFloat(FontManager fontManager2) {
    return 168.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager2) {
    return 30.0F;
  }

  @Override
  public void render(
      FontManager fontManager2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {}

  public void render(FontManager fontManager2, float floatValue, float floatValue2) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    getClientInitializerArray();
    float value = this.computeFloat();
    float floatValue3 = value * 1.0E9F;
    synchronized (this.list) {
      Iterator iterator = this.list.iterator();
      ArrayList arrayList = new ArrayList();

      while (iterator.hasNext()) {
        WeatherStateSupport weatherStateSupport = (WeatherStateSupport) iterator.next();
        if ((float) (System.nanoTime() - weatherStateSupport.timestamp) > floatValue3 + 3.0E8F) {
          iterator.remove();
        } else {
          arrayList.add(weatherStateSupport);
        }
      }

      boolean flag =
          OverlayRenderer.test()
              && (this.test8() || (Boolean) this.hUDModule.showHiddenInEditorSetting.getValue());
      if (!arrayList.isEmpty() || flag) {
        float float3 = this.computeFloat3();
        float floatValue4 = 168.0F * float3;
        float floatValue5 = 30.0F * float3;
        float floatValue6 = this.computeFloat4() * (floatValue - floatValue4);
        float floatValue7 = this.computeFloat5() * (floatValue2 - floatValue5);
        fontManager2.update2();
        fontManager2.apply(Math.round(floatValue6), Math.round(floatValue7));
        fontManager2.apply(float3);
        float floatValue8 = 30.0F;

        for (int index = arrayList.size() - 1; index >= 0; index--) {
          WeatherStateSupport weatherStateSupport2 = (WeatherStateSupport) arrayList.get(index);
          float floatValue9 = (float) (System.nanoTime() - weatherStateSupport2.timestamp) / 1.0E9F;
          float floatValue10 = Math.clamp((value + 0.3F - floatValue9) / 0.3F, 0.0F, 1.0F);
          float value2 = weatherStateSupport2.curveTween.getValue();
          float value3 =
              this.computeFloat(
                  fontManager2,
                  colorPalette,
                  168.0F,
                  floatValue8,
                  value2,
                  floatValue10,
                  weatherStateSupport2.string,
                  weatherStateSupport2.string2,
                  weatherStateSupport2.weatherState,
                  weatherStateSupport2.enabled);
          floatValue8 -= value3 + 6.0F;
        }

        if (arrayList.isEmpty() && flag) {
          this.computeFloat(
              fontManager2,
              colorPalette,
              168.0F,
              floatValue8,
              1.0F,
              0.5F,
              "Notification",
              null,
              null,
              true);
        }

        fontManager2.update3();
      }
    }
  }

  public float computeFloat(
      FontManager fontManager2,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      String text,
      String text2,
      WeatherState weatherState2,
      boolean flag) {
    getClientInitializerArray();
    boolean flag2 = text2 != null;
    float floatValue5 = flag2 ? 42.0F : 30.0F;
    float floatValue6 = weatherState2 != null ? 26.0F : 0.0F;
    float floatValue7 = 16.0F + floatValue6;
    float floatValue8 =
        Math.max(
            fontManager2.computeFloat(text, 13.5F),
            flag2 ? fontManager2.computeFloat(text2, 11.0F) : 0.0F);
    float floatValue9 = floatValue7 + floatValue8 + 12.0F;
    float floatValue10 = floatValue - floatValue9 + (1.0F - floatValue3) * (floatValue9 + 10.0F);
    float floatValue11 = floatValue2 - floatValue5;
    int n3 = flag ? colorPalette.getCount() : colorPalette.computeInt19();
    fontManager2.update2();
    fontManager2.apply3(floatValue4);
    fontManager2.render(
        floatValue10,
        floatValue11,
        floatValue9,
        floatValue5,
        9.0F,
        colorPalette.computeInt7(),
        colorPalette.computeInt8(),
        true);
    fontManager2.render(
        floatValue10 + 3.0F, floatValue11 + 5.0F, 3.0F, floatValue5 - 10.0F, 1.5F, n3);
    fontManager2.render2(
        floatValue10,
        floatValue11,
        floatValue9,
        floatValue5,
        9.0F,
        5.0F,
        ArgbColors.withOpacity(n3, 0.2F * floatValue4));
    if (weatherState2 != null) {
      this.render(
          fontManager2,
          floatValue10 + 12.0F + 2.0F + floatValue6 / 2.0F - 4.0F,
          floatValue11 + floatValue5 / 2.0F,
          weatherState2,
          n3,
          colorPalette.computeInt());
    }

    if (flag2) {
      if (flag) {
        fontManager2.computeFloat2(
            text,
            floatValue10 + floatValue7,
            floatValue11 + floatValue5 / 2.0F - 8.0F,
            13.5F,
            colorPalette.computeInt(),
            colorPalette.getCount());
      } else {
        fontManager2.computeFloat(
            text,
            floatValue10 + floatValue7,
            floatValue11 + floatValue5 / 2.0F - 8.0F,
            13.5F,
            colorPalette.computeInt16());
      }

      fontManager2.computeFloat(
          text2,
          floatValue10 + floatValue7,
          floatValue11 + floatValue5 / 2.0F + 8.0F,
          11.0F,
          colorPalette.computeInt18());
    } else if (flag) {
      fontManager2.computeFloat2(
          text,
          floatValue10 + floatValue7,
          floatValue11 + floatValue5 / 2.0F,
          13.5F,
          colorPalette.computeInt(),
          colorPalette.getCount());
    } else {
      fontManager2.computeFloat(
          text,
          floatValue10 + floatValue7,
          floatValue11 + floatValue5 / 2.0F,
          13.5F,
          colorPalette.computeInt18());
    }

    fontManager2.update3();
    return floatValue5;
  }

  public void render(
      FontManager fontManager, float f, float f2, WeatherState weatherState, int n, int n2) {
    NotificationsHud.getClientInitializerArray();
    switch (weatherState.ordinal()) {
      case 0:
        {
          this.render(fontManager, f, f2 - 3.0f, n);
          for (int i = -1; i <= 1; ++i) {
            float f3 = f + (float) i * 4.0f;
            fontManager.render2(f3 + 1.0f, f2 + 4.0f, f3 - 1.0f, f2 + 9.0f, 1.6f, n);
          }
          break;
        }
      case 1:
        {
          this.render(fontManager, f, f2 - 3.0f, n);
          fontManager.render2(f + 1.5f, f2 + 2.0f, f - 2.5f, f2 + 6.0f, 1.9f, n2);
          fontManager.render2(f - 2.5f, f2 + 6.0f, f + 1.5f, f2 + 6.0f, 1.9f, n2);
          fontManager.render2(f + 1.5f, f2 + 6.0f, f - 2.5f, f2 + 11.0f, 1.9f, n2);
          break;
        }
      case 2:
        {
          fontManager.render2(f, f2, 4.5f, n2);
          for (int i = 0; i < 8; ++i) {
            double d = (double) i * Math.PI / 4.0;
            float f4 = (float) Math.cos(d);
            float f5 = (float) Math.sin(d);
            fontManager.render2(
                f + f4 * 6.5f, f2 + f5 * 6.5f, f + f4 * 9.0f, f2 + f5 * 9.0f, 1.6f, n);
          }
          break;
        }
    }
  }

  public void render(FontManager fontManager2, float floatValue, float floatValue2, int intValue) {
    fontManager2.render(floatValue - 7.0F, floatValue2 - 1.0F, 14.0F, 5.0F, 2.5F, intValue);
    fontManager2.render2(floatValue - 4.5F, floatValue2 + 1.0F, 4.0F, intValue);
    fontManager2.render2(floatValue + 4.5F, floatValue2 + 1.0F, 4.0F, intValue);
    fontManager2.render2(floatValue, floatValue2 - 2.5F, 5.0F, intValue);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  static {
    setClientInitializerArray(new ClientInitializer[2]);
  }
}

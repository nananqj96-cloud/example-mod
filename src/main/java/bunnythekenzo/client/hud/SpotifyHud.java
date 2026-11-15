package bunnythekenzo.client.hud;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientData4;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.SpotifyBridge;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.module.SpotifyHUDModule;
import bunnythekenzo.client.render.FontManager;

public class SpotifyHud extends HudElement {
  public static final float amount = 252.0F;
  public static final float amount2 = 74.0F;
  public static final float amount3 = 54.0F;
  public static final float amount4 = 76.0F;
  public static final float amount5 = 18.0F;
  public static final float amount6 = 36.0F;
  public static final float amount7 = 46.0F;
  public static final float amount8 = 80.0F;
  public final SpotifyHUDModule spotifyHUDModule;
  public final SpotifyBridge spotifyBridge;
  public final ThemeManager themeManager;
  public boolean enabled;
  public boolean enabled2;
  public int count = -1;
  public long timestamp = -1L;
  public int count2 = 70;
  public static final long timestamp2 = System.nanoTime();

  public SpotifyHud(
      SpotifyHUDModule spotifyHUDModule2,
      SpotifyBridge spotifyBridge2,
      ThemeManager themeManager2) {
    super(
        "spotify",
        "Spotify",
        "What is playing, with transport controls",
        0.5F,
        0.965F,
        HudElement.getHudElementContract(spotifyHUDModule2));
    this.spotifyHUDModule = spotifyHUDModule2;
    this.spotifyBridge = spotifyBridge2;
    this.themeManager = themeManager2;
  }

  public boolean test() {
    return this.spotifyHUDModule.sourceSetting.isSelected("Demo");
  }

  public ClientData4 getClientData4() {
    if (this.test()) {
      long timestamp3 = (System.nanoTime() - timestamp2) / 1000000L % 227000L;
      return new ClientData4(
          true,
          "Neon Nights",
          "bunnythekenzo radio",
          timestamp3,
          227000L,
          true,
          true,
          0,
          70,
          System.nanoTime());
    } else {
      this.spotifyBridge.update();
      return this.spotifyBridge.getClientData4();
    }
  }

  public boolean test2() {
    return (Boolean) this.spotifyHUDModule.volumeSetting.getValue()
        && this.getClientData4().enabled();
  }

  @Override
  public boolean test3() {
    return !(Boolean) this.spotifyHUDModule.hideWhenIdleSetting.getValue()
        || this.getClientData4().enabled();
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 252.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return this.test2() ? 92.0F : 74.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ClientData4 clientData4 = this.getClientData4();
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    if (clientData4.enabled() || !(Boolean) this.spotifyHUDModule.hideWhenIdleSetting.getValue()) {
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
          13.0F,
          ArgbColors.withOpacity(-15264995, 0.92F),
          ArgbColors.withOpacity(-15856878, 0.92F),
          true);
      float floatValue5 = floatValue + 10.0F;
      float floatValue6 = floatValue2 + 10.0F;
      int intValue =
          this.test()
              ? -1
              : NanoVgImageCache.computeInt(this.spotifyBridge.getPath(), clientData4.count());
      if (!this.test() && intValue > 0) {
        this.render(fontManager, intValue, floatValue5, floatValue6, 54.0F, 8.0F);
      } else {
        this.render(fontManager, colorPalette, floatValue5, floatValue6, 54.0F);
      }

      if (!clientData4.enabled()) {
        fontManager.computeFloat(
            "Nothing playing",
            floatValue + 76.0F,
            floatValue2 + floatValue4 / 2.0F,
            13.5F,
            colorPalette.computeInt18());
      } else {
        float floatValue7 =
            (java.lang.Boolean) (this.spotifyHUDModule.controlsSetting.getValue())
                ? floatValue + floatValue3 - 84.0F
                : floatValue + floatValue3 - 12.0F;
        fontManager.computeFloat(
            clientData4.string(),
            floatValue + 76.0F,
            floatValue2 + 20.0F,
            14.0F,
            colorPalette.computeInt16(),
            floatValue7 - (floatValue + 76.0F) - 6.0F);
        fontManager.computeFloat(
            clientData4.string2(),
            floatValue + 76.0F,
            floatValue2 + 38.0F,
            11.5F,
            colorPalette.computeInt18(),
            floatValue7 - (floatValue + 76.0F) - 6.0F);
        if ((Boolean) this.spotifyHUDModule.controlsSetting.getValue()) {
          float floatValue8 = floatValue2 + 22.0F;
          int int18 = colorPalette.computeInt18();
          this.render(fontManager, floatValue + floatValue3 - 76.0F, floatValue8, int18);
          this.render2(
              fontManager,
              floatValue + floatValue3 - 52.0F,
              floatValue8,
              colorPalette.computeInt(),
              clientData4.enabled2());
          this.render2(fontManager, floatValue + floatValue3 - 28.0F, floatValue8, int18);
        }

        float floatValue9 = floatValue + 76.0F;
        float floatValue10 = floatValue3 - 76.0F - 12.0F;
        float floatValue11 = floatValue2 + 58.0F;
        long longValue = this.timestamp >= 0L ? this.timestamp : clientData4.computeLong();
        float floatValue12 =
            clientData4.timestamp2() > 0L
                ? Math.clamp((float) longValue / (float) clientData4.timestamp2(), 0.0F, 1.0F)
                : 0.0F;
        fontManager.computeFloat(
            getString(longValue),
            floatValue9,
            floatValue2 + 49.0F,
            9.5F,
            colorPalette.computeInt19());
        String text = getString(clientData4.timestamp2());
        fontManager.computeFloat(
            text,
            floatValue9 + floatValue10 - fontManager.computeFloat(text, 9.5F),
            floatValue2 + 49.0F,
            9.5F,
            colorPalette.computeInt19());
        fontManager.render(
            floatValue9,
            floatValue11,
            floatValue10,
            4.0F,
            2.0F,
            ArgbColors.withOpacity(-16777216, 0.5F));
        fontManager.render(
            floatValue9,
            floatValue11,
            Math.max(4.0F, floatValue10 * floatValue12),
            4.0F,
            2.0F,
            colorPalette.getCount(),
            colorPalette.computeInt(),
            false);
        fontManager.render2(
            floatValue9 + floatValue10 * floatValue12, floatValue11 + 2.0F, 4.0F, -1);
        if ((Boolean) this.spotifyHUDModule.volumeSetting.getValue()) {
          int intValue2 = this.enabled && this.count >= 0 ? this.count : clientData4.count2();
          float floatValue13 = Math.clamp((intValue2 < 0 ? 0 : intValue2) / 100.0F, 0.0F, 1.0F);
          float floatValue14 = floatValue + 36.0F;
          float floatValue15 = floatValue3 - 36.0F - 46.0F;
          float floatValue16 = floatValue2 + 80.0F - 2.0F;
          this.render(
              fontManager,
              floatValue + 18.0F,
              floatValue2 + 80.0F,
              colorPalette.computeInt18(),
              intValue2 == 0);
          fontManager.render(
              floatValue14,
              floatValue16,
              floatValue15,
              4.0F,
              2.0F,
              ArgbColors.withOpacity(-16777216, 0.5F));
          fontManager.render(
              floatValue14,
              floatValue16,
              Math.max(4.0F, floatValue15 * floatValue13),
              4.0F,
              2.0F,
              colorPalette.getCount(),
              colorPalette.computeInt(),
              false);
          fontManager.render2(
              floatValue14 + floatValue15 * floatValue13, floatValue2 + 80.0F, 4.0F, -1);
          String text2 = intValue2 < 0 ? "--" : intValue2 + "%";
          fontManager.computeFloat(
              text2,
              floatValue + floatValue3 - fontManager.computeFloat(text2, 9.5F) - 12.0F,
              floatValue2 + 80.0F,
              9.5F,
              colorPalette.computeInt19());
        }
      }
    }
  }

  public void render(
      FontManager fontManager, float floatValue, float y, int intValue, boolean flag) {
    long timestamp3 = fontManager.getTimestamp();

    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color =
          NanoVG.nvgRGBA(
              (byte) ArgbColors.red(intValue),
              (byte) ArgbColors.green(intValue),
              (byte) ArgbColors.blue(intValue),
              (byte) ArgbColors.alpha(intValue),
              NVGColor.malloc(stack));
      fontManager.render(floatValue - 7.0F, y - 3.0F, 5.0F, 6.0F, 1.0F, intValue);
      NanoVG.nvgFillColor(timestamp3, color);
      NanoVG.nvgBeginPath(timestamp3);
      NanoVG.nvgMoveTo(timestamp3, floatValue - 6.0F, y);
      NanoVG.nvgLineTo(timestamp3, floatValue + 2.0F, y - 7.0F);
      NanoVG.nvgLineTo(timestamp3, floatValue + 2.0F, y + 7.0F);
      NanoVG.nvgClosePath(timestamp3);
      NanoVG.nvgFill(timestamp3);
      NanoVG.nvgStrokeColor(timestamp3, color);
      NanoVG.nvgStrokeWidth(timestamp3, 1.7F);
      NanoVG.nvgLineCap(timestamp3, 1);
      if (flag) {
        NanoVG.nvgBeginPath(timestamp3);
        NanoVG.nvgMoveTo(timestamp3, floatValue + 5.0F, y - 3.5F);
        NanoVG.nvgLineTo(timestamp3, floatValue + 10.0F, y + 3.5F);
        NanoVG.nvgMoveTo(timestamp3, floatValue + 10.0F, y - 3.5F);
        NanoVG.nvgLineTo(timestamp3, floatValue + 5.0F, y + 3.5F);
        NanoVG.nvgStroke(timestamp3);
      } else {
        for (float r : new float[] {4.5F, 8.0F}) {
          NanoVG.nvgBeginPath(timestamp3);
          NanoVG.nvgArc(timestamp3, floatValue + 2.0F, y, r, -0.6F, 0.6F, 2);
          NanoVG.nvgStroke(timestamp3);
        }
      }
    }
  }

  public static String getString(long longValue) {
    long longValue2 = longValue / 1000L;
    return String.format("%d:%02d", longValue2 / 60L, longValue2 % 60L);
  }

  public void render(FontManager fontManager, int image, float ox, float oy, float ex, float r) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      long timestamp3 = fontManager.getTimestamp();
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgImagePattern(timestamp3, ox, oy, ex, ex, 0.0F, image, 1.0F, __result);
      NanoVG.nvgBeginPath(timestamp3);
      NanoVG.nvgRoundedRect(timestamp3, ox, oy, ex, ex, r);
      NanoVG.nvgFillPaint(timestamp3, __result);
      NanoVG.nvgFill(timestamp3);
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    float floatValue4 = floatValue + floatValue3 / 2.0F;
    float floatValue5 = floatValue2 + floatValue3 / 2.0F;
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue3,
        8.0F,
        ArgbColors.withOpacity(-16119795, 0.9F));
    fontManager.render2(floatValue4, floatValue5, floatValue3 * 0.4F, -15330789);
    fontManager.render2(
        floatValue4,
        floatValue5,
        floatValue3 * 0.3F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), 0.35F));
    fontManager.render2(
        floatValue4,
        floatValue5,
        floatValue3 * 0.22F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), 0.25F));
    fontManager.render2(floatValue4, floatValue5, floatValue3 * 0.12F, colorPalette.getCount());
    fontManager.computeFloat2(
        "67",
        floatValue4 - fontManager.computeFloat("67", 9.0F) / 2.0F,
        floatValue5,
        9.0F,
        -1,
        -1122834);
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2, int intValue) {
    this.render(fontManager, floatValue + 4.0F, floatValue2, -7.0F, intValue);
    fontManager.render(floatValue - 7.0F, floatValue2 - 5.5F, 2.0F, 11.0F, 1.0F, intValue);
  }

  public void render2(FontManager fontManager, float floatValue, float floatValue2, int intValue) {
    this.render(fontManager, floatValue - 4.0F, floatValue2, 7.0F, intValue);
    fontManager.render(floatValue + 5.0F, floatValue2 - 5.5F, 2.0F, 11.0F, 1.0F, intValue);
  }

  public void render2(
      FontManager fontManager, float floatValue, float floatValue2, int intValue, boolean flag) {
    fontManager.render2(floatValue, floatValue2, 10.0F, 1.4F, intValue);
    if (flag) {
      fontManager.render(floatValue - 3.5F, floatValue2 - 4.5F, 2.4F, 9.0F, 1.2F, intValue);
      fontManager.render(floatValue + 1.1F, floatValue2 - 4.5F, 2.4F, 9.0F, 1.2F, intValue);
    } else {
      this.render(fontManager, floatValue - 2.5F, floatValue2, 7.0F, intValue);
    }
  }

  public void render(FontManager fontManager, float x, float y, float floatValue, int intValue) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      long timestamp3 = fontManager.getTimestamp();
      NanoVG.nvgBeginPath(timestamp3);
      NanoVG.nvgMoveTo(timestamp3, x, y - 5.5F);
      NanoVG.nvgLineTo(timestamp3, x, y + 5.5F);
      NanoVG.nvgLineTo(timestamp3, x + floatValue, y);
      NanoVG.nvgClosePath(timestamp3);
      NanoVG.nvgFillColor(
          timestamp3,
          NanoVG.nvgRGBA(
              (byte) ArgbColors.red(intValue),
              (byte) ArgbColors.green(intValue),
              (byte) ArgbColors.blue(intValue),
              (byte) ArgbColors.alpha(intValue),
              NVGColor.malloc(stack)));
      NanoVG.nvgFill(timestamp3);
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2) {
    ClientData4 clientData4 = this.getClientData4();
    if (!clientData4.enabled()) {
      return false;
    }

    if ((Boolean) this.spotifyHUDModule.controlsSetting.getValue()
        && floatValue2 >= 10.0F
        && floatValue2 <= 34.0F) {
      if (test2(floatValue, 176.0F)) {
        if (this.test()) {
          return true;
        }

        this.spotifyBridge.update4();
        return true;
      }

      if (test2(floatValue, 200.0F)) {
        if (this.test()) {
          return true;
        }

        this.spotifyBridge.update5();
        return true;
      }

      if (test2(floatValue, 224.0F)) {
        if (this.test()) {
          return true;
        }

        this.spotifyBridge.update3();
        return true;
      }
    }

    if (floatValue2 >= 52.0F
        && floatValue2 <= 66.0F
        && floatValue >= 76.0F
        && floatValue <= 240.0F) {
      this.enabled2 = clientData4.enabled3() && clientData4.timestamp2() > 0L;
      if (this.enabled2) {
        this.apply(floatValue, floatValue2);
      }

      return true;
    } else if ((Boolean) this.spotifyHUDModule.volumeSetting.getValue()
        && floatValue2 >= 73.0F
        && floatValue2 <= 87.0F
        && floatValue >= 36.0F
        && floatValue <= 206.0F) {
      this.enabled = clientData4.count2() >= 0;
      this.count = -1;
      if (this.enabled) {
        this.apply(floatValue, floatValue2);
      }

      return true;
    } else if ((Boolean) this.spotifyHUDModule.volumeSetting.getValue()
        && floatValue2 >= 72.0F
        && floatValue2 <= 88.0F
        && test2(floatValue, 18.0F)) {
      if (clientData4.count2() > 0) {
        this.count2 = clientData4.count2();
      }

      if (!this.test() && clientData4.count2() >= 0) {
        this.spotifyBridge.apply(clientData4.count2() == 0 ? this.count2 : 0);
      }

      return true;
    } else {
      return false;
    }
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.enabled) {
      int count3 = Math.round(Math.clamp((floatValue - 36.0F) / 170.0F, 0.0F, 1.0F) * 100.0F);
      if (count3 != this.count && !this.test()) {
        this.spotifyBridge.apply(count3);
      }

      this.count = count3;
    }

    if (this.enabled2) {
      this.timestamp =
          Math.round(
              (double) Math.clamp((floatValue - 76.0F) / 164.0F, 0.0F, 1.0F)
                  * this.getClientData4().timestamp2());
    }
  }

  @Override
  public void update() {
    if (!this.test() && this.enabled2 && this.timestamp >= 0L && this.getClientData4().enabled()) {
      this.spotifyBridge.apply(this.timestamp);
    }

    this.enabled = this.enabled2 = false;
    this.count = -1;
    this.timestamp = -1L;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (this.test2() && !(floatValue2 < 71.0F) && !(floatValue2 > 89.0F)) {
      ClientData4 clientData4 = this.getClientData4();
      if (!this.test() && clientData4.count2() >= 0) {
        this.spotifyBridge.apply(clientData4.count2() + (int) Math.signum(doubleValue) * 5);
      }

      return true;
    } else {
      return false;
    }
  }

  public static boolean test2(float floatValue, float floatValue2) {
    return Math.abs(floatValue - floatValue2) <= 11.0F;
  }
}

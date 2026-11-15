package bunnythekenzo.client.hud;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClickRateTracker;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.CurveTween;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.render.FontManager;

public class KeystrokesHud extends HudElement {
  public static final float amount = 26.0F;
  public static final float amount2 = 3.0F;
  public static final float amount3 = 12.5F;
  public final ThemeManager themeManager;
  public final Map map = new HashMap();

  public KeystrokesHud(ThemeManager themeManager2, HudElementContract hudElementContract) {
    super(
        "keystrokes",
        "Keystrokes",
        "WASD, the mouse buttons and space",
        0.03F,
        0.72F,
        hudElementContract);
    this.themeManager = themeManager2;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 84.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return 102.6F;
  }

  public float pressT(String text, boolean flag) {
    CurveTween curveTween =
        (bunnythekenzo.client.CurveTween)
            (this.map.computeIfAbsent(
                text,
                value -> {
                  return new CurveTween(110.0F, 0.0F);
                }));
    curveTween.animateTo(flag ? 1.0F : 0.0F);
    return curveTween.getValue();
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      String text,
      String text2,
      boolean flag,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = this.pressT(text, flag);
    int value =
        ArgbColors.lerp(
            ArgbColors.withOpacity(-15462118, 0.78F),
            ArgbColors.withOpacity(colorPalette.getCount(), 0.85F),
            floatValue5);
    fontManager.render(floatValue, floatValue2, floatValue3, floatValue4, 6.0F, value);
    int value2 = ArgbColors.lerp(colorPalette.computeInt18(), -1, floatValue5);
    fontManager.computeFloat(
        text2,
        floatValue + (floatValue3 - fontManager.computeFloat(text2, 12.5F)) / 2.0F,
        floatValue2 + floatValue4 / 2.0F,
        12.5F,
        value2);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    GameOptions options = MinecraftClient.getInstance().options;
    float floatValue5 = floatValue2;
    this.render(
        fontManager,
        colorPalette,
        "w",
        "W",
        test(options.forwardKey),
        floatValue + 26.0F + 3.0F,
        floatValue5,
        26.0F,
        26.0F);
    floatValue5 += 29.0F;
    this.render(
        fontManager,
        colorPalette,
        "a",
        "A",
        test(options.leftKey),
        floatValue,
        floatValue5,
        26.0F,
        26.0F);
    this.render(
        fontManager,
        colorPalette,
        "s",
        "S",
        test(options.backKey),
        floatValue + 26.0F + 3.0F,
        floatValue5,
        26.0F,
        26.0F);
    this.render(
        fontManager,
        colorPalette,
        "d",
        "D",
        test(options.rightKey),
        floatValue + 58.0F,
        floatValue5,
        26.0F,
        26.0F);
    floatValue5 += 29.0F;
    float floatValue6 = (floatValue3 - 3.0F) / 2.0F;
    this.render(
        fontManager,
        colorPalette,
        "lmb",
        "LMB " + ClickRateTracker.getClicksPerSecond(0),
        test(options.attackKey),
        floatValue,
        floatValue5,
        floatValue6,
        26.0F);
    this.render(
        fontManager,
        colorPalette,
        "rmb",
        "RMB " + ClickRateTracker.getClicksPerSecond(1),
        test(options.useKey),
        floatValue + floatValue6 + 3.0F,
        floatValue5,
        floatValue6,
        26.0F);
    floatValue5 += 29.0F;
    floatValue6 = this.pressT("space", test(options.jumpKey));
    int value =
        ArgbColors.lerp(
            ArgbColors.withOpacity(-15462118, 0.78F),
            ArgbColors.withOpacity(colorPalette.getCount(), 0.85F),
            floatValue6);
    fontManager.render(floatValue, floatValue5, floatValue3, 15.6F, 6.0F, value);
    fontManager.render(
        floatValue + floatValue3 * 0.25F,
        floatValue5 + 7.8F - 1.25F,
        floatValue3 * 0.5F,
        2.5F,
        1.25F,
        ArgbColors.lerp(colorPalette.computeInt18(), -1, floatValue6));
  }

  public static boolean test(KeyBinding keyBinding) {
    return keyBinding.isPressed();
  }
}

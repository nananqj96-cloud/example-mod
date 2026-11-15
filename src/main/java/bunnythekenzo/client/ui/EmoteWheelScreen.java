package bunnythekenzo.client.ui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.AnimatedTextRows;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;

public final class EmoteWheelScreen extends Screen implements ClientScreen {
  public final EmoteCatalog emoteCatalog;
  public final int count;
  public final AnimatedTextRows animatedTextRows;
  public final TweenValue tweenValue = new TweenValue(190.0F, 0.0F, Easing.OUT_BACK);
  public int count2 = -1;
  public boolean enabled;

  public EmoteWheelScreen(EmoteCatalog emoteCatalog2, int count3) {
    super(Text.literal("Emote wheel"));
    this.emoteCatalog = emoteCatalog2;
    this.count = count3;
    this.animatedTextRows = new AnimatedTextRows(emoteCatalog2.getList().size());
    this.tweenValue.animateTo(1.0F);
  }

  @Override
  public void onDisplayed() {
    super.onDisplayed();
    Window window = this.client.getWindow();
    GLFW.glfwSetCursorPos(window.getHandle(), window.getWidth() / 2.0, window.getHeight() / 2.0);
    this.client.mouse.onResolutionChanged();
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void renderBackground(
      DrawContext drawContext, int intValue, int intValue2, float floatValue) {}

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {}

  @Override
  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float value = this.tweenValue.getValue();
    float floatValue5 = floatValue3 / 2.0F;
    float floatValue6 = floatValue4 / 2.0F;
    float floatValue7 = Math.min(205.0F, Math.min(floatValue3, floatValue4) * 0.31F);
    float floatValue8 = floatValue7 * 0.4F;
    fontManager.render(
        0.0F,
        0.0F,
        floatValue3,
        floatValue4,
        0.0F,
        ArgbColors.withOpacity(ColorPalette.getInstance().computeInt2(), 0.42F * value));
    this.count2 =
        this.animatedTextRows.computeInt(
            fontManager,
            this.emoteCatalog.getList(),
            floatValue5,
            floatValue6,
            floatValue8,
            floatValue7,
            floatValue,
            floatValue2,
            -1,
            value);
    ColorPalette instance = ColorPalette.getInstance();
    String text =
        this.count2 >= 0
            ? ((EmoteDefinition) this.emoteCatalog.getList().get(this.count2)).string()
            : "EMOTE WHEEL";
    fontManager.computeFloat2(
        text,
        floatValue5,
        floatValue6 - floatValue7 - 34.0F,
        18.0F,
        this.count2 >= 0 ? instance.computeInt16() : instance.computeInt17());
    String text2 =
        this.count2 >= 0
            ? (((EmoteDefinition) this.emoteCatalog.getList().get(this.count2)).test()
                ? "Blank emote  ·  Release to close"
                : "Release to dance  ·  Esc to cancel")
            : "Move outside the centre, then release";
    fontManager.computeFloat2(
        text2, floatValue5, floatValue6 + floatValue7 + 31.0F, 11.0F, instance.computeInt18());
  }

  @Override
  public boolean keyReleased(KeyInput keyInput) {
    if (keyInput.key() == this.count) {
      this.apply(true);
      return true;
    } else {
      return super.keyReleased(keyInput);
    }
  }

  @Override
  public boolean keyPressed(KeyInput keyInput) {
    if (keyInput.key() == 256) {
      this.apply(false);
      return true;
    } else {
      return true;
    }
  }

  @Override
  public boolean mouseClicked(Click click, boolean flag) {
    if (click.button() == 0 && this.count2 >= 0) {
      this.apply(true);
    }

    return true;
  }

  @Override
  public void close() {
    this.apply(false);
  }

  public void apply(boolean flag) {
    if (!this.enabled) {
      this.enabled = true;
      this.client.setScreen(null);
      if (flag && this.count2 >= 0) {
        this.emoteCatalog.apply(this.count2);
      }
    }
  }
}

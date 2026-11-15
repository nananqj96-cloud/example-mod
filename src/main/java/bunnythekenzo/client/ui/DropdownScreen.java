package bunnythekenzo.client.ui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiBlurOverride;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.OverlayRenderer;

public final class DropdownScreen extends Screen implements ClientScreen {
  public static final float amount = 0.8F;
  public final PanelMenu panelMenu = new PanelMenu();
  public final Screen screen;

  public DropdownScreen() {
    this(null);
  }

  public DropdownScreen(Screen screen2) {
    super(Text.literal("bunnythekenzo · Dropdown"));
    this.screen = screen2;
  }

  public Screen getScreen() {
    return this.screen;
  }

  public PanelMenu getPanelMenu() {
    return this.panelMenu;
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void close() {
    this.panelMenu.update();
  }

  @Override
  public void tick() {
    if (this.panelMenu.test() && this.client.currentScreen == this) {
      this.client.setScreen(
          this.panelMenu.isEnabled3() ? ScreenSupport4.getScreen(0, this.screen) : this.screen);
    }
  }

  @Override
  public void removed() {
    GuiBlurOverride.clear();
    this.panelMenu.close();
    GuiPreferences.getGuiPreferences().update3();
    JsonSupport2.update3();
    if (ClientInitializer.getClientConfig() != null) {
      ClientInitializer.getClientConfig().update3();
    }
  }

  @Override
  public void renderBackground(
      DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    if (this.client.world == null) {
      this.renderPanoramaBackground(drawContext, floatValue);
    }

    GuiBlurOverride.setBlurRadius(
        GuiPreferences.getGuiPreferences().computeFloat() * this.panelMenu.computeFloat());
    drawContext.applyBlur();
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    int intValue3 =
        Math.max(
            0,
            (int)
                (255.0F
                    * GuiPreferences.getGuiPreferences().computeFloat2()
                    * 0.22F
                    * this.panelMenu.computeFloat()));
    drawContext.fill(0, 0, this.width, this.height, intValue3 << 24 | 460042);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.panelMenu.render(
        fontManager,
        computeFloat(floatValue, floatValue3),
        computeFloat(floatValue2, floatValue4),
        floatValue3,
        floatValue4);
  }

  @Override
  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    fontManager.update2();
    fontManager.render2(floatValue3 / 2.0F, floatValue4 / 2.0F, 0.8F);
    this.panelMenu.render2(
        fontManager,
        computeFloat(floatValue, floatValue3),
        computeFloat(floatValue2, floatValue4),
        floatValue3,
        floatValue4);
    fontManager.update3();
  }

  public static float computeFloat(float floatValue, float floatValue2) {
    return (floatValue - floatValue2 / 2.0F) / 0.8F + floatValue2 / 2.0F;
  }

  public static float computeFloat(double doubleValue) {
    return computeFloat(OverlayRenderer.computeFloat(doubleValue), OverlayRenderer.computeFloat4());
  }

  public static float computeFloat2(double doubleValue) {
    return computeFloat(OverlayRenderer.computeFloat(doubleValue), OverlayRenderer.computeFloat5());
  }

  @Override
  public boolean mouseClicked(Click click, boolean flag) {
    this.panelMenu.click(computeFloat(click.x()), computeFloat2(click.y()), click.button());
    return true;
  }

  @Override
  public boolean mouseDragged(Click click, double doubleValue, double doubleValue2) {
    this.panelMenu.apply(computeFloat(click.x()), computeFloat2(click.y()));
    return true;
  }

  @Override
  public boolean mouseReleased(Click click) {
    if (click.button() == 0) {
      this.panelMenu.update3();
    }

    return true;
  }

  @Override
  public boolean mouseScrolled(
      double doubleValue, double doubleValue2, double doubleValue3, double doubleValue4) {
    this.panelMenu.render(computeFloat(doubleValue), computeFloat2(doubleValue2), doubleValue4);
    return true;
  }

  @Override
  public boolean keyPressed(KeyInput keyInput) {
    return this.panelMenu.test(keyInput.key(), (keyInput.modifiers() & 1) != 0)
        || super.keyPressed(keyInput);
  }

  @Override
  public boolean charTyped(CharInput charInput) {
    return this.panelMenu.charTyped(charInput.codepoint()) || super.charTyped(charInput);
  }
}

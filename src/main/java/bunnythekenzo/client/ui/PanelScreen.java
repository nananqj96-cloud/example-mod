package bunnythekenzo.client.ui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiBlurOverride;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.OverlayRenderer;

public class PanelScreen extends Screen implements ClientScreen {
  public final MenuRenderContext menuRenderContext = new MenuRenderContext();
  public final Screen screen;

  public PanelScreen() {
    this(null);
  }

  public PanelScreen(Screen screen2) {
    super(Text.literal("bunnythekenzo"));
    this.screen = screen2;
  }

  public Screen getScreen() {
    return this.screen;
  }

  public MenuRenderContext getMenuRenderContext() {
    return this.menuRenderContext;
  }

  public boolean test(String text) {
    return this.menuRenderContext.test(text);
  }

  public void update() {
    this.menuRenderContext.update();
  }

  public void update2() {
    this.menuRenderContext.update2();
  }

  public void apply(GuiModuleEntry guiModuleEntry) {
    this.menuRenderContext.apply(guiModuleEntry);
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void close() {
    this.menuRenderContext.update3();
  }

  @Override
  public void removed() {
    GuiBlurOverride.clear();
    this.menuRenderContext.close();
    GuiDialogManager.closeAll();
    GuiPreferences.getGuiPreferences().update3();
    ProfileStorage.getProfileStorage().test2();
    if (ClientInitializer.getEmoteCatalog() != null) {
      ClientInitializer.getEmoteCatalog().update4();
    }

    JsonSupport2.update3();
    JsonSupport2.update6();
  }

  @Override
  public void tick() {
    this.update3();
  }

  public void update3() {
    if (this.menuRenderContext.test() && this.client.currentScreen == this) {
      this.client.setScreen(
          this.menuRenderContext.isEnabled()
              ? ScreenSupport4.getScreen(1, this.screen)
              : this.screen);
    }
  }

  @Override
  public void renderBackground(
      DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    if (this.client.world == null) {
      this.renderPanoramaBackground(drawContext, floatValue);
    }

    GuiBlurOverride.setBlurRadius(
        GuiPreferences.getGuiPreferences().computeFloat() * this.menuRenderContext.computeFloat());
    drawContext.applyBlur();
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    int intValue3 =
        Math.max(
            2,
            (int)
                (255.0F
                    * GuiPreferences.getGuiPreferences().computeFloat2()
                    * 0.22F
                    * this.menuRenderContext.computeFloat()));
    drawContext.fill(0, 0, this.width, this.height, intValue3 << 24 | 460042);
    this.update3();
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.menuRenderContext.prepare(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
  }

  @Override
  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.menuRenderContext.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
  }

  public static float computeFloat(double doubleValue) {
    return OverlayRenderer.computeFloat(doubleValue);
  }

  public static float computeFloat2(double doubleValue) {
    return OverlayRenderer.computeFloat(doubleValue);
  }

  @Override
  public boolean mouseClicked(Click click, boolean flag) {
    this.menuRenderContext.test(computeFloat(click.x()), computeFloat2(click.y()), click.button());
    return true;
  }

  @Override
  public boolean mouseDragged(Click click, double doubleValue, double doubleValue2) {
    this.menuRenderContext.apply(computeFloat(click.x()), computeFloat2(click.y()));
    return true;
  }

  @Override
  public boolean mouseReleased(Click click) {
    this.menuRenderContext.update4();
    return true;
  }

  @Override
  public boolean mouseScrolled(
      double doubleValue, double doubleValue2, double doubleValue3, double doubleValue4) {
    this.menuRenderContext.test(
        computeFloat(doubleValue), computeFloat2(doubleValue2), doubleValue4);
    return true;
  }

  @Override
  public boolean charTyped(CharInput charInput) {
    return this.menuRenderContext.test2(charInput.codepoint()) || super.charTyped(charInput);
  }

  @Override
  public boolean keyPressed(KeyInput keyInput) {
    if (this.menuRenderContext.test(keyInput.key())) {
      return true;
    }

    switch (keyInput.key()) {
      case 256:
        if (!this.menuRenderContext.test3()) {
          this.menuRenderContext.update3();
        }

        return true;
      case 344:
        this.menuRenderContext.update3();
        return true;
      default:
        return super.keyPressed(keyInput);
    }
  }
}

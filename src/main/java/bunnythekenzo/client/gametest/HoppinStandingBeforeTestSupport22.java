package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteCatalog;

public final class HoppinStandingBeforeTestSupport22 extends Screen {
  public static final long timestamp = 2400000000L;

  public HoppinStandingBeforeTestSupport22(String text) {
    super(Text.literal(text));
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    try {
      Field declaredField = EmoteCatalog.class.getDeclaredField("timestamp");
      declaredField.setAccessible(true);
      declaredField.setLong(ClientInitializer.getEmoteCatalog(), System.nanoTime() - timestamp);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }

    drawContext.fillGradient(0, 0, this.width, this.height, -15586742, -16314852);
    drawContext.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 26, -1);
    InventoryScreen.drawEntity(
        drawContext,
        this.width / 2 - 165,
        55,
        this.width / 2 + 165,
        this.height - 36,
        125,
        0.0625F,
        this.width / 2.0F + 40.0F,
        this.height / 2.0F,
        this.client.player);
  }
}

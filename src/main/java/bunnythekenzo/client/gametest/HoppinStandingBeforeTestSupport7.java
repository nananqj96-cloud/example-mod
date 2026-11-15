package bunnythekenzo.client.gametest;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;

public final class HoppinStandingBeforeTestSupport7 extends Screen {
  public int count;

  public HoppinStandingBeforeTestSupport7() {
    super(Text.literal("Air gesture preview"));
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    ProtectionPerformanceTest.ensureInitialized();
    JerkingOffWheelTest.apply(
        this.count < 4 ? 0.5 + this.count / 4.0 : 9.5 + (this.count - 4) / 24.0);
    drawContext.fillGradient(0, 0, this.width, this.height, -15193279, -16249320);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer, "JERKING OFF", this.width / 2, 24, -1182465);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer,
        this.count < 4 ? "Slow opening" : "Fast buildup / confetti finale",
        this.width / 2,
        42,
        -7033914);
    InventoryScreen.drawEntity(
        drawContext,
        this.width / 2 - 165,
        62,
        this.width / 2 + 165,
        this.height - 24,
        125,
        0.0625F,
        this.width / 2.0F + 48.0F,
        this.height / 2.0F,
        this.client.player);
  }
}

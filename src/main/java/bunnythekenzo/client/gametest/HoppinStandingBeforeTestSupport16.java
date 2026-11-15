package bunnythekenzo.client.gametest;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;

public final class HoppinStandingBeforeTestSupport16 extends Screen {
  public int count;
  public boolean enabled;

  public HoppinStandingBeforeTestSupport16() {
    super(Text.literal("Take the L preview"));
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    TakeTheLMenuTest.apply(1.6 + this.count / 30.0);
    drawContext.fillGradient(0, 0, this.width, this.height, -15586742, -16314852);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer, "TAKE THE L", this.width / 2, 26, -1576705);
    ProtectionPerformanceTest.ensureInitialized();

    drawContext.drawCenteredTextWithShadow(
        this.textRenderer,
        "Original music / alternating kicks / articulated limbs",
        this.width / 2,
        44,
        -7755825);
    InventoryScreen.drawEntity(
        drawContext,
        this.width / 2 - 165,
        70,
        this.width / 2 + 165,
        this.height - 36,
        125,
        0.0625F,
        this.width / 2.0F + (this.enabled ? 65 : 0),
        this.height / 2.0F,
        this.client.player);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer, "BUNNYTHEKENZO  /  DANCE", this.width / 2, this.height - 23, -7755825);
  }
}

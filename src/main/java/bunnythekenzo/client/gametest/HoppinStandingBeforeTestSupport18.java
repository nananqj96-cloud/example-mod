package bunnythekenzo.client.gametest;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.text.Text;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class HoppinStandingBeforeTestSupport18 extends Screen {
  public boolean enabled;

  public HoppinStandingBeforeTestSupport18() {
    super(Text.literal("Twerking preview"));
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    ProtectionPerformanceTest.ensureInitialized();
    if (Double.isFinite(TwerkingMenuTest.value)) {
      TwerkingMenuTest.apply(TwerkingMenuTest.value);
    }

    drawContext.fillGradient(0, 0, this.width, this.height, -15586742, -16314852);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer, "TWERKING", this.width / 2, 26, -1576705);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer,
        "Original music / full routine / your skin",
        this.width / 2,
        44,
        -7755825);
    drawContext.fill(
        this.width / 2 - 110, this.height - 86, this.width / 2 + 110, this.height - 85, -14072996);
    EntityRenderer renderer =
        this.client.getEntityRenderDispatcher().getRenderer(this.client.player);
    PlayerEntityRenderState playerEntityRenderState =
        (PlayerEntityRenderState) renderer.getAndUpdateRenderState(this.client.player, 1.0F);
    playerEntityRenderState.light = 15728880;
    playerEntityRenderState.shadowPieces.clear();
    playerEntityRenderState.outlineColor = 0;
    playerEntityRenderState.bodyYaw = this.enabled ? -55.0F : 0.0F;
    playerEntityRenderState.relativeHeadYaw = playerEntityRenderState.pitch = 0.0F;
    playerEntityRenderState.height =
        playerEntityRenderState.height / playerEntityRenderState.baseScale;
    playerEntityRenderState.width =
        playerEntityRenderState.width / playerEntityRenderState.baseScale;
    playerEntityRenderState.baseScale = 1.0F;
    drawContext.addEntity(
        playerEntityRenderState,
        125.0F,
        new Vector3f(0.0F, playerEntityRenderState.height / 2.0F + 0.0625F, 0.0F),
        new Quaternionf().rotateZ((float) Math.PI),
        new Quaternionf(),
        this.width / 2 - 165,
        70,
        this.width / 2 + 165,
        this.height - 36);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer, "BUNNYTHEKENZO  /  DANCE", this.width / 2, this.height - 23, -7755825);
  }
}

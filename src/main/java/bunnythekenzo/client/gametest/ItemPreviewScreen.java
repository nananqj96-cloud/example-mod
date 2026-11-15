package bunnythekenzo.client.gametest;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ItemPreviewScreen extends Screen {
  public final ItemStack previewStack;
  public final MinecraftClient client;

  public ItemPreviewScreen(Text text, ItemStack itemStack2, MinecraftClient minecraftClient2) {
    super(text);
    this.previewStack = itemStack2;
    this.client = minecraftClient2;
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    drawContext.drawItem(this.previewStack, this.width / 2 - 24, this.height / 2);
    drawContext.drawStackOverlay(
        this.client.textRenderer, this.previewStack, this.width / 2 - 24, this.height / 2);
    drawContext.drawItemTooltip(
        this.client.textRenderer, this.previewStack, this.width / 2, this.height / 2);
  }

  @Override
  public boolean shouldPause() {
    return false;
  }
}

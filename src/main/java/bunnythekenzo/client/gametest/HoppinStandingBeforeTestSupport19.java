package bunnythekenzo.client.gametest;

import java.util.Locale;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.CustomGlintModule;

public final class HoppinStandingBeforeTestSupport19 extends Screen {
  public final ItemStack[] itemStackArray =
      new ItemStack[] {
        new ItemStack(Items.DIAMOND_SWORD),
        new ItemStack(Items.DIAMOND_PICKAXE),
        new ItemStack(Items.APPLE),
        new ItemStack(Items.GRASS_BLOCK),
        new ItemStack(Items.GLASS),
        new ItemStack(Items.POTION),
        new ItemStack(Items.SHIELD),
        new ItemStack(Items.TRIDENT)
      };

  public HoppinStandingBeforeTestSupport19() {
    super(Text.literal("Custom Glint materials"));
  }

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    ProtectionPerformanceTest.ensureInitialized();

    drawContext.fill(0, 0, this.width, this.height, -16249573);
    CustomGlintModule customGlintModule = ClientInitializer.getModuleManager().customGlintModule;
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer,
        ((String) customGlintModule.glintStyleSetting.getValue()).toUpperCase(Locale.ROOT)
            + " / "
            + (String) customGlintModule.finishSetting.getValue(),
        this.width / 2,
        18,
        -1712129);
    drawContext.drawCenteredTextWithShadow(
        this.textRenderer,
        "Real item models - original silhouettes and transparency",
        this.width / 2,
        32,
        -7169359);

    for (int index = 0; index < this.itemStackArray.length; index++) {
      int intValue3 = this.width / 2 - 170 + index % 4 * 90;
      int intValue4 = 62 + index / 4 * 95;
      drawContext.fill(intValue3 - 5, intValue4 - 4, intValue3 + 78, intValue4 + 80, -15525077);
      drawContext.getMatrices().pushMatrix();
      drawContext.getMatrices().translate(intValue3 + 5, intValue4 + 2);
      drawContext.getMatrices().scale(4.0F, 4.0F);
      drawContext.drawItem(this.itemStackArray[index], 0, 0);
      drawContext.getMatrices().popMatrix();
    }
  }

  @Override
  public boolean shouldPause() {
    return false;
  }
}

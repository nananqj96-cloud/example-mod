package bunnythekenzo.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(DrawContext.class)
public class DrawContextMixin {
  @ModifyVariable(
      method =
          "drawItemTooltip(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V",
      at = @At("HEAD"),
      argsOnly = true)
  public ItemStack getItemStack(ItemStack itemStack) {
    return FakeLootDropModule.getItemStack2(itemStack);
  }

  @ModifyVariable(
      method =
          "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
      at = @At("HEAD"),
      argsOnly = true)
  public ItemStack getItemStack2(ItemStack itemStack) {
    return FakeLootDropModule.getItemStack2(itemStack);
  }
}

package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(InGameHud.class)
public class InGameHudMixin {
  @ModifyExpressionValue(
      method = "renderHeldItemTooltip",
      at =
          @At(
              value = "FIELD",
              target =
                  "Lnet/minecraft/client/gui/hud/InGameHud;currentStack:Lnet/minecraft/item/ItemStack;"))
  public ItemStack getItemStack(ItemStack itemStack) {
    return FakeLootDropModule.getItemStack2(itemStack);
  }
}

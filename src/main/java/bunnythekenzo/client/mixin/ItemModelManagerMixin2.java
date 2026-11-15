package bunnythekenzo.client.mixin;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin2 {
  @ModifyVariable(method = "clearAndUpdate", at = @At("HEAD"), argsOnly = true)
  public ItemStack getItemStack(
      ItemStack itemStack,
      ItemRenderState itemRenderState,
      ItemStack itemStack2,
      ItemDisplayContext itemDisplayContext) {
    return itemDisplayContext != ItemDisplayContext.FIRST_PERSON_LEFT_HAND
            && itemDisplayContext != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
        ? FakeLootDropModule.getItemStack2(itemStack)
        : itemStack;
  }
}

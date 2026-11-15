package bunnythekenzo.client.mixin;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.OriginalGlintAccess;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
  @Inject(method = "clearAndUpdate", at = @At("RETURN"))
  public void apply(
      ItemRenderState itemRenderState,
      ItemStack itemStack,
      ItemDisplayContext itemDisplayContext,
      World world,
      HeldItemContext heldItemContext,
      int intValue,
      CallbackInfo callbackInfo) {
    ((OriginalGlintAccess) (Object) (itemRenderState)).setOriginalGlint(itemStack.hasGlint());
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.customGlintModule.test(itemDisplayContext, itemStack.hasGlint())) {
      itemRenderState.markAnimated();
    }
  }
}

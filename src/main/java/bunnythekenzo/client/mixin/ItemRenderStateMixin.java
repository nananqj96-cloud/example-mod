package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GlintSubmissionScope;
import bunnythekenzo.client.OriginalGlintAccess;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements OriginalGlintAccess {
  @Shadow public ItemDisplayContext displayContext;
  @Unique public boolean enabled;

  @Override
  public void setOriginalGlint(boolean enabled2) {
    this.enabled = enabled2;
  }

  @Inject(method = "clear", at = @At("HEAD"))
  public void setEnabled(CallbackInfo callbackInfo) {
    this.enabled = false;
  }

  @WrapMethod(method = "render")
  public void apply(
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      int intValue2,
      int intValue3,
      Operation operation) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    boolean flag =
        GlintSubmissionScope.setActive(
            moduleManager != null
                && moduleManager.customGlintModule.test(this.displayContext, this.enabled));

    try {
      operation.call(matrixStack, orderedRenderCommandQueue, intValue, intValue2, intValue3);
    } finally {
      GlintSubmissionScope.setActive(flag);
    }
  }
}

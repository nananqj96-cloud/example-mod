package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentModel.LayerType;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GlintSubmissionScope;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin2 {
  @WrapMethod(
      method =
          "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V")
  public void apply(
      LayerType layerType,
      RegistryKey registryKey,
      Model model,
      Object value2,
      ItemStack itemStack,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      Identifier identifier,
      int intValue2,
      int intValue3,
      Operation operation) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    boolean flag =
        GlintSubmissionScope.setActive(
            moduleManager != null && moduleManager.customGlintModule.test(itemStack.hasGlint()));

    try {
      operation.call(
          layerType,
          registryKey,
          model,
          value2,
          itemStack,
          matrixStack,
          orderedRenderCommandQueue,
          intValue,
          identifier,
          intValue2,
          intValue3);
    } finally {
      GlintSubmissionScope.setActive(flag);
    }
  }
}

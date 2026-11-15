package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ArmorTrimHiderModule;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin {
  @ModifyExpressionValue(
      method =
          "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/item/ItemStack;get(Lnet/minecraft/component/ComponentType;)Ljava/lang/Object;"))
  public Object getObject(
      Object value2,
      @Local(argsOnly = true) ItemStack itemStack,
      @Local(argsOnly = true) Object value3) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager == null) {
      return value2;
    } else {
      ArmorTrimHiderModule armorTrimHiderModule = moduleManager.armorTrimHiderModule;
      if (armorTrimHiderModule == null || !armorTrimHiderModule.isEnabled()) {
        return value2;
      } else {
        return !armorTrimHiderModule.test() && test(value3)
            ? value2
            : armorTrimHiderModule.getArmorTrim(itemStack, (ArmorTrim) value2);
      }
    }
  }

  private static boolean test(Object value2) {
    if (!(value2 instanceof PlayerEntityRenderState playerEntityRenderState)) {
      return false;
    } else {
      MinecraftClient instance = MinecraftClient.getInstance();
      return instance.player != null && playerEntityRenderState.id == instance.player.getId();
    }
  }
}

package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
  @Shadow public Slot focusedSlot;

  @ModifyExpressionValue(
      method = "drawMouseoverTooltip",
      at =
          @At(
              value = "INVOKE",
              target = "Lnet/minecraft/screen/slot/Slot;getStack()Lnet/minecraft/item/ItemStack;"))
  public ItemStack getItemStack(ItemStack itemStack) {
    return FakeLootDropModule.getItemStack2(itemStack);
  }

  @Inject(method = "drawMouseoverTooltip", at = @At("HEAD"), cancellable = true)
  public void apply(
      DrawContext drawContext, int intValue, int intValue2, CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.isEnabled()) {
      ItemStack itemStack = fakeLootDropModule.equipmentPreview.getItemStack(this.focusedSlot);
      if (!itemStack.isEmpty()) {
        MinecraftClient instance = MinecraftClient.getInstance();
        drawContext.drawTooltip(
            instance.textRenderer,
            Screen.getTooltipFromItem(instance, itemStack),
            itemStack.getTooltipData(),
            intValue,
            intValue2,
            itemStack.get(DataComponentTypes.TOOLTIP_STYLE));
        callbackInfo.cancel();
      }
    }
  }

  @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
  public void apply(
      DrawContext drawContext, Slot slot, int intValue, int intValue2, CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.isEnabled()) {
      ItemStack itemStack2 = fakeLootDropModule.equipmentPreview.getItemStack2(slot);
      if (!itemStack2.isEmpty()) {
        drawContext.drawItem(itemStack2, slot.x, slot.y);
        callbackInfo.cancel();
      }
    }
  }

  @Inject(
      method =
          "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V",
      at = @At("HEAD"),
      cancellable = true)
  public void apply(
      Slot slot,
      int intValue,
      int intValue2,
      SlotActionType slotActionType,
      CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null
        && fakeLootDropModule.isEnabled()
        && fakeLootDropModule.equipmentPreview.test(slot, slotActionType)) {
      callbackInfo.cancel();
    }
  }
}

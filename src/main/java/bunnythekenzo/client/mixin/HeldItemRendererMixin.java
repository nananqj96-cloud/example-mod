package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.module.AnimationsModule;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
  @Unique public float amount;

  @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
  public ItemStack getItemStack(ItemStack itemStack) {
    return FakeLootDropModule.getItemStack2(itemStack);
  }

  @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true, ordinal = 3)
  public float computeFloat(float floatValue) {
    AnimationsModule animationsModule = getAnimationsModule();
    return animationsModule != null && animationsModule.test() ? 0.0F : floatValue;
  }

  @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
  public void render(
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float floatValue,
      float floatValue2,
      Hand hand,
      float amount2,
      ItemStack itemStack,
      float floatValue3,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int intValue,
      CallbackInfo callbackInfo) {
    this.amount = amount2;
  }

  @ModifyReturnValue(method = "shouldSkipHandAnimationOnSwap", at = @At("RETURN"))
  public boolean test(boolean flag, ItemStack itemStack, ItemStack itemStack2) {
    AnimationsModule animationsModule = getAnimationsModule();
    return flag || animationsModule != null && animationsModule.test();
  }

  @Inject(method = "swingArm", at = @At("HEAD"), cancellable = true)
  public void render(
      float floatValue, MatrixStack matrixStack, int intValue, Arm arm, CallbackInfo callbackInfo) {
    AnimationsModule animationsModule = getAnimationsModule();
    if (animationsModule != null) {
      String text = (String) animationsModule.swingMotionSetting.getValue();
      if (!"Rotate".equals(text) && !"Static".equals(text)) {
        if ("Compact".equals(text)) {
          float value2 = animationsModule.computeFloat(floatValue);
          float floatValue2 = MathHelper.sin(MathHelper.sqrt(value2) * (float) Math.PI);
          float float2 = animationsModule.computeFloat2();
          matrixStack.translate(
              intValue * -0.1F * floatValue2 * float2, 0.055F * floatValue2 * float2, 0.0F);
          matrixStack.multiply(
              RotationAxis.POSITIVE_Z.rotationDegrees(intValue * -9.0F * floatValue2 * float2));
          callbackInfo.cancel();
        }
      } else {
        callbackInfo.cancel();
      }
    }
  }

  @ModifyExpressionValue(
      method = "updateHeldItems",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/network/ClientPlayerEntity;getHandEquippingProgress(F)F"))
  public float computeFloat2(float floatValue) {
    AnimationsModule animationsModule = getAnimationsModule();
    return animationsModule != null && animationsModule.test4() ? 1.0F : floatValue;
  }

  @Inject(method = "resetEquipProgress", at = @At("HEAD"), cancellable = true)
  public void apply(Hand hand, CallbackInfo callbackInfo) {
    AnimationsModule animationsModule = getAnimationsModule();
    if (animationsModule != null && animationsModule.test4()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "applyEatOrDrinkTransformation", at = @At("HEAD"), cancellable = true)
  public void render(
      MatrixStack matrixStack,
      float floatValue,
      Arm arm,
      ItemStack itemStack,
      PlayerEntity playerEntity,
      CallbackInfo callbackInfo) {
    AnimationsModule animationsModule = getAnimationsModule();
    if (animationsModule != null && animationsModule.test()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "applyEquipOffset", at = @At("TAIL"))
  public void render(
      MatrixStack matrixStack, Arm arm, float floatValue, CallbackInfo callbackInfo) {
    AnimationsModule animationsModule = getAnimationsModule();
    if (animationsModule != null) {
      int intValue = arm == Arm.RIGHT ? 1 : -1;
      matrixStack.translate(
          -intValue * animationsModule.centerPullSetting.floatValue(),
          animationsModule.liftSetting.floatValue(),
          animationsModule.depthSetting.floatValue());
      float value2 = animationsModule.handPitchSetting.floatValue();
      if (animationsModule.test()) {
        float floatValue2 = arm == Arm.RIGHT ? -1.0F : 1.0F;
        value2 += floatValue2 * animationsModule.computeFloat2(this.amount);
      }

      float value3 = animationsModule.handYawSetting.floatValue();
      float value4 = animationsModule.handRollSetting.floatValue();
      if (value3 != 0.0F) {
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(intValue * value3));
      }

      if (value2 != 0.0F) {
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(value2));
      }

      if (value4 != 0.0F) {
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(intValue * value4));
      }
    }
  }

  @Unique
  private static AnimationsModule getAnimationsModule() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (!(instance.player instanceof ClientPlayerEntity)) {
      return null;
    }

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null
            && moduleManager.animationsModule != null
            && moduleManager.animationsModule.isEnabled()
        ? moduleManager.animationsModule
        : null;
  }
}

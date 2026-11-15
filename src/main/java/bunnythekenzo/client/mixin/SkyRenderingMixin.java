package bunnythekenzo.client.mixin;

import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.MoonPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.render.SkyRenderer;

@Mixin(SkyRendering.class)
public class SkyRenderingMixin {
  @Inject(method = "renderTopSky", at = @At("HEAD"), cancellable = true)
  public void apply(int intValue, CallbackInfo callbackInfo) {
    if (SkyRenderer.test()) {
      SkyRenderer.update7();
      callbackInfo.cancel();
    }
  }

  @Inject(method = "renderGlowingSky", at = @At("HEAD"), cancellable = true)
  public void render(
      MatrixStack matrixStack, float floatValue, int intValue, CallbackInfo callbackInfo) {
    if (SkyRenderer.test()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "renderCelestialBodies", at = @At("HEAD"), cancellable = true)
  public void render(
      MatrixStack matrixStack,
      float floatValue,
      float floatValue2,
      float floatValue3,
      MoonPhase moonPhase,
      float floatValue4,
      float floatValue5,
      CallbackInfo callbackInfo) {
    if (SkyRenderer.test()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "renderSkyDark", at = @At("HEAD"), cancellable = true)
  public void apply(CallbackInfo callbackInfo) {
    if (SkyRenderer.test()) {
      callbackInfo.cancel();
    }
  }
}

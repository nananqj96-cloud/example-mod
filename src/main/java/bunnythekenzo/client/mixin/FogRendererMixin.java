package bunnythekenzo.client.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bunnythekenzo.client.render.SkyRenderer;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
  @Inject(method = "getFogColor", at = @At("RETURN"))
  public void render(
      Camera camera,
      float floatValue,
      ClientWorld clientWorld,
      int intValue,
      float floatValue2,
      CallbackInfoReturnable callbackInfoReturnable) {
    SkyRenderer.apply((Vector4f) callbackInfoReturnable.getReturnValue(), camera);
  }
}

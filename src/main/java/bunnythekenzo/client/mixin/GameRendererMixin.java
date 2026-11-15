package bunnythekenzo.client.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.render.AnimatedGlintTexture;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
  @Inject(method = "render(Lnet/minecraft/client/render/RenderTickCounter;Z)V", at = @At("HEAD"))
  public void apply(RenderTickCounter renderTickCounter, boolean flag, CallbackInfo callbackInfo) {
    AnimatedGlintTexture.tick();
  }
}

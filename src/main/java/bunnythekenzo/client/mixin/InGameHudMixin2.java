package bunnythekenzo.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.render.SpawnerLabelRenderer;

@Mixin(InGameHud.class)
public class InGameHudMixin2 {
  @Inject(
      method =
          "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V",
      at = @At("TAIL"))
  public void apply(
      DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
    SpawnerLabelRenderer.apply(drawContext);
  }
}

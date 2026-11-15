package bunnythekenzo.client.mixin;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EmoteBodyParts;
import bunnythekenzo.client.EmoteModelAccess;
import bunnythekenzo.client.EmoteModelGeometry;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin implements EmoteModelAccess {
  @Unique public EmoteModelGeometry emoteModelGeometry;

  @Override
  public EmoteModelGeometry getEmoteModelGeometry() {
    if (this.emoteModelGeometry == null) {
      this.emoteModelGeometry = new EmoteModelGeometry((BipedEntityModel) (Object) (this));
    }

    return this.emoteModelGeometry;
  }

  @Override
  public void applyArmPose(Arm arm, MatrixStack matrixStack) {
    if (this.emoteModelGeometry != null) {
      this.emoteModelGeometry.apply(arm, matrixStack);
    }
  }

  @Inject(
      method = "setAngles(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V",
      at = @At("HEAD"))
  public void apply(BipedEntityRenderState bipedEntityRenderState, CallbackInfo callbackInfo) {
    if (this.emoteModelGeometry != null) {
      this.emoteModelGeometry.update();
    }

    EmoteBodyParts.apply((BipedEntityModel) (Object) (this));
  }

  @Inject(
      method = "setAngles(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;)V",
      at = @At("TAIL"))
  public void apply2(BipedEntityRenderState bipedEntityRenderState, CallbackInfo callbackInfo) {
    if (bipedEntityRenderState instanceof PlayerEntityRenderState playerEntityRenderState
        && ClientInitializer.getEmoteCatalog() != null) {
      ClientInitializer.getEmoteCatalog()
          .apply((BipedEntityModel) (Object) (this), playerEntityRenderState);
    }
  }
}

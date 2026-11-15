package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.CameraChunkCulling;
import bunnythekenzo.client.module.FreecamModule;

@Mixin(Camera.class)
public abstract class CameraMixin {
  @Shadow
  public abstract void setPos(Vec3d vec3d);

  @Shadow
  public abstract void setRotation(float floatValue, float floatValue2);

  @Inject(method = "update", at = @At("TAIL"))
  public void render(
      World world,
      Entity entity,
      boolean flag,
      boolean flag2,
      float floatValue,
      CallbackInfo callbackInfo) {
    FreecamModule instance = FreecamModule.getInstance();
    if (instance != null && instance.test()) {
      this.setRotation(instance.computeFloat(floatValue), instance.computeFloat2(floatValue));
      this.setPos(instance.getVec3d(floatValue));
    }

    CameraChunkCulling.apply(MinecraftClient.getInstance());
  }
}

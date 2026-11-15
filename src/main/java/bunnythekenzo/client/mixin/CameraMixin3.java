package bunnythekenzo.client.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.EquipmentPreview;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(Camera.class)
public abstract class CameraMixin3 {
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
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.equipmentPreview.test3()) {
      EquipmentPreview equipmentPreview = fakeLootDropModule.equipmentPreview;
      Vec3d vec3d = equipmentPreview.getVec3d(floatValue).add(0.0, 1.2, 0.0);
      if (flag) {
        vec3d =
            vec3d.add(
                Vec3d.fromPolar(equipmentPreview.getAmount2(), equipmentPreview.getAmount())
                    .multiply(flag2 ? 4.0 : -4.0));
      }

      this.setRotation(
          equipmentPreview.getAmount() + (flag && flag2 ? 180 : 0),
          flag && flag2 ? -equipmentPreview.getAmount2() : equipmentPreview.getAmount2());
      this.setPos(vec3d);
    }
  }
}

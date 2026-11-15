package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.function.Predicate;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin2 {
  @WrapOperation(
      method =
          "getCrosshairTarget(Lnet/minecraft/entity/Entity;DDF)Lnet/minecraft/util/hit/HitResult;",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"))
  private static EntityHitResult getEntityHitResult(
      Entity entity,
      Vec3d vec3d,
      Vec3d vec3d2,
      Box box,
      Predicate predicate,
      double doubleValue,
      Operation operation) {
    EntityHitResult entityHitResult =
        (EntityHitResult) operation.call(entity, vec3d, vec3d2, box, predicate, doubleValue);
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null && moduleManager.hitBoxModule != null
        ? moduleManager.hitBoxModule.getEntityHitResult(
            entityHitResult, entity, vec3d, vec3d2, box, predicate, doubleValue)
        : entityHitResult;
  }
}

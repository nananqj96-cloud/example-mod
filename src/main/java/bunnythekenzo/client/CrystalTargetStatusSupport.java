package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class CrystalTargetStatusSupport {
  public static final double value = 144.0;

  public static boolean test(
      MinecraftClient minecraftClient, Vec3d vec3d, double doubleValue, BlockPos blockPos) {
    return getCrystalTargetStatus(minecraftClient, vec3d, doubleValue, blockPos, true)
        == CrystalTargetStatus.READY;
  }

  public static CrystalTargetStatus getCrystalTargetStatus(
      MinecraftClient minecraftClient,
      Vec3d vec3d,
      double doubleValue,
      BlockPos blockPos,
      boolean flag) {
    if (minecraftClient.world != null && minecraftClient.player != null) {
      if (flag && minecraftClient.world.getDifficulty() == Difficulty.PEACEFUL) {
        return CrystalTargetStatus.PEACEFUL;
      }

      boolean flag2 = false;
      boolean flag3 = false;
      boolean flag4 = false;
      boolean flag5 = false;

      for (PlayerEntity playerEntity : minecraftClient.world.getPlayers()) {
        if (playerEntity != minecraftClient.player
            && playerEntity.isAlive()
            && !playerEntity.isRemoved()
            && !playerEntity.isSpectator()) {
          flag2 = true;
          if (!(minecraftClient.player.squaredDistanceTo(playerEntity)
              > doubleValue * doubleValue)) {
            flag3 = true;
            if (!flag) {
              return CrystalTargetStatus.READY;
            }

            if (!(playerEntity.squaredDistanceTo(vec3d) >= 144.0)) {
              flag4 = true;
              if (!playerEntity.isCreative()
                  && !playerEntity.isInvulnerable()
                  && !playerEntity.getAbilities().invulnerable) {
                StatusEffectInstance statusEffect =
                    playerEntity.getStatusEffect(StatusEffects.RESISTANCE);
                if (statusEffect == null || statusEffect.getAmplifier() < 4) {
                  flag5 = true;
                  if (test(minecraftClient, vec3d, playerEntity, blockPos)) {
                    return CrystalTargetStatus.READY;
                  }
                }
              }
            }
          }
        }
      }

      return !flag2
          ? CrystalTargetStatus.NO_PLAYER
          : (!flag3
              ? CrystalTargetStatus.OUT_OF_RANGE
              : (!flag4
                  ? CrystalTargetStatus.OUT_OF_BLAST_RANGE
                  : (!flag5 ? CrystalTargetStatus.IMMUNE : CrystalTargetStatus.COVERED)));
    } else {
      return CrystalTargetStatus.NO_PLAYER;
    }
  }

  public static boolean test(
      MinecraftClient minecraftClient, Vec3d vec3d, PlayerEntity playerEntity, BlockPos blockPos) {
    Box boundingBox = playerEntity.getBoundingBox();
    double doubleValue = 1.0 / (boundingBox.getLengthX() * 2.0 + 1.0);
    double doubleValue2 = 1.0 / (boundingBox.getLengthY() * 2.0 + 1.0);
    double doubleValue3 = 1.0 / (boundingBox.getLengthZ() * 2.0 + 1.0);
    double doubleValue4 = (1.0 - Math.floor(1.0 / doubleValue) * doubleValue) / 2.0;
    double doubleValue5 = (1.0 - Math.floor(1.0 / doubleValue3) * doubleValue3) / 2.0;

    for (double doubleValue6 = 0.0; doubleValue6 <= 1.0; doubleValue6 += doubleValue) {
      for (double doubleValue7 = 0.0; doubleValue7 <= 1.0; doubleValue7 += doubleValue2) {
        for (double doubleValue8 = 0.0; doubleValue8 <= 1.0; doubleValue8 += doubleValue3) {
          Vec3d vec3d2 =
              new Vec3d(
                  MathHelper.lerp(doubleValue6, boundingBox.minX, boundingBox.maxX) + doubleValue4,
                  MathHelper.lerp(doubleValue7, boundingBox.minY, boundingBox.maxY),
                  MathHelper.lerp(doubleValue8, boundingBox.minZ, boundingBox.maxZ) + doubleValue5);
          if ((blockPos == null || VoxelShapes.fullCube().raycast(vec3d2, vec3d, blockPos) == null)
              && minecraftClient
                      .world
                      .raycast(
                          new RaycastContext(
                              vec3d2, vec3d, ShapeType.COLLIDER, FluidHandling.NONE, playerEntity))
                      .getType()
                  == Type.MISS) {
            return true;
          }
        }
      }
    }

    return false;
  }
}

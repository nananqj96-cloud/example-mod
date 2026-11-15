package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import bunnythekenzo.client.module.AimAssistModule;

public final class EntityAimAssistEngine implements AimAssistEngine {
  public final AimRotationController aimRotationController = new AimRotationController();
  public LivingEntity currentTarget;
  public static boolean enabled;

  @Override
  public void reset() {

    this.aimRotationController.reset();
    this.currentTarget = null;
    ClientInitializer.ensureInitialized();
  }

  @Override
  public double[] computeCorrection(
      AimAssistModule aimAssistModule,
      double doubleValue,
      double doubleValue2,
      double doubleValue3) {

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player == null || instance.world == null || instance.currentScreen != null) {
      this.reset();
      return null;
    } else if (!(Boolean) aimAssistModule.alwaysActiveSetting.getValue()
        && !instance.options.attackKey.isPressed()) {
      this.reset();
      return null;
    } else {
      Vec3d eyePos = instance.player.getEyePos();
      LivingEntity livingEntity2 = this.findTarget(aimAssistModule, instance, eyePos);
      if (livingEntity2 == null) {
        this.reset();
        return null;
      } else {
        this.currentTarget = livingEntity2;
        return this.aimRotationController.getValueArray(
            instance,
            this.getAimPoint(aimAssistModule, livingEntity2, eyePos),
            (Double) aimAssistModule.speedSetting.getValue(),
            (Double) aimAssistModule.smoothnessSetting.getValue(),
            (Boolean) aimAssistModule.verticalSetting.getValue(),
            doubleValue,
            doubleValue2,
            doubleValue3);
      }
    }
  }

  public LivingEntity findTarget(
      AimAssistModule aimAssistModule, MinecraftClient minecraftClient, Vec3d vec3d) {

    float value = aimAssistModule.rangeSetting.floatValue();
    if ((Boolean) aimAssistModule.stickyTargetSetting.getValue()
        && this.currentTarget != null
        && this.test(aimAssistModule, minecraftClient, this.currentTarget, value, vec3d)) {
      return this.currentTarget;
    }

    LivingEntity livingEntity2 = null;
    double doubleValue = (double) value * value;
    Box box = minecraftClient.player.getBoundingBox().expand(value);

    for (LivingEntity livingEntity3 :
        minecraftClient.world.getNonSpectatingEntities(LivingEntity.class, box)) {
      if (this.test(aimAssistModule, minecraftClient, livingEntity3, value, vec3d)) {
        double doubleValue2 =
            vec3d.squaredDistanceTo(
                livingEntity3.getEntityPos().add(0.0, livingEntity3.getHeight() * 0.5, 0.0));
        if (doubleValue2 <= doubleValue) {
          doubleValue = doubleValue2;
          livingEntity2 = livingEntity3;
        }
      }
    }

    return livingEntity2;
  }

  public boolean test(
      AimAssistModule aimAssistModule,
      MinecraftClient minecraftClient,
      LivingEntity livingEntity2,
      float floatValue,
      Vec3d vec3d) {
    if (livingEntity2 == minecraftClient.player
        || !livingEntity2.isAlive()
        || livingEntity2.isRemoved()) {
      return false;
    } else if (livingEntity2 instanceof ArmorStandEntity) {
      return false;
    } else if (!(Boolean) aimAssistModule.invisiblesSetting.getValue()
        && livingEntity2.isInvisible()) {
      return false;
    } else if (!this.test(aimAssistModule, livingEntity2)) {
      return false;
    } else if (minecraftClient.player.distanceTo(livingEntity2) > floatValue) {
      return false;
    } else {
      return aimAssistModule.fovSetting.floatValue() < 180.0F
              && this.computeFloat(aimAssistModule, minecraftClient, vec3d, livingEntity2)
                  > aimAssistModule.fovSetting.floatValue() * 0.5F
          ? false
          : !(Boolean) aimAssistModule.wallCheckSetting.getValue()
              || test(minecraftClient, livingEntity2);
    }
  }

  public boolean test(AimAssistModule aimAssistModule, Entity entity) {
    if (entity instanceof PlayerEntity) {
      return (Boolean) aimAssistModule.playersSetting.getValue();
    } else if (entity instanceof HostileEntity) {
      return (Boolean) aimAssistModule.hostilesSetting.getValue();
    } else {
      return entity instanceof AnimalEntity
          ? (Boolean) aimAssistModule.passiveSetting.getValue()
          : false;
    }
  }

  public float computeFloat(
      AimAssistModule aimAssistModule,
      MinecraftClient minecraftClient,
      Vec3d vec3d,
      LivingEntity livingEntity2) {
    float[] floatValues =
        getAmountArray(vec3d, this.getAimPoint(aimAssistModule, livingEntity2, vec3d));
    float floatValue =
        Math.abs(MathHelper.wrapDegrees(floatValues[0] - minecraftClient.player.getYaw()));
    float floatValue2 = Math.abs(floatValues[1] - minecraftClient.player.getPitch());
    return (float) Math.sqrt(floatValue * floatValue + floatValue2 * floatValue2);
  }

  public static boolean test(MinecraftClient minecraftClient, LivingEntity livingEntity2) {
    Vec3d eyePos = minecraftClient.player.getEyePos();
    Vec3d eyePos2 = livingEntity2.getEyePos();
    return minecraftClient
            .world
            .raycast(
                new RaycastContext(
                    eyePos,
                    eyePos2,
                    ShapeType.COLLIDER,
                    FluidHandling.NONE,
                    minecraftClient.player))
            .getType()
        == Type.MISS;
  }

  public Vec3d getAimPoint(
      AimAssistModule aimAssistModule, LivingEntity livingEntity2, Vec3d vec3d) {

    return switch ((String) aimAssistModule.targetSetting.getValue()) {
      case "Head" -> livingEntity2.getEyePos();
      case "Feet" -> livingEntity2.getEntityPos();
      case "Nearest" -> {
        Box boundingBox = livingEntity2.getBoundingBox();
        yield new Vec3d(
            MathHelper.clamp(vec3d.x, boundingBox.minX, boundingBox.maxX),
            MathHelper.clamp(vec3d.y, boundingBox.minY, boundingBox.maxY),
            MathHelper.clamp(vec3d.z, boundingBox.minZ, boundingBox.maxZ));
      }
      default -> livingEntity2.getEntityPos().add(0.0, livingEntity2.getHeight() * 0.5, 0.0);
    };
  }

  public static float[] getAmountArray(Vec3d vec3d, Vec3d vec3d2) {
    double doubleValue = vec3d2.x - vec3d.x;
    double doubleValue2 = vec3d2.y - vec3d.y;
    double doubleValue3 = vec3d2.z - vec3d.z;
    double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue3 * doubleValue3);
    float floatValue = (float) Math.toDegrees(Math.atan2(doubleValue3, doubleValue)) - 90.0F;
    float floatValue2 = (float) (-Math.toDegrees(Math.atan2(doubleValue2, doubleValue4)));
    return new float[] {floatValue, floatValue2};
  }

  public static void setEnabled(boolean enabled2) {
    enabled = enabled2;
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static boolean test() {
    boolean flag = isEnabled();
    return !flag;
  }

  static {
    setEnabled(false);
  }
}

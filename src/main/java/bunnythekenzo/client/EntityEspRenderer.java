package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.MobESPModule;
import bunnythekenzo.client.module.PlayerESPModule;

public final class EntityEspRenderer {
  public static void apply(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, PlayerESPModule playerESPModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    if (world != null && instance.player != null) {
      int integer = playerESPModule.colorSetting.getInteger();
      boolean flag = playerESPModule.styleSetting.isSelected("Glow");
      boolean flag2 = playerESPModule.styleSetting.isSelected("Box");
      float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
      boolean flag3 = (Boolean) playerESPModule.tracersSetting.getValue();

      for (AbstractClientPlayerEntity abstractClientPlayerEntity : world.getPlayers()) {
        if (abstractClientPlayerEntity != instance.player
            && abstractClientPlayerEntity.isAlive()
            && !abstractClientPlayerEntity.isSpectator()) {
          Box box = getBox(abstractClientPlayerEntity, tickProgress);
          apply(immediate, matrixStack, vec3d, box, integer, flag2, flag);
          if (flag3) {
            apply(box, integer);
          }
        }
      }

      VertexConsumerSupport4.apply(immediate);
    }
  }

  public static void apply(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, MobESPModule mobESPModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    if (world != null) {
      int integer = mobESPModule.hostileSetting.getInteger();
      int integer2 = mobESPModule.passiveSetting.getInteger();
      boolean flag = (Boolean) mobESPModule.passiveTooSetting.getValue();
      float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
      boolean flag2 = (Boolean) mobESPModule.tracersSetting.getValue();

      for (Entity entity : world.getEntities()) {
        if (entity instanceof MobEntity && entity.isAlive()) {
          boolean flag3 = entity instanceof Monster;
          if (flag3 || flag) {
            int intValue = flag3 ? integer : integer2;
            Box box = getBox(entity, tickProgress);
            apply(immediate, matrixStack, vec3d, box, intValue, false, false);
            if (flag2) {
              apply(box, intValue);
            }
          }
        }
      }

      VertexConsumerSupport4.apply(immediate);
    }
  }

  public static Box getBox(Entity entity, float floatValue) {
    Vec3d lerpedPos = entity.getLerpedPos(floatValue);
    return entity
        .getBoundingBox()
        .offset(
            lerpedPos.x - entity.getX(), lerpedPos.y - entity.getY(), lerpedPos.z - entity.getZ());
  }

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      Box box,
      int intValue,
      boolean flag,
      boolean flag2) {
    if (flag || flag2) {
      VertexConsumerSupport4.render(
          immediate,
          matrixStack,
          vec3d,
          box.minX,
          box.minY,
          box.minZ,
          box.maxX,
          box.maxY,
          box.maxZ,
          ArgbColors.withOpacity(intValue, flag2 ? 0.18F : 0.3F));
    }

    int intValue2 = flag2 ? 3 : (flag ? 2 : 1);

    for (int index = 0; index < intValue2; index++) {
      double doubleValue = index * 0.0025;
      VertexConsumerSupport4.render(
          immediate,
          matrixStack,
          vec3d,
          box.minX - doubleValue,
          box.minY - doubleValue,
          box.minZ - doubleValue,
          box.maxX + doubleValue,
          box.maxY + doubleValue,
          box.maxZ + doubleValue,
          intValue,
          1.0F);
    }
  }

  public static void apply(Box box, int intValue) {
    int intValue2 = intValue >>> 24;
    if (intValue2 == 0) {
      intValue |= -16777216;
    }

    WorldLabelRenderer.render(
        (box.minX + box.maxX) * 0.5,
        (box.minY + box.maxY) * 0.5,
        (box.minZ + box.maxZ) * 0.5,
        intValue,
        1.0F,
        0.0F);
  }
}

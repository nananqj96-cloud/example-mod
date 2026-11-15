package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.HitBoxModule;

public final class HitboxRenderer {
  public static final RenderLayer renderLayer =
      RenderLayer.of(
          "bunnythekenzo:hitbox_lines",
          RenderSetup.builder(
                  RenderPipelines.register(
                      RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET)
                          .withLocation("bunnythekenzo/pipeline/hitbox_lines")
                          .withDepthWrite(false)
                          .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                          .build()))
              .build());

  public static void render(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, HitBoxModule hitBoxModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (hitBoxModule.test(instance)
        && (Boolean) hitBoxModule.enableRenderSetting.getValue()
        && !instance.options.hudHidden) {
      float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
      double doubleValue = (Double) hitBoxModule.renderRangeSetting.getValue();
      VertexConsumer vertexConsumer = null;

      for (Entity entity :
          instance.world.getOtherEntities(
              instance.player,
              instance.player.getBoundingBox().expand(doubleValue),
              entity2 -> {
                return hitBoxModule.test(instance, entity2);
              })) {
        if (!(entity.getBoundingBox().squaredMagnitude(instance.player.getEyePos())
            > doubleValue * doubleValue)) {
          if (vertexConsumer == null) {
            vertexConsumer = immediate.getBuffer(renderLayer);
          }

          Vec3d vec3d2 =
              entity.getLerpedPos(tickProgress).subtract(entity.getEntityPos()).subtract(vec3d);
          if ((Boolean) hitBoxModule.showOriginalSetting.getValue()) {
            render(
                vertexConsumer,
                matrixStack.peek(),
                entity.getBoundingBox().expand(entity.getTargetingMargin()).offset(vec3d2),
                1895825407,
                1.0F);
          }

          int intValue =
              instance.targetedEntity == entity
                  ? hitBoxModule.targetColorSetting.getInteger()
                  : hitBoxModule.colorSetting.getInteger();
          render(
              vertexConsumer,
              matrixStack.peek(),
              hitBoxModule.getBox(entity).offset(vec3d2),
              intValue,
              hitBoxModule.lineWidthSetting.floatValue());
        }
      }

      if (vertexConsumer != null) {
        immediate.draw(renderLayer);
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer, Entry entry, Box box, int intValue, float floatValue) {
    for (int index = 0; index < 4; index++) {
      double doubleValue = (index & 1) == 0 ? box.minX : box.maxX;
      double doubleValue2 = (index & 1) == 0 ? box.minY : box.maxY;
      double doubleValue3 = (index & 2) == 0 ? box.minZ : box.maxZ;
      render(
          vertexConsumer,
          entry,
          box.minX,
          doubleValue2,
          doubleValue3,
          box.maxX,
          doubleValue2,
          doubleValue3,
          intValue,
          floatValue);
      render(
          vertexConsumer,
          entry,
          doubleValue,
          box.minY,
          doubleValue3,
          doubleValue,
          box.maxY,
          doubleValue3,
          intValue,
          floatValue);
      render(
          vertexConsumer,
          entry,
          doubleValue,
          (index & 2) == 0 ? box.minY : box.maxY,
          box.minZ,
          doubleValue,
          (index & 2) == 0 ? box.minY : box.maxY,
          box.maxZ,
          intValue,
          floatValue);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      double doubleValue6,
      int intValue,
      float floatValue) {
    Vec3d vec3d =
        new Vec3d(
                doubleValue4 - doubleValue,
                doubleValue5 - doubleValue2,
                doubleValue6 - doubleValue3)
            .normalize();
    vertexConsumer
        .vertex(entry, (float) doubleValue, (float) doubleValue2, (float) doubleValue3)
        .color(intValue)
        .normal(entry, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z)
        .lineWidth(floatValue);
    vertexConsumer
        .vertex(entry, (float) doubleValue4, (float) doubleValue5, (float) doubleValue6)
        .color(intValue)
        .normal(entry, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z)
        .lineWidth(floatValue);
  }
}

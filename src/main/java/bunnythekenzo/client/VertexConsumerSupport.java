package bunnythekenzo.client;

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
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap.Type;
import org.joml.Vector3f;

public final class VertexConsumerSupport {
  public static final RenderPipeline renderPipeline =
      RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
          .withLocation("bunnythekenzo/pipeline/flat_fill")
          .withCull(false)
          .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
          .build();
  public static final RenderLayer renderLayer =
      RenderLayer.of(
          "bunnythekenzo:flat_fill", RenderSetup.builder(renderPipeline).translucent().build());
  public static final RenderPipeline renderPipeline2 =
      RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET)
          .withLocation("bunnythekenzo/pipeline/flat_lines")
          .withDepthWrite(false)
          .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
          .build();
  public static final RenderLayer renderLayer2 =
      RenderLayer.of("bunnythekenzo:flat_lines", RenderSetup.builder(renderPipeline2).build());

  public static double computeDouble(double doubleValue, double doubleValue2, double doubleValue3) {
    ClientWorld world = MinecraftClient.getInstance().world;
    if (world == null) {
      return doubleValue;
    }

    int intValue = (int) Math.floor(doubleValue2);
    int intValue2 = (int) Math.floor(doubleValue3);
    int topY = world.getTopY(Type.WORLD_SURFACE, intValue, intValue2);
    if (topY <= world.getBottomY()) {
      return doubleValue;
    }

    BlockPos blockPos = new BlockPos(intValue, topY - 1, intValue2);
    return world.getFluidState(blockPos).isEmpty()
        ? doubleValue
        : Math.max(doubleValue, topY + 0.04);
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      int intValue) {
    VertexConsumer buffer = immediate.getBuffer(renderLayer);
    Entry entry = matrixStack.peek();
    float floatValue = (float) (doubleValue - vec3d.x);
    float floatValue2 = (float) (doubleValue2 - vec3d.z);
    float floatValue3 = (float) (doubleValue3 - vec3d.x);
    float floatValue4 = (float) (doubleValue4 - vec3d.z);
    float floatValue5 = (float) (doubleValue5 - vec3d.y);
    buffer.vertex(entry, floatValue, floatValue5, floatValue2).color(intValue);
    buffer.vertex(entry, floatValue, floatValue5, floatValue4).color(intValue);
    buffer.vertex(entry, floatValue3, floatValue5, floatValue4).color(intValue);
    buffer.vertex(entry, floatValue3, floatValue5, floatValue2).color(intValue);
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      int intValue,
      float floatValue) {
    VertexConsumer buffer = immediate.getBuffer(renderLayer2);
    Entry entry = matrixStack.peek();
    Vector3f vector3f =
        new Vector3f(
                (float) (doubleValue3 - doubleValue), 0.0F, (float) (doubleValue4 - doubleValue2))
            .normalize();
    buffer
        .vertex(
            entry,
            (float) (doubleValue - vec3d.x),
            (float) (doubleValue5 - vec3d.y),
            (float) (doubleValue2 - vec3d.z))
        .color(intValue)
        .normal(entry, vector3f)
        .lineWidth(floatValue);
    buffer
        .vertex(
            entry,
            (float) (doubleValue3 - vec3d.x),
            (float) (doubleValue5 - vec3d.y),
            (float) (doubleValue4 - vec3d.z))
        .color(intValue)
        .normal(entry, vector3f)
        .lineWidth(floatValue);
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      int intValue) {
    render3(
        immediate,
        matrixStack,
        vec3d,
        doubleValue,
        doubleValue2,
        doubleValue3,
        doubleValue4,
        intValue);
    int value = ArgbColors.withOpacity(intValue, 1.0F);
    render(
        immediate,
        matrixStack,
        vec3d,
        doubleValue - doubleValue4,
        doubleValue2,
        doubleValue,
        doubleValue2 - doubleValue4,
        doubleValue3,
        value,
        2.0F);
    render(
        immediate,
        matrixStack,
        vec3d,
        doubleValue,
        doubleValue2 - doubleValue4,
        doubleValue + doubleValue4,
        doubleValue2,
        doubleValue3,
        value,
        2.0F);
    render(
        immediate,
        matrixStack,
        vec3d,
        doubleValue + doubleValue4,
        doubleValue2,
        doubleValue,
        doubleValue2 + doubleValue4,
        doubleValue3,
        value,
        2.0F);
    render(
        immediate,
        matrixStack,
        vec3d,
        doubleValue,
        doubleValue2 + doubleValue4,
        doubleValue - doubleValue4,
        doubleValue2,
        doubleValue3,
        value,
        2.0F);
  }

  public static void render2(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      int intValue) {
    render3(
        immediate,
        matrixStack,
        vec3d,
        doubleValue,
        doubleValue2,
        doubleValue3,
        doubleValue4,
        intValue);
  }

  public static void render3(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      int intValue) {
    VertexConsumer buffer = immediate.getBuffer(renderLayer);
    Entry entry = matrixStack.peek();
    float floatValue = (float) (doubleValue3 - vec3d.y);
    buffer
        .vertex(
            entry,
            (float) (doubleValue - doubleValue4 - vec3d.x),
            floatValue,
            (float) (doubleValue2 - vec3d.z))
        .color(intValue);
    buffer
        .vertex(
            entry,
            (float) (doubleValue - vec3d.x),
            floatValue,
            (float) (doubleValue2 - doubleValue4 - vec3d.z))
        .color(intValue);
    buffer
        .vertex(
            entry,
            (float) (doubleValue + doubleValue4 - vec3d.x),
            floatValue,
            (float) (doubleValue2 - vec3d.z))
        .color(intValue);
    buffer
        .vertex(
            entry,
            (float) (doubleValue - vec3d.x),
            floatValue,
            (float) (doubleValue2 + doubleValue4 - vec3d.z))
        .color(intValue);
  }

  public static void apply(Immediate immediate) {
    immediate.draw(renderLayer);
    immediate.draw(renderLayer2);
  }
}

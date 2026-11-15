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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import bunnythekenzo.client.EmoteConfettiParticle;
import bunnythekenzo.client.EmoteConfettiSystem;

public final class EmoteConfettiRenderer {
  public static final RenderLayer renderLayer =
      RenderLayer.of(
          "bunnythekenzo:emote_confetti",
          RenderSetup.builder(
                  RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
                      .withLocation("bunnythekenzo/pipeline/emote_confetti")
                      .withCull(false)
                      .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                      .withDepthWrite(false)
                      .build())
              .translucent()
              .build());

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      EmoteConfettiSystem emoteConfettiSystem) {
    if (!emoteConfettiSystem.getList2().isEmpty()) {
      float tickProgress =
          MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(false);
      VertexConsumer buffer = immediate.getBuffer(renderLayer);
      Entry entry = matrixStack.peek();

      for (EmoteConfettiParticle emoteConfettiParticle :
          (java.lang.Iterable<EmoteConfettiParticle>)
              (java.lang.Iterable<?>) (emoteConfettiSystem.getList2())) {
        Vec3d vec3d2 =
            emoteConfettiParticle
                .vec3d
                .lerp(emoteConfettiParticle.vec3d2, tickProgress)
                .subtract(vec3d);
        float floatValue =
            MathHelper.lerp(
                tickProgress, emoteConfettiParticle.amount4, emoteConfettiParticle.amount5);
        float floatValue2 =
            MathHelper.lerp(
                tickProgress, emoteConfettiParticle.amount6, emoteConfettiParticle.amount7);
        Vector3f vector3f =
            new Vector3f(emoteConfettiParticle.amount, 0.0F, 0.0F)
                .rotateZ(floatValue)
                .rotateX(floatValue2)
                .rotateY(emoteConfettiParticle.amount3);
        Vector3f vector3f2 =
            new Vector3f(0.0F, emoteConfettiParticle.amount2, 0.0F)
                .rotateZ(floatValue)
                .rotateX(floatValue2)
                .rotateY(emoteConfettiParticle.amount3);
        Vector3f vector3f3 =
            new Vector3f(0.0F, 0.0F, emoteConfettiParticle.amount2 * 0.17F)
                .rotateZ(floatValue)
                .rotateX(floatValue2)
                .rotateY(emoteConfettiParticle.amount3);
        int intValue = (int) (emoteConfettiParticle.computeFloat(tickProgress) * 255.0F);
        int intValue2 = 222 + (int) (33.0 * Math.abs(Math.cos(floatValue2)));
        int intValue3 = intValue << 24 | intValue2 << 16 | intValue2 << 8 | intValue2;
        int intValue4 =
            intValue << 24 | intValue2 - 25 << 16 | intValue2 - 25 << 8 | intValue2 - 22;
        render(
            buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, -1.0F, -1.0F, 0.0F, intValue3);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, 1.0F, -1.0F, 0.0F, intValue3);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, 1.0F, 0.0F, 1.0F, intValue3);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, -1.0F, 0.0F, 1.0F, intValue3);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, -1.0F, 0.0F, 1.0F, intValue4);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, 1.0F, 0.0F, 1.0F, intValue4);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, 1.0F, 1.0F, 0.0F, intValue4);
        render(buffer, entry, vec3d2, vector3f, vector3f2, vector3f3, -1.0F, 1.0F, 0.0F, intValue4);
      }

      immediate.draw(renderLayer);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      Vector3f vector3f3,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    vertexConsumer
        .vertex(
            entry,
            (float) vec3d.x
                + vector3f.x * floatValue
                + vector3f2.x * floatValue2
                + vector3f3.x * floatValue3,
            (float) vec3d.y
                + vector3f.y * floatValue
                + vector3f2.y * floatValue2
                + vector3f3.y * floatValue3,
            (float) vec3d.z
                + vector3f.z * floatValue
                + vector3f2.z * floatValue2
                + vector3f3.z * floatValue3)
        .color(intValue);
  }
}

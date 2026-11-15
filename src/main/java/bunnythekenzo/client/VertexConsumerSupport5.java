package bunnythekenzo.client;

import java.util.Deque;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.Client67JumpCirclesModule;

public final class VertexConsumerSupport5 {
  public static final Identifier identifier =
      Identifier.of("bunnythekenzo", "textures/misc/67.png");
  public static final Identifier identifier2 =
      Identifier.of("bunnythekenzo", "textures/misc/ring.png");
  public static final float amount = 0.25F;
  public static final float amount2 = 0.4F;
  public static final float amount3 = 0.45F;
  public static final float amount4 = 10.0F;
  public static final int count = 16761566;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      Client67JumpCirclesModule client67JumpCirclesModule) {
    Deque deque = client67JumpCirclesModule.getDeque();
    if (!deque.isEmpty()) {
      long longValue = System.nanoTime();
      float floatValue = Math.max(0.1F, client67JumpCirclesModule.lifetimeSetting.floatValue());

      while (!deque.isEmpty()
          && ((Client67JumpCirclesSupport) deque.peekFirst()).computeFloat(longValue)
              > floatValue) {
        deque.removeFirst();
      }

      if (!deque.isEmpty()) {
        float value = client67JumpCirclesModule.sizeSetting.floatValue();
        int intValue = computeInt(client67JumpCirclesModule);
        VertexConsumer buffer =
            immediate.getBuffer(RenderLayers.entityTranslucentEmissive(identifier));

        for (Client67JumpCirclesSupport client67JumpCirclesSupport :
            (java.lang.Iterable<Client67JumpCirclesSupport>) (java.lang.Iterable<?>) (deque)) {
          render(
              matrixStack,
              buffer,
              client67JumpCirclesSupport,
              vec3d,
              longValue,
              floatValue,
              value,
              intValue);
        }

        if ((Boolean) client67JumpCirclesModule.shockwaveSetting.getValue()) {
          VertexConsumer buffer2 =
              immediate.getBuffer(RenderLayers.entityTranslucentEmissive(identifier2));

          for (Client67JumpCirclesSupport client67JumpCirclesSupport2 :
              (java.lang.Iterable<Client67JumpCirclesSupport>) (java.lang.Iterable<?>) (deque)) {
            render(
                matrixStack,
                buffer2,
                client67JumpCirclesSupport2,
                vec3d,
                longValue,
                value,
                intValue);
          }
        }
      }
    }
  }

  public static void render(
      MatrixStack matrixStack,
      VertexConsumer vertexConsumer,
      Client67JumpCirclesSupport client67JumpCirclesSupport,
      Vec3d vec3d,
      long longValue,
      float floatValue,
      float floatValue2,
      int intValue) {
    float value = client67JumpCirclesSupport.computeFloat(longValue);
    float floatValue3;
    float floatValue4;
    if (value < 0.25F) {
      float floatValue5 = value / 0.25F;
      floatValue3 = computeFloat4(floatValue5);
      floatValue4 = computeFloat2(Math.min(1.0F, floatValue5 * 2.0F));
    } else if (value > floatValue - 0.4F) {
      float floatValue6 = (value - (floatValue - 0.4F)) / 0.4F;
      floatValue3 = computeFloat(computeFloat2(floatValue6), 1.0F, 1.3F);
      floatValue4 = 1.0F - computeFloat3(floatValue6);
    } else {
      floatValue3 = 1.0F;
      floatValue4 = 1.0F;
    }

    float floatValue7 = 0.5F + 0.5F * MathHelper.sin(value * 10.0F);
    floatValue4 *= 0.82F + 0.18F * floatValue7;
    int intValue2 = computeInt(intValue, 16761566, floatValue7 * 0.35F);
    int intValue3 = computeInt(intValue2, floatValue4);
    matrixStack.push();
    matrixStack.translate(
        client67JumpCirclesSupport.value - vec3d.x,
        client67JumpCirclesSupport.value2 + client67JumpCirclesSupport.amount2 - vec3d.y,
        client67JumpCirclesSupport.value3 - vec3d.z);
    matrixStack.multiply(
        RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - client67JumpCirclesSupport.amount));
    render(matrixStack.peek(), vertexConsumer, 0.5F * floatValue2 * floatValue3, intValue3);
    matrixStack.pop();
  }

  public static void render(
      MatrixStack matrixStack,
      VertexConsumer vertexConsumer,
      Client67JumpCirclesSupport client67JumpCirclesSupport,
      Vec3d vec3d,
      long longValue,
      float floatValue,
      int intValue) {
    float value = client67JumpCirclesSupport.computeFloat(longValue);
    if (!(value >= 0.45F)) {
      float floatValue2 = value / 0.45F;
      float floatValue3 = computeFloat(computeFloat2(floatValue2), 0.35F, 2.2F) * floatValue;
      float floatValue4 = 0.85F * (1.0F - computeFloat3(floatValue2));
      int intValue2 = computeInt(intValue, floatValue4);
      matrixStack.push();
      matrixStack.translate(
          client67JumpCirclesSupport.value - vec3d.x,
          client67JumpCirclesSupport.value2
              + client67JumpCirclesSupport.amount2 * 0.5F
              + 0.004F
              - vec3d.y,
          client67JumpCirclesSupport.value3 - vec3d.z);
      render(matrixStack.peek(), vertexConsumer, floatValue3, intValue2);
      matrixStack.pop();
    }
  }

  public static int computeInt(Client67JumpCirclesModule client67JumpCirclesModule) {
    if ((Boolean) client67JumpCirclesModule.rainbowSetting.getValue()) {
      float floatValue = (float) (System.currentTimeMillis() % 4000L) / 4000.0F;
      return MathHelper.hsvToRgb(floatValue, 0.75F, 1.0F) & 16777215;
    } else {
      return client67JumpCirclesModule.computeInt();
    }
  }

  public static void render(
      Entry entry, VertexConsumer vertexConsumer, float floatValue, int intValue) {
    render(entry, vertexConsumer, -floatValue, -floatValue, 0.0F, 0.0F, intValue);
    render(entry, vertexConsumer, -floatValue, floatValue, 0.0F, 1.0F, intValue);
    render(entry, vertexConsumer, floatValue, floatValue, 1.0F, 1.0F, intValue);
    render(entry, vertexConsumer, floatValue, -floatValue, 1.0F, 0.0F, intValue);
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue) {
    vertexConsumer
        .vertex(entry, floatValue, 0.0F, floatValue2)
        .color(intValue)
        .texture(floatValue3, floatValue4)
        .overlay(OverlayTexture.DEFAULT_UV)
        .light(15728880)
        .normal(entry, 0.0F, 1.0F, 0.0F);
  }

  public static int computeInt(int intValue, float floatValue) {
    int intValue2 = (int) (computeFloat(floatValue) * 255.0F);
    return intValue2 << 24 | intValue & 16777215;
  }

  public static int computeInt(int intValue, int intValue2, float floatValue) {
    int intValue3 =
        (int)
            MathHelper.lerp(
                floatValue, (float) (intValue >> 16 & 0xFF), (float) (intValue2 >> 16 & 0xFF));
    int intValue4 =
        (int)
            MathHelper.lerp(
                floatValue, (float) (intValue >> 8 & 0xFF), (float) (intValue2 >> 8 & 0xFF));
    int intValue5 =
        (int) MathHelper.lerp(floatValue, (float) (intValue & 0xFF), (float) (intValue2 & 0xFF));
    return intValue3 << 16 | intValue4 << 8 | intValue5;
  }

  public static float computeFloat(float floatValue) {
    return floatValue < 0.0F ? 0.0F : (floatValue > 1.0F ? 1.0F : floatValue);
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue2 + (floatValue3 - floatValue2) * floatValue;
  }

  public static float computeFloat2(float floatValue) {
    floatValue = computeFloat(floatValue);
    float floatValue2 = 1.0F - floatValue;
    return 1.0F - floatValue2 * floatValue2 * floatValue2;
  }

  public static float computeFloat3(float floatValue) {
    floatValue = computeFloat(floatValue);
    return floatValue * floatValue;
  }

  public static float computeFloat4(float floatValue) {
    floatValue = computeFloat(floatValue);
    float floatValue2 = floatValue - 1.0F;
    return 1.0F + 3.2F * floatValue2 * floatValue2 * floatValue2 + 2.2F * floatValue2 * floatValue2;
  }
}

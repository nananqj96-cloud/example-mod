package bunnythekenzo.client;

import java.util.Deque;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import bunnythekenzo.client.module.HitParticlesModule;

public final class VertexConsumerSupport3 {
  public static final Identifier identifier =
      Identifier.of("bunnythekenzo", "textures/misc/67.png");
  public static final int count = 20;
  public static final float[] amountArray = new float[21];
  public static final float[] amountArray2 = new float[21];
  public static final float amount = 0.5F;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      HitParticlesModule hitParticlesModule) {
    Deque deque = hitParticlesModule.getDeque();
    Deque deque2 = hitParticlesModule.getDeque2();
    if (!deque.isEmpty() || !deque2.isEmpty()) {
      long longValue = System.nanoTime();

      while (!deque.isEmpty()
          && ((HitParticlesSupport2) deque.peekFirst()).computeFloat(longValue)
              > ((HitParticlesSupport2) deque.peekFirst()).amount8) {
        deque.removeFirst();
      }

      while (!deque2.isEmpty()
          && ((HitParticlesSupport) deque2.peekFirst()).computeFloat(longValue) > 0.5F) {
        deque2.removeFirst();
      }

      if (!deque.isEmpty() || !deque2.isEmpty()) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        Vector3f vector3f = new Vector3f(camera.getHorizontalPlane());
        Vector3f vector3f2 = new Vector3f();
        vector3f.cross(new Vector3f(0.0F, 1.0F, 0.0F), vector3f2);
        if (vector3f2.lengthSquared() < 1.0E-6F) {
          vector3f2.set(1.0F, 0.0F, 0.0F);
        }

        vector3f2.normalize();
        Vector3f vector3f3 = new Vector3f();
        vector3f2.cross(vector3f, vector3f3);
        vector3f3.normalize();
        float value = hitParticlesModule.computeFloat();
        Entry entry = matrixStack.peek();
        VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer2);

        for (HitParticlesSupport hitParticlesSupport :
            (java.lang.Iterable<HitParticlesSupport>) (java.lang.Iterable<?>) (deque2)) {
          apply(buffer, entry, vec3d, vector3f2, vector3f3, hitParticlesSupport, longValue);
        }

        boolean flag = false;

        for (HitParticlesSupport2 hitParticlesSupport2 :
            (java.lang.Iterable<HitParticlesSupport2>) (java.lang.Iterable<?>) (deque)) {
          float value2 = hitParticlesSupport2.computeFloat(longValue);
          if (!(value2 < 0.0F) && !(value2 > hitParticlesSupport2.amount8)) {
            if (hitParticlesSupport2.count2 == 2) {
              render(buffer, entry, vec3d, vector3f2, vector3f3, hitParticlesSupport2, value2);
            } else if (hitParticlesSupport2.count2 == 3) {
              flag = true;
            }
          }
        }

        VertexConsumer buffer2 = immediate.getBuffer(VertexConsumerSupport.renderLayer);

        for (HitParticlesSupport2 hitParticlesSupport22 :
            (java.lang.Iterable<HitParticlesSupport2>) (java.lang.Iterable<?>) (deque)) {
          float value3 = hitParticlesSupport22.computeFloat(longValue);
          if (!(value3 < 0.0F) && !(value3 > hitParticlesSupport22.amount8)) {
            if (hitParticlesSupport22.count2 == 1) {
              render2(
                  buffer2,
                  entry,
                  vec3d,
                  vector3f2,
                  vector3f3,
                  hitParticlesSupport22,
                  value3,
                  value);
            } else if (hitParticlesSupport22.count2 == 0) {
              render(
                  buffer2,
                  entry,
                  vec3d,
                  vector3f2,
                  vector3f3,
                  hitParticlesSupport22,
                  value3,
                  value);
            }
          }
        }

        VertexConsumerSupport.apply(immediate);
        if (flag) {
          VertexConsumer buffer3 =
              immediate.getBuffer(RenderLayers.entityTranslucentEmissive(identifier));

          for (HitParticlesSupport2 hitParticlesSupport23 :
              (java.lang.Iterable<HitParticlesSupport2>) (java.lang.Iterable<?>) (deque)) {
            if (hitParticlesSupport23.count2 == 3) {
              float value4 = hitParticlesSupport23.computeFloat(longValue);
              if (!(value4 < 0.0F) && !(value4 > hitParticlesSupport23.amount8)) {
                render(
                    buffer3,
                    entry,
                    vec3d,
                    vector3f2,
                    vector3f3,
                    vector3f,
                    hitParticlesSupport23,
                    value4);
              }
            }
          }
        }
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      HitParticlesSupport2 hitParticlesSupport2,
      float floatValue,
      float floatValue2) {
    float floatValue3 = floatValue / hitParticlesSupport2.amount8;
    float floatValue4 = computeFloat(floatValue3);
    if (!(floatValue4 <= 0.01F)) {
      float floatValue5 = (float) (hitParticlesSupport2.computeDouble(floatValue) - vec3d.x);
      float floatValue6 = (float) (hitParticlesSupport2.computeDouble2(floatValue) - vec3d.y);
      float floatValue7 = (float) (hitParticlesSupport2.computeDouble3(floatValue) - vec3d.z);
      float floatValue8 = hitParticlesSupport2.amount2 - hitParticlesSupport2.amount7 * floatValue;
      float floatValue9 =
          hitParticlesSupport2.amount * vector3f.x
              + floatValue8 * vector3f.y
              + hitParticlesSupport2.amount3 * vector3f.z;
      float floatValue10 =
          hitParticlesSupport2.amount * vector3f2.x
              + floatValue8 * vector3f2.y
              + hitParticlesSupport2.amount3 * vector3f2.z;
      float floatValue11 = MathHelper.sqrt(floatValue9 * floatValue9 + floatValue10 * floatValue10);
      float floatValue12;
      float floatValue13;
      if (floatValue11 > 1.0E-4F) {
        floatValue12 = floatValue9 / floatValue11;
        floatValue13 = floatValue10 / floatValue11;
      } else {
        floatValue12 = 0.0F;
        floatValue13 = 1.0F;
      }

      float floatValue14 = 0.35F + 0.65F * (1.0F - floatValue3);
      float floatValue15 = 0.28F * hitParticlesSupport2.amount4 * floatValue14;
      float floatValue16 = 0.055F * hitParticlesSupport2.amount4 * floatValue14;
      Vector3f vector3f3 =
          getVector3f(
              vector3f, vector3f2, floatValue12 * floatValue15, floatValue13 * floatValue15);
      Vector3f vector3f4 =
          getVector3f(
              vector3f, vector3f2, -floatValue13 * floatValue16, floatValue12 * floatValue16);
      int value =
          ArgbColors.withOpacity(
              ArgbColors.lighten(hitParticlesSupport2.count, 0.55F), floatValue4);
      render(
          vertexConsumer,
          entry,
          floatValue5,
          floatValue6,
          floatValue7,
          vector3f3,
          vector3f4,
          value);
      if (floatValue2 > 0.01F) {
        float floatValue17 =
            0.06F * hitParticlesSupport2.amount4 * floatValue14 * (1.0F + 0.6F * floatValue2);
        Vector3f vector3f5 = getVector3f(vector3f, vector3f2, floatValue17, floatValue17);
        Vector3f vector3f6 = getVector3f(vector3f, vector3f2, -floatValue17, floatValue17);
        int value2 =
            ArgbColors.withOpacity(hitParticlesSupport2.count, floatValue4 * 0.22F * floatValue2);
        render(
            vertexConsumer,
            entry,
            floatValue5,
            floatValue6,
            floatValue7,
            vector3f5,
            vector3f6,
            value2);
      }
    }
  }

  public static void render2(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      HitParticlesSupport2 hitParticlesSupport2,
      float floatValue,
      float floatValue2) {
    float floatValue3 = floatValue / hitParticlesSupport2.amount8;
    float floatValue4 = computeFloat(floatValue3);
    if (!(floatValue4 <= 0.01F)) {
      float floatValue5 = floatValue3 < 0.2F ? computeFloat5(floatValue3 / 0.2F) : 1.0F;
      float floatValue6 = 0.16F * hitParticlesSupport2.amount4 * floatValue5;
      float floatValue7 = (float) (hitParticlesSupport2.computeDouble(floatValue) - vec3d.x);
      float floatValue8 = (float) (hitParticlesSupport2.computeDouble2(floatValue) - vec3d.y);
      float floatValue9 = (float) (hitParticlesSupport2.computeDouble3(floatValue) - vec3d.z);
      float floatValue10 = 0.18F * MathHelper.sin(floatValue * 6.0F + hitParticlesSupport2.amount5);
      int value =
          ArgbColors.withOpacity(
              ArgbColors.lighten(hitParticlesSupport2.count, 0.25F), floatValue4);
      render(
          vertexConsumer,
          entry,
          vector3f,
          vector3f2,
          floatValue7,
          floatValue8,
          floatValue9,
          floatValue6,
          floatValue10,
          value);
      if (floatValue2 > 0.01F) {
        int value2 =
            ArgbColors.withOpacity(hitParticlesSupport2.count, floatValue4 * 0.3F * floatValue2);
        render(
            vertexConsumer,
            entry,
            vector3f,
            vector3f2,
            floatValue7,
            floatValue8,
            floatValue9,
            floatValue6 * (1.35F + 0.35F * floatValue2),
            floatValue10,
            value2);
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vector3f vector3f,
      Vector3f vector3f2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue) {
    for (int index = 0; index < 20; index++) {
      float floatValue6 = (amountArray[index] + floatValue5 * amountArray2[index]) * floatValue4;
      float floatValue7 = amountArray2[index] * floatValue4;
      float floatValue8 =
          (amountArray[index + 1] + floatValue5 * amountArray2[index + 1]) * floatValue4;
      float floatValue9 = amountArray2[index + 1] * floatValue4;
      render(vertexConsumer, entry, floatValue, floatValue2, floatValue3, intValue);
      render(
          vertexConsumer,
          entry,
          floatValue + vector3f.x * floatValue6 + vector3f2.x * floatValue7,
          floatValue2 + vector3f.y * floatValue6 + vector3f2.y * floatValue7,
          floatValue3 + vector3f.z * floatValue6 + vector3f2.z * floatValue7,
          intValue);
      render(
          vertexConsumer,
          entry,
          floatValue + vector3f.x * floatValue8 + vector3f2.x * floatValue9,
          floatValue2 + vector3f.y * floatValue8 + vector3f2.y * floatValue9,
          floatValue3 + vector3f.z * floatValue8 + vector3f2.z * floatValue9,
          intValue);
      render(vertexConsumer, entry, floatValue, floatValue2, floatValue3, intValue);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      HitParticlesSupport2 hitParticlesSupport2,
      float floatValue) {
    float floatValue2 = floatValue / hitParticlesSupport2.amount8;
    float floatValue3 = computeFloat(floatValue2);
    if (!(floatValue3 <= 0.01F)) {
      float floatValue4 =
          0.45F
              + 0.55F
                  * MathHelper.abs(
                      MathHelper.sin(floatValue * 42.0F + hitParticlesSupport2.amount5));
      floatValue3 *= floatValue4;
      float floatValue5 = (float) (hitParticlesSupport2.value - vec3d.x);
      float floatValue6 = (float) (hitParticlesSupport2.value2 - vec3d.y);
      float floatValue7 = (float) (hitParticlesSupport2.value3 - vec3d.z);
      float floatValue8 = (float) (hitParticlesSupport2.computeDouble(floatValue) - vec3d.x);
      float floatValue9 = (float) (hitParticlesSupport2.computeDouble2(floatValue) - vec3d.y);
      float floatValue10 = (float) (hitParticlesSupport2.computeDouble3(floatValue) - vec3d.z);
      float floatValue11 = floatValue8 - floatValue5;
      float floatValue12 = floatValue9 - floatValue6;
      float floatValue13 = floatValue10 - floatValue7;
      float floatValue14 =
          floatValue11 * vector3f.x + floatValue12 * vector3f.y + floatValue13 * vector3f.z;
      float floatValue15 =
          floatValue11 * vector3f2.x + floatValue12 * vector3f2.y + floatValue13 * vector3f2.z;
      float floatValue16 =
          MathHelper.sqrt(floatValue14 * floatValue14 + floatValue15 * floatValue15);
      float floatValue17;
      float floatValue18;
      if (floatValue16 > 1.0E-4F) {
        floatValue17 = -floatValue15 / floatValue16;
        floatValue18 = floatValue14 / floatValue16;
      } else {
        floatValue17 = 1.0F;
        floatValue18 = 0.0F;
      }

      float floatValue19 = 0.16F * hitParticlesSupport2.amount4;
      int value =
          ArgbColors.withOpacity(ArgbColors.lighten(hitParticlesSupport2.count, 0.6F), floatValue3);
      float floatValue20 = 2.4F * hitParticlesSupport2.amount4;
      float floatValue21 = floatValue5;
      float floatValue22 = floatValue6;
      float floatValue23 = floatValue7;

      for (int index = 1; index <= 4; index++) {
        float floatValue24 = index / 4.0F;
        float floatValue25 = MathHelper.sin(floatValue24 * (float) Math.PI);
        float floatValue26 =
            index == 4
                ? 0.0F
                : (computeFloat(hitParticlesSupport2, index) * 2.0F - 1.0F)
                    * floatValue19
                    * floatValue25;
        float floatValue27 =
            floatValue5
                + floatValue11 * floatValue24
                + (vector3f.x * floatValue17 + vector3f2.x * floatValue18) * floatValue26;
        float floatValue28 =
            floatValue6
                + floatValue12 * floatValue24
                + (vector3f.y * floatValue17 + vector3f2.y * floatValue18) * floatValue26;
        float floatValue29 =
            floatValue7
                + floatValue13 * floatValue24
                + (vector3f.z * floatValue17 + vector3f2.z * floatValue18) * floatValue26;
        render(
            vertexConsumer,
            entry,
            floatValue21,
            floatValue22,
            floatValue23,
            floatValue27,
            floatValue28,
            floatValue29,
            value,
            floatValue20);
        floatValue21 = floatValue27;
        floatValue22 = floatValue28;
        floatValue23 = floatValue29;
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      Vector3f vector3f3,
      HitParticlesSupport2 hitParticlesSupport2,
      float floatValue) {
    float floatValue2 = floatValue / hitParticlesSupport2.amount8;
    float floatValue3 = computeFloat(floatValue2);
    if (!(floatValue3 <= 0.01F)) {
      float floatValue4 = floatValue2 < 0.22F ? computeFloat5(floatValue2 / 0.22F) : 1.0F;
      float floatValue5 = 0.16F * hitParticlesSupport2.amount4 * floatValue4;
      float floatValue6 =
          hitParticlesSupport2.amount5 + hitParticlesSupport2.amount6 * floatValue * 0.5F;
      float floatValue7 = MathHelper.cos(floatValue6);
      float floatValue8 = MathHelper.sin(floatValue6);
      Vector3f vector3f4 =
          getVector3f(vector3f, vector3f2, floatValue7 * floatValue5, floatValue8 * floatValue5);
      Vector3f vector3f5 =
          getVector3f(vector3f, vector3f2, -floatValue8 * floatValue5, floatValue7 * floatValue5);
      float floatValue9 = (float) (hitParticlesSupport2.computeDouble(floatValue) - vec3d.x);
      float floatValue10 = (float) (hitParticlesSupport2.computeDouble2(floatValue) - vec3d.y);
      float floatValue11 = (float) (hitParticlesSupport2.computeDouble3(floatValue) - vec3d.z);
      int value = ArgbColors.withOpacity(hitParticlesSupport2.count, floatValue3);
      float floatValue12 = -vector3f3.x;
      float floatValue13 = -vector3f3.y;
      float floatValue14 = -vector3f3.z;
      render(
          vertexConsumer,
          entry,
          floatValue9 - vector3f4.x - vector3f5.x,
          floatValue10 - vector3f4.y - vector3f5.y,
          floatValue11 - vector3f4.z - vector3f5.z,
          0.0F,
          0.0F,
          value,
          floatValue12,
          floatValue13,
          floatValue14);
      render(
          vertexConsumer,
          entry,
          floatValue9 - vector3f4.x + vector3f5.x,
          floatValue10 - vector3f4.y + vector3f5.y,
          floatValue11 - vector3f4.z + vector3f5.z,
          0.0F,
          1.0F,
          value,
          floatValue12,
          floatValue13,
          floatValue14);
      render(
          vertexConsumer,
          entry,
          floatValue9 + vector3f4.x + vector3f5.x,
          floatValue10 + vector3f4.y + vector3f5.y,
          floatValue11 + vector3f4.z + vector3f5.z,
          1.0F,
          1.0F,
          value,
          floatValue12,
          floatValue13,
          floatValue14);
      render(
          vertexConsumer,
          entry,
          floatValue9 + vector3f4.x - vector3f5.x,
          floatValue10 + vector3f4.y - vector3f5.y,
          floatValue11 + vector3f4.z - vector3f5.z,
          1.0F,
          0.0F,
          value,
          floatValue12,
          floatValue13,
          floatValue14);
    }
  }

  public static void apply(
      VertexConsumer vertexConsumer,
      Entry entry,
      Vec3d vec3d,
      Vector3f vector3f,
      Vector3f vector3f2,
      HitParticlesSupport hitParticlesSupport,
      long longValue) {
    float value = hitParticlesSupport.computeFloat(longValue);
    if (!(value < 0.0F) && !(value >= 0.5F)) {
      float floatValue = value / 0.5F;
      float floatValue2 = 0.15F + computeFloat4(floatValue) * 1.15F;
      float floatValue3 = 0.8F * (1.0F - computeFloat3(floatValue));
      int value2 =
          ArgbColors.withOpacity(ArgbColors.lighten(hitParticlesSupport.count, 0.2F), floatValue3);
      float floatValue4 = (float) (hitParticlesSupport.value - vec3d.x);
      float floatValue5 = (float) (hitParticlesSupport.value2 - vec3d.y);
      float floatValue6 = (float) (hitParticlesSupport.value3 - vec3d.z);
      float floatValue7 = 0.0F;
      float floatValue8 = 0.0F;
      float floatValue9 = 0.0F;

      for (int index = 0; index <= 28; index++) {
        float floatValue10 = index / 28.0F * (float) (Math.PI * 2);
        float floatValue11 = MathHelper.cos(floatValue10) * floatValue2;
        float floatValue12 = MathHelper.sin(floatValue10) * floatValue2;
        float floatValue13 = floatValue4 + vector3f.x * floatValue11 + vector3f2.x * floatValue12;
        float floatValue14 = floatValue5 + vector3f.y * floatValue11 + vector3f2.y * floatValue12;
        float floatValue15 = floatValue6 + vector3f.z * floatValue11 + vector3f2.z * floatValue12;
        if (index > 0) {
          render(
              vertexConsumer,
              entry,
              floatValue7,
              floatValue8,
              floatValue9,
              floatValue13,
              floatValue14,
              floatValue15,
              value2,
              2.2F);
        }

        floatValue7 = floatValue13;
        floatValue8 = floatValue14;
        floatValue9 = floatValue15;
      }
    }
  }

  public static Vector3f getVector3f(
      Vector3f vector3f, Vector3f vector3f2, float floatValue, float floatValue2) {
    return new Vector3f(
        vector3f.x * floatValue + vector3f2.x * floatValue2,
        vector3f.y * floatValue + vector3f2.y * floatValue2,
        vector3f.z * floatValue + vector3f2.z * floatValue2);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      Vector3f vector3f,
      Vector3f vector3f2,
      int intValue) {
    render(
        vertexConsumer,
        entry,
        floatValue - vector3f.x - vector3f2.x,
        floatValue2 - vector3f.y - vector3f2.y,
        floatValue3 - vector3f.z - vector3f2.z,
        intValue);
    render(
        vertexConsumer,
        entry,
        floatValue + vector3f.x - vector3f2.x,
        floatValue2 + vector3f.y - vector3f2.y,
        floatValue3 + vector3f.z - vector3f2.z,
        intValue);
    render(
        vertexConsumer,
        entry,
        floatValue + vector3f.x + vector3f2.x,
        floatValue2 + vector3f.y + vector3f2.y,
        floatValue3 + vector3f.z + vector3f2.z,
        intValue);
    render(
        vertexConsumer,
        entry,
        floatValue - vector3f.x + vector3f2.x,
        floatValue2 - vector3f.y + vector3f2.y,
        floatValue3 - vector3f.z + vector3f2.z,
        intValue);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      float floatValue7) {
    Vector3f vector3f =
        new Vector3f(
            floatValue4 - floatValue, floatValue5 - floatValue2, floatValue6 - floatValue3);
    if (vector3f.lengthSquared() > 1.0E-9F) {
      vector3f.normalize();
    } else {
      vector3f.set(0.0F, 1.0F, 0.0F);
    }

    vertexConsumer
        .vertex(entry, floatValue, floatValue2, floatValue3)
        .color(intValue)
        .normal(entry, vector3f)
        .lineWidth(floatValue7);
    vertexConsumer
        .vertex(entry, floatValue4, floatValue5, floatValue6)
        .color(intValue)
        .normal(entry, vector3f)
        .lineWidth(floatValue7);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      float floatValue6,
      float floatValue7,
      float floatValue8) {
    vertexConsumer
        .vertex(entry, floatValue, floatValue2, floatValue3)
        .color(intValue)
        .texture(floatValue4, floatValue5)
        .overlay(OverlayTexture.DEFAULT_UV)
        .light(15728880)
        .normal(entry, floatValue6, floatValue7, floatValue8);
  }

  public static float computeFloat(HitParticlesSupport2 hitParticlesSupport2, int intValue) {
    float floatValue =
        MathHelper.sin(
                (float)
                    (hitParticlesSupport2.value * 12.9898
                        + hitParticlesSupport2.value3 * 78.233
                        + hitParticlesSupport2.amount5 * 3.17
                        + intValue * 43.123))
            * 43758.547F;
    return floatValue - MathHelper.floor(floatValue);
  }

  public static float computeFloat(float floatValue) {
    floatValue = computeFloat2(floatValue);
    return floatValue < 0.12F
        ? floatValue / 0.12F
        : 1.0F - computeFloat3((floatValue - 0.12F) / 0.88F);
  }

  public static float computeFloat2(float floatValue) {
    return floatValue < 0.0F ? 0.0F : (floatValue > 1.0F ? 1.0F : floatValue);
  }

  public static float computeFloat3(float floatValue) {
    floatValue = computeFloat2(floatValue);
    return floatValue * floatValue;
  }

  public static float computeFloat4(float floatValue) {
    floatValue = computeFloat2(floatValue);
    float floatValue2 = 1.0F - floatValue;
    return 1.0F - floatValue2 * floatValue2 * floatValue2;
  }

  public static float computeFloat5(float floatValue) {
    floatValue = computeFloat2(floatValue);
    float floatValue2 = floatValue - 1.0F;
    return 1.0F + 3.4F * floatValue2 * floatValue2 * floatValue2 + 2.4F * floatValue2 * floatValue2;
  }

  static {
    for (int index = 0; index <= 20; index++) {
      double doubleValue = index / 20.0 * Math.PI * 2.0;
      double doubleValue2 = 16.0 * Math.pow(Math.sin(doubleValue), 3.0);
      double doubleValue3 =
          13.0 * Math.cos(doubleValue)
              - 5.0 * Math.cos(2.0 * doubleValue)
              - 2.0 * Math.cos(3.0 * doubleValue)
              - Math.cos(4.0 * doubleValue);
      amountArray[index] = (float) (doubleValue2 / 17.0);
      amountArray2[index] = (float) (doubleValue3 / 17.0);
    }
  }
}

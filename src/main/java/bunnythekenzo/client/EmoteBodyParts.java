package bunnythekenzo.client;

import java.util.EnumSet;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.model.ModelPart.Quad;
import net.minecraft.client.model.ModelPart.Vertex;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public final class EmoteBodyParts {
  public static final String string = "bunnythekenzo_emote_upper";
  public static final String string2 = "bunnythekenzo_emote_lower";
  public static final Map map = new WeakHashMap();
  public final EmoteBending emoteBending;
  public final EmoteBending emoteBending2;
  public final EmoteBending emoteBending3;
  public final EmoteBending emoteBending4;

  public EmoteBodyParts(BipedEntityModel bipedEntityModel) {
    this.emoteBending = new EmoteBending(bipedEntityModel.rightArm, 4.0F);
    this.emoteBending2 = new EmoteBending(bipedEntityModel.leftArm, 4.0F);
    this.emoteBending3 = new EmoteBending(bipedEntityModel.rightLeg, 6.0F);
    this.emoteBending4 = new EmoteBending(bipedEntityModel.leftLeg, 6.0F);
  }

  public static void render(
      BipedEntityModel bipedEntityModel,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    render(bipedEntityModel, floatValue, floatValue2, floatValue3, floatValue4, 0.0F, 0.0F);
  }

  public static void render(
      BipedEntityModel bipedEntityModel,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    EmoteBodyParts emoteBodyParts =
        (bunnythekenzo.client.EmoteBodyParts)
            (map.computeIfAbsent(
                bipedEntityModel,
                (referenceArgument6_0) ->
                    new bunnythekenzo.client.EmoteBodyParts(
                        (net.minecraft.client.render.entity.model.BipedEntityModel)
                            referenceArgument6_0)));
    emoteBodyParts.emoteBending.bend(floatValue, floatValue5);
    emoteBodyParts.emoteBending2.bend(floatValue2, floatValue6);
    emoteBodyParts.emoteBending3.bend(floatValue3, 0.0F);
    emoteBodyParts.emoteBending4.bend(floatValue4, 0.0F);
  }

  public static void apply(BipedEntityModel bipedEntityModel, boolean flag) {
    if (bipedEntityModel instanceof PlayerEntityModel) {
      EmoteBodyParts emoteBodyParts = (EmoteBodyParts) map.get(bipedEntityModel);
      if (emoteBodyParts != null) {
        emoteBodyParts.emoteBending.apply(flag);
      }
    }
  }

  public static void apply(BipedEntityModel bipedEntityModel) {
    EmoteBodyParts emoteBodyParts = (EmoteBodyParts) map.get(bipedEntityModel);
    if (emoteBodyParts != null) {
      emoteBodyParts.emoteBending.update();
      emoteBodyParts.emoteBending2.update();
      emoteBodyParts.emoteBending3.update();
      emoteBodyParts.emoteBending4.update();
    }
  }

  public static void apply(BipedEntityModel bipedEntityModel, Arm arm, MatrixStack matrixStack) {
    EmoteBodyParts emoteBodyParts = (EmoteBodyParts) map.get(bipedEntityModel);
    if (emoteBodyParts != null) {
      EmoteBending emoteBending5 =
          arm == Arm.RIGHT ? emoteBodyParts.emoteBending : emoteBodyParts.emoteBending2;
      if (emoteBending5.enabled) {
        matrixStack.translate(0.0F, emoteBending5.amount / 16.0F, 0.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(emoteBending5.amount3));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotation(emoteBending5.amount2));
        matrixStack.translate(0.0F, -emoteBending5.amount / 16.0F, 0.0F);
      }
    }
  }

  public static Cuboid getCuboid(Cuboid cuboid) {
    for (int index = 0; index < cuboid.sides.length; index++) {
      Quad quad = cuboid.sides[index];
      Vertex[] vertexArray = new Vertex[quad.vertices().length];

      for (int index2 = 0; index2 < vertexArray.length; index2++) {
        Vertex vertex = quad.vertices()[index2];
        vertexArray[index2] =
            new Vertex(
                vertex.x(),
                vertex.y(),
                vertex.z(),
                (44.0F + (vertex.u() * 64.0F - 44.0F) * 0.35F) / 64.0F,
                (28.0F + (vertex.v() * 64.0F - 28.0F) * 0.55F) / 64.0F);
      }

      cuboid.sides[index] = new Quad(vertexArray, quad.direction());
    }

    return cuboid;
  }

  public static Cuboid getCuboid(Cuboid cuboid, float floatValue, boolean flag) {
    float POSITIVE_INFINITY = Float.POSITIVE_INFINITY;
    float NEGATIVE_INFINITY = Float.NEGATIVE_INFINITY;

    for (Quad quad : cuboid.sides) {
      for (Vertex vertex : quad.vertices()) {
        POSITIVE_INFINITY = Math.min(POSITIVE_INFINITY, vertex.y());
        NEGATIVE_INFINITY = Math.max(NEGATIVE_INFINITY, vertex.y());
      }
    }

    if (flag ? !(NEGATIVE_INFINITY <= floatValue) : !(POSITIVE_INFINITY >= floatValue)) {
      float floatValue2 = flag ? Math.max(POSITIVE_INFINITY, floatValue) : POSITIVE_INFINITY;
      float floatValue3 = flag ? NEGATIVE_INFINITY : Math.min(NEGATIVE_INFINITY, floatValue);
      float floatValue4 = flag ? floatValue : 0.0F;
      EmoteBodyPartsSupport2 emoteBodyPartsSupport2 =
          new EmoteBodyPartsSupport2(
              0,
              0,
              cuboid.minX,
              floatValue2 - floatValue4,
              cuboid.minZ,
              cuboid.maxX - cuboid.minX,
              floatValue3 - floatValue2,
              cuboid.maxZ - cuboid.minZ,
              0.0F,
              0.0F,
              0.0F,
              false,
              64.0F,
              64.0F,
              EnumSet.allOf(Direction.class));

      for (int index = 0; index < cuboid.sides.length; index++) {
        Quad quad2 = cuboid.sides[index];
        Vertex[] vertexArray = new Vertex[quad2.vertices().length];

        for (int index2 = 0; index2 < vertexArray.length; index2++) {
          Vertex vertex2 = quad2.vertices()[index2];
          float floatValue5 = Math.clamp(vertex2.y(), floatValue2, floatValue3);
          float floatValue6 = vertex2.u();
          float floatValue7 = vertex2.v();
          if (Math.abs(floatValue5 - vertex2.y()) > 1.0E-4F
              && Math.abs(quad2.direction().y()) < 0.5F) {
            for (Vertex vertex3 : quad2.vertices()) {
              if (Math.abs(vertex3.x() - vertex2.x()) < 1.0E-4F
                  && Math.abs(vertex3.z() - vertex2.z()) < 1.0E-4F
                  && Math.abs(vertex3.y() - vertex2.y()) > 1.0E-4F) {
                float floatValue8 = (floatValue5 - vertex2.y()) / (vertex3.y() - vertex2.y());
                floatValue6 += (vertex3.u() - vertex2.u()) * floatValue8;
                floatValue7 += (vertex3.v() - vertex2.v()) * floatValue8;
                break;
              }
            }
          }

          vertexArray[index2] =
              new Vertex(
                  vertex2.x(), floatValue5 - floatValue4, vertex2.z(), floatValue6, floatValue7);
        }

        emoteBodyPartsSupport2.sides[index] = new Quad(vertexArray, quad2.direction());
      }

      return emoteBodyPartsSupport2;
    } else {
      return null;
    }
  }
}

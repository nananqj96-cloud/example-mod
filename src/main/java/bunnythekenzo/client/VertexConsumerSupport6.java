package bunnythekenzo.client;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public final class VertexConsumerSupport6 {
  public final Map map = new LinkedHashMap();
  public final Vector3f vector3f = new Vector3f();
  public final Vector3f vector3f2 = new Vector3f();

  public void render(
      MatrixStack matrixStack,
      Identifier identifier,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue) {
    this.cuboid(
        matrixStack,
        identifier,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        intValue,
        null);
  }

  public void render(
      MatrixStack matrixStack,
      Identifier identifier,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5) {
    this.cuboid(
        matrixStack,
        identifier,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        -1,
        new float[][] {
          getAmountArray(intValue + intValue5, intValue2 + intValue5, intValue3, intValue4),
          getAmountArray(
              intValue + intValue5 * 2 + intValue3, intValue2 + intValue5, intValue3, intValue4),
          getAmountArray(intValue, intValue2 + intValue5, intValue5, intValue4),
          getAmountArray(
              intValue + intValue5 + intValue3, intValue2 + intValue5, intValue5, intValue4),
          getAmountArray(intValue + intValue5, intValue2, intValue3, intValue5),
          getAmountArray(intValue + intValue5 + intValue3, intValue2, intValue3, intValue5)
        });
  }

  public static float[] getAmountArray(int intValue, int intValue2, int intValue3, int intValue4) {
    return new float[] {
      intValue / 64.0F,
      intValue2 / 64.0F,
      (intValue + intValue3) / 64.0F,
      (intValue2 + intValue4) / 64.0F
    };
  }

  public void cuboid(
      MatrixStack matrixStack,
      Identifier identifier,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      float[][] floatValuesArray) {
    VertexConsumerSupport7 vertexConsumerSupport7 =
        (bunnythekenzo.client.VertexConsumerSupport7)
            (this.map.computeIfAbsent(
                identifier,
                value -> {
                  return new VertexConsumerSupport7();
                }));
    float floatValue7 = floatValue + floatValue4;
    float floatValue8 = floatValue2 + floatValue5;
    float floatValue9 = floatValue3 + floatValue6;
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        0,
        0.0F,
        0.0F,
        -1.0F,
        new float[] {
          floatValue7,
          floatValue2,
          floatValue3,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue,
          floatValue8,
          floatValue3,
          floatValue7,
          floatValue8,
          floatValue3
        });
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        1,
        0.0F,
        0.0F,
        1.0F,
        new float[] {
          floatValue,
          floatValue2,
          floatValue9,
          floatValue7,
          floatValue2,
          floatValue9,
          floatValue7,
          floatValue8,
          floatValue9,
          floatValue,
          floatValue8,
          floatValue9
        });
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        2,
        -1.0F,
        0.0F,
        0.0F,
        new float[] {
          floatValue,
          floatValue2,
          floatValue3,
          floatValue,
          floatValue2,
          floatValue9,
          floatValue,
          floatValue8,
          floatValue9,
          floatValue,
          floatValue8,
          floatValue3
        });
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        3,
        1.0F,
        0.0F,
        0.0F,
        new float[] {
          floatValue7,
          floatValue2,
          floatValue9,
          floatValue7,
          floatValue2,
          floatValue3,
          floatValue7,
          floatValue8,
          floatValue3,
          floatValue7,
          floatValue8,
          floatValue9
        });
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        4,
        0.0F,
        -1.0F,
        0.0F,
        new float[] {
          floatValue,
          floatValue2,
          floatValue3,
          floatValue7,
          floatValue2,
          floatValue3,
          floatValue7,
          floatValue2,
          floatValue9,
          floatValue,
          floatValue2,
          floatValue9
        });
    this.render(
        vertexConsumerSupport7,
        matrixStack,
        intValue,
        floatValuesArray,
        5,
        0.0F,
        1.0F,
        0.0F,
        new float[] {
          floatValue,
          floatValue8,
          floatValue9,
          floatValue7,
          floatValue8,
          floatValue9,
          floatValue7,
          floatValue8,
          floatValue3,
          floatValue,
          floatValue8,
          floatValue3
        });
  }

  public void render(
      VertexConsumerSupport7 vertexConsumerSupport7,
      MatrixStack matrixStack,
      int intValue,
      float[][] floatValuesArray,
      int index,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float[] floatValues) {
    float[] floatValues2 = floatValuesArray == null ? null : floatValuesArray[index];
    float floatValue4 = floatValues2 == null ? 0.0F : floatValues2[0];
    float floatValue5 = floatValues2 == null ? 0.0F : floatValues2[1];
    float floatValue6 = floatValues2 == null ? 1.0F : floatValues2[2];
    float floatValue7 = floatValues2 == null ? 1.0F : floatValues2[3];
    matrixStack
        .peek()
        .getNormalMatrix()
        .transform(this.vector3f2.set(floatValue, floatValue2, floatValue3))
        .normalize();

    for (int index2 = 0; index2 < 4; index2++) {
      matrixStack
          .peek()
          .getPositionMatrix()
          .transformPosition(
              this.vector3f.set(
                  floatValues[index2 * 3],
                  floatValues[index2 * 3 + 1],
                  floatValues[index2 * 3 + 2]));
      vertexConsumerSupport7.render(
          this.vector3f,
          index2 != 0 && index2 != 3 ? floatValue6 : floatValue4,
          index2 < 2 ? floatValue5 : floatValue7,
          this.vector3f2,
          intValue);
    }
  }

  public void submit(
      MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int intValue) {
    this.map.forEach(
        (value, value2) -> {
          orderedRenderCommandQueue.submitCustom(
              matrixStack,
              RenderLayers.entityCutoutNoCull((net.minecraft.util.Identifier) (value)),
              (entry, vertexConsumer) -> {
                ((bunnythekenzo.client.VertexConsumerSupport7) (value2))
                    .apply(entry, vertexConsumer, intValue);
              });
        });
  }
}

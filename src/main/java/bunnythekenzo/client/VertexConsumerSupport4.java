package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.Vec3d;

public final class VertexConsumerSupport4 {
  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      double doubleValue6,
      int intValue,
      float floatValue) {
    int intValue2 = intValue >>> 24;
    if (intValue2 != 0) {
      VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer2);
      Entry entry = matrixStack.peek();
      float floatValue2 = (float) (doubleValue - vec3d.x);
      float floatValue3 = (float) (doubleValue2 - vec3d.y);
      float floatValue4 = (float) (doubleValue3 - vec3d.z);
      float floatValue5 = (float) (doubleValue4 - vec3d.x);
      float floatValue6 = (float) (doubleValue5 - vec3d.y);
      float floatValue7 = (float) (doubleValue6 - vec3d.z);
      render(
          buffer,
          entry,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue6,
          floatValue7,
          intValue,
          floatValue);
    }
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
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue2,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue2,
        floatValue6,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue2,
        floatValue6,
        floatValue,
        floatValue2,
        floatValue6,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue2,
        floatValue6,
        floatValue,
        floatValue2,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue5,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue5,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue,
        floatValue5,
        floatValue6,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue5,
        floatValue6,
        floatValue,
        floatValue5,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue,
        floatValue5,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue3,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue4,
        floatValue2,
        floatValue6,
        floatValue4,
        floatValue5,
        floatValue6,
        intValue,
        floatValue7);
    render2(
        vertexConsumer,
        entry,
        floatValue,
        floatValue2,
        floatValue6,
        floatValue,
        floatValue5,
        floatValue6,
        intValue,
        floatValue7);
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
      double doubleValue6,
      int intValue) {
    if (intValue >>> 24 != 0) {
      VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer);
      Entry entry = matrixStack.peek();
      float floatValue = (float) (doubleValue - vec3d.x);
      float floatValue2 = (float) (doubleValue2 - vec3d.y);
      float floatValue3 = (float) (doubleValue3 - vec3d.z);
      float floatValue4 = (float) (doubleValue4 - vec3d.x);
      float floatValue5 = (float) (doubleValue5 - vec3d.y);
      float floatValue6 = (float) (doubleValue6 - vec3d.z);
      render(
          buffer,
          entry,
          intValue,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue2,
          floatValue6,
          floatValue,
          floatValue2,
          floatValue6);
      render(
          buffer,
          entry,
          intValue,
          floatValue,
          floatValue5,
          floatValue3,
          floatValue,
          floatValue5,
          floatValue6,
          floatValue4,
          floatValue5,
          floatValue6,
          floatValue4,
          floatValue5,
          floatValue3);
      render(
          buffer,
          entry,
          intValue,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue,
          floatValue5,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue3,
          floatValue4,
          floatValue2,
          floatValue3);
      render(
          buffer,
          entry,
          intValue,
          floatValue,
          floatValue2,
          floatValue6,
          floatValue4,
          floatValue2,
          floatValue6,
          floatValue4,
          floatValue5,
          floatValue6,
          floatValue,
          floatValue5,
          floatValue6);
      render(
          buffer,
          entry,
          intValue,
          floatValue,
          floatValue2,
          floatValue3,
          floatValue,
          floatValue2,
          floatValue6,
          floatValue,
          floatValue5,
          floatValue6,
          floatValue,
          floatValue5,
          floatValue3);
      render(
          buffer,
          entry,
          intValue,
          floatValue4,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue6,
          floatValue4,
          floatValue2,
          floatValue6);
    }
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
      double doubleValue6,
      int intValue,
      int intValue2,
      int intValue3,
      boolean flag,
      float floatValue) {
    intValue2 = Math.clamp(intValue2, 0, 255);
    intValue3 = Math.clamp(intValue3, 0, 255);
    if (intValue2 != 0 || intValue3 != 0) {
      Entry entry = matrixStack.peek();
      float floatValue2 = (float) (doubleValue - vec3d.x);
      float floatValue3 = (float) (doubleValue2 - vec3d.y);
      float floatValue4 = (float) (doubleValue3 - vec3d.z);
      float floatValue5 = (float) (doubleValue4 - vec3d.x);
      float floatValue6 = (float) (doubleValue5 - vec3d.y);
      float floatValue7 = (float) (doubleValue6 - vec3d.z);
      int intValue4 = intValue2 << 24 | intValue & 16777215;
      int intValue5 = intValue3 << 24 | intValue & 16777215;
      if (flag) {
        VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer);
        render(
            buffer,
            entry,
            intValue4,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue3,
            floatValue7);
        render(
            buffer,
            entry,
            intValue5,
            floatValue2,
            floatValue6,
            floatValue4,
            floatValue2,
            floatValue6,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue4);
        render(
            buffer,
            entry,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue6,
            floatValue4,
            floatValue2,
            floatValue6,
            floatValue4,
            intValue4,
            intValue5);
        render(
            buffer,
            entry,
            floatValue5,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue4,
            intValue4,
            intValue5);
        render(
            buffer,
            entry,
            floatValue5,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue6,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue7,
            intValue4,
            intValue5);
        render(
            buffer,
            entry,
            floatValue2,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue2,
            floatValue6,
            floatValue4,
            floatValue2,
            floatValue6,
            floatValue7,
            intValue4,
            intValue5);
      } else {
        VertexConsumer buffer2 = immediate.getBuffer(VertexConsumerSupport.renderLayer2);
        render2(
            buffer2,
            entry,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue4,
            intValue4,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue5,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue3,
            floatValue7,
            intValue4,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue5,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue3,
            floatValue7,
            intValue4,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue2,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue3,
            floatValue4,
            intValue4,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue2,
            floatValue6,
            floatValue4,
            floatValue5,
            floatValue6,
            floatValue4,
            intValue5,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue5,
            floatValue6,
            floatValue4,
            floatValue5,
            floatValue6,
            floatValue7,
            intValue5,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue2,
            floatValue6,
            floatValue7,
            intValue5,
            floatValue);
        render2(
            buffer2,
            entry,
            floatValue2,
            floatValue6,
            floatValue7,
            floatValue2,
            floatValue6,
            floatValue4,
            intValue5,
            floatValue);
        render(
            buffer2,
            entry,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue2,
            floatValue6,
            floatValue4,
            intValue4,
            intValue5,
            floatValue);
        render(
            buffer2,
            entry,
            floatValue5,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue6,
            floatValue4,
            intValue4,
            intValue5,
            floatValue);
        render(
            buffer2,
            entry,
            floatValue5,
            floatValue3,
            floatValue7,
            floatValue5,
            floatValue6,
            floatValue7,
            intValue4,
            intValue5,
            floatValue);
        render(
            buffer2,
            entry,
            floatValue2,
            floatValue3,
            floatValue7,
            floatValue2,
            floatValue6,
            floatValue7,
            intValue4,
            intValue5,
            floatValue);
      }
    }
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
    if (intValue >>> 24 != 0) {
      VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer2);
      Entry entry = matrixStack.peek();
      float floatValue2 = (float) (doubleValue - vec3d.x);
      float floatValue3 = (float) (doubleValue2 - vec3d.y);
      float floatValue4 = (float) (doubleValue3 - vec3d.z);
      float floatValue5 = (float) (doubleValue4 - vec3d.x);
      float floatValue6 = (float) (doubleValue5 - vec3d.z);
      render2(
          buffer,
          entry,
          floatValue2,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue3,
          floatValue4,
          intValue,
          floatValue);
      render2(
          buffer,
          entry,
          floatValue5,
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue3,
          floatValue6,
          intValue,
          floatValue);
      render2(
          buffer,
          entry,
          floatValue5,
          floatValue3,
          floatValue6,
          floatValue2,
          floatValue3,
          floatValue6,
          intValue,
          floatValue);
      render2(
          buffer,
          entry,
          floatValue2,
          floatValue3,
          floatValue6,
          floatValue2,
          floatValue3,
          floatValue4,
          intValue,
          floatValue);
    }
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      int intValue,
      int intValue2,
      float floatValue) {
    VertexConsumer buffer = immediate.getBuffer(VertexConsumerSupport.renderLayer2);
    Entry entry = matrixStack.peek();
    float floatValue2 = (float) (doubleValue2 - vec3d.y);
    double doubleValue5 = doubleValue + doubleValue4;
    double doubleValue6 = doubleValue3;

    for (int index = 1; index <= intValue; index++) {
      double doubleValue7 = (Math.PI * 2) * index / intValue;
      double doubleValue8 = doubleValue + Math.cos(doubleValue7) * doubleValue4;
      double doubleValue9 = doubleValue3 + Math.sin(doubleValue7) * doubleValue4;
      render2(
          buffer,
          entry,
          (float) (doubleValue5 - vec3d.x),
          floatValue2,
          (float) (doubleValue6 - vec3d.z),
          (float) (doubleValue8 - vec3d.x),
          floatValue2,
          (float) (doubleValue9 - vec3d.z),
          intValue2,
          floatValue);
      doubleValue5 = doubleValue8;
      doubleValue6 = doubleValue9;
    }
  }

  public static void apply(Immediate immediate) {
    VertexConsumerSupport.apply(immediate);
  }

  public static void render2(
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
    render(
        vertexConsumer,
        entry,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        intValue,
        intValue,
        floatValue7);
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
      int intValue2,
      float floatValue7) {
    float floatValue8 = floatValue4 - floatValue;
    float floatValue9 = floatValue5 - floatValue2;
    float floatValue10 = floatValue6 - floatValue3;
    float floatValue11 =
        floatValue8 * floatValue8 + floatValue9 * floatValue9 + floatValue10 * floatValue10;
    if (floatValue11 > 1.0E-9F) {
      float floatValue12 = 1.0F / (float) Math.sqrt(floatValue11);
      floatValue8 *= floatValue12;
      floatValue9 *= floatValue12;
      floatValue10 *= floatValue12;
    } else {
      floatValue8 = 0.0F;
      floatValue9 = 1.0F;
      floatValue10 = 0.0F;
    }

    vertexConsumer
        .vertex(entry, floatValue, floatValue2, floatValue3)
        .color(intValue)
        .normal(entry, floatValue8, floatValue9, floatValue10)
        .lineWidth(floatValue7);
    vertexConsumer
        .vertex(entry, floatValue4, floatValue5, floatValue6)
        .color(intValue2)
        .normal(entry, floatValue8, floatValue9, floatValue10)
        .lineWidth(floatValue7);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      int intValue,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      float floatValue12) {
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
    vertexConsumer.vertex(entry, floatValue4, floatValue5, floatValue6).color(intValue);
    vertexConsumer.vertex(entry, floatValue7, floatValue8, floatValue9).color(intValue);
    vertexConsumer.vertex(entry, floatValue10, floatValue11, floatValue12).color(intValue);
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
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      float floatValue12,
      int intValue,
      int intValue2) {
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
    vertexConsumer.vertex(entry, floatValue4, floatValue5, floatValue6).color(intValue);
    vertexConsumer.vertex(entry, floatValue7, floatValue8, floatValue9).color(intValue2);
    vertexConsumer.vertex(entry, floatValue10, floatValue11, floatValue12).color(intValue2);
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
    vertexConsumer.vertex(entry, floatValue10, floatValue11, floatValue12).color(intValue2);
    vertexConsumer.vertex(entry, floatValue7, floatValue8, floatValue9).color(intValue2);
    vertexConsumer.vertex(entry, floatValue4, floatValue5, floatValue6).color(intValue);
  }
}

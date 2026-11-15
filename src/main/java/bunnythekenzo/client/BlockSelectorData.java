package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.render.BlockSelectorRenderer;

public record BlockSelectorData(
    VertexConsumer vertexConsumer, Entry entry, boolean enabled, float amount, Vec3d vec3d) {

  public void render(Vec3d vec3d2, int intValue, float floatValue) {
    this.vertexConsumer
        .vertex(this.entry, (float) vec3d2.x, (float) vec3d2.y, (float) vec3d2.z)
        .color(ArgbColors.withOpacity(intValue, floatValue * this.amount));
  }

  public void render(
      Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, Vec3d vec3d5, int intValue, float floatValue) {
    this.render(vec3d2, intValue, floatValue);
    this.render(vec3d3, intValue, floatValue);
    this.render(vec3d4, intValue, floatValue);
    this.render(vec3d5, intValue, floatValue);
  }

  public void render(
      Vec3d vec3d2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Vec3d vec3d5,
      int intValue,
      float floatValue,
      float floatValue2) {
    this.render(vec3d2, intValue, floatValue);
    this.render(vec3d3, intValue, floatValue2);
    this.render(vec3d4, intValue, floatValue2);
    this.render(vec3d5, intValue, floatValue);
  }

  public void render(Vec3d vec3d2, Vec3d vec3d3, double doubleValue, int intValue, boolean flag) {
    if (!this.enabled || flag) {
      Vec3d vec3d4 = vec3d3.subtract(vec3d2);
      if (!(vec3d4.lengthSquared() < 1.0E-10) && !(doubleValue < 1.0E-4)) {
        vec3d4 = vec3d4.normalize();
        Vec3d vec3d5 =
            vec3d4
                .crossProduct(
                    Math.abs(vec3d4.y) > 0.9
                        ? BlockSelectorRenderer.vec3d
                        : BlockSelectorRenderer.vec3d2)
                .normalize();
        Vec3d vec3d6 = vec3d4.crossProduct(vec3d5).normalize();
        if (this.enabled) {
          Vec3d vec3d7 = vec3d4.crossProduct(this.vec3d.subtract(vec3d2.lerp(vec3d3, 0.5)));
          vec3d7 =
              (vec3d7.lengthSquared() < 1.0E-9 ? vec3d5 : vec3d7.normalize())
                  .multiply(doubleValue * 4.0 + 0.005);
          this.render(
              vec3d2, vec3d2.add(vec3d7), vec3d3.add(vec3d7), vec3d3, intValue, 0.22F, 0.0F);
          this.render(
              vec3d3,
              vec3d3.subtract(vec3d7),
              vec3d2.subtract(vec3d7),
              vec3d2,
              intValue,
              0.22F,
              0.0F);
        } else {
          this.render(vec3d2, vec3d3, vec3d5, vec3d6, doubleValue, intValue, 1.0F, !flag);
        }
      }
    }
  }

  public void render(
      Vec3d vec3d2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Vec3d vec3d5,
      double doubleValue,
      int intValue,
      float floatValue,
      boolean flag) {
    for (int index = 0; index < 8; index++) {
      Vec3d vec3d6 =
          vec3d4
              .multiply(Math.cos(index * (Math.PI * 2) / 8.0) * doubleValue)
              .add(vec3d5.multiply(Math.sin(index * (Math.PI * 2) / 8.0) * doubleValue));
      Vec3d vec3d7 =
          vec3d4
              .multiply(Math.cos((index + 1) * (Math.PI * 2) / 8.0) * doubleValue)
              .add(vec3d5.multiply(Math.sin((index + 1) * (Math.PI * 2) / 8.0) * doubleValue));
      int intValue2 =
          flag
              ? ArgbColors.darken(intValue, (float) (1.0 - BlockSelectorRenderer.valueArray[index]))
              : intValue;
      this.render(
          vec3d2.add(vec3d6),
          vec3d2.add(vec3d7),
          vec3d3.add(vec3d7),
          vec3d3.add(vec3d6),
          intValue2,
          floatValue);
      if (!this.enabled) {
        this.render(vec3d2, vec3d2, vec3d2.add(vec3d7), vec3d2.add(vec3d6), intValue2, floatValue);
        this.render(vec3d3, vec3d3, vec3d3.add(vec3d6), vec3d3.add(vec3d7), intValue2, floatValue);
      }
    }
  }

  public void render(
      Vec3d vec3d2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Vec3d vec3d5,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      int intValue) {
    if (this.enabled) {
      for (int index = 0; index < 16; index++) {
        Vec3d vec3d6 =
            BlockSelectorRenderer.getVec3d(
                vec3d2,
                vec3d4,
                vec3d5,
                doubleValue * 3.0,
                doubleValue * 3.0,
                index * (Math.PI * 2) / 16.0);
        Vec3d vec3d7 =
            BlockSelectorRenderer.getVec3d(
                vec3d2,
                vec3d4,
                vec3d5,
                doubleValue * 3.0,
                doubleValue * 3.0,
                (index + 1) * (Math.PI * 2) / 16.0);
        this.render(vec3d2, vec3d6, vec3d7, vec3d2, intValue, 0.26F, 0.0F);
      }
    } else {
      Vec3d vec3d8 = vec3d2.add(vec3d3.multiply(doubleValue2));
      Vec3d vec3d9 = vec3d2.subtract(vec3d3.multiply(doubleValue2));

      for (int index2 = 0; index2 < 4; index2++) {
        Vec3d vec3d10 =
            BlockSelectorRenderer.getVec3d(
                vec3d2,
                vec3d4,
                vec3d5,
                doubleValue,
                doubleValue,
                doubleValue3 + index2 * (Math.PI * 2) / 4.0);
        Vec3d vec3d11 =
            BlockSelectorRenderer.getVec3d(
                vec3d2,
                vec3d4,
                vec3d5,
                doubleValue,
                doubleValue,
                doubleValue3 + (index2 + 1) * (Math.PI * 2) / 4.0);
        this.render(
            vec3d8,
            vec3d8,
            vec3d10,
            vec3d11,
            ArgbColors.lighten(ArgbColors.darken(intValue, index2 * 0.12F), 0.12F),
            1.0F);
        this.render(
            vec3d9,
            vec3d9,
            vec3d11,
            vec3d10,
            ArgbColors.darken(intValue, 0.2F + index2 * 0.1F),
            1.0F);
      }
    }
  }

  public void render(
      Vec3d vec3d2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      double doubleValue6,
      int intValue) {
    Vec3d vec3d5 = vec3d3.crossProduct(vec3d4).normalize();
    int intValue2 = Math.max(3, (int) Math.ceil(Math.abs(doubleValue6) / (Math.PI * 2) * 96.0));

    for (int index = 0; index < intValue2; index++) {
      double doubleValue7 = doubleValue5 + doubleValue6 * index / intValue2;
      double doubleValue8 = doubleValue5 + doubleValue6 * (index + 1) / intValue2;
      Vec3d vec3d6 =
          BlockSelectorRenderer.getVec3d(
              vec3d2, vec3d3, vec3d4, doubleValue, doubleValue2, doubleValue7);
      Vec3d vec3d7 =
          BlockSelectorRenderer.getVec3d(
              vec3d2, vec3d3, vec3d4, doubleValue, doubleValue2, doubleValue8);
      Vec3d vec3d8 =
          vec3d3.multiply(Math.cos(doubleValue7)).add(vec3d4.multiply(Math.sin(doubleValue7)));
      Vec3d vec3d9 =
          vec3d3.multiply(Math.cos(doubleValue8)).add(vec3d4.multiply(Math.sin(doubleValue8)));
      if (this.enabled) {
        double doubleValue9 = doubleValue3 * 5.0 + 0.008;
        this.render(
            vec3d6,
            vec3d6.add(vec3d8.multiply(doubleValue9)),
            vec3d7.add(vec3d9.multiply(doubleValue9)),
            vec3d7,
            intValue,
            0.2F,
            0.0F);
        this.render(
            vec3d7,
            vec3d7.subtract(vec3d9.multiply(doubleValue9)),
            vec3d6.subtract(vec3d8.multiply(doubleValue9)),
            vec3d6,
            intValue,
            0.2F,
            0.0F);
      } else {
        for (int index2 = 0; index2 < 8; index2++) {
          double doubleValue10 = index2 * (Math.PI * 2) / 8.0;
          double doubleValue11 = (index2 + 1) * (Math.PI * 2) / 8.0;
          Vec3d vec3d10 =
              vec3d6
                  .add(vec3d8.multiply(Math.cos(doubleValue10) * doubleValue3))
                  .add(vec3d5.multiply(Math.sin(doubleValue10) * doubleValue4));
          Vec3d vec3d11 =
              vec3d6
                  .add(vec3d8.multiply(Math.cos(doubleValue11) * doubleValue3))
                  .add(vec3d5.multiply(Math.sin(doubleValue11) * doubleValue4));
          Vec3d vec3d12 =
              vec3d7
                  .add(vec3d9.multiply(Math.cos(doubleValue10) * doubleValue3))
                  .add(vec3d5.multiply(Math.sin(doubleValue10) * doubleValue4));
          Vec3d vec3d13 =
              vec3d7
                  .add(vec3d9.multiply(Math.cos(doubleValue11) * doubleValue3))
                  .add(vec3d5.multiply(Math.sin(doubleValue11) * doubleValue4));
          int int3 =
              ArgbColors.darken(intValue, (float) (1.0 - BlockSelectorRenderer.valueArray[index2]));
          this.render(vec3d10, vec3d11, vec3d13, vec3d12, int3, 1.0F);
          if (index == 0) {
            this.render(vec3d6, vec3d6, vec3d11, vec3d10, int3, 1.0F);
          }

          if (index == intValue2 - 1) {
            this.render(vec3d7, vec3d7, vec3d12, vec3d13, int3, 1.0F);
          }
        }
      }
    }
  }
}

package bunnythekenzo.client;

import java.util.Arrays;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.Vec3d;

public final class AuroraSupport {
  public float[] amountArray;
  public int[] countArray;
  public int count;

  public AuroraSupport(int intValue) {
    this.amountArray = new float[intValue * 5];
    this.countArray = new int[intValue];
  }

  public void render(
      Vec3d vec3d,
      Vec3d vec3d2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      float floatValue,
      float floatValue2,
      int intValue,
      int intValue2) {
    this.render(vec3d, floatValue, 0.0F, intValue);
    this.render(vec3d2, floatValue, 1.0F, intValue);
    this.render(vec3d3, floatValue2, 1.0F, intValue2);
    this.render(vec3d4, floatValue2, 0.0F, intValue2);
  }

  public void render(Vec3d vec3d, float floatValue, float floatValue2, int intValue) {
    if (this.count == this.countArray.length) {
      this.countArray = Arrays.copyOf(this.countArray, this.countArray.length * 2);
      this.amountArray = Arrays.copyOf(this.amountArray, this.amountArray.length * 2);
    }

    int index = this.count * 5;
    this.amountArray[index] = (float) vec3d.x;
    this.amountArray[index + 1] = (float) vec3d.y;
    this.amountArray[index + 2] = (float) vec3d.z;
    this.amountArray[index + 3] = floatValue;
    this.amountArray[index + 4] = floatValue2;
    this.countArray[this.count++] = intValue;
  }

  public void apply(Entry entry, VertexConsumer vertexConsumer) {
    for (int index = 0; index < this.count; index++) {
      int index2 = index * 5;
      vertexConsumer
          .vertex(
              entry,
              this.amountArray[index2],
              this.amountArray[index2 + 1],
              this.amountArray[index2 + 2])
          .texture(this.amountArray[index2 + 3], this.amountArray[index2 + 4])
          .color(this.countArray[index]);
    }
  }
}

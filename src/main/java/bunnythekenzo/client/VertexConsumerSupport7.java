package bunnythekenzo.client;

import java.util.Arrays;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import org.joml.Vector3f;

public final class VertexConsumerSupport7 {
  public float[] amountArray = new float[2304];
  public int count;

  public void render(
      Vector3f vector3f, float floatValue, float floatValue2, Vector3f vector3f2, int intValue) {
    if (this.count + 9 > this.amountArray.length) {
      this.amountArray = Arrays.copyOf(this.amountArray, this.amountArray.length * 2);
    }

    this.amountArray[this.count++] = vector3f.x;
    this.amountArray[this.count++] = vector3f.y;
    this.amountArray[this.count++] = vector3f.z;
    this.amountArray[this.count++] = floatValue;
    this.amountArray[this.count++] = floatValue2;
    this.amountArray[this.count++] = vector3f2.x;
    this.amountArray[this.count++] = vector3f2.y;
    this.amountArray[this.count++] = vector3f2.z;
    this.amountArray[this.count++] = Float.intBitsToFloat(intValue);
  }

  public void apply(Entry entry, VertexConsumer vertexConsumer, int intValue) {
    for (byte index = 0; index < this.count; index += 9) {
      vertexConsumer
          .vertex(
              entry,
              this.amountArray[index],
              this.amountArray[index + 1],
              this.amountArray[index + 2])
          .color(Float.floatToRawIntBits(this.amountArray[index + 8]))
          .texture(this.amountArray[index + 3], this.amountArray[index + 4])
          .overlay(OverlayTexture.DEFAULT_UV)
          .light(intValue)
          .normal(
              entry,
              this.amountArray[index + 5],
              this.amountArray[index + 6],
              this.amountArray[index + 7]);
    }
  }
}

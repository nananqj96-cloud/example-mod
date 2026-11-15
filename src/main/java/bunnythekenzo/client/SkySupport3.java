package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;

public final class SkySupport3 {
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];

  public static void apply(float[] floatValues) {
    System.arraycopy(amountArray, 0, floatValues, 0, 3);
  }

  public static void render(
      Immediate immediate, double doubleValue, float[] floatValues, float floatValue) {
    SkySupport4.skySupport42.apply(doubleValue, amountArray);
    SkySupport4.skySupport42.render(doubleValue, -0.18F, 0.95F, 0.26F, amountArray2);
    float value = SkySupport15.computeFloat(amountArray, floatValue);
    if (!(value <= 0.01F)) {
      SkySupport15.apply(floatValues, amountArray, amountArray3);
      SkySupport15.render(
          immediate,
          "ROCK",
          amountArray,
          0.042F,
          amountArray2,
          SkySupport15.computeFloat(doubleValue, 0.01F),
          amountArray3,
          value,
          doubleValue,
          false);
    }
  }
}

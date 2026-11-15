package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;

public final class SkySupport14 {
  public static final float amount = 0.047F;
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];

  public static void apply(float[] floatValues) {
    System.arraycopy(amountArray, 0, floatValues, 0, 3);
  }

  public static void render(
      Immediate immediate, double doubleValue, float[] floatValues, float floatValue) {
    SkySupport4.skySupport45.apply(doubleValue, amountArray);
    SkySupport4.skySupport45.render(doubleValue, 0.41F, 0.84F, -0.35F, amountArray2);
    float value = SkySupport15.computeFloat(amountArray, floatValue);
    if (!(value <= 0.01F)) {
      SkySupport15.apply(floatValues, amountArray, amountArray3);
      float value2 = SkySupport15.computeFloat(doubleValue, 0.021F);
      SkySupport15.render(
          immediate,
          "VOLCANIC",
          amountArray,
          0.047F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "VOLCANIC_HAZE",
          amountArray,
          0.048221998F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
    }
  }
}

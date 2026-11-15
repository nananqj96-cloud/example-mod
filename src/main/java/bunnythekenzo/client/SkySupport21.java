package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;

public final class SkySupport21 {
  public static final float amount = 0.055F;
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];

  public static void apply(float[] floatValues) {
    System.arraycopy(amountArray, 0, floatValues, 0, 3);
  }

  public static void render(
      Immediate immediate, double doubleValue, float[] floatValues, float floatValue) {
    SkySupport4.skySupport44.apply(doubleValue, amountArray);
    SkySupport4.skySupport44.render(doubleValue, -0.28F, 0.91F, 0.3F, amountArray2);
    float value = SkySupport15.computeFloat(amountArray, floatValue);
    if (!(value <= 0.01F)) {
      SkySupport15.apply(floatValues, amountArray, amountArray3);
      float value2 = SkySupport15.computeFloat(doubleValue, 0.016F);
      SkySupport15.render(
          immediate,
          "OCEAN",
          amountArray,
          0.055F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "OCEAN_CLOUDS",
          amountArray,
          0.05566F,
          amountArray2,
          SkySupport15.computeFloat(doubleValue, 0.023F) + 0.4F,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "OCEAN_HAZE",
          amountArray,
          0.05709F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
    }
  }
}

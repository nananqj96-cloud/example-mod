package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;

public final class SkySupport25 {
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];

  public static void apply(float[] floatValues) {
    System.arraycopy(amountArray, 0, floatValues, 0, 3);
  }

  public static void render(
      Immediate immediate, double doubleValue, float[] floatValues, float floatValue) {
    SkySupport4.skySupport43.apply(doubleValue, amountArray);
    SkySupport4.skySupport43.render(doubleValue, 0.52F, 0.74F, -0.42F, amountArray2);
    float value = SkySupport15.computeFloat(amountArray, floatValue);
    if (!(value <= 0.01F)) {
      SkySupport15.apply(floatValues, amountArray, amountArray3);
      float value2 = SkySupport15.computeFloat(doubleValue, 0.042F);
      SkySupport15.render(
          immediate,
          "ICE",
          amountArray,
          0.041F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "CLOUDS",
          amountArray,
          0.041328F,
          amountArray2,
          SkySupport15.computeFloat(doubleValue, 0.049F) + 0.4F,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "ICE_HAZE",
          amountArray,
          0.042435F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
    }
  }
}

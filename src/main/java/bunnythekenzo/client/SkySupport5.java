package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;

public final class SkySupport5 {
  public static final float amount = 0.072F;
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];
  public static final float[] amountArray3 = new float[3];
  public static final float[] amountArray4 = new float[3];
  public static final float[] amountArray5 = new float[3];
  public static final float[] amountArray6 = new float[] {0.0F, 1.0F, 0.0F};

  public static void apply(float[] floatValues) {
    System.arraycopy(amountArray, 0, floatValues, 0, 3);
  }

  public static void render(
      Immediate immediate, double doubleValue, float[] floatValues, float floatValue) {
    SkySupport4.skySupport4.apply(doubleValue, amountArray);
    SkySupport4.skySupport4.render(doubleValue, 0.3F, 0.9F, 0.31F, amountArray2);
    float value = SkySupport15.computeFloat(amountArray, floatValue);
    if (!(value <= 0.01F)) {
      SkySupport15.apply(floatValues, amountArray, amountArray3);
      float value2 = SkySupport15.computeFloat(doubleValue, 0.028F);
      float floatValue2 = SkySupport15.computeFloat(doubleValue, 0.09F) + 0.65F;
      SkySupport15.render(amountArray, amountArray2, 0.21239999F, floatValue2, amountArray4);
      boolean flag =
          (amountArray4[0] - amountArray[0]) * amountArray[0]
                  + (amountArray4[1] - amountArray[1]) * amountArray[1]
                  + (amountArray4[2] - amountArray[2]) * amountArray[2]
              > 0.0F;
      if (flag) {
        render(immediate, doubleValue, value);
      }

      SkySupport15.render(
          immediate, amountArray, 0.072F, amountArray2, amountArray3, value, doubleValue, true);
      SkySupport15.render(
          immediate,
          "GAS",
          amountArray,
          0.072F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate,
          "HAZE",
          amountArray,
          0.07358399F,
          amountArray2,
          value2,
          amountArray3,
          value,
          doubleValue,
          false);
      SkySupport15.render(
          immediate, amountArray, 0.072F, amountArray2, amountArray3, value, doubleValue, false);
      if (!flag) {
        render(immediate, doubleValue, value);
      }
    }
  }

  public static void render(Immediate immediate, double doubleValue, float floatValue) {
    System.arraycopy(amountArray3, 0, amountArray5, 0, 3);
    SkySupport15.render(
        immediate,
        "ROCK",
        amountArray4,
        0.0105F,
        amountArray6,
        SkySupport15.computeFloat(doubleValue, 0.047F),
        amountArray5,
        floatValue,
        doubleValue,
        true);
  }
}

package bunnythekenzo.client;

public final class SkySupport4 {
  public static final SkySupport4 skySupport4 = new SkySupport4(2.1, 0.0);
  public static final SkySupport4 skySupport42 = new SkySupport4(4.42, 2.25);
  public static final SkySupport4 skySupport43 = new SkySupport4(0.72, 1.1);
  public static final SkySupport4 skySupport44 = new SkySupport4(5.7, 3.4);
  public static final SkySupport4 skySupport45 = new SkySupport4(3.28, 5.65);
  public static final double value = Math.PI * 2;
  public static final double value2 = 1500.0;
  public final double value3;
  public final double value4;

  public SkySupport4(double value32, double value42) {
    this.value3 = value32;
    this.value4 = value42;
  }

  public void apply(double doubleValue, float[] floatValues) {
    double doubleValue2 =
        0.44 + 0.52 * Math.sin(doubleValue % 1500.0 * (Math.PI * 2) / 1500.0 + this.value4);
    double doubleValue3 = this.value3 + computeDouble(doubleValue);
    double doubleValue4 = Math.cos(doubleValue2);
    floatValues[0] = (float) (doubleValue4 * Math.cos(doubleValue3));
    floatValues[1] = (float) Math.sin(doubleValue2);
    floatValues[2] = (float) (doubleValue4 * Math.sin(doubleValue3));
  }

  public void render(
      double doubleValue,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float[] floatValues) {
    double doubleValue2 = computeDouble(doubleValue);
    double doubleValue3 = Math.cos(doubleValue2);
    double doubleValue4 = Math.sin(doubleValue2);
    double doubleValue5 =
        Math.sqrt(floatValue * floatValue + floatValue2 * floatValue2 + floatValue3 * floatValue3);
    floatValues[0] =
        (float) ((floatValue * doubleValue3 - floatValue3 * doubleValue4) / doubleValue5);
    floatValues[1] = (float) (floatValue2 / doubleValue5);
    floatValues[2] =
        (float) ((floatValue * doubleValue4 + floatValue3 * doubleValue3) / doubleValue5);
  }

  public static double computeDouble(double doubleValue) {
    return doubleValue % 2400.0 * (Math.PI * 2) / 2400.0;
  }
}

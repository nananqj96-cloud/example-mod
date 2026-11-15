package bunnythekenzo.client;

public final class SkySupport7 {
  public static float amount;
  public static float amount2;
  public static float amount3;

  public static void render(float amount4, float amount22, float amount32) {
    amount = amount4;
    amount2 = amount22;
    amount3 = amount32;
  }

  public static boolean test(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    float floatValue5 =
        floatValue4
            + 0.25F * (Math.abs(floatValue) + Math.abs(floatValue2) + Math.abs(floatValue3));
    return floatValue * amount + floatValue2 * amount2 + floatValue3 * amount3 >= -floatValue5;
  }
}

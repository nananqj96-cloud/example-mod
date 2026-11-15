package bunnythekenzo.client;

public final class SkySupport24 {
  public final float amount;
  public final float amount2;
  public final float amount3;
  public final float amount4;
  public final float amount5;
  public final float amount6;
  public final float amount7;
  public final float amount8;
  public final int count;
  public final int count2;
  public final int count3;
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12;
  public float amount13;
  public float amount14;
  public float amount15;

  public SkySupport24(
      float amount16,
      float amount22,
      float amount32,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float amount72,
      float amount82,
      int count4,
      int count22,
      int count32) {
    this.amount = amount16;
    this.amount2 = amount22;
    this.amount3 = amount32;
    float floatValue4 =
        (float)
            Math.sqrt(
                floatValue * floatValue + floatValue2 * floatValue2 + floatValue3 * floatValue3);
    this.amount4 = floatValue / floatValue4;
    this.amount5 = floatValue2 / floatValue4;
    this.amount6 = floatValue3 / floatValue4;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.count = count4;
    this.count2 = count22;
    this.count3 = count32;
  }
}

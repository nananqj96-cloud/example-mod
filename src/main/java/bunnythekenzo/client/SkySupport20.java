package bunnythekenzo.client;

public final class SkySupport20 {
  public final float[] amountArray;
  public final float[] amountArray2;
  public final float[] amountArray3;
  public final float[] amountArray4;
  public final float[] amountArray5;
  public final float[] amountArray6;
  public final float[] amountArray7;
  public final float[] amountArray8;
  public final float[] amountArray9;
  public final int[] countArray;

  public SkySupport20(int intValue, int intValue2) {
    int intValue3 = (intValue + 1) * (intValue2 + 1);
    this.amountArray = new float[intValue3];
    this.amountArray2 = new float[intValue3];
    this.amountArray3 = new float[intValue3];
    this.amountArray6 = new float[intValue3];
    this.amountArray7 = new float[intValue3];
    this.amountArray8 = new float[intValue3];
    this.amountArray9 = new float[intValue * intValue2 * 8];
    this.amountArray4 = new float[intValue3];
    this.amountArray5 = new float[intValue3];

    for (int index = 0; index <= intValue2; index++) {
      for (int index2 = 0; index2 <= intValue; index2++) {
        int index3 = index * (intValue + 1) + index2;
        this.amountArray4[index3] = (float) (Math.PI * 2) * index2 / intValue;
        this.amountArray5[index3] = (float) Math.PI * ((float) index / intValue2 - 0.5F);
        float floatValue = (float) Math.cos(this.amountArray5[index3]);
        this.amountArray[index3] = floatValue * (float) Math.cos(this.amountArray4[index3]);
        this.amountArray2[index3] = (float) Math.sin(this.amountArray5[index3]);
        this.amountArray3[index3] = floatValue * (float) Math.sin(this.amountArray4[index3]);
      }
    }

    this.countArray = new int[intValue * intValue2 * 4];
    int index4 = 0;

    for (int index5 = 0; index5 < intValue2; index5++) {
      for (int index6 = 0; index6 < intValue; index6++) {
        int intValue4 = index5 * (intValue + 1) + index6;
        this.countArray[index4++] = intValue4;
        this.countArray[index4++] = intValue4 + 1;
        this.countArray[index4++] = intValue4 + intValue + 2;
        this.countArray[index4++] = intValue4 + intValue + 1;
      }
    }

    for (int index7 = 0; index7 < this.countArray.length / 4; index7++) {
      this.apply(
          index7 * 8,
          this.countArray[index7 * 4],
          this.countArray[index7 * 4 + 1],
          this.countArray[index7 * 4 + 2]);
      this.apply(
          index7 * 8 + 4,
          this.countArray[index7 * 4],
          this.countArray[index7 * 4 + 2],
          this.countArray[index7 * 4 + 3]);
    }
  }

  public void apply(int index, int index2, int index3, int index4) {
    float floatValue = this.amountArray[index3] - this.amountArray[index2];
    float floatValue2 = this.amountArray2[index3] - this.amountArray2[index2];
    float floatValue3 = this.amountArray3[index3] - this.amountArray3[index2];
    float floatValue4 = this.amountArray[index4] - this.amountArray[index2];
    float floatValue5 = this.amountArray2[index4] - this.amountArray2[index2];
    float floatValue6 = this.amountArray3[index4] - this.amountArray3[index2];
    float floatValue7 = floatValue3 * floatValue5 - floatValue2 * floatValue6;
    float floatValue8 = floatValue * floatValue6 - floatValue3 * floatValue4;
    float floatValue9 = floatValue2 * floatValue4 - floatValue * floatValue5;
    this.amountArray9[index] = floatValue7;
    this.amountArray9[index + 1] = floatValue8;
    this.amountArray9[index + 2] = floatValue9;
    this.amountArray9[index + 3] =
        floatValue7 * this.amountArray[index2]
            + floatValue8 * this.amountArray2[index2]
            + floatValue9 * this.amountArray3[index2];
  }

  public boolean test(
      int intValue, float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    int index = intValue * 8;
    return this.amountArray9[index] * floatValue
                + this.amountArray9[index + 1] * floatValue2
                + this.amountArray9[index + 2] * floatValue3
                + this.amountArray9[index + 3] * floatValue4
            > 1.0E-7F
        && this.amountArray9[index + 4] * floatValue
                + this.amountArray9[index + 5] * floatValue2
                + this.amountArray9[index + 6] * floatValue3
                + this.amountArray9[index + 7] * floatValue4
            > 1.0E-7F;
  }
}

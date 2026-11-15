package bunnythekenzo.client;

public final class MonotonePoseCurve {
  public final float[][] amountArray;
  public final float[][] amountArray2;

  public MonotonePoseCurve(float[][] amountArray3) {
    this.amountArray = amountArray3;
    this.amountArray2 = new float[amountArray3.length][amountArray3[0].length];

    for (int index = 1; index < amountArray3.length - 1; index++) {
      float floatValue = amountArray3[index][0] - amountArray3[index - 1][0];
      float floatValue2 = amountArray3[index + 1][0] - amountArray3[index][0];

      for (int index2 = 1; index2 < amountArray3[index].length; index2++) {
        float floatValue3 =
            (amountArray3[index][index2] - amountArray3[index - 1][index2]) / floatValue;
        float floatValue4 =
            (amountArray3[index + 1][index2] - amountArray3[index][index2]) / floatValue2;
        if (!(floatValue3 * floatValue4 <= 0.0F)) {
          float floatValue5 = 2.0F * floatValue2 + floatValue;
          float floatValue6 = floatValue2 + 2.0F * floatValue;
          this.amountArray2[index][index2] =
              (floatValue5 + floatValue6) / (floatValue5 / floatValue3 + floatValue6 / floatValue4);
        }
      }
    }
  }

  public static MonotonePoseCurve getMonotonePoseCurve(int intValue, String text) {
    String[] textArray = text.strip().split("\\R");
    float[][] floatValuesArray = new float[textArray.length][intValue + 1];

    for (int index = 0; index < textArray.length; index++) {
      String[] textArray2 = textArray[index].strip().split("\\s+");
      if (textArray2.length != intValue + 1) {
        throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1105));
      }

      for (int index2 = 0; index2 < textArray2.length; index2++) {
        floatValuesArray[index][index2] = Float.parseFloat(textArray2[index2]);
        if (!Float.isFinite(floatValuesArray[index][index2])) {
          throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1106));
        }
      }

      if (index > 0 && floatValuesArray[index][0] <= floatValuesArray[index - 1][0]) {
        throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1107));
      }
    }

    return new MonotonePoseCurve(floatValuesArray);
  }

  public float computeFloat(float floatValue, int intValue) {
    int index = intValue + 1;
    int index2 = this.amountArray.length - 1;
    if (floatValue <= this.amountArray[0][0]) {
      return this.amountArray[0][index];
    }

    if (floatValue >= this.amountArray[index2][0]) {
      return this.amountArray[index2][index];
    }

    int index3 = 0;
    int index4 = index2;

    while (index4 - index3 > 1) {
      int index5 = index3 + index4 >>> 1;
      if (this.amountArray[index5][0] <= floatValue) {
        index3 = index5;
      } else {
        index4 = index5;
      }
    }

    float floatValue2 = this.amountArray[index4][0] - this.amountArray[index3][0];
    float floatValue3 = (floatValue - this.amountArray[index3][0]) / floatValue2;
    float floatValue4 = floatValue3 * floatValue3;
    float floatValue5 = floatValue4 * floatValue3;
    return (2.0F * floatValue5 - 3.0F * floatValue4 + 1.0F) * this.amountArray[index3][index]
        + (floatValue5 - 2.0F * floatValue4 + floatValue3)
            * floatValue2
            * this.amountArray2[index3][index]
        + (-2.0F * floatValue5 + 3.0F * floatValue4) * this.amountArray[index4][index]
        + (floatValue5 - floatValue4) * floatValue2 * this.amountArray2[index4][index];
  }
}

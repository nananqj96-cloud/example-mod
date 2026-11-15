package bunnythekenzo.client;

public final class GlintColorSampler {
  public static final float amount = (float) (Math.PI * 2);
  public static final float[] amountArray = new float[8192];
  public static final float[][] amountArray2 = new float[33][];

  public static int computeInt(
      GlintStyle glintStyle,
      float floatValue,
      float floatValue2,
      double doubleValue,
      float floatValue3) {
    return computeInt(
        glintStyle, floatValue, floatValue2, doubleValue, floatValue3, ClientData2.clientData2);
  }

  public static int computeInt(
      GlintStyle glintStyle,
      float floatValue,
      float floatValue2,
      double doubleValue,
      float floatValue3,
      ClientData2 clientData2) {
    if (floatValue3 <= 0.0F) {
      return -16777216;
    }

    floatValue = computeFloat2(floatValue);
    floatValue2 = computeFloat2(floatValue2);
    float floatValue4 = (float) doubleValue;
    float floatValue5 = clientData2.amount2();
    float floatValue6 = clientData2.amount3();
    float floatValue7 = floatValue * (float) (Math.PI * 2);
    float floatValue8 = floatValue2 * (float) (Math.PI * 2);
    float floatValue9 =
        computeFloat(floatValue + floatValue4 * 0.009F, floatValue2 - floatValue4 * 0.013F);
    float floatValue10 =
        computeFloat(floatValue7 + computeFloat(floatValue8 + floatValue4 * 0.15F));
    int intValue;
    switch (GlintStyleSwitchTable.countArray[glintStyle.ordinal()]) {
      case 1:
        float floatValue11 =
            computeFloat2(
                computeFloat(floatValue7 * 3.0F + floatValue8 * 2.0F - floatValue4 * 0.65F), 0.65F);
        intValue = computeInt(1576242, 13741055, floatValue11 * 0.8F + floatValue9 * 0.2F);
        break;
      case 2:
        float floatValue12 = (floatValue - 0.5F) * 1.35F;
        float floatValue13 = (floatValue2 - 0.5F) * 2.0F;
        float floatValue14 =
            (float) Math.sqrt(floatValue12 * floatValue12 + floatValue13 * floatValue13);
        float floatValue15 = (float) Math.atan2(floatValue13, floatValue12);
        float floatValue16 =
            0.5F
                + 0.5F
                    * computeFloat(
                        floatValue15 * 2.0F - floatValue14 * 17.0F + floatValue4 * 0.13F);
        float floatValue17 = computeFloat3(1.0F - floatValue14 * 1.8F);
        float floatValue18 =
            computeFloat2(0.22F, 0.82F, floatValue9 + floatValue17 * floatValue16 * 0.12F);
        float floatValue19 =
            computeFloat(floatValue + floatValue4 * 0.012F, floatValue2 - floatValue4 * 0.009F, 16);
        float floatValue20 = floatValue18 * (0.25F + floatValue5 * 0.95F);
        intValue = computeInt(132376, 1386079, floatValue20);
        intValue = computeInt2(intValue, 6640329, floatValue20 * floatValue20 * 0.72F);
        intValue = computeInt2(intValue, 12941498, floatValue20 * floatValue19 * 0.68F);
        intValue =
            computeInt2(
                intValue,
                3585508,
                computeFloat2(
                        0.35F,
                        0.78F,
                        computeFloat(floatValue + 0.38F, floatValue2 + floatValue4 * 0.007F))
                    * floatValue20
                    * 0.48F);
        intValue =
            computeInt(
                intValue, 330274, computeFloat2(0.57F, 0.85F, floatValue19) * floatValue20 * 0.3F);
        intValue =
            computeInt2(
                intValue,
                16769989,
                floatValue17 * floatValue17 * (0.1F + floatValue16 * 0.24F) * floatValue5);
        intValue =
            computeInt2(
                intValue,
                14216191,
                computeFloat(floatValue, floatValue2, floatValue4, 31, floatValue6, 1));
        intValue =
            computeInt2(
                intValue,
                16774107,
                computeFloat(
                        floatValue, floatValue2, floatValue4 * 0.6F, 11, floatValue6 * 0.18F, 7)
                    * 0.85F);
        break;
      case 3:
        float floatValue21 =
            computeFloat(
                floatValue7 * 2.0F
                    + floatValue8
                    + (floatValue9 - 0.5F) * 10.0F
                    - floatValue4 * 0.8F);
        float floatValue22 = computeFloat2(floatValue21, 0.7F - floatValue5 * 0.2F);
        intValue = computeInt(1050404, 8070853, floatValue9 * 0.7F + floatValue22 * 0.5F);
        intValue = computeInt2(intValue, 12023807, floatValue22 * (0.2F + 0.6F * floatValue5));
        intValue =
            computeInt2(
                intValue,
                5636049,
                computeFloat(
                        floatValue + floatValue4 * 0.014F,
                        floatValue2 - floatValue4 * 0.022F,
                        floatValue4,
                        19,
                        floatValue6,
                        2)
                    * 1.25F);
        break;
      case 4:
        float floatValue23 =
            Math.abs(
                computeFloat(
                    floatValue7 * 2.0F
                        - floatValue8 * 3.0F
                        + (floatValue9 - 0.5F) * 8.0F
                        + floatValue4 * 0.22F));
        float floatValue24 =
            1.0F - computeFloat2(0.015F, 0.035F + 0.2F * floatValue5, floatValue23);
        float floatValue25 = 0.55F + 0.45F * computeFloat(floatValue8 * 2.0F + floatValue4 * 1.3F);
        intValue = computeInt(131594, 2429759, floatValue9 * 0.7F);
        intValue = computeInt2(intValue, 6632386, floatValue24 * 0.65F);
        intValue =
            computeInt2(
                intValue,
                13739775,
                floatValue24 * floatValue24 * (0.15F + floatValue6 * floatValue25));
        break;
      case 5:
        float floatValue26 =
            computeFloat2(floatValue + floatValue4 * 0.009F, floatValue2 - floatValue4 * 0.022F, 5);
        float floatValue27 =
            1.0F
                - computeFloat2(
                    0.025F, 0.12F + 0.12F * floatValue5, Math.abs(floatValue26 - 0.38F));
        intValue =
            computeInt(
                268041,
                2727202,
                computeFloat2(0.2F, 0.8F, floatValue9) * (0.4F + floatValue5 * 0.6F));
        intValue = computeInt2(intValue, 11665193, floatValue27 * (0.35F + floatValue6 * 0.6F));
        intValue =
            computeInt2(
                intValue,
                15663000,
                computeFloat(
                        floatValue,
                        floatValue2 + floatValue4 * 0.035F,
                        floatValue4,
                        14,
                        floatValue6,
                        3)
                    * 0.65F);
        break;
      case 6:
        float floatValue28 = floatValue * 8.0F;
        float floatValue29 = floatValue2 * 8.0F;
        int intValue2 = (int) floatValue28;
        int intValue3 = (int) floatValue29;
        float floatValue30 =
            computeFloat2(floatValue28) + computeFloat2(floatValue29) < 1.0F
                ? computeFloat(intValue2, intValue3, 8)
                : computeFloat(intValue2 + 3, intValue3 + 5, 8);
        float floatValue31 =
            Math.min(
                Math.min(computeFloat2(floatValue28), computeFloat2(floatValue29)),
                Math.abs(1.0F - computeFloat2(floatValue28) - computeFloat2(floatValue29)));
        float floatValue32 =
            computeFloat2(
                computeFloat(
                    floatValue7 + floatValue8 * 2.0F - floatValue4 * 0.65F + floatValue30 * 3.0F),
                0.8F);
        intValue =
            computeInt(2758490, 12551142, 0.15F + floatValue30 * (0.3F + floatValue5 * 0.55F));
        intValue =
            computeInt2(
                intValue,
                16572671,
                (1.0F - computeFloat2(0.005F, 0.06F, floatValue31)) * floatValue32 * floatValue6);
        intValue = computeInt2(intValue, 13811199, floatValue32 * floatValue6 * 0.25F);
        break;
      case 7:
        int intValue4 = 3 + Math.round(floatValue5 * 4.0F);
        float floatValue33 = floatValue * intValue4;
        float floatValue34 = floatValue2 * intValue4;
        int intValue5 = (int) floatValue33;
        int intValue6 = (int) floatValue34;
        float floatValue35 = computeFloat2(floatValue33);
        float floatValue36 = computeFloat2(floatValue34);
        float floatValue37 = computeFloat(intValue5, intValue6, intValue4);
        float floatValue38 =
            floatValue37 < 0.5F ? Math.abs(floatValue35 - 0.5F) : Math.abs(floatValue36 - 0.5F);
        float floatValue39 = 1.0F - computeFloat2(0.055F, 0.18F, floatValue38);
        float floatValue40 =
            computeFloat2(
                computeFloat(
                    (floatValue37 < 0.5F ? floatValue8 : floatValue7) * 2.0F
                        - floatValue4 * 2.2F
                        + floatValue37 * 9.0F),
                0.75F);
        intValue = computeInt(464937, 1065559, floatValue9 * 0.5F);
        intValue =
            computeInt2(
                intValue,
                floatValue37 < 0.48F ? 1695743 : 15611357,
                floatValue39 * (0.55F + floatValue40 * floatValue6));
        float floatValue41 =
            1.0F
                - computeFloat2(
                    0.035F,
                    0.1F,
                    Math.max(Math.abs(floatValue35 - 0.5F), Math.abs(floatValue36 - 0.5F)));
        intValue = computeInt2(intValue, 13238271, floatValue41 * floatValue40 * floatValue6);
        break;
      case 8:
        float floatValue42 =
            computeFloat2(
                floatValue + floatValue4 * 0.016F + computeFloat(floatValue8) * 0.025F,
                floatValue2 - floatValue4 * 0.013F,
                7);
        float floatValue43 =
            computeFloat2(0.3F, 0.66F, floatValue42 + (floatValue9 - 0.5F) * floatValue5);
        intValue = computeInt(7409673, 16218133, floatValue9 * 0.65F + floatValue43 * 0.5F);
        intValue =
            computeInt(
                intValue, 16773547, floatValue43 * floatValue43 * (0.4F + floatValue6 * 0.6F));
        intValue =
            computeInt2(
                intValue,
                16759618,
                computeFloat2(
                        computeFloat(
                            floatValue7 * 2.0F + floatValue8 + floatValue9 * 6.0F - floatValue4),
                        0.9F)
                    * floatValue6
                    * 0.3F);
        break;
      case 9:
        float floatValue44 =
            floatValue7
                + floatValue8
                + computeFloat(floatValue8 * 2.0F + floatValue4 * 0.3F) * (1.5F - floatValue5)
                - floatValue4 * 0.5F;
        float floatValue45 =
            computeFloat2(computeFloat(floatValue44 * 2.0F + floatValue9 * 3.0F), 0.85F)
                * floatValue6;
        intValue =
            computeInt(
                45.0F + 175.0F * (0.5F + 0.5F * computeFloat(floatValue44)),
                45.0F
                    + 175.0F
                        * (0.5F
                            + 0.5F * computeFloat(floatValue44 + (float) (Math.PI * 2.0 / 3.0))),
                45.0F
                    + 175.0F
                        * (0.5F
                            + 0.5F * computeFloat(floatValue44 + (float) (Math.PI * 4.0 / 3.0))));
        intValue = computeInt(intValue, 16777215, floatValue45 * 0.6F);
        break;
      case 10:
        float floatValue46 =
            computeFloat(
                floatValue7 * 3.0F
                    + computeFloat(floatValue8 * 2.0F + floatValue4 * 0.4F) * 1.5F
                    + floatValue4 * 0.35F);
        float floatValue47 = computeFloat(floatValue8 * 3.0F - floatValue10 + floatValue4 * 0.45F);
        float floatValue48 = computeFloat2(floatValue46 * floatValue47, 0.65F);
        intValue = computeInt(134435, 480378, floatValue9 * (0.5F + floatValue5 * 0.5F));
        intValue = computeInt2(intValue, 3983065, floatValue48 * (0.4F + floatValue5 * 0.6F));
        intValue =
            computeInt2(
                intValue,
                8716259,
                computeFloat(
                        floatValue - floatValue4 * 0.009F,
                        floatValue2 - floatValue4 * 0.015F,
                        floatValue4,
                        24,
                        floatValue6,
                        4)
                    * 0.9F);
        break;
      case 11:
        float floatValue49 =
            computeFloat(
                floatValue8
                    + computeFloat(floatValue7 + floatValue4 * 0.18F) * 1.8F
                    + computeFloat(floatValue7 * 3.0F - floatValue4 * 0.13F) * 0.35F);
        float floatValue50 = computeFloat2(0.6F - floatValue5 * 0.25F, 0.97F, floatValue49);
        float floatValue51 =
            0.65F
                + 0.35F
                    * computeFloat(
                        floatValue7 * 33.0F
                            + computeFloat(floatValue7 * 7.0F) * 3.0F
                            + floatValue4 * 0.6F);
        intValue = computeInt(199968, 1651034, floatValue9 * 0.4F);
        intValue =
            computeInt2(
                intValue,
                computeInt(
                    5716684,
                    4652992,
                    0.5F + 0.5F * computeFloat(floatValue8 * 2.0F + floatValue4 * 0.2F)),
                floatValue50 * (0.5F + floatValue51 * floatValue6 * 0.5F));
        intValue =
            computeInt2(
                intValue,
                11993055,
                floatValue50 * floatValue50 * floatValue51 * floatValue6 * 0.35F);
        intValue =
            computeInt2(
                intValue,
                12638719,
                computeFloat(floatValue, floatValue2, floatValue4 * 0.5F, 27, 0.23F, 5) * 0.5F);
        break;
      case 12:
        float floatValue52 = computeFloat(floatValue, floatValue2 + floatValue4 * 0.065F);
        float floatValue53 =
            0.5F
                + 0.5F
                    * computeFloat(
                        floatValue7 * 4.0F
                            + (floatValue52 - 0.5F) * (6.0F + floatValue5 * 9.0F)
                            + computeFloat(floatValue8 + floatValue4 * 1.1F) * 0.7F
                            + floatValue4 * 0.55F);
        float floatValue54 =
            computeFloat2(0.3F, 0.95F, floatValue53 * (0.6F + floatValue52 * 0.65F));
        intValue = computeInt(2294795, 14167058, computeFloat2(0.15F, 0.65F, floatValue53));
        intValue = computeInt(intValue, 16757290, floatValue54);
        intValue = computeInt(intValue, 16773557, floatValue54 * floatValue54 * 0.7F);
        intValue =
            computeInt2(
                intValue,
                16763237,
                computeFloat(
                    floatValue + computeFloat(floatValue4 * 0.3F) * 0.02F,
                    floatValue2 + floatValue4 * 0.08F,
                    floatValue4,
                    18,
                    floatValue6,
                    6));
        break;
      default:
        throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1402));
    }

    return computeInt(
        (intValue >> 16 & 0xFF) * floatValue3,
        (intValue >> 8 & 0xFF) * floatValue3,
        (intValue & 0xFF) * floatValue3);
  }

  public static float computeFloat(float floatValue, float floatValue2) {
    float floatValue3 = computeFloat(floatValue, floatValue2, 3) - 0.5F;
    return computeFloat(floatValue + floatValue3 * 0.18F, floatValue2 - floatValue3 * 0.12F, 4)
            * 0.55F
        + computeFloat(floatValue, floatValue2, 8) * 0.27F
        + computeFloat(floatValue, floatValue2, 16) * 0.13F
        + computeFloat(floatValue, floatValue2, 32) * 0.05F;
  }

  public static float computeFloat(float floatValue, float floatValue2, int intValue) {
    float floatValue3 = computeFloat2(floatValue) * intValue;
    float floatValue4 = computeFloat2(floatValue2) * intValue;
    int intValue2 = (int) floatValue3;
    int intValue3 = (int) floatValue4;
    float floatValue5 = computeFloat2(floatValue3);
    float floatValue6 = computeFloat2(floatValue4);
    floatValue5 = floatValue5 * floatValue5 * (3.0F - 2.0F * floatValue5);
    floatValue6 = floatValue6 * floatValue6 * (3.0F - 2.0F * floatValue6);
    return computeFloat(
        computeFloat(
            computeFloat(intValue2, intValue3, intValue),
            computeFloat(intValue2 + 1, intValue3, intValue),
            floatValue5),
        computeFloat(
            computeFloat(intValue2, intValue3 + 1, intValue),
            computeFloat(intValue2 + 1, intValue3 + 1, intValue),
            floatValue5),
        floatValue6);
  }

  public static float computeFloat2(float floatValue, float floatValue2, int intValue) {
    float floatValue3 = computeFloat2(floatValue) * intValue;
    float floatValue4 = computeFloat2(floatValue2) * intValue;
    int intValue2 = (int) floatValue3;
    int intValue3 = (int) floatValue4;
    float floatValue5 = 2.0F;

    for (int index = -1; index <= 1; index++) {
      for (int index2 = -1; index2 <= 1; index2++) {
        float floatValue6 =
            intValue2
                + index2
                + 0.18F
                + computeFloat(intValue2 + index2, intValue3 + index, intValue) * 0.64F
                - floatValue3;
        float floatValue7 =
            intValue3
                + index
                + 0.18F
                + computeFloat(intValue3 + index + 7, intValue2 + index2 + 3, intValue) * 0.64F
                - floatValue4;
        floatValue5 = Math.min(floatValue5, floatValue6 * floatValue6 + floatValue7 * floatValue7);
      }
    }

    return (float) Math.sqrt(floatValue5);
  }

  public static float computeFloat(
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      float floatValue4,
      int intValue2) {
    float floatValue5 = computeFloat2(floatValue) * intValue;
    float floatValue6 = computeFloat2(floatValue2) * intValue;
    int intValue3 = (int) floatValue5;
    int intValue4 = (int) floatValue6;
    float floatValue7 =
        computeFloat(intValue3 + intValue2 * 3, intValue4 + intValue2 * 7, intValue);
    if (!(floatValue4 <= 0.0F) && !(floatValue7 > floatValue4 * 0.48F)) {
      float floatValue8 =
          computeFloat2(floatValue5)
              - (0.22F + computeFloat(intValue3 + 3, intValue4 + 1, intValue) * 0.56F);
      float floatValue9 =
          computeFloat2(floatValue6)
              - (0.22F + computeFloat(intValue3 + 9, intValue4 + 5, intValue) * 0.56F);
      float floatValue10 = floatValue8 * floatValue8 + floatValue9 * floatValue9;
      float floatValue11 = Math.max(0.0F, 1.0F - floatValue10 * 85.0F);
      float floatValue12 = Math.max(0.0F, 1.0F - floatValue10 * 15.0F);
      float floatValue13 =
          0.5F
              + 0.5F
                  * computeFloat(floatValue3 * (1.1F + floatValue7 * 5.0F) + floatValue7 * 173.0F);
      float floatValue14 =
          Math.max(0.0F, 1.0F - Math.abs(floatValue8) * 55.0F)
                  * Math.max(0.0F, 1.0F - Math.abs(floatValue9) * 5.0F)
              + Math.max(0.0F, 1.0F - Math.abs(floatValue9) * 55.0F)
                  * Math.max(0.0F, 1.0F - Math.abs(floatValue8) * 5.0F);
      return (floatValue11 * floatValue11
              + floatValue12 * floatValue12 * 0.16F
              + floatValue14 * 0.3F)
          * (0.4F + floatValue13 * 0.8F);
    } else {
      return 0.0F;
    }
  }

  public static float computeFloat(int intValue, int intValue2, int index) {
    return index > 0
            && index < amountArray2.length
            && intValue >= -1
            && intValue2 >= -1
            && intValue < index + 50
            && intValue2 < index + 50
        ? amountArray2[index][(intValue2 + 1) * (index + 51) + intValue + 1]
        : computeFloat2(intValue, intValue2, index);
  }

  public static float computeFloat2(int intValue, int intValue2, int intValue3) {
    int intValue4 =
        Math.floorMod(intValue, intValue3) * 374761393
            + Math.floorMod(intValue2, intValue3) * 668265263
            + 1540483477;
    intValue4 = (intValue4 ^ intValue4 >>> 13) * 1274126177;
    return ((intValue4 ^ intValue4 >>> 16) & 65535) / 65535.0F;
  }

  public static float computeFloat(float floatValue) {
    return amountArray[(int) Math.floor(floatValue * 1303.7972F) & 8191];
  }

  public static float computeFloat2(float floatValue) {
    return floatValue - (float) Math.floor(floatValue);
  }

  public static float computeFloat3(float floatValue) {
    return Math.clamp(floatValue, 0.0F, 1.0F);
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }

  public static float computeFloat2(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = computeFloat3((floatValue3 - floatValue) / (floatValue2 - floatValue));
    return floatValue4 * floatValue4 * (3.0F - 2.0F * floatValue4);
  }

  public static float computeFloat2(float floatValue, float floatValue2) {
    return computeFloat2(floatValue2, 1.0F, Math.abs(floatValue));
  }

  public static int computeInt(int intValue, int intValue2, float floatValue) {
    floatValue = computeFloat3(floatValue);
    return computeInt(
        computeFloat(intValue >> 16 & 0xFF, intValue2 >> 16 & 0xFF, floatValue),
        computeFloat(intValue >> 8 & 0xFF, intValue2 >> 8 & 0xFF, floatValue),
        computeFloat(intValue & 0xFF, intValue2 & 0xFF, floatValue));
  }

  public static int computeInt2(int intValue, int intValue2, float floatValue) {
    return computeInt(
        (intValue >> 16 & 0xFF) + (intValue2 >> 16 & 0xFF) * floatValue,
        (intValue >> 8 & 0xFF) + (intValue2 >> 8 & 0xFF) * floatValue,
        (intValue & 0xFF) + (intValue2 & 0xFF) * floatValue);
  }

  public static int computeInt(float floatValue, float floatValue2, float floatValue3) {
    return 0xFF000000
        | Math.clamp(Math.round(floatValue), 0, 255) << 16
        | Math.clamp(Math.round(floatValue2), 0, 255) << 8
        | Math.clamp(Math.round(floatValue3), 0, 255);
  }

  static {
    for (int index = 0; index < amountArray.length; index++) {
      amountArray[index] = (float) Math.sin(index * Math.PI * 2.0 / amountArray.length);
    }

    for (int index2 = 1; index2 < amountArray2.length; index2++) {
      int intValue = index2 + 51;
      float[] floatValues = amountArray2[index2] = new float[intValue * intValue];

      for (int index3 = -1; index3 < index2 + 50; index3++) {
        for (int index4 = -1; index4 < index2 + 50; index4++) {
          floatValues[(index3 + 1) * intValue + index4 + 1] = computeFloat2(index4, index3, index2);
        }
      }
    }
  }
}

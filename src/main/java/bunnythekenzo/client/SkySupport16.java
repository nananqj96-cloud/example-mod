package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;

public final class SkySupport16 {
  public final List list = new ArrayList();
  public final List list2 = new ArrayList();

  public SkySupport24 getSkySupport24(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue,
      int intValue2,
      int intValue3) {
    SkySupport24 skySupport24 =
        new SkySupport24(
            floatValue,
            floatValue2,
            floatValue3,
            floatValue4,
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue8,
            intValue,
            intValue2,
            intValue3);
    this.list.add(skySupport24);
    return skySupport24;
  }

  public void apply(
      SkySupport24 skySupport24, SkySupport24 skySupport242, SkySupport24 skySupport243) {
    float floatValue = skySupport242.amount - skySupport24.amount;
    float floatValue2 = skySupport242.amount2 - skySupport24.amount2;
    float floatValue3 = skySupport242.amount3 - skySupport24.amount3;
    float floatValue4 = skySupport243.amount - skySupport24.amount;
    float floatValue5 = skySupport243.amount2 - skySupport24.amount2;
    float floatValue6 = skySupport243.amount3 - skySupport24.amount3;
    float floatValue7 =
        (floatValue2 * floatValue6 - floatValue3 * floatValue5)
                * (skySupport24.amount4 + skySupport242.amount4 + skySupport243.amount4)
            + (floatValue3 * floatValue4 - floatValue * floatValue6)
                * (skySupport24.amount5 + skySupport242.amount5 + skySupport243.amount5)
            + (floatValue * floatValue5 - floatValue2 * floatValue4)
                * (skySupport24.amount6 + skySupport242.amount6 + skySupport243.amount6);
    this.list2.add(
        floatValue7 >= 0.0F
            ? new SkySupport19(skySupport24, skySupport242, skySupport243)
            : new SkySupport19(skySupport24, skySupport243, skySupport242));
  }

  public void apply(
      SkySupport24 skySupport24,
      SkySupport24 skySupport242,
      SkySupport24 skySupport243,
      SkySupport24 skySupport244) {
    this.apply(skySupport24, skySupport242, skySupport243);
    this.apply(skySupport24, skySupport243, skySupport244);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float[] floatValues,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      boolean flag,
      boolean flag2) {
    for (int index = 0; index < floatValues.length / 2 - 1; index++) {
      float floatValue3 = floatValues[index * 2];
      float floatValue4 = floatValues[index * 2 + 1];
      float floatValue5 = floatValues[index * 2 + 2];
      float floatValue6 = floatValues[index * 2 + 3];
      float floatValue7 = (floatValue4 - floatValue6) / (floatValue5 - floatValue3);
      float floatValue8 = floatValue7;
      if (flag2) {
        int index2 = Math.max(0, index - 1) * 2;
        int index3 = Math.min(floatValues.length / 2 - 1, index + 2) * 2;
        floatValue7 = (floatValues[index2 + 1] - floatValue6) / (floatValue5 - floatValues[index2]);
        floatValue8 = (floatValue4 - floatValues[index3 + 1]) / (floatValues[index3] - floatValue3);
      }

      float floatValue9 = flag ? -1.0F : 1.0F;

      for (int index4 = 0; index4 < intValue; index4++) {
        float floatValue10 = (float) (index4 * Math.PI * 2.0 / intValue);
        float floatValue11 = (float) ((index4 + 1) * Math.PI * 2.0 / intValue);
        float floatValue12 = (float) Math.cos(floatValue10);
        float floatValue13 = (float) Math.sin(floatValue10);
        float floatValue14 = (float) Math.cos(floatValue11);
        float floatValue15 = (float) Math.sin(floatValue11);
        this.apply(
            this.getSkySupport24(
                floatValue + floatValue12 * floatValue4,
                floatValue2 + floatValue13 * floatValue4,
                floatValue3,
                floatValue12 * floatValue9,
                floatValue13 * floatValue9,
                floatValue7 * floatValue9,
                (float) index4 / intValue,
                floatValue3,
                intValue2,
                intValue3,
                intValue4),
            this.getSkySupport24(
                floatValue + floatValue14 * floatValue4,
                floatValue2 + floatValue15 * floatValue4,
                floatValue3,
                floatValue14 * floatValue9,
                floatValue15 * floatValue9,
                floatValue7 * floatValue9,
                (index4 + 1.0F) / intValue,
                floatValue3,
                intValue2,
                intValue3,
                intValue4),
            this.getSkySupport24(
                floatValue + floatValue14 * floatValue6,
                floatValue2 + floatValue15 * floatValue6,
                floatValue5,
                floatValue14 * floatValue9,
                floatValue15 * floatValue9,
                floatValue8 * floatValue9,
                (index4 + 1.0F) / intValue,
                floatValue5,
                intValue2,
                intValue3,
                intValue4),
            this.getSkySupport24(
                floatValue + floatValue12 * floatValue6,
                floatValue2 + floatValue13 * floatValue6,
                floatValue5,
                floatValue12 * floatValue9,
                floatValue13 * floatValue9,
                floatValue8 * floatValue9,
                (float) index4 / intValue,
                floatValue5,
                intValue2,
                intValue3,
                intValue4));
      }
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      int intValue2,
      int intValue3) {
    this.render(
        floatValue,
        floatValue2,
        new float[] {
          floatValue3,
          floatValue5 - 0.002F,
          floatValue3 + 0.004F,
          floatValue5,
          floatValue4 - 0.004F,
          floatValue5,
          floatValue4,
          floatValue5 - 0.002F
        },
        48,
        intValue,
        intValue2,
        intValue3,
        false,
        false);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue) {
    float floatValue6 = floatValue3 + 0.29F * floatValue4;
    this.render(
        floatValue,
        floatValue2,
        new float[] {
          floatValue3,
          floatValue5,
          floatValue3 + 0.035F,
          floatValue5 * 0.98F,
          floatValue3 + 0.1F,
          floatValue5 * 0.8F,
          floatValue3 + 0.18F,
          floatValue5 * 0.55F,
          floatValue6,
          floatValue5 * 0.4F
        },
        48,
        6,
        -5602960,
        intValue,
        false,
        true);
    this.render(
        floatValue,
        floatValue2,
        new float[] {
          floatValue3 + 0.002F,
          floatValue5 * 0.91F,
          floatValue3 + 0.07F,
          floatValue5 * 0.77F,
          floatValue3 + 0.17F,
          floatValue5 * 0.47F,
          floatValue6 - 0.004F,
          floatValue5 * 0.29F
        },
        48,
        1,
        -14668741,
        intValue,
        true,
        true);
    this.render(
        floatValue,
        floatValue2,
        floatValue3 - 0.009F,
        floatValue3 + 0.017F,
        floatValue5 * 1.025F,
        2,
        -6312005,
        intValue);

    for (int index = 0; index < 40; index++) {
      float floatValue7 = (float) (index * Math.PI * 2.0 / 40.0);
      float floatValue8 = (float) ((index + 1) * Math.PI * 2.0 / 40.0);
      float floatValue9 = floatValue5 * 0.3F;
      this.apply(
          this.getSkySupport24(
              floatValue,
              floatValue2,
              floatValue6,
              0.0F,
              0.0F,
              -1.0F,
              0.5F,
              0.5F,
              5,
              -11416085,
              intValue),
          this.getSkySupport24(
              floatValue + floatValue9 * (float) Math.cos(floatValue7),
              floatValue2 + floatValue9 * (float) Math.sin(floatValue7),
              floatValue6,
              0.0F,
              0.0F,
              -1.0F,
              0.0F,
              0.0F,
              5,
              -11416085,
              intValue),
          this.getSkySupport24(
              floatValue + floatValue9 * (float) Math.cos(floatValue8),
              floatValue2 + floatValue9 * (float) Math.sin(floatValue8),
              floatValue6,
              0.0F,
              0.0F,
              -1.0F,
              1.0F,
              0.0F,
              5,
              -11416085,
              intValue));
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5,
      boolean flag) {
    for (int index = 0; index < intValue2; index++) {
      for (int index2 = 0; index2 < intValue; index2++) {
        SkySupport24[] skySupport24Array = new SkySupport24[4];
        int[] intValues = new int[] {0, 1, 1, 0};
        int[] intValues2 = new int[] {0, 0, 1, 1};

        for (int index3 = 0; index3 < 4; index3++) {
          float floatValue7 = (float) (index2 + intValues[index3]) / intValue;
          float floatValue8 = (float) (index + intValues2[index3]) / intValue2;
          float floatValue9 = (float) (floatValue7 * Math.PI * 2.0);
          float floatValue10 =
              (float) (flag ? floatValue8 * Math.PI / 2.0 : (floatValue8 - 0.5) * Math.PI);
          float floatValue11 = (float) (Math.cos(floatValue9) * Math.cos(floatValue10));
          float floatValue12 = (float) Math.sin(floatValue10);
          float floatValue13 = (float) (Math.sin(floatValue9) * Math.cos(floatValue10));
          skySupport24Array[index3] =
              this.getSkySupport24(
                  floatValue + floatValue4 * floatValue11,
                  floatValue2 + floatValue5 * floatValue12,
                  floatValue3 + floatValue6 * floatValue13,
                  floatValue11 / floatValue4,
                  floatValue12 / floatValue5,
                  floatValue13 / floatValue6,
                  floatValue7,
                  floatValue8,
                  intValue3,
                  intValue4,
                  intValue5);
        }

        this.apply(
            skySupport24Array[0], skySupport24Array[1], skySupport24Array[2], skySupport24Array[3]);
      }
    }
  }

  public void render(
      float[] floatValues,
      float floatValue,
      float floatValue2,
      int intValue,
      int intValue2,
      int intValue3) {
    float floatValue3 = 0.0F;
    float floatValue4 = 0.0F;

    for (int index = 0; index < 4; index++) {
      floatValue3 += floatValues[index * 2] * 0.25F;
      floatValue4 += floatValues[index * 2 + 1] * 0.25F;
    }

    SkySupport24[] skySupport24Array = new SkySupport24[4];
    SkySupport24[] skySupport24Array2 = new SkySupport24[4];

    for (int index2 = 0; index2 < 4; index2++) {
      float floatValue5 = floatValue3 + (floatValues[index2 * 2] - floatValue3) * 0.96F;
      float floatValue6 = floatValue4 + (floatValues[index2 * 2 + 1] - floatValue4) * 0.96F;
      skySupport24Array[index2] =
          this.getSkySupport24(
              floatValue5,
              floatValue + floatValue2 * 0.5F,
              floatValue6,
              0.0F,
              1.0F,
              0.0F,
              floatValue5,
              floatValue6,
              intValue,
              intValue2,
              intValue3);
      skySupport24Array2[index2] =
          this.getSkySupport24(
              floatValue5,
              floatValue - floatValue2 * 0.5F,
              floatValue6,
              0.0F,
              -1.0F,
              0.0F,
              floatValue5,
              floatValue6,
              intValue,
              intValue2,
              intValue3);
    }

    this.apply(
        skySupport24Array[0], skySupport24Array[1], skySupport24Array[2], skySupport24Array[3]);
    this.apply(
        skySupport24Array2[0], skySupport24Array2[1], skySupport24Array2[2], skySupport24Array2[3]);

    for (int index3 = 0; index3 < 4; index3++) {
      int index4 = (index3 + 1) % 4;
      float floatValue7 = -(floatValues[index4 * 2 + 1] - floatValues[index3 * 2 + 1]);
      float floatValue8 = floatValues[index4 * 2] - floatValues[index3 * 2];
      if (floatValue7 * ((floatValues[index3 * 2] + floatValues[index4 * 2]) * 0.5F - floatValue3)
              + floatValue8
                  * ((floatValues[index3 * 2 + 1] + floatValues[index4 * 2 + 1]) * 0.5F
                      - floatValue4)
          < 0.0F) {
        floatValue7 = -floatValue7;
        floatValue8 = -floatValue8;
      }

      float floatValue9 =
          (float) Math.sqrt(floatValue7 * floatValue7 + floatValue8 * floatValue8) * 0.38F;
      this.apply(
          this.getSkySupport24(
              skySupport24Array[index3].amount,
              skySupport24Array[index3].amount2,
              skySupport24Array[index3].amount3,
              floatValue7,
              floatValue9,
              floatValue8,
              0.0F,
              0.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              skySupport24Array[index4].amount,
              skySupport24Array[index4].amount2,
              skySupport24Array[index4].amount3,
              floatValue7,
              floatValue9,
              floatValue8,
              1.0F,
              0.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              floatValues[index4 * 2],
              floatValue,
              floatValues[index4 * 2 + 1],
              floatValue7,
              floatValue9,
              floatValue8,
              1.0F,
              1.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              floatValues[index3 * 2],
              floatValue,
              floatValues[index3 * 2 + 1],
              floatValue7,
              floatValue9,
              floatValue8,
              0.0F,
              1.0F,
              intValue,
              intValue2,
              intValue3));
      this.apply(
          this.getSkySupport24(
              floatValues[index3 * 2],
              floatValue,
              floatValues[index3 * 2 + 1],
              floatValue7,
              -floatValue9,
              floatValue8,
              0.0F,
              0.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              floatValues[index4 * 2],
              floatValue,
              floatValues[index4 * 2 + 1],
              floatValue7,
              -floatValue9,
              floatValue8,
              1.0F,
              0.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              skySupport24Array2[index4].amount,
              skySupport24Array2[index4].amount2,
              skySupport24Array2[index4].amount3,
              floatValue7,
              -floatValue9,
              floatValue8,
              1.0F,
              1.0F,
              intValue,
              intValue2,
              intValue3),
          this.getSkySupport24(
              skySupport24Array2[index3].amount,
              skySupport24Array2[index3].amount2,
              skySupport24Array2[index3].amount3,
              floatValue7,
              -floatValue9,
              floatValue8,
              0.0F,
              1.0F,
              intValue,
              intValue2,
              intValue3));
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      int intValue2,
      int intValue3) {
    for (int index = 0; index < 64; index++) {
      float floatValue7 = (float) (index * Math.PI * 2.0 / 64.0);
      float floatValue8 = (float) ((index + 1) * Math.PI * 2.0 / 64.0);
      this.render(
          floatValue + floatValue4 * (float) Math.cos(floatValue7),
          floatValue2,
          floatValue3 + floatValue5 * (float) Math.sin(floatValue7),
          floatValue + floatValue4 * (float) Math.cos(floatValue8),
          floatValue2,
          floatValue3 + floatValue5 * (float) Math.sin(floatValue8),
          floatValue6,
          intValue,
          intValue2,
          intValue3);
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue,
      int intValue2,
      int intValue3) {
    float floatValue8 = floatValue4 - floatValue;
    float floatValue9 = floatValue5 - floatValue2;
    float floatValue10 = floatValue6 - floatValue3;
    float floatValue11 =
        (float)
            Math.sqrt(
                floatValue8 * floatValue8
                    + floatValue9 * floatValue9
                    + floatValue10 * floatValue10);
    floatValue8 /= floatValue11;
    floatValue9 /= floatValue11;
    floatValue10 /= floatValue11;
    float floatValue12 = -floatValue10;
    float floatValue13 = floatValue8;
    float floatValue14 =
        (float) Math.sqrt(floatValue12 * floatValue12 + floatValue13 * floatValue13);
    if (floatValue14 < 0.001F) {
      floatValue12 = 1.0F;
      floatValue13 = 0.0F;
      floatValue14 = 1.0F;
    }

    floatValue12 /= floatValue14;
    floatValue13 /= floatValue14;
    float floatValue15 = floatValue9 * floatValue13 - floatValue10 * 0.0F;
    float floatValue16 = floatValue10 * floatValue12 - floatValue8 * floatValue13;
    float floatValue17 = floatValue8 * 0.0F - floatValue9 * floatValue12;

    for (int index = 0; index < 6; index++) {
      SkySupport24[] skySupport24Array = new SkySupport24[4];

      for (int index2 = 0; index2 < 4; index2++) {
        float floatValue18 =
            (float) ((index + (index2 != 1 && index2 != 2 ? 0 : 1)) * Math.PI / 3.0);
        float floatValue19 = (float) Math.cos(floatValue18);
        float floatValue20 = (float) Math.sin(floatValue18);
        float floatValue21 = floatValue12 * floatValue19 + floatValue15 * floatValue20;
        float floatValue22 = 0.0F * floatValue19 + floatValue16 * floatValue20;
        float floatValue23 = floatValue13 * floatValue19 + floatValue17 * floatValue20;
        boolean flag = index2 >= 2;
        skySupport24Array[index2] =
            this.getSkySupport24(
                (flag ? floatValue4 : floatValue) + floatValue21 * floatValue7,
                (flag ? floatValue5 : floatValue2) + floatValue22 * floatValue7,
                (flag ? floatValue6 : floatValue3) + floatValue23 * floatValue7,
                floatValue21,
                floatValue22,
                floatValue23,
                index / 6.0F,
                flag ? 1.0F : 0.0F,
                intValue,
                intValue2,
                intValue3);
      }

      this.apply(
          skySupport24Array[0], skySupport24Array[1], skySupport24Array[2], skySupport24Array[3]);
    }
  }

  public void render(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = (float) Math.cos(floatValue);
    float floatValue5 = (float) Math.sin(floatValue);
    float floatValue6 = floatValue4 * floatValue3;
    float floatValue7 = floatValue5 * floatValue3;

    for (int index = 0; index < 6; index++) {
      float floatValue8 = (float) (index * Math.PI / 3.0);
      float floatValue9 = (float) ((index + 1) * Math.PI / 3.0);
      this.apply(
          this.getSkySupport24(
              floatValue6,
              floatValue7,
              floatValue2,
              floatValue4,
              floatValue5,
              0.0F,
              0.0F,
              0.0F,
              2,
              -6312005,
              0),
          this.getSkySupport24(
              floatValue6 - floatValue5 * 0.01F * (float) Math.cos(floatValue8),
              floatValue7 + floatValue4 * 0.01F * (float) Math.cos(floatValue8),
              floatValue2 + 0.01F * (float) Math.sin(floatValue8),
              floatValue4,
              floatValue5,
              0.0F,
              0.0F,
              0.0F,
              2,
              -6312005,
              0),
          this.getSkySupport24(
              floatValue6 - floatValue5 * 0.01F * (float) Math.cos(floatValue9),
              floatValue7 + floatValue4 * 0.01F * (float) Math.cos(floatValue9),
              floatValue2 + 0.01F * (float) Math.sin(floatValue9),
              floatValue4,
              floatValue5,
              0.0F,
              0.0F,
              0.0F,
              2,
              -6312005,
              0));
    }
  }

  public void apply(boolean flag) {
    float floatValue = flag ? 0.087F : 0.081F;
    float floatValue2 = flag ? 32.0F : 0.0F;
    float[][] floatValuesArray =
        new float[][] {
          {-1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F},
          {1.0F, 1.0F, 1.0F, 1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F},
          {-1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F, 1.0F, 1.0F},
          {1.0F, 1.0F, -1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F},
          {-1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, -1.0F},
          {-1.0F, -1.0F, 1.0F, -1.0F, -1.0F, -1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -1.0F, 1.0F}
        };
    float[][] floatValuesArray2 =
        new float[][] {
          {0.0F, 0.0F, 1.0F},
          {1.0F, 0.0F, 0.0F},
          {-1.0F, 0.0F, 0.0F},
          {0.0F, 0.0F, -1.0F},
          {0.0F, 1.0F, 0.0F},
          {0.0F, -1.0F, 0.0F}
        };
    float[][] floatValuesArray3 =
        new float[][] {
          {8.0F, 8.0F, 16.0F, 16.0F},
          {0.0F, 8.0F, 8.0F, 16.0F},
          {16.0F, 8.0F, 24.0F, 16.0F},
          {24.0F, 8.0F, 32.0F, 16.0F},
          {8.0F, 0.0F, 16.0F, 8.0F},
          {16.0F, 0.0F, 24.0F, 8.0F}
        };

    for (int index = 0; index < 6; index++) {
      SkySupport24[] skySupport24Array = new SkySupport24[4];

      for (int index2 = 0; index2 < 4; index2++) {
        skySupport24Array[index2] =
            this.getSkySupport24(
                floatValuesArray[index][index2 * 3] * floatValue,
                floatValuesArray[index][index2 * 3 + 1] * floatValue,
                floatValuesArray[index][index2 * 3 + 2] * floatValue,
                floatValuesArray2[index][0],
                floatValuesArray2[index][1],
                floatValuesArray2[index][2],
                (floatValuesArray3[index][index2 < 2 ? 0 : 2] + floatValue2) / 64.0F,
                floatValuesArray3[index][index2 != 0 && index2 != 3 ? 3 : 1] / 64.0F,
                7,
                -1,
                5);
      }

      this.apply(
          skySupport24Array[0], skySupport24Array[1], skySupport24Array[2], skySupport24Array[3]);
    }
  }
}

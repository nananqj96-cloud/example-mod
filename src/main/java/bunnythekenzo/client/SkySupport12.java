package bunnythekenzo.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport12 {
  public static final int count = 0;
  public static final int count2 = 1;
  public static final int count3 = 2;
  public static final int count4 = 3;
  public static final int count5 = 4;
  public static final int count6 = 5;
  public static final int count7 = 6;
  public static final int count8 = 7;
  public static final int count9 = 8;
  public static final int count10 = 9;
  public static final int count11 = 0;
  public static final int count12 = 1;
  public static final int count13 = 2;
  public static final int count14 = 3;
  public static final int count15 = 4;
  public static final int count16 = 5;
  public static final int count17 = 6;
  public static final int count18 = 7;
  public static final int count19 = -1972505;
  public static final int count20 = -14668741;
  public static final int count21 = -6312005;
  public static final int count22 = -5602960;
  public static final int count23 = -11416085;
  public static final float amount = -1.23F;
  public static final float amount2 = -1.12F;
  public static final float amount3 = 0.48F;
  public static final float amount4 = -0.045F;
  public static final SkySupport24[] skySupport24Array;
  public static final SkySupport19[] skySupport19Array;
  public static final SkySupport19[] skySupport19Array2;
  public static final Comparator<SkySupport19> comparator =
      (skySupport19, skySupport192) -> {
        return Float.compare(skySupport192.amount, skySupport19.amount);
      };
  public static final float[][] amountArray = new float[8][12];
  public static final float[] amountArray2 = new float[3];
  public static float amount5;
  public static float amount6;
  public static float amount7;
  public static float amount8;
  public static float amount9;
  public static float amount10;
  public static float amount11;
  public static float amount12;
  public static float amount13;
  public static float amount14;
  public static float amount15;
  public static float amount16;
  public static float amount17;

  public static void render(
      float[] floatValues,
      float[] floatValues2,
      float[] floatValues3,
      float[] floatValues4,
      float amount172,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    amount5 = floatValues[0];
    amount6 = floatValues[1];
    amount7 = floatValues[2];
    amount8 = floatValues2[0];
    amount9 = floatValues2[1];
    amount10 = floatValues2[2];
    amount11 = floatValues3[0];
    amount12 = floatValues3[1];
    amount13 = floatValues3[2];
    amount14 = floatValues4[0];
    amount15 = floatValues4[1];
    amount16 = floatValues4[2];
    amount17 = amount172;

    for (int index = 0; index < amountArray.length; index++) {
      render(index, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    float floatValue4 = 0.035F * (float) Math.sin(floatValue * 1.9F) * (0.35F + floatValue2);
    float floatValue5 = 0.03F * (float) Math.sin(floatValue * 1.4F + 0.6F) * (0.35F + floatValue2);
    render(1, 0.0F, 0.0F, -0.9F, floatValue4, floatValue5, 0.0F, 0.0F, 0.0F, 0.0F);
    render(6, -0.48F, -0.045F, -0.88F, floatValue4 * 0.7F, -floatValue5, 0.0F, 0.0F, 0.0F, 0.0F);
    render(7, 0.48F, -0.045F, -0.88F, floatValue4 * 0.7F, floatValue5, 0.0F, 0.0F, 0.0F, 0.0F);
    render(
        2,
        -0.48F,
        -0.07F,
        -0.82F,
        0.075F * (float) Math.sin(floatValue * 1.3F) + 0.12F * floatValue2,
        0.0F,
        0.0F,
        0.0F,
        0.0F,
        0.0F);
    render(
        3,
        0.48F,
        -0.07F,
        -0.82F,
        -0.075F * (float) Math.sin(floatValue * 1.3F) + 0.12F * floatValue2,
        0.0F,
        0.0F,
        0.0F,
        0.0F,
        0.0F);
    float floatValue6 = floatValue3 * floatValue3 * (3.0F - 2.0F * floatValue3);
    render(4, 0.0F, 0.3F, -0.098F, -1.22F * floatValue6, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    float floatValue7 = 0.42F * (float) Math.sin(floatValue * 0.85F) * floatValue6;
    render(
        5,
        0.0F,
        0.0F,
        0.0F,
        -0.75F,
        floatValue7,
        0.0F,
        0.0F,
        0.235F + 0.195F * floatValue6,
        0.245F);
  }

  public static void render(int intValue, float floatValue, float[] floatValues) {
    render(intValue, floatValue, 0.0F, 0.0F, floatValues);
  }

  public static void render(
      int intValue, float floatValue, float floatValue2, float floatValue3, float[] floatValues) {
    int index = intValue == 0 ? 1 : (intValue == 1 ? 6 : 7);
    float floatValue4 = intValue == 0 ? 0.0F : (intValue == 1 ? -0.48F : 0.48F);
    float floatValue5 = intValue == 0 ? 0.0F : -0.045F;
    float floatValue6 = (intValue == 0 ? -1.23F : -1.12F) - floatValue;
    render(
        amountArray[index],
        floatValue4 + floatValue2,
        floatValue5 + floatValue3,
        floatValue6,
        amountArray2);
    floatValues[0] =
        amount5
            + amount17
                * (amountArray2[0] * amount8
                    + amountArray2[1] * amount11
                    + amountArray2[2] * amount14);
    floatValues[1] =
        amount6
            + amount17
                * (amountArray2[0] * amount9
                    + amountArray2[1] * amount12
                    + amountArray2[2] * amount15);
    floatValues[2] =
        amount7
            + amount17
                * (amountArray2[0] * amount10
                    + amountArray2[1] * amount13
                    + amountArray2[2] * amount16);
  }

  public static void render(
      Immediate immediate,
      Identifier identifier,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    for (SkySupport24 skySupport24 : skySupport24Array) {
      float[] floatValues = amountArray[skySupport24.count3];
      render(
          floatValues,
          skySupport24.amount,
          skySupport24.amount2,
          skySupport24.amount3,
          amountArray2);
      skySupport24.amount15 = amountArray2[1];
      skySupport24.amount9 =
          (amount5
                  + amount17
                      * (amountArray2[0] * amount8
                          + amountArray2[1] * amount11
                          + amountArray2[2] * amount14))
              * 100.0F;
      skySupport24.amount10 =
          (amount6
                  + amount17
                      * (amountArray2[0] * amount9
                          + amountArray2[1] * amount12
                          + amountArray2[2] * amount15))
              * 100.0F;
      skySupport24.amount11 =
          (amount7
                  + amount17
                      * (amountArray2[0] * amount10
                          + amountArray2[1] * amount13
                          + amountArray2[2] * amount16))
              * 100.0F;
      float floatValue4 =
          floatValues[0] * skySupport24.amount4
              + floatValues[1] * skySupport24.amount5
              + floatValues[2] * skySupport24.amount6;
      float floatValue5 =
          floatValues[3] * skySupport24.amount4
              + floatValues[4] * skySupport24.amount5
              + floatValues[5] * skySupport24.amount6;
      float floatValue6 =
          floatValues[6] * skySupport24.amount4
              + floatValues[7] * skySupport24.amount5
              + floatValues[8] * skySupport24.amount6;
      skySupport24.amount12 =
          floatValue4 * amount8 + floatValue5 * amount11 + floatValue6 * amount14;
      skySupport24.amount13 =
          floatValue4 * amount9 + floatValue5 * amount12 + floatValue6 * amount15;
      skySupport24.amount14 =
          floatValue4 * amount10 + floatValue5 * amount13 + floatValue6 * amount16;
    }

    int index = 0;

    for (SkySupport19 skySupport19 : skySupport19Array) {
      if (skySupport19.skySupport24.count != 7 || identifier != null) {
        SkySupport24 skySupport242 = skySupport19.skySupport24;
        SkySupport24 skySupport2422 = skySupport19.skySupport242;
        SkySupport24 skySupport243 = skySupport19.skySupport243;
        float floatValue7 = skySupport2422.amount9 - skySupport242.amount9;
        float floatValue8 = skySupport2422.amount10 - skySupport242.amount10;
        float floatValue9 = skySupport2422.amount11 - skySupport242.amount11;
        float floatValue10 = skySupport243.amount9 - skySupport242.amount9;
        float floatValue11 = skySupport243.amount10 - skySupport242.amount10;
        float floatValue12 = skySupport243.amount11 - skySupport242.amount11;
        if (skySupport242.count == 4
            || !((floatValue8 * floatValue12 - floatValue9 * floatValue11) * skySupport242.amount9
                    + (floatValue9 * floatValue10 - floatValue7 * floatValue12)
                        * skySupport242.amount10
                    + (floatValue7 * floatValue11 - floatValue8 * floatValue10)
                        * skySupport242.amount11
                >= 0.0F)) {
          float floatValue13 =
              (skySupport242.amount9 + skySupport2422.amount9 + skySupport243.amount9) / 3.0F;
          float floatValue14 =
              (skySupport242.amount10 + skySupport2422.amount10 + skySupport243.amount10) / 3.0F;
          float floatValue15 =
              (skySupport242.amount11 + skySupport2422.amount11 + skySupport243.amount11) / 3.0F;
          skySupport19.amount =
              floatValue13 * floatValue13
                  + floatValue14 * floatValue14
                  + floatValue15 * floatValue15;
          skySupport19Array2[index++] = skySupport19;
        }
      }
    }

    int intValue = Math.round(floatValue2 % (float) (Math.PI * 2) * 4096.0F);
    int intValue2 = Math.round(Math.clamp(floatValue3, 0.0F, 1.2F) * 16384.0F);
    int value = ArgbColors.lerp(SkyColorPalette.computeInt(), -2365198, 0.24F);
    GpuTextureView outputDepthTextureOverride = SkySupport23.begin();

    try {
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.rocket(identifier, false));
      int index2 = 0;

      for (int index3 = 0; index3 < index; index3++) {
        SkySupport19 skySupport192 = skySupport19Array2[index3];
        if (skySupport192.skySupport24.count == 4) {
          skySupport19Array2[index2++] = skySupport192;
        } else {
          render(buffer, skySupport192.skySupport24, floatValue, intValue, intValue2, value);
          render(buffer, skySupport192.skySupport242, floatValue, intValue, intValue2, value);
          render(buffer, skySupport192.skySupport243, floatValue, intValue, intValue2, value);
        }
      }

      immediate.draw();
      Arrays.sort(skySupport19Array2, 0, index2, comparator);
      buffer = immediate.getBuffer(SkyPipelines.rocket(identifier, true));

      for (int index4 = 0; index4 < index2; index4++) {
        SkySupport19 skySupport193 = skySupport19Array2[index4];
        render(buffer, skySupport193.skySupport24, floatValue, intValue, intValue2, value);
        render(buffer, skySupport193.skySupport242, floatValue, intValue, intValue2, value);
        render(buffer, skySupport193.skySupport243, floatValue, intValue, intValue2, value);
      }

      immediate.draw();
    } finally {
      RenderSystem.outputDepthTextureOverride = outputDepthTextureOverride;
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      SkySupport24 skySupport24,
      float floatValue,
      int intValue,
      int intValue2,
      int intValue3) {
    int intValue4 = skySupport24.count == 3 ? intValue3 : skySupport24.count2;
    vertexConsumer
        .vertex(skySupport24.amount9, skySupport24.amount10, skySupport24.amount11)
        .color(ArgbColors.withOpacity(intValue4, floatValue))
        .texture(skySupport24.amount7, skySupport24.amount8)
        .overlay(skySupport24.count, Math.round(skySupport24.amount15 * 8192.0F))
        .light(intValue, intValue2)
        .normal(skySupport24.amount12, skySupport24.amount13, skySupport24.amount14);
  }

  public static void render(
      int index,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9) {
    float floatValue10 = (float) Math.cos(floatValue4);
    float floatValue11 = (float) Math.sin(floatValue4);
    float floatValue12 = (float) Math.cos(floatValue5);
    float floatValue13 = (float) Math.sin(floatValue5);
    float floatValue14 = (float) Math.cos(floatValue6);
    float floatValue15 = (float) Math.sin(floatValue6);
    float[] floatValues = amountArray[index];
    floatValues[0] = floatValue14 * floatValue12;
    floatValues[1] = floatValue14 * floatValue13 * floatValue11 - floatValue15 * floatValue10;
    floatValues[2] = floatValue14 * floatValue13 * floatValue10 + floatValue15 * floatValue11;
    floatValues[3] = floatValue15 * floatValue12;
    floatValues[4] = floatValue15 * floatValue13 * floatValue11 + floatValue14 * floatValue10;
    floatValues[5] = floatValue15 * floatValue13 * floatValue10 - floatValue14 * floatValue11;
    floatValues[6] = -floatValue13;
    floatValues[7] = floatValue12 * floatValue11;
    floatValues[8] = floatValue12 * floatValue10;
    floatValues[9] =
        floatValue
            + floatValue7
            - floatValues[0] * floatValue
            - floatValues[1] * floatValue2
            - floatValues[2] * floatValue3;
    floatValues[10] =
        floatValue2
            + floatValue8
            - floatValues[3] * floatValue
            - floatValues[4] * floatValue2
            - floatValues[5] * floatValue3;
    floatValues[11] =
        floatValue3
            + floatValue9
            - floatValues[6] * floatValue
            - floatValues[7] * floatValue2
            - floatValues[8] * floatValue3;
  }

  public static void render(
      float[] floatValues,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float[] floatValues2) {
    floatValues2[0] =
        floatValues[0] * floatValue
            + floatValues[1] * floatValue2
            + floatValues[2] * floatValue3
            + floatValues[9];
    floatValues2[1] =
        floatValues[3] * floatValue
            + floatValues[4] * floatValue2
            + floatValues[5] * floatValue3
            + floatValues[10];
    floatValues2[2] =
        floatValues[6] * floatValue
            + floatValues[7] * floatValue2
            + floatValues[8] * floatValue3
            + floatValues[11];
  }

  static {
    SkySupport16 skySupport16 = new SkySupport16();
    skySupport16.render(
        0.0F,
        0.0F,
        new float[] {
          -0.88F, 0.22F, -0.8F, 0.27F, -0.67F, 0.294F, -0.4F, 0.3F, 0.16F, 0.3F, 0.48F, 0.29F,
          0.65F, 0.265F, 0.8F, 0.232F, 0.95F, 0.193F, 1.1F, 0.15F, 1.23F, 0.108F, 1.34F, 0.069F,
          1.43F, 0.035F, 1.49F, 0.001F
        },
        64,
        0,
        -1972505,
        0,
        false,
        true);
    skySupport16.render(0.0F, 0.0F, -0.66F, -0.62F, 0.302F, 3, -1972505, 0);
    skySupport16.render(0.0F, 0.0F, 0.49F, 0.525F, 0.289F, 2, -6312005, 0);
    skySupport16.render(0.0F, 0.0F, -0.79F, -0.755F, 0.281F, 2, -6312005, 0);
    skySupport16.render(0.0F, 0.0F, -0.56F, -0.54F, 0.303F, 2, -6312005, 0);

    for (int intValue : new int[] {-1, 1}) {
      float floatValue = intValue * 0.48F;
      skySupport16.render(
          floatValue,
          -0.045F,
          new float[] {
            -0.9F, 0.09F, -0.8F, 0.135F, -0.58F, 0.145F, 0.02F, 0.145F, 0.22F, 0.134F, 0.36F,
            0.104F, 0.47F, 0.056F, 0.53F, 0.001F
          },
          40,
          9,
          -1972505,
          0,
          false,
          true);
      skySupport16.render(floatValue, -0.045F, -0.73F, -0.64F, 0.146F, 1, -14668741, 0);
      skySupport16.render(floatValue, -0.045F, -0.26F, -0.2F, 0.148F, 3, -1972505, 0);
      skySupport16.render(floatValue, -0.045F, 0.035F, 0.06F, 0.145F, 2, -6312005, 0);
      skySupport16.render(floatValue, -0.045F, -1.12F, 0.9F, 0.135F, intValue < 0 ? 6 : 7);
      float[] floatValues =
          new float[] {
            intValue * 0.24F,
            -0.02F,
            intValue * 0.94F,
            -0.73F,
            intValue * 0.88F,
            -0.94F,
            intValue * 0.25F,
            -0.76F
          };
      skySupport16.render(floatValues, -0.07F, 0.055F, 9, -1972505, 0);
      float[] floatValues2 =
          new float[] {
            intValue * 0.48F,
            -0.82F,
            intValue * 0.87F,
            -0.94F,
            intValue * 0.81F,
            -1.055F,
            intValue * 0.45F,
            -0.94F
          };
      skySupport16.render(floatValues2, -0.068F, 0.028F, 1, -14668741, intValue < 0 ? 2 : 3);
      float[] floatValues3 =
          new float[] {
            intValue * 0.75F,
            -0.565F,
            intValue * 0.93F,
            -0.735F,
            intValue * 0.89F,
            -0.79F,
            intValue * 0.73F,
            -0.61F
          };
      skySupport16.render(floatValues3, -0.035F, 0.008F, 3, -1972505, 0);
      skySupport16.render(
          intValue * 0.895F,
          -0.032F,
          -0.77F,
          0.02F,
          0.02F,
          0.035F,
          12,
          6,
          8,
          intValue < 0 ? -41901 : -10361931,
          0,
          false);
      skySupport16.render(
          floatValue,
          0.1F,
          new float[] {-0.57F, 0.01F, -0.54F, 0.016F, 0.05F, 0.016F, 0.08F, 0.01F},
          12,
          2,
          -6312005,
          0,
          false,
          false);
    }

    skySupport16.render(0.0F, 0.0F, -1.23F, 1.0F, 0.254F, 1);
    skySupport16.render(
        0.0F,
        0.0F,
        new float[] {-0.972F, 0.109F, -0.93F, 0.12F, -0.865F, 0.21F},
        40,
        1,
        -14668741,
        0,
        false,
        true);

    for (int index = 0; index < 4; index++) {
      float floatValue2 = (float) ((Math.PI / 4) + index * Math.PI * 0.5);
      float floatValue3 = (float) Math.cos(floatValue2);
      float floatValue4 = (float) Math.sin(floatValue2);
      skySupport16.render(
          floatValue3 * 0.19F,
          floatValue4 * 0.19F,
          -0.86F,
          floatValue3 * 0.135F,
          floatValue4 * 0.135F,
          -1.0F,
          0.014F,
          2,
          -6312005,
          0);
    }

    for (int intValue2 : new int[] {-1, 1}) {
      skySupport16.render(
          intValue2 * 0.226F,
          0.207F,
          new float[] {-0.57F, 0.012F, -0.52F, 0.019F, 0.29F, 0.019F, 0.34F, 0.009F},
          12,
          2,
          -6312005,
          0,
          false,
          false);

      for (int index2 = 0; index2 < 7; index2++) {
        float floatValue5 = -0.71F + index2 * 0.021F;
        skySupport16.render(
            new float[] {
              intValue2 * 0.13F,
              floatValue5,
              intValue2 * 0.245F,
              floatValue5,
              intValue2 * 0.245F,
              floatValue5 + 0.01F,
              intValue2 * 0.13F,
              floatValue5 + 0.01F
            },
            0.224F,
            0.014F,
            2,
            -6312005,
            0);
      }
    }

    for (float floatValue6 : new float[] {-0.53F, 0.5F}) {
      for (int index3 = 0; index3 < 16; index3++) {
        skySupport16.render(
            index3 * (float) Math.PI / 8.0F, floatValue6, floatValue6 < 0.0F ? 0.304F : 0.291F);
      }
    }

    skySupport16.render(0.0F, 0.287F, 0.245F, 0.205F, 0.369F, 0.018F, 2, -6312005, 0);
    skySupport16.render(0.0F, 0.299F, 0.245F, 0.188F, 0.351F, 0.008F, 1, -14668741, 0);
    skySupport16.render(
        0.0F, 0.301F, 0.245F, 0.184F, 0.218F, 0.342F, 40, 12, 4, -13536613, 4, true);
    skySupport16.render(0.0F, 0.302F, 0.245F, 0.185F, 0.343F, 0.01F, 2, -6312005, 4);

    for (float floatValue7 : new float[] {0.1F, 0.4F}) {
      float floatValue8 = (float) Math.sqrt(1.0 - Math.pow((floatValue7 - 0.245F) / 0.342F, 2.0));

      for (int index4 = 0; index4 < 24; index4++) {
        float floatValue9 = (float) Math.PI * index4 / 24.0F;
        float floatValue10 = (float) Math.PI * (index4 + 1) / 24.0F;
        skySupport16.render(
            0.186F * floatValue8 * (float) Math.cos(floatValue9),
            0.301F + 0.22F * floatValue8 * (float) Math.sin(floatValue9),
            floatValue7,
            0.186F * floatValue8 * (float) Math.cos(floatValue10),
            0.301F + 0.22F * floatValue8 * (float) Math.sin(floatValue10),
            floatValue7,
            0.006F,
            2,
            -6312005,
            4);
      }
    }

    skySupport16.render(
        new float[] {-0.12F, -0.115F, 0.12F, -0.115F, 0.12F, -0.085F, -0.12F, -0.085F},
        0.309F,
        0.028F,
        2,
        -6312005,
        0);
    skySupport16.render(0.0F, 0.297F, -0.32F, 0.092F, 0.133F, 0.005F, 2, -6312005, 0);
    skySupport16.render(0.0F, 0.304F, 0.64F, 0.038F, 0.012F, 0.015F, 16, 6, 5, -11416085, 0, false);
    skySupport16.apply(false);
    skySupport16.apply(true);
    skySupport24Array =
        (bunnythekenzo.client.SkySupport24[]) (skySupport16.list.toArray(SkySupport24[]::new));
    skySupport19Array =
        (bunnythekenzo.client.SkySupport19[]) (skySupport16.list2.toArray(SkySupport19[]::new));
    skySupport19Array2 = new SkySupport19[skySupport19Array.length];
  }
}

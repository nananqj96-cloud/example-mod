package bunnythekenzo.client;

import java.util.Random;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3fc;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport11 {
  public static final long timestamp = 1734856926L;
  public static final int count = 64;
  public static final double value = 4.0;
  public static double[] valueArray;
  public static double[] valueArray2;
  public static double[] valueArray3;
  public static float[] amountArray;
  public static float[] amountArray2;
  public static float[] amountArray3;
  public static float[] amountArray4;
  public static float[] amountArray5;
  public static float[] amountArray6;
  public static float[] amountArray7;
  public static float[] amountArray8;
  public static float[] amountArray9;
  public static int count2 = -1;
  public static SkyParticleStyle skyParticleStyle;
  public static long timestamp2;
  public static float amount;
  public static double value2;
  public static double value3;
  public static double value4;
  public static boolean enabled;

  public static void update() {
    enabled = false;
  }

  public static void apply(int intValue) {
    valueArray = new double[intValue];
    valueArray2 = new double[intValue];
    valueArray3 = new double[intValue];
    amountArray = new float[intValue];
    amountArray2 = new float[intValue];
    amountArray3 = new float[intValue];
    amountArray4 = new float[intValue];
    amountArray5 = new float[intValue];
    amountArray6 = new float[intValue];
    amountArray7 = new float[intValue];
    amountArray8 = new float[intValue];
    amountArray9 = new float[intValue];
  }

  public static void render(
      int intValue,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      float floatValue) {
    Random random = new Random(1734856926L);

    for (int index = 0; index < intValue; index++) {
      valueArray[index] = doubleValue + (random.nextDouble() * 2.0 - 1.0) * floatValue;
      valueArray2[index] = doubleValue2 + (random.nextDouble() * 2.0 - 1.0) * floatValue;
      valueArray3[index] = doubleValue3 + (random.nextDouble() * 2.0 - 1.0) * floatValue;
      amountArray[index] = random.nextFloat() * (float) (Math.PI * 2);
      amountArray2[index] = random.nextFloat() * (float) (Math.PI * 2);
      amountArray3[index] = 0.55F + 0.9F * random.nextFloat();
      float floatValue2 = random.nextFloat();
      amountArray4[index] = 0.55F + 1.1F * floatValue2 * floatValue2;
      amountArray5[index] = random.nextFloat();
      amountArray6[index] = 0.45F + 0.55F * random.nextFloat();
      amountArray7[index] = random.nextFloat() * (float) (Math.PI * 2);
      amountArray8[index] =
          (random.nextBoolean() ? 1.0F : -1.0F) * (0.35F + 0.85F * random.nextFloat());
      amountArray9[index] = 0.65F + 0.7F * random.nextFloat();
    }

    enabled = true;
  }

  public static void apply(Immediate immediate, Camera camera, ClientWorld clientWorld) {
    int count22 = Math.round(SkyModule.computeFloat5());
    SkyParticleStyle skyParticleStyle2 = SkyModule.getSkyParticleStyle();
    float float6 = SkyModule.computeFloat6();
    Vec3d cameraPos = camera.getCameraPos();
    if (count2 != count22 || skyParticleStyle != skyParticleStyle2) {
      count2 = count22;
      skyParticleStyle = skyParticleStyle2;
      apply(count22);
      enabled = false;
    }

    if (!enabled
        || Math.abs(cameraPos.x - value2) > float6 * 4.0
        || Math.abs(cameraPos.y - value3) > float6 * 4.0
        || Math.abs(cameraPos.z - value4) > float6 * 4.0) {
      render(count2, cameraPos.x, cameraPos.y, cameraPos.z, float6);
    }

    value2 = cameraPos.x;
    value3 = cameraPos.y;
    value4 = cameraPos.z;
    render(cameraPos, clientWorld, float6);
    render(immediate, camera, cameraPos, float6);
  }

  public static void render(Vec3d vec3d, ClientWorld clientWorld, float floatValue) {
    long timestamp22 = System.nanoTime();
    float floatValue2 =
        timestamp2 == 0L ? 0.0F : (float) Math.min((timestamp22 - timestamp2) / 1.0E9, 0.1);
    timestamp2 = timestamp22;
    amount += floatValue2;
    if (!(floatValue2 <= 0.0F)) {
      float float8 = SkyModule.computeFloat8();
      float floatValue3 = skyParticleStyle.getDrift() * float8;
      float floatValue4 = skyParticleStyle.getSway() * float8;
      float floatValue5 = skyParticleStyle.getSpin() * float8;
      int index = 64;

      for (int index2 = 0; index2 < count2; index2++) {
        valueArray[index2] =
            valueArray[index2]
                + floatValue4
                    * Math.sin(amount * 0.53F * amountArray3[index2] + amountArray[index2])
                    * floatValue2;
        valueArray2[index2] =
            valueArray2[index2] + floatValue3 * amountArray9[index2] * floatValue2;
        valueArray3[index2] =
            valueArray3[index2]
                + floatValue4
                    * Math.cos(amount * 0.41F * amountArray3[index2] + amountArray2[index2])
                    * floatValue2;
        if (floatValue5 != 0.0F) {
          amountArray7[index2] =
              amountArray7[index2] + amountArray8[index2] * floatValue5 * floatValue2;
        }

        boolean flag = false;
        double doubleValue = valueArray[index2] - vec3d.x;
        double doubleValue2 = valueArray2[index2] - vec3d.y;
        double doubleValue3 = valueArray3[index2] - vec3d.z;
        if (doubleValue > floatValue || doubleValue < -floatValue) {
          valueArray[index2] = vec3d.x + computeDouble(doubleValue, floatValue);
          flag = true;
        }

        if (doubleValue2 > floatValue || doubleValue2 < -floatValue) {
          valueArray2[index2] = vec3d.y + computeDouble(doubleValue2, floatValue);
          flag = true;
        }

        if (doubleValue3 > floatValue || doubleValue3 < -floatValue) {
          valueArray3[index2] = vec3d.z + computeDouble(doubleValue3, floatValue);
          flag = true;
        }

        if (flag && index > 0 && clientWorld != null) {
          index--;
          render(index2, clientWorld, floatValue, vec3d);
        }
      }
    }
  }

  public static double computeDouble(double doubleValue, float floatValue) {
    double doubleValue2 = floatValue * 2.0;
    return doubleValue - doubleValue2 * Math.floor((doubleValue + floatValue) / doubleValue2);
  }

  public static void render(int index, ClientWorld clientWorld, float floatValue, Vec3d vec3d) {
    if (!clientWorld
        .getBlockState(
            BlockPos.ofFloored(valueArray[index], valueArray2[index], valueArray3[index]))
        .isAir()) {
      double doubleValue = (Math.random() * 2.0 - 1.0) * floatValue;
      double doubleValue2 = (Math.random() * 2.0 - 1.0) * floatValue;
      valueArray[index] = vec3d.x + doubleValue;
      valueArray3[index] = vec3d.z + doubleValue2;
    }
  }

  public static void render(Immediate immediate, Camera camera, Vec3d vec3d, float floatValue) {
    Vector3fc diagonalPlane = camera.getDiagonalPlane();
    Vector3fc verticalPlane = camera.getVerticalPlane();
    Vector3fc horizontalPlane = camera.getHorizontalPlane();
    SkySupport7.render(horizontalPlane.x(), horizontalPlane.y(), horizontalPlane.z());
    float floatValue2 = SkyModule.computeFloat7() * skyParticleStyle.getScale() * 0.5F;
    float float9 = SkyModule.computeFloat9();
    boolean flag = skyParticleStyle.getSpin() != 0.0F;
    int value5 = SkyColorPalette.computeInt();
    int int6 = SkyColorPalette.computeInt6();
    float floatValue3 = floatValue * 0.55F;
    VertexConsumer buffer =
        immediate.getBuffer(SkyPipelines.motes(skyParticleStyle, SkyModule.test8()));

    for (int index = 0; index < count2; index++) {
      float floatValue4 = (float) (valueArray[index] - vec3d.x);
      float floatValue5 = (float) (valueArray2[index] - vec3d.y);
      float floatValue6 = (float) (valueArray3[index] - vec3d.z);
      if (SkySupport7.test(
          floatValue4, floatValue5, floatValue6, floatValue2 * amountArray4[index] * 2.0F)) {
        float floatValue7 =
            (float)
                Math.sqrt(
                    floatValue4 * floatValue4
                        + floatValue5 * floatValue5
                        + floatValue6 * floatValue6);
        if (!(floatValue7 > floatValue)) {
          float floatValue8 =
              amountArray6[index]
                  * float9
                  * SkyColorPalette.computeFloat(0.3F, 1.1F, floatValue7)
                  * (1.0F - SkyColorPalette.computeFloat(floatValue3, floatValue, floatValue7));
          if (!(floatValue8 <= 0.004F)) {
            float floatValue9 = -diagonalPlane.x();
            float floatValue10 = -diagonalPlane.y();
            float floatValue11 = -diagonalPlane.z();
            float floatValue12 = verticalPlane.x();
            float floatValue13 = verticalPlane.y();
            float floatValue14 = verticalPlane.z();
            if (flag) {
              float floatValue15 = (float) Math.cos(amountArray7[index]);
              float floatValue16 = (float) Math.sin(amountArray7[index]);
              float floatValue17 = floatValue9 * floatValue15 + floatValue12 * floatValue16;
              float floatValue18 = floatValue10 * floatValue15 + floatValue13 * floatValue16;
              float floatValue19 = floatValue11 * floatValue15 + floatValue14 * floatValue16;
              floatValue12 = floatValue12 * floatValue15 - floatValue9 * floatValue16;
              floatValue13 = floatValue13 * floatValue15 - floatValue10 * floatValue16;
              floatValue14 = floatValue14 * floatValue15 - floatValue11 * floatValue16;
              floatValue9 = floatValue17;
              floatValue10 = floatValue18;
              floatValue11 = floatValue19;
            }

            float floatValue20 = floatValue2 * amountArray4[index];
            floatValue9 *= floatValue20;
            floatValue10 *= floatValue20;
            floatValue11 *= floatValue20;
            floatValue12 *= floatValue20;
            floatValue13 *= floatValue20;
            floatValue14 *= floatValue20;
            int value6 = ArgbColors.lerp(value5, int6, amountArray5[index]);
            float floatValue21 = Math.clamp(floatValue8, 0.0F, 1.0F);
            int intValue = (int) (ArgbColors.red(value6) * floatValue21 + 0.5F);
            int intValue2 = (int) (ArgbColors.green(value6) * floatValue21 + 0.5F);
            int intValue3 = (int) (ArgbColors.blue(value6) * floatValue21 + 0.5F);
            int intValue4 = (int) (floatValue21 * 255.0F + 0.5F);
            buffer
                .vertex(
                    floatValue4 - floatValue9 + floatValue12,
                    floatValue5 - floatValue10 + floatValue13,
                    floatValue6 - floatValue11 + floatValue14)
                .texture(0.0F, 0.0F)
                .color(intValue, intValue2, intValue3, intValue4);
            buffer
                .vertex(
                    floatValue4 - floatValue9 - floatValue12,
                    floatValue5 - floatValue10 - floatValue13,
                    floatValue6 - floatValue11 - floatValue14)
                .texture(0.0F, 1.0F)
                .color(intValue, intValue2, intValue3, intValue4);
            buffer
                .vertex(
                    floatValue4 + floatValue9 - floatValue12,
                    floatValue5 + floatValue10 - floatValue13,
                    floatValue6 + floatValue11 - floatValue14)
                .texture(1.0F, 1.0F)
                .color(intValue, intValue2, intValue3, intValue4);
            buffer
                .vertex(
                    floatValue4 + floatValue9 + floatValue12,
                    floatValue5 + floatValue10 + floatValue13,
                    floatValue6 + floatValue11 + floatValue14)
                .texture(1.0F, 0.0F)
                .color(intValue, intValue2, intValue3, intValue4);
          }
        }
      }
    }

    immediate.draw();
  }
}

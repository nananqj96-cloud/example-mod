package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.joml.Vector3f;

public final class TwerkingAnimation {
  public static final float amount = 145.0F;
  public static final long timestamp = 14371L;
  public static final float amount2 = (float) (Math.PI / 180.0);
  public static final float[][] amountArray =
      new float[][] {
        {0.0F, 180.0F, 8.0F, 12.0F, 0.0F},
        {0.267F, 197.0F, 11.0F, -12.0F, 0.0F},
        {0.533F, 185.0F, 18.0F, 10.0F, 0.0F},
        {0.8F, 180.0F, 24.0F, -7.0F, 0.0F},
        {0.933F, 195.0F, 28.0F, -5.0F, -2.0F},
        {1.2F, 268.0F, 27.0F, 0.0F, -3.0F},
        {1.467F, 342.0F, 25.0F, 2.0F, -3.0F},
        {1.733F, 360.0F, 12.0F, 1.0F, -2.0F},
        {2.0F, 350.0F, 4.0F, 0.0F, 4.0F},
        {2.267F, 354.0F, 5.0F, 0.0F, 7.0F},
        {2.533F, 360.0F, 11.0F, 0.0F, 3.0F},
        {2.8F, 360.0F, 9.0F, -6.0F, 0.0F},
        {3.067F, 360.0F, 3.0F, -13.0F, -4.0F},
        {3.333F, 365.0F, 7.0F, -3.0F, -7.0F},
        {3.6F, 417.0F, 20.0F, 6.0F, -3.0F},
        {3.867F, 486.0F, 26.0F, 3.0F, 0.0F},
        {4.133F, 534.0F, 21.0F, 0.0F, 0.0F},
        {4.4F, 540.0F, 7.0F, 0.0F, 0.0F},
        {4.667F, 540.0F, 4.0F, 0.0F, 0.0F}
      };
  public static final float[][] amountArray2 =
      new float[][] {
        {4.933F, 5.267F, 5.567F},
        {6.6F, 6.933F, 7.267F},
        {11.333F, 11.667F, 12.033F},
        {13.067F, 13.433F, 13.767F}
      };
  public static final float[][] amountArray3 =
      new float[][] {
        {5.767F, 6.133F, 6.433F},
        {7.433F, 7.767F, 8.067F},
        {12.167F, 12.567F, 12.9F},
        {13.867F, 14.267F, 14.6F}
      };
  public static final float[][] amountArray4 =
      new float[][] {
        {0.0F, 0.0F},
        {4.433F, 0.0F},
        {4.8F, 0.13F},
        {7.9F, 0.13F},
        {8.267F, 0.42F},
        {8.533F, 0.68F},
        {8.933F, 0.82F},
        {9.333F, 0.92F},
        {9.733F, 1.0F},
        {10.133F, 0.91F},
        {10.467F, 0.35F},
        {10.9F, 0.22F},
        {11.3F, 0.13F},
        {14.371F, 0.13F}
      };

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (Float.isFinite(floatValue) && Float.isFinite(floatValue2) && !(floatValue2 <= 0.0F)) {
      floatValue2 = Math.clamp(floatValue2, 0.0F, 1.0F);
      float floatValue3 = Math.clamp(floatValue, 0.0F, 14.371F);
      float floatValue4 = computeFloat(Math.clamp((floatValue3 - 4.35F) / 0.4F, 0.0F, 1.0F));
      float floatValue5 = computeFloat(amountArray2, floatValue3);
      float floatValue6 = computeFloat(amountArray3, floatValue3);
      float floatValue7 = computeFloat(amountArray4, floatValue3, 1);
      float floatValue8 = AnimationNoiseSamples.computeFloat(floatValue3) * floatValue4;
      float floatValue9 = floatValue5 - floatValue6;
      float floatValue10 =
          2.2F * floatValue9
              + 0.16F
                  * floatValue4
                  * (float) Math.sin((floatValue3 - 4.6F) * Math.PI * 145.0 / 60.0);
      float floatValue11 = (0.6F + 0.65F * floatValue8 + 0.65F * floatValue7) * floatValue4;
      float floatValue12 =
          (0.4F + 0.8F * floatValue7 - 0.38F * floatValue8 + 0.94F * floatValue5) * floatValue4;
      float floatValue13 =
          (0.4F + 0.8F * floatValue7 - 0.38F * floatValue8 + 0.94F * floatValue6) * floatValue4;
      float floatValue14 = (7.0F + 12.0F * floatValue7) * (float) (Math.PI / 180.0) * floatValue4;
      float floatValue15 =
          floatValue3 < 1.0F ? (float) Math.sin(floatValue3 * Math.PI * 4.0) * 0.25F : 0.0F;
      render(
          bipedEntityModel.rightLeg,
          -floatValue12 * 0.6F - floatValue11 / 12.0F + floatValue15,
          -(6.0F * floatValue4 + 20.0F * floatValue5) * (float) (Math.PI / 180.0),
          floatValue14 + 0.4F * floatValue5 + 0.18F * floatValue9,
          floatValue2);
      render(
          bipedEntityModel.leftLeg,
          -floatValue13 * 0.6F - floatValue11 / 12.0F - floatValue15,
          (6.0F * floatValue4 + 20.0F * floatValue6) * (float) (Math.PI / 180.0),
          -floatValue14 - 0.4F * floatValue6 + 0.18F * floatValue9,
          floatValue2);
      float floatValue16 =
          (9.0F * floatValue4 + 11.0F * floatValue7 + 6.0F * floatValue8)
              * (float) (Math.PI / 180.0);
      float floatValue17 = -7.0F * floatValue9 * (float) (Math.PI / 180.0);
      float floatValue18 = (3.0F * floatValue9 + 0.7F * floatValue8) * (float) (Math.PI / 180.0);
      render(bipedEntityModel.body, floatValue16, floatValue17, floatValue18, floatValue2);
      Vector3f vector3f =
          new Vector3f(0.0F, -12.0F, 0.0F)
              .rotateX(bipedEntityModel.body.pitch)
              .rotateY(bipedEntityModel.body.yaw)
              .rotateZ(bipedEntityModel.body.roll)
              .add(0.0F, 12.0F, 0.0F);
      bipedEntityModel.body.originX = bipedEntityModel.body.originX + vector3f.x;
      bipedEntityModel.body.originY = bipedEntityModel.body.originY + vector3f.y;
      bipedEntityModel.body.originZ = bipedEntityModel.body.originZ + vector3f.z;
      bipedEntityModel.head.originX = bipedEntityModel.head.originX + vector3f.x;
      bipedEntityModel.head.originY = bipedEntityModel.head.originY + vector3f.y;
      bipedEntityModel.head.originZ = bipedEntityModel.head.originZ + vector3f.z;
      render(bipedEntityModel.rightArm, -5.0F, bipedEntityModel.body);
      render(bipedEntityModel.leftArm, 5.0F, bipedEntityModel.body);
      float floatValue19 = computeFloat(amountArray, floatValue3, 2) * (float) (Math.PI / 180.0);
      float floatValue20 = computeFloat(amountArray, floatValue3, 3) * (float) (Math.PI / 180.0);
      render(
          bipedEntityModel.rightArm,
          floatValue20 + (0.04F + 0.09F * floatValue8) * floatValue4,
          -0.06F * floatValue5,
          floatValue19 + 0.13F * floatValue5 + 0.03F * floatValue7,
          floatValue2);
      render(
          bipedEntityModel.leftArm,
          -floatValue20 + (0.04F + 0.09F * floatValue8) * floatValue4,
          0.06F * floatValue6,
          -floatValue19 - 0.13F * floatValue6 - 0.03F * floatValue7,
          floatValue2);
      EmoteBodyParts.render(
          bipedEntityModel,
          (-0.06F - 0.13F * floatValue5) * floatValue2,
          (-0.06F - 0.13F * floatValue6) * floatValue2,
          floatValue12 * floatValue2,
          floatValue13 * floatValue2);
      render(
          bipedEntityModel.head,
          (-2.0F * floatValue4 - 2.0F * floatValue8) * (float) (Math.PI / 180.0),
          (12.0F * floatValue5 - 10.0F * floatValue6) * (float) (Math.PI / 180.0),
          computeFloat(amountArray, floatValue3, 4) * (float) (Math.PI / 180.0)
              - floatValue18 * 0.4F,
          floatValue2);
      float floatValue21 = computeFloat(amountArray, floatValue3, 1) - 180.0F + 8.0F * floatValue9;
      float floatValue22 =
          (float) Math.IEEEremainder(floatValue21 * (float) (Math.PI / 180.0), Math.PI * 2);
      render(bipedEntityModel.getRootPart(), 0.0F, floatValue22, 0.0F, floatValue2);
      bipedEntityModel.getRootPart().originX += floatValue10 * floatValue2;
      bipedEntityModel.getRootPart().originZ += floatValue11 * floatValue2;
      float floatValue23 =
          Math.max(
              computeFloat(bipedEntityModel.rightLeg, floatValue12 * floatValue2),
              computeFloat(bipedEntityModel.leftLeg, floatValue13 * floatValue2));
      bipedEntityModel.getRootPart().originY += 24.0F - floatValue23;
    }
  }

  public static void render(ModelPart modelPart, float floatValue, ModelPart modelPart2) {
    Vector3f vector3f =
        new Vector3f(floatValue, -10.0F, 0.0F)
            .rotateX(modelPart2.pitch)
            .rotateY(modelPart2.yaw)
            .rotateZ(modelPart2.roll)
            .add(0.0F, 12.0F, 0.0F);
    modelPart.originX = modelPart.originX + (vector3f.x - floatValue);
    modelPart.originY = modelPart.originY + (vector3f.y - 2.0F);
    modelPart.originZ = modelPart.originZ + vector3f.z;
  }

  public static float computeFloat(ModelPart modelPart, float floatValue) {
    float floatValue2 = -Float.MAX_VALUE;
    Vector3f vector3f = new Vector3f();

    for (byte byteValue = -1; byteValue <= 1; byteValue += 2) {
      for (byte byteValue2 = -1; byteValue2 <= 1; byteValue2 += 2) {
        vector3f
            .set(byteValue * 2.0F, 6.0F, byteValue2 * 2.0F)
            .rotateX(floatValue)
            .add(0.0F, 6.0F, 0.0F)
            .rotateX(modelPart.pitch)
            .rotateY(modelPart.yaw)
            .rotateZ(modelPart.roll)
            .add(modelPart.originX, modelPart.originY, modelPart.originZ);
        floatValue2 = Math.max(floatValue2, vector3f.y);
      }
    }

    return floatValue2;
  }

  public static float computeFloat(float[][] floatValuesArray, float floatValue) {
    for (float[] floatValues : floatValuesArray) {
      if (floatValue >= floatValues[0] && floatValue <= floatValues[2]) {
        return floatValue < floatValues[1]
            ? computeFloat((floatValue - floatValues[0]) / (floatValues[1] - floatValues[0]))
            : 1.0F
                - computeFloat((floatValue - floatValues[1]) / (floatValues[2] - floatValues[1]));
      }
    }

    return 0.0F;
  }

  public static float computeFloat(float[][] floatValuesArray, float floatValue, int index) {
    if (floatValue <= floatValuesArray[0][0]) {
      return floatValuesArray[0][index];
    }

    for (int index2 = 1; index2 < floatValuesArray.length; index2++) {
      if (floatValue <= floatValuesArray[index2][0]) {
        float[] floatValues = floatValuesArray[index2 - 1];
        float[] floatValues2 = floatValuesArray[index2];
        return computeFloat(
            floatValues[index],
            floatValues2[index],
            computeFloat((floatValue - floatValues[0]) / (floatValues2[0] - floatValues[0])));
      }
    }

    return floatValuesArray[floatValuesArray.length - 1][index];
  }

  public static float computeFloat(float floatValue) {
    return floatValue * floatValue * (3.0F - 2.0F * floatValue);
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }

  public static void render(
      ModelPart modelPart,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    modelPart.pitch = computeFloat(modelPart.pitch, floatValue, floatValue4);
    modelPart.yaw = computeFloat(modelPart.yaw, floatValue2, floatValue4);
    modelPart.roll = computeFloat(modelPart.roll, floatValue3, floatValue4);
  }
}

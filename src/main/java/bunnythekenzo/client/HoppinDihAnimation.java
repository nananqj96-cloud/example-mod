package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class HoppinDihAnimation {
  public static final long timestamp = 15045L;
  public static final float amount = 146.0F;
  public static final float amount2 = 0.36666667F;
  public static final float amount3 = 13.166667F;
  public static final float amount4 = 14.7F;
  public static final float amount5 = (float) (Math.PI * 2);
  public static final float amount6 = (float) (Math.PI / 180.0);
  public static final float[][] amountArray =
      new float[][] {
        {4.2F, -86.0F, -79.0F, -5.0F, -3.0F, 10.0F},
        {7.3F, -94.0F, -86.0F, -7.0F, -6.0F, 11.0F},
        {7.8F, -96.0F, -88.0F, -8.0F, -7.0F, 11.0F},
        {8.7F, -99.0F, -91.0F, -9.0F, -8.0F, 12.0F},
        {6.1F, -92.0F, -85.0F, -6.0F, -4.0F, 11.0F},
        {2.4F, -83.0F, -77.0F, -2.0F, 1.0F, 10.0F},
        {0.3F, -77.0F, -72.0F, 2.0F, 4.0F, 9.0F},
        {0.0F, -76.0F, -70.0F, 3.0F, 5.0F, 9.0F},
        {0.0F, -76.0F, -70.0F, 3.0F, 5.0F, 9.0F},
        {0.16F, -78.0F, -72.0F, 1.0F, 3.0F, 9.0F},
        {1.7F, -81.0F, -75.0F, -2.0F, 0.0F, 10.0F}
      };

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (Float.isFinite(floatValue) && Float.isFinite(floatValue2) && !(floatValue2 <= 0.0F)) {
      floatValue2 = Math.clamp(floatValue2, 0.0F, 1.0F);
      float floatValue3 = Math.max(0.0F, floatValue) * 30.0F;
      float floatValue4 = computeFloat(floatValue3, 0);
      float floatValue5 = computeFloat(floatValue3, 1) * (float) (Math.PI / 180.0);
      float floatValue6 = computeFloat(floatValue3, 2) * (float) (Math.PI / 180.0);
      float floatValue7 = computeFloat(floatValue3, 3) * (float) (Math.PI / 180.0);
      float floatValue8 = computeFloat(floatValue3, 4) * (float) (Math.PI / 180.0);
      float floatValue9 = computeFloat(floatValue3, 5) * (float) (Math.PI / 180.0);
      render(
          bipedEntityModel.rightLeg,
          floatValue5 + 0.034906585F,
          floatValue9,
          0.02617994F,
          floatValue2);
      render(
          bipedEntityModel.leftLeg,
          floatValue5 - 0.034906585F,
          -floatValue9,
          -0.02617994F,
          floatValue2);
      render(
          bipedEntityModel.rightArm,
          floatValue6 + 0.05235988F,
          (float) (-Math.PI / 20),
          0.13962634F,
          floatValue2);
      render(
          bipedEntityModel.leftArm, floatValue6, (float) (Math.PI / 20), -0.13962634F, floatValue2);
      render(bipedEntityModel.body, floatValue7, 0.0F, 0.0F, floatValue2);
      render(bipedEntityModel.head, floatValue8, -0.034906585F, 0.0F, floatValue2);
      float floatValue10 = 12.0F * (float) Math.cos(bipedEntityModel.body.pitch);
      float floatValue11 = 12.0F * (float) Math.sin(bipedEntityModel.body.pitch);
      bipedEntityModel.rightLeg.originY =
          computeFloat(bipedEntityModel.rightLeg.originY, floatValue10, floatValue2);
      bipedEntityModel.leftLeg.originY =
          computeFloat(bipedEntityModel.leftLeg.originY, floatValue10, floatValue2);
      bipedEntityModel.rightLeg.originZ =
          computeFloat(bipedEntityModel.rightLeg.originZ, floatValue11, floatValue2);
      bipedEntityModel.leftLeg.originZ =
          computeFloat(bipedEntityModel.leftLeg.originZ, floatValue11, floatValue2);
      float floatValue12 =
          Math.max(computeFloat(bipedEntityModel.rightLeg), computeFloat(bipedEntityModel.leftLeg));
      bipedEntityModel.getRootPart().originY +=
          24.0F - floatValue12 - (floatValue4 + 0.12F) * floatValue2;
      bipedEntityModel.getRootPart().originZ += 1.1F * floatValue2;
      bipedEntityModel.getRootPart().pitch =
          computeFloat(bipedEntityModel.getRootPart().pitch, 0.0F, floatValue2);
      bipedEntityModel.getRootPart().roll =
          computeFloat(bipedEntityModel.getRootPart().roll, 0.0F, floatValue2);
      float floatValue13 = Math.clamp((floatValue - 13.166667F) / 1.5333328F, 0.0F, 1.0F);
      float floatValue14 = -31.415928F * computeFloat(floatValue13);
      float floatValue15 = (float) Math.IEEEremainder(floatValue14, (float) (Math.PI * 2));
      bipedEntityModel.getRootPart().yaw =
          computeFloat(bipedEntityModel.getRootPart().yaw, floatValue15, floatValue2);
    }
  }

  public static float computeFloat(ModelPart modelPart) {
    float floatValue = (float) Math.sin(modelPart.pitch);
    float floatValue2 = (float) Math.cos(modelPart.pitch);
    float floatValue3 = (float) Math.sin(modelPart.yaw);
    float floatValue4 = (float) Math.cos(modelPart.yaw);
    float floatValue5 = (float) Math.sin(modelPart.roll);
    float floatValue6 = (float) Math.cos(modelPart.roll);
    float floatValue7 = floatValue5 * floatValue4;
    float floatValue8 = floatValue5 * floatValue3 * floatValue + floatValue6 * floatValue2;
    float floatValue9 = floatValue5 * floatValue3 * floatValue2 - floatValue6 * floatValue;
    return modelPart.originY
        + 2.0F * Math.abs(floatValue7)
        + Math.max(0.0F, 12.0F * floatValue8)
        + 2.0F * Math.abs(floatValue9);
  }

  public static float computeFloat(float floatValue, int index) {
    int intValue = (int) Math.floor(floatValue);
    float floatValue2 = floatValue - intValue;
    int index2 = Math.floorMod(intValue, amountArray.length);
    float floatValue3 = amountArray[(index2 + amountArray.length - 1) % amountArray.length][index];
    float floatValue4 = amountArray[index2][index];
    float floatValue5 = amountArray[(index2 + 1) % amountArray.length][index];
    float floatValue6 = amountArray[(index2 + 2) % amountArray.length][index];
    float floatValue7 = computeFloat(floatValue4 - floatValue3, floatValue5 - floatValue4);
    float floatValue8 = computeFloat(floatValue5 - floatValue4, floatValue6 - floatValue5);
    float floatValue9 = floatValue2 * floatValue2;
    float floatValue10 = floatValue9 * floatValue2;
    return (2.0F * floatValue10 - 3.0F * floatValue9 + 1.0F) * floatValue4
        + (floatValue10 - 2.0F * floatValue9 + floatValue2) * floatValue7
        + (-2.0F * floatValue10 + 3.0F * floatValue9) * floatValue5
        + (floatValue10 - floatValue9) * floatValue8;
  }

  public static float computeFloat(float floatValue, float floatValue2) {
    return floatValue * floatValue2 <= 0.0F
        ? 0.0F
        : 2.0F * floatValue * floatValue2 / (floatValue + floatValue2);
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

  public static float computeFloat(float floatValue) {
    return floatValue
        * floatValue
        * floatValue
        * (floatValue * (floatValue * 6.0F - 15.0F) + 10.0F);
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }
}

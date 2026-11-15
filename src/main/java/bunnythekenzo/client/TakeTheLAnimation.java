package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.joml.Vector3f;

public final class TakeTheLAnimation {
  public static final float amount = 150.0F;
  public static final long timestamp = 25267L;
  public static final float amount2 = 0.8F;
  public static final float[][] amountArray =
      new float[][] {
        {0.0F, -0.34F, 1.05F, 0.42F, -0.16F, 0.1F, 0.28F, 0.05F},
        {0.17F, -0.14F, 1.35F, 0.08F, -0.1F, 0.14F, 0.18F, 0.0F},
        {0.34F, -0.15F, 1.32F, 0.1F, -0.13F, 0.12F, 0.24F, 0.1F},
        {0.55F, -0.6F, 0.8F, 0.92F, -0.3F, 0.14F, 0.55F, 1.2F},
        {0.72F, -0.8F, 0.3F, 1.35F, -0.7F, 0.42F, 1.24F, 2.25F},
        {0.88F, -0.3F, 0.14F, 0.62F, -0.8F, 0.75F, 1.0F, 1.1F},
        {1.0F, -0.16F, 0.1F, 0.28F, -0.34F, 1.05F, 0.42F, 0.05F}
      };

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    float floatValue3 = floatValue * 2.0F / 0.8F;
    float floatValue4 = floatValue3 - (float) Math.floor(floatValue3);
    boolean flag = ((int) Math.floor(floatValue3) & 1) == 0;
    int index = 0;

    while (index < amountArray.length - 2 && floatValue4 > amountArray[index + 1][0]) {
      index++;
    }

    float[] floatValues = amountArray[index];
    float[] floatValues2 = amountArray[index + 1];
    float floatValue5 = (floatValue4 - floatValues[0]) / (floatValues2[0] - floatValues[0]);
    floatValue5 = floatValue5 * floatValue5 * (3.0F - 2.0F * floatValue5);
    float floatValue6 = computeFloat(floatValues[1], floatValues2[1], floatValue5);
    float floatValue7 = computeFloat(floatValues[2], floatValues2[2], floatValue5);
    float floatValue8 = computeFloat(floatValues[3], floatValues2[3], floatValue5);
    float floatValue9 = computeFloat(floatValues[4], floatValues2[4], floatValue5);
    float floatValue10 = computeFloat(floatValues[5], floatValues2[5], floatValue5);
    float floatValue11 = computeFloat(floatValues[6], floatValues2[6], floatValue5);
    float floatValue12 = computeFloat(floatValues[7], floatValues2[7], floatValue5);
    float floatValue13 = (float) Math.cos(floatValue3 * Math.PI);
    float floatValue14 = (float) Math.sin(floatValue3 * Math.PI * 2.0);
    render(
        bipedEntityModel.rightLeg,
        flag ? floatValue9 : floatValue6,
        -0.06F,
        flag ? floatValue10 : floatValue7,
        floatValue2);
    render(
        bipedEntityModel.leftLeg,
        flag ? floatValue6 : floatValue9,
        0.06F,
        flag ? -floatValue7 : -floatValue10,
        floatValue2);
    float floatValue15 = (flag ? floatValue11 : floatValue8) * floatValue2;
    float floatValue16 = (flag ? floatValue8 : floatValue11) * floatValue2;
    render(bipedEntityModel.rightArm, -0.5F, 0.0F, 2.55F + 0.025F * floatValue14, floatValue2);
    render(bipedEntityModel.leftArm, -0.18F, 0.06F, -0.76F + 0.035F * floatValue13, floatValue2);
    EmoteBodyParts.render(
        bipedEntityModel,
        -0.47F * floatValue2,
        -0.28F * floatValue2,
        floatValue15,
        floatValue16,
        (1.21F - 0.025F * floatValue14) * floatValue2,
        1.63F * floatValue2);
    EmoteBodyParts.apply(bipedEntityModel, floatValue2 > 0.5F);
    render(
        bipedEntityModel.body,
        0.045F + 0.025F * floatValue14,
        -0.035F * floatValue13,
        0.025F * floatValue13,
        floatValue2);
    render(
        bipedEntityModel.head,
        -0.035F - 0.035F * floatValue14,
        0.045F * floatValue13,
        -0.035F * floatValue13,
        floatValue2);
    render(
        bipedEntityModel.getRootPart(),
        0.0F,
        0.035F * floatValue13,
        -0.018F * floatValue13,
        floatValue2);
    bipedEntityModel.getRootPart().originX += 0.3F * floatValue13 * floatValue2;
    float floatValue17 =
        Math.max(
            computeFloat(bipedEntityModel, bipedEntityModel.rightLeg, floatValue15),
            computeFloat(bipedEntityModel, bipedEntityModel.leftLeg, floatValue16));
    bipedEntityModel.getRootPart().originY += 24.0F - floatValue17 - floatValue12 * floatValue2;
  }

  public static float computeFloat(
      BipedEntityModel bipedEntityModel, ModelPart modelPart, float floatValue) {
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
            .add(modelPart.originX, modelPart.originY, modelPart.originZ)
            .rotateX(bipedEntityModel.getRootPart().pitch)
            .rotateY(bipedEntityModel.getRootPart().yaw)
            .rotateZ(bipedEntityModel.getRootPart().roll);
        floatValue2 = Math.max(floatValue2, vector3f.y);
      }
    }

    return floatValue2;
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

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }
}

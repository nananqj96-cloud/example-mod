package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.joml.Vector3f;

public final class JerkingOffAnimation {
  public static final float amount = 78.0F;
  public static final long timestamp = 12000L;
  public static final float amount2 = 11.2F;
  public static final float amount3 = 0.65F;
  public static final float amount4 = 5.2F;
  public static final float amount5 = (float) (Math.PI * 2);

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    float floatValue3 = floatValue * 2.0F;
    float floatValue4 = 1.0F - computeFloat(Math.clamp((floatValue - 11.2F) / 0.4F, 0.0F, 1.0F));
    float floatValue5 =
        (float) Math.sin(computeDouble(floatValue) * (float) (Math.PI * 2)) * floatValue4;
    float floatValue6 = (float) Math.sin(floatValue3 * (float) (Math.PI * 2));
    float floatValue7 = 0.5F - 0.5F * (float) Math.cos(floatValue3 * (float) (Math.PI * 2));
    float floatValue8 = (float) Math.sin(floatValue3 * (float) (Math.PI * 2) / 4.0F);
    render(bipedEntityModel.rightArm, -0.23F, -0.9F, -0.3F, floatValue2);
    bipedEntityModel.rightArm.originZ -= 0.9F * floatValue2;
    render(
        bipedEntityModel.leftArm,
        -0.1F - 0.05F * floatValue5,
        -0.06F,
        -0.2F - 0.035F * floatValue8,
        floatValue2);
    float floatValue9 = (0.16F + 0.16F * floatValue7) * floatValue2;
    render(bipedEntityModel.rightLeg, -0.08F - 0.08F * floatValue7, -0.06F, 0.1F, floatValue2);
    render(bipedEntityModel.leftLeg, -0.08F - 0.08F * floatValue7, 0.06F, -0.1F, floatValue2);
    EmoteBodyParts.render(
        bipedEntityModel,
        (-0.32F + 0.14F * floatValue5) * floatValue2,
        (-0.3F - 0.12F * floatValue5) * floatValue2,
        floatValue9,
        floatValue9);
    render(
        bipedEntityModel.body,
        0.025F + 0.02F * floatValue7,
        0.045F * floatValue8,
        0.0F,
        floatValue2);
    render(
        bipedEntityModel.head,
        -0.045F + 0.055F * floatValue6,
        -0.08F + 0.055F * floatValue8,
        0.0F,
        floatValue2);
    render(bipedEntityModel.getRootPart(), 0.0F, 0.035F * floatValue8, 0.0F, floatValue2);
    float floatValue10 =
        Math.max(
            computeFloat(bipedEntityModel.rightLeg, floatValue9),
            computeFloat(bipedEntityModel.leftLeg, floatValue9));
    bipedEntityModel.getRootPart().originY += 24.0F - floatValue10;
  }

  public static double computeDouble(double doubleValue) {
    double doubleValue2 = Math.max(0.0, doubleValue);
    double doubleValue3 = Math.min(1.0, doubleValue2 / 11.2F);
    double doubleValue4 =
        doubleValue3 * doubleValue3 * doubleValue3
            - 0.5 * doubleValue3 * doubleValue3 * doubleValue3 * doubleValue3;
    return 0.65F * Math.min(doubleValue2, 11.2F)
        + 50.959995F * doubleValue4
        + 5.2F * Math.max(0.0, doubleValue2 - 11.2F);
  }

  public static float computeFloat(float floatValue) {
    return floatValue * floatValue * (3.0F - 2.0F * floatValue);
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

  public static void render(
      ModelPart modelPart,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    modelPart.pitch = modelPart.pitch + (floatValue - modelPart.pitch) * floatValue4;
    modelPart.yaw = modelPart.yaw + (floatValue2 - modelPart.yaw) * floatValue4;
    modelPart.roll = modelPart.roll + (floatValue3 - modelPart.roll) * floatValue4;
  }
}

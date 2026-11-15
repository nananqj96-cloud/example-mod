package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class EmotePoseAnimator {
  public static final float amount = (float) (Math.PI * 2);

  public static void render(
      BipedEntityModel bipedEntityModel,
      EmoteAnimation emoteAnimation,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    if (!(floatValue3 <= 0.001F)) {
      float floatValue4 = floatValue * floatValue2 / 60.0F;
      switch (EmoteAnimationSwitchTable.countArray[emoteAnimation.ordinal()]) {
        case 1:
          TwerkingAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 2:
          TakeTheLAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 3:
          render(bipedEntityModel, floatValue4, floatValue3);
          break;
        case 4:
          render2(bipedEntityModel, floatValue4, floatValue3);
          break;
        case 5:
          render3(bipedEntityModel, floatValue4, floatValue3);
          break;
        case 6:
          WizoxBanAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 7:
          OrangeJusticeAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 8:
          PassinhoDoJamalAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 9:
          HoppinDihAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 10:
          LushLifeAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 11:
          FarexPullMyPearlAnimation.render(bipedEntityModel, floatValue, floatValue3);
          break;
        case 12:
          JerkingOffAnimation.render(bipedEntityModel, floatValue, floatValue3);
        case 13:
      }
    }
  }

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    float floatValue3 = computeFloat3(floatValue * 0.5F);
    float floatValue4 = computeFloat3(floatValue);
    float floatValue5 = computeFloat(floatValue * 0.5F);
    float floatValue6 = computeFloat2(floatValue * 2.0F);
    render(
        bipedEntityModel.getRootPart(),
        -1.05F * floatValue6,
        0.035F * floatValue4,
        0.08F * floatValue5,
        -0.045F * floatValue3,
        floatValue2);
    render(
        bipedEntityModel.body,
        0.1F + 0.08F * floatValue4,
        -0.07F * floatValue3,
        -0.09F * floatValue5,
        floatValue2);
    render(
        bipedEntityModel.head,
        -0.1F * floatValue4,
        0.1F * floatValue3,
        0.06F * floatValue5,
        floatValue2);
    render(
        bipedEntityModel.rightArm,
        -0.88F - 0.43F * floatValue3,
        -0.12F * floatValue4,
        0.56F + 0.18F * floatValue4,
        floatValue2);
    render(
        bipedEntityModel.leftArm,
        -0.88F + 0.43F * floatValue3,
        0.12F * floatValue4,
        -0.56F + 0.18F * floatValue4,
        floatValue2);
    render(
        bipedEntityModel.rightLeg,
        0.16F * floatValue3 - 0.1F * floatValue6,
        -0.04F * floatValue4,
        0.07F * floatValue5,
        floatValue2);
    render(
        bipedEntityModel.leftLeg,
        -0.16F * floatValue3 - 0.1F * floatValue6,
        0.04F * floatValue4,
        0.07F * floatValue5,
        floatValue2);
  }

  public static void render2(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    float floatValue3 = computeFloat(floatValue);
    float floatValue4 = computeFloat(floatValue * 0.5F);
    float floatValue5 = computeFloat(floatValue * 0.25F);
    float floatValue6 = computeFloat2(floatValue * 2.0F);
    render(
        bipedEntityModel.getRootPart(),
        -0.75F * floatValue6,
        0.045F,
        0.075F * floatValue5,
        0.08F * floatValue4,
        floatValue2);
    render(bipedEntityModel.body, 0.11F, -0.09F * floatValue5, 0.11F * floatValue4, floatValue2);
    render(bipedEntityModel.head, -0.035F, 0.11F * floatValue5, -0.075F * floatValue4, floatValue2);
    render(
        bipedEntityModel.rightArm,
        -0.35F - 0.34F * floatValue3,
        0.08F * floatValue4,
        0.27F + 0.13F * floatValue4,
        floatValue2);
    render(
        bipedEntityModel.leftArm,
        -0.35F + 0.34F * floatValue3,
        0.08F * floatValue4,
        -0.27F + 0.13F * floatValue4,
        floatValue2);
    render2(
        bipedEntityModel.rightLeg,
        0.12F * floatValue3 - 0.06F * floatValue6,
        -0.1F * floatValue4,
        -0.08F * floatValue4,
        floatValue2);
    render2(
        bipedEntityModel.leftLeg,
        -0.12F * floatValue3 - 0.06F * floatValue6,
        0.1F * floatValue4,
        -0.08F * floatValue4,
        floatValue2);
  }

  public static void render3(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    float floatValue3 = computeFloat(floatValue * 0.5F);
    float floatValue4 = computeFloat(floatValue * 0.25F);
    float floatValue5 = computeFloat(floatValue * 0.5F - 0.55F);
    float floatValue6 = computeFloat(floatValue * 0.5F + 0.55F);
    float floatValue7 = computeFloat2(floatValue);
    render(
        bipedEntityModel.getRootPart(),
        -0.7F * floatValue7,
        0.02F,
        0.1F * floatValue4,
        0.1F * floatValue3,
        floatValue2);
    render(bipedEntityModel.body, 0.065F, -0.09F * floatValue4, 0.16F * floatValue3, floatValue2);
    render(bipedEntityModel.head, -0.04F, 0.13F * floatValue4, -0.1F * floatValue3, floatValue2);
    render(
        bipedEntityModel.rightArm,
        -1.18F + 0.34F * floatValue5,
        -0.16F * floatValue4,
        0.86F + 0.28F * floatValue5,
        floatValue2);
    render(
        bipedEntityModel.leftArm,
        -1.18F + 0.34F * floatValue6,
        0.16F * floatValue4,
        -0.86F + 0.28F * floatValue6,
        floatValue2);
    render(
        bipedEntityModel.rightLeg,
        0.17F * floatValue3 - 0.06F * floatValue7,
        -0.04F,
        0.08F * floatValue3,
        floatValue2);
    render(
        bipedEntityModel.leftLeg,
        -0.17F * floatValue3 - 0.06F * floatValue7,
        0.04F,
        0.08F * floatValue3,
        floatValue2);
  }

  public static float computeFloat(float floatValue) {
    return (float) Math.sin(floatValue * (float) (Math.PI * 2));
  }

  public static float computeFloat2(float floatValue) {
    float floatValue2 = (float) Math.sin(computeFloat4(floatValue) * Math.PI);
    return floatValue2 * floatValue2;
  }

  public static float computeFloat3(float floatValue) {
    int intValue = (int) Math.floor(floatValue);
    float floatValue2 = (float) Math.sin(computeFloat4(floatValue) * Math.PI);
    floatValue2 *= floatValue2;
    return (intValue & 1) == 0 ? floatValue2 : -floatValue2;
  }

  public static float computeFloat4(float floatValue) {
    return floatValue - (float) Math.floor(floatValue);
  }

  public static void render(
      ModelPart modelPart,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    modelPart.originY += floatValue * floatValue5;
    modelPart.pitch = computeFloat(modelPart.pitch, floatValue2, floatValue5);
    modelPart.yaw = computeFloat(modelPart.yaw, floatValue3, floatValue5);
    modelPart.roll = computeFloat(modelPart.roll, floatValue4, floatValue5);
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

  public static void render2(
      ModelPart modelPart,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    modelPart.pitch += floatValue * floatValue4;
    modelPart.yaw += floatValue2 * floatValue4;
    modelPart.roll += floatValue3 * floatValue4;
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }
}

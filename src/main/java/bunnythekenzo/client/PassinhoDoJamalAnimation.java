package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class PassinhoDoJamalAnimation {
  public static final float[][] amountArray =
      new float[][] {
        {0.0F, -1.0F, 1.0F, 0.0F, 0.0F},
        {0.23F, -1.0F, 1.0F, 0.0F, 0.0F},
        {0.57F, -0.2F, 0.15F, 0.0F, 0.0F},
        {0.83F, 0.0F, 0.0F, -0.04F, 0.0F},
        {1.03F, 0.0F, 0.0F, -0.1F, 0.0F},
        {1.37F, 0.0F, 0.0F, -0.02F, 0.0F},
        {1.7F, 0.0F, 0.05F, 0.0F, 0.0F},
        {2.03F, 1.0F, 0.12F, 0.045F, -0.16F},
        {2.2F, 1.0F, 0.12F, 0.04F, -0.17F},
        {2.47F, 0.0F, 0.1F, -0.04F, 0.0F},
        {2.77F, -1.0F, 0.13F, -0.04F, 0.12F},
        {2.93F, -0.8F, 0.12F, -0.01F, 0.1F},
        {3.03F, -0.3F, 0.25F, 0.0F, 0.02F},
        {3.23F, -1.0F, 1.0F, -0.06F, 0.08F},
        {3.33F, -1.0F, 1.0F, -0.07F, 0.1F},
        {3.43F, 0.0F, 0.5F, 0.0F, 0.0F},
        {3.6F, 1.0F, 0.93F, 0.06F, -0.1F},
        {3.7F, 0.85F, 0.92F, 0.04F, -0.08F},
        {3.8F, 0.0F, 0.55F, 0.0F, 0.0F},
        {4.0F, -1.0F, 1.0F, -0.1F, 0.16F},
        {4.17F, -0.94F, 0.94F, -0.075F, 0.16F},
        {4.37F, -1.0F, 1.0F, -0.085F, 0.11F},
        {4.5F, 0.0F, 0.55F, 0.0F, 0.0F},
        {4.73F, 1.0F, 1.0F, 0.095F, -0.15F},
        {4.93F, 0.95F, 0.96F, 0.075F, -0.13F},
        {5.1F, 0.55F, 0.88F, 0.035F, -0.08F},
        {5.23F, 0.0F, 0.55F, 0.0F, 0.0F},
        {5.43F, -1.0F, 1.0F, -0.09F, 0.16F},
        {5.63F, -1.0F, 0.96F, -0.08F, 0.15F},
        {5.8F, -0.75F, 0.93F, -0.06F, 0.09F},
        {6.03F, -0.5F, 0.7F, -0.01F, 0.0F},
        {6.17F, -0.65F, 0.8F, 0.035F, -0.06F},
        {6.43F, -0.55F, 0.77F, 0.05F, -0.07F},
        {6.6F, -0.4F, 0.7F, 0.01F, -0.02F},
        {6.73F, 0.4F, 0.8F, 0.045F, -0.1F},
        {6.9F, 1.0F, 1.0F, 0.1F, -0.22F},
        {7.07F, 0.92F, 0.94F, 0.085F, -0.2F},
        {7.2F, 0.8F, 0.94F, 0.06F, -0.16F},
        {7.33F, 0.0F, 0.6F, 0.0F, -0.02F},
        {7.57F, -1.0F, 1.0F, -0.09F, 0.13F},
        {7.77F, -0.94F, 0.96F, -0.075F, 0.14F},
        {7.93F, -0.85F, 0.94F, -0.07F, 0.1F},
        {8.07F, 0.0F, 0.55F, 0.0F, 0.0F},
        {8.3F, 1.0F, 1.0F, 0.11F, -0.15F},
        {8.5F, 0.95F, 0.94F, 0.09F, -0.16F},
        {8.67F, 0.75F, 0.92F, 0.065F, -0.12F},
        {8.83F, -0.4F, 0.63F, 0.0F, -0.04F},
        {9.0F, -0.65F, 0.72F, -0.005F, 0.02F},
        {9.17F, -0.5F, 0.68F, -0.01F, 0.03F},
        {9.37F, -0.65F, 0.75F, -0.045F, 0.1F},
        {9.53F, -0.65F, 0.79F, -0.06F, 0.13F},
        {9.7F, -1.0F, 1.0F, -0.095F, 0.22F},
        {9.87F, -0.95F, 0.95F, -0.085F, 0.22F},
        {10.03F, -0.8F, 0.96F, -0.07F, 0.16F},
        {10.17F, 0.0F, 0.55F, 0.0F, 0.02F},
        {10.37F, 1.0F, 1.0F, 0.095F, -0.12F},
        {10.57F, 0.95F, 0.95F, 0.085F, -0.14F},
        {10.73F, 0.8F, 0.93F, 0.06F, -0.1F},
        {10.87F, 0.0F, 0.55F, 0.0F, 0.0F},
        {11.07F, -1.0F, 1.0F, -0.095F, 0.14F},
        {11.23F, -0.95F, 0.95F, -0.08F, 0.14F},
        {11.433F, -0.8F, 0.96F, -0.07F, 0.12F}
      };
  public static final float[][] amountArray2 =
      new float[][] {
        {0.64F, 1.0F, 0.36F},
        {0.86F, -1.0F, 0.3F},
        {1.12F, 1.0F, 0.22F},
        {1.33F, -1.0F, 0.22F},
        {1.92F, -1.0F, 0.22F},
        {2.38F, 1.0F, 0.22F},
        {2.72F, -1.0F, 0.2F},
        {3.27F, -1.0F, 1.0F},
        {3.6F, 1.0F, 0.86F},
        {4.0F, -1.0F, 1.0F},
        {4.37F, -1.0F, 0.9F},
        {4.77F, 1.0F, 1.0F},
        {5.1F, 1.0F, 0.87F},
        {5.43F, -1.0F, 1.0F},
        {5.8F, -1.0F, 0.86F},
        {6.13F, -1.0F, 0.96F},
        {6.47F, -1.0F, 1.05F},
        {6.9F, 1.0F, 1.0F},
        {7.2F, 1.0F, 0.86F},
        {7.57F, -1.0F, 1.04F},
        {7.93F, -1.0F, 0.91F},
        {8.3F, 1.0F, 1.0F},
        {8.63F, 1.0F, 0.88F},
        {8.97F, -1.0F, 0.98F},
        {9.33F, -1.0F, 0.96F},
        {9.7F, -1.0F, 1.0F},
        {10.03F, -1.0F, 0.9F},
        {10.37F, 1.0F, 0.99F},
        {10.73F, 1.0F, 0.88F},
        {11.07F, -1.0F, 1.0F},
        {11.4F, -1.0F, 0.87F}
      };

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (!(floatValue2 <= 0.001F)) {
      float floatValue3 = Math.clamp(floatValue, 0.0F, 11.433F);
      float floatValue4 = Math.clamp(floatValue2, 0.0F, 1.0F);
      int index = 0;

      while (index < amountArray.length - 2 && floatValue3 > amountArray[index + 1][0]) {
        index++;
      }

      float[] floatValues = amountArray[index];
      float[] floatValues2 = amountArray[index + 1];
      float floatValue5 =
          computeFloat(
              Math.clamp(
                  (floatValue3 - floatValues[0]) / (floatValues2[0] - floatValues[0]), 0.0F, 1.0F));
      float floatValue6 = computeFloat(floatValues[1], floatValues2[1], floatValue5);
      float floatValue7 = computeFloat(floatValues[2], floatValues2[2], floatValue5);
      float floatValue8 = computeFloat(floatValues[3], floatValues2[3], floatValue5);
      float floatValue9 = computeFloat(floatValues[4], floatValues2[4], floatValue5);
      float floatValue10 = computeFloat(floatValue3, 1.0F);
      float floatValue11 = computeFloat(floatValue3, -1.0F);
      float floatValue12 = floatValue11 - floatValue10;
      float floatValue13 = computeFloat(Math.clamp((floatValue3 - 1.55F) / 1.55F, 0.0F, 1.0F));
      float floatValue14 = (floatValue3 - 3.351F) * 170.0F / 60.0F;
      float floatValue15 = (float) Math.pow(Math.sin(Math.PI * floatValue14), 2.0) * floatValue13;
      float floatValue16 = 0.12F + 0.15F * floatValue15;
      float floatValue17 = floatValue16 + 1.2F * floatValue10;
      float floatValue18 = floatValue16 + 1.2F * floatValue11;
      bipedEntityModel.getRootPart().originX +=
          (-0.7F * floatValue12 + 0.28F * floatValue6 * floatValue13) * floatValue4;
      ModelPart rootPart = bipedEntityModel.getRootPart();
      rootPart.originY =
          rootPart.originY + (12.0F - 12.0F * (float) Math.cos(floatValue16 / 2.0F)) * floatValue4;
      rootPart = bipedEntityModel.getRootPart();
      rootPart.originZ =
          rootPart.originZ
              + (0.28F * floatValue15
                      + (floatValue3 < 1.45F ? -0.7F * computeFloat(floatValue3 / 1.45F) : -0.7F))
                  * floatValue4;
      bipedEntityModel.getRootPart().yaw =
          computeFloat(bipedEntityModel.getRootPart().yaw, floatValue9, floatValue4);
      float floatValue19 = 0.02F + 0.025F * floatValue15;
      Quaternionf quaternionf =
          new Quaternionf()
              .rotationZYX(
                  floatValue8 * floatValue4,
                  -0.25F * floatValue9 * floatValue4,
                  floatValue19 * floatValue4);
      render(bipedEntityModel.body, quaternionf, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, floatValue4);
      render(
          bipedEntityModel.head,
          quaternionf,
          0.0F,
          0.0F,
          0.0F,
          -0.035F - 0.045F * floatValue15,
          -0.4F * floatValue9,
          -0.4F * floatValue8,
          floatValue4);
      float floatValue20 = Math.max(floatValue6, 0.0F);
      float floatValue21 = Math.max(-floatValue6, 0.0F);
      render(
          bipedEntityModel.rightArm,
          quaternionf,
          true,
          floatValue20,
          floatValue21,
          floatValue7,
          floatValue15,
          floatValue4);
      render(
          bipedEntityModel.leftArm,
          quaternionf,
          false,
          floatValue21,
          floatValue20,
          floatValue7,
          floatValue15,
          floatValue4);
      render(
          bipedEntityModel.rightLeg, floatValue10, floatValue17, true, floatValue12, floatValue4);
      render(
          bipedEntityModel.leftLeg, floatValue11, floatValue18, false, floatValue12, floatValue4);
      EmoteBodyParts.render(
          bipedEntityModel,
          -(0.55F + floatValue20 * (0.25F + 1.12F * floatValue7)) * floatValue4,
          -(0.55F + floatValue21 * (0.25F + 1.12F * floatValue7)) * floatValue4,
          floatValue17 * floatValue4,
          floatValue18 * floatValue4);
    }
  }

  public static void render(
      ModelPart modelPart,
      Quaternionf quaternionf,
      boolean flag,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    float floatValue6 = flag ? 1.0F : -1.0F;
    float floatValue7 = -0.06F - 0.06F * floatValue4 - floatValue * (0.1F + 0.74F * floatValue3);
    float floatValue8 = -floatValue6 * floatValue * (0.65F + 0.13F * floatValue3);
    float floatValue9 = floatValue6 * (0.14F + 0.1F * floatValue2 - floatValue * 0.18F);
    render(
        modelPart,
        quaternionf,
        modelPart.getDefaultTransform().x(),
        modelPart.getDefaultTransform().y(),
        modelPart.getDefaultTransform().z(),
        floatValue7,
        floatValue8,
        floatValue9,
        floatValue5);
  }

  public static void render(
      ModelPart modelPart,
      float floatValue,
      float floatValue2,
      boolean flag,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = flag ? 1.0F : -1.0F;
    modelPart.pitch =
        computeFloat(modelPart.pitch, -floatValue2 / 2.0F - 0.43F * floatValue, floatValue4);
    modelPart.yaw =
        computeFloat(modelPart.yaw, -floatValue5 * (0.075F + 0.16F * floatValue), floatValue4);
    modelPart.roll =
        computeFloat(
            modelPart.roll,
            floatValue5 * (0.055F + 0.095F * floatValue) - 0.025F * floatValue3,
            floatValue4);
  }

  public static void render(
      ModelPart modelPart,
      Quaternionf quaternionf,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    Vector3f vector3f =
        quaternionf
            .transform(new Vector3f(floatValue, floatValue2 - 12.0F, floatValue3))
            .add(0.0F, 12.0F, 0.0F);
    modelPart.originX =
        computeFloat(modelPart.originX, floatValue, floatValue7) + vector3f.x - floatValue;
    modelPart.originY =
        computeFloat(modelPart.originY, floatValue2, floatValue7) + vector3f.y - floatValue2;
    modelPart.originZ =
        computeFloat(modelPart.originZ, floatValue3, floatValue7) + vector3f.z - floatValue3;
    Quaternionf quaternionf2 =
        new Quaternionf()
            .rotationZYX(
                computeFloat(modelPart.roll, floatValue6, floatValue7),
                computeFloat(modelPart.yaw, floatValue5, floatValue7),
                computeFloat(modelPart.pitch, floatValue4, floatValue7));
    Vector3f eulerAnglesZYX =
        new Quaternionf(quaternionf).mul(quaternionf2).getEulerAnglesZYX(new Vector3f());
    modelPart.setAngles(eulerAnglesZYX.x, eulerAnglesZYX.y, eulerAnglesZYX.z);
  }

  public static float computeFloat(float floatValue, float floatValue2) {
    float floatValue3 = 0.0F;

    for (float[] floatValues : amountArray2) {
      if (floatValues[1] == floatValue2) {
        float floatValue4 = floatValue - floatValues[0];
        float floatValue5 = floatValue4 < 0.0F ? 0.15F : 0.17F;
        if (Math.abs(floatValue4) < floatValue5) {
          floatValue3 =
              Math.max(
                  floatValue3,
                  (1.0F - computeFloat(Math.abs(floatValue4) / floatValue5)) * floatValues[2]);
        }
      }
    }

    return floatValue3;
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }

  public static float computeFloat(float floatValue) {
    return floatValue * floatValue * (3.0F - 2.0F * floatValue);
  }
}

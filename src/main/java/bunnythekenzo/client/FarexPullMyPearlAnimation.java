package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class FarexPullMyPearlAnimation {
  public static final float amount = 128.4F;
  public static final float amount2 = 0.26F;
  public static final long timestamp = 13200L;
  public static final float amount3 = (float) (Math.PI / 180.0);
  public static final TextKeyframeCurve textKeyframeCurve =
      TextKeyframeCurve.getTextKeyframeCurve(
          4,
          "-.56 -10 0 12 -18\n"
              + "0 -30 -10 30 -55\n"
              + ".45 -20 -5 18 -35\n"
              + "1 -78 -28 12 -60\n"
              + "1.25 -78 -28 12 -60\n"
              + "1.65 -32 -12 30 -85\n"
              + "2 -28 -8 58 -90\n"
              + "2.28 -28 -8 58 -90\n"
              + "2.75 -18 -5 24 -40\n"
              + "3 -62 -35 -12 -95\n"
              + "3.55 -65 -25 2 -70\n"
              + "4 -100 -12 8 -12\n"
              + "4.20 -100 -12 8 -12\n"
              + "4.75 -52 -12 32 -110\n"
              + "5 -30 -5 54 -125\n"
              + "5.18 -30 -5 54 -125\n"
              + "5.55 -82 -22 5 -25\n"
              + "6 -35 -5 42 -115\n"
              + "6.22 -35 -5 42 -115\n"
              + "6.5 -52 -35 -12 -100\n"
              + "7 -28 0 78 -80\n"
              + "7.6 -28 0 78 -80\n"
              + "8 -32 10 45 -60\n"
              + "8.5 -80 -35 -8 -95\n"
              + "9 -110 -12 18 -65\n"
              + "9.5 -60 5 70 -35\n"
              + "10 -15 12 45 -35\n"
              + "10.5 -65 -35 -8 -100\n"
              + "11 -95 -5 18 -80\n"
              + "11.5 -38 10 65 -35\n"
              + "12 -145 0 28 -40\n"
              + "12.25 -150 0 22 -30\n"
              + "12.75 -75 0 82 -28\n"
              + "13 -25 0 65 -65\n"
              + "13.35 -25 0 65 -65\n"
              + "13.5 -58 -18 28 -105\n"
              + "13.75 -20 0 70 -70\n"
              + "14 -58 -18 28 -105\n"
              + "14.18 -58 -18 28 -105\n"
              + "14.5 -22 0 70 -65\n"
              + "15 -50 -30 -8 -95\n"
              + "15.5 -50 -30 -8 -95\n"
              + "16 -42 -22 -10 -40\n"
              + "16.65 -18 15 35 -25\n"
              + "17 -60 -25 -8 -80\n"
              + "17.5 -90 -20 10 -65\n"
              + "18 -130 -5 30 -35\n"
              + "18.6 -110 0 55 -20\n"
              + "19 -30 8 65 -65\n"
              + "19.6 -40 -15 30 -80\n"
              + "20 -102 -10 10 -10\n"
              + "20.18 -102 -10 10 -10\n"
              + "20.5 -25 0 65 -120\n"
              + "20.75 -25 0 65 -120\n"
              + "21 -148 0 38 -15\n"
              + "21.2 -148 0 38 -15\n"
              + "21.5 -55 -25 -8 -105\n"
              + "22 -65 -30 -12 -100\n"
              + "22.25 -135 -8 48 -20\n"
              + "22.5 -65 -30 -12 -100\n"
              + "23 -25 0 85 -35\n"
              + "23.4 -25 0 85 -35\n"
              + "24 -70 -25 -8 -110\n"
              + "24.5 -65 -20 4 -110\n"
              + "25 -60 -15 8 -100\n"
              + "25.5 -72 -5 20 -65\n"
              + "26 -115 -12 38 -10\n"
              + "26.35 -115 -12 38 -10\n"
              + "27 -82 -12 20 -15\n"
              + "27.7 -82 -12 20 -15\n");
  public static final TextKeyframeCurve textKeyframeCurve2 =
      TextKeyframeCurve.getTextKeyframeCurve(
          4,
          "-.56 -10 0 -12 -18\n"
              + "0 -15 5 -22 -25\n"
              + ".5 -48 15 -28 -65\n"
              + "1 -28 8 -42 -95\n"
              + "1.4 -28 8 -42 -95\n"
              + "2 -65 30 10 -90\n"
              + "2.25 -65 30 10 -90\n"
              + "2.75 -35 5 -30 -50\n"
              + "3 -20 -10 -62 -65\n"
              + "3.55 -62 20 -8 -55\n"
              + "4 -95 12 -8 -18\n"
              + "4.2 -95 12 -8 -18\n"
              + "4.75 -62 8 -28 -90\n"
              + "5 -35 5 -48 -115\n"
              + "5.18 -35 5 -48 -115\n"
              + "5.55 -78 18 -6 -30\n"
              + "6 -28 -5 -55 -125\n"
              + "6.22 -28 -5 -55 -125\n"
              + "6.5 -90 5 -30 -65\n"
              + "7 -125 0 -30 -25\n"
              + "7.6 -125 0 -30 -25\n"
              + "8 -70 25 5 -100\n"
              + "8.5 -35 -10 -65 -35\n"
              + "9 -20 -10 -50 -30\n"
              + "9.5 -68 30 10 -95\n"
              + "10 -112 8 -24 -70\n"
              + "10.5 -58 -5 -70 -35\n"
              + "11 -20 -10 -42 -35\n"
              + "11.5 -65 25 5 -85\n"
              + "12 -60 0 -78 -35\n"
              + "12.5 -148 0 -25 -30\n"
              + "13 -95 0 -65 -20\n"
              + "13.35 -95 0 -65 -20\n"
              + "13.5 -25 0 -70 -60\n"
              + "13.75 -55 18 -28 -105\n"
              + "14 -20 0 -70 -70\n"
              + "14.18 -20 0 -70 -70\n"
              + "14.5 -55 18 -28 -105\n"
              + "15 -35 10 -45 -105\n"
              + "15.5 -35 10 -45 -105\n"
              + "16 -45 22 10 -35\n"
              + "16.65 -15 -15 -35 -25\n"
              + "17 -42 20 5 -60\n"
              + "17.5 -65 20 -10 -80\n"
              + "18 -95 15 -20 -60\n"
              + "18.6 -138 0 -35 -25\n"
              + "19 -90 -5 -70 -30\n"
              + "19.6 -45 15 -25 -75\n"
              + "20 -98 10 -10 -10\n"
              + "20.18 -98 10 -10 -10\n"
              + "20.5 -35 0 -55 -120\n"
              + "20.75 -35 0 -55 -120\n"
              + "21 -52 28 5 -100\n"
              + "21.2 -52 28 5 -100\n"
              + "21.5 -150 0 -35 -15\n"
              + "21.7 -150 0 -35 -15\n"
              + "22 -50 25 8 -100\n"
              + "22.25 -50 25 8 -100\n"
              + "22.5 -138 5 -48 -20\n"
              + "23 -25 0 -85 -35\n"
              + "23.4 -25 0 -85 -35\n"
              + "24 -70 25 8 -110\n"
              + "24.5 -65 20 -4 -110\n"
              + "25 -60 15 -8 -100\n"
              + "25.5 -30 10 -55 -80\n"
              + "26 -22 5 -62 -95\n"
              + "26.35 -22 5 -62 -95\n"
              + "27 -18 10 -42 -100\n"
              + "27.7 -18 10 -42 -100\n");
  public static final TextKeyframeCurve textKeyframeCurve3 =
      TextKeyframeCurve.getTextKeyframeCurve(
          6,
          "-.56 0 0 0 0 0 0\n"
              + "0 4 -8 -5 -8 12 4\n"
              + ".5 1 0 3 -3 -5 -3\n"
              + "1 -4 12 7 -8 -18 -6\n"
              + "1.25 -4 12 7 -8 -18 -6\n"
              + "2 6 -15 -9 3 20 7\n"
              + "2.25 6 -15 -9 3 20 7\n"
              + "3 -2 18 7 -10 -22 -5\n"
              + "3.5 8 0 0 0 0 0\n"
              + "4 16 -6 -3 -3 8 0\n"
              + "4.2 16 -6 -3 -3 8 0\n"
              + "5 -12 12 6 -14 -15 -5\n"
              + "5.18 -12 12 6 -14 -15 -5\n"
              + "5.55 12 -8 -4 0 10 2\n"
              + "6 -10 -14 -7 -12 18 5\n"
              + "6.22 -10 -14 -7 -12 18 5\n"
              + "7 -6 15 12 -9 -24 -10\n"
              + "7.6 -6 15 12 -9 -24 -10\n"
              + "8 6 10 7 -4 -15 -5\n"
              + "9 3 -15 -9 -8 18 5\n"
              + "10 8 14 9 -4 -18 -6\n"
              + "11 3 -12 -8 -8 16 5\n"
              + "12 -5 -12 -8 -18 18 6\n"
              + "12.5 -8 6 2 -20 -8 -2\n"
              + "13 2 16 9 -5 -20 -7\n"
              + "13.35 2 16 9 -5 -20 -7\n"
              + "13.5 8 -12 -7 0 15 5\n"
              + "13.75 -6 12 7 -10 -16 -5\n"
              + "14 8 -12 -7 0 15 5\n"
              + "14.18 8 -12 -7 0 15 5\n"
              + "14.5 -6 12 7 -10 -16 -5\n"
              + "15 -5 0 0 -10 20 -8\n"
              + "15.5 -5 0 0 -10 20 -8\n"
              + "16 26 -12 -10 8 18 6\n"
              + "16.65 20 12 10 4 -18 -6\n"
              + "17 30 16 8 12 -20 -6\n"
              + "17.5 12 5 2 -3 -8 -2\n"
              + "18 -14 -10 -6 -18 14 4\n"
              + "18.6 -8 -16 -10 -12 20 7\n"
              + "19 5 12 8 -5 -16 -6\n"
              + "19.6 10 0 0 0 0 0\n"
              + "20 20 -5 -4 2 8 0\n"
              + "20.18 20 -5 -4 2 8 0\n"
              + "20.5 -15 12 8 -15 -18 -6\n"
              + "20.75 -15 12 8 -15 -18 -6\n"
              + "21 -6 -18 -12 -12 25 8\n"
              + "21.2 -6 -18 -12 -12 25 8\n"
              + "21.5 -6 18 12 -12 -25 -8\n"
              + "21.7 -6 18 12 -12 -25 -8\n"
              + "22 12 0 0 0 0 0\n"
              + "22.25 -5 -16 -10 -10 20 7\n"
              + "22.5 -5 16 10 -10 -20 -7\n"
              + "23 -10 0 0 -16 0 0\n"
              + "23.4 -10 0 0 -16 0 0\n"
              + "24 8 -18 -5 -6 35 2\n"
              + "24.5 3 8 3 -8 25 -2\n"
              + "25 0 8 3 -8 20 -2\n"
              + "25.5 6 4 0 -8 10 0\n"
              + "26 -5 -12 -9 -12 20 6\n"
              + "26.35 -5 -12 -9 -12 20 6\n"
              + "27 -3 -8 -6 -8 15 4\n"
              + "27.7 -3 -8 -6 -8 15 4\n");
  public static final TextKeyframeCurve textKeyframeCurve4 =
      TextKeyframeCurve.getTextKeyframeCurve(
          13,
          "-.56 0 0 0 0 0  0 0 0 0 0  0 0 0\n"
              + "0 -8 -8 8 20 0  -5 8 -8 15 0  0 0 0\n"
              + ".5 -18 12 6 28 1.5  0 -8 -4 12 0  1 0 .2\n"
              + "1 0 -12 12 15 0  -10 12 -12 25 0  2 0 0\n"
              + "1.3 0 -12 12 15 0  -10 12 -12 25 0  2 0 0\n"
              + "2 -12 -12 10 28 0  0 12 -10 15 0  -2 0 0\n"
              + "2.3 -12 -12 10 28 0  0 12 -10 15 0  -2 0 0\n"
              + "3 0 -10 10 12 0  -22 10 -6 35 2  1 0 .2\n"
              + "3.5 -12 -10 12 30 0  -12 10 -12 30 0  0 -1 0\n"
              + "4 -12 -15 14 30 0  -4 12 -12 18 0  0 -1.8 0\n"
              + "4.2 -12 -15 14 30 0  -4 12 -12 18 0  0 -1.8 0\n"
              + "5 -2 -15 14 18 0  -24 8 -8 38 2.5  1 1.2 0\n"
              + "5.18 -2 -15 14 18 0  -24 8 -8 38 2.5  1 1.2 0\n"
              + "5.55 -15 -12 12 32 0  -10 12 -12 24 0  0 -1 0\n"
              + "6 -28 -8 8 42 2.8  0 15 -14 18 0  -1 1.3 0\n"
              + "6.22 -28 -8 8 42 2.8  0 15 -14 18 0  -1 1.3 0\n"
              + "7 -2 -18 16 18 0  -2 18 -16 18 0  -1 .3 0\n"
              + "7.6 -2 -18 16 18 0  -2 18 -16 18 0  -1 .3 0\n"
              + "8 -18 18 6 30 1.8  0 -12 -6 15 0  2 0 .15\n"
              + "8.5 10 -18 8 12 0  -6 15 -8 18 0  2.5 -.8 0\n"
              + "9 0 10 6 15 0  -28 -15 -4 45 2.6  1.2 0 .3\n"
              + "9.5 -6 -15 8 18 0  12 18 -8 12 0  -1.2 .8 0\n"
              + "10 -32 15 4 48 2.5  0 -10 -6 15 0  -2 0 .4\n"
              + "10.5 12 -18 8 12 0  -8 15 -8 20 0  -2 -.8 0\n"
              + "11 0 12 6 15 0  -20 -15 -6 32 1.7  -.8 0 .15\n"
              + "11.5 -5 -12 8 18 0  8 18 -8 10 0  .8 .5 0\n"
              + "12 -10 -15 14 25 0  -10 15 -14 25 0  0 0 0\n"
              + "12.5 -2 -10 8 10 0  -26 10 -4 40 2  1 0 .3\n"
              + "13 -12 -15 14 28 0  -8 15 -14 20 0  2 0 0\n"
              + "13.35 -12 -15 14 28 0  -8 15 -14 20 0  2 0 0\n"
              + "13.5 -16 -12 12 35 0  -5 12 -12 16 0  -1 0 0\n"
              + "13.75 -5 -12 12 16 0  -16 12 -12 35 0  1 0 0\n"
              + "14 -16 -12 12 35 0  -5 12 -12 16 0  -1 0 0\n"
              + "14.18 -16 -12 12 35 0  -5 12 -12 16 0  -1 0 0\n"
              + "14.5 -5 -12 12 16 0  -16 12 -12 35 0  1 0 0\n"
              + "15 -2 -5 4 10 0  -35 8 -4 55 3.5  0 0 0\n"
              + "15.5 -2 -5 4 10 0  -35 8 -4 55 3.5  0 0 0\n"
              + "16 -32 -18 22 70 0  -25 18 -22 60 0  -2 0 0\n"
              + "16.65 -24 -18 22 58 0  -34 18 -22 72 0  2 0 0\n"
              + "17 -28 -18 20 65 0  -28 18 -20 65 0  1 -1 0\n"
              + "17.5 -18 -15 16 42 0  -18 15 -16 42 0  0 0 0\n"
              + "18 -3 -12 12 15 0  -3 12 -12 15 0  -1 1 0\n"
              + "18.6 -5 -10 10 15 0  -20 12 -6 30 1.5  -2 0 .2\n"
              + "19 -24 -10 8 38 2  0 15 -10 15 0  1 0 0\n"
              + "19.6 -12 -12 12 28 0  -12 12 -12 28 0  0 -.5 0\n"
              + "20 -15 -18 18 36 0  -5 15 -14 20 0  0 -2 0\n"
              + "20.18 -15 -18 18 36 0  -5 15 -14 20 0  0 -2 0\n"
              + "20.5 0 -15 16 15 0  -40 8 -6 60 4  1 1.5 .25\n"
              + "20.75 0 -15 16 15 0  -40 8 -6 60 4  1 1.5 .25\n"
              + "21 -5 -18 18 16 0  -12 18 -18 30 0  -2 0 0\n"
              + "21.2 -5 -18 18 16 0  -12 18 -18 30 0  -2 0 0\n"
              + "21.5 -12 -18 18 30 0  -5 18 -18 16 0  2 0 0\n"
              + "21.7 -12 -18 18 30 0  -5 18 -18 16 0  2 0 0\n"
              + "22 -20 -12 12 45 0  -20 12 -12 45 0  0 0 0\n"
              + "22.25 -8 -18 18 20 0  -20 18 -14 38 1  -1 0 .1\n"
              + "22.5 -22 -18 14 40 1  -8 18 -18 20 0  1 0 .1\n"
              + "23 -4 -20 20 18 0  -4 20 -20 18 0  0 0 0\n"
              + "23.4 -4 -20 20 18 0  -4 20 -20 18 0  0 0 0\n"
              + "24 -14 12 5 32 0  -10 -12 -5 25 0  0 0 0\n"
              + "24.5 -28 -10 3 42 2.5  0 8 -3 12 0  0 0 .3\n"
              + "25 0 -8 3 12 0  -28 10 -3 42 2.5  0 0 .3\n"
              + "25.5 -15 -12 10 30 0  -15 12 -10 30 0  0 0 0\n"
              + "26 -4 -18 18 15 0  -12 18 -14 28 0  -1 0 0\n"
              + "26.35 -4 -18 18 15 0  -12 18 -14 28 0  -1 0 0\n"
              + "27 -3 -12 12 14 0  -3 12 -12 14 0  0 0 0\n"
              + "27.7 -3 -12 12 14 0  -3 12 -12 14 0  0 0 0\n");
  public static final TextKeyframeCurve textKeyframeCurve5 =
      TextKeyframeCurve.getTextKeyframeCurve(
          1, "-.56 0\n23.4 0\n24 -25\n24.5 75\n25 220\n25.5 345\n26 360\n27.7 360\n");

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (Float.isFinite(floatValue) && Float.isFinite(floatValue2) && !(floatValue2 <= 0.0F)) {
      float floatValue3 = Math.clamp(floatValue2, 0.0F, 1.0F);
      float floatValue4 = (Math.clamp(floatValue, 0.0F, 13.2F) - 0.26F) * 128.4F / 60.0F;
      render(bipedEntityModel.rightArm, textKeyframeCurve, 0, floatValue4, floatValue3);
      render(bipedEntityModel.leftArm, textKeyframeCurve2, 0, floatValue4, floatValue3);
      render(bipedEntityModel.body, textKeyframeCurve3, 0, floatValue4, floatValue3);
      render(bipedEntityModel.head, textKeyframeCurve3, 3, floatValue4, floatValue3);
      render(bipedEntityModel.rightLeg, textKeyframeCurve4, 0, floatValue4, floatValue3);
      render(bipedEntityModel.leftLeg, textKeyframeCurve4, 5, floatValue4, floatValue3);
      float floatValue5 =
          computeFloat(
              bipedEntityModel.rightArm,
              textKeyframeCurve.computeFloat(floatValue4, 3),
              floatValue3,
              true);
      float floatValue6 =
          computeFloat(
              bipedEntityModel.leftArm,
              textKeyframeCurve2.computeFloat(floatValue4, 3),
              floatValue3,
              true);
      float floatValue7 =
          computeFloat(
              bipedEntityModel.rightLeg,
              textKeyframeCurve4.computeFloat(floatValue4, 3),
              floatValue3,
              false);
      float floatValue8 =
          computeFloat(
              bipedEntityModel.leftLeg,
              textKeyframeCurve4.computeFloat(floatValue4, 8),
              floatValue3,
              false);
      EmoteBodyParts.render(bipedEntityModel, floatValue5, floatValue6, floatValue7, floatValue8);
      bipedEntityModel.rightLeg.originY =
          bipedEntityModel.rightLeg.originY
              - textKeyframeCurve4.computeFloat(floatValue4, 4) * floatValue3;
      bipedEntityModel.leftLeg.originY =
          bipedEntityModel.leftLeg.originY
              - textKeyframeCurve4.computeFloat(floatValue4, 9) * floatValue3;
      float pitch = bipedEntityModel.body.pitch;
      float yaw = bipedEntityModel.body.yaw;
      float roll = bipedEntityModel.body.roll;
      render(bipedEntityModel.body, pitch, yaw, roll);
      render(bipedEntityModel.head, pitch, yaw, roll);
      render(bipedEntityModel.rightArm, pitch, yaw, roll);
      render(bipedEntityModel.leftArm, pitch, yaw, roll);
      ModelPart rootPart = bipedEntityModel.getRootPart();
      rootPart.originX =
          rootPart.originX + textKeyframeCurve4.computeFloat(floatValue4, 10) * floatValue3;
      rootPart.originZ =
          rootPart.originZ + textKeyframeCurve4.computeFloat(floatValue4, 11) * floatValue3;
      float value = textKeyframeCurve5.computeFloat(floatValue4, 0);
      rootPart.yaw +=
          (floatValue4 >= 26.0F ? value - 360.0F : value) * (float) (Math.PI / 180.0) * floatValue3;
      float floatValue9 =
          Math.max(
              computeFloat(bipedEntityModel.rightLeg, floatValue7),
              computeFloat(bipedEntityModel.leftLeg, floatValue8));
      rootPart.originY =
          rootPart.originY
              + (24.0F
                  - floatValue9
                  - textKeyframeCurve4.computeFloat(floatValue4, 12) * floatValue3);
    }
  }

  public static void render(
      ModelPart modelPart,
      TextKeyframeCurve textKeyframeCurve6,
      int intValue,
      float floatValue,
      float floatValue2) {
    modelPart.pitch =
        modelPart.pitch
            + (textKeyframeCurve6.computeFloat(floatValue, intValue) * (float) (Math.PI / 180.0)
                    - modelPart.pitch)
                * floatValue2;
    modelPart.yaw =
        modelPart.yaw
            + (textKeyframeCurve6.computeFloat(floatValue, intValue + 1) * (float) (Math.PI / 180.0)
                    - modelPart.yaw)
                * floatValue2;
    modelPart.roll =
        modelPart.roll
            + (textKeyframeCurve6.computeFloat(floatValue, intValue + 2) * (float) (Math.PI / 180.0)
                    - modelPart.roll)
                * floatValue2;
  }

  public static float computeFloat(
      ModelPart modelPart, float floatValue, float floatValue2, boolean flag) {
    float floatValue3 = floatValue * (float) (Math.PI / 180.0) * floatValue2;
    modelPart.pitch =
        modelPart.pitch
            - (float)
                Math.atan2(
                    6.0F * computeFloat(floatValue3),
                    (flag ? 4 : 6) + 6.0F * computeFloat2(floatValue3));
    return floatValue3;
  }

  public static void render(
      ModelPart modelPart, float floatValue, float floatValue2, float floatValue3) {
    float originX = modelPart.originX;
    float floatValue4 = modelPart.originY - 12.0F;
    float originZ = modelPart.originZ;
    float floatValue5 =
        floatValue4 * computeFloat2(floatValue) - originZ * computeFloat(floatValue);
    float floatValue6 =
        floatValue4 * computeFloat(floatValue) + originZ * computeFloat2(floatValue);
    float floatValue7 =
        originX * computeFloat2(floatValue2) + floatValue6 * computeFloat(floatValue2);
    float originZ2 =
        -originX * computeFloat(floatValue2) + floatValue6 * computeFloat2(floatValue2);
    modelPart.originX =
        floatValue7 * computeFloat2(floatValue3) - floatValue5 * computeFloat(floatValue3);
    modelPart.originY =
        12.0F + floatValue7 * computeFloat(floatValue3) + floatValue5 * computeFloat2(floatValue3);
    modelPart.originZ = originZ2;
  }

  public static float computeFloat(ModelPart modelPart, float floatValue) {
    float floatValue2 = -Float.MAX_VALUE;

    for (int index = 0; index < 8; index++) {
      float floatValue3 = (index & 1) == 0 ? -2.0F : 2.0F;
      float floatValue4 = (index & 2) == 0 ? 0.0F : 6.0F;
      float floatValue5 = (index & 4) == 0 ? -2.0F : 2.0F;
      float floatValue6 =
          floatValue4 * computeFloat2(floatValue) - floatValue5 * computeFloat(floatValue);
      floatValue5 =
          floatValue4 * computeFloat(floatValue) + floatValue5 * computeFloat2(floatValue);
      floatValue4 = 6.0F + floatValue6;
      float floatValue7 =
          floatValue4 * computeFloat2(modelPart.pitch)
              - floatValue5 * computeFloat(modelPart.pitch);
      float floatValue8 =
          floatValue4 * computeFloat(modelPart.pitch)
              + floatValue5 * computeFloat2(modelPart.pitch);
      float floatValue9 =
          floatValue3 * computeFloat2(modelPart.yaw) + floatValue8 * computeFloat(modelPart.yaw);
      floatValue2 =
          Math.max(
              floatValue2,
              modelPart.originY
                  + floatValue9 * computeFloat(modelPart.roll)
                  + floatValue7 * computeFloat2(modelPart.roll));
    }

    return floatValue2;
  }

  public static float computeFloat(float floatValue) {
    return (float) Math.sin(floatValue);
  }

  public static float computeFloat2(float floatValue) {
    return (float) Math.cos(floatValue);
  }
}

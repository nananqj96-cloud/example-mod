package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class LushLifeAnimation {
  public static final float amount = 98.0F;
  public static final long timestamp = 14419L;
  public static final float amount2 = (float) (Math.PI / 180.0);
  public static final MonotonePoseCurve monotonePoseCurve =
      MonotonePoseCurve.getMonotonePoseCurve(
          3,
          "0.000 -8 0 20\n"
              + "0.400 -5 0 16\n"
              + "0.767 -12 -4 20\n"
              + "1.100 -6 0 14\n"
              + "1.433 -4 0 12\n"
              + "1.800 -12 -5 20\n"
              + "2.000 -22 -8 27\n"
              + "2.200 -48 -15 35\n"
              + "2.433 -22 -8 20\n"
              + "2.700 -58 -15 30\n"
              + "2.933 -43 -8 28\n"
              + "3.100 -27 -12 25\n"
              + "3.333 -42 -15 42\n"
              + "3.533 -18 -5 28\n"
              + "3.733 -4 0 10\n"
              + "4.067 -10 -5 18\n"
              + "4.333 -12 -8 21\n"
              + "4.600 -24 -10 64\n"
              + "4.800 -22 -8 70\n"
              + "5.000 -26 -10 66\n"
              + "5.233 -24 -8 71\n"
              + "5.533 -18 -8 55\n"
              + "5.767 -12 -4 29\n"
              + "6.067 -5 0 13\n"
              + "6.233 -15 -10 8\n"
              + "6.400 -65 -45 -8\n"
              + "6.567 -115 -10 15\n"
              + "6.733 -35 -8 12\n"
              + "6.933 -8 0 13\n"
              + "7.167 -12 -4 9\n"
              + "7.400 -10 0 14\n"
              + "7.633 -45 -20 10\n"
              + "7.767 -95 -12 12\n"
              + "7.933 -74 -34 -8\n"
              + "8.033 -72 -28 12\n"
              + "8.200 -139 -6 24\n"
              + "8.400 -153 0 20\n"
              + "8.667 -151 0 19\n"
              + "9.100 -150 -3 22\n"
              + "9.400 -151 0 18\n"
              + "9.767 -148 -4 22\n"
              + "10.133 -145 -4 22\n"
              + "10.367 -131 -5 26\n"
              + "10.567 -48 -8 25\n"
              + "10.733 -28 -8 22\n"
              + "10.933 -48 -15 37\n"
              + "11.167 -45 -12 35\n"
              + "11.400 -65 -10 20\n"
              + "11.600 -56 -4 17\n"
              + "11.800 -34 -10 13\n"
              + "12.033 -93 -33 -6\n"
              + "12.200 -83 -36 -8\n"
              + "12.433 -40 -25 -8\n"
              + "12.667 -30 -15 5\n"
              + "12.867 -56 -5 12\n"
              + "13.033 -100 -8 15\n"
              + "13.200 -76 -5 15\n"
              + "13.400 -8 0 17\n"
              + "13.733 -14 -5 23\n"
              + "14.067 -9 -5 20\n"
              + "14.167 -38 -15 15\n"
              + "14.300 -116 -8 25\n"
              + "14.419 -122 -5 25\n");
  public static final MonotonePoseCurve monotonePoseCurve2 =
      MonotonePoseCurve.getMonotonePoseCurve(
          3,
          "0.000 -10 0 -23\n"
              + "0.400 -5 0 -16\n"
              + "0.767 -4 0 -12\n"
              + "1.100 -8 3 -16\n"
              + "1.433 -7 0 -15\n"
              + "1.800 -16 5 -22\n"
              + "2.033 -36 12 -26\n"
              + "2.200 -62 18 -30\n"
              + "2.433 -35 10 -20\n"
              + "2.700 -60 18 -35\n"
              + "2.933 -36 10 -34\n"
              + "3.100 -28 8 -33\n"
              + "3.333 -46 14 -42\n"
              + "3.567 -38 8 -22\n"
              + "3.767 -4 0 -10\n"
              + "4.067 -7 0 -16\n"
              + "4.300 -18 5 -16\n"
              + "4.433 -65 38 8\n"
              + "4.600 -140 8 -25\n"
              + "4.767 -82 12 -65\n"
              + "4.933 -20 10 -35\n"
              + "5.067 -66 38 8\n"
              + "5.200 -138 8 -25\n"
              + "5.400 -60 10 -58\n"
              + "5.567 -15 5 -22\n"
              + "5.700 -62 38 10\n"
              + "5.867 -115 8 -16\n"
              + "6.033 -73 10 -20\n"
              + "6.167 -8 0 -15\n"
              + "6.400 -14 8 -20\n"
              + "6.667 -13 5 -22\n"
              + "6.800 -53 12 -25\n"
              + "7.000 -118 12 -18\n"
              + "7.200 -80 43 10\n"
              + "7.400 -90 5 -6\n"
              + "7.633 -92 0 -6\n"
              + "7.833 -93 2 -8\n"
              + "8.000 -48 30 10\n"
              + "8.233 -24 18 11\n"
              + "8.500 -23 20 12\n"
              + "8.900 -24 20 12\n"
              + "9.300 -23 20 12\n"
              + "9.467 -65 30 8\n"
              + "9.633 -90 40 10\n"
              + "9.767 -94 25 -8\n"
              + "9.967 -57 4 -65\n"
              + "10.100 -61 8 -53\n"
              + "10.267 -90 42 10\n"
              + "10.433 -55 10 -48\n"
              + "10.633 -18 4 -20\n"
              + "10.867 -30 8 -30\n"
              + "11.067 -42 12 -40\n"
              + "11.267 -48 8 -34\n"
              + "11.467 -67 5 -20\n"
              + "11.600 -55 4 -17\n"
              + "11.800 -34 10 -13\n"
              + "12.033 -93 33 6\n"
              + "12.200 -83 36 8\n"
              + "12.433 -40 25 8\n"
              + "12.667 -30 15 -5\n"
              + "12.867 -56 5 -12\n"
              + "13.033 -100 8 -15\n"
              + "13.200 -76 5 -15\n"
              + "13.400 -8 0 -17\n"
              + "13.733 -14 5 -23\n"
              + "14.067 -9 5 -20\n"
              + "14.167 -38 15 -15\n"
              + "14.300 -116 8 -25\n"
              + "14.419 -122 5 -25\n");
  public static final MonotonePoseCurve monotonePoseCurve3 =
      MonotonePoseCurve.getMonotonePoseCurve(
          6,
          "0.000 0 0 -3 -4 -3 -12\n"
              + "0.400 0 0 -1 -2 0 -3\n"
              + "0.767 1 -2 3 -4 2 6\n"
              + "1.100 0 0 0 -2 0 1\n"
              + "1.433 1 2 -3 -2 -2 -4\n"
              + "1.800 2 0 2 -3 2 4\n"
              + "2.200 2 -3 -3 -5 2 -5\n"
              + "2.433 1 0 0 -2 0 0\n"
              + "2.700 2 3 2 -4 -2 4\n"
              + "3.100 0 -2 -3 -5 3 -6\n"
              + "3.433 1 2 3 -6 -3 6\n"
              + "3.767 0 0 0 -3 0 0\n"
              + "4.233 1 0 -1 -2 0 -2\n"
              + "4.633 3 -6 -4 -5 4 -5\n"
              + "4.933 2 4 4 -3 -4 3\n"
              + "5.233 3 -5 -4 -4 4 -5\n"
              + "5.533 2 5 4 -3 -3 5\n"
              + "5.867 2 -5 -5 -5 5 -7\n"
              + "6.167 1 0 0 -4 0 0\n"
              + "6.533 1 7 6 -6 -5 7\n"
              + "6.800 1 0 0 -3 0 0\n"
              + "7.100 2 -7 -6 -5 4 -6\n"
              + "7.433 3 -3 -3 -5 2 -4\n"
              + "7.800 3 4 4 -6 -3 4\n"
              + "8.233 -2 -4 -3 -8 3 -5\n"
              + "8.533 -3 -6 -5 -6 -15 -5\n"
              + "8.767 -4 -8 -6 -5 -25 -6\n"
              + "9.067 0 -5 -3 -4 -24 -5\n"
              + "9.300 -4 -8 -6 -6 -24 -6\n"
              + "9.633 -1 -5 -3 -5 -22 -4\n"
              + "9.933 -4 -7 -5 -6 -24 -6\n"
              + "10.267 -1 -5 -2 -4 -21 -3\n"
              + "10.467 1 0 0 -3 -12 0\n"
              + "10.667 2 5 1 -3 16 0\n"
              + "10.933 3 8 2 -4 20 2\n"
              + "11.167 2 6 1 -4 22 2\n"
              + "11.400 1 3 -2 -5 15 -4\n"
              + "11.600 1 0 0 -4 0 0\n"
              + "12.033 -3 -2 -2 -7 2 -4\n"
              + "12.300 5 0 2 -1 0 2\n"
              + "12.600 5 0 0 1 0 0\n"
              + "12.967 -2 0 -2 -5 0 -3\n"
              + "13.267 2 0 2 -2 0 4\n"
              + "13.500 1 -3 -4 -4 3 -7\n"
              + "13.833 1 3 4 -4 -3 5\n"
              + "14.067 0 0 0 -3 0 0\n"
              + "14.300 3 0 1 -5 0 1\n"
              + "14.419 2 0 0 -4 0 0\n");
  public static final MonotonePoseCurve monotonePoseCurve4 =
      MonotonePoseCurve.getMonotonePoseCurve(
          10,
          "0.000 0 -3 1 0 3 -1 0 0 0 0\n"
              + "0.400 -3 -2 0 2 2 0 0 0 -0.2 0\n"
              + "0.767 2 -2 0 -3 2 0 0 0 0.2 0\n"
              + "1.100 -2 -2 0 1 2 0 0 0 0 0\n"
              + "1.433 0 -3 1 -12 4 -3 0 0.9 -0.3 0\n"
              + "1.667 -8 -3 2 0 3 -1 0.5 0 0.2 0\n"
              + "1.900 0 -3 0 -6 3 -1 0 0.4 -0.2 0\n"
              + "2.200 -5 -4 2 1 4 -2 0.2 0 -0.2 0\n"
              + "2.433 1 -3 1 -4 3 -1 0 0.2 0.2 0\n"
              + "2.700 -3 -3 1 1 3 -1 0 0 0 0\n"
              + "3.100 1 -3 1 -3 3 -1 0 0 0.2 0\n"
              + "3.433 -3 -3 1 1 3 -1 0 0 -0.2 0\n"
              + "3.767 0 -3 1 0 3 -1 0 0 0 0\n"
              + "4.267 0 -4 2 -4 5 -3 0 0.3 0 0\n"
              + "4.567 0 -5 3 -14 7 -17 0 1.2 0.3 0\n"
              + "4.800 -7 -8 9 -2 8 -14 0 0 0.7 0\n"
              + "5.033 -3 -8 13 -8 8 -9 0 0 -0.5 0\n"
              + "5.300 -9 -8 8 -2 8 -14 0 0 0.6 0\n"
              + "5.600 -2 -8 14 -8 8 -9 0 0 -0.5 0\n"
              + "5.900 -8 -8 9 -2 8 -14 0 0 0.6 0\n"
              + "6.200 -2 -8 13 -7 8 -9 0 0 -0.5 0\n"
              + "6.500 -8 -8 9 -2 8 -14 0 0 0.6 0\n"
              + "6.800 -2 -6 10 -8 6 -10 0 0 -0.3 0\n"
              + "7.033 0 -4 4 -17 5 -8 0 1.4 0.2 0\n"
              + "7.267 -8 -3 1 -2 3 -1 0.4 0 0 0\n"
              + "7.433 -3 -3 1 2 3 -1 0 0 -0.2 0\n"
              + "7.767 2 -3 1 -4 3 -1 0 0 0.2 0\n"
              + "8.067 -3 -3 1 0 3 -1 0 0 0 0\n"
              + "8.333 -5 0 1 0 4 -1 0.2 0 0 0\n"
              + "8.567 -13 -5 2 -8 4 -2 0 0 -0.5 0.5\n"
              + "8.767 -21 -3 2 -15 5 -2 0 0 -0.8 0.8\n"
              + "9.033 -8 -3 2 -5 5 -2 0 0 -0.3 0.4\n"
              + "9.300 -22 -3 2 -16 5 -2 0 0 -0.8 0.8\n"
              + "9.600 -9 -3 2 -6 5 -2 0 0 -0.3 0.4\n"
              + "9.933 -22 -3 2 -16 5 -2 0 0 -0.8 0.8\n"
              + "10.233 -9 -3 2 -6 5 -2 0 0 -0.3 0.4\n"
              + "10.467 -6 -4 2 -10 5 -3 0 0.5 0 0.3\n"
              + "10.733 -10 -8 3 8 8 -4 0.8 0 -0.4 0.6\n"
              + "10.933 5 -5 2 23 6 -5 0 1.7 -0.3 1\n"
              + "11.133 -10 -4 4 32 8 -6 0 2.1 0 1.6\n"
              + "11.333 16 -5 3 -9 4 -3 1.2 0 0.3 2.1\n"
              + "11.567 -4 -3 1 0 3 -1 0 0 0 2.5\n"
              + "11.800 0 -3 1 9 3 -1 0 0.6 0.1 2.8\n"
              + "12.033 11 -3 2 -5 3 -1 0.8 0 -0.2 3.3\n"
              + "12.233 -6 -3 1 1 3 -1 0 0 0 3.8\n"
              + "12.433 -4 -3 1 12 3 -2 0 0.9 0.2 4.3\n"
              + "12.667 10 -3 2 -4 3 -1 0.7 0 -0.2 4.7\n"
              + "12.867 -4 -3 1 8 3 -1 0 0.5 0.1 5\n"
              + "13.067 -3 -3 1 0 3 -1 0 0 0 5.2\n"
              + "13.267 -3 -5 5 -12 8 -16 0 1 0.4 5.2\n"
              + "13.500 -7 -8 9 -2 8 -14 0 0 0.6 5.2\n"
              + "13.800 -2 -8 14 -7 8 -9 0 0 -0.5 5.2\n"
              + "14.067 -6 -8 10 -3 8 -13 0 0 0.4 5.2\n"
              + "14.300 -8 -6 8 -8 6 -8 0 0 0 5.2\n"
              + "14.419 -5 -6 8 -5 6 -8 0 0 0 5.2\n");
  public static final MonotonePoseCurve monotonePoseCurve5 =
      MonotonePoseCurve.getMonotonePoseCurve(
          1,
          "0.000 0\n"
              + "8.267 0\n"
              + "8.500 12\n"
              + "8.733 36\n"
              + "10.367 36\n"
              + "10.533 50\n"
              + "10.733 98\n"
              + "10.933 172\n"
              + "11.133 219\n"
              + "11.333 287\n"
              + "11.567 360\n"
              + "14.419 360\n");
  public static final MonotonePoseCurve monotonePoseCurve6 =
      MonotonePoseCurve.getMonotonePoseCurve(
          4,
          "0.000 -18 -20 8 8\n"
              + "1.100 -15 -15 6 6\n"
              + "1.433 -15 -15 5 20\n"
              + "1.800 -20 -25 18 8\n"
              + "2.200 -80 -90 12 8\n"
              + "2.433 -50 -65 8 12\n"
              + "2.700 -95 -95 10 10\n"
              + "3.100 -65 -55 10 8\n"
              + "3.333 -70 -75 8 10\n"
              + "3.767 -12 -12 8 8\n"
              + "4.333 -15 -35 10 10\n"
              + "4.600 -22 -118 12 25\n"
              + "4.800 -15 -55 18 10\n"
              + "5.067 -18 -90 10 20\n"
              + "5.200 -20 -118 18 10\n"
              + "5.400 -18 -60 12 16\n"
              + "5.567 -20 -25 10 20\n"
              + "5.867 -20 -112 20 10\n"
              + "6.167 -18 -18 10 16\n"
              + "6.400 -95 -22 12 16\n"
              + "6.567 -110 -20 18 10\n"
              + "6.800 -20 -55 10 15\n"
              + "7.033 -18 -112 8 28\n"
              + "7.200 -20 -95 18 10\n"
              + "7.433 -18 -18 10 10\n"
              + "7.767 -75 -20 12 12\n"
              + "7.933 -85 -40 12 12\n"
              + "8.200 -112 -75 8 8\n"
              + "8.500 -118 -70 18 15\n"
              + "8.767 -118 -70 40 34\n"
              + "9.033 -118 -70 18 16\n"
              + "9.300 -118 -70 40 34\n"
              + "9.600 -118 -50 18 16\n"
              + "9.933 -116 -70 40 34\n"
              + "10.233 -112 -40 18 16\n"
              + "10.467 -95 -70 20 20\n"
              + "10.733 -55 -40 24 18\n"
              + "10.933 -70 -65 12 35\n"
              + "11.133 -65 -60 15 45\n"
              + "11.333 -75 -65 32 15\n"
              + "11.600 -80 -80 12 12\n"
              + "11.800 -65 -65 10 20\n"
              + "12.033 -100 -100 22 12\n"
              + "12.433 -80 -80 12 24\n"
              + "12.667 -55 -55 22 12\n"
              + "13.033 -100 -100 12 12\n"
              + "13.400 -20 -20 15 20\n"
              + "13.733 -20 -20 20 12\n"
              + "14.067 -20 -20 12 20\n"
              + "14.300 -108 -108 20 20\n"
              + "14.419 -108 -108 15 15\n");

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (Float.isFinite(floatValue) && Float.isFinite(floatValue2) && !(floatValue2 <= 0.0F)) {
      float floatValue3 = Math.clamp(floatValue2, 0.0F, 1.0F);
      float floatValue4 = Math.clamp(floatValue, 0.0F, 14.419F);
      render(bipedEntityModel.rightArm, monotonePoseCurve, 0, floatValue4, floatValue3);
      render(bipedEntityModel.leftArm, monotonePoseCurve2, 0, floatValue4, floatValue3);
      render(bipedEntityModel.body, monotonePoseCurve3, 0, floatValue4, floatValue3);
      render(bipedEntityModel.head, monotonePoseCurve3, 3, floatValue4, floatValue3);
      render(bipedEntityModel.rightLeg, monotonePoseCurve4, 0, floatValue4, floatValue3);
      render(bipedEntityModel.leftLeg, monotonePoseCurve4, 3, floatValue4, floatValue3);
      float floatValue5 =
          computeFloat(
              bipedEntityModel.rightArm,
              monotonePoseCurve6.computeFloat(floatValue4, 0),
              floatValue3,
              true);
      float floatValue6 =
          computeFloat(
              bipedEntityModel.leftArm,
              monotonePoseCurve6.computeFloat(floatValue4, 1),
              floatValue3,
              true);
      float floatValue7 =
          computeFloat(
              bipedEntityModel.rightLeg,
              monotonePoseCurve6.computeFloat(floatValue4, 2),
              floatValue3,
              false);
      float floatValue8 =
          computeFloat(
              bipedEntityModel.leftLeg,
              monotonePoseCurve6.computeFloat(floatValue4, 3),
              floatValue3,
              false);
      EmoteBodyParts.render(bipedEntityModel, floatValue5, floatValue6, floatValue7, floatValue8);
      bipedEntityModel.rightLeg.originY =
          bipedEntityModel.rightLeg.originY
              - monotonePoseCurve4.computeFloat(floatValue4, 6) * floatValue3;
      bipedEntityModel.leftLeg.originY =
          bipedEntityModel.leftLeg.originY
              - monotonePoseCurve4.computeFloat(floatValue4, 7) * floatValue3;
      float floatValue9 =
          monotonePoseCurve3.computeFloat(floatValue4, 0) * (float) (Math.PI / 180.0) * floatValue3;
      float floatValue10 =
          monotonePoseCurve3.computeFloat(floatValue4, 1) * (float) (Math.PI / 180.0) * floatValue3;
      float floatValue11 =
          monotonePoseCurve3.computeFloat(floatValue4, 2) * (float) (Math.PI / 180.0) * floatValue3;
      render(bipedEntityModel.body, floatValue9, floatValue10, floatValue11);
      render(bipedEntityModel.head, floatValue9, floatValue10, floatValue11);
      render(bipedEntityModel.rightArm, floatValue9, floatValue10, floatValue11);
      render(bipedEntityModel.leftArm, floatValue9, floatValue10, floatValue11);
      ModelPart rootPart = bipedEntityModel.getRootPart();
      rootPart.originX =
          rootPart.originX + monotonePoseCurve4.computeFloat(floatValue4, 8) * floatValue3;
      rootPart.originZ =
          rootPart.originZ + monotonePoseCurve4.computeFloat(floatValue4, 9) * floatValue3;
      rootPart.yaw =
          rootPart.yaw
              + (float) Math.IEEEremainder(monotonePoseCurve5.computeFloat(floatValue4, 0), 360.0)
                  * (float) (Math.PI / 180.0)
                  * floatValue3;
      float floatValue12 =
          Math.max(
              computeFloat(bipedEntityModel.rightLeg, floatValue7),
              computeFloat(bipedEntityModel.leftLeg, floatValue8));
      rootPart.originY += (24.0F - floatValue12) * floatValue3;
      float floatValue13 = (floatValue4 - 0.5F) * 98.0F / 60.0F;
      float floatValue14 = (float) (1.0 - Math.cos(floatValue13 * Math.PI * 2.0));
      rootPart.originY -= floatValue14 * (floatValue4 < 4.4F ? 0.07F : 0.13F) * floatValue3;
    }
  }

  public static void render(
      ModelPart modelPart,
      MonotonePoseCurve monotonePoseCurve7,
      int intValue,
      float floatValue,
      float floatValue2) {
    modelPart.pitch =
        modelPart.pitch
            + (monotonePoseCurve7.computeFloat(floatValue, intValue) * (float) (Math.PI / 180.0)
                    - modelPart.pitch)
                * floatValue2;
    modelPart.yaw =
        modelPart.yaw
            + (monotonePoseCurve7.computeFloat(floatValue, intValue + 1) * (float) (Math.PI / 180.0)
                    - modelPart.yaw)
                * floatValue2;
    modelPart.roll =
        modelPart.roll
            + (monotonePoseCurve7.computeFloat(floatValue, intValue + 2) * (float) (Math.PI / 180.0)
                    - modelPart.roll)
                * floatValue2;
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

  public static float computeFloat(
      ModelPart modelPart, float floatValue, float floatValue2, boolean flag) {
    float floatValue3 = floatValue * (float) (Math.PI / 180.0) * floatValue2;
    float floatValue4 = flag ? 4.0F : 6.0F;
    modelPart.pitch =
        modelPart.pitch
            - (float)
                Math.atan2(
                    6.0F * computeFloat(floatValue3),
                    floatValue4 + 6.0F * computeFloat2(floatValue3));
    return floatValue3;
  }

  public static float computeFloat(float floatValue) {
    return (float) Math.sin(floatValue);
  }

  public static float computeFloat2(float floatValue) {
    return (float) Math.cos(floatValue);
  }
}

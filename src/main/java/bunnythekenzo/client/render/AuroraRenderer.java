package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import bunnythekenzo.client.AuroraData;
import bunnythekenzo.client.AuroraSupport;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.AuroraModule;
import bunnythekenzo.client.module.ModuleManager;

public final class AuroraRenderer {
  public static final int count = 80;
  public static final double value = Math.PI * 2;
  public static final int[][] countArray =
      new int[][] {
        {5701578, 4377855, 11434495, 16295404},
        {16747208, 13867263, 9427967, 16769266},
        {16765820, 16749678, 16737439, 12944383},
        {14613247, 7858687, 6527743, 11058431},
        {16039167, 12417023, 7892223, 9033215}
      };
  public static RenderLayer renderLayer;
  public static RenderLayer renderLayer2;
  public static RenderLayer renderLayer3;
  public static int count2;

  public static void update() {
    if (renderLayer == null) {
      renderLayer = getRenderLayer("aurora_silk", null, false);
      renderLayer2 = getRenderLayer("aurora_bloom", "BLOOM", true);
      renderLayer3 = getRenderLayer("aurora_stardust", "STARDUST", true);
    }
  }

  public static RenderLayer getRenderLayer(String text, String text2, boolean flag) {
    Identifier identifier = Identifier.of("bunnythekenzo", "core/aurora");
    Builder builder =
        RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
            .withLocation(Identifier.of("bunnythekenzo", "pipeline/" + text))
            .withVertexShader(identifier)
            .withFragmentShader(identifier)
            .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
            .withBlend(flag ? BlendFunction.ADDITIVE : BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false);
    if (text2 != null) {
      builder.withShaderDefine(text2);
    }

    net.minecraft.client.render.RenderSetup.Builder builder2 =
        RenderSetup.builder(RenderPipelines.register(builder.build()));
    if (!flag) {
      builder2.translucent();
    }

    return RenderLayer.of("bunnythekenzo:" + text, builder2.build());
  }

  public static int getCount2() {
    return count2;
  }

  public static void apply(
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      OrderedRenderCommandQueue orderedRenderCommandQueue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (renderLayer != null && moduleManager != null && moduleManager.auroraModule.isEnabled()) {
      AuroraModule auroraModule = moduleManager.auroraModule;
      if (auroraModule.test(instance)
          && (!instance.options.getPerspective().isFirstPerson()
              || (Boolean) auroraModule.firstPersonSetting.getValue())) {
        float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
        Vec3d vec3d = auroraModule.getVec3d(0.0, tickProgress);
        Vec3d pos = worldRenderState.cameraRenderState.pos;
        if (vec3d != null && !(vec3d.squaredDistanceTo(pos) > 4096.0)) {
          float value2 = auroraModule.computeFloat(tickProgress);
          if (!(value2 < 0.005F)) {
            double value3 = auroraModule.computeDouble(tickProgress);
            float floatValue = auroraModule.brightnessSetting.floatValue() / 100.0F * value2;
            if (instance.options.getPerspective().isFirstPerson()) {
              floatValue *= 0.68F;
            }

            int[][] intValuesArray = countArray;

            int[] intValues =
                intValuesArray[
                    switch ((String) auroraModule.toneSetting.getValue()) {
                      case "Candy" -> 1;
                      case "Sunset" -> 2;
                      case "Glacier" -> 3;
                      case "Amethyst" -> 4;
                      default -> 0;
                    }];
            Quaternionf rotation = instance.gameRenderer.getCamera().getRotation();
            Vec3d vec3d2 = new Vec3d(new Vector3f(1.0F, 0.0F, 0.0F).rotate(rotation));
            Vec3d vec3d3 = new Vec3d(new Vector3f(0.0F, 1.0F, 0.0F).rotate(rotation));
            AuroraSupport auroraSupport = new AuroraSupport(4096);
            AuroraSupport auroraSupport2 = new AuroraSupport(4096);
            AuroraSupport auroraSupport3 = new AuroraSupport(1024);
            float float2 = auroraModule.computeFloat2(tickProgress);
            int value4 = auroraModule.ribbonsSetting.intValue();

            for (int index = 0; index < value4; index++) {
              Vec3d[] vec3dArray = new Vec3d[81];
              float[] floatValues = new float[vec3dArray.length];
              int[] intValues2 = new int[vec3dArray.length];

              for (int index2 = 0; index2 <= 80; index2++) {
                double doubleValue = index2 / 80.0;
                double doubleValue2 =
                    doubleValue * (Double) auroraModule.trailLengthSetting.getValue();
                double doubleValue3 =
                    (value3
                                - doubleValue2
                                    * Math.max(
                                        0.45,
                                        (Double) auroraModule.flowSpeedSetting.getValue() / 100.0))
                            * 1.9
                        + index * (Math.PI * 2) / value4;
                double doubleValue4 =
                    (Double) auroraModule.orbitSizeSetting.getValue() * (1.0 + float2 * 0.14);
                double doubleValue5;
                switch ((String) auroraModule.shapeSetting.getValue()) {
                  case "Halo":
                    doubleValue4 *= 1.0 + 0.07 * Math.sin(doubleValue3 * 2.0 + index);
                    doubleValue5 = 2.05 + 0.15 * Math.sin(doubleValue3 * 2.0 + index);
                    break;
                  case "Helix":
                    doubleValue4 *= 0.86 + 0.12 * Math.sin(doubleValue3);
                    doubleValue5 =
                        0.3 + (1.0 - doubleValue) * 1.9 + 0.13 * Math.sin(doubleValue3 * 2.0);
                    break;
                  default:
                    doubleValue4 *= 1.0 + 0.12 * Math.sin(doubleValue3 * 1.6 + index * 1.7);
                    doubleValue5 =
                        1.12
                            + 0.65 * Math.sin(doubleValue3 + index * 1.4)
                            + 0.16 * Math.sin(doubleValue3 * 2.3 + index);
                }

                Vec3d vec3d4 =
                    auroraModule.test() ? vec3d : auroraModule.getVec3d(doubleValue2, tickProgress);
                vec3dArray[index2] =
                    vec3d4
                        .add(
                            Math.cos(doubleValue3) * doubleValue4,
                            doubleValue5,
                            Math.sin(doubleValue3) * doubleValue4)
                        .subtract(pos);
                double doubleValue6 =
                    Math.pow(Math.sin(Math.PI * (0.04 + doubleValue * 0.96)), 0.65);
                floatValues[index2] =
                    (float)
                        (0.24
                            * (Double) auroraModule.ribbonWidthSetting.getValue()
                            / 100.0
                            * doubleValue6
                            * (1.0 + float2 * 0.25));
                float floatValue2 = (float) Math.pow(1.0 - doubleValue, 0.65);
                intValues2[index2] =
                    computeInt(
                        intValues,
                        (double) index / value4 * 0.7 + doubleValue * 0.55 + value3 * 0.035,
                        floatValue * floatValue2);
              }

              render(
                  auroraSupport,
                  auroraSupport2,
                  vec3dArray,
                  floatValues,
                  intValues2,
                  value3,
                  vec3d2);
              render(auroraSupport3, vec3dArray[0], vec3d2, vec3d3, 0.11F, intValues2[0]);
              if ((Boolean) auroraModule.stardustSetting.getValue()) {
                for (int index3 = 0; index3 < 14; index3++) {
                  double doubleValue7 =
                      computeDouble(index3 * 0.61803398875 + value3 * 0.095 + index * 0.23);
                  int index4 = Math.min(79, (int) (doubleValue7 * 80.0));
                  Vec3d vec3d5 =
                      vec3dArray[index4].lerp(vec3dArray[index4 + 1], doubleValue7 * 80.0 - index4);
                  double doubleValue8 = value3 * 0.8 + index3 * 2.4 + index * 1.3;
                  vec3d5 =
                      vec3d5.add(
                          Math.sin(doubleValue8) * (0.12 + doubleValue7 * 0.25),
                          Math.sin(doubleValue8 * 1.3) * 0.17 + doubleValue7 * 0.28,
                          Math.cos(doubleValue8 * 0.7) * 0.18);
                  float floatValue3 =
                      (float) (0.45 + 0.55 * Math.pow(Math.sin(value3 * 1.6 + index3 * 1.7), 2.0));
                  render(
                      auroraSupport3,
                      vec3d5,
                      vec3d2,
                      vec3d3,
                      0.045F + 0.045F * floatValue3,
                      computeInt(
                          intValues,
                          (double) index / value4 + doubleValue7 * 0.4,
                          floatValue * (float) Math.sin(Math.PI * doubleValue7) * floatValue3));
                }
              }
            }

            if ((Boolean) auroraModule.jumpBloomSetting.getValue() && !auroraModule.test()) {
              for (AuroraData auroraData :
                  (java.lang.Iterable<AuroraData>)
                      (java.lang.Iterable<?>) (auroraModule.getList2())) {
                double doubleValue9 =
                    Math.clamp(
                        (auroraModule.computeDouble2(tickProgress) - auroraData.value()) / 1.35,
                        0.0,
                        1.0);
                render(
                    auroraSupport,
                    auroraSupport2,
                    auroraSupport3,
                    auroraData.vec3d().subtract(pos),
                    doubleValue9,
                    intValues,
                    floatValue,
                    vec3d2,
                    vec3d3);
              }
            }

            orderedRenderCommandQueue.submitCustom(
                matrixStack, renderLayer2, auroraSupport2::apply);
            orderedRenderCommandQueue.submitCustom(matrixStack, renderLayer, auroraSupport::apply);
            orderedRenderCommandQueue.submitCustom(
                matrixStack, renderLayer3, auroraSupport3::apply);
            count2++;
          }
        }
      }
    }
  }

  public static void render(
      AuroraSupport auroraSupport,
      AuroraSupport auroraSupport2,
      Vec3d[] vec3dArray,
      float[] floatValues,
      int[] intValues,
      double doubleValue,
      Vec3d vec3d) {
    Vec3d vec3d2 = null;
    Vec3d vec3d3 = null;
    Vec3d vec3d4 = null;
    Vec3d vec3d5 = null;
    Vec3d vec3d6 = null;

    for (int index = 0; index < vec3dArray.length; index++) {
      Vec3d vec3d7 =
          vec3dArray[Math.min(index + 1, vec3dArray.length - 1)].subtract(
              vec3dArray[Math.max(0, index - 1)]);
      Vec3d vec3d8 = vec3d7.normalize().crossProduct(vec3dArray[index].normalize().multiply(-1.0));
      double length = vec3d8.length();
      vec3d8 = vec3d8.lengthSquared() < 1.0E-9 ? vec3d : vec3d8.normalize();
      if (vec3d2 != null && vec3d8.dotProduct(vec3d2) < 0.0) {
        vec3d8 = vec3d8.multiply(-1.0);
      }

      if (vec3d2 != null) {
        vec3d8 = vec3d2.lerp(vec3d8, 0.45).normalize();
      }

      Vec3d vec3d9 = vec3d8.multiply(floatValues[index] * Math.min(1.0, length * 2.0));
      Vec3d vec3d10 = vec3dArray[index].subtract(vec3d9);
      Vec3d vec3d11 = vec3dArray[index].add(vec3d9);
      Vec3d vec3d12 = vec3d9.multiply(2.9);
      Vec3d vec3d13 = vec3dArray[index].subtract(vec3d12);
      Vec3d vec3d14 = vec3dArray[index].add(vec3d12);
      if (index > 0) {
        float floatValue = (index - 1.0F) / 80.0F + (float) (doubleValue % 100.0) * 0.1F;
        float floatValue2 = index / 80.0F + (float) (doubleValue % 100.0) * 0.1F;
        auroraSupport.render(
            vec3d3,
            vec3d4,
            vec3d11,
            vec3d10,
            floatValue,
            floatValue2,
            intValues[index - 1],
            intValues[index]);
        auroraSupport2.render(
            vec3d5,
            vec3d6,
            vec3d14,
            vec3d13,
            floatValue,
            floatValue2,
            intValues[index - 1],
            intValues[index]);
      }

      vec3d2 = vec3d8;
      vec3d3 = vec3d10;
      vec3d4 = vec3d11;
      vec3d5 = vec3d13;
      vec3d6 = vec3d14;
    }
  }

  public static void render(
      AuroraSupport auroraSupport,
      AuroraSupport auroraSupport2,
      AuroraSupport auroraSupport3,
      Vec3d vec3d,
      double doubleValue,
      int[] intValues,
      float floatValue,
      Vec3d vec3d2,
      Vec3d vec3d3) {
    float floatValue2 =
        (float) (Math.sin(Math.PI * Math.pow(doubleValue, 0.55)) * (1.0 - doubleValue))
            * floatValue;
    double doubleValue2 = 1.0 - Math.pow(1.0 - doubleValue, 3.0);

    for (int index = 0; index < 2; index++) {
      Vec3d[] vec3dArray = new Vec3d[81];
      float[] floatValues = new float[vec3dArray.length];
      int[] intValues2 = new int[vec3dArray.length];

      for (int index2 = 0; index2 <= 80; index2++) {
        double doubleValue3 = (Math.PI * 2) * index2 / 80.0;
        double doubleValue4 =
            (0.35 + doubleValue2 * (index == 0 ? 2.1 : 1.55))
                * (1.0 + 0.065 * Math.cos(doubleValue3 * 8.0 + doubleValue * 2.0));
        vec3dArray[index2] =
            vec3d.add(
                Math.cos(doubleValue3) * doubleValue4,
                index * 0.08 + Math.sin(doubleValue3 * 8.0) * 0.012,
                Math.sin(doubleValue3) * doubleValue4);
        floatValues[index2] = 0.035F + (float) doubleValue * 0.018F;
        intValues2[index2] =
            computeInt(
                intValues, index2 / 80.0 + index * 0.2, floatValue2 * (index == 0 ? 1.0F : 0.6F));
      }

      render(
          auroraSupport, auroraSupport2, vec3dArray, floatValues, intValues2, doubleValue, vec3d2);
    }

    for (int index3 = 0; index3 < 16; index3++) {
      double doubleValue5 = index3 * (Math.PI * 2) / 16.0 + 0.15;
      double doubleValue6 = 0.4 + doubleValue2 * 1.8;
      Vec3d vec3d4 =
          vec3d.add(
              Math.cos(doubleValue5) * doubleValue6,
              Math.sin(Math.PI * doubleValue) * (0.4 + index3 % 3 * 0.12),
              Math.sin(doubleValue5) * doubleValue6);
      render(
          auroraSupport3,
          vec3d4,
          vec3d2,
          vec3d3,
          0.065F,
          computeInt(intValues, index3 / 16.0, floatValue2));
    }
  }

  public static void render(
      AuroraSupport auroraSupport,
      Vec3d vec3d,
      Vec3d vec3d2,
      Vec3d vec3d3,
      float floatValue,
      int intValue) {
    Vec3d vec3d4 = vec3d2.multiply(floatValue);
    Vec3d vec3d5 = vec3d3.multiply(floatValue);
    auroraSupport.render(
        vec3d.subtract(vec3d4).subtract(vec3d5),
        vec3d.subtract(vec3d4).add(vec3d5),
        vec3d.add(vec3d4).add(vec3d5),
        vec3d.add(vec3d4).subtract(vec3d5),
        0.0F,
        1.0F,
        intValue,
        intValue);
  }

  public static int computeInt(int[] intValues, double doubleValue, float floatValue) {
    double doubleValue2 = computeDouble(doubleValue) * intValues.length;
    int intValue = intValues[(int) doubleValue2];
    int intValue2 = intValues[((int) doubleValue2 + 1) % intValues.length];
    double doubleValue3 = doubleValue2 - (int) doubleValue2;
    doubleValue3 = doubleValue3 * doubleValue3 * (3.0 - 2.0 * doubleValue3);
    int intValue3 =
        (int)
            ((intValue >> 16 & 0xFF) * (1.0 - doubleValue3)
                + (intValue2 >> 16 & 0xFF) * doubleValue3);
    int intValue4 =
        (int)
            ((intValue >> 8 & 0xFF) * (1.0 - doubleValue3)
                + (intValue2 >> 8 & 0xFF) * doubleValue3);
    int intValue5 =
        (int) ((intValue & 0xFF) * (1.0 - doubleValue3) + (intValue2 & 0xFF) * doubleValue3);
    return Math.clamp(Math.round(floatValue * 255.0F), 0, 255) << 24
        | intValue3 << 16
        | intValue4 << 8
        | intValue5;
  }

  public static double computeDouble(double doubleValue) {
    return doubleValue - Math.floor(doubleValue);
  }
}

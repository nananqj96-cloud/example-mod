package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CrystalPulseData;
import bunnythekenzo.client.CrystalPulseData2;
import bunnythekenzo.client.CrystalPulseSupport;
import bunnythekenzo.client.module.CrystalPulseModule;
import bunnythekenzo.client.module.ModuleManager;

public final class CrystalPulseRenderer {
  public static final double value = Math.PI * 2;
  public static final Vec3d vec3d = new Vec3d(1.0, 0.0, 0.0);
  public static final Vec3d vec3d2 = new Vec3d(0.0, 0.0, 1.0);
  public static final int[][] countArray =
      new int[][] {
        {7798752, 10923519},
        {12355839, 15839487},
        {7588607, 14352383},
        {16747711, 16765925},
        {16756325, 16740747}
      };
  public static RenderLayer renderLayer;
  public static RenderLayer renderLayer2;
  public static int count;

  public static void update() {
    if (renderLayer == null) {
      renderLayer = getRenderLayer("crystal_pulse", false);
      renderLayer2 = getRenderLayer("crystal_glints", true);
    }
  }

  public static RenderLayer getRenderLayer(String text, boolean flag) {
    Identifier identifier = Identifier.of("bunnythekenzo", "core/crystal_pulse");
    Builder builder =
        RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
            .withLocation(Identifier.of("bunnythekenzo", "pipeline/" + text))
            .withVertexShader(identifier)
            .withFragmentShader(identifier)
            .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
            .withBlend(BlendFunction.ADDITIVE)
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false);
    if (flag) {
      builder.withShaderDefine("SHARD");
    }

    return RenderLayer.of(
        "bunnythekenzo:" + text,
        RenderSetup.builder(RenderPipelines.register(builder.build())).build());
  }

  public static int getCount() {
    return count;
  }

  public static void apply(
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      OrderedRenderCommandQueue orderedRenderCommandQueue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (renderLayer != null
        && moduleManager != null
        && moduleManager.crystalPulseModule.isEnabled()
        && instance.player != null
        && instance.world != null) {
      CrystalPulseModule crystalPulseModule = moduleManager.crystalPulseModule;
      if (crystalPulseModule.test(instance.world)
          && !moduleManager.freecamModule.test()
          && !instance.player.isSpectator()) {
        float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
        List list = crystalPulseModule.halos(tickProgress);
        List list2 =
            (java.lang.Boolean) (crystalPulseModule.impactWavesSetting.getValue())
                ? crystalPulseModule.getList2()
                : List.of();
        if (!list.isEmpty() || !list2.isEmpty()) {
          Vec3d pos = worldRenderState.cameraRenderState.pos;
          Quaternionf rotation = instance.gameRenderer.getCamera().getRotation();
          Vec3d vec3d3 = new Vec3d(new Vector3f(1.0F, 0.0F, 0.0F).rotate(rotation));
          Vec3d vec3d4 = new Vec3d(new Vector3f(0.0F, 1.0F, 0.0F).rotate(rotation));
          int[][] intValuesArray = countArray;

          int[] intValues =
              intValuesArray[
                  switch ((String) crystalPulseModule.toneSetting.getValue()) {
                    case "Amethyst" -> 1;
                    case "Glacier" -> 2;
                    case "Rose" -> 3;
                    case "Ember" -> 4;
                    default -> 0;
                  }];
          double double2 = crystalPulseModule.computeDouble2(tickProgress);
          double value2 = crystalPulseModule.computeDouble(tickProgress);
          double doubleValue = (Double) crystalPulseModule.sizeSetting.getValue() / 100.0;
          float floatValue = crystalPulseModule.intensitySetting.floatValue() / 100.0F;
          int intValue =
              crystalPulseModule.crosshairFocusSetting.getValue()
                      && instance.crosshairTarget instanceof EntityHitResult entityHitResult
                  ? entityHitResult.getEntity().getId()
                  : -1;
          CrystalPulseSupport crystalPulseSupport = new CrystalPulseSupport(4096);
          CrystalPulseSupport crystalPulseSupport2 = new CrystalPulseSupport(512);

          for (CrystalPulseData2 crystalPulseData2 :
              (java.lang.Iterable<CrystalPulseData2>) (java.lang.Iterable<?>) (list)) {
            float floatValue2 =
                floatValue
                    * (float) crystalPulseData2.value()
                    * computeFloat(
                        crystalPulseData2.vec3d(),
                        pos,
                        (Double) crystalPulseModule.renderDistanceSetting.getValue());
            if (!(floatValue2 < 0.005F)) {
              boolean flag = crystalPulseData2.count() == intValue;
              floatValue2 *= flag ? 1.0F : 0.9F;
              Vec3d vec3d5 = crystalPulseData2.vec3d().add(0.0, 0.045, 0.0).subtract(pos);
              double doubleValue2 =
                  double2 * 0.38 + Math.floorMod(crystalPulseData2.count(), 37) * 0.17;
              double doubleValue3 = 0.78 * doubleValue;
              render(
                  crystalPulseSupport,
                  vec3d5,
                  vec3d,
                  vec3d2,
                  doubleValue3,
                  0.085 * doubleValue,
                  0.0,
                  Math.PI * 2,
                  intValues,
                  doubleValue2,
                  floatValue2,
                  false);
              if (!crystalPulseModule.styleSetting.isSelected("Minimal")) {
                for (int index = 0; index < 4; index++) {
                  double doubleValue4 = doubleValue2 * 0.5 + index * (Math.PI * 2) / 4.0;
                  render(
                      crystalPulseSupport,
                      vec3d5.add(0.0, 0.012, 0.0),
                      vec3d,
                      vec3d2,
                      doubleValue3 * 1.24,
                      0.052 * doubleValue,
                      doubleValue4,
                      doubleValue4 + 0.72,
                      intValues,
                      doubleValue2 + 0.7,
                      floatValue2 * 0.62F,
                      true);
                }

                if (flag) {
                  for (int index2 = 0; index2 < 4; index2++) {
                    double doubleValue5 = (Math.PI / 4) + index2 * (Math.PI * 2) / 4.0;
                    render(
                        crystalPulseSupport2,
                        vec3d5.add(
                            Math.cos(doubleValue5) * doubleValue3 * 1.25,
                            0.065,
                            Math.sin(doubleValue5) * doubleValue3 * 1.25),
                        vec3d3,
                        vec3d4,
                        0.085 * doubleValue,
                        computeInt(intValues, doubleValue5, floatValue2 * 0.85F));
                  }
                }

                if (crystalPulseModule.styleSetting.isSelected("Orbit")) {
                  Vec3d vec3d6 = vec3d5.add(0.0, 1.05, 0.0);
                  Vec3d vec3d7 = new Vec3d(0.0, 0.52, 0.854166);
                  double doubleValue6 = -double2 * 0.65 + doubleValue2;
                  render(
                      crystalPulseSupport,
                      vec3d6,
                      vec3d,
                      vec3d7,
                      0.99 * doubleValue,
                      0.055 * doubleValue,
                      doubleValue6,
                      doubleValue6 + 5.340707511102648,
                      intValues,
                      doubleValue2,
                      floatValue2 * 0.55F,
                      true);
                  Vec3d vec3d8 =
                      vec3d6
                          .add(
                              vec3d.multiply(
                                  Math.cos(doubleValue6 + 5.340707511102648) * 0.99 * doubleValue))
                          .add(
                              vec3d7.multiply(
                                  Math.sin(doubleValue6 + 5.340707511102648) * 0.99 * doubleValue));
                  render(
                      crystalPulseSupport2,
                      vec3d8,
                      vec3d3,
                      vec3d4,
                      0.1 * doubleValue,
                      computeInt(intValues, doubleValue2, floatValue2));
                }
              }
            }
          }

          for (CrystalPulseData crystalPulseData :
              (java.lang.Iterable<CrystalPulseData>) (java.lang.Iterable<?>) (list2)) {
            double doubleValue7 =
                Math.clamp(
                    (value2 - crystalPulseData.value()) / crystalPulseData.value2(), 0.0, 1.0);
            if (!(doubleValue7 >= 1.0)) {
              float floatValue3 =
                  floatValue
                      * computeFloat(
                          crystalPulseData.vec3d(),
                          pos,
                          (Double) crystalPulseModule.renderDistanceSetting.getValue());
              Vec3d vec3d9 = crystalPulseData.vec3d().subtract(pos);
              double doubleValue8 =
                  AnimationPreferences.isEnabled() ? 1.0 - Math.pow(1.0 - doubleValue7, 3.0) : 0.35;
              float floatValue4 = (float) Math.pow(1.0 - doubleValue7, 1.7) * floatValue3;
              double doubleValue9 =
                  0.45 + doubleValue8 * (Double) crystalPulseModule.waveSizeSetting.getValue();
              render(
                  crystalPulseSupport,
                  vec3d9,
                  vec3d,
                  vec3d2,
                  doubleValue9,
                  0.13,
                  0.0,
                  Math.PI * 2,
                  intValues,
                  crystalPulseData.count() * 0.1,
                  floatValue4,
                  false);
              render(
                  crystalPulseSupport,
                  vec3d9.add(0.0, 0.028, 0.0),
                  vec3d,
                  vec3d2,
                  doubleValue9 * 0.77,
                  0.055,
                  0.0,
                  Math.PI * 2,
                  intValues,
                  crystalPulseData.count() * 0.1 + 1.8,
                  floatValue4 * 0.6F,
                  false);
              if ((Boolean) crystalPulseModule.crystalShardsSetting.getValue()
                  && AnimationPreferences.isEnabled()) {
                for (int index3 = 0; index3 < 8; index3++) {
                  double doubleValue10 =
                      index3 * (Math.PI * 2) / 8.0 + crystalPulseData.count() * 0.71;
                  double doubleValue11 =
                      0.3
                          + doubleValue8
                              * (Double) crystalPulseModule.waveSizeSetting.getValue()
                              * (0.52 + index3 % 3 * 0.12);
                  double doubleValue12 =
                      0.16 + Math.sin(doubleValue7 * Math.PI) * (0.38 + index3 % 3 * 0.16);
                  Vec3d vec3d10 =
                      vec3d9.add(
                          Math.cos(doubleValue10) * doubleValue11,
                          doubleValue12,
                          Math.sin(doubleValue10) * doubleValue11);
                  render(
                      crystalPulseSupport2,
                      vec3d10,
                      vec3d3,
                      vec3d4,
                      (0.075 + index3 % 2 * 0.025) * (1.0 - doubleValue7 * 0.6),
                      computeInt(intValues, doubleValue10, floatValue4 * 0.8F));
                }
              }
            }
          }

          if (crystalPulseSupport.count > 0) {
            orderedRenderCommandQueue.submitCustom(
                matrixStack, renderLayer, crystalPulseSupport::apply);
          }

          if (crystalPulseSupport2.count > 0) {
            orderedRenderCommandQueue.submitCustom(
                matrixStack, renderLayer2, crystalPulseSupport2::apply);
          }

          if (crystalPulseSupport.count > 0 || crystalPulseSupport2.count > 0) {
            count++;
          }
        }
      }
    }
  }

  public static float computeFloat(Vec3d vec3d3, Vec3d vec3d4, double doubleValue) {
    double doubleValue2 = vec3d3.distanceTo(vec3d4);
    return (float)
        (Math.clamp((doubleValue - doubleValue2) / 3.0, 0.0, 1.0)
            * Math.clamp((doubleValue2 - 0.35) / 1.4, 0.0, 1.0));
  }

  public static void render(
      CrystalPulseSupport crystalPulseSupport,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Vec3d vec3d5,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      int[] intValues,
      double doubleValue5,
      float floatValue,
      boolean flag) {
    int intValue =
        Math.max(8, (int) Math.ceil((doubleValue4 - doubleValue3) / (Math.PI * 2) * 80.0));
    Vec3d vec3d6 = null;
    Vec3d vec3d7 = null;
    int intValue2 = 0;

    for (int index = 0; index <= intValue; index++) {
      double doubleValue6 = (double) index / intValue;
      double doubleValue7 = doubleValue3 + (doubleValue4 - doubleValue3) * doubleValue6;
      Vec3d vec3d8 =
          vec3d4.multiply(Math.cos(doubleValue7)).add(vec3d5.multiply(Math.sin(doubleValue7)));
      Vec3d vec3d9 = vec3d3.add(vec3d8.multiply(doubleValue - doubleValue2));
      Vec3d vec3d10 = vec3d3.add(vec3d8.multiply(doubleValue + doubleValue2));
      float floatValue2 = flag ? (float) Math.pow(Math.sin(doubleValue6 * Math.PI), 0.45) : 1.0F;
      int intValue3 = computeInt(intValues, doubleValue7 + doubleValue5, floatValue * floatValue2);
      if (index > 0) {
        crystalPulseSupport.apply(vec3d6, vec3d7, vec3d10, vec3d9, intValue2, intValue3);
      }

      vec3d6 = vec3d9;
      vec3d7 = vec3d10;
      intValue2 = intValue3;
    }
  }

  public static int computeInt(int[] intValues, double doubleValue, float floatValue) {
    double doubleValue2 = 0.5 + 0.5 * Math.sin(doubleValue);
    int intValue = intValues[0];
    int intValue2 = intValues[1];
    int intValue3 =
        (int)
            ((intValue >> 16 & 0xFF) * (1.0 - doubleValue2)
                + (intValue2 >> 16 & 0xFF) * doubleValue2);
    int intValue4 =
        (int)
            ((intValue >> 8 & 0xFF) * (1.0 - doubleValue2)
                + (intValue2 >> 8 & 0xFF) * doubleValue2);
    int intValue5 =
        (int) ((intValue & 0xFF) * (1.0 - doubleValue2) + (intValue2 & 0xFF) * doubleValue2);
    return Math.clamp(Math.round(floatValue * 255.0F), 0, 255) << 24
        | intValue3 << 16
        | intValue4 << 8
        | intValue5;
  }

  public static void render(
      CrystalPulseSupport crystalPulseSupport,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Vec3d vec3d5,
      double doubleValue,
      int intValue) {
    Vec3d vec3d6 = vec3d4.multiply(doubleValue);
    Vec3d vec3d7 = vec3d5.multiply(doubleValue * 1.35);
    crystalPulseSupport.apply(
        vec3d3.subtract(vec3d6).subtract(vec3d7),
        vec3d3.subtract(vec3d6).add(vec3d7),
        vec3d3.add(vec3d6).add(vec3d7),
        vec3d3.add(vec3d6).subtract(vec3d7),
        intValue,
        intValue);
  }
}

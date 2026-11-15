package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.LumaData;
import bunnythekenzo.client.LumaSupport;
import bunnythekenzo.client.module.LumaModule;
import bunnythekenzo.client.module.ModuleManager;

public final class LumaRenderer {
  public static final float[][] amountArray =
      new float[][] {
        {0.0F, 0.12F},
        {0.15F, 0.48F},
        {0.38F, 0.78F},
        {0.68F, 1.03F},
        {0.88F, 1.12F},
        {1.02F, 0.96F},
        {1.09F, 0.71F},
        {1.05F, 0.46F},
        {0.93F, 0.23F},
        {0.74F, 0.06F},
        {0.43F, -0.05F},
        {0.0F, -0.13F}
      };
  public static final float[][] amountArray2 =
      new float[][] {
        {0.0F, -0.06F},
        {0.34F, -0.13F},
        {0.65F, -0.23F},
        {0.84F, -0.43F},
        {0.83F, -0.61F},
        {0.69F, -0.8F},
        {0.48F, -0.96F},
        {0.36F, -0.79F},
        {0.22F, -0.59F},
        {0.1F, -0.34F},
        {0.0F, -0.26F}
      };
  public static final int[][] countArray =
      new int[][] {
        {-8916782, -7620097, -4023297},
        {-23611, -10012, -2645516},
        {-17554, -29282, -6996},
        {-7873025, -4736257, -3147017}
      };
  public static RenderLayer renderLayer;
  public static RenderLayer renderLayer2;
  public static int count;

  public static void update() {
    if (renderLayer == null) {
      renderLayer = getRenderLayer("luma_wings", BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA);
      renderLayer2 = getRenderLayer("luma_light", BlendFunction.ADDITIVE);
    }
  }

  public static RenderLayer getRenderLayer(String text, BlendFunction blendFunction) {
    Identifier identifier = Identifier.of("bunnythekenzo", "core/luma");
    RenderPipeline renderPipeline =
        RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                .withLocation(Identifier.of("bunnythekenzo", "pipeline/" + text))
                .withVertexShader(identifier)
                .withFragmentShader(identifier)
                .withVertexFormat(
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS)
                .withBlend(blendFunction)
                .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                .withDepthWrite(false)
                .withCull(false)
                .build());
    return RenderLayer.of("bunnythekenzo:" + text, RenderSetup.builder(renderPipeline).build());
  }

  public static int getCount() {
    return count;
  }

  public static void submit(
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      OrderedRenderCommandQueue orderedRenderCommandQueue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (renderLayer != null
        && moduleManager != null
        && moduleManager.lumaModule.isEnabled()
        && instance.world != null
        && instance.player != null
        && instance.player.isAlive()
        && !instance.player.isSpectator()
        && !instance.player.isInvisible()
        && !instance.player.isSleeping()
        && (moduleManager.freecamModule == null || !moduleManager.freecamModule.test())) {
      LumaModule lumaModule = moduleManager.lumaModule;
      if (lumaModule.getCount4() != 0) {
        Vec3d pos = worldRenderState.cameraRenderState.pos;
        if (!(instance.player.getEntityPos().squaredDistanceTo(pos) > 4096.0)) {
          float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
          float float3 = lumaModule.computeFloat3(tickProgress);
          if (!(float3 < 0.01F)) {
            float floatValue = (float) (lumaModule.computeDouble(tickProgress) % 6283.185307179586);
            boolean flag = lumaModule.test();
            float floatValue2 = lumaModule.wingSizeSetting.floatValue() * 0.0024F;
            float value = lumaModule.computeFloat(tickProgress);
            float floatValue3 = lumaModule.glowSetting.floatValue() / 100.0F;
            boolean flag2 = (Boolean) lumaModule.stardustSetting.getValue();

            byte index =
                switch ((String) lumaModule.paletteSetting.getValue()) {
                  case "Sakura" -> 1;
                  case "Sunset" -> 2;
                  case "Glacier" -> 3;
                  default -> 0;
                };
            Vector3f vector3f =
                new Vector3f(instance.gameRenderer.getCamera().getHorizontalPlane());
            Vector3f vector3f2 = vector3f.cross(new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f());
            if (vector3f2.lengthSquared() < 1.0E-5F) {
              vector3f2.set(1.0F, 0.0F, 0.0F);
            }

            vector3f2.normalize();
            Vector3f vector3f3 = new Vector3f(vector3f2).cross(vector3f).normalize();
            Vec3d vec3d = new Vec3d(vector3f2);
            Vec3d vec3d2 = new Vec3d(vector3f3);
            LumaData[] lumaDataArray = new LumaData[lumaModule.getCount4()];

            for (int index2 = 0; index2 < lumaDataArray.length; index2++) {
              LumaSupport lumaSupport = lumaModule.getLumaSupport(index2);
              Vec3d vec3d3 = lumaSupport.getVec3d(tickProgress);
              int intValue =
                  lumaModule.silkTrailsSetting.getValue() && !flag
                      ? Math.min(
                          lumaSupport.getCount2() - 1,
                          (int) Math.ceil((Double) lumaModule.trailLengthSetting.getValue() * 20.0))
                      : 0;
              Vec3d[] vec3dArray = new Vec3d[Math.max(0, intValue)];

              for (int index3 = 0; index3 < vec3dArray.length; index3++) {
                vec3dArray[index3] =
                    lumaSupport
                        .getVec3d(index3 + 1)
                        .lerp(lumaSupport.getVec3d(index3), tickProgress)
                        .subtract(pos);
              }

              int count2 = countArray[index][index2 % 3];
              float amount =
                  float3
                      * (float) MathHelper.clamp((vec3d3.distanceTo(pos) - 0.45) / 0.7, 0.0, 1.0);
              lumaDataArray[index2] =
                  new LumaData(vec3d3.subtract(pos), vec3dArray, count2, amount);
              float floatValue4 =
                  flag
                      ? 0.24F
                      : 0.25F
                          + (0.5F
                                  + 0.5F
                                      * MathHelper.sin(
                                          floatValue * (8.0F + index2 % 3 * 0.7F) + index2 * 1.9F))
                              * 0.9F;
              float floatValue5 = floatValue2 * (0.85F + index2 % 4 * 0.1F);
              matrixStack.push();
              matrixStack.translate(vec3d3.x - pos.x, vec3d3.y - pos.y, vec3d3.z - pos.z);
              matrixStack.multiply(
                  RotationAxis.POSITIVE_Y.rotation(lumaSupport.computeFloat(tickProgress)));
              matrixStack.multiply(
                  RotationAxis.POSITIVE_X.rotationDegrees(
                      -18.0F + (flag ? 0.0F : MathHelper.sin(floatValue * 1.3F + index2) * 12.0F)));
              matrixStack.multiply(
                  RotationAxis.POSITIVE_Z.rotationDegrees(
                      flag ? 0.0F : MathHelper.sin(floatValue * 0.9F + index2 * 2) * 18.0F));
              matrixStack.scale(floatValue5, floatValue5, floatValue5);
              orderedRenderCommandQueue.submitCustom(
                  matrixStack,
                  renderLayer,
                  (entry, vertexConsumer) -> {
                    render(entry, vertexConsumer, floatValue4, count2, amount);
                  });
              matrixStack.pop();
            }

            orderedRenderCommandQueue.submitCustom(
                matrixStack,
                renderLayer2,
                (entry2, vertexConsumer2) -> {
                  for (int index4 = 0; index4 < lumaDataArray.length; index4++) {
                    LumaData lumaData = lumaDataArray[index4];
                    if (!(lumaData.amount() < 0.005)) {
                      if (floatValue3 > 0.0F) {
                        render(
                            entry2,
                            vertexConsumer2,
                            lumaData.vec3d(),
                            vec3d,
                            vec3d2,
                            floatValue2 * (1.9F + value * 0.5F),
                            lumaData.count(),
                            lumaData.amount() * floatValue3 * 0.24F,
                            1);
                        render(
                            entry2,
                            vertexConsumer2,
                            lumaData.vec3d(),
                            vec3d,
                            vec3d2,
                            floatValue2 * 0.21F,
                            computeInt(lumaData.count(), -1, 0.65F),
                            lumaData.amount() * floatValue3 * 0.8F,
                            4);
                      }

                      render(entry2, vertexConsumer2, lumaData, vec3d, floatValue2);
                      if (flag2 && lumaData.vec3dArray().length > 6) {
                        for (int index5 = 5 + index4 % 3;
                            index5 < lumaData.vec3dArray().length;
                            index5 += 7) {
                          float floatValue6 = 1.0F - (float) index5 / lumaData.vec3dArray().length;
                          float floatValue7 =
                              0.45F
                                  + 0.55F
                                      * MathHelper.sin(floatValue * 2.5F + index4 * 2 + index5)
                                      * MathHelper.sin(floatValue * 2.5F + index4 * 2 + index5);
                          Vec3d vec3d4 =
                              lumaData.vec3dArray()[index5].add(
                                  Math.sin(index4 * 5 + index5) * 0.06,
                                  index5 * 0.004,
                                  Math.cos(index4 + index5 * 3) * 0.06);
                          render(
                              entry2,
                              vertexConsumer2,
                              vec3d4,
                              vec3d,
                              vec3d2,
                              0.035F * floatValue7,
                              lumaData.count(),
                              lumaData.amount() * floatValue6 * floatValue7 * 0.7F,
                              4);
                        }
                      }
                    }
                  }
                });
            count++;
          }
        }
      }
    }
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      float floatValue,
      int intValue,
      float floatValue2) {
    for (int intValue2 : new int[] {-1, 1}) {
      render(
          entry,
          vertexConsumer,
          amountArray,
          0.4F,
          0.38F,
          intValue2,
          floatValue,
          intValue,
          floatValue2);
      render(
          entry,
          vertexConsumer,
          amountArray2,
          0.32F,
          -0.42F,
          intValue2,
          floatValue * 0.82F,
          computeInt(intValue, -1713153, 0.22F),
          floatValue2);
    }

    int intValue3 = computeInt(intValue, -2852, 0.8F);

    for (int index = 0; index < 8; index++) {
      double doubleValue = index * Math.PI / 4.0;
      double doubleValue2 = (index + 1) * Math.PI / 4.0;
      float floatValue3 = (float) Math.cos(doubleValue);
      float floatValue4 = (float) Math.sin(doubleValue);
      float floatValue5 = (float) Math.cos(doubleValue2);
      float floatValue6 = (float) Math.sin(doubleValue2);
      render(
          entry,
          vertexConsumer,
          floatValue3 * 0.028F,
          floatValue4 * 0.028F,
          -0.37F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          floatValue3,
          floatValue4,
          0.0F);
      render(
          entry,
          vertexConsumer,
          floatValue5 * 0.028F,
          floatValue6 * 0.028F,
          -0.37F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          floatValue5,
          floatValue6,
          0.0F);
      render(
          entry,
          vertexConsumer,
          floatValue5 * 0.045F,
          floatValue6 * 0.045F,
          0.3F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          floatValue5,
          floatValue6,
          0.0F);
      render(
          entry,
          vertexConsumer,
          floatValue3 * 0.045F,
          floatValue4 * 0.045F,
          0.3F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          floatValue3,
          floatValue4,
          0.0F);
    }

    for (int intValue4 : new int[] {-1, 1}) {
      render(
          entry,
          vertexConsumer,
          intValue4 * 0.025F,
          0.01F,
          0.24F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          0.0F,
          1.0F,
          0.0F);
      render(
          entry,
          vertexConsumer,
          intValue4 * 0.014F,
          0.01F,
          0.24F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          0.0F,
          1.0F,
          0.0F);
      render(
          entry,
          vertexConsumer,
          intValue4 * 0.125F,
          0.035F,
          0.53F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          0.0F,
          1.0F,
          0.0F);
      render(
          entry,
          vertexConsumer,
          intValue4 * 0.139F,
          0.035F,
          0.53F,
          0.0F,
          0.0F,
          intValue3,
          floatValue2,
          2,
          0.0F,
          0.0F,
          1.0F,
          0.0F);
    }
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      float[][] floatValuesArray,
      float floatValue,
      float floatValue2,
      int intValue,
      float floatValue3,
      int intValue2,
      float floatValue4) {
    float floatValue5 = MathHelper.cos(floatValue3);
    float floatValue6 = MathHelper.sin(floatValue3);

    for (int index = 0; index < floatValuesArray.length; index++) {
      float[] floatValues = floatValuesArray[index];
      float[] floatValues2 = floatValuesArray[(index + 1) % floatValuesArray.length];
      render(
          entry,
          vertexConsumer,
          floatValue,
          floatValue2,
          intValue,
          floatValue5,
          floatValue6,
          computeInt(intValue2, -1, 0.65F),
          floatValue4,
          0.0F);
      render(
          entry,
          vertexConsumer,
          floatValues[0],
          floatValues[1],
          intValue,
          floatValue5,
          floatValue6,
          intValue2,
          floatValue4,
          1.0F);
      render(
          entry,
          vertexConsumer,
          floatValues2[0],
          floatValues2[1],
          intValue,
          floatValue5,
          floatValue6,
          intValue2,
          floatValue4,
          1.0F);
      render(
          entry,
          vertexConsumer,
          floatValue,
          floatValue2,
          intValue,
          floatValue5,
          floatValue6,
          computeInt(intValue2, -1, 0.65F),
          floatValue4,
          0.0F);
    }
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      int intValue,
      float floatValue3,
      float floatValue4,
      int intValue2,
      float floatValue5,
      float floatValue6) {
    render(
        entry,
        vertexConsumer,
        intValue * floatValue * floatValue3,
        floatValue * floatValue4,
        floatValue2,
        floatValue,
        floatValue2,
        intValue2,
        floatValue5,
        0,
        floatValue6,
        -intValue * floatValue4,
        floatValue3,
        0.0F);
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      LumaData lumaData,
      Vec3d vec3d,
      float floatValue) {
    Vec3d[] vec3dArray = lumaData.vec3dArray();

    for (int index = 0; index + 1 < vec3dArray.length; index++) {
      Vec3d vec3d2 = vec3dArray[index];
      Vec3d vec3d3 = vec3dArray[index + 1];
      Vec3d vec3d4 = vec3d3.subtract(vec3d2);
      if (!(vec3d4.lengthSquared() < 1.0E-7) && !(vec3d4.lengthSquared() > 16.0)) {
        Vec3d vec3d5 = vec3d4.crossProduct(vec3d2.add(vec3d3).multiply(0.5));
        vec3d5 = vec3d5.lengthSquared() < 1.0E-10 ? vec3d : vec3d5.normalize();
        float floatValue2 = 1.0F - (float) index / vec3dArray.length;
        float floatValue3 = 1.0F - (float) (index + 1) / vec3dArray.length;
        Vec3d vec3d6 = vec3d5.multiply(floatValue * 0.055 * floatValue2);
        Vec3d vec3d7 = vec3d5.multiply(floatValue * 0.055 * floatValue3);
        float floatValue4 = lumaData.amount() * 0.65F;
        render(
            entry,
            vertexConsumer,
            vec3d2.add(vec3d6),
            0.0F,
            0.0F,
            lumaData.count(),
            floatValue4 * floatValue2 * floatValue2,
            3);
        render(
            entry,
            vertexConsumer,
            vec3d2.subtract(vec3d6),
            1.0F,
            0.0F,
            lumaData.count(),
            floatValue4 * floatValue2 * floatValue2,
            3);
        render(
            entry,
            vertexConsumer,
            vec3d3.subtract(vec3d7),
            1.0F,
            1.0F,
            lumaData.count(),
            floatValue4 * floatValue3 * floatValue3,
            3);
        render(
            entry,
            vertexConsumer,
            vec3d3.add(vec3d7),
            0.0F,
            1.0F,
            lumaData.count(),
            floatValue4 * floatValue3 * floatValue3,
            3);
      }
    }
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      Vec3d vec3d,
      Vec3d vec3d2,
      Vec3d vec3d3,
      float floatValue,
      int intValue,
      float floatValue2,
      int intValue2) {
    Vec3d vec3d4 = vec3d2.multiply(floatValue);
    Vec3d vec3d5 = vec3d3.multiply(floatValue);
    render(
        entry,
        vertexConsumer,
        vec3d.subtract(vec3d4).subtract(vec3d5),
        0.0F,
        0.0F,
        intValue,
        floatValue2,
        intValue2);
    render(
        entry,
        vertexConsumer,
        vec3d.add(vec3d4).subtract(vec3d5),
        1.0F,
        0.0F,
        intValue,
        floatValue2,
        intValue2);
    render(
        entry,
        vertexConsumer,
        vec3d.add(vec3d4).add(vec3d5),
        1.0F,
        1.0F,
        intValue,
        floatValue2,
        intValue2);
    render(
        entry,
        vertexConsumer,
        vec3d.subtract(vec3d4).add(vec3d5),
        0.0F,
        1.0F,
        intValue,
        floatValue2,
        intValue2);
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      Vec3d vec3d,
      float floatValue,
      float floatValue2,
      int intValue,
      float floatValue3,
      int intValue2) {
    render(
        entry,
        vertexConsumer,
        (float) vec3d.x,
        (float) vec3d.y,
        (float) vec3d.z,
        floatValue,
        floatValue2,
        intValue,
        floatValue3,
        intValue2,
        0.0F,
        0.0F,
        1.0F,
        0.0F);
  }

  public static void render(
      Entry entry,
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      int intValue,
      float floatValue6,
      int intValue2,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10) {
    int intValue3 =
        Math.clamp(Math.round(floatValue6 * 255.0F), 0, 255) << 24 | intValue & 16777215;
    vertexConsumer
        .vertex(entry, floatValue, floatValue2, floatValue3)
        .color(intValue3)
        .texture(floatValue4, floatValue5)
        .overlay(intValue2, 0)
        .light(Math.round(floatValue7 * 4096.0F), 0)
        .normal(entry, floatValue8, floatValue9, floatValue10);
  }

  public static int computeInt(int intValue, int intValue2, float floatValue) {
    int intValue3 =
        Math.round(
            MathHelper.lerp(
                floatValue, (float) (intValue >> 16 & 0xFF), (float) (intValue2 >> 16 & 0xFF)));
    int intValue4 =
        Math.round(
            MathHelper.lerp(
                floatValue, (float) (intValue >> 8 & 0xFF), (float) (intValue2 >> 8 & 0xFF)));
    int intValue5 =
        Math.round(
            MathHelper.lerp(floatValue, (float) (intValue & 0xFF), (float) (intValue2 & 0xFF)));
    return 0xFF000000 | intValue3 << 16 | intValue4 << 8 | intValue5;
  }
}

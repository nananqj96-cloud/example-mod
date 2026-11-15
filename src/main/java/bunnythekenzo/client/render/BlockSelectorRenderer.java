package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.RenderSetup.Builder;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimatedBlockOutline;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.BlockSelectorData;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.CustomBlockOutlineModule;
import bunnythekenzo.client.module.ModuleManager;

public final class BlockSelectorRenderer {
  public static final double value = Math.PI * 2;
  public static final Vec3d vec3d = new Vec3d(1.0, 0.0, 0.0);
  public static final Vec3d vec3d2 = new Vec3d(0.0, 1.0, 0.0);
  public static final Vec3d vec3d3 = new Vec3d(0.0, 0.0, 1.0);
  public static final double[] valueArray =
      new double[] {0.72, 0.96, 1.0, 0.84, 0.57, 0.43, 0.5, 0.63};
  public static RenderLayer renderLayer;
  public static RenderLayer renderLayer2;
  public static int count;

  public static void update() {
    if (renderLayer == null) {
      renderLayer = getRenderLayer("block_selector_material", false);
      renderLayer2 = getRenderLayer("block_selector_light", true);
    }
  }

  public static RenderLayer getRenderLayer(String text, boolean flag) {
    RenderPipeline renderPipeline =
        RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
            .withLocation("bunnythekenzo/pipeline/" + text)
            .withBlend(flag ? BlendFunction.LIGHTNING : BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false)
            .build();
    Builder builder = RenderSetup.builder(RenderPipelines.register(renderPipeline));
    if (!flag) {
      builder.translucent();
    }

    return RenderLayer.of("bunnythekenzo:" + text, builder.build());
  }

  public static int getCount() {
    return count;
  }

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      CustomBlockOutlineModule customBlockOutlineModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (customBlockOutlineModule != null) {
      if (customBlockOutlineModule.isEnabled()
          && instance.world != null
          && instance.player != null
          && !instance.options.hudHidden
          && (moduleManager.freecamModule == null || !moduleManager.freecamModule.test())) {
        BlockHitResult blockHitResult =
            instance.crosshairTarget instanceof BlockHitResult blockHitResult2
                    && blockHitResult2.getType() == Type.BLOCK
                ? blockHitResult2
                : null;
        customBlockOutlineModule.blockOutlineTransition.apply(
            instance.currentScreen == null ? worldRenderState.outlineRenderState : null,
            blockHitResult,
            instance.world,
            System.nanoTime(),
            customBlockOutlineModule);
        if (customBlockOutlineModule.blockOutlineTransition.getAnimatedBlockOutline() != null
            || customBlockOutlineModule.blockOutlineTransition.getOutlineDirectionSwitchTable()
                != null) {
          update();
          Vec3d pos = worldRenderState.cameraRenderState.pos;
          apply(immediate, matrixStack, pos, customBlockOutlineModule, renderLayer, false);
          if ((Double) customBlockOutlineModule.glowSetting.getValue() > 0.0) {
            apply(immediate, matrixStack, pos, customBlockOutlineModule, renderLayer2, true);
          }

          count++;
        }
      } else {
        customBlockOutlineModule.blockOutlineTransition.update();
      }
    }
  }

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d4,
      CustomBlockOutlineModule customBlockOutlineModule,
      RenderLayer renderLayer3,
      boolean flag) {
    VertexConsumer buffer = immediate.getBuffer(renderLayer3);
    apply(
        buffer,
        matrixStack,
        vec3d4,
        customBlockOutlineModule,
        customBlockOutlineModule.blockOutlineTransition.getOutlineDirectionSwitchTable(),
        flag);
    apply(
        buffer,
        matrixStack,
        vec3d4,
        customBlockOutlineModule,
        customBlockOutlineModule.blockOutlineTransition.getAnimatedBlockOutline(),
        flag);
    immediate.draw(renderLayer3);
  }

  public static void apply(
      VertexConsumer vertexConsumer,
      MatrixStack matrixStack,
      Vec3d vec3d4,
      CustomBlockOutlineModule customBlockOutlineModule,
      AnimatedBlockOutline animatedBlockOutline,
      boolean enabled) {
    if (animatedBlockOutline != null && !(animatedBlockOutline.getValue() <= 0.001)) {
      double value2 = customBlockOutlineModule.blockOutlineTransition.getValue();
      double value3 = animatedBlockOutline.computeDouble();
      double doubleValue =
          customBlockOutlineModule.animationSetting.isSelected("Pulse")
              ? Math.sin(value2 * 2.7)
              : 0.0;
      double doubleValue2 = (Double) customBlockOutlineModule.detailScaleSetting.getValue() / 100.0;
      double doubleValue3 =
          (Double) customBlockOutlineModule.spacingSetting.getValue() / 100.0
              + (1.0 - value3) * 0.14
              + doubleValue * 0.009;
      float amount =
          (float)
              (value3
                  * (Double) customBlockOutlineModule.opacitySetting.getValue()
                  / 100.0
                  * (0.96 + doubleValue * 0.04));
      if (enabled) {
        amount *= customBlockOutlineModule.glowSetting.floatValue() / 100.0F;
      }

      matrixStack.push();

      try {
        matrixStack.translate(
            animatedBlockOutline.blockPos.getX() - vec3d4.x,
            animatedBlockOutline.blockPos.getY() - vec3d4.y,
            animatedBlockOutline.blockPos.getZ() - vec3d4.z);
        BlockSelectorData blockSelectorData =
            new BlockSelectorData(
                vertexConsumer,
                matrixStack.peek(),
                enabled,
                amount,
                vec3d4.subtract(
                    animatedBlockOutline.blockPos.getX(),
                    animatedBlockOutline.blockPos.getY(),
                    animatedBlockOutline.blockPos.getZ()));
        Box box = animatedBlockOutline.getBox();
        double doubleValue4 =
            (Double) customBlockOutlineModule.thicknessSetting.getValue() * 0.0075 * doubleValue2;
        int intValue = computeInt(customBlockOutlineModule, value2, 0.0);
        int intValue2 = computeInt(customBlockOutlineModule, value2, 1.0);
        render(
            blockSelectorData,
            box,
            doubleValue3,
            doubleValue4,
            doubleValue2,
            value3,
            value2,
            (String) customBlockOutlineModule.styleSetting.getValue(),
            intValue,
            intValue2);
        if ((Boolean) customBlockOutlineModule.orbitRingsSetting.getValue()
            && !customBlockOutlineModule.styleSetting.isSelected("Focus")) {
          render(
              blockSelectorData,
              box,
              doubleValue3,
              doubleValue4,
              doubleValue2,
              value2,
              customBlockOutlineModule,
              intValue,
              intValue2);
        }

        if ((Boolean) customBlockOutlineModule.faceMarkerSetting.getValue()) {
          render(
              blockSelectorData,
              animatedBlockOutline,
              doubleValue3,
              doubleValue4,
              doubleValue2,
              value3,
              value2,
              customBlockOutlineModule,
              intValue,
              intValue2);
        }
      } finally {
        matrixStack.pop();
      }
    }
  }

  public static int computeInt(
      CustomBlockOutlineModule customBlockOutlineModule, double doubleValue, double doubleValue2) {
    if ((Boolean) customBlockOutlineModule.rainbowSetting.getValue()) {
      return ArgbColors.fromHsv(
          (float) ((doubleValue * 30.0 + doubleValue2 * 120.0) % 360.0), 0.62F, 1.0F);
    }

    float floatValue = (float) doubleValue2;
    if (customBlockOutlineModule.animationSetting.isSelected("Gradient Flow")) {
      floatValue = (float) (0.5 + 0.5 * Math.sin(doubleValue * 1.4 + doubleValue2 * Math.PI));
    }

    return ArgbColors.lerp(
        customBlockOutlineModule.colorSetting.getInteger() | 0xFF000000,
        customBlockOutlineModule.accentColorSetting.getInteger() | 0xFF000000,
        floatValue);
  }

  public static void render(
      BlockSelectorData blockSelectorData,
      Box box,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      String text,
      int intValue,
      int intValue2) {
    double doubleValue6 = text.equals("Focus") ? 0.17 : 0.24;
    double doubleValue7 =
        Math.min(box.getLengthX() * 0.32, doubleValue6 * doubleValue3)
            * (0.55 + doubleValue4 * 0.45);
    double doubleValue8 =
        Math.min(box.getLengthY() * 0.32, doubleValue6 * doubleValue3)
            * (0.55 + doubleValue4 * 0.45);
    double doubleValue9 =
        Math.min(box.getLengthZ() * 0.32, doubleValue6 * doubleValue3)
            * (0.55 + doubleValue4 * 0.45);
    doubleValue2 =
        Math.min(
            doubleValue2,
            (Math.min(box.getLengthX(), Math.min(box.getLengthY(), box.getLengthZ()))
                    + doubleValue * 2.0)
                * 0.17);

    for (int index = 0; index < 2; index++) {
      for (int index2 = 0; index2 < 2; index2++) {
        for (int index3 = 0; index3 < 2; index3++) {
          int intValue3 = index == 0 ? 1 : -1;
          int intValue4 = index2 == 0 ? 1 : -1;
          int intValue5 = index3 == 0 ? 1 : -1;
          Vec3d vec3d4 =
              new Vec3d(
                  index == 0 ? box.minX - doubleValue : box.maxX + doubleValue,
                  index2 == 0 ? box.minY - doubleValue : box.maxY + doubleValue,
                  index3 == 0 ? box.minZ - doubleValue : box.maxZ + doubleValue);
          int value2 = ArgbColors.lerp(intValue, intValue2, (index + index2 + index3) / 4.0F);
          if (text.equals("Orbit")) {
            blockSelectorData.render(
                vec3d4,
                vec3d2,
                vec3d,
                vec3d3,
                doubleValue2 * 2.5,
                doubleValue2 * 4.8,
                doubleValue5 * 0.65 + index + index3,
                value2);
          } else {
            Vec3d[] vec3dArray =
                new Vec3d[] {
                  vec3d4.add(intValue3 * doubleValue7, 0.0, 0.0),
                  vec3d4.add(0.0, intValue4 * doubleValue8, 0.0),
                  vec3d4.add(0.0, 0.0, intValue5 * doubleValue9)
                };

            for (Vec3d vec3d5 : vec3dArray) {
              blockSelectorData.render(vec3d4, vec3d5, doubleValue2, value2, false);
              Vec3d vec3d6 =
                  new Vec3d(-intValue3, -intValue4, -intValue5)
                      .normalize()
                      .multiply(doubleValue2 * 0.9);
              blockSelectorData.render(
                  vec3d4.lerp(vec3d5, 0.18).add(vec3d6),
                  vec3d4.lerp(vec3d5, 0.86).add(vec3d6),
                  doubleValue2 * 0.22,
                  intValue2,
                  true);
              blockSelectorData.render(
                  vec3d4.lerp(vec3d5, 0.88),
                  vec3d5,
                  doubleValue2 * 1.12,
                  ArgbColors.lighten(intValue2, 0.3F),
                  true);
            }

            if (!text.equals("Focus")) {
              blockSelectorData.render(
                  vec3d4,
                  vec3d2,
                  vec3d,
                  vec3d3,
                  doubleValue2 * 1.9,
                  doubleValue2 * 3.4,
                  Math.PI / 4,
                  intValue2);
            }
          }
        }
      }
    }
  }

  public static void render(
      BlockSelectorData blockSelectorData,
      Box box,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      CustomBlockOutlineModule customBlockOutlineModule,
      int intValue,
      int intValue2) {
    boolean flag = customBlockOutlineModule.styleSetting.isSelected("Orbit");
    double doubleValue5 = (box.getLengthX() * 0.5 + doubleValue) * Math.sqrt(2.0);
    double doubleValue6 = (box.getLengthZ() * 0.5 + doubleValue) * Math.sqrt(2.0);
    Vec3d center = box.getCenter();
    double doubleValue7 =
        customBlockOutlineModule.animationSetting.isSelected("Pulse") ? 0.0 : doubleValue4 * 0.55;
    int intValue3 = flag ? 2 : 1;

    for (int index = 0; index < intValue3; index++) {
      double doubleValue8 = index == 0 ? 1.0 : -1.0;
      Vec3d vec3d4 = center.add(0.0, (index == 0 ? 0.21 : -0.21) * box.getLengthY(), 0.0);
      Vec3d vec3d5 = new Vec3d(1.0, flag ? 0.2 * doubleValue8 : 0.07, 0.0).normalize();
      Vec3d vec3d6 = vec3d5.crossProduct(vec3d2).normalize();
      double doubleValue9 = doubleValue7 * doubleValue8 + index * 1.7;
      int intValue4 = index == 0 ? intValue : intValue2;

      for (int index2 = 0; index2 < 4; index2++) {
        double doubleValue10 = doubleValue9 + index2 * (Math.PI * 2) / 4.0;
        blockSelectorData.render(
            vec3d4,
            vec3d5,
            vec3d6,
            doubleValue5,
            doubleValue6,
            doubleValue2 * 0.7,
            doubleValue2 * 0.48,
            doubleValue10,
            1.1938052083641213,
            intValue4);
        blockSelectorData.render(
            vec3d4,
            vec3d5,
            vec3d6,
            doubleValue5,
            doubleValue6,
            doubleValue2 * 0.23,
            doubleValue2 * 0.68,
            doubleValue10,
            0.2827433388230814,
            ArgbColors.lighten(intValue2, 0.25F));
      }

      if ((Boolean) customBlockOutlineModule.sparklesSetting.getValue()) {
        for (int index3 = 0; index3 < (flag ? 3 : 2); index3++) {
          double doubleValue11 = doubleValue9 + index3 * (Math.PI * 2) / (flag ? 3 : 2);
          Vec3d vec3d7 =
              getVec3d(vec3d4, vec3d5, vec3d6, doubleValue5, doubleValue6, doubleValue11);
          blockSelectorData.render(
              vec3d7,
              vec3d2,
              vec3d,
              vec3d3,
              0.023 * doubleValue3,
              0.056 * doubleValue3,
              -doubleValue4 + index3,
              intValue2);
        }
      }
    }
  }

  public static void render(
      BlockSelectorData blockSelectorData,
      AnimatedBlockOutline animatedBlockOutline,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      CustomBlockOutlineModule customBlockOutlineModule,
      int intValue,
      int intValue2) {
    Box box2 = animatedBlockOutline.getBox2();
    Direction direction = animatedBlockOutline.getDirection();
    Vec3d vec3d4 =
        new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
    Vec3d vec3d5 = direction.getAxis() == Axis.Y ? vec3d : vec3d4.crossProduct(vec3d2).normalize();
    Vec3d vec3d6 = vec3d4.crossProduct(vec3d5).normalize();
    double doubleValue6 = computeDouble(box2, vec3d4) * 0.5;
    double doubleValue7 = computeDouble(box2, vec3d5);
    double doubleValue8 = computeDouble(box2, vec3d6);
    Vec3d vec3d7 = box2.getCenter().add(vec3d4.multiply(doubleValue6 + 0.012 + doubleValue * 0.18));
    double doubleValue9 =
        Math.min(Math.min(doubleValue7, doubleValue8) * 0.32, 0.235 * doubleValue3)
            * (1.12 - 0.12 * doubleValue4);
    double doubleValue10 = Math.min(doubleValue2 * 0.52, doubleValue9 * 0.13);
    double doubleValue11 =
        customBlockOutlineModule.animationSetting.isSelected("Pulse") ? 0.0 : doubleValue5 * 0.72;

    for (int index = 0; index < 3; index++) {
      blockSelectorData.render(
          vec3d7,
          vec3d5,
          vec3d6,
          doubleValue9,
          doubleValue9,
          doubleValue10,
          doubleValue10 * 0.72,
          doubleValue11 + index * (Math.PI * 2) / 3.0,
          1.5079644737231006,
          intValue);
      blockSelectorData.render(
          vec3d7.add(vec3d4.multiply(0.012)),
          vec3d5,
          vec3d6,
          doubleValue9 * 0.74,
          doubleValue9 * 0.74,
          doubleValue10 * 0.55,
          doubleValue10 * 0.45,
          -doubleValue11 * 1.3 + index * (Math.PI * 2) / 3.0,
          1.0681415022205298,
          intValue2);
    }

    for (int index2 = 0; index2 < 12; index2++) {
      double doubleValue12 = index2 * (Math.PI * 2) / 12.0;
      Vec3d vec3d8 =
          vec3d5.multiply(Math.cos(doubleValue12)).add(vec3d6.multiply(Math.sin(doubleValue12)));
      double doubleValue13 = doubleValue9 * (index2 % 3 == 0 ? 1.24 : 1.14);
      blockSelectorData.render(
          vec3d7.add(vec3d8.multiply(doubleValue9 * 1.05)),
          vec3d7.add(vec3d8.multiply(doubleValue13)),
          doubleValue10 * 0.27,
          index2 % 3 == 0 ? intValue2 : ArgbColors.darken(intValue, 0.2F),
          true);
    }

    double doubleValue14 = doubleValue9 * 0.14;
    blockSelectorData.render(
        vec3d7.add(vec3d4.multiply(0.014)),
        vec3d4,
        vec3d5,
        vec3d6,
        doubleValue14,
        doubleValue14 * 0.65,
        Math.PI / 4,
        ArgbColors.lighten(intValue2, 0.25F));
  }

  public static double computeDouble(Box box, Vec3d vec3d4) {
    return Math.abs(vec3d4.x) * box.getLengthX()
        + Math.abs(vec3d4.y) * box.getLengthY()
        + Math.abs(vec3d4.z) * box.getLengthZ();
  }

  public static Vec3d getVec3d(
      Vec3d vec3d4,
      Vec3d vec3d5,
      Vec3d vec3d6,
      double doubleValue,
      double doubleValue2,
      double doubleValue3) {
    return vec3d4
        .add(vec3d5.multiply(Math.cos(doubleValue3) * doubleValue))
        .add(vec3d6.multiply(Math.sin(doubleValue3) * doubleValue2));
  }
}

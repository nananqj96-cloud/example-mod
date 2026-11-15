package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.TargetESPSupport;
import bunnythekenzo.client.module.TargetESPModule;

public final class TargetEspRenderer {
  public static final int count = 80;
  public static final RenderLayer renderLayer =
      getRenderLayer("target_esp", DepthTestFunction.LEQUAL_DEPTH_TEST);
  public static final RenderLayer renderLayer2 =
      getRenderLayer("target_esp_through", DepthTestFunction.NO_DEPTH_TEST);

  public static RenderLayer getRenderLayer(String text, DepthTestFunction depthTestFunction) {
    RenderPipeline renderPipeline =
        RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
            .withLocation("bunnythekenzo/pipeline/" + text)
            .withCull(false)
            .withDepthWrite(false)
            .withDepthTestFunction(depthTestFunction)
            .build();
    return RenderLayer.of(
        "bunnythekenzo:" + text, RenderSetup.builder(renderPipeline).translucent().build());
  }

  public static void apply(
      Immediate immediate, MatrixStack matrixStack, Vec3d vec3d, TargetESPModule targetESPModule) {
    MinecraftClient instance = MinecraftClient.getInstance();
    float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
    targetESPModule.render(instance, tickProgress, System.nanoTime());
    if (!instance.options.hudHidden
        && (targetESPModule.getTargetESPSupport() != null
            || targetESPModule.getTargetESPSupport2() != null)) {
      RenderLayer renderLayer3 =
          (java.lang.Boolean) (targetESPModule.throughWallsSetting.getValue())
              ? renderLayer2
              : renderLayer;
      VertexConsumer buffer = immediate.getBuffer(renderLayer3);
      render(
          buffer,
          matrixStack,
          vec3d,
          targetESPModule,
          targetESPModule.getTargetESPSupport2(),
          tickProgress);
      render(
          buffer,
          matrixStack,
          vec3d,
          targetESPModule,
          targetESPModule.getTargetESPSupport(),
          tickProgress);
      immediate.draw(renderLayer3);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      MatrixStack matrixStack,
      Vec3d vec3d,
      TargetESPModule targetESPModule,
      TargetESPSupport targetESPSupport,
      float floatValue) {
    if (targetESPSupport != null && !(targetESPSupport.computeFloat() < 0.003F)) {
      PlayerEntity playerEntity = targetESPSupport.playerEntity;
      float value = targetESPModule.computeFloat();
      float floatValue2 =
          (java.lang.Boolean) (targetESPModule.pulseSetting.getValue())
              ? MathHelper.sin(value * 2.4F)
              : 0.0F;
      float floatValue3 =
          targetESPSupport.computeFloat()
              * targetESPModule.opacitySetting.floatValue()
              / 100.0F
              * (0.94F + floatValue2 * 0.06F);
      float floatValue4 =
          targetESPModule.scaleSetting.floatValue()
              * (0.86F + targetESPSupport.computeFloat() * 0.14F)
              * (1.0F + floatValue2 * 0.018F);
      float floatValue5 = Math.max(playerEntity.getWidth() * 0.5F, 0.3F) + 0.3F;
      float floatValue6 = Math.max(0.5F, playerEntity.getHeight());
      Vec3d lerpedPos = playerEntity.getLerpedPos(floatValue);
      matrixStack.push();

      try {
        matrixStack.translate(
            lerpedPos.x - vec3d.x,
            lerpedPos.y - vec3d.y + floatValue6 * 0.5,
            lerpedPos.z - vec3d.z);
        matrixStack.scale(floatValue4, floatValue4, floatValue4);
        matrixStack.translate(0.0, -floatValue6 * 0.5, 0.0);
        if (targetESPModule.styleSetting.isSelected("Helix")) {
          render(
              vertexConsumer,
              matrixStack.peek(),
              targetESPModule,
              playerEntity,
              floatValue5,
              floatValue6,
              value,
              floatValue3);
        } else if (targetESPModule.styleSetting.isSelected("Scan")) {
          render2(
              vertexConsumer,
              matrixStack.peek(),
              targetESPModule,
              playerEntity,
              floatValue5,
              floatValue6,
              value,
              floatValue3);
        } else {
          render(
              vertexConsumer,
              matrixStack,
              targetESPModule,
              playerEntity,
              floatValue5,
              floatValue6,
              value,
              floatValue3);
        }
      } finally {
        matrixStack.pop();
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      MatrixStack matrixStack,
      TargetESPModule targetESPModule,
      PlayerEntity playerEntity,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    for (int index = 0; index < 2; index++) {
      float floatValue5 = index == 0 ? 1.0F : -1.0F;
      matrixStack.push();
      matrixStack.translate(0.0F, floatValue2 * (index == 0 ? 0.43F : 0.63F), 0.0F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_Y.rotation(floatValue3 * 0.35F * floatValue5 + index * 1.5F));
      matrixStack.multiply(
          RotationAxis.POSITIVE_X.rotation(floatValue5 * Math.min(0.58F, floatValue2 * 0.3F)));
      float floatValue6 = floatValue3 * floatValue5 * 1.15F + index * (float) Math.PI;
      render(
          vertexConsumer,
          matrixStack.peek(),
          targetESPModule,
          playerEntity,
          floatValue + index * 0.055F,
          0.0F,
          floatValue6,
          5.152212F,
          0.018F,
          floatValue4,
          index * 0.5F);
      if ((Boolean) targetESPModule.showCrystalsSetting.getValue()) {
        float floatValue7 = floatValue + index * 0.055F;
        matrixStack.push();
        matrixStack.translate(
            MathHelper.cos(floatValue6) * floatValue7,
            0.0F,
            MathHelper.sin(floatValue6) * floatValue7);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(floatValue3 * 1.8F * floatValue5));
        render(
            vertexConsumer,
            matrixStack.peek(),
            targetESPModule.computeInt(playerEntity, index * 0.5F),
            0.075F,
            floatValue4);
        matrixStack.pop();
      }

      matrixStack.pop();
    }

    for (int index2 = 0; index2 < 4; index2++) {
      render(
          vertexConsumer,
          matrixStack.peek(),
          targetESPModule,
          playerEntity,
          floatValue * 0.92F,
          0.04F,
          index2 * (float) (Math.PI / 2) - floatValue3 * 0.25F,
          0.8F,
          0.008F,
          floatValue4 * 0.36F,
          index2 * 0.25F);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      TargetESPModule targetESPModule,
      PlayerEntity playerEntity,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    int intValue = Math.max(12, Math.round(80.0F * floatValue4 / (float) (Math.PI * 2)));
    float floatValue8 = targetESPModule.glowSetting.floatValue() / 100.0F;

    for (int index = 0; index < intValue; index++) {
      float floatValue9 = (float) index / intValue;
      float floatValue10 = (index + 1.0F) / intValue;
      float floatValue11 = floatValue3 + floatValue4 * floatValue9;
      float floatValue12 = floatValue3 + floatValue4 * floatValue10;
      float floatValue13 =
          floatValue4 >= 6.2821856F
              ? 1.0F
              : Math.min(1.0F, Math.min(floatValue9, 1.0F - floatValue9) * 14.0F);
      float floatValue14 =
          floatValue4 >= 6.2821856F
              ? 1.0F
              : Math.min(1.0F, Math.min(floatValue10, 1.0F - floatValue10) * 14.0F);
      int intValue2 =
          computeInt(
              targetESPModule.computeInt(playerEntity, floatValue7 + floatValue9),
              floatValue6 * floatValue13);
      int intValue3 =
          computeInt(
              targetESPModule.computeInt(playerEntity, floatValue7 + floatValue10),
              floatValue6 * floatValue14);
      float floatValue15 = floatValue - floatValue5;
      float floatValue16 = floatValue + floatValue5;
      float floatValue17 = floatValue5 * 0.5F;
      render(
          vertexConsumer,
          entry,
          floatValue11,
          floatValue12,
          floatValue15,
          floatValue,
          floatValue2,
          floatValue2 + floatValue17,
          ArgbColors.lighten(intValue2, 0.45F),
          ArgbColors.lighten(intValue3, 0.45F),
          intValue2,
          intValue3);
      render(
          vertexConsumer,
          entry,
          floatValue11,
          floatValue12,
          floatValue,
          floatValue16,
          floatValue2 + floatValue17,
          floatValue2,
          intValue2,
          intValue3,
          ArgbColors.lighten(intValue2, 0.35F),
          ArgbColors.lighten(intValue3, 0.35F));
      render(
          vertexConsumer,
          entry,
          floatValue11,
          floatValue12,
          floatValue15,
          floatValue,
          floatValue2,
          floatValue2 - floatValue17,
          intValue2,
          intValue3,
          intValue2,
          intValue3);
      render(
          vertexConsumer,
          entry,
          floatValue11,
          floatValue12,
          floatValue,
          floatValue16,
          floatValue2 - floatValue17,
          floatValue2,
          intValue2,
          intValue3,
          intValue2,
          intValue3);
      if (floatValue8 > 0.0F) {
        int intValue4 = computeInt(intValue2, floatValue8 * 0.27F);
        int intValue5 = computeInt(intValue3, floatValue8 * 0.27F);
        float floatValue18 = 0.035F + floatValue8 * 0.065F;
        render(
            vertexConsumer,
            entry,
            floatValue11,
            floatValue12,
            floatValue16,
            floatValue16 + floatValue18,
            floatValue2,
            floatValue2,
            intValue4,
            intValue5,
            intValue4 & 16777215,
            intValue5 & 16777215);
        render(
            vertexConsumer,
            entry,
            floatValue11,
            floatValue12,
            floatValue15 - floatValue18,
            floatValue15,
            floatValue2,
            floatValue2,
            intValue4 & 16777215,
            intValue5 & 16777215,
            intValue4,
            intValue5);
      }
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      TargetESPModule targetESPModule,
      PlayerEntity playerEntity,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = targetESPModule.glowSetting.floatValue() / 100.0F;

    for (int index = 0; index < 2; index++) {
      for (int index2 = 0; index2 < 80; index2++) {
        float floatValue6 = index2 / 80.0F;
        float floatValue7 = (index2 + 1.0F) / 80.0F;
        float floatValue8 =
            floatValue3 * 1.4F
                + floatValue6 * (float) (Math.PI * 2) * 1.25F
                + index * (float) Math.PI;
        float floatValue9 =
            floatValue3 * 1.4F
                + floatValue7 * (float) (Math.PI * 2) * 1.25F
                + index * (float) Math.PI;
        float floatValue10 = 0.07F + floatValue6 * floatValue2;
        float floatValue11 = 0.07F + floatValue7 * floatValue2;
        int intValue =
            computeInt(
                targetESPModule.computeInt(playerEntity, floatValue6 + index * 0.5F),
                floatValue4 * MathHelper.sin(floatValue6 * (float) Math.PI));
        int intValue2 =
            computeInt(
                targetESPModule.computeInt(playerEntity, floatValue7 + index * 0.5F),
                floatValue4 * MathHelper.sin(floatValue7 * (float) Math.PI));
        render(
            vertexConsumer,
            entry,
            floatValue,
            floatValue8,
            floatValue9,
            floatValue10 - 0.018F,
            floatValue11 - 0.018F,
            floatValue10 + 0.018F,
            floatValue11 + 0.018F,
            intValue,
            intValue2,
            ArgbColors.lighten(intValue, 0.4F),
            ArgbColors.lighten(intValue2, 0.4F));
        if (floatValue5 > 0.0F) {
          int intValue3 = computeInt(intValue, floatValue5 * 0.3F);
          int intValue4 = computeInt(intValue2, floatValue5 * 0.3F);
          render(
              vertexConsumer,
              entry,
              floatValue,
              floatValue8,
              floatValue9,
              floatValue10 - 0.1F,
              floatValue11 - 0.1F,
              floatValue10 - 0.018F,
              floatValue11 - 0.018F,
              intValue3 & 16777215,
              intValue4 & 16777215,
              intValue3,
              intValue4);
          render(
              vertexConsumer,
              entry,
              floatValue,
              floatValue8,
              floatValue9,
              floatValue10 + 0.018F,
              floatValue11 + 0.018F,
              floatValue10 + 0.1F,
              floatValue11 + 0.1F,
              intValue3,
              intValue4,
              intValue3 & 16777215,
              intValue4 & 16777215);
        }
      }
    }
  }

  public static void render2(
      VertexConsumer vertexConsumer,
      Entry entry,
      TargetESPModule targetESPModule,
      PlayerEntity playerEntity,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float floatValue5 = floatValue3 * 1.7F;
    float floatValue6 = 0.06F + (0.5F - MathHelper.cos(floatValue5) * 0.5F) * floatValue2;
    float floatValue7 = -MathHelper.sin(floatValue5) * Math.min(0.48F, floatValue2 * 0.3F);

    for (int index = 0; index < 80; index++) {
      float floatValue8 = index / 80.0F;
      float floatValue9 = (index + 1.0F) / 80.0F;
      int intValue =
          computeInt(targetESPModule.computeInt(playerEntity, floatValue8), floatValue4 * 0.2F);
      int intValue2 =
          computeInt(targetESPModule.computeInt(playerEntity, floatValue9), floatValue4 * 0.2F);
      render(
          vertexConsumer,
          entry,
          floatValue,
          floatValue8 * (float) (Math.PI * 2),
          floatValue9 * (float) (Math.PI * 2),
          floatValue6,
          floatValue6,
          floatValue6 + floatValue7,
          floatValue6 + floatValue7,
          intValue,
          intValue2,
          intValue & 16777215,
          intValue2 & 16777215);
    }

    render(
        vertexConsumer,
        entry,
        targetESPModule,
        playerEntity,
        floatValue,
        floatValue6,
        0.0F,
        (float) (Math.PI * 2),
        0.021F,
        floatValue4,
        0.0F);
    render(
        vertexConsumer,
        entry,
        targetESPModule,
        playerEntity,
        floatValue * 1.06F,
        0.045F,
        floatValue3 * 0.5F,
        (float) (Math.PI * 8.0 / 5.0),
        0.009F,
        floatValue4 * 0.3F,
        0.5F);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      int intValue,
      float floatValue,
      float floatValue2) {
    for (int index = 0; index < 4; index++) {
      float floatValue3 = index * (float) (Math.PI / 2);
      float floatValue4 = (index + 1) * (float) (Math.PI / 2);
      float floatValue5 = MathHelper.cos(floatValue3) * floatValue;
      float floatValue6 = MathHelper.sin(floatValue3) * floatValue;
      float floatValue7 = MathHelper.cos(floatValue4) * floatValue;
      float floatValue8 = MathHelper.sin(floatValue4) * floatValue;
      int intValue2 = computeInt(ArgbColors.lighten(intValue, index * 0.12F), floatValue2);
      int intValue3 = computeInt(ArgbColors.lighten(intValue, 0.7F), floatValue2);
      render(vertexConsumer, entry, 0.0F, floatValue * 1.8F, 0.0F, intValue3);
      render(vertexConsumer, entry, floatValue5, 0.0F, floatValue6, intValue2);
      render(vertexConsumer, entry, floatValue7, 0.0F, floatValue8, intValue2);
      render(vertexConsumer, entry, 0.0F, floatValue * 1.8F, 0.0F, intValue3);
      render(vertexConsumer, entry, 0.0F, -floatValue * 1.8F, 0.0F, intValue2);
      render(vertexConsumer, entry, floatValue7, 0.0F, floatValue8, intValue2);
      render(vertexConsumer, entry, floatValue5, 0.0F, floatValue6, intValue3);
      render(vertexConsumer, entry, 0.0F, -floatValue * 1.8F, 0.0F, intValue2);
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4) {
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue) * floatValue3,
        floatValue5,
        MathHelper.sin(floatValue) * floatValue3,
        intValue);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue2) * floatValue3,
        floatValue5,
        MathHelper.sin(floatValue2) * floatValue3,
        intValue2);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue2) * floatValue4,
        floatValue6,
        MathHelper.sin(floatValue2) * floatValue4,
        intValue4);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue) * floatValue4,
        floatValue6,
        MathHelper.sin(floatValue) * floatValue4,
        intValue3);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue,
      int intValue2,
      int intValue3,
      int intValue4) {
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue2) * floatValue,
        floatValue4,
        MathHelper.sin(floatValue2) * floatValue,
        intValue);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue3) * floatValue,
        floatValue5,
        MathHelper.sin(floatValue3) * floatValue,
        intValue2);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue3) * floatValue,
        floatValue7,
        MathHelper.sin(floatValue3) * floatValue,
        intValue4);
    render(
        vertexConsumer,
        entry,
        MathHelper.cos(floatValue2) * floatValue,
        floatValue6,
        MathHelper.sin(floatValue2) * floatValue,
        intValue3);
  }

  public static void render(
      VertexConsumer vertexConsumer,
      Entry entry,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    vertexConsumer.vertex(entry, floatValue, floatValue2, floatValue3).color(intValue);
  }

  public static int computeInt(int intValue, float floatValue) {
    return ArgbColors.withOpacity(intValue, (intValue >>> 24) / 255.0F * floatValue);
  }
}

package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.util.List;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.BatchingRenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.item.ItemRenderState.Glint;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import bunnythekenzo.client.GlintSubmissionScope;
import bunnythekenzo.client.render.GlintPipelines;

@Mixin(BatchingRenderCommandQueue.class)
public class BatchingRenderCommandQueueMixin {
  @WrapMethod(method = "submitItem")
  public void apply(
      MatrixStack matrixStack,
      ItemDisplayContext itemDisplayContext,
      int intValue,
      int intValue2,
      int intValue3,
      int[] intValues,
      List list,
      RenderLayer renderLayer,
      Glint glint,
      Operation operation) {
    if (GlintSubmissionScope.isActive()) {
      renderLayer = GlintPipelines.getRenderLayer(renderLayer, true);
      if (GlintPipelines.test2(renderLayer)) {
        glint = Glint.NONE;
      }
    }

    operation.call(
        matrixStack,
        itemDisplayContext,
        intValue,
        intValue2,
        intValue3,
        intValues,
        list,
        renderLayer,
        glint);
  }

  @WrapMethod(method = "submitModel")
  public void apply(
      Model model,
      Object value2,
      MatrixStack matrixStack,
      RenderLayer renderLayer,
      int intValue,
      int intValue2,
      int intValue3,
      Sprite sprite,
      int intValue4,
      CrumblingOverlayCommand crumblingOverlayCommand,
      Operation operation) {
    if (GlintSubmissionScope.isActive()) {
      if (GlintPipelines.test3(renderLayer)) {
        return;
      }

      renderLayer = GlintPipelines.material(renderLayer, false, sprite);
    }

    operation.call(
        model,
        value2,
        matrixStack,
        renderLayer,
        intValue,
        intValue2,
        intValue3,
        sprite,
        intValue4,
        crumblingOverlayCommand);
  }

  @WrapMethod(method = "submitModelPart")
  public void apply(
      ModelPart modelPart,
      MatrixStack matrixStack,
      RenderLayer renderLayer,
      int intValue,
      int intValue2,
      Sprite sprite,
      boolean flag,
      boolean flag2,
      int intValue3,
      CrumblingOverlayCommand crumblingOverlayCommand,
      int intValue4,
      Operation operation) {
    if (GlintSubmissionScope.isActive()) {
      if (GlintPipelines.test3(renderLayer)) {
        return;
      }

      renderLayer = GlintPipelines.material(renderLayer, false, sprite);
      if (GlintPipelines.test2(renderLayer)) {
        flag2 = false;
      }
    }

    operation.call(
        modelPart,
        matrixStack,
        renderLayer,
        intValue,
        intValue2,
        sprite,
        flag,
        flag2,
        intValue3,
        crumblingOverlayCommand,
        intValue4);
  }
}

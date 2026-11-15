package bunnythekenzo.client.mixin;

import java.util.List;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderState.Glint;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.VertexConsumerSupport8;
import bunnythekenzo.client.render.GlintPipelines;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
  @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
  private static void apply(
      ItemDisplayContext itemDisplayContext,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int intValue,
      int intValue2,
      int[] intValues,
      List list,
      RenderLayer renderLayer,
      Glint glint,
      CallbackInfo callbackInfo) {
    if (GlintPipelines.test(renderLayer)) {
      VertexConsumer buffer = vertexConsumerProvider.getBuffer(renderLayer);

      for (BakedQuad bakedQuad : (java.lang.Iterable<BakedQuad>) (java.lang.Iterable<?>) (list)) {
        int intValue3 =
            bakedQuad.hasTint()
                    && bakedQuad.tintIndex() >= 0
                    && bakedQuad.tintIndex() < intValues.length
                ? intValues[bakedQuad.tintIndex()]
                : -1;
        new VertexConsumerSupport8(buffer, bakedQuad.sprite())
            .quad(
                matrixStack.peek(),
                bakedQuad,
                ColorHelper.getRed(intValue3) / 255.0F,
                ColorHelper.getGreen(intValue3) / 255.0F,
                ColorHelper.getBlue(intValue3) / 255.0F,
                ColorHelper.getAlpha(intValue3) / 255.0F,
                intValue,
                intValue2);
      }

      callbackInfo.cancel();
    }
  }
}

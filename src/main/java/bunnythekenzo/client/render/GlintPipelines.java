package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.RenderSetup.Builder;
import net.minecraft.client.render.TextureTransform;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.mixin.Class12247Class12338Accessor;
import bunnythekenzo.client.mixin.RenderLayerAccessor;
import bunnythekenzo.client.mixin.RenderSetupAccessor;
import bunnythekenzo.client.module.CustomGlintModule;

public final class GlintPipelines {
  public static final Map map = new HashMap();
  public static final Map map2 = new HashMap();
  public static final Map map3 = new IdentityHashMap();

  public static TextureTransform parameters(Sprite sprite) {
    return new TextureTransform(
        "67glint_parameters",
        () -> {
          CustomGlintModule customGlintModule =
              ClientInitializer.getModuleManager().customGlintModule;
          return new Matrix4f()
              .m00(customGlintModule.computeFloat())
              .m11(customGlintModule.computeFloat2())
              .m22(customGlintModule.glowSetting.floatValue() / 100.0F)
              .m33(customGlintModule.patternSizeSetting.floatValue() / 100.0F)
              .m01(sprite == null ? 0.0F : sprite.getMinU())
              .m02(sprite == null ? 0.0F : sprite.getMinV())
              .m10(sprite == null ? 3.0F : 2.0F / (sprite.getMaxU() - sprite.getMinU()))
              .m12(sprite == null ? 3.0F : 2.0F / (sprite.getMaxV() - sprite.getMinV()));
        });
  }

  public static boolean test(RenderLayer renderLayer) {
    return Boolean.TRUE.equals(map3.get(renderLayer));
  }

  public static boolean test2(RenderLayer renderLayer) {
    return map3.containsKey(renderLayer);
  }

  public static boolean test3(RenderLayer renderLayer) {
    return renderLayer == RenderLayers.glint()
        || renderLayer == RenderLayers.glintTranslucent()
        || renderLayer == RenderLayers.entityGlint()
        || renderLayer == RenderLayers.armorEntityGlint();
  }

  public static RenderLayer getRenderLayer(RenderLayer renderLayer, boolean flag) {
    return material(renderLayer, flag, null);
  }

  public static RenderLayer material(RenderLayer renderLayer, boolean enabled, Sprite sprite) {
    return renderLayer != null
            && !test2(renderLayer)
            && renderLayer.getVertexFormat()
                == VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL
        ? (RenderLayer)
            map.computeIfAbsent(
                new GlintPipelinesData(renderLayer, enabled, sprite),
                value -> {
                  return create(
                      ((bunnythekenzo.client.render.GlintPipelinesData) (value)).renderLayer(),
                      ((bunnythekenzo.client.render.GlintPipelinesData) (value)).enabled(),
                      ((bunnythekenzo.client.render.GlintPipelinesData) (value)).sprite());
                })
        : renderLayer;
  }

  public static RenderLayer create(RenderLayer renderLayer, boolean enabled, Sprite sprite) {
    AnimatedGlintTexture.tick();
    RenderSetupAccessor renderSetupAccessor =
        (RenderSetupAccessor) (Object) (((RenderLayerAccessor) renderLayer).getRenderSetup());
    RenderPipeline renderPipeline =
        (com.mojang.blaze3d.pipeline.RenderPipeline)
            (map2.computeIfAbsent(
                new GlintPipelinesData2(renderLayer.getRenderPipeline(), enabled),
                (referenceArgument65_0) ->
                    GlintPipelines.pipeline(
                        (bunnythekenzo.client.render.GlintPipelinesData2) referenceArgument65_0)));
    Builder builder =
        RenderSetup.builder(renderPipeline)
            .expectedBufferSize(renderLayer.getExpectedBufferSize())
            .layeringTransform(renderSetupAccessor.getLayeringTransform())
            .outputTarget(renderSetupAccessor.getOutputTarget())
            .outlineMode(renderSetupAccessor.getOutlineMode())
            .textureTransform(parameters(sprite));
    renderSetupAccessor
        .getTextures()
        .forEach(
            (value, value2) -> {
              Class12247Class12338Accessor class12247Class12338Accessor =
                  (Class12247Class12338Accessor) value2;
              builder.texture(
                  (java.lang.String) (value),
                  class12247Class12338Accessor.getLocation(),
                  class12247Class12338Accessor.getSampler());
            });
    builder.texture(
        "GlintSampler",
        AnimatedGlintTexture.identifier,
        () -> {
          return RenderSystem.getSamplerCache().getRepeated(FilterMode.LINEAR);
        });
    if (renderSetupAccessor.getUseLightmap()) {
      builder.useLightmap();
    }

    if (renderSetupAccessor.getUseOverlay()) {
      builder.useOverlay();
    }

    if (renderLayer.isTranslucent()) {
      builder.translucent();
    }

    if (renderLayer.hasCrumbling()) {
      builder.crumbling();
    }

    RenderLayer renderLayer2 = RenderLayer.of("67glint_" + map.size(), builder.build());
    map3.put(renderLayer2, enabled);
    return renderLayer2;
  }

  public static RenderPipeline pipeline(GlintPipelinesData2 glintPipelinesData2) {
    RenderPipeline renderPipeline = glintPipelinesData2.renderPipeline();
    com.mojang.blaze3d.pipeline.RenderPipeline.Builder builder =
        RenderPipeline.builder()
            .withLocation(getIdentifier("pipeline/glint_" + map2.size()))
            .withVertexShader(getIdentifier("core/custom_glint"))
            .withFragmentShader(getIdentifier("core/custom_glint"))
            .withVertexFormat(
                renderPipeline.getVertexFormat(), renderPipeline.getVertexFormatMode())
            .withDepthTestFunction(renderPipeline.getDepthTestFunction())
            .withDepthWrite(renderPipeline.isWriteDepth())
            .withColorWrite(renderPipeline.isWriteColor(), renderPipeline.isWriteAlpha())
            .withCull(renderPipeline.isCull())
            .withPolygonMode(renderPipeline.getPolygonMode())
            .withColorLogic(renderPipeline.getColorLogic())
            .withDepthBias(
                renderPipeline.getDepthBiasScaleFactor(), renderPipeline.getDepthBiasConstant());
    renderPipeline.getBlendFunction().ifPresentOrElse(builder::withBlend, builder::withoutBlend);
    renderPipeline
        .getUniforms()
        .forEach(
            uniformDescription -> {
              builder.withUniform(uniformDescription.name(), uniformDescription.type());
            });
    renderPipeline.getSamplers().stream()
        .filter(
            text -> {
              return !text.equals("Sampler1")
                  || !glintPipelinesData2.enabled()
                      && !renderPipeline.getShaderDefines().flags().contains("NO_OVERLAY");
            })
        .forEach(builder::withSampler);
    builder.withSampler("GlintSampler");
    renderPipeline.getShaderDefines().flags().forEach(builder::withShaderDefine);
    renderPipeline
        .getShaderDefines()
        .values()
        .forEach(
            (text2, text3) -> {
              builder.withShaderDefine(text2, Float.parseFloat(text3));
            });
    if (glintPipelinesData2.enabled()) {
      builder.withShaderDefine("GLINT_ITEM");
    }

    return RenderPipelines.register(builder.build());
  }

  public static Identifier getIdentifier(String text) {
    return Identifier.of("bunnythekenzo", text);
  }

  public static void update() {
    map.clear();
    map3.clear();
  }
}

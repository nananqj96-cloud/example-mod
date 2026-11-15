package bunnythekenzo.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.SkyParticleStyle;

public final class SkyPipelines {
  public static final int count = 1048576;
  public static boolean enabled;
  public static BufferAllocator bufferAllocator;
  public static Immediate immediate;
  public static BufferAllocator bufferAllocator2;
  public static Immediate immediate2;
  public static RenderPipeline renderPipeline;
  public static RenderPipeline renderPipeline2;
  public static RenderPipeline renderPipeline3;
  public static RenderPipeline renderPipeline4;
  public static RenderPipeline renderPipeline5;
  public static RenderPipeline renderPipeline6;
  public static RenderPipeline renderPipeline7;
  public static RenderPipeline renderPipeline8;
  public static RenderPipeline renderPipeline9;
  public static RenderPipeline renderPipeline10;
  public static RenderLayer renderLayer;
  public static final Map map = new HashMap();
  public static final Map map2 = new HashMap();
  public static final Map map3 = new HashMap();

  public static Identifier getIdentifier(String text) {
    return Identifier.of("bunnythekenzo", "textures/sky/" + text + ".png");
  }

  public static Identifier getIdentifier2(String text) {
    return Identifier.of("bunnythekenzo", "pipeline/" + text);
  }

  public static Identifier getIdentifier3(String text) {
    return Identifier.ofVanilla("core/" + text);
  }

  public static void update() {
    if (!enabled) {
      enabled = true;

      try {
        renderPipeline6 =
            RenderPipelines.register(getBuilder2("rocket_model").withDepthWrite(true).build());
        renderPipeline7 =
            RenderPipelines.register(getBuilder2("rocket_glass").withDepthWrite(false).build());
        renderPipeline8 =
            RenderPipelines.register(
                RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                    .withLocation(getIdentifier2("rocket_plume"))
                    .withVertexShader(Identifier.of("bunnythekenzo", "core/rocket_plume"))
                    .withFragmentShader(Identifier.of("bunnythekenzo", "core/rocket_plume"))
                    .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
                    .withBlend(BlendFunction.ADDITIVE)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withDepthWrite(false)
                    .withCull(false)
                    .build());

        for (String text :
            new String[] {
              "GAS",
              "ROCK",
              "ICE",
              "CLOUDS",
              "HAZE",
              "ICE_HAZE",
              "RINGS",
              "OCEAN",
              "OCEAN_CLOUDS",
              "OCEAN_HAZE",
              "VOLCANIC",
              "VOLCANIC_HAZE"
            }) {
          Builder builder =
              RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                  .withLocation(getIdentifier2("planet_" + text.toLowerCase(Locale.ROOT)))
                  .withVertexShader(Identifier.of("bunnythekenzo", "core/planet"))
                  .withFragmentShader(Identifier.of("bunnythekenzo", "core/planet"))
                  .withShaderDefine(text)
                  .withVertexFormat(
                      VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS)
                  .withBlend(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA)
                  .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                  .withDepthWrite(false)
                  .withCull(!"RINGS".equals(text));
          if ("ROCK".equals(text)) {
            builder.withSampler("Sampler0");
          }

          map3.put(text, RenderPipelines.register(builder.build()));
        }

        renderPipeline =
            RenderPipelines.register(
                RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                    .withLocation(getIdentifier2("sky_gradient"))
                    .withVertexShader(getIdentifier3("position_color"))
                    .withFragmentShader(getIdentifier3("position_color"))
                    .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withDepthWrite(false)
                    .withCull(false)
                    .build());
        renderPipeline2 =
            RenderPipelines.register(
                getBuilder("sky_sprite")
                    .withBlend(BlendFunction.ADDITIVE)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build());
        renderPipeline3 =
            RenderPipelines.register(
                getBuilder("sky_sprite_soft")
                    .withBlend(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build());
        renderPipeline4 =
            RenderPipelines.register(
                RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                    .withLocation(getIdentifier2("sky_solid"))
                    .withVertexShader(getIdentifier3("position_color"))
                    .withFragmentShader(getIdentifier3("position_color"))
                    .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
                    .withBlend(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withDepthWrite(false)
                    .withCull(true)
                    .build());
        renderPipeline5 =
            RenderPipelines.register(
                RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
                    .withLocation(getIdentifier2("sky_skin"))
                    .withVertexShader(getIdentifier3("position_tex_color"))
                    .withFragmentShader(getIdentifier3("position_tex_color"))
                    .withSampler("Sampler0")
                    .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .withDepthWrite(false)
                    .withCull(true)
                    .build());
        renderPipeline9 =
            RenderPipelines.register(
                getBuilder("mote_additive")
                    .withBlend(BlendFunction.ADDITIVE)
                    .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                    .build());
        renderPipeline10 =
            RenderPipelines.register(
                getBuilder("mote_soft")
                    .withBlend(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA)
                    .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                    .build());
      } catch (Throwable failure) {
        ClientInitializer.logger.error("Could not build the Sky render pipelines", failure);
        renderPipeline = null;
      }
    }
  }

  public static Builder getBuilder(String text) {
    return RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
        .withLocation(getIdentifier2(text))
        .withVertexShader(getIdentifier3("position_tex_color"))
        .withFragmentShader(getIdentifier3("position_tex_color"))
        .withSampler("Sampler0")
        .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
        .withDepthWrite(false)
        .withCull(false);
  }

  public static Builder getBuilder2(String text) {
    return RenderPipeline.builder(RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET)
        .withLocation(getIdentifier2(text))
        .withVertexShader(Identifier.of("bunnythekenzo", "core/rocket"))
        .withFragmentShader(Identifier.of("bunnythekenzo", "core/rocket"))
        .withSampler("Sampler0")
        .withVertexFormat(
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.TRIANGLES)
        .withBlend(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA)
        .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
        .withCull(false);
  }

  public static boolean test() {
    return renderPipeline != null;
  }

  public static RenderLayer getRenderLayer() {
    if (renderLayer == null) {
      renderLayer = RenderLayer.of("67sky_gradient", RenderSetup.builder(renderPipeline).build());
    }

    return renderLayer;
  }

  public static RenderLayer sky(String text, boolean flag) {
    String text2 = text + (flag ? "_add" : "_soft");
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            text2,
            value -> {
              return RenderLayer.of(
                  "67sky_" + value,
                  RenderSetup.builder(flag ? renderPipeline2 : renderPipeline3)
                      .texture("Sampler0", getIdentifier(text))
                      .build());
            }));
  }

  public static RenderLayer solid() {
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            "__solid",
            value -> {
              return RenderLayer.of("67sky_solid", RenderSetup.builder(renderPipeline4).build());
            }));
  }

  public static RenderLayer planet(String text) {
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            "__planet/" + text,
            value -> {
              net.minecraft.client.render.RenderSetup.Builder builder =
                  RenderSetup.builder((RenderPipeline) map3.get(text));
              if ("ROCK".equals(text)) {
                builder.texture("Sampler0", getIdentifier("moon_relief"));
              }

              return RenderLayer.of("67sky_planet_" + text, builder.build());
            }));
  }

  public static RenderLayer rocket(Identifier identifier, boolean flag) {
    Identifier identifier2 = identifier == null ? getIdentifier("mote") : identifier;
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            "__rocket/" + identifier2 + flag,
            value -> {
              return RenderLayer.of(
                  "67sky_rocket_" + identifier2.getPath() + flag,
                  RenderSetup.builder(flag ? renderPipeline7 : renderPipeline6)
                      .texture("Sampler0", identifier2)
                      .build());
            }));
  }

  public static RenderLayer rocketPlume() {
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            "__rocket_plume",
            value -> {
              return RenderLayer.of(
                  "67sky_rocket_plume", RenderSetup.builder(renderPipeline8).build());
            }));
  }

  public static RenderLayer skin(Identifier identifier) {
    return (net.minecraft.client.render.RenderLayer)
        (map.computeIfAbsent(
            "__skin/" + identifier,
            value -> {
              return RenderLayer.of(
                  "67sky_skin_" + identifier.getPath(),
                  RenderSetup.builder(renderPipeline5).texture("Sampler0", identifier).build());
            }));
  }

  public static RenderLayer getRenderLayer2() {
    return sky(SkyParticleStyle.MOTES.getSprite(), true);
  }

  public static RenderLayer motes(SkyParticleStyle skyParticleStyle, boolean flag) {
    String text = skyParticleStyle.getSprite() + (flag ? "_add" : "_soft");
    return (net.minecraft.client.render.RenderLayer)
        (map2.computeIfAbsent(
            text,
            value -> {
              return RenderLayer.of(
                  "67sky_" + value,
                  RenderSetup.builder(flag ? renderPipeline9 : renderPipeline10)
                      .texture("Sampler0", getIdentifier(skyParticleStyle.getSprite()))
                      .build());
            }));
  }

  public static Immediate getImmediate() {
    if (immediate == null) {
      bufferAllocator = new BufferAllocator(1048576);
      immediate = VertexConsumerProvider.immediate(bufferAllocator);
    }

    return immediate;
  }

  public static Immediate getImmediate2() {
    if (immediate2 == null) {
      bufferAllocator2 = new BufferAllocator(1048576);
      immediate2 = VertexConsumerProvider.immediate(bufferAllocator2);
    }

    return immediate2;
  }
}

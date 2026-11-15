package bunnythekenzo.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;

public record GlintPipelinesData(RenderLayer renderLayer, boolean enabled, Sprite sprite) {}

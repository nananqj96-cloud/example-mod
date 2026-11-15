#version 330
#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
uniform sampler2D Sampler0;
uniform sampler2D GlintSampler;
in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec2 effectCoord;
out vec4 fragColor;

void main() {
    vec4 source = texture(Sampler0, texCoord0);
#ifdef ALPHA_CUTOUT
    if (source.a < ALPHA_CUTOUT) discard;
#else
    if (source.a < 0.001) discard;
#endif
    vec4 base = source * vertexColor * ColorModulator;
#ifndef NO_OVERLAY
    base.rgb = mix(overlayColor.rgb, base.rgb, overlayColor.a);
#endif
    base *= lightMapColor;
    float visibility = TextureMat[0][0];
    float strength = TextureMat[1][1];
    float glow = TextureMat[2][2];
    float size = max(0.5, TextureMat[3][3]);
    vec3 material = texture(GlintSampler, fract(effectCoord / size)).rgb;
    material *= mix(lightMapColor.rgb, vec3(1.0), glow);
    vec3 overlay = 1.0 - (1.0 - base.rgb) * (1.0 - material * strength);
    vec3 fullEffect = strength > 0.0 ? material * strength : base.rgb;
    vec4 color = vec4(mix(fullEffect, overlay, visibility), base.a);
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}

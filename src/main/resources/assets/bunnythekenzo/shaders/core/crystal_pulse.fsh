#version 330
in vec2 uv;
in vec4 tint;
out vec4 fragColor;

void main() {
#ifdef SHARD
    vec2 p = uv * 2.0 - 1.0;
    float diamond = abs(p.x) + abs(p.y);
    float edge = max(fwidth(diamond), 0.015);
    float facet = (1.0 - smoothstep(0.30 - edge, 0.30 + edge, diamond));
    float core = exp(-dot(p, p) * 100.0);
    float glow = exp(-dot(p, p) * 7.0) * 0.24;
    float energy = (facet * 0.7 + glow + core * 0.45) * (1.0 - smoothstep(0.7, 1.0, length(p)));
#else
    float d = abs(uv.y * 2.0 - 1.0);
    float pixel = max(fwidth(d), 0.025);
    float filamentWidth = max(0.10, pixel * 0.8);
    float core = exp(-pow(d / filamentWidth, 2.0)) * min(1.0, 0.20 / filamentWidth);
    float glow = exp(-d * d * 8.0) * 0.35;
    float energy = (core * 0.85 + glow) * (1.0 - smoothstep(0.72, 1.0, d));
#endif
    float alpha = energy * tint.a;
    vec3 color = mix(tint.rgb, vec3(0.94, 1.0, 1.0), core * 0.52);
    fragColor = vec4(color * alpha, alpha);
    if (alpha < 0.002) discard;
}

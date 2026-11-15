#version 330
in vec2 uv;
in vec4 tint;
out vec4 fragColor;

void main() {
#ifdef STARDUST
    vec2 p = uv * 2.0 - 1.0;
    float r = length(p);
    float halo = exp(-r * r * 7.0) * 0.24;
    float star = pow(max(0.0, 1.0 - sqrt(abs(p.x)) - sqrt(abs(p.y))), 1.25);
    float core = exp(-r * r * 180.0);
    float energy = (halo + star * 0.9 + core) * (1.0 - smoothstep(0.7, 1.0, r));
    float alpha = energy * tint.a;
    fragColor = vec4(mix(tint.rgb, vec3(1.0), core * 0.65) * alpha, alpha);
#else
    float d = abs(uv.y * 2.0 - 1.0);
#ifdef BLOOM
    float energy = exp(-d * d * 6.5) * (1.0 - smoothstep(0.65, 1.0, d));
    float alpha = energy * tint.a * 0.22;
    fragColor = vec4(tint.rgb * alpha, alpha);
#else
    float silk = pow(max(0.0, 1.0 - d), 2.0);
    float filament = exp(-pow((d - 0.31 - sin(uv.x * 23.0) * 0.06) * 28.0, 2.0));
    float core = exp(-d * d * 260.0);
    float shimmer = 0.88 + 0.12 * sin(uv.x * 37.0 + uv.y * 5.0);
    float alpha = (silk * 0.6 * shimmer + filament * 0.2 + core * 0.55) * tint.a;
    vec3 color = mix(tint.rgb, vec3(0.95, 1.0, 1.0), core * 0.58);
    fragColor = vec4(color, alpha);
#endif
#endif
    if (fragColor.a < 0.003) discard;
}

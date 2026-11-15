#version 330

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
in vec3 worldPosition;
in vec3 worldNormal;
in vec4 vertexColor;
in vec2 surfaceUV;
in float deckHeight;
flat in int material;
flat in float animation;
flat in float thrust;
out vec4 fragColor;

float hairline(float coordinate, float width) {
    float d = abs(fract(coordinate + 0.5) - 0.5);
    float aa = max(fwidth(coordinate), 0.0001);
    return 1.0 - smoothstep(width, width + aa, d);
}

float stroke(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a, ba = b - a;
    float d = length(pa - ba * clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0));
    return 1.0 - smoothstep(0.026, 0.026 + max(fwidth(d), 0.002), d);
}

float insignia(vec2 p) {
    float six = stroke(p, vec2(-0.06,0.22), vec2(-0.22,0.22));
    six = max(six, stroke(p, vec2(-0.22,0.22), vec2(-0.24,-0.20)));
    six = max(six, stroke(p, vec2(-0.24,-0.20), vec2(-0.06,-0.20)));
    six = max(six, stroke(p, vec2(-0.06,-0.20), vec2(-0.06,0.0)));
    six = max(six, stroke(p, vec2(-0.06,0.0), vec2(-0.24,0.0)));
    float seven = stroke(p, vec2(0.05,0.22), vec2(0.25,0.22));
    seven = max(seven, stroke(p, vec2(0.25,0.22), vec2(0.08,-0.20)));
    return max(six, seven);
}

void main() {
    vec3 n = normalize(worldNormal);
    vec3 v = normalize(-worldPosition);
    if (material == 4 && dot(n, v) < 0.0) n = -n;
    vec3 key = vec3(-0.36, 0.78, 0.40);
    vec3 l = normalize(v * 0.86 + (key - v * dot(key, v)) * 0.68);
    vec3 h = normalize(l + v);
    float facing = clamp(dot(n, v), 0.0, 1.0);
    float fresnel = pow(1.0 - facing, 5.0);
    vec3 base = vertexColor.rgb;
    float alpha = vertexColor.a;
    float metal = 0.05, roughness = 0.48;

    if (material == 0 || material == 9) {
        float seams = max(hairline(surfaceUV.x * 12.0, 0.005), hairline(surfaceUV.y * 3.8, 0.008));
        base *= 1.0 - seams * 0.16;
        vec2 mark = vec2((surfaceUV.x - 0.25) * 5.8, (surfaceUV.y + 0.32) * 2.5);
        if (material == 0) {
            float heatShield = 1.0 - smoothstep(-0.65, -0.64, surfaceUV.y);
            base = mix(base, vec3(0.10, 0.145, 0.20), heatShield);
            roughness = mix(roughness, 0.72, heatShield);
            float angle = surfaceUV.x * 6.2831853;
            vec2 opening = vec2(cos(angle) * 0.30 / 0.176, (surfaceUV.y - 0.245) / 0.30);
            float inset = (1.0 - smoothstep(0.91, 1.0, length(opening))) * smoothstep(0.1, 0.6, sin(angle));
            base = mix(base, vec3(0.025, 0.060, 0.095), inset);
            base = mix(base, vec3(0.10, 0.16, 0.23), insignia(mark));
        }
    } else if (material == 1) {
        float tiles = max(hairline(surfaceUV.x * 24.0, 0.018), hairline(surfaceUV.y * 18.0, 0.018));
        base *= 1.0 - tiles * 0.35;
        roughness = 0.72;
    } else if (material == 2 || material == 6) {
        metal = 0.82;
        roughness = 0.26;
        float ribs = hairline(surfaceUV.y * 65.0, 0.10);
        base *= 1.0 - ribs * (material == 6 ? 0.32 : 0.06);
    } else if (material == 3) {
        metal = 0.35;
        roughness = 0.30;
    } else if (material == 4) {
        metal = 0.40;
        roughness = 0.09;
        alpha *= 0.28 + 0.55 * fresnel;
    } else if (material == 7) {
        if (deckHeight < 0.29) discard;
        vec4 skin = texture(Sampler0, surfaceUV);
        if (skin.a < 0.01) discard;
        base *= skin.rgb;
        alpha *= skin.a;
        roughness = 0.80;
    }

    float diffuse = max(dot(n, l), 0.0);
    float spec = pow(max(dot(n, h), 0.0), mix(16.0, 180.0, 1.0 - roughness));
    vec3 reflected = reflect(-v, n);
    vec3 environment = mix(vec3(0.06, 0.11, 0.20), vec3(0.68, 0.78, 0.90), smoothstep(-0.4, 0.8, reflected.y));
    vec3 col = base * (0.25 + 0.75 * diffuse);
    col += mix(vec3(0.65, 0.78, 0.92), base, metal) * spec * (0.26 + metal * 0.75);
    col += environment * (0.05 + fresnel * 0.26) * metal;
    if (material == 4) {
        col += environment * (0.15 + 0.65 * fresnel) + vec3(0.65,0.83,1.0) * spec * 1.2;
    } else if (material == 5) {
        col = base * (0.80 + thrust * 0.20);
    } else if (material == 6) {
        col += vec3(0.30, 0.12, 0.045) * thrust * (1.0 - smoothstep(-1.18, -0.91, surfaceUV.y));
    } else if (material == 8) {
        float pulse = pow(0.5 + 0.5 * sin(animation * 4.0), 16.0);
        col = base * (0.45 + 0.55 * pulse);
    }
    alpha *= ColorModulator.a;
    fragColor = vec4(clamp(col * ColorModulator.rgb, 0.0, 1.0) * alpha, alpha);
}

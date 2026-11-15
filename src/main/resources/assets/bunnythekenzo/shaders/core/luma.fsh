#version 330
#moj_import <minecraft:dynamictransforms.glsl>

in vec3 viewPosition;
in vec3 viewNormal;
in vec4 tint;
in vec2 uv;
in float edge;
flat in int surface;
out vec4 fragColor;

void main() {
    vec3 color = tint.rgb;
    float alpha = tint.a;
    if (surface == 0) {
        vec3 n = normalize(viewNormal);
        vec3 v = normalize(-viewPosition);
        float facing = abs(dot(n, v));
        float fresnel = pow(1.0 - facing, 2.3);
        vec3 film = .5 + .5 * cos(vec3(.2, 2.3, 4.4) + (1.0 - facing) * 8.0 + uv.x * 2.8);
        float rim = smoothstep(.83, .985, edge);
        float veinPhase = atan(uv.y, max(.04, uv.x)) * 8.0 + length(uv) * 1.2;
        float vein = 1.0 - smoothstep(.035, .035 + max(.08, fwidth(veinPhase) * 1.2), abs(sin(veinPhase)));
        float softLight = .76 + .24 * abs(dot(n, normalize(vec3(-.4, .8, .6))));
        color *= softLight;
        color = mix(color, film, .18 + fresnel * .20);
        color += vec3(.16, .13, .10) * rim + tint.rgb * vein * .13;
        color = mix(color, vec3(1.0, .97, .91), pow(facing, 12.0) * .15);
        alpha *= .92;
    } else if (surface == 1) {
        vec2 p = uv * 2.0 - 1.0;
        float r2 = dot(p, p);
        alpha *= exp(-r2 * 4.8) * (1.0 - smoothstep(.5, 1.0, r2));
    } else if (surface == 2) {
        float light = .65 + .35 * abs(dot(normalize(viewNormal), normalize(vec3(-.4, .8, .6))));
        color = tint.rgb * light + vec3(.10);
    } else if (surface == 3) {
        alpha *= pow(max(0.0, 1.0 - abs(uv.x * 2.0 - 1.0)), 1.6);
        color = mix(tint.rgb, vec3(1.0), .22);
    } else {
        vec2 p = abs(uv * 2.0 - 1.0);
        float core = exp(-dot(p, p) * 14.0);
        float crossLight = exp(-min(p.x, p.y) * 34.0) * pow(max(0.0, 1.0 - max(p.x, p.y)), 2.0);
        alpha *= max(core, crossLight * .7);
        color = mix(tint.rgb, vec3(1.0), .48);
    }
    alpha *= ColorModulator.a;
    if (alpha < .002) discard;
    fragColor = vec4(clamp(color, 0.0, 1.0) * ColorModulator.rgb * alpha, alpha);
}

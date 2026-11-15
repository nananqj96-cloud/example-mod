#version 330
#moj_import <minecraft:dynamictransforms.glsl>
in vec2 plumeUV;
in vec4 vertexColor;
out vec4 fragColor;
void main() {
    float t = plumeUV.y;
    float diamonds = pow(0.5 + 0.5 * cos(t * 39.0 - 0.7), 7.0);
    float flow = 0.82 + 0.18 * sin(plumeUV.x * 3.0 - t * 21.0);
    float fade = pow(max(0.0, 1.0 - t), 1.6) * smoothstep(0.0, 0.035, t);
    vec3 colour = mix(vec3(0.68, 0.91, 1.0), vertexColor.rgb, smoothstep(0.04, 0.72, t));
    colour = mix(colour, vec3(0.86, 0.97, 1.0), diamonds * 0.65);
    float a = vertexColor.a * fade * flow * (0.65 + diamonds * 0.65) * ColorModulator.a;
    fragColor = vec4(colour * ColorModulator.rgb * a, a);
}

#version 330
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;
out vec3 viewPosition;
out vec3 viewNormal;
out vec4 tint;
out vec2 uv;
out float edge;
flat out int surface;

void main() {
    vec4 view = ModelViewMat * vec4(Position, 1.0);
    gl_Position = ProjMat * view;
    viewPosition = view.xyz;
    viewNormal = mat3(ModelViewMat) * Normal;
    tint = Color;
    uv = UV0;
    edge = float(UV2.x) / 4096.0;
    surface = UV1.x;
}

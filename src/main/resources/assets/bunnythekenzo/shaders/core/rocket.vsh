#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

out vec3 worldPosition;
out vec3 worldNormal;
out vec4 vertexColor;
out vec2 surfaceUV;
out float deckHeight;
flat out int material;
flat out float animation;
flat out float thrust;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    worldPosition = Position;
    worldNormal = Normal;
    vertexColor = Color;
    surfaceUV = UV0;
    material = UV1.x;
    deckHeight = float(UV1.y) / 8192.0;
    animation = float(UV2.x) / 4096.0;
    thrust = float(UV2.y) / 16384.0;
}

#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

out vec3 localPosition;
flat out vec3 sunlight;
flat out vec3 viewer;
flat out float weatherPhase;
flat out float cloudDrift;
out vec4 vertexColor;

vec3 direction(vec2 angles) {
    return vec3(cos(angles.x) * cos(angles.y), sin(angles.y), sin(angles.x) * cos(angles.y));
}

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
#ifdef RINGS
    localPosition = vec3(cos(UV0.x), 0.0, sin(UV0.x)) * UV0.y;
#else
    localPosition = direction(UV0);
#endif
    sunlight = normalize(Normal);
    viewer = direction(vec2(UV1) / 8192.0);
    weatherPhase = float(UV2.x) / 4096.0;
    cloudDrift = float(UV2.y) / 4096.0 + 0.4;
    vertexColor = Color;
}

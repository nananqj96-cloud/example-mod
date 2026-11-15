#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec3 localPosition;
flat in vec3 sunlight;
flat in vec3 viewer;
flat in float weatherPhase;
flat in float cloudDrift;
in vec4 vertexColor;
out vec4 fragColor;

#ifdef ROCK
uniform sampler2D Sampler0;
#endif

float hash(vec3 p) {
    p = fract(p * 0.1031);
    p += dot(p, p.yzx + 33.33);
    return fract((p.x + p.y) * p.z);
}

vec3 hash3(vec3 p) {
    return vec3(hash(p), hash(p + 19.19), hash(p + 47.47));
}

float noise(vec3 p) {
    vec3 i = floor(p), f = fract(p);
    vec3 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(mix(hash(i), hash(i + vec3(1,0,0)), u.x),
                   mix(hash(i + vec3(0,1,0)), hash(i + vec3(1,1,0)), u.x), u.y),
               mix(mix(hash(i + vec3(0,0,1)), hash(i + vec3(1,0,1)), u.x),
                   mix(hash(i + vec3(0,1,1)), hash(i + vec3(1,1,1)), u.x), u.y), u.z);
}

float fbm(vec3 p) {
    float value = 0.0, amplitude = 0.53;
    float footprint = max(length(dFdx(p)), length(dFdy(p)));
    for (int i = 0; i < 5; i++) {
        float weight = 1.0 - smoothstep(0.35, 1.3, footprint);
        // Filtered-out octaves contribute their mean; do not evaluate eight hashes for them.
        float detail = 0.5;
        if (weight > 0.0) detail = noise(p);
        value += amplitude * mix(0.5, detail, weight);
        p = p * 2.03 + vec3(13.1, 7.7, 5.3);
        footprint *= 2.03;
        amplitude *= 0.49;
    }
    return value;
}

float filteredSin(float phase) {
    return sin(phase) * (1.0 - smoothstep(0.8, 3.1, fwidth(phase)));
}

float ringDensity(float radius) {
    float edge = smoothstep(1.30, 1.34, radius) * (1.0 - smoothstep(2.13, 2.18, radius));
    float inner = mix(0.22, 0.90, smoothstep(1.48, 1.57, radius));
    float cassini = 1.0 - smoothstep(1.855, 1.87, radius) * (1.0 - smoothstep(1.915, 1.93, radius));
    float encke = 1.0 - 0.68 * smoothstep(2.04, 2.046, radius) * (1.0 - smoothstep(2.058, 2.065, radius));
    float grain = 0.87 + 0.08 * filteredSin(radius * 360.0) + 0.05 * filteredSin(radius * 790.0);
    return edge * inner * cassini * encke * grain;
}

vec3 gasSurface(vec3 p) {
    float turbulence = fbm(p * vec3(10.0, 17.0, 10.0));
    float latitude = p.y + 0.052 * (turbulence - 0.5);
    latitude += 0.003 * sin(weatherPhase + p.x * 9.0) * cos(p.z * 12.0);
    float broad = 0.5 + 0.5 * filteredSin(latitude * 39.0 + turbulence * 3.8);
    float narrow = 0.5 + 0.5 * filteredSin(latitude * 106.0 + turbulence * 4.0);
    vec3 col = mix(vec3(0.58, 0.40, 0.25), vec3(0.91, 0.80, 0.62), smoothstep(0.12, 0.88, broad));
    col = mix(col, vec3(0.98, 0.91, 0.75), narrow * 0.29);
    col *= 0.88 + 0.24 * turbulence;
    vec3 stormCentre = normalize(vec3(0.80, -0.24, 0.55));
    vec3 east = normalize(cross(vec3(0,1,0), stormCentre));
    vec3 north = cross(stormCentre, east);
    vec2 q = vec2(dot(p, east) / 0.205, dot(p, north) / 0.095);
    float r = length(q);
    float storm = (1.0 - smoothstep(0.82, 1.16, r)) * smoothstep(0.8, 0.95, dot(p, stormCentre));
    float swirl = 0.5 + 0.5 * filteredSin(r * 22.0 - atan(q.y, q.x) * 2.0 + turbulence * 5.0 - weatherPhase * 2.0);
    vec3 spot = mix(vec3(0.52, 0.22, 0.12), vec3(0.90, 0.56, 0.32), swirl);
    return mix(col, spot, storm * 0.88);
}

#ifdef ROCK
float craterRelief(vec3 p) {
    // Static 54-cell crater search baked offline. Decode before relief derivatives;
    // RG stores a linearly filterable 16-bit height, with duplicated seam/pole rows.
    vec2 uv = vec2(atan(p.z, p.x) / 6.28318530718 + 0.5,
                   asin(clamp(p.y, -1.0, 1.0)) / 3.14159265359 + 0.5);
    vec2 dimensions = vec2(textureSize(Sampler0, 0));
    uv = (uv * (dimensions - 1.0) + 0.5) / dimensions;
    vec2 heightRG = texture(Sampler0, uv).rg;
    return dot(heightRG, vec2(65280.0, 255.0)) * (4.0 / 65535.0) - 2.0;
}
#endif

vec3 reliefNormal(vec3 n, float height, float strength) {
    vec3 dp1 = dFdx(localPosition), dp2 = dFdy(localPosition);
    vec3 r1 = cross(dp2, n), r2 = cross(n, dp1);
    float determinant = dot(dp1, r1);
    vec3 gradient = sign(determinant) * (dFdx(height) * r1 + dFdy(height) * r2);
    return normalize(abs(determinant) * n - strength * gradient + n * 0.00000001);
}

vec3 turnLongitude(vec3 p, float angle) {
    float c = cos(angle), s = sin(angle);
    return vec3(p.x * c - p.z * s, p.y, p.x * s + p.z * c);
}

float oceanClouds(vec3 p) {
    vec3 flow = turnLongitude(p, 0.13 * sin(p.y * 9.0 + weatherPhase));
    float wisps = fbm(flow * 17.0 + 31.0);
    float fronts = fbm(flow * vec3(6.0, 10.0, 6.0) + (wisps - 0.5) * 1.8);
    float coverage = smoothstep(0.47, 0.68, fronts + (wisps - 0.5) * 0.22);
    vec3 centre = normalize(vec3(0.45, 0.40, 0.80));
    vec3 east = normalize(cross(vec3(0, 1, 0), centre));
    vec3 north = cross(centre, east);
    vec2 q = vec2(dot(p, east), dot(p, north));
    float r = length(q);
    float stormPatch = (1.0 - smoothstep(0.16, 0.32, r)) * smoothstep(0.90, 0.97, dot(p, centre));
    float arms = 0.5 + 0.5 * filteredSin(atan(q.y, q.x) * 3.0 - r * 51.0 + weatherPhase * 2.0 + wisps * 2.0);
    float eye = smoothstep(0.017, 0.037, r);
    return mix(coverage, (0.28 + 0.70 * smoothstep(0.20, 0.82, arms)) * eye, stormPatch);
}

float plateEdge(vec3 p) {
    vec3 cell = floor(p), fraction = fract(p);
    float first = 20.0, second = 20.0;
    for (int z = -1; z <= 1; z++) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                vec3 offset = vec3(x, y, z);
                vec3 delta = offset + 0.2 + 0.6 * hash3(cell + offset) - fraction;
                float distance2 = dot(delta, delta);
                second = min(second, max(first, distance2));
                first = min(first, distance2);
            }
        }
    }
    return sqrt(second) - sqrt(first);
}

void main() {
    vec3 n = normalize(localPosition);
    vec3 l = normalize(sunlight), v = normalize(viewer);
    float mu = dot(n, l), facing = max(0.0, dot(n, v));
    float alpha = vertexColor.a;
    vec3 col;

#ifdef RINGS
    float radius = length(localPosition);
    float density = ringDensity(radius);
    float along = dot(localPosition, l);
    float miss = length(localPosition - along * l);
    float shadow = (1.0 - smoothstep(0.96, 1.04, miss)) * (1.0 - step(0.0, along));
    col = mix(vec3(0.42, 0.34, 0.26), vec3(0.90, 0.83, 0.68), smoothstep(1.35, 1.80, radius));
    col *= (0.42 + 0.58 * sqrt(abs(l.y))) * (1.0 - shadow * 0.88);
    alpha *= density * 0.90;
#elif defined(HAZE) || defined(ICE_HAZE) || defined(OCEAN_HAZE) || defined(VOLCANIC_HAZE)
    float rim = pow(1.0 - facing, 4.0);
    float day = smoothstep(-0.22, 0.42, mu);
#ifdef OCEAN_HAZE
    float twilight = exp(-abs(mu) * 8.0);
    col = mix(vec3(0.045, 0.16, 0.42), vec3(0.24, 0.60, 1.0), day);
    col = mix(col, vec3(0.98, 0.36, 0.10), twilight * 0.62);
    alpha *= rim * (0.06 + 0.57 * day);
#elif defined(VOLCANIC_HAZE)
    vec3 ashFlow = turnLongitude(n, 0.10 * sin(weatherPhase));
    float ash = fbm(ashFlow * 9.0 + vec3(0.0, sin(weatherPhase) * 0.4, 0.0));
    col = mix(vec3(0.28, 0.065, 0.025), vec3(0.95, 0.34, 0.075), day);
    alpha *= rim * (0.10 + day * 0.19) * (0.65 + ash * 0.45);
#elif defined(ICE_HAZE)
    col = mix(vec3(0.09, 0.23, 0.47), vec3(0.39, 0.74, 1.0), day);
    alpha *= rim * (0.09 + 0.48 * day);
#else
    col = mix(vec3(0.20, 0.24, 0.38), vec3(0.92, 0.73, 0.46), day);
    alpha *= rim * (0.025 + 0.22 * day);
#endif
#elif defined(OCEAN_CLOUDS)
    float coverage = oceanClouds(n);
    alpha *= coverage * 0.92;
    vec3 cloudNormal = reliefNormal(n, coverage, 0.0018);
    float cloudDay = pow(max(dot(cloudNormal, l), 0.0), 0.78);
    col = vec3(0.95, 0.97, 1.0) * (vec3(0.035, 0.05, 0.08) + cloudDay * vec3(1.0, 0.98, 0.92));
    col += vec3(0.24, 0.085, 0.025) * exp(-abs(mu) * 14.0) * 0.25;
#elif defined(CLOUDS)
    vec3 flow = n * vec3(7.0, 12.0, 7.0);
    flow.x += 0.38 * sin(n.y * 13.0 + weatherPhase);
    float clouds = fbm(flow);
    alpha *= smoothstep(0.51, 0.72, clouds) * 0.72;
    col = vec3(0.84, 0.93, 1.0) * (0.09 + 0.91 * pow(max(mu, 0.0), 0.75));
#else
    vec3 albedo;
    vec3 normal = n;
    vec3 emission = vec3(0.0);
#ifdef GAS
    albedo = gasSurface(n);
#elif defined(ROCK)
    float maria = smoothstep(0.39, 0.58, fbm(n * 3.3 + 11.0));
    float terrain = fbm(n * 38.0);
    float impacts = craterRelief(n);
    albedo = mix(vec3(0.30, 0.31, 0.33), vec3(0.68, 0.66, 0.62), maria);
    albedo *= 0.83 + terrain * 0.35 + impacts * 0.19;
    normal = reliefNormal(n, impacts + terrain * 0.16, 0.006);
#elif defined(OCEAN)
    float warp = fbm(n * 5.2 + 19.0);
    float continent = fbm(n * 3.1 + vec3(warp * 1.1, 8.0, 3.0));
    float detail = fbm(n * 42.0 + 7.0);
    float coastWidth = max(fwidth(continent) * 1.4, 0.003);
    float land = smoothstep(0.51 - coastWidth, 0.51 + coastWidth, continent);
    float shallows = smoothstep(0.465, 0.514, continent);
    float mountains = smoothstep(0.52, 0.72, detail) * smoothstep(0.52, 0.60, continent);
    float dry = smoothstep(0.25, 0.55, abs(n.y)) * (1.0 - smoothstep(0.57, 0.72, abs(n.y)));
    vec3 ground = mix(vec3(0.065, 0.22, 0.11), vec3(0.48, 0.38, 0.19), dry * (0.5 + detail * 0.5));
    ground = mix(ground, vec3(0.39, 0.36, 0.30), mountains);
    ground *= 0.76 + detail * 0.52;
    float beach = 1.0 - smoothstep(0.514, 0.527, continent);
    ground = mix(ground, vec3(0.66, 0.61, 0.40), beach * 0.52);
    vec3 water = mix(vec3(0.014, 0.075, 0.22), vec3(0.035, 0.38, 0.43), shallows * 0.78);
    albedo = mix(water, ground, land);
    float caps = smoothstep(0.78, 0.91, abs(n.y) + (detail - 0.5) * 0.14);
    float snow = max(caps, mountains * smoothstep(0.65, 0.77, detail));
    albedo = mix(albedo, vec3(0.88, 0.94, 0.96), snow);
    normal = reliefNormal(n, (detail * 0.28 + mountains) * land, 0.0035);
#elif defined(VOLCANIC)
    float rock = fbm(n * 31.0 + 17.0);
    float warp = fbm(n * 4.8 + 3.0);
    vec3 plates = n * 4.8 + (warp - 0.5) * 1.75;
    float edge = plateEdge(plates);
    float aa = max(fwidth(edge), 0.006);
    float fissure = 1.0 - smoothstep(0.036 - aa * 0.5, 0.036 + aa * 0.5, edge);
    float hotBank = 1.0 - smoothstep(0.05, 0.13 + aa * 0.5, edge);
    float activity = smoothstep(0.38, 0.66, fbm(n * 3.3 + 29.0));
    vec3 flow = n * 19.0 + vec3(sin(weatherPhase), cos(weatherPhase), sin(weatherPhase * 2.0)) * 0.85;
    float current = fbm(flow);
    float heat = smoothstep(0.29, 0.72, current + 0.10 * sin(weatherPhase * 2.0 + n.y * 11.0));
    albedo = mix(vec3(0.13, 0.12, 0.115), vec3(0.44, 0.36, 0.28), rock);
    albedo *= 1.0 - hotBank * 0.38;
    normal = reliefNormal(n, rock * 0.40 + smoothstep(0.02, 0.24, edge) * 0.34, 0.008);
    vec3 molten = mix(vec3(0.78, 0.055, 0.003), vec3(1.0, 0.49, 0.065), heat);
    float exposed = (0.16 + activity * 0.84) * (0.32 + 0.68 * sqrt(facing));
    emission = molten * fissure * (0.52 + heat * 0.65) * exposed;
    emission += vec3(0.28, 0.024, 0.002) * hotBank * (0.20 + heat * 0.22) * exposed;
#else
    float continents = fbm(n * 3.8 + 8.0);
    float frost = fbm(n * 21.0);
    float ice = smoothstep(0.46, 0.58, continents);
    float caps = smoothstep(0.64, 0.85, abs(n.y) + (frost - 0.5) * 0.18);
    albedo = mix(vec3(0.045, 0.18, 0.29), vec3(0.40, 0.67, 0.74), ice);
    albedo = mix(albedo, vec3(0.88, 0.95, 0.97), max(caps, ice * smoothstep(0.49, 0.72, frost)));
    float fractures = 1.0 - smoothstep(0.012, 0.04 + fwidth(frost), abs(frost - 0.49));
    albedo *= 1.0 - fractures * ice * 0.15;
    normal = reliefNormal(n, frost, 0.006);
#endif
    float diffuse = pow(max(dot(normal, l), 0.0), 0.83);
    col = albedo * (vec3(0.025, 0.035, 0.055) + diffuse * vec3(1.04, 1.0, 0.93));
    col += emission;
#ifdef GAS
    if (abs(l.y) > 0.001) {
        float travel = -n.y / l.y;
        float radius = length((n + l * travel).xz);
        float ringShadow = ringDensity(radius) * smoothstep(0.0, 0.025, travel);
        col *= 1.0 - 0.66 * ringShadow;
    }
#endif
#ifdef OCEAN
    vec3 cloudPoint = normalize(n + l * 0.016);
    float shadow = oceanClouds(turnLongitude(cloudPoint, cloudDrift));
    col *= 1.0 - shadow * 0.33 * smoothstep(-0.02, 0.18, mu);
    vec3 halfVector = normalize(l + v);
    float glint = pow(max(dot(n, halfVector), 0.0), 110.0) * max(mu, 0.0);
    float fresnel = 0.025 + 0.40 * pow(1.0 - facing, 5.0);
    col += vec3(0.95, 0.86, 0.66) * glint * (0.65 + fresnel) * (1.0 - land) * (1.0 - caps) * (1.0 - shadow * 0.8);
    col += vec3(0.07, 0.24, 0.55) * pow(1.0 - facing, 3.0) * smoothstep(-0.12, 0.35, mu) * 0.30;
#endif
#ifdef ICE
    vec3 halfVector = normalize(l + v);
    float glint = pow(max(dot(normal, halfVector), 0.0), 70.0) * max(mu, 0.0);
    col += vec3(0.45, 0.68, 0.80) * glint * 0.20;
    col += vec3(0.10, 0.29, 0.48) * pow(1.0 - facing, 3.0) * smoothstep(-0.1, 0.5, mu) * 0.24;
#endif
#endif
    alpha *= ColorModulator.a;
    fragColor = vec4(clamp(col * vertexColor.rgb * ColorModulator.rgb, 0.0, 1.0) * alpha, alpha);
}

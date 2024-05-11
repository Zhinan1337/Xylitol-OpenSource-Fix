//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import org.lwjgl.opengl.*;
import shop.xiaoda.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.client.*;

public class ShaderUtil
{
    private final int programID;
    private String clickgui;
    private String kawaseUpGlow;
    private String gradientround;
    private String glowShader;
    private String chams;
    private String roundRectTexture;
    private String roundRectOutline;
    private String kawaseUpBloom;
    private String bloom;
    private String arc;
    private String kawaseDownBloom;
    private String kawaseUp;
    private String kawaseDown;
    private String gradientMask;
    private String mask;
    private String gradient;
    private String roundedRectGradient;
    private String roundedRect;
    
    public ShaderUtil(final String fragmentShaderLoc, final String vertexShaderLoc) {
        this.clickgui = "#define M_PI 3.1415926535897932384626433832795\n#define M_TWO_PI (2.0 * M_PI)\n\nuniform float iTime;\nuniform vec2 iResolution;\nuniform vec4 iMouse;\nuniform vec2 rectsize;\nfloat rand(vec2 n) {\n    return fract(sin(dot(n, vec2(12.9898,12.1414))) * 83758.5453);\n}\n\nfloat noise(vec2 n) {\n    const vec2 d = vec2(0.0, 1.0);\n    vec2 b = floor(n);\n    vec2 f = smoothstep(vec2(0.0), vec2(1.0), fract(n));\n    return mix(mix(rand(b), rand(b + d.yx), f.x), mix(rand(b + d.xy), rand(b + d.yy), f.x), f.y);\n}\n\nvec3 ramp(float t) {\n    return t <= .5 ? vec3( 1. - t * 1.4, .2, 1.05 ) / t : vec3( .3 * (1. - t) * 2., .2, 1.05 ) / t;\n}\nvec2 polarMap(vec2 uv, float shift, float inner) {\n\n    uv = vec2(0.5) - uv;\n\n\n    float px = 1.0 - fract(atan(uv.y, uv.x) / 6.28 + 0.25) + shift;\n    float py = (sqrt(uv.x * uv.x + uv.y * uv.y) * (1.0 + inner * 2.0) - inner) * 2.0;\n\n    return vec2(px, py);\n}\nfloat fire(vec2 n) {\n    return noise(n) + noise(n * 2.1) * .6 + noise(n * 5.4) * .42;\n}\n\nfloat shade(vec2 uv, float t) {\n    uv.x += uv.y < .5 ? 23.0 + t * .035 : -11.0 + t * .03;\n    uv.y = abs(uv.y - .5);\n    uv.x *= 35.0;\n\n    float q = fire(uv - t * .013) / 2.0;\n    vec2 r = vec2(fire(uv + q / 2.0 + t - uv.x - uv.y), fire(uv + q - t));\n\n    return pow((r.y + r.y) * max(.0, uv.y) + .1, 4.0);\n}\n\nvec3 color(float grad) {\n\n    float m2 = iMouse.z < 0.0001 ? 1.15 : iMouse.y * 3.0 / iResolution.y;\n    grad =sqrt( grad);\n    vec3 color = vec3(1.0 / (pow(vec3(0.5, 0.0, .1) + 2.61, vec3(2.0))));\n    vec3 color2 = color;\n    color = ramp(grad);\n    color /= (m2 + max(vec3(0), color));\n\n    return color;\n\n}\n\nvoid mainImage(out vec4 fragColor, in vec2 fragCoord) {\n\n    float m1 = iMouse.z < 0.0001 ? 3.6 : iMouse.x * 5.0 / iResolution.x;\n\n    float t = iTime;\n    vec2 uv = fragCoord.xy / rectsize.xy;\n    float ff = 1.0 - uv.y;\n    uv.x -= (rectsize.x / rectsize.y - 1.0) / 2.0;\n    vec2 uv2 = uv;\n    uv2.y = 1.0 - uv2.y;\n    uv = polarMap(uv, 1.3, m1);\n    uv2 = polarMap(uv2, 1.9, m1);\n\n    vec3 c1 = color(shade(uv, t)) * ff;\n    vec3 c2 = color(shade(uv2, t)) * (1.0 - ff);\n\n    fragColor = vec4(c1 + c2, 1.0);\n}\n";
        this.kawaseUpGlow = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform bool check;\nuniform float lastPass;\nuniform float exposure;\n\nvoid main() {\n    if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, 1.0 - exp(-result.a * exposure), step(0.0, lastPass)));\n}";
        this.gradientround = "#version 120\n\nuniform vec2 u_size;\nuniform float u_radius;\nuniform vec4 u_first_color;\nuniform vec4 u_second_color;\nuniform int u_direction;\n\nvoid main(void)\n{\n    vec2 tex_coord = gl_TexCoord[0].st;\n    vec4 color = mix(u_first_color, u_second_color, u_direction > 0.0 ? tex_coord.y : tex_coord.x);\n    gl_FragColor = vec4(color.rgb, color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));\n}";
        this.glowShader = "#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform vec3 color;\nuniform bool avoidTexture;\nuniform float exposure, radius;\nuniform float weights[256];\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y == 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerColor = texture2D(textureIn, gl_TexCoord[0].st);\n    innerColor.rgb *= innerColor.a;\n    innerColor *= weights[0];\n    for (float r = 1.0; r <= radius; r++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerColor += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n    }\n\n    gl_FragColor = vec4(innerColor.rgb / innerColor.a, mix(innerColor.a, 1.0 - exp(-innerColor.a * exposure), step(0.0, direction.y)));\n}\n";
        this.chams = "#version 120\n\nuniform sampler2D textureIn;\nuniform vec4 color;\nvoid main() {\n    float alpha = texture2D(textureIn, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(color.rgb, color.a * mix(0.0, alpha, step(0.0, alpha)));\n}\n";
        this.roundRectTexture = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}";
        this.roundRectOutline = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n\n}";
        this.kawaseUpBloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n}";
        this.bloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform float radius;\nuniform float weights[256];\n\n#define offset texelSize * direction\n\nfloat smoothAlpha(sampler2D tex, vec2 uv, float threshold, float smoothing) {\n    float alpha = texture2D(tex, uv).a;\n    float edge0 = threshold * (1.0 - smoothing);\n    float edge1 = threshold * (1.0 + smoothing);\n    alpha = smoothstep(edge0, edge1, alpha);\n    return alpha;\n}\n\nvoid main() {\n    if (direction.y > 0 && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    float blr = smoothAlpha(inTexture, gl_TexCoord[0].st, 0.5, 0.5) * weights[0];\n\n    for (float f = 1.0; f <= radius; f++) {\n        blr += smoothAlpha(inTexture, gl_TexCoord[0].st + f * offset, 0.5, 0.5) * (weights[int(abs(f))]);\n        blr += smoothAlpha(inTexture, gl_TexCoord[0].st - f * offset, 0.5, 0.5) * (weights[int(abs(f))]);\n    }\n\n    gl_FragColor = vec4(0.0, 0.0, 0.0, blr);\n}\n";
        this.arc = "#version 120\n\n#define PI 3.14159265359\n\nuniform float radialSmoothness, radius, borderThickness, progress;\nuniform int change;\nuniform vec4 color;\nuniform vec2 pos;\n\nvoid main() {\n    vec2 st = gl_FragCoord.xy - (pos + radius + borderThickness);\n  //  vec2 rp = st * 2. - 1.;\n\n    float circle = sqrt(dot(st,st));\n\n    //Radius minus circle to get just the outline\n    float smoothedAlpha = 1.0 - smoothstep(borderThickness, borderThickness + 3., abs(radius-circle));\n    vec4 circleColor = vec4(color.rgb, smoothedAlpha * color.a);\n\n    gl_FragColor = mix(vec4(circleColor.rgb, 0.0), circleColor, smoothstep(0., radialSmoothness, change * (atan(st.y,st.x) - (progress-.5) * PI * 2.5)));\n}";
        this.kawaseDownBloom = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);\n    sum.rgb *= sum.a;\n    sum *= 4.0;\n    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);\n    smp1.rgb *= smp1.a;\n    sum += smp1;\n    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3;\n    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 result = sum / 8.0;\n    gl_FragColor = vec4(result.rgb / result.a, result.a);\n}";
        this.kawaseUp = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;\n\n    gl_FragColor = vec4(sum.rgb /12.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));\n}\n";
        this.kawaseDown = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;\n    sum += texture2D(inTexture, uv - halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    gl_FragColor = vec4(sum.rgb * .125, 1.0);\n}\n";
        this.gradientMask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec3 color1, color2, color3, color4;\nuniform float alpha;\n\n#define NOISE .5/255.0\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4), texColorAlpha * alpha);\n}";
        this.mask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D u_texture, u_texture2;\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(u_texture, gl_TexCoord[0].st).a;\n    vec3 tex2Color = texture2D(u_texture2, gl_TexCoord[0].st).rgb;\n    gl_FragColor = vec4(tex2Color, texColorAlpha);\n}";
        this.gradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec4 color1, color2, color3, color4;\n#define NOISE .5/255.0\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    gl_FragColor = createGradient(coords, color1, color2, color3, color4);\n}";
        this.roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n   // use the bottom leftColor as the alpha\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}";
        this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
        final int program = GL20.glCreateProgram();
        try {
            int fragmentShaderID = 0;
            switch (fragmentShaderLoc) {
                case "kawaseUpGlow": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.kawaseUpGlow.getBytes()), 35632);
                    break;
                }
                case "glow": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.glowShader.getBytes()), 35632);
                    break;
                }
                case "chams": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.chams.getBytes()), 35632);
                    break;
                }
                case "roundRectTexture": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.roundRectTexture.getBytes()), 35632);
                    break;
                }
                case "roundRectOutline": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.roundRectOutline.getBytes()), 35632);
                    break;
                }
                case "kawaseUpBloom": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.kawaseUpBloom.getBytes()), 35632);
                    break;
                }
                case "kawaseDownBloom": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.kawaseDownBloom.getBytes()), 35632);
                    break;
                }
                case "kawaseUp": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.kawaseUp.getBytes()), 35632);
                    break;
                }
                case "kawaseDown": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.kawaseDown.getBytes()), 35632);
                    break;
                }
                case "gradientMask": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.gradientMask.getBytes()), 35632);
                    break;
                }
                case "mask": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.mask.getBytes()), 35632);
                    break;
                }
                case "gradientround": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.gradientround.getBytes()), 35632);
                    break;
                }
                case "gradient": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.gradient.getBytes()), 35632);
                    break;
                }
                case "clickgui": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.clickgui.getBytes()), 35632);
                    break;
                }
                case "roundedRect": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.roundedRect.getBytes()), 35632);
                    break;
                }
                case "roundedRectGradient": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.roundedRectGradient.getBytes()), 35632);
                    break;
                }
                case "arc": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.arc.getBytes()), 35632);
                    break;
                }
                case "bloom": {
                    fragmentShaderID = this.createShader(new ByteArrayInputStream(this.bloom.getBytes()), 35632);
                    break;
                }
                default: {
                    fragmentShaderID = this.createShader(Client.mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 35632);
                    break;
                }
            }
            GL20.glAttachShader(program, fragmentShaderID);
            final int vertexShaderID = this.createShader(Client.mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 35633);
            GL20.glAttachShader(program, vertexShaderID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glLinkProgram(program);
        final int status = GL20.glGetProgrami(program, 35714);
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }
    
    public ShaderUtil(final String fragmentShadersrc, final boolean notUsed) {
        this.clickgui = "#define M_PI 3.1415926535897932384626433832795\n#define M_TWO_PI (2.0 * M_PI)\n\nuniform float iTime;\nuniform vec2 iResolution;\nuniform vec4 iMouse;\nuniform vec2 rectsize;\nfloat rand(vec2 n) {\n    return fract(sin(dot(n, vec2(12.9898,12.1414))) * 83758.5453);\n}\n\nfloat noise(vec2 n) {\n    const vec2 d = vec2(0.0, 1.0);\n    vec2 b = floor(n);\n    vec2 f = smoothstep(vec2(0.0), vec2(1.0), fract(n));\n    return mix(mix(rand(b), rand(b + d.yx), f.x), mix(rand(b + d.xy), rand(b + d.yy), f.x), f.y);\n}\n\nvec3 ramp(float t) {\n    return t <= .5 ? vec3( 1. - t * 1.4, .2, 1.05 ) / t : vec3( .3 * (1. - t) * 2., .2, 1.05 ) / t;\n}\nvec2 polarMap(vec2 uv, float shift, float inner) {\n\n    uv = vec2(0.5) - uv;\n\n\n    float px = 1.0 - fract(atan(uv.y, uv.x) / 6.28 + 0.25) + shift;\n    float py = (sqrt(uv.x * uv.x + uv.y * uv.y) * (1.0 + inner * 2.0) - inner) * 2.0;\n\n    return vec2(px, py);\n}\nfloat fire(vec2 n) {\n    return noise(n) + noise(n * 2.1) * .6 + noise(n * 5.4) * .42;\n}\n\nfloat shade(vec2 uv, float t) {\n    uv.x += uv.y < .5 ? 23.0 + t * .035 : -11.0 + t * .03;\n    uv.y = abs(uv.y - .5);\n    uv.x *= 35.0;\n\n    float q = fire(uv - t * .013) / 2.0;\n    vec2 r = vec2(fire(uv + q / 2.0 + t - uv.x - uv.y), fire(uv + q - t));\n\n    return pow((r.y + r.y) * max(.0, uv.y) + .1, 4.0);\n}\n\nvec3 color(float grad) {\n\n    float m2 = iMouse.z < 0.0001 ? 1.15 : iMouse.y * 3.0 / iResolution.y;\n    grad =sqrt( grad);\n    vec3 color = vec3(1.0 / (pow(vec3(0.5, 0.0, .1) + 2.61, vec3(2.0))));\n    vec3 color2 = color;\n    color = ramp(grad);\n    color /= (m2 + max(vec3(0), color));\n\n    return color;\n\n}\n\nvoid mainImage(out vec4 fragColor, in vec2 fragCoord) {\n\n    float m1 = iMouse.z < 0.0001 ? 3.6 : iMouse.x * 5.0 / iResolution.x;\n\n    float t = iTime;\n    vec2 uv = fragCoord.xy / rectsize.xy;\n    float ff = 1.0 - uv.y;\n    uv.x -= (rectsize.x / rectsize.y - 1.0) / 2.0;\n    vec2 uv2 = uv;\n    uv2.y = 1.0 - uv2.y;\n    uv = polarMap(uv, 1.3, m1);\n    uv2 = polarMap(uv2, 1.9, m1);\n\n    vec3 c1 = color(shade(uv, t)) * ff;\n    vec3 c2 = color(shade(uv2, t)) * (1.0 - ff);\n\n    fragColor = vec4(c1 + c2, 1.0);\n}\n";
        this.kawaseUpGlow = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform bool check;\nuniform float lastPass;\nuniform float exposure;\n\nvoid main() {\n    if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, 1.0 - exp(-result.a * exposure), step(0.0, lastPass)));\n}";
        this.gradientround = "#version 120\n\nuniform vec2 u_size;\nuniform float u_radius;\nuniform vec4 u_first_color;\nuniform vec4 u_second_color;\nuniform int u_direction;\n\nvoid main(void)\n{\n    vec2 tex_coord = gl_TexCoord[0].st;\n    vec4 color = mix(u_first_color, u_second_color, u_direction > 0.0 ? tex_coord.y : tex_coord.x);\n    gl_FragColor = vec4(color.rgb, color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));\n}";
        this.glowShader = "#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform vec3 color;\nuniform bool avoidTexture;\nuniform float exposure, radius;\nuniform float weights[256];\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y == 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerColor = texture2D(textureIn, gl_TexCoord[0].st);\n    innerColor.rgb *= innerColor.a;\n    innerColor *= weights[0];\n    for (float r = 1.0; r <= radius; r++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerColor += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n    }\n\n    gl_FragColor = vec4(innerColor.rgb / innerColor.a, mix(innerColor.a, 1.0 - exp(-innerColor.a * exposure), step(0.0, direction.y)));\n}\n";
        this.chams = "#version 120\n\nuniform sampler2D textureIn;\nuniform vec4 color;\nvoid main() {\n    float alpha = texture2D(textureIn, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(color.rgb, color.a * mix(0.0, alpha, step(0.0, alpha)));\n}\n";
        this.roundRectTexture = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}";
        this.roundRectOutline = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n\n}";
        this.kawaseUpBloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n}";
        this.bloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform float radius;\nuniform float weights[256];\n\n#define offset texelSize * direction\n\nfloat smoothAlpha(sampler2D tex, vec2 uv, float threshold, float smoothing) {\n    float alpha = texture2D(tex, uv).a;\n    float edge0 = threshold * (1.0 - smoothing);\n    float edge1 = threshold * (1.0 + smoothing);\n    alpha = smoothstep(edge0, edge1, alpha);\n    return alpha;\n}\n\nvoid main() {\n    if (direction.y > 0 && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    float blr = smoothAlpha(inTexture, gl_TexCoord[0].st, 0.5, 0.5) * weights[0];\n\n    for (float f = 1.0; f <= radius; f++) {\n        blr += smoothAlpha(inTexture, gl_TexCoord[0].st + f * offset, 0.5, 0.5) * (weights[int(abs(f))]);\n        blr += smoothAlpha(inTexture, gl_TexCoord[0].st - f * offset, 0.5, 0.5) * (weights[int(abs(f))]);\n    }\n\n    gl_FragColor = vec4(0.0, 0.0, 0.0, blr);\n}\n";
        this.arc = "#version 120\n\n#define PI 3.14159265359\n\nuniform float radialSmoothness, radius, borderThickness, progress;\nuniform int change;\nuniform vec4 color;\nuniform vec2 pos;\n\nvoid main() {\n    vec2 st = gl_FragCoord.xy - (pos + radius + borderThickness);\n  //  vec2 rp = st * 2. - 1.;\n\n    float circle = sqrt(dot(st,st));\n\n    //Radius minus circle to get just the outline\n    float smoothedAlpha = 1.0 - smoothstep(borderThickness, borderThickness + 3., abs(radius-circle));\n    vec4 circleColor = vec4(color.rgb, smoothedAlpha * color.a);\n\n    gl_FragColor = mix(vec4(circleColor.rgb, 0.0), circleColor, smoothstep(0., radialSmoothness, change * (atan(st.y,st.x) - (progress-.5) * PI * 2.5)));\n}";
        this.kawaseDownBloom = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);\n    sum.rgb *= sum.a;\n    sum *= 4.0;\n    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);\n    smp1.rgb *= smp1.a;\n    sum += smp1;\n    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3;\n    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 result = sum / 8.0;\n    gl_FragColor = vec4(result.rgb / result.a, result.a);\n}";
        this.kawaseUp = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;\n\n    gl_FragColor = vec4(sum.rgb /12.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));\n}\n";
        this.kawaseDown = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;\n    sum += texture2D(inTexture, uv - halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    gl_FragColor = vec4(sum.rgb * .125, 1.0);\n}\n";
        this.gradientMask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec3 color1, color2, color3, color4;\nuniform float alpha;\n\n#define NOISE .5/255.0\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4), texColorAlpha * alpha);\n}";
        this.mask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D u_texture, u_texture2;\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(u_texture, gl_TexCoord[0].st).a;\n    vec3 tex2Color = texture2D(u_texture2, gl_TexCoord[0].st).rgb;\n    gl_FragColor = vec4(tex2Color, texColorAlpha);\n}";
        this.gradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec4 color1, color2, color3, color4;\n#define NOISE .5/255.0\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    gl_FragColor = createGradient(coords, color1, color2, color3, color4);\n}";
        this.roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n   // use the bottom leftColor as the alpha\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}";
        this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
        final int program = GL20.glCreateProgram();
        final int fragmentShaderID = this.createShader(new ByteArrayInputStream(fragmentShadersrc.getBytes()), 35632);
        int vertexShaderID = 0;
        try {
            vertexShaderID = this.createShader(Client.mc.getResourceManager().getResource(new ResourceLocation("express/shader/vertex.vsh")).getInputStream(), 35633);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        GL20.glAttachShader(program, fragmentShaderID);
        GL20.glAttachShader(program, vertexShaderID);
        GL20.glLinkProgram(program);
        final int status = GL20.glGetProgrami(program, 35714);
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }
    
    public ShaderUtil(final String fragmentShaderLoc) {
        this(fragmentShaderLoc, "express/shader/vertex.vsh");
    }
    
    public void init() {
        GL20.glUseProgram(this.programID);
    }
    
    public void unload() {
        GL20.glUseProgram(0);
    }
    
    public int getUniform(final String name) {
        return GL20.glGetUniformLocation(this.programID, (CharSequence)name);
    }
    
    public void setUniformf(final String name, final float... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, (CharSequence)name);
        switch (args.length) {
            case 1: {
                GL20.glUniform1f(loc, args[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
            }
        }
    }
    
    public void setUniformi(final String name, final int... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, (CharSequence)name);
        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        }
        else {
            GL20.glUniform1i(loc, args[0]);
        }
    }
    
    public static void drawQuads(final float x, final float y, final float width, final float height) {
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(x, y);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(x, y + height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(x + width, y + height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(x + width, y);
        GL20.glEnd();
    }
    
    public static void drawQuads() {
        final ScaledResolution sr = new ScaledResolution(Client.mc);
        final float width = (float)sr.getScaledWidth_double();
        final float height = (float)sr.getScaledHeight_double();
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(0.0f, 0.0f);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(0.0f, height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(width, height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(width, 0.0f);
        GL20.glEnd();
    }
    
    public static void drawQuads(final float width, final float height) {
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(0.0f, 0.0f);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(0.0f, height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(width, height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(width, 0.0f);
        GL20.glEnd();
    }
    
    private int createShader(final InputStream inputStream, final int shaderType) {
        final int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, (CharSequence)FileUtil.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }
}

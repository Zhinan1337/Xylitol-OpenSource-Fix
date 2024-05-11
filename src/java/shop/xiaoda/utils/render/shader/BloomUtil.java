//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.shader;

import net.minecraft.client.shader.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class BloomUtil
{
    public static ShaderUtil gaussianBloom;
    public static Framebuffer framebuffer;
    
    public static void renderBlur(final int sourceTexture, final int radius, final int offset) {
        BloomUtil.framebuffer = RenderUtil.createFrameBuffer(BloomUtil.framebuffer);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; ++i) {
            weightBuffer.put(MathUtil.calculateGaussianValue((float)i, (float)radius));
        }
        weightBuffer.rewind();
        RenderUtil.setAlphaLimit(0.0f);
        BloomUtil.framebuffer.framebufferClear();
        BloomUtil.framebuffer.bindFramebuffer(true);
        BloomUtil.gaussianBloom.init();
        setupUniforms(radius, offset, 0, weightBuffer);
        RenderUtil.bindTexture(sourceTexture);
        ShaderUtil.drawQuads();
        BloomUtil.gaussianBloom.unload();
        BloomUtil.framebuffer.unbindFramebuffer();
        Client.mc.getFramebuffer().bindFramebuffer(true);
        BloomUtil.gaussianBloom.init();
        setupUniforms(radius, 0, offset, weightBuffer);
        GL13.glActiveTexture(34000);
        RenderUtil.bindTexture(sourceTexture);
        GL13.glActiveTexture(33984);
        RenderUtil.bindTexture(BloomUtil.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        BloomUtil.gaussianBloom.unload();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.bindTexture(0);
    }
    
    public static void setupUniforms(final int radius, final int directionX, final int directionY, final FloatBuffer weights) {
        BloomUtil.gaussianBloom.setUniformi("inTexture", 0);
        BloomUtil.gaussianBloom.setUniformi("textureToCheck", 16);
        BloomUtil.gaussianBloom.setUniformf("radius", (float)radius);
        BloomUtil.gaussianBloom.setUniformf("texelSize", 1.0f / Client.mc.displayWidth, 1.0f / Client.mc.displayHeight);
        BloomUtil.gaussianBloom.setUniformf("direction", (float)directionX, (float)directionY);
        GL20.glUniform1fv(BloomUtil.gaussianBloom.getUniform("weights"), weights);
    }
    
    static {
        BloomUtil.gaussianBloom = new ShaderUtil("express/shader/fragment/bloom.frag");
        BloomUtil.framebuffer = new Framebuffer(1, 1, false);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.shader;

import net.minecraft.client.shader.*;
import shop.xiaoda.*;
import org.lwjgl.*;
import shop.xiaoda.utils.client.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.client.renderer.*;

public class GaussianBlur
{
    private static final ShaderUtil gaussianBlur;
    private static Framebuffer framebuffer;
    
    private static void setupUniforms(final float dir1, final float dir2, final float radius) {
        GaussianBlur.gaussianBlur.setUniformi("textureIn", 0);
        GaussianBlur.gaussianBlur.setUniformf("texelSize", 1.0f / Client.mc.displayWidth, 1.0f / Client.mc.displayHeight);
        GaussianBlur.gaussianBlur.setUniformf("direction", dir1, dir2);
        GaussianBlur.gaussianBlur.setUniformf("radius", radius);
        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; ++i) {
            weightBuffer.put(MathUtil.calculateGaussianValue((float)i, radius / 2.0f));
        }
        weightBuffer.rewind();
        GL20.glUniform1fv(GaussianBlur.gaussianBlur.getUniform("weights"), weightBuffer);
    }
    
    public static void startBlur() {
        StencilUtil.initStencilToWrite();
    }
    
    public static void endBlur(final float radius, final float compression) {
        StencilUtil.readStencilBuffer(1);
        (GaussianBlur.framebuffer = RenderUtil.createFrameBuffer(GaussianBlur.framebuffer)).framebufferClear();
        GaussianBlur.framebuffer.bindFramebuffer(false);
        GaussianBlur.gaussianBlur.init();
        setupUniforms(compression, 0.0f, radius);
        RenderUtil.bindTexture(Client.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        GaussianBlur.framebuffer.unbindFramebuffer();
        GaussianBlur.gaussianBlur.unload();
        Client.mc.getFramebuffer().bindFramebuffer(false);
        GaussianBlur.gaussianBlur.init();
        setupUniforms(0.0f, compression, radius);
        RenderUtil.bindTexture(GaussianBlur.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        GaussianBlur.gaussianBlur.unload();
        StencilUtil.uninitStencilBuffer();
        RenderUtil.resetColor();
        GlStateManager.bindTexture(0);
    }
    
    static {
        gaussianBlur = new ShaderUtil("Tenacity/Shaders/gaussian.frag");
        GaussianBlur.framebuffer = new Framebuffer(1, 1, false);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.shader;

import net.minecraft.client.shader.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class KawaseBlur
{
    public static ShaderUtil kawaseDown;
    public static ShaderUtil kawaseUp;
    public static Framebuffer framebuffer;
    private static int currentIterations;
    private static final List<Framebuffer> framebufferList;
    
    public static void setupUniforms(final float offset) {
        KawaseBlur.kawaseDown.setUniformf("offset", offset, offset);
        KawaseBlur.kawaseUp.setUniformf("offset", offset, offset);
    }
    
    private static void initFramebuffers(final float iterations) {
        for (final Framebuffer framebuffer : KawaseBlur.framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        KawaseBlur.framebufferList.clear();
        KawaseBlur.framebufferList.add(KawaseBlur.framebuffer = RenderUtil.createFrameBuffer((Framebuffer)null));
        for (int i = 1; i <= iterations; ++i) {
            final Framebuffer currentBuffer = new Framebuffer((int)(Client.mc.displayWidth / Math.pow(2.0, i)), (int)(Client.mc.displayHeight / Math.pow(2.0, i)), false);
            currentBuffer.setFramebufferFilter(9729);
            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(3553, 10242, 33648);
            GL11.glTexParameteri(3553, 10243, 33648);
            GlStateManager.bindTexture(0);
            KawaseBlur.framebufferList.add(currentBuffer);
        }
    }
    
    public static void renderBlur(final int stencilFrameBufferTexture, final int iterations, final int offset) {
        if (KawaseBlur.currentIterations != iterations || KawaseBlur.framebuffer.framebufferWidth != Client.mc.displayWidth || KawaseBlur.framebuffer.framebufferHeight != Client.mc.displayHeight) {
            initFramebuffers((float)iterations);
            KawaseBlur.currentIterations = iterations;
        }
        renderFBO(KawaseBlur.framebufferList.get(1), Client.mc.getFramebuffer().framebufferTexture, KawaseBlur.kawaseDown, (float)offset);
        for (int i = 1; i < iterations; ++i) {
            renderFBO(KawaseBlur.framebufferList.get(i + 1), KawaseBlur.framebufferList.get(i).framebufferTexture, KawaseBlur.kawaseDown, (float)offset);
        }
        for (int i = iterations; i > 1; --i) {
            renderFBO(KawaseBlur.framebufferList.get(i - 1), KawaseBlur.framebufferList.get(i).framebufferTexture, KawaseBlur.kawaseUp, (float)offset);
        }
        final Framebuffer lastBuffer = KawaseBlur.framebufferList.get(0);
        lastBuffer.framebufferClear();
        lastBuffer.bindFramebuffer(false);
        KawaseBlur.kawaseUp.init();
        KawaseBlur.kawaseUp.setUniformf("offset", (float)offset, (float)offset);
        KawaseBlur.kawaseUp.setUniformi("inTexture", 0);
        KawaseBlur.kawaseUp.setUniformi("check", 1);
        KawaseBlur.kawaseUp.setUniformi("textureToCheck", 16);
        KawaseBlur.kawaseUp.setUniformf("halfpixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
        KawaseBlur.kawaseUp.setUniformf("iResolution", (float)lastBuffer.framebufferWidth, (float)lastBuffer.framebufferHeight);
        GL13.glActiveTexture(34000);
        RenderUtil.bindTexture(stencilFrameBufferTexture);
        GL13.glActiveTexture(33984);
        RenderUtil.bindTexture(KawaseBlur.framebufferList.get(1).framebufferTexture);
        ShaderUtil.drawQuads();
        KawaseBlur.kawaseUp.unload();
        Client.mc.getFramebuffer().bindFramebuffer(true);
        RenderUtil.bindTexture(KawaseBlur.framebufferList.get(0).framebufferTexture);
        RenderUtil.setAlphaLimit(0.0f);
        RenderUtil.startBlend();
        ShaderUtil.drawQuads();
        GlStateManager.bindTexture(0);
    }
    
    private static void renderFBO(final Framebuffer framebuffer, final int framebufferTexture, final ShaderUtil shader, final float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);
        shader.init();
        RenderUtil.bindTexture(framebufferTexture);
        shader.setUniformf("offset", offset, offset);
        shader.setUniformi("inTexture", 0);
        shader.setUniformi("check", 0);
        shader.setUniformf("halfpixel", 1.0f / framebuffer.framebufferWidth, 1.0f / framebuffer.framebufferHeight);
        shader.setUniformf("iResolution", (float)framebuffer.framebufferWidth, (float)framebuffer.framebufferHeight);
        ShaderUtil.drawQuads();
        shader.unload();
    }
    
    static {
        KawaseBlur.kawaseDown = new ShaderUtil("kawaseDown");
        KawaseBlur.kawaseUp = new ShaderUtil("kawaseUp");
        KawaseBlur.framebuffer = new Framebuffer(1, 1, false);
        framebufferList = new ArrayList<Framebuffer>();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.shader;

import net.minecraft.client.shader.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class KawaseBloom
{
    public static ShaderUtil kawaseDown;
    public static ShaderUtil kawaseUp;
    public static Framebuffer framebuffer;
    public static Framebuffer stencilFramebuffer;
    private static int currentIterations;
    private static final List<Framebuffer> framebufferList;
    
    private static void initFramebuffers(final float iterations) {
        for (final Framebuffer framebuffer : KawaseBloom.framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        KawaseBloom.framebufferList.clear();
        KawaseBloom.framebufferList.add(KawaseBloom.framebuffer = RenderUtil.createFrameBuffer((Framebuffer)null, true));
        for (int i = 1; i <= iterations; ++i) {
            final Framebuffer currentBuffer = new Framebuffer((int)(Client.mc.displayWidth / Math.pow(2.0, i)), (int)(Client.mc.displayHeight / Math.pow(2.0, i)), true);
            currentBuffer.setFramebufferFilter(9729);
            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(3553, 10242, 33648);
            GL11.glTexParameteri(3553, 10243, 33648);
            GlStateManager.bindTexture(0);
            KawaseBloom.framebufferList.add(currentBuffer);
        }
    }
    
    public static void shadow2(final Runnable drawMod) {
        (KawaseBloom.stencilFramebuffer = RenderUtil.createFrameBuffer(KawaseBloom.stencilFramebuffer)).framebufferClear();
        KawaseBloom.stencilFramebuffer.bindFramebuffer(false);
        drawMod.run();
        KawaseBloom.stencilFramebuffer.unbindFramebuffer();
        renderBlur(KawaseBloom.stencilFramebuffer.framebufferTexture, 4, 4);
    }
    
    public static void shadow(final Runnable drawMod) {
        (KawaseBloom.stencilFramebuffer = RenderUtil.createFrameBuffer(KawaseBloom.stencilFramebuffer)).framebufferClear();
        KawaseBloom.stencilFramebuffer.bindFramebuffer(false);
        drawMod.run();
        KawaseBloom.stencilFramebuffer.unbindFramebuffer();
        renderBlur(KawaseBloom.stencilFramebuffer.framebufferTexture, 2, 3);
    }
    
    public static void renderBlur(final int framebufferTexture, final int iterations, final int offset) {
        if (KawaseBloom.currentIterations != iterations || KawaseBloom.framebuffer.framebufferWidth != Client.mc.displayWidth || KawaseBloom.framebuffer.framebufferHeight != Client.mc.displayHeight) {
            initFramebuffers((float)iterations);
            KawaseBloom.currentIterations = iterations;
        }
        RenderUtil.setAlphaLimit(0.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 1);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        renderFBO(KawaseBloom.framebufferList.get(1), framebufferTexture, KawaseBloom.kawaseDown, (float)offset);
        for (int i = 1; i < iterations; ++i) {
            renderFBO(KawaseBloom.framebufferList.get(i + 1), KawaseBloom.framebufferList.get(i).framebufferTexture, KawaseBloom.kawaseDown, (float)offset);
        }
        for (int i = iterations; i > 1; --i) {
            renderFBO(KawaseBloom.framebufferList.get(i - 1), KawaseBloom.framebufferList.get(i).framebufferTexture, KawaseBloom.kawaseUp, (float)offset);
        }
        final Framebuffer lastBuffer = KawaseBloom.framebufferList.get(0);
        lastBuffer.framebufferClear();
        lastBuffer.bindFramebuffer(false);
        KawaseBloom.kawaseUp.init();
        KawaseBloom.kawaseUp.setUniformf("offset", (float)offset, (float)offset);
        KawaseBloom.kawaseUp.setUniformi("inTexture", 0);
        KawaseBloom.kawaseUp.setUniformi("check", 1);
        KawaseBloom.kawaseUp.setUniformi("textureToCheck", 16);
        KawaseBloom.kawaseUp.setUniformf("halfpixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
        KawaseBloom.kawaseUp.setUniformf("iResolution", (float)lastBuffer.framebufferWidth, (float)lastBuffer.framebufferHeight);
        GlStateManager.setActiveTexture(34000);
        RenderUtil.bindTexture(framebufferTexture);
        GlStateManager.setActiveTexture(33984);
        RenderUtil.bindTexture(KawaseBloom.framebufferList.get(1).framebufferTexture);
        ShaderUtil.drawQuads();
        KawaseBloom.kawaseUp.unload();
        GlStateManager.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Client.mc.getFramebuffer().bindFramebuffer(false);
        RenderUtil.bindTexture(KawaseBloom.framebufferList.get(0).framebufferTexture);
        RenderUtil.setAlphaLimit(0.0f);
        RenderUtil.startBlend();
        ShaderUtil.drawQuads();
        GlStateManager.bindTexture(0);
        RenderUtil.setAlphaLimit(0.0f);
        RenderUtil.startBlend();
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
        KawaseBloom.kawaseDown = new ShaderUtil("kawaseDownBloom");
        KawaseBloom.kawaseUp = new ShaderUtil("kawaseUpBloom");
        KawaseBloom.framebuffer = new Framebuffer(1, 1, true);
        KawaseBloom.stencilFramebuffer = new Framebuffer(1, 1, false);
        framebufferList = new ArrayList<Framebuffer>();
    }
}

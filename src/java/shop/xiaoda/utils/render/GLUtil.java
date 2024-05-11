//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class GLUtil
{
    public static int[] enabledCaps;
    
    public static void startBlend() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
    }
    
    public static void setupRendering(final int mode, final Runnable runnable) {
        GlStateManager.glBegin(mode);
        runnable.run();
        GlStateManager.glEnd();
    }
    
    public static void enableDepth() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
    }
    
    public static void disableCaps() {
        for (final int cap : GLUtil.enabledCaps) {
            GL11.glDisable(cap);
        }
    }
    
    public static void enableCaps(final int... caps) {
        for (final int cap : caps) {
            GL11.glEnable(cap);
        }
        GLUtil.enabledCaps = caps;
    }
    
    public static void enableTexture2D() {
        GL11.glEnable(3553);
    }
    
    public static void disableTexture2D() {
        GL11.glDisable(3553);
    }
    
    public static void enableBlending() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }
    
    public static void disableDepth() {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
    }
    
    public static void disableBlending() {
        GL11.glDisable(3042);
    }
    
    public static void endBlend() {
        GlStateManager.disableBlend();
    }
    
    public static void render(final int mode, final Runnable render) {
        GlStateManager.glBegin(mode);
        render.run();
        GlStateManager.glEnd();
    }
    
    public static void setup2DRendering(final Runnable f) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        f.run();
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }
    
    public static void setup2DRendering() {
        setup2DRendering(true);
    }
    
    public static void setup2DRendering(final boolean blend) {
        if (blend) {
            startBlend();
        }
        GlStateManager.disableTexture2D();
    }
    
    public static void end2DRendering() {
        GlStateManager.enableTexture2D();
        endBlend();
    }
    
    public static void rotate(final float x, final float y, final float rotate, final Runnable f) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, -1.0f);
        GlStateManager.translate(-x, -y, 0.0f);
        f.run();
        GlStateManager.popMatrix();
    }
    
    static {
        GLUtil.enabledCaps = new int[32];
    }
}

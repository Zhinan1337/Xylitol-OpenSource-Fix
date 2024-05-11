//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.*;

public final class SplashScreen
{
    private static final int DEFAULT_MAX = 14;
    private static int PROGRESS;
    private static String CURRENT;
    private static TextureManager ctm;
    private static float animated;
    static float hue2;
    
    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
            return;
        }
        drawSplash(Minecraft.getMinecraft().getTextureManager());
    }
    
    public static void setProgress(final int givenProgress, final String givenSplash) {
        SplashScreen.PROGRESS = givenProgress;
        SplashScreen.CURRENT = givenSplash;
        update();
    }
    
    public static void drawSplash(final TextureManager tm) {
        if (SplashScreen.ctm == null) {
            SplashScreen.ctm = tm;
        }
        final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        final int scaleFactor = scaledresolution.getScaleFactor();
        final Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * scaleFactor, scaledresolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, (double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        Gui.drawRect(0.0, 0.0, (double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), new Color(32, 32, 32).getRGB());
        final float h_ = SplashScreen.hue2;
        final float h2 = SplashScreen.hue2 + 85.0f;
        SplashScreen.hue2 += 0.5;
        final Color c = Color.getHSBColor(h_ / 255.0f, 0.9f, 1.0f);
        final Color c2 = Color.getHSBColor(h2 / 255.0f, 0.9f, 1.0f);
        final int color1 = c.getRGB();
        RenderUtil.drawRect(0.0, 0.0, scaledresolution.getScaledWidth(), 1.0, color1);
        drawProgress();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledresolution.getScaledWidth() * scaleFactor, scaledresolution.getScaledHeight() * scaleFactor);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        Minecraft.getMinecraft().updateDisplay();
    }
    
    private static void drawProgress() {
        if (Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final float startX = sr.getScaledWidth() / 2.0f - 255.0f;
        final float endX = sr.getScaledWidth() / 2.0f + 255.0f;
        final double nProgress = SplashScreen.PROGRESS;
        final double calc = nProgress / 14.0 * 510.0;
        SplashScreen.animated = (float)new AnimationUtil().animateNoFast(startX + calc, SplashScreen.animated, 0.10000000149011612);
        RenderUtil.drawImage(new ResourceLocation("express/splash.png"), 0, 0, sr.getScaledWidth(), sr.getScaledHeight());
        Gui.drawRect((double)startX, (double)(sr.getScaledHeight() - 35.0f), (double)endX, (double)(sr.getScaledHeight() - 34.0f), new Color(255, 255, 255, 100).getRGB());
        Gui.drawRect((double)startX, (double)(sr.getScaledHeight() - 35.0f), (double)SplashScreen.animated, (double)(sr.getScaledHeight() - 34.0f), new Color(255, 255, 255, 255).getRGB());
        FontManager.arial16.drawStringWithShadow("\u6b63\u5728\u521d\u59cb\u5316" + SplashScreen.CURRENT + "...", startX, sr.getScaledHeight() - 44.0f, new Color(255, 255, 255, 255).getRGB());
        FontManager.arial12.drawString("CNPROD" + Client.NAME.toUpperCase() + Client.VERSION, 10.0f, sr.getScaledHeight() - 10.0f, new Color(255, 255, 255, 200).getRGB());
    }
    
    static {
        SplashScreen.CURRENT = "";
        SplashScreen.animated = 0.0f;
    }
}

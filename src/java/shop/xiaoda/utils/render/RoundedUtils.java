//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.gui.altmanager.*;

public class RoundedUtils
{
    public static ShaderUtil roundedShader;
    public static ShaderUtil roundedOutlineShader;
    private static final ShaderUtil roundedTexturedShader;
    private static final ShaderUtil roundedGradientShader;
    private static final ShaderUtil circleShader;
    
    public static void drawGradientRoundLR(final float x, final float y, final float width, final float height, final float radius, final Color color1, final Color color2) {
        drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final float progress, final int change, final Color color, final float smoothness) {
        GLUtil.startBlend();
        final float borderThickness = 1.0f;
        RoundedUtils.circleShader.init();
        RoundedUtils.circleShader.setUniformf("radialSmoothness", smoothness);
        RoundedUtils.circleShader.setUniformf("radius", radius);
        RoundedUtils.circleShader.setUniformf("borderThickness", borderThickness);
        RoundedUtils.circleShader.setUniformf("progress", progress);
        RoundedUtils.circleShader.setUniformi("change", change);
        RoundedUtils.circleShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        final float wh = radius + 10.0f;
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        RoundedUtils.circleShader.setUniformf("pos", (x + (wh / 2.0f - (radius + borderThickness) / 2.0f)) * sr.getScaleFactor(), Minecraft.getMinecraft().displayHeight - (radius + borderThickness) * sr.getScaleFactor() - (y + (wh / 2.0f - (radius + borderThickness) / 2.0f)) * sr.getScaleFactor());
        ShaderUtil.drawQuads(x, y, wh, wh);
        RoundedUtils.circleShader.unload();
        GLUtil.endBlend();
    }
    
    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        RenderUtil.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtils.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedShader);
        RoundedUtils.roundedShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedUtils.roundedShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final boolean blur, final Color color) {
        RenderUtil.resetColor();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        RenderUtil.setAlphaLimit(0.0f);
        RoundedUtils.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedShader);
        RoundedUtils.roundedShader.setUniformi("blur", blur ? 1 : 0);
        RoundedUtils.roundedShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedUtils.roundedShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientRound(final float x, final float y, final float width, final float height, final float radius, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        RenderUtil.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtils.roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedGradientShader);
        RoundedUtils.roundedGradientShader.setUniformf("color1", topLeft.getRed() / 255.0f, topLeft.getGreen() / 255.0f, topLeft.getBlue() / 255.0f, topLeft.getAlpha() / 255.0f);
        RoundedUtils.roundedGradientShader.setUniformf("color2", bottomRight.getRed() / 255.0f, bottomRight.getGreen() / 255.0f, bottomRight.getBlue() / 255.0f, bottomRight.getAlpha() / 255.0f);
        RoundedUtils.roundedGradientShader.setUniformf("color3", bottomLeft.getRed() / 255.0f, bottomLeft.getGreen() / 255.0f, bottomLeft.getBlue() / 255.0f, bottomLeft.getAlpha() / 255.0f);
        RoundedUtils.roundedGradientShader.setUniformf("color4", topRight.getRed() / 255.0f, topRight.getGreen() / 255.0f, topRight.getBlue() / 255.0f, topRight.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedUtils.roundedGradientShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawRoundOutline(final float x, final float y, final float width, final float height, final float radius, final float outlineThickness, final Color color, final Color outlineColor) {
        RenderUtil.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtils.roundedOutlineShader.init();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedOutlineShader);
        RoundedUtils.roundedOutlineShader.setUniformf("outlineThickness", outlineThickness * sr.getScaleFactor());
        RoundedUtils.roundedOutlineShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        RoundedUtils.roundedOutlineShader.setUniformf("outlineColor", outlineColor.getRed() / 255.0f, outlineColor.getGreen() / 255.0f, outlineColor.getBlue() / 255.0f, outlineColor.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - (2.0f + outlineThickness), y - (2.0f + outlineThickness), width + (4.0f + outlineThickness * 2.0f), height + (4.0f + outlineThickness * 2.0f));
        RoundedUtils.roundedOutlineShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientCornerLR(final float x, final float y, final float width, final float height, final float radius, final Color topLeft, final Color bottomRight) {
        final Color mixedColor = ColorUtils.interpolateColorC(topLeft, bottomRight, 0.5f);
        drawGradientRound(x, y, width, height, radius, mixedColor, topLeft, bottomRight, mixedColor);
    }
    
    public static void drawGradientCornerRL(final float x, final float y, final float width, final float height, final float radius, final Color bottomLeft, final Color topRight) {
        final Color mixedColor = ColorUtils.interpolateColorC(topRight, bottomLeft, 0.5f);
        drawGradientRound(x, y, width, height, radius, bottomLeft, mixedColor, mixedColor, topRight);
    }
    
    public static void drawRoundTextured(final float x, final float y, final float width, final float height, final float radius, final float alpha) {
        RenderUtil.resetColor();
        RoundedUtils.roundedTexturedShader.init();
        RoundedUtils.roundedTexturedShader.setUniformi("textureIn", 0);
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedTexturedShader);
        RoundedUtils.roundedTexturedShader.setUniformf("alpha", alpha);
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedUtils.roundedTexturedShader.unload();
        GlStateManager.disableBlend();
    }
    
    private static void setupRoundedRectUniforms(final float x, final float y, final float width, final float height, final float radius, final ShaderUtil roundedTexturedShader) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(), Minecraft.getMinecraft().displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }
    
    public static void round(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        RenderUtil.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtils.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtils.roundedShader);
        RoundedUtils.roundedShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        RoundedUtils.roundedShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void rect(final float x, final float y, final float width, final float height) {
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
    }
    
    public static void round(final int x, final int y, final int width, final int height, final float radius, final int rgb) {
        round((float)x, (float)y, (float)width, (float)height, radius, new Color(rgb));
    }
    
    static {
        RoundedUtils.roundedShader = new ShaderUtil("express/shader/roundedRect.frag");
        RoundedUtils.roundedOutlineShader = new ShaderUtil("express/shader/roundRectOutline.frag");
        roundedTexturedShader = new ShaderUtil("express/shader/roundRectTextured.frag");
        roundedGradientShader = new ShaderUtil("express/shader/roundedRectGradient.frag");
        circleShader = new ShaderUtil("arc");
    }
}

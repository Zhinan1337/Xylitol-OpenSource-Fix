//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import net.minecraft.client.gui.*;
import shop.xiaoda.utils.client.*;
import org.lwjgl.opengl.*;

public class GLRendering
{
    public static void glDrawPoint(final double x, final double y, final float radius, final ScaledResolution scaledResolution, final int colour) {
        final boolean restore = glEnableBlend();
        GL11.glEnable(2832);
        GL11.glHint(3153, 4354);
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glPointSize(radius * GL11.glGetFloat(2982) * scaledResolution.getScaleFactor());
        GL11.glBegin(0);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glDisable(2832);
        GL11.glHint(3153, 4352);
        GL11.glEnable(3553);
    }
    
    public static void glDrawRoundedQuad(final double x, final double y, final float width, final float height, final float radius, final int colour) {
        final boolean restore = glEnableBlend();
        final boolean alphaTest = GL11.glIsEnabled(3008);
        if (alphaTest) {
            GL11.glDisable(3008);
        }
        GL11.glDisable(3553);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL20.glUseProgram(0);
        GL11.glEnable(3553);
        if (alphaTest) {
            GL11.glEnable(3008);
        }
        glRestoreBlend(restore);
    }
    
    public static void glDrawGradientLine(final double x, final double y, final double x1, final double y1, final float lineWidth, final int colour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glShadeModel(7425);
        final int noAlpha = Colors.removeAlphaComponent(colour);
        GL11.glDisable(3008);
        GL11.glBegin(3);
        glColour(noAlpha);
        GL11.glVertex2d(x, y);
        final double dif = x1 - x;
        glColour(colour);
        GL11.glVertex2d(x + dif * 0.4, y);
        GL11.glVertex2d(x + dif * 0.6, y);
        glColour(noAlpha);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        glRestoreBlend(restore);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3553);
    }
    
    public static void glDrawOutlinedQuad(final double x, final double y, final double width, final double height, final float thickness, final int colour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glLineWidth(thickness);
        GL11.glBegin(2);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        glRestoreBlend(restore);
    }
    
    public static void glDrawFilledQuad(final double x, final double y, final double width, final double height, final int colour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glEnable(3553);
    }
    
    public static void glDrawRoundedRectEllipse(final double x, final double y, final double width, final double height, final RoundingMode roundingMode, final int roundingDef, final double roundingLevel, final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;
        switch (roundingMode) {
            case TOP: {
                tLeft = true;
                tRight = true;
                break;
            }
            case BOTTOM: {
                bLeft = true;
                bRight = true;
                break;
            }
            case FULL: {
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            }
            case LEFT: {
                bLeft = true;
                tLeft = true;
                break;
            }
            case RIGHT: {
                bRight = true;
                tRight = true;
                break;
            }
            case TOP_LEFT: {
                tLeft = true;
                break;
            }
            case TOP_RIGHT: {
                tRight = true;
                break;
            }
            case BOTTOM_LEFT: {
                bLeft = true;
                break;
            }
            case BOTTOM_RIGHT: {
                bRight = true;
                break;
            }
        }
        GL11.glTranslated(x, y, 0.0);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        final boolean restore = glEnableBlend();
        if (tLeft) {
            glDrawFilledEllipse(roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.5), (int)(roundingDef * 0.75), roundingDef, false, colour);
        }
        if (tRight) {
            glDrawFilledEllipse(width - roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.75), roundingDef, roundingDef, false, colour);
        }
        if (bLeft) {
            glDrawFilledEllipse(roundingLevel, height - roundingLevel, roundingLevel, (int)(roundingDef * 0.25), (int)(roundingDef * 0.5), roundingDef, false, colour);
        }
        if (bRight) {
            glDrawFilledEllipse(width - roundingLevel, height - roundingLevel, roundingLevel, 0, (int)(roundingDef * 0.25), roundingDef, false, colour);
        }
        GL11.glDisable(2881);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glBegin(9);
        if (tLeft) {
            GL11.glVertex2d(roundingLevel, roundingLevel);
            GL11.glVertex2d(0.0, roundingLevel);
        }
        else {
            GL11.glVertex2d(0.0, 0.0);
        }
        if (bLeft) {
            GL11.glVertex2d(0.0, height - roundingLevel);
            GL11.glVertex2d(roundingLevel, height - roundingLevel);
            GL11.glVertex2d(roundingLevel, height);
        }
        else {
            GL11.glVertex2d(0.0, height);
        }
        if (bRight) {
            GL11.glVertex2d(width - roundingLevel, height);
            GL11.glVertex2d(width - roundingLevel, height - roundingLevel);
            GL11.glVertex2d(width, height - roundingLevel);
        }
        else {
            GL11.glVertex2d(width, height);
        }
        if (tRight) {
            GL11.glVertex2d(width, roundingLevel);
            GL11.glVertex2d(width - roundingLevel, roundingLevel);
            GL11.glVertex2d(width - roundingLevel, 0.0);
        }
        else {
            GL11.glVertex2d(width, 0.0);
        }
        if (tLeft) {
            GL11.glVertex2d(roundingLevel, 0.0);
        }
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glTranslated(-x, -y, 0.0);
        GL11.glEnable(3553);
    }
    
    public static void glDrawFilledQuad(final double x, final double y, final double width, final double height, final int startColour, final int endColour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColour(startColour);
        GL11.glVertex2d(x, y);
        glColour(endColour);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        glColour(startColour);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        glRestoreBlend(restore);
        GL11.glEnable(3553);
    }
    
    public static void glDrawArcOutline(final double x, final double y, final float radius, final float angleStart, final float angleEnd, final float lineWidth, final int colour) {
        final int segments = (int)(radius * 4.0f);
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        GL11.glLineWidth(lineWidth);
        glColour(colour);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glTranslated(x, y, 0.0);
        GL11.glBegin(3);
        final float[][] arcVertices;
        final float[][] vertices = arcVertices = MathUtil.getArcVertices(radius, angleStart, angleEnd, segments);
        for (final float[] vertex : arcVertices) {
            GL11.glVertex2f(vertex[0], vertex[1]);
        }
        GL11.glEnd();
        GL11.glTranslated(-x, -y, 0.0);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        glRestoreBlend(restore);
        GL11.glEnable(3553);
    }
    
    public static void glDrawRoundedOutline(final double x, final double y, final double width, final double height, final float lineWidth, final RoundingMode roundingMode, final float rounding, final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;
        switch (roundingMode) {
            case TOP: {
                tLeft = true;
                tRight = true;
                break;
            }
            case BOTTOM: {
                bLeft = true;
                bRight = true;
                break;
            }
            case FULL: {
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            }
            case LEFT: {
                bLeft = true;
                tLeft = true;
                break;
            }
            case RIGHT: {
                bRight = true;
                tRight = true;
                break;
            }
            case TOP_LEFT: {
                tLeft = true;
                break;
            }
            case TOP_RIGHT: {
                tRight = true;
                break;
            }
            case BOTTOM_LEFT: {
                bLeft = true;
                break;
            }
            case BOTTOM_RIGHT: {
                bRight = true;
                break;
            }
        }
        GL11.glTranslated(x, y, 0.0);
        final boolean restore = glEnableBlend();
        if (tLeft) {
            glDrawArcOutline(rounding, rounding, rounding, 270.0f, 360.0f, lineWidth, colour);
        }
        if (tRight) {
            glDrawArcOutline(width - rounding, rounding, rounding, 0.0f, 90.0f, lineWidth, colour);
        }
        if (bLeft) {
            glDrawArcOutline(rounding, height - rounding, rounding, 180.0f, 270.0f, lineWidth, colour);
        }
        if (bRight) {
            glDrawArcOutline(width - rounding, height - rounding, rounding, 90.0f, 180.0f, lineWidth, colour);
        }
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glBegin(1);
        if (tLeft) {
            GL11.glVertex2d(0.0, (double)rounding);
        }
        else {
            GL11.glVertex2d(0.0, 0.0);
        }
        if (bLeft) {
            GL11.glVertex2d(0.0, height - rounding);
            GL11.glVertex2d((double)rounding, height);
        }
        else {
            GL11.glVertex2d(0.0, height);
            GL11.glVertex2d(0.0, height);
        }
        if (bRight) {
            GL11.glVertex2d(width - rounding, height);
            GL11.glVertex2d(width, height - rounding);
        }
        else {
            GL11.glVertex2d(width, height);
            GL11.glVertex2d(width, height);
        }
        if (tRight) {
            GL11.glVertex2d(width, (double)rounding);
            GL11.glVertex2d(width - rounding, 0.0);
        }
        else {
            GL11.glVertex2d(width, 0.0);
            GL11.glVertex2d(width, 0.0);
        }
        if (tLeft) {
            GL11.glVertex2d((double)rounding, 0.0);
        }
        else {
            GL11.glVertex2d(0.0, 0.0);
        }
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glTranslated(-x, -y, 0.0);
        GL11.glEnable(3553);
    }
    
    public static void glScissorBox(final double x, final double y, final double width, final double height, final ScaledResolution scaledResolution) {
        if (!GL11.glIsEnabled(3089)) {
            GL11.glEnable(3089);
        }
        final int scaling = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * scaling), (int)((scaledResolution.getScaledHeight() - (y + height)) * scaling), (int)(width * scaling), (int)(height * scaling));
    }
    
    public static void glDrawRoundedRect(final double x, final double y, final double width, final double height, final RoundingMode roundingMode, final float rounding, final float scaleFactor, final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;
        switch (roundingMode) {
            case TOP: {
                tLeft = true;
                tRight = true;
                break;
            }
            case BOTTOM: {
                bLeft = true;
                bRight = true;
                break;
            }
            case FULL: {
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            }
            case LEFT: {
                bLeft = true;
                tLeft = true;
                break;
            }
            case RIGHT: {
                bRight = true;
                tRight = true;
                break;
            }
            case TOP_LEFT: {
                tLeft = true;
                break;
            }
            case TOP_RIGHT: {
                tRight = true;
                break;
            }
            case BOTTOM_LEFT: {
                bLeft = true;
                break;
            }
            case BOTTOM_RIGHT: {
                bRight = true;
                break;
            }
        }
        final float alpha = (colour >> 24 & 0xFF) / 255.0f;
        final boolean restore = glEnableBlend();
        glColour(colour);
        GL11.glTranslated(x, y, 0.0);
        GL11.glDisable(3553);
        GL11.glBegin(9);
        if (tLeft) {
            GL11.glVertex2d((double)rounding, (double)rounding);
            GL11.glVertex2d(0.0, (double)rounding);
        }
        else {
            GL11.glVertex2d(0.0, 0.0);
        }
        if (bLeft) {
            GL11.glVertex2d(0.0, height - rounding);
            GL11.glVertex2d((double)rounding, height - rounding);
            GL11.glVertex2d((double)rounding, height);
        }
        else {
            GL11.glVertex2d(0.0, height);
        }
        if (bRight) {
            GL11.glVertex2d(width - rounding, height);
            GL11.glVertex2d(width - rounding, height - rounding);
            GL11.glVertex2d(width, height - rounding);
        }
        else {
            GL11.glVertex2d(width, height);
        }
        if (tRight) {
            GL11.glVertex2d(width, (double)rounding);
            GL11.glVertex2d(width - rounding, (double)rounding);
            GL11.glVertex2d(width - rounding, 0.0);
        }
        else {
            GL11.glVertex2d(width, 0.0);
        }
        if (tLeft) {
            GL11.glVertex2d((double)rounding, 0.0);
        }
        GL11.glEnd();
        GL11.glEnable(2832);
        GL11.glHint(3153, 4354);
        GL11.glPointSize(rounding * 2.0f * GL11.glGetFloat(2982) * scaleFactor);
        GL11.glBegin(0);
        if (tLeft) {
            GL11.glVertex2d((double)rounding, (double)rounding);
        }
        if (tRight) {
            GL11.glVertex2d(width - rounding, (double)rounding);
        }
        if (bLeft) {
            GL11.glVertex2d((double)rounding, height - rounding);
        }
        if (bRight) {
            GL11.glVertex2d(width - rounding, height - rounding);
        }
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glHint(3153, 4352);
        glRestoreBlend(restore);
        GL11.glTranslated(-x, -y, 0.0);
        GL11.glEnable(3553);
    }
    
    public static void glDrawFilledEllipse(final double x, final double y, final double radius, final int startIndex, final int endIndex, final int polygons, final boolean smooth, final int colour) {
        final boolean restore = glEnableBlend();
        if (smooth) {
            GL11.glEnable(2881);
            GL11.glHint(3155, 4354);
        }
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glDisable(2884);
        GL11.glBegin(9);
        GL11.glVertex2d(x, y);
        for (double i = startIndex; i <= endIndex; ++i) {
            final double theta = 6.283185307179586 * i / polygons;
            GL11.glVertex2d(x + radius * Math.cos(theta), y + radius * Math.sin(theta));
        }
        GL11.glEnd();
        glRestoreBlend(restore);
        if (smooth) {
            GL11.glDisable(2881);
            GL11.glHint(3155, 4352);
        }
        GL11.glEnable(2884);
        GL11.glEnable(3553);
    }
    
    public static void glDrawFilledEllipse(final double x, final double y, final float radius, final int colour) {
        final boolean restore = glEnableBlend();
        GL11.glEnable(2832);
        GL11.glHint(3153, 4354);
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glPointSize(radius);
        GL11.glBegin(0);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glDisable(2832);
        GL11.glHint(3153, 4352);
        GL11.glEnable(3553);
    }
    
    public static void glEndScissor() {
        GL11.glDisable(3089);
    }
    
    public static void glDrawFilledRect(final double x, final double y, final double x1, final double y1, final int color) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        glColour(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y);
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glEnable(3553);
    }
    
    public static void glDrawSidewaysGradientRect(final double x, final double y, final double width, final double height, final int startColour, final int endColour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColour(startColour);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        glColour(endColour);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        glRestoreBlend(restore);
    }
    
    public static void glDrawFilledRect(final double x, final double y, final double x1, final double y1, final int startColour, final int endColour) {
        final boolean restore = glEnableBlend();
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColour(startColour);
        GL11.glVertex2d(x, y);
        glColour(endColour);
        GL11.glVertex2d(x, y1);
        GL11.glVertex2d(x1, y1);
        glColour(startColour);
        GL11.glVertex2d(x1, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        glRestoreBlend(restore);
    }
    
    public static void glFilledQuad(final double x, final double y, final double width, final double height, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        RenderUtil.color(color);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void glFilledEllipse(final double x, final double y, final float radius, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2832);
        GL11.glPointSize(radius);
        GL11.glBegin(0);
        RenderUtil.color(color);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glEnable(3553);
    }
    
    public static void glColour(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public static boolean glEnableBlend() {
        final boolean wasEnabled = GL11.glIsEnabled(3042);
        if (!wasEnabled) {
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 0);
        }
        return wasEnabled;
    }
    
    public static void glRestoreBlend(final boolean wasEnabled) {
        if (!wasEnabled) {
            GL11.glDisable(3042);
        }
    }
    
    public enum RoundingMode
    {
        TOP_LEFT, 
        BOTTOM_LEFT, 
        TOP_RIGHT, 
        BOTTOM_RIGHT, 
        LEFT, 
        RIGHT, 
        TOP, 
        BOTTOM, 
        FULL;
    }
}

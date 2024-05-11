//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

import java.awt.*;
import shop.xiaoda.utils.client.*;

public final class ColorUtils
{
    public static int getRGB(final int r, final int g, final int b) {
        return getRGB(r, g, b, 255);
    }
    
    public static Color interpolateColorC(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return new Color(MathUtil.interpolateInt(color1.getRed(), color2.getRed(), amount), MathUtil.interpolateInt(color1.getGreen(), color2.getGreen(), amount), MathUtil.interpolateInt(color1.getBlue(), color2.getBlue(), amount), MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
    
    public static int getRGB(final int r, final int g, final int b, final int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
    
    public static int[] splitRGB(final int rgb) {
        final int[] ints = { rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF };
        return ints;
    }
    
    public static Color interpolateColorsBackAndForth(final int speed, final int index, final Color start, final Color end, final boolean trueColor) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
        return trueColor ? interpolateColorHue(start, end, angle / 360.0f) : interpolateColorC(start, end, angle / 360.0f);
    }
    
    public static Color interpolateColorHue(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        final float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        final float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
        final Color resultColor = Color.getHSBColor(MathUtil.interpolateFloat(color1HSB[0], color2HSB[0], amount), MathUtil.interpolateFloat(color1HSB[1], color2HSB[1], amount), MathUtil.interpolateFloat(color1HSB[2], color2HSB[2], amount));
        return applyOpacity(resultColor, MathUtil.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount) / 255.0f);
    }
    
    public static Color applyOpacity(final Color color, float opacity) {
        opacity = Math.min(1.0f, Math.max(0.0f, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
    }
    
    public static int interpolateColor(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return interpolateColorC(color1, color2, amount).getRGB();
    }
    
    public static Color brighter(final Color color, final float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        final int alpha = color.getAlpha();
        final int i = (int)(1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }
        return new Color(Math.min((int)(r / FACTOR), 255), Math.min((int)(g / FACTOR), 255), Math.min((int)(b / FACTOR), 255), alpha);
    }
    
    public static int getRGB(final int rgb) {
        return 0xFF000000 | rgb;
    }
    
    public static int reAlpha(final int rgb, final int alpha) {
        return getRGB(getRed(rgb), getGreen(rgb), getBlue(rgb), alpha);
    }
    
    public static int getRed(final int rgb) {
        return rgb >> 16 & 0xFF;
    }
    
    public static int getGreen(final int rgb) {
        return rgb >> 8 & 0xFF;
    }
    
    public static int getBlue(final int rgb) {
        return rgb & 0xFF;
    }
    
    public static int getAlpha(final int rgb) {
        return rgb >> 24 & 0xFF;
    }
}

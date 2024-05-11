//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import java.awt.*;

public class HSBData
{
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    
    public HSBData(final float hue, final float saturation, final float brightness, final float alpha) {
        this.alpha = 1.0f;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.alpha = alpha;
    }
    
    public HSBData(final Color color) {
        this.alpha = 1.0f;
        final float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = hsbColor[0];
        this.saturation = hsbColor[1];
        this.brightness = hsbColor[2];
    }
    
    public Color getAsColor() {
        final Color beforeReAlpha = Color.getHSBColor(this.hue, this.saturation, this.brightness);
        return new Color(beforeReAlpha.getRed(), beforeReAlpha.getGreen(), beforeReAlpha.getBlue(), Math.round(255.0f * this.alpha));
    }
    
    public float getHue() {
        return this.hue;
    }
    
    public void setHue(final float hue) {
        this.hue = hue;
    }
    
    public float getSaturation() {
        return this.saturation;
    }
    
    public void setSaturation(final float saturation) {
        this.saturation = saturation;
    }
    
    public float getBrightness() {
        return this.brightness;
    }
    
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
    }
}

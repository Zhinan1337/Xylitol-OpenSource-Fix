//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

import java.awt.*;

public class ColorValue extends Value<Integer>
{
    private int color;
    
    public ColorValue(final String name, final int color, final Dependency dependenc) {
        super(name, dependenc);
        this.setValue(color);
        this.color = color;
    }
    
    public ColorValue(final String name, final int color) {
        super(name);
        this.setValue(color);
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public Color getColorC() {
        return new Color(this.color);
    }
    
    public void setColor(final int color) {
        this.setValue(color);
        this.color = color;
    }
    
    public float[] getHSB() {
        if (this.value == null) {
            return new float[] { 0.0f, 0.0f, 0.0f };
        }
        final float[] hsbValues = new float[3];
        int cMax = Math.max((int)this.value >>> 16 & 0xFF, (int)this.value >>> 8 & 0xFF);
        if (((int)this.value & 0xFF) > cMax) {
            cMax = ((int)this.value & 0xFF);
        }
        int cMin = Math.min((int)this.value >>> 16 & 0xFF, (int)this.value >>> 8 & 0xFF);
        if (((int)this.value & 0xFF) < cMin) {
            cMin = ((int)this.value & 0xFF);
        }
        final float brightness = cMax / 255.0f;
        final float saturation = (cMax != 0) ? ((cMax - cMin) / (float)cMax) : 0.0f;
        float hue;
        if (saturation == 0.0f) {
            hue = 0.0f;
        }
        else {
            final float redC = (cMax - ((int)this.value >>> 16 & 0xFF)) / (float)(cMax - cMin);
            final float greenC = (cMax - ((int)this.value >>> 8 & 0xFF)) / (float)(cMax - cMin);
            final float blueC = (cMax - ((int)this.value & 0xFF)) / (float)(cMax - cMin);
            hue = ((((int)this.value >>> 16 & 0xFF) == cMax) ? (blueC - greenC) : ((((int)this.value >>> 8 & 0xFF) == cMax) ? (2.0f + redC - blueC) : (4.0f + greenC - redC))) / 6.0f;
            if (hue < 0.0f) {
                ++hue;
            }
        }
        hsbValues[0] = hue;
        hsbValues[1] = saturation;
        hsbValues[2] = brightness;
        return hsbValues;
    }
    
    @Override
    public Integer getConfigValue() {
        return this.color;
    }
}

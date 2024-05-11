//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation.impl;

import shop.xiaoda.utils.render.animation.*;

public class EaseOutSine extends Animation
{
    public EaseOutSine(final int ms, final double endPoint) {
        super(ms, endPoint);
    }
    
    public EaseOutSine(final int ms, final double endPoint, final Direction direction) {
        super(ms, endPoint, direction);
    }
    
    protected boolean correctOutput() {
        return true;
    }
    
    protected double getEquation(final double x) {
        return Math.sin(x * 1.5707963267948966);
    }
}

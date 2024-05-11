//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation.impl;

import shop.xiaoda.utils.render.animation.*;

public class EaseInOutQuad extends Animation
{
    public EaseInOutQuad(final int ms, final double endPoint) {
        super(ms, endPoint);
    }
    
    public EaseInOutQuad(final int ms, final double endPoint, final Direction direction) {
        super(ms, endPoint, direction);
    }
    
    protected double getEquation(final double x) {
        return (x < 0.5) ? (2.0 * Math.pow(x, 2.0)) : (1.0 - Math.pow(-2.0 * x + 2.0, 2.0) / 2.0);
    }
}

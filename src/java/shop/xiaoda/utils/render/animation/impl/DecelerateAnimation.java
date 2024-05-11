//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation.impl;

import shop.xiaoda.utils.render.animation.*;

public class DecelerateAnimation extends Animation
{
    public DecelerateAnimation(final int ms, final double endPoint) {
        super(ms, endPoint);
    }
    
    public DecelerateAnimation(final int ms, final double endPoint, final Direction direction) {
        super(ms, endPoint, direction);
    }
    
    protected double getEquation(final double x) {
        return 1.0 - (x - 1.0) * (x - 1.0);
    }
}

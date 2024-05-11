//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation.impl;

import shop.xiaoda.utils.render.animation.*;

public class OutputAnimation
{
    private double now;
    
    public OutputAnimation(final int now) {
        this.now = now;
    }
    
    public void animate(final double target, final float speed) {
        this.now = AnimationUtils.animate(target, this.now, (double)speed);
    }
    
    public double getOutput() {
        return this.now;
    }
    
    public void setNow(final int now) {
        this.now = now;
    }
}

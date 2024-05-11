//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.*;

public class EventStep implements Event
{
    private double stepHeight;
    private final boolean pre;
    
    public EventStep(final double stepHeight, final boolean pre) {
        this.stepHeight = stepHeight;
        this.pre = pre;
    }
    
    public double getStepHeight() {
        return this.stepHeight;
    }
    
    public void setStepHeight(final double stepHeight) {
        this.stepHeight = stepHeight;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
}

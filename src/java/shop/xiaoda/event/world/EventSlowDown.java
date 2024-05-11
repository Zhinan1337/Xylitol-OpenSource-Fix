//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;

public class EventSlowDown extends EventCancellable
{
    private final Type type;
    private float strafeMultiplier;
    private float forwardMultiplier;
    
    public EventSlowDown(final Type type, final float strafeMultiplier, final float forwardMultiplier) {
        this.type = type;
        this.strafeMultiplier = strafeMultiplier;
        this.forwardMultiplier = forwardMultiplier;
    }
    
    public float getStrafeMultiplier() {
        return this.strafeMultiplier;
    }
    
    public float getForwardMultiplier() {
        return this.forwardMultiplier;
    }
    
    public void setStrafeMultiplier(final float strafeMultiplier) {
        this.strafeMultiplier = strafeMultiplier;
    }
    
    public void setForwardMultiplier(final float forwardMultiplier) {
        this.forwardMultiplier = forwardMultiplier;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        Item, 
        Sprinting, 
        SoulSand;
    }
}

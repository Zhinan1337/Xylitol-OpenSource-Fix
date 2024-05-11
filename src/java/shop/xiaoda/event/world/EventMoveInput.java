//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;

public class EventMoveInput extends EventCancellable
{
    private float forward;
    private float strafe;

    public EventMoveInput(final float forward, final float strafe) {
        this.forward = forward;
        this.strafe = strafe;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public float getStrafe() {
        return this.strafe;
    }

    public void setForward(final float forward) {
        this.forward = forward;
    }
    
    public void setStrafe(final float strafe) {
        this.strafe = strafe;
    }
}

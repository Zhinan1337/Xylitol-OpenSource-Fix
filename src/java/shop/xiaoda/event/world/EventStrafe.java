//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;
import shop.xiaoda.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.utils.player.*;

public class EventStrafe extends EventCancellable
{
    public float strafe;
    public float forward;
    public float friction;
    public float yaw;
    
    public void setSpeed(final double speed, final double motionMultiplier) {
        this.setFriction((float)((this.getForward() != 0.0f && this.getStrafe() != 0.0f) ? (speed * 0.9800000190734863) : speed));
        final EntityPlayerSP thePlayer = Client.mc.thePlayer;
        thePlayer.motionX *= motionMultiplier;
        final EntityPlayerSP thePlayer2 = Client.mc.thePlayer;
        thePlayer2.motionZ *= motionMultiplier;
    }
    
    public void setSpeed(final double speed) {
        this.setFriction((float)((this.getForward() != 0.0f && this.getStrafe() != 0.0f) ? (speed * 0.9800000190734863) : speed));
        MoveUtil.stop();
    }
    
    public EventStrafe(final float Strafe, final float Forward, final float Friction, final float Yaw) {
        this.strafe = Strafe;
        this.forward = Forward;
        this.friction = Friction;
        this.yaw = Yaw;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public void setStrafe(final float strafe) {
        this.strafe = strafe;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public void setForward(final float forward) {
        this.forward = forward;
    }
    
    public float getFriction() {
        return this.friction;
    }
    
    public void setFriction(final float friction) {
        this.friction = friction;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
}

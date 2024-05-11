//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

public class FixedRotations
{
    private float yaw;
    private float pitch;
    private float lastYaw;
    private float lastPitch;
    
    public FixedRotations(final float startingYaw, final float startingPitch) {
        this.yaw = startingYaw;
        this.lastYaw = startingYaw;
        this.pitch = startingPitch;
        this.lastPitch = startingPitch;
    }
    
    public void updateRotations(final float requestedYaw, final float requestedPitch, final boolean sprint) {
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.yaw = requestedYaw;
        this.pitch = Math.max(-90.0f, sprint ? this.pitch : Math.min(90.0f, this.pitch));
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public float getLastYaw() {
        return this.lastYaw;
    }
    
    public float getLastPitch() {
        return this.lastPitch;
    }
}

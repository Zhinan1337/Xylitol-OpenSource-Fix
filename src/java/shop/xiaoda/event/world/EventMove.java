//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.client.*;

public class EventMove extends EventCancellable
{
    public double x;
    public double y;
    public double z;
    private final double motionX;
    private final double motionY;
    private final double motionZ;
    
    public EventMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setMoveSpeed(final double speed) {
        double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            this.setX(0.0);
            this.setZ(0.0);
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                forward = ((forward > 0.0) ? 1.0 : -1.0);
            }
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            this.setX(forward * speed * cos + strafe * speed * sin);
            this.setZ(forward * speed * sin - strafe * speed * cos);
        }
    }
}

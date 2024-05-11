//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class FallingPlayer
{
    private final Minecraft mc;
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private final float strafe;
    private final float forward;
    
    public FallingPlayer(final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final float yaw, final float strafe, final float forward) {
        this.mc = Minecraft.getMinecraft();
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
    }
    
    public FallingPlayer(final EntityPlayer player) {
        this.mc = Minecraft.getMinecraft();
        this.x = player.posX;
        this.y = player.posY;
        this.z = player.posZ;
        this.motionX = player.motionX;
        this.motionY = player.motionY;
        this.motionZ = player.motionZ;
        this.yaw = player.rotationYaw;
        this.strafe = player.moveStrafing;
        this.forward = player.moveForward;
    }
    
    private void calculateForTick(float strafe, float forward) {
        float v = strafe * strafe + forward * forward;
        if (v >= 1.0E-4f) {
            v = (float)Math.sqrt(v);
            if (v < 1.0f) {
                v = 1.0f;
            }
            v = this.mc.thePlayer.jumpMovementFactor / v;
            strafe *= v;
            forward *= v;
            final float f1 = (float)Math.sin(this.yaw * 3.1415927f / 180.0f);
            final float f2 = (float)Math.cos(this.yaw * 3.1415927f / 180.0f);
            this.motionX += strafe * f2 - forward * f1;
            this.motionZ += forward * f2 + strafe * f1;
        }
        this.motionY -= 0.08;
        this.motionX *= 0.9100000262260437;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9100000262260437;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
    }
    
    public CollisionResult findCollision(final int ticks) {
        for (int i = 0; i < ticks; ++i) {
            final Vec3 start = new Vec3(this.x, this.y, this.z);
            this.calculateForTick(this.strafe * 0.98f, this.forward * 0.98f);
            final Vec3 end = new Vec3(this.x, this.y, this.z);
            final float w = this.mc.thePlayer.width / 2.0f;
            BlockPos raytracedBlock;
            if ((raytracedBlock = this.rayTrace(start, end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)w, 0.0, (double)w), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)(-w), 0.0, (double)w), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)w, 0.0, (double)(-w)), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)(-w), 0.0, (double)(-w)), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)w, 0.0, (double)(w / 2.0f)), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)(-w), 0.0, (double)(w / 2.0f)), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)(w / 2.0f), 0.0, (double)w), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
            if ((raytracedBlock = this.rayTrace(start.addVector((double)(w / 2.0f), 0.0, (double)(-w)), end)) != null) {
                return new CollisionResult(raytracedBlock, i);
            }
        }
        return null;
    }

    private BlockPos rayTrace(final Vec3 start, final Vec3 end) {
        final MovingObjectPosition result = this.mc.theWorld.rayTraceBlocks(start, end, true);
        if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && result.sideHit == EnumFacing.UP) {
            return result.getBlockPos();
        }
        return null;
    }
    
    public static class CollisionResult
    {
        private final BlockPos pos;
        private final int tick;
        
        public CollisionResult(final BlockPos pos, final int tick) {
            this.pos = pos;
            this.tick = tick;
        }
        
        public BlockPos getPos() {
            return this.pos;
        }
        
        public int getTick() {
            return this.tick;
        }
    }
}

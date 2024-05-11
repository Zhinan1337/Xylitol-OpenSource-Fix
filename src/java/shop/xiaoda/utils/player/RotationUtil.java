//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.player;

import net.minecraft.client.*;
import org.lwjgl.compatibility.util.vector.*;
import org.apache.commons.lang3.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.utils.vec.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.world.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.vec.Vector3d;

public class RotationUtil
{
    public static Minecraft mc;
    private float yaw;
    private float pitch;
    
    public RotationUtil(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public static int wrapAngleToDirection(final float yaw, final int zones) {
        int angle = (int)(yaw + 360 / (2 * zones) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }
    
    public static float getGCD() {
        return (float)(Math.pow(RotationUtil.mc.gameSettings.mouseSensitivity * 0.6 + 0.2, 3.0) * 1.2);
    }
    
    public static Vector2f getRotationFromEyeToPointOffset(final Vec3 position, final EnumFacing enumFacing) {
        double x = position.xCoord + 0.5;
        double y = position.yCoord + 0.5;
        double z = position.zCoord + 0.5;
        x += enumFacing.getDirectionVec().getX() * 0.5;
        y += enumFacing.getDirectionVec().getY() * 0.5;
        z += enumFacing.getDirectionVec().getZ() * 0.5;
        return getRot(new Vec3(x, y, z));
    }
    
    public static Vector2f getRot(final Vec3 pos) {
        final Vec3 vec = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.getEntityBoundingBox().minY + RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
        final double x = pos.xCoord - vec.xCoord;
        final double y = pos.yCoord - vec.yCoord;
        final double z = pos.zCoord - vec.zCoord;
        final double sqrt = Math.sqrt(x * x + z * z);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(y, sqrt)));
        return new Vector2f(yaw, Math.min(Math.max(pitch, -90.0f), 90.0f));
    }
    
    public static float[] getRotationsToPosition(final double x, final double y, final double z) {
        final double deltaX = x - RotationUtil.mc.thePlayer.posX;
        final double deltaY = y - RotationUtil.mc.thePlayer.posY - RotationUtil.mc.thePlayer.getEyeHeight();
        final double deltaZ = z - RotationUtil.mc.thePlayer.posZ;
        final double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final float yaw = (float)Math.toDegrees(-Math.atan2(deltaX, deltaZ));
        final float pitch = (float)Math.toDegrees(-Math.atan2(deltaY, horizontalDistance));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotationsToPosition(final double x, final double y, final double z, final double targetX, final double targetY, final double targetZ) {
        final double dx = targetX - x;
        final double dy = targetY - y;
        final double dz = targetZ - z;
        final double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        final float yaw = (float)Math.toDegrees(-Math.atan2(dx, dz));
        final float pitch = (float)Math.toDegrees(-Math.atan2(dy, horizontalDistance));
        return new float[] { yaw, pitch };
    }
    
    public static float[] scaffoldRots(final double bx, final double by, final double bz, final float lastYaw, final float lastPitch, final float yawSpeed, final float pitchSpeed, final boolean random) {
        final double x = bx - RotationUtil.mc.thePlayer.posX;
        final double y = by - (RotationUtil.mc.thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight());
        final double z = bz - RotationUtil.mc.thePlayer.posZ;
        final float calcYaw = (float)(Math.toDegrees(MathHelper.atan2(z, x)) - 90.0);
        final float calcPitch = (float)(-(MathHelper.atan2(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / 3.141592653589793));
        float pitch = updateRotation(lastPitch, calcPitch, pitchSpeed + RandomUtils.nextFloat(0.0f, 15.0f));
        float yaw = updateRotation(lastYaw, calcYaw, yawSpeed + RandomUtils.nextFloat(0.0f, 15.0f));
        if (random) {
            yaw += (float)ThreadLocalRandom.current().nextDouble(-2.0, 2.0);
            pitch += (float)ThreadLocalRandom.current().nextDouble(-0.2, 0.2);
        }
        return new float[] { yaw, pitch };
    }
    
    public static float[] mouseSens(float yaw, float pitch, final float lastYaw, final float lastPitch) {
        if (RotationUtil.mc.gameSettings.mouseSensitivity == 0.5) {
            RotationUtil.mc.gameSettings.mouseSensitivity = 0.47887325f;
        }
        if (yaw == lastYaw && pitch == lastPitch) {
            return new float[] { yaw, pitch };
        }
        final float f1 = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f2 = f1 * f1 * f1 * 8.0f;
        final int deltaX = (int)((6.667 * yaw - 6.667 * lastYaw) / f2);
        final int deltaY = (int)((6.667 * pitch - 6.667 * lastPitch) / f2) * -1;
        final float f3 = deltaX * f2;
        final float f4 = deltaY * f2;
        yaw = (float)(lastYaw + f3 * 0.15);
        final float f5 = (float)(lastPitch - f4 * 0.15);
        pitch = MathHelper.clamp_float(f5, -90.0f, 90.0f);
        return new float[] { yaw, pitch };
    }
    
    public static float rotateToYaw(final float yawSpeed, final float currentYaw, final float calcYaw) {
        float yaw = updateRotation(currentYaw, calcYaw, yawSpeed + RandomUtils.nextFloat(0.0f, 15.0f));
        final double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        if (-yawSpeed > diffYaw || diffYaw > yawSpeed) {
            yaw += (float)(RandomUtils.nextFloat(1.0f, 2.0f) * Math.sin(RotationUtil.mc.thePlayer.rotationPitch * 3.141592653589793));
        }
        if (yaw == currentYaw) {
            return currentYaw;
        }
        if (RotationUtil.mc.gameSettings.mouseSensitivity == 0.5) {
            RotationUtil.mc.gameSettings.mouseSensitivity = 0.47887325f;
        }
        final float f1 = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f2 = f1 * f1 * f1 * 8.0f;
        final int deltaX = (int)((6.667 * yaw - 6.666666666666667 * currentYaw) / f2);
        final float f3 = deltaX * f2;
        yaw = (float)(currentYaw + f3 * 0.15);
        return yaw;
    }
    
    public static float updateRotation(final float current, final float calc, final float maxDelta) {
        float f = MathHelper.wrapAngleTo180_float(calc - current);
        if (f > maxDelta) {
            f = maxDelta;
        }
        if (f < -maxDelta) {
            f = -maxDelta;
        }
        return current + f;
    }
    
    public static float rotateToPitch(final float pitchSpeed, final float currentPitch, final float calcPitch) {
        float pitch = updateRotation(currentPitch, calcPitch, pitchSpeed + RandomUtils.nextFloat(0.0f, 15.0f));
        if (pitch != calcPitch) {
            pitch += (float)(RandomUtils.nextFloat(1.0f, 2.0f) * Math.sin(RotationUtil.mc.thePlayer.rotationYaw * 3.141592653589793));
        }
        if (RotationUtil.mc.gameSettings.mouseSensitivity == 0.5) {
            RotationUtil.mc.gameSettings.mouseSensitivity = 0.47887325f;
        }
        final float f1 = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f2 = f1 * f1 * f1 * 8.0f;
        final int deltaY = (int)((6.667 * pitch - 6.666667 * currentPitch) / f2) * -1;
        final float f3 = deltaY * f2;
        final float f4 = (float)(currentPitch - f3 * 0.15);
        pitch = MathHelper.clamp_float(f4, -90.0f, 90.0f);
        return pitch;
    }
    
    public static double getRotationDifference(final Entity entity) {
        final Vector2f rotation = toRotation(getCenter(entity.getEntityBoundingBox()), true);
        return getRotationDifference(rotation, new Vector2f(RotationUtil.mc.thePlayer.rotationYaw, RotationUtil.mc.thePlayer.rotationPitch));
    }
    
    public static double getRotationDifference(final Vector2f a, final Vector2f b) {
        return Math.hypot(RotationComponent.getAngleDifference(a.getX(), b.getX()), a.getY() - b.getY());
    }
    
    public static Vec3 getCenter(final AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }
    
    public static Vector2f toRotation(final Vec3 vec, final boolean predict) {
        final Vec3 eyesPos = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.getEntityBoundingBox().minY + RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
        if (predict) {
            eyesPos.addVector(RotationUtil.mc.thePlayer.motionX, RotationUtil.mc.thePlayer.motionY, RotationUtil.mc.thePlayer.motionZ);
        }
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        return new Vector2f(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }
    
    public static float[] getRotationsNeeded(final Entity target) {
        final double yDist = target.posY - RotationUtil.mc.thePlayer.posY;
        Vec3 pos;
        if (yDist >= 1.7) {
            pos = new Vec3(target.posX, target.posY, target.posZ);
        }
        else if (yDist <= -1.7) {
            pos = new Vec3(target.posX, target.posY + target.getEyeHeight(), target.posZ);
        }
        else {
            pos = new Vec3(target.posX, target.posY + target.getEyeHeight() / 2.0f, target.posZ);
        }
        final Vec3 vec = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.getEntityBoundingBox().minY + RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
        final double x = pos.xCoord - vec.xCoord;
        final double y = pos.yCoord - vec.yCoord;
        final double z = pos.zCoord - vec.zCoord;
        final double sqrt = Math.sqrt(x * x + z * z);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(y, sqrt)));
        return new float[] { yaw, Math.min(Math.max(pitch, -90.0f), 90.0f) };
    }
    
    public static float[] getHVHRotation(final Entity entity, final double maxRange) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - RotationUtil.mc.thePlayer.posX;
        final double diffZ = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        final Vec3 BestPos = getNearestPointBB(RotationUtil.mc.thePlayer.getPositionEyes(1.0f), entity.getEntityBoundingBox());
        final Location myEyePos = new Location(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final double diffY = BestPos.yCoord - myEyePos.getY();
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotationsNeededBlock(final double x, final double y, final double z) {
        final double diffX = x + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = z + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double diffY = y + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static Vector2f getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = RotationUtil.mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new Vector2f(yaw, pitch);
    }
    
    public static Vector2f getRotationsNonLivingEntity(final Entity entity) {
        return getRotations(entity.posX, entity.posY + (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) * 0.5, entity.posZ);
    }
    
    public static void setVisualRotations(final float yaw, final float pitch) {
        final EntityPlayerSP thePlayer = RotationUtil.mc.thePlayer;
        RotationUtil.mc.thePlayer.renderYawOffset = yaw;
        thePlayer.rotationYawHead = yaw;
        RotationUtil.mc.thePlayer.rotationPitchHead = pitch;
    }
    
    public static Vec3 getVectorForRotation(final Vector2f rotation) {
        final float yawCos = MathHelper.cos(-rotation.getX() * 0.017453292f - 3.1415927f);
        final float yawSin = MathHelper.sin(-rotation.getX() * 0.017453292f - 3.1415927f);
        final float pitchCos = -MathHelper.cos(-rotation.getY() * 0.017453292f);
        final float pitchSin = MathHelper.sin(-rotation.getY() * 0.017453292f);
        return new Vec3((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
    }
    
    public static float[] getRotations(final Entity entity) {
        final double pX = Minecraft.getMinecraft().thePlayer.posX;
        final double pY = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
        final double pZ = Minecraft.getMinecraft().thePlayer.posZ;
        final double eX = entity.posX;
        final double eY = entity.posY + entity.height / 2.0f;
        final double eZ = entity.posZ;
        final double dX = pX - eX;
        final double dY = pY - eY;
        final double dZ = pZ - eZ;
        final double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        final double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        final double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new float[] { (float)yaw, (float)(90.0 - pitch) };
    }
    
    public static Vec3 getNearestPointBB(final Vec3 eye, final AxisAlignedBB box) {
        final double[] origin = { eye.xCoord, eye.yCoord, eye.zCoord };
        final double[] destMins = { box.minX, box.minY, box.minZ };
        final double[] destMaxs = { box.maxX, box.maxY, box.maxZ };
        for (int i = 0; i < 3; ++i) {
            if (origin[i] > destMaxs[i]) {
                origin[i] = destMaxs[i];
            }
            else if (origin[i] < destMins[i]) {
                origin[i] = destMins[i];
            }
        }
        return new Vec3(origin[0], origin[1], origin[2]);
    }
    
    public static Vector2f toRotationMisc(final Vec3 vec, final boolean predict) {
        final Vec3 eyesPos = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.getEntityBoundingBox().minY + RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
        if (predict) {
            eyesPos.addVector(RotationUtil.mc.thePlayer.motionX, RotationUtil.mc.thePlayer.motionY, RotationUtil.mc.thePlayer.motionZ);
        }
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        return new Vector2f(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }
    
    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }
    
    public static float getBowRot(final Entity entity) {
        final double diffX = entity.posX - RotationUtil.mc.thePlayer.posX;
        final double diffZ = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
        final Location myEyePos = new Location(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        for (double diffY = entity.boundingBox.minY + 0.7; diffY < entity.boundingBox.maxY - 0.1; diffY += 0.1) {
            if (myEyePos.distanceTo(new Location(entity.posX, diffY, entity.posZ)) < myEyePos.distanceTo(BestPos)) {
                BestPos = new Location(entity.posX, diffY, entity.posZ);
            }
        }
        double diffY = BestPos.getY() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return yaw;
    }
    
    public static Vector2f calculate(final Vector3d from, final Vector3d to) {
        final Vector3d diff = to.subtract(from);
        final double distance = Math.hypot(diff.getX(), diff.getZ());
        final float yaw = (float)(MathHelper.atan2(diff.getZ(), diff.getX()) * 57.2957763671875) - 90.0f;
        final float pitch = (float)(-(MathHelper.atan2(diff.getY(), distance) * 57.2957763671875));
        return new Vector2f(yaw, pitch);
    }
    
    public static Vector2f calculate(final Vec3 to) {
        return calculate(RotationUtil.mc.thePlayer.getCustomPositionVector().add(0.0, RotationUtil.mc.thePlayer.getEyeHeight(), 0.0), new Vector3d(to.xCoord, to.yCoord, to.zCoord));
    }
    
    public static Vector2f calculate(final Vector3d to) {
        return calculate(RotationUtil.mc.thePlayer.getCustomPositionVector().add(0.0, RotationUtil.mc.thePlayer.getEyeHeight(), 0.0), to);
    }
    
    public static Vector2f calculate(final Vector3d position, final EnumFacing enumFacing) {
        double x = position.getX() + 0.5;
        double y = position.getY() + 0.5;
        double z = position.getZ() + 0.5;
        x += enumFacing.getDirectionVec().getX() * 0.5;
        y += enumFacing.getDirectionVec().getY() * 0.5;
        z += enumFacing.getDirectionVec().getZ() * 0.5;
        return calculate(new Vector3d(x, y, z));
    }
    
    public static Vector2f calculate(final Entity entity) {
        return calculate(entity.getCustomPositionVector().add(0.0, Math.max(0.0, Math.min(RotationUtil.mc.thePlayer.posY - entity.posY + RotationUtil.mc.thePlayer.getEyeHeight(), (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) * 0.9)), 0.0));
    }
    
    public static Vector2f calculate(final Entity entity, final boolean adaptive, final double range) {
        final Vector2f normalRotations = calculate(entity);
        if (!adaptive || RayCastUtil.rayCast(normalRotations, range).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            return normalRotations;
        }
        for (double yPercent = 1.0; yPercent >= 0.0; yPercent -= 0.25) {
            for (double xPercent = 1.0; xPercent >= -0.5; xPercent -= 0.5) {
                for (double zPercent = 1.0; zPercent >= -0.5; zPercent -= 0.5) {
                    final Vector2f adaptiveRotations = calculate(entity.getCustomPositionVector().add((entity.getEntityBoundingBox().maxX - entity.getEntityBoundingBox().minX) * xPercent, (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) * yPercent, (entity.getEntityBoundingBox().maxZ - entity.getEntityBoundingBox().minZ) * zPercent));
                    if (RayCastUtil.rayCast(adaptiveRotations, range).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                        return adaptiveRotations;
                    }
                }
            }
        }
        return normalRotations;
    }
    
    public static void setVisualRotations(final float[] rotations) {
        setVisualRotations(rotations[0], rotations[1]);
    }
    
    public static void setVisualRotations(final EventMotion e) {
        setVisualRotations(e.getYaw(), e.getPitch());
    }
    
    public static float getRotation(final float currentRotation, final float targetRotation, final float maxIncrement) {
        float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
        if (deltaAngle > maxIncrement) {
            deltaAngle = maxIncrement;
        }
        if (deltaAngle < -maxIncrement) {
            deltaAngle = -maxIncrement;
        }
        return currentRotation + deltaAngle / 2.0f;
    }
    
    public static Vector2f resetRotation(final Vector2f rotation) {
        if (rotation == null) {
            return null;
        }
        final float yaw = RotationUtil.mc.thePlayer.rotationYaw;
        final float pitch = RotationUtil.mc.thePlayer.rotationPitch;
        return new Vector2f(yaw, pitch);
    }
    
    public static Vector2f applySensitivityPatch(final Vector2f rotation, final Vector2f previousRotation) {
        final float mouseSensitivity = (float)(RotationUtil.mc.gameSettings.mouseSensitivity * (1.0 + Math.random() / 1.0E7) * 0.6000000238418579 + 0.20000000298023224);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0f * 0.15;
        final float yaw = previousRotation.x + (float)(Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float)(Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f));
    }
    
    public static Vector2f smooth(final Vector2f targetRotation) {
        final float yaw = targetRotation.x;
        final float pitch = targetRotation.y;
        return new Vector2f(yaw, pitch);
    }
    
    public static Vector2f smooth(final Vector2f lastRotation, final Vector2f targetRotation, final double speed) {
        final float yaw = targetRotation.x;
        final float pitch = targetRotation.y;
        return new Vector2f(yaw, pitch);
    }
    
    public static Vector2f applySensitivityPatch(final Vector2f rotation) {
        final Vector2f previousRotation = RotationUtil.mc.thePlayer.getPreviousRotation();
        final float mouseSensitivity = (float)(RotationUtil.mc.gameSettings.mouseSensitivity * (1.0 + Math.random() / 1.0E7) * 0.6000000238418579 + 0.20000000298023224);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0f * 0.15;
        final float yaw = previousRotation.x + (float)(Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float)(Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f));
    }
    
    private static float[] getRotationsByVec(final Vec3 origin, final Vec3 position) {
        final Vec3 difference = position.subtract(origin);
        final double distance = difference.lengthVector();
        final float yaw = (float)Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotationBlock(final BlockPos pos) {
        return getRotationsByVec(RotationUtil.mc.thePlayer.getPositionVector().addVector(0.0, (double)RotationUtil.mc.thePlayer.getEyeHeight(), 0.0), new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
    }
    
    public static float getYawDirection(final float yaw, final float strafe, final float moveForward) {
        float rotationYaw = yaw;
        if (moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (strafe > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (strafe < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return rotationYaw;
    }
    
    public static float getClampRotation() {
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    static {
        RotationUtil.mc = Minecraft.getMinecraft();
    }
}

// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.player;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.compatibility.util.vector.Vector2f;
import shop.xiaoda.event.world.EventMove;
import shop.xiaoda.event.world.EventMoveInput;
import shop.xiaoda.utils.client.PacketUtil;

public class MoveUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final double WALK_SPEED = 0.221;

    public static void setSpeed(double speed) {
        MoveUtil.mc.thePlayer.motionX = -Math.sin(MoveUtil.getDirection()) * speed;
        MoveUtil.mc.thePlayer.motionZ = Math.cos(MoveUtil.getDirection()) * speed;
    }

    public static int getSpeedAmplifier() {
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return 1 + MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        }
        return 0;
    }

    public static void jump(EventMove event) {
        double jumpY = MoveUtil.mc.thePlayer.getJumpUpwardsMotion();
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            jumpY += (double)((float)(MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f);
        }
        MoveUtil.mc.thePlayer.motionY = jumpY;
        event.setY(MoveUtil.mc.thePlayer.motionY);
    }

    public static void strafeNoTargetStrafe(EventMove event, double speed) {
        float direction = (float)Math.toRadians(MoveUtil.getPlayerDirection());
        if (MoveUtil.isMoving()) {
            MoveUtil.mc.thePlayer.motionX = -Math.sin(direction) * speed;
            event.setX(MoveUtil.mc.thePlayer.motionX);
            MoveUtil.mc.thePlayer.motionZ = Math.cos(direction) * speed;
            event.setZ(MoveUtil.mc.thePlayer.motionZ);
        } else {
            MoveUtil.mc.thePlayer.motionX = 0.0;
            event.setX(0.0);
            MoveUtil.mc.thePlayer.motionZ = 0.0;
            event.setZ(0.0);
        }
    }

    public static float[] incrementMoveDirection(float forward, float strafe) {
        if (forward != 0.0f || strafe != 0.0f) {
            float value;
            float f = value = forward != 0.0f ? Math.abs(forward) : Math.abs(strafe);
            if (forward > 0.0f) {
                if (strafe > 0.0f) {
                    strafe = 0.0f;
                } else if (strafe == 0.0f) {
                    strafe = -value;
                } else if (strafe < 0.0f) {
                    forward = 0.0f;
                }
            } else if (forward == 0.0f) {
                forward = strafe > 0.0f ? value : -value;
            } else if (strafe < 0.0f) {
                strafe = 0.0f;
            } else if (strafe == 0.0f) {
                strafe = value;
            } else if (strafe > 0.0f) {
                forward = 0.0f;
            }
        }
        return new float[]{forward, strafe};
    }

    public static double direction() {
        float rotationYaw = MoveUtil.mc.thePlayer.movementYaw;
        if (MoveUtil.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtil.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (MoveUtil.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtil.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 70.0f * forward;
        }
        if (MoveUtil.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 70.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static double direction(float rotationYaw, double moveForward, double moveStrafing) {
        if (moveForward < 0.0) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (moveForward < 0.0) {
            forward = -0.5f;
        } else if (moveForward > 0.0) {
            forward = 0.5f;
        }
        if (moveStrafing > 0.0) {
            rotationYaw -= 90.0f * forward;
        }
        if (moveStrafing < 0.0) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static void stop() {
        MoveUtil.mc.thePlayer.motionX = 0.0;
        MoveUtil.mc.thePlayer.motionZ = 0.0;
    }

    public static double predictedMotion(double motion, int ticks) {
        if (ticks == 0) {
            return motion;
        }
        double predicted = motion;
        for (int i = 0; i < ticks; ++i) {
            predicted = (predicted - 0.08) * (double)0.98f;
        }
        return predicted;
    }

    public static void fixMovement(EventMoveInput event, float yaw) {
        float forward = event.getForward();
        float strafe = event.getStrafe();
        double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction(MoveUtil.mc.thePlayer.rotationYaw, forward, strafe)));
        if (forward == 0.0f && strafe == 0.0f) {
            return;
        }
        float closestForward = 0.0f;
        float closestStrafe = 0.0f;
        float closestDifference = Float.MAX_VALUE;
        for (float predictedForward = -1.0f; predictedForward <= 1.0f; predictedForward += 1.0f) {
            for (float predictedStrafe = -1.0f; predictedStrafe <= 1.0f; predictedStrafe += 1.0f) {
                double predictedAngle;
                double difference;
                if (predictedStrafe == 0.0f && predictedForward == 0.0f || !((difference = Math.abs(angle - (predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction(yaw, predictedForward, predictedStrafe)))))) < (double)closestDifference)) continue;
                closestDifference = (float)difference;
                closestForward = predictedForward;
                closestStrafe = predictedStrafe;
            }
        }
        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }

    public static float getMoveYaw(float yaw) {
        Vector2f from = new Vector2f((float)MoveUtil.mc.thePlayer.lastTickPosX, (float)MoveUtil.mc.thePlayer.lastTickPosZ);
        Vector2f to = new Vector2f((float)MoveUtil.mc.thePlayer.posX, (float)MoveUtil.mc.thePlayer.posZ);
        Vector2f diff = new Vector2f(to.x - from.x, to.y - from.y);
        double x = diff.x;
        double z = diff.y;
        if (x != 0.0 && z != 0.0) {
            yaw = (float)Math.toDegrees((Math.atan2(-x, z) + (double)MathHelper.PI2) % (double)MathHelper.PI2);
        }
        return yaw;
    }

    public static boolean isPositionValidity(Vec3 vec3) {
        BlockPos pos = new BlockPos(vec3);
        if (MoveUtil.isBlockSolid(pos) || MoveUtil.isBlockSolid(pos.add(0, 1, 0))) {
            return false;
        }
        return MoveUtil.isSafeToWalkOn(pos.add(0, -1, 0));
    }

    public static boolean isBlockNearBy(double distance) {
        double smallX = Math.min(MoveUtil.mc.thePlayer.posX - distance, MoveUtil.mc.thePlayer.posX + distance);
        double smallY = Math.min(MoveUtil.mc.thePlayer.posY, MoveUtil.mc.thePlayer.posY);
        double smallZ = Math.min(MoveUtil.mc.thePlayer.posZ - distance, MoveUtil.mc.thePlayer.posZ + distance);
        double bigX = Math.max(MoveUtil.mc.thePlayer.posX - distance, MoveUtil.mc.thePlayer.posX + distance);
        double bigY = Math.max(MoveUtil.mc.thePlayer.posY, MoveUtil.mc.thePlayer.posY);
        double bigZ = Math.max(MoveUtil.mc.thePlayer.posZ - distance, MoveUtil.mc.thePlayer.posZ + distance);
        int x = (int)smallX;
        while ((double)x <= bigX) {
            int y = (int)smallY;
            while ((double)y <= bigY) {
                int z = (int)smallZ;
                while ((double)z <= bigZ) {
                    if (!MoveUtil.isPositionValidity(new Vec3(x, y, z)) && MoveUtil.isPositionValidity(new Vec3(x, y + 1, z))) {
                        return true;
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        return false;
    }

    private static boolean isBlockSolid(BlockPos pos) {
        Block block = MoveUtil.mc.theWorld.getBlockState(pos).getBlock();
        return block instanceof BlockSlab || block instanceof BlockStairs || block instanceof BlockCactus || block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSkull || block instanceof BlockPane || block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockGlass || block instanceof BlockPistonBase || block instanceof BlockPistonExtension || block instanceof BlockPistonMoving || block instanceof BlockStainedGlass || block instanceof BlockTrapDoor;
    }

    private static boolean isSafeToWalkOn(BlockPos pos) {
        Block block = MoveUtil.mc.theWorld.getBlockState(pos).getBlock();
        return !(block instanceof BlockFence) && !(block instanceof BlockWall);
    }

    public static Block getBlockUnderPlayer(EntityPlayer entityPlayer, double height) {
        return MoveUtil.mc.theWorld.getBlockState(new BlockPos(entityPlayer.posX, entityPlayer.posY - height, entityPlayer.posZ)).getBlock();
    }

    public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = (double)block.getX() + 0.5 - MoveUtil.mc.thePlayer.posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.getZ() + 0.5 - MoveUtil.mc.thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.getY() + 0.5;
        double d1 = MoveUtil.mc.thePlayer.posY + (double)MoveUtil.mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static float getDistanceToGround(Entity e) {
        if (MoveUtil.mc.thePlayer.isCollidedVertically && MoveUtil.mc.thePlayer.onGround) {
            return 0.0f;
        }
        for (float a = (float)e.posY; a > 0.0f; a -= 1.0f) {
            int id;
            int i;
            int[] stairs = new int[]{53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            int[] exemptIds = new int[]{6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177};
            Block block = MoveUtil.mc.theWorld.getBlockState(new BlockPos(e.posX, a - 1.0f, e.posZ)).getBlock();
            if (block instanceof BlockAir) continue;
            if (Block.getIdFromBlock(block) == 44 || Block.getIdFromBlock(block) == 126) {
                return Math.max((float)(e.posY - (double)a - 0.5), 0.0f);
            }
            int[] arrayOfInt1 = stairs;
            int j = stairs.length;
            for (i = 0; i < j; ++i) {
                id = arrayOfInt1[i];
                if (Block.getIdFromBlock(block) != id) continue;
                return Math.max((float)(e.posY - (double)a - 1.0), 0.0f);
            }
            arrayOfInt1 = exemptIds;
            j = exemptIds.length;
            for (i = 0; i < j; ++i) {
                id = arrayOfInt1[i];
                if (Block.getIdFromBlock(block) != id) continue;
                return Math.max((float)(e.posY - (double)a), 0.0f);
            }
            return (float)(e.posY - (double)a + block.getBlockBoundsMaxY() - 1.0);
        }
        return 0.0f;
    }

    public static Block getBlockAtPosC(double x, double y, double z) {
        EntityPlayerSP inPlayer = MoveUtil.mc.thePlayer;
        return MoveUtil.mc.theWorld.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }

    public static boolean isMoving() {
        return MoveUtil.mc.thePlayer != null && (MoveUtil.mc.thePlayer.movementInput.moveForward != 0.0f || MoveUtil.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }

    public static float getSpeed() {
        return (float)Math.sqrt(MoveUtil.mc.thePlayer.motionX * MoveUtil.mc.thePlayer.motionX + MoveUtil.mc.thePlayer.motionZ * MoveUtil.mc.thePlayer.motionZ);
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static int getSpeedEffect() {
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = (double)MoveUtil.mc.thePlayer.capabilities.getWalkSpeed() * 2.873;
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            baseSpeed /= 1.0 + 0.2 * (double)(MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static int getSpeedEffect(EntityPlayer player) {
        if (player.isPotionActive(Potion.moveSpeed)) {
            return player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        }
        return baseJumpHeight;
    }

    public static int getJumpEffect() {
        if (MoveUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            return MoveUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }

    public static void strafe() {
        MoveUtil.strafe(MoveUtil.getSpeed());
    }

    public static void strafe(EventMove eventMove) {
        MoveUtil.strafe(eventMove, (double)MoveUtil.getSpeed());
    }

    public static void strafe(double speed) {
        if (!MoveUtil.isMoving()) {
            return;
        }
        double yaw = MoveUtil.getDirection();
        MoveUtil.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtil.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void strafe(EventMove eventMove, double speed) {
        if (!MoveUtil.isMoving()) {
            return;
        }
        double yaw = MoveUtil.getDirection();
        MoveUtil.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        eventMove.setX(MoveUtil.mc.thePlayer.motionX);
        MoveUtil.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        eventMove.setZ(MoveUtil.mc.thePlayer.motionZ);
    }

    public static void strafe(double speed, float yaw) {
        if (!MoveUtil.isMoving()) {
            return;
        }
        yaw = (float)Math.toRadians(yaw);
        MoveUtil.mc.thePlayer.motionX = (double)(-MathHelper.sin(yaw)) * speed;
        MoveUtil.mc.thePlayer.motionZ = (double)MathHelper.cos(yaw) * speed;
    }

    public static void setMotion(double speed) {
        MoveUtil.setMotion(speed, MoveUtil.mc.thePlayer.rotationYaw);
    }

    public static void setMotion(EventMove e, double speed, float yaw) {
        double forward = MoveUtil.mc.thePlayer.movementInput.moveForward;
        double strafe = MoveUtil.mc.thePlayer.movementInput.moveStrafe;
        if (forward == 0.0 && strafe == 0.0) {
            e.x = 0.0;
            MoveUtil.mc.thePlayer.motionX = 0.0;
            e.z = 0.0;
            MoveUtil.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            MoveUtil.mc.thePlayer.motionX = e.x = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            MoveUtil.mc.thePlayer.motionZ = e.z = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static void setMotion(EventMove e, double speed) {
        MoveUtil.setMotion(e, speed, MoveUtil.mc.thePlayer.rotationYaw);
    }

    public static void setMotion(double speed, float yaw) {
        double forward = MoveUtil.mc.thePlayer.movementInput.moveForward;
        double strafe = MoveUtil.mc.thePlayer.movementInput.moveStrafe;
        if (forward == 0.0 && strafe == 0.0) {
            MoveUtil.mc.thePlayer.motionX = 0.0;
            MoveUtil.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            MoveUtil.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            MoveUtil.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static void hypixelTeleport(double[] startPos, BlockPos endPos) {
        double dist = Math.sqrt(MoveUtil.mc.thePlayer.getDistanceSq(endPos));
        double distanceEntreLesPackets = 0.31 + (double)(MoveUtil.getSpeedEffect() / 20);
        double ztp = 0.0;
        if (dist > distanceEntreLesPackets) {
            double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1L;
            double xtp = MoveUtil.mc.thePlayer.posX;
            double ytp = MoveUtil.mc.thePlayer.posY;
            ztp = MoveUtil.mc.thePlayer.posZ;
            double count = 0.0;
            int i = 1;
            while ((double)i < nbPackets) {
                double xdi = ((double)endPos.getX() - MoveUtil.mc.thePlayer.posX) / nbPackets;
                double zdi = ((double)endPos.getZ() - MoveUtil.mc.thePlayer.posZ) / nbPackets;
                double ydi = ((double)endPos.getY() - MoveUtil.mc.thePlayer.posY) / nbPackets;
                count += 1.0;
                if (!MoveUtil.mc.theWorld.getBlockState(new BlockPos(xtp += xdi, (ytp += ydi) - 1.0, ztp += zdi)).getBlock().isFullBlock()) {
                    if (count <= 2.0) {
                        ytp += 2.0E-8;
                    } else if (count >= 4.0) {
                        count = 0.0;
                    }
                }
                C03PacketPlayer.C04PacketPlayerPosition Packet2 = new C03PacketPlayer.C04PacketPlayerPosition(xtp, ytp, ztp, false);
                PacketUtil.sendPacketNoEvent(Packet2);
                ++i;
            }
            MoveUtil.mc.thePlayer.setPosition((double)endPos.getX() + 0.5, endPos.getY(), (double)endPos.getZ() + 0.5);
        } else {
            MoveUtil.mc.thePlayer.setPosition(endPos.getX(), endPos.getY(), endPos.getZ());
        }
    }

    public static boolean checkTeleport(double x, double y, double z, double distBetweenPackets) {
        double distx = MoveUtil.mc.thePlayer.posX - x;
        double disty = MoveUtil.mc.thePlayer.posY - y;
        double distz = MoveUtil.mc.thePlayer.posZ - z;
        double dist = Math.sqrt(MoveUtil.mc.thePlayer.getDistanceSq(x, y, z));
        double distanceEntreLesPackets = distBetweenPackets;
        double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1L;
        double xtp = MoveUtil.mc.thePlayer.posX;
        double ytp = MoveUtil.mc.thePlayer.posY;
        double ztp = MoveUtil.mc.thePlayer.posZ;
        int i = 1;
        while ((double)i < nbPackets) {
            AxisAlignedBB bb;
            double xdi = (x - MoveUtil.mc.thePlayer.posX) / nbPackets;
            double ydi = (y - MoveUtil.mc.thePlayer.posY) / nbPackets;
            double zdi = (z - MoveUtil.mc.thePlayer.posZ) / nbPackets;
            if (!MoveUtil.mc.theWorld.getCollidingBoundingBoxes(MoveUtil.mc.thePlayer, bb = new AxisAlignedBB((xtp += xdi) - 0.3, ytp += ydi, (ztp += zdi) - 0.3, xtp + 0.3, ytp + 1.8, ztp + 0.3)).isEmpty()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public static double getDirection() {
        float rotationYaw = MoveUtil.mc.thePlayer.rotationYaw;
        if (MoveUtil.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtil.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (MoveUtil.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtil.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtil.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static float getPlayerDirection() {
        float direction = MoveUtil.mc.thePlayer.rotationYaw;
        if (MoveUtil.mc.thePlayer.moveForward > 0.0f) {
            if (MoveUtil.mc.thePlayer.moveStrafing > 0.0f) {
                direction -= 45.0f;
            } else if (MoveUtil.mc.thePlayer.moveStrafing < 0.0f) {
                direction += 45.0f;
            }
        } else if (MoveUtil.mc.thePlayer.moveForward < 0.0f) {
            direction = MoveUtil.mc.thePlayer.moveStrafing > 0.0f ? (direction -= 135.0f) : (MoveUtil.mc.thePlayer.moveStrafing < 0.0f ? (direction += 135.0f) : (direction -= 180.0f));
        } else if (MoveUtil.mc.thePlayer.moveStrafing > 0.0f) {
            direction -= 90.0f;
        } else if (MoveUtil.mc.thePlayer.moveStrafing < 0.0f) {
            direction += 90.0f;
        }
        return direction;
    }

    public static void useDiagonalSpeed() {
        boolean active;
        KeyBinding[] gameSettings = new KeyBinding[]{MoveUtil.mc.gameSettings.keyBindForward, MoveUtil.mc.gameSettings.keyBindRight, MoveUtil.mc.gameSettings.keyBindBack, MoveUtil.mc.gameSettings.keyBindLeft};
        int[] down = new int[]{0};
        Arrays.stream(gameSettings).forEach(keyBinding -> {
            down[0] = down[0] + (keyBinding.isKeyDown() ? 1 : 0);
        });
        boolean bl = active = down[0] == 1;
        if (!active) {
            return;
        }
        double groundIncrease = 0.0026000750109401644;
        double airIncrease = 5.199896488849598E-4;
        double increase = MoveUtil.mc.thePlayer.onGround ? 0.0026000750109401644 : 5.199896488849598E-4;
        MoveUtil.moveFlying(increase);
    }

    public static void moveFlying(double increase) {
        if (!MoveUtil.isMoving()) {
            return;
        }
        double yaw = MoveUtil.direction();
        MoveUtil.mc.thePlayer.motionX += (double)(-MathHelper.sin((float)yaw)) * increase;
        MoveUtil.mc.thePlayer.motionZ += (double)MathHelper.cos((float)yaw) * increase;
    }
}

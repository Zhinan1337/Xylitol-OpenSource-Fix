//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import shop.xiaoda.utils.misc.*;
import shop.xiaoda.utils.player.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class WorldUtil implements MinecraftInstance
{
    public static BlockInfo getBlockUnder(final double y, final int maxRange) {
        return getBlockInfo(WorldUtil.mc.thePlayer.posX, y - 1.0, WorldUtil.mc.thePlayer.posZ, maxRange);
    }
    
    public static BlockInfo getBlockInfo(final double x, final double y, final double z, final int maxRange) {
        final BlockPos pos = new BlockPos(x, y, z);
        final EnumFacing playerDirectionFacing = getHorizontalFacing(MoveUtil.getPlayerDirection()).getOpposite();
        final ArrayList<EnumFacing> facingValues = new ArrayList<EnumFacing>();
        facingValues.add(playerDirectionFacing);
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != playerDirectionFacing && facing != EnumFacing.UP) {
                facingValues.add(facing);
            }
        }
        final CopyOnWriteArrayList<BlockPos> aaa = new CopyOnWriteArrayList<BlockPos>();
        aaa.add(pos);
        for (int i = 0; i < maxRange; ++i) {
            final ArrayList<BlockPos> ccc = new ArrayList<BlockPos>(aaa);
            if (!aaa.isEmpty()) {
                for (final BlockPos bbbb : aaa) {
                    for (final EnumFacing facing2 : facingValues) {
                        final BlockPos n = bbbb.offset(facing2);
                        if (!isAirOrLiquid(n)) {
                            return new BlockInfo(n, facing2.getOpposite());
                        }
                        aaa.add(n);
                    }
                }
            }
            for (final BlockPos dddd : ccc) {
                aaa.remove(dddd);
            }
            ccc.clear();
        }
        return null;
    }
    
    public static Vec3 getVec3(final BlockPos pos, final EnumFacing facing, final boolean randomised) {
        Vec3 vec3 = new Vec3((Vec3i)pos);
        double amount1 = 0.5;
        double amount2 = 0.5;
        if (randomised) {
            amount1 = 0.45 + Math.random() * 0.1;
            amount2 = 0.45 + Math.random() * 0.1;
        }
        if (facing == EnumFacing.UP) {
            vec3 = vec3.addVector(amount1, 1.0, amount2);
        }
        else if (facing == EnumFacing.DOWN) {
            vec3 = vec3.addVector(amount1, 0.0, amount2);
        }
        else if (facing == EnumFacing.EAST) {
            vec3 = vec3.addVector(1.0, amount1, amount2);
        }
        else if (facing == EnumFacing.WEST) {
            vec3 = vec3.addVector(0.0, amount1, amount2);
        }
        else if (facing == EnumFacing.NORTH) {
            vec3 = vec3.addVector(amount1, amount2, 0.0);
        }
        else if (facing == EnumFacing.SOUTH) {
            vec3 = vec3.addVector(amount1, amount2, 1.0);
        }
        return vec3;
    }
    
    public static EnumFacing getHorizontalFacing(final float yaw) {
        return EnumFacing.getHorizontal(MathHelper.floor_double(yaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    public static boolean isAir(final BlockPos pos) {
        final Block block = WorldUtil.mc.theWorld.getBlockState(pos).getBlock();
        return block instanceof BlockAir;
    }
    
    public static boolean isAirOrLiquid(final BlockPos pos) {
        final Block block = WorldUtil.mc.theWorld.getBlockState(pos).getBlock();
        return block instanceof BlockAir || block instanceof BlockLiquid;
    }
    
    public static MovingObjectPosition raytrace(final float yaw, final float pitch) {
        final float partialTicks = WorldUtil.mc.timer.renderPartialTicks;
        final float blockReachDistance = WorldUtil.mc.playerController.getBlockReachDistance();
        final Vec3 vec3 = WorldUtil.mc.thePlayer.getPositionEyes(partialTicks);
        final Vec3 vec4 = WorldUtil.mc.thePlayer.getVectorForRotationPublic(pitch, yaw);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
        return WorldUtil.mc.theWorld.rayTraceBlocks(vec3, vec5, false, false, true);
    }
    
    public static MovingObjectPosition raytraceLegit(final float yaw, final float pitch, final float lastYaw, final float lastPitch) {
        final float partialTicks = WorldUtil.mc.timer.renderPartialTicks;
        final float blockReachDistance = WorldUtil.mc.playerController.getBlockReachDistance();
        final Vec3 vec3 = WorldUtil.mc.thePlayer.getPositionEyes(partialTicks);
        final float f = lastPitch + (pitch - lastPitch) * partialTicks;
        final float f2 = lastYaw + (yaw - lastYaw) * partialTicks;
        final Vec3 vec4 = WorldUtil.mc.thePlayer.getVectorForRotationPublic(f, f2);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
        return WorldUtil.mc.theWorld.rayTraceBlocks(vec3, vec5, false, false, true);
    }
    
    public static boolean isBlockUnder() {
        for (int y = (int)WorldUtil.mc.thePlayer.posY; y >= 0; --y) {
            if (!(WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX, (double)y, WorldUtil.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockUnder(final int distance) {
        for (int y = (int)WorldUtil.mc.thePlayer.posY; y >= (int)WorldUtil.mc.thePlayer.posY - distance; --y) {
            if (!(WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX, (double)y, WorldUtil.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean negativeExpand(final double negativeExpandValue) {
        return WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX + negativeExpandValue, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX - negativeExpandValue, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir && WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX - negativeExpandValue, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ)).getBlock() instanceof BlockAir && WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX + negativeExpandValue, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ)).getBlock() instanceof BlockAir && WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && WorldUtil.mc.theWorld.getBlockState(new BlockPos(WorldUtil.mc.thePlayer.posX, WorldUtil.mc.thePlayer.posY - 1.0, WorldUtil.mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir;
    }
}

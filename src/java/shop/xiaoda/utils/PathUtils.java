//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import shop.xiaoda.utils.misc.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;
import javax.vecmath.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.vec.Vector3d;

public final class PathUtils implements MinecraftInstance
{
    public static ArrayList<Vec3> findTeleportPath(final EntityLivingBase current, final EntityLivingBase target, final double dashDistance) {
        final double curX = current.posX;
        final double curY = current.posY;
        final double curZ = current.posZ;
        final double tpX = target.posX;
        final double tpY = target.posY;
        final double tpZ = target.posZ;
        Vec3 topFrom = new Vec3(curX, curY, curZ);
        final Vec3 to = new Vec3(tpX, tpY, tpZ);
        if (!canPassThrow(new BlockPos(topFrom))) {
            topFrom = topFrom.addVector(0.0, 1.0, 0.0);
        }
        final AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        final ArrayList<Vec3> path = new ArrayList<Vec3>();
        final ArrayList<Vec3> pathFinderPath = (ArrayList<Vec3>)pathfinder.getPath();
        for (final Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0420: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > dashDistance * dashDistance) {
                        canContinue = false;
                    }
                    else {
                        final double smallX = Math.min(lastDashLoc.xCoord, pathElm.xCoord);
                        final double smallY = Math.min(lastDashLoc.yCoord, pathElm.yCoord);
                        final double smallZ = Math.min(lastDashLoc.zCoord, pathElm.zCoord);
                        final double bigX = Math.max(lastDashLoc.xCoord, pathElm.xCoord);
                        final double bigY = Math.max(lastDashLoc.yCoord, pathElm.yCoord);
                        final double bigZ = Math.max(lastDashLoc.zCoord, pathElm.zCoord);
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0420;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }
    
    private static boolean canPassThrow(final BlockPos pos) {
        final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }
    
    public static List<Vector3d> findBlinkPath(final double tpX, final double tpY, final double tpZ) {
        final List<Vector3d> positions = new ArrayList<Vector3d>();
        double curX = PathUtils.mc.thePlayer.posX;
        double curY = PathUtils.mc.thePlayer.posY;
        double curZ = PathUtils.mc.thePlayer.posZ;
        double distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);
        int count = 0;
        while (distance > 0.0) {
            distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);
            final double diffX = curX - tpX;
            final double diffY = curY - tpY;
            final double diffZ = curZ - tpZ;
            final double offset = ((count & 0x1) == 0x0) ? 0.4 : 0.1;
            final double minX = Math.min(Math.abs(diffX), offset);
            if (diffX < 0.0) {
                curX += minX;
            }
            if (diffX > 0.0) {
                curX -= minX;
            }
            final double minY = Math.min(Math.abs(diffY), 0.25);
            if (diffY < 0.0) {
                curY += minY;
            }
            if (diffY > 0.0) {
                curY -= minY;
            }
            final double minZ = Math.min(Math.abs(diffZ), offset);
            if (diffZ < 0.0) {
                curZ += minZ;
            }
            if (diffZ > 0.0) {
                curZ -= minZ;
            }
            positions.add(new Vector3d(curX, curY, curZ));
            ++count;
        }
        return positions;
    }
    
    public static List<Vector3d> findPath(final double tpX, final double tpY, final double tpZ, final double offset) {
        final List<Vector3d> positions = new ArrayList<Vector3d>();
        final double steps = Math.ceil(getDistance(PathUtils.mc.thePlayer.posX, PathUtils.mc.thePlayer.posY, PathUtils.mc.thePlayer.posZ, tpX, tpY, tpZ) / offset);
        final double dX = tpX - PathUtils.mc.thePlayer.posX;
        final double dY = tpY - PathUtils.mc.thePlayer.posY;
        final double dZ = tpZ - PathUtils.mc.thePlayer.posZ;
        for (double d = 1.0; d <= steps; ++d) {
            positions.add(new Vector3d(PathUtils.mc.thePlayer.posX + dX * d / steps, PathUtils.mc.thePlayer.posY + dY * d / steps, PathUtils.mc.thePlayer.posZ + dZ * d / steps));
        }
        return positions;
    }
    
    private static double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double xDiff = x1 - x2;
        final double yDiff = y1 - y2;
        final double zDiff = z1 - z2;
        return MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
}

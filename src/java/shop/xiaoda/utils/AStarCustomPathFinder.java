//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import shop.xiaoda.utils.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class AStarCustomPathFinder
{
    private final Vec3 startVec3;
    private final Vec3 endVec3;
    private ArrayList<Vec3> path;
    private final ArrayList<Hub> hubs;
    private final ArrayList<Hub> hubsToWork;
    private final double minDistanceSquared = 9.0;
    private final boolean nearest = true;
    private static final Vec3[] flatCardinalDirections;
    
    public AStarCustomPathFinder(final Vec3 startVec3, final Vec3 endVec3) {
        this.path = new ArrayList<Vec3>();
        this.hubs = new ArrayList<Hub>();
        this.hubsToWork = new ArrayList<Hub>();
        this.startVec3 = BlockUtil.floorVec3(startVec3.addVector(0.0, 0.0, 0.0));
        this.endVec3 = BlockUtil.floorVec3(endVec3.addVector(0.0, 0.0, 0.0));
    }
    
    public ArrayList<Vec3> getPath() {
        return this.path;
    }
    
    public void compute() {
        this.compute(1000, 4);
    }
    
    public void compute(final int loops, final int depth) {
        this.path.clear();
        this.hubsToWork.clear();
        final ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(this.startVec3);
        this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.squareDistanceTo(this.endVec3), 0.0, 0.0));
    Label_0339:
        for (int i = 0; i < loops; ++i) {
            this.hubsToWork.sort(new CompareHub());
            int j = 0;
            if (this.hubsToWork.size() == 0) {
                break;
            }
            for (final Hub hub : new ArrayList<Hub>(this.hubsToWork)) {
                if (++j > depth) {
                    break;
                }
                this.hubsToWork.remove(hub);
                this.hubs.add(hub);
                for (final Vec3 direction : AStarCustomPathFinder.flatCardinalDirections) {
                    final Vec3 loc = BlockUtil.floorVec3(hub.getLoc().add(direction));
                    if (checkPositionValidity(loc, false) && this.addHub(hub, loc, 0.0)) {
                        break Label_0339;
                    }
                }
                final Vec3 loc2 = BlockUtil.floorVec3(hub.getLoc().addVector(0.0, 1.0, 0.0));
                if (checkPositionValidity(loc2, false) && this.addHub(hub, loc2, 0.0)) {
                    break Label_0339;
                }
                final Vec3 loc3 = BlockUtil.floorVec3(hub.getLoc().addVector(0.0, -1.0, 0.0));
                if (checkPositionValidity(loc3, false) && this.addHub(hub, loc3, 0.0)) {
                    break Label_0339;
                }
            }
        }
        this.hubs.sort(new CompareHub());
        this.path = this.hubs.get(0).getPath();
    }
    
    public static boolean checkPositionValidity(final Vec3 loc, final boolean checkGround) {
        return checkPositionValidity((int)loc.xCoord, (int)loc.yCoord, (int)loc.zCoord, checkGround);
    }
    
    public static boolean checkPositionValidity(final int x, final int y, final int z, final boolean checkGround) {
        final BlockPos block1 = new BlockPos(x, y, z);
        final BlockPos block2 = new BlockPos(x, y + 1, z);
        final BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }
    
    private static boolean isBlockSolid(final BlockPos blockPos) {
        final Block block = BlockUtil.getBlock(blockPos);
        return block != null && (block.isFullBlock() || block instanceof BlockSlab || block instanceof BlockStairs || block instanceof BlockCactus || block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSkull || block instanceof BlockPane || block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockGlass || block instanceof BlockPistonBase || block instanceof BlockPistonExtension || block instanceof BlockPistonMoving || block instanceof BlockStainedGlass || block instanceof BlockTrapDoor);
    }
    
    private static boolean isSafeToWalkOn(final BlockPos blockPos) {
        final Block block = BlockUtil.getBlock(blockPos);
        return block != null && !(block instanceof BlockFence) && !(block instanceof BlockWall);
    }
    
    public Hub isHubExisting(final Vec3 loc) {
        for (final Hub hub : this.hubs) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        for (final Hub hub : this.hubsToWork) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        return null;
    }
    
    public boolean addHub(final Hub parent, final Vec3 loc, final double cost) {
        final Hub existingHub = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if ((loc.xCoord == this.endVec3.xCoord && loc.yCoord == this.endVec3.yCoord && loc.zCoord == this.endVec3.zCoord) || loc.squareDistanceTo(this.endVec3) <= 9.0) {
                this.path.clear();
                (this.path = parent.getPath()).add(loc);
                return true;
            }
            final ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(this.endVec3), cost, totalCost));
        }
        else if (existingHub.getCost() > cost) {
            final ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(this.endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }
    
    static {
        flatCardinalDirections = new Vec3[] { new Vec3(1.0, 0.0, 0.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 0.0, -1.0) };
    }
    
    private class Hub
    {
        private Vec3 loc;
        private Hub parent;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;
        
        public Hub(final Vec3 loc, final Hub parent, final ArrayList<Vec3> path, final double squareDistanceToFromTarget, final double cost, final double totalCost) {
            this.loc = null;
            this.parent = null;
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }
        
        public Vec3 getLoc() {
            return this.loc;
        }
        
        public Hub getParent() {
            return this.parent;
        }
        
        public ArrayList<Vec3> getPath() {
            return this.path;
        }
        
        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }
        
        public double getCost() {
            return this.cost;
        }
        
        public void setLoc(final Vec3 loc) {
            this.loc = loc;
        }
        
        public void setParent(final Hub parent) {
            this.parent = parent;
        }
        
        public void setPath(final ArrayList<Vec3> path) {
            this.path = path;
        }
        
        public void setSquareDistanceToFromTarget(final double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }
        
        public void setCost(final double cost) {
            this.cost = cost;
        }
        
        public double getTotalCost() {
            return this.totalCost;
        }
        
        public void setTotalCost(final double totalCost) {
            this.totalCost = totalCost;
        }
    }
    
    public class CompareHub implements Comparator<Hub>
    {
        @Override
        public int compare(final Hub o1, final Hub o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }
}

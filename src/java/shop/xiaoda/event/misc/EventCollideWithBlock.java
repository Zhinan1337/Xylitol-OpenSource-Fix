//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.misc;

import shop.xiaoda.event.api.events.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EventCollideWithBlock implements Event
{
    public AxisAlignedBB boundingBox;
    private Block block;
    private BlockPos blockPos;
    private final int x;
    private final int y;
    private final int z;
    
    public EventCollideWithBlock(final Block block, final BlockPos pos, final AxisAlignedBB boundingBox) {
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
        this.x = this.blockPos.getX();
        this.y = this.blockPos.getY();
        this.z = this.blockPos.getZ();
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public BlockPos getPos() {
        return this.blockPos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}

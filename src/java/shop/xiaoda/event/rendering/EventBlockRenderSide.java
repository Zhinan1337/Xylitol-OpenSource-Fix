//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class EventBlockRenderSide implements Event
{
    public final BlockPos pos;
    private final IBlockAccess world;
    private final EnumFacing side;
    public double maxX;
    public double maxY;
    public double maxZ;
    public double minX;
    public double minY;
    public double minZ;
    private boolean toRender;
    
    public EventBlockRenderSide(final IBlockAccess world, final BlockPos pos, final EnumFacing side, final double maxX, final double minX, final double maxY, final double minY, final double maxZ, final double minZ) {
        this.world = world;
        this.pos = pos;
        this.side = side;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
    }
    
    public IBlockAccess getWorld() {
        return this.world;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getSide() {
        return this.side;
    }
    
    public boolean isToRender() {
        return this.toRender;
    }
    
    public void setToRender(final boolean toRender) {
        this.toRender = toRender;
    }
}

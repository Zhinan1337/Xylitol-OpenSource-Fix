//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.util.*;

public class BlockCache
{
    private final BlockPos position;
    private final EnumFacing facing;
    
    public BlockCache(final BlockPos position, final EnumFacing facing) {
        this.position = position;
        this.facing = facing;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
}

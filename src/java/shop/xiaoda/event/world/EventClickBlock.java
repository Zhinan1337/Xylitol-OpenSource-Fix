//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.*;
import net.minecraft.util.*;

public final class EventClickBlock implements Event
{
    private final BlockPos clickedBlock;
    private final EnumFacing enumFacing;
    
    public EventClickBlock(final BlockPos clickedBlock, final EnumFacing enumFacing) {
        this.clickedBlock = clickedBlock;
        this.enumFacing = enumFacing;
    }
    
    public BlockPos getClickedBlock() {
        return this.clickedBlock;
    }
    
    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.player;

import net.minecraft.util.*;

public class EnumFacingOffset
{
    public EnumFacing enumFacing;
    private final Vec3 offset;
    
    public EnumFacingOffset(final EnumFacing enumFacing, final Vec3 offset) {
        this.enumFacing = enumFacing;
        this.offset = offset;
    }
    
    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
    
    public Vec3 getOffset() {
        return this.offset;
    }
}

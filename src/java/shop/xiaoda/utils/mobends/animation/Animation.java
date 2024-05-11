//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation;

import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.data.*;

public abstract class Animation
{
    public abstract void animate(final EntityLivingBase p0, final ModelBase p1, final EntityData p2);
    
    public abstract String getName();
}

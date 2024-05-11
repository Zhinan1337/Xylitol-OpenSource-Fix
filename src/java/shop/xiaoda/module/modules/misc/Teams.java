//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.*;
import java.util.*;
import shop.xiaoda.utils.player.*;

public class Teams extends Module
{
    private static final BoolValue armorValue;
    private static final BoolValue colorValue;
    private static final BoolValue scoreboardValue;
    
    public Teams() {
        super("Teams", Category.Misc);
    }
    
    public static boolean isSameTeam(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entity;
            return Objects.requireNonNull(Client.instance.moduleManager.getModule("Teams")).getState() && ((Teams.armorValue.getValue() && PlayerUtil.armorTeam(entityPlayer)) || (Teams.colorValue.getValue() && PlayerUtil.colorTeam(entityPlayer)) || (Teams.scoreboardValue.getValue() && PlayerUtil.scoreTeam(entityPlayer)));
        }
        return false;
    }
    
    static {
        armorValue = new BoolValue("ArmorColor", true);
        colorValue = new BoolValue("Color", true);
        scoreboardValue = new BoolValue("ScoreboardTeam", true);
    }
}

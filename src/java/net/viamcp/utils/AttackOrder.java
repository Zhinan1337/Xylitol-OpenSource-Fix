//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.utils;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.viamcp.*;
import net.viamcp.protocols.*;

public class AttackOrder
{
    private static final Minecraft mc;
    private static final int VER_1_8_ID = 47;
    
    public static void sendFixedAttack(final EntityPlayer entityIn, final Entity target) {
        if (ViaMCP.getInstance().getVersion() <= ProtocolCollection.getProtocolById(47).getVersion()) {
            send1_8Attack(entityIn, target);
        }
        else {
            send1_9Attack(entityIn, target);
        }
    }
    
    private static void send1_8Attack(final EntityPlayer entityIn, final Entity target) {
        AttackOrder.mc.thePlayer.swingItem();
        AttackOrder.mc.playerController.attackEntity(entityIn, target);
    }
    
    private static void send1_9Attack(final EntityPlayer entityIn, final Entity target) {
        AttackOrder.mc.playerController.attackEntity(entityIn, target);
        AttackOrder.mc.thePlayer.swingItem();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}

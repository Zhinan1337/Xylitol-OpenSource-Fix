//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.component;

import shop.xiaoda.event.world.*;
import net.viamcp.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.*;
import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.event.*;

public class InventoryClickFixComponent
{
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (ViaMCP.getInstance().getVersion() > 47 && (event.getPacket() instanceof C0EPacketClickWindow || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C08PacketPlayerBlockPlacement) && (Client.mc.currentScreen instanceof GuiChest || Client.mc.currentScreen instanceof GuiInventory)) {
            PacketUtil.sendPacketC0F();
        }
    }
}

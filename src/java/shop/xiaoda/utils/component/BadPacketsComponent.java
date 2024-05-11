//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.component;

import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;

public final class BadPacketsComponent
{
    private static boolean slot;
    private static boolean attack;
    private static boolean swing;
    private static boolean block;
    private static boolean inventory;
    
    public static boolean bad() {
        return bad(true, true, true, true, true);
    }
    
    public static boolean bad(final boolean slot, final boolean attack, final boolean swing, final boolean block, final boolean inventory) {
        return (BadPacketsComponent.slot && slot) || (BadPacketsComponent.attack && attack) || (BadPacketsComponent.swing && swing) || (BadPacketsComponent.block && block) || (BadPacketsComponent.inventory && inventory);
    }
    
    @EventTarget(4)
    public void onPacketSend(final EventPacketSend event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof C09PacketHeldItemChange) {
            BadPacketsComponent.slot = true;
        }
        else if (packet instanceof C0APacketAnimation) {
            BadPacketsComponent.swing = true;
        }
        else if (packet instanceof C02PacketUseEntity) {
            BadPacketsComponent.attack = true;
        }
        else if (packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging) {
            BadPacketsComponent.block = true;
        }
        else if (packet instanceof C0EPacketClickWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) || packet instanceof C0DPacketCloseWindow) {
            BadPacketsComponent.inventory = true;
        }
        else if (packet instanceof C03PacketPlayer) {
            reset();
        }
    }
    
    public static void reset() {
        BadPacketsComponent.slot = false;
        BadPacketsComponent.swing = false;
        BadPacketsComponent.attack = false;
        BadPacketsComponent.block = false;
        BadPacketsComponent.inventory = false;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;

public class CancelVelocity extends VelocityMode
{
    public CancelVelocity() {
        super("Cancel", Category.Combat);
    }
    
    @Override
    public String getTag() {
        return "Cancel";
    }
    
    @Override
    public void onAttack(final EventAttack event) {
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onPacketSend(final EventPacketSend event) {
    }
    
    @Override
    public void onWorldLoad(final EventWorldLoad event) {
    }
    
    @EventTarget
    @Override
    public void onPacketReceive(final EventPacketReceive e) {
        if (this.mc.thePlayer == null) {
            return;
        }
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packetEntityVelocity = (S12PacketEntityVelocity)e.getPacket();
            if (packetEntityVelocity.getEntityID() != this.mc.thePlayer.getEntityId()) {
                return;
            }
            e.setCancelled(true);
        }
        if (e.getPacket() instanceof S27PacketExplosion) {
            e.setCancelled(true);
        }
    }
    
    @Override
    public void onUpdate(final EventUpdate event) {
    }
}

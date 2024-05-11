//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;

public class HypixelVelocity extends VelocityMode
{
    public HypixelVelocity() {
        super("Hypixel", Category.Combat);
    }
    
    @Override
    public String getTag() {
        return "Watchdog";
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
            if (this.mc.thePlayer.onGround || packetEntityVelocity.getMotionY() / 8000.0 < 0.2 || packetEntityVelocity.getMotionY() / 8000.0 > 0.41995) {
                this.mc.thePlayer.motionY = packetEntityVelocity.getMotionY() / 8000.0;
            }
        }
    }
    
    @Override
    public void onUpdate(final EventUpdate event) {
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import shop.xiaoda.module.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.*;

public class AACVelocity extends VelocityMode
{
    boolean shouldCancel;
    
    public AACVelocity() {
        super("AAC", Category.Combat);
    }
    
    @Override
    public String getTag() {
        return Velocity.aacModes.getValue().name();
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
    
    @Override
    public void onUpdate(final EventUpdate event) {
    }
    
    @EventTarget
    @Override
    public void onPacketReceive(final EventPacketReceive e) {
        if (this.mc.thePlayer == null) {
            return;
        }
        final Packet<?> packet = (Packet<?>)e.getPacket();
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packetEntityVelocity = (S12PacketEntityVelocity)e.getPacket();
            if (packetEntityVelocity.getEntityID() != this.mc.thePlayer.getEntityId()) {
                return;
            }
            if (Velocity.aacModes.getValue() == velMode.AAC5_2_0) {
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, Double.MAX_VALUE, this.mc.thePlayer.posZ, true));
                e.setCancelled();
            }
        }
        if (Velocity.aacModes.getValue() == velMode.AAC5 && this.shouldCancel && packet instanceof S08PacketPlayerPosLook) {
            PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, 1.5E30, this.mc.thePlayer.posZ, true));
            PacketUtil.send((Packet<?>)new C03PacketPlayer.C05PacketPlayerLook(((S08PacketPlayerPosLook)packet).getYaw(), ((S08PacketPlayerPosLook)packet).getPitch(), this.mc.thePlayer.onGround));
            this.shouldCancel = false;
        }
        if (e.getPacket() instanceof S27PacketExplosion) {
            if (Velocity.aacModes.getValue() == velMode.AAC5) {
                e.setCancelled();
                this.shouldCancel = true;
            }
            if (Velocity.aacModes.getValue() == velMode.AAC5_2_0) {
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, Double.MAX_VALUE, this.mc.thePlayer.posZ, true));
                e.setCancelled();
            }
        }
    }
    
    public enum velMode
    {
        AAC5_2_0, 
        AAC5;
    }
}

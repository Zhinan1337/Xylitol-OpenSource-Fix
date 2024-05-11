//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;

public class SuperKnockback extends Module
{
    private final ModeValue<KnockBackMode> modeValue;
    private final BoolValue onlyMoveValue;
    private final BoolValue onlyGroundValue;
    
    public SuperKnockback() {
        super("SuperKnockback", Category.Combat);
        this.modeValue = new ModeValue<KnockBackMode>("Mode", KnockBackMode.values(), KnockBackMode.Vanilla);
        this.onlyMoveValue = new BoolValue("OnlyMove", true);
        this.onlyGroundValue = new BoolValue("OnlyGround", false);
    }
    
    @EventTarget
    public void onAttack(final EventAttack event) {
        if ((!MoveUtil.isMoving() && this.onlyMoveValue.getValue()) || (!SuperKnockback.mc.thePlayer.onGround && this.onlyGroundValue.getValue())) {
            return;
        }
        switch (this.modeValue.getValue()) {
            case Vanilla: {
                if (SuperKnockback.mc.thePlayer.isSprinting()) {
                    SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.thePlayer.setSprinting(true);
                SuperKnockback.mc.thePlayer.serverSprintState = true;
                break;
            }
            case SneakPacket: {
                if (SuperKnockback.mc.thePlayer.isSprinting()) {
                    SuperKnockback.mc.thePlayer.setSprinting(true);
                }
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                SuperKnockback.mc.thePlayer.serverSprintState = true;
                break;
            }
            case ExtraPacket: {
                if (SuperKnockback.mc.thePlayer.isSprinting()) {
                    SuperKnockback.mc.thePlayer.setSprinting(true);
                }
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                SuperKnockback.mc.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)SuperKnockback.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                SuperKnockback.mc.thePlayer.setSprinting(true);
                SuperKnockback.mc.thePlayer.serverSprintState = true;
                break;
            }
        }
    }
    
    public enum KnockBackMode
    {
        Vanilla, 
        SneakPacket, 
        ExtraPacket;
    }
}

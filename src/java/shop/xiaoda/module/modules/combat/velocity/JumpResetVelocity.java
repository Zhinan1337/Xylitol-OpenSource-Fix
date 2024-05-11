//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;

public class JumpResetVelocity extends VelocityMode
{
    public JumpResetVelocity() {
        super("JumpReset", Category.Combat);
    }
    
    @Override
    public String getTag() {
        return "JumpReset";
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
        if (this.mc.thePlayer.onGround && this.mc.thePlayer.hurtTime > 0) {
            this.mc.thePlayer.setSprinting(false);
            this.mc.thePlayer.movementInput.jump = true;
        }
    }
    
    @EventTarget
    @Override
    public void onPacketReceive(final EventPacketReceive e) {
    }
}

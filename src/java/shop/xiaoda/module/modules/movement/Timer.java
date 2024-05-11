//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.component.*;
import shop.xiaoda.event.world.*;

public class Timer extends Module
{
    private final NumberValue speedValue;
    private final BoolValue onMoveValue;
    private final BoolValue grimTimer;
    private final BoolValue spartanBypass;
    private final BoolValue timerDebug;
    int balance;
    boolean blinkStart;
    private final StopWatch stopWatch;
    
    public Timer() {
        super("Timer", Category.Movement);
        this.speedValue = new NumberValue("Speed", 2.0, 0.1, 10.0, 0.1);
        this.onMoveValue = new BoolValue("OnMove", true);
        this.grimTimer = new BoolValue("Grim-Timer[Balance]", false);
        this.spartanBypass = new BoolValue("Spartan-Grim-Timer[Balance-Bypass]", false);
        this.timerDebug = new BoolValue("Grim-Timer[Balance-Debug]", false, this.grimTimer::getValue);
        this.balance = 0;
        this.stopWatch = new StopWatch();
    }
    
    public void onDisable() {
        if (Timer.mc.thePlayer == null) {
            return;
        }
        Timer.mc.timer.timerSpeed = 1.0f;
        this.balance = 0;
        this.reset();
        if (this.blinkStart) {
            if (this.spartanBypass.getValue()) {
                Timer.mc.timer.timerSpeed = 0.1f;
            }
            BlinkUtils.setBlinkState(true, true, false, false, false, false, false, false, false, false, false);
            BlinkUtils.clearPacket(null, false, -1);
            if (this.spartanBypass.getValue()) {
                Timer.mc.timer.timerSpeed = 1.0f;
            }
        }
    }
    
    public void onEnable() {
        this.reset();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.onMoveValue.getValue()) {
            Timer.mc.timer.timerSpeed = (MoveUtil.isMoving() ? this.speedValue.getValue().floatValue() : 1.0f);
        }
        else {
            Timer.mc.timer.timerSpeed = (this.spartanBypass.getValue() ? ((float)MathUtil.getRandom(0.1, this.speedValue.getValue())) : this.speedValue.getValue().floatValue());
        }
        if (this.balance > 0 && this.grimTimer.getValue() && this.timerDebug.getValue() && Timer.mc.thePlayer.ticksExisted % 20 == 0) {
            DebugUtil.log("[GrimTimer-Balance]:" + this.balance);
        }
        if (!MoveUtil.isMoving()) {
            BlinkUtils.setBlinkState(false, false, false, false, true, true, false, false, false, false, false);
            this.blinkStart = true;
        }
    }
    
    private void reset() {
        this.balance = 0;
        this.stopWatch.reset();
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook && this.grimTimer.getValue() && this.balance != 0) {
            this.balance = 0;
            if (this.blinkStart) {
                if (this.spartanBypass.getValue()) {
                    Timer.mc.timer.timerSpeed = 0.1f;
                }
                BlinkUtils.setBlinkState(true, true, false, false, false, false, false, false, false, false, false);
                BlinkUtils.clearPacket(null, false, -1);
                if (this.spartanBypass.getValue()) {
                    Timer.mc.timer.timerSpeed = 1.0f;
                }
            }
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer && this.grimTimer.getValue()) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)event.getPacket();
            event.setCancelled(!c03PacketPlayer.getRotating() && !c03PacketPlayer.isMoving() && !BadPacketsComponent.bad() && Timer.mc.thePlayer.posX == Timer.mc.thePlayer.lastTickPosX && Timer.mc.thePlayer.posY == Timer.mc.thePlayer.lastTickPosY && Timer.mc.thePlayer.posZ == Timer.mc.thePlayer.lastTickPosZ);
            if (!event.isCancelled()) {
                this.balance -= 50;
            }
            this.balance += (int)this.stopWatch.getElapsedTime();
            this.stopWatch.reset();
        }
    }
    
    @EventTarget
    public void onWorld(final EventWorldLoad event) {
        this.reset();
        this.state = false;
    }
}

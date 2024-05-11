
package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.event.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.gui.notification.*;

public class TimerRange extends Module
{
    private int playerTicks;
    private int smartCounter;
    private boolean confirmAttack;
    private boolean confirmLagBack;
    private final ModeValue<mode> timerBoostMode;
    private final NumberValue ticksValue;
    private final NumberValue timerBoostValue;
    private final NumberValue timerChargedValue;
    private final NumberValue rangeValue;
    private final NumberValue minRange;
    private final NumberValue maxRange;
    private final NumberValue minTickDelay;
    private final NumberValue maxTickDelay;
    private final BoolValue resetlagBack;
    
    public TimerRange() {
        super("TimerRange", Category.Combat);
        this.playerTicks = 0;
        this.smartCounter = 0;
        this.confirmAttack = false;
        this.confirmLagBack = false;
        this.timerBoostMode = new ModeValue<mode>("TimerMode", mode.values(), mode.Normal);
        this.ticksValue = new NumberValue("Ticks", 10.0, 1.0, 20.0, 1.0);
        this.timerBoostValue = new NumberValue("TimerBoost", 1.5, 0.01, 35.0, 0.01);
        this.timerChargedValue = new NumberValue("TimerCharged", 0.45, 0.05, 5.0, 0.01);
        this.rangeValue = new NumberValue("Range", 3.5, 1.0, 5.0, 0.1, () -> this.timerBoostMode.is("Normal"));
        this.minRange = new NumberValue("MinRange", 1.0, 1.0, 5.0, 0.1, () -> this.timerBoostMode.is("Smart"));
        this.maxRange = new NumberValue("MaxRange", 5.0, 1.0, 5.0, 0.1, () -> this.timerBoostMode.is("Smart"));
        this.minTickDelay = new NumberValue("MinTickDelay", 5.0, 1.0, 100.0, 1.0, () -> this.timerBoostMode.is("Smart"));
        this.maxTickDelay = new NumberValue("MaxTickDelay", 100.0, 1.0, 100.0, 1.0, () -> this.timerBoostMode.is("Smart"));
        this.resetlagBack = new BoolValue("ResetOnLagback", false);
    }
    
    public void onEnable() {
        this.timerReset();
    }
    
    public void onDisable() {
        this.timerReset();
        this.smartCounter = 0;
        this.playerTicks = 0;
    }
    
    @EventTarget
    public void onAttack(final EventAttack event) {
        if (!(event.getTarget() instanceof EntityLivingBase) || this.shouldResetTimer()) {
            this.timerReset();
            return;
        }
        this.confirmAttack = true;
        final EntityLivingBase targetEntity = (EntityLivingBase)event.getTarget();
        final double entityDistance = TimerRange.mc.thePlayer.getClosestDistanceToEntity((Entity)targetEntity);
        final int randomCounter = MathUtil.getRandomNumberUsingNextInt(this.minTickDelay.getValue().intValue(), this.maxTickDelay.getValue().intValue());
        final double randomRange = MathUtil.getRandomInRange(this.minRange.getValue(), this.maxRange.getValue());
        ++this.smartCounter;
        final String name = this.timerBoostMode.getValue().name();
        boolean shouldSlowed = false;
        switch (name) {
            case "Normal": {
                shouldSlowed = (entityDistance <= this.rangeValue.getValue());
                break;
            }
            case "Smart": {
                shouldSlowed = (this.smartCounter >= randomCounter && entityDistance <= randomRange);
                break;
            }
            default: {
                shouldSlowed = false;
                break;
            }
        }
        if (shouldSlowed && this.confirmAttack) {
            this.confirmAttack = false;
            this.playerTicks = this.ticksValue.getValue().intValue();
            if (this.resetlagBack.getValue()) {
                this.confirmLagBack = true;
            }
            this.smartCounter = 0;
        }
        else {
            this.timerReset();
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        this.setSuffix(this.timerBoostMode.getValue().name());
        final double timerboost = MathUtil.getRandomInRange(0.5, 0.56);
        final double charged = MathUtil.getRandomInRange(0.75, 0.91);
        if (this.playerTicks <= 0) {
            this.timerReset();
            return;
        }
        final double tickProgress = this.playerTicks / this.ticksValue.getValue();
        final float playerSpeed = (float)((tickProgress < timerboost) ? this.timerBoostValue.getValue() : ((tickProgress < charged) ? this.timerChargedValue.getValue() : 1.0));
        final float speedAdjustment = (playerSpeed >= 0.0f) ? playerSpeed : ((float)(1.0 + this.ticksValue.getValue() - this.playerTicks));
        final float adjustedTimerSpeed = Math.max(speedAdjustment, 0.0f);
        TimerRange.mc.timer.timerSpeed = adjustedTimerSpeed;
        --this.playerTicks;
    }
    
    private void timerReset() {
        TimerRange.mc.timer.timerSpeed = 1.0f;
    }
    
    private boolean shouldResetTimer() {
        final EntityPlayerSP player = TimerRange.mc.thePlayer;
        return this.playerTicks >= 1 || player.isSpectator() || player.isDead || player.isInWater() || player.isInLava() || player.isInWeb || player.isOnLadder() || player.isRiding();
    }
    
    @EventTarget
    public void onPacket(final EventPacketReceive event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook && this.resetlagBack.getValue() && this.confirmLagBack && !this.shouldResetTimer()) {
            this.confirmLagBack = false;
            this.timerReset();
            NotificationManager.post(NotificationType.WARNING, "TimerRange", "Lagback Detected | Timer Reset");
        }
    }
    
    public enum mode
    {
        Normal, 
        Smart;
    }
}

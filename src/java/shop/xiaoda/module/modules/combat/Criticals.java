//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.attack.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import shop.xiaoda.utils.client.*;

public class Criticals extends Module
{
    private final TimeUtil timer;
    private final TimeUtil prevent;
    private final ModeValue<modeEnums> modeValue;
    private final NumberValue hurtTimeValue;
    private final NumberValue delayValue;
    private int groundTicks;
    
    public Criticals() {
        super("Criticals", Category.Combat);
        this.timer = new TimeUtil();
        this.prevent = new TimeUtil();
        this.modeValue = new ModeValue<modeEnums>("Mode", modeEnums.values(), modeEnums.Hypixel);
        this.hurtTimeValue = new NumberValue("HurtTime", 15.0, 0.0, 20.0, 1.0);
        this.delayValue = new NumberValue("Delay", 3.0, 0.0, 10.0, 0.5);
    }
    
    public void onEnable() {
        this.timer.reset();
        this.prevent.reset();
        this.groundTicks = 0;
    }
    
    @EventTarget
    void onUpdate(final EventMotion event) {
        this.setSuffix(this.modeValue.getValue().toString());
        if (PlayerUtil.isOnGround(0.01)) {
            ++this.groundTicks;
        }
        else {
            this.groundTicks = 0;
        }
        if (this.groundTicks > 20) {
            this.groundTicks = 20;
        }
        if (this.modeValue.getValue() == modeEnums.NoGround) {
            event.setOnGround(false);
        }
    }
    
    @EventTarget
    void onStep(final EventStep event) {
        if (!event.isPre()) {
            this.prevent.reset();
        }
    }
    
    @EventTarget
    void onAttack(final EventAttack event) {
        final boolean canCrit = this.groundTicks > 3 && Criticals.mc.theWorld.getBlockState(new BlockPos(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY - 1.0, Criticals.mc.thePlayer.posZ)).getBlock().isFullBlock() && !PlayerUtil.isInLiquid() && !PlayerUtil.isOnLiquid() && !Criticals.mc.thePlayer.isOnLadder() && Criticals.mc.thePlayer.ridingEntity == null && Criticals.mc.thePlayer.onGround;
        if (event.isPre() && canCrit && event.getTarget().hurtResistantTime <= this.hurtTimeValue.getValue().intValue() && this.prevent.hasPassed(300L) && this.timer.hasPassed(this.delayValue.getValue().intValue() * 100L)) {
            final String lowerCase = this.modeValue.getValue().toString().toLowerCase();
            switch (lowerCase) {
                case "hypixel": {
                    final double[] array;
                    final double[] values = array = new double[] { 0.0625 + Math.random() / 100.0, 0.03125 + Math.random() / 100.0 };
                    for (final double value : array) {
                        Criticals.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + value, Criticals.mc.thePlayer.posZ, false));
                    }
                    break;
                }
                case "hvh": {
                    for (final double offset : new double[] { 0.06253453, 0.02253453, 0.001253453, 1.135346E-4 }) {
                        Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + offset, Criticals.mc.thePlayer.posZ, false));
                    }
                    break;
                }
                case "packet": {
                    final double[] values = { 0.0425, 0.0015, MathUtil.getRandom().nextBoolean() ? 0.012 : 0.014 };
                    if (Criticals.mc.thePlayer.ticksExisted % 2 == 0) {
                        for (final double value : values) {
                            final double random = MathUtil.getRandom().nextBoolean() ? MathUtil.getRandom(-1.0E-8, -1.0E-7) : MathUtil.getRandom(1.0E-7, 1.0E-8);
                            Criticals.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + value + random, Criticals.mc.thePlayer.posZ, false));
                        }
                        break;
                    }
                    break;
                }
                case "visual": {
                    Criticals.mc.thePlayer.onCriticalHit(event.getTarget());
                    break;
                }
                case "jump": {
                    Criticals.mc.thePlayer.jump();
                    break;
                }
                case "hop": {
                    Criticals.mc.thePlayer.motionY = 0.1;
                    Criticals.mc.thePlayer.fallDistance = 0.1f;
                    Criticals.mc.thePlayer.onGround = false;
                    break;
                }
            }
        }
    }
    
    private enum modeEnums
    {
        Packet, 
        Hypixel, 
        HvH, 
        Hop, 
        Jump, 
        Visual, 
        NoGround;
    }
}

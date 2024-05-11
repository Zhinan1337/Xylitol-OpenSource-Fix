//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.viamcp.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.event.*;

public class Regen extends Module
{
    private final ModeValue<regenMode> modeValue;
    private final NumberValue healthValue;
    private final NumberValue foodValue;
    private final NumberValue speedValue;
    private final BoolValue noAirValue;
    private final BoolValue potionEffectValue;
    private boolean resetTimer;
    
    public Regen() {
        super("Regen", Category.Player);
        this.modeValue = new ModeValue<regenMode>("Mode", regenMode.values(), regenMode.Vanilla);
        this.healthValue = new NumberValue("Health", 18.0, 0.0, 20.0, 1.0);
        this.foodValue = new NumberValue("Food", 18.0, 0.0, 20.0, 1.0);
        this.speedValue = new NumberValue("Speed", 100.0, 1.0, 100.0, 1.0);
        this.noAirValue = new BoolValue("NoAir", false);
        this.potionEffectValue = new BoolValue("PotionEffect", false);
        this.resetTimer = false;
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        this.setSuffix(this.modeValue.get());
        if (this.resetTimer) {
            Regen.mc.timer.timerSpeed = 1.0f;
        }
        this.resetTimer = false;
        if ((!this.noAirValue.getValue() || Regen.mc.thePlayer.onGround) && !Regen.mc.thePlayer.capabilities.isCreativeMode && Regen.mc.thePlayer.getFoodStats().getFoodLevel() > this.foodValue.getValue() && Regen.mc.thePlayer.isEntityAlive() && Regen.mc.thePlayer.getHealth() < this.healthValue.getValue()) {
            if (this.potionEffectValue.getValue() && !Regen.mc.thePlayer.isPotionActive(Potion.regeneration)) {
                return;
            }
            switch (this.modeValue.get()) {
                case Vanilla: {
                    for (int i = 0; i < this.speedValue.getValue(); ++i) {
                        PacketUtil.send((Packet<?>)new C03PacketPlayer(Regen.mc.thePlayer.onGround));
                    }
                    break;
                }
                case Grim1_17: {
                    if (ViaMCP.getInstance().getVersion() > 755) {
                        return;
                    }
                    for (int i = 0; i < this.speedValue.getValue(); ++i) {
                        PacketUtil.send((Packet<?>)new C03PacketPlayer.C06PacketPlayerPosLook(Regen.mc.thePlayer.posX, Regen.mc.thePlayer.posY, Regen.mc.thePlayer.posZ, Regen.mc.thePlayer.rotationYaw, Regen.mc.thePlayer.rotationPitch, Regen.mc.thePlayer.onGround));
                    }
                    break;
                }
                case AAC4NoFire: {
                    if (Regen.mc.thePlayer.isBurning() && Regen.mc.thePlayer.ticksExisted % 10 == 0) {
                        for (int i = 0; i < 35; ++i) {
                            PacketUtil.send((Packet<?>)new C03PacketPlayer(true));
                        }
                        break;
                    }
                    break;
                }
                case NewSpartan: {
                    if (Regen.mc.thePlayer.ticksExisted % 5 == 0) {
                        this.resetTimer = true;
                        Regen.mc.timer.timerSpeed = 0.98f;
                        for (int i = 0; i < 10; ++i) {
                            PacketUtil.send((Packet<?>)new C03PacketPlayer(true));
                        }
                        break;
                    }
                    if (MoveUtil.isMoving()) {
                        PacketUtil.send((Packet<?>)new C03PacketPlayer(Regen.mc.thePlayer.onGround));
                        break;
                    }
                    break;
                }
                case OldSpartan: {
                    if (MoveUtil.isMoving() || !Regen.mc.thePlayer.onGround) {
                        return;
                    }
                    for (int i = 0; i < 9; ++i) {
                        PacketUtil.send((Packet<?>)new C03PacketPlayer(Regen.mc.thePlayer.onGround));
                    }
                    Regen.mc.timer.timerSpeed = 0.45f;
                    this.resetTimer = true;
                    break;
                }
            }
        }
    }
    
    public enum regenMode
    {
        Vanilla, 
        Grim1_17, 
        OldSpartan, 
        NewSpartan, 
        AAC4NoFire;
    }
}

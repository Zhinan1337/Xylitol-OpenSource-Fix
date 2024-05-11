//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;

public final class FastEat extends Module
{
    public final ModeValue<eatModes> modeValue;
    private final BoolValue noMoveValue;
    private final NumberValue delayValue;
    private final NumberValue customSpeedValue;
    private final NumberValue customTimer;
    private final TimeUtil msTimer;
    private boolean usedTimer;
    public boolean grimEat;
    
    public FastEat() {
        super("FastEat", Category.Player);
        this.modeValue = new ModeValue<eatModes>("Mode", eatModes.values(), eatModes.Grim);
        this.noMoveValue = new BoolValue("NoMove", false);
        this.delayValue = new NumberValue("CustomDelay", 0.0, 0.0, 300.0, 1.0);
        this.customSpeedValue = new NumberValue("CustomSpeed", 2.0, 1.0, 35.0, 1.0);
        this.customTimer = new NumberValue("CustomTimer", 1.1, 0.5, 2.0, 0.1);
        this.msTimer = new TimeUtil();
        this.usedTimer = false;
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (FastEat.mc.thePlayer == null) {
            return;
        }
        if (this.usedTimer) {
            FastEat.mc.timer.timerSpeed = 1.0f;
            this.usedTimer = false;
        }
        if (!FastEat.mc.thePlayer.isUsingItem()) {
            this.msTimer.reset();
            return;
        }
        final Item usingItem = FastEat.mc.thePlayer.getItemInUse().getItem();
        if (usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || (usingItem instanceof ItemPotion && !(FastEat.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
            final eatModes mode = this.modeValue.getValue();
            switch (mode) {
                case Instant: {
                    for (int i = 0; i < 35; ++i) {
                        FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(FastEat.mc.thePlayer.onGround));
                    }
                    FastEat.mc.playerController.onStoppedUsingItem((EntityPlayer)FastEat.mc.thePlayer);
                    break;
                }
                case NCP: {
                    if (FastEat.mc.thePlayer.getItemInUseDuration() > 14) {
                        for (int i = 0; i < 20; ++i) {
                            FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(FastEat.mc.thePlayer.onGround));
                        }
                        FastEat.mc.playerController.onStoppedUsingItem((EntityPlayer)FastEat.mc.thePlayer);
                        break;
                    }
                    break;
                }
                case AAC: {
                    FastEat.mc.timer.timerSpeed = 1.22f;
                    this.usedTimer = true;
                    break;
                }
                case VulCan: {
                    if (FastEat.mc.thePlayer.onGround) {
                        FastEat.mc.timer.timerSpeed = 0.55f;
                        this.usedTimer = true;
                        FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(FastEat.mc.thePlayer.onGround));
                        FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(FastEat.mc.thePlayer.posX, FastEat.mc.thePlayer.posY, FastEat.mc.thePlayer.posZ, FastEat.mc.thePlayer.onGround));
                        break;
                    }
                    break;
                }
                case Grim: {
                    this.usedTimer = true;
                    this.grimEat = true;
                    FastEat.mc.timer.timerSpeed = 0.3f;
                    for (int i = 0; i < 2; ++i) {
                        final EntityPlayerSP thePlayer = FastEat.mc.thePlayer;
                        ++thePlayer.positionUpdateTicks;
                        FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(FastEat.mc.thePlayer.onGround));
                    }
                    this.grimEat = false;
                    break;
                }
                case CustomDelay: {
                    FastEat.mc.timer.timerSpeed = this.customTimer.getValue().floatValue();
                    this.usedTimer = true;
                    if (!this.msTimer.hasPassed(this.delayValue.getValue().longValue())) {
                        return;
                    }
                    for (int i = 0; i < this.customSpeedValue.getValue().intValue(); ++i) {
                        FastEat.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer(FastEat.mc.thePlayer.onGround));
                    }
                    this.msTimer.reset();
                    break;
                }
            }
        }
    }
    
    @EventTarget
    public void onMove(final EventMove event) {
        if (FastEat.mc.thePlayer == null || event == null) {
            return;
        }
        if (!this.getState() || !FastEat.mc.thePlayer.isUsingItem() || !this.noMoveValue.getValue()) {
            return;
        }
        final Item usingItem = FastEat.mc.thePlayer.getItemInUse().getItem();
        if (usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || (usingItem instanceof ItemPotion && !(FastEat.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
            event.setCancelled(true);
        }
    }
    
    public void onDisable() {
        if (this.usedTimer) {
            FastEat.mc.timer.timerSpeed = 1.0f;
            this.usedTimer = false;
        }
    }
    
    public enum eatModes
    {
        Instant, 
        NCP, 
        Grim, 
        VulCan, 
        AAC, 
        CustomDelay;
    }
}

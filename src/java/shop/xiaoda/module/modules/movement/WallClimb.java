//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.network.*;
import shop.xiaoda.event.misc.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class WallClimb extends Module
{
    private final ModeValue<WallClimbModes> modeValue;
    private final ModeValue<ClipMode> clipMode;
    private final NumberValue checkerClimbMotionValue;
    private final NumberValue verusClimbSpeed;
    private boolean glitch;
    private boolean canClimb;
    private int waited;
    
    public WallClimb() {
        super("WallClimb", Category.Movement);
        this.modeValue = new ModeValue<WallClimbModes>("Mode", WallClimbModes.values(), WallClimbModes.Simple);
        this.clipMode = new ModeValue<ClipMode>("ClipMode", ClipMode.values(), ClipMode.Fast, () -> this.modeValue.get().equals(WallClimbModes.Clip));
        this.checkerClimbMotionValue = new NumberValue("CheckerClimbMotion", 0.0, 0.0, 1.0, 0.1, () -> this.modeValue.get().equals(WallClimbModes.CheckerClimb));
        this.verusClimbSpeed = new NumberValue("VerusClimbSpeed", 0.0, 0.0, 1.0, 0.1, () -> this.modeValue.get().equals(WallClimbModes.Verus));
    }
    
    public void onEnable() {
        this.glitch = false;
        this.canClimb = false;
        this.waited = 0;
    }
    
    @EventTarget
    public void onMove(final EventMove event) {
        if (!WallClimb.mc.thePlayer.isCollidedHorizontally || WallClimb.mc.thePlayer.isOnLadder() || WallClimb.mc.thePlayer.isInWater() || WallClimb.mc.thePlayer.isInLava()) {
            return;
        }
        if (this.modeValue.getValue().equals(WallClimbModes.Simple)) {
            event.setY(0.2);
            WallClimb.mc.thePlayer.motionY = 0.0;
        }
    }
    
    @EventTarget
    public void onJump(final EventJump event) {
        if (this.modeValue.getValue().equals(WallClimbModes.Verus) && this.canClimb) {
            event.setCancelled();
        }
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        if (event.isPost()) {
            return;
        }
        switch (this.modeValue.getValue()) {
            case Clip: {
                if (WallClimb.mc.thePlayer.motionY < 0.0) {
                    this.glitch = true;
                }
                if (WallClimb.mc.thePlayer.isCollidedHorizontally) {
                    switch (this.clipMode.get()) {
                        case Jump: {
                            if (WallClimb.mc.thePlayer.onGround) {
                                WallClimb.mc.thePlayer.jump();
                                break;
                            }
                            break;
                        }
                        case Fast: {
                            if (WallClimb.mc.thePlayer.onGround) {
                                WallClimb.mc.thePlayer.motionY = 0.42;
                                break;
                            }
                            if (WallClimb.mc.thePlayer.motionY < 0.0) {
                                WallClimb.mc.thePlayer.motionY = -0.3;
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
                break;
            }
            case CheckerClimb: {
                final boolean isInsideBlock = BlockUtil.collideBlockIntersects(WallClimb.mc.thePlayer.getEntityBoundingBox(), block -> !(block instanceof BlockAir));
                final float motion = this.checkerClimbMotionValue.getValue().floatValue();
                if (isInsideBlock && motion != 0.0f) {
                    WallClimb.mc.thePlayer.motionY = motion;
                    break;
                }
                break;
            }
            case AAC_3_3_12: {
                if (WallClimb.mc.thePlayer.isCollidedHorizontally && !WallClimb.mc.thePlayer.isOnLadder()) {
                    ++this.waited;
                    if (this.waited == 1) {
                        WallClimb.mc.thePlayer.motionY = 0.43;
                    }
                    if (this.waited == 12) {
                        WallClimb.mc.thePlayer.motionY = 0.43;
                    }
                    if (this.waited == 23) {
                        WallClimb.mc.thePlayer.motionY = 0.43;
                    }
                    if (this.waited == 29) {
                        WallClimb.mc.thePlayer.setPosition(WallClimb.mc.thePlayer.posX, WallClimb.mc.thePlayer.posY + 0.5, WallClimb.mc.thePlayer.posZ);
                    }
                    if (this.waited >= 30) {
                        this.waited = 0;
                        break;
                    }
                    break;
                }
                else {
                    if (WallClimb.mc.thePlayer.onGround) {
                        this.waited = 0;
                        break;
                    }
                    break;
                }
            }
            case AACGlide: {
                if (!WallClimb.mc.thePlayer.isCollidedHorizontally || WallClimb.mc.thePlayer.isOnLadder()) {
                    return;
                }
                WallClimb.mc.thePlayer.motionY = -0.189;
                break;
            }
            case Verus: {
                if (!WallClimb.mc.thePlayer.isCollidedHorizontally || WallClimb.mc.thePlayer.isInWater() || WallClimb.mc.thePlayer.isInLava() || WallClimb.mc.thePlayer.isOnLadder() || WallClimb.mc.thePlayer.isInWeb || WallClimb.mc.thePlayer.isOnLadder()) {
                    this.canClimb = false;
                    break;
                }
                this.canClimb = true;
                WallClimb.mc.thePlayer.motionY = this.verusClimbSpeed.getValue();
                WallClimb.mc.thePlayer.onGround = true;
                break;
            }
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacketSend event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            if (this.glitch) {
                final float yaw = (float)MoveUtil.getDirection();
                packetPlayer.x -= MathHelper.sin(yaw) * 1.0E-8;
                packetPlayer.z += MathHelper.cos(yaw) * 1.0E-8;
                this.glitch = false;
            }
            if (this.canClimb) {
                packetPlayer.onGround = true;
            }
        }
    }
    
    @EventTarget
    public void onBlockBB(final EventCollideWithBlock event) {
        if (WallClimb.mc.thePlayer == null) {
            return;
        }
        switch (this.modeValue.getValue()) {
            case CheckerClimb: {
                if (event.getY() > WallClimb.mc.thePlayer.posY) {
                    event.setBoundingBox((AxisAlignedBB)null);
                    break;
                }
                break;
            }
            case Clip: {
                if (event.getBlock() != null && WallClimb.mc.thePlayer != null && event.getBlock() instanceof BlockAir && event.getY() < WallClimb.mc.thePlayer.posY && WallClimb.mc.thePlayer.isCollidedHorizontally && !WallClimb.mc.thePlayer.isOnLadder() && !WallClimb.mc.thePlayer.isInWater() && !WallClimb.mc.thePlayer.isInLava()) {
                    event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(WallClimb.mc.thePlayer.posX, (double)((int)WallClimb.mc.thePlayer.posY - 1), WallClimb.mc.thePlayer.posZ));
                    break;
                }
                break;
            }
        }
    }
    
    public enum WallClimbModes
    {
        Simple, 
        CheckerClimb, 
        Clip, 
        AAC_3_3_12, 
        AACGlide, 
        Verus;
    }
    
    public enum ClipMode
    {
        Jump, 
        Fast;
    }
}

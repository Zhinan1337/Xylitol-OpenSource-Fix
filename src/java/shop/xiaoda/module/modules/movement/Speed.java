//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import shop.xiaoda.utils.player.*;

public class Speed extends Module
{
    private static final ModeValue<speedMode> MODE;
    private static final ModeValue<grimMode> grimModeModeValue;
    public static BoolValue fastStop;
    private static boolean wasOnGround;
    
    public Speed() {
        super("Speed", Category.Movement);
    }
    
    public void onDisable() {
        if (Speed.mc.thePlayer == null) {
            return;
        }
        if (Speed.MODE.getValue() == speedMode.AAC4) {
            Speed.mc.timer.timerSpeed = 1.0f;
        }
    }
    
    @EventTarget
    public void update(final EventUpdate event) {
        switch (Speed.MODE.getValue()) {
            case Grim: {
                for (final Entity entity : Speed.mc.theWorld.loadedEntityList) {
                    double speed = 1.0;
                    switch (Speed.grimModeModeValue.getValue()) {
                        case HighSpeed: {
                            speed = 1.45;
                            break;
                        }
                        case Fast: {
                            speed = 1.3;
                            break;
                        }
                        case Normal: {
                            speed = 1.25;
                            break;
                        }
                        case Slow: {
                            speed = 1.2;
                            break;
                        }
                    }
                    if (Speed.fastStop.getValue() && !MoveUtil.isMoving()) {
                        MoveUtil.stop();
                    }
                    if (entity instanceof EntityLivingBase && entity.getEntityId() != Speed.mc.thePlayer.getEntityId() && Speed.mc.thePlayer.getDistanceToEntity(entity) <= 1.3 && Speed.mc.thePlayer.offGroundTicks >= 3.5) {
                        final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
                        thePlayer.motionX *= speed;
                        final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
                        thePlayer2.motionZ *= speed;
                        return;
                    }
                }
                break;
            }
        }
    }
    
    @EventTarget
    public void onMove(final EventMotion event) {
        this.setSuffix(Speed.MODE.getValue().name());
        if (event.isPre()) {
            Label_0745: {
                switch (Speed.MODE.getValue()) {
                    case HvH: {
                        if (MoveUtil.isMoving()) {
                            if (Speed.mc.thePlayer.onGround) {
                                Speed.mc.thePlayer.motionY = 0.42;
                                Speed.wasOnGround = true;
                            }
                            else if (Speed.wasOnGround) {
                                if (!Speed.mc.thePlayer.isCollidedHorizontally) {
                                    Speed.mc.thePlayer.motionY = -0.0484000015258789;
                                }
                                Speed.wasOnGround = false;
                            }
                            MoveUtil.setMotion(0.63);
                            break;
                        }
                        final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
                        final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
                        final double n = 0.0;
                        thePlayer2.motionZ = n;
                        thePlayer.motionX = n;
                        break;
                    }
                    case Vulcan: {
                        switch (Speed.mc.thePlayer.offGroundTicks) {
                            case 0: {
                                Speed.mc.thePlayer.jump();
                                if (Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                    MoveUtil.strafe(0.6);
                                    break;
                                }
                                MoveUtil.strafe(0.485);
                                break;
                            }
                            case 9: {
                                if (!(PlayerUtil.blockRelativeToPlayer(0.0, Speed.mc.thePlayer.motionY, 0.0) instanceof BlockAir)) {
                                    MoveUtil.strafe();
                                    break;
                                }
                                break;
                            }
                            case 1:
                            case 2: {
                                MoveUtil.strafe();
                                break;
                            }
                            case 5: {
                                Speed.mc.thePlayer.motionY = MoveUtil.predictedMotion(Speed.mc.thePlayer.motionY, 2);
                                break;
                            }
                        }
                        break;
                    }
                    case WatchDog: {
                        if (Speed.mc.thePlayer.hurtTime > 6) {
                            final EntityPlayerSP thePlayer3 = Speed.mc.thePlayer;
                            thePlayer3.motionX *= 1.007;
                            final EntityPlayerSP thePlayer4 = Speed.mc.thePlayer;
                            thePlayer4.motionZ *= 1.007;
                        }
                        if ((Speed.mc.gameSettings.keyBindLeft.pressed || Speed.mc.gameSettings.keyBindRight.pressed) && Speed.mc.thePlayer.motionY < -0.05 && Speed.mc.thePlayer.motionY > -0.08) {
                            MoveUtil.strafe(0.15);
                        }
                        if (Speed.mc.thePlayer.onGround && (Speed.mc.thePlayer.moveForward != 0.0f || Speed.mc.thePlayer.moveStrafing != 0.0f)) {
                            Speed.mc.thePlayer.jump();
                            MoveUtil.setSpeed((float)(0.46 + PlayerUtil.getSpeedPotion() * 0.02));
                            break;
                        }
                        break;
                    }
                    case AAC4: {
                        if (Speed.mc.thePlayer.isInWater()) {
                            return;
                        }
                        if (!MoveUtil.isMoving()) {
                            Speed.mc.thePlayer.motionX = 0.0;
                            Speed.mc.thePlayer.motionZ = 0.0;
                            break Label_0745;
                        }
                        if (Speed.mc.thePlayer.onGround) {
                            Speed.mc.gameSettings.keyBindJump.pressed = false;
                            Speed.mc.thePlayer.jump();
                        }
                        if (!Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.fallDistance <= 0.1) {
                            Speed.mc.thePlayer.speedInAir = 0.03f;
                            Speed.mc.timer.timerSpeed = 1.45f;
                        }
                        if (Speed.mc.thePlayer.fallDistance > 0.1 && Speed.mc.thePlayer.fallDistance < 1.3) {
                            Speed.mc.thePlayer.speedInAir = 0.0105f;
                            Speed.mc.timer.timerSpeed = 0.7f;
                        }
                        if (Speed.mc.thePlayer.fallDistance >= 1.3) {
                            Speed.mc.timer.timerSpeed = 1.0f;
                            Speed.mc.thePlayer.speedInAir = 0.0105f;
                        }
                        break Label_0745;
                    }
                    case Pika: {
                        if (MoveUtil.isMoving()) {
                            if (Speed.mc.thePlayer.onGround) {
                                Speed.mc.thePlayer.motionY = 0.42;
                                Speed.wasOnGround = true;
                            }
                            else if (Speed.wasOnGround) {
                                Speed.wasOnGround = false;
                            }
                            MoveUtil.setMotion(MoveUtil.getBaseMoveSpeed() * 0.6000000238418579, Speed.mc.thePlayer.getRotationYawHead());
                            break;
                        }
                        final EntityPlayerSP thePlayer5 = Speed.mc.thePlayer;
                        thePlayer5.motionX *= 0.8;
                        final EntityPlayerSP thePlayer6 = Speed.mc.thePlayer;
                        thePlayer6.motionZ *= 0.8;
                        break;
                    }
                }
            }
        }
    }
    
    static {
        MODE = new ModeValue<speedMode>("Mode", speedMode.values(), speedMode.HvH);
        grimModeModeValue = new ModeValue<grimMode>("GrimMode", grimMode.values(), grimMode.Fast, () -> Speed.MODE.getValue().equals(speedMode.Grim));
        Speed.fastStop = new BoolValue("FastStop", true, () -> Speed.MODE.getValue().equals(speedMode.Grim));
        Speed.wasOnGround = false;
    }
    
    private enum grimMode
    {
        HighSpeed, 
        Fast, 
        Normal, 
        Slow;
    }
    
    enum speedMode
    {
        HvH, 
        Pika, 
        WatchDog, 
        Grim, 
        Vulcan, 
        AAC4;
    }
}

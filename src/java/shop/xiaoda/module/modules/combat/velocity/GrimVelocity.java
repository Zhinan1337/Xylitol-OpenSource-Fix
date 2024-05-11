//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import java.util.*;
import net.minecraft.network.play.*;
import net.minecraft.util.Timer;
import shop.xiaoda.module.*;
import net.minecraft.network.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.client.entity.*;
import net.viamcp.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.module.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.module.modules.misc.*;
import shop.xiaoda.*;
import net.minecraft.network.play.client.*;

public class GrimVelocity extends VelocityMode
{
    double motionNoXZ;
    boolean attacked;
    boolean velocityInput;
    public static boolean shouldCancel;
    int resetPersec;
    int grimTCancel;
    int updates;
    int cancelPacket;
    LinkedList<Packet<INetHandlerPlayClient>> inBus;
    boolean lastSprinting;
    
    public GrimVelocity() {
        super("Grim", Category.Combat);
        this.resetPersec = 8;
        this.grimTCancel = 0;
        this.updates = 0;
        this.cancelPacket = 6;
        this.inBus = new LinkedList<Packet<INetHandlerPlayClient>>();
    }
    
    @Override
    public void onEnable() {
        if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45) {
            this.grimTCancel = 0;
            this.inBus.clear();
        }
        GrimVelocity.shouldCancel = false;
    }
    
    @Override
    public void onDisable() {
        if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45) {
            while (this.inBus.size() > 0) {
                this.inBus.poll().processPacket(this.mc.getNetHandler());
            }
            this.grimTCancel = 0;
        }
        GrimVelocity.shouldCancel = false;
        if (this.mc.thePlayer.hurtTime > 0 && !this.mc.thePlayer.isOnLadder()) {
            PacketUtil.sendPacketC0F();
            final BlockPos pos = new BlockPos((Entity)this.mc.thePlayer);
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_43) {
                this.mc.timer.lastSyncSysClock = MathUtil.getRandom(32052, 89505);
                final Timer timer = this.mc.timer;
                --timer.elapsedPartialTicks;
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                ++thePlayer.positionUpdateTicks;
                PacketUtil.send((Packet<?>)new C03PacketPlayer(this.mc.thePlayer.onGround));
            }
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_43 || Velocity.grimModes.getValue() == velMode.GrimV_2_4_40) {
                PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
                PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
            }
        }
    }
    
    @Override
    public void onWorldLoad(final EventWorldLoad event) {
        GrimVelocity.shouldCancel = false;
        this.grimTCancel = 0;
        this.inBus.clear();
    }
    
    @Override
    public String getTag() {
        return Velocity.grimModes.getValue().name();
    }
    
    @Override
    public void onUpdate(final EventUpdate event) {
        if (this.mc.getNetHandler() == null) {
            return;
        }
        if (this.mc.theWorld == null) {
            return;
        }
        if (this.mc.thePlayer == null) {
            return;
        }
        if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_40) {
            ++this.updates;
            if (this.resetPersec > 0 && this.updates >= 0) {
                this.updates = 0;
                if (this.grimTCancel > 0) {
                    --this.grimTCancel;
                }
            }
        }
        if (GrimVelocity.shouldCancel && this.mc.thePlayer.hurtTime > 0) {
            PacketUtil.sendPacketC0F();
            final BlockPos pos = new BlockPos((Entity)this.mc.thePlayer);
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_43) {
                this.mc.timer.lastSyncSysClock = MathUtil.getRandom(32052, 89505);
                final Timer timer = this.mc.timer;
                --timer.elapsedPartialTicks;
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                ++thePlayer.positionUpdateTicks;
                PacketUtil.send((Packet<?>)new C03PacketPlayer(this.mc.thePlayer.onGround));
            }
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_43 || Velocity.grimModes.getValue() == velMode.GrimV_2_4_40) {
                PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
                PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
            }
            GrimVelocity.shouldCancel = false;
        }
        if (Velocity.grimModes.getValue() == velMode.Vertical) {
            if (ViaMCP.getInstance().getVersion() > 47) {
                if (this.velocityInput) {
                    if (this.attacked) {
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        thePlayer2.motionX *= this.motionNoXZ;
                        final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                        thePlayer3.motionZ *= this.motionNoXZ;
                        this.attacked = false;
                    }
                    else if (this.mc.thePlayer.hurtTime == 6 && this.mc.thePlayer.onGround && !this.mc.gameSettings.keyBindJump.isKeyDown()) {
                        this.mc.thePlayer.movementInput.jump = true;
                    }
                    if (this.mc.thePlayer.hurtTime == 0) {
                        this.velocityInput = false;
                    }
                }
            }
            else if (this.mc.thePlayer.hurtTime > 0 && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.addVelocity(-1.3E-10, -1.3E-10, -1.3E-10);
                this.mc.thePlayer.setSprinting(false);
            }
        }
        if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45) {
            if (this.resetPersec > 0 && this.updates >= 0) {
                this.updates = 0;
                if (this.grimTCancel > 0) {
                    --this.grimTCancel;
                }
            }
            if (this.grimTCancel == 0) {
                while (!this.inBus.isEmpty()) {
                    this.inBus.poll().processPacket(this.mc.getNetHandler());
                }
            }
        }
    }
    
    @Override
    public void onAttack(final EventAttack event) {
    }
    
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
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45 || Velocity.grimModes.getValue() == velMode.GrimV_2_3_40) {
                e.setCancelled(true);
                this.grimTCancel = ((Velocity.grimModes.getValue() == velMode.GrimV_2_3_45) ? 3 : this.cancelPacket);
            }
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_40 || Velocity.grimModes.getValue() == velMode.GrimV_2_4_43) {
                e.setCancelled(true);
                GrimVelocity.shouldCancel = true;
            }
            if (Velocity.grimModes.getValue() == velMode.Vertical && ViaMCP.getInstance().getVersion() > 47) {
                this.velocityInput = true;
                final MovingObjectPosition movingObjectPosition = this.mc.objectMouseOver;
                final EntityLivingBase targets = (EntityLivingBase)((Velocity.grimRayCastValue.getValue() && movingObjectPosition != null && MovingObjectPosition.entityHit == KillAura.target) ? MovingObjectPosition.entityHit : KillAura.target);
                if (this.mc.thePlayer.getDistanceToEntity((Entity)targets) <= Velocity.grimCheckRangeValue.getValue().floatValue()) {
                    for (int i = 0; i < Velocity.grimAttackPacketCountValue.getValue().intValue(); ++i) {
                        if (this.mc.thePlayer.serverSprintState && this.mc.thePlayer.isMoving()) {
                            PacketUtil.sendPacketC0F();
                            PacketUtil.send((Packet<?>)new C02PacketUseEntity((Entity)targets, C02PacketUseEntity.Action.ATTACK));
                            PacketUtil.send((Packet<?>)new C0APacketAnimation());
                        }
                        else {
                            PacketUtil.sendPacketC0F();
                            PacketUtil.send((Packet<?>)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                            this.mc.thePlayer.setSprinting(false);
                            PacketUtil.send((Packet<?>)new C02PacketUseEntity((Entity)targets, C02PacketUseEntity.Action.ATTACK));
                            PacketUtil.send((Packet<?>)new C0APacketAnimation());
                            PacketUtil.send((Packet<?>)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        }
                    }
                    this.attacked = true;
                    KillAura.isBlocking = false;
                    this.motionNoXZ = this.getMotionNoXZ(packetEntityVelocity);
                }
            }
            if (this.grimTCancel > 0 && packet instanceof S32PacketConfirmTransaction && Velocity.grimModes.getValue() == velMode.GrimV_2_3_40) {
                e.setCancelled(true);
                --this.grimTCancel;
            }
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45 && this.grimTCancel > 0 && packet.getClass().getSimpleName().startsWith("S") && !(packet instanceof S12PacketEntityVelocity) && !(packet instanceof S27PacketExplosion) && !(packet instanceof S03PacketTimeUpdate)) {
                e.setCancelled(true);
                this.inBus.add((Packet<INetHandlerPlayClient>)packet);
            }
        }
        if (e.getPacket() instanceof S27PacketExplosion) {
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45 || Velocity.grimModes.getValue() == velMode.GrimV_2_3_40) {
                e.setCancelled(true);
                this.grimTCancel = ((Velocity.grimModes.getValue() == velMode.GrimV_2_3_45) ? 3 : this.cancelPacket);
            }
            if (Velocity.grimModes.getValue() == velMode.GrimV_2_4_40 || Velocity.grimModes.getValue() == velMode.GrimV_2_4_43) {
                e.setCancelled(true);
                GrimVelocity.shouldCancel = true;
            }
        }
    }
    
    private double getMotionNoXZ(final S12PacketEntityVelocity packetEntityVelocity) {
        final double strength = new Vec3((double)packetEntityVelocity.getMotionX(), (double)packetEntityVelocity.getMotionY(), (double)packetEntityVelocity.getMotionZ()).lengthVector();
        double motionNoXZ;
        if (strength >= 20000.0) {
            if (this.mc.thePlayer.onGround) {
                motionNoXZ = 0.05425;
            }
            else {
                motionNoXZ = 0.065;
            }
        }
        else if (strength >= 5000.0) {
            if (this.mc.thePlayer.onGround) {
                motionNoXZ = 0.01625;
            }
            else {
                motionNoXZ = 0.0452;
            }
        }
        else {
            motionNoXZ = 0.0075;
        }
        return motionNoXZ;
    }
    
    @Override
    public void onPacketSend(final EventPacketSend e) {
        if (this.mc.thePlayer == null) {
            return;
        }
        final Packet<?> packet = (Packet<?>)e.getPacket();
        if (Velocity.grimModes.getValue() == velMode.Vertical && ViaMCP.getInstance().getVersion() > 47 && !((Disabler)Client.instance.moduleManager.getModule((Class)Disabler.class)).getState() && Disabler.modeValue.getValue() != Disabler.mode.Grim && !Disabler.badPacketsF.getValue() && packet instanceof C0BPacketEntityAction && this.velocityInput) {
            if (((C0BPacketEntityAction)packet).getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
                if (this.lastSprinting) {
                    e.setCancelled(true);
                }
                this.lastSprinting = true;
            }
            else if (((C0BPacketEntityAction)packet).getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                if (!this.lastSprinting) {
                    e.setCancelled(true);
                }
                this.lastSprinting = false;
            }
        }
        if (Velocity.grimModes.getValue() == velMode.GrimV_2_3_45 && this.grimTCancel > 0) {
            if (packet instanceof C0APacketAnimation) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C13PacketPlayerAbilities) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C08PacketPlayerBlockPlacement) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C07PacketPlayerDigging) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C02PacketUseEntity) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C0EPacketClickWindow) {
                PacketUtil.sendPacketC0F();
            }
            else if (packet instanceof C0BPacketEntityAction) {
                PacketUtil.sendPacketC0F();
            }
        }
    }
    
    public enum velMode
    {
        Vertical, 
        GrimV_2_4_43, 
        GrimV_2_4_40, 
        GrimV_2_3_45, 
        GrimV_2_3_40;
    }
}

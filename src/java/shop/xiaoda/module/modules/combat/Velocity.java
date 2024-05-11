//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

 
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.module.modules.combat.velocity.*;
import shop.xiaoda.event.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import shop.xiaoda.module.modules.player.*;
import shop.xiaoda.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.utils.*;
import net.minecraft.network.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.event.world.*;
import net.viamcp.*;


public class Velocity extends Module
{
    public static final ModeValue<velMode> modes;
    public static final ModeValue<GrimVelocity.velMode> grimModes;
    public static final BoolValue grimRayCastValue;
    public static final NumberValue grimCheckRangeValue;
    public static final NumberValue grimAttackPacketCountValue;
    public static final ModeValue<AACVelocity.velMode> aacModes;
    public BoolValue OnlyMove;
    public BoolValue OnlyGround;
    private final BoolValue BlinkCheck;
    private final BoolValue FireCheckValue;
    private final BoolValue WaterCheckValue;
    private final BoolValue S08FlagCheckValue;
    public NumberValue S08FlagTickValue;
    public BoolValue debugMessageValue;
    int flags;
    
    public Velocity() {
        super("Velocity", Category.Combat);
        this.OnlyMove = new BoolValue("OnlyMove", false);
        this.OnlyGround = new BoolValue("OnlyGround", false);
        this.BlinkCheck = new BoolValue("BlinkCheck", false);
        this.FireCheckValue = new BoolValue("FireCheck", false);
        this.WaterCheckValue = new BoolValue("WaterCheck", false);
        this.S08FlagCheckValue = new BoolValue("S08FlagCheck", false);
        this.S08FlagTickValue = new NumberValue("S08FlagTicks", 6.0, 0.0, 30.0, 1.0);
        this.debugMessageValue = new BoolValue("S08DebugMessage", false);
        VelocityMode.init();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (Velocity.mc.getNetHandler() == null) {
            return;
        }
        if (Velocity.mc.theWorld == null) {
            return;
        }
        if (Velocity.mc.thePlayer == null) {
            return;
        }
        if (this.S08FlagCheckValue.getValue() && this.flags > 0) {
            --this.flags;
        }
        final VelocityMode vel = VelocityMode.get(Velocity.modes.getValue().name());
        this.setSuffix(vel.getTag());
        vel.onUpdate(event);
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive e) {
        if (Velocity.mc.thePlayer == null) {
            return;
        }
        final Packet<?> packet = (Packet<?>)e.getPacket();
        if ((this.OnlyGround.getValue() && !Velocity.mc.thePlayer.onGround) || (this.OnlyMove.getValue() && !MoveUtil.isMoving()) || this.flags != 0) {
            return;
        }
        if (Velocity.mc.thePlayer.isDead) {
            return;
        }
        if (Velocity.mc.currentScreen instanceof GuiGameOver) {
            return;
        }
        if (Velocity.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
            return;
        }
        if (Velocity.mc.thePlayer.isBurning() && this.FireCheckValue.getValue()) {
            return;
        }
        if (Velocity.mc.thePlayer.isInWater() && this.WaterCheckValue.getValue()) {
            return;
        }
        if (Velocity.mc.thePlayer.isOnLadder()) {
            return;
        }
        if ((Client.instance.moduleManager.getModule((Class)Blink.class)).getState() && this.BlinkCheck.getValue()) {
            return;
        }
        if (packet instanceof S08PacketPlayerPosLook && this.S08FlagCheckValue.getValue()) {
            this.flags = this.S08FlagTickValue.getValue().intValue();
            if (this.debugMessageValue.getValue()) {
                DebugUtil.log(true, "VelocityDebug S08 Flags");
            }
        }
        final VelocityMode vel = VelocityMode.get(Velocity.modes.getValue().name());
        vel.onPacketReceive(e);
    }
    
    @EventTarget
    public void onAttack(final EventAttack e) {
        final VelocityMode vel = VelocityMode.get(Velocity.modes.getValue().name());
        vel.onAttack(e);
    }
    
    @EventTarget
    public void onWorldLoad(final EventWorldLoad e) {
        final VelocityMode vel = VelocityMode.get(Velocity.modes.getValue().name());
        vel.onWorldLoad(e);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend e) {
        if (Velocity.mc.thePlayer == null) {
            return;
        }
        if ((this.OnlyGround.getValue() && !Velocity.mc.thePlayer.onGround) || (this.OnlyMove.getValue() && !MoveUtil.isMoving()) || this.flags != 0) {
            return;
        }
        if (Velocity.mc.thePlayer.isDead) {
            return;
        }
        if (Velocity.mc.currentScreen instanceof GuiGameOver) {
            return;
        }
        if (Velocity.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
            return;
        }
        if (Velocity.mc.thePlayer.isBurning() && this.FireCheckValue.getValue()) {
            return;
        }
        if (Velocity.mc.thePlayer.isInWater() && this.WaterCheckValue.getValue()) {
            return;
        }
        if (Velocity.mc.thePlayer.isOnLadder()) {
            return;
        }
        if ((Client.instance.moduleManager.getModule((Class)Blink.class)).getState() && this.BlinkCheck.getValue()) {
            return;
        }
        final VelocityMode vel = VelocityMode.get(Velocity.modes.getValue().name());
        vel.onPacketSend(e);
    }
    
    static {
        modes = new ModeValue<velMode>("Mode", velMode.values(), velMode.Cancel);
        grimModes = new ModeValue<GrimVelocity.velMode>("GrimMode", GrimVelocity.velMode.values(), GrimVelocity.velMode.Vertical, () -> Velocity.modes.getValue().equals(velMode.Grim));
        grimRayCastValue = new BoolValue("Grim-RayCast", false, () -> ViaMCP.getInstance().getVersion() > 47 && Velocity.grimModes.getValue().equals(GrimVelocity.velMode.Vertical));
        grimCheckRangeValue = new NumberValue("Grim-CheckRange", 4.0, 2.0, 6.0, 0.1, () -> ViaMCP.getInstance().getVersion() > 47 && Velocity.grimModes.getValue().equals(GrimVelocity.velMode.Vertical));
        grimAttackPacketCountValue = new NumberValue("Grim-AttackPacket-Count", 12.0, 5.0, 50.0, 1.0, () -> ViaMCP.getInstance().getVersion() > 47 && Velocity.grimModes.getValue().equals(GrimVelocity.velMode.Vertical));
        aacModes = new ModeValue<AACVelocity.velMode>("AACMode", AACVelocity.velMode.values(), AACVelocity.velMode.AAC5, () -> Velocity.modes.getValue().equals(velMode.AAC));
    }
    
    public enum velMode
    {
        Grim, 
        AAC, 
        Cancel, 
        JumpReset, 
        Hypixel;
    }
}

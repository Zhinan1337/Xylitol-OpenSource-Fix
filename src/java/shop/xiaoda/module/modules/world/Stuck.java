//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.gui.notification.*;
import javax.vecmath.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.world.*;

public class Stuck extends Module
{
    private static Stuck INSTANCE;
    public BoolValue antiSB;
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private boolean onGround;
    private Vector2f rotation;
    
    public Stuck() {
        super("Stuck", Category.World);
        this.antiSB = new BoolValue("Anti SB", true);
        this.onGround = false;
        Stuck.INSTANCE = this;
    }
    
    public void onEnable() {
        if (Stuck.mc.thePlayer == null) {
            return;
        }
        this.onGround = Stuck.mc.thePlayer.onGround;
        this.x = Stuck.mc.thePlayer.posX;
        this.y = Stuck.mc.thePlayer.posY;
        this.z = Stuck.mc.thePlayer.posZ;
        this.motionX = Stuck.mc.thePlayer.motionX;
        this.motionY = Stuck.mc.thePlayer.motionY;
        this.motionZ = Stuck.mc.thePlayer.motionZ;
        this.rotation = new Vector2f(Stuck.mc.thePlayer.rotationYaw, Stuck.mc.thePlayer.rotationPitch);
        final float f = Stuck.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 1.2f;
        final Vector2f rotation = this.rotation;
        rotation.x -= this.rotation.x % gcd;
        final Vector2f rotation2 = this.rotation;
        rotation2.y -= this.rotation.y % gcd;
    }
    
    public void onDisable() {
        if (this.antiSB.getValue() && !Stuck.mc.thePlayer.onGround) {
            NotificationManager.post(NotificationType.WARNING, "Stuck", "You can't disable this module now!");
            this.setState(true);
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacketSend event) {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement)event.getPacket();
            final Vector2f current = new Vector2f(Stuck.mc.thePlayer.rotationYaw, Stuck.mc.thePlayer.rotationPitch);
            final float f = Stuck.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float gcd = f * f * f * 1.2f;
            final Vector2f vector2f = current;
            vector2f.x -= current.x % gcd;
            final Vector2f vector2f2 = current;
            vector2f2.y -= current.y % gcd;
            if (this.rotation.equals((Tuple2f)current)) {
                return;
            }
            this.rotation = current;
            event.setCancelled(true);
            PacketUtil.sendPacketNoEvent((Packet<?>)new C03PacketPlayer.C05PacketPlayerLook(current.x, current.y, this.onGround));
            PacketUtil.sendPacketNoEvent((Packet<?>)new C08PacketPlayerBlockPlacement(Stuck.mc.thePlayer.getHeldItem()));
        }
        if (event.getPacket() instanceof C03PacketPlayer) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onPacketR(final EventPacketReceive event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            this.setStateSilent(false);
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        Stuck.mc.thePlayer.motionX = 0.0;
        Stuck.mc.thePlayer.motionY = 0.0;
        Stuck.mc.thePlayer.motionZ = 0.0;
        Stuck.mc.thePlayer.setPosition(this.x, this.y, this.z);
    }
    
    public static boolean isStuck() {
        return Stuck.INSTANCE.getState();
    }
    
    public static void throwPearl(final Vector2f current) {
        if (!Stuck.INSTANCE.getState()) {
            return;
        }
        Stuck.mc.thePlayer.rotationYaw = current.x;
        Stuck.mc.thePlayer.rotationPitch = current.y;
        final float f = Stuck.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float gcd = f * f * f * 1.2f;
        current.x -= current.x % gcd;
        current.y -= current.y % gcd;
        if (!Stuck.INSTANCE.rotation.equals((Tuple2f)current)) {
            PacketUtil.sendPacketNoEvent((Packet<?>)new C03PacketPlayer.C05PacketPlayerLook(current.x, current.y, Stuck.INSTANCE.onGround));
        }
        Stuck.INSTANCE.rotation = current;
        PacketUtil.sendPacketNoEvent((Packet<?>)new C08PacketPlayerBlockPlacement(Stuck.mc.thePlayer.getHeldItem()));
    }
}

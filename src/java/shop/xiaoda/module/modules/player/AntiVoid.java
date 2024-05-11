//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.world.*;
import net.minecraft.util.*;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.world.*;
import net.minecraft.item.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.component.*;
import shop.xiaoda.utils.*;

public class AntiVoid extends Module
{
    private final NumberValue distance;
    private final BoolValue toggleScaffold;
    private Scaffold scaffold;
    private int overVoidTicks;
    private Vec3 position;
    private Vec3 motion;
    private boolean wasVoid;
    private boolean setBack;
    boolean shouldStuck;
    double x;
    double y;
    double z;
    boolean wait;
    ModeValue<modeEnum> mode;
    
    public AntiVoid() {
        super("AntiVoid", Category.Player);
        this.distance = new NumberValue("Distance", 5.0, 0.0, 10.0, 1.0);
        this.toggleScaffold = new BoolValue("Toggle Scaffold", true);
        this.z = 0.0;
        this.mode = new ModeValue<modeEnum>("Mode", modeEnum.values(), modeEnum.Grim);
    }
    
    public void onDisable() {
        AntiVoid.mc.thePlayer.isDead = false;
    }
    
    @EventTarget
    public void onPacket(final EventPacketSend event) {
        if (!AntiVoid.mc.thePlayer.onGround && this.shouldStuck && event.getPacket() instanceof C03PacketPlayer && !(event.packet instanceof C03PacketPlayer.C05PacketPlayerLook) && !(event.packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
            final C03PacketPlayer c03 = (C03PacketPlayer)event.getPacket();
            event.setCancelled();
        }
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement && this.wait) {
            this.shouldStuck = false;
            AntiVoid.mc.timer.timerSpeed = 0.2f;
            this.wait = false;
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacketReceive event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook)event.getPacket();
            this.x = s08.getX();
            this.y = s08.getY();
            this.z = s08.getZ();
            AntiVoid.mc.timer.timerSpeed = 0.2f;
        }
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        if (event.isPost()) {
            return;
        }
        if (AntiVoid.mc.thePlayer.getHeldItem() == null) {
            AntiVoid.mc.timer.timerSpeed = 1.0f;
        }
        if (AntiVoid.mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) {
            this.wait = true;
        }
        if (this.shouldStuck && !AntiVoid.mc.thePlayer.onGround) {
            AntiVoid.mc.thePlayer.motionX = 0.0;
            AntiVoid.mc.thePlayer.motionY = 0.0;
            AntiVoid.mc.thePlayer.motionZ = 0.0;
            AntiVoid.mc.thePlayer.setPositionAndRotation(this.x, this.y, this.z, AntiVoid.mc.thePlayer.rotationYaw, AntiVoid.mc.thePlayer.rotationPitch);
        }
        final boolean overVoid = !AntiVoid.mc.thePlayer.onGround && !PlayerUtil.isBlockUnder(30.0, true);
        if (!overVoid) {
            this.shouldStuck = false;
            this.x = AntiVoid.mc.thePlayer.posX;
            this.y = AntiVoid.mc.thePlayer.posY;
            this.z = AntiVoid.mc.thePlayer.posZ;
            AntiVoid.mc.timer.timerSpeed = 1.0f;
        }
        if (overVoid) {
            ++this.overVoidTicks;
        }
        else if (AntiVoid.mc.thePlayer.onGround) {
            this.overVoidTicks = 0;
        }
        if (overVoid && this.position != null && this.motion != null && this.overVoidTicks < 30.0 + this.distance.getValue() * 20.0) {
            if (!this.setBack) {
                this.wasVoid = true;
                if (FallDistanceComponent.distance > this.distance.getValue() || this.setBack) {
                    FallDistanceComponent.distance = 0.0f;
                    this.setBack = true;
                    DebugUtil.log(1);
                    this.shouldStuck = true;
                    this.x = AntiVoid.mc.thePlayer.posX;
                    this.y = AntiVoid.mc.thePlayer.posY;
                    this.z = AntiVoid.mc.thePlayer.posZ;
                }
            }
        }
        else {
            if (this.shouldStuck) {
                this.toggle();
            }
            this.shouldStuck = false;
            AntiVoid.mc.timer.timerSpeed = 1.0f;
            this.setBack = false;
            if (this.wasVoid) {
                this.wasVoid = false;
            }
            this.motion = new Vec3(AntiVoid.mc.thePlayer.motionX, AntiVoid.mc.thePlayer.motionY, AntiVoid.mc.thePlayer.motionZ);
            this.position = new Vec3(AntiVoid.mc.thePlayer.posX, AntiVoid.mc.thePlayer.posY, AntiVoid.mc.thePlayer.posZ);
        }
    }
    
    enum modeEnum
    {
        Grim;
    }
}

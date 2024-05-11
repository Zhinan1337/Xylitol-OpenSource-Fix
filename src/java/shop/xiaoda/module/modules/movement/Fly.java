//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.module.modules.world.*;
import shop.xiaoda.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.misc.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.util.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.rendering.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import java.util.*;

public class Fly extends Module
{
    private final ModeValue<flyModes> flyMode;
    private boolean started;
    private boolean notUnder;
    private boolean clipped;
    private boolean teleport;
    private final LinkedList<double[]> positions;
    private final TimeUtil pulseTimer;
    
    public Fly() {
        super("Fly", Category.Movement);
        this.flyMode = new ModeValue<flyModes>("FlyMode", flyModes.values(), flyModes.Vanilla);
        this.positions = new LinkedList<double[]>();
        this.pulseTimer = new TimeUtil();
    }
    
    public void onDisable() {
        Fly.mc.timer.timerSpeed = 1.0f;
        if (this.flyMode.getValue() == flyModes.GrimGhostBlock) {
            synchronized (this.positions) {
                this.positions.clear();
            }
            if (Fly.mc.thePlayer == null) {
                return;
            }
            BlinkUtils.setBlinkState(true, true, false, false, false, false, false, false, false, false, false);
            (Client.instance.moduleManager.getModule((Class)Scaffold.class)).setState(false);
            BlinkUtils.clearPacket(null, false, -1);
        }
    }
    
    public void onEnable() {
        if (this.flyMode.getValue() == flyModes.DoMCer) {
            DebugUtil.log("\u9700\u8981\u9876\u5934");
            this.notUnder = false;
            this.started = false;
            this.clipped = false;
            this.teleport = false;
        }
        if (this.flyMode.getValue() == flyModes.GrimGhostBlock) {
            if (Fly.mc.thePlayer == null) {
                return;
            }
            BlinkUtils.setBlinkState(false, false, true, false, false, false, false, false, false, false, false);
            synchronized (this.positions) {
                this.positions.add(new double[] { Fly.mc.thePlayer.posX, Fly.mc.thePlayer.getEntityBoundingBox().minY + Fly.mc.thePlayer.getEyeHeight() / 2.0f, Fly.mc.thePlayer.posZ });
                this.positions.add(new double[] { Fly.mc.thePlayer.posX, Fly.mc.thePlayer.getEntityBoundingBox().minY, Fly.mc.thePlayer.posZ });
            }
            (Client.instance.moduleManager.getModule((Class)Scaffold.class)).setState(true);
            this.pulseTimer.reset();
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (this.flyMode.getValue() == flyModes.GrimGhostBlock && (event.getPacket() instanceof C16PacketClientStatus || event.getPacket() instanceof C00PacketKeepAlive)) {
            event.setCancelled();
        }
    }
    
    @EventTarget
    public void onTP(final EventTeleport event) {
        if (this.teleport) {
            event.setCancelled(true);
            this.teleport = false;
            DebugUtil.log("Teleported");
            this.toggle();
        }
    }
    
    @EventTarget
    public void onStrafe(final EventStrafe event) {
        if (this.flyMode.getValue().equals(flyModes.DoMCer)) {
            final AxisAlignedBB bb = Fly.mc.thePlayer.getEntityBoundingBox().offset(0.0, 1.0, 0.0);
            if (Fly.mc.theWorld.getCollidingBoundingBoxes((Entity)Fly.mc.thePlayer, bb).isEmpty() || this.started) {
                switch (Fly.mc.thePlayer.offGroundTicks) {
                    case 0: {
                        if (this.notUnder && this.clipped) {
                            this.started = true;
                            event.setSpeed(10.0);
                            Fly.mc.thePlayer.motionY = 0.41999998688697815;
                            this.notUnder = false;
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (this.started) {
                            event.setSpeed(9.6);
                            break;
                        }
                        break;
                    }
                }
            }
            else {
                this.notUnder = true;
                if (this.clipped) {
                    return;
                }
                this.clipped = true;
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C06PacketPlayerPosLook(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, Fly.mc.thePlayer.rotationYaw, Fly.mc.thePlayer.rotationPitch, false));
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C06PacketPlayerPosLook(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY - 0.1, Fly.mc.thePlayer.posZ, Fly.mc.thePlayer.rotationYaw, Fly.mc.thePlayer.rotationPitch, false));
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C06PacketPlayerPosLook(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, Fly.mc.thePlayer.rotationYaw, Fly.mc.thePlayer.rotationPitch, false));
                this.teleport = true;
            }
            MoveUtil.strafe();
            Fly.mc.timer.timerSpeed = 0.4f;
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.flyMode.getValue() == flyModes.GrimGhostBlock) {
            this.setSuffix(("GhostBlock-Blink:" + BlinkUtils.bufferSize(null)));
            synchronized (this.positions) {
                this.positions.add(new double[] { Fly.mc.thePlayer.posX, Fly.mc.thePlayer.getEntityBoundingBox().minY, Fly.mc.thePlayer.posZ });
            }
            if (Fly.mc.thePlayer.ticksExisted % 2 == 1) {
                PacketUtil.sendPacketC0F(true);
            }
            if (this.pulseTimer.hasReached(2900.0)) {
                synchronized (this.positions) {
                    this.positions.clear();
                }
                BlinkUtils.releasePacket(null, false, -1, 0);
                this.pulseTimer.reset();
            }
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        if (this.flyMode.getValue() == flyModes.GrimGhostBlock) {
            synchronized (this.positions) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                Fly.mc.entityRenderer.disableLightmap();
                GL11.glLineWidth(2.0f);
                GL11.glBegin(3);
                RenderUtil.glColor(new Color(68, 131, 123, 255).getRGB());
                final double renderPosX = Fly.mc.getRenderManager().viewerPosX;
                final double renderPosY = Fly.mc.getRenderManager().viewerPosY;
                final double renderPosZ = Fly.mc.getRenderManager().viewerPosZ;
                for (final double[] pos : this.positions) {
                    GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
                }
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                GL11.glEnd();
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        }
    }
    
    public enum flyModes
    {
        Vanilla, 
        DoMCer, 
        GrimGhostBlock;
    }
}

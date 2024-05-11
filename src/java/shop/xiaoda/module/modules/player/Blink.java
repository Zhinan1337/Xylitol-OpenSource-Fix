//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import net.minecraft.client.entity.*;
import java.util.concurrent.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.event.rendering.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import java.util.*;

public class Blink extends Module
{
    private final ModeValue<PacketModeValues> packetModeValue;
    private final BoolValue pulseValue;
    private final NumberValue pulseDelayValue;
    private final BoolValue noC00C16;
    private final BoolValue packetKickBypass;
    private final TimeUtil pulseTimer;
    private static EntityOtherPlayerMP fakePlayer;
    private final LinkedList<double[]> positions;
    private final LinkedBlockingQueue<Packet<INetHandlerPlayClient>> packets;
    
    public Blink() {
        super("Blink", Category.Player);
        this.packetModeValue = new ModeValue<PacketModeValues>("PacketMode", PacketModeValues.values(), PacketModeValues.All);
        this.pulseValue = new BoolValue("Pulse", false);
        this.pulseDelayValue = new NumberValue("PulseDelay", 1000.0, 100.0, 5000.0, 100.0);
        this.noC00C16 = new BoolValue("NoC00-C16", false);
        this.packetKickBypass = new BoolValue("PacketKickBypass", false);
        this.pulseTimer = new TimeUtil();
        this.positions = new LinkedList<double[]>();
        this.packets = new LinkedBlockingQueue<Packet<INetHandlerPlayClient>>();
    }
    
    public void onEnable() {
        if (Blink.mc.thePlayer == null) {
            return;
        }
        if (this.packetModeValue.getValue() == PacketModeValues.OutGoing || this.packetModeValue.getValue() == PacketModeValues.All) {
            BlinkUtils.setBlinkState(false, false, true, false, false, false, false, false, false, false, false);
            if (!this.pulseValue.getValue()) {
                (Blink.fakePlayer = new EntityOtherPlayerMP((World)Blink.mc.theWorld, Blink.mc.thePlayer.gameProfile)).clonePlayer((EntityPlayer)Blink.mc.thePlayer, true);
                Blink.fakePlayer.copyLocationAndAnglesFrom((Entity)Blink.mc.thePlayer);
                Blink.fakePlayer.rotationYawHead = Blink.mc.thePlayer.rotationYawHead;
                Blink.mc.theWorld.addEntityToWorld(-1337, (Entity)Blink.fakePlayer);
            }
        }
        this.packets.clear();
        synchronized (this.positions) {
            this.positions.add(new double[] { Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY + Blink.mc.thePlayer.getEyeHeight() / 2.0f, Blink.mc.thePlayer.posZ });
            this.positions.add(new double[] { Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY, Blink.mc.thePlayer.posZ });
        }
        this.pulseTimer.reset();
    }
    
    public void onDisable() {
        synchronized (this.positions) {
            this.positions.clear();
        }
        if (Blink.mc.thePlayer == null) {
            return;
        }
        BlinkUtils.setBlinkState(true, true, false, false, false, false, false, false, false, false, false);
        BlinkUtils.clearPacket(null, false, -1);
        this.packets.clear();
        if (this.packetModeValue.getValue() == PacketModeValues.InBound || this.packetModeValue.getValue() == PacketModeValues.All) {
            this.clearPackets();
        }
        if (Blink.fakePlayer != null) {
            Blink.mc.theWorld.removeEntityFromWorld(Blink.fakePlayer.getEntityId());
            Blink.fakePlayer = null;
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        this.setSuffix(BlinkUtils.bufferSize(null));
        synchronized (this.positions) {
            this.positions.add(new double[] { Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY, Blink.mc.thePlayer.posZ });
        }
        if (this.packetKickBypass.getValue() && Blink.mc.thePlayer.ticksExisted % 2 == 1) {
            PacketUtil.sendPacketC0F(true);
        }
        if (this.pulseValue.getValue() && this.pulseTimer.hasReached((double)this.pulseDelayValue.getValue().longValue())) {
            synchronized (this.positions) {
                this.positions.clear();
            }
            BlinkUtils.releasePacket(null, false, -1, 0);
            if (this.packetModeValue.getValue() == PacketModeValues.InBound || this.packetModeValue.getValue() == PacketModeValues.All) {
                this.clearPackets();
            }
            this.pulseTimer.reset();
        }
    }
    
    private void clearPackets() {
        while (!this.packets.isEmpty()) {
            try {
                PacketUtil.handlePacket(this.packets.take());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive event) {
        final Packet<INetHandlerPlayClient> packet = (Packet<INetHandlerPlayClient>)event.getPacket();
        if ((this.packetModeValue.getValue() == PacketModeValues.InBound || this.packetModeValue.getValue() == PacketModeValues.All) && packet.getClass().getSimpleName().startsWith("S")) {
            if (Blink.mc.thePlayer.ticksExisted < 20) {
                return;
            }
            event.setCancelled(true);
            this.packets.add(packet);
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if ((this.packetModeValue.getValue() == PacketModeValues.OutGoing || this.packetModeValue.getValue() == PacketModeValues.All) && (event.getPacket() instanceof C16PacketClientStatus || (event.getPacket() instanceof C00PacketKeepAlive && this.noC00C16.getValue()))) {
            event.setCancelled();
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        if (this.packetModeValue.getValue() == PacketModeValues.OutGoing || this.packetModeValue.getValue() == PacketModeValues.All) {
            synchronized (this.positions) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                Blink.mc.entityRenderer.disableLightmap();
                GL11.glLineWidth(2.0f);
                GL11.glBegin(3);
                RenderUtil.glColor(new Color(68, 131, 123, 255).getRGB());
                final double renderPosX = Blink.mc.getRenderManager().viewerPosX;
                final double renderPosY = Blink.mc.getRenderManager().viewerPosY;
                final double renderPosZ = Blink.mc.getRenderManager().viewerPosZ;
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
    
    static {
        Blink.fakePlayer = null;
    }
    
    public enum PacketModeValues
    {
        All, 
        InBound, 
        OutGoing;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.component;

import java.util.concurrent.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.*;
import java.util.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.server.*;

public final class PingSpoofComponent
{
    public static final ConcurrentLinkedQueue<PacketUtil.TimedPacket> incomingPackets;
    public static final ConcurrentLinkedQueue<PacketUtil.TimedPacket> outgoingPackets;
    private static final TimeUtil stopWatch;
    public static boolean spoofing;
    public static int delay;
    public static boolean normal;
    public static boolean teleport;
    public static boolean velocity;
    public static boolean world;
    public static boolean entity;
    public static boolean client;
    
    @EventTarget(0)
    public void onUpdate(final EventUpdate event) {
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
            if (System.currentTimeMillis() > packet.getTime() + (PingSpoofComponent.spoofing ? PingSpoofComponent.delay : 0)) {
                try {
                    PacketUtil.receivePacketNoEvent(packet.getPacket());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                PingSpoofComponent.incomingPackets.remove(packet);
            }
        }
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
            if (System.currentTimeMillis() > packet.getTime() + (PingSpoofComponent.spoofing ? PingSpoofComponent.delay : 0)) {
                try {
                    PacketUtil.sendPacketNoEvent(packet.getPacket());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                PingSpoofComponent.outgoingPackets.remove(packet);
            }
        }
        if (PingSpoofComponent.stopWatch.delay(60.0f) || Client.mc.thePlayer.ticksExisted <= 20 || !Client.mc.getNetHandler().doneLoadingTerrain) {
            PingSpoofComponent.spoofing = false;
            for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
                PacketUtil.receivePacketNoEvent(packet.getPacket());
                PingSpoofComponent.incomingPackets.remove(packet);
            }
            for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
                PacketUtil.sendPacketNoEvent(packet.getPacket());
                PingSpoofComponent.outgoingPackets.remove(packet);
            }
        }
    }
    
    @EventTarget(0)
    public void onWorldLoad(final EventWorldLoad event) {
        PingSpoofComponent.incomingPackets.clear();
        PingSpoofComponent.stopWatch.reset();
        PingSpoofComponent.spoofing = false;
    }
    
    public static void dispatch() {
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
            try {
                PacketUtil.receivePacketNoEvent(packet.getPacket());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            PingSpoofComponent.incomingPackets.remove(packet);
        }
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
            PacketUtil.sendPacketNoEvent(packet.getPacket());
            PingSpoofComponent.outgoingPackets.remove(packet);
        }
    }
    
    public static void setSpoofing(final int delay, final boolean normal, final boolean teleport, final boolean velocity, final boolean world, final boolean entity) {
        PingSpoofComponent.spoofing = true;
        PingSpoofComponent.delay = delay;
        PingSpoofComponent.normal = normal;
        PingSpoofComponent.teleport = teleport;
        PingSpoofComponent.velocity = velocity;
        PingSpoofComponent.world = world;
        PingSpoofComponent.entity = entity;
        PingSpoofComponent.client = false;
        PingSpoofComponent.stopWatch.reset();
    }
    
    public static void setSpoofing(final int delay, final boolean normal, final boolean teleport, final boolean velocity, final boolean world, final boolean entity, final boolean client) {
        PingSpoofComponent.spoofing = true;
        PingSpoofComponent.delay = delay;
        PingSpoofComponent.normal = normal;
        PingSpoofComponent.teleport = teleport;
        PingSpoofComponent.velocity = velocity;
        PingSpoofComponent.world = world;
        PingSpoofComponent.entity = entity;
        PingSpoofComponent.client = client;
        PingSpoofComponent.stopWatch.reset();
    }
    
    @EventTarget(0)
    public void onPacketSend(final EventPacketSend event) {
        if (!PingSpoofComponent.client || !PingSpoofComponent.spoofing) {
            return;
        }
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof C03PacketPlayer || packet instanceof C16PacketClientStatus || packet instanceof C0DPacketCloseWindow || packet instanceof C0EPacketClickWindow || packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity || packet instanceof C0APacketAnimation || packet instanceof C09PacketHeldItemChange || packet instanceof C18PacketSpectate || packet instanceof C19PacketResourcePackStatus || packet instanceof C17PacketCustomPayload || packet instanceof C15PacketClientSettings || packet instanceof C14PacketTabComplete || packet instanceof C07PacketPlayerDigging || packet instanceof C08PacketPlayerBlockPlacement) {
            PingSpoofComponent.outgoingPackets.add(new PacketUtil.TimedPacket((Packet)packet, System.currentTimeMillis()));
            event.setCancelled(true);
        }
    }
    
    @EventTarget(0)
    public void onPacketReceive(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof S01PacketJoinGame) {
            PingSpoofComponent.incomingPackets.clear();
            PingSpoofComponent.stopWatch.reset();
            PingSpoofComponent.spoofing = false;
        }
        if (PingSpoofComponent.spoofing && Client.mc.getNetHandler().doneLoadingTerrain && (((packet instanceof S32PacketConfirmTransaction || packet instanceof S00PacketKeepAlive) && PingSpoofComponent.normal) || ((packet instanceof S08PacketPlayerPosLook || packet instanceof S09PacketHeldItemChange) && PingSpoofComponent.teleport) || (((packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).getEntityID() == Client.mc.thePlayer.getEntityId()) || packet instanceof S27PacketExplosion) && PingSpoofComponent.velocity) || ((packet instanceof S26PacketMapChunkBulk || packet instanceof S21PacketChunkData || packet instanceof S23PacketBlockChange || packet instanceof S22PacketMultiBlockChange) && PingSpoofComponent.world) || ((packet instanceof S13PacketDestroyEntities || packet instanceof S14PacketEntity || packet instanceof S18PacketEntityTeleport || packet instanceof S20PacketEntityProperties || packet instanceof S19PacketEntityHeadLook) && PingSpoofComponent.entity))) {
            PingSpoofComponent.incomingPackets.add(new PacketUtil.TimedPacket((Packet)packet, System.currentTimeMillis()));
            event.setCancelled(true);
        }
    }
    
    static {
        incomingPackets = new ConcurrentLinkedQueue<PacketUtil.TimedPacket>();
        outgoingPackets = new ConcurrentLinkedQueue<PacketUtil.TimedPacket>();
        stopWatch = new TimeUtil();
        PingSpoofComponent.client = true;
    }
}

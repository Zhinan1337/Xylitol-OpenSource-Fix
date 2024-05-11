//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

 
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.api.events.*;
import net.minecraft.network.play.client.*;
import java.util.concurrent.*;


public class Disabler extends Module
{
    public static final ModeValue<mode> modeValue;
    private final BoolValue oldGrimPostValue;
    private final BoolValue postValue;
    private final BoolValue badPacketsA;
    public static final BoolValue badPacketsF;
    private final BoolValue fakePingValue;
    private final BoolValue sprint;
    private final BoolValue reach;
    private final BoolValue movement;
    private final BoolValue miscellaneous;
    private final HashMap<Packet<?>, Long> packetsMap;
    int lastSlot;
    boolean lastSprinting;
    private static boolean lastResult;
    public static List<Packet<INetHandler>> storedPackets;
    public static ConcurrentLinkedDeque<Integer> pingPackets;
    static Disabler INSTANCE;
    
    public Disabler() {
        super("Disabler", Category.Misc);
        this.oldGrimPostValue = new BoolValue("OldGrim-Post", false, () -> Disabler.modeValue.getValue().equals(mode.Grim));
        this.postValue = new BoolValue("NewGrim-Post", false, () -> Disabler.modeValue.getValue().equals(mode.Grim));
        this.badPacketsA = new BoolValue("Grim-BadPacketsA", false, () -> Disabler.modeValue.getValue().equals(mode.Grim));
        this.fakePingValue = new BoolValue("Grim-FakePing", false, () -> Disabler.modeValue.getValue().equals(mode.Grim));
        this.sprint = new BoolValue("Vulcan-Omni-Sprint", true, () -> Disabler.modeValue.getValue().equals(mode.Vulcan));
        this.reach = new BoolValue("Vulcan-Reach (4.5 Blocks)", true, () -> Disabler.modeValue.getValue().equals(mode.Vulcan));
        this.movement = new BoolValue("Vulcan-Strafe and Jump", true, () -> Disabler.modeValue.getValue().equals(mode.Vulcan));
        this.miscellaneous = new BoolValue("Vulcan-Miscellaneous (Auto-Block, BadPackets)", true, () -> Disabler.modeValue.getValue().equals(mode.Vulcan));
        this.packetsMap = new HashMap<Packet<?>, Long>();
        this.lastSlot = -1;
        Disabler.INSTANCE = this;
    }
    
    public void onEnable() {
        this.setSuffix(Disabler.modeValue.getValue().name());
    }
    
    @EventTarget(1)
    public void onUpdate(final EventUpdate event) {
        if (Disabler.modeValue.getValue().equals(mode.Grim)) {
            if (!getGrimPost()) {
                processPackets();
            }
            if (this.fakePingValue.getValue()) {
                try {
                    synchronized (this.packetsMap) {
                        final Iterator<Map.Entry<Packet<?>, Long>> iterator = this.packetsMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            final Map.Entry<Packet<?>, Long> entry = iterator.next();
                            if (entry.getValue() < System.currentTimeMillis()) {
                                Disabler.mc.getNetHandler().addToSendQueue((Packet)entry.getKey());
                                iterator.remove();
                            }
                        }
                    }
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        this.setSuffix(Disabler.modeValue.getValue().name());
    }
    
    @EventTarget
    public void onWorld(final EventWorldLoad event) {
        this.lastSlot = -1;
        this.lastSprinting = false;
    }
    
    @EventTarget
    public void onPacket(final EventPacketSend event) {
        switch (Disabler.modeValue.getValue()) {
            case Grim: {
                final Packet<?> packet = (Packet<?>)event.getPacket();
                if (Disabler.mc.thePlayer == null) {
                    return;
                }
                if (Disabler.mc.thePlayer.isDead) {
                    return;
                }
                if (Disabler.badPacketsF.getValue() && packet instanceof C0BPacketEntityAction) {
                    if (((C0BPacketEntityAction)packet).getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
                        if (this.lastSprinting) {
                            event.setCancelled(true);
                        }
                        this.lastSprinting = true;
                    }
                    else if (((C0BPacketEntityAction)packet).getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                        if (!this.lastSprinting) {
                            event.setCancelled(true);
                        }
                        this.lastSprinting = false;
                    }
                }
                if (this.badPacketsA.getValue() && packet instanceof C09PacketHeldItemChange) {
                    final int slot = ((C09PacketHeldItemChange)packet).getSlotId();
                    if (slot == this.lastSlot && slot != -1) {
                        event.setCancelled(true);
                    }
                    this.lastSlot = ((C09PacketHeldItemChange)packet).getSlotId();
                }
                if (this.fakePingValue.getValue() && (packet instanceof C00PacketKeepAlive || packet instanceof C16PacketClientStatus) && Disabler.mc.thePlayer.getHealth() > 0.0f && !this.packetsMap.containsKey(packet)) {
                    event.setCancelled(true);
                    synchronized (this.packetsMap) {
                        this.packetsMap.put(packet, System.currentTimeMillis() + TimeUtil.randomDelay(199999, 9999999));
                    }
                }
                if (this.oldGrimPostValue.getValue() && !this.postValue.getValue() && Disabler.mc.getCurrentServerData() != null && (packet instanceof C0APacketAnimation || packet instanceof C02PacketUseEntity || packet instanceof C0EPacketClickWindow || packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging)) {
                    PacketUtil.sendPacketC0F();
                }
            }
            case Vulcan: {
                if (this.miscellaneous.getValue() && event.getPacket() instanceof C17PacketCustomPayload) {
                    event.setCancelled(true);
                    break;
                }
                break;
            }
        }
    }
    
    public static boolean getGrimPost() {
        final boolean result = Disabler.INSTANCE != null && Disabler.INSTANCE.getState() && Disabler.modeValue.getValue().name().equals("Grim") && Disabler.INSTANCE.postValue.getValue() && !Disabler.INSTANCE.oldGrimPostValue.getValue() && Disabler.mc.thePlayer != null && Disabler.mc.thePlayer.isEntityAlive() && Disabler.mc.thePlayer.ticksExisted >= 10 && !(Disabler.mc.currentScreen instanceof GuiDownloadTerrain);
        if (Disabler.lastResult && !result) {
            Disabler.lastResult = false;
            Disabler.mc.addScheduledTask(Disabler::processPackets);
        }
        return Disabler.lastResult = result;
    }
    
    public static boolean grimPostDelay(final Packet<?> packet) {
        if (Disabler.mc.thePlayer == null) {
            return false;
        }
        if (Disabler.mc.currentScreen instanceof GuiDownloadTerrain) {
            return false;
        }
        if (packet instanceof S00PacketServerInfo) {
            return false;
        }
        if (packet instanceof S01PacketEncryptionRequest) {
            return false;
        }
        if (packet instanceof S38PacketPlayerListItem) {
            return false;
        }
        if (packet instanceof S00PacketDisconnect) {
            return false;
        }
        if (packet instanceof S40PacketDisconnect) {
            return false;
        }
        if (packet instanceof S21PacketChunkData) {
            return false;
        }
        if (packet instanceof S01PacketPong) {
            return false;
        }
        if (packet instanceof S44PacketWorldBorder) {
            return false;
        }
        if (packet instanceof S01PacketJoinGame) {
            return false;
        }
        if (packet instanceof S19PacketEntityHeadLook) {
            return false;
        }
        if (packet instanceof S3EPacketTeams) {
            return false;
        }
        if (packet instanceof S02PacketChat) {
            return false;
        }
        if (packet instanceof S2FPacketSetSlot) {
            return false;
        }
        if (packet instanceof S1CPacketEntityMetadata) {
            return false;
        }
        if (packet instanceof S20PacketEntityProperties) {
            return false;
        }
        if (packet instanceof S35PacketUpdateTileEntity) {
            return false;
        }
        if (packet instanceof S03PacketTimeUpdate) {
            return false;
        }
        if (packet instanceof S47PacketPlayerListHeaderFooter) {
            return false;
        }
        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity sPacketEntityVelocity = (S12PacketEntityVelocity)packet;
            return sPacketEntityVelocity.getEntityID() == Disabler.mc.thePlayer.getEntityId();
        }
        return packet instanceof S27PacketExplosion || packet instanceof S32PacketConfirmTransaction || packet instanceof S08PacketPlayerPosLook || packet instanceof S18PacketEntityTeleport || packet instanceof S19PacketEntityStatus || packet instanceof S04PacketEntityEquipment || packet instanceof S23PacketBlockChange || packet instanceof S22PacketMultiBlockChange || packet instanceof S13PacketDestroyEntities || packet instanceof S00PacketKeepAlive || packet instanceof S06PacketUpdateHealth || packet instanceof S14PacketEntity || packet instanceof S0FPacketSpawnMob || packet instanceof S2DPacketOpenWindow || packet instanceof S30PacketWindowItems || packet instanceof S3FPacketCustomPayload || packet instanceof S2EPacketCloseWindow;
    }
    
    public static void processPackets() {
        if (!Disabler.storedPackets.isEmpty()) {
            for (final Packet<INetHandler> packet : Disabler.storedPackets) {
                final EventPacketReceive event = new EventPacketReceive((Packet)packet, EnumPacketDirection.CLIENTBOUND, (INetHandler)Disabler.mc.getNetHandler());
                EventManager.call((Event)event);
                if (event.isCancelled()) {
                    continue;
                }
                packet.processPacket((INetHandler)Disabler.mc.getNetHandler());
            }
            Disabler.storedPackets.clear();
        }
    }
    
    public static void fixC0F(final C0FPacketConfirmTransaction packet) {
        final int id = packet.getUid();
        if (id >= 0 || Disabler.pingPackets.isEmpty()) {
            PacketUtil.sendPacketNoEvent((Packet<?>)packet);
        }
        else {
            do {
                final int current = Disabler.pingPackets.getFirst();
                PacketUtil.sendPacketNoEvent((Packet<?>)new C0FPacketConfirmTransaction(packet.getWindowId(), (short)current, true));
                Disabler.pingPackets.pollFirst();
                if (current == id) {
                    break;
                }
            } while (!Disabler.pingPackets.isEmpty());
        }
    }
    
    static {
        modeValue = new ModeValue<mode>("Mode", mode.values(), mode.Grim);
        badPacketsF = new BoolValue("Grim-BadPacketsF", false, () -> Disabler.modeValue.getValue().equals(mode.Grim));
        Disabler.lastResult = false;
        Disabler.storedPackets = new CopyOnWriteArrayList<Packet<INetHandler>>();
        Disabler.pingPackets = new ConcurrentLinkedDeque<Integer>();
    }
    
    public enum mode
    {
        Grim, 
        Vulcan;
    }
}

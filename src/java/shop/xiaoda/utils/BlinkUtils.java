//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.network.*;
import net.minecraft.network.play.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.client.*;
import java.util.*;

public class BlinkUtils
{
    private static final LinkedList<Packet<INetHandlerPlayServer>> playerBuffer;
    private static final boolean[] packetToggleStat;
    public static final int MisMatch_Type = -302;
    public static boolean movingPacketStat;
    public static boolean transactionStat;
    public static boolean keepAliveStat;
    public static boolean actionStat;
    public static boolean abilitiesStat;
    public static boolean invStat;
    public static boolean interactStat;
    public static boolean otherPacket;
    
    public static void releasePacket(final String packetType, final boolean onlySelected, final int amount, final int minBuff) {
        int count = 0;
        if (packetType == null) {
            count = -1;
            for (final Packet<INetHandlerPlayServer> packets : BlinkUtils.playerBuffer) {
                final int packetID = Integer.parseInt(packets.getClass().getSimpleName().substring(1, 3), 16);
                if (BlinkUtils.packetToggleStat[packetID] || !onlySelected) {
                    PacketUtil.sendPacketNoEvent(packets);
                }
            }
        }
        else {
            final LinkedList<Packet<INetHandlerPlayServer>> tempBuffer = new LinkedList<Packet<INetHandlerPlayServer>>();
            for (final Packet<INetHandlerPlayServer> packets2 : BlinkUtils.playerBuffer) {
                final String className = packets2.getClass().getSimpleName();
                if (className.equalsIgnoreCase(packetType)) {
                    tempBuffer.add(packets2);
                }
            }
            while (tempBuffer.size() > minBuff && (count < amount || amount <= 0)) {
                PacketUtil.sendPacketNoEvent(tempBuffer.pop());
                ++count;
            }
        }
        clearPacket(packetType, onlySelected, count);
    }
    
    public static void clearPacket(final String packetType, final boolean onlySelected, final int amount) {
        if (packetType == null) {
            final LinkedList<Packet<INetHandlerPlayServer>> tempBuffer = new LinkedList<Packet<INetHandlerPlayServer>>();
            for (final Packet<INetHandlerPlayServer> packets : BlinkUtils.playerBuffer) {
                final int packetID = Integer.parseInt(packets.getClass().getSimpleName().substring(1, 3), 16);
                if (!BlinkUtils.packetToggleStat[packetID] && onlySelected) {
                    tempBuffer.add(packets);
                }
            }
            BlinkUtils.playerBuffer.clear();
            BlinkUtils.playerBuffer.addAll(tempBuffer);
        }
        else {
            int count = 0;
            final LinkedList<Packet<INetHandlerPlayServer>> tempBuffer2 = new LinkedList<Packet<INetHandlerPlayServer>>();
            for (final Packet<INetHandlerPlayServer> packets2 : BlinkUtils.playerBuffer) {
                final String className = packets2.getClass().getSimpleName();
                if (!className.equalsIgnoreCase(packetType)) {
                    tempBuffer2.add(packets2);
                }
                else {
                    if (++count <= amount) {
                        continue;
                    }
                    tempBuffer2.add(packets2);
                }
            }
            BlinkUtils.playerBuffer.clear();
            BlinkUtils.playerBuffer.addAll(tempBuffer2);
        }
    }
    
    public static boolean pushPacket(final Packet<?> packets) {
        final int packetID = Integer.parseInt(packets.getClass().getSimpleName().substring(1, 3), 16);
        if (packetID <= BlinkUtils.packetToggleStat.length && !Minecraft.getMinecraft().isSingleplayer() && BlinkUtils.packetToggleStat[packetID] && !isBlacklisted(packets.getClass().getSimpleName())) {
            BlinkUtils.playerBuffer.add((Packet<INetHandlerPlayServer>)packets);
            return true;
        }
        return false;
    }
    
    private static boolean isBlacklisted(final String packetType) {
        switch (packetType) {
            case "C00Handshake":
            case "C00PacketLoginStart":
            case "C00PacketServerQuery":
            case "C01PacketChatMessage":
            case "C01PacketEncryptionResponse":
            case "C01PacketPing": {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static void setBlinkState() {
        setBlinkState(true, false, false, false, false, false, false, false, false, false, false);
        clearPacket(null, false, -1);
    }
    
    public static int bufferSize(final String packetType) {
        if (packetType == null) {
            return BlinkUtils.playerBuffer.size();
        }
        int packetCount = 0;
        boolean flag = false;
        for (final Object packets : BlinkUtils.playerBuffer) {
            final String className = packets.getClass().getSimpleName();
            if (className.equalsIgnoreCase(packetType)) {
                flag = true;
                ++packetCount;
            }
        }
        if (flag) {
            return packetCount;
        }
        return -302;
    }
    
    public static void setBlinkState(final boolean off, final boolean release, final boolean all, final boolean packetMoving, final boolean packetTransaction, final boolean packetKeepAlive, final boolean packetAction, final boolean packetAbilities, final boolean packetInventory, final boolean packetInteract, final boolean other) {
        if (release) {
            releasePacket(null, false, -1, 0);
        }
        BlinkUtils.movingPacketStat = ((packetMoving && !off) || all);
        BlinkUtils.transactionStat = ((packetTransaction && !off) || all);
        BlinkUtils.keepAliveStat = ((packetKeepAlive && !off) || all);
        BlinkUtils.actionStat = ((packetAction && !off) || all);
        BlinkUtils.abilitiesStat = ((packetAbilities && !off) || all);
        BlinkUtils.invStat = ((packetInventory && !off) || all);
        BlinkUtils.interactStat = ((packetInteract && !off) || all);
        BlinkUtils.otherPacket = ((other && !off) || all);
        if (all) {
            Arrays.fill(BlinkUtils.packetToggleStat, true);
        }
        else {
            for (int i = 0; i < BlinkUtils.packetToggleStat.length; ++i) {
                switch (i) {
                    case 0: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.keepAliveStat;
                        break;
                    }
                    case 1:
                    case 17:
                    case 18:
                    case 20:
                    case 21:
                    case 23:
                    case 24:
                    case 25: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.otherPacket;
                        break;
                    }
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.movingPacketStat;
                        break;
                    }
                    case 15: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.transactionStat;
                        break;
                    }
                    case 2:
                    case 9:
                    case 10:
                    case 11: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.actionStat;
                        break;
                    }
                    case 12:
                    case 19: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.abilitiesStat;
                        break;
                    }
                    case 13:
                    case 14:
                    case 16:
                    case 22: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.invStat;
                        break;
                    }
                    case 7:
                    case 8: {
                        BlinkUtils.packetToggleStat[i] = BlinkUtils.interactStat;
                        break;
                    }
                }
            }
        }
    }
    
    static {
        playerBuffer = new LinkedList<Packet<INetHandlerPlayServer>>();
        packetToggleStat = new boolean[26];
        BlinkUtils.movingPacketStat = false;
        BlinkUtils.transactionStat = false;
        BlinkUtils.keepAliveStat = false;
        BlinkUtils.actionStat = false;
        BlinkUtils.abilitiesStat = false;
        BlinkUtils.invStat = false;
        BlinkUtils.interactStat = false;
        setBlinkState(true, true, BlinkUtils.otherPacket = false, false, false, false, false, false, false, false, false);
        clearPacket(null, false, -1);
    }
}

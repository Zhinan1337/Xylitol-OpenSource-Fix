// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S14PacketEntity;
import shop.xiaoda.Client;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.world.EventPacketReceive;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.utils.DebugUtil;

public class AntiBot
        extends Module {
    private static final BoolValue entityID = new BoolValue("EntityID", false);
    private static final BoolValue sleep = new BoolValue("Sleep", false);
    private static final BoolValue noArmor = new BoolValue("NoArmor", false);
    private static final BoolValue height = new BoolValue("Height", false);
    private static final BoolValue ground = new BoolValue("Ground", false);
    private static final BoolValue dead = new BoolValue("Dead", false);
    private static final BoolValue health = new BoolValue("Health", false);
    private static final BoolValue hytGetNames = new BoolValue("HytGetName", false);
    private final BoolValue tips = new BoolValue("HytGetNameTips", true);
    private static final ModeValue<hytGetNameMode> hytGetNameModes = new ModeValue("HytGetNameMode", (Enum[])hytGetNameMode.values(), (Enum)hytGetNameMode.HytBedWars4v4);
    private static final List<Integer> groundBotList = new ArrayList<Integer>();
    private static final List<String> playerName = new ArrayList<String>();

    public AntiBot() {
        super("AntiBot", Category.Combat);
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        this.clearAll();
    }

    private void clearAll() {
        playerName.clear();
    }

    @EventTarget
    public void onPacketReceive(EventPacketReceive event) {
        Entity entity;
        if (AntiBot.mc.thePlayer == null || AntiBot.mc.theWorld == null) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (event.getPacket() instanceof S14PacketEntity && ((Boolean)ground.getValue()).booleanValue() && (entity = ((S14PacketEntity)event.getPacket()).getEntity(AntiBot.mc.theWorld)) instanceof EntityPlayer && ((S14PacketEntity)event.getPacket()).onGround && !groundBotList.contains(entity.getEntityId())) {
            groundBotList.add(entity.getEntityId());
        }
        if (((Boolean)hytGetNames.getValue()).booleanValue() && packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = (S02PacketChat)packet;
            if (s02PacketChat.getChatComponent().getUnformattedText().contains("获得胜利!") || s02PacketChat.getChatComponent().getUnformattedText().contains("游戏开始 ...")) {
                this.clearAll();
            }
            switch ((hytGetNameMode)((Object)hytGetNameModes.getValue())) {
                case HytBedWars4v4:
                case HytBedWars1v1:
                case HytBedWars32: {
                    String name;
                    Matcher matcher = Pattern.compile("杀死了 (.*?)\\(").matcher(s02PacketChat.getChatComponent().getUnformattedText());
                    Matcher matcher2 = Pattern.compile("起床战争>> (.*?) (\\((((.*?) 死了!)))").matcher(s02PacketChat.chatComponent.getUnformattedText());
                    if ((matcher.find() && !s02PacketChat.chatComponent.getUnformattedText().contains(": 起床战争>>") || !s02PacketChat.chatComponent.getUnformattedText().contains(": 杀死了")) && !(name = matcher.group(1).trim()).isEmpty()) {
                        playerName.add(name);
                        if (((Boolean)this.tips.getValue()).booleanValue()) {
                            DebugUtil.log("\u00a78[\u00a7c\u00a7l" + Client.NAME + "Tips\u00a78]\u00a7c§dAddBot：" + name);
                        }
                        String finalName = name;
                        new Thread(() -> {
                            try {
                                Thread.sleep(6000L);
                                playerName.remove(finalName);
                                if (((Boolean)this.tips.getValue()).booleanValue()) {
                                    DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dRemovedBot：" + finalName);
                                }
                            }
                            catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    }
                    if ((!matcher2.find() || s02PacketChat.chatComponent.getUnformattedText().contains(": 起床战争>>")) && s02PacketChat.chatComponent.getUnformattedText().contains(": 杀死了") || (name = matcher2.group(1).trim()).isEmpty()) break;
                    playerName.add(name);
                    if (((Boolean)this.tips.getValue()).booleanValue()) {
                        DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dAddBot：" + name);
                    }
                    String finalName1 = name;
                    new Thread(() -> {
                        try {
                            Thread.sleep(6000L);
                            playerName.remove(finalName1);
                            if (((Boolean)this.tips.getValue()).booleanValue()) {
                                DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dRemovedBot：" + finalName1);
                            }
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    break;
                }
                case HytBedWars16: {
                    String name;
                    Matcher matcher = Pattern.compile("击败了 (.*?)!").matcher(s02PacketChat.chatComponent.getUnformattedText());
                    Matcher matcher2 = Pattern.compile("玩家 (.*?)死了！").matcher(s02PacketChat.chatComponent.getUnformattedText());
                    if ((matcher.find() && !s02PacketChat.chatComponent.getUnformattedText().contains(": 击败了") || !s02PacketChat.chatComponent.getUnformattedText().contains(": 玩家 ")) && !(name = matcher.group(1).trim()).isEmpty()) {
                        playerName.add(name);
                        if (((Boolean)this.tips.getValue()).booleanValue()) {
                            DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dAddBot：" + name);
                        }
                        String finalName = name;
                        new Thread(() -> {
                            try {
                                Thread.sleep(10000L);
                                playerName.remove(finalName);
                                if (((Boolean)this.tips.getValue()).booleanValue()) {
                                    DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dRemovedBot：" + finalName);
                                }
                            }
                            catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    }
                    if ((!matcher2.find() || s02PacketChat.chatComponent.getUnformattedText().contains(": 击败了")) && s02PacketChat.chatComponent.getUnformattedText().contains(": 玩家 ") || (name = matcher2.group(1).trim()).isEmpty()) break;
                    playerName.add(name);
                    DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dAddBot：" + name);
                    String finalName1 = name;
                    new Thread(() -> {
                        try {
                            Thread.sleep(10000L);
                            playerName.remove(finalName1);
                            DebugUtil.log("§8[§c§l" + Client.NAME + "Tips§8]§c§dRemovedBot：" + finalName1);
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                    break;
                }
            }
        }
    }

    public static boolean isServerBot(Entity entity) {
        if (Objects.requireNonNull(Client.instance.moduleManager.getModule(AntiBot.class)).getState() && entity instanceof EntityPlayer) {
            if (((Boolean)hytGetNames.getValue()).booleanValue() && playerName.contains(entity.getName())) {
                return true;
            }
            if (((Boolean)height.getValue()).booleanValue() && ((double)entity.height <= 0.5 || ((EntityPlayer)entity).isPlayerSleeping() || entity.ticksExisted < 80)) {
                return true;
            }
            if (((Boolean)dead.getValue()).booleanValue() && entity.isDead) {
                return true;
            }
            if (((Boolean)health.getValue()).booleanValue() && ((EntityPlayer)entity).getHealth() == 0.0f) {
                return true;
            }
            if (((Boolean)sleep.getValue()).booleanValue() && ((EntityPlayer)entity).isPlayerSleeping()) {
                return true;
            }
            if (((Boolean)entityID.getValue()).booleanValue() && (entity.getEntityId() >= 1000000000 || entity.getEntityId() <= -1)) {
                return true;
            }
            if (((Boolean)ground.getValue()).booleanValue() && !groundBotList.contains(entity.getEntityId())) {
                return true;
            }
            return (Boolean)noArmor.getValue() != false && ((EntityPlayer)entity).inventory.armorInventory[0] == null && ((EntityPlayer)entity).inventory.armorInventory[1] == null && ((EntityPlayer)entity).inventory.armorInventory[2] == null && ((EntityPlayer)entity).inventory.armorInventory[3] == null;
        }
        return false;
    }

    public static enum hytGetNameMode {
        HytBedWars4v4,
        HytBedWars1v1,
        HytBedWars32,
        HytBedWars16;

    }
}

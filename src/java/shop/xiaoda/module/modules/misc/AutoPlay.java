// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.misc;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.world.WorldSettings;
import shop.xiaoda.Client;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.world.EventMotion;
import shop.xiaoda.event.world.EventPacketReceive;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.gui.notification.NotificationManager;
import shop.xiaoda.gui.notification.NotificationType;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.combat.KillAura;
import shop.xiaoda.module.modules.player.ChestStealer;
import shop.xiaoda.module.modules.player.InvCleaner;
import shop.xiaoda.module.modules.world.PlayerWarn;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.module.values.NumberValue;
import shop.xiaoda.utils.PathUtils;
import shop.xiaoda.utils.client.MathUtil;
import shop.xiaoda.utils.client.TimeUtil;

public class AutoPlay
        extends Module {
    private final ModeValue<playMode> modeValue = new ModeValue("Mode", (Enum[])playMode.values(), (Enum)playMode.HYT);
    private final BoolValue swValue = new BoolValue("SkyWars", true);
    private final BoolValue bwValue = new BoolValue("BedWars", true);
    private final BoolValue toggleModule = new BoolValue("Toggle Module", true);
    private final NumberValue delayValue = new NumberValue("Delay", 3.0, 1.0, 10.0, 0.1);
    public boolean display = false;
    private final TimeUtil timer = new TimeUtil();
    private boolean waiting = false;

    public AutoPlay() {
        super("AutoPlay", Category.Misc);
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        this.waiting = false;
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if (event.isPost()) {
            return;
        }
        switch ((playMode)((Object)this.modeValue.getValue())) {
            case HYT: {
                if (AutoPlay.mc.playerController.getCurrentGameType() != WorldSettings.GameType.SPECTATOR) {
                    return;
                }
                ItemStack itemStack = AutoPlay.mc.thePlayer.inventoryContainer.getSlot(43).getStack();
                if (this.waiting) {
                    if (this.timer.hasReached(((Double)this.delayValue.getValue()).longValue() * 1000L)) {
                        this.drop(43);
                    }
                    return;
                }
                if (!itemStack.getDisplayName().contains("再来一局")) {
                    return;
                }
                if ((!itemStack.getItem().equals(Items.minecart) || !((Boolean)this.swValue.getValue()).booleanValue()) && (!itemStack.getItem().equals(Items.chest_minecart) || !((Boolean)this.bwValue.getValue()).booleanValue())) break;
                this.waiting = true;
                this.timer.reset();
            }
        }
    }

    @EventTarget
    public void onPacketReceiveEvent(EventPacketReceive event) {
        Packet<?> packet = event.getPacket();
        if (event.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle s45 = (S45PacketTitle)packet;
            if (s45.getMessage() == null) {
                return;
            }
            if (s45.getMessage().getUnformattedText().equals("\u00a7b\u00a7l空岛战争")) {
                Timer timer = new Timer();
                TimerTask task = new TimerTask(){

                    @Override
                    public void run() {
                        PathUtils.findBlinkPath(-21.0, mc.thePlayer.posY, 3.0).forEach(vector3d -> {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(vector3d.getX(), vector3d.getY(), vector3d.getZ(), true));
                            mc.thePlayer.setPosition(-21.0, mc.thePlayer.posY, 3.0);
                            mc.thePlayer.inventory.currentItem = 2;
                            mc.playerController.updateController();
                        });
                    }
                };
                timer.schedule(task, 50L);
                NotificationManager.post(NotificationType.SUCCESS, "AutoClip", "Teleport!", 5.0f);
            }
        }
        if (AutoPlay.mc.thePlayer == null || AutoPlay.mc.theWorld == null) {
            return;
        }
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet1 = (S02PacketChat)event.getPacket();
            String text = packet1.getChatComponent().getUnformattedText();
            Pattern pattern = Pattern.compile("玩家(.*?)在本局游戏中行为异常");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                NotificationManager.post(NotificationType.WARNING, "BanChecker", "A player was banned.", 5.0f);
            }
            if ((matcher = (pattern = Pattern.compile("你在地图(.*?)中赢得了(.*?)")).matcher(text)).find() && ((Boolean)this.toggleModule.getValue()).booleanValue()) {
                Client.instance.moduleManager.getModule(InvCleaner.class).setState(false);
                Client.instance.moduleManager.getModule(ChestStealer.class).setState(false);
                Client.instance.moduleManager.getModule(KillAura.class).setState(false);
                NotificationManager.post(NotificationType.SUCCESS, "Game Ending", "Sending you to next game in " + this.delayValue.getValue() + "s", 5.0f);
            }
            if (text.contains("      喜欢      一般      不喜欢") || text.contains("[起床战争] Game 结束！感谢您的参与！")) {
                NotificationManager.post(NotificationType.SUCCESS, "Game Ending", "Your Health: " + MathUtil.DF_1.format(AutoPlay.mc.thePlayer.getHealth()), 5.0f);
            }
            if (text.contains("开始倒计时: 1 秒")) {
                if (!Client.instance.moduleManager.getModule(PlayerWarn.class).getState()) {
                    NotificationManager.post(NotificationType.WARNING, "Skywars Warning (Wait 15s)", "请开启PlayerWarn.", 15.0f);
                } else {
                    NotificationManager.post(NotificationType.INFO, "Skywars Starting", "喜报，主播你的脑子在正常工作", 5.0f);
                }
                if (((Boolean)this.toggleModule.getValue()).booleanValue()) {
                    Client.instance.moduleManager.getModule(KillAura.class).setState(true);
                    Client.instance.moduleManager.getModule(InvCleaner.class).setState(true);
                    Client.instance.moduleManager.getModule(ChestStealer.class).setState(true);
                }
            }
            if (text.contains("空岛战争")) {
                NotificationManager.post(NotificationType.WARNING, "Bedwars Warning (Wait 15s)", "Using OldScaffold May Result In A Ban.", 5.0f);
            }
        }
    }

    public void drop(int slot) {
        AutoPlay.mc.playerController.windowClick(AutoPlay.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, AutoPlay.mc.thePlayer);
    }

    public static enum playMode {
        HYT;

    }
}

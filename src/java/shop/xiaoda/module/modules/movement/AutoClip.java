// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.movement;

import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S45PacketTitle;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.world.EventPacketReceive;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.gui.notification.NotificationManager;
import shop.xiaoda.gui.notification.NotificationType;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.NumberValue;

public class AutoClip
        extends Module {
    private final NumberValue high = new NumberValue("High", 2.0, 1.0, 20.0, 1.0);
    private final NumberValue delay = new NumberValue("Delay", 50.0, 1.0, 2000.0, 1.0);
    private boolean teleported = false;

    public AutoClip() {
        super("AutoClip", Category.Movement);
    }

    @Override
    public void onEnable() {
        this.teleported = false;
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        this.teleported = false;
    }

    @EventTarget
    public void onPacketReceiveEvent(EventPacketReceive event) {
        Packet<?> packet = event.getPacket();
        if (event.getPacket() instanceof S45PacketTitle && !this.teleported) {
            S45PacketTitle s45 = (S45PacketTitle)packet;
            if (s45.getMessage() == null) {
                return;
            }
            if (s45.getMessage().getUnformattedText().equals("\u00a7a战斗开始...")) {
                Timer timer = new Timer();
                TimerTask task = new TimerTask(){

                    @Override
                    public void run() {
                        double amount = (Double)AutoClip.this.high.getValue();
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + amount, mc.thePlayer.posZ);
                        NotificationManager.post(NotificationType.SUCCESS, "AutoClip", "Clip UP!", 5.0f);
                        AutoClip.this.teleported = true;
                    }
                };
                timer.schedule(task, ((Double)this.delay.getValue()).intValue());
            }
        }
    }
}

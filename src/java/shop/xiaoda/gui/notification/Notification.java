//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.notification;

import shop.xiaoda.utils.misc.*;
import shop.xiaoda.utils.render.animation.*;
import shop.xiaoda.utils.render.animation.impl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.utils.render.*;

public class Notification implements MinecraftInstance
{
    private final NotificationType notificationType;
    private final String title;
    private final String description;
    private final float time;
    private final AnimTimeUtil timerUtil;
    private final Animation animation;
    
    public Notification(final NotificationType type, final String title, final String description) {
        this(type, title, description, NotificationManager.getToggleTime());
    }
    
    public Notification(final NotificationType type, final String title, final String description, final float time) {
        this.title = title;
        this.description = description;
        this.time = (float)(long)(time * 1000.0f);
        this.timerUtil = new AnimTimeUtil();
        this.notificationType = type;
        this.animation = new DecelerateAnimation(300, 1.0);
    }
    
    public void drawLettuce(final float x, final float y, final float width, final float height) {
        final Color color = ColorUtil.applyOpacity(ColorUtil.interpolateColorC(Color.BLACK, this.getNotificationType().getColor(), 0.65f), 70.0f);
        final float percentage = Math.min(this.timerUtil.getTime() / this.getTime(), 1.0f);
        Gui.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), new Color(0, 0, 0, 70).getRGB());
        Gui.drawRect((double)x, (double)y, (double)(x + width * percentage), (double)(y + height), color.getRGB());
        final Color notificationColor = ColorUtil.applyOpacity(this.getNotificationType().getColor(), 70.0f);
        final Color textColor = ColorUtil.applyOpacity(Color.WHITE, 80.0f);
        FontManager.icontestFont35.drawString(this.getNotificationType().getIcon(), x + 3.0f, y + FontManager.icontestFont35.getMiddleOfBox(height), notificationColor.getRGB());
        FontManager.arial20.drawString(this.getDescription(), x + 2.8f + FontManager.icontestFont35.getStringWidth(this.getNotificationType().getIcon()) + 2.0f, y + 8.0f, textColor.getRGB());
    }
    
    public void blurLettuce(final float x, final float y, final float width, final float height, final boolean glow) {
        final Color color = ColorUtil.applyOpacity(ColorUtil.interpolateColorC(Color.BLACK, this.getNotificationType().getColor(), glow ? 0.65f : 0.0f), 70.0f);
        final float percentage = Math.min(this.timerUtil.getTime() / this.getTime(), 1.0f);
        Gui.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), Color.BLACK.getRGB());
        Gui.drawRect((double)x, (double)y, (double)(x + width * percentage), (double)(y + height), color.getRGB());
        RenderUtil.resetColor();
    }
    
    public NotificationType getNotificationType() {
        return this.notificationType;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public float getTime() {
        return this.time;
    }
    
    public AnimTimeUtil getTimerUtil() {
        return this.timerUtil;
    }
    
    public Animation getAnimation() {
        return this.animation;
    }
}

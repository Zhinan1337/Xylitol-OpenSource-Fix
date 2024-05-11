package shop.xiaoda.module;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import shop.xiaoda.Client;
import shop.xiaoda.event.EventManager;
import shop.xiaoda.gui.notification.NotificationManager;
import shop.xiaoda.gui.notification.NotificationType;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.values.Value;
import shop.xiaoda.utils.render.animation.Animation;
import shop.xiaoda.utils.render.animation.Direction;
import shop.xiaoda.utils.render.animation.impl.DecelerateAnimation;

public class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String suffix;
    public Category category;
    public boolean state = false;
    public boolean defaultOn = false;
    public int key = -1;
    private final Animation animation = new DecelerateAnimation(250, 1.0).setDirection(Direction.BACKWARDS);
    public double progress;
    public double animX = 0.0;
    public double animY = 0.0;
    private final List<Value<?>> values = new ArrayList();

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.suffix = "";
    }

    public void toggle() {
        this.setState(!this.state);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return Client.instance.moduleManager.getModule(clazz);
    }

    public boolean getState() {
        return this.state;
    }

    public void setStateSilent(boolean state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (Module.mc.theWorld != null) {
            Module.mc.theWorld.playSound(Module.mc.thePlayer.posX, Module.mc.thePlayer.posY, Module.mc.thePlayer.posZ, "random.click", 0.5f, state ? 0.6f : 0.5f, false);
        }
        if (state) {
            EventManager.register(this);
            NotificationManager.post(NotificationType.SUCCESS, "Module", this.name + " Enabled");
        } else {
            EventManager.unregister(this);
            NotificationManager.post(NotificationType.DISABLE, "Module", this.name + " Disabled");
        }
    }

    public void setState(boolean state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (Module.mc.theWorld != null) {
            Module.mc.theWorld.playSound(Module.mc.thePlayer.posX, Module.mc.thePlayer.posY, Module.mc.thePlayer.posZ, "random.click", 0.5f, state ? 0.6f : 0.5f, false);
        }
        if (state) {
            EventManager.register(this);
            this.onEnable();
            NotificationManager.post(NotificationType.SUCCESS, "Module", this.name + " Enabled");
        } else {
            EventManager.unregister(this);
            this.onDisable();
            NotificationManager.post(NotificationType.DISABLE, "Module", this.name + " Disabled");
        }
    }

    public List<Value<?>> getValues() {
        return this.values;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(Object obj) {
        String suffix = obj.toString();
        this.suffix = suffix.isEmpty() ? suffix : String.format("\u00a7f%s\u00a77", EnumChatFormatting.GRAY + suffix);
    }

    public boolean isDefaultOn() {
        return this.defaultOn;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getAnimX() {
        return this.animX;
    }

    public void setAnimX(double animX) {
        this.animX = animX;
    }

    public double getAnimY() {
        return this.animY;
    }

    public void setAnimY(double animY) {
        this.animY = animY;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public Animation getAnimation() {
        return this.animation;
    }
}

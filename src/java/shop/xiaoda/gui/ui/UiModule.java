//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.module.*;
import shop.xiaoda.*;
import shop.xiaoda.event.*;

public class UiModule extends Gui
{
    public static final Minecraft mc;
    public double moveX;
    public double moveY;
    public double posX;
    public double posY;
    public double width;
    public double height;
    public String name;
    public boolean state;
    
    public UiModule(final String name, final double posX, final double posY, final double width, final double height) {
        this.moveX = 0.0;
        this.moveY = 0.0;
        this.state = false;
        this.name = name;
        this.posX = posX / RenderUtil.width();
        this.posY = posY / RenderUtil.height();
        this.width = width;
        this.height = height;
    }
    
    public <T extends Module> T getModule(final Class<T> clazz) {
        return Client.instance.moduleManager.getModule(clazz);
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getPosX() {
        return this.posX * RenderUtil.width();
    }
    
    public double getPosY() {
        return this.posY * RenderUtil.height();
    }
    
    public void setPosX(final double posX) {
        this.posX = posX / RenderUtil.width();
    }
    
    public void setPosY(final double posY) {
        this.posY = posY / RenderUtil.height();
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(final double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    public void setState(final boolean state) {
        if (state && !this.state) {
            EventManager.register(this);
        }
        else if (this.state && !state) {
            EventManager.unregister(this);
        }
        this.state = state;
    }
    
    public void toggle() {
        this.setState(!this.state);
    }
    
    public boolean getState() {
        return this.state;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}

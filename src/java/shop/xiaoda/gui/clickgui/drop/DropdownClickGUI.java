//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.clickgui.drop;

import net.minecraft.client.gui.*;
import shop.xiaoda.*;
import java.awt.*;

import shop.xiaoda.module.Module;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.render.fontRender.*;
import org.lwjgl.input.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.values.*;
import net.minecraft.util.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class DropdownClickGUI extends GuiScreen
{
    List<Float> posX;
    List<Float> posY;
    List<Module> inSetting;
    List<Value<?>> inMode;
    
    public DropdownClickGUI() {
        this.posX = new ArrayList<Float>();
        this.posY = new ArrayList<Float>();
        this.inSetting = new ArrayList<Module>();
        this.inMode = new ArrayList<Value<?>>();
    }
    
    public void initGui() {
        this.posX = (List<Float>)Client.instance.cGUIPosX;
        this.posY = (List<Float>)Client.instance.cGUIPosY;
        this.inSetting = (List<Module>)Client.instance.cGUIInSetting;
        this.inMode = (List<Value<?>>)Client.instance.cGUIInMode;
    }
    
    public void onGuiClosed() {
        Client.instance.configManager.saveAllConfig();
        Client.instance.cGUIPosX = this.posX;
        Client.instance.cGUIPosY = this.posY;
        Client.instance.cGUIInSetting = this.inSetting;
        Client.instance.cGUIInMode = this.inMode;
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        BlurUtil.blurArea(0.0f, 0.0f, (float)this.width, (float)this.height, 10.0f);
        RenderUtil.drawRect(0.0, 0.0, this.width, this.height, new Color(0, 0, 0, 100).getRGB());
        int cOrder = -1;
        for (final Category c : Category.values()) {
            ++cOrder;
            final String category = c.toString();
            try {
                this.posY.get(cOrder);
            }
            catch (Exception e2) {
                this.posY.add(5.0f);
            }
            try {
                this.posX.get(cOrder);
            }
            catch (Exception e2) {
                this.posX.add(cOrder * 120 + 80.0f);
            }
            final float x = this.posX.get(cOrder);
            final float y = this.posY.get(cOrder);
            RenderUtil.drawRect(x, y, x + 110.0f, y + 15.0f, new Color(255, 255, 255).getRGB());
            FontManager.arial18.drawCenteredString(category, x + 55.0f, y + 5.0f, new Color(30, 30, 30).getRGB());
            int mOrder = -1;
            float settingY = 0.0f;
            for (final Module m : Client.instance.moduleManager.getModsByCategory(c)) {
                ++mOrder;
                final boolean isOnModule = mouseX > x && mouseY > y + 15.0f + settingY + mOrder * 20 && mouseX < x + 110.0f && mouseY < y + mOrder * 20 + 35.0f + settingY;
                final int backgroundColor = isOnModule ? (m.getState() ? new Color(140, 190, 240).getRGB() : new Color(240, 240, 240).getRGB()) : (m.getState() ? new Color(150, 200, 250).getRGB() : new Color(250, 250, 250).getRGB());
                RenderUtil.drawRect(x, y + 15.0f + mOrder * 20 + settingY, x + 110.0f, y + mOrder * 20 + 35.0f + settingY, backgroundColor);
                FontManager.Tahoma18.drawCenteredString(m.name, x + 55.0f, y + 22.0f + mOrder * 20 + settingY, new Color(30, 30, 30).getRGB());
                if (this.inSetting.contains(m)) {
                    int currentSettingY = 0;
                    for (final Value<?> v : m.getValues()) {
                        if (!v.isAvailable()) {
                            continue;
                        }
                        RenderUtil.drawRect(x, y + mOrder * 20 + 35.0f + settingY + currentSettingY, x + 110.0f, y + mOrder * 20 + 35.0f + settingY + currentSettingY + 15.0f, m.getState() ? new Color(140, 190, 240).getRGB() : new Color(240, 240, 240).getRGB());
                        FontManager.Tahoma18.drawString(v.getName(), x + 1.0f, y + mOrder * 20 + 35.0f + settingY + currentSettingY + 5.0f, new Color(50, 50, 50).getRGB());
                        if (v instanceof ModeValue) {
                            RenderUtil.drawRect(x + 50.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY, x + 108.0f, y + mOrder * 20 + 48.0f + settingY + currentSettingY, m.getState() ? new Color(155, 205, 255).getRGB() : new Color(255, 255, 255).getRGB());
                            if (this.inMode.contains(v)) {
                                RenderUtil.drawTriangle(x + 100.0f, y + mOrder * 20 + 39.0f + settingY + currentSettingY, x + 100.0f, y + mOrder * 20 + 46.0f + settingY + currentSettingY, x + 106.0f, y + mOrder * 20 + 42.5 + settingY + currentSettingY, new Color(70, 70, 70).getRGB());
                            }
                            else {
                                RenderUtil.drawTriangle(x + 100.0f, y + mOrder * 20 + 40.0f + settingY + currentSettingY, x + 103.0f, y + mOrder * 20 + 46.0f + settingY + currentSettingY, x + 106.0f, y + mOrder * 20 + 40.0f + settingY + currentSettingY, new Color(70, 70, 70).getRGB());
                            }
                            FontManager.Tahoma16.drawCenteredString(v.getValue().toString(), x + 75.0f, y + mOrder * 20 + 40.0f + settingY + currentSettingY, new Color(30, 30, 30).getRGB());
                            if (this.inMode.contains(v)) {
                                RenderUtil.drawShadow(x + 115.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY, x + 160.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY + ((ModeValue)v).getModes().length * 10);
                                int modeOrder = 0;
                                for (final Enum e : ((ModeValue)v).getModes()) {
                                    RenderUtil.drawRect(x + 115.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY + modeOrder * 10, x + 160.0f, y + mOrder * 20 + 47.0f + settingY + currentSettingY + modeOrder * 10, m.getState() ? new Color(140, 190, 240).getRGB() : new Color(240, 240, 240).getRGB());
                                    if (v.getValue() == e) {
                                        FontManager.Tahoma14.drawString(e.toString(), x + 116.0f, y + mOrder * 20 + 40.5f + settingY + currentSettingY + modeOrder * 10, new Color(30, 30, 30).getRGB());
                                    }
                                    else {
                                        FontManager.Tahoma14.drawString(e.toString(), x + 116.0f, y + mOrder * 20 + 40.5f + settingY + currentSettingY + modeOrder * 10, new Color(150, 150, 150).getRGB());
                                    }
                                    ++modeOrder;
                                }
                            }
                        }
                        if (v instanceof NumberValue) {
                            final NumberValue n = (NumberValue)v;
                            final double diffMaxMin = ((NumberValue)v).getMax() - ((NumberValue)v).getMin();
                            final double diffValMin = n.getValue() - ((NumberValue)v).getMin();
                            RenderUtil.drawRect(x + 50.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY, x + 108.0f, y + mOrder * 20 + 48.0f + settingY + currentSettingY, m.getState() ? new Color(155, 205, 255).getRGB() : new Color(255, 255, 255).getRGB());
                            RenderUtil.drawRect(x + 50.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY, x + 50.0f + 58.0 * diffValMin / diffMaxMin, y + mOrder * 20 + 48.0f + settingY + currentSettingY, m.getState() ? new Color(255, 255, 255).getRGB() : new Color(155, 205, 255).getRGB());
                            FontManager.Tahoma16.drawString(v.getValue().toString(), x + 107.0f - FontManager.Tahoma16.getStringWidth(v.getValue().toString()), y + mOrder * 20 + 41.0f + settingY + currentSettingY, new Color(30, 30, 30).getRGB());
                            if (Mouse.isButtonDown(0) && mouseX >= x + 50.0f && mouseY >= y + mOrder * 20 + 37.0f + settingY + currentSettingY && mouseX <= x + 108.0f && mouseY <= y + mOrder * 20 + 48.0f + settingY + currentSettingY) {
                                n.setValue(MathUtil.round((mouseX - x - 50.0f) / 58.0f * (diffMaxMin + 1.0) + n.getMin(), 1));
                                if (n.getValue() > n.getMax()) {
                                    n.setValue(n.getMax());
                                }
                                if (n.getValue() < n.getMin()) {
                                    n.setValue(n.getMin());
                                }
                            }
                        }
                        if (v instanceof BoolValue) {
                            final BoolValue o = (BoolValue)v;
                            RenderUtil.drawRect(x + 97.0f, y + mOrder * 20 + 37.0f + settingY + currentSettingY, x + 108.0f, y + mOrder * 20 + 48.0f + settingY + currentSettingY, m.getState() ? (o.getValue() ? new Color(160, 210, 255).getRGB() : new Color(110, 160, 210).getRGB()) : (o.getValue() ? new Color(255, 255, 255).getRGB() : new Color(210, 210, 210).getRGB()));
                        }
                        currentSettingY += 15;
                    }
                    RenderUtil.drawTexturedRect(x, y + mOrder * 20 + 35.0f + settingY, 110.0f, 9.0f, new ResourceLocation("shaders/panelbottom.png"), Color.white.getRGB());
                    settingY += currentSettingY;
                }
            }
            RenderUtil.drawShadow(x, y, x + 110.0f, y + mOrder * 20 + 35.0f + settingY);
            RenderUtil.drawTexturedRect(x, y + 15.0f, 110.0f, 9.0f, new ResourceLocation("shaders/panelbottom.png"), Color.white.getRGB());
            final boolean isOnCategory = mouseX > x && mouseY > y && mouseX < x + 110.0f && mouseY < y + 15.0f;
            if (Mouse.isButtonDown(0) && isOnCategory) {
                this.posX.set(cOrder, x + Mouse.getDX() / 2.0f);
                this.posY.set(cOrder, y - Mouse.getDY() / 2.0f);
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        int cOrder = -1;
        for (final Category c : Category.values()) {
            ++cOrder;
            final float x = this.posX.get(cOrder);
            final float y = this.posY.get(cOrder);
            int mOrder = -1;
            float settingY = 0.0f;
            for (final Module m : Client.instance.moduleManager.getModsByCategory(c)) {
                ++mOrder;
                final boolean isOnModule = mouseX > x && mouseY > y + 15.0f + settingY + mOrder * 20 && mouseX < x + 110.0f && mouseY < y + mOrder * 20 + 35.0f + settingY;
                if (isOnModule && mouseButton == 0) {
                    m.setState(!m.getState());
                }
                if (isOnModule && mouseButton == 1 && !m.getValues().isEmpty()) {
                    if (this.inSetting.contains(m)) {
                        this.inSetting.remove(m);
                    }
                    else {
                        this.inSetting.add(m);
                    }
                }
                if (this.inSetting.contains(m)) {
                    int currentSettingY = 0;
                    for (final Value<?> v : m.getValues()) {
                        if (!v.isAvailable()) {
                            continue;
                        }
                        if (v instanceof ModeValue) {
                            if (mouseX > x + 50.0f && mouseY > y + mOrder * 20 + 37.0f + settingY + currentSettingY && mouseX < x + 108.0f && mouseY < y + mOrder * 20 + 48.0f + settingY + currentSettingY) {
                                if (this.inMode.contains(v)) {
                                    this.inMode.remove(v);
                                }
                                else {
                                    this.inMode.add(v);
                                }
                            }
                            if (this.inMode.contains(v)) {
                                int modeOrder = 0;
                                for (final Enum e : ((ModeValue)v).getModes()) {
                                    if (mouseX > x + 115.0f && mouseY > y + mOrder * 20 + 37.0f + settingY + currentSettingY + modeOrder * 10 && mouseX < x + 160.0f && mouseY < y + mOrder * 20 + 47.0f + settingY + currentSettingY + modeOrder * 10) {
                                        ((ModeValue)v).setMode(e.name());
                                    }
                                    ++modeOrder;
                                }
                            }
                        }
                        if (v instanceof NumberValue) {}
                        if (v instanceof BoolValue) {
                            final BoolValue o = (BoolValue)v;
                            if (mouseX > x + 97.0f && mouseY > y + mOrder * 20 + 37.0f + settingY + currentSettingY && mouseX < x + 108.0f && mouseY < y + mOrder * 20 + 48.0f + settingY + currentSettingY) {
                                o.setValue(!o.getValue());
                            }
                        }
                        currentSettingY += 15;
                    }
                    settingY += currentSettingY;
                }
            }
            final boolean isOnCategory = mouseX > x && mouseY > y && mouseX < x + 110.0f && mouseY < y + 15.0f;
            if (Mouse.isButtonDown(0) && isOnCategory) {
                this.posX.set(cOrder, x + Mouse.getDX() / 2.0f);
                this.posY.set(cOrder, y - Mouse.getDY() / 2.0f);
            }
        }
    }
}

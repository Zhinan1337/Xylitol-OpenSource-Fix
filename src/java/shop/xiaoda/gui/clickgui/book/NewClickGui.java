//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.clickgui.book;

import net.minecraft.client.shader.*;
import org.checkerframework.org.apache.commons.lang3.StringUtils;
import org.lwjgl.compatibility.display.Display;
import shop.xiaoda.*;
import shop.xiaoda.config.*;
import shop.xiaoda.event.rendering.*;
import java.awt.*;
import shop.xiaoda.event.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.render.fontRender.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.module.modules.render.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.utils.*;
import java.math.*;
import javax.imageio.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import shop.xiaoda.module.*;

public class NewClickGui extends GuiScreen
{
    private static NewClickGui instance;
    private final float[] moduleWheel;
    private final TextValue searching;
    private final TextValue config;
    private final RippleAnimation searchRipple;
    private final ArrayList<Config> configs;
    private final RippleAnimation configButton;
    private int x;
    private int y;
    private int dragX;
    private int dragY;
    private boolean dragging;
    private float cateAnimation;
    private float visibleAnimation;
    private TextValue currentEditing;
    private boolean quitting;
    private Page page;
    private boolean search;
    private Framebuffer buffer;
    private float catePanelPos;
    
    public NewClickGui() {
        this.moduleWheel = new float[] { 0.0f, 0.0f };
        this.searching = TextValue.create("Search").defaultTo("");
        this.config = TextValue.create("Config").defaultTo("");
        this.searchRipple = new RippleAnimation();
        this.configs = new ArrayList<Config>();
        this.configButton = new RippleAnimation();
        this.currentEditing = null;
        this.page = Page.Combat;
        this.search = false;
        this.buffer = new Framebuffer(1, 1, false);
        this.catePanelPos = (float)this.x;
        this.x = 30;
        this.y = 30;
        this.quitting = false;
    }
    
    public static NewClickGui getInstance() {
        return (NewClickGui.instance == null) ? (NewClickGui.instance = new NewClickGui()) : NewClickGui.instance;
    }
    
    private void updateConfigs() {
        this.configs.clear();
        this.configs.add(new Config(new File("Default")));
        final ConfigManager configManager = Client.instance.configManager;
        final File[] files = ConfigManager.dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    this.configs.add(new Config(file));
                }
            }
        }
    }
    
    public boolean isQuitting() {
        return this.quitting;
    }
    
    public void setQuitting(final boolean quitting) {
        this.quitting = quitting;
    }
    
    @EventTarget
    public void onShader(final EventShader e) {
        if (e.isBloom()) {
            RoundedUtils.round((float)this.x, (float)this.y, 530.0f, 330.0f, 4.0f, new Color(0, 0, 0));
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        Client.instance.moduleManager.getModule(ClickGui.class).setState(false);
        EventManager.unregister(this);
        this.search = false;
        this.quitting = false;
    }
    
    public void initGui() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.x = (int)(scaledResolution.getScaledWidth() / 2.0f - 265.0f);
        this.y = (int)(scaledResolution.getScaledHeight() / 2.0f - 165.0f);
        EventManager.register(this);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.visibleAnimation = AnimationUtil.animateSmooth(this.visibleAnimation, this.quitting ? 0.0f : 100.0f, 0.2f);
        final int wheel = Mouse.hasWheel() ? Mouse.getDWheel() : 0;
        if (this.quitting) {
            if (Display.isActive() && !this.mc.inGameHasFocus) {
                this.mc.inGameHasFocus = true;
                this.mc.mouseHelper.grabMouseCursor();
            }
            if (Math.round(this.visibleAnimation) <= 2) {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
        RoundedUtils.round((float)this.x, (float)this.y, 530.0f, 330.0f, 4.0f, this.getColor(24, 28, 31));
        RoundedUtils.round((float)this.x, (float)this.y, 530.0f, 330.0f, 4.0f, this.getColor(24, 28, 31));
        RoundedUtils.round((float)this.x, (float)(this.y + 300), 120.0f, 30.0f, 12.0f, this.getColor(34, 38, 41));
        Mask.defineMask();
        RenderUtil.drawCircle2(this.x + 17, this.y + 315, 26.0f, -1);
        Mask.finishDefineMask();
        Mask.drawOnMask();
        RenderUtil.drawImage(new ResourceLocation("ircavatar"), this.x + 4, this.y + 315 - 13, 26, 26, this.getColor(Color.WHITE).getRGB());
        Mask.resetMask();
        FontManager.arial18.drawString(Client.instance.getUser(), (float)(this.x + 34), (float)(this.y + 315 - 11), this.getColor(Color.WHITE).getRGB());
        FontManager.arial18.drawString("#1337", (float)(this.x + 34), (float)(this.y + 315 + 2), this.getColor(Color.GRAY).getRGB());
        Gui.drawRect3((double)(this.x + 120), (double)this.y, 0.5, 330.0, this.getColor(48, 51, 54).getRGB());
        if (this.page.isCategoryPage) {
            Gui.drawRect3((double)(this.x + 4), (double)(this.y + 32 + 24), 116.0, 0.5, this.getColor(48, 51, 54).getRGB());
        }
        FontManager.lettuceBoldFont26.drawCenteredString("XYLITOL", (float)(this.x + 60), (float)(this.y + 8), this.getColor(new Color(255, 255, 255, 150)).getRGB());
        if (this.page.isCategoryPage) {
            RoundedUtils.round((float)(this.x + 4), (float)(this.y + 32), 112.0f, 20.0f, 4.0f, this.getColor(48, 51, 54));
            if (!this.search) {
                RoundedUtils.round(this.x + 112 - 16, this.y + 34, 16, 16, 4.0f, this.getColor(24, 28, 31).getRGB());
                FontManager.arial22.drawString("^F", (float)(this.x + 112 - 15), this.y + 38.0f, this.getColor(new Color(HUD.mainColor.getColor())).getRGB());
                FontManager.arial18.drawString("Search Module..", (float)(this.x + 8), (float)(this.y + 39), RenderUtil.isHovering((float)(this.x + 4), (float)(this.y + 32), 112.0f, 20.0f, mouseX, mouseY) ? this.getColor(255, 255, 255, 201).getRGB() : this.getColor(255, 255, 255, 180).getRGB());
            }
            else {
                try {
                    if (StringUtils.isEmpty((CharSequence)(this.searching).getValue())) {
                        FontManager.arial18.drawString("Search Module..", (float)(this.x + 8), (float)(this.y + 39), RenderUtil.isHovering((float)(this.x + 4), (float)(this.y + 32), 112.0f, 20.0f, mouseX, mouseY) ? this.getColor(255, 255, 255, 60).getRGB() : this.getColor(255, 255, 255, 50).getRGB());
                    }
                    FontManager.arial18.drawString(this.searching.getValue(), (float)(this.x + 8), (float)(this.y + 37), RenderUtil.isHovering((float)(this.x + 4), (float)(this.y + 32), 112.0f, 20.0f, mouseX, mouseY) ? this.getColor(255, 255, 255, 201).getRGB() : this.getColor(255, 255, 255, 180).getRGB());
                }
                catch (Exception ex) {}
            }
            this.searchRipple.draw(() -> RoundedUtils.round(this.x + 4, this.y + 32, 112, 20, 4.0f, this.getColor(48, 51, 54).getRGB()));
        }
        this.catePanelPos = AnimationUtil.animateSmooth(this.catePanelPos, this.page.isCategoryPage ? 28.0f : 0.0f, 0.3f);
        int cy = (int)(this.y + 32 + this.catePanelPos);
        for (final Page page : Page.values()) {
            Label_1477: {
                if (this.search) {
                    if (!page.isCategoryPage) {
                        break Label_1477;
                    }
                    if (this.searching != null && this.searching.getValue() != null && Client.instance.moduleManager.haveModules(page.category, this.searching.getValue().toLowerCase().replaceAll(" ", ""))) {
                        break Label_1477;
                    }
                }
                if (RenderUtil.isHovering((float)(this.x + 4), (float)cy, 112.0f, 20.0f, mouseX, mouseY)) {
                    page.x = AnimationUtil.animateSmooth(page.x, 12.0f, 0.1f);
                    page.alpha = AnimationUtil.animateSmooth(page.alpha, 0.1f, 0.1f);
                    RoundedUtils.round(this.x + 4, cy, 112, 20, 4.0f, this.getColor(45, 50, 58).getRGB());
                }
                else {
                    page.alpha = AnimationUtil.animateSmooth(page.alpha, 0.0f, 0.1f);
                    page.x = AnimationUtil.animateSmooth(page.x, 10.0f, 0.1f);
                }
                if (page == this.page) {
                    this.cateAnimation = AnimationUtil.animateSmooth(this.cateAnimation, (float)cy, 0.3f);
                    RoundedUtils.round(this.x + 4, cy, 112, 20, 4.0f, this.getColor(RenderUtil.reAlpha(new Color(HUD.mainColor.getColor()).darker().darker(), 201)).getRGB());
                }
                final int finalCy = cy;
                page.ripple.draw(() -> RoundedUtils.round(this.x + 4, finalCy, 112, 20, 4.0f, this.getColor(41, 39, 51).getRGB()));
                final int colorSet = (page == this.page) ? 255 : 166;
                RenderUtil.drawImage(new ResourceLocation("express/icon/" + page.name().toLowerCase() + ".png"), this.x + page.x, cy + 3.5f, 12.0, 12.0, this.getColor(colorSet, colorSet, colorSet, 200).getRGB());
                FontManager.arial18.drawString(page.name().replaceAll("_", " "), this.x + page.x + 16.0f, (float)(cy + 6), this.getColor(colorSet, colorSet, colorSet, 200).getRGB());
                cy += 24;
            }
        }
        Mask.defineMask();
        Gui.drawRect3((double)(this.x + 120), (double)this.y, 410.0, 330.0, -1);
        Mask.finishDefineMask();
        Mask.drawOnMask();
        float my = this.y + 6 + this.moduleWheel[1];
        if (this.page.isCategoryPage) {
            for (final Module module : this.search ? Client.instance.moduleManager.getModules() : Client.instance.moduleManager.getModsByCategory(this.page.category)) {
                if (this.search && this.searching != null && !module.getName().toLowerCase().replaceAll(" ", "").contains(this.searching.getValue().toLowerCase().replaceAll(" ", ""))) {
                    continue;
                }
                FontManager.arial18.drawString(module.getName(), (float)(this.x + 125), my + 2.0f, this.getColor(255, 255, 255, 200).getRGB());
                final double y = my;
                final double h = 12.0;
                final double width = 12.0;
                final double left = this.x + 510;
                if (module.getState()) {
                    if (module.progress < 1.0) {
                        final Module module2 = module;
                        module2.progress += 1.0 / Minecraft.getDebugFPS() * 6.0;
                    }
                    else {
                        module.progress = 1.0;
                    }
                }
                else if (module.progress > 0.0) {
                    final Module module3 = module;
                    module3.progress -= 1.0 / Minecraft.getDebugFPS() * 6.0;
                }
                else {
                    module.progress = 0.0;
                }
                final double size = 5.0;
                final int color = HUD.mainColor.getColor();
                final int bgColor = module.getState() ? this.getColor(new Color(color)).getRGB() : this.getColor(new Color(6710888)).getRGB();
                GLRendering.glDrawFilledEllipse(left + 2.5, y + 6.0, 12.0f, bgColor);
                GLRendering.glDrawFilledEllipse(left + 12.0 - 2.5, y + 6.0, 12.0f, bgColor);
                Gui.drawRect3(left + 2.5, y + 6.0 - 3.0, 7.0, 6.0, bgColor);
                GLRendering.glDrawFilledEllipse(left + 2.5 + 0.5 + module.progress * 6.0, y + 6.0, 10.0f, this.getColor(new Color(-15066853)).getRGB());
                my += 18.0f;
                for (final Value<?> option : module.getValues()) {
                    if (!option.isAvailable()) {
                        continue;
                    }
                    my = this.drawOptions(my, option, mouseX, mouseY);
                }
            }
        }
        Mask.resetMask();
        final float[] nextWheel = RenderUtil.getNextWheelPosition(wheel, this.moduleWheel, (float)(this.y + 10), (float)(this.y + 290), my, 0.0f, RenderUtil.isHovering((float)(this.x + 120), (float)this.y, 410.0f, 330.0f, mouseX, mouseY));
        this.moduleWheel[0] = nextWheel[0];
        this.moduleWheel[1] = nextWheel[1];
        if (!Mouse.isButtonDown(0) && this.dragging) {
            this.dragging = false;
        }
        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (GuiScreen.isCtrlKeyDown() && Keyboard.isKeyDown(Keyboard.KEY_F)) {
            this.search = !this.search;
            if (this.searching != null) {
                this.searching.setValue("");
            }
            return;
        }
        if (this.search && this.searching != null) {
            this.handleKeyInput(this.searching, typedChar, keyCode);
        }
        this.handleKeyInput(this.currentEditing, typedChar, keyCode);
    }
    
    private void handleKeyInput(final TextValue text, final char typedChar, final int keyCode) {
        if (text != null) {
            if (keyCode == Keyboard.KEY_BACK && !text.getValue().isEmpty()) {
                text.setValue(text.getSelectedString().isEmpty() ? text.getValue().substring(0, text.getValue().length() - 1) : "");
                text.setSelectedString("");
                return;
            }
            if (GuiScreen.isKeyComboCtrlA(keyCode)) {
                text.setSelectedString(text.getValue());
                return;
            }
            if (GuiScreen.isKeyComboCtrlC(keyCode)) {
                GuiScreen.setClipboardString(text.getSelectedString());
                return;
            }
            if (GuiScreen.isKeyComboCtrlV(keyCode)) {
                if (text.getSelectedString().isEmpty() && (text.getValue() + GuiScreen.getClipboardString()).length() > 22) {
                    text.setSelectedString("");
                    return;
                }
                text.setValue(text.getSelectedString().isEmpty() ? (text.getValue() + GuiScreen.getClipboardString()) : GuiScreen.getClipboardString());
                text.setSelectedString("");
            }
            else {
                if (GuiScreen.isCtrlKeyDown()) {
                    return;
                }
                if (keyCode == Keyboard.KEY_ESCAPE) {
                    text.setSelectedString("");
                    return;
                }
                if (text.getValue().length() > 22) {
                    return;
                }
                text.setValue(text.getSelectedString().isEmpty() ? (text.getValue() + ChatAllowedCharacters.filterAllowedCharacters(String.valueOf(typedChar))) : ChatAllowedCharacters.filterAllowedCharacters(String.valueOf(typedChar)));
                text.setSelectedString("");
            }
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (this.page.isCategoryPage) {
            for (final Module module : this.search ? Client.instance.moduleManager.getModules() : Client.instance.moduleManager.getModsByCategory(this.page.category)) {
                if (this.search && this.searching != null && !module.getName().toLowerCase().replaceAll(" ", "").contains(this.searching.getValue().toLowerCase().replaceAll(" ", ""))) {
                    continue;
                }
                for (final Value<?> option : module.getValues()) {
                    if (option instanceof NumberValue) {
                        final NumberValue opt = (NumberValue)option;
                        opt.sliding = false;
                    }
                }
            }
        }
        else {
            for (final Value<?> option2 : this.page.uiGenerator.getOptions()) {
                if (option2 instanceof NumberValue) {
                    final NumberValue opt2 = (NumberValue)option2;
                    opt2.sliding = false;
                }
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.page.isCategoryPage && RenderUtil.isHovering((float)(this.x + 4), (float)(this.y + 32), 112.0f, 20.0f, mouseX, mouseY)) {
            this.search = !this.search;
            if (this.searching != null) {
                this.searching.setValue("");
            }
            if (this.search) {
                this.searchRipple.mouseClicked((float)mouseX, (float)mouseY);
            }
        }
        int cy = (int)(this.y + 32 + this.catePanelPos);
        for (final Page cate : Page.values()) {
            Label_0268: {
                if (this.search && this.searching != null) {
                    if (!cate.isCategoryPage) {
                        break Label_0268;
                    }
                    if (Client.instance.moduleManager.haveModules(cate.category, this.searching.getValue().toLowerCase().replaceAll(" ", ""))) {
                        break Label_0268;
                    }
                }
                if (RenderUtil.isHovering((float)(this.x + 4), (float)cy, 112.0f, 20.0f, mouseX, mouseY)) {
                    if (!this.search) {
                        this.page = cate;
                    }
                    this.page.ripple.mouseClicked((float)mouseX, (float)mouseY);
                    this.moduleWheel[0] = 0.0f;
                    this.moduleWheel[1] = 0.0f;
                }
                cy += 24;
            }
        }
        this.currentEditing = null;
        float my = this.y + 6 + this.moduleWheel[1];
        if (this.page.isCategoryPage) {
            for (final Module module : this.search ? Client.instance.moduleManager.getModules() : Client.instance.moduleManager.getModsByCategory(this.page.category)) {
                if (this.search && this.searching != null && !module.getName().toLowerCase().replaceAll(" ", "").contains(this.searching.getValue().toLowerCase().replaceAll(" ", ""))) {
                    continue;
                }
                final float y = my;
                final float h = 16.0f;
                final float left = (float)(this.x + 500);
                final boolean hover = RenderUtil.isHovering(left, y + 8.0f - 14.0f, 28.0f, (float)(FontManager.arial18.getHeight() + 10), mouseX, mouseY);
                if (hover) {
                    module.toggle();
                }
                my += 18.0f;
                for (final Value<?> option : module.getValues()) {
                    my = this.handleClick(my, option, mouseX, mouseY);
                }
            }
        }
        else {
            for (final Value<?> option2 : this.page.uiGenerator.getOptions()) {
                my = this.handleClick(my, option2, mouseX, mouseY);
            }
        }
        if (mouseButton == 0 && RenderUtil.isHovering((float)this.x, (float)this.y, 120.0f, 30.0f, mouseX, mouseY)) {
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
            this.dragging = true;
        }
    }
    
    private Color getColor(final int r, final int g, final int b) {
        return RenderUtil.reAlpha(new Color(r, g, b), Math.min(Math.round(this.visibleAnimation / 100.0f * 255.0f), 255));
    }
    
    private Color getColor(final int r, final int g, final int b, final int a) {
        return RenderUtil.reAlpha(new Color(r, g, b), Math.min(Math.round(this.visibleAnimation / 100.0f * a), 255));
    }
    
    private Color getColor(final Color color) {
        return RenderUtil.reAlpha(color, Math.min(Math.round(this.visibleAnimation / 100.0f * color.getAlpha()), 255));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    private float handleClick(float my, final Value<?> option, final int mouseX, final int mouseY) {
        final float x = (float)(this.x + 8);
        if (option instanceof BoolValue) {
            final BoolValue opt = (BoolValue)option;
            if (RenderUtil.isHovering(x + 125.0f, my, 400.0f, 10.0f, mouseX, mouseY)) {
                opt.setValue(!opt.getValue());
            }
            my += 20.0f;
        }
        if (option instanceof NumberValue) {
            final NumberValue opt2 = (NumberValue)option;
            opt2.sliding = RenderUtil.isHovering(x + 125.0f, my + 10.0f, 395.0f, 8.0f, mouseX, mouseY);
            my += 22.0f;
        }
        if (option instanceof ModeValue) {
            final ModeValue opt3 = (ModeValue)option;
            if (RenderUtil.isHovering(x + 125.0f, my + 10.0f, 392.0f, 16.0f, mouseX, mouseY)) {
                opt3.expanded = !opt3.expanded;
                opt3.animation.mouseClicked((float)mouseX, (float)mouseY);
            }
            if (opt3.expanded) {
                float comboY = my + 30.0f;
                for (final Enum s : opt3.getModes()) {
                    if (!s.equals(opt3.getValue())) {
                        if (RenderUtil.isHovering(x + 127.0f, comboY, 392.0f, 16.0f, mouseX, mouseY)) {
                            opt3.setValue(s);
                            opt3.expanded = false;
                        }
                        comboY += 16.0f;
                    }
                }
            }
            my += 30 + ((opt3.expanded && opt3.getModes().length > 1) ? (16 * (opt3.getModes().length - 1)) : 0) + opt3.height;
        }
        if (option instanceof ColorValue) {
            final int height = 85;
            my += height;
        }
        return my;
    }
    
    private float drawOptions(float my, final Value<?> option, final int mouseX, final int mouseYi) {
        final float x = (float)(this.x + 8);
        if (option instanceof BoolValue) {
            final BoolValue opt = (BoolValue)option;
            final float finalMy = my;
            RenderUtil.drawCircle2(x + 125.0f + 5.0f, finalMy + 5.0f, 12.0f, this.getColor(new Color(6710888)).getRGB());
            RenderUtil.drawCircle2(x + 125.0f + 5.0f, finalMy + 5.0f, 12.0f, HUD.mainColor.getColor());
            if (opt.getValue()) {
                opt.alpha = AnimationUtil.animateSmooth(opt.alpha, 255.0f, 0.1f);
                RenderUtil.renderLine(x + 127.0f, my + 5.0f, x + 129.5, my + 8.0f, 1.5f, this.getColor(Color.WHITE).getRGB());
                RenderUtil.renderLine(x + 129.5, my + 8.0f, x + 132.5, my + 2.0f, 1.5f, this.getColor(Color.WHITE).getRGB());
            }
            else {
                opt.alpha = AnimationUtil.animateSmooth(opt.alpha, 0.0f, 0.1f);
            }
            FontManager.arial16.drawString(opt.getName(), x + 138.0f, my + 2.0f, this.getColor(255, 255, 255, 150).getRGB());
            my += 20.0f;
        }
        if (option instanceof NumberValue) {
            final NumberValue opt2 = (NumberValue)option;
            FontManager.arial16.drawString(opt2.getName(), x + 125.0f, my + 1.0f, this.getColor(255, 255, 255, 150).getRGB());
            FontManager.arial16.drawString(String.valueOf(opt2.getValue().floatValue()), x + 522.0f - 8.0f - FontManager.arial16.getStringWidth(opt2.getValue().toString()), my + 1.0f, this.getColor(255, 255, 255, 150).getRGB());
            final double percentage = (opt2.getValue().floatValue() - opt2.getMin()) / (opt2.getMax() - opt2.getMin());
            final double sliderOffset = x + 126.0f;
            final double sliderWidth = 387.0;
            if (opt2.animatedPercentage < percentage) {
                final double inc = 1.0 / Minecraft.getDebugFPS() * 2.0;
                if (percentage - opt2.animatedPercentage < inc) {
                    opt2.animatedPercentage = percentage;
                }
                else {
                    final NumberValue numberValue = opt2;
                    numberValue.animatedPercentage += inc;
                }
            }
            else if (opt2.animatedPercentage > percentage) {
                final double inc = 1.0 / Minecraft.getDebugFPS() * 2.0;
                if (opt2.animatedPercentage - percentage < inc) {
                    opt2.animatedPercentage = percentage;
                }
                else {
                    final NumberValue numberValue2 = opt2;
                    numberValue2.animatedPercentage -= inc;
                }
            }
            if (opt2.sliding) {
                double num = Math.max(opt2.getMin(), Math.min(opt2.getMax(), round((mouseX - (x + 126.0f)) * (opt2.getMax() - opt2.getMin()) / 387.0 + opt2.getMin(), opt2.getInc())));
                num = Math.round(num * (1.0 / opt2.getInc())) / (1.0 / opt2.getInc());
                opt2.setValue(num);
            }
            Gui.drawRect3(sliderOffset, (double)(my + 1.0f + 14.0f), 387.0, 1.0, this.getColor(new Color(-2135904080, true)).getRGB());
            Gui.drawRect3(sliderOffset, (double)(my + 1.0f + 14.0f), 387.0 * opt2.animatedPercentage, 1.0, this.getColor(new Color(HUD.mainColor.getColor())).getRGB());
            GLRendering.glDrawFilledEllipse(sliderOffset + 387.0 * opt2.animatedPercentage, my + 1.0f + 14.0f + 0.5, 12.0f, this.getColor(new Color(HUD.mainColor.getColor())).getRGB());
            if (opt2.sliding) {
                GLRendering.glDrawFilledEllipse(sliderOffset + 387.0 * opt2.animatedPercentage, my + 1.0f + 14.0f + 0.5, 17.0f, ColorUtil.overwriteAlphaComponent(this.getColor(new Color(HUD.mainColor.getColor())).getRGB(), 80));
            }
            my += 22.0f;
        }
        if (option instanceof ModeValue) {
            final ModeValue opt3 = (ModeValue)option;
            FontManager.arial16.drawString(opt3.getName(), x + 125.0f, my - 1.0f, this.getColor(255, 255, 255, 150).getRGB());
            RoundedUtils.round(x + 125.0f, my + 10.0f, 392.0f, (float)(16 + ((opt3.expanded && opt3.getModes().length > 1) ? (16 * (opt3.getModes().length - 1)) : 0)), 2.0f, this.getColor(0, 0, 0, 40));
            if (opt3.expanded) {
                float comboY = my + 30.0f;
                for (final Enum s : opt3.getModes()) {
                    if (!s.name().equals(opt3.getConfigValue())) {
                        FontManager.arial16.drawString(s.name(), x + 127.0f, comboY + 2.0f, this.getColor(255, 255, 255, 150).getRGB());
                        comboY += 16.0f;
                    }
                }
            }
            FontManager.arial16.drawString(opt3.getConfigValue(), x + 127.0f, my + 14.0f, this.getColor(255, 255, 255, 180).getRGB());
            Mask.drawOnMask();
            opt3.height = AnimationUtil.animateSmooth(opt3.height, (float)((opt3.expanded && opt3.getModes().length > 1) ? (16 * (opt3.getModes().length - 1)) : 0), 0.3f);
            my += 30.0f + opt3.height;
        }
        if (option instanceof ColorValue) {
            final ColorValue opt4 = (ColorValue)option;
            final float left = x + 122.0f;
            final float right = x + 122.0f + 400.0f - 4.0f;
            final float top = 0.0f;
            final Color lastColor = RenderUtil.getColor(opt4.getColor());
            final HSBData hsbData = new HSBData(lastColor);
            final float[] hsba = { hsbData.getHue(), hsbData.getSaturation(), hsbData.getBrightness(), hsbData.getAlpha() };
            final int height = 85;
            FontManager.arial16.drawString(opt4.getName(), left + 3.0f, top + 3.0f + my, this.getColor(255, 255, 255, 150).getRGB());
            Gui.drawRect3((double)(right - 77.0f - 0.5f), (double)(top + 3.0f - 0.5f + my), 10.0, 10.0, this.getColor(Color.BLACK).getRGB());
            for (int xPosition = 0; xPosition < 5; ++xPosition) {
                for (int yPosition = 0; yPosition < 5; ++yPosition) {
                    Gui.drawRect3((double)(right - 77.0f + xPosition * 2), (double)(top + 3.0f + my + yPosition * 2), 2.0, 2.0, (yPosition % 2 == 0 == (xPosition % 2 == 0)) ? this.getColor(255, 255, 255).getRGB() : this.getColor(190, 190, 190).getRGB());
                }
            }
            Gui.drawRect3((double)(right - 77.0f), (double)(top + 3.0f + my), 10.0, 10.0, this.getColor(hsbData.getAsColor()).getRGB());
            final boolean onCurrent = RenderUtil.isHovering(right - 78.0f, top + 2.0f + my, 12.0f, 12.0f, mouseX, mouseYi);
            if (onCurrent) {
                Gui.drawRect3((double)(right - 77.0f), (double)(top + 3.0f + my), 10.0, 10.0, this.getColor(0, 0, 0, 80).getRGB());
            }
            Gui.drawRect3((double)(right - 63.0f), (double)(top + 3.0f + my), 60.0, 60.0, this.getColor(Color.getHSBColor(hsba[0], 1.0f, 1.0f)).getRGB());
            RenderUtil.drawHGradientRect(right - 63.0f, top + 3.0f + my, 60.0, 60.0, this.getColor(Color.getHSBColor(hsba[0], 0.0f, 1.0f)).getRGB(), 15);
            RenderUtil.drawVGradientRect(right - 63.0f, top + 3.0f + my, 60.0, 60.0, 15, this.getColor(Color.getHSBColor(hsba[0], 1.0f, 0.0f)).getRGB());
            Gui.drawRect3((double)(right - 63.0f + hsba[1] * 60.0f - 1.0f), (double)(top + 3.0f + my + (1.0f - hsba[2]) * 60.0f - 1.0f), 2.0, 2.0, new Color(0, 0, 0, 100).getRGB());
            final boolean onSB = RenderUtil.isHovering(right - 64.0f, top + 2.0f + my, 62.0f, 62.0f, mouseX, mouseYi);
            if (onSB && Mouse.isButtonDown(0)) {
                hsbData.setSaturation(Math.min(Math.max((mouseX - (right - 63.0f)) / 60.0f, 0.0f), 1.0f));
                hsbData.setBrightness(1.0f - Math.min(Math.max((mouseYi - (top + 3.0f + my)) / 60.0f, 0.0f), 1.0f));
                opt4.setColor(hsbData.getAsColor().getRGB());
            }
            for (int i = 0; i < 60; ++i) {
                final Color lasCol = Color.getHSBColor(Math.max(i - 1, 0) / 60.0f, 1.0f, 1.0f);
                final Color tarCol = Color.getHSBColor(i / 60.0f, 1.0f, 1.0f);
                RenderUtil.drawHGradientRect(right - 63.0f + i, top + 3.0f + my + 63.0f, 1.0, 6.0, this.getColor(lasCol).getRGB(), this.getColor(tarCol).getRGB());
            }
            Gui.drawRect3((double)(right - 63.0f + hsba[0] * 60.0f - 0.5f), (double)(top + 3.0f + my + 63.0f), 1.0, 6.0, new Color(0, 0, 0, 80).getRGB());
            final boolean onHue = RenderUtil.isHovering(right - 64.0f, top + 2.0f + my + 63.0f, 62.0f, 8.0f, mouseX, mouseYi);
            if (onHue && Mouse.isButtonDown(0)) {
                hsbData.setHue(Math.min(Math.max((mouseX - (right - 63.0f)) / 60.0f, 0.0f), 1.0f));
                opt4.setColor(hsbData.getAsColor().getRGB());
            }
            my += 85.0f;
        }
        return my;
    }
    
    public static double round(final double value, final double inc) {
        if (inc == 0.0) {
            return value;
        }
        if (inc == 1.0) {
            return (double)Math.round(value);
        }
        final double halfOfInc = inc / 2.0;
        final double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc).doubleValue();
        }
        return new BigDecimal(floored).doubleValue();
    }
    
    private void readAvatar(final byte[] bytes) throws Exception {
        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        final BufferedImage bi1 = ImageIO.read(bais);
        try {
            final File w2 = new File("W:\\img\\00000000003.jpg");
            this.mc.getTextureManager().loadTexture(new ResourceLocation("ircavatar"), (ITextureObject)new DynamicTexture(bi1));
        }
        finally {
            bais.close();
        }
    }
    
    static {
        NewClickGui.instance = new NewClickGui();
    }
    
    private enum Page
    {
        Combat(true), 
        Movement(true), 
        Player(true), 
        Render(true), 
        World(true), 
        Misc(true);
        
        public final boolean isCategoryPage;
        public final RippleAnimation ripple;
        public Category category;
        public UIGenerator uiGenerator;
        public float alpha;
        public float x;
        
        private Page(final boolean isCategoryPage) {
            this.category = Category.Combat;
            this.alpha = 0.0f;
            this.x = 6.0f;
            this.isCategoryPage = isCategoryPage;
            this.ripple = new RippleAnimation();
            if (isCategoryPage) {
                for (final Category cat : Category.values()) {
                    if (cat.name().equals(this.name())) {
                        this.category = cat;
                    }
                }
            }
        }
    }
    
    private static class Config
    {
        public RippleAnimation animation1;
        public RippleAnimation animation2;
        public RippleAnimation animation3;
        
        public Config(final File object) {
            this.animation1 = new RippleAnimation();
            this.animation2 = new RippleAnimation();
            this.animation3 = new RippleAnimation();
        }
    }
    
    public interface UIGenerator
    {
        ArrayList<Value<?>> getOptions();
    }
}

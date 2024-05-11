//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import java.text.*;
import java.awt.*;
import shop.xiaoda.utils.render.fontRender.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import shop.xiaoda.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.misc.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.gui.ui.modules.*;
import shop.xiaoda.gui.ui.*;
import shop.xiaoda.gui.notification.*;
import shop.xiaoda.utils.render.animation.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.module.*;
import org.lwjgl.input.*;
import java.util.*;
import java.util.List;

public class HUD extends Module
{
    public final ModeValue<HUDmode> hudModeValue;
    public static BoolValue arraylist;
    public static final BoolValue importantModules;
    public static final BoolValue hLine;
    public static final NumberValue height;
    public static final ModeValue<ModuleList.ANIM> animation;
    public static final BoolValue background;
    public static final NumberValue backgroundAlpha;
    public static BoolValue tab;
    public static BoolValue notifications;
    public static BoolValue Debug;
    public static BoolValue potionInfo;
    public static BoolValue targetHud;
    public static ModeValue<THUDMode> thudmodeValue;
    public static BoolValue multi_targetHUD;
    public static BoolValue titleBar;
    private final ModeValue<TitleMode> modeValue;
    public static BoolValue Information;
    public static NumberValue animationSpeed;
    public static NumberValue scoreBoardHeightValue;
    public static ColorValue mainColor;
    private final TabGUI tabGUI;
    private float leftY;
    public int offsetValue;
    private final SimpleDateFormat dateFormat;
    
    public HUD() {
        super("HUD", Category.Render);
        this.hudModeValue = new ModeValue<HUDmode>("HUD Mode", HUDmode.values(), HUDmode.Neon);
        this.modeValue = new ModeValue<TitleMode>("Title Mode", TitleMode.values(), TitleMode.Simple);
        this.tabGUI = new TabGUI();
        this.offsetValue = 0;
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.setState(true);
    }
    
    public static Color color(final int tick) {
        Color textColor = new Color(-1);
        textColor = ColorUtil.fade(5, tick * 20, new Color(HUD.mainColor.getColor()), 1.0f);
        return textColor;
    }
    
    public static RapeMasterFontManager getFont() {
        return FontManager.arial18;
    }
    
    @EventTarget
    public void onShader(final EventShader e) {
        final String fps = String.valueOf(Minecraft.getDebugFPS());
        Label_0447: {
            if (HUD.titleBar.getValue()) {
                final String lowerCase = this.modeValue.getValue().toString().toLowerCase();
                switch (lowerCase) {
                    case "simple": {
                        switch (this.hudModeValue.getValue()) {
                            case Shit: {
                                final String str = EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + Client.instance.USER + EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS() + "fps" + EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + (HUD.mc.isSingleplayer() ? "SinglePlayer" : HUD.mc.getCurrentServerData().serverIP);
                                RoundedUtils.drawRound(6.0f, 6.0f, (float)(FontManager.arial16.getStringWidth(str) + 8 + FontManager.bold22.getStringWidth(Client.NAME.toUpperCase())), 15.0f, 0.0f, new Color(0, 0, 0));
                                RoundedUtils.drawRound(6.0f, 6.0f, (float)(FontManager.arial16.getStringWidth(str) + 8 + FontManager.bold22.getStringWidth(Client.NAME.toUpperCase())), 1.0f, 1.0f, new Color(0, 0, 0));
                                break Label_0447;
                            }
                            case Neon: {
                                final String title = String.format("| v%s | %s | %sfps", Client.instance.getVersion(), Client.instance.USER, Minecraft.getDebugFPS());
                                final String mark = Client.NAME;
                                final float width = (float)(getFont().getStringWidth(title) + FontManager.bold22.getStringWidth(mark) + 6);
                                Gui.drawRect3(4.0, 4.0, (double)(width + 6.0f), (double)(FontManager.bold22.getHeight() + 4), new Color(0, 0, 0, 255).getRGB());
                                break Label_0447;
                            }
                        }
                        break;
                    }
                }
            }
        }
        this.drawNotificationsEffects(e.isBloom());
        if (HUD.tab.getValue()) {
            this.tabGUI.blur(this, this.leftY);
        }
    }
    
    @EventTarget
    public void onKey(final EventKey e) {
        if (HUD.tab.getValue()) {
            this.tabGUI.onKey(e.getKey());
        }
    }
    
    @EventTarget
    public void onRender(final EventRender2D e) {
        final String name = Client.instance.USER;
        final String fps = String.valueOf(Minecraft.getDebugFPS());
        final int lengthName = FontManager.Tahoma16.getStringWidth(name);
        final int nlRectX = lengthName + 74;
        final UiModule DebugHud = Client.instance.uiManager.getModule((Class)Debug.class);
        DebugHud.setState((boolean)HUD.Debug.getValue());
        final UiModule Arraylist = Client.instance.uiManager.getModule((Class)ModuleList.class);
        Arraylist.setState((boolean)HUD.arraylist.getValue());
        final UiModule InformationHUD = Client.instance.uiManager.getModule((Class)Information.class);
        InformationHUD.setState((boolean)HUD.Information.getValue());
        final UiModule potionHUD = Client.instance.uiManager.getModule((Class)PotionsInfo.class);
        potionHUD.setState((boolean)HUD.potionInfo.getValue());
        this.drawNotifications();
        if (HUD.titleBar.getValue()) {
            final String lowerCase = this.modeValue.getValue().toString().toLowerCase();
            switch (lowerCase) {
                case "simple": {
                    switch (this.hudModeValue.getValue()) {
                        case Shit: {
                            final String str = EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + Client.instance.USER + EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS() + "fps" + EnumChatFormatting.DARK_GRAY + " | " + EnumChatFormatting.WHITE + (HUD.mc.isSingleplayer() ? "SinglePlayer" : HUD.mc.getCurrentServerData().serverIP);
                            RoundedUtils.drawRound(6.0f, 6.0f, (float)(FontManager.arial16.getStringWidth(str) + 8 + FontManager.bold22.getStringWidth(Client.NAME.toUpperCase())), 15.0f, 0.0f, new Color(19, 19, 19, 230));
                            RoundedUtils.drawRound(6.0f, 6.0f, (float)(FontManager.arial16.getStringWidth(str) + 8 + FontManager.bold22.getStringWidth(Client.NAME.toUpperCase())), 1.0f, 1.0f, color(8));
                            FontManager.arial16.drawString(str, (float)(11 + FontManager.bold22.getStringWidth(Client.NAME.toUpperCase())), 11.5f, Color.WHITE.getRGB());
                            FontManager.bold22.drawString(Client.NAME.toUpperCase(), 9.5f, 12.0f, color(8).getRGB());
                            FontManager.bold22.drawString(Client.NAME.toUpperCase(), 10.0f, 12.5f, Color.WHITE.getRGB());
                            break;
                        }
                        case Neon: {
                            final String title = String.format("| v%s | %s | %sfps", Client.instance.getVersion(), Client.instance.USER, Minecraft.getDebugFPS());
                            final String mark = Client.NAME;
                            final float width = (float)(getFont().getStringWidth(title) + FontManager.bold22.getStringWidth(mark) + 6);
                            RenderUtil.drawRect(4.0, 4.0, width + 10.0f, FontManager.bold22.getHeight() + 8, new Color(0, 0, 0, 100).getRGB());
                            FontManager.bold22.drawStringDynamic(mark, 8.0, 10.5, 1, 6);
                            getFont().drawString(title, (float)(12 + FontManager.bold22.getStringWidth(mark)), 9.0f, -1);
                            break;
                        }
                    }
                    this.leftY = AnimationUtil.animate(this.leftY, 24.0f, 0.3f);
                    break;
                }
                case "neverlose": {
                    Gui.drawRect(5.0, 5.0, (double)nlRectX, 20.0, new Color(0, 0, 0, 190).getRGB());
                    FontManager.Tahoma18.drawString(Client.NAME, 11.2f, 10.2f, new Color(71, 166, 253).getRGB());
                    FontManager.Tahoma18.drawString(Client.NAME, 10.0f, 9.5f, new Color(255, 255, 255).getRGB());
                    FontManager.Tahoma18.drawString("|", 43.0f, 9.5f, new Color(255, 255, 255).getRGB());
                    FontManager.Tahoma16.drawString(fps, 48.5f, 10.3f, new Color(255, 255, 255).getRGB());
                    FontManager.Tahoma18.drawString("|", 64.0f, 9.5f, new Color(255, 255, 255).getRGB());
                    FontManager.Tahoma16.drawString(name, 70.0f, 10.3f, new Color(255, 255, 255).getRGB());
                    RenderUtil.drawShadow(5.0f, 5.0f, (float)nlRectX, 20.0f);
                    break;
                }
                case "onetap": {
                    final String text = Client.NAME + " | " + fps + " | " + name + " | Yaw: " + (int)HUD.mc.thePlayer.rotationYaw % 360 + " | Pitch: " + (int)HUD.mc.thePlayer.rotationPitch;
                    final int otRectX = FontManager.Tahoma14.getStringWidth(text) + 11;
                    RenderUtil.drawRoundedRect(5.0f, 6.0f, (float)otRectX, 19.0f, 4, new Color(0, 0, 0, 200).getRGB());
                    RenderUtil.drawRoundedRect(5.0f, 5.0f, (float)otRectX, 7.0f, 4, HUD.mainColor.getColor());
                    FontManager.Tahoma14.drawStringWithShadow(text, 8.0f, 11.0f, new Color(255, 255, 255).getRGB());
                    break;
                }
            }
        }
        final UiModule Thud = Client.instance.uiManager.getModule((Class)TargetHud.class);
        Thud.setState((boolean)HUD.targetHud.getValue());
        if (HUD.tab.getValue()) {
            this.tabGUI.renderTabGUI(this, this.leftY);
        }
    }
    
    public void drawNotifications() {
        final ScaledResolution sr = new ScaledResolution(HUD.mc);
        float yOffset = 0.0f;
        NotificationManager.setToggleTime(2.0f);
        for (final Notification notification : NotificationManager.getNotifications()) {
            final Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimerUtil().hasTimeElapsed((long)notification.getTime()) ? Direction.BACKWARDS : Direction.FORWARDS);
            if (animation.finished(Direction.BACKWARDS)) {
                NotificationManager.getNotifications().remove(notification);
            }
            else {
                animation.setDuration(200);
                final int actualOffset = 3;
                final int notificationHeight = 23;
                final int notificationWidth = FontManager.arial20.getStringWidth(notification.getDescription()) + 25;
                final float x = (float)(sr.getScaledWidth() - (notificationWidth + 5) * animation.getOutput());
                final float y = sr.getScaledHeight() - (yOffset + 18.0f + this.offsetValue + notificationHeight + 15.0f);
                notification.drawLettuce(x, y, (float)notificationWidth, (float)notificationHeight);
                yOffset += (float)((notificationHeight + actualOffset) * animation.getOutput());
            }
        }
    }
    
    public void drawNotificationsEffects(final boolean bloom) {
        final ScaledResolution sr = new ScaledResolution(HUD.mc);
        float yOffset = 0.0f;
        for (final Notification notification : NotificationManager.getNotifications()) {
            final Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimerUtil().hasTimeElapsed((long)notification.getTime()) ? Direction.BACKWARDS : Direction.FORWARDS);
            if (animation.finished(Direction.BACKWARDS)) {
                NotificationManager.getNotifications().remove(notification);
            }
            else {
                animation.setDuration(200);
                final int actualOffset = 3;
                final int notificationHeight = 23;
                final int notificationWidth = FontManager.arial20.getStringWidth(notification.getDescription()) + 25;
                final float x = (float)(sr.getScaledWidth() - (notificationWidth + 5) * animation.getOutput());
                final float y = sr.getScaledHeight() - (yOffset + 18.0f + this.offsetValue + notificationHeight + 15.0f);
                notification.blurLettuce(x, y, (float)notificationWidth, (float)notificationHeight, bloom);
                yOffset += (float)((notificationHeight + actualOffset) * animation.getOutput());
            }
        }
    }
    
    static {
        HUD.arraylist = new BoolValue("Arraylist", true);
        importantModules = new BoolValue("Arraylist-Important", false, () -> HUD.arraylist.getValue());
        hLine = new BoolValue("Arraylist-HLine", true, () -> HUD.arraylist.getValue());
        height = new NumberValue("Arraylist-Height", 11.0, 9.0, 20.0, 1.0, () -> HUD.arraylist.getValue());
        animation = new ModeValue<ModuleList.ANIM>("Arraylist-Animation", ModuleList.ANIM.values(), ModuleList.ANIM.ScaleIn, () -> HUD.arraylist.getValue());
        background = new BoolValue("Arraylist-Background", true, () -> HUD.arraylist.getValue());
        backgroundAlpha = new NumberValue("Arraylist-Background Alpha", 0.35, 0.01, 1.0, 0.01, () -> HUD.arraylist.getValue());
        HUD.tab = new BoolValue("TabGUI", false);
        HUD.notifications = new BoolValue("Notification", false);
        HUD.Debug = new BoolValue("Debug", false);
        HUD.potionInfo = new BoolValue("Potion", false);
        HUD.targetHud = new BoolValue("TargetHUD", true);
        HUD.thudmodeValue = new ModeValue<THUDMode>("THUD Style", THUDMode.values(), THUDMode.Neon);
        HUD.multi_targetHUD = new BoolValue("Multi TargetHUD", true);
        HUD.titleBar = new BoolValue("TitleBar", true);
        HUD.Information = new BoolValue("Information", false);
        HUD.animationSpeed = new NumberValue("Animation Speed", 4.0, 1.0, 10.0, 0.1);
        HUD.scoreBoardHeightValue = new NumberValue("Scoreboard Height", 0.0, 0.0, 300.0, 1.0);
        HUD.mainColor = new ColorValue("Main Color", Color.white.getRGB());
    }
    
    public class TabGUI
    {
        private Section section;
        private Category selectedType;
        private Module selectedModule;
        private int maxType;
        private int maxModule;
        private float horizonAnimation;
        private int currentType;
        private int currentModule;
        
        public TabGUI() {
            this.section = Section.TYPES;
            this.selectedType = Category.values()[0];
            this.selectedModule = null;
            this.horizonAnimation = 0.0f;
            this.currentType = 0;
            this.currentModule = 0;
        }
        
        public void init() {
            final Category[] arrCategory = Category.values();
            final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            for (final Category category : arrCategory) {
                final int categoryWidth = fontRenderer.getStringWidth(category.name().toUpperCase()) + 4;
                this.maxType = Math.max(this.maxType, categoryWidth);
            }
            final ModuleManager moduleManager = Client.instance.moduleManager;
            for (final Module module : Client.instance.moduleManager.getModules()) {
                final int moduleWidth = fontRenderer.getStringWidth(module.getName().toUpperCase()) + 4;
                this.maxModule = Math.max(this.maxModule, moduleWidth);
            }
            this.maxModule += 12;
            this.maxType = Math.max(this.maxType, this.maxModule);
            this.maxModule += this.maxType;
        }
        
        public void blur(final HUD hud, final float y) {
            float moduleY;
            float categoryY = moduleY = y;
            final int moduleWidth = 60;
            final int moduleX = moduleWidth + 1;
            Gui.drawRect(4.0, (double)categoryY, (double)(moduleWidth - 3), (double)(categoryY + 12 * Category.values().length), new Color(0, 0, 0, 255).getRGB());
            for (final Category category : Category.values()) {
                final boolean isSelected = this.selectedType == category;
                if (isSelected) {
                    Gui.drawRect(5.0, (double)(categoryY + 2.0f), 6.5, (double)(float)(categoryY + HUD.getFont().getHeight() + 1.5), HUD.color(0).getRGB());
                    moduleY = categoryY;
                }
                categoryY += 12.0f;
            }
            if (this.section == Section.MODULES || this.horizonAnimation > 1.0f) {
                final int moduleHeight = 12 * Client.instance.moduleManager.getModsByCategory(this.selectedType).size();
                if (this.horizonAnimation < moduleWidth) {
                    this.horizonAnimation += (float)((moduleWidth - this.horizonAnimation) / 20.0);
                }
                Gui.drawRect((double)moduleX, (double)moduleY, (double)(moduleX + this.horizonAnimation), (double)(moduleY + moduleHeight), new Color(0, 0, 0, 255).getRGB());
                for (final Module module : Client.instance.moduleManager.getModsByCategory(this.selectedType)) {
                    final boolean isSelected2 = this.selectedModule == module;
                    if (isSelected2) {
                        Gui.drawRect((double)(moduleX + 1.0f), (double)(moduleY + 2.0f), (double)(moduleX + 2.5f), (double)(moduleY + HUD.getFont().getHeight() + 1.0f), new Color(0, 0, 0, 255).getRGB());
                    }
                    moduleY += 12.0f;
                }
            }
            if (this.horizonAnimation > 0.0f && this.section != Section.MODULES) {
                this.horizonAnimation -= 5.0f;
            }
        }
        
        public void renderTabGUI(final HUD hud, final float y) {
            float moduleY;
            float categoryY = moduleY = y;
            final int moduleWidth = 60;
            final int moduleX = moduleWidth + 1;
            Gui.drawRect(4.0, (double)categoryY, (double)(moduleWidth - 3), (double)(categoryY + 12 * Category.values().length), new Color(0, 0, 0, 100).getRGB());
            for (final Category category : Category.values()) {
                final boolean isSelected = this.selectedType == category;
                final int color = isSelected ? -1 : new Color(150, 150, 150).getRGB();
                HUD.getFont().drawString(category.name(), 8.0f, categoryY + 2.0f, color);
                if (isSelected) {
                    Gui.drawRect(5.0, (double)(categoryY + 2.0f), 6.5, (double)(float)(categoryY + HUD.getFont().getHeight() + 1.5), HUD.color(0).getRGB());
                    moduleY = categoryY;
                }
                categoryY += 12.0f;
            }
            if (this.section == Section.MODULES || this.horizonAnimation > 1.0f) {
                final int moduleHeight = 12 * Client.instance.moduleManager.getModsByCategory(this.selectedType).size();
                if (this.horizonAnimation < moduleWidth) {
                    this.horizonAnimation += (float)((moduleWidth - this.horizonAnimation) / 20.0);
                }
                Gui.drawRect((double)moduleX, (double)moduleY, (double)(moduleX + this.horizonAnimation), (double)(moduleY + moduleHeight), new Color(0, 0, 0, 100).getRGB());
                for (final Module module : Client.instance.moduleManager.getModsByCategory(this.selectedType)) {
                    final boolean isSelected2 = this.selectedModule == module;
                    final int color2 = isSelected2 ? new Color(-1).getRGB() : (module.getState() ? -1 : 11184810);
                    HUD.getFont().drawString(module.getName(), (float)(moduleX + 3), moduleY + 2.0f, color2);
                    if (isSelected2) {
                        Gui.drawRect((double)(moduleX + 1.0f), (double)(moduleY + 2.0f), (double)(moduleX + 2.5f), (double)(moduleY + HUD.getFont().getHeight() + 1.0f), HUD.color(0).getRGB());
                    }
                    moduleY += 12.0f;
                }
            }
            if (this.horizonAnimation > 0.0f && this.section != Section.MODULES) {
                this.horizonAnimation -= 5.0f;
            }
        }
        
        public void onKey(final int key) {
            final Minecraft mc = Minecraft.getMinecraft();
            final ModuleManager moduleManager = Client.instance.moduleManager;
            final Category[] values = Category.values();
            if (mc.gameSettings.showDebugInfo) {
                return;
            }
            final int KEY_DOWN = Keyboard.KEY_DOWN;
            final int KEY_UP = Keyboard.KEY_UP;
            final int KEY_RIGHT = Keyboard.KEY_RIGHT;
            final int KEY_RETURN = Keyboard.KEY_RETURN;
            final int KEY_LEFT = Keyboard.KEY_LEFT;
            switch (key) {
                case 208: {
                    if (this.section == Section.TYPES) {
                        this.currentType = (this.currentType + 1) % values.length;
                        this.selectedType = values[this.currentType];
                        break;
                    }
                    if (this.section == Section.MODULES) {
                        final List<Module> modulesByCategory = (List<Module>)moduleManager.getModsByCategory(this.selectedType);
                        this.currentModule = (this.currentModule + 1) % modulesByCategory.size();
                        this.selectedModule = modulesByCategory.get(this.currentModule);
                        break;
                    }
                    break;
                }
                case 200: {
                    if (this.section == Section.TYPES) {
                        this.currentType = (this.currentType + values.length - 1) % values.length;
                        this.selectedType = values[this.currentType];
                        break;
                    }
                    if (this.section == Section.MODULES) {
                        final List<Module> modulesByCategory = (List<Module>)moduleManager.getModsByCategory(this.selectedType);
                        this.currentModule = (this.currentModule + modulesByCategory.size() - 1) % modulesByCategory.size();
                        this.selectedModule = modulesByCategory.get(this.currentModule);
                        break;
                    }
                    break;
                }
                case 205: {
                    if (this.section == Section.TYPES) {
                        this.currentModule = 0;
                        this.selectedModule = moduleManager.getModsByCategory(this.selectedType).get(0);
                        this.section = Section.MODULES;
                        this.horizonAnimation = 0.0f;
                        break;
                    }
                    break;
                }
                case 28: {
                    if (this.section == Section.MODULES) {
                        this.selectedModule.toggle();
                        break;
                    }
                    if (this.section == Section.TYPES) {
                        this.currentModule = 0;
                        this.section = Section.MODULES;
                        this.selectedModule = moduleManager.getModsByCategory(this.selectedType).get(0);
                        break;
                    }
                    break;
                }
                case 203: {
                    if (this.section == Section.MODULES) {
                        this.section = Section.TYPES;
                        this.currentModule = 0;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public enum Section
    {
        TYPES, 
        MODULES;
    }
    
    public enum TitleMode
    {
        Simple, 
        NeverLose, 
        OneTap;
    }
    
    public enum HUDmode
    {
        Neon, 
        Shit;
    }
    
    public enum THUDMode
    {
        Neon, 
        Novoline, 
        Exhibition, 
        ThunderHack, 
        Raven, 
        Sils, 
        WTFNovo, 
        Exire, 
        Moon;
    }
}

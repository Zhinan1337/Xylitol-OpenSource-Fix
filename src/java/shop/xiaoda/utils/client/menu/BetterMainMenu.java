// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.client.menu;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import shop.xiaoda.gui.altmanager.ColorUtils;
import shop.xiaoda.gui.altmanager.GuiAltManager;
import shop.xiaoda.gui.clickgui.book.RippleAnimation;
import shop.xiaoda.utils.client.menu.button.MenuButton;
import shop.xiaoda.utils.client.menu.utils.MainMenuBackground;
import shop.xiaoda.utils.misc.MinecraftInstance;
import shop.xiaoda.utils.misc.MouseUtils;
import shop.xiaoda.utils.render.RenderUtil;
import shop.xiaoda.utils.render.RoundedUtils;
import shop.xiaoda.utils.render.animation.Animation;
import shop.xiaoda.utils.render.animation.Direction;
import shop.xiaoda.utils.render.animation.impl.DecelerateAnimation;
import shop.xiaoda.utils.render.fontRender.FontManager;

public class BetterMainMenu
        extends GuiScreen
        implements MinecraftInstance {
    public boolean hoverSwitch;
    public boolean hoverFlushed;
    public RippleAnimation a3 = new RippleAnimation();
    public Animation a = new DecelerateAnimation(500, 1.0);
    public Animation a2 = new DecelerateAnimation(500, 1.0);
    private final List<MenuButton> buttons = Arrays.asList(new MenuButton("Singleplayer"), new MenuButton("Multiplayer"), new MenuButton("AltManager"), new MenuButton("Settings"), new MenuButton("Exit"));
    private List<String> bgNames = Arrays.asList("mainmenu", "login", "load", "mtf");
    int bg = 0;

    public BetterMainMenu() {
        try {
            this.shaderBackground = new MainMenuBackground("/assets/minecraft/express/shader/mainmenu.fsh");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        this.buttons.forEach(MenuButton::initGui);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String switchBGText = "Click to switch background.";
        this.a.setDirection(this.hoverSwitch ? Direction.FORWARDS : Direction.BACKWARDS);
        this.a2.setDirection(this.hoverFlushed ? Direction.FORWARDS : Direction.BACKWARDS);
        this.hoverSwitch = MouseUtils.isHovering(8.0f, 8.0f, 25.0f + (float)this.a.getOutput() * (float)(FontManager.lettuceFont20.getStringWidth(switchBGText) + 5), 25.0f, mouseX, mouseY);
        this.hoverFlushed = MouseUtils.isHovering(this.width - 128, 8.0f, 120.0f, 60.0f, mouseX, mouseY);
        this.renderBackground();
        FontManager.icontestFont90.drawCenteredStringWithShadow((Object)((Object)EnumChatFormatting.BOLD) + "T", (float)this.width / 2.0f + 5.0f, (float)this.height / 2.0f - 80.0f, ColorUtils.interpolateColorsBackAndForth(12, 1, new Color(106, 181, 253), new Color(111, 119, 253), true).getRGB());
        FontManager.lettuceFont20.drawStringWithShadow("Made with     by Xylitol dev team", 1.0f, this.height - 10, Color.WHITE.getRGB());
        FontManager.icontestFont20.drawStringWithShadow("Q", 1 + FontManager.lettuceFont20.getStringWidth("Made with "), this.height - 8, Color.RED.getRGB());
        RoundedUtils.drawRound(8.0f, 8.0f, 25.0f + (float)this.a.getOutput() * (float)(FontManager.lettuceFont20.getStringWidth(switchBGText) + 10), 25.0f, 12.0f, ColorUtils.interpolateColorC(new Color(0, 0, 0, 100), new Color(0, 0, 0, 150), (float)this.a.getOutput()));
        this.a3.draw(() -> RoundedUtils.drawRound(8.0f, 8.0f, 25.0f + (float)this.a.getOutput() * (float)(FontManager.lettuceFont20.getStringWidth(switchBGText) + 10), 25.0f, 12.0f, ColorUtils.interpolateColorC(new Color(0, 0, 0, 100), new Color(0, 0, 0, 150), (float)this.a.getOutput())));
        RenderUtil.scissorStart(7.0, 6.0, 25.0f + (float)this.a.getOutput() * (float)(FontManager.lettuceFont20.getStringWidth(switchBGText) + 10) + 3.0f, 30.0);
        FontManager.icontestFont40.drawCenteredString("k", 20.5f, FontManager.icontestFont20.getMiddleOfBox(25.0f) + 3.5f, Color.WHITE.getRGB());
        FontManager.lettuceFont20.drawString(switchBGText, 35.0f, FontManager.icontestFont40.getMiddleOfBox(25.0f) + 14.0f, Color.WHITE.getRGB());
        RenderUtil.scissorEnd();
        MinecraftInstance.mc.getTextureManager().bindTexture(new ResourceLocation("express/icon/banner.png"));
        RoundedUtils.drawRoundTextured(this.width - 128, 8.0f, 120.0f, 60.0f, 12.5f, 1.0f);
        RoundedUtils.drawRound((float)this.width - 128.2f, 8.0f, 120.2f, 60.8f, 12.5f, ColorUtils.interpolateColorC(new Color(0, 0, 0, 100), new Color(0, 0, 0, 150), (float)this.a2.getOutput()));
        FontManager.lettuceBoldFont26.drawCenteredStringWithShadow("Sponsored by", (float)this.width - 69.5f, 25.0f, ColorUtils.interpolateColor(Color.WHITE, Color.WHITE.darker(), (float)this.a2.getOutput()));
        FontManager.lettuceBoldFont26.drawCenteredStringWithShadow("Asaka Shop", (float)this.width - 69.5f, 40.0f, ColorUtils.interpolateColor(Color.WHITE, Color.WHITE.darker(), (float)this.a2.getOutput()));
        float buttonWidth = 140.0f;
        float buttonHeight = 25.0f;
        int count = 0;
        for (MenuButton button : this.buttons) {
            button.x = (float)this.width / 2.0f - buttonWidth / 2.0f;
            button.y = (float)this.height / 2.0f - buttonHeight / 2.0f - 25.0f + (float)count;
            button.width = buttonWidth;
            button.height = buttonHeight;
            button.clickAction = () -> {
                switch (button.text) {
                    case "Singleplayer": {
                        MinecraftInstance.mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    }
                    case "Multiplayer": {
                        MinecraftInstance.mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    }
                    case "Settings": {
                        MinecraftInstance.mc.displayGuiScreen(new GuiOptions(this, MinecraftInstance.mc.gameSettings));
                        break;
                    }
                    case "AltManager": {
                        MinecraftInstance.mc.displayGuiScreen(new GuiAltManager(this));
                        break;
                    }
                    case "Exit": {
                        MinecraftInstance.mc.shutdown();
                    }
                }
            };
            button.drawScreen(mouseX, mouseY);
            count = (int)((float)count + (buttonHeight + 5.0f));
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void doSwitchBG() {
        if (this.bg >= this.bgNames.size()) {
            this.bg = 0;
        }
        String name = this.bgNames.get(this.bg);
        System.out.println(this.bg + "-" + name);
        try {
            this.shaderBackground = new MainMenuBackground("/assets/minecraft/express/shader/" + name + ".fsh");
            ++this.bg;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
        this.a3.mouseClicked(mouseX, mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hoverSwitch && mouseButton == 0) {
            this.doSwitchBG();
        }
        if (this.hoverFlushed && mouseButton == 0) {
            try {
                Desktop.getDesktop().browse(new URI("https://asaka.lol"));
            }
            catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

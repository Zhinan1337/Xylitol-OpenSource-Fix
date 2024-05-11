//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.utils.render.fontRender.*;
import java.io.*;

public abstract class GuiAltLogin extends GuiScreen
{
    private final GuiScreen previousScreen;
    private GuiTextField username;
    protected volatile String status;
    
    public GuiAltLogin(final GuiScreen previousScreen) {
        this.status = EnumChatFormatting.YELLOW + "Pending...";
        this.previousScreen = previousScreen;
    }
    
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                if (this.username.getText().length() != 0) {
                    this.onLogin(this.username.getText(), "");
                    break;
                }
                this.status = EnumChatFormatting.RED + "Login failed!";
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 1145: {
                this.onLogin(StringUtils.randomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_", 10), "");
                break;
            }
        }
    }
    
    public abstract void onLogin(final String p0, final String p1);
    
    public void drawScreen(final int x, final int y, final float z) {
        this.drawDefaultBackground();
        this.drawBackground(0);
        this.username.drawTextBox();
        FontManager.arial20.drawCenteredStringWithShadow("Directly Login", (float)(this.width / 2), 20.0f, -1);
        FontManager.arial20.drawCenteredStringWithShadow(this.status, (float)(this.width / 2), (float)(this.height / 4 + 24 + 38), -1);
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            FontManager.arial20.drawStringWithShadow("Username", (float)(this.width / 2 - 96), (float)(this.height / 4 + 24 + 72 - 4), -7829368);
        }
        super.drawScreen(x, y, z);
    }
    
    public void initGui() {
        final int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.buttonList.add(new GuiButton(1145, this.width / 2 - 100, var3 + 72 + 12 + 48, "Random User Name"));
        (this.username = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 100, var3 + 72 - 12, 200, 20)).setFocused(true);
        this.username.setMaxStringLength(200);
    }
    
    protected void keyTyped(final char character, final int key) throws IOException {
        super.keyTyped(character, key);
        if (character == '\t' && this.username.isFocused()) {
            this.username.setFocused(!this.username.isFocused());
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }
    
    protected void mouseClicked(final int x, final int y, final int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.username.mouseClicked(x, y, button);
    }
    
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}

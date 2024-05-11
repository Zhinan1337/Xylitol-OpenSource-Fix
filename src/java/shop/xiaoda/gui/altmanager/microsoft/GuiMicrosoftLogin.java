//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager.microsoft;

import org.apache.commons.io.*;
import net.minecraft.client.gui.*;
import java.io.*;
import shop.xiaoda.utils.render.fontRender.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

public final class GuiMicrosoftLogin extends GuiScreen
{
    private volatile MicrosoftLogin microsoftLogin;
    private volatile boolean closed;
    private final GuiScreen parentScreen;
    
    public GuiMicrosoftLogin(final GuiScreen parentScreen) {
        this.closed = false;
        this.parentScreen = parentScreen;
        final Thread thread = new Thread("MicrosoftLogin Thread") {
            @Override
            public void run() {
                try {
                    GuiMicrosoftLogin.this.microsoftLogin = new MicrosoftLogin();
                    while (!GuiMicrosoftLogin.this.closed) {
                        if (GuiMicrosoftLogin.this.microsoftLogin.logged) {
                            IOUtils.closeQuietly((Closeable)GuiMicrosoftLogin.this.microsoftLogin);
                            GuiMicrosoftLogin.this.closed = true;
                            GuiMicrosoftLogin.this.microsoftLogin.setStatus("Login successful! " + GuiMicrosoftLogin.this.microsoftLogin.getUserName());
                            GuiMicrosoftLogin.this.mc.session = new Session(GuiMicrosoftLogin.this.microsoftLogin.getUserName(), GuiMicrosoftLogin.this.microsoftLogin.getUuid(), GuiMicrosoftLogin.this.microsoftLogin.getAccessToken(), "mojang");
                            break;
                        }
                    }
                }
                catch (Throwable e) {
                    GuiMicrosoftLogin.this.closed = true;
                    e.printStackTrace();
                    IOUtils.closeQuietly((Closeable)GuiMicrosoftLogin.this.microsoftLogin);
                    GuiMicrosoftLogin.this.microsoftLogin.setStatus("Login failed! " + e.getClass().getName() + ":" + e.getMessage());
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            if (this.microsoftLogin != null && !this.closed) {
                this.microsoftLogin.close();
                this.closed = true;
            }
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 50, "Back"));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawBackground(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.microsoftLogin == null) {
            FontManager.arial20.drawCenteredStringWithShadow(EnumChatFormatting.YELLOW + "Logging in...", this.width / 2.0f, this.height / 2.0f - 5.0f, -1);
        }
        else {
            FontManager.arial20.drawCenteredStringWithShadow(this.microsoftLogin.getStatus(), this.width / 2.0f, this.height / 2.0f - 5.0f, -1);
        }
    }
}

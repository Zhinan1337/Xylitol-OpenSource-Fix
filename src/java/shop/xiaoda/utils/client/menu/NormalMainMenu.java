//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.menu;

import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.utils.render.fontRender.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.gui.altmanager.*;
import java.io.*;

public class NormalMainMenu extends GuiScreen
{
    private float currentX;
    private float currentY;
    private static String str;
    private int alpha;
    
    public NormalMainMenu() {
        if (NormalMainMenu.str == null) {
            NormalMainMenu.str = "i like fadouse";
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution sr = new ScaledResolution(this.mc);
        Gui.drawRect(10.0, 10.0, (double)(sr.getScaledWidth() + 10), (double)(sr.getScaledHeight() + 10), Color.BLACK.getAlpha());
        final int w = sr.getScaledWidth();
        final int h = sr.getScaledHeight();
        final double halfW = sr.getScaledWidth_double() / 2.0;
        final double halfH = sr.getScaledHeight_double() / 2.0;
        GlStateManager.pushMatrix();
        this.currentX += (float)((mouseX - halfW - this.currentX) / sr.getScaleFactor() * 0.30000001192092896);
        this.currentY += (float)((mouseY - halfH - this.currentY) / sr.getScaleFactor() * 0.30000001192092896);
        GlStateManager.translate(this.currentX / 40.0f, this.currentY / 40.0f, 0.0f);
        final ScaledResolution res = new ScaledResolution(this.mc);
        RenderUtil.drawImage(new ResourceLocation("express/Background.jpg"), 0, 0, res.getScaledWidth(), res.getScaledHeight());
        final int PZ0 = 20;
        final int PZ2 = PZ0 * 2;
        Gui.drawScaledCustomSizeModalRect(-PZ0, -PZ0, 0.0f, 0.0f, w + PZ2, h + PZ2, w + PZ2, h + PZ2, (float)(w + PZ2), (float)(h + PZ2));
        GlStateManager.bindTexture(0);
        for (int i = 0; i < 4; ++i) {
            final int xd = i * 22;
            final boolean hover = mouseX > this.width / 2 - 60 && mouseX < this.width / 2 + 60 && mouseY > this.height / 2 - 40 + xd && mouseY < this.height / 2 - 20 + xd;
            String str = "";
            switch (i) {
                case 0: {
                    str = "Single Player";
                    break;
                }
                case 1: {
                    str = "Multi Player";
                    break;
                }
                case 2: {
                    str = "Options";
                    break;
                }
                case 3: {
                    str = "AltManager";
                    break;
                }
            }
            RenderUtil.drawRect(this.width / 2 - 60, this.height / 2 - 40 + xd, this.width / 2 + 60, this.height / 2 - 20 + xd, new Color(0, 0, 0, hover ? 120 : 80).getAlpha());
            FontManager.Tahoma20.drawCenteredString(str, (float)(this.width / 2), (float)(this.height / 2 - 33 + xd), -1);
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(2881);
        GL11.glDisable(2881);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        FontManager.Tahoma18.drawCenteredString("Null " + Client.instance.getUser(), (float)(this.width / 2), 5.0f, -1);
        FontManager.Tahoma18.drawString("01smX3 " + Client.instance.getVersion() + " <" + NormalMainMenu.str + "\u00a7r>", 5.0f, (float)(this.height - 13), -1);
        final CopyOnWriteArrayList<String> strs = new CopyOnWriteArrayList<String>();
        strs.add("none");
        strs.sort((o1, o2) -> FontManager.Tahoma14.getStringWidth(o2) - FontManager.Tahoma14.getStringWidth(o1));
        FontManager.Tahoma14.drawString(String.valueOf(Client.instance.getVersion()) + " SkidLog:", 5.0f, 5.0f, -1);
        int y = 15;
        final Iterator<String> iterator = strs.iterator();
        while (iterator.hasNext()) {
            final String str = iterator.next();
            FontManager.Tahoma14.drawString(str, 5.0f, (float)y, -1);
            y += 9;
        }
        GlStateManager.popMatrix();
        this.alpha -= 0;
        if (this.alpha < 0) {
            this.alpha = 0;
        }
        RenderUtil.drawRect(0.0, 0.0, this.width, this.height, new Color(0, 0, 0, this.alpha).getRGB());
        if (!Client.instance.isLogged()) {
            Client.instance.setLogged(true);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < 4; ++i) {
            final int xd = i * 22;
            final boolean bl;
            final boolean hover = bl = (mouseX > this.width / 2 - 60 && mouseX < this.width / 2 + 60 && mouseY > this.height / 2 - 40 + xd && mouseY < this.height / 2 - 20 + xd);
            if (hover) {
                if (mouseButton == 0) {
                    switch (i) {
                        case 0: {
                            this.mc.displayGuiScreen((GuiScreen)new GuiSelectWorld((GuiScreen)this));
                            break;
                        }
                        case 1: {
                            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
                            break;
                        }
                        case 2: {
                            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
                        }
                        case 3: {
                            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager((GuiScreen)this));
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public static void setStr(final String state) {
        NormalMainMenu.str = state;
    }
    
    public void drawImage(final ResourceLocation image, final int x, final int y, final int width, final int height) {
        this.mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GlStateManager.bindTexture(0);
    }
}

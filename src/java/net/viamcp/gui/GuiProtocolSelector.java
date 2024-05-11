//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.gui;

import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.viamcp.*;
import net.viamcp.protocols.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class GuiProtocolSelector extends GuiScreen
{
    private GuiScreen parent;
    public SlotList list;
    
    public GuiProtocolSelector(final GuiScreen parent) {
        this.parent = parent;
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 25, 200, 20, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 180, this.height - 25, 75, 20, "Credits"));
        this.list = new SlotList(this.mc, this.width, this.height, 32, this.height - 32, 10);
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        this.list.actionPerformed(guiButton);
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen((GuiScreen)new GuiCredits((GuiScreen)this));
        }
    }
    
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0, 2.0, 2.0);
        final String title = EnumChatFormatting.BOLD + "ViaMCP Reborn";
        this.drawString(this.fontRendererObj, title, (this.width - this.fontRendererObj.getStringWidth(title) * 2) / 4, 5, -1);
        GlStateManager.popMatrix();
        final String versionName = ProtocolCollection.getProtocolById(ViaMCP.getInstance().getVersion()).getName();
        final String versionCodeName = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getName();
        final String versionReleaseDate = ProtocolCollection.getProtocolInfoById(ViaMCP.getInstance().getVersion()).getReleaseDate();
        final String versionTitle = "Version: " + versionName + " - " + versionCodeName;
        final String versionReleased = "Released: " + versionReleaseDate;
        final int fixedHeight = (5 + this.fontRendererObj.FONT_HEIGHT) * 2 + 2;
        this.drawString(this.fontRendererObj, new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Version Information").toString(), (this.width - this.fontRendererObj.getStringWidth("Version Information")) / 2, fixedHeight, -1);
        this.drawString(this.fontRendererObj, versionTitle, (this.width - this.fontRendererObj.getStringWidth(versionTitle)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT, -1);
        this.drawString(this.fontRendererObj, versionReleased, (this.width - this.fontRendererObj.getStringWidth(versionReleased)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 2, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    class SlotList extends GuiSlot
    {
        public SlotList(final Minecraft mc, final int width, final int height, final int top, final int bottom, final int slotHeight) {
            super(mc, width, height, top + 30, bottom, 18);
        }
        
        protected int getSize() {
            return ProtocolCollection.values().length;
        }
        
        protected void elementClicked(final int i, final boolean b, final int i1, final int i2) {
            final int protocolVersion = ProtocolCollection.values()[i].getVersion().getVersion();
            ViaMCP.getInstance().setVersion(protocolVersion);
            ViaMCP.getInstance().asyncSlider.setVersion(protocolVersion);
        }
        
        protected boolean isSelected(final int i) {
            return false;
        }
        
        protected void drawBackground() {
            GuiProtocolSelector.this.drawDefaultBackground();
        }
        
        protected void drawSlot(final int i, final int i1, final int i2, final int i3, final int i4, final int i5) {
            Gui.drawCenteredString(this.mc.fontRendererObj, ((ViaMCP.getInstance().getVersion() == ProtocolCollection.values()[i].getVersion().getVersion()) ? (EnumChatFormatting.GREEN.toString() + EnumChatFormatting.BOLD) : EnumChatFormatting.GRAY.toString()) + ProtocolCollection.getProtocolById(ProtocolCollection.values()[i].getVersion().getVersion()).getName(), this.width / 2, i2 + 2, -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            Gui.drawCenteredString(this.mc.fontRendererObj, "PVN: " + ProtocolCollection.getProtocolById(ProtocolCollection.values()[i].getVersion().getVersion()).getVersion(), this.width, (i2 + 2) * 2 + 20, -1);
            GlStateManager.popMatrix();
        }
    }
}

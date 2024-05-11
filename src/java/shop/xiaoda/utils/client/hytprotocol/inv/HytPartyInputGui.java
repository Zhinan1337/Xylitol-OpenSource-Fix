//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol.inv;

import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.utils.misc.*;
import shop.xiaoda.utils.client.hytprotocol.metadata.*;
import shop.xiaoda.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.hytprotocol.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;

public class HytPartyInputGui extends GuiEditSign implements MinecraftInstance
{
    private String field;
    private VexViewMetadata confirm;
    String prefix;
    
    public HytPartyInputGui(final String field, final VexViewMetadata confirm, final String prefix) {
        super(getSign());
        this.field = field;
        this.confirm = confirm;
        this.prefix = prefix;
    }
    
    public static TileEntitySign getSign() {
        final TileEntitySign teSign = new TileEntitySign();
        teSign.setWorldObj((World)Client.mc.theWorld);
        teSign.setPos(BlockPos.ORIGIN);
        return teSign;
    }
    
    public void onGuiClosed() {
        try {
            Sender.sendJson(Sender.getTextSetJson(this.field, this.tileSign.signText[0].getFormattedText().trim()));
            Sender.sendJson(Sender.getButtionClickJson(this.confirm.id));
            Sender.sendJson("{\"packet_sub_type\":\"null\",\"packet_data\":\"null\",\"packet_type\":\"gui_close\"}");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "\u8bf7\u8f93\u5165\u73a9\u5bb6 ID", this.width / 2, 40, 16777215);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), 0.0f, 50.0f);
        final float f = 93.75f;
        GlStateManager.scale(-f, -f, -f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        final Block block = this.tileSign.getBlockType();
        if (block == Blocks.standing_sign) {
            final float f2 = this.tileSign.getBlockMetadata() * 360 / 16.0f;
            GlStateManager.rotate(f2, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int i = this.tileSign.getBlockMetadata();
            float f3 = 0.0f;
            if (i == 2) {
                f3 = 180.0f;
            }
            if (i == 4) {
                f3 = 90.0f;
            }
            if (i == 5) {
                f3 = -90.0f;
            }
            GlStateManager.rotate(f3, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

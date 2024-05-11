//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol.inv;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import shop.xiaoda.utils.*;
import net.minecraft.item.*;
import java.io.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;

public class TestInvGui extends GuiContainer
{
    private static final ResourceLocation CHEST_GUI_TEXTURE;
    
    public TestInvGui() {
        super((Container)new TestContainer());
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        final int i = (this.width - this.xSize) / 2;
        final int j = (this.height - this.ySize) / 2;
        final int cut = 89;
        final int off = 33;
        this.fontRendererObj.drawString("sb", i + 8, j + off + 6, 4210752);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TestInvGui.CHEST_GUI_TEXTURE);
        this.drawTexturedModalRect(i, j + off, 0, 0, this.xSize, 35);
        this.drawTexturedModalRect(i, j + 18 + 17 + off, 0, 126 + cut, this.xSize, 96 - cut);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        if (slot != null && slot.getHasStack()) {
            final ItemStack clickedStack = slot.getStack();
            DebugUtil.log("\u4f60\u70b9\u4e86 " + clickedStack.getDisplayName());
            if (clickedStack.getItem() instanceof ItemSword) {
                final ItemSword sword = (ItemSword)clickedStack.getItem();
                final String material = sword.getToolMaterialName();
                if (material.equalsIgnoreCase("iron")) {}
            }
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void handleMouseClick(final Slot slotIn, final int slotId, final int clickedButton, final int clickType) {
        if (clickType == 4) {
            return;
        }
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }
    
    static {
        CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    }
    
    private static class TestContainer extends Container
    {
        private IInventory inventory;
        private ArrayList<Slot> slots;
        
        public TestContainer() {
            this.slots = new ArrayList<Slot>();
            this.inventory = (IInventory)new InventoryBasic("HytSkywarsContainer", false, 9);
            final int playerInventoryStartX = 8;
            final int playerInventoryStartY = 51;
            for (int j = 0; j < 9; ++j) {
                final Slot slot = new Slot(this.inventory, j, playerInventoryStartX + j * 18, playerInventoryStartY);
                this.slots.add(slot);
                this.addSlotToContainer(slot);
            }
            final ItemStack singleItem = new ItemStack(Items.iron_sword);
            singleItem.canItalic = false;
            singleItem.setStackDisplayName("\u5355\u4eba\u6a21\u5f0f");
            singleItem.addEnchantment(Enchantment.unbreaking, 1);
            final ItemStack doubleItem = new ItemStack(Items.diamond_sword);
            doubleItem.canItalic = false;
            doubleItem.setStackDisplayName("\u53cc\u4eba\u6a21\u5f0f");
            doubleItem.addEnchantment(Enchantment.unbreaking, 1);
            this.slots.get(0).putStack(singleItem);
            this.slots.get(1).putStack(doubleItem);
        }
        
        public boolean canInteractWith(final EntityPlayer playerIn) {
            return true;
        }
    }
}

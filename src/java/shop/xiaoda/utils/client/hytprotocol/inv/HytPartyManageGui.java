//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol.inv;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.hytprotocol.metadata.*;
import net.minecraft.client.renderer.*;
import shop.xiaoda.utils.client.hytprotocol.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;

public class HytPartyManageGui extends GuiContainer
{
    private static final ResourceLocation CHEST_GUI_TEXTURE;
    private VexViewMetadata leaveButton;
    private VexViewMetadata disbandButton;
    private VexViewMetadata inviteButton;
    private VexViewMetadata requestButton;
    private VexViewMetadata disband;
    private VexViewMetadata invite;
    private VexViewMetadata request;
    public static Item leaveMaterial;
    public static Item disbandMaterial;
    public static Item inviteMaterial;
    public static Item requestMaterial;
    
    public HytPartyManageGui(final VexViewMetadata leaveButton, final VexViewMetadata disbandButton, final VexViewMetadata inviteButton, final VexViewMetadata requestButton) {
        super((Container)new HytPartyManageContainer(inviteButton != null, disbandButton != null));
        this.leaveButton = leaveButton;
        this.disbandButton = disbandButton;
        this.inviteButton = inviteButton;
        this.requestButton = requestButton;
        this.disband = null;
        this.invite = null;
        this.request = null;
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        final int i = (this.width - this.xSize) / 2;
        final int j = (this.height - this.ySize) / 2;
        final int cut = 89;
        final int off = 33;
        this.fontRendererObj.drawString("\u5750\u6302\u8f66", i + 8, j + off + 6, 4210752);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(HytPartyManageGui.CHEST_GUI_TEXTURE);
        this.drawTexturedModalRect(i, j + off, 0, 0, this.xSize, 35);
        this.drawTexturedModalRect(i, j + 18 + 17 + off, 0, 126 + cut, this.xSize, 96 - cut);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        if (slot != null && slot.getHasStack()) {
            final ItemStack clickedStack = slot.getStack();
            final Item item = clickedStack.getItem();
            if (item.equals(HytPartyManageGui.leaveMaterial)) {
                Sender.sendButtonClicked(this.leaveButton.id);
            }
            else if (item.equals(HytPartyManageGui.disbandMaterial)) {
                Sender.sendButtonClicked(this.disbandButton.id);
            }
            else if (item.equals(HytPartyManageGui.inviteMaterial)) {
                Sender.sendButtonClicked(this.inviteButton.id);
            }
            else if (item.equals(HytPartyManageGui.requestMaterial)) {
                Sender.sendButtonClicked(this.requestButton.id);
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
        HytPartyManageGui.leaveMaterial = Item.getItemFromBlock(Blocks.redstone_block);
        HytPartyManageGui.disbandMaterial = Item.getItemFromBlock(Blocks.redstone_torch);
        HytPartyManageGui.inviteMaterial = Item.getItemFromBlock((Block)Blocks.chest);
        HytPartyManageGui.requestMaterial = Items.iron_door;
    }
    
    private static class HytPartyManageContainer extends Container
    {
        private IInventory inventory;
        private ArrayList<Slot> slots;
        private boolean canInvite;
        
        public HytPartyManageContainer(final boolean canInvite, final boolean canDisband) {
            this.slots = new ArrayList<Slot>();
            this.canInvite = canInvite;
            this.inventory = (IInventory)new InventoryBasic("HytPartyManageContainer", false, 9);
            final int playerInventoryStartX = 8;
            final int playerInventoryStartY = 51;
            for (int j = 0; j < 9; ++j) {
                final Slot slot = new Slot(this.inventory, j, playerInventoryStartX + j * 18, playerInventoryStartY);
                this.slots.add(slot);
                this.addSlotToContainer(slot);
            }
            if (canInvite) {
                final ItemStack leaveItem = new ItemStack(HytPartyManageGui.leaveMaterial);
                leaveItem.canItalic = false;
                leaveItem.setStackDisplayName("\u79bb\u5f00\u961f\u4f0d");
                leaveItem.addEnchantment(Enchantment.unbreaking, 1);
                this.slots.get(0).putStack(leaveItem);
                final ItemStack disbandItem = new ItemStack(HytPartyManageGui.disbandMaterial);
                disbandItem.canItalic = false;
                disbandItem.setStackDisplayName("\u89e3\u6563\u961f\u4f0d");
                disbandItem.addEnchantment(Enchantment.unbreaking, 1);
                this.slots.get(1).putStack(disbandItem);
                final ItemStack inviteItem = new ItemStack(HytPartyManageGui.inviteMaterial);
                inviteItem.canItalic = false;
                inviteItem.setStackDisplayName("\u9080\u8bf7\u5165\u961f");
                inviteItem.addEnchantment(Enchantment.unbreaking, 1);
                this.slots.get(2).putStack(inviteItem);
                final ItemStack requestItem = new ItemStack(HytPartyManageGui.requestMaterial);
                requestItem.canItalic = false;
                requestItem.setStackDisplayName("\u7533\u8bf7\u5217\u8868");
                requestItem.addEnchantment(Enchantment.unbreaking, 1);
                this.slots.get(3).putStack(requestItem);
            }
            else {
                final ItemStack leaveItem = new ItemStack(HytPartyManageGui.leaveMaterial);
                leaveItem.canItalic = false;
                leaveItem.setStackDisplayName("\u79bb\u5f00\u961f\u4f0d");
                leaveItem.addEnchantment(Enchantment.unbreaking, 1);
                this.slots.get(0).putStack(leaveItem);
                if (canDisband) {
                    final ItemStack disbandItem = new ItemStack(HytPartyManageGui.disbandMaterial);
                    disbandItem.canItalic = false;
                    disbandItem.setStackDisplayName("\u89e3\u6563\u961f\u4f0d");
                    disbandItem.addEnchantment(Enchantment.unbreaking, 1);
                    this.slots.get(1).putStack(disbandItem);
                }
            }
        }
        
        public boolean canInteractWith(final EntityPlayer playerIn) {
            return true;
        }
    }
}

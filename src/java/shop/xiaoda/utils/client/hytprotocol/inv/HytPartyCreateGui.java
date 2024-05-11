// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.client.hytprotocol.inv;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shop.xiaoda.utils.client.hytprotocol.Sender;
import shop.xiaoda.utils.client.hytprotocol.metadata.VexViewMetadata;

public class HytPartyCreateGui
        extends GuiContainer {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private VexViewMetadata createButton;
    private VexViewMetadata joinButton;
    public static Item createMaterial = Item.getItemFromBlock(Blocks.crafting_table);
    public static Item joinMaterial = Items.oak_door;

    public HytPartyCreateGui(VexViewMetadata createButton, VexViewMetadata joinButton) {
        super(new HytPartyCreateContainer());
        this.createButton = createButton;
        this.joinButton = joinButton;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int cut = 89;
        int off = 33;
        this.fontRendererObj.drawString("创建挂车", i + 8, j + off + 6, 0x404040);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        this.drawTexturedModalRect(i, j + off, 0, 0, this.xSize, 35);
        this.drawTexturedModalRect(i, j + 18 + 17 + off, 0, 126 + cut, this.xSize, 96 - cut);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        if (slot != null && slot.getHasStack()) {
            ItemStack clickedStack = slot.getStack();
            Item item = clickedStack.getItem();
            if (item.equals(createMaterial)) {
                Sender.sendButtonClicked(this.createButton.id);
            } else if (item.equals(joinMaterial)) {
                Sender.sendButtonClicked(this.joinButton.id);
            }
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
        if (clickType == 4) {
            return;
        }
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }

    private static class HytPartyCreateContainer
            extends Container {
        private IInventory inventory;
        private ArrayList<Slot> slots = new ArrayList();

        public HytPartyCreateContainer() {
            this.inventory = new InventoryBasic("HytPartyCreateContainer", false, 9);
            int playerInventoryStartX = 8;
            int playerInventoryStartY = 51;
            for (int j = 0; j < 9; ++j) {
                Slot slot = new Slot(this.inventory, j, playerInventoryStartX + j * 18, playerInventoryStartY);
                this.slots.add(slot);
                this.addSlotToContainer(slot);
            }
            ItemStack createItem = new ItemStack(createMaterial);
            createItem.canItalic = false;
            createItem.setStackDisplayName("创建队伍");
            createItem.addEnchantment(Enchantment.unbreaking, 1);
            this.slots.get(0).putStack(createItem);
            ItemStack joinItem = new ItemStack(joinMaterial);
            joinItem.canItalic = false;
            joinItem.setStackDisplayName("加入队伍");
            joinItem.addEnchantment(Enchantment.unbreaking, 1);
            this.slots.get(1).putStack(joinItem);
        }

        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    }
}

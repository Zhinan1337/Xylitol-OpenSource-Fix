//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.world.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class InvCleaner extends Module
{
    private final ModeValue<MODE> mode;
    private final NumberValue delay;
    public final NumberValue slotWeapon;
    public final NumberValue slotPick;
    public final NumberValue slotAxe;
    public final NumberValue slotGapple;
    public final NumberValue slotShovel;
    public final NumberValue slotBow;
    public final NumberValue slotBlock;
    public final NumberValue slotPearl;
    public final String[] serverItems;
    private final int[] bestArmorPieces;
    private final List<Integer> trash;
    private final int[] bestToolSlots;
    private final List<Integer> gappleStackSlots;
    private int bestSwordSlot;
    private int bestPearlSlot;
    private int bestBowSlot;
    private boolean serverOpen;
    private boolean clientOpen;
    private int ticksSinceLastClick;
    private boolean nextTickCloseInventory;
    
    public InvCleaner() {
        super("InvManager", Category.World);
        this.mode = new ModeValue<MODE>("Mode", MODE.values(), MODE.Spoof);
        this.delay = new NumberValue("Delay", 0.0, 0.0, 300.0, 10.0);
        this.slotWeapon = new NumberValue("Weapon Slot", 1.0, 1.0, 9.0, 1.0);
        this.slotPick = new NumberValue("Pickaxe Slot", 2.0, 1.0, 9.0, 1.0);
        this.slotAxe = new NumberValue("Axe Slot", 3.0, 1.0, 9.0, 1.0);
        this.slotGapple = new NumberValue("Gapple Slot", 4.0, 1.0, 9.0, 1.0);
        this.slotShovel = new NumberValue("Shovel Slot", 5.0, 1.0, 9.0, 1.0);
        this.slotBow = new NumberValue("Bow Slot", 6.0, 1.0, 9.0, 1.0);
        this.slotBlock = new NumberValue("Block Slot", 7.0, 1.0, 9.0, 1.0);
        this.slotPearl = new NumberValue("Pearl Slot", 8.0, 1.0, 9.0, 1.0);
        this.serverItems = new String[] { "\u9009\u62e9\u6e38\u620f", "\u52a0\u5165\u6e38\u620f", "\u804c\u4e1a\u9009\u62e9\u83dc\u5355", "\u79bb\u5f00\u5bf9\u5c40", "\u518d\u6765\u4e00\u5c40", "selector", "tracking compass", "(right click)", "tienda ", "perfil", "salir", "shop", "collectibles", "game", "profil", "lobby", "show all", "hub", "friends only", "cofre", "(click", "teleport", "play", "exit", "hide all", "jeux", "gadget", " (activ", "emote", "amis", "bountique", "choisir", "choose " };
        this.bestArmorPieces = new int[4];
        this.trash = new ArrayList<Integer>();
        this.bestToolSlots = new int[3];
        this.gappleStackSlots = new ArrayList<Integer>();
    }
    
    @EventTarget
    private void onPacket(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof S2DPacketOpenWindow) {
            this.clientOpen = false;
            this.serverOpen = false;
        }
    }
    
    @EventTarget
    private void onPacketSend(final EventPacketSend event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof C16PacketClientStatus) {
            final C16PacketClientStatus clientStatus = (C16PacketClientStatus)packet;
            if (clientStatus.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                this.clientOpen = true;
                this.serverOpen = true;
            }
        }
        else if (packet instanceof C0DPacketCloseWindow) {
            final C0DPacketCloseWindow packetCloseWindow = (C0DPacketCloseWindow)packet;
            if (packetCloseWindow.windowId == InvCleaner.mc.thePlayer.inventoryContainer.windowId) {
                this.clientOpen = false;
                this.serverOpen = false;
            }
        }
        else if (packet instanceof C0EPacketClickWindow && !InvCleaner.mc.thePlayer.isUsingItem()) {
            this.ticksSinceLastClick = 0;
        }
    }
    
    private boolean dropItem(final List<Integer> listOfSlots) {
        if (!listOfSlots.isEmpty()) {
            final int slot = listOfSlots.remove(0);
            InventoryUtil.windowClick(InvCleaner.mc, slot, 1, InventoryUtil.ClickType.DROP_ITEM);
            return true;
        }
        return false;
    }
    
    @EventTarget
    private void onMotion(final EventMotion event) {
        if (event.isPost() && !InvCleaner.mc.thePlayer.isUsingItem() && (InvCleaner.mc.currentScreen == null || InvCleaner.mc.currentScreen instanceof GuiChat || InvCleaner.mc.currentScreen instanceof GuiInventory || InvCleaner.mc.currentScreen instanceof GuiIngameMenu)) {
            ++this.ticksSinceLastClick;
            if (this.ticksSinceLastClick < Math.floor(this.delay.getValue() / 50.0)) {
                return;
            }
            if (this.clientOpen || (InvCleaner.mc.currentScreen == null && !this.mode.getValue().name().equals("OpenInv"))) {
                this.clear();
                for (int slot = 5; slot < 45; ++slot) {
                    final ItemStack stack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                    if (stack != null) {
                        if (stack.getItem() instanceof ItemSword && InventoryUtil.isBestSword(InvCleaner.mc.thePlayer, stack)) {
                            this.bestSwordSlot = slot;
                        }
                        else if (stack.getItem() instanceof ItemTool && InventoryUtil.isBestTool(InvCleaner.mc.thePlayer, stack)) {
                            final int toolType = InventoryUtil.getToolType(stack);
                            if (toolType != -1 && slot != this.bestToolSlots[toolType]) {
                                this.bestToolSlots[toolType] = slot;
                            }
                        }
                        else if (stack.getItem() instanceof ItemArmor && InventoryUtil.isBestArmor(InvCleaner.mc.thePlayer, stack)) {
                            final ItemArmor armor = (ItemArmor)stack.getItem();
                            final int pieceSlot = this.bestArmorPieces[armor.armorType];
                            if (pieceSlot == -1 || slot != pieceSlot) {
                                this.bestArmorPieces[armor.armorType] = slot;
                            }
                        }
                        else if (stack.getItem() instanceof ItemBow && InventoryUtil.isBestBow(InvCleaner.mc.thePlayer, stack)) {
                            if (slot != this.bestBowSlot) {
                                this.bestBowSlot = slot;
                            }
                        }
                        else if (stack.getItem() instanceof ItemAppleGold) {
                            this.gappleStackSlots.add(slot);
                        }
                        else if (stack.getItem() instanceof ItemEnderPearl) {
                            this.bestPearlSlot = slot;
                        }
                        else if (!this.trash.contains(slot) && !isValidStack(stack)) {
                            if (!Arrays.stream(this.serverItems).anyMatch(stack.getDisplayName()::contains)) {
                                this.trash.add(slot);
                            }
                        }
                    }
                }
                final boolean busy = !this.trash.isEmpty() || this.equipArmor(false) || this.sortItems(false);
                if (!busy) {
                    if (this.nextTickCloseInventory) {
                        this.close();
                        this.nextTickCloseInventory = false;
                    }
                    else {
                        this.nextTickCloseInventory = true;
                    }
                    return;
                }
                final boolean waitUntilNextTick = !this.serverOpen;
                this.open();
                if (this.nextTickCloseInventory) {
                    this.nextTickCloseInventory = false;
                }
                if (waitUntilNextTick) {
                    return;
                }
                if (this.equipArmor(true)) {
                    return;
                }
                if (this.dropItem(this.trash)) {
                    return;
                }
                this.sortItems(true);
            }
        }
    }
    
    private boolean sortItems(final boolean moveItems) {
        final int goodSwordSlot = this.slotWeapon.getValue().intValue() + 35;
        if (this.bestSwordSlot != -1 && this.bestSwordSlot != goodSwordSlot) {
            if (moveItems) {
                this.putItemInSlot(goodSwordSlot, this.bestSwordSlot);
                this.bestSwordSlot = goodSwordSlot;
            }
            return true;
        }
        final int goodBowSlot = this.slotBow.getValue().intValue() + 35;
        if (this.bestBowSlot != -1 && this.bestBowSlot != goodBowSlot) {
            if (moveItems) {
                this.putItemInSlot(goodBowSlot, this.bestBowSlot);
                this.bestBowSlot = goodBowSlot;
            }
            return true;
        }
        final int goodGappleSlot = this.slotGapple.getValue().intValue() + 35;
        if (!this.gappleStackSlots.isEmpty()) {
            this.gappleStackSlots.sort(Comparator.comparingInt(slot -> InvCleaner.mc.thePlayer.inventoryContainer.getSlot((int)slot).getStack().stackSize));
            final int bestGappleSlot = this.gappleStackSlots.get(0);
            if (bestGappleSlot != goodGappleSlot) {
                if (moveItems) {
                    this.putItemInSlot(goodGappleSlot, bestGappleSlot);
                    this.gappleStackSlots.set(0, goodGappleSlot);
                }
                return true;
            }
        }
        final int[] toolSlots = { this.slotPick.getValue().intValue() + 35, this.slotAxe.getValue().intValue() + 35, this.slotShovel.getValue().intValue() + 35 };
        for (final int toolSlot : this.bestToolSlots) {
            if (toolSlot != -1) {
                final int type = InventoryUtil.getToolType(InvCleaner.mc.thePlayer.inventoryContainer.getSlot(toolSlot).getStack());
                if (type != -1 && toolSlot != toolSlots[type]) {
                    if (moveItems) {
                        this.putToolsInSlot(type, toolSlots);
                    }
                    return true;
                }
            }
        }
        final int goodBlockSlot = this.slotBlock.getValue().intValue() + 35;
        final int mostBlocksSlot = this.getMostBlocks();
        if (mostBlocksSlot != -1 && mostBlocksSlot != goodBlockSlot) {
            final Slot dss = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(goodBlockSlot);
            final ItemStack dsis = dss.getStack();
            if (dsis == null || !(dsis.getItem() instanceof ItemBlock) || dsis.stackSize < InvCleaner.mc.thePlayer.inventoryContainer.getSlot(mostBlocksSlot).getStack().stackSize || !Arrays.stream(this.serverItems).noneMatch(dsis.getDisplayName().toLowerCase()::contains)) {
                this.putItemInSlot(goodBlockSlot, mostBlocksSlot);
            }
        }
        final int goodPearlSlot = this.slotPearl.getValue().intValue() + 35;
        if (this.bestPearlSlot != -1 && this.bestPearlSlot != goodPearlSlot) {
            if (moveItems) {
                this.putItemInSlot(goodPearlSlot, this.bestPearlSlot);
                this.bestPearlSlot = goodPearlSlot;
            }
            return true;
        }
        return false;
    }
    
    public int getMostBlocks() {
        int stack = 0;
        int biggestSlot = -1;
        for (int i = 9; i < 45; ++i) {
            final Slot slot = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i);
            final ItemStack is = slot.getStack();
            if (is != null && is.getItem() instanceof ItemBlock && is.stackSize > stack && Arrays.stream(this.serverItems).noneMatch(is.getDisplayName().toLowerCase()::contains)) {
                stack = is.stackSize;
                biggestSlot = i;
            }
        }
        return biggestSlot;
    }
    
    private boolean equipArmor(final boolean moveItems) {
        for (int i = 0; i < this.bestArmorPieces.length; ++i) {
            final int piece = this.bestArmorPieces[i];
            if (piece != -1) {
                final int armorPieceSlot = i + 5;
                final ItemStack stack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(armorPieceSlot).getStack();
                if (stack == null) {
                    if (moveItems) {
                        InventoryUtil.windowClick(InvCleaner.mc, piece, 0, InventoryUtil.ClickType.SHIFT_CLICK);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private void putItemInSlot(final int slot, final int slotIn) {
        InventoryUtil.windowClick(InvCleaner.mc, slotIn, slot - 36, InventoryUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
    }
    
    private void putToolsInSlot(final int tool, final int[] toolSlots) {
        final int toolSlot = toolSlots[tool];
        InventoryUtil.windowClick(InvCleaner.mc, this.bestToolSlots[tool], toolSlot - 36, InventoryUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        this.bestToolSlots[tool] = toolSlot;
    }
    
    private static boolean isValidStack(final ItemStack stack) {
        return (stack.getItem() instanceof ItemBlock && InventoryUtil.isStackValidToPlace(stack)) || (stack.getItem() instanceof ItemPotion && InventoryUtil.isBuffPotion(stack)) || (stack.getItem() instanceof ItemFood && InventoryUtil.isGoodFood(stack)) || InventoryUtil.isGoodItem(stack.getItem());
    }
    
    public void onEnable() {
        this.ticksSinceLastClick = 0;
        this.clientOpen = (InvCleaner.mc.currentScreen instanceof GuiInventory);
        this.serverOpen = this.clientOpen;
    }
    
    public void onDisable() {
        this.close();
        this.clear();
    }
    
    private void open() {
        if (!this.clientOpen && !this.serverOpen) {
            InvCleaner.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            this.serverOpen = true;
        }
    }
    
    private void close() {
        if (!this.clientOpen && this.serverOpen) {
            InvCleaner.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(InvCleaner.mc.thePlayer.inventoryContainer.windowId));
            this.serverOpen = false;
        }
    }
    
    private void clear() {
        this.trash.clear();
        this.bestBowSlot = -1;
        this.bestSwordSlot = -1;
        this.gappleStackSlots.clear();
        Arrays.fill(this.bestArmorPieces, -1);
        Arrays.fill(this.bestToolSlots, -1);
    }
    
    public enum MODE
    {
        OpenInv, 
        Spoof;
    }
}

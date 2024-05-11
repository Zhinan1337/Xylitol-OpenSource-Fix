//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.client.entity.*;
import java.util.function.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public final class InventoryUtil
{
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;
    
    private InventoryUtil() {
    }
    
    public static int findSlotMatching(final EntityPlayerSP player, final Predicate<ItemStack> cond) {
        for (int i = 44; i >= 9; --i) {
            final ItemStack stack = player.inventoryContainer.getSlot(i).getStack();
            if (cond.test(stack)) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean hasFreeSlots(final EntityPlayerSP player) {
        for (int i = 9; i < 45; ++i) {
            if (!player.inventoryContainer.getSlot(i).getHasStack()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidStack(final EntityPlayerSP player, final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        final Item item = stack.getItem();
        if (item instanceof ItemSword) {
            return isBestSword(player, stack);
        }
        if (item instanceof ItemArmor) {
            return isBestArmor(player, stack);
        }
        if (item instanceof ItemTool) {
            return isBestTool(player, stack);
        }
        if (item instanceof ItemBow) {
            return isBestBow(player, stack);
        }
        if (item instanceof ItemFood) {
            return isGoodFood(stack);
        }
        if (item instanceof ItemBlock) {
            return isStackValidToPlace(stack);
        }
        if (item instanceof ItemPotion) {
            return isBuffPotion(stack);
        }
        return isGoodItem(item);
    }
    
    public static void swap(final int slot, final int switchSlot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, switchSlot, 2, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
    }
    
    public static boolean isGoodItem(final Item item) {
        return item instanceof ItemEnderPearl || item == Items.arrow || item == Items.lava_bucket || item == Items.water_bucket;
    }
    
    public static boolean isBestSword(final EntityPlayerSP player, final ItemStack itemStack) {
        double damage = 0.0;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemSword) {
                final double newDamage = getItemDamage(stack);
                if (newDamage > damage) {
                    damage = newDamage;
                    bestStack = stack;
                }
            }
        }
        return bestStack == itemStack || getItemDamage(itemStack) > damage;
    }
    
    public static boolean isBestArmor(final EntityPlayerSP player, final ItemStack itemStack) {
        final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        double reduction = 0.0;
        ItemStack bestStack = null;
        for (int i = 5; i < 45; ++i) {
            final ItemStack stack = player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemArmor && !stack.getItem().getUnlocalizedName().equalsIgnoreCase("item.helmetChain") && !stack.getItem().getUnlocalizedName().equalsIgnoreCase("item.leggingsChain")) {
                final ItemArmor stackArmor = (ItemArmor)stack.getItem();
                if (stackArmor.armorType == itemArmor.armorType) {
                    final double newReduction = getDamageReduction(stack);
                    if (newReduction > reduction) {
                        reduction = newReduction;
                        bestStack = stack;
                    }
                }
            }
        }
        return bestStack == itemStack || getDamageReduction(itemStack) > reduction;
    }
    
    public static int getToolType(final ItemStack stack) {
        final ItemTool tool = (ItemTool)stack.getItem();
        if (tool instanceof ItemPickaxe) {
            return 0;
        }
        if (tool instanceof ItemAxe) {
            return 1;
        }
        if (tool instanceof ItemSpade) {
            return 2;
        }
        return -1;
    }
    
    public static boolean isBestTool(final EntityPlayerSP player, final ItemStack itemStack) {
        final int type = getToolType(itemStack);
        Tool bestTool = new Tool(-1, -1.0, null);
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemTool && type == getToolType(stack)) {
                final double efficiency = getToolEfficiency(stack);
                if (efficiency > bestTool.getEfficiency()) {
                    bestTool = new Tool(i, efficiency, stack);
                }
            }
        }
        return bestTool.getStack() == itemStack || getToolEfficiency(itemStack) > bestTool.getEfficiency();
    }
    
    public static boolean isBestBow(final EntityPlayerSP player, final ItemStack itemStack) {
        double bestBowDmg = -1.0;
        ItemStack bestBow = null;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBow) {
                final double damage = getBowDamage(stack);
                if (damage > bestBowDmg) {
                    bestBow = stack;
                    bestBowDmg = damage;
                }
            }
        }
        return itemStack == bestBow || getBowDamage(itemStack) > bestBowDmg;
    }
    
    public static double getDamageReduction(final ItemStack stack) {
        double reduction = 0.0;
        final ItemArmor armor = (ItemArmor)stack.getItem();
        reduction += armor.damageReduceAmount;
        if (stack.isItemEnchanted()) {
            reduction += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
        }
        return reduction;
    }
    
    public static boolean isBuffPotion(final ItemStack stack) {
        final ItemPotion potion = (ItemPotion)stack.getItem();
        final List<PotionEffect> effects = (List<PotionEffect>)potion.getEffects(stack);
        for (final PotionEffect effect : effects) {
            if (Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
                return false;
            }
        }
        return true;
    }
    
    public static double getBowDamage(final ItemStack stack) {
        double damage = 0.0;
        if (stack.getItem() instanceof ItemBow && stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        }
        return damage;
    }
    
    public static boolean isGoodFood(final ItemStack stack) {
        final ItemFood food = (ItemFood)stack.getItem();
        return food instanceof ItemAppleGold || (food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3f);
    }
    
    public static float getToolEfficiency(final ItemStack itemStack) {
        final ItemTool tool = (ItemTool)itemStack.getItem();
        float efficiency = tool.getToolMaterial().getEfficiencyOnProperMaterial();
        final int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        if (efficiency > 1.0f && lvl > 0) {
            efficiency += lvl * lvl + 1;
        }
        return efficiency;
    }
    
    public static double getItemDamage(final ItemStack stack) {
        double damage = 0.0;
        final Multimap<String, AttributeModifier> attributeModifierMap = (Multimap<String, AttributeModifier>)stack.getAttributeModifiers();
        for (final String attributeName : attributeModifierMap.keySet()) {
            if (attributeName.equals("generic.attackDamage")) {
                final Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get(attributeName).iterator();
                if (attributeModifiers.hasNext()) {
                    damage += attributeModifiers.next().getAmount();
                    break;
                }
                break;
            }
        }
        if (stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }
    
    public static void windowClick(final Minecraft mc, final int windowId, final int slotId, final int mouseButtonClicked, final ClickType mode) {
        PacketUtil.sendPacketNoEvent((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), (EntityPlayer)mc.thePlayer);
        PacketUtil.sendPacketNoEvent((Packet)new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
    }
    
    public static void windowClick(final Minecraft mc, final int slotId, final int mouseButtonClicked, final ClickType mode) {
        PacketUtil.sendPacketNoEvent((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), (EntityPlayer)mc.thePlayer);
        PacketUtil.sendPacketNoEvent((Packet)new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
    }
    
    public static boolean isStackValidToPlace(final ItemStack stack) {
        return stack.stackSize >= 1 && validateBlock(Block.getBlockFromItem(stack.getItem()), BlockAction.PLACE);
    }
    
    public static boolean validateBlock(final Block block, final BlockAction action) {
        if (block instanceof BlockContainer) {
            return false;
        }
        final Material material = block.getMaterial();
        switch (action) {
            case PLACE: {
                return !(block instanceof BlockFalling) && block.isFullBlock() && block.isFullCube();
            }
            case REPLACE: {
                return material.isReplaceable();
            }
            case PLACE_ON: {
                return block.isFullBlock() && block.isFullCube();
            }
            default: {
                return true;
            }
        }
    }
    
    public enum BlockAction
    {
        PLACE, 
        REPLACE, 
        PLACE_ON;
    }
    
    public enum ClickType
    {
        CLICK, 
        SHIFT_CLICK, 
        SWAP_WITH_HOT_BAR_SLOT, 
        PLACEHOLDER, 
        DROP_ITEM;
    }
    
    private static class Tool
    {
        private final int slot;
        private final double efficiency;
        private final ItemStack stack;
        
        public Tool(final int slot, final double efficiency, final ItemStack stack) {
            this.slot = slot;
            this.efficiency = efficiency;
            this.stack = stack;
        }
        
        public int getSlot() {
            return this.slot;
        }
        
        public double getEfficiency() {
            return this.efficiency;
        }
        
        public ItemStack getStack() {
            return this.stack;
        }
    }
}

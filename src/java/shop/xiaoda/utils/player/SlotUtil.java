//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.player;

import shop.xiaoda.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import java.util.*;

public final class SlotUtil
{
    public static final List<Block> blacklist;
    public static final List<Block> interactList;
    
    public static int findBlock() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack item = Client.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (item != null && item.getItem() instanceof ItemBlock && item.stackSize > 0) {
                final Block block = ((ItemBlock)item.getItem()).getBlock();
                if ((block.isFullBlock() || block instanceof BlockGlass || block instanceof BlockStainedGlass || block instanceof BlockTNT) && !SlotUtil.blacklist.contains(block)) {
                    return i - 36;
                }
            }
        }
        return -1;
    }
    
    public static ItemStack getItemStack() {
        return (Client.mc.thePlayer == null || Client.mc.thePlayer.inventoryContainer == null) ? null : Client.mc.thePlayer.inventoryContainer.getSlot(getItemIndex() + 36).getStack();
    }
    
    public static int getItemIndex() {
        final InventoryPlayer inventoryPlayer = Client.mc.thePlayer.inventory;
        return inventoryPlayer.currentItem;
    }
    
    public static int findSword() {
        int bestDurability = -1;
        float bestDamage = -1.0f;
        int bestSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Client.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemSword) {
                    final ItemSword sword = (ItemSword)itemStack.getItem();
                    final int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                    final float damage = sword.getDamageVsEntity() + sharpnessLevel * 1.25f;
                    final int durability = sword.getMaxDamage();
                    if (bestDamage < damage) {
                        bestDamage = damage;
                        bestDurability = durability;
                        bestSlot = i;
                    }
                    if (damage == bestDamage && durability > bestDurability) {
                        bestDurability = durability;
                        bestSlot = i;
                    }
                }
            }
        }
        return bestSlot;
    }
    
    public static int findItem(final Item item) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Client.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null) {
                if (item == null) {
                    return i;
                }
            }
            else if (itemStack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findBlock(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Client.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null) {
                if (block == null) {
                    return i;
                }
            }
            else if (itemStack.getItem() instanceof ItemBlock && ((ItemBlock)itemStack.getItem()).getBlock() == block) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findTool(final BlockPos blockPos) {
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        final IBlockState blockState = Client.mc.theWorld.getBlockState(blockPos);
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Client.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                final float speed = itemStack.getStrVsBlock(blockState.getBlock());
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }
    
    public static ItemStack getCurrentItemInSlot(final int slot) {
        return (slot < 9 && slot >= 0) ? Client.mc.thePlayer.inventory.mainInventory[slot] : null;
    }
    
    public static float getStrVsBlock(final Block blockIn, final int slot) {
        float f = 1.0f;
        if (Client.mc.thePlayer.inventory.mainInventory[slot] != null) {
            f *= Client.mc.thePlayer.inventory.mainInventory[slot].getStrVsBlock(blockIn);
        }
        return f;
    }
    
    public static float getPlayerRelativeBlockHardness(final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final int slot) {
        final Block block = Client.mc.theWorld.getBlockState(pos).getBlock();
        final float f = block.getBlockHardness(worldIn, pos);
        return (f < 0.0f) ? 0.0f : (canHeldItemHarvest(block, slot) ? (getToolDigEfficiency(block, slot) / f / 30.0f) : (getToolDigEfficiency(block, slot) / f / 100.0f));
    }
    
    public static boolean canHeldItemHarvest(final Block blockIn, final int slot) {
        if (blockIn.getMaterial().isToolNotRequired()) {
            return true;
        }
        final ItemStack itemstack = Client.mc.thePlayer.inventory.getStackInSlot(slot);
        return itemstack != null && itemstack.canHarvestBlock(blockIn);
    }
    
    public static float getToolDigEfficiency(final Block blockIn, final int slot) {
        float f = getStrVsBlock(blockIn, slot);
        if (f > 1.0f) {
            final int i = EnchantmentHelper.getEfficiencyModifier((EntityLivingBase)Client.mc.thePlayer);
            final ItemStack itemstack = getCurrentItemInSlot(slot);
            if (i > 0 && itemstack != null) {
                f += i * i + 1;
            }
        }
        if (Client.mc.thePlayer.isPotionActive(Potion.digSpeed)) {
            f *= 1.0f + (Client.mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (Client.mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
            float f2 = 0.0f;
            switch (Client.mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                case 0: {
                    f2 = 0.3f;
                    break;
                }
                case 1: {
                    f2 = 0.09f;
                    break;
                }
                case 2: {
                    f2 = 0.0027f;
                    break;
                }
                default: {
                    f2 = 8.1E-4f;
                    break;
                }
            }
            f *= f2;
        }
        if (Client.mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)Client.mc.thePlayer)) {
            f /= 5.0f;
        }
        if (!Client.mc.thePlayer.onGround) {
            f /= 5.0f;
        }
        return f;
    }
    
    private SlotUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    static {
        blacklist = Arrays.asList(Blocks.enchanting_table, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, (Block)Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch);
        interactList = Arrays.asList(Blocks.enchanting_table, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, Blocks.crafting_table, Blocks.furnace, Blocks.dispenser, Blocks.iron_door, Blocks.oak_door, Blocks.noteblock, Blocks.dropper);
    }
}

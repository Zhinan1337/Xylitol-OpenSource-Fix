//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.item;

import net.minecraft.client.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import org.apache.commons.lang3.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public final class ItemUtils
{
    private static final Minecraft mc;
    private static final int[] itemHelmet;
    private static final int[] itemChestPlate;
    private static final int[] itemLeggings;
    private static final int[] itemBoots;
    
    public static float getSwordDamage(final ItemStack itemStack) {
        float damage = 0.0f;
        final Optional<AttributeModifier> attributeModifier = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (attributeModifier.isPresent()) {
            damage = (float)attributeModifier.get().getAmount();
        }
        return damage + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    public static boolean isBestSword(final ContainerChest c, final ItemStack item) {
        final float itemdamage1 = getSwordDamage(item);
        float itemdamage2 = 0.0f;
        for (int i = 0; i < 45; ++i) {
            if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final float tempdamage = getSwordDamage(ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                if (tempdamage >= itemdamage2) {
                    itemdamage2 = tempdamage;
                }
            }
        }
        for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
            if (c.getLowerChestInventory().getStackInSlot(i) != null) {
                final float tempdamage = getSwordDamage(c.getLowerChestInventory().getStackInSlot(i));
                if (tempdamage >= itemdamage2) {
                    itemdamage2 = tempdamage;
                }
            }
        }
        return itemdamage1 == itemdamage2;
    }
    
    public static boolean isBestArmor(final ContainerChest c, final ItemStack item) {
        final float itempro1 = (float)((ItemArmor)item.getItem()).damageReduceAmount;
        float itempro2 = 0.0f;
        if (isContain(ItemUtils.itemHelmet, Item.getIdFromItem(item.getItem()))) {
            for (int i = 0; i < 45; ++i) {
                if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isContain(ItemUtils.itemHelmet, Item.getIdFromItem(ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()))) {
                    final float temppro = (float)((ItemArmor)ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
            for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null && isContain(ItemUtils.itemHelmet, Item.getIdFromItem(c.getLowerChestInventory().getStackInSlot(i).getItem()))) {
                    final float temppro = (float)((ItemArmor)c.getLowerChestInventory().getStackInSlot(i).getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
        }
        if (isContain(ItemUtils.itemChestPlate, Item.getIdFromItem(item.getItem()))) {
            for (int i = 0; i < 45; ++i) {
                if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isContain(ItemUtils.itemChestPlate, Item.getIdFromItem(ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()))) {
                    final float temppro = (float)((ItemArmor)ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
            for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null && isContain(ItemUtils.itemChestPlate, Item.getIdFromItem(c.getLowerChestInventory().getStackInSlot(i).getItem()))) {
                    final float temppro = (float)((ItemArmor)c.getLowerChestInventory().getStackInSlot(i).getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
        }
        if (isContain(ItemUtils.itemLeggings, Item.getIdFromItem(item.getItem()))) {
            for (int i = 0; i < 45; ++i) {
                if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isContain(ItemUtils.itemLeggings, Item.getIdFromItem(ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()))) {
                    final float temppro = (float)((ItemArmor)ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
            for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null && isContain(ItemUtils.itemLeggings, Item.getIdFromItem(c.getLowerChestInventory().getStackInSlot(i).getItem()))) {
                    final float temppro = (float)((ItemArmor)c.getLowerChestInventory().getStackInSlot(i).getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
        }
        if (isContain(ItemUtils.itemBoots, Item.getIdFromItem(item.getItem()))) {
            for (int i = 0; i < 45; ++i) {
                if (ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isContain(ItemUtils.itemBoots, Item.getIdFromItem(ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()))) {
                    final float temppro = (float)((ItemArmor)ItemUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
            for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
                if (c.getLowerChestInventory().getStackInSlot(i) != null && isContain(ItemUtils.itemBoots, Item.getIdFromItem(c.getLowerChestInventory().getStackInSlot(i).getItem()))) {
                    final float temppro = (float)((ItemArmor)c.getLowerChestInventory().getStackInSlot(i).getItem()).damageReduceAmount;
                    if (temppro > itempro2) {
                        itempro2 = temppro;
                    }
                }
            }
        }
        return itempro1 == itempro2;
    }
    
    public static boolean isContain(final int[] arr, final int targetValue) {
        return ArrayUtils.contains(arr, targetValue);
    }
    
    public static boolean isPotionNegative(final ItemStack itemStack) {
        final ItemPotion potion = (ItemPotion)itemStack.getItem();
        final List<PotionEffect> potionEffectList = (List<PotionEffect>)potion.getEffects(itemStack);
        return potionEffectList.stream().map(potionEffect -> Potion.potionTypes[potionEffect.getPotionID()]).anyMatch(Potion::isBadEffect);
    }
    
    public static int getEnchantment(final ItemStack itemStack, final Enchantment enchantment) {
        if (itemStack == null || itemStack.getEnchantmentTagList() == null || itemStack.getEnchantmentTagList().hasNoTags()) {
            return 0;
        }
        for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); ++i) {
            final NBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);
            if (tagCompound.getShort("ench") == enchantment.effectId || tagCompound.getShort("id") == enchantment.effectId) {
                return tagCompound.getShort("lvl");
            }
        }
        return 0;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        itemHelmet = new int[] { 298, 302, 306, 310, 314 };
        itemChestPlate = new int[] { 299, 303, 307, 311, 315 };
        itemLeggings = new int[] { 300, 304, 308, 312, 316 };
        itemBoots = new int[] { 301, 305, 309, 313, 317 };
    }
}

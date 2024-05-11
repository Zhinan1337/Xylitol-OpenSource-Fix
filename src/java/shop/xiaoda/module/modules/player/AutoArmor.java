//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;

public class AutoArmor extends Module
{
    public static NumberValue DELAY;
    public static ModeValue<EMode> MODE;
    private final BoolValue drop;
    private final TimeUtil timer;
    
    public AutoArmor() {
        super("AutoArmor", Category.Player);
        this.drop = new BoolValue("Drop", true);
        this.timer = new TimeUtil();
    }
    
    @EventTarget
    public void onEvent(final EventTick event) {
        this.setSuffix(AutoArmor.MODE.getValue());
        final long delay = AutoArmor.DELAY.getValue().longValue() * 50L;
        if (AutoArmor.MODE.getValue() == EMode.OpenInv && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if ((AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.hasReached((double)delay)) {
            this.getBestArmor();
        }
    }
    
    public void getBestArmor() {
        for (int type = 1; type < 5; ++type) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                final ItemStack is = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (isBestArmor(is, type)) {
                    continue;
                }
                if (AutoArmor.MODE.getValue() == EMode.FakeInv) {
                    final C16PacketClientStatus p = new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT);
                    AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)p);
                }
                if (this.drop.getValue()) {
                    this.drop(4 + type);
                }
            }
            for (int i = 9; i < 45; ++i) {
                if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack is2 = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (isBestArmor(is2, type) && getProtection(is2) > 0.0f) {
                        this.shiftClick(i);
                        this.timer.reset();
                        if (AutoArmor.DELAY.getValue().longValue() > 0L) {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public static boolean isBestArmor(final ItemStack stack, final int type) {
        String strType = "";
        switch (type) {
            case 1: {
                strType = "helmet";
                break;
            }
            case 2: {
                strType = "chestplate";
                break;
            }
            case 3: {
                strType = "leggings";
                break;
            }
            case 4: {
                strType = "boots";
                break;
            }
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        final float protection = getProtection(stack);
        if (((ItemArmor)stack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CHAIN) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(is) > protection && is.getUnlocalizedName().contains(strType)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void shiftClick(final int slot) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)AutoArmor.mc.thePlayer);
    }
    
    public void drop(final int slot) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, (EntityPlayer)AutoArmor.mc.thePlayer);
    }
    
    public static float getProtection(final ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            prot += (float)(armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack) / 100.0);
        }
        return prot;
    }
    
    static {
        AutoArmor.DELAY = new NumberValue("Delay", 1.0, 0.0, 10.0, 1.0);
        AutoArmor.MODE = new ModeValue<EMode>("Mode", EMode.values(), EMode.Basic);
    }
    
    public enum EMode
    {
        Basic, 
        OpenInv, 
        FakeInv;
    }
}

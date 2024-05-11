//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import shop.xiaoda.*;
import net.minecraft.util.*;
import shop.xiaoda.event.world.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class AutoTool extends Module
{
    private final BoolValue spoofValue;
    private int blockBreak;
    private BlockPos blockPos;
    private int oldSlot;
    int slot;
    public boolean render;
    
    public AutoTool() {
        super("AutoTool", Category.Player);
        this.spoofValue = new BoolValue("Spoof", false);
        this.oldSlot = -1;
    }
    
    @EventTarget
    public void onClick(final EventClickBlock event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!this.spoofValue.getValue()) {
            this.switchSlot(event.getClickedBlock());
        }
        else {
            this.blockBreak = 3;
            this.blockPos = event.getClickedBlock();
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (this.spoofValue.getValue()) {
            final Packet<?> packet = (Packet<?>)event.getPacket();
            if (this.spoofValue.getValue() && packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)packet).getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK && this.oldSlot != -1 && AutoTool.mc.thePlayer.inventory.currentItem != this.oldSlot && this.blockBreak > 0) {
                event.setCancelled(true);
                PacketUtil.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(this.oldSlot));
                PacketUtil.sendPacketC0F();
                if (!(Client.instance.moduleManager.getModule((Class)SpeedMine.class)).getState()) {
                    PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, ((C07PacketPlayerDigging)packet).getPosition(), ((C07PacketPlayerDigging)packet).getFacing()));
                    PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, ((C07PacketPlayerDigging)packet).getPosition(), ((C07PacketPlayerDigging)packet).getFacing()));
                }
                PacketUtil.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(AutoTool.mc.thePlayer.inventory.currentItem));
            }
            if (this.spoofValue.getValue() && packet instanceof C09PacketHeldItemChange && this.blockBreak > 0 && (((C09PacketHeldItemChange)packet).getSlotId() == this.oldSlot || ((C09PacketHeldItemChange)packet).getSlotId() == AutoTool.mc.thePlayer.inventory.currentItem)) {
                event.setCancelled(true);
            }
            if (AutoTool.mc.objectMouseOver != null && AutoTool.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && AutoTool.mc.gameSettings.keyBindAttack.isKeyDown()) {
                this.blockBreak = 3;
                this.blockPos = AutoTool.mc.objectMouseOver.getBlockPos();
            }
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if (this.spoofValue.getValue() && event.isPre()) {
            switch (AutoTool.mc.objectMouseOver.typeOfHit) {
                case BLOCK: {
                    if (this.blockPos != null && this.blockBreak > 0) {
                        this.slot = this.findTool(this.blockPos);
                        break;
                    }
                    this.slot = -1;
                    break;
                }
                case ENTITY: {
                    this.slot = this.findSword();
                    break;
                }
                default: {
                    this.slot = -1;
                    break;
                }
            }
            if (this.oldSlot != -1) {
                this.setSlot(this.oldSlot);
            }
            else if (this.slot != -1) {
                this.setSlot(this.slot);
            }
            this.oldSlot = this.slot;
            --this.blockBreak;
        }
    }
    
    public void setSlot(final int slot, boolean render) {
        if (slot < 0 || slot > 8) {
            return;
        }
        AutoTool.mc.thePlayer.inventory.alternativeCurrentItem = slot;
        AutoTool.mc.thePlayer.inventory.alternativeSlot = true;
        render = this.render;
    }
    
    public void setSlot(final int slot) {
        this.setSlot(slot, true);
    }
    
    public int findSword() {
        int bestDurability = -1;
        float bestDamage = -1.0f;
        int bestSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = AutoTool.mc.thePlayer.inventory.getStackInSlot(i);
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
    
    public int findTool(final BlockPos blockPos) {
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        final IBlockState blockState = AutoTool.mc.theWorld.getBlockState(blockPos);
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = AutoTool.mc.thePlayer.inventory.getStackInSlot(i);
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
    
    public void switchSlot(final BlockPos blockPos) {
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        final Block block = AutoTool.mc.theWorld.getBlockState(blockPos).getBlock();
        for (int i = 0; i <= 8; ++i) {
            final ItemStack item = AutoTool.mc.thePlayer.inventory.getStackInSlot(i);
            if (item != null) {
                final float speed = item.getStrVsBlock(block);
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
        }
        if (bestSlot != -1) {
            AutoTool.mc.thePlayer.inventory.currentItem = bestSlot;
        }
    }
}

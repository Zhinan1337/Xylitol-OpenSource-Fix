//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.event.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.enchantment.*;
import shop.xiaoda.utils.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.world.*;

public class AutoWeapon extends Module
{
    public final BoolValue silentValue;
    private final NumberValue ticksValue;
    private final BoolValue itemTool;
    private boolean attackEnemy;
    private int spoofedSlot;
    
    public AutoWeapon() {
        super("AutoWeapon", Category.Combat);
        this.silentValue = new BoolValue("SpoofItem", false);
        this.ticksValue = new NumberValue("SpoofTicks", 10.0, 1.0, 20.0, 1.0);
        this.itemTool = new BoolValue("ItemTool", true);
        this.attackEnemy = false;
        this.spoofedSlot = 0;
    }
    
    @EventTarget
    public void onAttack(final EventAttack event) {
        this.attackEnemy = true;
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && this.attackEnemy) {
            this.attackEnemy = false;
            int slot = -1;
            double maxDamage = 0.0;
            for (int i = 0; i < 9; ++i) {
                if (AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i) != null && (AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemSword || (AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemTool && this.itemTool.getValue()))) {
                    final double damage = ((AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i).getAttributeModifiers().get("generic.attackDamage").stream().findFirst().orElse(null) != null) ? Objects.requireNonNull((AttributeModifier)AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i).getAttributeModifiers().get("generic.attackDamage").stream().findFirst().orElse(null)).getAmount() : 0.0) + 1.25 * ItemUtils.getEnchantment(AutoWeapon.mc.thePlayer.inventory.getStackInSlot(i), Enchantment.sharpness);
                    if (damage > maxDamage) {
                        maxDamage = damage;
                        slot = i;
                    }
                }
            }
            if (slot == AutoWeapon.mc.thePlayer.inventory.currentItem || slot == -1) {
                return;
            }
            if (this.silentValue.getValue()) {
                AutoWeapon.mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(slot));
                this.spoofedSlot = this.ticksValue.getValue().intValue();
            }
            else {
                AutoWeapon.mc.thePlayer.inventory.currentItem = slot;
                AutoWeapon.mc.playerController.updateController();
            }
            AutoWeapon.mc.getNetHandler().addToSendQueue(event.getPacket());
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.spoofedSlot > 0) {
            if (this.spoofedSlot == 1) {
                AutoWeapon.mc.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(AutoWeapon.mc.thePlayer.inventory.currentItem));
            }
            --this.spoofedSlot;
        }
    }
}

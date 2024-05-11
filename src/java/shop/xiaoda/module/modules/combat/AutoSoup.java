//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import org.apache.commons.lang3.*;
import shop.xiaoda.event.*;

public class AutoSoup extends Module
{
    private final BoolValue postValue;
    private final BoolValue sendPostC0FFix;
    private final NumberValue health;
    private final NumberValue minDelay;
    private final NumberValue maxDelay;
    private final BoolValue dropBowl;
    private final BoolValue Legit;
    private final TimeUtil timer;
    private boolean switchBack;
    private long decidedTimer;
    private int soup;
    
    public AutoSoup() {
        super("AutoSoup", Category.Combat);
        this.postValue = new BoolValue("Post", false);
        this.sendPostC0FFix = new BoolValue("SendPostC0FFix", true);
        this.health = new NumberValue("Health", 15.0, 0.0, 20.0, 1.0);
        this.minDelay = new NumberValue("Min Delay", 300.0, 0.0, 1000.0, 1.0);
        this.maxDelay = new NumberValue("Max Delay", 500.0, 0.0, 1000.0, 1.0);
        this.dropBowl = new BoolValue("Drop Bowl", true);
        this.Legit = new BoolValue("Legit", false);
        this.timer = new TimeUtil();
        this.soup = -37;
    }
    
    public void onDisable() {
        this.switchBack = false;
        this.soup = -37;
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if ((this.postValue.getValue() && event.isPost()) || (!this.postValue.getValue() && event.isPre())) {
            if (this.switchBack) {
                if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                    PacketUtil.sendPacketC0F();
                }
                PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                if (this.dropBowl.getValue()) {
                    if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                        PacketUtil.sendPacketC0F();
                    }
                    PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                if (this.Legit.getValue()) {
                    AutoSoup.mc.playerController.updateController();
                }
                else {
                    PacketUtil.send((Packet<?>)new C09PacketHeldItemChange(AutoSoup.mc.thePlayer.inventory.currentItem));
                }
                this.switchBack = false;
                return;
            }
            if (this.timer.hasPassed(this.decidedTimer) && AutoSoup.mc.thePlayer.ticksExisted > 10 && AutoSoup.mc.thePlayer.getHealth() < this.health.getValue().intValue()) {
                this.soup = PlayerUtil.findSoup() - 36;
                if (this.soup != -37) {
                    if (this.Legit.getValue()) {
                        AutoSoup.mc.thePlayer.inventory.currentItem = this.soup;
                        AutoSoup.mc.gameSettings.keyBindUseItem.setPressed(true);
                    }
                    else {
                        PacketUtil.send((Packet<?>)new C09PacketHeldItemChange(this.soup));
                        if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                            PacketUtil.sendPacketC0F();
                        }
                        PacketUtil.send((Packet<?>)new C08PacketPlayerBlockPlacement(AutoSoup.mc.thePlayer.inventory.getStackInSlot(this.soup)));
                    }
                    this.switchBack = true;
                }
                else {
                    final int soupInInventory = PlayerUtil.findItem(9, 36, Items.mushroom_stew);
                    if (soupInInventory != -1 && PlayerUtil.hasSpaceHotbar()) {
                        final boolean openInventory = !(AutoSoup.mc.currentScreen instanceof GuiInventory);
                        if (openInventory) {
                            AutoSoup.mc.thePlayer.setSprinting(false);
                            AutoSoup.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                        }
                        AutoSoup.mc.playerController.windowClick(0, soupInInventory, 0, 1, (EntityPlayer)AutoSoup.mc.thePlayer);
                        if (openInventory) {
                            AutoSoup.mc.getNetHandler().addToSendQueue((Packet)new C0DPacketCloseWindow());
                        }
                    }
                }
                final int delayFirst = (int)Math.floor(Math.min(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()));
                final int delaySecond = (int)Math.ceil(Math.max(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()));
                this.decidedTimer = RandomUtils.nextInt(delayFirst, delaySecond);
                this.timer.reset();
            }
        }
    }
}

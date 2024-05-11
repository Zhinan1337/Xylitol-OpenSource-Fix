//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.event.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class AutoLobby extends Module
{
    private final NumberValue health;
    private final BoolValue randomhub;
    private final BoolValue disabler;
    private final BoolValue keepArmor;
    private final BoolValue noHub;
    
    public AutoLobby() {
        super("AutoLobby", Category.Misc);
        this.health = new NumberValue("Health", 5.0, 0.0, 20.0, 1.0);
        this.randomhub = new BoolValue("RandomHub", false);
        this.disabler = new BoolValue("AutoDisable-KillAura-Velocity", true);
        this.keepArmor = new BoolValue("KeepArmor", true);
        this.noHub = new BoolValue("NoHub", false);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        final KillAura killAura = (KillAura)Client.instance.moduleManager.getModule((Class)KillAura.class);
        final Velocity velocity = (Velocity)Client.instance.moduleManager.getModule((Class)Velocity.class);
        if (!this.noHub.getValue()) {
            if (AutoLobby.mc.thePlayer.getHealth() <= this.health.getValue().floatValue()) {
                if (this.keepArmor.getValue()) {
                    for (int i = 0; i <= 3; ++i) {
                        final int armorSlot = 3 - i;
                        this.move(8 - armorSlot);
                    }
                }
                if (this.randomhub.getValue()) {
                    AutoLobby.mc.thePlayer.sendChatMessage("/hub " + (int)(Math.random() * 100.0 + 1.0));
                }
                else {
                    AutoLobby.mc.thePlayer.sendChatMessage("/hub");
                }
                if (this.disabler.getValue()) {
                    assert killAura != null;
                    killAura.state = false;
                    assert velocity != null;
                    velocity.state = false;
                }
            }
        }
        else if ((AutoLobby.mc.thePlayer.isDead || AutoLobby.mc.thePlayer.getHealth() == 0.0f || AutoLobby.mc.thePlayer.getHealth() <= 0.0f) && this.disabler.getValue()) {
            killAura.setState(false);
            velocity.setState(false);
        }
    }
    
    private void move(final int item) {
        if (item != -1) {
            final boolean openInventory = !(AutoLobby.mc.currentScreen instanceof GuiInventory);
            if (openInventory) {
                AutoLobby.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            }
            AutoLobby.mc.playerController.windowClick(AutoLobby.mc.thePlayer.inventoryContainer.windowId, item, 0, 1, (EntityPlayer)AutoLobby.mc.thePlayer);
            if (openInventory) {
                AutoLobby.mc.getNetHandler().addToSendQueue((Packet)new C0DPacketCloseWindow());
            }
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.*;
import shop.xiaoda.module.modules.player.*;
import shop.xiaoda.event.world.*;
import net.minecraft.world.*;

public class ModuleHelper extends Module
{
    private final BoolValue lagBackCheckValue;
    
    public ModuleHelper() {
        super("ModuleHelper", Category.Misc);
        this.lagBackCheckValue = new BoolValue("LagBackCheck", false);
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive event) {
        if (event.getPacket() instanceof S01PacketJoinGame || (event.getPacket() instanceof S08PacketPlayerPosLook && this.lagBackCheckValue.getValue())) {
            this.disableModule();
        }
    }
    
    @EventTarget
    public void onWorld(final EventWorldLoad event) {
        this.disableModule();
    }
    
    public void disableModule() {
        if (((KillAura)Client.instance.moduleManager.getModule((Class)KillAura.class)).state) {
            ((KillAura)Client.instance.moduleManager.getModule((Class)KillAura.class)).setState(false);
        }
        if ((Client.instance.moduleManager.getModule((Class)InvCleaner.class)).state) {
            (Client.instance.moduleManager.getModule((Class)InvCleaner.class)).setState(false);
        }
        if (((ChestStealer)Client.instance.moduleManager.getModule((Class)ChestStealer.class)).state) {
            ((ChestStealer)Client.instance.moduleManager.getModule((Class)ChestStealer.class)).setState(false);
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if (ModuleHelper.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
            if ((Client.instance.moduleManager.getModule((Class)InvCleaner.class)).state) {
                (Client.instance.moduleManager.getModule((Class)InvCleaner.class)).setState(false);
            }
            if (((ChestStealer)Client.instance.moduleManager.getModule((Class)ChestStealer.class)).state) {
                ((ChestStealer)Client.instance.moduleManager.getModule((Class)ChestStealer.class)).setState(false);
            }
        }
    }
}

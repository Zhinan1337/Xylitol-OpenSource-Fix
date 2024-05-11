//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;

public class NoPitchLimit extends Module
{
    private final BoolValue serverSideValue;
    
    public NoPitchLimit() {
        super("NoPitchLimit", Category.Misc);
        this.serverSideValue = new BoolValue("ServerSide", true);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend e) {
        if (this.serverSideValue.getValue()) {
            return;
        }
        final Packet<?> packet = (Packet<?>)e.packet;
        if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).pitch = Math.max(Math.min(((C03PacketPlayer)packet).pitch, 90.0f), -90.0f);
        }
    }
}

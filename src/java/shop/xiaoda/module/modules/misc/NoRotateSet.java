//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.utils.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import shop.xiaoda.event.*;

public class NoRotateSet extends Module
{
    private final BoolValue confirmValue;
    private final BoolValue illegalRotationValue;
    private final BoolValue noZeroValue;
    
    public NoRotateSet() {
        super("NoRotateSet", Category.Misc);
        this.confirmValue = new BoolValue("Confirm", true);
        this.illegalRotationValue = new BoolValue("ConfirmIllegalRotation", false);
        this.noZeroValue = new BoolValue("NoZero", false);
    }
    
    @EventTarget
    public void onPacket(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (NoRotateSet.mc.thePlayer == null) {
            return;
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            if (this.noZeroValue.getValue() && ((S08PacketPlayerPosLook)packet).getYaw() == 0.0f && ((S08PacketPlayerPosLook)packet).getPitch() == 0.0f) {
                return;
            }
            if ((this.illegalRotationValue.getValue() || (((S08PacketPlayerPosLook)packet).getPitch() <= 90.0f && ((S08PacketPlayerPosLook)packet).getPitch() >= -90.0f && RotationComponent.lastServerRotations != null && ((S08PacketPlayerPosLook)packet).getYaw() != RotationComponent.lastServerRotations.x && ((S08PacketPlayerPosLook)packet).getPitch() != RotationComponent.lastServerRotations.y)) && this.confirmValue.getValue()) {
                PacketUtil.send((Packet<?>)new C03PacketPlayer.C05PacketPlayerLook(((S08PacketPlayerPosLook)packet).getYaw(), ((S08PacketPlayerPosLook)packet).getPitch(), NoRotateSet.mc.thePlayer.onGround));
            }
            ((S08PacketPlayerPosLook)packet).yaw = NoRotateSet.mc.thePlayer.rotationYaw;
            ((S08PacketPlayerPosLook)packet).pitch = NoRotateSet.mc.thePlayer.rotationPitch;
        }
    }
}

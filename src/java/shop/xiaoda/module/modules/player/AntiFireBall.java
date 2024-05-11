//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import shop.xiaoda.event.*;

public class AntiFireBall extends Module
{
    private final BoolValue postValue;
    private final BoolValue sendPostC0FFix;
    private final BoolValue rotation;
    private final NumberValue minRotationSpeed;
    private final NumberValue maxRotationSpeed;
    private final BoolValue moveFix;
    private final BoolValue noSwing;
    private final TimeUtil timerUtil;
    
    public AntiFireBall() {
        super("AntiFireBall", Category.Player);
        this.postValue = new BoolValue("Post", false);
        this.sendPostC0FFix = new BoolValue("SendPostC0FFix", true);
        this.rotation = new BoolValue("Rotation", true);
        this.minRotationSpeed = new NumberValue("MinRotationSpeed", 10.0, 0.0, 10.0, 1.0);
        this.maxRotationSpeed = new NumberValue("MaxRotationSpeed", 10.0, 0.0, 10.0, 1.0);
        this.moveFix = new BoolValue("MoveFix", false);
        this.noSwing = new BoolValue("NoSwing", false);
        this.timerUtil = new TimeUtil();
    }
    
    @EventTarget
    public void onMotion(final EventMotion event) {
        if ((this.postValue.getValue() && event.isPost()) || (!this.postValue.getValue() && event.isPre())) {
            for (final Entity entity : AntiFireBall.mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityFireball && AntiFireBall.mc.thePlayer.getDistanceToEntity(entity) < 5.5f && this.timerUtil.delay(300.0f)) {
                    this.timerUtil.reset();
                    if (this.rotation.getValue()) {
                        RotationComponent.setRotations(RotationUtil.getRotationsNonLivingEntity(entity), MathUtil.getRandom(this.minRotationSpeed.getValue().intValue(), this.maxRotationSpeed.getValue().intValue()), ((boolean)this.moveFix.getValue()) ? MovementFix.NORMAL : MovementFix.OFF);
                    }
                    if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                        PacketUtil.sendPacketC0F();
                    }
                    PacketUtil.send((Packet<?>)new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                    if (this.noSwing.getValue()) {
                        if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                            PacketUtil.sendPacketC0F();
                        }
                        PacketUtil.send((Packet<?>)new C0APacketAnimation());
                    }
                    else {
                        if (this.sendPostC0FFix.getValue() && this.postValue.getValue()) {
                            PacketUtil.sendPacketC0F();
                        }
                        AntiFireBall.mc.thePlayer.swingItem();
                    }
                }
            }
        }
    }
}

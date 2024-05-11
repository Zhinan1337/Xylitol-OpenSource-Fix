//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat.velocity;

import net.minecraft.client.*;
import java.util.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.attack.*;
import shop.xiaoda.event.world.*;

public abstract class VelocityMode
{
    private final String name;
    Minecraft mc;
    private static final HashMap<Class<? extends VelocityMode>, VelocityMode> velocitys;
    
    public VelocityMode(final String name, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
    }
    
    public static void init() {
        VelocityMode.velocitys.put((Class<? extends VelocityMode>)AACVelocity.class, (VelocityMode)new AACVelocity());
        VelocityMode.velocitys.put((Class<? extends VelocityMode>)CancelVelocity.class, (VelocityMode)new CancelVelocity());
        VelocityMode.velocitys.put((Class<? extends VelocityMode>)GrimVelocity.class, (VelocityMode)new GrimVelocity());
        VelocityMode.velocitys.put((Class<? extends VelocityMode>)JumpResetVelocity.class, (VelocityMode)new JumpResetVelocity());
        VelocityMode.velocitys.put((Class<? extends VelocityMode>)HypixelVelocity.class, (VelocityMode)new HypixelVelocity());
    }
    
    public abstract void onEnable();
    
    public abstract void onDisable();
    
    public abstract String getTag();
    
    public static VelocityMode get(final String name) {
        return VelocityMode.velocitys.values().stream().filter(vel -> vel.name.equals(name)).findFirst().orElse(null);
    }
    
    public abstract void onAttack(final EventAttack p0);
    
    public abstract void onPacketSend(final EventPacketSend p0);
    
    public abstract void onWorldLoad(final EventWorldLoad p0);
    
    public abstract void onPacketReceive(final EventPacketReceive p0);
    
    public abstract void onUpdate(final EventUpdate p0);
    
    static {
        velocitys = new HashMap<Class<? extends VelocityMode>, VelocityMode>();
    }
}

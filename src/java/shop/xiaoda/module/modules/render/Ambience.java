//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class Ambience extends Module
{
    private final ModeValue<timemode> mode;
    private final ModeValue<weatherMode> weathermode;
    private final NumberValue cycleSpeed;
    private final BoolValue reverseCycle;
    private final NumberValue time;
    private final NumberValue rainstrength;
    private int timeCycle;
    
    public Ambience() {
        super("Ambience", Category.Render);
        this.mode = new ModeValue<timemode>("Time-Mode", timemode.values(), timemode.Static);
        this.weathermode = new ModeValue<weatherMode>("Weather-Mode", weatherMode.values(), weatherMode.Clear);
        this.cycleSpeed = new NumberValue("Cycle-Speed", 24.0, 1.0, 24.0, 1.0);
        this.reverseCycle = new BoolValue("Reverse-Cycle", false);
        this.time = new NumberValue("Static-Time", 24000.0, 0.0, 24000.0, 100.0);
        this.rainstrength = new NumberValue("Rain-Strength", 0.1, 0.1, 0.5, 0.1);
        this.timeCycle = 0;
    }
    
    public void onEnable() {
        this.timeCycle = 0;
    }
    
    @EventTarget
    public void onUpdate(final EventTick event) {
        if (this.mode.getValue().name().equalsIgnoreCase("static")) {
            Ambience.mc.theWorld.setWorldTime(this.time.getValue().longValue());
        }
        else {
            Ambience.mc.theWorld.setWorldTime((long)this.timeCycle);
            this.timeCycle += (int)((this.reverseCycle.getValue() ? (-this.cycleSpeed.getValue()) : ((double)this.cycleSpeed.getValue())) * 10.0);
            if (this.timeCycle > 24000) {
                this.timeCycle = 0;
            }
            else if (this.timeCycle < 0) {
                this.timeCycle = 24000;
            }
        }
        if (this.weathermode.getValue().name().equalsIgnoreCase("clear")) {
            Ambience.mc.theWorld.setRainStrength(0.0f);
        }
        else {
            Ambience.mc.theWorld.setRainStrength((float)this.rainstrength.getValue().longValue());
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
    
    public enum timemode
    {
        Static, 
        Cycle;
    }
    
    public enum weatherMode
    {
        Clear, 
        Rain;
    }
}

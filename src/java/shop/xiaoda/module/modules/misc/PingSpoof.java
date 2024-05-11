//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.utils.component.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.event.*;

public class PingSpoof extends Module
{
    private final NumberValue minDelay;
    private final NumberValue maxDelay;
    private final BoolValue teleports;
    private final BoolValue velocity;
    private final BoolValue world;
    private final BoolValue entities;
    
    public PingSpoof() {
        super("PingSpoof", Category.Misc);
        this.minDelay = new NumberValue("MinDelay", 50.0, 50.0, 30000.0, 50.0);
        this.maxDelay = new NumberValue("MaxDelay", 50.0, 50.0, 30000.0, 50.0);
        this.teleports = new BoolValue("DelayTeleports", false);
        this.velocity = new BoolValue("DelayVelocity", false);
        this.world = new BoolValue("DelayBlockUpdates", false);
        this.entities = new BoolValue("DelayEntityMovements", false);
    }
    
    public void onEnable() {
        PingSpoofComponent.spoofing = true;
    }
    
    public void onDisable() {
        PingSpoofComponent.spoofing = false;
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        PingSpoofComponent.setSpoofing(MathUtil.getRandom(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()), true, this.teleports.getValue(), this.velocity.getValue(), this.world.getValue(), this.entities.getValue());
    }
}

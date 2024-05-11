//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.event.*;

public class Strafe extends Module
{
    public Strafe() {
        super("Strafe", Category.Movement);
    }
    
    public void onDisable() {
        Strafe.mc.timer.timerSpeed = 1.0f;
    }
    
    @EventTarget
    public void onStrafe(final EventStrafe event) {
        MoveUtil.strafe();
    }
}

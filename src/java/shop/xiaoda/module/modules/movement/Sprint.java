//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.Module;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", Category.Movement);
        this.setState(true);
    }
    
    @EventTarget
    public void onUpdate(final EventStrafe event) {
        Sprint.mc.gameSettings.keyBindSprint.pressed = true;
    }
}

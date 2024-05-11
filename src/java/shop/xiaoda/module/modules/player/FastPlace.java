//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.item.*;
import shop.xiaoda.event.*;

public class FastPlace extends Module
{
    private final NumberValue ticks;
    
    public FastPlace() {
        super("FastPlace", Category.Player);
        this.ticks = new NumberValue("Ticks", 0.0, 4.0, 0.0, 1.0);
    }
    
    @EventTarget
    public void onTick(final EventTick e) {
        if (FastPlace.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            FastPlace.mc.rightClickDelayTimer = Math.min(0, this.ticks.getValue().intValue());
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.potion.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.Module;

public class FullBright extends Module
{
    public FullBright() {
        super("FullBright", Category.Render);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        FullBright.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1337, 5));
    }
    
    public void onDisable() {
        if (FullBright.mc.thePlayer.isPotionActive(Potion.nightVision)) {
            FullBright.mc.thePlayer.removePotionEffectClient(Potion.nightVision.id);
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;

public class NoFall extends Module
{
    ModeValue<modeEnum> mode;
    
    public NoFall() {
        super("NoFall", Category.Player);
        this.mode = new ModeValue<modeEnum>("Mode", modeEnum.values(), modeEnum.HypixelSpoof);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion e) {
        this.setSuffix(this.mode.getValue().toString());
        switch (this.mode.getValue()) {
            case HypixelSpoof: {
                if (NoFall.mc.thePlayer.ticksExisted > 50 && NoFall.mc.thePlayer.fallDistance > 3.0f) {
                    e.setOnGround(true);
                    break;
                }
                break;
            }
            case GroundSpoof: {
                if (!NoFall.mc.thePlayer.onGround) {
                    e.setOnGround(true);
                    break;
                }
                break;
            }
        }
    }
    
    enum modeEnum
    {
        HypixelSpoof, 
        GroundSpoof;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.block.*;
import shop.xiaoda.event.*;

public class FastLadder extends Module
{
    private final NumberValue yMotionValue;
    
    public FastLadder() {
        super("FastLadder", Category.Movement);
        this.yMotionValue = new NumberValue("YMotion", 0.15, 0.1, 0.2, 0.01);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        if (event.isPost()) {
            return;
        }
        final Block block = BlockUtil.getBlock(new BlockPos(FastLadder.mc.thePlayer.posX, FastLadder.mc.thePlayer.posY + 1.0, FastLadder.mc.thePlayer.posZ));
        if ((block instanceof BlockLadder && FastLadder.mc.thePlayer.isCollidedHorizontally) || block instanceof BlockVine || BlockUtil.getBlock(new BlockPos(FastLadder.mc.thePlayer.posX, FastLadder.mc.thePlayer.posY, FastLadder.mc.thePlayer.posZ)) instanceof BlockVine) {
            FastLadder.mc.thePlayer.motionY = this.yMotionValue.getValue();
            FastLadder.mc.thePlayer.motionX = 0.0;
            FastLadder.mc.thePlayer.motionZ = 0.0;
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

 
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import java.util.*;


public class NoWater extends Module
{
    private final ModeValue<noWaterMode> modeValue;
    public static boolean shouldCancelWater;
    
    public NoWater() {
        super("NoWater", Category.Movement);
        this.modeValue = new ModeValue<noWaterMode>("Mode", noWaterMode.values(), noWaterMode.Vanilla);
    }
    
    public void onDisable() {
        NoWater.shouldCancelWater = false;
    }
    
    @EventTarget
    public void onWorldLoad(final EventWorldLoad e) {
        NoWater.shouldCancelWater = false;
    }
    
    @EventTarget
    public void onUpdate(final EventMotion e) {
        this.setSuffix(this.modeValue.getValue().name());
        if (NoWater.mc.thePlayer == null) {
            return;
        }
        if (e.isPost()) {
            return;
        }
        NoWater.shouldCancelWater = false;
        final Map<BlockPos, Block> searchBlock = BlockUtil.searchBlocks(5);
        for (final Map.Entry<BlockPos, Block> block : searchBlock.entrySet()) {
            final boolean checkBlock = NoWater.mc.theWorld.getBlockState((BlockPos)block.getKey()).getBlock() == Blocks.water || NoWater.mc.theWorld.getBlockState((BlockPos)block.getKey()).getBlock() == Blocks.flowing_water || NoWater.mc.theWorld.getBlockState((BlockPos)block.getKey()).getBlock() == Blocks.lava || NoWater.mc.theWorld.getBlockState((BlockPos)block.getKey()).getBlock() == Blocks.flowing_lava;
            if (checkBlock) {
                NoWater.shouldCancelWater = true;
                if (!this.modeValue.getValue().equals(noWaterMode.Grim) || !NoWater.shouldCancelWater) {
                    continue;
                }
                PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, (BlockPos)block.getKey(), NoWater.mc.objectMouseOver.sideHit));
                PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, (BlockPos)block.getKey(), NoWater.mc.objectMouseOver.sideHit));
            }
        }
    }
    
    public enum noWaterMode
    {
        Vanilla, 
        Grim;
    }
}

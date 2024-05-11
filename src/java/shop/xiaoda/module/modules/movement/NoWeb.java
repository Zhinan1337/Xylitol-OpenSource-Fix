//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

 
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.player.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import java.util.*;
import shop.xiaoda.event.*;


public class NoWeb extends Module
{
    private final ModeValue<noWebMode> modeValue;
    
    public NoWeb() {
        super("NoWeb", Category.Movement);
        this.modeValue = new ModeValue<noWebMode>("Mode", noWebMode.values(), noWebMode.Grim);
    }
    
    public void onDisable() {
        NoWeb.mc.timer.timerSpeed = 1.0f;
    }
    
    @EventTarget
    private void onUpdate(final EventMotion e) {
        if (e.isPost()) {
            return;
        }
        this.setSuffix(this.modeValue.getValue().name());
        if (!NoWeb.mc.thePlayer.isInWeb) {
            return;
        }
        switch (this.modeValue.getValue()) {
            case Vanilla: {
                NoWeb.mc.thePlayer.isInWeb = false;
                break;
            }
            case Grim: {
                final Map<BlockPos, Block> searchBlock = BlockUtil.searchBlocks(5);
                for (final Map.Entry<BlockPos, Block> block : searchBlock.entrySet()) {
                    if (NoWeb.mc.theWorld.getBlockState((BlockPos)block.getKey()).getBlock() instanceof BlockWeb) {
                        PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, (BlockPos)block.getKey(), NoWeb.mc.objectMouseOver.sideHit));
                        PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, (BlockPos)block.getKey(), NoWeb.mc.objectMouseOver.sideHit));
                    }
                }
                NoWeb.mc.thePlayer.isInWeb = false;
                break;
            }
            case AAC: {
                NoWeb.mc.thePlayer.jumpMovementFactor = 0.59f;
                if (!NoWeb.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    NoWeb.mc.thePlayer.motionY = 0.0;
                    break;
                }
                break;
            }
            case LowAAC: {
                NoWeb.mc.thePlayer.jumpMovementFactor = ((NoWeb.mc.thePlayer.movementInput.moveStrafe != 0.0f) ? 1.0f : 1.21f);
                if (!NoWeb.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    NoWeb.mc.thePlayer.motionY = 0.0;
                }
                if (NoWeb.mc.thePlayer.onGround) {
                    NoWeb.mc.thePlayer.jump();
                    break;
                }
                break;
            }
            case Rewind: {
                NoWeb.mc.thePlayer.jumpMovementFactor = 0.42f;
                if (NoWeb.mc.thePlayer.onGround) {
                    NoWeb.mc.thePlayer.jump();
                    break;
                }
                break;
            }
        }
    }
    
    private enum noWebMode
    {
        Vanilla, 
        Grim, 
        AAC, 
        LowAAC, 
        Rewind;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import shop.xiaoda.module.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.event.world.*;
import net.minecraft.block.*;
import net.minecraft.client.settings.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.Module;

public class Eagle extends Module
{
    public Eagle() {
        super("Eagle", Category.World);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return Eagle.mc.theWorld.getBlockState(pos).getBlock();
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer player) {
        return getBlock(new BlockPos(player.posX, player.posY - 1.0, player.posZ));
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        if (event.isPre()) {
            if (getBlockUnderPlayer((EntityPlayer)Eagle.mc.thePlayer) instanceof BlockAir) {
                if (Eagle.mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
            }
            else if (Eagle.mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
        }
    }
    
    public void onEnable() {
        if (Eagle.mc.thePlayer == null) {
            return;
        }
        Eagle.mc.thePlayer.setSneaking(false);
        super.onEnable();
    }
    
    public void onDisable() {
        KeyBinding.setKeyBindState(Eagle.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.onDisable();
    }
}

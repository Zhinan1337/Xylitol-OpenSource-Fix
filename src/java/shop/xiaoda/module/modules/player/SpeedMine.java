//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.player;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import net.minecraft.util.*;
import shop.xiaoda.module.*;
import net.minecraft.network.play.client.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;

public class SpeedMine extends Module
{
    private final NumberValue speed;
    private final BoolValue abortPacketSpoof;
    private final BoolValue speedCheckBypass;
    private EnumFacing facing;
    private BlockPos pos;
    private boolean boost;
    private float damage;
    
    public SpeedMine() {
        super("SpeedMine", Category.Player);
        this.speed = new NumberValue("Speed", 1.1, 1.0, 3.0, 0.1);
        this.abortPacketSpoof = new BoolValue("AbortPacketSpoof", true);
        this.speedCheckBypass = new BoolValue("VanillaCheckBypass", true);
        this.boost = false;
        this.damage = 0.0f;
    }
    
    public void onDisable() {
        if (SpeedMine.mc.thePlayer == null) {
            return;
        }
        if (this.speedCheckBypass.getValue()) {
            SpeedMine.mc.thePlayer.removePotionEffect(Potion.digSpeed.id);
        }
    }
    
    @EventTarget
    private void onPacket(final EventPacketSend e) {
        if (e.packet instanceof C07PacketPlayerDigging) {
            if (((C07PacketPlayerDigging)e.getPacket()).getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                this.boost = true;
                this.pos = ((C07PacketPlayerDigging)e.getPacket()).getPosition();
                this.facing = ((C07PacketPlayerDigging)e.getPacket()).getFacing();
                this.damage = 0.0f;
            }
            else if (((C07PacketPlayerDigging)e.getPacket()).getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || ((C07PacketPlayerDigging)e.getPacket()).getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                this.boost = false;
                this.pos = null;
                this.facing = null;
            }
        }
    }
    
    @EventTarget
    private void onUpdate(final EventUpdate e) {
        if (this.speedCheckBypass.getValue()) {
            SpeedMine.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 89640, 2));
        }
        if (SpeedMine.mc.playerController.extendedReach()) {
            SpeedMine.mc.playerController.blockHitDelay = 0;
        }
        else if (this.pos != null && this.boost) {
            final IBlockState blockState = SpeedMine.mc.theWorld.getBlockState(this.pos);
            this.damage += (float)(blockState.getBlock().getPlayerRelativeBlockHardness((EntityPlayer)SpeedMine.mc.thePlayer, (World)SpeedMine.mc.theWorld, this.pos) * this.speed.getValue());
            if (this.damage >= 1.0f) {
                SpeedMine.mc.theWorld.setBlockState(this.pos, Blocks.air.getDefaultState(), 11);
                if (this.abortPacketSpoof.getValue()) {
                    PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
                }
                PacketUtil.sendPacketNoEvent((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing));
                this.damage = 0.0f;
                this.boost = false;
            }
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

 
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import shop.xiaoda.module.modules.world.*;
import shop.xiaoda.event.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.module.modules.combat.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;


public class NoSlow extends Module
{
    public final List<Integer> blacklist;
    boolean eatSlow;
    boolean canCanCelC08;
    TimeUtil timer;
    long delay;
    boolean fasterDelay;
    private final ModeValue<NoSlowMode> mode;
    
    public NoSlow() {
        super("NoSlow", Category.Movement);
        this.blacklist = Arrays.asList(54, 146, 61, 62);
        this.timer = new TimeUtil();
        this.delay = 100L;
        this.mode = new ModeValue<NoSlowMode>("Mode", NoSlowMode.values(), NoSlowMode.Vanilla);
    }
    
    private boolean shouldCancelPlacement(final MovingObjectPosition objectPosition) {
        return !this.blacklist.contains(Block.getIdFromBlock(NoSlow.mc.theWorld.getBlockState(objectPosition.getBlockPos()).getBlock()));
    }
    
    private boolean isHoldingPotionAndSword(final ItemStack stack, final boolean checkSword, final boolean checkPotionFood) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemAppleGold && checkPotionFood) {
            return true;
        }
        if (stack.getItem() instanceof ItemPotion && checkPotionFood) {
            return !ItemPotion.isSplash(stack.getMetadata());
        }
        if (stack.getItem() instanceof ItemFood && checkPotionFood) {
            return true;
        }
        if (stack.getItem() instanceof ItemSword && checkSword) {
            return true;
        }
        if (stack.getItem() instanceof ItemBow) {
            return checkPotionFood;
        }
        return stack.getItem() instanceof ItemBucketMilk && checkPotionFood;
    }
    
    @EventTarget
    public void onSlowDown(final EventSlowDown e) {
        if (e.getType() == EventSlowDown.Type.Item) {
            e.setCancelled(!this.eatSlow || this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, false));
            if (NoSlow.mc.thePlayer.isUsingItem() && NoSlow.mc.thePlayer.moveForward > 0.0f) {
                NoSlow.mc.thePlayer.setSprinting(!(this.getModule((Class)Scaffold.class)).getState());
            }
        }
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), false, this.mode.getValue().equals(NoSlowMode.Grim)) && NoSlow.mc.thePlayer.isUsingItem()) {
            if (packet instanceof S30PacketWindowItems) {
                event.setCancelled();
                this.eatSlow = false;
            }
            if (packet instanceof S2FPacketSetSlot) {
                event.setCancelled();
            }
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), false, this.mode.getValue().equals(NoSlowMode.Grim))) {
            if (packet instanceof C08PacketPlayerBlockPlacement) {
                this.eatSlow = true;
            }
            if (packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                this.eatSlow = true;
            }
        }
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), this.mode.getValue().equals(NoSlowMode.OldGrim), this.mode.getValue().equals(NoSlowMode.OldGrim)) && !(NoSlow.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && NoSlow.mc.thePlayer.getHeldItem() != null && NoSlow.mc.thePlayer.getHeldItem().getItem() != null && !this.canCanCelC08 && (((C08PacketPlayerBlockPlacement)event.getPacket()).position.getX() != -1 || ((C08PacketPlayerBlockPlacement)event.getPacket()).position.getY() != -1 || (((C08PacketPlayerBlockPlacement)event.getPacket()).position.getZ() != -1 && this.shouldCancelPlacement(NoSlow.mc.objectMouseOver)))) {
            event.setCancelled(this.canCanCelC08 = true);
            this.canCanCelC08 = false;
        }
    }
    
    @EventTarget
    public void onUpdate(final EventMotion e) {
        this.setSuffix(this.mode.getValue().toString());
        switch (this.mode.getValue()) {
            case Grim: {
                if (e.isPre() && !KillAura.isBlocking) {
                    if (NoSlow.mc.thePlayer.isUsingItem() && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, false)) {
                        PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                    if (NoSlow.mc.thePlayer.isUsingItem() && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), false, true)) {
                        NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0EPacketClickWindow(0, 36, 0, 2, new ItemStack(Block.getBlockById(166)), (short)0));
                    }
                }
                if (e.isPost() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking) && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, false)) {
                    PacketUtil.sendPacketC0F();
                    NoSlow.mc.playerController.sendUseItem((EntityPlayer)NoSlow.mc.thePlayer, (World)NoSlow.mc.theWorld, NoSlow.mc.thePlayer.getHeldItem());
                    break;
                }
                break;
            }
            case OldGrim: {
                if (e.isPre() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking) && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, true)) {
                    PacketUtil.send((Packet<?>)new C09PacketHeldItemChange(NoSlow.mc.thePlayer.inventory.currentItem % 8 + 1));
                    PacketUtil.send((Packet<?>)new C09PacketHeldItemChange(NoSlow.mc.thePlayer.inventory.currentItem));
                    break;
                }
                break;
            }
            case AAC4:
            case Packet: {
                if (e.isPre() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking)) {
                    PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                if (e.isPost() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking)) {
                    NoSlow.mc.playerController.sendUseItem((EntityPlayer)NoSlow.mc.thePlayer, (World)NoSlow.mc.theWorld, NoSlow.mc.thePlayer.getHeldItem());
                    break;
                }
                break;
            }
            case Intave: {
                if (e.isPre() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking) && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, false)) {
                    PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                if (e.isPost() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking) && this.isHoldingPotionAndSword(NoSlow.mc.thePlayer.getHeldItem(), true, false) && this.timer.hasTimeElapsed(this.delay)) {
                    PacketUtil.send((Packet<?>)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    if (this.fasterDelay) {
                        this.delay = 100L;
                    }
                    else {
                        this.delay = 200L;
                    }
                    this.fasterDelay = !this.fasterDelay;
                    this.timer.reset();
                    break;
                }
                break;
            }
            case AAC5: {
                if (e.isPost() && (NoSlow.mc.thePlayer.isUsingItem() || KillAura.isBlocking)) {
                    NoSlow.mc.playerController.sendUseItem((EntityPlayer)NoSlow.mc.thePlayer, (World)NoSlow.mc.theWorld, NoSlow.mc.thePlayer.getHeldItem());
                    break;
                }
                break;
            }
            case Hypixel: {
                if (e.isPre()) {
                    if (mc.thePlayer.isUsingItem() && !mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 3 == 0) {
                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), EnumFacing.UP.getIndex(), null, 0.0f, 0.0f, 0.0f));
                    }

                    if (mc.thePlayer.isBlocking()) {
                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    }
                }
            }
        }
    }
    
    enum NoSlowMode
    {
        Vanilla, 
        OldGrim, 
        Grim, 
        Packet, 
        AAC5, 
        AAC4, 
        Intave,
        Hypixel
    }
}

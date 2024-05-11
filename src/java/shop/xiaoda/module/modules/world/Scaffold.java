//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.modules.movement.*;
import shop.xiaoda.module.*;
import shop.xiaoda.gui.notification.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.player.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.utils.*;
import net.minecraft.init.*;
import java.util.*;

public class Scaffold extends Module
{
    public static final List<Block> invalidBlocks;
    public final ModeValue<mode> modeValue;
    public static double keepYCoord;
    public final BoolValue swing;
    public final BoolValue sprinValue;
    public final BoolValue adStrafe;
    private static final BoolValue keepYValue;
    private final NumberValue speed;
    public final ModeValue<placetime> placeTime;
    public final BoolValue eagle;
    public final BoolValue safeValue;
    public final BoolValue blockCount;
    public final ModeValue<OldScaffold.counterModes> counterMode;
    public final BoolValue shadow;
    public final BoolValue blur;
    boolean tip;
    private int slot;
    private BlockData data;
    protected Random rand;
    private int vl;
    
    public Scaffold() {
        super("Scaffold", Category.World);
        this.modeValue = new ModeValue<mode>("Mode", mode.values(), mode.Normal);
        this.swing = new BoolValue("Swing", true);
        this.sprinValue = new BoolValue("Sprint", Boolean.valueOf(true)) {
            @Override
            public Boolean getValue() {
                return (Scaffold.this.vl > 15) ? Boolean.valueOf(Scaffold.this.doHighVLTips()) : super.getValue();
            }
        };
        this.adStrafe = new BoolValue("ADStrafe", false);
        this.speed = new NumberValue("Speed", 1.0, 0.0, 2.0, 0.01);
        this.placeTime = new ModeValue<placetime>("PlaceTime", placetime.values(), placetime.Tick);
        this.eagle = new BoolValue("Eagle", true);
        this.safeValue = new BoolValue("Safe walk", true);
        this.blockCount = new BoolValue("BlockCount", true);
        this.counterMode = new ModeValue<OldScaffold.counterModes>("BlockCountMode", OldScaffold.counterModes.values(), OldScaffold.counterModes.Simple);
        this.shadow = new BoolValue("Shadow", true);
        this.blur = new BoolValue("Blur", true);
        this.tip = false;
        this.rand = new Random();
        this.vl = 0;
    }
    
    private boolean doHighVLTips() {
        if (!this.tip) {
            NotificationManager.post(NotificationType.WARNING, "Scaffold", "You are in high vl,sprint disabled.");
        }
        this.tip = true;
        return false;
    }
    
    public void onEnable() {
        if (Scaffold.mc.thePlayer == null) {
            return;
        }
        this.tip = false;
        this.data = null;
        this.slot = -1;
    }
    
    public void onDisable() {
        if (Scaffold.mc.thePlayer == null) {
            return;
        }
        KeyBinding.setKeyBindState(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        if (this.adStrafe.getValue()) {
            if (Scaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
                Scaffold.mc.gameSettings.keyBindLeft.setPressed(false);
            }
            else if (Scaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
                Scaffold.mc.gameSettings.keyBindRight.setPressed(false);
            }
        }
    }
    
    @EventTarget
    public void onUpdate(final EventMotion event) {
        if (event.isPre() && this.eagle.getValue()) {
            if (Eagle.getBlockUnderPlayer((EntityPlayer)Scaffold.mc.thePlayer) instanceof BlockAir) {
                if (Scaffold.mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
            }
            else if (Scaffold.mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
        }
    }
    
    @EventTarget
    public void onStrafe(final EventStrafe event) {
        if (Scaffold.keepYValue.getValue() && Scaffold.mc.thePlayer.onGround && MoveUtil.isMoving() && !Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
            Scaffold.mc.thePlayer.jump();
        }
    }
    
    @EventTarget
    private void onTick(final EventTick event) {
        if (Scaffold.mc.thePlayer == null) {
            return;
        }
        if (this.slot < 0) {
            return;
        }
        if (this.placeTime.is("Tick")) {
            this.place();
        }
    }
    
    @EventTarget
    private void onMove(final EventMove event) {
        if (Scaffold.mc.thePlayer.onGround && this.safeValue.getValue()) {
            Scaffold.mc.thePlayer.safeWalk = true;
        }
    }
    
    @EventTarget
    private void onPlace(final EventPlace event) {
        this.slot = this.getBlockSlot();
        if (this.slot < 0) {
            return;
        }
        event.setCancelled();
        if (Scaffold.mc.thePlayer == null) {
            return;
        }
        if (this.placeTime.is("Legit")) {
            this.place();
            Scaffold.mc.sendClickBlockToController(Scaffold.mc.currentScreen == null && Scaffold.mc.gameSettings.keyBindAttack.isKeyDown() && Scaffold.mc.inGameHasFocus);
        }
    }
    
    public static double getYLevel() {
        if (!Scaffold.keepYValue.getValue()) {
            return Scaffold.mc.thePlayer.posY - 1.0;
        }
        return MoveUtil.isMoving() ? Scaffold.keepYCoord : (Scaffold.mc.thePlayer.posY - 1.0);
    }
    
    @EventTarget
    public void onWorld(final EventWorldLoad e) {
        this.tip = false;
        this.vl = 0;
    }
    
    @EventTarget
    public void onPacket(final EventPacketSend e) {
        if (e.getPacket() instanceof C03PacketPlayer && this.sprinValue.getValue()) {
            final C03PacketPlayer c03 = (C03PacketPlayer)e.getPacket();
            if (c03.pitch > 90.0f) {
                ++this.vl;
            }
        }
    }
    
    @EventTarget
    private void onUpdate(final EventUpdate event) {
        if (Scaffold.mc.thePlayer.onGround) {
            Scaffold.keepYCoord = Math.floor(Scaffold.mc.thePlayer.posY - 1.0);
        }
        if (this.getBlockCount() < 1) {
            return;
        }
        float yaw = this.adStrafe.getValue() ? this.getYaw() : PlayerUtil.getMoveYaw(this.getYaw());
        float pitch = 83.5f;
        if (this.sprinValue.getValue()) {
            yaw = (float)(RotationUtil.wrapAngleToDirection(Scaffold.mc.thePlayer.rotationYaw, 4) * 90);
            pitch = 102.0f;
        }
        RotationComponent.setRotations(new Vector2f(PlayerUtil.getMoveYaw(yaw), pitch), 10.0, MovementFix.NORMAL);
    }
    
    @EventTarget
    private void onMotion(final EventMotion event) {
        if (event.isPre()) {
            if (this.getBlockCount() < 1) {
                return;
            }
            if (this.sprinValue.getValue() && Scaffold.mc.thePlayer.moveForward > 0.0f) {
                Scaffold.mc.thePlayer.setSprinting(true);
            }
            if (this.getBlockCount() <= 0) {
                final int spoofSlot = this.getBestSpoofSlot();
                this.getBlock(spoofSlot);
            }
            this.data = ((this.getBlockData(new BlockPos(Scaffold.mc.thePlayer.posX, getYLevel(), Scaffold.mc.thePlayer.posZ)) == null) ? this.getBlockData(new BlockPos(Scaffold.mc.thePlayer.posX, getYLevel(), Scaffold.mc.thePlayer.posZ).down(1)) : this.getBlockData(new BlockPos(Scaffold.mc.thePlayer.posX, getYLevel(), Scaffold.mc.thePlayer.posZ)));
            this.slot = this.getBlockSlot();
            if (this.slot < 0) {
                return;
            }
            Scaffold.mc.thePlayer.inventoryContainer.getSlot(this.slot + 36).getStack();
        }
    }
    
    @EventTarget
    private void onMotion2(final EventMotion event) {
        this.slot = this.getBlockSlot();
        if (this.slot < 0) {
            return;
        }
        if (this.placeTime.is("Pre") && event.isPre()) {
            this.place();
        }
        if (this.placeTime.is("Post") && event.isPost()) {
            this.place();
        }
    }
    
    private void place() {
        if (MoveUtil.isMoving()) {
            Scaffold.mc.gameSettings.keyBindJump.pressed = false;
        }
        this.slot = this.getBlockSlot();
        if (this.slot < 0) {
            return;
        }
        final int last = Scaffold.mc.thePlayer.inventory.currentItem;
        Scaffold.mc.thePlayer.inventory.currentItem = this.slot;
        if (PlayerUtil.block(Scaffold.mc.thePlayer.posX, getYLevel(), Scaffold.mc.thePlayer.posZ) instanceof BlockAir) {
            if (this.getBlockCount() < 1) {
                return;
            }
            if (this.data != null) {
                final boolean normalPlace = this.modeValue.getValue().equals(mode.Normal) || this.sprinValue.getValue();
                if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.getCurrentEquippedItem(), this.data.getBlockPos(), normalPlace ? this.data.getEnumFacing() : Scaffold.mc.objectMouseOver.sideHit, normalPlace ? this.getVec3(this.data.getBlockPos(), this.data.getEnumFacing()) : Scaffold.mc.objectMouseOver.hitVec)) {
                    if (Scaffold.mc.thePlayer.onGround && !this.sprinValue.getValue()) {
                        final EntityPlayerSP thePlayer = Scaffold.mc.thePlayer;
                        thePlayer.motionX *= this.speed.getValue();
                        final EntityPlayerSP thePlayer2 = Scaffold.mc.thePlayer;
                        thePlayer2.motionZ *= this.speed.getValue();
                    }
                    if (this.swing.getValue()) {
                        Scaffold.mc.thePlayer.swingItem();
                    }
                    else {
                        Scaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
                    }
                }
                Scaffold.mc.thePlayer.inventory.currentItem = last;
            }
        }
    }
    
    private Vec3 getVec3(final BlockPos pos, final EnumFacing face) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += MathUtil.getRandomInRange(0.3, -0.3);
            z += MathUtil.getRandomInRange(0.3, -0.3);
        }
        else {
            y += MathUtil.getRandomInRange(0.3, -0.3);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += MathUtil.getRandomInRange(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += MathUtil.getRandomInRange(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }
    
    public int getBlockSlot() {
        for (int i = 0; i < 9; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i + 36).getHasStack() && Scaffold.mc.thePlayer.inventoryContainer.getSlot(i + 36).getStack().getItem() instanceof ItemBlock) {
                return i;
            }
        }
        return -1;
    }
    
    private BlockData getBlockData(final BlockPos pos) {
        if (this.isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos2 = pos.add(-1, 0, 0);
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos3 = pos.add(1, 0, 0);
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos4 = pos.add(0, 0, 1);
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos5 = pos.add(0, 0, -1);
        if (this.isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        pos.add(-2, 0, 0);
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        pos.add(2, 0, 0);
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        pos.add(0, 0, 2);
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        pos.add(0, 0, -2);
        if (this.isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos6 = pos.add(0, -1, 0);
        if (this.isPosSolid(pos6.add(0, -1, 0))) {
            return new BlockData(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos6.add(-1, 0, 0))) {
            return new BlockData(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos6.add(1, 0, 0))) {
            return new BlockData(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos6.add(0, 0, 1))) {
            return new BlockData(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos6.add(0, 0, -1))) {
            return new BlockData(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos7 = pos6.add(1, 0, 0);
        if (this.isPosSolid(pos7.add(0, -1, 0))) {
            return new BlockData(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos7.add(-1, 0, 0))) {
            return new BlockData(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos7.add(1, 0, 0))) {
            return new BlockData(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos7.add(0, 0, 1))) {
            return new BlockData(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos7.add(0, 0, -1))) {
            return new BlockData(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos8 = pos6.add(-1, 0, 0);
        if (this.isPosSolid(pos8.add(0, -1, 0))) {
            return new BlockData(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos8.add(-1, 0, 0))) {
            return new BlockData(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos8.add(1, 0, 0))) {
            return new BlockData(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos8.add(0, 0, 1))) {
            return new BlockData(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos8.add(0, 0, -1))) {
            return new BlockData(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos9 = pos6.add(0, 0, 1);
        if (this.isPosSolid(pos9.add(0, -1, 0))) {
            return new BlockData(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos9.add(-1, 0, 0))) {
            return new BlockData(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos9.add(1, 0, 0))) {
            return new BlockData(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos9.add(0, 0, 1))) {
            return new BlockData(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos9.add(0, 0, -1))) {
            return new BlockData(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos pos10 = pos6.add(0, 0, -1);
        if (this.isPosSolid(pos10.add(0, -1, 0))) {
            return new BlockData(pos10.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos10.add(-1, 0, 0))) {
            return new BlockData(pos10.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos10.add(1, 0, 0))) {
            return new BlockData(pos10.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos10.add(0, 0, 1))) {
            return new BlockData(pos10.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos10.add(0, 0, -1))) {
            return new BlockData(pos10.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private boolean isPosSolid(final BlockPos pos) {
        final Block block = Scaffold.mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isVisuallyOpaque() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }
    
    public int getBlockCount() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = stack.getItem();
                if (stack.getItem() instanceof ItemBlock && this.isValid(item)) {
                    n += stack.stackSize;
                }
            }
        }
        return n;
    }
    
    private boolean isValid(final Item item) {
        return item instanceof ItemBlock && !Scaffold.invalidBlocks.contains(((ItemBlock)item).getBlock());
    }
    
    private float getYaw() {
        if (Scaffold.mc.gameSettings.keyBindBack.isKeyDown()) {
            return Scaffold.mc.thePlayer.rotationYaw;
        }
        if (Scaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
            return Scaffold.mc.thePlayer.rotationYaw + 90.0f;
        }
        if (Scaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
            return Scaffold.mc.thePlayer.rotationYaw - 90.0f;
        }
        return Scaffold.mc.thePlayer.rotationYaw - 180.0f;
    }
    
    private void getBlock(final int switchSlot) {
        for (int i = 9; i < 45; ++i) {
            if (Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (Scaffold.mc.currentScreen == null || Scaffold.mc.currentScreen instanceof GuiInventory)) {
                final ItemStack is = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)is.getItem();
                    if (this.isValid((Item)block)) {
                        if (36 + switchSlot != i) {
                            InventoryUtil.swap(i, switchSlot);
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    int getBestSpoofSlot() {
        int spoofSlot = 5;
        for (int i = 36; i < 45; ++i) {
            if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                spoofSlot = i - 36;
                break;
            }
        }
        return spoofSlot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    static {
        invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, (Block)Blocks.chest, Blocks.dispenser, Blocks.air, (Block)Blocks.water, (Block)Blocks.lava, (Block)Blocks.flowing_water, (Block)Blocks.flowing_lava, (Block)Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, (Block)Blocks.stone_slab, (Block)Blocks.wooden_slab, (Block)Blocks.stone_slab2, (Block)Blocks.red_mushroom, (Block)Blocks.brown_mushroom, (Block)Blocks.yellow_flower, (Block)Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, (Block)Blocks.cactus, Blocks.ladder, Blocks.web);
        keepYValue = new BoolValue("Keep Y", false);
    }
    
    public enum counterModes
    {
        Simple, 
        Classic;
    }
    
    public enum placetime
    {
        Post, 
        Pre, 
        Tick, 
        Legit;
    }
    
    public enum mode
    {
        Normal, 
        Legit;
    }
    
    private static class BlockData
    {
        private final BlockPos blockPos;
        private final EnumFacing enumFacing;
        
        public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }
        
        public BlockPos getBlockPos() {
            return this.blockPos;
        }
        
        public EnumFacing getEnumFacing() {
            return this.enumFacing;
        }
    }
}

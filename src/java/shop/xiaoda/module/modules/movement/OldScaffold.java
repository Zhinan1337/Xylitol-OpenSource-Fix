//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import org.lwjgl.compatibility.display.Display;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import shop.xiaoda.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.event.*;
import net.minecraft.potion.*;
import org.lwjgl.input.*;
 
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.player.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.utils.*;
import net.minecraft.init.*;
import java.util.*;

public class OldScaffold extends Module
{
    public static double keepYCoord;
    public static final List<Block> invalidBlocks;
    public final BoolValue swing;
    public final BoolValue adStrafe;
    private static final BoolValue keepYValue;
    public final BoolValue sprint;
    public final ModeValue<sprintModes> sprintMode;
    public final ModeValue<placeTimeModes> placeTime;
    public final BoolValue rotation;
    public final ModeValue<rotationModes> rotationMode;
    private final NumberValue rotationSpeedH;
    private final NumberValue rotationSpeedV;
    public final BoolValue keepRotation;
    public static final BoolValue grimSprintValue;
    public final BoolValue customSpeed;
    private final NumberValue speed;
    public final BoolValue blockCount;
    public final ModeValue<counterModes> counterMode;
    public final BoolValue shadow;
    public final BoolValue blur;
    public int slot;
    private BlockData data;
    float[] rotationDelta;
    float[] lastRotation;
    protected Random rand;
    private double scale;
    public static float cameraYaw;
    public static float cameraPitch;
    private static int previousPerspective;
    public static boolean perspectiveToggled;
    
    public OldScaffold() {
        super("OldScaffold", Category.Movement);
        this.swing = new BoolValue("Swing", true);
        this.adStrafe = new BoolValue("ADStrafe", false);
        this.sprint = new BoolValue("Sprint", false);
        this.sprintMode = new ModeValue<sprintModes>("SprintMode", sprintModes.values(), sprintModes.Normal);
        this.placeTime = new ModeValue<placeTimeModes>("PlaceTime", placeTimeModes.values(), placeTimeModes.Post);
        this.rotation = new BoolValue("Rotation", true);
        this.rotationMode = new ModeValue<rotationModes>("RotationMode", rotationModes.values(), rotationModes.Normal);
        this.rotationSpeedH = new NumberValue("HorizontalSpeed", 4.2, 0.0, 40.0, 0.5);
        this.rotationSpeedV = new NumberValue("VerticalSpeed", 2.4, 0.0, 40.0, 0.5);
        this.keepRotation = new BoolValue("KeepRotation", true);
        this.customSpeed = new BoolValue("CustomSpeed", true);
        this.speed = new NumberValue("Speed", 0.11, 0.08, 0.4, 0.01);
        this.blockCount = new BoolValue("BlockCount", true);
        this.counterMode = new ModeValue<counterModes>("BlockCountMode", counterModes.values(), counterModes.Simple);
        this.shadow = new BoolValue("Shadow", true);
        this.blur = new BoolValue("Blur", true);
        this.rotationDelta = new float[] { 0.0f, 0.0f };
        this.lastRotation = new float[] { 0.0f, 0.0f };
        this.rand = new Random();
        this.scale = 0.0;
    }
    
    public void onEnable() {
        if (OldScaffold.mc.thePlayer == null) {
            return;
        }
        this.data = null;
        this.slot = -1;
        this.lastRotation[0] = OldScaffold.mc.thePlayer.rotationYaw;
        this.lastRotation[1] = OldScaffold.mc.thePlayer.rotationPitch;
        this.rotationDelta[0] = 0.0f;
        this.rotationDelta[1] = 0.0f;
        OldScaffold.perspectiveToggled = OldScaffold.grimSprintValue.getValue();
        OldScaffold.cameraYaw = OldScaffold.mc.thePlayer.rotationYaw;
        OldScaffold.cameraPitch = OldScaffold.mc.thePlayer.rotationPitch;
        if (OldScaffold.perspectiveToggled) {
            OldScaffold.previousPerspective = OldScaffold.mc.gameSettings.thirdPersonView;
            OldScaffold.mc.gameSettings.thirdPersonView = 0;
        }
        else {
            OldScaffold.mc.gameSettings.thirdPersonView = 0;
        }
        super.onEnable();
    }
    
    public static boolean overrideMouse() {
        if (OldScaffold.mc.thePlayer != null && Client.instance != null && (!OldScaffold.grimSprintValue.getValue() || !((OldScaffold)Client.instance.moduleManager.getModule((Class)OldScaffold.class)).getState())) {
            return OldScaffold.mc.inGameHasFocus;
        }
        if (OldScaffold.mc.inGameHasFocus && Display.isActive()) {
            if (!OldScaffold.perspectiveToggled) {
                return true;
            }
            OldScaffold.mc.mouseHelper.mouseXYChange();
            final float f1 = OldScaffold.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float f2 = f1 * f1 * f1 * 8.0f;
            final float f3 = OldScaffold.mc.mouseHelper.deltaX * f2;
            final float f4 = OldScaffold.mc.mouseHelper.deltaY * f2;
            OldScaffold.cameraYaw += f3 * 0.15f;
            OldScaffold.cameraPitch -= f4 * 0.15f;
            if (OldScaffold.cameraPitch > 90.0f) {
                OldScaffold.cameraPitch = 90.0f;
            }
            if (OldScaffold.cameraPitch < -90.0f) {
                OldScaffold.cameraPitch = -90.0f;
            }
        }
        return false;
    }
    
    public static void resetPerspective() {
        OldScaffold.perspectiveToggled = false;
        OldScaffold.mc.gameSettings.thirdPersonView = 0;
        OldScaffold.mc.thePlayer.rotationYaw = OldScaffold.cameraYaw;
        OldScaffold.mc.thePlayer.rotationPitch = OldScaffold.cameraPitch;
    }
    
    public void onDisable() {
        resetPerspective();
        this.scale = 0.0;
        if (this.adStrafe.getValue()) {
            if (OldScaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
                OldScaffold.mc.gameSettings.keyBindLeft.setPressed(false);
            }
            else if (OldScaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
                OldScaffold.mc.gameSettings.keyBindRight.setPressed(false);
            }
        }
        this.lastRotation[0] = OldScaffold.mc.thePlayer.rotationYaw;
        this.lastRotation[1] = OldScaffold.mc.thePlayer.rotationPitch;
        this.rotationDelta[0] = 0.0f;
        this.rotationDelta[1] = 0.0f;
        super.onDisable();
    }
    
    @EventTarget
    private void onTick(final EventTick event) {
        if (this.placeTime.getValue() == placeTimeModes.Tick) {
            this.place();
        }
    }
    
    @EventTarget
    private void onClick(final EventPlace event) {
        if (this.adStrafe.getValue()) {
            if (OldScaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
                OldScaffold.mc.gameSettings.keyBindLeft.setPressed(false);
                OldScaffold.mc.gameSettings.keyBindRight.setPressed(true);
            }
            else if (OldScaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
                OldScaffold.mc.gameSettings.keyBindRight.setPressed(false);
                OldScaffold.mc.gameSettings.keyBindLeft.setPressed(true);
            }
            else {
                OldScaffold.mc.gameSettings.keyBindLeft.setPressed(true);
            }
        }
        if (this.placeTime.getValue() == placeTimeModes.Legit) {
            this.place();
        }
    }
    
    @EventTarget
    
    public void onMove(final EventMove event) {
        if (this.sprintMode.getValue() == sprintModes.Hypixel && MoveUtil.isMoving() && PlayerUtil.isOnGround(1.0E-4)) {
            if (OldScaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                event.setMoveSpeed(MoveUtil.getBaseMoveSpeed() * 0.55);
                OldScaffold.mc.thePlayer.setSprinting(true);
            }
            else if (!Keyboard.isKeyDown(OldScaffold.mc.gameSettings.keyBindJump.getKeyCode())) {
                event.setMoveSpeed(MoveUtil.getBaseMoveSpeed() * 0.74);
                OldScaffold.mc.thePlayer.setSprinting(true);
            }
            else {
                OldScaffold.mc.thePlayer.setSprinting(false);
            }
        }
    }
    
    public static double getYLevel() {
        if (!OldScaffold.keepYValue.getValue()) {
            return OldScaffold.mc.thePlayer.posY - 1.0;
        }
        return OldScaffold.mc.thePlayer.isMoving() ? OldScaffold.keepYCoord : (OldScaffold.mc.thePlayer.posY - 1.0);
    }
    
    @EventTarget
    private void onPreUpdate(final EventMotion event) {
        if (!event.isPre()) {
            return;
        }
        if (OldScaffold.mc.thePlayer.onGround) {
            OldScaffold.keepYCoord = Math.floor(OldScaffold.mc.thePlayer.posY - 1.0);
        }
        if (this.getBlockCount() < 1) {
            return;
        }
        if (this.rotation.getValue() && (this.keepRotation.getValue() || PlayerUtil.getBlock(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ)) instanceof BlockAir)) {
            if (this.rotationMode.getValue() == rotationModes.Normal) {
                if (OldScaffold.grimSprintValue.getValue()) {
                    event.setPitch(98.0f);
                    RotationUtil.setVisualRotations(event);
                    if (OldScaffold.mc.thePlayer.ticksExisted % 14 == 0) {
                        OldScaffold.mc.gameSettings.keyBindSneak.pressed = true;
                        OldScaffold.mc.gameSettings.keyBindSneak.pressed = false;
                    }
                }
                else {
                    float[] rotations = { 0.0f, 0.0f };
                    rotations = new float[] { this.adStrafe.getValue() ? (OldScaffold.mc.thePlayer.rotationYaw - 180.0f) : (PlayerUtil.getMoveYaw(OldScaffold.mc.thePlayer.rotationYaw) - 180.0f), 83.5f };
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                    RotationUtil.setVisualRotations(event);
                }
            }
            else if (this.rotationMode.getValue() == rotationModes.Smooth) {
                final double horizontalSpeed = this.rotationSpeedH.getValue() * 3.0 + ((this.rotationSpeedH.getValue() > 0.0) ? this.rand.nextDouble() : 0.0);
                final double verticalSpeed = this.rotationSpeedV.getValue() * 3.0 + ((this.rotationSpeedV.getValue() > 0.0) ? this.rand.nextDouble() : 0.0);
                final float yaw = this.adStrafe.getValue() ? (OldScaffold.mc.thePlayer.rotationYaw - 180.0f) : (PlayerUtil.getMoveYaw(OldScaffold.mc.thePlayer.rotationYaw) - 180.0f);
                final float pitch = 83.5f;
                this.rotationDelta[0] = Math.abs(yaw - this.lastRotation[0]);
                this.rotationDelta[1] = Math.abs(pitch - this.lastRotation[1]);
                RotationComponent.setRotations(new Vector2f(RotationUtil.getRotation(this.lastRotation[0], yaw, (float)horizontalSpeed), RotationUtil.getRotation(this.lastRotation[1], pitch, (float)verticalSpeed)), 9.0, MovementFix.NORMAL);
                this.lastRotation[0] = RotationUtil.getRotation(this.lastRotation[0], yaw, (float)horizontalSpeed);
                this.lastRotation[1] = RotationUtil.getRotation(this.lastRotation[1], pitch, (float)verticalSpeed);
                RotationUtil.setVisualRotations(event);
            }
        }
        if (this.sprint.getValue()) {
            if (this.sprintMode.getValue() == sprintModes.Normal) {
                if (OldScaffold.mc.gameSettings.keyBindForward.isKeyDown() && OldScaffold.mc.thePlayer.isMoving()) {
                    OldScaffold.mc.thePlayer.setSprinting(true);
                }
            }
            else if (this.sprintMode.getValue() == sprintModes.Legit) {
                if (OldScaffold.mc.gameSettings.keyBindForward.isKeyDown() && OldScaffold.mc.thePlayer.isMoving() && !(PlayerUtil.getBlock(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ)) instanceof BlockAir)) {
                    OldScaffold.mc.thePlayer.setSprinting(true);
                }
                else {
                    OldScaffold.mc.thePlayer.setSprinting(false);
                }
            }
        }
        else {
            OldScaffold.mc.thePlayer.setSprinting(false);
        }
        if (this.customSpeed.getValue()) {
            if (OldScaffold.mc.thePlayer.isMoving()) {
                MoveUtil.setSpeed(this.speed.getValue());
            }
            else {
                final EntityPlayerSP thePlayer = OldScaffold.mc.thePlayer;
                thePlayer.motionX *= 0.0;
                final EntityPlayerSP thePlayer2 = OldScaffold.mc.thePlayer;
                thePlayer2.motionZ *= 0.0;
            }
        }
        if (this.getBlockCount() <= 0 && this.getallBlockCount() <= 0) {
            return;
        }
        if (this.getBlockCount() <= 0) {
            final int spoofSlot = this.getBestSpoofSlot();
            this.getBlock(spoofSlot);
        }
        this.data = ((this.getBlockData(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ)) == null) ? this.getBlockData(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ).down(1)) : this.getBlockData(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ)));
        this.slot = this.getBlockSlot();
        OldScaffold.mc.thePlayer.inventoryContainer.getSlot(this.slot + 36).getStack();
        if (this.data == null || this.slot == -1 || this.getBlockCount() <= 0 || (!MoveUtil.isMoving() && !OldScaffold.mc.gameSettings.keyBindJump.isKeyDown())) {
            return;
        }
        if (!this.sprint.getValue() || this.sprintMode.getValue() == sprintModes.Legit) {}
        if (this.placeTime.getValue() == placeTimeModes.Pre) {
            this.place();
        }
    }
    
    @EventTarget
    private void onPostUpdate(final EventMotion event) {
        if (!event.isPost()) {
            return;
        }
        if (this.placeTime.getValue() == placeTimeModes.Post) {
            PacketUtil.sendPacketC0F();
            this.place();
        }
    }
    
    public void place() {
        this.slot = this.getBlockSlot();
        if (PlayerUtil.getBlock(new BlockPos(OldScaffold.mc.thePlayer.posX, getYLevel(), OldScaffold.mc.thePlayer.posZ)) instanceof BlockAir) {
            if (this.getBlockCount() < 1) {
                return;
            }
            final int last = OldScaffold.mc.thePlayer.inventory.currentItem;
            OldScaffold.mc.thePlayer.inventory.currentItem = this.slot;
            if (this.data != null) {
                if (OldScaffold.mc.playerController.onPlayerRightClick(OldScaffold.mc.thePlayer, OldScaffold.mc.theWorld, OldScaffold.mc.thePlayer.getCurrentEquippedItem(), this.data.getBlockPos(), this.data.getEnumFacing(), this.getVec3(this.data.getBlockPos(), this.data.getEnumFacing()))) {
                    if (this.swing.getValue()) {
                        OldScaffold.mc.thePlayer.swingItem();
                    }
                    else {
                        OldScaffold.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
                    }
                }
                OldScaffold.mc.thePlayer.inventory.currentItem = last;
            }
        }
    }
    
    public Vec3 getVec3(final BlockPos pos, final EnumFacing face) {
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
            if (OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i + 36).getHasStack() && OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i + 36).getStack().getItem() instanceof ItemBlock) {
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
        final Block block = OldScaffold.mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isVisuallyOpaque() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }
    
    public int getBlockCount() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = stack.getItem();
                if (stack.getItem() instanceof ItemBlock && isValid(item)) {
                    n += stack.stackSize;
                }
            }
        }
        return n;
    }
    
    public int getallBlockCount() {
        int n = 0;
        for (int i = 0; i < 36; ++i) {
            if (OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = stack.getItem();
                if (stack.getItem() instanceof ItemBlock && isValid(item)) {
                    n += stack.stackSize;
                }
            }
        }
        return n;
    }
    
    public static boolean isValid(final Item item) {
        return item instanceof ItemBlock && !OldScaffold.invalidBlocks.contains(((ItemBlock)item).getBlock());
    }
    
    void getBlock(final int hotbarSlot) {
        for (int i = 9; i < 45; ++i) {
            if (OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (OldScaffold.mc.currentScreen == null || OldScaffold.mc.currentScreen instanceof GuiInventory)) {
                final ItemStack is = OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)is.getItem();
                    if (isValid((Item)block)) {
                        if (36 + hotbarSlot != i) {
                            InventoryUtil.swap(i, hotbarSlot);
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
            if (!OldScaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                spoofSlot = i - 36;
                break;
            }
        }
        return spoofSlot;
    }
    
    static {
        invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, (Block)Blocks.chest, Blocks.dispenser, Blocks.air, (Block)Blocks.water, (Block)Blocks.lava, (Block)Blocks.flowing_water, (Block)Blocks.flowing_lava, (Block)Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, (Block)Blocks.stone_slab, (Block)Blocks.wooden_slab, (Block)Blocks.stone_slab2, (Block)Blocks.red_mushroom, (Block)Blocks.brown_mushroom, (Block)Blocks.yellow_flower, (Block)Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, (Block)Blocks.stained_glass_pane, Blocks.iron_bars, (Block)Blocks.cactus, Blocks.ladder, Blocks.web);
        keepYValue = new BoolValue("Keep Y", false);
        grimSprintValue = new BoolValue("GrimSprint", true);
        OldScaffold.cameraYaw = 0.0f;
        OldScaffold.cameraPitch = 0.0f;
        OldScaffold.previousPerspective = 0;
        OldScaffold.perspectiveToggled = false;
    }
    
    enum sprintModes
    {
        Normal, 
        Legit, 
        Hypixel;
    }
    
    enum placeTimeModes
    {
        Pre, 
        Post, 
        Tick, 
        Legit;
    }
    
    enum rotationModes
    {
        Normal, 
        Smooth;
    }
    
    public enum counterModes
    {
        Simple, 
        Classic;
    }
    
    private class BlockData
    {
        private final BlockPos pos;
        private final EnumFacing facing;
        
        public BlockData(final BlockPos pos, final EnumFacing facing) {
            this.pos = pos;
            this.facing = facing;
        }
        
        public BlockPos getBlockPos() {
            return this.pos;
        }
        
        public EnumFacing getEnumFacing() {
            return this.facing;
        }
    }
}

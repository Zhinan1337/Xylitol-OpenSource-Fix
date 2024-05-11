//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.world;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.*;
import shop.xiaoda.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.compatibility.util.vector.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.tileentity.*;
import shop.xiaoda.module.modules.render.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import shop.xiaoda.module.modules.combat.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.utils.render.GLUtil;

public class ChestAura extends Module
{
    private final NumberValue range;
    private final NumberValue delay;
    private final BoolValue rayTrace;
    private final BoolValue rotation;
    private final BoolValue moveFix;
    private final BoolValue once;
    private final BoolValue esp;
    private final ArrayList<BlockPos> opened;
    private final TimeUtil timer;
    private BlockPos blockCorner;
    private final ArrayList<BlockPos> pos;
    public static BlockPos lastBlock;
    
    public ChestAura() {
        super("ContainerAura", Category.World);
        this.range = new NumberValue("Range", 3.0, 1.0, 7.0, 0.1);
        this.delay = new NumberValue("Delay", 120.0, 0.0, 1000.0, 10.0);
        this.rayTrace = new BoolValue("RayTrace", true);
        this.rotation = new BoolValue("Rotation", true);
        this.moveFix = new BoolValue("Movement Fix", true);
        this.once = new BoolValue("Log Opened Chest", true);
        this.esp = new BoolValue("ESP", true);
        this.opened = new ArrayList<BlockPos>();
        this.timer = new TimeUtil();
        this.pos = new ArrayList<BlockPos>();
    }
    
    public void onDisable() {
        this.opened.clear();
        this.pos.clear();
        ChestAura.lastBlock = null;
        super.onDisable();
    }
    
    @EventTarget
    private void onUpdate(final EventMotion event) {
        if (event.isPre() && !ChestAura.mc.thePlayer.isUsingItem() && ChestAura.mc.currentScreen == null && ChestAura.mc.thePlayer.hurtTime == 0 && !((Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class)).getState()) {
            if (ChestAura.lastBlock != null) {
                if (ChestAura.mc.thePlayer.getDistance((double)ChestAura.lastBlock.getX(), (double)ChestAura.lastBlock.getY(), (double)ChestAura.lastBlock.getZ()) > this.range.getValue()) {
                    ChestAura.lastBlock = null;
                }
                if (ChestAura.mc.currentScreen instanceof GuiChest || ChestAura.mc.currentScreen instanceof GuiFurnace || ChestAura.mc.currentScreen instanceof GuiBrewingStand) {
                    this.pos.add(ChestAura.lastBlock);
                    ChestAura.lastBlock = null;
                }
            }
            if (ChestAura.lastBlock == null) {
                this.blockCorner = null;
            }
            if (ChestAura.mc.currentScreen == null && this.isAllowed()) {
                final BlockPos chest = (ChestAura.lastBlock != null) ? ChestAura.lastBlock : this.getNextChest();
                if (this.once.getValue() && this.opened.contains(chest)) {
                    return;
                }
                if (chest != null && this.blockCorner != null) {
                    final float[] rot = RotationUtil.getRotationsNeededBlock(chest.getX(), chest.getY(), chest.getZ());
                    if (this.rotation.getValue()) {
                        final float rotation1 = rot[0];
                        final float rotation2 = rot[1] + 1.0f;
                        if (this.moveFix.getValue()) {
                            RotationComponent.setRotations(new Vector2f(rotation1, rotation2), 10.0, MovementFix.NORMAL);
                        }
                        else {
                            event.setYaw(rotation1);
                            event.setPitch(rotation2);
                        }
                    }
                    if (this.moveFix.getValue()) {
                        RotationComponent.setRotations(new Vector2f(rot[0], rot[1]), 10.0, MovementFix.NORMAL);
                    }
                    else {
                        event.setYaw(rot[0]);
                        event.setPitch(rot[1]);
                    }
                    if (this.timer.isDelayComplete(this.delay.getValue().intValue())) {
                        ChestAura.mc.thePlayer.swingItem();
                        ChestAura.mc.playerController.onPlayerRightClick(ChestAura.mc.thePlayer, ChestAura.mc.theWorld, ChestAura.mc.thePlayer.getHeldItem(), chest, EnumFacing.DOWN, new Vec3(ChestAura.mc.thePlayer.posX, ChestAura.mc.thePlayer.posY, ChestAura.mc.thePlayer.posZ));
                        this.timer.reset();
                        ChestAura.lastBlock = chest;
                        this.opened.add(chest);
                    }
                }
            }
        }
    }
    
    @EventTarget
    public void onWorld(final EventWorldLoad e) {
        this.opened.clear();
        this.pos.clear();
        ChestAura.lastBlock = null;
    }
    
    @EventTarget
    public void onRender3DEvent(final EventRender3D var1) {
        if (this.esp.getValue()) {
            for (final TileEntity chest : ChestAura.mc.theWorld.loadedTileEntityList) {
                if (ChestAura.mc.thePlayer.getDistance(chest.getPos()) < 10.0) {
                    int color = -1;
                    if (chest instanceof TileEntityChest) {
                        color = 16755200;
                    }
                    else if (chest instanceof TileEntityEnderChest) {
                        color = 6947071;
                    }
                    if (this.opened.contains(chest.getPos())) {
                        color = HUD.color(1).getRGB();
                    }
                    if (color == -1) {
                        continue;
                    }
                    final BlockPos blockpos = chest.getPos();
                    GL11.glPushMatrix();
                    GL11.glDisable(2929);
                    GLUtil.startBlend();
                    GL11.glDepthMask(false);
                    GL11.glDisable(3553);
                    GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)51);
                    GL11.glTranslated(-ChestAura.mc.getRenderManager().renderPosX, -ChestAura.mc.getRenderManager().renderPosY, -ChestAura.mc.getRenderManager().renderPosZ);
                    RenderGlobal.drawSelectionBoundingBox(chest.getBlockType().getCollisionBoundingBox((World)ChestAura.mc.theWorld, blockpos, chest.getBlockType().getStateFromMeta(chest.getBlockMetadata())), false, true);
                    GL11.glEnable(2929);
                    GLUtil.endBlend();
                    GL11.glDepthMask(true);
                    GL11.glEnable(3553);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    private boolean isAllowed() {
        return !((KillAura)Client.instance.moduleManager.getModule((Class)KillAura.class)).getState() || KillAura.target == null;
    }
    
    private BlockPos getNextChest() {
        final Iterator<BlockPos> positions = BlockPos.getAllInBox(ChestAura.mc.thePlayer.getPosition().subtract(new Vec3i((double)this.range.getValue(), (double)this.range.getValue(), (double)this.range.getValue())), ChestAura.mc.thePlayer.getPosition().add(new Vec3i((double)this.range.getValue(), (double)this.range.getValue(), (double)this.range.getValue()))).iterator();
        BlockPos chestPos;
        while ((chestPos = positions.next()) != null) {
            if (ChestAura.mc.theWorld.getBlockState(chestPos.add(0, 1, 0)).getBlock() instanceof BlockAir && (ChestAura.mc.theWorld.getBlockState(chestPos).getBlock() instanceof BlockChest || ChestAura.mc.theWorld.getBlockState(chestPos).getBlock() instanceof BlockFurnace || ChestAura.mc.theWorld.getBlockState(chestPos).getBlock() instanceof BlockBrewingStand)) {
                if (this.pos.contains(chestPos)) {
                    continue;
                }
                final BlockPos blockPos;
                final BlockPos corner = blockPos = (this.rayTrace.getValue() ? PlayerUtil.getBlockCorner(new BlockPos(ChestAura.mc.thePlayer.posX, ChestAura.mc.thePlayer.posY + ChestAura.mc.thePlayer.getEyeHeight(), ChestAura.mc.thePlayer.posZ), chestPos) : chestPos);
                if (corner == null) {
                    continue;
                }
                this.blockCorner = corner;
                return chestPos;
            }
        }
        return null;
    }
}

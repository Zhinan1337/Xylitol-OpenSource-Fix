//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import net.minecraft.entity.*;

import java.util.List;
import java.util.function.*;
import shop.xiaoda.module.*;
import java.awt.*;
import net.minecraft.entity.item.*;
import shop.xiaoda.event.*;
import shop.xiaoda.utils.render.GLUtil;
import shop.xiaoda.utils.vec.*;
import net.minecraft.network.play.server.*;
import shop.xiaoda.utils.player.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import shop.xiaoda.utils.vec.Vector3d;

public final class TargetStrafe extends Module
{
    public final ModeValue<RenderMode> renderModeValue;
    public final NumberValue radiusValue;
    public final NumberValue strafeSpeedValue;
    public final BoolValue doTsValue;
    public final BoolValue holdSpaceValue;
    public final BoolValue AutoJumpValue;
    private final BoolValue S08FlagCheckValue;
    public NumberValue S08FlagTickValue;
    public final NumberValue shapeValue;
    private final NumberValue pointsValue;
    private final ColorValue renderColorValue;
    private final ColorValue activePointColorValue;
    private final ColorValue dormantPointColorValue;
    private final ColorValue invalidPointColorValue;
    private final List<Point3D> currentPoints;
    EntityLivingBase currentTarget;
    private float yaw;
    private Entity target;
    private boolean left;
    private boolean colliding;
    public Point3D currentPoint;
    int flags;
    private final Predicate<Entity> ENTITY_FILTER;
    
    public TargetStrafe() {
        super("TargetStrafe", Category.Movement);
        this.renderModeValue = new ModeValue<RenderMode>("Mode", RenderMode.values(), RenderMode.NORMAL);
        this.radiusValue = new NumberValue("Radius", 2.0, 0.1, 4.0, 0.1);
        this.strafeSpeedValue = new NumberValue("Strafe Speed", 0.15, 0.01, 1.0, 0.05);
        this.doTsValue = new BoolValue("Do Strafe", true);
        this.holdSpaceValue = new BoolValue("Hold Space", true);
        this.AutoJumpValue = new BoolValue("Auto Jump", true);
        this.S08FlagCheckValue = new BoolValue("S08Flag Check", false);
        this.S08FlagTickValue = new NumberValue("S08Flag Ticks", 6.0, 0.0, 30.0, 1.0);
        this.shapeValue = new NumberValue("Shape", 12.0, 0.0, 30.0, 1.0);
        this.pointsValue = new NumberValue("Points", 12.0, 1.0, 90.0, 1.0);
        this.renderColorValue = new ColorValue("Color", new Color(120, 255, 120).getRGB());
        this.activePointColorValue = new ColorValue("Active Color", new Color(-2147418368).getRGB());
        this.dormantPointColorValue = new ColorValue("Dormant Color", new Color(553648127).getRGB());
        this.invalidPointColorValue = new ColorValue("Invalid Color", new Color(553582592).getRGB());
        this.currentPoints = new ArrayList<Point3D>();
        this.ENTITY_FILTER = (entity -> entity.isEntityAlive() && TargetStrafe.mc.thePlayer.getDistanceSqToEntity(entity) <= 6.0 && entity != TargetStrafe.mc.thePlayer && AntiBot.isServerBot(entity) && !(entity instanceof EntityArmorStand));
    }
    
    @EventTarget
    public void onMove(final EventMoveInput event) {
        if (!this.doTsValue.getValue()) {
            return;
        }
        if (this.flags != 0) {
            return;
        }
        if (this.target != null && this.distanceToTarget() <= 3.0) {
            this.setRotation();
            event.setForward(1.0f);
            event.setStrafe(0.0f);
        }
    }
    
    @EventTarget
    public void onJump(final EventJump event) {
        if (!this.doTsValue.getValue()) {
            return;
        }
        if (this.target != null && this.distanceToTarget() <= 3.0) {
            this.setRotation();
            event.setYaw(this.yaw);
        }
    }
    
    @EventTarget
    public void onStrafe(final EventStrafe event) {
        if (!this.doTsValue.getValue()) {
            return;
        }
        if (this.target != null && this.distanceToTarget() <= 3.0) {
            this.setRotation();
            event.setYaw(this.yaw);
            if (TargetStrafe.mc.thePlayer.hurtTime != 0) {
                return;
            }
            if (TargetStrafe.mc.thePlayer.onGround && this.AutoJumpValue.getValue()) {
                TargetStrafe.mc.thePlayer.jump();
            }
            final float friction = 0.2f;
            event.setFriction(friction);
            MoveUtil.strafe(0.05000000074505806, this.yaw);
        }
    }
    
    private void setRotation() {
        if (this.target == null) {
            return;
        }
        float yaw = RotationUtil.smooth(RotationUtil.calculate(new Vector3d(this.target.posX, this.target.posY, this.target.posZ))).x + 135.0f * (this.left ? -1 : 1);
        final double range = this.radiusValue.getValue();
        final double posX = -MathHelper.sin((float)Math.toRadians(yaw)) * range + this.target.posX;
        final double posZ = MathHelper.cos((float)Math.toRadians(yaw)) * range + this.target.posZ;
        yaw = RotationUtil.smooth(RotationUtil.calculate(new Vector3d(posX, this.target.posY + this.target.getEyeHeight(), posZ))).x;
        this.yaw = yaw;
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook && this.S08FlagCheckValue.getValue()) {
            this.flags = this.S08FlagTickValue.getValue().intValue();
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (!this.doTsValue.getValue()) {
            return;
        }
        this.updateTarget();
        if (!this.doTsValue.getValue()) {
            return;
        }
        if (this.S08FlagCheckValue.getValue() && this.flags > 0) {
            --this.flags;
        }
        if (this.flags != 0) {
            return;
        }
        if (this.target == null) {
            return;
        }
        if (TargetStrafe.mc.gameSettings.keyBindSprint.isKeyDown() && this.target != null && this.distanceToTarget() <= 3.0) {
            TargetStrafe.mc.gameSettings.keyBindSprint.pressed = false;
        }
        if (TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown()) {
            this.left = true;
        }
        if (TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown()) {
            this.left = false;
        }
        if (TargetStrafe.mc.thePlayer.isCollidedHorizontally || !PlayerUtil.isBlockUnder(5.0)) {
            if (!this.colliding) {
                this.left = !this.left;
            }
            this.colliding = true;
        }
        else {
            this.colliding = false;
        }
    }
    
    private double distanceToTarget() {
        return TargetStrafe.mc.thePlayer.getDistanceSqToEntity(this.target);
    }
    
    private void updateTarget() {
        final KillAura aura = (KillAura)Client.instance.moduleManager.getModule((Class)KillAura.class);
        if (aura.state && KillAura.target != null && (this.target == null || !this.target.isEntityAlive() || this.distanceToTarget() > 6.0 || TargetStrafe.mc.thePlayer.offGroundTicks < 2)) {
            if (aura.state && KillAura.target != null) {
                this.target = (Entity)KillAura.target;
            }
            else {
                this.target = this.getTarget();
            }
        }
        if (this.target.isDead) {
            this.target = null;
        }
    }
    
    private Entity getTarget() {
        return (Entity)TargetStrafe.mc.theWorld.loadedEntityList.parallelStream().filter(this.ENTITY_FILTER).findFirst().orElse(null);
    }
    
    @EventTarget
    public void onEventMotion(final EventMotion event) {
        if (this.flags != 0) {
            return;
        }
        if (event.isPre()) {
            final EntityLivingBase target = KillAura.getTarget();
            if (target != null) {
                this.collectPoints(this.currentTarget = target);
                this.currentPoint = this.findPoint(target, this.currentPoints);
            }
            else {
                this.currentTarget = null;
                this.currentPoint = null;
            }
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        if (this.flags != 0) {
            return;
        }
        if (this.renderModeValue.getValue() == RenderMode.POINT && KillAura.getTarget() != null) {
            final float partialTicks = event.getPartialTicks();
            for (final Point3D point : this.currentPoints) {
                final double pointSize = 0.03;
                int color;
                if (this.currentPoint == point) {
                    color = this.activePointColorValue.getValue();
                }
                else if (point.valid) {
                    color = this.dormantPointColorValue.getValue();
                }
                else {
                    color = this.invalidPointColorValue.getValue();
                }
                final double x = RenderUtil.interpolate(point.prevX, point.x, partialTicks);
                final double y = RenderUtil.interpolate(point.prevY, point.y, partialTicks);
                final double z = RenderUtil.interpolate(point.prevZ, point.z, partialTicks);
                final AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 0.03, y + 0.03, z + 0.03);
                GLUtil.enableBlending();
                GLUtil.disableDepth();
                GLUtil.disableTexture2D();
                RenderUtil.color(color);
                final double renderX = Minecraft.getMinecraft().getRenderManager().renderPosX;
                final double renderY = Minecraft.getMinecraft().getRenderManager().renderPosY;
                final double renderZ = Minecraft.getMinecraft().getRenderManager().renderPosZ;
                GL11.glTranslated(-renderX, -renderY, -renderZ);
                RenderGlobal.drawSelectionBoundingBox(bb, false, true);
                GL11.glTranslated(renderX, renderY, renderZ);
                GLUtil.disableBlending();
                GLUtil.enableDepth();
                GLUtil.enableTexture2D();
            }
        }
        if (this.renderModeValue.getValue() == RenderMode.NORMAL) {
            final double x2 = KillAura.getTarget().lastTickPosX + (KillAura.getTarget().posX - KillAura.getTarget().lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks - TargetStrafe.mc.getRenderManager().viewerPosX;
            final double y2 = KillAura.getTarget().lastTickPosY + (KillAura.getTarget().posY - KillAura.getTarget().lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks - TargetStrafe.mc.getRenderManager().viewerPosY;
            final double z2 = KillAura.getTarget().lastTickPosZ + (KillAura.getTarget().posZ - KillAura.getTarget().lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ;
            RenderUtil.TScylinder2((Entity)KillAura.getTarget(), x2, y2, z2, this.radiusValue.getValue() - 0.00625, 6.0f, this.shapeValue.getValue().intValue(), new Color(0, 0, 0).getRGB());
            RenderUtil.TScylinder2((Entity)KillAura.getTarget(), x2, y2, z2, this.radiusValue.getValue() + 0.00625, 6.0f, this.shapeValue.getValue().intValue(), new Color(0, 0, 0).getRGB());
            if (MoveUtil.isMoving() && KillAura.strict && this.holdSpaceValue.getValue() && TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown()) {
                RenderUtil.drawCircle((Entity)KillAura.getTarget(), x2, y2, z2, this.radiusValue.getValue(), this.shapeValue.getValue().intValue(), 5.0f, this.renderColorValue.getValue());
            }
            else if (MoveUtil.isMoving() && KillAura.strict && !this.holdSpaceValue.getValue() && this.canStrafe()) {
                RenderUtil.drawCircle((Entity)KillAura.getTarget(), x2, y2, z2, this.radiusValue.getValue(), this.shapeValue.getValue().intValue(), 5.0f, this.renderColorValue.getValue());
            }
            else {
                RenderUtil.drawCircle((Entity)KillAura.getTarget(), x2, y2, z2, this.radiusValue.getValue(), this.shapeValue.getValue().intValue(), 5.0f, new Color(255, 255, 255).getRGB());
            }
        }
    }
    
    private Point3D findPoint(final EntityLivingBase target, final List<Point3D> points) {
        Point3D bestPoint = null;
        float biggestDif = -1.0f;
        for (final Point3D point : points) {
            if (point.valid) {
                final float yawChange = Math.abs(this.getYawChangeToPoint(target, point));
                if (yawChange <= biggestDif) {
                    continue;
                }
                biggestDif = yawChange;
                bestPoint = point;
            }
        }
        return bestPoint;
    }
    
    private float getYawChangeToPoint(final EntityLivingBase target, final Point3D point) {
        final double xDist = point.x - target.posX;
        final double zDist = point.z - target.posZ;
        final float yaw = target.rotationYaw;
        final float pitch = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return yaw + MathHelper.wrapAngleTo180_float(pitch - yaw);
    }
    
    private void collectPoints(final EntityLivingBase entity) {
        final int size = this.pointsValue.getValue().intValue();
        final double radius = this.radiusValue.getValue();
        this.currentPoints.clear();
        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;
        final double prevX = entity.prevPosX;
        final double prevY = entity.prevPosY;
        final double prevZ = entity.prevPosZ;
        for (int i = 0; i < size; ++i) {
            final double cos = radius * StrictMath.cos(i * 6.2831855f / size);
            final double sin = radius * StrictMath.sin(i * 6.2831855f / size);
            final double pointX = x + cos;
            final double pointZ = z + sin;
            this.currentPoints.add(new Point3D(pointX, y, pointZ, prevX + cos, prevY, prevZ + sin, this.validatePoint(pointX, pointZ)));
        }
    }
    
    private boolean validatePoint(final double x, final double z) {
        final Vec3 pointVec = new Vec3(x, TargetStrafe.mc.thePlayer.posY, z);
        final IBlockState blockState = TargetStrafe.mc.theWorld.getBlockState(new BlockPos(pointVec));
        final boolean canBeSeen = TargetStrafe.mc.theWorld.rayTraceBlocks(TargetStrafe.mc.thePlayer.getPositionVector(), pointVec, false, false, false) == null;
        return !this.isOverVoid(x, z) && !blockState.getBlock().canCollideCheck(blockState, false) && canBeSeen;
    }
    
    private boolean isOverVoid(final double x, final double z) {
        double posY;
        for (double startY = posY = TargetStrafe.mc.thePlayer.posY; posY > 0.0; --posY) {
            final IBlockState state = TargetStrafe.mc.theWorld.getBlockState(new BlockPos(x, posY, z));
            if (state.getBlock().canCollideCheck(state, false)) {
                return startY - posY > 3.0;
            }
        }
        return true;
    }
    
    public boolean keyMode() {
        if (TargetStrafe.mc.gameSettings.keyBindBack.isKeyDown() || TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown() || TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown()) {
            return false;
        }
        final boolean active = Minecraft.getMinecraft().thePlayer.movementInput.moveForward != 0.0f;
        if (this.holdSpaceValue.getValue()) {
            return TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown() && active;
        }
        return active;
    }
    
    public boolean canStrafe() {
        return KillAura.strict && KillAura.target != null && !Minecraft.getMinecraft().thePlayer.isSneaking() && this.keyMode();
    }
    
    private static final class Point3D
    {
        private final double x;
        private final double y;
        private final double z;
        private final double prevX;
        private final double prevY;
        private final double prevZ;
        private final boolean valid;
        
        public Point3D(final double x, final double y, final double z, final double prevX, final double prevY, final double prevZ, final boolean valid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prevX = prevX;
            this.prevY = prevY;
            this.prevZ = prevZ;
            this.valid = valid;
        }
    }
    
    public enum RenderMode
    {
        NORMAL, 
        POLYGON, 
        POINT, 
        OFF;
    }
}

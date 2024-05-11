// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.combat;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.viamcp.ViaMCP;
import net.viamcp.utils.AttackOrder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.compatibility.util.vector.Vector2f;
import shop.xiaoda.Client;
import shop.xiaoda.event.EventManager;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.attack.EventAttack;
import shop.xiaoda.event.misc.EventKey;
import shop.xiaoda.event.rendering.EventRender3D;
import shop.xiaoda.event.world.EventMotion;
import shop.xiaoda.event.world.EventSlowDown;
import shop.xiaoda.event.world.EventTick;
import shop.xiaoda.event.world.EventUpdate;
import shop.xiaoda.event.world.EventWorldLoad;
import shop.xiaoda.gui.notification.NotificationManager;
import shop.xiaoda.gui.notification.NotificationType;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.modules.combat.AntiBot;
import shop.xiaoda.module.modules.misc.Teams;
import shop.xiaoda.module.modules.player.Blink;
import shop.xiaoda.module.modules.render.HUD;
import shop.xiaoda.module.modules.world.Scaffold;
import shop.xiaoda.module.values.BoolValue;
import shop.xiaoda.module.values.ColorValue;
import shop.xiaoda.module.values.ModeValue;
import shop.xiaoda.module.values.NumberValue;
import shop.xiaoda.utils.MovementFix;
import shop.xiaoda.utils.RayCastUtil;
import shop.xiaoda.utils.RotationComponent;
import shop.xiaoda.utils.client.TimeUtil;
import shop.xiaoda.utils.player.RotationUtil;
import shop.xiaoda.utils.render.RenderUtil;

public class KillAura
        extends Module {
    public static EntityLivingBase target;
    public static List<Entity> targets;
    private int index;
    private final TimeUtil lossTimer = new TimeUtil();
    public static float[] KaRotation;
    private float randomYaw;
    private float randomPitch;
    public static float[] lastRotation;
    public static ModeValue<AuraModes> Mode;
    public static ModeValue<RotationModes> RotationMode;
    public static ModeValue<AttackingModes> AttackingMode;
    private final ModeValue<AutoBlockModes> autoBlockMode = new ModeValue("Auto block Mode", (Enum[])AutoBlockModes.values(), (Enum)AutoBlockModes.RightClick);
    private final ModeValue<TargetGetModes> targetGetMode = new ModeValue("TargetGet", (Enum[])TargetGetModes.values(), (Enum)TargetGetModes.Angle);
    private final ModeValue<MovementFix> moveType = new ModeValue("Movement Type", (Enum[])MovementFix.values(), (Enum)MovementFix.NORMAL);
    public static final NumberValue cpsValue;
    public NumberValue range = new NumberValue("Range", 3.2, 1.0, 7.0, 0.01);
    public NumberValue blockRange = new NumberValue("Block Range", 3.3, 1.0, 6.0, 0.01);
    public NumberValue wallRange = new NumberValue("Wall Range", 4.5, 1.0, 7.0, 0.1);
    public NumberValue Fov = new NumberValue("Fov", 360.0, 0.0, 360.0, 1.0);
    public NumberValue switchDelay = new NumberValue("SwitchDelay", 200.0, 1.0, 1000.0, 10.0);
    public BoolValue autoBlock = new BoolValue("Auto block", true);
    private final BoolValue keepSprint = new BoolValue("Keep sprint", true);
    private final BoolValue rayCast = new BoolValue("Ray cast", true);
    private final BoolValue legitValue = new BoolValue("Legit", true);
    public BoolValue playersValue = new BoolValue("Players", true);
    public BoolValue animalsValue = new BoolValue("Animals", true);
    public BoolValue mobsValue = new BoolValue("Mobs", false);
    public BoolValue invisibleValue = new BoolValue("Invisible", false);
    public BoolValue altSwitch = new BoolValue("LAlt Switch Strafe", false);
    private final ModeValue<espMode> targetEsp = new ModeValue("Target ESP", (Enum[])espMode.values(), (Enum)espMode.Circle);
    private final BoolValue circleValue = new BoolValue("Circle", true);
    public ColorValue circleColor = new ColorValue("CircleColor", Color.WHITE.getRGB(), this.circleValue::getValue);
    private final NumberValue circleAccuracy = new NumberValue("CircleAccuracy", 15.0, 0.0, 60.0, 1.0);
    public static boolean isBlocking;
    public static boolean strict;
    private final Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> this.getDistanceToEntity((Entity)e2, Minecraft.getMinecraft().thePlayer));
    private final Comparator<Entity> healthComparator = Comparator.comparingDouble(e2 -> ((EntityLivingBase)e2).getHealth());
    private final Comparator<Entity> hurtResistantTimeComparator = Comparator.comparingDouble(e2 -> e2.hurtResistantTime);
    private final Comparator<Entity> totalArmorComparator = Comparator.comparingDouble(e2 -> ((EntityLivingBase)e2).getTotalArmorValue());
    private final Comparator<Entity> ticksExistedComparator = Comparator.comparingDouble(e2 -> e2.ticksExisted);
    private final TimeUtil attackTimer = new TimeUtil();
    private final TimeUtil switchTimer = new TimeUtil();
    private float curPitch;
    private float curYaw;

    public KillAura() {
        super("KillAura", Category.Combat);
    }

    @Override
    public void onDisable() {
        if (KillAura.mc.thePlayer == null) {
            return;
        }
        this.lossTimer.reset();
        targets.clear();
        if (isBlocking) {
            this.stopBlock();
        }
        this.resetRo();
        this.index = 0;
        target = null;
    }

    @EventTarget
    public void onWorld(EventWorldLoad event) {
        isBlocking = false;
    }

    @Override
    public void onEnable() {
        if (KillAura.mc.thePlayer == null) {
            return;
        }
        this.resetRo();
        this.lossTimer.reset();
        this.index = 0;
        target = null;
    }

    public static boolean hasSword() {
        if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
            return Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
        }
        return false;
    }

    @EventTarget
    public void onKey(EventKey event) {
        if (event.getKey() == Keyboard.KEY_LMENU && ((Boolean)this.altSwitch.getValue()).booleanValue()) {
            boolean bl = strict = !strict;
            if (strict) {
                this.moveType.setValue(MovementFix.TRADITIONAL);
            } else {
                this.moveType.setValue(MovementFix.NORMAL);
            }
            NotificationManager.post(NotificationType.SUCCESS, "MovementCorrection", "Changed to " + (strict ? "Strict" : "Silent"));
        }
    }

    public float getDistanceToEntity(Entity target, Entity entityIn) {
        Vec3 eyes = entityIn.getPositionEyes(1.0f);
        Vec3 pos = RotationUtil.getNearestPointBB(eyes, target.getEntityBoundingBox());
        double xDist = Math.abs(pos.xCoord - eyes.xCoord);
        double yDist = Math.abs(pos.yCoord - eyes.yCoord);
        double zDist = Math.abs(pos.zCoord - eyes.zCoord);
        return (float)Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0) + Math.pow(zDist, 2.0));
    }

    private double getRange() {
        return Math.max((Double)this.range.getValue(), (Boolean)this.autoBlock.getValue() != false ? ((Double)this.blockRange.getValue()).doubleValue() : ((Double)this.range.getValue()).doubleValue());
    }

    @EventTarget
    private void onMotion(EventUpdate event) {
        if (Objects.requireNonNull(Client.instance.moduleManager.getModule(String.valueOf(Scaffold.class))).getState()) {
            return;
        }
        this.setSuffix(Mode.getValue());
        targets = this.getTargets(this.getRange());
        if (targets.isEmpty()) {
            if (RotationMode.getValue() == RotationModes.Rise) {
                this.randomiseTargetRotations();
            }
            target = null;
        }
        if (!this.shouldBlock()) {
            this.stopBlock();
        }
        switch ((TargetGetModes)(this.targetGetMode.getValue())) {
            case Angle: {
                targets.sort(this.angleComparator);
                break;
            }
            case Health: {
                targets.sort(this.healthComparator);
                break;
            }
            case HurtResistantTime: {
                targets.sort(this.hurtResistantTimeComparator);
                break;
            }
            case TotalArmor: {
                targets.sort(this.totalArmorComparator);
                break;
            }
            case TicksExisted: {
                targets.sort(this.ticksExistedComparator);
            }
        }
        if (targets.size() > 1 && (Mode.getValue() == AuraModes.Switch || Mode.getValue() == AuraModes.Multiple) && (this.switchTimer.delay(((Double)this.switchDelay.getValue()).longValue()) || ((AuraModes)(Mode.getValue())).equals(AuraModes.Multiple))) {
            ++this.index;
            this.switchTimer.reset();
        }
        if (targets.size() > 1 && Mode.getValue() == AuraModes.Single) {
            if ((double)this.getDistanceToEntity(target, KillAura.mc.thePlayer) > this.getRange()) {
                ++this.index;
            } else if (KillAura.target.isDead) {
                ++this.index;
            }
        }
        if (!targets.isEmpty()) {
            if (this.index >= targets.size()) {
                this.index = 0;
            }
            target = (EntityLivingBase)targets.get(this.index);
            this.doRotation(target);
            if (AttackingMode.is("Update")) {
                if (this.shouldBlock()) {
                    this.doBlock();
                }
                this.attack();
                if (this.shouldBlock()) {
                    this.doBlock();
                }
            }
        } else {
            this.resetRo();
        }
    }

    @EventTarget
    public void onTickEvent(EventTick event) {
        if (!targets.isEmpty() && AttackingMode.is("Tick")) {
            this.doRotation(target);
            if (this.shouldBlock()) {
                this.doBlock();
            }
            this.attack();
            if (this.shouldBlock()) {
                this.doBlock();
            }
        }
    }

    @EventTarget
    public void doAttackEntity(EventMotion event) {
        if (event.isPre() && !targets.isEmpty() && AttackingMode.is("Pre")) {
            this.doRotation(target);
            if (this.shouldBlock()) {
                this.doBlock();
            }
            this.attack();
            if (this.shouldBlock()) {
                this.doBlock();
            }
        }
        if (event.isPost() && !targets.isEmpty() && AttackingMode.is("Post")) {
            this.doRotation(target);
            if (this.shouldBlock()) {
                this.doBlock();
            }
            this.attack();
            if (this.shouldBlock()) {
                this.doBlock();
            }
        }
    }

    private void drawTargetESP(Entity ent, EventRender3D event) {
        GlStateManager.pushMatrix();
        GL11.glShadeModel(7425);
        GL11.glHint(3154, 4354);
        KillAura.mc.entityRenderer.setupCameraTransform(KillAura.mc.timer.renderPartialTicks, 2);
        double x = ent.prevPosX + (ent.posX - ent.prevPosX) * (double)event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = ent.prevPosY + (ent.posY - ent.prevPosY) * (double)event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * (double)event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        double xMoved = ent.posX - ent.prevPosX;
        double yMoved = ent.posY - ent.prevPosY;
        double zMoved = ent.posZ - ent.prevPosZ;
        double motionX = 0.0;
        double motionY = 0.0;
        double motionZ = 0.0;
        AxisAlignedBB axisAlignedBB = ent.getEntityBoundingBox();
        int color = ((EntityLivingBase)ent).hurtTime > 3 ? new Color(235, 40, 40, 45).getRGB() : new Color(150, 255, 40, 45).getRGB();
        switch ((espMode)(this.targetEsp.getValue())) {
            case Circle: {
                this.drawShadow(ent, HUD.color(0).getRGB());
                break;
            }
            case RedBox: {
                RenderUtil.renderBoundingBox((EntityLivingBase)ent, Color.red, 255.0f);
                RenderUtil.resetColor();
                break;
            }
            case Box: {
                GlStateManager.translate(x + (xMoved + motionX + (KillAura.mc.thePlayer.motionX + 0.005)), y + (yMoved + motionY + (KillAura.mc.thePlayer.motionY - 0.002)), z + (zMoved + motionZ + (KillAura.mc.thePlayer.motionZ + 0.005)));
                RenderUtil.drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.minX - 0.1 - ent.posX, axisAlignedBB.minY - 0.1 - ent.posY, axisAlignedBB.minZ - 0.1 - ent.posZ, axisAlignedBB.maxX + 0.1 - ent.posX, axisAlignedBB.maxY + 0.2 - ent.posY, axisAlignedBB.maxZ + 0.1 - ent.posZ), true, color);
                break;
            }
            case Normal: {
                GlStateManager.translate(x + (xMoved + motionX + (KillAura.mc.thePlayer.motionX + 0.005)), y + (yMoved + motionY + (KillAura.mc.thePlayer.motionY - 0.002)), z + (zMoved + motionZ + (KillAura.mc.thePlayer.motionZ + 0.005)));
                RenderUtil.drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.minX - ent.posX, axisAlignedBB.minY + (double)ent.getEyeHeight() + 0.11 - ent.posY, axisAlignedBB.minZ - ent.posZ, axisAlignedBB.maxX - ent.posX, axisAlignedBB.maxY - 0.13 - ent.posY, axisAlignedBB.maxZ - ent.posZ), false, color);
            }
        }
        GlStateManager.popMatrix();
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if (((Boolean)this.circleValue.getValue()).booleanValue()) {
            GL11.glPushMatrix();
            GL11.glTranslated(KillAura.mc.thePlayer.lastTickPosX + (KillAura.mc.thePlayer.posX - KillAura.mc.thePlayer.lastTickPosX) * (double)KillAura.mc.timer.renderPartialTicks - KillAura.mc.getRenderManager().renderPosX, KillAura.mc.thePlayer.lastTickPosY + (KillAura.mc.thePlayer.posY - KillAura.mc.thePlayer.lastTickPosY) * (double)KillAura.mc.timer.renderPartialTicks - KillAura.mc.getRenderManager().renderPosY, KillAura.mc.thePlayer.lastTickPosZ + (KillAura.mc.thePlayer.posZ - KillAura.mc.thePlayer.lastTickPosZ) * (double)KillAura.mc.timer.renderPartialTicks - KillAura.mc.getRenderManager().renderPosZ);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.0f);
            RenderUtil.glColor((Integer)this.circleColor.getValue());
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glBegin(3);
            int i = 0;
            while (i <= 360) {
                GL11.glVertex2f((float)(Math.cos((double)i * Math.PI / 180.0) * (double)((Double)this.range.getValue()).floatValue()), (float)(Math.sin((double)i * Math.PI / 180.0) * (double)((Double)this.range.getValue()).floatValue()));
                i = (int)((double)i + (61.0 - (Double)this.circleAccuracy.getValue()));
            }
            GL11.glVertex2f((float)(Math.cos(Math.PI * 2) * (double)((Double)this.range.getValue()).floatValue()), (float)(Math.sin(Math.PI * 2) * (double)((Double)this.range.getValue()).floatValue()));
            GL11.glEnd();
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        }
        if (this.targetEsp.getValue() != espMode.Off) {
            switch ((AuraModes)(Mode.getValue())) {
                case Single:
                case Switch: {
                    if (target == null) break;
                    for (Entity ent : targets) {
                        this.drawTargetESP(ent, event);
                    }
                    break;
                }
                case Multiple: {
                    if (targets.isEmpty()) break;
                    for (Entity ent : targets) {
                        this.drawTargetESP(ent, event);
                    }
                    break;
                }
            }
        }
    }

    @EventTarget
    void onSlowDownEvent(EventSlowDown event) {
        if (event.getType() == EventSlowDown.Type.Sprinting && ((Boolean)this.keepSprint.getValue()).booleanValue()) {
            event.setCancelled(true);
        }
    }

    private void randomiseTargetRotations() {
        this.randomYaw += (float)(Math.random() - 0.5);
        this.randomPitch += (float)(Math.random() - 0.5) * 2.0f;
    }

    private void doRotation(EntityLivingBase target) {
        if ((double)this.getDistanceToEntity(target, KillAura.mc.thePlayer) <= (Double)this.range.getValue()) {
            switch ((RotationModes)(RotationMode.getValue())) {
                case Simple: {
                    KaRotation = KillAura.getRotation(target);
                    break;
                }
                case Rise: {
                    KaRotation = new float[]{RotationUtil.calculate(target, true, (Double)this.range.getValue()).getX(), Math.min(RotationUtil.calculate(target, true, (Double)this.range.getValue()).getY(), 90.0f)};
                    this.randomiseTargetRotations();
                    KaRotation[0] = KaRotation[0] + this.randomYaw;
                    KaRotation[1] = KaRotation[1] + this.randomPitch;
                    if (RayCastUtil.rayCast((Vector2f)new Vector2f((float)KillAura.KaRotation[0], (float)KillAura.KaRotation[1]), (double)((Double)this.range.getValue()).doubleValue()).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) break;
                    this.randomPitch = 0.0f;
                    this.randomYaw = 0.0f;
                    break;
                }
                case Normal: {
                    KaRotation = KillAura.getRotationNormal(target);
                    break;
                }
                case HvH: {
                    KaRotation = RotationUtil.getHVHRotation(target, (Double)this.range.getValue() + 0.1);
                    break;
                }
                case Hypixel: {
                    Random rand = new Random();
                    this.setRotation();
                    KaRotation = new float[]{new Vector2f(this.curYaw + (float)rand.nextInt(12) - 5.0f, this.curPitch).getX(), new Vector2f(this.curYaw + (float)rand.nextInt(12) - 5.0f, this.curPitch).getY()};
                    break;
                }
                case Smart: {
                    KaRotation = RotationUtil.getRotationsNeeded(target);
                    this.randomiseTargetRotations();
                    KaRotation[0] = KaRotation[0] + this.randomYaw;
                    KaRotation[1] = KaRotation[1] + this.randomPitch;
                    if (RayCastUtil.rayCast((Vector2f)new Vector2f((float)KillAura.KaRotation[0], (float)KillAura.KaRotation[1]), (double)((Double)this.range.getValue()).doubleValue()).typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) break;
                    this.randomPitch = 0.0f;
                    this.randomYaw = 0.0f;
                }
            }
            KillAura.lastRotation[0] = KaRotation[0];
            KillAura.lastRotation[2] = Math.min(90.0f, KaRotation[1]);
            RotationComponent.setRotations(new Vector2f(lastRotation[0], lastRotation[2]), 10.0, (MovementFix)(this.moveType.getValue()), true);
        }
    }

    private void resetRo() {
        lastRotation = new float[]{KillAura.mc.thePlayer.rotationYaw, KillAura.mc.thePlayer.renderYawOffset, KillAura.mc.thePlayer.rotationPitch};
    }

    private void attackEntity(Entity target) {
        AttackOrder.sendFixedAttack(KillAura.mc.thePlayer, target);
        this.attackTimer.reset();
    }

    public boolean shouldBlock() {
        return (Boolean)this.autoBlock.getValue() != false && KillAura.hasSword() && target != null;
    }

    private EntityLivingBase getMouseTarget() {
        MovingObjectPosition movingObjectPosition = KillAura.mc.objectMouseOver;
        if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (MovingObjectPosition.entityHit instanceof EntityLivingBase) {
                return (EntityLivingBase)MovingObjectPosition.entityHit;
            }
        }
        return null;
    }

    private void attack() {
        if (this.shouldAttack()) {
            EntityLivingBase targets;
            EntityLivingBase entityLivingBase = targets = (Boolean)this.legitValue.getValue() != false ? this.getMouseTarget() : target;
            if (targets != null) {
                EventManager.call(new EventAttack(targets, true));
                this.attackEntity(targets);
                EventManager.call(new EventAttack(targets, false));
                if (((Boolean)this.keepSprint.getValue()).booleanValue()) {
                    if (!(!(KillAura.mc.thePlayer.fallDistance > 0.0f) || KillAura.mc.thePlayer.onGround || KillAura.mc.thePlayer.isOnLadder() || KillAura.mc.thePlayer.isInWater() || KillAura.mc.thePlayer.isPotionActive(Potion.blindness) || KillAura.mc.thePlayer.ridingEntity != null)) {
                        KillAura.mc.thePlayer.onCriticalHit(targets);
                    }
                    if (EnchantmentHelper.getModifierForCreature(KillAura.mc.thePlayer.getHeldItem(), targets.getCreatureAttribute()) > 0.0f) {
                        KillAura.mc.thePlayer.onEnchantmentCritical(targets);
                    }
                }
            }
        }
    }

    private void setRotation() {
        Random rand = new Random();
        float[] rotations = RotationUtil.getRotations(target);
        this.curYaw = rotations[0];
        this.curPitch = rotations[1] + (float)rand.nextInt(12) - 5.0f;
        if (this.curPitch > 90.0f) {
            this.curPitch = 90.0f;
        } else if (this.curPitch < -90.0f) {
            this.curPitch = -90.0f;
        }
    }

    
    private void doBlock() {
        if (KillAura.hasSword()) {
            switch ((AutoBlockModes)(this.autoBlockMode.getValue())) {
                case Watchdog: {
                    if (ViaMCP.getInstance().getVersion() <= 47) {
                        return;
                    }
                    PacketWrapper useItemWD = PacketWrapper.create(29, null, (UserConnection)Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItemWD.write(Type.VAR_INT, 1);
                    PacketUtil.sendToServer(useItemWD, Protocol1_8TO1_9.class, true, true);
                    shop.xiaoda.utils.client.PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.inventory.getCurrentItem()));
                    break;
                }
                case RightClick: {
                    KeyBinding.setKeyBindState(KillAura.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    if (!Minecraft.getMinecraft().playerController.sendUseItem(KillAura.mc.thePlayer, KillAura.mc.theWorld, KillAura.mc.thePlayer.inventory.getCurrentItem())) break;
                    mc.getItemRenderer().resetEquippedProgress();
                    break;
                }
                case Shield_1_9: {
                    if (ViaMCP.getInstance().getVersion() <= 47) {
                        return;
                    }
                    shop.xiaoda.utils.client.PacketUtil.sendPacketC0F();
                    PacketWrapper useItem = PacketWrapper.create(29, null, (UserConnection)Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem.write(Type.VAR_INT, 1);
                    PacketUtil.sendToServer(useItem, Protocol1_8TO1_9.class, true, true);
                    KillAura.mc.playerController.sendUseItem(KillAura.mc.thePlayer, KillAura.mc.theWorld, KillAura.mc.thePlayer.getHeldItem());
                    break;
                }
                case C08: {
                    if (!((double)KillAura.mc.thePlayer.swingProgressInt < 0.5) || KillAura.mc.thePlayer.swingProgressInt == -1) break;
                    shop.xiaoda.utils.client.PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, KillAura.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                    KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.inventory.getCurrentItem()));
                    break;
                }
                case SendUseItem: {
                    if (ViaMCP.getInstance().getVersion() <= 47) {
                        KillAura.mc.playerController.sendUseItem(KillAura.mc.thePlayer, KillAura.mc.theWorld, KillAura.mc.thePlayer.getHeldItem());
                        break;
                    }
                    shop.xiaoda.utils.client.PacketUtil.sendPacketC0F();
                    PacketWrapper useItem_1_9 = PacketWrapper.create(29, null, (UserConnection)Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem_1_9.write(Type.VAR_INT, 1);
                    PacketUtil.sendToServer(useItem_1_9, Protocol1_8TO1_9.class, true, true);
                    KillAura.mc.playerController.sendUseItem(KillAura.mc.thePlayer, KillAura.mc.theWorld, KillAura.mc.thePlayer.getHeldItem());
                }
            }
            isBlocking = true;
        }
    }

    private void stopBlock() {
        if (KillAura.hasSword() && isBlocking) {
            switch ((AutoBlockModes)(this.autoBlockMode.getValue())) {
                case Watchdog: {
                    KeyBinding.setKeyBindState(KillAura.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    break;
                }
                case RightClick: {
                    KeyBinding.setKeyBindState(KillAura.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    Minecraft.getMinecraft().playerController.onStoppedUsingItem(KillAura.mc.thePlayer);
                    break;
                }
                case C08:
                case SendUseItem: {
                    if (KillAura.mc.thePlayer.swingProgressInt != -1) break;
                    shop.xiaoda.utils.client.PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
                }
                case Shield_1_9: {
                    KillAura.mc.gameSettings.keyBindUseItem.pressed = false;
                    shop.xiaoda.utils.client.PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }
            isBlocking = false;
        }
    }

    public List<Entity> getTargets(Double value) {
        return Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(e -> (double)this.getDistanceToEntity((Entity)e, KillAura.mc.thePlayer) <= value && this.shouldAdd((Entity)e)).collect(Collectors.toList());
    }

    private boolean shouldAdd(Entity target) {
        float entityFov = (float)RotationUtil.getRotationDifference(target);
        float fov = ((Double)this.Fov.getValue()).floatValue();
        Module blink = Client.instance.moduleManager.getModule(String.valueOf(Blink.class));
        double d = this.getDistanceToEntity(target, KillAura.mc.thePlayer);
        double d2 = KillAura.hasSword() ? this.getRange() : ((Double)this.range.getValue()).doubleValue();
        if (d > d2) {
            return false;
        }
        if (target.isInvisible() && !((Boolean)this.invisibleValue.getValue()).booleanValue()) {
            return false;
        }
        if (!target.isEntityAlive()) {
            return false;
        }
        if (fov != 360.0f && !(entityFov <= fov)) {
            return false;
        }
        if (target == Minecraft.getMinecraft().thePlayer || target.isDead || Minecraft.getMinecraft().thePlayer.getHealth() == 0.0f) {
            return false;
        }
        if ((target instanceof EntityMob || target instanceof EntityGhast || target instanceof EntityGolem || target instanceof EntityDragon || target instanceof EntitySlime) && ((Boolean)this.mobsValue.getValue()).booleanValue()) {
            return true;
        }
        if ((target instanceof EntitySquid || target instanceof EntityBat || target instanceof EntityVillager) && ((Boolean)this.animalsValue.getValue()).booleanValue()) {
            return true;
        }
        if (target instanceof EntityAnimal && ((Boolean)this.animalsValue.getValue()).booleanValue()) {
            return true;
        }
        if (AntiBot.isServerBot(target)) {
            return false;
        }
        if (blink.getState()) {
            return false;
        }
        if (Teams.isSameTeam(target)) {
            return false;
        }
        return target instanceof EntityPlayer && (Boolean)this.playersValue.getValue() != false;
    }

    protected final Vec3 getVectorForRotation(float p_getVectorForRotation_1_, float p_getVectorForRotation_2_) {
        float f = MathHelper.cos(-p_getVectorForRotation_2_ * ((float)Math.PI / 180) - (float)Math.PI);
        float f1 = MathHelper.sin(-p_getVectorForRotation_2_ * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = -MathHelper.cos(-p_getVectorForRotation_1_ * ((float)Math.PI / 180));
        float f3 = MathHelper.sin(-p_getVectorForRotation_1_ * ((float)Math.PI / 180));
        return new Vec3(f1 * f2, f3, f * f2);
    }

    private boolean isLookingAtEntity(float yaw, float pitch) {
        double range = KillAura.mc.thePlayer.canEntityBeSeen(target) ? ((Double)this.range.getValue()).doubleValue() : ((Double)this.wallRange.getValue()).doubleValue();
        Vec3 src = KillAura.mc.thePlayer.getPositionEyes(1.0f);
        Vec3 rotationVec = this.getVectorForRotation(pitch, yaw);
        Vec3 dest = src.addVector(rotationVec.xCoord * range, rotationVec.yCoord * range, rotationVec.zCoord * range);
        MovingObjectPosition obj = KillAura.mc.theWorld.rayTraceBlocks(src, dest, false, false, true);
        if (obj == null) {
            return false;
        }
        return target.getEntityBoundingBox().expand(0.1f, 0.1f, 0.1f).calculateIntercept(src, dest) != null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean shouldAttack() {
        MovingObjectPosition movingObjectPosition = KillAura.mc.objectMouseOver;
        if (!this.attackTimer.hasReached(1000.0 / ((Double)cpsValue.getValue() * 1.5))) return false;
        double d = this.getDistanceToEntity(target, KillAura.mc.thePlayer);
        Double d2 = KillAura.mc.thePlayer.canEntityBeSeen(target) ? (Double)this.range.getValue() : (Double)this.wallRange.getValue();
        if (!(d <= d2)) return false;
        if (!this.isLookingAtEntity(lastRotation[0], lastRotation[2])) return false;
        if ((Boolean)this.rayCast.getValue() == false) return true;
        if (!KillAura.mc.thePlayer.canEntityBeSeen(target)) return true;
        if ((Boolean)this.rayCast.getValue() == false) return false;
        if (movingObjectPosition == null) return false;
        if (MovingObjectPosition.entityHit != target) return false;
        return true;
    }

    private static float[] getRotationFloat(EntityLivingBase target, double xDiff, double yDiff) {
        double zDiff = target.posZ - KillAura.mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-Math.atan2(yDiff, dist) * 180.0 / Math.PI);
        float[] array = new float[2];
        int n = 0;
        float rotationYaw = lastRotation[0];
        array[n] = rotationYaw + MathHelper.wrapAngleTo180_float(yaw - lastRotation[0]);
        int n3 = 1;
        float rotationPitch = KillAura.mc.thePlayer.rotationPitch;
        array[n3] = rotationPitch + MathHelper.wrapAngleTo180_float(pitch - KillAura.mc.thePlayer.rotationPitch);
        return array;
    }

    public static float[] getRotation(EntityLivingBase target) {
        double xDiff = target.posX - KillAura.mc.thePlayer.posX;
        double yDiff = target.posY + (double)(target.getEyeHeight() / 5.0f * 3.0f) - (KillAura.mc.thePlayer.posY + (double)KillAura.mc.thePlayer.getEyeHeight());
        return KillAura.getRotationFloat(target, xDiff, yDiff);
    }

    public static float[] getRotationNormal(EntityLivingBase target) {
        double xDiff = target.posX - KillAura.mc.thePlayer.posX;
        double yDiff = target.posY + (double)(target.getEyeHeight() / 5.0f * 4.0f) - (KillAura.mc.thePlayer.posY + (double)KillAura.mc.thePlayer.getEyeHeight());
        return KillAura.getRotationFloat(target, xDiff, yDiff);
    }

    private void drawShadow(Entity entity, int color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);
        GL11.glEnable(2929);
        GlStateManager.alphaFunc(516, 0.0f);
        GL11.glShadeModel(7425);
        GlStateManager.disableCull();
        GL11.glBegin(5);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)KillAura.mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)KillAura.mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosY + Math.sin((double)System.currentTimeMillis() / 200.0) * (double)(entity.height / 2.0f) + (double)(1.0f * (entity.height / 2.0f));
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)KillAura.mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        float i = 0.0f;
        while ((double)i < Math.PI * 2) {
            double vecX = x + 0.67 * Math.cos(i);
            double vecZ = z + 0.67 * Math.sin(i);
            RenderUtil.glColor(new Color(RenderUtil.getColor(color).getRed(), RenderUtil.getColor(color).getGreen(), RenderUtil.getColor(color).getBlue(), 0).getRGB());
            GL11.glVertex3d(vecX, y - Math.cos((double)System.currentTimeMillis() / 200.0) * (double)(entity.height / 2.0f) / 2.0, vecZ);
            RenderUtil.glColor(new Color(RenderUtil.getColor(color).getRed(), RenderUtil.getColor(color).getGreen(), RenderUtil.getColor(color).getBlue(), 160).getRGB());
            GL11.glVertex3d(vecX, y, vecZ);
            i = (float)((double)i + 0.09817477042468103);
        }
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableCull();
        GL11.glDisable(2848);
        GL11.glDisable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor3f(255.0f, 255.0f, 255.0f);
    }

    public static float getPlayerRotation(Entity ent) {
        double x = ent.posX - KillAura.mc.thePlayer.posX;
        double z = ent.posZ - KillAura.mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.2957795;
        yaw = -yaw;
        return (float)yaw;
    }

    public static EntityLivingBase getTarget() {
        return target;
    }

    static {
        targets = new ArrayList<Entity>(0);
        Mode = new ModeValue("Mode", (Enum[])AuraModes.values(), (Enum)AuraModes.Single);
        RotationMode = new ModeValue("Rotation Mode", (Enum[])RotationModes.values(), (Enum)RotationModes.Simple);
        AttackingMode = new ModeValue("Attacking Mode", (Enum[])AttackingModes.values(), (Enum)AttackingModes.Pre);
        cpsValue = new NumberValue("CPS", 10.0, 1.0, 20.0, 1.0);
        strict = false;
    }

    public static enum TargetGetModes {
        Angle,
        Health,
        HurtResistantTime,
        TotalArmor,
        TicksExisted;

    }

    public static enum AutoBlockModes {
        RightClick,
        C08,
        SendUseItem,
        Shield_1_9,
        Watchdog,
        Fake;

    }

    public static enum AttackingModes {
        Pre,
        Post,
        All,
        Update,
        Tick;

    }

    public static enum RotationModes {
        Simple,
        Normal,
        HvH,
        Rise,
        Hypixel,
        Smart;

    }

    public static enum AuraModes {
        Switch,
        Single,
        Multiple;

    }

    private static enum espMode {
        Circle,
        Box,
        RedBox,
        Normal,
        Off;

    }
}

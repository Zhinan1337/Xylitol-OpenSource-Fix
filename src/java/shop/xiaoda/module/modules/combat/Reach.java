//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.combat;

import shop.xiaoda.module.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.misc.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;

public class Reach extends Module
{
    public static NumberValue MinReach;
    public static NumberValue MaxReach;
    private final BoolValue RandomReach;
    private final BoolValue weaponOnly;
    private final BoolValue movingOnly;
    private final BoolValue sprintOnly;
    private final BoolValue hitThroughBlocks;
    
    public Reach() {
        super("Reach", Category.Combat);
        this.RandomReach = new BoolValue("Random Reach", true);
        this.weaponOnly = new BoolValue("Weapon Only", false);
        this.movingOnly = new BoolValue("Moving Only", false);
        this.sprintOnly = new BoolValue("Sprint Only", false);
        this.hitThroughBlocks = new BoolValue("HitThroughBlocks", false);
    }
    
    public static double getRandomDoubleInRange(final double minDouble, final double maxDouble) {
        return (minDouble >= maxDouble) ? minDouble : (new Random().nextDouble() * (maxDouble - minDouble) + minDouble);
    }
    
    public static Object[] doReach(final double reachValue, final double AABB) {
        final Entity target = Reach.mc.getRenderViewEntity();
        Entity entity = null;
        if (target == null || Reach.mc.theWorld == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        final Vec3 targetEyes = target.getPositionEyes(0.0f);
        final Vec3 targetLook = target.getLook(0.0f);
        final Vec3 targetVector = targetEyes.addVector(targetLook.xCoord * reachValue, targetLook.yCoord * reachValue, targetLook.zCoord * reachValue);
        Vec3 targetVec = null;
        final List<Entity> targetHitbox = (List<Entity>)Reach.mc.theWorld.getEntitiesWithinAABBExcludingEntity(target, target.getEntityBoundingBox().addCoord(targetLook.xCoord * reachValue, targetLook.yCoord * reachValue, targetLook.zCoord * reachValue).expand(1.0, 1.0, 1.0));
        double reaching = reachValue;
        for (final Entity targetEntity : targetHitbox) {
            if (targetEntity.canBeCollidedWith()) {
                final float targetCollisionBorderSize = targetEntity.getCollisionBorderSize();
                AxisAlignedBB targetAABB = targetEntity.getEntityBoundingBox().expand((double)targetCollisionBorderSize, (double)targetCollisionBorderSize, (double)targetCollisionBorderSize);
                targetAABB = targetAABB.expand(AABB, AABB, AABB);
                final MovingObjectPosition targetPosition = targetAABB.calculateIntercept(targetEyes, targetVector);
                if (targetAABB.isVecInside(targetEyes)) {
                    if (0.0 >= reaching && reaching != 0.0) {
                        continue;
                    }
                    entity = targetEntity;
                    targetVec = ((targetPosition == null) ? targetEyes : targetPosition.hitVec);
                    reaching = 0.0;
                }
                else {
                    if (targetPosition == null) {
                        continue;
                    }
                    final double targetHitVec = targetEyes.distanceTo(targetPosition.hitVec);
                    if (targetHitVec >= reaching && reaching != 0.0) {
                        continue;
                    }
                    if (targetEntity == target.ridingEntity) {
                        if (reaching != 0.0) {
                            continue;
                        }
                        entity = targetEntity;
                        targetVec = targetPosition.hitVec;
                    }
                    else {
                        entity = targetEntity;
                        targetVec = targetPosition.hitVec;
                        reaching = targetHitVec;
                    }
                }
            }
        }
        if (reaching < reachValue && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity == null || targetVec == null) {
            return null;
        }
        return new Object[] { entity, targetVec };
    }
    
    @EventTarget
    public void onTick(final EventTick e) {
        this.setSuffix(String.format("Min:%s Max:%s", ((Value)Reach.MinReach).getValue(), ((Value)Reach.MaxReach).getValue()));
    }
    
    @EventTarget
    public void onClicked(final EventClick ev) {
        if (this.weaponOnly.getValue()) {
            if (Reach.mc.thePlayer.getCurrentEquippedItem() == null) {
                return;
            }
            if (!(Reach.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Reach.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                return;
            }
        }
        if (this.movingOnly.getValue() && Reach.mc.thePlayer.moveForward == 0.0 && Reach.mc.thePlayer.moveStrafing == 0.0) {
            return;
        }
        if (this.sprintOnly.getValue() && !Reach.mc.thePlayer.isSprinting()) {
            return;
        }
        if (!this.hitThroughBlocks.getValue() && Reach.mc.objectMouseOver != null) {
            final BlockPos blocksReach = Reach.mc.objectMouseOver.getBlockPos();
            if (blocksReach != null && Reach.mc.theWorld.getBlockState(blocksReach).getBlock() != Blocks.air) {
                return;
            }
        }
        double Reach;
        if (this.RandomReach.getValue()) {
            Reach = getRandomDoubleInRange(shop.xiaoda.module.modules.combat.Reach.MinReach.getValue(), shop.xiaoda.module.modules.combat.Reach.MaxReach.getValue()) + 0.1;
        }
        else {
            Reach = shop.xiaoda.module.modules.combat.Reach.MinReach.getValue();
        }
        final Object[] reach = doReach(Reach, 0.0);
        doReach(Reach, 0.0);
        if (reach == null) {
            return;
        }
        shop.xiaoda.module.modules.combat.Reach.mc.objectMouseOver = new MovingObjectPosition((Entity)reach[0], (Vec3)reach[1]);
        shop.xiaoda.module.modules.combat.Reach.mc.pointedEntity = (Entity)reach[0];
    }
    
    static {
        Reach.MinReach = new NumberValue("Min", 3.5, 0.0, 6.0, 0.1);
        Reach.MaxReach = new NumberValue("Max", 6.0, 0.1, 6.0, 0.1);
    }
}

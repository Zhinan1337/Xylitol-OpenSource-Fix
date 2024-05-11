package shop.xiaoda.utils;

import org.lwjgl.compatibility.util.vector.*;
import shop.xiaoda.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.util.*;

public final class RayCastUtil
{
    public static MovingObjectPosition rayCast(final Vector2f rotation, final double range) {
        return rayCast(rotation, range, 0.0f);
    }
    
    public static MovingObjectPosition rayCast(final Vector2f rotation, final double range, final float expand) {
        return rayCast(rotation, range, expand, (Entity)Client.mc.thePlayer);
    }
    
    public static MovingObjectPosition rayCast(final Vector2f rotation, final double range, final float expand, final Entity entity) {
        final float partialTicks = Client.mc.timer.renderPartialTicks;
        if (entity != null && Client.mc.theWorld != null) {
            MovingObjectPosition objectMouseOver = entity.rayTraceCustom(range, rotation.x, rotation.y);
            double d1 = range;
            final Vec3 vec3 = entity.getPositionEyes(partialTicks);
            if (objectMouseOver != null) {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }
            final Vec3 vec4 = Client.mc.thePlayer.getVectorForRotationPublic(rotation.y, rotation.x);
            final Vec3 vec5 = vec3.addVector(vec4.xCoord * range, vec4.yCoord * range, vec4.zCoord * range);
            Entity pointedEntity = null;
            Vec3 vec6 = null;
            final float f = 1.0f;
            final List<Entity> list = (List<Entity>)Client.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec4.xCoord * range, vec4.yCoord * range, vec4.zCoord * range).expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;
            for (final Entity entity2 : list) {
                final float f2 = entity2.getCollisionBorderSize() + expand;
                final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 < 0.0) {
                        continue;
                    }
                    pointedEntity = entity2;
                    vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                    d2 = 0.0;
                }
                else {
                    if (movingobjectposition == null) {
                        continue;
                    }
                    final double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 >= d2 && d2 != 0.0) {
                        continue;
                    }
                    pointedEntity = entity2;
                    vec6 = movingobjectposition.hitVec;
                    d2 = d3;
                }
            }
            if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
                objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
            }
            return objectMouseOver;
        }
        return null;
    }
    
    public static boolean overBlock(final Vector2f rotation, final EnumFacing enumFacing, final BlockPos pos, final boolean strict) {
        final MovingObjectPosition movingObjectPosition = Client.mc.thePlayer.rayTraceCustom(4.5, rotation.x, rotation.y);
        if (movingObjectPosition == null) {
            return false;
        }
        final Vec3 hitVec = movingObjectPosition.hitVec;
        return hitVec != null && movingObjectPosition.getBlockPos().equals(pos) && (!strict || movingObjectPosition.sideHit == enumFacing);
    }
    
    public static boolean overBlock(final EnumFacing enumFacing, final BlockPos pos, final boolean strict) {
        final MovingObjectPosition movingObjectPosition = Client.mc.objectMouseOver;
        if (movingObjectPosition == null) {
            return false;
        }
        final Vec3 hitVec = movingObjectPosition.hitVec;
        return hitVec != null && movingObjectPosition.getBlockPos().equals(pos) && (!strict || movingObjectPosition.sideHit == enumFacing);
    }
    
    public static Boolean overBlock(final Vector2f rotation, final BlockPos pos) {
        return overBlock(rotation, EnumFacing.UP, pos, false);
    }
    
    public static Boolean overBlock(final Vector2f rotation, final BlockPos pos, final EnumFacing enumFacing) {
        return overBlock(rotation, enumFacing, pos, true);
    }
}

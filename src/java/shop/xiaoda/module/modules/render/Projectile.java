//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;
import net.minecraft.item.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.render.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import org.lwjgl.compatibility.util.glu.*;
import java.util.*;
import java.util.List;

import net.minecraft.util.*;

public class Projectile extends Module
{
    float yaw;
    float pitch;
    
    public Projectile() {
        super("Projectile", Category.Render);
    }
    
    @EventTarget
    public void onMotion(final EventMotion e) {
        if (e.isPost()) {
            return;
        }
        this.yaw = e.getYaw();
        this.pitch = e.getPitch();
    }
    
    @EventTarget
    public void onR3D(final EventRender3D e) {
        boolean isBow = false;
        float pitchDifference = 0.0f;
        float motionFactor = 1.5f;
        float motionSlowdown = 0.99f;
        if (Projectile.mc.thePlayer.getCurrentEquippedItem() != null) {
            final Item heldItem = Projectile.mc.thePlayer.getCurrentEquippedItem().getItem();
            float gravity;
            float size;
            if (heldItem instanceof ItemBow) {
                isBow = true;
                gravity = 0.05f;
                size = 0.3f;
                float power = Projectile.mc.thePlayer.getItemInUseDuration() / 20.0f;
                power = (power * power + power * 2.0f) / 3.0f;
                if (power < 0.1) {
                    return;
                }
                if (power > 1.0f) {
                    power = 1.0f;
                }
                motionFactor = power * 3.0f;
            }
            else if (heldItem instanceof ItemFishingRod) {
                gravity = 0.04f;
                size = 0.25f;
                motionSlowdown = 0.92f;
            }
            else if (ItemPotion.isSplash(Projectile.mc.thePlayer.getCurrentEquippedItem().getMetadata())) {
                gravity = 0.05f;
                size = 0.25f;
                pitchDifference = -20.0f;
                motionFactor = 0.5f;
            }
            else {
                if (!(heldItem instanceof ItemSnowball) && !(heldItem instanceof ItemEnderPearl) && !(heldItem instanceof ItemEgg) && !heldItem.equals(Item.getItemById(46))) {
                    return;
                }
                gravity = 0.03f;
                size = 0.25f;
            }
            double posX = Projectile.mc.getRenderManager().renderPosX - MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * 0.16f;
            double posY = Projectile.mc.getRenderManager().renderPosY + Projectile.mc.thePlayer.getEyeHeight() - 0.10000000149011612;
            double posZ = Projectile.mc.getRenderManager().renderPosZ - MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * 0.16f;
            double motionX = -MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * (isBow ? 1.0 : 0.4);
            double motionY = -MathHelper.sin((this.pitch + pitchDifference) / 180.0f * 3.1415927f) * (isBow ? 1.0 : 0.4);
            double motionZ = MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * (isBow ? 1.0 : 0.4);
            final float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            motionX /= distance;
            motionY /= distance;
            motionZ /= distance;
            motionX *= motionFactor;
            motionY *= motionFactor;
            motionZ *= motionFactor;
            MovingObjectPosition landingPosition = null;
            boolean hasLanded = false;
            boolean hitEntity = false;
            RenderUtil.enableRender3D(true);
            RenderUtil.color(new Color(206, 89, 255, 255).getRGB());
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            while (!hasLanded && posY > 0.0) {
                Vec3 posBefore = new Vec3(posX, posY, posZ);
                Vec3 posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                landingPosition = Projectile.mc.theWorld.rayTraceBlocks(posBefore, posAfter, false, true, false);
                posBefore = new Vec3(posX, posY, posZ);
                posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                if (landingPosition != null) {
                    hasLanded = true;
                    posAfter = new Vec3(landingPosition.hitVec.xCoord, landingPosition.hitVec.yCoord, landingPosition.hitVec.zCoord);
                }
                final AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
                final List entityList = this.getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
                for (int i = 0; i < entityList.size(); ++i) {
                    final Entity var18 = (Entity) entityList.get(i);
                    if (var18.canBeCollidedWith() && var18 != Projectile.mc.thePlayer) {
                        final AxisAlignedBB var19 = var18.getEntityBoundingBox().expand((double)size, (double)size, (double)size);
                        final MovingObjectPosition possibleEntityLanding = var19.calculateIntercept(posBefore, posAfter);
                        if (possibleEntityLanding != null) {
                            hitEntity = true;
                            hasLanded = true;
                            landingPosition = possibleEntityLanding;
                        }
                    }
                }
                posX += motionX;
                posY += motionY;
                posZ += motionZ;
                final BlockPos var20 = new BlockPos(posX, posY, posZ);
                final Block var21 = Projectile.mc.theWorld.getBlockState(var20).getBlock();
                if (var21.getMaterial() == Material.water) {
                    motionX *= 0.6;
                    motionY *= 0.6;
                    motionZ *= 0.6;
                }
                else {
                    motionX *= motionSlowdown;
                    motionY *= motionSlowdown;
                    motionZ *= motionSlowdown;
                }
                motionY -= gravity;
                GL11.glVertex3d(posX - Projectile.mc.getRenderManager().renderPosX, posY - Projectile.mc.getRenderManager().renderPosY, posZ - Projectile.mc.getRenderManager().renderPosZ);
            }
            GL11.glEnd();
            GL11.glPushMatrix();
            GL11.glTranslated(posX - Projectile.mc.getRenderManager().renderPosX, posY - Projectile.mc.getRenderManager().renderPosY, posZ - Projectile.mc.getRenderManager().renderPosZ);
            if (landingPosition != null) {
                final int side = landingPosition.sideHit.getIndex();
                if (side == 1 && heldItem instanceof ItemEnderPearl) {
                    RenderUtil.color(new Color(255, 248, 0, 255).getRGB());
                }
                else if (side == 2) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (side == 3) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (side == 4) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                else if (side == 5) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (hitEntity) {
                    RenderUtil.color(new Color(255, 248, 0, 255).getRGB());
                }
            }
            this.renderPoint();
            GL11.glPopMatrix();
            RenderUtil.disableRender3D(true);
        }
    }
    
    private void renderPoint() {
        GL11.glBegin(1);
        GL11.glVertex3d(-0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, -0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glEnd();
        final Cylinder c = new Cylinder();
        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.5f, 0.5f, 0.0f, 256, 27);
    }
    
    private List getEntitiesWithinAABB(final AxisAlignedBB axisalignedBB) {
        final ArrayList list = new ArrayList();
        final int chunkMinX = MathHelper.floor_double((axisalignedBB.minX - 2.0) / 16.0);
        final int chunkMaxX = MathHelper.floor_double((axisalignedBB.maxX + 2.0) / 16.0);
        final int chunkMinZ = MathHelper.floor_double((axisalignedBB.minZ - 2.0) / 16.0);
        final int chunkMaxZ = MathHelper.floor_double((axisalignedBB.maxZ + 2.0) / 16.0);
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (Projectile.mc.theWorld.getChunkProvider().chunkExists(x, z)) {
                    Projectile.mc.theWorld.getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity((Entity)Projectile.mc.thePlayer, axisalignedBB, (List)list, EntitySelectors.selectAnything);
                }
            }
        }
        return list;
    }
}

// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.module.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import shop.xiaoda.event.EventTarget;
import shop.xiaoda.event.rendering.EventRender3D;
import shop.xiaoda.module.Category;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.ColorValue;
import shop.xiaoda.utils.render.RenderUtil;

public class Skeletal
        extends Module {
    private static final Map<EntityPlayer, float[][]> entityModelRotations = new HashMap<EntityPlayer, float[][]>();
    public static ColorValue skeletonColor = new ColorValue("SkeletonColor", Color.WHITE.getRGB());

    public Skeletal() {
        super("Skeletal", Category.Render);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
        GL11.glDisable(2848);
        entityModelRotations.keySet().removeIf(player -> !Skeletal.mc.theWorld.playerEntities.contains(player));
        Skeletal.mc.theWorld.playerEntities.forEach(player -> {
            if (player == Skeletal.mc.thePlayer || player.isInvisible()) {
                return;
            }
            float[][] modelRotations = entityModelRotations.get(player);
            if (modelRotations == null) {
                return;
            }
            GL11.glPushMatrix();
            GL11.glLineWidth(1.0f);
            int c = (Integer)skeletonColor.getValue();
            RenderUtil.glColor(c);
            Vec3 interp = this.interpolateRender((EntityPlayer)player);
            double x = interp.xCoord - Minecraft.getMinecraft().getRenderManager().renderPosX;
            double y = interp.yCoord - Minecraft.getMinecraft().getRenderManager().renderPosY;
            double z = interp.zCoord - Minecraft.getMinecraft().getRenderManager().renderPosZ;
            GL11.glTranslated(x, y, z);
            float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * Skeletal.mc.timer.renderPartialTicks;
            GL11.glRotatef(-bodyYawOffset, 0.0f, 1.0f, 0.0f);
            GL11.glTranslated(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
            float legHeight = player.isSneaking() ? 0.6f : 0.75f;
            GL11.glPushMatrix();
            GL11.glTranslated(-0.125, legHeight, 0.0);
            if (modelRotations[3][0] != 0.0f) {
                GL11.glRotatef(modelRotations[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[3][1] != 0.0f) {
                GL11.glRotatef(modelRotations[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[3][2] != 0.0f) {
                GL11.glRotatef(modelRotations[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -legHeight, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.125, legHeight, 0.0);
            if (modelRotations[4][0] != 0.0f) {
                GL11.glRotatef(modelRotations[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[4][1] != 0.0f) {
                GL11.glRotatef(modelRotations[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[4][2] != 0.0f) {
                GL11.glRotatef(modelRotations[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -legHeight, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, player.isSneaking() ? -0.05 : 0.0, player.isSneaking() ? -0.01725 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(-0.375, (double)legHeight + 0.55, 0.0);
            if (modelRotations[1][0] != 0.0f) {
                GL11.glRotatef(modelRotations[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[1][1] != 0.0f) {
                GL11.glRotatef(modelRotations[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[1][2] != 0.0f) {
                GL11.glRotatef(-modelRotations[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375, (double)legHeight + 0.55, 0.0);
            if (modelRotations[2][0] != 0.0f) {
                GL11.glRotatef(modelRotations[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (modelRotations[2][1] != 0.0f) {
                GL11.glRotatef(modelRotations[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (modelRotations[2][2] != 0.0f) {
                GL11.glRotatef(-modelRotations[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)legHeight + 0.55, 0.0);
            if (modelRotations[0][0] != 0.0f) {
                GL11.glRotatef(modelRotations[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.3, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(0.0, player.isSneaking() ? -0.16175 : 0.0, player.isSneaking() ? -0.48025 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, legHeight, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125, 0.0, 0.0);
            GL11.glVertex3d(0.125, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, legHeight, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.55, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)legHeight + 0.55, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375, 0.0, 0.0);
            GL11.glVertex3d(0.375, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        });
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
    }

    public static void updateModel(EntityPlayer player, ModelPlayer model) {
        entityModelRotations.put(player, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    public Vec3 interpolateRender(EntityPlayer player) {
        float part = Skeletal.mc.timer.renderPartialTicks;
        double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)part;
        double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)part;
        double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)part;
        return new Vec3(interpX, interpY, interpZ);
    }
}

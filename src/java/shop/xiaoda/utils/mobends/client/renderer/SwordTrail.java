//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.renderer;

import java.util.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.client.*;
import shop.xiaoda.module.modules.render.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.model.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.client.model.*;

public class SwordTrail
{
    public List<TrailPart> trailPartList;
    
    public SwordTrail() {
        this.trailPartList = new ArrayList<TrailPart>();
    }
    
    public void reset() {
        this.trailPartList.clear();
    }
    
    public void render(final ModelBendsPlayer model) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(MoBendsMod.texture_NULL);
        GL11.glDepthFunc(515);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2884);
        GL11.glDisable(2896);
        GL11.glHint(3152, 4354);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        for (int i = 0; i < this.trailPartList.size(); ++i) {
            final TrailPart part = this.trailPartList.get(i);
            float alpha = part.ticksExisted / 5.0f;
            alpha = GUtil.max(alpha, 1.0f);
            alpha = 1.0f - alpha;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            final Vector3f[] point = { new Vector3f(0.0f, 0.0f, -8.0f + 8.0f * alpha), new Vector3f(0.0f, 0.0f, -8.0f - 8.0f * alpha) };
            GUtil.rotateX(point, part.itemRotation.getX());
            GUtil.rotateY(point, part.itemRotation.getY());
            GUtil.rotateZ(point, part.itemRotation.getZ());
            GUtil.translate(point, new Vector3f(-1.0f, -6.0f, 0.0f));
            GUtil.rotateX(point, part.foreArm.rotateAngleX / 3.1415927f * 180.0f);
            GUtil.rotateY(point, part.foreArm.rotateAngleY / 3.1415927f * 180.0f);
            GUtil.rotateZ(point, part.foreArm.rotateAngleZ / 3.1415927f * 180.0f);
            GUtil.rotateX(point, part.foreArm.pre_rotation.getX());
            GUtil.rotateY(point, part.foreArm.pre_rotation.getY());
            GUtil.rotateZ(point, -part.foreArm.pre_rotation.getZ());
            GUtil.translate(point, new Vector3f(0.0f, -4.0f, 0.0f));
            GUtil.rotateX(point, part.arm.rotateAngleX / 3.1415927f * 180.0f);
            GUtil.rotateY(point, part.arm.rotateAngleY / 3.1415927f * 180.0f);
            GUtil.rotateZ(point, part.arm.rotateAngleZ / 3.1415927f * 180.0f);
            GUtil.rotateX(point, part.arm.pre_rotation.getX());
            GUtil.rotateY(point, part.arm.pre_rotation.getY());
            GUtil.rotateZ(point, -part.arm.pre_rotation.getZ());
            GUtil.translate(point, new Vector3f(-5.0f, 10.0f, 0.0f));
            GUtil.rotateX(point, part.body.rotateAngleX / 3.1415927f * 180.0f);
            GUtil.rotateY(point, part.body.rotateAngleY / 3.1415927f * 180.0f);
            GUtil.rotateZ(point, part.body.rotateAngleZ / 3.1415927f * 180.0f);
            GUtil.rotateX(point, part.body.pre_rotation.getX());
            GUtil.rotateY(point, part.body.pre_rotation.getY());
            GUtil.rotateZ(point, part.body.pre_rotation.getZ());
            GUtil.translate(point, new Vector3f(0.0f, 12.0f, 0.0f));
            GUtil.rotateX(point, part.renderRotation.getX());
            GUtil.rotateY(point, part.renderRotation.getY());
            GUtil.translate(point, part.renderOffset);
            if (i > 0) {
                GL11.glVertex3f(point[1].x, point[1].y, point[1].z);
                GL11.glVertex3f(point[0].x, point[0].y, point[0].z);
                GL11.glVertex3f(point[0].x, point[0].y, point[0].z);
                GL11.glVertex3f(point[1].x, point[1].y, point[1].z);
            }
            else {
                GL11.glVertex3f(point[0].x, point[0].y, point[0].z);
                GL11.glVertex3f(point[1].x, point[1].y, point[1].z);
            }
            if (i == this.trailPartList.size() - 1) {
                GL11.glVertex3f(point[1].x, point[1].y, point[1].z);
                GL11.glVertex3f(point[0].x, point[0].y, point[0].z);
            }
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(2896);
    }
    
    public void add(final ModelBendsPlayer argModel) {
        final TrailPart newPart = new TrailPart(argModel);
        newPart.body.sync((ModelRendererBends)argModel.bipedBody);
        newPart.body.setPosition(argModel.bipedBody.rotationPointX, argModel.bipedBody.rotationPointY, argModel.bipedBody.rotationPointZ);
        newPart.body.setOffset(argModel.bipedBody.offsetX, argModel.bipedBody.offsetY, argModel.bipedBody.offsetZ);
        newPart.arm.sync((ModelRendererBends)argModel.bipedRightArm);
        newPart.arm.setPosition(argModel.bipedRightArm.rotationPointX, argModel.bipedRightArm.rotationPointY, argModel.bipedRightArm.rotationPointZ);
        newPart.arm.setOffset(argModel.bipedRightArm.offsetX, argModel.bipedRightArm.offsetY, argModel.bipedRightArm.offsetZ);
        newPart.foreArm.sync(argModel.bipedRightForeArm);
        newPart.foreArm.setPosition(argModel.bipedRightForeArm.rotationPointX, argModel.bipedRightForeArm.rotationPointY, argModel.bipedRightForeArm.rotationPointZ);
        newPart.foreArm.setOffset(argModel.bipedRightForeArm.offsetX, argModel.bipedRightForeArm.offsetY, argModel.bipedRightForeArm.offsetZ);
        newPart.renderOffset.set((ReadableVector3f)argModel.renderOffset.vSmooth);
        newPart.renderRotation.set((ReadableVector3f)argModel.renderRotation.vSmooth);
        newPart.itemRotation.set((ReadableVector3f)argModel.renderItemRotation.vSmooth);
        this.trailPartList.add(newPart);
    }
    
    public void update(final float argPartialTicks) {
        for (int i = 0; i < this.trailPartList.size(); ++i) {
            final TrailPart trailPart = this.trailPartList.get(i);
            trailPart.ticksExisted += argPartialTicks;
        }
        for (int i = 0; i < this.trailPartList.size(); ++i) {
            if (this.trailPartList.get(i).ticksExisted > 20.0f) {
                this.trailPartList.remove(this.trailPartList.get(i));
            }
        }
    }
    
    public class TrailPart
    {
        public ModelRendererBends body;
        public ModelRendererBends arm;
        public ModelRendererBends foreArm;
        public Vector3f renderRotation;
        public Vector3f renderOffset;
        public Vector3f itemRotation;
        float ticksExisted;
        
        public TrailPart(final ModelBendsPlayer argModel) {
            this.renderRotation = new Vector3f();
            this.renderOffset = new Vector3f();
            this.itemRotation = new Vector3f();
            this.body = new ModelRendererBends((ModelBase)argModel);
            this.arm = new ModelRendererBends((ModelBase)argModel);
            this.foreArm = new ModelRendererBends((ModelBase)argModel);
        }
    }
}

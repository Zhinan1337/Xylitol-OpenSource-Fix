//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model.entity;

import shop.xiaoda.utils.mobends.util.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import org.lwjgl.compatibility.util.vector.*;
import shop.xiaoda.utils.mobends.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.utils.mobends.pack.*;
import net.minecraft.client.entity.*;

public class ModelBendsZombie extends ModelBiped
{
    public ModelRenderer bipedRightForeArm;
    public ModelRenderer bipedLeftForeArm;
    public ModelRenderer bipedRightForeLeg;
    public ModelRenderer bipedLeftForeLeg;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public float headRotationX;
    public float headRotationY;
    public float armSwing;
    public float armSwingAmount;
    
    public ModelBendsZombie() {
        this(0.0f, false);
    }
    
    public ModelBendsZombie(final float modelSize, final boolean p_i1168_2_) {
        this(modelSize, 0.0f, 64, p_i1168_2_ ? 32 : 64);
    }
    
    protected ModelBendsZombie(final float modelSize, final float p_i1167_2_, final int textureWidthIn, final int textureHeightIn) {
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        (this.bipedHead = new ModelRendererBends((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + p_i1167_2_ - 12.0f, 0.0f);
        (this.bipedHeadwear = new ModelRendererBends((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRendererBends((ModelBase)this, 16, 16).setShowChildIfHidden(true)).addBox(-4.0f, -12.0f, -2.0f, 8, 12, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + p_i1167_2_ + 12.0f, 0.0f);
        (this.bipedRightArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody)).addBox(-3.0f, -2.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + p_i1167_2_ - 12.0f, 0.0f);
        this.bipedLeftArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + p_i1167_2_ - 12.0f, 0.0f);
        (this.bipedRightLeg = new ModelRendererBends((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + p_i1167_2_, 0.0f);
        this.bipedLeftLeg = new ModelRendererBends((ModelBase)this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + p_i1167_2_, 0.0f);
        (this.bipedRightForeArm = new ModelRendererBends((ModelBase)this, 40, 22)).addBox(0.0f, 0.0f, -4.0f, 4, 6, 4, modelSize);
        this.bipedRightForeArm.setRotationPoint(-3.0f, 4.0f, 2.0f);
        ((ModelRendererBends)this.bipedRightForeArm).getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0f, -6.0f);
        this.bipedLeftForeArm = new ModelRendererBends((ModelBase)this, 40, 22);
        this.bipedLeftForeArm.mirror = true;
        this.bipedLeftForeArm.addBox(0.0f, 0.0f, -4.0f, 4, 6, 4, modelSize);
        this.bipedLeftForeArm.setRotationPoint(-1.0f, 4.0f, 2.0f);
        ((ModelRendererBends)this.bipedLeftForeArm).getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0f, -6.0f);
        (this.bipedRightForeLeg = new ModelRendererBends((ModelBase)this, 0, 22)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, modelSize);
        this.bipedRightForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        ((ModelRendererBends)this.bipedRightForeLeg).getBox().offsetTextureQuad(this.bipedRightForeLeg, 3, 0.0f, -6.0f);
        this.bipedLeftForeLeg = new ModelRendererBends((ModelBase)this, 0, 22);
        this.bipedLeftForeLeg.mirror = true;
        this.bipedLeftForeLeg.addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, modelSize);
        this.bipedLeftForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        ((ModelRendererBends)this.bipedLeftForeLeg).getBox().offsetTextureQuad(this.bipedLeftForeLeg, 3, 0.0f, -6.0f);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedHead.addChild(this.bipedHeadwear);
        this.bipedRightArm.addChild(this.bipedRightForeArm);
        this.bipedLeftArm.addChild(this.bipedLeftForeArm);
        this.bipedRightLeg.addChild(this.bipedRightForeLeg);
        this.bipedLeftLeg.addChild(this.bipedLeftForeLeg);
        ((ModelRendererBends_SeperatedChild)this.bipedRightArm).setSeperatedPart((ModelRendererBends)this.bipedRightForeArm);
        ((ModelRendererBends_SeperatedChild)this.bipedLeftArm).setSeperatedPart((ModelRendererBends)this.bipedLeftForeArm);
        ((ModelRendererBends)this.bipedRightArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        ((ModelRendererBends)this.bipedLeftArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        ((ModelRendererBends)this.bipedRightLeg).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        ((ModelRendererBends)this.bipedLeftLeg).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
    }
    
    public void render(final Entity argEntity, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, argEntity);
        if (this.isChild) {
            final float f6 = 2.0f;
            final ModelRendererBends modelRendererBends = (ModelRendererBends)this.bipedHead;
            modelRendererBends.scaleX *= 1.5f;
            final ModelRendererBends modelRendererBends2 = (ModelRendererBends)this.bipedHead;
            modelRendererBends2.scaleY *= 1.5f;
            final ModelRendererBends modelRendererBends3 = (ModelRendererBends)this.bipedHead;
            modelRendererBends3.scaleZ *= 1.5f;
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * scale, 0.0f);
            this.bipedBody.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            GL11.glPopMatrix();
        }
        else {
            this.bipedBody.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
        }
    }
    
    public void setRotationAngles(final float argSwingTime, final float argSwingAmount, final float argArmSway, final float argHeadY, final float argHeadX, final float argNr6, final Entity argEntity) {
        if (Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        final Data_Zombie data = Data_Zombie.get(argEntity.getEntityId());
        this.armSwing = argSwingTime;
        this.armSwingAmount = argSwingAmount;
        this.headRotationX = argHeadX;
        this.headRotationY = argHeadY;
        ((ModelRendererBends)this.bipedHead).sync(data.head);
        ((ModelRendererBends)this.bipedHeadwear).sync(data.headwear);
        ((ModelRendererBends)this.bipedBody).sync(data.body);
        ((ModelRendererBends)this.bipedRightArm).sync(data.rightArm);
        ((ModelRendererBends)this.bipedLeftArm).sync(data.leftArm);
        ((ModelRendererBends)this.bipedRightLeg).sync(data.rightLeg);
        ((ModelRendererBends)this.bipedLeftLeg).sync(data.leftLeg);
        ((ModelRendererBends)this.bipedRightForeArm).sync(data.rightForeArm);
        ((ModelRendererBends)this.bipedLeftForeArm).sync(data.leftForeArm);
        ((ModelRendererBends)this.bipedRightForeLeg).sync(data.rightForeLeg);
        ((ModelRendererBends)this.bipedLeftForeLeg).sync(data.leftForeLeg);
        this.renderOffset.set(data.renderOffset);
        this.renderRotation.set(data.renderRotation);
        if (Data_Zombie.get(argEntity.getEntityId()).canBeUpdated()) {
            this.renderOffset.setSmooth(new Vector3f(0.0f, -1.0f, 0.0f), 0.5f);
            this.renderRotation.setSmooth(new Vector3f(0.0f, 0.0f, 0.0f), 0.5f);
            ((ModelRendererBends)this.bipedHead).resetScale();
            ((ModelRendererBends)this.bipedHeadwear).resetScale();
            ((ModelRendererBends)this.bipedBody).resetScale();
            ((ModelRendererBends)this.bipedRightArm).resetScale();
            ((ModelRendererBends)this.bipedLeftArm).resetScale();
            ((ModelRendererBends)this.bipedRightLeg).resetScale();
            ((ModelRendererBends)this.bipedLeftLeg).resetScale();
            ((ModelRendererBends)this.bipedRightForeArm).resetScale();
            ((ModelRendererBends)this.bipedLeftForeArm).resetScale();
            ((ModelRendererBends)this.bipedRightForeLeg).resetScale();
            ((ModelRendererBends)this.bipedLeftForeLeg).resetScale();
            BendsVar.tempData = Data_Zombie.get(argEntity.getEntityId());
            if (Data_Zombie.get(argEntity.getEntityId()).motion.x == 0.0f & Data_Zombie.get(argEntity.getEntityId()).motion.z == 0.0f) {
                AnimatedEntity.getByEntity(argEntity).get("stand").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Zombie.get(argEntity.getEntityId()));
                BendsPack.animate(this, "zombie", "stand");
            }
            else {
                AnimatedEntity.getByEntity(argEntity).get("walk").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Zombie.get(argEntity.getEntityId()));
                BendsPack.animate(this, "zombie", "walk");
            }
            ((ModelRendererBends)this.bipedHead).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedHeadwear).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedBody).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftLeg).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightLeg).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftForeArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightForeArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftForeLeg).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightForeLeg).update(data.ticksPerFrame);
            this.renderOffset.update(data.ticksPerFrame);
            this.renderRotation.update(data.ticksPerFrame);
            data.updatedThisFrame = true;
        }
        Data_Zombie.get(argEntity.getEntityId()).syncModelInfo(this);
    }
    
    public void postRender(final float argScale) {
        GL11.glTranslatef(this.renderOffset.vSmooth.x * argScale, this.renderOffset.vSmooth.y * argScale, this.renderOffset.vSmooth.z * argScale);
        GL11.glRotatef(-this.renderRotation.getX(), 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(-this.renderRotation.getY(), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.renderRotation.getZ(), 0.0f, 0.0f, 1.0f);
    }
    
    public void postRenderArm(final float argScale) {
        this.bipedRightArm.postRender(argScale);
        this.bipedRightForeArm.postRender(argScale);
        GL11.glTranslatef(0.0f * argScale, 4.0f * argScale, -2.0f * argScale);
        GL11.glTranslatef(0.0f * argScale, -8.0f * argScale, 0.0f * argScale);
    }
    
    public void updateWithEntityData(final AbstractClientPlayer argPlayer) {
        final Data_Zombie data = Data_Zombie.get(argPlayer.getEntityId());
        if (data != null) {
            this.renderOffset.set(data.renderOffset);
            this.renderRotation.set(data.renderRotation);
        }
    }
}

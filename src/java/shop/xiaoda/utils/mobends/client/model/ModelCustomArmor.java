//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model;

import shop.xiaoda.utils.mobends.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;

public class ModelCustomArmor extends ModelBiped
{
    public ModelRenderer bipedRightForeArm;
    public ModelRenderer bipedLeftForeArm;
    public ModelRenderer bipedRightForeLeg;
    public ModelRenderer bipedLeftForeLeg;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public SmoothVector3f renderItemRotation;
    public float headRotationX;
    public float headRotationY;
    public float armSwing;
    public float armSwingAmount;
    
    public ModelCustomArmor() {
        this(0.0f);
    }
    
    public ModelCustomArmor(final float modelSize) {
        this(modelSize, 0.0f, 64, 32);
    }
    
    public ModelCustomArmor(final float modelSize, final float p_i1149_2_, final int textureWidthIn, final int textureHeightIn) {
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.renderItemRotation = new SmoothVector3f();
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        (this.bipedHead = new ModelRendererBends((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + p_i1149_2_ - 12.0f, 0.0f);
        (this.bipedHeadwear = new ModelRendererBends((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, modelSize + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRendererBends((ModelBase)this, 16, 16).setShowChildIfHidden(true)).addBox(-4.0f, -12.0f, -2.0f, 8, 12, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + p_i1149_2_ + 12.0f, 0.0f);
        (this.bipedRightArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody)).addBox(-3.0f, -2.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + p_i1149_2_ - 12.0f, 0.0f);
        this.bipedLeftArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + p_i1149_2_ - 12.0f, 0.0f);
        (this.bipedRightLeg = new ModelRendererBends((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + p_i1149_2_, 0.0f);
        this.bipedLeftLeg = new ModelRendererBends((ModelBase)this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + p_i1149_2_, 0.0f);
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
    }
    
    public void render(final Entity argEntity, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, argEntity);
        if (this.isChild) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * scale, 0.0f);
            this.bipedHead.render(scale);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * scale, 0.0f);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
            GL11.glPopMatrix();
        }
        else {
            this.bipedBody.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
        }
    }
    
    public void setRotationAngles(final float argSwingTime, final float argSwingAmount, final float argArmSway, final float argHeadY, final float argHeadX, final float argNr6, final Entity argEntity) {
    }
    
    public void updateWithModelData(final ModelBendsPlayer argModel) {
        if (argModel == null) {
            return;
        }
        ((ModelRendererBends)this.bipedHead).sync((ModelRendererBends)argModel.bipedHead);
        ((ModelRendererBends)this.bipedHeadwear).sync((ModelRendererBends)argModel.bipedHeadwear);
        ((ModelRendererBends)this.bipedBody).sync((ModelRendererBends)argModel.bipedBody);
        ((ModelRendererBends)this.bipedLeftArm).sync((ModelRendererBends)argModel.bipedLeftArm);
        ((ModelRendererBends)this.bipedLeftForeArm).sync(argModel.bipedLeftForeArm);
        ((ModelRendererBends)this.bipedLeftForeLeg).sync(argModel.bipedLeftForeLeg);
        ((ModelRendererBends)this.bipedLeftLeg).sync((ModelRendererBends)argModel.bipedLeftLeg);
        ((ModelRendererBends)this.bipedRightArm).sync((ModelRendererBends)argModel.bipedRightArm);
        ((ModelRendererBends)this.bipedRightForeArm).sync(argModel.bipedRightForeArm);
        ((ModelRendererBends)this.bipedRightForeLeg).sync(argModel.bipedRightForeLeg);
        ((ModelRendererBends)this.bipedRightLeg).sync((ModelRendererBends)argModel.bipedRightLeg);
    }
}

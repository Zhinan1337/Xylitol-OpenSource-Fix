//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model.entity;

import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.renderer.*;
import shop.xiaoda.utils.mobends.client.model.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;

public class ModelBendsPlayerArmor extends ModelBiped
{
    public ModelRendererBends bipedRightForeArm;
    public ModelRendererBends bipedLeftForeArm;
    public ModelRendererBends bipedRightForeLeg;
    public ModelRendererBends bipedLeftForeLeg;
    public ModelRendererBends bipedCloak;
    public ModelRendererBends bipedEars;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public SmoothVector3f renderItemRotation;
    public SwordTrail swordTrail;
    public float headRotationX;
    public float headRotationY;
    public float armSwing;
    public float armSwingAmount;
    
    public ModelBendsPlayerArmor(final float p_i46304_1_) {
        super(p_i46304_1_);
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.renderItemRotation = new SmoothVector3f();
        this.swordTrail = new SwordTrail();
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.bipedEars = new ModelRendererBends((ModelBase)this, 24, 0)).addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, p_i46304_1_);
        (this.bipedCloak = new ModelRendererBends((ModelBase)this, 0, 0)).setTextureSize(64, 32);
        this.bipedCloak.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, p_i46304_1_);
        (this.bipedHead = new ModelRendererBends((ModelBase)this, 0, 0).setShowChildIfHidden(true)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46304_1_);
        this.bipedHead.setRotationPoint(0.0f, -12.0f, 0.0f);
        (this.bipedHeadwear = new ModelRendererBends((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46304_1_ + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRendererBends((ModelBase)this, 16, 16)).addBox(-4.0f, -12.0f, -2.0f, 8, 12, 4, p_i46304_1_);
        this.bipedBody.setRotationPoint(0.0f, 12.0f, 0.0f);
        (this.bipedLeftArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-1.0f, -2.0f, -2.0f, 4, 6, 4, p_i46304_1_);
        this.bipedLeftArm.setRotationPoint(5.0f, -10.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        (this.bipedRightArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-3.0f, -2.0f, -2.0f, 4, 6, 4, p_i46304_1_);
        this.bipedRightArm.setRotationPoint(-5.0f, -10.0f, 0.0f);
        ((ModelRendererBends)this.bipedRightArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        ((ModelRendererBends)this.bipedLeftArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        (this.bipedLeftForeArm = new ModelRendererBends((ModelBase)this, 40, 22)).addBox(-1.0f, 0.0f, -4.0f, 4, 6, 4, p_i46304_1_);
        this.bipedLeftForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
        this.bipedLeftForeArm.mirror = true;
        this.bipedLeftForeArm.getBox().offsetTextureQuad(this.bipedLeftForeArm, 3, 0.0f, -6.0f);
        (this.bipedRightForeArm = new ModelRendererBends((ModelBase)this, 40, 22)).addBox(-3.0f, 0.0f, -4.0f, 4, 6, 4, p_i46304_1_);
        this.bipedRightForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
        this.bipedRightForeArm.getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0f, -6.0f);
        (this.bipedRightLeg = new ModelRendererBends((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46304_1_);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRendererBends((ModelBase)this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46304_1_);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedRightForeLeg = new ModelRendererBends((ModelBase)this, 0, 22)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, p_i46304_1_);
        this.bipedRightForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        this.bipedRightForeLeg.getBox().offsetTextureQuad(this.bipedRightForeLeg, 3, 0.0f, -6.0f);
        this.bipedLeftForeLeg = new ModelRendererBends((ModelBase)this, 0, 22);
        this.bipedLeftForeLeg.mirror = true;
        this.bipedLeftForeLeg.addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, p_i46304_1_);
        this.bipedLeftForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        this.bipedLeftForeLeg.getBox().offsetTextureQuad(this.bipedLeftForeLeg, 3, 0.0f, -6.0f);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedHead.addChild(this.bipedHeadwear);
        this.bipedRightArm.addChild((ModelRenderer)this.bipedRightForeArm);
        this.bipedLeftArm.addChild((ModelRenderer)this.bipedLeftForeArm);
        this.bipedRightLeg.addChild((ModelRenderer)this.bipedRightForeLeg);
        this.bipedLeftLeg.addChild((ModelRenderer)this.bipedLeftForeLeg);
        ((ModelRendererBends_SeperatedChild)this.bipedRightArm).setSeperatedPart(this.bipedRightForeArm);
        ((ModelRendererBends_SeperatedChild)this.bipedLeftArm).setSeperatedPart(this.bipedLeftForeArm);
        ((ModelRendererBends)this.bipedRightLeg).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
        ((ModelRendererBends)this.bipedLeftLeg).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
    }
    
    public void render(final Entity argEntity, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, argEntity);
        GL11.glPushMatrix();
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
            GL11.glPushMatrix();
            this.bipedBody.postRender(scale);
            this.bipedHead.render(scale);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
    
    public void setRotationAngles(final float argSwingTime, final float argSwingAmount, final float argArmSway, final float argHeadY, final float argHeadX, final float argNr6, final Entity argEntity) {
        if (Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        if (Minecraft.getMinecraft().theWorld.isRemote && Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        final Data_Player data = Data_Player.get(argEntity.getEntityId());
        this.armSwing = argSwingTime;
        this.armSwingAmount = argSwingAmount;
        this.headRotationX = argHeadX;
        this.headRotationY = argHeadY;
        if (Minecraft.getMinecraft().currentScreen != null) {
            this.headRotationY = 0.0f;
        }
        ((ModelRendererBends)this.bipedHead).sync(data.head);
        ((ModelRendererBends)this.bipedHeadwear).sync(data.headwear);
        ((ModelRendererBends)this.bipedBody).sync(data.body);
        ((ModelRendererBends)this.bipedRightArm).sync(data.rightArm);
        ((ModelRendererBends)this.bipedLeftArm).sync(data.leftArm);
        ((ModelRendererBends)this.bipedRightLeg).sync(data.rightLeg);
        ((ModelRendererBends)this.bipedLeftLeg).sync(data.leftLeg);
        this.bipedRightForeArm.sync(data.rightForeArm);
        this.bipedLeftForeArm.sync(data.leftForeArm);
        this.bipedRightForeLeg.sync(data.rightForeLeg);
        this.bipedLeftForeLeg.sync(data.leftForeLeg);
        this.renderOffset.set(data.renderOffset);
        this.renderRotation.set(data.renderRotation);
        this.renderItemRotation.set(data.renderItemRotation);
        this.swordTrail = data.swordTrail;
    }
    
    public void postRender(final float argScale) {
        GlStateManager.translate(this.renderOffset.vSmooth.x * argScale, -this.renderOffset.vSmooth.y * argScale, this.renderOffset.vSmooth.z * argScale);
        GlStateManager.rotate(this.renderRotation.getX(), 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(this.renderRotation.getY(), 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderRotation.getZ(), 0.0f, 0.0f, 1.0f);
    }
    
    public void postRenderTranslate(final float argScale) {
        GlStateManager.translate(this.renderOffset.vSmooth.x * argScale, -this.renderOffset.vSmooth.y * argScale, this.renderOffset.vSmooth.z * argScale);
    }
    
    public void postRenderRotate(final float argScale) {
        GlStateManager.rotate(this.renderRotation.getX(), 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(this.renderRotation.getY(), 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderRotation.getZ(), 0.0f, 0.0f, 1.0f);
    }
    
    public void updateWithEntityData(final AbstractClientPlayer argPlayer) {
        final Data_Player data = Data_Player.get(argPlayer.getEntityId());
        if (data != null) {
            this.renderOffset.set(data.renderOffset);
            this.renderRotation.set(data.renderRotation);
            this.renderItemRotation.set(data.renderItemRotation);
        }
    }
}

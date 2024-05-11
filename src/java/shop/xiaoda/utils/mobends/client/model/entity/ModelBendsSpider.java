//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model.entity;

import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.entity.monster.*;
import shop.xiaoda.utils.mobends.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.mobends.pack.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.client.renderer.*;

public class ModelBendsSpider extends ModelSpider
{
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;
    public ModelRendererBends spiderForeLeg1;
    public ModelRendererBends spiderForeLeg2;
    public ModelRendererBends spiderForeLeg3;
    public ModelRendererBends spiderForeLeg4;
    public ModelRendererBends spiderForeLeg5;
    public ModelRendererBends spiderForeLeg6;
    public ModelRendererBends spiderForeLeg7;
    public ModelRendererBends spiderForeLeg8;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public float headRotationX;
    public float headRotationY;
    public float armSwing;
    public float armSwingAmount;
    
    public ModelBendsSpider() {
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        final float f = 0.0f;
        final byte b0 = 15;
        final float legLength = 12.0f;
        final float foreLegLength = 15.0f;
        final float legRatio = 1.0f;
        final float foreLegRatio = 1.0f;
        (this.spiderHead = new ModelRendererBends((ModelBase)this, 32, 4)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f);
        this.spiderHead.setRotationPoint(0.0f, (float)b0, -3.0f);
        (this.spiderNeck = new ModelRendererBends((ModelBase)this, 0, 0)).addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, f);
        this.spiderNeck.setRotationPoint(0.0f, (float)b0, 0.0f);
        (this.spiderBody = new ModelRendererBends((ModelBase)this, 0, 12)).addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, f);
        this.spiderBody.setRotationPoint(0.0f, (float)b0, 9.0f);
        (this.spiderLeg1 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-7.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg1.setRotationPoint(-4.0f, (float)b0, 2.0f);
        ((ModelRendererBends)this.spiderLeg1).offsetBox_Add(8.0f - legLength, -0.01f, -0.01f).resizeBox(legLength, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg2 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg2.setRotationPoint(4.0f, (float)b0, 2.0f);
        ((ModelRendererBends)this.spiderLeg2).offsetBox_Add(0.0f, -0.01f, -0.01f).resizeBox(legLength, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg3 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-7.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg3.setRotationPoint(-4.0f, (float)b0, 1.0f);
        ((ModelRendererBends)this.spiderLeg3).offsetBox_Add(8.0f - legLength * legRatio, -0.01f, -0.01f).resizeBox(legLength * legRatio, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg4 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg4.setRotationPoint(4.0f, (float)b0, 1.0f);
        ((ModelRendererBends)this.spiderLeg4).offsetBox_Add(0.0f, -0.01f, -0.01f).resizeBox(legLength * legRatio, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg5 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-7.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg5.setRotationPoint(-4.0f, (float)b0, 0.0f);
        ((ModelRendererBends)this.spiderLeg5).offsetBox_Add(8.0f - legLength * legRatio, -0.01f, -0.01f).resizeBox(legLength * legRatio, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg6 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg6.setRotationPoint(4.0f, (float)b0, 0.0f);
        ((ModelRendererBends)this.spiderLeg6).offsetBox_Add(0.0f, -0.01f, -0.01f).resizeBox(legLength * legRatio, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg7 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-7.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg7.setRotationPoint(-4.0f, (float)b0, -1.0f);
        ((ModelRendererBends)this.spiderLeg7).offsetBox_Add(8.0f - legLength, -0.01f, -0.01f).resizeBox(legLength, 2.02f, 2.02f).updateVertices();
        (this.spiderLeg8 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 8, 2, 2, f);
        this.spiderLeg8.setRotationPoint(4.0f, (float)b0, -1.0f);
        ((ModelRendererBends)this.spiderLeg8).offsetBox_Add(0.0f, -0.01f, -0.01f).resizeBox(legLength, 2.02f, 2.02f).updateVertices();
        (this.spiderForeLeg1 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-foreLegLength, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg1.setRotationPoint(-legLength + 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg1.resizeBox(foreLegLength, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg2 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(0.0f, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg2.setRotationPoint(legLength - 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg2.resizeBox(foreLegLength, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg3 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-foreLegLength * foreLegRatio, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg3.setRotationPoint(-legLength * legRatio + 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg3.resizeBox(foreLegLength * foreLegRatio, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg4 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(0.0f, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg4.setRotationPoint(legLength * legRatio - 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg4.resizeBox(foreLegLength * foreLegRatio, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg5 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-foreLegLength * foreLegRatio, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg5.setRotationPoint(-legLength * legRatio + 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg5.resizeBox(foreLegLength * foreLegRatio, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg6 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(0.0f, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg6.setRotationPoint(legLength * legRatio - 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg6.resizeBox(foreLegLength * foreLegRatio, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg7 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(-foreLegLength, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg7.setRotationPoint(-legLength + 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg7.resizeBox(foreLegLength, 2.0f, 2.0f).updateVertices();
        (this.spiderForeLeg8 = new ModelRendererBends((ModelBase)this, 18, 0)).addBox(0.0f, 0.0f, -1.0f, 8, 2, 2, f);
        this.spiderForeLeg8.setRotationPoint(legLength - 1.0f, -1.0f, 0.0f);
        this.spiderForeLeg8.resizeBox(foreLegLength, 2.0f, 2.0f).updateVertices();
        this.spiderLeg1.addChild((ModelRenderer)this.spiderForeLeg1);
        this.spiderLeg2.addChild((ModelRenderer)this.spiderForeLeg2);
        this.spiderLeg3.addChild((ModelRenderer)this.spiderForeLeg3);
        this.spiderLeg4.addChild((ModelRenderer)this.spiderForeLeg4);
        this.spiderLeg5.addChild((ModelRenderer)this.spiderForeLeg5);
        this.spiderLeg6.addChild((ModelRenderer)this.spiderForeLeg6);
        this.spiderLeg7.addChild((ModelRenderer)this.spiderForeLeg7);
        this.spiderLeg8.addChild((ModelRenderer)this.spiderForeLeg8);
    }
    
    public void render(final Entity entityIn, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.2f, 0.0f);
        this.spiderHead.render(scale);
        this.spiderNeck.render(scale);
        this.spiderBody.render(scale);
        this.spiderLeg1.render(scale);
        this.spiderLeg2.render(scale);
        this.spiderLeg3.render(scale);
        this.spiderLeg4.render(scale);
        this.spiderLeg5.render(scale);
        this.spiderLeg6.render(scale);
        this.spiderLeg7.render(scale);
        this.spiderLeg8.render(scale);
        GL11.glPopMatrix();
    }
    
    public void setRotationAngles(final float argSwingTime, final float argSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity argEntity) {
        if (Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        if (Minecraft.getMinecraft().theWorld.isRemote && Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        final EntitySpider spider = (EntitySpider)argEntity;
        final Data_Spider data = Data_Spider.get(argEntity.getEntityId());
        this.headRotationX = headPitch;
        this.headRotationY = netHeadYaw;
        this.armSwing = argSwingTime;
        this.armSwingAmount = argSwingAmount;
        ((ModelRendererBends)this.spiderHead).sync(data.spiderHead);
        ((ModelRendererBends)this.spiderNeck).sync(data.spiderNeck);
        ((ModelRendererBends)this.spiderBody).sync(data.spiderBody);
        ((ModelRendererBends)this.spiderLeg1).sync(data.spiderLeg1);
        ((ModelRendererBends)this.spiderLeg2).sync(data.spiderLeg2);
        ((ModelRendererBends)this.spiderLeg3).sync(data.spiderLeg3);
        ((ModelRendererBends)this.spiderLeg4).sync(data.spiderLeg4);
        ((ModelRendererBends)this.spiderLeg5).sync(data.spiderLeg5);
        ((ModelRendererBends)this.spiderLeg6).sync(data.spiderLeg6);
        ((ModelRendererBends)this.spiderLeg7).sync(data.spiderLeg7);
        ((ModelRendererBends)this.spiderLeg8).sync(data.spiderLeg8);
        this.spiderForeLeg1.sync(data.spiderForeLeg1);
        this.spiderForeLeg2.sync(data.spiderForeLeg2);
        this.spiderForeLeg3.sync(data.spiderForeLeg3);
        this.spiderForeLeg4.sync(data.spiderForeLeg4);
        this.spiderForeLeg5.sync(data.spiderForeLeg5);
        this.spiderForeLeg6.sync(data.spiderForeLeg6);
        this.spiderForeLeg7.sync(data.spiderForeLeg7);
        this.spiderForeLeg8.sync(data.spiderForeLeg8);
        this.renderOffset.set(data.renderOffset);
        this.renderRotation.set(data.renderRotation);
        if (Data_Spider.get(argEntity.getEntityId()).canBeUpdated()) {
            ((ModelRendererBends)this.spiderHead).resetScale();
            ((ModelRendererBends)this.spiderNeck).resetScale();
            ((ModelRendererBends)this.spiderBody).resetScale();
            ((ModelRendererBends)this.spiderLeg1).resetScale();
            ((ModelRendererBends)this.spiderLeg2).resetScale();
            ((ModelRendererBends)this.spiderLeg3).resetScale();
            ((ModelRendererBends)this.spiderLeg4).resetScale();
            ((ModelRendererBends)this.spiderLeg5).resetScale();
            ((ModelRendererBends)this.spiderLeg6).resetScale();
            ((ModelRendererBends)this.spiderLeg7).resetScale();
            ((ModelRendererBends)this.spiderLeg8).resetScale();
            this.spiderForeLeg1.resetScale();
            this.spiderForeLeg2.resetScale();
            this.spiderForeLeg3.resetScale();
            this.spiderForeLeg4.resetScale();
            this.spiderForeLeg5.resetScale();
            this.spiderForeLeg6.resetScale();
            this.spiderForeLeg7.resetScale();
            this.spiderForeLeg8.resetScale();
            if (data.calcCollidedHorizontally()) {
                AnimatedEntity.getByEntity(argEntity).get("wallClimb").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)data);
                BendsPack.animate(this, "spider", "wallClimb");
            }
            else if (!data.isOnGround() | data.ticksAfterTouchdown < 2.0f) {
                AnimatedEntity.getByEntity(argEntity).get("jump").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)data);
                BendsPack.animate(this, "spider", "jump");
            }
            else {
                AnimatedEntity.getByEntity(argEntity).get("onGround").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)data);
                if (Data_Player.get(argEntity.getEntityId()).motion.x == 0.0f & Data_Player.get(argEntity.getEntityId()).motion.z == 0.0f) {
                    BendsPack.animate(this, "spider", "stand");
                }
                else {
                    BendsPack.animate(this, "spider", "walk");
                }
            }
            ((ModelRendererBends)this.spiderHead).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderNeck).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderBody).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg1).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg2).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg3).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg4).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg5).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg6).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg7).update(data.ticksPerFrame);
            ((ModelRendererBends)this.spiderLeg8).update(data.ticksPerFrame);
            this.spiderForeLeg1.update(data.ticksPerFrame);
            this.spiderForeLeg2.update(data.ticksPerFrame);
            this.spiderForeLeg3.update(data.ticksPerFrame);
            this.spiderForeLeg4.update(data.ticksPerFrame);
            this.spiderForeLeg5.update(data.ticksPerFrame);
            this.spiderForeLeg6.update(data.ticksPerFrame);
            this.spiderForeLeg7.update(data.ticksPerFrame);
            this.spiderForeLeg8.update(data.ticksPerFrame);
            this.renderOffset.update(data.ticksPerFrame);
            this.renderRotation.update(data.ticksPerFrame);
            data.updatedThisFrame = true;
        }
        Data_Spider.get(argEntity.getEntityId()).syncModelInfo(this);
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
    
    public void updateWithEntityData(final EntitySpider argSpider) {
        final Data_Spider data = Data_Spider.get(argSpider.getEntityId());
        if (data != null) {
            this.renderOffset.set(data.renderOffset);
            this.renderRotation.set(data.renderRotation);
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model.entity;

import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.renderer.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import shop.xiaoda.utils.mobends.*;
import org.lwjgl.compatibility.util.vector.*;
import net.minecraft.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.utils.mobends.pack.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;

public class ModelBendsPlayer extends ModelPlayer
{
    public ModelRendererBends bipedRightForeArm;
    public ModelRendererBends bipedLeftForeArm;
    public ModelRendererBends bipedRightForeLeg;
    public ModelRendererBends bipedLeftForeLeg;
    public ModelRendererBends bipedRightForeArmwear;
    public ModelRendererBends bipedLeftForeArmwear;
    public ModelRendererBends bipedRightForeLegwear;
    public ModelRendererBends bipedLeftForeLegwear;
    public SmoothVector3f renderOffset;
    public SmoothVector3f renderRotation;
    public SmoothVector3f renderItemRotation;
    public SwordTrail swordTrail;
    public float headRotationX;
    public float headRotationY;
    public float armSwing;
    public float armSwingAmount;
    private final ModelRenderer bipedCape;
    private final ModelRenderer bipedDeadmau5Head;
    private final boolean smallArms;
    
    public ModelBendsPlayer(final float scaleFactor, final boolean useSmallArms) {
        this(scaleFactor, useSmallArms, true);
    }
    
    public ModelBendsPlayer(final float scaleFactor, final boolean useSmallArms, final boolean bigTexture) {
        super(scaleFactor, useSmallArms);
        this.renderOffset = new SmoothVector3f();
        this.renderRotation = new SmoothVector3f();
        this.renderItemRotation = new SmoothVector3f();
        this.swordTrail = new SwordTrail();
        this.textureWidth = 64;
        this.textureHeight = (bigTexture ? 64 : 32);
        this.smallArms = useSmallArms;
        (this.bipedDeadmau5Head = new ModelRendererBends((ModelBase)this, 24, 0)).addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, scaleFactor);
        (this.bipedCape = new ModelRendererBends((ModelBase)this, 0, 0)).setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, scaleFactor);
        (this.bipedHeadwear = new ModelRendererBends((ModelBase)this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, scaleFactor + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRendererBends((ModelBase)this, 16, 16)).addBox(-4.0f, -12.0f, -2.0f, 8, 12, 4, scaleFactor);
        this.bipedBody.setRotationPoint(0.0f, 12.0f, 0.0f);
        (this.bipedHead = new ModelRendererBends((ModelBase)this, 0, 0).setShowChildIfHidden(true)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, scaleFactor);
        this.bipedHead.setRotationPoint(0.0f, -12.0f, 0.0f);
        if (useSmallArms) {
            (this.bipedLeftArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 32, 48).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-1.0f, -2.0f, -2.0f, 3, 6, 4, scaleFactor);
            this.bipedLeftArm.setRotationPoint(5.0f, -9.5f, 0.0f);
            (this.bipedRightArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-2.0f, -2.0f, -2.0f, 3, 6, 4, scaleFactor);
            this.bipedRightArm.setRotationPoint(-5.0f, -9.5f, 0.0f);
            (this.bipedLeftArmwear = new ModelRendererBends((ModelBase)this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 3, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box = ((ModelRendererBends)this.bipedLeftArmwear).getBox();
            box.resY -= 0.25f;
            ((ModelRendererBends)this.bipedLeftArmwear).getBox().updateVertexPositions(this.bipedLeftArmwear);
            this.bipedLeftArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            (this.bipedRightArmwear = new ModelRendererBends((ModelBase)this, 40, 32)).addBox(-2.0f, -2.0f, -2.0f, 3, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box2 = ((ModelRendererBends)this.bipedRightArmwear).getBox();
            box2.resY -= 0.25f;
            ((ModelRendererBends)this.bipedRightArmwear).getBox().updateVertexPositions(this.bipedRightArmwear);
            this.bipedRightArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            ((ModelRendererBends)this.bipedRightArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(3.02f, 6.0f, 4.02f).updateVertices();
            ((ModelRendererBends)this.bipedLeftArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(3.02f, 6.0f, 4.02f).updateVertices();
            (this.bipedLeftForeArm = new ModelRendererBends((ModelBase)this, 32, 54)).addBox(-1.0f, 0.0f, -4.0f, 3, 6, 4, scaleFactor);
            this.bipedLeftForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
            this.bipedLeftForeArm.getBox().offsetTextureQuad(this.bipedLeftForeArm, 3, 0.0f, -6.0f);
            (this.bipedRightForeArm = new ModelRendererBends((ModelBase)this, 40, 22)).addBox(-2.0f, 0.0f, -4.0f, 3, 6, 4, scaleFactor);
            this.bipedRightForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
            this.bipedRightForeArm.getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0f, -6.0f);
            (this.bipedLeftForeArmwear = new ModelRendererBends((ModelBase)this, 48, 54)).addBox(-1.0f, 0.0f, -4.0f, 3, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box3 = this.bipedLeftForeArmwear.getBox();
            box3.resY -= 0.25f;
            final ModelBoxBends box4 = this.bipedLeftForeArmwear.getBox();
            box4.offsetY += 0.25f;
            this.bipedLeftForeArmwear.getBox().updateVertexPositions(this.bipedLeftForeArmwear);
            this.bipedLeftForeArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.bipedLeftForeArmwear.getBox().offsetTextureQuad(this.bipedLeftForeArmwear, 3, 0.0f, -6.0f);
            (this.bipedRightForeArmwear = new ModelRendererBends((ModelBase)this, 40, 38)).addBox(-2.0f, 0.0f, -4.0f, 3, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box5 = this.bipedRightForeArmwear.getBox();
            box5.resY -= 0.25f;
            final ModelBoxBends box6 = this.bipedRightForeArmwear.getBox();
            box6.offsetY += 0.25f;
            this.bipedRightForeArmwear.getBox().updateVertexPositions(this.bipedRightForeArmwear);
            this.bipedRightForeArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.bipedRightForeArmwear.getBox().offsetTextureQuad(this.bipedRightForeArmwear, 3, 0.0f, -6.0f);
        }
        else {
            (this.bipedLeftArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 32, 48).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-1.0f, -2.0f, -2.0f, 4, 6, 4, scaleFactor);
            this.bipedLeftArm.setRotationPoint(5.0f, -10.0f, 0.0f);
            (this.bipedRightArm = new ModelRendererBends_SeperatedChild((ModelBase)this, 40, 16).setMother((ModelRendererBends)this.bipedBody).setShowChildIfHidden(true)).addBox(-3.0f, -2.0f, -2.0f, 4, 6, 4, scaleFactor);
            this.bipedRightArm.setRotationPoint(-5.0f, -10.0f, 0.0f);
            (this.bipedLeftArmwear = new ModelRendererBends((ModelBase)this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 4, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box7 = ((ModelRendererBends)this.bipedLeftArmwear).getBox();
            box7.resY -= 0.25f;
            ((ModelRendererBends)this.bipedLeftArmwear).getBox().updateVertexPositions(this.bipedLeftArmwear);
            this.bipedLeftArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            (this.bipedRightArmwear = new ModelRendererBends((ModelBase)this, 40, 32)).addBox(-3.0f, -2.0f, -2.0f, 4, 6, 4, scaleFactor + 0.25f);
            this.bipedRightArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            ((ModelRendererBends)this.bipedRightArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
            ((ModelRendererBends)this.bipedLeftArm).offsetBox_Add(-0.01f, 0.0f, -0.01f).resizeBox(4.02f, 6.0f, 4.02f).updateVertices();
            (this.bipedLeftForeArm = new ModelRendererBends((ModelBase)this, 32, 54)).addBox(-1.0f, 0.0f, -4.0f, 4, 6, 4, scaleFactor);
            this.bipedLeftForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
            this.bipedLeftForeArm.getBox().offsetTextureQuad(this.bipedLeftForeArm, 3, 0.0f, -6.0f);
            (this.bipedRightForeArm = new ModelRendererBends((ModelBase)this, 40, 22)).addBox(-3.0f, 0.0f, -4.0f, 4, 6, 4, scaleFactor);
            this.bipedRightForeArm.setRotationPoint(0.0f, 4.0f, 2.0f);
            this.bipedRightForeArm.getBox().offsetTextureQuad(this.bipedRightForeArm, 3, 0.0f, -6.0f);
            (this.bipedLeftForeArmwear = new ModelRendererBends((ModelBase)this, 48, 54)).addBox(-1.0f, 0.0f, -4.0f, 4, 6, 4, scaleFactor + 0.25f);
            final ModelBoxBends box8 = this.bipedLeftForeArmwear.getBox();
            box8.resY -= 0.25f;
            final ModelBoxBends box9 = this.bipedLeftForeArmwear.getBox();
            box9.offsetY += 0.25f;
            this.bipedLeftForeArmwear.getBox().updateVertexPositions(this.bipedLeftForeArmwear);
            this.bipedLeftForeArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.bipedLeftForeArmwear.getBox().offsetTextureQuad(this.bipedLeftForeArmwear, 3, 0.0f, -6.0f);
            (this.bipedRightForeArmwear = new ModelRendererBends((ModelBase)this, 40, 38)).addBox(-3.0f, 0.0f, -4.0f, 4, 6, 4, scaleFactor + 0.25f);
            this.bipedRightForeArmwear.setRotationPoint(0.0f, 0.0f, 0.0f);
            this.bipedRightForeArmwear.getBox().offsetTextureQuad(this.bipedRightForeArmwear, 3, 0.0f, -6.0f);
        }
        (this.bipedRightLeg = new ModelRendererBends((ModelBase)this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, scaleFactor);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
        (this.bipedLeftLeg = new ModelRendererBends((ModelBase)this, 16, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, scaleFactor);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedLeftLegwear = new ModelRendererBends((ModelBase)this, 0, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, scaleFactor + 0.25f);
        final ModelBoxBends box10 = ((ModelRendererBends)this.bipedLeftLegwear).getBox();
        box10.resY -= 0.25f;
        ((ModelRendererBends)this.bipedLeftLegwear).getBox().updateVertexPositions(this.bipedLeftLegwear);
        this.bipedLeftLegwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedRightLegwear = new ModelRendererBends((ModelBase)this, 0, 32)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, scaleFactor + 0.25f);
        final ModelBoxBends box11 = ((ModelRendererBends)this.bipedRightLegwear).getBox();
        box11.resY -= 0.25f;
        ((ModelRendererBends)this.bipedRightLegwear).getBox().updateVertexPositions(this.bipedRightLegwear);
        this.bipedRightLegwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBodyWear = new ModelRendererBends((ModelBase)this, 16, 32)).addBox(-4.0f, -12.0f, -2.0f, 8, 12, 4, scaleFactor + 0.25f);
        this.bipedBodyWear.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedRightForeLeg = new ModelRendererBends((ModelBase)this, 0, 22)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, scaleFactor);
        this.bipedRightForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        this.bipedRightForeLeg.getBox().offsetTextureQuad(this.bipedRightForeLeg, 3, 0.0f, -6.0f);
        (this.bipedLeftForeLeg = new ModelRendererBends((ModelBase)this, 16, 54)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, scaleFactor);
        this.bipedLeftForeLeg.setRotationPoint(0.0f, 6.0f, -2.0f);
        this.bipedLeftForeLeg.getBox().offsetTextureQuad(this.bipedLeftForeLeg, 3, 0.0f, -6.0f);
        (this.bipedRightForeLegwear = new ModelRendererBends((ModelBase)this, 0, 38)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, scaleFactor + 0.25f);
        final ModelBoxBends box12 = this.bipedRightForeLegwear.getBox();
        box12.resY -= 0.25f;
        final ModelBoxBends box13 = this.bipedRightForeLegwear.getBox();
        box13.offsetY += 0.25f;
        this.bipedRightForeLegwear.getBox().updateVertexPositions(this.bipedRightForeLegwear);
        this.bipedRightForeLegwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedRightForeLegwear.getBox().offsetTextureQuad(this.bipedRightForeLegwear, 3, 0.0f, -6.0f);
        (this.bipedLeftForeLegwear = new ModelRendererBends((ModelBase)this, 0, 54)).addBox(-2.0f, 0.0f, 0.0f, 4, 6, 4, scaleFactor + 0.25f);
        final ModelBoxBends box14 = this.bipedLeftForeLegwear.getBox();
        box14.resY -= 0.25f;
        final ModelBoxBends box15 = this.bipedLeftForeLegwear.getBox();
        box15.offsetY += 0.25f;
        this.bipedLeftForeLegwear.getBox().updateVertexPositions(this.bipedLeftForeLegwear);
        this.bipedLeftForeLegwear.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedLeftForeLegwear.getBox().offsetTextureQuad(this.bipedLeftForeLegwear, 3, 0.0f, -6.0f);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedBodyWear);
        this.bipedHead.addChild(this.bipedHeadwear);
        this.bipedRightArm.addChild((ModelRenderer)this.bipedRightForeArm);
        this.bipedLeftArm.addChild((ModelRenderer)this.bipedLeftForeArm);
        this.bipedRightArm.addChild(this.bipedRightArmwear);
        this.bipedLeftArm.addChild(this.bipedLeftArmwear);
        this.bipedRightForeArm.addChild((ModelRenderer)this.bipedRightForeArmwear);
        this.bipedLeftForeArm.addChild((ModelRenderer)this.bipedLeftForeArmwear);
        this.bipedRightLeg.addChild((ModelRenderer)this.bipedRightForeLeg);
        this.bipedLeftLeg.addChild((ModelRenderer)this.bipedLeftForeLeg);
        this.bipedRightLeg.addChild(this.bipedRightLegwear);
        this.bipedLeftLeg.addChild(this.bipedLeftLegwear);
        this.bipedRightForeLeg.addChild((ModelRenderer)this.bipedRightForeLegwear);
        this.bipedLeftForeLeg.addChild((ModelRenderer)this.bipedLeftForeLegwear);
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
        final AnimatedEntity animatedEntity = AnimatedEntity.getByEntity(argEntity);
        if (animatedEntity == null) {
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
        if (Data_Player.get(argEntity.getEntityId()).canBeUpdated()) {
            this.renderOffset.setSmooth(new Vector3f(0.0f, -1.0f, 0.0f), 0.5f);
            this.renderRotation.setSmooth(new Vector3f(0.0f, 0.0f, 0.0f), 0.5f);
            this.renderItemRotation.setSmooth(new Vector3f(0.0f, 0.0f, 0.0f), 0.5f);
            ((ModelRendererBends)this.bipedHead).resetScale();
            ((ModelRendererBends)this.bipedHeadwear).resetScale();
            ((ModelRendererBends)this.bipedBody).resetScale();
            ((ModelRendererBends)this.bipedRightArm).resetScale();
            ((ModelRendererBends)this.bipedLeftArm).resetScale();
            ((ModelRendererBends)this.bipedRightLeg).resetScale();
            ((ModelRendererBends)this.bipedLeftLeg).resetScale();
            this.bipedRightForeArm.resetScale();
            this.bipedLeftForeArm.resetScale();
            this.bipedRightForeLeg.resetScale();
            this.bipedLeftForeLeg.resetScale();
            BendsVar.tempData = Data_Player.get(argEntity.getEntityId());
            if (argEntity.isRiding()) {
                animatedEntity.get("riding").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                BendsPack.animate(this, "player", "riding");
            }
            else if (argEntity.isInWater()) {
                animatedEntity.get("swimming").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                BendsPack.animate(this, "player", "swimming");
            }
            else if (!Data_Player.get(argEntity.getEntityId()).isOnGround() | Data_Player.get(argEntity.getEntityId()).ticksAfterTouchdown < 2.0f) {
                animatedEntity.get("jump").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                BendsPack.animate(this, "player", "jump");
            }
            else {
                if (Data_Player.get(argEntity.getEntityId()).motion.x == 0.0f & Data_Player.get(argEntity.getEntityId()).motion.z == 0.0f) {
                    animatedEntity.get("stand").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "stand");
                }
                else if (argEntity.isSprinting()) {
                    animatedEntity.get("sprint").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "sprint");
                }
                else {
                    animatedEntity.get("walk").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "walk");
                }
                if (argEntity.isSneaking()) {
                    animatedEntity.get("sneak").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "sneak");
                }
            }
            if (this.aimedBow) {
                animatedEntity.get("bow").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                BendsPack.animate(this, "player", "bow");
            }
            else {
                final ItemStack currentItem = ((EntityPlayer)argEntity).getCurrentEquippedItem();
                if (currentItem != null && !(currentItem.getItem() instanceof ItemAxe) && !(currentItem.getItem() instanceof ItemSword)) {
                    animatedEntity.get("mining").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "mining");
                }
                else if (currentItem != null && currentItem.getItem() instanceof ItemAxe) {
                    animatedEntity.get("axe").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                    BendsPack.animate(this, "player", "axe");
                }
                else {
                    animatedEntity.get("attack").animate((EntityLivingBase)argEntity, (ModelBase)this, (EntityData)Data_Player.get(argEntity.getEntityId()));
                }
            }
            ((ModelRendererBends)this.bipedHead).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedHeadwear).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedBody).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightArm).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedLeftLeg).update(data.ticksPerFrame);
            ((ModelRendererBends)this.bipedRightLeg).update(data.ticksPerFrame);
            this.bipedLeftForeArm.update(data.ticksPerFrame);
            this.bipedRightForeArm.update(data.ticksPerFrame);
            this.bipedLeftForeLeg.update(data.ticksPerFrame);
            this.bipedRightForeLeg.update(data.ticksPerFrame);
            this.renderOffset.update(data.ticksPerFrame);
            this.renderRotation.update(data.ticksPerFrame);
            this.renderItemRotation.update(data.ticksPerFrame);
            this.swordTrail.update(data.ticksPerFrame);
            data.updatedThisFrame = true;
        }
        Data_Player.get(argEntity.getEntityId()).syncModelInfo(this);
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
    
    public void renderRightArm() {
        this.bipedRightArm.render(0.0625f);
        this.bipedRightArmwear.render(0.0625f);
    }
    
    public void renderLeftArm() {
        this.bipedLeftArm.render(0.0625f);
        this.bipedLeftArmwear.render(0.0625f);
    }
    
    public void setInvisible(final boolean invisible) {
        super.setInvisible(invisible);
        this.bipedLeftArmwear.showModel = invisible;
        this.bipedRightArmwear.showModel = invisible;
        this.bipedLeftLegwear.showModel = invisible;
        this.bipedRightLegwear.showModel = invisible;
        this.bipedBodyWear.showModel = invisible;
        this.bipedCape.showModel = invisible;
        this.bipedDeadmau5Head.showModel = invisible;
    }
    
    public void postRenderArm(final float scale) {
        if (this.smallArms) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            ++bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(scale);
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            --bipedRightArm2.rotationPointX;
        }
        else {
            this.bipedRightArm.postRender(scale);
        }
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

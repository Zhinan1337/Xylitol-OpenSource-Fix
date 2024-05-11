//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.utils.mobends.client.model.*;
import net.minecraft.util.*;

public class Animation_Jump extends Animation
{
    public String getName() {
        return "jump";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final ModelBendsPlayer model = (ModelBendsPlayer)argModel;
        final Data_Player data = (Data_Player)argData;
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(0.0f, 0.3f);
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(0.0f, 0.1f);
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothZ(0.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(45.0f, 0.05f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-45.0f, 0.05f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(0.0f, 0.5f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(0.0f, 0.5f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f, 0.1f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f, 0.1f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-20.0f, 0.1f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-20.0f, 0.1f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-45.0f, 0.1f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-45.0f, 0.1f);
        model.bipedRightForeLeg.rotation.setSmoothX(50.0f, 0.1f);
        model.bipedLeftForeLeg.rotation.setSmoothX(50.0f, 0.1f);
        model.bipedRightForeArm.rotation.setSmoothX(0.0f, 0.3f);
        model.bipedLeftForeArm.rotation.setSmoothX(0.0f, 0.3f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY);
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX - model.bipedBody.rotateAngleX);
        if (data.ticksAfterLiftoff < 2.0f) {
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(20.0f, 2.0f);
            ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(0.0f, 2.0f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(0.0f, 2.0f);
            model.bipedRightForeLeg.rotation.setSmoothX(0.0f, 2.0f);
            model.bipedLeftForeLeg.rotation.setSmoothX(0.0f, 2.0f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(2.0f, 2.0f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-2.0f, 2.0f);
            model.bipedRightForeArm.rotation.setSmoothX(-20.0f, 2.0f);
            model.bipedLeftForeArm.rotation.setSmoothX(-20.0f, 2.0f);
        }
        if (argData.motion.x != 0.0f | argData.motion.z != 0.0f) {
            if (argEntity.isSprinting()) {
                float bodyRot = 0.0f;
                float bodyLean = argData.motion.y;
                if (bodyLean < -0.2f) {
                    bodyLean = -0.2f;
                }
                if (bodyLean > 0.2f) {
                    bodyLean = 0.2f;
                }
                bodyLean = bodyLean * -100.0f + 20.0f;
                ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(bodyLean, 0.3f);
                ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(5.0f, 0.3f);
                ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-5.0f, 0.3f);
                ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(10.0f, 0.3f);
                ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-10.0f, 0.3f);
                if (data.sprintJumpLeg) {
                    ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-45.0f, 0.4f);
                    ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(45.0f, 0.4f);
                    ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(50.0f, 0.3f);
                    ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-50.0f, 0.3f);
                    bodyRot = 20.0f;
                }
                else {
                    ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(45.0f, 0.4f);
                    ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-45.0f, 0.4f);
                    ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-50.0f, 0.3f);
                    ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(50.0f, 0.3f);
                    bodyRot = -20.0f;
                }
                ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(bodyRot, 0.3f);
                ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY - bodyRot);
                ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX - 20.0f);
                final float var = model.bipedRightLeg.rotateAngleX;
                model.bipedLeftForeLeg.rotation.setSmoothX((float)((var < 0.0f) ? 45 : 2), 0.3f);
                model.bipedRightForeLeg.rotation.setSmoothX((float)((var < 0.0f) ? 2 : 45), 0.3f);
            }
            else {
                ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-5.0f + 0.5f * (float)(MathHelper.cos(model.armSwing * 0.6662f) * 1.4f * model.armSwingAmount / 3.141592653589793 * 180.0), 1.0f);
                ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-5.0f + 0.5f * (float)(MathHelper.cos(model.armSwing * 0.6662f + 3.1415927f) * 1.4f * model.armSwingAmount / 3.141592653589793 * 180.0), 1.0f);
                final float var2 = (float)(model.armSwing * 0.6662f / 3.141592653589793) % 2.0f;
                model.bipedLeftForeLeg.rotation.setSmoothX((float)((var2 > 1.0f) ? 45 : 0), 0.3f);
                model.bipedRightForeLeg.rotation.setSmoothX((float)((var2 > 1.0f) ? 0 : 45), 0.3f);
                model.bipedLeftForeArm.rotation.setSmoothX((float)(Math.cos(model.armSwing * 0.6662f + 1.5707963267948966) + 1.0) / 2.0f * -20.0f, 1.0f);
                model.bipedRightForeArm.rotation.setSmoothX((float)(Math.cos(model.armSwing * 0.6662f) + 1.0) / 2.0f * -20.0f, 0.3f);
            }
        }
    }
}

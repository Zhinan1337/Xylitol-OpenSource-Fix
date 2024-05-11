//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.mobends.util.*;
import shop.xiaoda.utils.mobends.client.model.*;

public class Animation_Swimming extends Animation
{
    public String getName() {
        return "swimming";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final ModelBendsPlayer model = (ModelBendsPlayer)argModel;
        final Data_Player data = (Data_Player)argData;
        float armSway = (MathHelper.cos(data.ticks * 0.1625f) + 1.0f) / 2.0f;
        float legFlap = MathHelper.cos(data.ticks * 0.4625f);
        final float foreArmSway = (float)(data.ticks * 0.1625f % 6.283185307179586) / 6.2831855f;
        float foreArmStretch = armSway * 2.0f;
        --foreArmStretch;
        foreArmStretch = GUtil.min(foreArmStretch, 0.0f);
        if (data.motion.x == 0.0f & data.motion.z == 0.0f) {
            armSway = (MathHelper.cos(data.ticks * 0.0825f) + 1.0f) / 2.0f;
            final float armSway2 = (-MathHelper.sin(data.ticks * 0.0825f) + 1.0f) / 2.0f;
            legFlap = MathHelper.cos(data.ticks * 0.2625f);
            ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothX(0.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(armSway2 * 30.0f - 15.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(armSway2 * 30.0f - 15.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-armSway * 30.0f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(armSway * 30.0f);
            model.bipedLeftForeArm.rotation.setSmoothX(armSway2 * -40.0f, 0.3f);
            model.bipedRightForeArm.rotation.setSmoothX(armSway2 * -40.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(legFlap * 40.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-legFlap * 40.0f, 0.3f);
            model.bipedLeftForeLeg.rotation.setSmoothX(5.0f, 0.4f);
            model.bipedRightForeLeg.rotation.setSmoothX(5.0f, 0.4f);
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(armSway * 10.0f);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothX(model.headRotationX);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothY(model.headRotationY);
        }
        else {
            ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothX(-70.0f - armSway * -20.0f, 0.3f);
            model.renderRotation.setSmoothX(70.0f, 0.3f);
            model.renderOffset.setSmoothZ(10.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).pre_rotation.setSmoothY(90.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).pre_rotation.setSmoothY(-90.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(armSway * -120.0f - 45.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(armSway * -120.0f - 45.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).pre_rotation.setSmoothZ(armSway * -20.0f);
            ((ModelRendererBends)model.bipedRightArm).pre_rotation.setSmoothZ(-(armSway * -20.0f));
            model.bipedLeftForeArm.rotation.setSmoothX((foreArmSway < 0.55f | foreArmSway > 0.9) ? (foreArmStretch * -60.0f) : -60.0f, 0.3f);
            model.bipedRightForeArm.rotation.setSmoothX((foreArmSway < 0.55f | foreArmSway > 0.9) ? (foreArmStretch * -60.0f) : -60.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(legFlap * 40.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-legFlap * 40.0f, 0.3f);
            model.bipedLeftForeLeg.rotation.setSmoothX(5.0f, 0.4f);
            model.bipedRightForeLeg.rotation.setSmoothX(5.0f, 0.4f);
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(armSway * -20.0f);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothX(model.headRotationX);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothY(model.headRotationY);
            model.renderItemRotation.setSmoothX(armSway * 120.0f, 0.3f);
        }
    }
}

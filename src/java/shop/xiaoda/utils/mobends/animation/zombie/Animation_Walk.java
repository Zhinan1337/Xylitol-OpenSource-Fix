//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.zombie;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.mobends.client.model.*;

public class Animation_Walk extends Animation
{
    public String getName() {
        return "walk";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final EntityZombie zombie = (EntityZombie)argEntity;
        final ModelBendsZombie model = (ModelBendsZombie)argModel;
        final Data_Zombie data = (Data_Zombie)argData;
        model.renderOffset.setSmoothY(-3.0f);
        final float var2 = 30.0f + MathHelper.cos(model.armSwing * 0.6662f * 2.0f) * 10.0f;
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(var2, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(0.9f * (float)(MathHelper.cos(model.armSwing * 0.6662f + 3.1415927f) * 2.0f * model.armSwingAmount * 0.5f / 3.141592653589793 * 180.0));
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(0.9f * (float)(MathHelper.cos(model.armSwing * 0.6662f) * 2.0f * model.armSwingAmount * 0.5f / 3.141592653589793 * 180.0));
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(5.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-5.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-5.0f + 0.9f * (float)(MathHelper.cos(model.armSwing * 0.6662f) * 1.4f * model.armSwingAmount / 3.141592653589793 * 180.0), 1.0f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-5.0f + 0.9f * (float)(MathHelper.cos(model.armSwing * 0.6662f + 3.1415927f) * 1.4f * model.armSwingAmount / 3.141592653589793 * 180.0), 1.0f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothY(0.0f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothY(0.0f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f, 0.2f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f, 0.2f);
        final float var3 = (float)(model.armSwing * 0.6662f / 3.141592653589793) % 2.0f;
        ((ModelRendererBends)model.bipedLeftForeLeg).rotation.setSmoothX((float)((var3 > 1.0f) ? 45 : 0), 0.3f);
        ((ModelRendererBends)model.bipedRightForeLeg).rotation.setSmoothX((float)((var3 > 1.0f) ? 0 : 45), 0.3f);
        ((ModelRendererBends)model.bipedLeftForeArm).rotation.setSmoothX((float)(Math.cos(model.armSwing * 0.6662f + 1.5707963267948966) + 1.0) / 2.0f * -20.0f, 1.0f);
        ((ModelRendererBends)model.bipedRightForeArm).rotation.setSmoothX((float)(Math.cos(model.armSwing * 0.6662f) + 1.0) / 2.0f * -20.0f, 0.3f);
        final float var4 = MathHelper.cos(model.armSwing * 0.6662f + 3.1415927f) / 3.1415927f * 180.0f * 0.5f;
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(MathHelper.cos(model.armSwing * 0.6662f + 3.1415927f) / 3.1415927f * 180.0f * 0.5f, 0.3f);
        if (data.currentWalkingState == 1) {
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-120.0f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-120.0f);
        }
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX - 30.0f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY - var4);
    }
}

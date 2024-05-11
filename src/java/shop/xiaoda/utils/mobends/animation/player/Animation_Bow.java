//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;

public class Animation_Bow extends Animation
{
    public String getName() {
        return "bow";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final ModelBendsPlayer model = (ModelBendsPlayer)argModel;
        final Data_Player data = (Data_Player)argData;
        final EntityPlayer player = (EntityPlayer)argEntity;
        float aimedBowDuration = 0.0f;
        if (player != null) {
            aimedBowDuration = (float)player.getItemInUseDuration();
        }
        if (aimedBowDuration > 15.0f) {
            aimedBowDuration = 15.0f;
        }
        if (aimedBowDuration < 10.0f) {
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(0.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(0.0f, 0.3f);
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(30.0f, 0.3f);
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(0.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(0.0f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-30.0f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-30.0f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothY(80.0f);
            final float var = aimedBowDuration / 10.0f;
            model.bipedLeftForeArm.rotation.setSmoothX(var * -50.0f);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothX(model.headRotationX - 30.0f, 0.3f);
        }
        else {
            final float var2 = 20.0f - (aimedBowDuration - 10.0f) / 5.0f * 20.0f;
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(var2, 0.3f);
            final float var3 = (aimedBowDuration - 10.0f) / 5.0f * -25.0f;
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(var3 + model.headRotationY, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-90.0f - var2, 0.3f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-30.0f);
            ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothY(80.0f);
            final float var4 = aimedBowDuration / 10.0f;
            model.bipedLeftForeArm.rotation.setSmoothX(var4 * -30.0f);
            ((ModelRendererBends)model.bipedRightArm).pre_rotation.setSmoothY(var3);
            float var5 = -90.0f + model.headRotationX;
            var5 = GUtil.min(var5, -120.0f);
            ((ModelRendererBends)model.bipedLeftArm).pre_rotation.setSmoothX(var5, 0.3f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(model.headRotationX - 90.0f);
            ((ModelRendererBends)model.bipedHead).rotation.setY(-var3);
            ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothX(-var2, 0.3f);
            ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX);
        }
    }
}

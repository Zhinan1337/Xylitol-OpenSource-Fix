//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import net.minecraft.entity.player.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.utils.mobends.client.model.*;

public class Animation_Attack_PunchStance
{
    public static void animate(final EntityPlayer player, final ModelBendsPlayer model, final Data_Player data) {
        if (!(data.motion.x == 0.0f & data.motion.z == 0.0f)) {
            return;
        }
        model.renderRotation.setSmoothY(20.0f);
        model.renderOffset.setSmoothY(-2.0f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-90.0f, 0.3f);
        model.bipedRightForeArm.rotation.setSmoothX(-80.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-90.0f, 0.3f);
        model.bipedLeftForeArm.rotation.setSmoothX(-80.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(20.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(-20.0f, 0.3f);
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(10.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothY(-25.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f);
        model.bipedRightForeLeg.rotation.setSmoothX(30.0f, 0.3f);
        model.bipedLeftForeLeg.rotation.setSmoothX(30.0f, 0.3f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY - 20.0f);
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX - 10.0f);
    }
}

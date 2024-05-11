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
import net.minecraft.util.*;

public class Animation_Mining extends Animation
{
    public String getName() {
        return "mining";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final ModelBendsPlayer model = (ModelBendsPlayer)argModel;
        final Data_Player data = (Data_Player)argData;
        final EntityPlayer player = (EntityPlayer)argEntity;
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f, 0.3f);
        model.renderOffset.setSmoothY(-1.5f, 0.3f);
        if (player.isSwingInProgress) {
            final float speed = 1.8f;
            final float progress = player.ticksExisted * speed / 20.0f % 1.0f;
            final float progress2 = (player.ticksExisted - 2) * speed / 20.0f % 1.0f;
            final float armSwing = (MathHelper.cos(progress * 3.1415927f * 2.0f) + 1.0f) / 2.0f * -60.0f - 30.0f + model.headRotationX * 0.5f - 30.0f;
            final float armYRot = 30.0f + MathHelper.cos((armSwing - 90.0f) / 180.0f * 3.14f) * -5.0f;
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(armSwing, 0.7f);
            ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothY(-armYRot, 0.7f);
            model.renderItemRotation.setSmoothZ(-30.0f, 0.3f);
            ((ModelRendererBends)model.bipedBody).rotation.setSmoothY(MathHelper.sin(progress2 * 3.1415927f * 2.0f) * -20.0f);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothX(model.headRotationX - model.bipedBody.rotateAngleX);
            ((ModelRendererBends)model.bipedHead).rotation.setSmoothY(model.headRotationY - model.bipedBody.rotateAngleY);
        }
    }
}

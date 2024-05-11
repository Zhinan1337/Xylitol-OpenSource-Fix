//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import net.minecraft.entity.player.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.item.*;
import shop.xiaoda.utils.mobends.util.*;
import org.lwjgl.compatibility.util.vector.*;
import shop.xiaoda.utils.mobends.client.model.*;

public class Animation_Attack_Combo1
{
    public static void animate(final EntityPlayer player, final ModelBendsPlayer model, final Data_Player data) {
        if (data.ticksAfterPunch < 0.5f) {
            model.swordTrail.reset();
        }
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            model.swordTrail.add(model);
        }
        final float attackState = data.ticksAfterPunch / 10.0f;
        float armSwing = attackState * 3.0f;
        armSwing = GUtil.max(armSwing, 1.0f);
        if (!player.isRiding()) {
            model.renderRotation.setSmoothY(30.0f, 0.7f);
        }
        final Vector3f bodyRot = new Vector3f(0.0f, 0.0f, 0.0f);
        bodyRot.x = 20.0f - attackState * 20.0f;
        bodyRot.y = -40.0f * attackState + 50.0f * attackState;
        ((ModelRendererBends)model.bipedBody).rotation.setSmooth(bodyRot, 0.9f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY - 30.0f);
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX);
        ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothX(-bodyRot.x, 0.9f);
        ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothY(-bodyRot.y, 0.9f);
        ((ModelRendererBends)model.bipedRightArm).pre_rotation.setSmoothZ(60.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-20.0f + armSwing * 100.0f, 3.0f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothY(0.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(0.0f, 0.9f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(20.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).pre_rotation.setSmoothZ(-80.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothY(0.0f, 0.3f);
        model.bipedRightForeArm.rotation.setSmoothX(-20.0f, 0.3f);
        model.bipedLeftForeArm.rotation.setSmoothX(-60.0f, 0.3f);
        if (data.motion.x == 0.0f & data.motion.z == 0.0f) {
            ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-30.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-30.0f, 0.3f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothY(-25.0f, 0.3f);
            ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f);
            ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f);
            model.bipedRightForeLeg.rotation.setSmoothX(30.0f, 0.3f);
            model.bipedLeftForeLeg.rotation.setSmoothX(30.0f, 0.3f);
            if (!player.isRiding()) {
                model.renderOffset.setSmoothY(-2.0f);
            }
        }
        model.renderItemRotation.setSmoothX(90.0f, 0.9f);
    }
}

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
import net.minecraft.util.*;

public class Animation_Attack_Combo2
{
    public static void animate(final EntityPlayer player, final ModelBendsPlayer model, final Data_Player data) {
        if (data.ticksAfterPunch < 0.5f) {
            model.swordTrail.reset();
        }
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            model.swordTrail.add(model);
        }
        final float attackState = data.ticksAfterPunch / 10.0f;
        float armSwing = attackState * 2.0f;
        armSwing = GUtil.max(armSwing, 1.0f);
        float var5 = attackState * 1.6f;
        var5 = GUtil.max(var5, 1.0f);
        final float var6 = 50.0f + 360.0f * var5;
        float var7;
        for (var7 = 50.0f + 360.0f * var5; var7 > 360.0f; var7 -= 360.0f) {}
        if (var6 > 360.0f) {
            float var8 = (attackState - data.ticksPerFrame / 10.0f) * 2.0f;
            var8 = GUtil.max(var8, 1.0f);
            model.renderRotation.vOld.y = var7;
            model.renderRotation.vFinal.y = var7;
            model.renderRotation.completion.y = 0.0f;
        }
        else {
            model.renderRotation.setSmoothY(var6, 0.7f);
        }
        final Vector3f bodyRot = new Vector3f(0.0f, 0.0f, 0.0f);
        bodyRot.x = 20.0f - attackState * 20.0f;
        bodyRot.y = -40.0f * attackState;
        ((ModelRendererBends)model.bipedBody).rotation.setSmooth(bodyRot, 0.9f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY);
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX);
        ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothX(-bodyRot.x, 0.9f);
        ((ModelRendererBends)model.bipedHead).pre_rotation.setSmoothY(-bodyRot.y, 0.9f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f);
        model.bipedRightForeLeg.rotation.setSmoothX(30.0f, 0.3f);
        model.bipedLeftForeLeg.rotation.setSmoothX(30.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).pre_rotation.setSmoothZ(-(-60.0f - var5 * 80.0f), 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-20.0f + armSwing * 70.0f, 3.0f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothY(0.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothZ(0.0f, 0.9f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothZ(20.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).pre_rotation.setSmoothZ(-80.0f, 0.3f);
        model.bipedRightForeArm.rotation.setSmoothX(-20.0f, 0.3f);
        model.bipedLeftForeArm.rotation.setSmoothX(-60.0f, 0.3f);
        model.renderItemRotation.setSmoothX(90.0f * attackState, 0.9f);
        float var9 = data.ticksAfterPunch * 5.0f;
        float var10 = data.ticksAfterPunch * 5.0f;
        var9 = (MathHelper.cos(var9 * 0.0625f) + 1.0f) / 2.0f * 20.0f;
        var10 = (MathHelper.cos(var10 * 0.0625f) + 1.0f) / 2.0f * 20.0f;
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothY(0.0f, 0.9f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothY(-25.0f, 0.9f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(var9);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-var9);
        model.renderOffset.setSmoothY(-2.0f);
    }
}

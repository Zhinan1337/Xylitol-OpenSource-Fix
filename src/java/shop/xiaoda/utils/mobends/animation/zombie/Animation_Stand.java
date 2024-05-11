//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.zombie;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import shop.xiaoda.utils.mobends.client.model.*;

public class Animation_Stand extends Animation
{
    public String getName() {
        return "stand";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final EntityZombie zombie = (EntityZombie)argEntity;
        final ModelBendsZombie model = (ModelBendsZombie)argModel;
        final Data_Zombie data = (Data_Zombie)argData;
        model.renderOffset.setSmoothY(-3.0f);
        ((ModelRendererBends)model.bipedBody).rotation.setSmoothX(30.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-30.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-20.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-20.0f, 0.3f);
        ((ModelRendererBends)model.bipedRightForeLeg).rotation.setSmoothX(25.0f, 0.3f);
        ((ModelRendererBends)model.bipedLeftForeLeg).rotation.setSmoothX(25.0f, 0.3f);
        ((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX - 30.0f);
        ((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY);
    }
}

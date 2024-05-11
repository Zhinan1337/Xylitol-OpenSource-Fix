//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.pack;

import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import java.util.*;

public class BendsPack
{
    public String filename;
    public String displayName;
    public String author;
    public String description;
    public static List<BendsTarget> targets;
    
    public static BendsTarget getTargetByID(final String argID) {
        for (final BendsTarget target : BendsPack.targets) {
            if (target.mob.equalsIgnoreCase(argID)) {
                return target;
            }
        }
        return null;
    }
    
    public static void animate(final ModelBendsPlayer model, final String target, final String anim) {
        final BendsTarget bendsTarget = getTargetByID(target);
        if (bendsTarget == null) {
            return;
        }
        bendsTarget.applyToModel((ModelRendererBends)model.bipedBody, anim, "body");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedHead, anim, "head");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftArm, anim, "leftArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightArm, anim, "rightArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftLeg, anim, "leftLeg");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightLeg, anim, "rightLeg");
        bendsTarget.applyToModel(model.bipedLeftForeArm, anim, "leftForeArm");
        bendsTarget.applyToModel(model.bipedRightForeArm, anim, "rightForeArm");
        bendsTarget.applyToModel(model.bipedLeftForeLeg, anim, "leftForeLeg");
        bendsTarget.applyToModel(model.bipedRightForeLeg, anim, "rightForeLeg");
        bendsTarget.applyToModel(model.renderItemRotation, anim, "itemRotation");
        bendsTarget.applyToModel(model.renderRotation, anim, "playerRotation");
    }
    
    public static void animate(final ModelBendsZombie model, final String target, final String anim) {
        final BendsTarget bendsTarget = getTargetByID(target);
        if (bendsTarget == null) {
            return;
        }
        bendsTarget.applyToModel((ModelRendererBends)model.bipedBody, anim, "body");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedHead, anim, "head");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftArm, anim, "leftArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightArm, anim, "rightArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftLeg, anim, "leftLeg");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightLeg, anim, "rightLeg");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftForeArm, anim, "leftForeArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightForeArm, anim, "rightForeArm");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedLeftForeLeg, anim, "leftForeLeg");
        bendsTarget.applyToModel((ModelRendererBends)model.bipedRightForeLeg, anim, "rightForeLeg");
    }
    
    public static void animate(final ModelBendsSpider model, final String target, final String anim) {
        final BendsTarget bendsTarget = getTargetByID(target);
        if (bendsTarget == null) {
            return;
        }
        bendsTarget.applyToModel((ModelRendererBends)model.spiderBody, anim, "body");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderNeck, anim, "neck");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderHead, anim, "head");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg1, anim, "leg1");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg2, anim, "leg2");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg3, anim, "leg3");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg4, anim, "leg4");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg5, anim, "leg5");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg6, anim, "leg6");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg7, anim, "leg7");
        bendsTarget.applyToModel((ModelRendererBends)model.spiderLeg8, anim, "leg8");
        bendsTarget.applyToModel(model.spiderForeLeg1, anim, "foreLeg1");
        bendsTarget.applyToModel(model.spiderForeLeg2, anim, "foreLeg2");
        bendsTarget.applyToModel(model.spiderForeLeg3, anim, "foreLeg3");
        bendsTarget.applyToModel(model.spiderForeLeg4, anim, "foreLeg4");
        bendsTarget.applyToModel(model.spiderForeLeg5, anim, "foreLeg5");
        bendsTarget.applyToModel(model.spiderForeLeg6, anim, "foreLeg7");
        bendsTarget.applyToModel(model.spiderForeLeg7, anim, "foreLeg7");
        bendsTarget.applyToModel(model.spiderForeLeg8, anim, "foreLeg8");
    }
    
    static {
        BendsPack.targets = new ArrayList<BendsTarget>();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.pack;

import java.util.*;
import shop.xiaoda.utils.mobends.client.model.*;
import shop.xiaoda.utils.mobends.util.*;

public class BendsTarget
{
    public String mob;
    public List<BendsAction> actions;
    public float visual_DeletePopUp;
    
    public BendsTarget(final String argMob) {
        this.actions = new ArrayList<BendsAction>();
        this.mob = argMob;
        this.visual_DeletePopUp = 0.0f;
    }
    
    public void applyToModel(final ModelRendererBends box, final String anim, final String model) {
        for (int i = 0; i < this.actions.size(); ++i) {
            if ((this.actions.get(i).anim.equalsIgnoreCase(anim) | this.actions.get(i).anim.equalsIgnoreCase("all")) & this.actions.get(i).model.equalsIgnoreCase(model)) {
                if (this.actions.get(i).prop == BendsAction.EnumBoxProperty.ROT) {
                    box.rotation.setSmooth(this.actions.get(i).axis, this.actions.get(i).getNumber((this.actions.get(i).axis == EnumAxis.X) ? box.rotation.vFinal.x : ((this.actions.get(i).axis == EnumAxis.Y) ? box.rotation.vFinal.y : box.rotation.vFinal.z)), this.actions.get(i).smooth);
                }
                else if (this.actions.get(i).prop == BendsAction.EnumBoxProperty.PREROT) {
                    box.pre_rotation.setSmooth(this.actions.get(i).axis, this.actions.get(i).getNumber((this.actions.get(i).axis == EnumAxis.X) ? box.pre_rotation.vFinal.x : ((this.actions.get(i).axis == EnumAxis.Y) ? box.pre_rotation.vFinal.y : box.pre_rotation.vFinal.z)), this.actions.get(i).smooth);
                }
                else if (this.actions.get(i).prop == BendsAction.EnumBoxProperty.SCALE) {
                    if (this.actions.get(i).axis == null | this.actions.get(i).axis == EnumAxis.X) {
                        box.scaleX = this.actions.get(i).getNumber(box.scaleX);
                    }
                    if (this.actions.get(i).axis == null | this.actions.get(i).axis == EnumAxis.Y) {
                        box.scaleY = this.actions.get(i).getNumber(box.scaleY);
                    }
                    if (this.actions.get(i).axis == null | this.actions.get(i).axis == EnumAxis.Z) {
                        box.scaleZ = this.actions.get(i).getNumber(box.scaleZ);
                    }
                }
            }
        }
    }
    
    public void applyToModel(final SmoothVector3f box, final String anim, final String model) {
        for (int i = 0; i < this.actions.size(); ++i) {
            if (((this.actions.get(i).anim.equalsIgnoreCase(anim) | this.actions.get(i).anim.equalsIgnoreCase("all")) & this.actions.get(i).model.equalsIgnoreCase(model)) && this.actions.get(i).prop == BendsAction.EnumBoxProperty.ROT) {
                box.setSmooth(this.actions.get(i).axis, this.actions.get(i).getNumber((this.actions.get(i).axis == EnumAxis.X) ? box.vFinal.x : ((this.actions.get(i).axis == EnumAxis.Y) ? box.vFinal.y : box.vFinal.z)), this.actions.get(i).smooth);
            }
        }
    }
}

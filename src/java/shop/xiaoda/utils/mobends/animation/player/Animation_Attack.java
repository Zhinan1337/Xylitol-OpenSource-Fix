//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.animation.player;

import shop.xiaoda.utils.mobends.animation.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import shop.xiaoda.utils.mobends.data.*;
import net.minecraft.entity.player.*;
import shop.xiaoda.utils.mobends.pack.*;

public class Animation_Attack extends Animation
{
    public String getName() {
        return "attack";
    }
    
    public void animate(final EntityLivingBase argEntity, final ModelBase argModel, final EntityData argData) {
        final ModelBendsPlayer model = (ModelBendsPlayer)argModel;
        final Data_Player data = (Data_Player)argData;
        final EntityPlayer player = (EntityPlayer)argEntity;
        if (player.getCurrentEquippedItem() != null) {
            if (data.ticksAfterPunch < 10.0f) {
                if (data.currentAttack == 1) {
                    Animation_Attack_Combo0.animate((EntityPlayer)argEntity, model, data);
                    BendsPack.animate(model, "player", "attack");
                    BendsPack.animate(model, "player", "attack_0");
                }
                else if (data.currentAttack == 2) {
                    Animation_Attack_Combo1.animate((EntityPlayer)argEntity, model, data);
                    BendsPack.animate(model, "player", "attack");
                    BendsPack.animate(model, "player", "attack_1");
                }
                else if (data.currentAttack == 3) {
                    Animation_Attack_Combo2.animate((EntityPlayer)argEntity, model, data);
                    BendsPack.animate(model, "player", "attack");
                    BendsPack.animate(model, "player", "attack_2");
                }
            }
            else if (data.ticksAfterPunch < 60.0f) {
                Animation_Attack_Stance.animate((EntityPlayer)argEntity, model, data);
                BendsPack.animate(model, "player", "attack_stance");
            }
        }
        else if (data.ticksAfterPunch < 10.0f) {
            Animation_Attack_Punch.animate((EntityPlayer)argEntity, model, data);
            BendsPack.animate(model, "player", "attack");
            BendsPack.animate(model, "player", "punch");
        }
        else if (data.ticksAfterPunch < 60.0f) {
            Animation_Attack_PunchStance.animate((EntityPlayer)argEntity, model, data);
            BendsPack.animate(model, "player", "punch_stance");
        }
    }
}

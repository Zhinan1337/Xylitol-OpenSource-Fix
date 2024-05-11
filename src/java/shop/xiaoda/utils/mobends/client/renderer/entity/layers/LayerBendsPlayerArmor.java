//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class LayerBendsPlayerArmor extends LayerArmorBase<ModelBiped>
{
    public LayerBendsPlayerArmor(final RendererLivingEntity<?> rendererIn) {
        super((RendererLivingEntity)rendererIn);
    }
    
    protected void initArmor() {
        this.modelLeggings = new ModelBendsPlayerArmor(0.5f);
        this.modelArmor = new ModelBendsPlayerArmor(1.5f);
    }
    
    public void doRenderLayer(final EntityLivingBase entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        super.doRenderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale);
    }
    
    protected void setModelPartVisible(final ModelBiped model, final int armorSlot) {
        this.setModelVisible(model);
        switch (armorSlot) {
            case 1: {
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            }
            case 2: {
                model.bipedBody.showModel = true;
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            }
            case 3: {
                model.bipedBody.showModel = true;
                model.bipedRightArm.showModel = true;
                model.bipedLeftArm.showModel = true;
                break;
            }
            case 4: {
                model.bipedHead.showModel = true;
                model.bipedHeadwear.showModel = true;
                break;
            }
        }
    }
    
    protected void setModelVisible(final ModelBiped model) {
        model.setInvisible(false);
    }
}

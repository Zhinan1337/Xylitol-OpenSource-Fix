//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.renderer.entity;

import net.minecraft.client.renderer.entity.*;
import shop.xiaoda.utils.mobends.client.model.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.model.*;

public class RenderBendsSpider extends RenderSpider
{
    public RenderBendsSpider(final RenderManager renderManager) {
        super(renderManager);
        this.mainModel = (ModelBase)new ModelBendsSpider();
    }
    
    protected void preRenderCallback(final EntityLivingBase argEntity, final float partialTickTime) {
        final float f1 = 0.9375f;
        GlStateManager.scale(f1, f1, f1);
        ((ModelBendsSpider)this.mainModel).updateWithEntityData((EntitySpider)argEntity);
        ((ModelBendsSpider)this.mainModel).postRenderTranslate(0.0625f);
        ((ModelBendsSpider)this.mainModel).postRenderRotate(0.0625f);
    }
}

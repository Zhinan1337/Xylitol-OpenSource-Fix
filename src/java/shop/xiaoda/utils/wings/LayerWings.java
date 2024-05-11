//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.wings;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerWings implements LayerRenderer<AbstractClientPlayer>
{
    private static ModelWings modelWings;
    private final RenderPlayer playerRenderer;
    private final ResourceLocation wing;
    
    public LayerWings(final RenderPlayer playerRenderer) {
        this.wing = new ResourceLocation("express/wings.png");
        this.playerRenderer = playerRenderer;
        LayerWings.modelWings = new ModelWings(2);
    }
    
    public void doRenderLayer(final AbstractClientPlayer player, final float v, final float v1, final float v2, final float v3, final float v4, final float v5, final float v6) {
        if (player.isInvisible() || player != Minecraft.getMinecraft().thePlayer) {
            return;
        }
        GlStateManager.pushMatrix();
        this.playerRenderer.getMainModel().bipedBody.postRender(0.0625f);
        if (player.isSneaking()) {
            GlStateManager.translate(0.0, 0.20000000298023224, 0.0);
        }
        LayerWings.modelWings.renderLegacy(0.13f, 0.0625f, this.wing);
        GlStateManager.popMatrix();
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
}

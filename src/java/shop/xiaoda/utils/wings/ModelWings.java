//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.wings;

import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class ModelWings extends ModelBase
{
    private final List<ModelBase> compiledFrames;
    private int frameCount;
    
    public ModelWings(final int n) {
        this.compiledFrames = new ArrayList<ModelBase>();
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.frameCount = n;
        for (int i = 0; i < n; ++i) {
            final ModelBase modelBase = new ModelBase() {};
            modelBase.textureHeight = 256;
            modelBase.textureWidth = 256;
            modelBase.setTextureOffset("wing.skin", -56, 88);
            modelBase.setTextureOffset("wingtip.skin", -56, 144);
            modelBase.setTextureOffset("wing.bone", 112, 88);
            modelBase.setTextureOffset("wingtip.bone", 112, 136);
            final ModelRenderer modelRenderer = new ModelRenderer(modelBase, "wing");
            modelRenderer.setRotationPoint(-12.0f, 5.0f, 2.0f);
            modelRenderer.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
            modelRenderer.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
            final ModelRenderer modelRenderer2 = new ModelRenderer(modelBase, "wingtip");
            modelRenderer2.setRotationPoint(-56.0f, 0.0f, 0.0f);
            modelRenderer2.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
            modelRenderer2.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
            modelRenderer.addChild(modelRenderer2);
            if (n > 1) {
                for (final ModelRenderer modelRenderer3 : modelBase.boxList) {
                    for (final ModelBox modelBox : modelRenderer3.cubeList) {
                        for (int j = 0; j < modelBox.quadList.length; ++j) {
                            final TexturedQuad texturedQuad = modelBox.quadList[j];
                            for (int k = 0; k < texturedQuad.vertexPositions.length; ++k) {
                                final PositionTextureVertex positionTextureVertex = texturedQuad.vertexPositions[k];
                                texturedQuad.vertexPositions[k] = positionTextureVertex.setTexturePosition(positionTextureVertex.texturePositionX, this.mapV(positionTextureVertex.texturePositionY, i * n, n));
                            }
                        }
                    }
                }
            }
            this.compiledFrames.add(modelBase);
        }
    }
    
    private float mapV(final float f, final int n, final int n2) {
        return (float)(f / n2 + Math.floor(n / (float)n2) / n2);
    }
    
    public void renderLegacy(final float f, final float f2, final ResourceLocation resourceLocation) {
        final int n = 1;
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GlStateManager.pushMatrix();
        GlStateManager.scale(f, f, f);
        GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.5f, 0.25f);
        final float f3 = System.currentTimeMillis() % 2000L / 2000.0f * 3.1415927f * 2.0f;
        final ModelBase object = this.compiledFrames.get(n % this.compiledFrames.size());
        final ModelRenderer modelRenderer = object.boxList.get(0);
        final ModelRenderer modelRenderer2 = object.boxList.get(1);
        for (int i = 0; i < 2; ++i) {
            GL11.glEnable(2884);
            modelRenderer.rotateAngleX = -0.125f - MathHelper.cos(f3) * 0.2f;
            modelRenderer.rotateAngleY = 0.75f;
            modelRenderer.rotateAngleZ = (float)(MathHelper.sin(f3) + 0.125) * 0.8f;
            modelRenderer2.rotateAngleZ = (float)(MathHelper.sin(f3 + 2.0f) + 0.5) * 0.75f;
            modelRenderer.render(f2);
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
            if (i == 0) {
                GL11.glCullFace(1028);
            }
            GlStateManager.shadeModel(7424);
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
        GL11.glCullFace(1029);
        GL11.glDisable(2884);
    }
}

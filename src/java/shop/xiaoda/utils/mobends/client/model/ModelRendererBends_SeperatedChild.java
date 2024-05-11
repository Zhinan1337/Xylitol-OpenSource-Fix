//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;

public class ModelRendererBends_SeperatedChild extends ModelRendererBends
{
    ModelRendererBends momModel;
    ModelRendererBends seperatedModel;
    
    public ModelRendererBends_SeperatedChild(final ModelBase argModel) {
        super(argModel);
    }
    
    public ModelRendererBends_SeperatedChild(final ModelBase argModel, final String argName) {
        super(argModel, argName);
    }
    
    public ModelRendererBends_SeperatedChild(final ModelBase argModel, final int argTexOffsetX, final int argTexOffsetY) {
        super(argModel, argTexOffsetX, argTexOffsetY);
    }
    
    public ModelRendererBends_SeperatedChild setMother(final ModelRendererBends argMom) {
        this.momModel = argMom;
        return this;
    }
    
    public ModelRendererBends_SeperatedChild setSeperatedPart(final ModelRendererBends argPart) {
        this.seperatedModel = argPart;
        return this;
    }
    
    public void postRender(final float scale) {
        this.updateBends(scale);
        this.momModel.postRender(scale);
        if (!this.isHidden && this.showModel) {
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    GL11.glRotatef(-this.pre_rotation.getY(), 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(this.pre_rotation.getX(), 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(this.pre_rotation.getZ(), 0.0f, 0.0f, 1.0f);
                }
            }
            else {
                GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                GL11.glRotatef(-this.pre_rotation.getY(), 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getX(), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getZ(), 0.0f, 0.0f, 1.0f);
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
        this.seperatedModel.postRender(scale);
        GL11.glTranslatef(-this.seperatedModel.rotationPointX * scale, -this.seperatedModel.rotationPointY * scale, -this.seperatedModel.rotationPointZ * scale);
    }
}

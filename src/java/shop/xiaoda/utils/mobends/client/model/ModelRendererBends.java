//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model;

import shop.xiaoda.utils.mobends.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.compatibility.util.vector.*;

public class ModelRendererBends extends ModelRenderer
{
    public SmoothVector3f rotation;
    public SmoothVector3f pre_rotation;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    public int txOffsetX;
    public int txOffsetY;
    public boolean compiled;
    private int displayList;
    public boolean showChildIfHidden;
    
    public ModelRendererBends(final ModelBase argModel) {
        super(argModel);
        this.rotation = new SmoothVector3f();
        this.pre_rotation = new SmoothVector3f();
        this.showChildIfHidden = false;
        final float scaleX = 1.0f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
    }
    
    public ModelRendererBends(final ModelBase argModel, final String argName) {
        super(argModel, argName);
        this.rotation = new SmoothVector3f();
        this.pre_rotation = new SmoothVector3f();
        this.showChildIfHidden = false;
        final float scaleX = 1.0f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
    }
    
    public ModelRendererBends(final ModelBase argModel, final int argTexOffsetX, final int argTexOffsetY) {
        super(argModel, argTexOffsetX, argTexOffsetY);
        this.rotation = new SmoothVector3f();
        this.pre_rotation = new SmoothVector3f();
        this.showChildIfHidden = false;
        this.txOffsetX = argTexOffsetX;
        this.txOffsetY = argTexOffsetY;
        final float scaleX = 1.0f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
    }
    
    public void updateBends(final float argTicksPerFrame) {
        this.rotateAngleX = (float)(this.rotation.getX() / 180.0f * 3.141592653589793);
        this.rotateAngleY = (float)(this.rotation.getY() / 180.0f * 3.141592653589793);
        this.rotateAngleZ = (float)(this.rotation.getZ() / 180.0f * 3.141592653589793);
    }
    
    public ModelRendererBends setShowChildIfHidden(final boolean arg0) {
        this.showChildIfHidden = arg0;
        return this;
    }
    
    public void compileDisplayList(final float scale) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(1), 4864);
        final WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        for (int i = 0; i < this.cubeList.size(); ++i) {
            this.cubeList.get(i).render(worldrenderer, scale);
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    public void render(final float p_78785_1_) {
        this.updateBends(p_78785_1_);
        if (!this.compiled) {
            this.compileDisplayList(p_78785_1_);
        }
        GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
        if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
            if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                GL11.glRotatef(-this.pre_rotation.getY(), 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getX(), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getZ(), 0.0f, 0.0f, 1.0f);
                GL11.glScalef(this.scaleX, this.scaleY, this.scaleZ);
                if (!this.isHidden & this.showModel) {
                    GL11.glCallList(this.displayList);
                }
                if ((this.showChildIfHidden || (!this.isHidden & this.showModel)) && this.childModels != null) {
                    for (int i = 0; i < this.childModels.size(); ++i) {
                        this.childModels.get(i).render(p_78785_1_);
                    }
                }
            }
            else {
                GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
                GL11.glRotatef(-this.pre_rotation.getY(), 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getX(), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getZ(), 0.0f, 0.0f, 1.0f);
                GL11.glScalef(this.scaleX, this.scaleY, this.scaleZ);
                if (!this.isHidden & this.showModel) {
                    GL11.glCallList(this.displayList);
                }
                if ((this.showChildIfHidden || (!this.isHidden & this.showModel)) && this.childModels != null) {
                    for (int i = 0; i < this.childModels.size(); ++i) {
                        this.childModels.get(i).render(p_78785_1_);
                    }
                }
                GL11.glTranslatef(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
            }
        }
        else {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
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
            GL11.glScalef(this.scaleX, this.scaleY, this.scaleZ);
            if (!this.isHidden & this.showModel) {
                GL11.glCallList(this.displayList);
            }
            if ((this.showChildIfHidden || (!this.isHidden & this.showModel)) && this.childModels != null) {
                for (int i = 0; i < this.childModels.size(); ++i) {
                    this.childModels.get(i).render(p_78785_1_);
                }
            }
            GL11.glPopMatrix();
        }
        GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
    }
    
    public void update(final float p_78785_1_) {
        this.rotation.update(p_78785_1_);
        this.pre_rotation.update(p_78785_1_);
        this.updateBends(p_78785_1_);
    }
    
    public void renderWithRotation(final float p_78791_1_) {
        this.updateBends(p_78791_1_);
        super.renderWithRotation(p_78791_1_);
    }
    
    public void postRender(final float scale) {
        this.updateBends(scale);
        if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
            if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                GL11.glRotatef(-this.pre_rotation.getY(), 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getX(), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(this.pre_rotation.getZ(), 0.0f, 0.0f, 1.0f);
                GL11.glScalef(this.scaleX, this.scaleY, this.scaleZ);
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
            GL11.glScalef(this.scaleX, this.scaleY, this.scaleZ);
        }
    }
    
    public ModelRendererBends setPosition(final float argX, final float argY, final float argZ) {
        this.rotationPointX = argX;
        this.rotationPointY = argY;
        this.rotationPointZ = argZ;
        return this;
    }
    
    public ModelRendererBends setOffset(final float argX, final float argY, final float argZ) {
        this.offsetX = argX;
        this.offsetY = argY;
        this.offsetZ = argZ;
        return this;
    }
    
    public ModelRendererBends setScale(final float argX, final float argY, final float argZ) {
        this.scaleX = argX;
        this.scaleY = argY;
        this.scaleZ = argZ;
        return this;
    }
    
    public ModelRendererBends resetScale() {
        final float scaleX = 1.0f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
        return this;
    }
    
    public void sync(final ModelRendererBends argBox) {
        if (argBox != null) {
            this.rotateAngleX = argBox.rotateAngleX;
            this.rotateAngleY = argBox.rotateAngleY;
            this.rotateAngleZ = argBox.rotateAngleZ;
            this.rotation.vOld.set((ReadableVector3f)argBox.rotation.vOld);
            this.rotation.completion.set((ReadableVector3f)argBox.rotation.completion);
            this.rotation.vFinal.set((ReadableVector3f)argBox.rotation.vFinal);
            this.rotation.vSmooth.set((ReadableVector3f)argBox.rotation.vSmooth);
            this.rotation.smoothness.set((ReadableVector3f)argBox.rotation.smoothness);
            this.pre_rotation.vOld.set((ReadableVector3f)argBox.pre_rotation.vOld);
            this.pre_rotation.completion.set((ReadableVector3f)argBox.pre_rotation.completion);
            this.pre_rotation.vFinal.set((ReadableVector3f)argBox.pre_rotation.vFinal);
            this.pre_rotation.vSmooth.set((ReadableVector3f)argBox.pre_rotation.vSmooth);
            this.pre_rotation.smoothness.set((ReadableVector3f)argBox.pre_rotation.smoothness);
            this.scaleX = argBox.scaleX;
            this.scaleY = argBox.scaleY;
            this.scaleZ = argBox.scaleZ;
        }
    }
    
    public void addBox(final float p_78790_1_, final float p_78790_2_, final float p_78790_3_, final int width, final int height, final int depth, final float scaleFactor) {
        this.cubeList.add(new ModelBoxBends((ModelRenderer)this, this.txOffsetX, this.txOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, width, height, depth, scaleFactor));
    }
    
    public ModelBoxBends getBox() {
        return (ModelBoxBends) this.cubeList.get(0);
    }
    
    public ModelRendererBends offsetBox(final float argX, final float argY, final float argZ) {
        this.getBox().offset(argX, argY, argZ);
        return this;
    }
    
    public ModelRendererBends offsetBox_Add(final float argX, final float argY, final float argZ) {
        this.getBox().offset_add(argX, argY, argZ);
        return this;
    }
    
    public ModelRendererBends resizeBox(final float argX, final float argY, final float argZ) {
        this.getBox().resize(argX, argY, argZ);
        return this;
    }
    
    public ModelRendererBends updateVertices() {
        this.getBox().updateVertexPositions((ModelRenderer)this);
        this.compiled = false;
        return this;
    }
}

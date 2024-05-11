//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;

public class ModelBoxBends extends ModelBox
{
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float resX;
    public float resY;
    public float resZ;
    public float originalResX;
    public float originalResY;
    public float originalResZ;
    public float txOffsetX;
    public float txOffsetY;
    public float addSize;
    public PositionTextureVertex[] vertices;
    public TexturedQuad[] quads;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    public static final int FRONT = 4;
    public static final int BACK = 5;
    
    public ModelBoxBends(final ModelRenderer server, final int cacheFile, final int p_i1171_3_, float p_i1171_4_, float p_i1171_5_, float p_i1171_6_, final int p_i1171_7_, final int p_i1171_8_, final int p_i1171_9_, final float p_i1171_10_) {
        super(server, p_i1171_9_, p_i1171_9_, p_i1171_10_, p_i1171_10_, p_i1171_10_, p_i1171_9_, p_i1171_9_, p_i1171_9_, p_i1171_10_);
        this.addSize = 0.0f;
        this.offsetX = p_i1171_4_;
        this.offsetY = p_i1171_5_;
        this.offsetZ = p_i1171_6_;
        final float n = (float)p_i1171_7_;
        this.originalResX = n;
        this.resX = n;
        final float n2 = (float)p_i1171_8_;
        this.originalResY = n2;
        this.resY = n2;
        final float n3 = (float)p_i1171_9_;
        this.originalResZ = n3;
        this.resZ = n3;
        this.txOffsetX = (float)cacheFile;
        this.txOffsetY = (float)p_i1171_3_;
        this.vertices = new PositionTextureVertex[8];
        this.quads = new TexturedQuad[6];
        float f4 = p_i1171_4_ + p_i1171_7_;
        float f5 = p_i1171_5_ + p_i1171_8_;
        float f6 = p_i1171_6_ + p_i1171_9_;
        p_i1171_4_ -= p_i1171_10_;
        p_i1171_5_ -= p_i1171_10_;
        p_i1171_6_ -= p_i1171_10_;
        f4 += p_i1171_10_;
        f5 += p_i1171_10_;
        f6 += p_i1171_10_;
        this.addSize = p_i1171_10_;
        if (server.mirror) {
            final float f7 = f4;
            f4 = p_i1171_4_;
            p_i1171_4_ = f7;
        }
        final PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i1171_4_, p_i1171_5_, p_i1171_6_, 0.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex8 = new PositionTextureVertex(f4, p_i1171_5_, p_i1171_6_, 0.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex9 = new PositionTextureVertex(f4, f5, p_i1171_6_, 8.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex10 = new PositionTextureVertex(p_i1171_4_, f5, p_i1171_6_, 8.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex11 = new PositionTextureVertex(p_i1171_4_, p_i1171_5_, f6, 0.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex12 = new PositionTextureVertex(f4, p_i1171_5_, f6, 0.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex13 = new PositionTextureVertex(f4, f5, f6, 8.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex14 = new PositionTextureVertex(p_i1171_4_, f5, f6, 8.0f, 0.0f);
        this.vertices[0] = positiontexturevertex7;
        this.vertices[1] = positiontexturevertex8;
        this.vertices[2] = positiontexturevertex9;
        this.vertices[3] = positiontexturevertex10;
        this.vertices[4] = positiontexturevertex11;
        this.vertices[5] = positiontexturevertex12;
        this.vertices[6] = positiontexturevertex13;
        this.vertices[7] = positiontexturevertex14;
        this.quads[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex12, positiontexturevertex8, positiontexturevertex9, positiontexturevertex13 }, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex11, positiontexturevertex14, positiontexturevertex10 }, cacheFile, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex12, positiontexturevertex11, positiontexturevertex7, positiontexturevertex8 }, cacheFile + p_i1171_9_, p_i1171_3_, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, server.textureWidth, server.textureHeight);
        this.quads[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex9, positiontexturevertex10, positiontexturevertex14, positiontexturevertex13 }, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_7_, p_i1171_3_, server.textureWidth, server.textureHeight);
        this.quads[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex8, positiontexturevertex7, positiontexturevertex10, positiontexturevertex9 }, cacheFile + p_i1171_9_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex11, positiontexturevertex12, positiontexturevertex13, positiontexturevertex14 }, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        if (server.mirror) {
            for (int j1 = 0; j1 < this.quads.length; ++j1) {
                this.quads[j1].flipFace();
            }
        }
    }
    
    public void updateVertexPositions(final ModelRenderer server) {
        float f4 = this.offsetX + this.resX;
        float f5 = this.offsetY + this.resY;
        float f6 = this.offsetZ + this.resZ;
        final int cacheFile = (int)this.txOffsetX;
        final int p_i1171_3_ = (int)this.txOffsetY;
        final int p_i1171_7_ = (int)this.originalResX;
        final int p_i1171_8_ = (int)this.originalResY;
        final int p_i1171_9_ = (int)this.originalResZ;
        float p_x = this.offsetX;
        float p_y = this.offsetY;
        float p_z = this.offsetZ;
        p_x -= this.addSize;
        p_y -= this.addSize;
        p_z -= this.addSize;
        f4 += this.addSize;
        f5 += this.addSize;
        f6 += this.addSize;
        if (server.mirror) {
            final float f7 = f4;
            f4 = p_x;
            p_x = f7;
        }
        final PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_x, p_y, p_z, 0.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex8 = new PositionTextureVertex(f4, p_y, p_z, 0.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex9 = new PositionTextureVertex(f4, f5, p_z, 8.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex10 = new PositionTextureVertex(p_x, f5, p_z, 8.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex11 = new PositionTextureVertex(p_x, p_y, f6, 0.0f, 0.0f);
        final PositionTextureVertex positiontexturevertex12 = new PositionTextureVertex(f4, p_y, f6, 0.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex13 = new PositionTextureVertex(f4, f5, f6, 8.0f, 8.0f);
        final PositionTextureVertex positiontexturevertex14 = new PositionTextureVertex(p_x, f5, f6, 8.0f, 0.0f);
        this.vertices[0] = positiontexturevertex7;
        this.vertices[1] = positiontexturevertex8;
        this.vertices[2] = positiontexturevertex9;
        this.vertices[3] = positiontexturevertex10;
        this.vertices[4] = positiontexturevertex11;
        this.vertices[5] = positiontexturevertex12;
        this.vertices[6] = positiontexturevertex13;
        this.vertices[7] = positiontexturevertex14;
        this.quads[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex12, positiontexturevertex8, positiontexturevertex9, positiontexturevertex13 }, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex11, positiontexturevertex14, positiontexturevertex10 }, cacheFile, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex12, positiontexturevertex11, positiontexturevertex7, positiontexturevertex8 }, cacheFile + p_i1171_9_, p_i1171_3_, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, server.textureWidth, server.textureHeight);
        this.quads[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex9, positiontexturevertex10, positiontexturevertex14, positiontexturevertex13 }, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_7_, p_i1171_3_, server.textureWidth, server.textureHeight);
        this.quads[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex8, positiontexturevertex7, positiontexturevertex10, positiontexturevertex9 }, cacheFile + p_i1171_9_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        this.quads[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex11, positiontexturevertex12, positiontexturevertex13, positiontexturevertex14 }, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_, p_i1171_3_ + p_i1171_9_, cacheFile + p_i1171_9_ + p_i1171_7_ + p_i1171_9_ + p_i1171_7_, p_i1171_3_ + p_i1171_9_ + p_i1171_8_, server.textureWidth, server.textureHeight);
        if (server.mirror) {
            for (int j1 = 0; j1 < this.quads.length; ++j1) {
                this.quads[j1].flipFace();
            }
            p_x = f4;
        }
    }
    
    public void render(final WorldRenderer renderer, final float p_78245_2_) {
        for (int i = 0; i < this.quads.length; ++i) {
            this.quads[i].draw(renderer, p_78245_2_);
        }
    }
    
    public ModelBoxBends offsetTextureQuad(final ModelRenderer argModel, final int argID, final float argX, final float argY) {
        if (argID >= 0 & argID < this.quads.length) {
            final PositionTextureVertex positionTextureVertex = this.quads[argID].vertexPositions[0];
            positionTextureVertex.texturePositionX += argX / argModel.textureWidth;
            final PositionTextureVertex positionTextureVertex2 = this.quads[argID].vertexPositions[1];
            positionTextureVertex2.texturePositionX += argX / argModel.textureWidth;
            final PositionTextureVertex positionTextureVertex3 = this.quads[argID].vertexPositions[2];
            positionTextureVertex3.texturePositionX += argX / argModel.textureWidth;
            final PositionTextureVertex positionTextureVertex4 = this.quads[argID].vertexPositions[3];
            positionTextureVertex4.texturePositionX += argX / argModel.textureWidth;
            final PositionTextureVertex positionTextureVertex5 = this.quads[argID].vertexPositions[0];
            positionTextureVertex5.texturePositionY += argY / argModel.textureHeight;
            final PositionTextureVertex positionTextureVertex6 = this.quads[argID].vertexPositions[1];
            positionTextureVertex6.texturePositionY += argY / argModel.textureHeight;
            final PositionTextureVertex positionTextureVertex7 = this.quads[argID].vertexPositions[2];
            positionTextureVertex7.texturePositionY += argY / argModel.textureHeight;
            final PositionTextureVertex positionTextureVertex8 = this.quads[argID].vertexPositions[3];
            positionTextureVertex8.texturePositionY += argY / argModel.textureHeight;
        }
        return this;
    }
    
    public void resize(final float argX, final float argY, final float argZ) {
        this.resX = argX;
        this.resY = argY;
        this.resZ = argZ;
    }
    
    public void offset(final float argX, final float argY, final float argZ) {
        this.offsetX = argX;
        this.offsetY = argY;
        this.offsetZ = argZ;
    }
    
    public void offset_add(final float argX, final float argY, final float argZ) {
        this.offsetX += argX;
        this.offsetY += argY;
        this.offsetZ += argZ;
    }
}

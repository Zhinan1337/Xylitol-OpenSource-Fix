//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.util;

import org.lwjgl.compatibility.util.vector.*;

public class SmoothVector3f
{
    public Vector3f vOld;
    public Vector3f vSmooth;
    public Vector3f vFinal;
    public Vector3f smoothness;
    public Vector3f completion;
    
    public SmoothVector3f() {
        this.vOld = new Vector3f(0.0f, 0.0f, 0.0f);
        this.vSmooth = new Vector3f(0.0f, 0.0f, 0.0f);
        this.vFinal = new Vector3f(0.0f, 0.0f, 0.0f);
        this.smoothness = new Vector3f(1.0f, 1.0f, 1.0f);
        this.completion = new Vector3f(0.0f, 0.0f, 0.0f);
    }
    
    public void setSmooth(final Vector3f argRotation, final float argSmooth) {
        if (this.vFinal != argRotation) {
            this.vFinal = argRotation;
            this.vOld = this.vSmooth;
            this.completion = new Vector3f(0.0f, 0.0f, 0.0f);
            this.smoothness = new Vector3f(argSmooth, argSmooth, argSmooth);
        }
    }
    
    public void setSmoothZero() {
        this.setSmoothZero(1.0f);
    }
    
    public void setSmoothZero(final float argSmooth) {
        this.setSmooth(new Vector3f(0.0f, 0.0f, 0.0f), argSmooth);
    }
    
    public void setSmooth(final Vector3f argRotation) {
        this.setSmooth(argRotation, 1.0f);
    }
    
    public void setSmooth(final EnumAxis argAxis, final float argRot, final float argSmooth) {
        if (((argAxis == EnumAxis.X) ? this.vFinal.x : ((argAxis == EnumAxis.Y) ? this.vFinal.y : this.vFinal.z)) != argRot) {
            if (argAxis == EnumAxis.X) {
                this.vFinal.x = argRot;
            }
            if (argAxis == EnumAxis.Y) {
                this.vFinal.y = argRot;
            }
            if (argAxis == EnumAxis.Z) {
                this.vFinal.z = argRot;
            }
            if (argAxis == EnumAxis.X) {
                this.vOld.x = this.vSmooth.x;
            }
            if (argAxis == EnumAxis.Y) {
                this.vOld.y = this.vSmooth.y;
            }
            if (argAxis == EnumAxis.Z) {
                this.vOld.z = this.vSmooth.z;
            }
            if (argAxis == EnumAxis.X) {
                this.completion.x = 0.0f;
            }
            if (argAxis == EnumAxis.Y) {
                this.completion.y = 0.0f;
            }
            if (argAxis == EnumAxis.Z) {
                this.completion.z = 0.0f;
            }
        }
        if (argAxis == EnumAxis.X) {
            this.smoothness.x = argSmooth;
        }
        if (argAxis == EnumAxis.Y) {
            this.smoothness.y = argSmooth;
        }
        if (argAxis == EnumAxis.Z) {
            this.smoothness.z = argSmooth;
        }
    }
    
    public void setSmoothX(final float argX, final float argSmooth) {
        if (this.vFinal.x != argX) {
            this.vFinal.x = argX;
            this.vOld.x = this.vSmooth.x;
            this.completion.x = 0.0f;
        }
        this.smoothness.x = argSmooth;
    }
    
    public void setSmoothY(final float argY, final float argSmooth) {
        if (this.vFinal.y != argY) {
            this.vFinal.y = argY;
            this.vOld.y = this.vSmooth.y;
            this.completion.y = 0.0f;
        }
        this.smoothness.y = argSmooth;
    }
    
    public void setSmoothZ(final float argZ, final float argSmooth) {
        if (this.vFinal.z != argZ) {
            this.vFinal.z = argZ;
            this.vOld.z = this.vSmooth.z;
            this.completion.z = 0.0f;
        }
        this.smoothness.z = argSmooth;
    }
    
    public void setSmoothX(final float argX) {
        this.setSmoothX(argX, 0.6f);
    }
    
    public void setSmoothY(final float argY) {
        this.setSmoothY(argY, 0.6f);
    }
    
    public void setSmoothZ(final float argZ) {
        this.setSmoothZ(argZ, 0.6f);
    }
    
    public void setX(final float argX) {
        this.vOld.x = argX;
        this.vSmooth.x = argX;
        this.vFinal.x = argX;
        this.completion.x = 1.0f;
    }
    
    public void setY(final float argY) {
        this.vOld.y = argY;
        this.vSmooth.y = argY;
        this.vFinal.y = argY;
        this.completion.y = 1.0f;
    }
    
    public void setZ(final float argZ) {
        this.vOld.z = argZ;
        this.vSmooth.z = argZ;
        this.vFinal.z = argZ;
        this.completion.z = 1.0f;
    }
    
    public void set(final SmoothVector3f argV) {
        this.completion = argV.completion;
        this.smoothness = argV.smoothness;
        this.vFinal = argV.vFinal;
        this.vOld = argV.vOld;
        this.vSmooth = argV.vSmooth;
    }
    
    public float getX() {
        return this.vSmooth.x;
    }
    
    public float getY() {
        return this.vSmooth.y;
    }
    
    public float getZ() {
        return this.vSmooth.z;
    }
    
    public void update(final float argTicksPerFrame) {
        final Vector3f completion = this.completion;
        completion.x += argTicksPerFrame * this.smoothness.x;
        final Vector3f completion2 = this.completion;
        completion2.y += argTicksPerFrame * this.smoothness.y;
        final Vector3f completion3 = this.completion;
        completion3.z += argTicksPerFrame * this.smoothness.z;
        this.completion = GUtil.max(this.completion, 1.0f);
        if (this.completion.x >= 1.0f) {
            final Vector3f vOld = this.vOld;
            final Vector3f vSmooth = this.vSmooth;
            final float x = this.vFinal.x;
            vSmooth.x = x;
            vOld.x = x;
        }
        else {
            this.vSmooth.x = this.vOld.x + (this.vFinal.x - this.vOld.x) * this.completion.x;
        }
        if (this.completion.y >= 1.0f) {
            final Vector3f vOld2 = this.vOld;
            final Vector3f vSmooth2 = this.vSmooth;
            final float y = this.vFinal.y;
            vSmooth2.y = y;
            vOld2.y = y;
        }
        else {
            this.vSmooth.y = this.vOld.y + (this.vFinal.y - this.vOld.y) * this.completion.y;
        }
        if (this.completion.z >= 1.0f) {
            final Vector3f vOld3 = this.vOld;
            final Vector3f vSmooth3 = this.vSmooth;
            final float z = this.vFinal.z;
            vSmooth3.z = z;
            vOld3.z = z;
        }
        else {
            this.vSmooth.z = this.vOld.z + (this.vFinal.z - this.vOld.z) * this.completion.z;
        }
        this.setSmooth(new Vector3f(0.0f, 0.0f, 0.0f), 0.5f);
    }
}

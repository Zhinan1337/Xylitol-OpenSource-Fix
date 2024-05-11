//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.player;

import net.minecraft.util.*;
import org.lwjgl.compatibility.util.vector.*;

class VecRotation
{
    final Vec3 vec3;
    final Vector2f rotation;
    
    public VecRotation(final Vec3 Vec3, final Vector2f Rotation) {
        this.vec3 = Vec3;
        this.rotation = Rotation;
    }
    
    public Vec3 getVec3() {
        return this.vec3;
    }
    
    public Vector2f getRotation() {
        return this.rotation;
    }
    
    public Vec3 getVec() {
        return this.vec3;
    }
}

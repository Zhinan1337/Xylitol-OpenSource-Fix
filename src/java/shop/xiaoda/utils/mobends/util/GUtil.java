//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.util;

import org.lwjgl.compatibility.util.vector.*;

public class GUtil
{
    public static float max(final float argNum, final float argMax) {
        if (argNum > argMax) {
            return argMax;
        }
        return argNum;
    }
    
    public static Vector3f max(final Vector3f argNum, final float argMax) {
        final Vector3f result = argNum;
        if (argNum.x > argMax) {
            result.x = argMax;
        }
        if (argNum.y > argMax) {
            result.y = argMax;
        }
        if (argNum.z > argMax) {
            result.z = argMax;
        }
        return result;
    }
    
    public static float min(final float argNum, final float argMax) {
        if (argNum < argMax) {
            return argMax;
        }
        return argNum;
    }
    
    public static Vector3f translate(final Vector3f num, final Vector3f move) {
        num.x += move.x;
        num.y += move.y;
        num.z += move.z;
        return num;
    }
    
    public static Vector3f scale(final Vector3f num, final Vector3f move) {
        num.x *= move.x;
        num.y *= move.y;
        num.z *= move.z;
        return num;
    }
    
    public static Vector3f rotateX(Vector3f num, final float rotation) {
        final Vector3f y = new Vector3f();
        final Vector3f z = new Vector3f();
        y.y = (float)Math.cos((180.0f + rotation) / 180.0f * 3.141592653589793);
        y.z = (float)Math.sin((180.0f + rotation) / 180.0f * 3.141592653589793);
        y.normalise();
        final Vector3f vector3f = y;
        vector3f.y *= -num.y;
        final Vector3f vector3f2 = y;
        vector3f2.z *= num.y;
        z.y = (float)Math.sin((180.0f + rotation) / 180.0f * 3.141592653589793);
        z.z = (float)Math.cos((180.0f + rotation) / 180.0f * 3.141592653589793);
        z.normalise();
        final Vector3f vector3f3 = z;
        vector3f3.y *= -num.z;
        final Vector3f vector3f4 = z;
        vector3f4.z *= -num.z;
        num = new Vector3f(num.x, y.y + z.y, y.z + z.z);
        return num;
    }
    
    public static Vector3f rotateY(Vector3f num, final float rotation) {
        final Vector3f x = new Vector3f();
        final Vector3f z = new Vector3f();
        x.x = (float)Math.cos(-rotation / 180.0f * 3.141592653589793);
        x.z = (float)Math.sin(-rotation / 180.0f * 3.141592653589793);
        x.normalise();
        final Vector3f vector3f = x;
        vector3f.x *= -num.x;
        final Vector3f vector3f2 = x;
        vector3f2.z *= num.x;
        z.x = (float)Math.sin(-rotation / 180.0f * 3.141592653589793);
        z.z = (float)Math.cos(-rotation / 180.0f * 3.141592653589793);
        z.normalise();
        final Vector3f vector3f3 = z;
        vector3f3.x *= num.z;
        final Vector3f vector3f4 = z;
        vector3f4.z *= num.z;
        num = new Vector3f(x.x + z.x, num.y, x.z + z.z);
        return num;
    }
    
    public static Vector3f rotateZ(Vector3f num, final float rotation) {
        final Vector3f x = new Vector3f();
        final Vector3f y = new Vector3f();
        x.x = (float)Math.sin((rotation - 90.0f) / 180.0f * 3.141592653589793);
        x.y = (float)Math.cos((rotation - 90.0f) / 180.0f * 3.141592653589793);
        x.normalise();
        final Vector3f vector3f = x;
        vector3f.x *= -num.x;
        final Vector3f vector3f2 = x;
        vector3f2.y *= num.x;
        y.x = (float)Math.cos((rotation - 90.0f) / 180.0f * 3.141592653589793);
        y.y = (float)Math.sin((rotation - 90.0f) / 180.0f * 3.141592653589793);
        y.normalise();
        final Vector3f vector3f3 = y;
        vector3f3.x *= -num.y;
        final Vector3f vector3f4 = y;
        vector3f4.y *= -num.y;
        num = new Vector3f(y.x + x.x, y.y + x.y, num.z);
        return num;
    }
    
    public static Vector3f[] translate(final Vector3f[] nums, final Vector3f move) {
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = translate(nums[i], move);
        }
        return nums;
    }
    
    public static Vector3f[] scale(final Vector3f[] nums, final Vector3f move) {
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = scale(nums[i], move);
        }
        return nums;
    }
    
    public static Vector3f[] rotateX(final Vector3f[] nums, final float move) {
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = rotateX(nums[i], move);
        }
        return nums;
    }
    
    public static Vector3f[] rotateY(final Vector3f[] nums, final float move) {
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = rotateY(nums[i], move);
        }
        return nums;
    }
    
    public static Vector3f[] rotateZ(final Vector3f[] nums, final float move) {
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = rotateZ(nums[i], move);
        }
        return nums;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation;

public class AnimationUtils
{
    public static float clamp(final float number, final float min, final float max) {
        return (number < min) ? min : Math.min(number, max);
    }
    
    public static double animate(final double target, double current, double speed) {
        if (current == target) {
            return current;
        }
        final boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
            if (current >= target) {
                current = target;
            }
        }
        else {
            current -= factor;
            if (current <= target) {
                current = target;
            }
        }
        return current;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import java.util.function.*;
import net.minecraft.util.*;

public class SmoothScrollingEverywhere
{
    private static Function<Double, Double> easingMethod;
    
    public static Function<Double, Double> getEasingMethod() {
        return SmoothScrollingEverywhere.easingMethod;
    }
    
    public static long getScrollDuration() {
        return 600L;
    }
    
    public static float getScrollStep() {
        return 40.0f;
    }
    
    public static float getBounceBackMultiplier() {
        return 0.24f;
    }
    
    public static boolean isUnlimitFps() {
        return true;
    }
    
    public static float handleScrollingPosition(final float[] target, final float scroll, final float maxScroll, final float delta, final double start, final double duration) {
        if (getBounceBackMultiplier() >= 0.0f) {
            target[0] = clamp(target[0], maxScroll);
            if (target[0] < 0.0f) {
                final int n = 0;
                target[n] -= target[0] * (1.0f - getBounceBackMultiplier()) * delta / 3.0f;
            }
            else if (target[0] > maxScroll) {
                target[0] = (target[0] - maxScroll) * (1.0f - (1.0f - getBounceBackMultiplier()) * delta / 3.0f) + maxScroll;
            }
        }
        else {
            target[0] = clamp(target[0], maxScroll, 0.0f);
        }
        if (!Precision.almostEquals(scroll, target[0], 0.001f)) {
            return expoEase(scroll, target[0], Math.min((System.currentTimeMillis() - start) / duration * delta * 3.0, 1.0));
        }
        return target[0];
    }
    
    public static float expoEase(final float start, final float end, final double amount) {
        return start + (end - start) * getEasingMethod().apply(amount).floatValue();
    }
    
    public static double clamp(final double v, final double maxScroll) {
        return clamp(v, maxScroll, 300.0);
    }
    
    public static double clamp(final double v, final double maxScroll, final double clampExtension) {
        return MathHelper.clamp_double(v, -clampExtension, maxScroll + clampExtension);
    }
    
    public static float clamp(final float v, final float maxScroll) {
        return clamp(v, maxScroll, 300.0f);
    }
    
    public static float clamp(final float v, final float maxScroll, final float clampExtension) {
        return MathHelper.clamp_float(v, -clampExtension, maxScroll + clampExtension);
    }
    
    static {
        SmoothScrollingEverywhere.easingMethod = (Function<Double, Double>)(v -> v);
    }
    
    private static class Precision
    {
        public static final float FLOAT_EPSILON = 0.001f;
        public static final double DOUBLE_EPSILON = 1.0E-7;
        
        public static boolean almostEquals(final float value1, final float value2, final float acceptableDifference) {
            return Math.abs(value1 - value2) <= acceptableDifference;
        }
        
        public static boolean almostEquals(final double value1, final double value2, final double acceptableDifference) {
            return Math.abs(value1 - value2) <= acceptableDifference;
        }
    }
}

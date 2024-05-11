//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import shop.xiaoda.module.modules.render.*;

public class AnimationUtil
{
    private long previousTime;
    public static long deltaTime;
    
    public AnimationUtil() {
        this.previousTime = System.nanoTime() / 10000L;
    }
    
    public void resetTime() {
        final long currentTime = System.nanoTime() / 10000L;
        AnimationUtil.deltaTime = currentTime - this.previousTime;
        this.previousTime = currentTime;
    }
    
    public double animate(final double value, final double target, double speed, final boolean tBTV) {
        if (AnimationUtil.deltaTime <= 1L) {
            AnimationUtil.deltaTime = 500L;
        }
        speed = speed * (double)HUD.animationSpeed.getValue() / 4.0;
        final double increment = speed * AnimationUtil.deltaTime / 500.0;
        final double returnValue = value + (target - value) * increment / 200.0;
        if (Math.abs(target - returnValue) >= 0.05 * (4.0 / (double)HUD.animationSpeed.getValue())) {
            if (tBTV) {
                if (returnValue > target) {
                    return target;
                }
            }
            else if (target > returnValue) {
                return target;
            }
            return returnValue;
        }
        return target;
    }
    
    public double animateNoFast(final double value, final double target, double speed) {
        if (AnimationUtil.deltaTime <= 1L) {
            AnimationUtil.deltaTime = 500L;
        }
        speed = speed * (double)HUD.animationSpeed.getValue() / 4.0;
        final double increment = speed * AnimationUtil.deltaTime / 500.0;
        final double returnValue = value + (target - value) * increment / 200.0;
        if (Math.abs(target - returnValue) < 0.05 * (4.0 / (double)HUD.animationSpeed.getValue())) {
            return target;
        }
        return returnValue;
    }
    
    public static float animate(final float value, final float target, float speed) {
        if (AnimationUtil.deltaTime <= 1L) {
            AnimationUtil.deltaTime = 500L;
        }
        speed *= 70.0f;
        final float increment = speed * AnimationUtil.deltaTime / 500.0f;
        final float returnValue = value + (target - value) * increment / 200.0f;
        if (Math.abs(target - returnValue) < 0.05 * (4.0 / (double)HUD.animationSpeed.getValue())) {
            return target;
        }
        return returnValue;
    }
    
    public double animateRO(final double value, final double target, final double speed) {
        if (AnimationUtil.deltaTime <= 1L) {
            AnimationUtil.deltaTime = 500L;
        }
        final double increment = speed * AnimationUtil.deltaTime / 500.0;
        final double returnValue = value + (target - value) * increment / 200.0;
        if (Math.abs(target - returnValue) < 0.05) {
            return target;
        }
        return returnValue;
    }
    
    public static float animateSmooth(float current, final float target, float speed) {
        if (current == target) {
            return current;
        }
        final boolean larger = target > current;
        if (speed < 0.0f) {
            speed = 0.0f;
        }
        else if (speed > 1.0f) {
            speed = 1.0f;
        }
        final double dif = Math.max(target, (double)current) - Math.min(target, (double)current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += (float)factor;
            if (current >= target) {
                current = target;
            }
        }
        else if (target < current) {
            current -= (float)factor;
            if (current <= target) {
                current = target;
            }
        }
        return current;
    }
    
    static {
        AnimationUtil.deltaTime = 500L;
    }
}

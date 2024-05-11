//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render.animation;

public class AnimTimeUtil
{
    public long lastMS;
    
    public AnimTimeUtil() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public boolean hasTimeElapsed(final long time) {
        return System.currentTimeMillis() - this.lastMS > time;
    }
    
    public boolean hasTimeElapsed(final double time) {
        return this.hasTimeElapsed((long)time);
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }
    
    public void setTime(final long time) {
        this.lastMS = time;
    }
}

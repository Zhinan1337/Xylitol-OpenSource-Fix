//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client;

import java.util.*;

public class StopWatch
{
    private long millis;
    
    public void setMillis(final long millis) {
        this.millis = millis;
    }
    
    public static long randomDelay(final int minDelay, final int maxDelay) {
        return nextInt(minDelay, maxDelay);
    }
    
    public static int nextInt(final int startInclusive, final int endExclusive) {
        return (endExclusive - startInclusive <= 0) ? startInclusive : (startInclusive + new Random().nextInt(endExclusive - startInclusive));
    }
    
    public StopWatch() {
        this.reset();
    }
    
    public boolean finished(final long delay) {
        return System.currentTimeMillis() - delay >= this.millis;
    }
    
    public void reset() {
        this.millis = System.currentTimeMillis();
    }
    
    public long getMillis() {
        return this.millis;
    }
    
    public long getElapsedTime() {
        return System.currentTimeMillis() - this.millis;
    }
}

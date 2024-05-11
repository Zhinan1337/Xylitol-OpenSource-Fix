//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.misc;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.world.*;
import shop.xiaoda.utils.system.*;
import shop.xiaoda.event.*;

public class MemoryFix extends Module
{
    private final NumberValue cleanUpDelay;
    private final NumberValue cleanUpLimit;
    private final TimeUtil cleanUpDelayTime;
    
    public MemoryFix() {
        super("MemoryFix", Category.Misc);
        this.cleanUpDelay = new NumberValue("CleanUpDelay", 120.0, 10.0, 600.0, 1.0);
        this.cleanUpLimit = new NumberValue("CleanUpLimit", 80.0, 20.0, 95.0, 1.0);
        this.cleanUpDelayTime = new TimeUtil();
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        final long maxMem = Runtime.getRuntime().maxMemory();
        final long totalMem = Runtime.getRuntime().totalMemory();
        final long freeMem = Runtime.getRuntime().freeMemory();
        final long usedMem = totalMem - freeMem;
        if (this.cleanUpDelayTime.hasReached(this.cleanUpDelay.getValue() * 1000.0) && this.cleanUpLimit.getValue() <= (float)(usedMem * 100L / maxMem)) {
            MemoryUtils.memoryCleanup();
            this.cleanUpDelayTime.reset();
        }
    }
}

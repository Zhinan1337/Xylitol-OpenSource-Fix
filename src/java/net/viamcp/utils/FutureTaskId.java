//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.utils;

import com.viaversion.viaversion.api.platform.*;
import java.util.concurrent.*;

public class FutureTaskId implements PlatformTask<Future<?>>
{
    private final Future<?> object;
    
    public FutureTaskId(final Future<?> object) {
        this.object = object;
    }
    
    public Future<?> getObject() {
        return this.object;
    }
    
    public void cancel() {
        this.object.cancel(false);
    }
}

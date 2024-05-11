//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.api.events.callables;

import shop.xiaoda.event.api.events.*;

public abstract class EventCancellable implements Event, Cancellable
{
    private boolean cancelled;
    
    protected EventCancellable() {
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
    
    @Override
    public void setCancelled() {
        this.cancelled = true;
    }
}

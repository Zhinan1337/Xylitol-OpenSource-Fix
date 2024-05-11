//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.callables.*;

public class EventPlace extends EventCancellable
{
    private boolean shouldRightClick;
    private int slot;
    
    public EventPlace(final int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public void setSlot(final int slot) {
        this.slot = slot;
    }
    
    public boolean isShouldRightClick() {
        return this.shouldRightClick;
    }
    
    public void setShouldRightClick(final boolean shouldRightClick) {
        this.shouldRightClick = shouldRightClick;
    }
}

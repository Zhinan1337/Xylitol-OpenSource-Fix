//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.misc;

import shop.xiaoda.event.api.events.callables.*;

public class EventDisplayChest extends EventCancellable
{
    private String string;
    
    public EventDisplayChest(final String sb) {
        this.string = sb;
    }
    
    public String getString() {
        return this.string;
    }
}

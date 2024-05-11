//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.misc;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.util.*;

public class EventChatComponent extends EventCancellable
{
    private final IChatComponent chatComponent;
    
    public EventChatComponent(final IChatComponent icc) {
        this.chatComponent = icc;
    }
    
    public IChatComponent getChatComponent() {
        return this.chatComponent;
    }
}

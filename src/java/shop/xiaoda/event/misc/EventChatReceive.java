//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.misc;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.util.*;

public class EventChatReceive extends EventCancellable
{
    public final byte type;
    public IChatComponent message;
    
    public EventChatReceive(final byte type, final IChatComponent message) {
        this.type = type;
        this.message = message;
    }
}

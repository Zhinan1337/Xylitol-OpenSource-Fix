//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.entity.*;

public class EventRenderNameTag extends EventCancellable
{
    private static Entity target;
    
    public EventRenderNameTag(final Entity entity) {
        EventRenderNameTag.target = entity;
    }
    
    public Entity getTarget() {
        return EventRenderNameTag.target;
    }
}

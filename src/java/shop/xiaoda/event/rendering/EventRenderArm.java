//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.callables.*;
import net.minecraft.entity.*;

public class EventRenderArm extends EventCancellable
{
    private final Entity entity;
    private final boolean pre;
    
    public EventRenderArm(final Entity entity, final boolean pre) {
        this.entity = entity;
        this.pre = pre;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.attack;

import shop.xiaoda.event.api.events.*;
import net.minecraft.entity.*;

public class EventAttack implements Event
{
    private final boolean pre;
    private Entity target;
    
    public EventAttack(final Entity entity, final boolean pre) {
        this.target = entity;
        this.pre = pre;
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    public void setTarget(final Entity target) {
        this.target = target;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
}

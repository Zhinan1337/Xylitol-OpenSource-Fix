//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.*;
import net.minecraft.client.multiplayer.*;

public class EventWorldLoad implements Event
{
    private final WorldClient world;
    
    public EventWorldLoad(final WorldClient world) {
        this.world = world;
    }
    
    public WorldClient getWorld() {
        return this.world;
    }
}

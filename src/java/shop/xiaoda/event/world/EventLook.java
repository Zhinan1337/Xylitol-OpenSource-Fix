//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.world;

import shop.xiaoda.event.api.events.*;
import org.lwjgl.compatibility.util.vector.*;

public class EventLook implements Event
{
    private Vector2f rotation;
    
    public EventLook(Vector2f rotation) {
        this.rotation = rotation;
    }
    
    public Vector2f getRotation() {
        return this.rotation;
    }
    
    public void setRotation(final Vector2f rotation) {
        this.rotation = rotation;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.*;
import net.minecraft.client.gui.*;

public class EventRender2D implements Event
{
    private float partialTicks;
    private ScaledResolution scaledResolution;
    
    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }
}

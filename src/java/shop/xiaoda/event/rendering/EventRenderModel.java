//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.rendering;

import shop.xiaoda.event.api.events.*;
import net.minecraft.entity.*;

public class EventRenderModel implements Event
{
    private boolean pre;
    private final EntityLivingBase entity;
    private final Runnable modelRenderer;
    private final Runnable layerRenderer;
    
    public EventRenderModel(final EntityLivingBase entity, final Runnable modelRenderer, final Runnable layerRenderer) {
        this.pre = true;
        this.entity = entity;
        this.modelRenderer = modelRenderer;
        this.layerRenderer = layerRenderer;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public void setPost() {
        this.pre = false;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
    
    public void drawModel() {
        this.modelRenderer.run();
    }
    
    public void drawLayers() {
        this.layerRenderer.run();
    }
}

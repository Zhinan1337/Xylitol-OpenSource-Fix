//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import net.minecraft.client.shader.*;
import shop.xiaoda.module.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.api.events.*;
import shop.xiaoda.utils.render.shader.*;
import shop.xiaoda.module.modules.world.*;
import shop.xiaoda.*;
import shop.xiaoda.module.modules.movement.*;
import shop.xiaoda.utils.render.*;

public class PostProcessing extends Module
{
    public final BoolValue blur;
    private final NumberValue iterations;
    private final NumberValue offset;
    private final BoolValue bloom;
    private final NumberValue shadowRadius;
    private final NumberValue shadowOffset;
    private String currentMode;
    private Framebuffer stencilFramebuffer;
    
    public PostProcessing() {
        super("PostProcessing", Category.Render);
        this.blur = new BoolValue("Blur", true);
        this.iterations = new NumberValue("Blur Iterations", 2.0, 1.0, 8.0, 1.0);
        this.offset = new NumberValue("Blur Offset", 3.0, 1.0, 10.0, 1.0);
        this.bloom = new BoolValue("Bloom", true);
        this.shadowRadius = new NumberValue("Bloom Iterations", 3.0, 1.0, 8.0, 1.0);
        this.shadowOffset = new NumberValue("Bloom Offset", 1.0, 1.0, 10.0, 1.0);
        this.stencilFramebuffer = new Framebuffer(1, 1, false);
    }
    
    public void blurScreen() {
        if (!this.getState()) {
            return;
        }
        if (this.blur.getValue()) {
            (this.stencilFramebuffer = RenderUtil.createFrameBuffer(this.stencilFramebuffer)).framebufferClear();
            this.stencilFramebuffer.bindFramebuffer(false);
            final EventShader eventShader = new EventShader(false);
            EventManager.call((Event)eventShader);
            RenderUtil.resetColor();
            this.drawBlur();
            RenderUtil.resetColor();
            this.stencilFramebuffer.unbindFramebuffer();
            KawaseBlur.renderBlur(this.stencilFramebuffer.framebufferTexture, this.iterations.getValue().intValue(), this.offset.getValue().intValue());
        }
        if (this.bloom.getValue()) {
            (this.stencilFramebuffer = RenderUtil.createFrameBuffer(this.stencilFramebuffer)).framebufferClear();
            this.stencilFramebuffer.bindFramebuffer(false);
            final EventShader eventShader = new EventShader(true);
            EventManager.call((Event)eventShader);
            RenderUtil.resetColor();
            this.drawBloom();
            RenderUtil.resetColor();
            this.stencilFramebuffer.unbindFramebuffer();
            KawaseBloom.renderBlur(this.stencilFramebuffer.framebufferTexture, this.shadowRadius.getValue().intValue(), this.shadowOffset.getValue().intValue());
        }
    }
    
    private void drawBloom() {
        final Scaffold Scaffold = Client.instance.moduleManager.getModule(Scaffold.class);
        if (Scaffold.blockCount.getValue() && Scaffold.shadow.getValue()) {
            switch (Scaffold.counterMode.getValue()) {
                case Simple: {
                    ScaffoldCounter.drawDefaultBloom();
                    break;
                }
                default: {
                    ScaffoldCounter.drawClassicBloom();
                    break;
                }
            }
        }
    }
    
    private void drawBlur() {
        final Scaffold Scaffold = (Scaffold)Client.instance.moduleManager.getModule((Class)Scaffold.class);
        if (Scaffold.blockCount.getValue() && Scaffold.blur.getValue()) {
            switch (Scaffold.counterMode.getValue()) {
                case Simple: {
                    ScaffoldCounter.drawDefaultBloom();
                    break;
                }
                case Classic: {
                    ScaffoldCounter.drawClassicBloom();
                    break;
                }
            }
        }
    }
}

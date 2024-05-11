//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.clickgui.book;

import org.lwjgl.opengl.*;
import shop.xiaoda.utils.render.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RippleAnimation
{
    public final List<Ripple> ripples;
    
    public RippleAnimation() {
        this.ripples = new ArrayList<Ripple>();
    }
    
    public void addRipple(final float x, final float y, final float radius, final float speed) {
        this.ripples.add(new Ripple(x, y, radius, speed));
    }
    
    public void mouseClicked(final float mouseX, final float mouseY, final float speed) {
        this.ripples.add(new Ripple(mouseX, mouseY, 100.0f, speed));
    }
    
    public void mouseClicked(final float mouseX, final float mouseY) {
        this.ripples.add(new Ripple(mouseX, mouseY, 100.0f, 1.0f));
    }
    
    public void draw(final float x, final float y, final float width, final float height) {
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        GL11.glDepthFunc(519);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glColorMask(false, false, false, false);
        RenderUtil.drawRect(x, y, width, height, -1);
        GL11.glDepthMask(false);
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthFunc(514);
        for (final Ripple c : this.ripples) {
            c.progress = AnimationUtil.animateSmooth(c.progress, c.topRadius, c.speed / 50.0f);
            RenderUtil.drawCircle2(c.x, c.y, c.progress, new Color(1.0f, 1.0f, 1.0f, (1.0f - Math.min(1.0f, Math.max(0.0f, c.progress / c.topRadius))) / 2.0f).getRGB());
        }
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        GL11.glDepthFunc(515);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
    }
    
    public void draw(final Runnable context) {
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        GL11.glDepthFunc(519);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glColorMask(false, false, false, false);
        context.run();
        GL11.glDepthMask(false);
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthFunc(514);
        for (final Ripple c : this.ripples) {
            c.progress = AnimationUtil.animateSmooth(c.progress, c.topRadius, c.speed / 50.0f);
            RenderUtil.drawCircle2(c.x, c.y, c.progress, new Color(1.0f, 1.0f, 1.0f, (1.0f - Math.min(1.0f, Math.max(0.0f, c.progress / c.topRadius))) / 2.0f).getRGB());
        }
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        GL11.glDepthFunc(515);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
    }
    
    public static class Ripple
    {
        public float x;
        public float y;
        public float topRadius;
        public float speed;
        public float alpha;
        public float progress;
        public boolean complete;
        
        public Ripple(final float x, final float y, final float rad, final float speed) {
            this.x = x;
            this.y = y;
            this.alpha = 200.0f;
            this.topRadius = rad;
            this.speed = speed;
        }
    }
}

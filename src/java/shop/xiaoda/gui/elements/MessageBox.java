//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.elements;

import shop.xiaoda.gui.clickgui.book.*;
import shop.xiaoda.utils.render.animation.impl.*;
import shop.xiaoda.utils.render.animation.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.utils.render.shader.*;
import shop.xiaoda.utils.render.fontRender.*;

public class MessageBox
{
    public float x;
    public float y;
    public String message;
    public float width;
    public float height;
    public Animation anim;
    private Animation hoverAnimation;
    private RippleAnimation ripple;
    
    public MessageBox(final String message, final float width, final float height) {
        this.anim = new DecelerateAnimation(200, 1.0);
        this.hoverAnimation = new DecelerateAnimation(300, 1.0);
        this.ripple = new RippleAnimation();
        this.message = message;
        this.width = width;
        this.height = height;
    }
    
    public void initGui() {
        this.anim.setDirection(Direction.BACKWARDS);
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.x = sr.getScaledWidth() / 2.0f - this.width / 2.0f;
        this.y = sr.getScaledHeight() / 2.0f - this.height / 2.0f;
    }
    
    public void draw(final int mouseX, final int mouseY) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        RenderUtil.scaleStart(sr.getScaledWidth() / 2.0f, sr.getScaledHeight() / 2.0f, (float)this.anim.getOutput());
        KawaseBloom.shadow2(() -> RoundedUtils.drawRound(this.x, this.y, this.width, this.height, 4.0f, new Color(0, 0, 0, 230)));
        RoundedUtils.drawRound(this.x, this.y, this.width, this.height, 4.0f, new Color(255, 255, 255, 255));
        FontManager.arial16.drawString("Window", this.x + 5.0f, this.y + 4.0f, 0);
        FontManager.arial16.drawString(this.message, this.x + this.width / 2.0f - FontManager.arial16.getStringWidth(this.message) / 2, this.y + this.height / 2.0f - 7.0f, 0);
        final boolean hovered = RenderUtil.isHovering(this.x + 4.0f, this.y + this.height - 20.0f, this.width - 8.0f, 14.0f, mouseX, mouseY);
        this.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        RoundedUtils.drawRound(this.x + 4.0f, this.y + this.height - 20.0f, this.width - 8.0f, 14.0f, 3.0f, new Color(0, 0, 0, (int)(this.hoverAnimation.getOutput() * 50.0)));
        this.ripple.draw(() -> RoundedUtils.drawRound(this.x + 4.0f, this.y + this.height - 20.0f, this.width - 8.0f, 14.0f, 3.0f, new Color(255, 255, 255, 255)));
        FontManager.arial16.drawString("\u786e\u5b9a", this.x + this.width / 2.0f - FontManager.arial16.getStringWidth("\u786e\u5b9a") / 2, this.y + this.height - 16.0f, 0);
        RenderUtil.scaleEnd();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.ripple.mouseClicked((float)mouseX, (float)mouseY);
        if (this.anim.finished(Direction.FORWARDS) && RenderUtil.isHovering(this.x + this.width / 2.0f - this.width / 4.0f, this.y + this.height - 18.0f, this.x + this.width / 2.0f + this.width / 4.0f, this.y + this.height - 3.0f, mouseX, mouseY) && mouseButton == 0) {
            this.anim.setDirection(Direction.BACKWARDS);
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.elements;

import shop.xiaoda.utils.client.menu.*;
import shop.xiaoda.gui.clickgui.book.*;
import java.awt.*;
import shop.xiaoda.utils.client.*;
import shop.xiaoda.utils.render.animation.impl.*;
import shop.xiaoda.utils.misc.*;
import shop.xiaoda.utils.render.animation.*;
import shop.xiaoda.utils.render.*;
import shop.xiaoda.utils.render.shader.*;
import shop.xiaoda.utils.render.fontRender.*;

public class MDUIButton implements Screen
{
    public final String text;
    public final int id;
    private Animation hoverAnimation;
    public float x;
    public float y;
    public float width;
    public float height;
    public Runnable clickAction;
    private RippleAnimation ripple;
    public final Color color;
    private TimeUtil timer;
    
    public MDUIButton(final String text, final int id, final Color color) {
        this.ripple = new RippleAnimation();
        this.timer = new TimeUtil();
        this.text = text;
        this.id = id;
        this.color = color;
    }
    
    @Override
    public void initGui() {
        this.hoverAnimation = new DecelerateAnimation(300, 1.0);
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        final boolean hovered = MouseUtils.isHovering(this.x, this.y, this.width, this.height, mouseX, mouseY);
        this.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        KawaseBloom.shadow(() -> RoundedUtils.drawRound(this.x, this.y + 2.0f, this.width, this.height - 2.0f, 4.0f, new Color(0, 0, 0, (int)(200.0 * this.hoverAnimation.getOutput()))));
        RoundedUtils.drawRound(this.x, this.y, this.width, this.height, 4.0f, this.color);
        this.ripple.draw(() -> RoundedUtils.drawRound(this.x, this.y, this.width, this.height, 4.0f, new Color(255, 255, 255)));
        FontManager.arial20.drawCenteredString(this.text, this.x + this.width / 2.0f, this.y + FontManager.arial20.getMiddleOfBox(this.height) + 2.0f, -1);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final boolean hovered = MouseUtils.isHovering(this.x, this.y, this.width, this.height, mouseX, mouseY);
        this.ripple.mouseClicked((float)mouseX, (float)mouseY);
        if (hovered && this.timer.hasReached(200.0)) {
            this.clickAction.run();
            this.timer.reset();
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
}

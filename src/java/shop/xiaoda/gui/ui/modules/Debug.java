//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui.modules;

import shop.xiaoda.gui.ui.*;
import shop.xiaoda.event.rendering.*;
import shop.xiaoda.utils.render.*;
import java.awt.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.event.*;

public class Debug extends UiModule
{
    public Debug() {
        super("Debug", 20.0, 60.0, 150.0, 200.0);
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        final EntityPlayerSP player = Debug.mc.thePlayer;
        double x = this.posX * RenderUtil.width();
        double y = this.posY * RenderUtil.height();
        final RapeMasterFontManager A16 = FontManager.arial16;
        RenderUtil.drawRect(x, y, x + 120.0, y + 200.0, new Color(20, 20, 20, 180).getRGB());
        x += 5.0;
        y -= 5.0;
        A16.drawStringWithShadow("Health: " + this.toFloat(player.getHealth()), x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("X:" + this.toFloat(player.posX) + " Y:" + this.toFloat(player.posY) + " Z:" + this.toFloat(player.posZ), x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("Motion X:" + this.toDouble(player.motionX) + " Y:" + this.toDouble(player.motionY) + " Z:" + this.toDouble(player.motionZ), x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("Hurt Time: " + player.hurtTime, x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("Hurt ResistantTime Time: " + player.hurtResistantTime, x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("Yaw: " + this.toFloat(player.rotationYaw) + " Pitch" + this.toFloat(player.rotationPitch), x, y += 10.0, new Color(255, 255, 255).getRGB());
        A16.drawStringWithShadow("Head: " + this.toFloat(player.rotationYawHead) + " Body: " + this.toFloat(player.renderYawOffset), x, y += 10.0, new Color(255, 255, 255).getRGB());
    }
    
    private float toFloat(final double value) {
        return (int)value + (int)(value * 10.0) % 10 / 10.0f;
    }
    
    private float toDouble(final double value) {
        return (int)value + (int)(value * 100.0) % 100 / 100.0f;
    }
}

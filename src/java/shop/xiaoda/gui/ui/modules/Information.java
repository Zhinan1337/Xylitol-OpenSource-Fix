//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.ui.modules;

import shop.xiaoda.gui.ui.*;
import shop.xiaoda.event.rendering.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.*;
import shop.xiaoda.utils.render.fontRender.*;
import shop.xiaoda.event.*;

public class Information extends UiModule
{
    public Information() {
        super("Information", 50.0, 20.0, 100.0, 20.0);
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        final RapeMasterFontManager f18 = FontManager.arial18;
        final double positionX = this.getPosX();
        double positionY = this.getPosY() + 12.0;
        final ArrayList<Integer> positionLongList = new ArrayList<Integer>();
        positionLongList.add(f18.getStringWidth("X: " + (int)Information.mc.thePlayer.posX + " "));
        positionLongList.add(f18.getStringWidth("Y: " + (int)Information.mc.thePlayer.posY + " "));
        positionLongList.add(f18.getStringWidth("Z: " + (int)Information.mc.thePlayer.posZ + " "));
        final int mainTextColor = new Color(160, 160, 160).getRGB();
        f18.drawStringWithShadow("X", positionX, positionY, mainTextColor);
        f18.drawStringWithShadow(": " + (int)Information.mc.thePlayer.posX, positionX + f18.getStringWidth("X"), positionY, Color.white.getRGB());
        f18.drawStringWithShadow("Y", positionX + positionLongList.get(0), positionY, mainTextColor);
        f18.drawStringWithShadow(": " + (int)Information.mc.thePlayer.posY, positionX + f18.getStringWidth("Y") + positionLongList.get(0), positionY, Color.white.getRGB());
        f18.drawStringWithShadow("Z", positionX + positionLongList.get(1) + positionLongList.get(0), positionY, mainTextColor);
        f18.drawStringWithShadow(": " + (int)Information.mc.thePlayer.posZ, positionX + f18.getStringWidth("Z") + positionLongList.get(1) + positionLongList.get(0), positionY, Color.white.getRGB());
        positionY -= f18.getStringHeight() + 2.0;
        f18.drawStringWithShadow("Fps", positionX, positionY, new Color(160, 160, 160).getRGB());
        f18.drawStringWithShadow(": " + Minecraft.getDebugFPS(), positionX + f18.getStringWidth("Fps"), positionY, Color.white.getRGB());
    }
}

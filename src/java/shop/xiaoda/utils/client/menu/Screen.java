//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.menu;

import shop.xiaoda.utils.misc.*;

public interface Screen extends MinecraftInstance
{
    default void onDrag(final int mouseX, final int mouseY) {
    }
    
    void initGui();
    
    void keyTyped(final char p0, final int p1);
    
    void drawScreen(final int p0, final int p1);
    
    void mouseClicked(final int p0, final int p1, final int p2);
    
    void mouseReleased(final int p0, final int p1, final int p2);
}

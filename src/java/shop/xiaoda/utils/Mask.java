//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import org.lwjgl.opengl.*;

public class Mask
{
    public static void defineMask() {
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        GL11.glDepthFunc(519);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glColorMask(false, false, false, false);
    }
    
    public static void finishDefineMask() {
        GL11.glDepthMask(false);
        GL11.glColorMask(true, true, true, true);
    }
    
    public static void drawOnMask() {
        GL11.glDepthFunc(514);
    }
    
    public static void drawOffMask() {
        GL11.glDepthFunc(517);
    }
    
    public static void drawMCMask() {
        GL11.glDepthFunc(515);
    }
    
    public static void resetMask() {
        GL11.glDepthMask(true);
        GL11.glClearDepth(1.0);
        GL11.glClear(256);
        drawMCMask();
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import shop.xiaoda.utils.misc.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;

public class KeyboardUtil implements MinecraftInstance
{
    public static boolean isPressed(final KeyBinding key) {
        return Keyboard.isKeyDown(key.getKeyCode());
    }
    
    public static void resetKeybinding(final KeyBinding key) {
        if (KeyboardUtil.mc.currentScreen != null) {
            key.pressed = false;
        }
        else {
            key.pressed = isPressed(key);
        }
    }
    
    public static void resetKeybindings(final KeyBinding... keys) {
        for (final KeyBinding key : keys) {
            resetKeybinding(key);
        }
    }
}

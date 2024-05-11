//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;
import shop.xiaoda.*;

public class DebugUtil
{
    private static Minecraft mc;
    
    public static void print(final Object... debug) {
        if (isDev()) {
            final String message = Arrays.toString(debug);
            DebugUtil.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(message));
        }
    }
    
    public static void log(final Object message) {
        final String text = String.valueOf(message);
        DebugUtil.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(text));
    }
    
    public static void log(final boolean prefix, final Object message) {
        final String text = EnumChatFormatting.BOLD + "[" + Client.NAME + "] " + EnumChatFormatting.RESET + " " + message;
        DebugUtil.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(text));
    }
    
    public static void log(final String prefix, final Object message) {
        final String text = EnumChatFormatting.BOLD + "[" + Client.NAME + "-" + prefix + "] " + EnumChatFormatting.RESET + " " + message;
        DebugUtil.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(text));
    }
    
    public static void logcheater(final String prefix, final Object message) {
        final String text = EnumChatFormatting.BOLD + "[" + Client.NAME + "-" + prefix + "] " + EnumChatFormatting.RESET + " " + message;
        DebugUtil.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(text));
    }
    
    private static boolean isDev() {
        return true;
    }
    
    static {
        DebugUtil.mc = Minecraft.getMinecraft();
    }
}

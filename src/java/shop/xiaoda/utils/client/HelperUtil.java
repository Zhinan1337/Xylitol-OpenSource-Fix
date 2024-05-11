//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client;

import net.minecraft.client.*;
import shop.xiaoda.utils.*;
import net.minecraft.util.*;

public class HelperUtil
{
    public static Minecraft mc;
    
    public static void sendMessage(final String message) {
        new ChatUtil.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }
    
    public static void sendWarmingMessage(final String message) {
        new ChatUtil.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.RED).build().displayClientSided();
    }
    
    public static void sendMessageWithoutPrefix(final String message) {
        new ChatUtil.ChatMessageBuilder(false, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }
    
    public static boolean onServer(final String server) {
        return !HelperUtil.mc.isSingleplayer() && HelperUtil.mc.getCurrentServerData().serverIP.toLowerCase().contains(server);
    }
    
    static {
        HelperUtil.mc = Minecraft.getMinecraft();
    }
}

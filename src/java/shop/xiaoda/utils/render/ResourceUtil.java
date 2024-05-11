//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import java.awt.*;
import java.io.*;

public final class ResourceUtil
{
    public static Font createFontTTF(final String path) {
        try {
            return Font.createFont(0, getResourceStream(path));
        }
        catch (Exception ignored) {
            return null;
        }
    }
    
    public static InputStream getResourceStream(final String path) {
        final String s = "/assets/minecraft/express/" + path;
        return ResourceUtil.class.getResourceAsStream(s);
    }
}

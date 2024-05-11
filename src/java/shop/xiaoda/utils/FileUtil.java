//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import java.io.*;

public class FileUtil
{
    public static final String SEPARATOR;
    public static final String SYS_DIR;
    
    static {
        SEPARATOR = File.separator;
        SYS_DIR = System.getenv("WINDIR") + FileUtil.SEPARATOR + "system32";
    }
}

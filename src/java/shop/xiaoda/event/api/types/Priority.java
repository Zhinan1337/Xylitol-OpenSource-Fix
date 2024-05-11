//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.event.api.types;

public final class Priority
{
    public static final byte HIGHEST = 0;
    public static final byte HIGH = 1;
    public static final byte MEDIUM = 2;
    public static final byte LOW = 3;
    public static final byte LOWEST = 4;
    public static final byte VERYVERYLOW = 100;
    public static final byte VERY_LOW = 0;
    public static final byte[] VALUE_ARRAY;
    
    static {
        VALUE_ARRAY = new byte[] { 0, 1, 2, 3, 4, 100 };
    }
}

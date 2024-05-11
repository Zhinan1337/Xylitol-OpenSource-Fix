//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

public enum AccountEnum
{
    OFFLINE("OFFLINE"), 
    MICROSOFT("MICROSOFT");
    
    private final String writeName;
    
    private AccountEnum(final String name) {
        this.writeName = name;
    }
    
    public static AccountEnum parse(final String str) {
        for (final AccountEnum value : values()) {
            if (value.writeName.equals(str)) {
                return value;
            }
        }
        return null;
    }
}

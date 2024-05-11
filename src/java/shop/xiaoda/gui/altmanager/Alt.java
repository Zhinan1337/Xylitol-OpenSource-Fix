//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

public abstract class Alt
{
    private final String userName;
    private final AccountEnum accountType;
    
    public Alt(final String userName, final AccountEnum accountType) {
        this.userName = userName;
        this.accountType = accountType;
    }
    
    public AccountEnum getAccountType() {
        return this.accountType;
    }
    
    public String getUserName() {
        return this.userName;
    }
}

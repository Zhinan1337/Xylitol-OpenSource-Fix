//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager.altimpl;

import shop.xiaoda.gui.altmanager.*;

public final class MicrosoftAlt extends Alt
{
    private final String refreshToken;
    
    public MicrosoftAlt(final String userName, final String refreshToken) {
        super(userName, AccountEnum.MICROSOFT);
        this.refreshToken = refreshToken;
    }
    
    public String getRefreshToken() {
        return this.refreshToken;
    }
}

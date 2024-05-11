//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

import java.util.*;
import com.google.gson.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

public final class AltManager
{
    public static AltManager Instance;
    private final ArrayList<Alt> altList;
    private final Gson gson;
    
    public AltManager() {
        this.altList = new ArrayList<Alt>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        AltManager.Instance = this;
    }
    
    public void addAlt(final Alt alt) {
        this.altList.add(alt);
    }
    
    public ArrayList<Alt> getAltList() {
        return this.altList;
    }
    
    public static LoginStatus loginAlt(final String account, final String password) throws AuthenticationException {
        if (StringUtils.isNullOrEmpty(password)) {
            Minecraft.getMinecraft().session = new Session(account, "", "", "mojang");
            return LoginStatus.SUCCESS;
        }
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(account);
        auth.setPassword(password);
        try {
            auth.logIn();
            Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
            return LoginStatus.SUCCESS;
        }
        catch (AuthenticationException e) {
            throw e;
        }
    }
    
    public enum LoginStatus
    {
        FAILED, 
        SUCCESS, 
        EXCEPTION {
            private Exception exception;
            
            public Exception getException() {
                return this.exception;
            }
            
            public void setException(final Exception exception) {
                this.exception = exception;
            }
        };
    }
}

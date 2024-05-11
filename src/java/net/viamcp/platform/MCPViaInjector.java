//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.libs.gson.*;

public class MCPViaInjector implements ViaInjector
{
    public void inject() {
    }
    
    public void uninject() {
    }
    
    public int getServerProtocolVersion() {
        return 47;
    }
    
    public String getEncoderName() {
        return "via-encoder";
    }
    
    public String getDecoderName() {
        return "via-decoder";
    }
    
    public JsonObject getDump() {
        return new JsonObject();
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.config;

import com.google.gson.*;

public abstract class Config
{
    private final String name;
    
    public Config(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public abstract JsonObject saveConfig();
    
    public abstract void loadConfig(final JsonObject p0);
}

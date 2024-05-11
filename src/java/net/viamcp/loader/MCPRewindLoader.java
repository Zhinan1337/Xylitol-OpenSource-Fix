//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.loader;

import java.io.*;
import de.gerrygames.viarewind.api.*;
import java.util.logging.*;
import com.viaversion.viaversion.api.*;

public class MCPRewindLoader implements ViaRewindPlatform
{
    public MCPRewindLoader(final File file) {
        final ViaRewindConfigImpl conf = new ViaRewindConfigImpl(file.toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        this.init((ViaRewindConfig)conf);
    }
    
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
}

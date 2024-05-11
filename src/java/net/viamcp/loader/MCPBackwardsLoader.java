//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.loader;

import com.viaversion.viabackwards.api.*;
import java.io.*;
import java.util.logging.*;
import net.viamcp.*;

public class MCPBackwardsLoader implements ViaBackwardsPlatform
{
    private final File file;

    public MCPBackwardsLoader(final File file)
    {
        this.init(this.file = new File(file, "ViaBackwards"));
    }

    @Override
    public Logger getLogger()
    {
        return ViaMCP.getInstance().getjLogger();
    }

    @Override
    public void disable() { }

    @Override
    public boolean isOutdated()
    {
        return false;
    }

    @Override
    public File getDataFolder()
    {
        return new File(this.file, "config.yml");
    }
}

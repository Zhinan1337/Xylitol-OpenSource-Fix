//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.command;

import java.util.*;

public abstract class Command
{
    private final String[] name;
    
    public Command(final String... name) {
        this.name = name;
    }
    
    public abstract List<String> autoComplete(final int p0, final String[] p1);
    
    public String[] getNames() {
        return this.name;
    }
    
    public abstract void run(final String[] p0);
    
    boolean match(final String name) {
        for (final String alias : this.name) {
            if (alias.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return this.name[0].equalsIgnoreCase(name);
    }
}

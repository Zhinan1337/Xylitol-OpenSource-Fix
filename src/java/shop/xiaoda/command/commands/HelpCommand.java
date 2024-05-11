//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.command.commands;

import shop.xiaoda.command.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.*;
import java.util.*;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super(new String[] { "help", "h" });
    }
    
    public List<String> autoComplete(final int arg, final String[] args) {
        return new ArrayList<String>();
    }
    
    public void run(final String[] args) {
        DebugUtil.log("\u00a7a[Commands]:\u00a7f");
        for (final Command command : Client.instance.commandManager.getCommands()) {
            DebugUtil.log("\u00a7e." + Arrays.toString(command.getNames()));
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.command.commands;

import shop.xiaoda.command.*;
import java.util.*;
import shop.xiaoda.*;
import net.minecraft.util.*;
import shop.xiaoda.utils.client.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super(new String[] { "config", "cfg" });
    }
    
    public List<String> autoComplete(final int arg, final String[] args) {
        return new ArrayList<String>();
    }
    
    public void run(final String[] args) {
        if (args.length == 2) {
            final String s = args[0];
            switch (s) {
                case "save": {
                    final String name = args[1];
                    if (!name.isEmpty()) {
                        Client.instance.configManager.saveUserConfig(name + ".json");
                        HelperUtil.sendMessage(EnumChatFormatting.GREEN + "Config " + name + " Saved!");
                        break;
                    }
                    HelperUtil.sendMessage(EnumChatFormatting.RED + "?");
                    break;
                }
                case "load": {
                    final String name = args[1];
                    if (!name.isEmpty()) {
                        Client.instance.configManager.loadUserConfig(name + ".json");
                        HelperUtil.sendMessage(EnumChatFormatting.GREEN + "Config " + name + " Loaded!");
                        break;
                    }
                    HelperUtil.sendMessage(EnumChatFormatting.RED + "?");
                    break;
                }
            }
        }
        else {
            HelperUtil.sendMessage(EnumChatFormatting.RED + "Usage: config save/load <name>");
        }
    }
}

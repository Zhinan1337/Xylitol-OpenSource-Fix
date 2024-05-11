//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.command;

import shop.xiaoda.command.commands.*;
import java.util.stream.*;
import java.util.*;

public class CommandManager
{
    private final List<Command> commands;
    public String[] latestAutoComplete;
    public String prefix;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        this.latestAutoComplete = new String[0];
        this.prefix = ".";
    }
    
    public List<Command> getCommands() {
        return this.commands;
    }
    
    public void init() {
        this.reg(new BindCommand());
        this.reg(new ToggleCommand());
        this.reg(new ConfigCommand());
        this.reg(new BindsCommand());
        this.reg(new HelpCommand());
    }
    
    public Collection<String> autoComplete(final String currCmd) {
        final String raw = currCmd.substring(1);
        final String[] split = raw.split(" ");
        final List<String> ret = new ArrayList<String>();
        final Command currentCommand = (split.length >= 1) ? this.commands.stream().filter(cmd -> cmd.match(split[0])).findFirst().orElse(null) : null;
        if (split.length >= 2 || (currentCommand != null && currCmd.endsWith(" "))) {
            if (currentCommand == null) {
                return ret;
            }
            final String[] args = new String[split.length - 1];
            System.arraycopy(split, 1, args, 0, split.length - 1);
            final List<String> autocomplete = (List<String>)currentCommand.autoComplete(args.length + (currCmd.endsWith(" ") ? 1 : 0), args);
            this.latestAutoComplete = ((autocomplete.size() > 0 && autocomplete.get(0).equals("none")) ? new String[] { "" } : autocomplete.toArray(new String[0]));
            return (autocomplete == null) ? new ArrayList<String>() : autocomplete;
        }
        else {
            if (split.length == 1) {
                for (final Command command : this.commands) {
                    ret.addAll(Arrays.asList(command.getNames()));
                }
                return ret.stream().map(str -> "." + str).filter(str -> str.toLowerCase().startsWith(currCmd.toLowerCase())).collect(Collectors.toList());
            }
            return ret;
        }
    }
    
    public void reg(final Command command) {
        this.commands.add(command);
    }
    
    public Command getCommand(final String name) {
        for (final Command c : this.commands) {
            for (final String s : c.getNames()) {
                if (s.equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }
}

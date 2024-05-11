//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.command.commands;

import shop.xiaoda.command.*;
import shop.xiaoda.*;
import shop.xiaoda.module.*;
import org.lwjgl.input.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.*;
import java.util.*;

public class BindsCommand extends Command
{
    public BindsCommand() {
        super(new String[] { "binds" });
    }
    
    public List<String> autoComplete(final int arg, final String[] args) {
        return new ArrayList<String>();
    }
    
    public void run(final String[] args) {
        for (final Module module : Client.instance.moduleManager.getModules()) {
            if (module.getKey() == -1) {
                continue;
            }
            DebugUtil.log("\u00a7a[Binds]\u00a7f" + module.name + " :" + Keyboard.getKeyName(module.key));
        }
    }
}

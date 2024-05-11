// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.command.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.EnumChatFormatting;
import shop.xiaoda.Client;
import shop.xiaoda.command.Command;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.client.HelperUtil;

public class ToggleCommand
        extends Command {
    public ToggleCommand() {
        super("toggle", "t");
    }

    @Override
    public List<String> autoComplete(int arg, String[] args) {
        String prefix = "";
        boolean flag = false;
        if (arg == 0 || args.length == 0) {
            flag = true;
        } else if (arg == 1) {
            flag = true;
            prefix = args[0];
        }
        if (flag) {
            String finalPrefix = prefix;
            return Client.instance.moduleManager.getModules().stream().filter(mod -> mod.getName().toLowerCase().startsWith(finalPrefix)).map(Module::getName).collect(Collectors.toList());
        }
        if (arg == 2) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("none");
            arrayList.add("show");
            return arrayList;
        }
        return new ArrayList<String>();
    }

    @Override
    public void run(String[] args) {
        if (args.length == 1) {
            Module m = Client.instance.moduleManager.getModule(args[0]);
            if (m != null) {
                m.toggle();
            } else {
                HelperUtil.sendMessage((Object)((Object)EnumChatFormatting.RED) + "Module not found!");
            }
        } else {
            HelperUtil.sendMessage((Object)((Object)EnumChatFormatting.RED) + "Usage: t <Module>");
        }
    }
}

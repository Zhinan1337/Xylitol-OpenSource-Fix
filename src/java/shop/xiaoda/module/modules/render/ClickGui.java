//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.render;

import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import shop.xiaoda.module.*;
import org.lwjgl.input.*;
import shop.xiaoda.gui.clickgui.express.*;
import net.minecraft.client.gui.*;
import shop.xiaoda.gui.clickgui.drop.*;
import shop.xiaoda.gui.clickgui.book.*;

public class ClickGui extends Module
{
    public ModeValue<ClickGuiMode> mode;
    
    public ClickGui() {
        super("ClickGui", Category.Render);
        this.mode = new ModeValue<ClickGuiMode>("Mode", ClickGuiMode.values(), ClickGuiMode.Book);
        this.setKey(Keyboard.KEY_RSHIFT);
    }
    
    public void onEnable() {
        switch (this.mode.getValue()) {
            case Normal: {
                ClickGui.mc.displayGuiScreen((GuiScreen)new NormalClickGUI());
                break;
            }
            case DropDown: {
                ClickGui.mc.displayGuiScreen((GuiScreen)new DropdownClickGUI());
                break;
            }
            case Book: {
                ClickGui.mc.displayGuiScreen((GuiScreen)NewClickGui.getInstance());
                break;
            }
        }
        this.setState(false);
    }
    
    public enum ClickGuiMode
    {
        Normal, 
        DropDown, 
        Book;
    }
}

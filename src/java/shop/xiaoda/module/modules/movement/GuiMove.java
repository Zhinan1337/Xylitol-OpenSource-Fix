//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.modules.movement;

import shop.xiaoda.module.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import shop.xiaoda.event.world.*;
import net.minecraft.client.gui.inventory.*;
import shop.xiaoda.gui.clickgui.express.*;
import shop.xiaoda.gui.clickgui.drop.*;
import shop.xiaoda.gui.clickgui.book.*;
import shop.xiaoda.event.*;
import shop.xiaoda.module.Module;

import java.util.*;

public class GuiMove extends Module
{
    private static final List<KeyBinding> keys;
    
    public GuiMove() {
        super("InvMove", Category.Movement);
    }
    
    public static void updateStates() {
        if (GuiMove.mc.currentScreen != null) {
            for (final KeyBinding k : GuiMove.keys) {
                k.setPressed(GameSettings.isKeyDown(k));
                if (Keyboard.isKeyDown(Keyboard.KEY_UP) && GuiMove.mc.thePlayer.rotationPitch > -90.0f) {
                    final EntityPlayerSP thePlayer = GuiMove.mc.thePlayer;
                    thePlayer.rotationPitch -= 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && GuiMove.mc.thePlayer.rotationPitch < 90.0f) {
                    final EntityPlayerSP thePlayer2 = GuiMove.mc.thePlayer;
                    thePlayer2.rotationPitch += 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                    final EntityPlayerSP thePlayer3 = GuiMove.mc.thePlayer;
                    thePlayer3.rotationYaw -= 5.0f;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                    final EntityPlayerSP thePlayer4 = GuiMove.mc.thePlayer;
                    thePlayer4.rotationYaw += 5.0f;
                }
            }
        }
    }
    
    @EventTarget
    public void onMotion(final EventUpdate event) {
        if (GuiMove.mc.currentScreen instanceof GuiContainer || GuiMove.mc.currentScreen instanceof NormalClickGUI || GuiMove.mc.currentScreen instanceof DropdownClickGUI || GuiMove.mc.currentScreen instanceof NewClickGui) {
            updateStates();
        }
    }
    
    static {
        keys = Arrays.asList(GuiMove.mc.gameSettings.keyBindForward, GuiMove.mc.gameSettings.keyBindBack, GuiMove.mc.gameSettings.keyBindLeft, GuiMove.mc.gameSettings.keyBindRight, GuiMove.mc.gameSettings.keyBindJump);
    }
}

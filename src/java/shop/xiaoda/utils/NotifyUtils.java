//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import javax.swing.*;
import java.awt.*;

public class NotifyUtils
{
    public static void notice(final String title, final String message) {
        JOptionPane.getRootFrame().setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(null, message, title, 2);
    }
    
    public static String showInputDialog(final String message) {
        JOptionPane.getRootFrame().setAlwaysOnTop(true);
        return JOptionPane.showInputDialog(message);
    }
}

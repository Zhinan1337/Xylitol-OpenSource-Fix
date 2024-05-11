//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.java.games.input.*;
import org.apache.logging.log4j.*;

public class RawInput extends MouseHelper
{
    private static final Logger logger;
    public static Mouse mouse;
    public static int dx;
    public static int dy;
    public static Controller[] controllers;
    
    public void mouseXYChange() {
        this.deltaX = RawInput.dx;
        RawInput.dx = 0;
        this.deltaY = -RawInput.dy;
        RawInput.dy = 0;
    }
    
    public void init() {
        RawInput.logger.warn("RawInput is enabled, this may cause some issues!");
        Minecraft.getMinecraft().mouseHelper = this;
        RawInput.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        final Thread inputThread = new Thread(() -> {
            while (true) {
                for (int i = 0; i < RawInput.controllers.length && RawInput.mouse == null; ++i) {
                    if (RawInput.controllers[i].getType() == Controller.Type.MOUSE) {
                        RawInput.controllers[i].poll();
                        if (((Mouse)RawInput.controllers[i]).getX().getPollData() != 0.0 || ((Mouse)RawInput.controllers[i]).getY().getPollData() != 0.0) {
                            RawInput.mouse = (Mouse)RawInput.controllers[i];
                        }
                    }
                }
                if (RawInput.mouse != null) {
                    RawInput.mouse.poll();
                    RawInput.dx += (int)RawInput.mouse.getX().getPollData();
                    RawInput.dy += (int)RawInput.mouse.getY().getPollData();
                }
                try {
                    Thread.sleep(1L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        inputThread.setName("inputThread");
        inputThread.start();
    }
    
    static {
        logger = LogManager.getLogger((Class)RawInput.class);
        RawInput.dx = 0;
        RawInput.dy = 0;
    }
}

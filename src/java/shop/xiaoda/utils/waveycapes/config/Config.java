//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.waveycapes.config;

import shop.xiaoda.utils.waveycapes.*;

public class Config
{
    public static final WindMode windMode;
    public static final CapeStyle capeStyle;
    public static final CapeMovement capeMovement;
    public static final int gravity = 25;
    public static final int heightMultiplier = 6;
    
    static {
        windMode = WindMode.NONE;
        capeStyle = CapeStyle.SMOOTH;
        capeMovement = CapeMovement.BASIC_SIMULATION;
    }
}

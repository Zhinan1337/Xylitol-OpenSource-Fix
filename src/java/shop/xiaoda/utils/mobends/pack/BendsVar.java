//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.pack;

import shop.xiaoda.utils.mobends.data.*;

public class BendsVar
{
    public static EntityData tempData;
    
    public static float getGlobalVar(final String name) {
        if (name.equalsIgnoreCase("ticks")) {
            if (BendsVar.tempData == null) {
                return 0.0f;
            }
            return BendsVar.tempData.ticks;
        }
        else {
            if (!name.equalsIgnoreCase("ticksAfterPunch")) {
                return Float.POSITIVE_INFINITY;
            }
            if (BendsVar.tempData == null) {
                return 0.0f;
            }
            return BendsVar.tempData.ticksAfterPunch;
        }
    }
}

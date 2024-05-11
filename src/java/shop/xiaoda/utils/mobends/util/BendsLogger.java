//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.mobends.util;

public enum BendsLogger
{
    INFO, 
    DEBUG, 
    ERROR;
    
    public static void log(final String argText, final BendsLogger argType) {
        if (argType != BendsLogger.DEBUG) {
            System.out.println("(MO'BENDS - " + argType.name() + " ) " + argText);
        }
    }
}

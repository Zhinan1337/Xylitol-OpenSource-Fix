//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

public enum MovementFix
{
    OFF("Off"), 
    NORMAL("Xylitol"), 
    TRADITIONAL("Traditional"), 
    BACKWARDS_SPRINT("Backwards Sprint");
    
    String name;
    
    @Override
    public String toString() {
        return this.name;
    }
    
    private MovementFix(final String name) {
        this.name = name;
    }
}

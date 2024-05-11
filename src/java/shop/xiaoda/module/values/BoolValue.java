//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

public class BoolValue extends Value<Boolean>
{
    public float alpha;
    
    public BoolValue(final String name, final Boolean value) {
        super(name);
        this.alpha = 255.0f;
        this.setValue(value);
    }
    
    public BoolValue(final String name, final Boolean value, final Dependency dependenc) {
        super(name, dependenc);
        this.alpha = 255.0f;
        this.setValue(value);
    }
    
    @Override
    public Boolean getConfigValue() {
        return (Boolean)this.value;
    }
}

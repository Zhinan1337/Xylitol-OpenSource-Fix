//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

import shop.xiaoda.gui.clickgui.book.*;

public class ModeValue<V extends Enum<?>> extends Value<V>
{
    private final V[] modes;
    public boolean expanded;
    public float height;
    public RippleAnimation animation;
    
    public ModeValue(final String name, final V[] modes, final V value) {
        super(name);
        this.animation = new RippleAnimation();
        this.modes = modes;
        this.setValue(value);
    }
    
    public ModeValue(final String name, final V[] modes, final V value, final Dependency dependenc) {
        super(name, dependenc);
        this.animation = new RippleAnimation();
        this.modes = modes;
        this.setValue(value);
    }
    
    public V[] getModes() {
        return this.modes;
    }
    
    public boolean is(final String sb) {
        return this.getValue().name().equalsIgnoreCase(sb);
    }
    
    public void setMode(final String mode) {
        for (final V e : this.modes) {
            if (e.name().equalsIgnoreCase(mode)) {
                this.setValue(e);
            }
        }
    }
    
    public boolean isValid(final String name) {
        for (final V e : this.modes) {
            if (e.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getConfigValue() {
        return this.getValue().name();
    }
    
    public V get() {
        return this.getValue();
    }
}

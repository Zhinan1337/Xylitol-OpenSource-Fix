//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module.values;

public abstract class Value<V>
{
    public V value;
    String name;
    protected final Dependency dependency;

    public Value(final String name, final Dependency dependenc) {
        this.name = name;
        this.dependency = dependenc;
    }

    public Value(final String name) {
        this.name = name;
        this.dependency = (() -> Boolean.TRUE);
    }

    public String getName() {
        return this.name;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(final V val) {
        this.value = val;
    }

    public abstract <T> T getConfigValue();

    public boolean isAvailable() {
        return this.dependency != null && this.dependency.check();
    }

    @FunctionalInterface
    public interface Dependency
    {
        boolean check();
    }
}

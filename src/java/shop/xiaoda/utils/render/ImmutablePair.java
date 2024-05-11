//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import java.util.function.*;

public final class ImmutablePair<A, B> extends Pair<A, B>
{
    private final A a;
    private final B b;
    
    ImmutablePair(final A a, final B b) {
        this.a = a;
        this.b = b;
    }
    
    public static <A, B> ImmutablePair<A, B> of(final A a, final B b) {
        return new ImmutablePair<A, B>(a, b);
    }
    
    public Pair<A, A> pairOfFirst() {
        return Pair.of(this.a);
    }
    
    public Pair<B, B> pairOfSecond() {
        return Pair.of(this.b);
    }
    
    @Override
    public A getFirst() {
        return this.a;
    }
    
    @Override
    public B getSecond() {
        return this.b;
    }
    
    @Override
    public <R> R apply(final BiFunction<? super A, ? super B, ? extends R> func) {
        return (R)func.apply(this.a, this.b);
    }
    
    @Override
    public void use(final BiConsumer<? super A, ? super B> func) {
        func.accept(this.a, this.b);
    }
}

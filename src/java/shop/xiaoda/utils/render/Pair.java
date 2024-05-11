//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.render;

import java.io.*;
import java.util.function.*;
import java.util.*;

public abstract class Pair<A, B> implements Serializable
{
    public static <A, B> Pair<A, B> of(final A a, final B b) {
        return (Pair<A, B>)ImmutablePair.of(a, b);
    }
    
    public static <A> Pair<A, A> of(final A a) {
        return (Pair<A, A>)ImmutablePair.of(a, a);
    }
    
    public abstract A getFirst();
    
    public abstract B getSecond();
    
    public abstract <R> R apply(final BiFunction<? super A, ? super B, ? extends R> p0);
    
    public abstract void use(final BiConsumer<? super A, ? super B> p0);
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getFirst(), this.getSecond());
    }
    
    @Override
    public boolean equals(final Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof Pair) {
            final Pair<?, ?> other = (Pair<?, ?>)that;
            return Objects.equals(this.getFirst(), other.getFirst()) && Objects.equals(this.getSecond(), other.getSecond());
        }
        return false;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager;

import java.util.concurrent.*;

public final class StringUtils
{
    public static final String ALPHA_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    
    public static String replace(String s, final Object... o) {
        for (int i = 0; i < o.length; ++i) {
            s = s.replace(build("{", i, "}"), o[i].toString());
        }
        return s;
    }
    
    public static String breakString(final String s) {
        final StringBuilder sb = new StringBuilder();
        final String[] sArray = s.split("");
        int index = 0;
        for (final String s2 : sArray) {
            if (!s2.equals("")) {
                if (s2.equals(s2.toUpperCase()) && Character.isLetter(s2.toCharArray()[0]) && index != 0) {
                    sb.append(" ");
                }
                sb.append(s2);
                ++index;
            }
        }
        return sb.toString();
    }
    
    public static String build(final Object... objects) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Object o : objects) {
            stringBuilder.append(o);
        }
        return stringBuilder.toString();
    }
    
    public static String randomString(final String pool, final int length) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(pool.charAt(ThreadLocalRandom.current().nextInt(0, pool.length())));
        }
        return builder.toString();
    }
    
    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.length() == 0;
    }
    
    public static boolean isNotNullOrEmpty(final String s) {
        return s != null && isNotEmpty(s);
    }
    
    public static boolean isNotEmpty(final String s) {
        return s.length() != 0;
    }
}

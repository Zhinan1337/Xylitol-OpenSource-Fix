//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import java.nio.charset.*;
import java.io.*;

public class CryptoUtils
{
    public static void encryptString(final OutputStream out, final String str) throws IOException {
        if (str == null) {
            writeInt(out, -1);
        }
        else if (str.isEmpty()) {
            writeInt(out, 0);
        }
        else {
            final byte[] bs = str.getBytes(StandardCharsets.UTF_8);
            writeInt(out, bs.length);
            out.write(bs);
        }
    }
    
    public static void writeInt(final OutputStream out, final int i) throws IOException {
        out.write((byte)(i >> 24));
        out.write((byte)(i >> 16));
        out.write((byte)(i >> 8));
        out.write(i);
    }
    
    public static String decryptString(final InputStream in) throws IOException {
        final int length = decrypt(in);
        if (length < 0) {
            return null;
        }
        if (length == 0) {
            return "";
        }
        final byte[] value = new byte[length];
        in.read(value);
        return new String(value, StandardCharsets.UTF_8);
    }
    
    public static void encrypt(final OutputStream out, final int i) throws IOException {
        out.write((byte)(i >> 24));
        out.write((byte)(i >> 16));
        out.write((byte)(i >> 8));
        out.write(i);
    }
    
    public static int decrypt(final InputStream in) throws IOException {
        return ((byte)in.read() & 0xFF) << 24 | ((byte)in.read() & 0xFF) << 16 | ((byte)in.read() & 0xFF) << 8 | ((byte)in.read() & 0xFF);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.utils;

import io.netty.channel.*;

public class NettyUtil
{
    public static ChannelPipeline decodeEncodePlacement(final ChannelPipeline instance, String base, final String newHandler, final ChannelHandler handler) {
        final String s = base;
        switch (s) {
            case "decoder": {
                if (instance.get("via-decoder") != null) {
                    base = "via-decoder";
                    break;
                }
                break;
            }
            case "encoder": {
                if (instance.get("via-encoder") != null) {
                    base = "via-encoder";
                    break;
                }
                break;
            }
        }
        return instance.addBefore(base, newHandler, handler);
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.handler;

import io.netty.buffer.*;
import com.viaversion.viaversion.util.*;
import io.netty.channel.*;
import java.lang.reflect.*;
import io.netty.handler.codec.*;

public class CommonTransformer
{
    public static final String HANDLER_DECODER_NAME = "via-decoder";
    public static final String HANDLER_ENCODER_NAME = "via-encoder";

    public static void decompress(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException
    {
        ChannelHandler handler = ctx.pipeline().get("decompress");
        ByteBuf decompressed = handler instanceof MessageToMessageDecoder ? (ByteBuf) PipelineUtil.callDecode((MessageToMessageDecoder<?>) handler, ctx, buf).get(0) : (ByteBuf) PipelineUtil.callDecode((ByteToMessageDecoder) handler, ctx, buf).get(0);

        try
        {
            buf.clear().writeBytes(decompressed);
        }
        finally
        {
            decompressed.release();
        }
    }

    public static void compress(ChannelHandlerContext ctx, ByteBuf buf) throws Exception
    {
        ByteBuf compressed = ctx.alloc().buffer();

        try
        {
            PipelineUtil.callEncode((MessageToByteEncoder<?>) ctx.pipeline().get("compress"), ctx, buf, compressed);
            buf.clear().writeBytes(compressed);
        }
        finally
        {
            compressed.release();
        }
    }
}

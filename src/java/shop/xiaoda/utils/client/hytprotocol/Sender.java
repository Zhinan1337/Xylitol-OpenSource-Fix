//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol;

import net.minecraft.network.play.client.*;
import shop.xiaoda.utils.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import java.util.zip.*;
import java.io.*;

public class Sender
{
    public static final String JsonCloseGUI = "{\"packet_sub_type\":\"null\",\"packet_data\":\"null\",\"packet_type\":\"gui_close\"}";
    public static final String JsonOpenGUI = "{\"packet_sub_type\":\"null\",\"packet_data\":\"null\",\"packet_type\":\"opengui\"}";
    
    public static void sendJson(final String json) throws IOException {
        final byte[] data = encode(json);
        final ByteBuf buf = Unpooled.wrappedBuffer(data);
        final C17PacketCustomPayload packet = new C17PacketCustomPayload("VexView", new PacketBuffer(buf));
        PacketUtil.send((Packet<?>)packet);
    }
    
    public static void sendButtonClicked(final String id) {
        try {
            sendJson(getButtionClickJson(id));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getButtionClickJson(final String id) {
        return "{\"packet_sub_type\":\"" + id + "\",\"packet_data\":\"null\",\"packet_type\":\"button\"}";
    }
    
    private static byte[] encode(final String json) throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes("UTF-8"));
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final GZIPOutputStream out = new GZIPOutputStream(bout);
        final byte[] array = new byte[256];
        int read;
        while ((read = in.read(array)) >= 0) {
            out.write(array, 0, read);
        }
        out.close();
        out.finish();
        return bout.toByteArray();
    }
    
    public static String getTextSetJson(final String id, final String text) {
        return "{\"packet_sub_type\":\"" + id + "\",\"packet_data\":\"" + text + "\",\"packet_type\":\"fieldtext\"}";
    }
}

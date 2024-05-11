//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol;

import java.util.*;
import io.netty.buffer.*;
import java.util.zip.*;
import java.io.*;
import shop.xiaoda.utils.client.hytprotocol.metadata.*;

public class Reader
{
    public static boolean inviting;
    public final String[] elements;
    public final boolean invited;
    public final String inviter;
    public final boolean sign;
    public final boolean list;
    public final ArrayList<PartyMetadata> requests;
    
    public Reader(final ByteBuf byteBuf) throws IOException {
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String result = this.decode(bytes);
        if (!result.contains("[but]\u624b\u52a8\u8f93\u5165")) {
            this.sign = result.contains("[gui]https://img.166.net/gameyw-misc/opd/squash/20221221/104939-4q3d0pgm59.png");
            if (this.sign) {
                result = result.replace("[but]", "[but]sign");
            }
            this.list = result.contains("[gui]https://ok.166.net/gameyw-misc/opd/squash/20210915/195203-c2npy8skq6.png");
            this.requests = new ArrayList<PartyMetadata>();
            if (this.list) {
                result = result.replace("null<#>[but]", "null<#>[denyButton]");
                result = result.replace("[but]", "[but]accept");
                result = result.replace("[denyButton]", "[but]deny");
            }
        }
        else {
            this.sign = false;
            this.list = false;
            this.requests = null;
        }
        this.elements = result.split("<&>");
        if (this.list) {
            boolean nextDeny = false;
            String cacheName = "";
            int cacheAccept = -1;
            for (int i = 0; i < this.elements.length; ++i) {
                final String element = this.elements[i];
                if (element.contains("[but]\u9080\u8bf7\u7ec4\u961f")) {
                    Reader.inviting = true;
                }
                if (!element.equals("true<#>[but]accept\u7533\u8bf7\u5217\u8868")) {
                    if (!nextDeny) {
                        if (element.contains("<#>[but]accept")) {
                            cacheName = element.substring(0, element.length() - 14);
                            i += 6;
                            cacheAccept = i;
                            nextDeny = true;
                        }
                    }
                    else if (element.contains("<#>[but]deny")) {
                        nextDeny = false;
                        final ArrayList<PartyMetadata> requests = this.requests;
                        final String s = cacheName;
                        final String s2 = this.elements[cacheAccept];
                        final String[] elements = this.elements;
                        i += 6;
                        requests.add(new PartyMetadata(s, s2, elements[i]));
                    }
                }
            }
        }
        final int index = this.containsString("\u9080\u8bf7\u4f60\u52a0\u5165\u961f\u4f0d");
        if (index != -1) {
            this.invited = true;
            this.inviter = this.elements[index - 3].replace("\u00a76\uff1a<#>[txt]50", "").replace("\u00a76\u73a9\u5bb6 \u00a73\u00a7l", "");
        }
        else {
            this.invited = false;
            this.inviter = "";
        }
    }
    
    private String decode(final byte[] bytes) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
        final byte[] array = new byte[256];
        int read;
        while ((read = gZIPInputStream.read(array)) >= 0) {
            byteArrayOutputStream.write(array, 0, read);
        }
        return byteArrayOutputStream.toString("UTF-8");
    }
    
    public boolean containsButtons(final String... buttons) {
        for (final String button : buttons) {
            if (!this.containsButton(button)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsButton(final String btn) {
        for (final String element : this.elements) {
            if (element.endsWith("[but]" + btn)) {
                return true;
            }
        }
        return false;
    }
    
    public VexViewMetadata getButton(final String name) {
        for (int i = 0; i < this.elements.length; ++i) {
            final String e = this.elements[i];
            if (e.endsWith("[but]" + name)) {
                return new VexViewMetadata(name, this.elements[i + 6]);
            }
        }
        return null;
    }
    
    public int getButtonIndex(final String name) {
        for (int i = 0; i < this.elements.length; ++i) {
            final String e = this.elements[i];
            if (e.endsWith("[but]" + name)) {
                return i;
            }
        }
        return 0;
    }
    
    public int containsString(final String s) {
        for (int i = 0; i < this.elements.length; ++i) {
            final String e = this.elements[i];
            if (e.contains(s)) {
                return i;
            }
        }
        return -1;
    }
    
    public String getElement(final int index) {
        return this.elements[index];
    }
    
    static {
        Reader.inviting = false;
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.client.hytprotocol.packet;

import io.netty.buffer.*;
import shop.xiaoda.utils.client.hytprotocol.*;
import net.minecraft.client.gui.*;
import java.util.*;

import shop.xiaoda.utils.client.hytprotocol.Reader;
import shop.xiaoda.utils.client.hytprotocol.inv.*;
import shop.xiaoda.utils.client.hytprotocol.metadata.*;
import java.io.*;

public class VexViewPacket implements HytPacket
{
    public String getChannel() {
        return "VexView";
    }
    
    public void process(final ByteBuf byteBuf) throws IOException {
        final Reader vexViewReader = new Reader(byteBuf);
        if (vexViewReader.sign) {
            Sender.sendButtonClicked(vexViewReader.getButton("sign").id);
        }
        else if (vexViewReader.containsButton("\u624b\u52a8\u8f93\u5165")) {
            Sender.sendButtonClicked(vexViewReader.getButton("\u624b\u52a8\u8f93\u5165").id);
        }
        else if (vexViewReader.containsButton("\u63d0\u4ea4")) {
            String prefix = "";
            if (Reader.inviting) {
                prefix = "/kh invite ";
                Reader.inviting = false;
            }
            else {
                prefix = "/kh join ";
            }
            VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyInputGui(vexViewReader.getElement(vexViewReader.getButtonIndex("\u63d0\u4ea4") - 1), vexViewReader.getButton("\u63d0\u4ea4"), prefix));
        }
        else if (vexViewReader.list) {
            VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyInviteGui((ArrayList)vexViewReader.requests));
        }
        else if (vexViewReader.containsButtons("\u521b\u5efa\u961f\u4f0d", "\u7533\u8bf7\u5165\u961f")) {
            VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyCreateGui(vexViewReader.getButton("\u521b\u5efa\u961f\u4f0d"), vexViewReader.getButton("\u7533\u8bf7\u5165\u961f")));
        }
        else if (vexViewReader.containsButtons("\u7533\u8bf7\u5217\u8868", "\u7533\u8bf7\u5217\u8868", "\u8e22\u51fa\u961f\u5458", "\u79bb\u5f00\u961f\u4f0d", "\u89e3\u6563\u961f\u4f0d")) {
            if (vexViewReader.containsButton("\u9080\u8bf7\u73a9\u5bb6")) {
                VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyManageGui(vexViewReader.getButton("\u79bb\u5f00\u961f\u4f0d"), vexViewReader.getButton("\u89e3\u6563\u961f\u4f0d"), vexViewReader.getButton("\u9080\u8bf7\u73a9\u5bb6"), vexViewReader.getButton("\u7533\u8bf7\u5217\u8868")));
            }
            else {
                VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyManageGui(vexViewReader.getButton("\u79bb\u5f00\u961f\u4f0d"), vexViewReader.getButton("\u89e3\u6563\u961f\u4f0d"), (VexViewMetadata)null, (VexViewMetadata)null));
            }
        }
        else if (vexViewReader.containsButton("\u79bb\u5f00\u961f\u4f0d")) {
            VexViewPacket.mc.displayGuiScreen((GuiScreen)new HytPartyManageGui(vexViewReader.getButton("\u79bb\u5f00\u961f\u4f0d"), (VexViewMetadata)null, (VexViewMetadata)null, (VexViewMetadata)null));
        }
    }
}

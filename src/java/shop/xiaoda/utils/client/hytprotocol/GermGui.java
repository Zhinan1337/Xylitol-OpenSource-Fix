// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.utils.client.hytprotocol;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.yaml.snakeyaml.Yaml;
import shop.xiaoda.module.modules.misc.Protocol;
import shop.xiaoda.utils.client.menu.button.MenuButton;

public class GermGui
        extends GuiScreen {
    private final float width;
    private final float height;
    private ScaledResolution sr;
    Map<String, Object> btnmap;
    private final List<MenuButton> buttons = new ArrayList<MenuButton>();
    private List<String> buttonTextList = new LinkedList<String>();

    public GermGui(float width, float height, Map<String, Object> btnmap) {
        this.width = width;
        this.height = height;
        this.btnmap = btnmap;
    }

    @Override
    public void initGui() {
        this.sr = new ScaledResolution(this.mc);
        for (Object scrollablePart : this.btnmap.values()) {
            if (!(scrollablePart instanceof Map)) continue;
            Map newMap4 = (Map)scrollablePart;
            Map relativeParts = (Map)newMap4.get("relativeParts");
            for (Object entry : relativeParts.entrySet()) {
                Map innerMap;
                Object texts;
                //Map.Entry
                Object value = entry.getClass();
                if (!(value instanceof Map) || (texts = (innerMap = (Map)value).get("texts")) == null) continue;
                String text = texts.toString().replace("[", "").replace("]", "").replace("\u00a78", "");
                this.buttonTextList.add(text);
                this.buttons.add(new MenuButton(text));
            }
        }
        this.buttons.forEach(MenuButton::initGui);
    }

    public String getToken(Map<String, Object> objectMap) {
        if (objectMap == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map newMap = (Map)objectMap.get(Protocol.field_3285);
        if (newMap != null) {
            Map relativeParts;
            Iterator iterator;
            Map newMap2 = null;
            String omg = "";
            for (Object s : newMap.keySet()) {
                newMap2 = (Map)newMap.get(s);
                omg = (String) s;
            }
            sb.append(omg).append("$");
            Map scrollableParts = (Map)newMap2.get("scrollableParts");
            Map newMap4 = null;
            Iterator iterator2 = scrollableParts.keySet().iterator();
            if (iterator2.hasNext()) {
                String s = (String)iterator2.next();
                newMap4 = (Map)scrollableParts.get(s);
                sb.append(s).append("$");
            }
            if ((iterator = (relativeParts = (Map)newMap4.get("relativeParts")).keySet().iterator()).hasNext()) {
                String s = (String)iterator.next();
                sb.append(s);
            }
            return sb.toString();
        }
        return "";
    }

    public String getSecToken(Map<String, Object> objectMap) {
        if (objectMap == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map newMap = (Map)objectMap.get(Protocol.field_3285);
        if (newMap != null) {
            Map newMap2 = null;
            String omg = "";
            for (Object s : newMap.keySet()) {
                newMap2 = (Map)newMap.get(s);
                omg = (String) s;
            }
            sb.append(omg).append("$");
            Map scrollableParts = (Map)newMap2.get("scrollableParts");
            Map newMap4 = null;
            newMap4 = (Map)scrollableParts.get(scrollableParts.keySet().stream().skip(1L).findFirst().get());
            sb.append((String)scrollableParts.keySet().stream().skip(1L).findFirst().get()).append("$");
            Map relativeParts = (Map)newMap4.get("relativeParts");
            Iterator iterator = relativeParts.keySet().iterator();
            if (iterator.hasNext()) {
                String s = (String)iterator.next();
                sb.append(s);
            }
            return sb.toString();
        }
        return "";
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        float x = (float)this.sr.getScaledWidth() / 2.0f - this.width / 2.0f;
        float y = (float)this.sr.getScaledHeight() / 2.0f - this.height / 2.0f;
        float buttonWidth = 140.0f;
        float buttonHeight = 25.0f;
        int count = 0;
        for (MenuButton button : this.buttons) {
            button.x = (float)this.sr.getScaledWidth() / 2.0f - buttonWidth / 2.0f;
            button.y = (float)this.sr.getScaledHeight() / 2.0f - buttonHeight / 2.0f - 25.0f + (float)count;
            button.width = buttonWidth;
            button.height = buttonHeight;
            String first = this.buttonTextList.get(0);
            AtomicReference<String> key = new AtomicReference<String>("");
            button.clickAction = () -> {
                if (button.text.equals(first)) {
                    Map objectMap = (Map)new Yaml().load(Protocol.field_955);
                    PacketBuffer packetBuffer3 = new PacketBuffer(Unpooled.buffer().writeInt(13));
                    packetBuffer3.writeString(Protocol.field_3285);
                    packetBuffer3.writeString(this.getToken(objectMap));
                    packetBuffer3.writeInt(0);
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("germmod-netease", packetBuffer3));
                } else {
                    Map.Entry firstEntry;
                    Map newMap4;
                    Map relativeParts;
                    PacketBuffer packetBuffer3 = new PacketBuffer(Unpooled.buffer().writeInt(13));
                    if (this.btnmap != null && (relativeParts = (Map)(newMap4 = (Map)this.btnmap.values().stream().skip(1L).findFirst().orElse(null)).get("relativeParts")) != null && (firstEntry = (Map.Entry)relativeParts.entrySet().stream().findFirst().orElse(null)) != null) {
                        key.set((String)firstEntry.getKey());
                    }
                    Map objectMap = (Map)new Yaml().load(Protocol.field_955);
                    packetBuffer3.writeString(Protocol.field_3285);
                    packetBuffer3.writeString(this.getSecToken(objectMap));
                    packetBuffer3.writeInt(0);
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("germmod-netease", packetBuffer3));
                    this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("germmod-netease", new PacketBuffer(Unpooled.buffer().writeInt(11)).writeString((String)key.get())));
                }
            };
            button.drawScreen(p_drawScreen_1_, p_drawScreen_2_);
            count = (int)((float)count + (buttonHeight + 5.0f));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.buttons.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}

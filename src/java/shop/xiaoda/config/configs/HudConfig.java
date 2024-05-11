//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.config.configs;

import shop.xiaoda.config.*;
import shop.xiaoda.*;
import shop.xiaoda.gui.ui.*;
import com.google.gson.*;
import java.util.*;
import shop.xiaoda.utils.render.*;

public class HudConfig extends Config
{
    public HudConfig() {
        super("hud.json");
    }
    
    public JsonObject saveConfig() {
        final JsonObject object = new JsonObject();
        for (final UiModule hud : Client.instance.uiManager.getModules()) {
            final JsonObject hudObject = new JsonObject();
            hudObject.addProperty("x", (Number)hud.posX);
            hudObject.addProperty("y", (Number)hud.posY);
            object.add(hud.getName(), (JsonElement)hudObject);
        }
        return object;
    }
    
    public void loadConfig(final JsonObject object) {
        for (final UiModule hud : Client.instance.uiManager.getModules()) {
            if (object.has(hud.getName())) {
                final JsonObject hudObject = object.get(hud.getName()).getAsJsonObject();
                hud.setPosX(hudObject.get("x").getAsDouble() * RenderUtil.width());
                hud.setPosY(hudObject.get("y").getAsDouble() * RenderUtil.height());
            }
        }
    }
}

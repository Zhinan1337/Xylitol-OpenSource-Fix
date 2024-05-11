//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.config.configs;

import shop.xiaoda.config.*;
import shop.xiaoda.*;
import shop.xiaoda.module.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.module.values.*;
import com.google.gson.*;
import java.util.*;
import java.awt.*;

public class ModuleConfig extends Config
{
    public ModuleConfig() {
        super("modules.json");
    }
    
    public JsonObject saveConfig() {
        final JsonObject object = new JsonObject();
        for (final Module module : Client.instance.moduleManager.getModules()) {
            final JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("state", Boolean.valueOf(module.getState()));
            moduleObject.addProperty("key", (Number)module.getKey());
            final JsonObject valuesObject = new JsonObject();
            for (final Value<?> value : module.getValues()) {
                if (value instanceof NumberValue) {
                    valuesObject.addProperty(value.getName(), (Number)((Value<Number>)value).getValue());
                }
                else if (value instanceof BoolValue) {
                    valuesObject.addProperty(value.getName(), Boolean.valueOf(((BoolValue)value).getValue()));
                }
                else if (value instanceof ModeValue) {
                    valuesObject.addProperty(value.getName(), ((ModeValue)value).getConfigValue());
                }
                else {
                    if (!(value instanceof ColorValue)) {
                        continue;
                    }
                    valuesObject.addProperty(value.getName(), (Number)((ColorValue)value).getColor());
                }
            }
            moduleObject.add("values", (JsonElement)valuesObject);
            object.add(module.getName(), (JsonElement)moduleObject);
        }
        return object;
    }
    
    public void loadConfig(final JsonObject object) {
        for (final Module module : Client.instance.moduleManager.getModules()) {
            if (object.has(module.getName())) {
                final JsonObject moduleObject = object.get(module.getName()).getAsJsonObject();
                if (moduleObject.has("state")) {
                    module.setState(moduleObject.get("state").getAsBoolean());
                }
                if (moduleObject.has("key")) {
                    module.setKey(moduleObject.get("key").getAsInt());
                }
                if (!moduleObject.has("values")) {
                    continue;
                }
                final JsonObject valuesObject = moduleObject.get("values").getAsJsonObject();
                for (final Value<?> value : module.getValues()) {
                    if (valuesObject.has(value.getName())) {
                        final JsonElement theValue = valuesObject.get(value.getName());
                        if (value instanceof NumberValue) {
                            ((NumberValue)value).setValue(theValue.getAsNumber().doubleValue());
                        }
                        else if (value instanceof BoolValue) {
                            ((BoolValue)value).setValue(theValue.getAsBoolean());
                        }
                        else if (value instanceof ModeValue) {
                            ((ModeValue)value).setMode(theValue.getAsString());
                        }
                        else {
                            if (!(value instanceof ColorValue)) {
                                continue;
                            }
                            final Color color = new Color(theValue.getAsInt());
                            ((ColorValue)value).setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB());
                        }
                    }
                }
            }
        }
    }
}

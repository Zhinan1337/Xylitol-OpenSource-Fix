// Decompiled with: CFR 0.152
// Class Version: 8
package shop.xiaoda.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import shop.xiaoda.Client;
import shop.xiaoda.config.Config;
import shop.xiaoda.config.configs.HudConfig;
import shop.xiaoda.config.configs.ModuleConfig;

public class ConfigManager {
    public static final List<Config> configs = new ArrayList<Config>();
    public static final File dir = new File(Client.mc.mcDataDir, "Xylitol");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager() {
        if (!dir.exists()) {
            dir.mkdir();
        }
        configs.add(new ModuleConfig());
        configs.add(new HudConfig());
    }

    public void loadConfig(String name) {
        File file = new File(dir, name);
        if (file.exists()) {
            System.out.println("Loading config: " + name);
            for (Config config : configs) {
                if (!config.getName().equals(name)) continue;
                try {
                    config.loadConfig(new JsonParser().parse(IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8)).getAsJsonObject());
                }
                catch (IOException e) {
                    System.out.println("Failed to load config: " + name);
                    e.printStackTrace();
                }
                break;
            }
        } else {
            System.out.println("Config " + name + " doesn't exist, creating a new one...");
            this.saveConfig(name);
        }
    }

    public void loadUserConfig(String name) {
        File file = new File(dir, name);
        if (file.exists()) {
            System.out.println("Loading config: " + name);
            for (Config config : configs) {
                if (!config.getName().equals("modules.json")) continue;
                try {
                    config.loadConfig(new JsonParser().parse(IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8)).getAsJsonObject());
                }
                catch (IOException e) {
                    System.out.println("Failed to load config: " + name);
                    e.printStackTrace();
                }

                break;
            }
        } else {
            System.out.println("Config " + name + " doesn't exist, creating a new one...");
            this.saveUserConfig(name);
        }
    }

    public void saveConfig(String name) {
        File file = new File(dir, name);
        try {
            System.out.println("Saving config: " + name);
            file.createNewFile();
            for (Config config : configs) {
                if (!config.getName().equals(name)) continue;
                FileUtils.writeByteArrayToFile(file, gson.toJson(config.saveConfig()).getBytes(StandardCharsets.UTF_8));
                break;
            }
        }
        catch (IOException e) {
            System.out.println("Failed to save config: " + name);
        }
    }

    public void saveUserConfig(String name) {
        File file = new File(dir, name);
        try {
            System.out.println("Saving config: " + name);
            file.createNewFile();
            for (Config config : configs) {
                if (!config.getName().equals("modules.json")) continue;
                FileUtils.writeByteArrayToFile(file, gson.toJson(config.saveConfig()).getBytes(StandardCharsets.UTF_8));
                break;
            }
        }
        catch (IOException e) {
            System.out.println("Failed to save config: " + name);
        }
    }

    public void loadAllConfig() {
        System.out.println("Loading all configs...");
        configs.forEach(it -> this.loadConfig(it.getName()));
    }

    public void saveAllConfig() {
        System.out.println("Saving all configs...");
        configs.forEach(it -> this.saveConfig(it.getName()));
    }
}

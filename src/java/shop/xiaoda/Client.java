
package shop.xiaoda;

import net.minecraft.client.renderer.texture.TextureManager;
import org.apache.commons.compress.utils.IOUtils;
import org.lwjgl.compatibility.display.Display;
import shop.xiaoda.gui.SplashScreen;

import net.minecraft.client.*;
import shop.xiaoda.module.Module;
import shop.xiaoda.utils.client.hytprotocol.packet.*;
import shop.xiaoda.config.*;
import shop.xiaoda.gui.altmanager.*;
import shop.xiaoda.command.*;
import shop.xiaoda.gui.ui.*;
import shop.xiaoda.module.*;
import shop.xiaoda.module.values.*;
import sun.misc.*;
import shop.xiaoda.utils.novoshader.*;
import java.lang.invoke.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.*;
import org.lwjgl.opengl.*;
import shop.xiaoda.event.*;
import shop.xiaoda.utils.*;
import shop.xiaoda.utils.component.*;
import shop.xiaoda.gui.*;
import java.util.*;
import shop.xiaoda.utils.client.menu.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import java.nio.*;
import org.apache.commons.compress.utils.*;
import java.io.*;
import java.util.List;


public class Client
{
    public static Minecraft mc;
    public static Client instance;
    public static String NAME;
    public static String VERSION;
    public static final ResourceLocation cape;
    public String USER;
    public VexViewPacket vexviewPacket;
    private static boolean logged;
    public String commandPrefix;
    public ConfigManager configManager;
    public AltManager altManager;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public UiManager uiManager;
    public List<Float> cGUIPosX;
    public List<Float> cGUIPosY;
    public List<Module> cGUIInSetting;
    public List<Value<?>> cGUIInMode;
    public BackgroundShader blobShader;
    
    public String getUser() {
        return this.USER;
    }
    
    public String getVersion() {
        return Client.VERSION;
    }
    
    public boolean isLogged() {
        return Client.logged;
    }
    
    public void setLogged(final boolean state) {
        Client.logged = state;
    }
    
    public Client() {
        this.USER = "1";
        this.commandPrefix = ".";
        this.cGUIPosX = new ArrayList<Float>();
        this.cGUIPosY = new ArrayList<Float>();
        this.cGUIInSetting = new ArrayList<Module>();
        this.cGUIInMode = new ArrayList<Value<?>>();
        Client.logged = false;
    }

    public void init() {
        Client.logged = true;
        try {
            try {
                    SplashScreen.setProgress(10, "ModuleManager");
                    Client.instance = this;
                    SplashScreen.setProgress(11, "EventManager");
                    this.altManager = new AltManager();
                    this.commandManager = new CommandManager();
                    this.configManager = new ConfigManager();
                    this.uiManager = new UiManager();
                    SplashScreen.setProgress(12, "Fonts");
                    this.vexviewPacket = new VexViewPacket();
                    SplashScreen.setProgress(13, "MemCleaner");
                    SplashScreen.setProgress(14, "Done");
                    this.setWindowIcon();
                    try {
                            Client.instance.setLogged(true);
                            Client.instance.USER = "Loser Xylitol";
                            Client.instance.moduleManager = new ModuleManager();
                            EventManager.register(Client.instance);
                            EventManager.register(new RotationComponent());
                            EventManager.register(new FallDistanceComponent());
                            EventManager.register(new InventoryClickFixComponent());
                            EventManager.register(new PingSpoofComponent());
                            EventManager.register(new BadPacketsComponent());
                            Client.instance.moduleManager.init();
                            Client.instance.commandManager.init();
                            Client.instance.uiManager.init();
                            Client.instance.configManager.loadAllConfig();
                            mc.displayGuiScreen(new BetterMainMenu());
                    }
                    catch (Exception ex13) {}
            }
            catch (Exception e) {
            }
        }
        catch (Throwable throwable) {
        }
    }
    
    
    public static void displayGuiScreen(GuiScreen guiScreenIn) {
        if (Client.mc.currentScreen != null) {
            Client.mc.currentScreen.onGuiClosed();
        }
        if (guiScreenIn == null && Client.mc.theWorld == null) {
            guiScreenIn = new BetterMainMenu();
        }
        else if (guiScreenIn == null && Client.mc.thePlayer.getHealth() <= 0.0f) {
            guiScreenIn = new GuiGameOver();
        }
        if (guiScreenIn instanceof BetterMainMenu) {
            Client.mc.gameSettings.showDebugInfo = false;
            Client.mc.ingameGUI.getChatGUI().clearChatMessages();
        }
        if ((Client.mc.currentScreen = guiScreenIn) != null) {
            Client.mc.setIngameNotInFocus();
            final ScaledResolution scaledresolution = new ScaledResolution(Client.mc);
            final int i = scaledresolution.getScaledWidth();
            final int j = scaledresolution.getScaledHeight();
            guiScreenIn.setWorldAndResolution(Client.mc, i, j);
            Client.mc.skipRenderWorld = false;
        }
        else {
            Client.mc.mcSoundHandler.resumeSounds();
            Client.mc.setIngameFocus();
        }
    }
    
    private void setWindowIcon() {
        final Util.EnumOS util$enumos = Util.getOSType();
        if (util$enumos != Util.EnumOS.OSX) {
            InputStream inputstream = null;
            InputStream inputstream2 = null;
            try {
                inputstream = Client.mc.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
                inputstream2 = Client.mc.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
                if (inputstream != null && inputstream2 != null) {
                    Display.setIcon(new ByteBuffer[] { Client.mc.readImageToBuffer(inputstream), Client.mc.readImageToBuffer(inputstream2) });
                }
            }
            catch (IOException ioexception) {
                final Minecraft mc = Client.mc;
                Minecraft.logger.error("Couldn't set icon", (Throwable)ioexception);
            }
            finally {
                IOUtils.closeQuietly((Closeable)inputstream);
                IOUtils.closeQuietly((Closeable)inputstream2);
            }
        }
    }
    
    public AltManager getAltManager() {
        return this.altManager;
    }


    
    static {
        Client.mc = Minecraft.getMinecraft();
        Client.NAME = "Xylitol Beta";
        Client.VERSION = "121023-2";
        cape = new ResourceLocation("express/cape/cape.png");
        Client.logged = false;
    }
}

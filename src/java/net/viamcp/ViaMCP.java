//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp;

import java.util.logging.*;
import io.netty.channel.*;
import java.io.*;
import net.viamcp.gui.*;
import org.apache.logging.log4j.*;
import net.viamcp.utils.*;
import com.google.common.util.concurrent.*;
import io.netty.channel.local.*;
import com.viaversion.viaversion.*;
import net.viamcp.platform.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.*;
import net.viamcp.loader.*;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.*;
import java.util.logging.Logger;

public class ViaMCP
{
    public final static int PROTOCOL_VERSION = 47;
    private static final ViaMCP instance = new ViaMCP();

    public static ViaMCP getInstance()
    {
        return instance;
    }

    private final java.util.logging.Logger jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaMCP"));
    private final CompletableFuture<Void> INIT_FUTURE = new CompletableFuture<>();

    private ExecutorService ASYNC_EXEC;
    private EventLoop EVENT_LOOP;

    private File file;
    private int version;
    private String lastServer;

    /**
     * Version Slider that works Asynchronously with the Version GUI
     * Please initialize this before usage, with initAsyncSlider() or initAsyncSlider(x, y, width (min. 110), height)
     */
    public AsyncVersionSlider asyncSlider;

    public void start()
    {
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ViaMCP-%d").build();
        ASYNC_EXEC = Executors.newFixedThreadPool(8, factory);

        EVENT_LOOP = new LocalEventLoopGroup(1, factory).next();
        EVENT_LOOP.submit(INIT_FUTURE::join);

        setVersion(PROTOCOL_VERSION);
        this.file = new File("ViaMCP");
        if (this.file.mkdir())
        {
            this.getjLogger().info("Creating ViaMCP Folder");
        }

        Via.init(ViaManagerImpl.builder().injector(new MCPViaInjector()).loader(new MCPViaLoader()).platform(new MCPViaPlatform(file)).build());

        MappingDataLoader.enableMappingsCache();
        ((ViaManagerImpl) Via.getManager()).init();

        new MCPBackwardsLoader(file);
        new MCPRewindLoader(file);

        INIT_FUTURE.complete(null);
    }

    public void initAsyncSlider()
    {
        this.initAsyncSlider(5, 5, 110, 20);
    }

    public void initAsyncSlider(int x, int y, int width, int height)
    {
        asyncSlider = new AsyncVersionSlider(-1, x, y, Math.max(width, 110), height);
    }

    public Logger getjLogger()
    {
        return jLogger;
    }

    public CompletableFuture<Void> getInitFuture()
    {
        return INIT_FUTURE;
    }

    public ExecutorService getAsyncExecutor()
    {
        return ASYNC_EXEC;
    }

    public EventLoop getEventLoop()
    {
        return EVENT_LOOP;
    }

    public File getFile()
    {
        return file;
    }

    public String getLastServer()
    {
        return lastServer;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public void setLastServer(String lastServer)
    {
        this.lastServer = lastServer;
    }
}

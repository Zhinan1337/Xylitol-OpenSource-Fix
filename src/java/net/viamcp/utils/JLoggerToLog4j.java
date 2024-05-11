//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package net.viamcp.utils;

import java.util.logging.*;
import java.text.*;

public class JLoggerToLog4j extends Logger
{
    private final org.apache.logging.log4j.Logger base;
    
    public JLoggerToLog4j(final org.apache.logging.log4j.Logger logger) {
        super("logger", null);
        this.base = logger;
    }
    
    @Override
    public void log(final LogRecord record) {
        this.log(record.getLevel(), record.getMessage());
    }
    
    @Override
    public void log(final Level level, final String msg) {
        if (level == Level.FINE) {
            this.base.debug(msg);
        }
        else if (level == Level.WARNING) {
            this.base.warn(msg);
        }
        else if (level == Level.SEVERE) {
            this.base.error(msg);
        }
        else if (level == Level.INFO) {
            this.base.info(msg);
        }
        else {
            this.base.trace(msg);
        }
    }
    
    @Override
    public void log(final Level level, final String msg, final Object param1) {
        if (level == Level.FINE) {
            this.base.debug(msg, new Object[] { param1 });
        }
        else if (level == Level.WARNING) {
            this.base.warn(msg, new Object[] { param1 });
        }
        else if (level == Level.SEVERE) {
            this.base.error(msg, new Object[] { param1 });
        }
        else if (level == Level.INFO) {
            this.base.info(msg, new Object[] { param1 });
        }
        else {
            this.base.trace(msg, new Object[] { param1 });
        }
    }
    
    @Override
    public void log(final Level level, final String msg, final Object[] params) {
        this.log(level, MessageFormat.format(msg, params));
    }
    
    @Override
    public void log(final Level level, final String msg, final Throwable params) {
        if (level == Level.FINE) {
            this.base.debug(msg, params);
        }
        else if (level == Level.WARNING) {
            this.base.warn(msg, params);
        }
        else if (level == Level.SEVERE) {
            this.base.error(msg, params);
        }
        else if (level == Level.INFO) {
            this.base.info(msg, params);
        }
        else {
            this.base.trace(msg, params);
        }
    }
}

package net.verox.arclight.util;

import net.verox.arclight.ArclightMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArclightLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger(ArclightMod.MOD_ID);

    public LogLevel logLevel;

    public ArclightLogger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void error(String msg){
        LOGGER.error(msg);
    }
    public void warn(String msg){
        LOGGER.warn(msg);
    }
    public void info(String msg){
        LOGGER.info(msg);
    }
    public void debug(String msg){
        if(logLevel.ordinal()>=LogLevel.DEBUG.ordinal()) LOGGER.info("[DEBUG] " + msg);
    }
    public void trace(String msg){
        if(logLevel.ordinal()>=LogLevel.TRACE.ordinal()) LOGGER.trace("[TRACE] " + msg);
    }


    public enum LogLevel {
        OFF,FATAL,ERROR,WARN,INFO,DEBUG,TRACE,ALL

    }
}

package club.mcams.carpet.logging;

import carpet.logging.HUDLogger;
import carpet.logging.LoggerRegistry;

import club.mcams.carpet.logging.logger.ServerRuntimeHUDLogger;

import java.lang.reflect.Field;

public class AmsCarpetLoggerRegistry {
    public static boolean __serverRuntime;

    public static void registerLoggers() {
        LoggerRegistry.registerLogger(ServerRuntimeHUDLogger.NAME, standardHUDLogger(ServerRuntimeHUDLogger.NAME, null, null));
    }

    public static HUDLogger standardHUDLogger(String logName, String def, String [] options) {
        return new HUDLogger(getLoggerField(logName), logName, def, options);
    }

    public static Field getLoggerField(String logName) {
        try {
            return AmsCarpetLoggerRegistry.class.getField("__" + logName);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException();
        }
    }
}
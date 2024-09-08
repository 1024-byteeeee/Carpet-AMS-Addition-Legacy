package club.mcams.carpet.logging;

import carpet.logging.LoggerRegistry;

import club.mcams.carpet.logging.logger.ServerRuntimeHUDLogger;

public class AmsCarpetHUDController {
    public static void updateHUD() {
        doHudLogging(AmsCarpetLoggerRegistry.__serverRuntime, ServerRuntimeHUDLogger.getInstance());
    }

    private static void doHudLogging(boolean condition, AbstractHUDLogger logger) {
        if (condition) {
            LoggerRegistry.getLogger(logger.getName()).log(logger::onHudUpdate);
        }
    }
}
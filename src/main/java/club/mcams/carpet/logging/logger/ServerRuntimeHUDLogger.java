package club.mcams.carpet.logging.logger;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.logging.AbstractHUDLogger;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.Messenger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.BaseText;

import java.time.Duration;
import java.time.Instant;

public class ServerRuntimeHUDLogger extends AbstractHUDLogger {
    private static final Translator translator = new Translator("logger.serverRuntime");
    public static final String NAME = "serverRuntime";
    private static final ServerRuntimeHUDLogger INSTANCE = new ServerRuntimeHUDLogger();

    private ServerRuntimeHUDLogger() {
        super(NAME);
    }

    public static ServerRuntimeHUDLogger getInstance() {
        return INSTANCE;
    }

    @Override
    public BaseText[] onHudUpdate(String option, PlayerEntity playerEntity) {
        Instant currentTime = Instant.now();
        Instant serverStartTime = Instant.ofEpochMilli(AmsServer.serverStartTimeMillis);
        Duration elapsedDuration = Duration.between(serverStartTime, currentTime);
        long elapsedSeconds = elapsedDuration.getSeconds();
        long hours = elapsedSeconds / 3600;
        long remainingSeconds = elapsedSeconds % 3600;
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        String formattedTime = String.format("%02d : %02d : %02d", hours, minutes, seconds);
        return new BaseText[]{
            Messenger.c(
                String.format("q %s ", translator.tr("server_runtime").getString()),
                String.format("c %s", formattedTime)
            )
        };
    }
}
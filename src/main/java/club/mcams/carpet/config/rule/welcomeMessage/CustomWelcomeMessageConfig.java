package club.mcams.carpet.config.rule.welcomeMessage;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.translations.Translator;
import club.mcams.carpet.utils.Messenger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomWelcomeMessageConfig {
    private static final Translator translator = new Translator("rule.welcomeMessage");

    public static void handleMessage(PlayerEntity player, MinecraftServer server) {
        try {
            Path path = server.getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir().toPath().resolve("carpetamsaddition/welcomeMessage.json");
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                JsonObject defaultConfig = new JsonObject();
                defaultConfig.addProperty(
                    "welcomeMessage",
                    String.format(
                        "§3§o %s \n §a%s/carpetamsaddition/welcomeMessage.json",
                        translator.tr("modify_content_in").getString(),
                        translator.tr("save_path").getString()
                    )
                );
                FileWriter writer = new FileWriter(path.toFile());
                Gson gson = new Gson();
                gson.toJson(defaultConfig, writer);
                writer.close();
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject config = jsonParser.parse(new FileReader(path.toString())).getAsJsonObject();
            String welcomeMessage = config.get("welcomeMessage").getAsString();
            player.sendMessage(Messenger.s(welcomeMessage));
        } catch (Exception e) {
            AmsServer.LOGGER.error("An error occurred while processing the welcome message configuration", e);
        }
    }
}

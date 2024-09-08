package club.mcams.carpet.config.rule.commandLeader;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.commands.rule.commandPlayerLeader.LeaderCommandRegistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LeaderConfig {
    public static void loadFromJson(String configFilePath) {
        Gson gson = new Gson();
        Path path = Paths.get(configFilePath);
        LeaderCommandRegistry.LEADER_LIST.clear();
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                Type type = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> simplifiedMap = gson.fromJson(json, type);
                LeaderCommandRegistry.LEADER_LIST.putAll(simplifiedMap);
            } catch (IOException e) {
                AmsServer.LOGGER.warn("Failed to load config", e);
            }
        }
    }

    public static void saveToJson(Map<String, String> customBlockMap, String configFilePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, String> simplifiedMap = new HashMap<>(customBlockMap);
        String json = gson.toJson(simplifiedMap);
        try {
            Path path = Paths.get(configFilePath);
            Files.createDirectories(path.getParent());
            Files.write(path, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            AmsServer.LOGGER.warn("Failed to save config", e);
        }
    }

    public static String getPath(MinecraftServer server) {
        File worldDirectory = server.getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir();
        return new File(worldDirectory, "carpetamsaddition/leader.json").getAbsolutePath();
    }
}

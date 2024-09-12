package club.mcams.carpet.config.rule.commandCustomMovableBlock;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.commands.rule.commandCustomMovableBlock.CustomMovableBlockRegistry;

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
import java.util.ArrayList;
import java.util.List;

public class CustomMovableBlockConfig {
    public static void loadFromJson(String configFilePath) {
        Gson gson = new Gson();
        Path path = Paths.get(configFilePath);
        CustomMovableBlockRegistry.CUSTOM_MOVABLE_BLOCKS.clear();
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                Type type = new TypeToken<List<String>>() {}.getType();
                List<String> simplifiedMap = gson.fromJson(json, type);
                CustomMovableBlockRegistry.CUSTOM_MOVABLE_BLOCKS.addAll(simplifiedMap);
            } catch (IOException e) {
                AmsServer.LOGGER.warn("Failed to load config", e);
            }
        }
    }

    public static void saveToJson(List<String> customBlockMap, String configFilePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<String> simplifiedMap = new ArrayList<>(customBlockMap);
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
        return new File(worldDirectory, "carpetamsaddition/custom_movable_block.json").getAbsolutePath();
    }
}

package club.mcams.carpet.config.rule.commandCustomBlockBlastResistance;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceCommandRegistry;
import club.mcams.carpet.utils.RegexTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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

public class CustomBlockBlastResistanceConfig {
    public static void loadFromJson(String configFilePath) {
        Gson gson = new Gson();
        Path path = Paths.get(configFilePath);
        CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.clear();
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                Type type = new TypeToken<Map<String, Float>>() {}.getType();
                Map<String, Float> simplifiedMap = gson.fromJson(json, type);
                for (Map.Entry<String, Float> entry : simplifiedMap.entrySet()) {
                    BlockState state = Registry.BLOCK.get(new Identifier(entry.getKey())).getDefaultState();
                    CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.put(state, entry.getValue());
                }
            } catch (IOException e) {
                AmsServer.LOGGER.warn("Failed to load config", e);
            }
        }
    }

    public static void saveToJson(Map<BlockState, Float> customBlockMap, String configFilePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Float> simplifiedMap = new HashMap<>();
        for (Map.Entry<BlockState, Float> entry : customBlockMap.entrySet()) {
            BlockState state = entry.getKey().getBlock().getDefaultState();
            String blockName = RegexTools.getBlockRegisterName(state.getBlock().toString());
            simplifiedMap.put(blockName, entry.getValue());
        }
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
        return new File(worldDirectory, "carpetamsaddition/custom_block_Blast_Resistance.json").getAbsolutePath();
    }
}

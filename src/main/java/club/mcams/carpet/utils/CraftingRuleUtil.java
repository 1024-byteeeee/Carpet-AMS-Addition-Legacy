package club.mcams.carpet.utils;

import carpet.CarpetServer;
import carpet.script.bundled.BundledModule;
import carpet.settings.ParsedRule;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.settings.CraftingRule;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class CraftingRuleUtil {
    public static void clearAmsDatapacks(MinecraftServer minecraftServer) {
        File datapackPath = new File(minecraftServer.getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir(), "datapacks/AmsData/data/");
        if (Files.isDirectory(datapackPath.toPath())) {
            try {
                FileUtils.deleteDirectory(datapackPath);
            } catch (IOException e) {
                AmsServer.LOGGER.error("Error deleting directory: {}", datapackPath, e);
            }
        }
    }

    public static void loadAmsDatapacks(MinecraftServer minecraftServer) {
        File worldDirectory = minecraftServer.getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir();
        String datapackPath = new File(worldDirectory, "datapacks").getAbsolutePath();
        if (Files.isDirectory(new File(datapackPath + "/Ams_flexibleData/").toPath())) {
            try {
                FileUtils.deleteDirectory(new File(datapackPath + "/Ams_flexibleData/"));
            } catch (IOException e) {
                AmsServer.LOGGER.error("Failed to delete directory Ams_flexibleData: {}", e.getMessage());
            }
        }
        datapackPath += "/AmsData/";
        boolean isFirstLoad = !Files.isDirectory(new File(datapackPath).toPath());
        try {
            Files.createDirectories(new File(datapackPath + "data/ams/recipes").toPath());
            Files.createDirectories(new File(datapackPath + "data/ams/advancements").toPath());
            Files.createDirectories(new File(datapackPath + "data/minecraft/recipes").toPath());
            copyFile("assets/carpetamsaddition/AmsRecipeTweakPack/pack.mcmeta", datapackPath + "pack.mcmeta");
        } catch (IOException e) {
            AmsServer.LOGGER.error("Failed to create directories or copy files: {}", e.getMessage());
        }
        copyFile(
            "assets/carpetamsaddition/AmsRecipeTweakPack/ams/advancements/root.json",
            datapackPath + "data/ams/advancements/root.json"
        );
        for (Field f : AmsServerSettings.class.getDeclaredFields()) {
            CraftingRule craftingRule = f.getAnnotation(CraftingRule.class);
            if (craftingRule == null) continue;
            registerCraftingRule(
                craftingRule.name().isEmpty() ? f.getName() : craftingRule.name(),
                craftingRule.recipes(),
                craftingRule.recipeNamespace(),
                datapackPath + "data/"
            );
        }
        AmsServer.minecraftServer.reload();
        if (isFirstLoad) {
            minecraftServer.getCommandManager().execute(minecraftServer.getCommandSource(), "/datapack enable \"file/AmsData\"");
        }
    }

    private static void registerCraftingRule(String ruleName, String[] recipes, String recipeNamespace, String dataPath) {
        updateCraftingRule(CarpetServer.settingsManager.getRule(ruleName), recipes, recipeNamespace, dataPath, ruleName);
        CarpetServer.settingsManager.addRuleObserver(
            (source, rule, s) -> {
                if (rule.name.equals(ruleName)) {
                    updateCraftingRule(rule, recipes, recipeNamespace, dataPath, ruleName);
                    AmsServer.minecraftServer.reload();
                }
            }
        );
    }

    private static void updateCraftingRule(ParsedRule<?> rule, String[] recipes, String recipeNamespace, String datapackPath, String ruleName) {
        ruleName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ruleName);
        if (rule.type == String.class) {
            String value = rule.getAsString();
            List<String> installedRecipes = Lists.newArrayList();
            try {
                Stream<Path> fileStream = Files.list(new File(datapackPath + recipeNamespace, "recipes").toPath());
                fileStream.forEach(( path -> {
                    for (String recipeName : recipes) {
                        String fileName = path.getFileName().toString();
                        if (fileName.startsWith(recipeName)) {
                            installedRecipes.add(fileName);
                        }
                    }
                } ));
                fileStream.close();
            } catch (IOException e) {
                AmsServer.LOGGER.error("Failed to list recipes in directory: {}", e.getMessage());
            }
            deleteRecipes(installedRecipes.toArray(new String[0]), recipeNamespace, datapackPath, ruleName, false);
            if (recipeNamespace.equals("ams")) {
                List<String> installedAdvancements = Lists.newArrayList();
                try {
                    Stream<Path> fileStream = Files.list(new File(datapackPath, "ams/advancements").toPath());
                    String finalRuleName = ruleName;
                    fileStream.forEach(( path -> {
                        String fileName = path.getFileName().toString().replace(".json", "");
                        if (fileName.startsWith(finalRuleName)) {
                            installedAdvancements.add(fileName);
                        }
                    } ));
                    fileStream.close();
                } catch (IOException e) {
                    AmsServer.LOGGER.error("Failed to list advancements in directory: {}", e.getMessage());
                }
                for (String advancement : installedAdvancements.toArray(new String[0])) {
                    removeAdvancement(datapackPath, advancement);
                }
            }
            if (!value.equals("off")) {
                List<String> tempRecipes = Lists.newArrayList();
                for (String recipeName : recipes) {
                    tempRecipes.add(recipeName + "_" + value + ".json");
                }
                copyRecipes(tempRecipes.toArray(new String[0]), recipeNamespace, datapackPath, ruleName + "_" + value);
            }
        }
        if (rule.type == int.class && (Integer) rule.get() > 0) {
            copyRecipes(recipes, recipeNamespace, datapackPath, ruleName);
            int value = (Integer) rule.get();
            for (String recipeName : recipes) {
                String filePath = datapackPath + recipeNamespace + "/recipes/" + recipeName;
                JsonObject jsonObject = readJson(filePath);
                assert jsonObject != null;
                jsonObject.getAsJsonObject("result").addProperty("count", value);
                writeJson(jsonObject, filePath);
            }
        } else if (rule.type == boolean.class && rule.getBoolValue()) {
            copyRecipes(recipes, recipeNamespace, datapackPath, ruleName);
        } else {
            deleteRecipes(recipes, recipeNamespace, datapackPath, ruleName, true);
        }
    }

    private static void writeAdvancement(String datapackPath, String ruleName, String[] recipes) {
        copyFile(
            "assets/carpetamsaddition/AmsRecipeTweakPack/ams/advancements/recipe_rule.json",
            datapackPath + "ams/advancements/" + ruleName + ".json"
        );
        JsonObject advancementJson = readJson(datapackPath + "ams/advancements/" + ruleName + ".json");
        if (advancementJson != null) {
            JsonArray recipeRewards = advancementJson.getAsJsonObject("rewards").getAsJsonArray("recipes");
            for (String recipeName : recipes) {
                recipeRewards.add("ams:" + recipeName.replace(".json", ""));
            }
            writeJson(advancementJson, datapackPath + "ams/advancements/" + ruleName + ".json");
        } else {
            JsonObject defaultJson = new JsonObject();
            writeJson(defaultJson, datapackPath + "ams/advancements/" + ruleName + ".json");
        }
    }

    private static void removeAdvancement(String datapackPath, String ruleName) {
        try {
            Files.deleteIfExists(new File(datapackPath + "ams/advancements/" + ruleName + ".json").toPath());
        } catch (IOException e) {
            AmsServer.LOGGER.error("Failed to delete advancement file: {}.json: {}", ruleName, e.getMessage());
        }
    }

    private static void copyFile(String resourcePath, String targetPath) {
        InputStream source = BundledModule.class.getClassLoader().getResourceAsStream(resourcePath);
        Path target = new File(targetPath).toPath();
        try {
            assert source != null;
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            AmsServer.LOGGER.error("Resource '{}' not found.", resourcePath);
        } catch (NullPointerException e) {
            AmsServer.LOGGER.error("Resource '{}' is null.", resourcePath);
        }
    }

    private static void deleteRecipes(String[] recipes, String recipeNamespace, String datapackPath, String ruleName, boolean removeAdvancement) {
        for (String recipeName : recipes) {
            try {
                Files.deleteIfExists(new File(datapackPath + recipeNamespace + "/" + "recipes", recipeName).toPath());
            } catch (IOException e) {
                AmsServer.LOGGER.error("Failed to delete recipe file {}: {}", recipeName, e.getMessage());
            }
        }
        if (removeAdvancement && recipeNamespace.equals("ams")) {
            removeAdvancement(datapackPath, ruleName);
        }
    }

    private static void copyRecipes(String[] recipes, String recipeNamespace, String datapackPath, String ruleName) {
        for (String recipeName : recipes) {
            copyFile(
                "assets/carpetamsaddition/AmsRecipeTweakPack/" + recipeNamespace + "/recipes/" + recipeName,
                datapackPath + recipeNamespace + "/recipes/" + recipeName
            );
        }
        if (recipeNamespace.equals("ams")) {
            writeAdvancement(datapackPath, ruleName, recipes);
        }
    }

    private static JsonObject readJson(String filePath) {
        JsonParser jsonParser = new JsonParser();
        try {
            FileReader reader = new FileReader(filePath);
            return jsonParser.parse(reader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            AmsServer.LOGGER.error("File not found: {}", filePath, e);
        }
        return null;
    }

    private static void writeJson(JsonObject jsonObject, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
            writer.close();
        } catch (IOException e) {
            AmsServer.LOGGER.error("Failed to write JSON to file '{}': {}", filePath, e.getMessage());
        }
    }
}

package club.mcams.carpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;

import club.mcams.carpet.commands.RegisterCommands;
import club.mcams.carpet.commands.rule.commandPlayerLeader.LeaderCommandRegistry;
import club.mcams.carpet.config.LoadConfigFromJson;
import club.mcams.carpet.config.rule.welcomeMessage.CustomWelcomeMessageConfig;
import club.mcams.carpet.helpers.rule.fancyFakePlayerName.FancyFakePlayerNameTeamController;
import club.mcams.carpet.logging.AmsCarpetLoggerRegistry;
import club.mcams.carpet.settings.CarpetRuleRegistrar;
import club.mcams.carpet.translations.AMSTranslations;
import club.mcams.carpet.translations.TranslationConstants;
import club.mcams.carpet.utils.CountRulesUtil;
import club.mcams.carpet.utils.CraftingRuleUtil;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class AmsServer implements CarpetExtension {
    private static final AmsServer AMS_SERVER_INSTANCE = new AmsServer();
    public static MinecraftServer minecraftServer;
    public static long serverStartTimeMillis;
    public static final int ruleCount = CountRulesUtil.countRules();
    public static final String fancyName = "Carpet AMS Addition";
    public static final String name = AmsServerMod.getModId();
    public static final String compactName = name.replace("-", "");  // carpetamsaddition
    private static final AmsServer INSTANCE = new AmsServer();
    public static final Logger LOGGER = LogManager.getLogger(fancyName);

    public static void init() {
        CarpetServer.manageExtension(INSTANCE);
        AMSTranslations.loadTranslations();
    }

    public static AmsServer getInstance() {
        return AMS_SERVER_INSTANCE;
    }

    @Override
    public String version() {
        return AmsServerMod.getModId();
    }

    @Override
    public void onGameStarted() {
        // let's /carpet handle our few simple settings
        LOGGER.info(String.format("%s v%s loaded! (Total rules: %d)", fancyName, AmsServerMod.getVersion(), ruleCount));
        LOGGER.info("open source: https://github.com/Minecraft-AMS/Carpet-AMS-Addition");
        LOGGER.info("issues: https://github.com/Minecraft-AMS/Carpet-AMS-Addition/issues");
        CarpetRuleRegistrar.register(CarpetServer.settingsManager, AmsServerSettings.class);
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) {
        if (AmsServerSettings.welcomeMessage) {
            CustomWelcomeMessageConfig.handleMessage(player, AmsServer.minecraftServer);
        }

        if (LeaderCommandRegistry.LEADER_LIST.containsValue(player.getUuidAsString())) {
            player.addStatusEffect(LeaderCommandRegistry.HIGH_LIGHT);
        }

        if (!LeaderCommandRegistry.LEADER_LIST.containsValue(player.getUuidAsString())) {
            player.removeStatusEffect(LeaderCommandRegistry.HIGH_LIGHT.getEffectType());
        }
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        serverStartTimeMillis = System.currentTimeMillis();
        minecraftServer = server;
    }

    @Override
    public void onServerClosed(MinecraftServer server) {
        FancyFakePlayerNameTeamController.removeBotTeam(server, AmsServerSettings.fancyFakePlayerName);
        CraftingRuleUtil.clearAmsDatapacks(server);
    }

    public void onServerLoadedWorlds_AMS(MinecraftServer server) {
        FancyFakePlayerNameTeamController.removeBotTeam(server, AmsServerSettings.fancyFakePlayerName);
        LoadConfigFromJson.load(server);
        CraftingRuleUtil.loadAmsDatapacks(server);
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        RegisterCommands.registerCommands(dispatcher);
    }

    //#if MC>=11500
    @Override
    public void registerLoggers() {
        AmsCarpetLoggerRegistry.registerLoggers();
    }
    //#endif

    //#if MC>=11500
    @Override
    public Map<String, String> canHasTranslations(String lang) {
        Map<String, String> trimmedTranslation = Maps.newHashMap();
        String prefix = TranslationConstants.CARPET_TRANSLATIONS_KEY_PREFIX;
        AMSTranslations.getTranslation(lang).forEach((key, value) -> {
            if (key.startsWith(prefix)) {
                String newKey = key.substring(prefix.length());
                trimmedTranslation.put(newKey, value);
            }
        });
        return trimmedTranslation;
    }
    //#endif
}



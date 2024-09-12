package club.mcams.carpet.config;

import club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceCommandRegistry;
import club.mcams.carpet.commands.rule.commandCustomCommandPermissionLevel.CustomCommandPermissionLevelRegistry;
import club.mcams.carpet.commands.rule.commandPlayerLeader.LeaderCommandRegistry;
import club.mcams.carpet.config.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceConfig;
import club.mcams.carpet.config.rule.commandCustomCommandPermissionLevel.CustomCommandPermissionLevelConfig;
import club.mcams.carpet.config.rule.commandCustomMovableBlock.CustomMovableBlockConfig;
import club.mcams.carpet.config.rule.commandLeader.LeaderConfig;

import net.minecraft.server.MinecraftServer;

public class LoadConfigFromJson {
    public static void load(MinecraftServer server) {
        clearMemory();
        CustomMovableBlockConfig.loadFromJson(CustomMovableBlockConfig.getPath(server));
        CustomBlockBlastResistanceConfig.loadFromJson(CustomBlockBlastResistanceConfig.getPath(server));
        CustomCommandPermissionLevelConfig.loadFromJson(CustomCommandPermissionLevelConfig.getPath(server));
        LeaderConfig.loadFromJson(LeaderConfig.getPath(server));
    }

    private static void clearMemory() {
        CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.clear();
        CustomCommandPermissionLevelRegistry.COMMAND_PERMISSION_MAP.clear();
        LeaderCommandRegistry.LEADER_LIST.clear();
    }
}

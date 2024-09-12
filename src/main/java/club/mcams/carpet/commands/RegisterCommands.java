package club.mcams.carpet.commands;

import club.mcams.carpet.commands.rule.amsUpdateSuppressionCrashFix.AmsUpdateSuppressionCrashFixCommandRegistry;
import club.mcams.carpet.commands.rule.commandAnvilInteractionDisabled.AnvilInteractionDisabledCommandRegistry;
import club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceCommandRegistry;
import club.mcams.carpet.commands.rule.commandCustomCommandPermissionLevel.CustomCommandPermissionLevelRegistry;
import club.mcams.carpet.commands.rule.commandCustomMovableBlock.CustomMovableBlockRegistry;
import club.mcams.carpet.commands.rule.commandGetSaveSize.GetSaveSizeCommandRegistry;
import club.mcams.carpet.commands.rule.commandGetSystemInfo.GetSystemInfoCommandRegistry;
import club.mcams.carpet.commands.rule.commandGoto.GotoCommandRegistry;
import club.mcams.carpet.commands.rule.commandHere.HereCommandRegistry;
import club.mcams.carpet.commands.rule.commandPacketInternetGroper.PingCommandRegistry;
import club.mcams.carpet.commands.rule.commandPlayerChunkLoadController.PlayerChunkLoadControllerCommandRegistry;
import club.mcams.carpet.commands.rule.commandPlayerLeader.LeaderCommandRegistry;
import club.mcams.carpet.commands.rule.commandWhere.WhereCommandRegistry;
import club.mcams.carpet.commands.rule.commandGetPlayerSkull.GetPlayerSkullCommandRegistry;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.ServerCommandSource;

public class RegisterCommands {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {

        AmsUpdateSuppressionCrashFixCommandRegistry.register(dispatcher);

        AnvilInteractionDisabledCommandRegistry.register(dispatcher);

        CustomBlockBlastResistanceCommandRegistry.register(dispatcher);

        HereCommandRegistry.register(dispatcher);

        WhereCommandRegistry.register(dispatcher);

        LeaderCommandRegistry.register(dispatcher);

        PingCommandRegistry.register(dispatcher);

        GetSaveSizeCommandRegistry.register(dispatcher);

        GetSaveSizeCommandRegistry.register(dispatcher);

        GotoCommandRegistry.register(dispatcher);

        CustomCommandPermissionLevelRegistry.register(dispatcher);

        GetPlayerSkullCommandRegistry.register(dispatcher);

        PlayerChunkLoadControllerCommandRegistry.register(dispatcher);

        GetSystemInfoCommandRegistry.register(dispatcher);

        CustomMovableBlockRegistry.register(dispatcher);

    }
}

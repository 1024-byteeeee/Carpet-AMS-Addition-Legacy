package club.mcams.carpet.helpers.rule.fancyFakePlayerName;

import club.mcams.carpet.utils.Messenger;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class FancyNameHelper {
    public static void addBotTeamNamePrefix(ServerPlayerEntity player, String teamName) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            Scoreboard scoreboard = player.getServer().getScoreboard();
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = FancyFakePlayerNameTeamController.addBotTeam(player.getServer(),teamName);
                team.setPrefix(Messenger.s("[bot] ").formatted(Formatting.BOLD));
                team.setColor(Formatting.DARK_GREEN);
            }
            scoreboard.addPlayerToTeam(player.getGameProfile().getName(), team);
        }
    }

    public static String addBotNameSuffix(final CommandContext<?> context, final String name, String teamName) {
        final String SUFFIX = "_bot";
        String playerName = StringArgumentType.getString(context, name);
        if (!Objects.equals(teamName, "false")) {
            playerName = playerName + SUFFIX;
        }
        return playerName;
    }
}

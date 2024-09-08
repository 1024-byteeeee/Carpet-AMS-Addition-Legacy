package club.mcams.carpet.helpers.rule.fancyFakePlayerName;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class FancyFakePlayerNameTeamController {
    public static void kickFakePlayerFromBotTeam(ServerPlayerEntity player, String teamName) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            Scoreboard scoreboard = player.getServer().getScoreboard();
            Team team = scoreboard.getTeam(teamName);
            if (team != null) {
                team.getPlayerList().remove(player.getGameProfile().getName());
            }
        }
    }

    public static Team addBotTeam(MinecraftServer server, String teamName) {
        return server.getScoreboard().addTeam(teamName);
    }

    public static void removeBotTeam(MinecraftServer server, String teamName) {
        Team fancyFakePlayerNameTeam = server.getScoreboard().getTeam(teamName);
        if (!Objects.equals(teamName, "false") && fancyFakePlayerNameTeam != null) {
            server.getScoreboard().removeTeam(fancyFakePlayerNameTeam);
        }
    }
}

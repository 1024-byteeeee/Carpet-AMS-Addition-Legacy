package club.mcams.carpet.helpers.rule.commandPlayerChunkLoadController;

import club.mcams.carpet.AmsServer;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class ChunkLoading {
    public static Map<String, Boolean> onlinePlayerMap = new HashMap<>();

    public static void setPlayerInteraction(String playerName, boolean b, boolean online) {
        if (playerFromName(playerName) == null) {
            return;
        }
        if (online) {
            onlinePlayerMap.put(playerName, b);
        }
    }

    protected static ServerPlayerEntity playerFromName(String name) {
        return AmsServer.minecraftServer.getPlayerManager().getPlayer(name);
    }
}
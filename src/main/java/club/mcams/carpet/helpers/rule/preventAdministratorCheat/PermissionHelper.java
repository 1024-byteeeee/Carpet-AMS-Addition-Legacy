package club.mcams.carpet.helpers.rule.preventAdministratorCheat;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class PermissionHelper {
    public static boolean canCheat(ServerCommandSource source) {
        return !(source.getEntity() instanceof ServerPlayerEntity) || !AmsServerSettings.preventAdministratorCheat;
    }
}

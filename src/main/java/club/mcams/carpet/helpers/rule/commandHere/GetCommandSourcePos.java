package club.mcams.carpet.helpers.rule.commandHere;

import net.minecraft.server.command.ServerCommandSource;

public class GetCommandSourcePos {
    public static int[] getPos(ServerCommandSource source) {
        int x = (int) source.getPosition().getX();
        int y = (int) source.getPosition().getY();
        int z = (int) source.getPosition().getZ();
        return new int[]{x, y, z};
    }
}

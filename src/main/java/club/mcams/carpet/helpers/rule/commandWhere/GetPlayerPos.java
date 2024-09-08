package club.mcams.carpet.helpers.rule.commandWhere;

import net.minecraft.entity.player.PlayerEntity;

public class GetPlayerPos {
    public static int[] getPos(PlayerEntity player) {
        //#if MC>=11500
        int x = (int) player.getX();
        int y = (int) player.getY();
        int z = (int) player.getZ();
        //#else
        //$$ int x = (int) player.x;
        //$$ int y = (int) player.y;
        //$$ int z = (int) player.z;
        //#endif
        return new int[]{x, y, z};
    }
}

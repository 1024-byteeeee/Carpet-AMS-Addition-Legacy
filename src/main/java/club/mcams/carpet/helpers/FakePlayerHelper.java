package club.mcams.carpet.helpers;

import carpet.patches.EntityPlayerMPFake;

import net.minecraft.entity.player.PlayerEntity;

public class FakePlayerHelper {
    public static boolean isFakePlayer(PlayerEntity player) {
        return player instanceof EntityPlayerMPFake;
    }
}

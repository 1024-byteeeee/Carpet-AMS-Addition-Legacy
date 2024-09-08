package club.mcams.carpet.helpers.rule.headHunter_commandGetPlayerSkull;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;

public class SkullSkinHelper {
    public static void writeNbtToPlayerSkull(PlayerEntity player, ItemStack headStack) {
        headStack.getOrCreateTag().putString("SkullOwner", player.getGameProfile().getName());
    }
}

package club.mcams.carpet.mixin.rule.headHunter;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.headHunter_commandGetPlayerSkull.SkullSkinHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
        method = "dropInventory",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"
        )
    )
    private void dropPlayerSkull(CallbackInfo ci) {
        if (AmsServerSettings.headHunter) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            ItemStack headStack = new ItemStack(Items.PLAYER_HEAD);
            SkullSkinHelper.writeNbtToPlayerSkull(player, headStack);
            player.dropStack(headStack);
        }
    }
}

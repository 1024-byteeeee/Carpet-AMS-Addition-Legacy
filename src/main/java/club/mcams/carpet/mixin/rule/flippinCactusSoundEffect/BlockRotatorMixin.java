package club.mcams.carpet.mixin.rule.flippinCactusSoundEffect;

import carpet.helpers.BlockRotator;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRotator.class)
public abstract class BlockRotatorMixin {
    @Inject(
        method = "flip_block",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
        )
    )
    private static void flipBlock(BlockState state, World world, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.flippinCactusSoundEffect != 0) {
            float volume, pitch;
            SoundEvent leverClick = SoundEvents.BLOCK_LEVER_CLICK;
            SoundEvent villagerAmbient = SoundEvents.ENTITY_VILLAGER_AMBIENT;
            SoundEvent endermanTeleport = SoundEvents.ENTITY_ENDERMAN_TELEPORT;
            SoundCategory blocks = SoundCategory.BLOCKS;
            SoundCategory players = SoundCategory.PLAYERS;
            switch (AmsServerSettings.flippinCactusSoundEffect) {
                case 1:
                    volume = 1.0F;
                    pitch = 0.95F;
                    player.playSound(leverClick, blocks, volume, pitch);
                    break;
                case 2:
                    volume = 1.0F;
                    pitch = 2.5F;
                    player.playSound(leverClick, blocks, volume, pitch);
                    break;
                case 3:
                    volume = 1.0F;
                    pitch = 0.75F;
                    player.playSound(leverClick, blocks, volume, pitch);
                    break;
                case 4:
                    volume = 1.0F;
                    pitch = 1.0F;
                    player.playSound(villagerAmbient, players, volume, pitch);
                    break;
                case 5:
                    volume = 1.0F;
                    pitch = 1.0F;
                    player.playSound(endermanTeleport, players, volume, pitch);
            }
        }
    }
}

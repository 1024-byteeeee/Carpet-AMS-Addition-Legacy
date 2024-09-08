package club.mcams.carpet.mixin.rule.sneakToEditSign;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSignBlock.class)
public abstract class AbstractSignBlockMixin {
    @Inject(
        //#if MC>=11500
        method = "onUse",
        //#else
        //$$ method = "activate",
        //#endif
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"
        ),
        cancellable = true
    )
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (
            AmsServerSettings.sneakToEditSign &&
            player.isSneaking() &&
            player.getMainHandStack().isEmpty() &&
            player.getOffHandStack().isEmpty()
        ) {
            SignBlockEntity signBlockEntity = (SignBlockEntity) world.getBlockEntity(pos);
            player.openEditSignScreen(signBlockEntity);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}

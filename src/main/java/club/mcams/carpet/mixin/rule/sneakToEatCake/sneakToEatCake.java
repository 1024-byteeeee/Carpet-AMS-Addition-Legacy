package club.mcams.carpet.mixin.rule.sneakToEatCake;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CakeBlock.class)
public abstract class sneakToEatCake {
    @WrapOperation(
        //#if MC>=11500
        method = "onUse",
        //#else
        //$$ method = "activate",
        //#endif
        at = @At(
            value = "INVOKE",
            //#if MC>=11500
            target = "Lnet/minecraft/block/CakeBlock;tryEat(Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/util/ActionResult;"
            //#else
            //$$ target = "Lnet/minecraft/block/CakeBlock;tryEat(Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Z"
            //#endif
        )
    )
    //#if MC>=11500
    private ActionResult tryEat(CakeBlock cakeBlock, IWorld world, BlockPos pos, BlockState state, PlayerEntity player, Operation<ActionResult> original) {
    //#else
    //$$ private boolean tryEat(CakeBlock cakeBlock, IWorld world, BlockPos pos, BlockState state, PlayerEntity player, Operation<Boolean> original) {
    //#endif
        if (AmsServerSettings.sneakToEatCake) {
            return
                player.isSneaking() ?
                original.call(cakeBlock, world, pos, state, player) :
                //#if MC>=11500
                ActionResult.FAIL;
                //#else
                //$$ false;
                //#endif
        } else {
            return original.call(cakeBlock, world, pos, state, player);
        }
    }
}

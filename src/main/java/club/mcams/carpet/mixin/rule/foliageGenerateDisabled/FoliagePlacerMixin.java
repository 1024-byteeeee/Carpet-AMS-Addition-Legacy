package club.mcams.carpet.mixin.rule.foliageGenerateDisabled;

import club.mcams.carpet.AmsServerSettings;

//#if MC<11500
//$$ import net.minecraft.block.BlockState;
//$$ import net.minecraft.state.property.Properties;
//$$ import net.minecraft.util.math.BlockPos;
//$$ import net.minecraft.world.ModifiableWorld;
//#endif

import net.minecraft.world.gen.feature.AbstractTreeFeature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//#if MC<11500
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#else
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#endif

//#if MC>=11500
@Mixin(AbstractTreeFeature.class)
public abstract class FoliagePlacerMixin {
    @Inject(method = "setLeavesBlockState", at = @At("HEAD"))
    private void generateSquare(CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.foliageGenerateDisabled) {
            cir.cancel();
        }
    }
}
//#else
//$$ @Mixin(AbstractTreeFeature.class)
//$$ public abstract class FoliagePlacerMixin {
//$$     @Inject(method = "setBlockStateWithoutUpdatingNeighbors", at = @At("HEAD"), cancellable = true)
//$$     private void generateLeaves(ModifiableWorld modifiableWorld, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
//$$         if (AmsServerSettings.foliageGenerateDisabled && blockState.contains(Properties.DISTANCE_1_7)) {
//$$             ci.cancel();
//$$         }
//$$     }
//$$ }
//#endif
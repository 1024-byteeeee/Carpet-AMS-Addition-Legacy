package club.mcams.carpet.mixin.rule.preventEndSpikeRespawn;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.gen.feature.EndSpikeFeature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin {
    @WrapOperation(
        method="generateSpike",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/gen/feature/EndSpikeFeature;setBlockState(Lnet/minecraft/world/ModifiableWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
        )
    )
    private void onSetBlockState(EndSpikeFeature endSpikeFeature, ModifiableWorld modifiableWorld, BlockPos blockPos, BlockState blockState, Operation<Void> original) {
        if (AmsServerSettings.preventEndSpikeRespawn.equals("false")) {
            original.call(endSpikeFeature, modifiableWorld, blockPos, blockState);
        }
    }

    @Inject(
        method="generateSpike",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/IWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
        ),
        cancellable = true
    )
    private void onSpawnEntity(CallbackInfo ci) {
        if (AmsServerSettings.preventEndSpikeRespawn.equals("true")) {
            ci.cancel();
        }
    }
}

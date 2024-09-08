package club.mcams.carpet.mixin.rule.extinguishedCampfire_campfireSmokeParticleDisabled;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {
    @ModifyReturnValue(method = "getPlacementState", at = @At("RETURN"))
    private BlockState setPlacementState(BlockState original) {
        if (AmsServerSettings.extinguishedCampfire && original != null) {
            return original.with(CampfireBlock.LIT, false);
        } else {
            return original;
        }
    }

    @Inject(method = "spawnSmokeParticle", at = @At("HEAD"), cancellable = true)
    private static void noSpawnSmokeParticle(CallbackInfo ci) {
        if (AmsServerSettings.campfireSmokeParticleDisabled) {
           ci.cancel();
        }
    }
}

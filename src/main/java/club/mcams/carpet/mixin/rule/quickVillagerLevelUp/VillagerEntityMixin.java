package club.mcams.carpet.mixin.rule.quickVillagerLevelUp;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.passive.VillagerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin implements VillagerEntityInvoker{
    @Inject(method = "getExperience", at = @At("HEAD"))
    private void quickLevelUp(CallbackInfoReturnable<Integer> cir) {
        if (AmsServerSettings.quickVillagerLevelUp && this.invokerGetVillagerData().getLevel() < 5) {
            this.invokerLevelUp();
        }
    }
}

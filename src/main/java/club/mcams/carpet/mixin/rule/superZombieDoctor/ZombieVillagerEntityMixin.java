package club.mcams.carpet.mixin.rule.superZombieDoctor;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.mob.ZombieVillagerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillagerEntity.class)
public abstract class ZombieVillagerEntityMixin implements ZombieVillagerEntityAccessor{
    @Inject(method = "tick", at = @At("HEAD"))
    private void modifyConversionTime(CallbackInfo ci) {
        if (AmsServerSettings.superZombieDoctor) {
            ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) (Object) this;
            if (zombieVillagerEntity.isConverting()) {
                this.setConversionTimer(0);
            }
        }
    }
}

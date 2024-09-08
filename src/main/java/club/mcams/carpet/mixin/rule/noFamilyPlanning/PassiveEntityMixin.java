package club.mcams.carpet.mixin.rule.noFamilyPlanning;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PassiveEntity.class)
public abstract class PassiveEntityMixin extends MobEntityWithAi {
    protected PassiveEntityMixin(EntityType<? extends MobEntityWithAi> type, World world) {
        super(type, world);
    }

    @Shadow
    @Final
    private static TrackedData<Boolean> CHILD;

    @ModifyReturnValue(method = "getBreedingAge", at = @At("RETURN"))
    private int getBreedingAge(int original) {
        if (AmsServerSettings.noFamilyPlanning && !this.dataTracker.get(CHILD)) {
            return 0;
        } else {
            return original;
        }
    }
}
package club.mcams.carpet.mixin.rule.shulkerHitLevitationDisabled_immuneShulkerBullet;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ShulkerBulletEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ShulkerBulletEntity.class)
public abstract class ShulkerBulletEntityMixin {
    @ModifyArg(
        method = "onHit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"
        )
    )
    private StatusEffectInstance noLevitation(StatusEffectInstance statusEffectInstance) {
        if (AmsServerSettings.shulkerHitLevitationDisabled || AmsServerSettings.immuneShulkerBullet) {
            return new StatusEffectInstance(StatusEffects.LEVITATION, 0);
        } else {
            return statusEffectInstance;
        }
    }

    @ModifyArg(
        method = "onHit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
        )
    )
    private float noDamage(float amount) {
        return AmsServerSettings.immuneShulkerBullet ? 0.0F : amount;
    }
}

package club.mcams.carpet.mixin.rule.enderPearlSoundEffect;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.thrown.ThrownEnderpearlEntity;
import net.minecraft.entity.thrown.ThrownItemEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity {
    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
        method = "onCollision",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
        )
    )
    private void onCollision(CallbackInfo ci) {
        if (AmsServerSettings.enderPearlSoundEffect) {
            this.world.playSound(
                null,
                //#if MC>=11500
                this.getX(), this.getY(), this.getZ(),
                //#else
                //$$ this.x, this.y, this.z,
                //#endif
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS,
                1.0F, 1.0F
            );
        }
    }
}

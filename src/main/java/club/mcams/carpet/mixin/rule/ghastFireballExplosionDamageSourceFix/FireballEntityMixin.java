package club.mcams.carpet.mixin.rule.ghastFireballExplosionDamageSourceFix;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft < 1.19.3")
@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin {
    @ModifyArg(
        method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
        at = @At(
            value ="INVOKE",
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"
        ),
        index = 0
    )
    public Entity fillUpExplosionOwner(@Nullable Entity entity) {
        if(AmsServerSettings.ghastFireballExplosionDamageSourceFix) {
            return (FireballEntity) (Object) this;
        } else {
            return entity;
        }
    }
}

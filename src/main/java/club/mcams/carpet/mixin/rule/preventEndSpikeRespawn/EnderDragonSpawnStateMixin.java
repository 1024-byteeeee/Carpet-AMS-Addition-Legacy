package club.mcams.carpet.mixin.rule.preventEndSpikeRespawn;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(targets="net/minecraft/entity/boss/dragon/EnderDragonSpawnState$3")
public abstract class EnderDragonSpawnStateMixin {
    @WrapOperation(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
        )
    )
    private boolean onRemoveBlock(ServerWorld serverWorld, BlockPos blockPos, boolean b, Operation<Boolean> original) {
        return Objects.equals(AmsServerSettings.preventEndSpikeRespawn, "false") ? original.call(serverWorld, blockPos, b) : false;
    }

    @WrapOperation(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"
        )
    )
    private Explosion onCreateExplosion(ServerWorld serverWorld, Entity entity, double x, double y, double z, float power, Explosion.DestructionType destructionType, Operation<Explosion> original) {
        return Objects.equals(AmsServerSettings.preventEndSpikeRespawn, "false") ? original.call(serverWorld, entity, x, y, z, power, destructionType) : null;
    }
}

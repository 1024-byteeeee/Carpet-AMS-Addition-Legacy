package club.mcams.carpet.mixin.rule.playerNoNetherPortalCrossing;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin {
    @WrapOperation(
        method = "onEntityCollision",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;canUsePortals()Z"
        )
    )
    private boolean onEntityCollision(Entity entity, Operation<Boolean> original) {
        if (AmsServerSettings.playerNoNetherPortalTeleport && entity instanceof PlayerEntity) {
            return false;
        } else {
            return original.call(entity);
        }
    }
}

package club.mcams.carpet.mixin.rule.mitePearl;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.thrown.ThrownEnderpearlEntity;
import net.minecraft.entity.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
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
            target = "Ljava/util/Random;nextFloat()F"
        )
    )
    private void onCollision(CallbackInfo ci) {
        if (AmsServerSettings.mitePearl) {
            Entity entity = this.getOwner();
            EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(this.world);
            if (entity instanceof ServerPlayerEntity && endermiteEntity != null) {
                //#if MC>=11500
                endermiteEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.yaw, entity.pitch);
                //#else
                //$$ endermiteEntity.refreshPositionAndAngles(entity.x, entity.y, entity.z, entity.yaw, entity.pitch);
                //#endif
                this.world.spawnEntity(endermiteEntity);
            }
        }
    }
}

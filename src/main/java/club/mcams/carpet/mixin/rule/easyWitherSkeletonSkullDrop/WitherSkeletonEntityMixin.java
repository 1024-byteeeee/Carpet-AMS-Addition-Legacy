package club.mcams.carpet.mixin.rule.easyWitherSkeletonSkullDrop;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {
    protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void dropEquipment(CallbackInfo ci) {
        if (AmsServerSettings.easyWitherSkeletonSkullDrop) {
            this.dropItem(Items.WITHER_SKELETON_SKULL);
        }
    }
}

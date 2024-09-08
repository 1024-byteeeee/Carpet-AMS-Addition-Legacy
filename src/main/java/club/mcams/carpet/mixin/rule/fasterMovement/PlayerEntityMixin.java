package club.mcams.carpet.mixin.rule.fasterMovement;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if (!Objects.equals(AmsServerSettings.fasterMovement, "VANILLA")) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            World world = player.getEntityWorld();
            DimensionType dimensionType = world.getDimension().getType();
            if (
                (AmsServerSettings.fasterMovementController == AmsServerSettings.fasterMovementDimension.END && dimensionType.equals(DimensionType.THE_END)) ||
                (AmsServerSettings.fasterMovementController == AmsServerSettings.fasterMovementDimension.NETHER && dimensionType.equals(DimensionType.THE_NETHER)) ||
                (AmsServerSettings.fasterMovementController == AmsServerSettings.fasterMovementDimension.OVERWORLD  && dimensionType.equals(DimensionType.OVERWORLD)) ||
                (AmsServerSettings.fasterMovementController == AmsServerSettings.fasterMovementDimension.ALL)
            ) {
                float speed = (float) getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue();
                switch (AmsServerSettings.fasterMovement) {
                    case "Ⅰ":
                        speed = 0.2F;
                        break;
                    case "Ⅱ":
                        speed = 0.3F;
                        break;
                    case "Ⅲ":
                        speed = 0.4F;
                        break;
                    case "Ⅳ":
                        speed = 0.5F;
                        break;
                    case "Ⅴ":
                        speed = 0.6F;
                }
                cir.setReturnValue(speed);
            }
        }
    }
}

package club.mcams.carpet.mixin.rule.breedableParrots;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity {
    protected ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    protected void initGoals(CallbackInfo ci) {
        if (!Objects.equals(AmsServerSettings.breedableParrots, "none")) {
            this.targetSelector.add(1, new AnimalMateGoal(this, 1.0F));
        }
    }

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!Objects.equals(AmsServerSettings.breedableParrots, "none")) {
            String item = stack.getItem().toString();
            if (Objects.equals(AmsServerSettings.breedableParrots, item)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "canBreedWith", at = @At("HEAD"), cancellable = true)
    private void canBreedWith(AnimalEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (
            !Objects.equals(AmsServerSettings.breedableParrots, "none") &&
            other instanceof ParrotEntity
        ) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "createChild", at = @At("HEAD"), cancellable = true)
    private void createChild(PassiveEntity mate, CallbackInfoReturnable<PassiveEntity> cir) {
        if (!Objects.equals(AmsServerSettings.breedableParrots, "none")) {
            PassiveEntity child = EntityType.PARROT.create(world);
            if (child != null) {
                child.setBreedingAge(-24000);
            }
            cir.setReturnValue(child);
        }
    }
}

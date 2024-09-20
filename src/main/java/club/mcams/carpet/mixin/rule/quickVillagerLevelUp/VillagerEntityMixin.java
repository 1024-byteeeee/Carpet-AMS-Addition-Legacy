package club.mcams.carpet.mixin.rule.quickVillagerLevelUp;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin implements VillagerEntityInvoker{
    @Inject(
        method = "interactMob",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/passive/VillagerEntity;beginTradeWith(Lnet/minecraft/entity/player/PlayerEntity;)V"
        )
    )
    private void quickLevelUp(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.quickVillagerLevelUp && this.invokerGetVillagerData().getLevel() < 5) {
            this.invokerLevelUp();
        }
    }
}

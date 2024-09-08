package club.mcams.carpet.mixin.rule.blueSkullController;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = net.minecraft.entity.boss.WitherEntity.class, priority = 168)
public abstract class WitherEntityMixin implements WitherEntityInvoker{
    @Inject(method = "method_6878", at = @At("HEAD"), cancellable = true)
    private void surelyShootBlueSkull(int headIndex, LivingEntity target, CallbackInfo ci) {
        if(AmsServerSettings.blueSkullController == AmsServerSettings.blueSkullProbability.SURELY) {
            //#if MC>=11500
            this.invokeShootSkullAt(headIndex, target.getX(), target.getY() + (double)target.getStandingEyeHeight() * 0.5, target.getZ(), true);
            //#else
            //$$ this.invokeShootSkullAt(headIndex, target.x, target.y + (double)target.getStandingEyeHeight() * 0.5, target.z, true);
            //#endif
            ci.cancel();
        }
    }

    @WrapOperation(
        method = "mobTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"
        )
    )
    private Difficulty modifyDifficulty(World instance, Operation<Difficulty> original) {
        if(AmsServerSettings.blueSkullController == AmsServerSettings.blueSkullProbability.NEVER) {
            return Difficulty.EASY;
        } else {
            return original.call(instance);
        }
    }
}
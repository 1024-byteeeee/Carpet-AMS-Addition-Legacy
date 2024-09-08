package club.mcams.carpet.mixin.rule.sharedVillagerDiscounts;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import static net.minecraft.village.VillageGossipType.MAJOR_POSITIVE;

@Mixin(VillagerGossips.class)
public abstract class VillagerGossipsMixin {

    @Shadow
    @Final
    private Map<UUID, Object> entityReputation;

    @Inject(method = "getReputationFor(Ljava/util/UUID;Ljava/util/function/Predicate;)I", at = @At("HEAD"), cancellable = true)
    private void getReputation(UUID target, Predicate<VillageGossipType> filter, CallbackInfoReturnable<Integer> cir) {
        if (AmsServerSettings.sharedVillagerDiscounts && filter.test(MAJOR_POSITIVE)) {
            GetValueForInvoker targetReputation = (GetValueForInvoker) entityReputation.get(target);
            int otherReputation = 0;
            if (targetReputation != null) {
                for (Object r : entityReputation.values()) {
                    GetValueForInvoker invoker = (GetValueForInvoker) r;
                    if (invoker != targetReputation) {
                        otherReputation += invoker.invokeGetValueFor(vgt -> filter.test(vgt) && !vgt.equals(MAJOR_POSITIVE));
                    }
                }
            }
            int majorPositiveReputation = 0;
            for (Object r : entityReputation.values()) {
                GetValueForInvoker invoker = (GetValueForInvoker) r;
                majorPositiveReputation += invoker.invokeGetValueFor(vgt -> vgt.equals(MAJOR_POSITIVE));
            }
            int totalReputation = otherReputation + Math.min(majorPositiveReputation, MAJOR_POSITIVE.maxValue * MAJOR_POSITIVE.multiplier);
            cir.setReturnValue(totalReputation);
        }
    }
}

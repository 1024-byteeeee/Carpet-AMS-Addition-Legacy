package club.mcams.carpet.mixin.rule.sharedVillagerDiscounts;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.function.Predicate;

@Mixin(VillagerGossips.class)
public abstract class VillagerGossipsMixin implements VillagerGossipsAccessor, VillagerGossips_ReputationInvoker {
    @Inject(method = "getReputationFor(Ljava/util/UUID;Ljava/util/function/Predicate;)I", at = @At("HEAD"), cancellable = true)
    private void getReputation(UUID target, Predicate<VillageGossipType> filter, CallbackInfoReturnable<Integer> cir) {
        if (AmsServerSettings.sharedVillagerDiscounts && filter.test(VillageGossipType.MAJOR_POSITIVE)) {
            VillagerGossips_ReputationInvoker targetReputation = (VillagerGossips_ReputationInvoker) this.getEntityReputation().get(target);
            int otherRep = 0;
            if (targetReputation != null) {
                otherRep = targetReputation.invokeGetValueFor(vgt -> filter.test(vgt) && !vgt.equals(VillageGossipType.MAJOR_POSITIVE));
            }
            int majorPositiveRep = 0;
            for (Object reputation : this.getEntityReputation().values()) {
                VillagerGossips_ReputationInvoker invoker = (VillagerGossips_ReputationInvoker) reputation;
                majorPositiveRep += invoker.invokeGetValueFor(vgt -> vgt.equals(VillageGossipType.MAJOR_POSITIVE));
            }
            int maxMajorPositiveRep = VillageGossipType.MAJOR_POSITIVE.maxValue * VillageGossipType.MAJOR_POSITIVE.multiplier;
            cir.setReturnValue(otherRep + Math.min(majorPositiveRep, maxMajorPositiveRep));
        }
    }
}

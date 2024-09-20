package club.mcams.carpet.mixin.rule.sharedVillagerDiscounts;

import net.minecraft.village.VillageGossipType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;

@Mixin(targets = "net.minecraft.village.VillagerGossips$Reputation")
public interface VillagerGossips_ReputationInvoker {
    @Invoker("getValueFor")
    int invokeGetValueFor(Predicate<VillageGossipType> gossipTypeFilter);
}

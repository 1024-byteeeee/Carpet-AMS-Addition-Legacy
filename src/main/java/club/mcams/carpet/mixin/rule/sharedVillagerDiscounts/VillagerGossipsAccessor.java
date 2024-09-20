package club.mcams.carpet.mixin.rule.sharedVillagerDiscounts;

import net.minecraft.village.VillagerGossips;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

@Mixin(VillagerGossips.class)
public interface VillagerGossipsAccessor {
    @Accessor("entityReputation")
    Map<UUID, Object> getEntityReputation();
}

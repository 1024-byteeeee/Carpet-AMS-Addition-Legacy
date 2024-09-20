package club.mcams.carpet.mixin.rule.fullMoonEveryDay;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.world.dimension.Dimension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Dimension.class)
public abstract class DimensionTypeMixin {
    @ModifyReturnValue(method = "getMoonPhase", at = @At("RETURN"))
    private int alwaysFullMoon(int original) {
        return AmsServerSettings.fullMoonEveryDay ? 0 : original;
    }
}

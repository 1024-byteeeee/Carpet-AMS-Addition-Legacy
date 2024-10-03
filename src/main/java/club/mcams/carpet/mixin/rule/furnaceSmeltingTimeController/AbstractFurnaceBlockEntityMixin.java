package club.mcams.carpet.mixin.rule.furnaceSmeltingTimeController;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
    @ModifyReturnValue(method = "getCookTime", at = @At("RETURN"))
    private int setCookTime(int original) {
        return AmsServerSettings.furnaceSmeltingTimeController != -1 ? AmsServerSettings.furnaceSmeltingTimeController : original;
    }
}

package club.mcams.carpet.mixin.rule.fakePeace;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(BlockState.class)
public abstract class BlockStateMixin {
    @ModifyReturnValue(method = "allowsSpawning", at = @At("RETURN"))
    private boolean allowsSpawning(boolean original) {
        return AmsServerSettings.fakePeace ? false : original;
    }
}

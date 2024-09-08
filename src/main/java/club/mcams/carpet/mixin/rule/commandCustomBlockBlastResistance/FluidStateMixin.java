package club.mcams.carpet.mixin.rule.commandCustomBlockBlastResistance;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceCommandRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(FluidState.class)
public interface FluidStateMixin {

    @Shadow
    BlockState getBlockState();

    @Inject(method = "getBlastResistance", at = @At("HEAD"), cancellable = true)
    default void getBlastResistance(CallbackInfoReturnable<Float> cir) {
        if (!Objects.equals(AmsServerSettings.commandCustomBlockBlastResistance, "false") && AmsServerSettings.enhancedWorldEater == -1.0F) {
            BlockState fluidState = this.getBlockState().getBlock().getDefaultState();
            if (CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.containsKey(fluidState)) {
                cir.setReturnValue(CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.get(fluidState));
            }
        }
    }
}

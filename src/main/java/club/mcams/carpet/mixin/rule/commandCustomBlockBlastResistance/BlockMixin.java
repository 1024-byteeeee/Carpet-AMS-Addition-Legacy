package club.mcams.carpet.mixin.rule.commandCustomBlockBlastResistance;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.commands.rule.commandCustomBlockBlastResistance.CustomBlockBlastResistanceCommandRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Final
    @Shadow
    protected StateManager<Block, BlockState> stateManager;

    @Inject(method = "getBlastResistance", at = @At("HEAD"), cancellable = true)
    private void getBlastResistance(CallbackInfoReturnable<Float> cir) {
        if (!Objects.equals(AmsServerSettings.commandCustomBlockBlastResistance, "false") && AmsServerSettings.enhancedWorldEater == -1.0F) {
            BlockState blockState = stateManager.getDefaultState().getBlock().getDefaultState();
            if (CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.containsKey(blockState)) {
                cir.setReturnValue(CustomBlockBlastResistanceCommandRegistry.CUSTOM_BLOCK_BLAST_RESISTANCE_MAP.get(blockState));
            }
        }
    }
}

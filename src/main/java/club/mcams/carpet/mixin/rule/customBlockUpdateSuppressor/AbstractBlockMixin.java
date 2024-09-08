package club.mcams.carpet.mixin.rule.customBlockUpdateSuppressor;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.customBlockUpdateSuppressor.BlockUpdateSuppressorExceptionHelper;
import club.mcams.carpet.utils.RegexTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static club.mcams.carpet.commands.rule.amsUpdateSuppressionCrashFix.AmsUpdateSuppressionCrashFixCommandRegistry.amsUpdateSuppressionCrashFixForceMode;

@Mixin(BlockState.class)
public abstract class AbstractBlockMixin {
    @Inject(method = "neighborUpdate", at = @At("HEAD"))
    private void neighborUpdate(World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean bl, CallbackInfo ci) {
        if (!Objects.equals(AmsServerSettings.customBlockUpdateSuppressor, "none")) {
            if (amsUpdateSuppressionCrashFixForceMode) {
                AmsServerSettings.amsUpdateSuppressionCrashFix = true;
            }
            String blockName = RegexTools.getBlockRegisterName(world.getBlockState(pos).getBlock().toString()); // Block{minecraft:bedrock} -> minecraft:bedrock
            if (Objects.equals(AmsServerSettings.customBlockUpdateSuppressor, blockName)) {
                BlockUpdateSuppressorExceptionHelper.throwException();
            }
        }
    }
}
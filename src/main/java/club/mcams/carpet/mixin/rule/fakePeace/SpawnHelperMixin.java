package club.mcams.carpet.mixin.rule.fakePeace;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import net.minecraft.entity.EntityCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {
    @Inject(method = "spawnEntitiesInChunk", at = @At(value = "HEAD"), cancellable = true)
    private static void allowsSpawning(
        EntityCategory category,
        //#if MC>=11500
        ServerWorld world,
        //#else
        //$$ World world,
        //#endif
        WorldChunk chunk, BlockPos spawnPos, CallbackInfo ci
    ) {
        if (!Objects.equals(AmsServerSettings.fakePeace, "false") && category.equals(EntityCategory.MONSTER)) {
            World serverWorld = Objects.requireNonNull(world.getServer()).getWorld(world.getDimension().getType());
            DimensionWrapper worldDimension = DimensionWrapper.of(serverWorld);
            Set<String> dimensionCP = new HashSet<>(Arrays.asList(AmsServerSettings.fakePeace.split(",")));
            if (dimensionCP.contains(worldDimension.getIdentifierString()) || Objects.equals(AmsServerSettings.fakePeace, "true")) {
                ci.cancel();
            }
        }
    }
}

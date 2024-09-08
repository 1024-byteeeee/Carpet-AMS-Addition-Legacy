package club.mcams.carpet.mixin.rule.regeneratingDragonEgg;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {

    @Shadow
    @Final
    private ServerWorld world;

    @Shadow
    private boolean previouslyKilled;

    @Shadow
    private UUID dragonUuid;

    @Inject(method = "dragonKilled", at = @At("HEAD"))
    private void dragonKilled(EnderDragonEntity dragon, CallbackInfo ci) {
        if (AmsServerSettings.regeneratingDragonEgg && this.previouslyKilled && dragon.getUuid().equals(this.dragonUuid)) {
            this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.DRAGON_EGG.getDefaultState());
        }
    }
}
package club.mcams.carpet.mixin.rule.blockChunkLoader;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.blockChunkLoader.BlockChunkLoaderHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Inject(method = "playNote", at = @At("HEAD"))
    private void playNoteMixin(
            //#if MC>=11900
            //$$ Entity entity,
            //$$ BlockState blockState,
            //#endif
            World world,
            BlockPos pos,
            CallbackInfo info
    ) {
        if (!Objects.equals(AmsServerSettings.noteBlockChunkLoader, "false")) {
            handleChunkLoading(world, pos);
        }
    }

    @Unique
    private void handleChunkLoading(World world, BlockPos pos) {
        if (!world.isClient) {
            ChunkPos chunkPos = new ChunkPos(pos);
            BlockState noteBlockUp = world.getBlockState(pos.up(1));
            if (Objects.equals(AmsServerSettings.noteBlockChunkLoader, "note_block")) {
                BlockChunkLoaderHelper.addNoteBlockTicket((ServerWorld) world, chunkPos);
            } else if (Objects.equals(AmsServerSettings.noteBlockChunkLoader, "bone_block")) {
                loadChunkIfMatch(world, chunkPos, noteBlockUp, Blocks.BONE_BLOCK);
            } else if (Objects.equals(AmsServerSettings.noteBlockChunkLoader, "wither_skeleton_skull")) {
                loadChunkIfMatch(world, chunkPos, noteBlockUp, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL);
            }
        }
    }

    @Unique
    private void loadChunkIfMatch(World world, ChunkPos chunkPos, BlockState blockState, Block... blocks) {
        for (Block block : blocks) {
            if (blockState.getBlock() == block) {
                BlockChunkLoaderHelper.addNoteBlockTicket((ServerWorld) world, chunkPos);
                break;
            }
        }
    }
}

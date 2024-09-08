package club.mcams.carpet.helpers.rule.blockChunkLoader;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class BlockChunkLoaderHelper {
    private static final ChunkTicketType<ChunkPos> NOTE_BLOCK_TICKET_TYPE = createChunkTicketType("note_block");
    private static final ChunkTicketType<ChunkPos> PISTON_BLOCK_TICKET_TYPE = createChunkTicketType("piston_block");
    private static final ChunkTicketType<ChunkPos> BELL_BLOCK_TICKET_TYPE = createChunkTicketType("bell_block");

    public static void addNoteBlockTicket(ServerWorld world, ChunkPos chunkPos) {
        addTicket(world, chunkPos, NOTE_BLOCK_TICKET_TYPE);
    }

    public static void addPistonBlockTicket(ServerWorld world, ChunkPos chunkPos) {
        addTicket(world, chunkPos, PISTON_BLOCK_TICKET_TYPE);
    }

    public static void addBellBlockTicket(ServerWorld world, ChunkPos chunkPos) {
        addTicket(world, chunkPos, BELL_BLOCK_TICKET_TYPE);
    }

    private static void addTicket(ServerWorld world, ChunkPos chunkPos, ChunkTicketType<ChunkPos> ticketType) {
        world.getChunkManager().addTicket(ticketType, chunkPos, getLoadRange(), chunkPos);
        blockChunkLoaderKeepWorldTickUpdate(world);
    }

    private static void blockChunkLoaderKeepWorldTickUpdate(ServerWorld world) {
        if (AmsServerSettings.blockChunkLoaderKeepWorldTickUpdate) {
            world.resetIdleTimeout();
        }
    }

    private static int getLoadTime() {
        return AmsServerSettings.blockChunkLoaderTimeController;
    }

    private static int getLoadRange() {
        return AmsServerSettings.blockChunkLoaderRangeController;
    }

    private static ChunkTicketType<ChunkPos> createChunkTicketType(String type) {
        //#if MC>=11500
        return ChunkTicketType.create(type, Comparator.comparingLong(ChunkPos::toLong), getLoadTime());
        //#else
        //$$ return ChunkTicketType.method_20628(type, Comparator.comparingLong(ChunkPos::toLong), getLoadTime());
        //#endif
    }
}
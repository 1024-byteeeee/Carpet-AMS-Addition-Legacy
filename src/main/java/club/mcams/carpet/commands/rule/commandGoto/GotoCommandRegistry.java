package club.mcams.carpet.commands.rule.commandGoto;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.utils.CommandHelper;
import club.mcams.carpet.utils.compat.DimensionWrapper;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.command.arguments.DimensionArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class GotoCommandRegistry {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
        CommandManager.literal("goto")
        .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandGoto))
        .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
        .executes(
            context -> executeSimpleTeleport(
            context.getSource().getPlayer(), DimensionArgumentType.getDimensionArgument(context, "dimension")
        ))
        .then(CommandManager.argument("destination", BlockPosArgumentType.blockPos())
        .executes(
            context -> executeTeleport(
            context.getSource().getPlayer(),
            DimensionArgumentType.getDimensionArgument(context, "dimension"),
            BlockPosArgumentType.getBlockPos(context, "destination")
        )))));
    }

    private static int executeTeleport(ServerPlayerEntity player, DimensionType targetDimension, BlockPos destinationPos) {
        MinecraftServer server = player.getServer();
        ServerWorld targetWorld = null;
        if (server != null) {
            targetWorld = server.getWorld(targetDimension);
        }
        if (targetWorld != null) {
            player.teleport(targetWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), player.getYaw(1), player.getPitch(1));
            return 1;
        } else {
            return 0;
        }
    }

    private static int executeSimpleTeleport(ServerPlayerEntity player, DimensionType targetWorld) {
        DimensionWrapper currentDimension = DimensionWrapper.of(player.getServerWorld());
        DimensionWrapper targetDimension = DimensionWrapper.of(targetWorld);
        return executeTeleport(player, targetWorld, calculatePos(player, currentDimension, targetDimension));
    }

    private static BlockPos calculatePos(ServerPlayerEntity player, DimensionWrapper currentDimension, DimensionWrapper targetDimension) {
        if (currentDimension.getValue().equals(DimensionType.OVERWORLD) && targetDimension.getValue().equals(DimensionType.THE_NETHER)) {
            //#if MC>=11500
            return new BlockPos(player.getX() / 8, player.getY(), player.getZ() / 8);
            //#else
            //$$ return new BlockPos(player.x / 8, player.y, player.z / 8);
            //#endif
        } else if (currentDimension.getValue().equals(DimensionType.THE_NETHER) && targetDimension.getValue().equals(DimensionType.OVERWORLD)) {
            //#if MC>=11500
            return new BlockPos(player.getX() * 8, player.getY(), player.getZ() * 8);
            //#else
            //$$ return new BlockPos(player.x * 8, player.y, player.z * 8);
            //#endif
        } else {
            return player.getBlockPos();
        }
    }
}

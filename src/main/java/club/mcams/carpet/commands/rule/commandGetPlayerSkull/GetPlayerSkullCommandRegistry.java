package club.mcams.carpet.commands.rule.commandGetPlayerSkull;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.headHunter_commandGetPlayerSkull.SkullSkinHelper;
import club.mcams.carpet.utils.CommandHelper;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class GetPlayerSkullCommandRegistry {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("getPlayerSkull")
            .requires(source -> CommandHelper.canUseCommand(source, AmsServerSettings.commandGetPlayerSkull))
            .then(CommandManager.argument("player", EntityArgumentType.players())
            .executes(context -> execute(
                context.getSource().getPlayer(), EntityArgumentType.getPlayer(context, "player"))
            ))
        );
    }

    private static int execute(PlayerEntity player, PlayerEntity targetPlayer) {
        ItemStack headStack = new ItemStack(Items.PLAYER_HEAD);
        SkullSkinHelper.writeNbtToPlayerSkull(targetPlayer, headStack);
        player.giveItemStack(headStack);
        return 1;
    }
}
